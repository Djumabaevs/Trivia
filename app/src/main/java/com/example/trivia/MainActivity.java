package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.example.trivia.data.AnswerListAsuncResponse;
import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.Repository;
import com.example.trivia.databinding.ActivityMainBinding;
import com.example.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);



        List<Question> questions = new Repository().getQuestions(questionArrayList ->
                Log.d("Main", "onCreate: " + questionArrayList));
        new Repository().getQuestions(listOfQuestions ->
                binding.questionTextView.setText(listOfQuestions.get(currentQuestionIndex).getAnswer()));



    }
}