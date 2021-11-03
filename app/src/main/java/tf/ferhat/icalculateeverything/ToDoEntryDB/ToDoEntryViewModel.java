package tf.ferhat.icalculateeverything.ToDoEntryDB;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


/*
What is a ViewModel?

The ViewModel's role is to provide data to the UI and survive configuration changes.
A ViewModel acts as a communication center between the Repository and the UI.
You can also use a ViewModel to share data between fragments. The ViewModel is part of the lifecycle library.
--

Why use a ViewModel?

A ViewModel holds your app's UI data in a lifecycle-conscious way that survives configuration changes.
Separating your app's UI data from your Activity and Fragment classes lets you better follow the single
responsibility principle:
Your activities and fragments are responsible for drawing data to the screen, while your ViewModel can
take care of holding and processing all the data needed for the UI.
--

In the ViewModel, use LiveData for changeable data that the UI will use or display. Using LiveData has several benefits:

    You can put an observer on the data (instead of polling for changes) and only update the
    the UI when the data actually changes.
    The Repository and the UI are completely separated by the ViewModel. There are no database calls from the ViewModel, making the code more testable.
 */
public class ToDoEntryViewModel extends AndroidViewModel {
    private ToDoEntryRepository mRepository;
    private LiveData<List<ToDoEntry>> mAllEntries;


    public ToDoEntryViewModel (Application application) {
        super(application);
        mRepository = new ToDoEntryRepository(application);
        mAllEntries = mRepository.getAll();
    }

    //Add a "getter" method for all the entries. This completely hides the implementation from the UI.
    public LiveData<List<ToDoEntry>> getAll() { return mAllEntries; }

    //Create a wrapper insert() method that calls the Repository's insert() method. In this way, the implementation of insert() is completely hidden from the UI.
    public void insert(ToDoEntry word) { mRepository.insert(word); }

    public void deleteAll() { mRepository.deleteAll(); }

    public void deleteById(int id) { mRepository.deleteById(id); }


    public ToDoEntry findById(int id) { return mRepository.findById(id); }


    public void findByTitle(String title) { mRepository.findByTitle(title); }

    public void findByCategory(String cat) { mRepository.findByCategory(cat); }

}
