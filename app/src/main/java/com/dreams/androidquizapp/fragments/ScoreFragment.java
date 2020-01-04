package com.dreams.androidquizapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreams.androidquizapp.OnFragmentInteractionListener;
import com.dreams.androidquizapp.R;

import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the {@link
 * OnFragmentInteractionListener} interface to handle interaction events.
 */
public class ScoreFragment extends Fragment implements View.OnClickListener
{

  // UI Variables
  private TextView totalQuestionsTV, totalCorrectTV, finalGradeTV, commentTV;
  private OnFragmentInteractionListener mListener;
  // Extras Variables
  private int numCorrect;
  private int quizSize;
  private int scorePer;

  public ScoreFragment()
  {
    // Required empty public constructor
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
      throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {

    super.onCreate(savedInstanceState);
    // Get Arguments from bundle
    Bundle bundle = this.getArguments();
    if (bundle != null)
    {
      scorePer = bundle.getInt("scorePercentage", 0);
      quizSize = bundle.getInt("quizSize", 0);
      numCorrect = bundle.getInt("numCorrect", 0);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment Based on the TYPE of Question.
    View mView = inflater.inflate(R.layout.fragment_score, container, false);

    // Assign/initiate variables
    totalQuestionsTV = mView.findViewById(R.id.numofquestions_tv);
    commentTV = mView.findViewById(R.id.comment_tv);
    totalCorrectTV = mView.findViewById(R.id.numcorrect_tv);
    finalGradeTV = mView.findViewById(R.id.finalgrade_tv);
    mView.findViewById(R.id.reset_btn).setOnClickListener(this);
    mView.findViewById(R.id.close_btn).setOnClickListener(this);

    //Set UI Objects Text values
    totalQuestionsTV.setText("There were " + quizSize + " Questions.");
    totalCorrectTV.setText("Correct Answers: " + numCorrect);
    finalGradeTV.setText(scorePer + "%");

    if (scorePer >= 80)
    {
      finalGradeTV.setTextColor(Color.GREEN);
      commentTV.setTextColor(Color.GREEN);
      commentTV.setText("OMEGA GOOD JOB!");
    } else if (scorePer > 50 && scorePer < 79)
    {
      finalGradeTV.setTextColor(Color.YELLOW);
      commentTV.setTextColor(Color.YELLOW);
      commentTV.setText("YOU'RE ALMOST THERE!");
    } else
    {
      finalGradeTV.setTextColor(Color.RED);
      commentTV.setTextColor(Color.RED);
      commentTV.setText("BETTER STUDY!");
    }
    return mView;
  }

  @Override
  public void onDetach()
  {

    super.onDetach();
    mListener = null;
  }

  @Override
  public void onClick(View v)
  {
    // Switch statement for what is clicked.
    Log.wtf("yeah", String.valueOf(v.getId()));
    switch (v.getId())
    {
      case R.id.reset_btn:
        // Reset to Quiz fragment!
        Fragment homeFragment = new QuizFragment();
        if (getFragmentManager() != null)
        {
          FragmentTransaction ft = getFragmentManager().beginTransaction();
          ft.replace(this.getId(), homeFragment);
          ft.commit();
        }
        break;

      case R.id.close_btn:
        // Closes the App
        Objects.requireNonNull(getActivity()).finishAffinity();
        break;
    }
  }
}
