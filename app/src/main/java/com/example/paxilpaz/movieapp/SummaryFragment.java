package com.example.paxilpaz.movieapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

        Log.d(LOG_CAT, "Created Summary Fragment");
    }

    @Override
    public void onStart() {
        super.onStart();
        movieArrayAdapter = new MovieArrayAdapter(getActivity().getApplicationContext(),
                R.layout.my_image_view,
                new Movie[] { });
        updateMovies();
    }

    private void updateMovies() {
        Log.d(LOG_CAT,"Updating movies");
        FetchMovieTask fetchMovieTask = new FetchMovieTask();
        fetchMovieTask.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_CAT,"Creating view");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView)rootView.findViewById(R.id.gridView);
        gridView.setAdapter(movieArrayAdapter);
        Log.d(LOG_CAT,"View created");
        return rootView;
    }

    private class FetchMovieTask extends AsyncTask<Void, Void, Movie[]> {

        @Override
        protected Movie[] doInBackground(Void... params) {
            Log.d(LOG_CAT,"Fetching movies");
            return new Movie[0];
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            Log.d(LOG_CAT,"Movies fetched");
            if (movies != null) {
                movieArrayAdapter.setMovies(movies);
                Log.d(LOG_CAT,"Array changed (fakelly)");
            }
        }
    }
}
