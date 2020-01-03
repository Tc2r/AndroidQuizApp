package com.dreams.androidquizapp.services;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dreams.androidquizapp.controllers.AnswersController;
import com.dreams.androidquizapp.models.Answer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnswersServiceJsonGitImpl
{

  private RequestQueue mQueue;
  private AnswersController context;
  private ArrayList<Answer> answerArrayList;

  public AnswersServiceJsonGitImpl(AnswersController context)
  {

    this.context = context;
    answerArrayList = new ArrayList<>();
    mQueue = Volley.newRequestQueue(context.fragment.getContext());

  }

  public void getAnswers()
  {

    jsonParse();
  }

  private void jsonParse()
  {

    String url = "https://raw.githubusercontent.com/Tc2r1/json_resources/master/android_answers.json";

    final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                                            new Response.Listener<JSONObject>()
                                                            {
                                                              @Override
                                                              public void onResponse(JSONObject response)
                                                              {
                                                                // Parse Data from Json using Native code.
                                                                try
                                                                {
                                                                  JSONArray jsonArray = response.getJSONArray(
                                                                          "answers");

                                                                  // Create Objects from each jsonObject in the jsonArray.
                                                                  for (int i = 0; i < jsonArray
                                                                                              .length(); i++
                                                                  )
                                                                  {
                                                                    JSONObject answer = jsonArray
                                                                                                .getJSONObject(
                                                                                                        i);
                                                                    String answerString = answer.getString(
                                                                            "answer");
                                                                    String detailsString = answer.getString(
                                                                            "details");
                                                                    Answer newAnswer = new Answer();
                                                                    newAnswer.setAnswer(
                                                                            answerString);
                                                                    newAnswer.setDetails(
                                                                            detailsString);
                                                                    answerArrayList.add(newAnswer);
                                                                  }

                                                                  context.passAnswers(
                                                                          answerArrayList);
                                                                } catch (JSONException e)
                                                                {
                                                                  e.printStackTrace();
                                                                }
                                                              }
                                                            }, new Response.ErrorListener()
    {
      @Override
      public void onErrorResponse(VolleyError error)
      {

        error.printStackTrace();

      }
    }
    );
    mQueue.add(request);
  }
}
