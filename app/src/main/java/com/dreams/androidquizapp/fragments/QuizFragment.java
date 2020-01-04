package com.dreams.androidquizapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.dreams.androidquizapp.R;
import com.dreams.androidquizapp.controllers.AnswersController;
import com.dreams.androidquizapp.controllers.QuestionsController;
import com.dreams.androidquizapp.models.Answer;
import com.dreams.androidquizapp.models.Question;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Tc2r on 5/31/2017. Updated to Fragment on 1/1/2020.
 * <p>
 * Description: Displays the score after a quiz is taken.
 */

public class QuizFragment extends Fragment
{

  // Static Variables
  private static final int QUIZ_SIZE = 10;


  // UI Variables
  private LinearLayout fragContainer;
  private TextView titleTv, scoreTv;

  // Fragments and Model Variables
  private QuestionFragment newFragment;
  private FragmentTransaction ft;
  private ArrayList<Question> quizList;
  private ArrayList<Question> testList;
  private ArrayList<Answer> answersList;
  private boolean[] selectedQuestion;

  // Variables
  private Random random;
  private int currentQuestion = 0;
  private int numOfCorrect = 0;
  private int scorePer = 0;
  private double pointPerQ, score;

  private QuestionsController questionsController;
  private AnswersController answersController;

  private RequestQueue mQueue;


  //  private HomeViewModel homeViewModel;
  //
  //  public View onCreateView(@NonNull LayoutInflater inflater,
  //                           ViewGroup container, Bundle savedInstanceState) {
  //    homeViewModel =
  //            ViewModelProviders.of(this).get(HomeViewModel.class);
  //    View root = inflater.inflate(R.layout.fragment_quiz, container, false);
  //    final TextView textView = root.findViewById(R.id.text_home);
  //    homeViewModel.getText().observe(this, new Observer<String>() {
  //      @Override
  //      public void onChanged(@Nullable String s) {
  //        textView.setText(s);
  //      }
  //    });
  //    return root;
  //  }

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {

    View root = inflater.inflate(R.layout.fragment_quiz, container, false);
    // Initalize and assignments
    titleTv = root.findViewById(R.id.title_tv);
    scoreTv = root.findViewById(R.id.score_tv);
    fragContainer = root.findViewById(R.id.fragment_container);
    testList = new ArrayList<>();
    quizList = new ArrayList<>();
    answersList = new ArrayList<>();
    random = new Random();

    // Get List of questions to populate Quiz
    getQuestions();
    // get Wrong answers from server
    getAnswers();

    return root;
  }

  private void getAnswers()
  {

    answersController = new AnswersController();
    answersController.getAnswers(this);
  }

  private void getQuestions()
  {

    questionsController = new QuestionsController();
    questionsController.getQuestions(this);
  }

  public void updateQuizQuestions(ArrayList questions)
  {

    quizList = questions;

    if (quizList.size() > 0 && answersList.size() > 0)
    {
      createQuiz();
    }
  }


  public void updateQuizAnswers(ArrayList answers)
  {

    answersList = answers;

    if (quizList.size() > 0 && answersList.size() > 0)
    {
      createQuiz();
    }
  }

  private void createQuiz()
  {

    Log.i(" Size: ", "QuestionList is: " + quizList.size());
    Log.i(" Size: ", "AnswerList is: " + answersList.size());
    // set booleanArray to be same size as quizList

    selectedQuestion = new boolean[quizList.size()];

    // set an int to a random number in the quizList
    int randNum = random.nextInt(quizList.size());
    int i = 0;

    // sets the score system for quiz.
    // TODO: 5/31/2017 maybe remove this and simply divide correct answers by total questions
    pointPerQ = 1.0 / QUIZ_SIZE;

    while (i < QUIZ_SIZE)
    {
      //Log.wtf("testList Size: ", String.valueOf(testList.size()) + " Vs "+ String.valueOf(quizSize) );
      // if boolean at randNum in selectedQuestion is false
      if (!selectedQuestion[randNum])
      {
        // Add position randNum to test list;
        testList.add(quizList.get(randNum));
        // set this question selected to true.
        selectedQuestion[randNum] = true;
        i++;
      } else
      {
        // if question already selected, change randNum;
        randNum = random.nextInt(quizList.size());
      }
    }
    // On first run, start quiz without updating score
    nextQuestion(false);
  }

  void nextQuestion(boolean correctAnswer)
  {
    // if previous question was answered correctly
    // update variables accordingly.
    if (correctAnswer)
    {
      numOfCorrect++;
      score += pointPerQ;
      scorePer = (int) (score * 100);

    }

    scoreTv.setText(String.format("%s%s%s", getString(R.string.score_display_text), String.valueOf(scorePer),
        getString(R.string.score_percent_display_text)));
    // if quiz is not complete, continue quiz with new QuestionFragment
    if (currentQuestion < QUIZ_SIZE)
    {
      newFragment = QuestionFragment.newInstance(testList.get(currentQuestion), answersList);
      currentQuestion++;
      titleTv.setText(String.format("%s%s of %s", getString(R.string.question_display_text),
          Integer.toString(currentQuestion), Integer.toString(QUIZ_SIZE)));
      ft = getChildFragmentManager().beginTransaction();
      ft.replace(fragContainer.getId(), newFragment);
      ft.commit();
    } else
    {
      // Quiz is over, go to final page fragment!
      Fragment scoreFragment = new ScoreFragment();
      Bundle bundle = new Bundle();

      // add variables to send.
      bundle.putInt("scorePercentage", scorePer);
      bundle.putInt("quizSize", QUIZ_SIZE);
      bundle.putInt("numCorrect", numOfCorrect);
      scoreFragment.setArguments(bundle);
      if (getFragmentManager() != null)
      {
        ft = getFragmentManager().beginTransaction();
        ft.replace(this.getId(), scoreFragment);
        ft.commit();
      }
    }
  }

  void restartFragment()
  {

    if (this.getFragmentManager() != null)
    {
      this.getFragmentManager().beginTransaction().detach(this).attach(this).commit();
      currentQuestion = 0;
      numOfCorrect = 0;
      scorePer = 0;
      score = 0;
    }
  }
}