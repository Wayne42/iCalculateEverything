package tf.ferhat.icalculateeverything;

import android.content.Intent;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class AboutActivity  extends AppCompatActivity {

    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        header = findViewById(R.id.headerAboutActivity);
        header.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-50, -1, view.getWidth() + 50, view.getHeight());

            }
        });
        Button Donate = findViewById(R.id.button_donate);
        Donate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "Google Play does not allow donation buttons", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
