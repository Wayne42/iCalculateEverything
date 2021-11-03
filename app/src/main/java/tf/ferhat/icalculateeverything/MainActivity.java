package tf.ferhat.icalculateeverything;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    private EditText displayCalculation;
    private TextView displayResult;
    private boolean isSaveToCalculate = true;

    Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
        @Override
        public double apply(double... args) {
            final int arg = (int) args[0];
            if ((double) arg != args[0]) {
                throw new IllegalArgumentException("Operand for factorial has to be an integer");
            }
            if (arg < 0) {
                throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
            }
            double result = 1;
            for (int i = 1; i <= arg; i++) {
                result *= i;
            }
            return result;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color10TransparentBlack)));

        final View activityRootView = findViewById(R.id.layout);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);
                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    if (displayCalculation != null) {
                        displayCalculation.setCursorVisible(true);
                    }
                } else {
                    if (displayCalculation != null) {
                        displayCalculation.setCursorVisible(false);
                    }
                }
            }
        });

        displayCalculation = findViewById(R.id.displayCalculation);
        displayResult = findViewById(R.id.displayResult);
        initNumberButtons();
        initActionButtons();

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // code for portrait mode
        } else {
            // code for landscape mode
            initScienceButtons();
        }
        try{
            ConstraintLayout cL = findViewById(R.id.layout);
            AnimationDrawable aD = (AnimationDrawable) cL.getBackground();
            aD.setEnterFadeDuration(2000);
            aD.setExitFadeDuration(4000);
            aD.start();
        }catch(Exception e){
            Toast.makeText(this, "Animation Stopped", Toast.LENGTH_SHORT).show();
        }

    }

    public void initNumberButtons(){
        Button button0 = findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("0");
                updateResult();
            }
        });
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("1");
                updateResult();
            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("2");
                updateResult();
            }
        });
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("3");
                updateResult();
            }
        });
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("4");
                updateResult();
            }
        });
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("5");
                updateResult();
            }
        });
        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("6");
                updateResult();
            }
        });
        Button button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("7");
                updateResult();
            }
        });
        Button button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("8");
                updateResult();
            }
        });
        Button button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("9");
                updateResult();
            }
        });
    }

    public void initActionButtons() {
        Button equals = findViewById(R.id.buttonEquals);
        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcResult();
            }
        });
        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displaytext = displayCalculation.getText().toString();
                if(!displaytext.isEmpty()){
                    displayCalculation.setText(displaytext.substring(0,displaytext.length()-1));
                    String str = displayCalculation.getText().toString();
                    if(str.matches("")){
                        displayResult.setText("");
                        isSaveToCalculate = true;
                        return;
                    }
                    int countOpenB = str.length() - str.replace("(", "").length();
                    int countCloseB = str.length() - str.replace(")", "").length();
                    if(countOpenB == countCloseB){
                        isSaveToCalculate = true;
                    }
                    updateResult();
                }
                else{
                    Toast.makeText(MainActivity.this, "There is nothing to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                displayCalculation.setText("");
                displayResult.setText("");
                isSaveToCalculate = true;
                return true;
            }
        });
        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("+");
            }
        });
        Button buttonSubtract = findViewById(R.id.buttonSubtract);
        buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("-");
            }
        });
        Button buttonDivide = findViewById(R.id.buttonDivide);
        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("/");
            }
        });
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("*");
            }
        });
        Button buttonOpenBracket = findViewById(R.id.buttonOpenBracket);
        buttonOpenBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("(");
                isSaveToCalculate = false;
            }
        });
        Button buttonCloseBracket = findViewById(R.id.buttonCloseBracket);
        buttonCloseBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append(")");
                isSaveToCalculate = true;
                updateResult();
            }
        });
        Button buttonDot = findViewById(R.id.buttonDot);
        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append(".");
            }
        });
    }

    public void initScienceButtons(){
        Button buttonSquare = findViewById(R.id.buttonSquare);
        buttonSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("^2");
                isSaveToCalculate = false;
            }
        });
        Button buttonRoot = findViewById(R.id.buttonRoot);
        buttonRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("sqrt(");
                isSaveToCalculate = false;
            }
        });
        Button buttonSIN = findViewById(R.id.buttonSIN);
        buttonSIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("sin(");
                isSaveToCalculate = false;
            }
        });
        Button buttonCOS = findViewById(R.id.buttonCOS);
        buttonCOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("cos(");
                isSaveToCalculate = false;
            }
        });
        Button buttonTAN = findViewById(R.id.buttonTAN);
        buttonTAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("tan(");
                isSaveToCalculate = false;
            }
        });
        Button buttonPI = findViewById(R.id.buttonPI);
        buttonPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("pi");
                updateResult();
            }
        });
        Button buttonLOG = findViewById(R.id.buttonLOG);
        buttonLOG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("log(");
                isSaveToCalculate = false;
            }
        });
        Button buttonE = findViewById(R.id.buttonE);
        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("e");
                updateResult();
            }
        });
        Button buttonFaculty = findViewById(R.id.buttonFaculty);
        buttonFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("!");
                updateResult();
            }
        });
        Button buttonModulo = findViewById(R.id.buttonModulo);
        buttonModulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCalculation.append("%");
                isSaveToCalculate = false;
            }
        });
    }

    public void calcResult(){
        try{
            //term entspricht dem im Display bisher eigegebenen Text; z.B „5+6“
            String term = displayCalculation.getText().toString();

            Expression e = new ExpressionBuilder(term)
                    .operator(factorial)
                    .variables("pi", "e")
                    .build()
                    .setVariable("pi", Math.PI)
                    .setVariable("e", Math.E);
            //Hier wird nun das Ergebnis berechnet
            double result = e.evaluate();
            if(result == (int)result){
                int resultInteger = (int) result;
                displayResult.setText("");
                displayCalculation.setText(resultInteger + "");
                specialTitleCheck(resultInteger);
                return;
            }
            this.setTitle(R.string.app_name);
            displayResult.setText("");
            displayCalculation.setText(result + "");
        }catch(Exception e){
            String err = e.toString();
            err = err.substring(err.indexOf(":")+1);
            Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
        }
    }

    public void updateResult(){
        if(!isSaveToCalculate){
            return;
        }
        try{
            //term entspricht dem im Display bisher eigegebenen Text; z.B „5+6“
            String term = displayCalculation.getText().toString();
            Expression e = new ExpressionBuilder(term)
                    .operator(factorial)
                    .variables("pi", "e")
                    .build()
                    .setVariable("pi", Math.PI)
                    .setVariable("e", Math.E);
            //Hier wird nun das Ergebnis berechnet
            double result = e.evaluate();
            if(result == (int)result){
                int resultInteger = (int) result;
                displayResult.setText(resultInteger + "");
                specialTitleCheck(resultInteger);
                return;
            }
            this.setTitle(R.string.app_name);
            displayResult.setText(result + "");
        }catch(Exception e){
            //String err = e.toString();
            //err = err.substring(err.indexOf(":")+1);
            //Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
        }
    }


    public void specialTitleCheck(int number){
        SharedPreferences mySharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplication());

        int value = mySharedPrefs.getInt("pin", 7777);


        if(number == 42){
            this.setTitle("Is that my purpose?");
        }else if(number == value){
            Intent HIDDEN = new Intent(this,UnrealMainActivity.class);
            startActivity(HIDDEN);
        }else if(number == 7){
            this.setTitle("Always lucky, aye.");
        }else if(number == 13){
            this.setTitle("Bad luck is just a number");
        }else if(number == 0){
            this.setTitle("Zer0");
        }else if(number > 900000){
            this.setTitle("The result? Obviously over 9000.");
        }else if(number > 9000){
            this.setTitle("The result? Over 9000.");
        }else if(number == 69){
            this.setTitle("lol");
        }else if(number == 1337){
            animateTitle("l33t 15 n34t?");
        }

        else{
            this.setTitle(R.string.app_name);
        }
    }

    private int ttn = 0;
    public void animateTitle(String title){
        try{
            final String tt = title;
            new CountDownTimer(tt.length()*100, 100) {

                public void onTick(long millisUntilFinished) {
                    try{
                        ttn++;
                        MainActivity.this.setTitle(tt.substring(0,ttn));
                    }catch(Exception e){}
                }

                public void onFinish() {
                    try{
                        ttn = 0;
                        MainActivity.this.setTitle(tt);
                    }catch(Exception e){}
                }
            }.start();
        }catch(Exception e){}
    }
}
