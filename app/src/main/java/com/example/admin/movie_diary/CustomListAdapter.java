package com.example.admin.movie_diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Movie> implements View.OnClickListener{

    private ArrayList<Movie> dataSet;
    Context mContext;
    int mResource;

    @Override
    public void onClick(View v) {

    }

    public CustomListAdapter(Context context, int resource, ArrayList<Movie> data) {
        super(context, R.layout.adapter_list, data);
        this.dataSet = data;
        this.mContext=context;
        mResource = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie theMovie = dataSet.get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView titleText = (TextView) convertView.findViewById(R.id.movieName);
        TextView genreText = (TextView) convertView.findViewById(R.id.genre);
        TextView ratingText = (TextView) convertView.findViewById(R.id.rating);

        titleText.setText(theMovie.getTitle());
        genreText.setText(theMovie.getGenre());
        ratingText.setText(theMovie.getRating());

        /*Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;*/

        /*viewHolder.titleText.setText(dataModel.getName());
        viewHolder.genreText.setText(dataModel.getType());
        viewHolder.ratingText.setText(dataModel.getVersion_number());*/
        // Return the completed view to render on screen
        return convertView;
    }
}

