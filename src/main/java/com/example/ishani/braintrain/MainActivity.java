package com.example.ishani.braintrain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> answers=new ArrayList<>();
    int locationCorrectAnswer;
    TextView resultTV, timerTV, hsTV;
    TextView scoreTV;
    Button startButton;
    Button btn1, btn2, btn3, btn4, playAgainButton;
    TextView sumtextView;
    ConstraintLayout gameLayout;
    int questions=0;
    int score=0;
    SharedPreferences prefs;
    int value=0, x;

    public void playAgain(View view){
        score=0;
        questions=0;
        timerTV.setText("30s");
        scoreTV.setText("0/0");
        resultTV.setText(".....");

        playAgainButton.setVisibility(View.INVISIBLE);
        generateQuestion();

        new CountDownTimer(30000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTV.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                timerTV.setText("0s");
                resultTV.setText("Your score:"+Integer.toString(score)+"/"+Integer.toString(questions));
                if(prefs.getInt("key", -1)>score){
                    SharedPreferences.Editor editor=prefs.edit();
                    editor.putInt("key",score);
                    editor.commit();
                    hsTV.setText(Integer.toString(score));
                }
            }
        }.start();
    }
    public void generateQuestion(){
        Random rand=new Random();
        int a=rand.nextInt(21);
        int b=rand.nextInt(21);
        sumtextView.setText(Integer.toString(a)+" + "+Integer.toString(b));
        locationCorrectAnswer=rand.nextInt(4);

        answers.clear();
        int incorrectAnswer;
        for(int i=0;i<4;i++){
            if(i==locationCorrectAnswer){
                answers.add(a+b);
            }else{
                incorrectAnswer=rand.nextInt(41);
                while (incorrectAnswer==a+b){
                    incorrectAnswer=rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        btn1.setText(Integer.toString(answers.get(0)));
        btn2.setText(Integer.toString(answers.get(1)));
        btn3.setText(Integer.toString(answers.get(2)));
        btn4.setText(Integer.toString(answers.get(3)));
    }
    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(ConstraintLayout.VISIBLE);
        initialize();
        playAgain(findViewById(R.id.playAgainBtn));

    }

    public void chooseAnswer(View view){
        if(view.getTag().toString().equals(Integer.toString(locationCorrectAnswer))){
            score++;
            resultTV.setText("Correct!");
        }
        else {
            resultTV.setText("Wrong!");
        }
        questions++;
        scoreTV.setText(Integer.toString(score)+"/"+Integer.toString(questions));
        generateQuestion();
    }

    public void initialize(){
        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        if(!prefs.contains("key")){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("key", 0);
            editor.commit();
        }else{
            return;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton=findViewById(R.id.startButton);
        resultTV=findViewById(R.id.resultTextView);
        scoreTV=findViewById(R.id.scoreTextView);
        btn1=findViewById(R.id. button1);
        btn2=findViewById(R.id.button2);
        btn3=findViewById(R.id.button3);
        btn4=findViewById(R.id.button4);
        playAgainButton=findViewById(R.id.playAgainBtn);
        sumtextView=findViewById(R.id.sumTextView);
        timerTV=findViewById(R.id.timerTextView);
        gameLayout=findViewById(R.id.gameLayout);
        hsTV=findViewById(R.id.highScoreTextView);






    }
}
