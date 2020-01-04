package com.dreams.androidquizapp.services;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dreams.androidquizapp.controllers.QuestionsController;
import com.dreams.androidquizapp.models.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionsServiceGsonGitImpl
{

  private RequestQueue mQueue;
  private QuestionsController context;

  public QuestionsServiceGsonGitImpl(QuestionsController context)
  {

    this.context = context;
    mQueue = Volley.newRequestQueue(context.quizFragment.getContext());

  }

  public void getQuestions()
  {

    jsonParse();
  }

  private void jsonParse()
  {

    String url = "https://raw.githubusercontent.com/Tc2r1/json_resources/master/android_questions.json";

    final JsonObjectRequest request =
        new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {
          @Override
          public void onResponse(JSONObject response)
          {

            try
            {
              Gson gson = new Gson();
              JSONArray jsonArray = response.getJSONArray("questions");
              Type listType = new TypeToken<List<Question>>() {}.getType();
              List<Question> questions = gson.fromJson(jsonArray.toString(), listType);
              Log.wtf("Question:", 1 + " is " + jsonArray.length());

              context.passQuestions((ArrayList) questions);
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
        });
    mQueue.add(request);
  }
}
