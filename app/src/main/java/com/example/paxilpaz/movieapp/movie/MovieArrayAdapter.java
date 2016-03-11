package com.example.paxilpaz.movieapp.movie;

import android.content.Context;
import android.net.Uri;
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

    private static final String SCHEME = "http";
    private static final String AUTHORITY = "image.tmdb.org";
    private static final String T = "t";
    private static final String P = "p";
    private static final String DIM = "w500";

    public MovieArrayAdapter(Context context, int resourceLayout, List<Movie> movies) {
        super(context,resourceLayout,movies);
        this.context = context;

    }

    @Override
    public View getView(int position, View viewLayout, ViewGroup parent) {
        MovieHolder holder = null;
        Movie movieItem = getItem(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewLayout ==null) {
            viewLayout = inflater.inflate(R.layout.my_image_view, null);
            holder = new MovieHolder();
            holder.img = (ImageView)viewLayout.findViewById(R.id.image_view_thumbnail);
            viewLayout.setTag(holder);
        } else {
            holder = (MovieHolder)viewLayout.getTag();
        }

        Uri uri = new Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(T)
                    .appendPath(P)
                    .appendPath(DIM)
                    .appendEncodedPath(movieItem.getBackdrop_path())
                    .build();
            Picasso.with(context).load(uri.toString()).into(holder.img);
        return viewLayout;
    }

    private class MovieHolder {
        ImageView img;
    }
}