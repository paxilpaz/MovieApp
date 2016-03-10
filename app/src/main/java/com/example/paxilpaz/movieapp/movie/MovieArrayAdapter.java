package com.example.paxilpaz.movieapp.movie;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.paxilpaz.movieapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by paxilpaz on 10/03/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private int resourceLayout;
    private Movie movies[];

    private static final String LOG_CAT = MovieArrayAdapter.class.getSimpleName();

    private static final String SCHEME = "http";
    private static final String AUTHORITY = "image.tmdb.org";
    private static final String T = "t";
    private static final String P = "p";
    private static final String DIM = "w185";

    public MovieArrayAdapter(Context context, int resourceLayout, Movie[] movies) {
        super(context,resourceLayout);
        this.context = context;
        this.movies = movies;
        this.resourceLayout = resourceLayout;
    }

    @Override
    public View getView(int position, View viewLayout, ViewGroup parent) {
        View row = viewLayout;
        MovieHolder holder;

        if (row ==null) {
            Log.d(LOG_CAT,"Row did not exist");
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resourceLayout, parent, false);
            holder = new MovieHolder();
            holder.img = (ImageView)row.findViewById(R.id.image_view_thumbnail);
            row.setTag(holder);
        } else {
            Log.d(LOG_CAT,"Recycling row");
            holder = (MovieHolder)row.getTag();
        }

        Movie movie = movies[position];
        Uri uri = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(T)
                .appendPath(P)
                .appendPath(DIM)
                .appendPath(movie.getBackdrop_path())
                .build();
        Log.d(LOG_CAT,"URI is \t" + uri.toString());
        Picasso.with(context).load(uri).into(holder.img);
        Log.d(LOG_CAT, "image created");
        return row;
    }

    public void setMovies(Movie[] movies) {
        this.movies = movies;
    }

    private static class MovieHolder {
        ImageView img;
    }
}