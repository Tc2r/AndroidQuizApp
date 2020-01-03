package com.dreams.androidquizapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dreams.androidquizapp.OnFragmentInteractionListener;
import com.dreams.androidquizapp.R;
import com.dreams.androidquizapp.models.Answer;
import com.dreams.androidquizapp.models.Question;

import java.util.ArrayList;
import java.util.Random;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment implements View.OnClickListener
{


  // Declare Constants
  private static final String QUESTION = "Question";
  private static final String KEY = "Key";
  private static final String DETAILS = "Details";
  private static final String FILLANSWERS = "Fill";
  private static final String QUESTIONTYPE = "TYPE";
  private static final String QUESTIONTRUEORFALSE = "false";
  private static final String QUESTIONID = "ID";

  // Declare UI Variables
  private TextView questionTV;
  private TextView detailsA, detailsB, detailsC, detailsD;
  private TextView answerA, answerB, answerC, answerD;

  // Declare Variables
  private boolean questionTrueOrFalse;
  private int questionId;
  private String quizQuestion, questionType;
  private String quizAnswer, quizDetails;
  private ArrayList<Answer> otherAnswers;
  private Button nextBtn, newBtn, trueBtn, falseBtn;
  private boolean[] answerCheck = new boolean[4];
  private OnFragmentInteractionListener mListener;
  private int keyPosition;
  private boolean answered = false;
  private boolean correct = false;


  public QuestionFragment()
  {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param question    Accepts a Question object.
   * @param fillAnswers Accepts an array of Incorrect Answer objects
   * @return A new instance of fragment QuestionFragment.
   */
  public static QuestionFragment newInstance(Question question, ArrayList<Answer> fillAnswers)
  {
    // Create new instance of fragment
    QuestionFragment fragment = new QuestionFragment();
    // Add arguments to bundle of new instance.
    Bundle args = new Bundle();
    args.putString(QUESTION, question.getQuestion());
    args.putString(KEY, question.getShortAns());
    args.putString(DETAILS, question.getDetails());
    args.putString(QUESTIONTYPE, question.getQuestionType());
    args.putString(QUESTIONTRUEORFALSE, question.getTrueOrFalse());
    args.putInt(QUESTIONID, question.getId());
    args.putParcelableArrayList(FILLANSWERS, fillAnswers);

    fragment.setArguments(args);
    return fragment;
  }


  @Override
  public void onAttach(Context context)
  {

    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener)
    {
      mListener = (OnFragmentInteractionListener) context;
    } else
    {
      throw new RuntimeException(
              context.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {

    super.onCreate(savedInstanceState);
    // Get Arguments from bundle
    if (getArguments() != null)
    {
      questionId = getArguments().getInt(QUESTIONID);
      questionType = getArguments().getString(QUESTIONTYPE);
      quizQuestion = getArguments().getString(QUESTION);
      quizAnswer = getArguments().getString(KEY);
      quizDetails = getArguments().getString(DETAILS);
      questionTrueOrFalse = Boolean.parseBoolean(getArguments().getString(QUESTIONTRUEORFALSE));
      otherAnswers = getArguments().getParcelableArrayList(FILLANSWERS);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment Based on the TYPE of Question.
    View mView = null;
    questionType = questionType.toLowerCase();
    switch (questionType)
    {
      case "multi":
        mView = multiLayout(inflater, container);
        break;
      case "fill":

        break;
      case "bool":
        mView = boolLayout(inflater, container);
        break;
    }


    return mView;
  }

  @Override
  public void onDetach()
  {

    super.onDetach();
    mListener = null;
  }

  private View boolLayout(LayoutInflater inflater, ViewGroup container)
  {

    View mView;
    mView = inflater.inflate(R.layout.fragment_bool_question, container, false);

    // Assign Variables to Object Ids
    questionTV = mView.findViewById(R.id.question_tv);
    nextBtn = mView.findViewById(R.id.nextBtn);
    newBtn = mView.findViewById(R.id.newQuizBtn);
    trueBtn = mView.findViewById(R.id.trueBtn);
    falseBtn = mView.findViewById(R.id.falseBtn);

    //Set Listeners
    nextBtn.setOnClickListener(this);
    newBtn.setOnClickListener(this);
    trueBtn.setOnClickListener(this);
    falseBtn.setOnClickListener(this);


    // Set the Question Text
    questionTV.setText(quizQuestion);
    return mView;
  }

  private View multiLayout(LayoutInflater inflater, ViewGroup container)
  {

    View mView;
    mView = inflater.inflate(R.layout.fragment_multi_question, container, false);

    // Assign Variables to Object Ids
    questionTV = mView.findViewById(R.id.question);
    answerA = mView.findViewById(R.id.answer_a_summary);
    detailsA = mView.findViewById(R.id.answer_a_details);
    answerB = mView.findViewById(R.id.answer_b_summary);
    detailsB = mView.findViewById(R.id.answer_b_details);
    answerC = mView.findViewById(R.id.answer_c_summary);
    detailsC = mView.findViewById(R.id.answer_c_details);
    answerD = mView.findViewById(R.id.answer_d_summary);
    detailsD = mView.findViewById(R.id.answer_d_details);
    nextBtn = mView.findViewById(R.id.nextBtn);
    newBtn = mView.findViewById(R.id.newQuizBtn);

    //Set Listeners
    nextBtn.setOnClickListener(this);
    newBtn.setOnClickListener(this);
    answerA.setOnClickListener(this);
    answerB.setOnClickListener(this);
    answerC.setOnClickListener(this);
    answerD.setOnClickListener(this);


    // Set the Question Text
    questionTV.setText(quizQuestion);

    // Create random and int for key positioning.
    Random random = new Random(System.currentTimeMillis());
    keyPosition = random.nextInt(4);

    // Position Key according to random int.
    switch (keyPosition)
    {
      case 0:
        answerA.setText(quizAnswer);
        detailsA.setText(quizDetails);
        answerCheck[0] = true;
        break;
      case 1:
        answerB.setText(quizAnswer);
        detailsB.setText(quizDetails);
        answerCheck[1] = true;
        break;
      case 2:
        answerC.setText(quizAnswer);
        detailsC.setText(quizDetails);
        answerCheck[2] = true;
        break;
      case 3:
        answerD.setText(quizAnswer);
        detailsD.setText(quizDetails);
        answerCheck[3] = true;
        break;
    }
    int randomAnswer;
    for (int j = 0; j < answerCheck.length; j++)
    {
      // if position in answer check =/= true, fill with random answer
      if (!answerCheck[j])
      {
        randomAnswer = random.nextInt(otherAnswers.size());
        switch (j)
        {
          case 0:
            answerA.setText((String.valueOf(otherAnswers.get(randomAnswer).getAnswer())));
            detailsA.setText((String.valueOf(otherAnswers.get(randomAnswer).getDetails())));
            answerCheck[0] = true;
            break;
          case 1:
            answerB.setText((String.valueOf(otherAnswers.get(randomAnswer).getAnswer())));
            detailsB.setText((String.valueOf(otherAnswers.get(randomAnswer).getDetails())));
            answerCheck[1] = true;
            break;
          case 2:
            answerC.setText((String.valueOf(otherAnswers.get(randomAnswer).getAnswer())));
            detailsC.setText((String.valueOf(otherAnswers.get(randomAnswer).getDetails())));
            answerCheck[2] = true;
            break;
          case 3:
            answerD.setText((String.valueOf(otherAnswers.get(randomAnswer).getAnswer())));
            detailsD.setText((String.valueOf(otherAnswers.get(randomAnswer).getDetails())));
            answerCheck[3] = true;
            break;
        }
      }
    }
    return mView;
  }

  @Override
  public void onClick(View view)
  {

    // Switch statement for what is clicked.
    switch (view.getId())
    {
      case R.id.trueBtn:
        // Check if question has not been answered
        if (!answered)
        {
          if (questionTrueOrFalse)
          {
            trueBtn.setBackgroundColor(Color.GREEN);

            // Register the correct answer
            correct = true;
          } else
          {
            trueBtn.setBackgroundColor(Color.RED);
          }

          answered = true;
        }
        break;
      case R.id.falseBtn:
        // Check if question has not been answered
        if (!answered)
        {
          if (!questionTrueOrFalse)
          {
            falseBtn.setBackgroundColor(Color.GREEN);

            // Register the correct answer
            correct = true;
          } else
          {
            falseBtn.setBackgroundColor(Color.RED);
          }

          answered = true;
        }

        break;
      case R.id.nextBtn:
        QuizFragment quizFragment = (QuizFragment)getParentFragment();
        if (quizFragment != null)
        {
          quizFragment.nextQuestion(correct);
        }
        break;
      case R.id.newQuizBtn:
        QuizFragment refreshFragment = (QuizFragment)getParentFragment();
        if (refreshFragment != null)
        {
          refreshFragment.restartFragment();
        }

        break;
      case R.id.answer_a_summary:
        if (detailsA.getVisibility() != View.VISIBLE)
        {
          detailsA.setVisibility(View.VISIBLE);

          // Check if question has not been answered
          if (!answered)
          {

            // Check if this selection is correct answer
            if (keyPosition == 0)
            {
              // Right answer is A
              detailsA.setTextColor(Color.GREEN);
              answerA.setTextColor(Color.GREEN);

              // Register the correct answer
              correct = true;
            } else
            {
              // Incorrect Answer Selected
              detailsA.setTextColor(Color.RED);
              answerA.setTextColor(Color.RED);
            }
            answered = true;
          }
        }
        break;
      case R.id.answer_b_summary:
        if (detailsB.getVisibility() != View.VISIBLE)
        {
          detailsB.setVisibility(View.VISIBLE);

          // Check if question has not been answered
          if (!answered)
          {

            // Check if this selection is correct answer
            if (keyPosition == 1)
            {
              // Right answer is A
              detailsB.setTextColor(Color.GREEN);
              answerB.setTextColor(Color.GREEN);

              // Register the correct answer
              correct = true;
            } else
            {
              // Incorrect Answer Selected
              detailsB.setTextColor(Color.RED);
              answerB.setTextColor(Color.RED);
            }
            answered = true;
          }
        }
        break;
      case R.id.answer_c_summary:
        if (detailsC.getVisibility() != View.VISIBLE)
        {
          detailsC.setVisibility(View.VISIBLE);

          // Check if question has not been answered
          if (!answered)
          {

            // Check if this selection is correct answer
            if (keyPosition == 2)
            {
              // Right answer is A
              detailsC.setTextColor(Color.GREEN);
              answerC.setTextColor(Color.GREEN);

              // Register the correct answer
              correct = true;
            } else
            {
              // Incorrect Answer Selected
              detailsC.setTextColor(Color.RED);
              answerC.setTextColor(Color.RED);
            }
            answered = true;
          }
        }
        break;
      case R.id.answer_d_summary:
        if (detailsD.getVisibility() != View.VISIBLE)
        {
          detailsD.setVisibility(View.VISIBLE);
          // Check if question has not been answered
          if (!answered)
          {
            // Check if this selection is correct answer
            if (keyPosition == 3)
            {
              // Right answer is A
              detailsD.setTextColor(Color.GREEN);
              answerD.setTextColor(Color.GREEN);
              // Register the correct answer
              correct = true;
            } else
            {
              // Incorrect Answer Selected
              detailsD.setTextColor(Color.RED);
              answerD.setTextColor(Color.RED);
            }
            answered = true;
          }
        }
        break;
    }
  }
}
