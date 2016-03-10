package com.example.paxilpaz.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.paxilpaz.movieapp.movie.Movie;
import com.example.paxilpaz.movieapp.movie.MovieArrayAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class SummaryFragment extends Fragment {

    private static final String LOG_CAT = SummaryFragment.class.getSimpleName();

    private MovieArrayAdapter movieArrayAdapter;


    public SummaryFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
        movieArrayAdapter = new MovieArrayAdapter(getActivity().getApplicationContext(),
                R.layout.my_image_view,
                new Movie[] { });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView)rootView.findViewById(R.id.gridView);
        gridView.setAdapter(movieArrayAdapter);
        return rootView;
    }
}
