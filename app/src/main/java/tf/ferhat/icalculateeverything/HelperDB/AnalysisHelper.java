package tf.ferhat.icalculateeverything.HelperDB;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AnalysisHelper")
public class AnalysisHelper {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "key")
    public String key;

    @ColumnInfo(name = "val")
    public String val;

    public AnalysisHelper(String key, String value){
        this.key = key;
        this.val = value;
    }

    public int getId(){
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }

}
