package com.example.paxilpaz.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.paxilpaz.movieapp.movie.Movie;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    private static final String LOG_CAT = MovieDetailFragment.class.getSimpleName();

    public MovieDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Movie movie = (Movie)getActivity().getIntent().getParcelableExtra("myMovie");
        ((TextView)root.findViewById(R.id.original_title)).setText(movie.getOriginal_title());
        ((TextView)root.findViewById(R.id.title)).setText(movie.getTitle());
        ((TextView)root.findViewById(R.id.original_language)).setText(movie.getOriginal_language());
        ((TextView)root.findViewById(R.id.overview)).setText(movie.getOverview());
        Picasso.with(getContext()).load(movie.getPoster_path()).into((ImageView)
                root.findViewById(R.id.image_view_detail));
        ((RatingBar)root.findViewById(R.id.rating_bar)).setRating((float) (movie.getVote_average() * 0.5));
        return root;
    }
}
