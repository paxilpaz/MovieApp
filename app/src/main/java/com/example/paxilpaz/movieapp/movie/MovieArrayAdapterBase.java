package com.example.paxilpaz.movieapp.movie;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.paxilpaz.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by paxilpaz on 11/03/16.
 */
public class MovieArrayAdapterBase extends BaseAdapter {

    private Context context;
    private List<Movie> movies;

    private static final String LOG_CAT = MovieArrayAdapter.class.getSimpleName();

    private static final String SCHEME = "http";
    private static final String AUTHORITY = "image.tmdb.org";
    private static final String T = "t";
    private static final String P = "p";
    private static final String DIM = "w500";

    public MovieArrayAdapterBase(Context c, List<Movie> movies) {
        context = c;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movieItem = movies.get(position);

        Uri uri = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(T)
                .appendPath(P)
                .appendPath(DIM)
                .appendEncodedPath(movieItem.getBackdrop_path())
                .build();

        if (convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.my_image_view,
                   parent, false);
        }

        Picasso.with(context).load(uri.toString()).into((ImageView) convertView);

        return convertView;
    }

    public void setMovies(List<Movie> m) {
        movies.clear();
        movies.addAll(m);
        notifyDataSetChanged();
    }
}
