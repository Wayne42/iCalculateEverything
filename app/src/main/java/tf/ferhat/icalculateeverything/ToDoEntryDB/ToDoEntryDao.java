package tf.ferhat.icalculateeverything.ToDoEntryDB;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


//used by room to access the database
//database access object?
@Dao
public interface ToDoEntryDao {
    @Query("SELECT * FROM ToDoEntries ORDER BY id DESC")
    LiveData<List<ToDoEntry>> getAll();

    @Query("SELECT * FROM ToDoEntries WHERE title LIKE :title")
    List<ToDoEntry> findByTitle(String title);

    @Query("SELECT * FROM ToDoEntries WHERE id LIKE :id")
    ToDoEntry findById(int id);

    @Query("SELECT * FROM ToDoEntries WHERE category LIKE :category")
    List<ToDoEntry> findByCategory(String category);

    @Query("DELETE FROM ToDoEntries")
    void deleteAll();

    @Query("DELETE FROM ToDoEntries WHERE id LIKE :id")
    void deleteById(int id);

    @Query("DELETE FROM ToDoEntries WHERE category LIKE :category")
    void deleteByCategory(String category);

    @Insert
    void insert(ToDoEntry entry);


}
