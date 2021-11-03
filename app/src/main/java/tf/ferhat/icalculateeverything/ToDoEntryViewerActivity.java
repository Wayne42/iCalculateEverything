package tf.ferhat.icalculateeverything;

import android.app.Activity;
import android.graphics.Outline;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import tf.ferhat.icalculateeverything.ToDoEntryDB.ToDoEntry;
import tf.ferhat.icalculateeverything.ToDoEntryDB.ToDoEntryViewModel;

public class ToDoEntryViewerActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_menu_totoentryviewer, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.deleteThisToDoEntry){
            mToDoEntryViewModel.deleteById(idHelper);
            finish();
            Toast.makeText(this, "Entry removed", Toast.LENGTH_LONG).show();

        }else if(item.getItemId() == R.id.exportThisToDoEntry){
            Toast.makeText(this, "Exportfunction not available yet", Toast.LENGTH_LONG).show();
        }
        else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private ToDoEntryViewModel mToDoEntryViewModel;



    private TextView header;
    private int idHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todoentry_viewer);
        final int id = getIntent().getExtras().getInt("ID");
        idHelper = id;

        mToDoEntryViewModel = ViewModelProviders.of(this).get(ToDoEntryViewModel.class);

        header = findViewById(R.id.headerAboutActivity);
        header.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-50, -1, view.getWidth() + 50, view.getHeight());

            }
        });

        new ToDoEntryAsyncTask(this, id, mToDoEntryViewModel).execute();
    }


    private class ToDoEntryAsyncTask extends AsyncTask<Void, Void, Void> {

        //Prevent leak
        private Activity toDoEntryViewerActivity;
        private int id;
        private ToDoEntryViewModel tdevm;

        public ToDoEntryAsyncTask(Activity activity, int id, ToDoEntryViewModel v) {
            toDoEntryViewerActivity = activity;
            this.id = id;
            tdevm = v;
        }

        @Override
        protected Void doInBackground(Void... params) {
            final ToDoEntry toDisplay = tdevm.findById(id);


            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    TextView header = toDoEntryViewerActivity.findViewById(R.id.headerAboutActivity);
                    header.setText( toDisplay.getTitle() );

                    TextView desc = toDoEntryViewerActivity.findViewById(R.id.toDoEntryDescription);
                    desc.setText( toDisplay.getDescription());

                    TextView cat = toDoEntryViewerActivity.findViewById(R.id.toDoEntryCategory);
                    cat.setText( toDisplay.getCategory());

                    TextView date = toDoEntryViewerActivity.findViewById(R.id.toDoEntryDate);
                    date.setText( toDisplay.getDateAsString());

                    TextView add = toDoEntryViewerActivity.findViewById(R.id.toDoEntryAdditionals);
                    add.setText( toDisplay.getAdditionals());
                    ImageView image = toDoEntryViewerActivity.findViewById(R.id.toDoEntryImage);
                    if( TextUtils.isEmpty(toDisplay.getBase64pictureFull()) ){
                        image.setVisibility(View.GONE);
                    }else{
                        image.setImageBitmap(toDisplay.getImageFull());
                    }
                }
            });


            return null;
        }

    }

}
