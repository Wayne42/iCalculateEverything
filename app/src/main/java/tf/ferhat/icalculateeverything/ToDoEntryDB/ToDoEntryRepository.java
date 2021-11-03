package tf.ferhat.icalculateeverything.ToDoEntryDB;

/*
What is a Repository?

A Repository is a class that abstracts access to multiple data sources. The Repository is not part of the Architecture Components libraries, but is a suggested best practice for code separation and architecture.
Repository class handles data operations. It provides a clean API to the rest of the app for app data.
---

Why use a Repository?

A Repository manages query threads and allows you to use multiple backends.
In the most common example, the Repository implements the logic for deciding whether to fetch data from a network or use results cached in a local database.
*/

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ToDoEntryRepository {
    private ToDoEntryDao mToDoEntryDao;
    private LiveData<List<ToDoEntry>> mAllToDoEntries;

    //Add a constructor that gets a handle to the database and initializes the member variables.
    ToDoEntryRepository(Application application) {
        ToDoEntryRoomDatabase db = ToDoEntryRoomDatabase.getDatabase(application);
        mToDoEntryDao = db.toDoEntryDao();
        mAllToDoEntries = mToDoEntryDao.getAll();
    }

    //Add a wrapper for getAll(). Room executes all queries on a separate thread. Observed LiveData will notify the observer when the data has changed.
    LiveData<List<ToDoEntry>> getAll() {
        return mAllToDoEntries;
    }

    //Add a wrapper for the insert() method. You must call this on a non-UI thread or your app will crash. Room ensures that you don't do any long-running operations on the main thread, blocking the UI.
    public void insert (ToDoEntry entry) {
        new insertAsyncTask(mToDoEntryDao).execute(entry);
    }

    public void deleteById(int id){ new deleteByIdAsyncTask(mToDoEntryDao).execute(id); }


    public void deleteAll(){ new deleteAllAsyncTask(mToDoEntryDao).execute(); }


    public ToDoEntry findById(int id){ return mToDoEntryDao.findById(id); }


    public List<ToDoEntry> findByTitle(String title){ return mToDoEntryDao.findByTitle(title); }

    public List<ToDoEntry> findByCategory(String cat){ return mToDoEntryDao.findByCategory(cat); }



    //There is nothing magical about the AsyncTask, so here it is for you to copy.  // :) wow, this is some fine pitch black magic
    private static class insertAsyncTask extends AsyncTask<ToDoEntry, Void, Void> {
        private ToDoEntryDao mAsyncTaskDao;
        insertAsyncTask(ToDoEntryDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final ToDoEntry... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    //There is nothing magical about the AsyncTask, so here it is for you to copy.  // :) wow, this is some fine pitch black magic
    private static class deleteAllAsyncTask extends AsyncTask<ToDoEntry, Void, Void> {
        private ToDoEntryDao mAsyncTaskDao;
        deleteAllAsyncTask(ToDoEntryDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final ToDoEntry... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    //There is nothing magical about the AsyncTask, so here it is for you to copy.  // :) wow, this is some fine pitch black magic
    private static class deleteByIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        private ToDoEntryDao mAsyncTaskDao;
        deleteByIdAsyncTask(ToDoEntryDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Integer... params) {
            mAsyncTaskDao.deleteById(params[0]);
            return null;
        }
    }
}
