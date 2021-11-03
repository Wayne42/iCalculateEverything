package tf.ferhat.icalculateeverything;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;
public class SettingsActivity  extends AppCompatActivity {

    private TextView header;
    private TextView pinEditField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        header = findViewById(R.id.headerSettingsActivity);
        header.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-50, -1, view.getWidth() + 50, view.getHeight());

            }
        });

        pinEditField = findViewById(R.id.editTextPin);
        SharedPreferences mySharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplication());

        int value = mySharedPrefs.getInt("pin", 7777);
        pinEditField.setText(value + "");



        Button save = findViewById(R.id.button_save_settings);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int pin;
                try{
                    pin = Integer.parseInt( pinEditField.getText().toString() ) ;
                }catch(Exception e){
                    Toast.makeText(SettingsActivity.this, "Pin has to be only Numbers", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences mySharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getApplication());
                mySharedPrefs.edit().putInt("pin", pin).commit();
                Toast.makeText(SettingsActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
