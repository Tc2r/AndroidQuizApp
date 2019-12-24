package com.tc2r.androidquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tc2r.androidquizapp.controllers.AnswersController;
import com.tc2r.androidquizapp.controllers.QuestionsController;
import com.tc2r.androidquizapp.fragments.QuestionFragment;
import com.tc2r.androidquizapp.models.Answer;
import com.tc2r.androidquizapp.models.Question;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener
{

  // Static Variables
  private final static int QUIZ_SIZE = 4;

  // UI Variables
  private LinearLayout fragContainer;
  private TextView titleTv, scoreTv;

  // Fragments and Model Variables
  private QuestionFragment newFragment;
  private ArrayList<Question> quizList;
  private ArrayList<Question> testList;
  private ArrayList<Answer> answersList;
  private boolean selectedQuestion[];

  // Variables
  private Random random;
  private int currentQuestion = 0;
  private int numOfCorrect = 0;
  private int scorePer = 0;
  private double pointPerQ, score;

  private QuestionsController questionsController;
  private AnswersController answersController;


  @Override
  protected void onCreate(Bundle savedInstanceState)
  {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Initalize and assignments
    titleTv = findViewById(R.id.title_tv);
    scoreTv = findViewById(R.id.score_tv);
    testList = new ArrayList<>();
    quizList = new ArrayList<>();
    answersList = new ArrayList<>();
    random = new Random();

    // get Wrong answers from server
    getAnswers();
    getQuestions();
    createQuiz();

  }

  public void getAnswers()
  {

    answersController = new AnswersController();
    answersList = answersController.getAnswers();
  }

  public void getQuestions()
  {

    questionsController = new QuestionsController();
    quizList = questionsController.getQuestions();
  }

  private void createQuiz()
  {

    Log.wtf(" Size: ", "QuestionList is: " + quizList.size());
    Log.wtf(" Size: ", "AnswerList is: " + answersList.size());
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

  public void nextQuestion(boolean correctAnswer)
  {
    // if previous question was answered correctly
    // update variables accordingly.
    if (correctAnswer)
    {
      numOfCorrect++;
      score += pointPerQ;
      scorePer = (int) (score * 100);
      scoreTv.setText(String.format("%s%s", getString(R.string.score_display_text), String.valueOf(scorePer)));
    }
    // if quiz is not complete, continue quiz with new QuestionFragment
    if (currentQuestion < QUIZ_SIZE)
    {
      newFragment = QuestionFragment.newInstance(testList.get(currentQuestion), answersList);
      currentQuestion++;
      titleTv.setText(String.format("%s%s of %s", getString(R.string.question_display_text), Integer.toString(
              currentQuestion), Integer.toString(QUIZ_SIZE)));
      fragContainer = findViewById(R.id.fragment_container);
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(fragContainer.getId(), newFragment);
      ft.commit();
    } else
    {
      // Quiz is over, go to final page!
      // create intent
      Intent intent = new Intent(this, ScoreActivity.class);

      // add variables to send.
      intent.putExtra("scorePercentage", scorePer);
      intent.putExtra("quizSize", QUIZ_SIZE);
      intent.putExtra("numCorrect", numOfCorrect);

      // use intent.
      startActivity(intent);
    }
  }

  @Override
  public void fragmentInitialized()
  {

  }
}
