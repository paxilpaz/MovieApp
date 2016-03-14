package com.example.paxilpaz.movieapp.movie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.paxilpaz.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by paxilpaz on 10/03/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private Context context;

    private static final String LOG_CAT = MovieArrayAdapter.class.getSimpleName();

    public MovieArrayAdapter(Context context,  List<Movie> movies) {
        super(context,0,movies);
        this.context = context;
    }

    @Override
    public View getView(int position, View viewLayout, ViewGroup parent) {
        Movie movieItem = getItem(position);

        if (viewLayout == null) {
            viewLayout = LayoutInflater.from(context).inflate(R.layout.my_image_view, parent, false);
            Log.d(LOG_CAT, "View not existent. Creating it...");
        } else {
            Log.d(LOG_CAT, "Recycling view...");
        }

        Picasso.with(context).load(movieItem.getBackdrop_path()).into((ImageView)viewLayout);

        return viewLayout;
    }
}