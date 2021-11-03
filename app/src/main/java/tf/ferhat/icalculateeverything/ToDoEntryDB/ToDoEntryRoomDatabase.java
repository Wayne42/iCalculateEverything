package tf.ferhat.icalculateeverything.ToDoEntryDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ToDoEntry.class}, version = 1)
public abstract class ToDoEntryRoomDatabase extends RoomDatabase {
    public abstract ToDoEntryDao toDoEntryDao();


    //Make the RoomDatabase a singleton to prevent having multiple instances of the database opened at the same time.
    private static volatile ToDoEntryRoomDatabase INSTANCE;

    static ToDoEntryRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ToDoEntryRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    //Add the code to get a database. This code uses Room's database builder to create a RoomDatabase object in the application context from the ToDoEntryRoomDatabase class and names it "todoentry_database".
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ToDoEntryRoomDatabase.class, "todoentry_database")//
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}




