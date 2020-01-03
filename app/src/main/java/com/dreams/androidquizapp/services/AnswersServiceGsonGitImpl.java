package com.dreams.androidquizapp.services;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dreams.androidquizapp.controllers.AnswersController;
import com.dreams.androidquizapp.models.Answer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AnswersServiceGsonGitImpl
{

  private RequestQueue mQueue;
  private AnswersController context;

  public AnswersServiceGsonGitImpl(AnswersController context)
  {

    this.context = context;
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

                                                                try
                                                                {
                                                                  Gson gson = new Gson();
                                                                  JSONArray jsonArray = response.getJSONArray(
                                                                          "answers");
                                                                  Type listType = new TypeToken<List<Answer>>() {}
                                                                                          .getType();
                                                                  List<Answer> answers = gson.fromJson(
                                                                          jsonArray.toString(),
                                                                          listType
                                                                                                      );
                                                                  Log.wtf("Answer:",
                                                                          1 + " is " + answers.get(
                                                                                  1).getDetails());

                                                                  context.passAnswers(
                                                                          (ArrayList) answers);
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
