package tf.ferhat.icalculateeverything.HelperDB;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AnalysisHelperDao {
    @Query("SELECT * FROM AnalysisHelper WHERE id LIKE :id")
    AnalysisHelper findById(int id);

    @Query("DELETE FROM AnalysisHelper")
    void deleteAll();

    @Query("DELETE FROM AnalysisHelper WHERE id LIKE :id")
    void deleteById(int id);

    @Query("DELETE FROM AnalysisHelper WHERE key LIKE :key")
    void deleteByKey(String key);

    @Insert
    void insert(AnalysisHelper analysisHelper);

}
