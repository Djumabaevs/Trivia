package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.trivia.data.AnswerListAsuncResponse;
import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.Repository;
import com.example.trivia.databinding.ActivityMainBinding;
import com.example.trivia.model.Question;
import com.example.trivia.model.Score;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    List<Question> questions;

    private int scoreCounter = 0;
    private Score score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        score = new Score();

        binding.scoreText.setText(MessageFormat.format("Current score: {0}", String.valueOf(score.getScore())));
/*
        List<Question> questionsFirst = new Repository().getQuestions(questionArrayList ->
                Log.d("Main", "onCreate: " + questionArrayList));*/


        questions = new Repository().getQuestions(listOfQuestions -> {

                binding.questionTextView.setText(listOfQuestions.get(currentQuestionIndex).getAnswer());

                updateCounter(listOfQuestions);
        });

        binding.buttonNext.setOnClickListener(view -> {

            getNextQuestion();

        });

        binding.buttonTrue.setOnClickListener(view -> {

            checkAnswer(true);
            updateQuestion();

        });
        binding.buttonFalse.setOnClickListener(view -> {

            checkAnswer(false);
            updateQuestion();
        });
    }

    private void checkAnswer(boolean userChooseCorrect) {
        boolean answer = questions.get(currentQuestionIndex).getAnswerTrue();
        int snackMessageId = 0;
        if(userChooseCorrect == answer) {
            snackMessageId = R.string.correct_answer;
            fadeAnimation();
            addPoints();
        } else {
            deductPoints();
            snackMessageId  = R.string.incorrect_answer;
            shakeAnimation();

        }
        Snackbar.make(binding.cardView, snackMessageId, Snackbar.LENGTH_SHORT).show();
    }

    private void updateCounter(ArrayList<Question> arrayList) {
        binding.textViewOutOf.setText(String.format(getString(R.string.textQuestion),
                currentQuestionIndex,arrayList.size()));
    }

    private void updateQuestion() {
        String question = questions.get(currentQuestionIndex).getAnswer();
        binding.questionTextView.setText(question);
        updateCounter((ArrayList<Question>) questions);
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        binding.cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.RED);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
                getNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private void getNextQuestion() {
        currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
        updateQuestion();
    }

    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
                getNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private void addPoints() {
        scoreCounter += 100;
        score.setScore(scoreCounter);
        Log.d("Score", "Score points: " + score.getScore());
        binding.scoreText.setText(String.valueOf(score.getScore()));
        binding.scoreText.setText(MessageFormat.format("Current score: {0}", String.valueOf(score.getScore())));
    }

    private void deductPoints() {
        if(scoreCounter > 0) {
            scoreCounter -= 100;
            score.setScore(scoreCounter);
            Log.d("Deduct2", "Loose points: " + score.getScore());
            binding.scoreText.setText(MessageFormat.format("Current score: {0}", String.valueOf(score.getScore())));
        } else {
            scoreCounter = 0;
            score.setScore(scoreCounter);
            Log.d("Deduct", "Loose points: " + score.getScore());
        }
    }
}