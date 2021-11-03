package tf.ferhat.icalculateeverything.ToDoEntryDB;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/* SQL Table Structure for ToDoEntry Objects defined here*/


@Entity(tableName = "ToDoEntries")
public class ToDoEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "duetodate")
    public String duetodate;

    @ColumnInfo (name = "base64picture_thumbnail")
    public String base64picturethumbnail;

    @ColumnInfo (name = "base64picture_full")
    public String base64picturefull;

    @ColumnInfo (name = "additionals")
    public String additionals;

    @ColumnInfo (name = "category")
    public String category;


    public ToDoEntry (String title, String description,String duetodate, String base64picturethumbnail, String base64picturefull, String category, String additionals){
        this.title = title;
        this.description = description;
        this.duetodate = duetodate;
        this.base64picturethumbnail = base64picturethumbnail;
        this.base64picturefull = base64picturefull;
        this.category = category;
        this.additionals = additionals;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDuetodate() {
        return duetodate;
    }

    public String getBase64pictureThumbnail() {
        return base64picturethumbnail;
    }

    public String getBase64pictureFull() {
        return base64picturefull;
    }

    public String getCategory() {
        return category;
        //return TextUtils.isEmpty(category) ? "no category" : category;
    }

    public String getAdditionals() {
        return additionals;
    }

    public String getDateAsString(){
        return new Date(Long.parseLong(getDuetodate())).toLocaleString();
    }

    public Bitmap getImageThumbnail(){
        byte[] arr = Base64.decode(getBase64pictureThumbnail(), Base64.URL_SAFE);
        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        return img;
    }

    public Bitmap getImageFull(){
        byte[] arr = Base64.decode(getBase64pictureFull(), Base64.URL_SAFE);
        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        return img;
    }

    public void setId(int id){
        this.id = id;
    }

}

