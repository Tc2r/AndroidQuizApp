package com.dreams.androidquizapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tc2r on 5/19/2017.
 * <p>
 * Description:
 */

public class Answer implements com.dreams.androidquizapp.models.DomainObject, Parcelable
{

  public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>()
  {
    @Override
    public Answer createFromParcel(Parcel source)
    {

      return new Answer(source);
    }

    @Override
    public Answer[] newArray(int size)
    {

      return new Answer[size];
    }
  };
  private Integer id;
  private String answer;
  private String details;

  public Answer()
  {

  }

  private Answer(Parcel in)
  {

    this.answer = in.readString();
    this.details = in.readString();
  }

  @Override
  public Integer getId()
  {

    return id;
  }

  @Override
  public void setId(Integer id)
  {

    this.id = id;

  }

  public String getAnswer()
  {

    return answer;
  }

  public void setAnswer(String answer)
  {

    this.answer = answer;
  }

  public String getDetails()
  {

    return details;
  }

  public void setDetails(String details)
  {

    this.details = details;
  }

  @Override
  public int describeContents()
  {

    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags)
  {

    dest.writeString(this.answer);
    dest.writeString(this.details);
  }

}
