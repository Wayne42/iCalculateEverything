package tf.ferhat.icalculateeverything;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

public class NewToDoEntryActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1111;
    private EditText mEditTitleView;
    private EditText mEditDescriptionView;
    private ImageView mImageView;
    private String base64imagethumbnail = "";
    private String base64imagefull = "";
    private EditText mCategory;
    private EditText mAdditionals;

    private static final int CAMERA_REQUEST_CODE = 1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todoentry);

        final TextView tv = findViewById(R.id.newToDoEntryHeader);
        tv.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-50, -1, view.getWidth() + 50, view.getHeight());

            }
        });
        //tv.setClipToOutline(false);
        tv.setText("New Entry");

        mEditTitleView = findViewById(R.id.edit_title);
        mEditDescriptionView = findViewById(R.id.edit_description);


        mImageView = findViewById(R.id.imagePreview);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      //  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                      //  startActivityForResult(intent, CAMERA_REQUEST_CODE);

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent chooser = new Intent(Intent.ACTION_CHOOSER);
                chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);
                chooser.putExtra(Intent.EXTRA_TITLE, "choose action");
                Intent[] intentArray = {cameraIntent};
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                if(!isStoragePermissionGranted()){
                    //Toast.makeText(NewToDoEntryActivity.this, "Need permission to continue", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(NewToDoEntryActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
                if(!isStoragePermissionGranted()){
                    return;
                }

                startActivityForResult(chooser, RESULT_LOAD_IMAGE);
                }
            }

        );

        mCategory = findViewById(R.id.edit_category);
        mAdditionals = findViewById(R.id.edit_additionals);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (  TextUtils.isEmpty(mEditTitleView.getText() )) { //check if empty
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String date = new Date().getTime() + "";

                    String title = mEditTitleView.getText().toString();
                    String description = mEditDescriptionView.getText().toString();

                    String cat = mCategory.getText().toString();
                    String add = mAdditionals.getText().toString();

                    title = TextUtils.isEmpty(title) ? "Sample Title" : title;
                    description = TextUtils.isEmpty(description)  ? "Empty Description" : description;
                    cat = TextUtils.isEmpty(cat)? "NoCategory" : cat;
                    add = TextUtils.isEmpty(add)  ? "" : add;
                    base64imagethumbnail = TextUtils.isEmpty(base64imagethumbnail) ? "" : base64imagethumbnail;
                    base64imagefull = TextUtils.isEmpty(base64imagefull) ? "" : base64imagefull;

                    replyIntent.putExtra("title", title);
                    replyIntent.putExtra("desc", description);
                    replyIntent.putExtra("date", date);
                    replyIntent.putExtra("base64picturethumbnail", base64imagethumbnail);
                    replyIntent.putExtra("base64picturefull", base64imagefull);

                    replyIntent.putExtra("cat", cat);
                    replyIntent.putExtra("add", add);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        final int MY_CAMERA_REQUEST_CODE = 100;

        //todo use appropriate resultCode in your case
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == FragmentActivity.RESULT_OK) {
            if (data.getData() != null) {
                // this case will occur in case of picking image from the Gallery,
                // but not when taking picture with a camera
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());

                    //Log.i("BASE64IMAGEURL", "onActivityResult GALLERY PICKED: " + bitmap.getHeight() + "H W" + bitmap.getWidth()  + ";;;" + base64imagefull + ";;;"+"    |END");
                    // do whatever you want with the Bitmap ....
                    base64imagefull = getEncodedString(bitmap);
                    // use thumbnail
                    float aspectRatio = bitmap.getWidth() /
                            (float) bitmap.getHeight();
                    int height = 160;
                    int width = Math.round(height * aspectRatio);

                    bitmap = Bitmap.createScaledBitmap(
                            bitmap, width, height, false);

                    base64imagethumbnail = getEncodedString(bitmap);
                    mImageView.setImageBitmap(getBitmapFromEncodedString(base64imagethumbnail));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // this case will occur when taking a picture with a camera
                Bitmap bitmap = null;


                Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED,
                                MediaStore.Images.ImageColumns.ORIENTATION}, MediaStore.Images.Media.DATE_ADDED,
                        null, "date_added DESC");


                if (cursor != null && cursor.moveToFirst()) {
                    Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                    String photoPath = uri.toString();
                    cursor.close();
                    if (photoPath != null) {
                        bitmap = BitmapFactory.decodeFile(photoPath);
                    }
                }

                if (bitmap == null) {
                    // for safety reasons you can
                    // use thumbnail if not retrieved full sized image
                    bitmap = (Bitmap) data.getExtras().get("data");
                }
                // do whatever you want with the Bitmap ....
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                base64imagefull = getEncodedString(bitmap);
                // use thumbnail
                float aspectRatio = bitmap.getWidth() /
                        (float) bitmap.getHeight();
                int height = 160;
                int width = Math.round(height * aspectRatio);

                bitmap = Bitmap.createScaledBitmap(
                        bitmap, width, height, false);

                base64imagethumbnail = getEncodedString(bitmap);
                mImageView.setImageBitmap(getBitmapFromEncodedString(base64imagethumbnail));

            }

        }
    }

    private String getEncodedString(Bitmap bitmap){
        //Log.i("IMGDEBUGBASE64IMAGE", "getEncodedString: " + bitmap.getByteCount() + " HEIGHT: " + bitmap.getHeight() + " WIDTH: " + bitmap.getWidth());
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        float aspectRatio = bitmap.getWidth() /
                (float) bitmap.getHeight();
        int height = 640;
        int width = 640;
        if(bitmap.getHeight() > height || bitmap.getWidth() > width){
            width = Math.round(height * aspectRatio);
            bitmap = Bitmap.createScaledBitmap(
                    bitmap, width, height, false);
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);


        byte[] imageArr = os.toByteArray();

        return Base64.encodeToString(imageArr, Base64.URL_SAFE);

    }

    private Bitmap getBitmapFromEncodedString(String encodedString){
        byte[] arr = Base64.decode(encodedString, Base64.URL_SAFE);
        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        //Log.i("IMGDEBUGBASE64IMAGE", "getBitmapFromEncodedString: " + img.getByteCount() + " HEIGHT: " + img.getHeight() + " WIDTH: " + img.getWidth());
        return img;
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted");
                return true;
            } else {

                //Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            //Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}