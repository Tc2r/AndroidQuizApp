package com.dreams.androidquizapp.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreams.androidquizapp.R;
import com.dreams.androidquizapp.fragments.NewsFragment.OnListFragmentInteractionListener;
import com.dreams.androidquizapp.fragments.dummy.DummyContent.DummyItem;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNewsRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsRecyclerViewAdapter.ViewHolder>
{

  private final List<DummyItem> mValues;
  private final OnListFragmentInteractionListener mListener;

  public MyNewsRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener
                                  )
  {

    mValues = items;
    mListener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {

    View view = LayoutInflater.from(parent.getContext())
                              .inflate(R.layout.fragment_news, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position)
  {

    DummyItem dummyItem = mValues.get(position);
    holder.mItem = mValues.get(position);
    holder.mUpdateTitleView.setText(dummyItem.title);
    holder.mUpdateDateView.setText(dummyItem.date);
    holder.mContentView.setText(dummyItem.content);
    holder.mIdView.setText(dummyItem.id);
    holder.mView.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {

        if (null != mListener)
        {
          // Notify the active callbacks interface (the activity, if the
          // fragment is attached to one) that an item has been selected.
          mListener.onListFragmentInteraction(holder.mItem);
        }
      }
    });
  }

  @Override
  public int getItemCount()
  {

    return mValues.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {

    public final View mView;
    public final TextView mIdView;
    public final TextView mContentView;
    public final TextView mUpdateDateView;
    public final TextView mUpdateTitleView;
    public DummyItem mItem;

    public ViewHolder(View view)
    {
      super(view);
      mView = view;
      mIdView = (TextView) view.findViewById(R.id.update_id_tv);
      mContentView = (TextView) view.findViewById(R.id.content);
      mUpdateDateView = (TextView) view.findViewById(R.id.update_date_tv);
      mUpdateTitleView = (TextView) view.findViewById(R.id.update_title_tv);
    }

    @Override
    public String toString()
    {

      return super.toString() + " '" + mContentView.getText() + "'";
    }
  }
}
