package tf.ferhat.icalculateeverything;


import android.animation.ValueAnimator;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class UnrealMainActivity extends AppCompatActivity {
    private int activeScreen = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.about){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            Toast.makeText(this, "work in progress;\ncreated with at least some love by ferhat tohidi far", Toast.LENGTH_LONG).show();
        }else if(item.getItemId() == R.id.settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            //Toast.makeText(this, "No settings available yet", Toast.LENGTH_LONG).show();
        }
        else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unreal_main);

        final TextView tv = findViewById(R.id.headerTextView);
        tv.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(-50, -1, view.getWidth() + 50, view.getHeight());

            }
        });
        //tv.setClipToOutline(false);
        tv.setText("Entries");

        final LinearLayout ll = findViewById(R.id.linearLayout);
        ll.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 24);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.unrealFragment, new FrontFragment()).commit();
        final Button frontButton = findViewById(R.id.MENU_FRONT_BUTTON);
        final Button backButton = findViewById(R.id.MENU_BACK_BUTTON);

        frontButton.setBackground(getResources().getDrawable(R.drawable.btn_rounded_top_left_active));
        frontButton.setTextColor(getResources().getColor(R.color.color50TransparentWhite));

        backButton.setBackground(getResources().getDrawable(R.drawable.btn_rounded_top_right));
        backButton.setTextColor(getResources().getColor(R.color.color50TransparentWhite));

        frontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeScreen == 1){
                    activeScreen = 0;
                }else{return;}
                getSupportFragmentManager().beginTransaction().replace(R.id.unrealFragment, new FrontFragment()).commit();
                animateBackwards();
                frontButton.setBackground(getResources().getDrawable(R.drawable.btn_rounded_top_left_active));
                backButton.setBackground(getResources().getDrawable(R.drawable.btn_rounded_top_right));

                tv.setText("Entries");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeScreen == 0){
                    activeScreen = 1;
                }else{return;}
                getSupportFragmentManager().beginTransaction().replace(R.id.unrealFragment, new BackFragment()).commit();
                animateForwards();
                frontButton.setBackground(getResources().getDrawable(R.drawable.btn_rounded_top_left));
                backButton.setBackground(getResources().getDrawable(R.drawable.btn_rounded_top_right_active));

                tv.setText("Analysis");
            }
        });

        animateBackwards();
    }

    public void animateForwards(){

        final ImageView backgroundOne = (ImageView) findViewById(R.id.unrealBackground1);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.unrealBackground2);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(200L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(-translationX);
                backgroundTwo.setTranslationX(-(translationX - width));
            }
        });
        animator.start();
    }


    public void animateBackwards(){

        final ImageView backgroundOne = (ImageView) findViewById(R.id.unrealBackground1);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.unrealBackground2);


        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(200L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = -(width * progress);

                backgroundTwo.setTranslationX(-translationX);
                backgroundOne.setTranslationX(-(translationX + width));
            }
        });
        animator.start();
    }


}
