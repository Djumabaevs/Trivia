package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.trivia.data.AnswerListAsuncResponse;
import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.Repository;
import com.example.trivia.databinding.ActivityMainBinding;
import com.example.trivia.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    List<Question> questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);



        List<Question> questionsFirst = new Repository().getQuestions(questionArrayList ->
                Log.d("Main", "onCreate: " + questionArrayList));


        questions = new Repository().getQuestions(listOfQuestions -> {

                binding.questionTextView.setText(listOfQuestions.get(currentQuestionIndex).getAnswer());

                updateCounter(listOfQuestions);
        });

        binding.buttonNext.setOnClickListener(view -> {
            currentQuestionIndex = (currentQuestionIndex  +1) % questions.size();
            updateQuestion();
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
        } else {
            snackMessageId  =R.string.incorrect_answer;
            shakeAnimation();
        }
        Snackbar.make(binding.cardView, snackMessageId, Snackbar.LENGTH_SHORT).show();
    }

    private void updateCounter(ArrayList<Question> arrayList) {
        binding.textViewOutOf.setText(String.format(getString(R.string.textQuestion),
                currentQuestionIndex, questions.size()));
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



    }

}