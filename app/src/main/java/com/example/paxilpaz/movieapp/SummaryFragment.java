package com.example.paxilpaz.movieapp;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.paxilpaz.movieapp.movie.Movie;
import com.example.paxilpaz.movieapp.movie.MovieArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class SummaryFragment extends Fragment {

    private static final String LOG_CAT = SummaryFragment.class.getSimpleName();
    private GridView gridView;
    private String dimension;

    public SummaryFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
        FetchMovieTask fetchMovieTask = new FetchMovieTask();
        fetchMovieTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView)rootView.findViewById(R.id.gridView);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        dimension = sharedPreferences.getString(getString(R.string.preference_preview_width),
                getString(R.string.preference_preview_width_default));
        gridView.setColumnWidth(convertDimension());
        return rootView;
    }

    private int convertDimension() {
        return Integer.parseInt(dimension.substring(1));
    }

    private class FetchMovieTask extends AsyncTask<Void, Void, Movie[]> {

        private static final String SCHEME = "http";
        private static final String AUTHORITY = "api.themoviedb.org";
        private static final String AUTHORITY_IMAGE = "image.tmdb.org";
        private static final String VERSION = "3";
        private static final String MOVIE = "movie";
        private static final String API_KEY = "api_key";
        private static final String T = "t";
        private static final String P = "p";

        @Override
        protected Movie[] doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String resultFromFetch = null;

            try {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                        getActivity().getApplicationContext());
                String filter = sharedPreferences.getString(getString(R.string.preference_sorting_key),
                        getString(R.string.preference_sorting_default));

                Uri uri = new Uri.Builder().scheme(SCHEME)
                        .authority(AUTHORITY)
                        .appendPath(VERSION)
                        .appendPath(MOVIE)
                        .appendPath(filter)
                        .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();

                String formattedURI = uri.toString();

                URL url = new URL(formattedURI);

                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                StringBuffer sb = new StringBuffer();
                if (is == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line + "\n");

                if (sb.length() == 0) {
                    return null;
                }
                resultFromFetch = sb.toString();
            } catch(IOException e) {
                Log.e(LOG_CAT, "IO exception", e);
                return null;
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_CAT, "reader close exception", e);
                    }
                }
            }

            try {
                return parseResponse(resultFromFetch);
            } catch (JSONException e) {
                Log.e(LOG_CAT, "JSON not well formatted", e);
            }
            return null;
        }

        private Movie[] parseResponse(String resultFromFetch) throws JSONException {
            final String RESULTS = "results";
            final String POSTER_PATH = "poster_path";
            final String OVERVIEW = "overview";
            final String ID = "id";
            final String ORIGINAL_TITLE = "original_title";
            final String ORIGINAL_LANGUAGE = "original_language";
            final String TITLE = "title";
            final String BACKDROP_PATH = "backdrop_path";
            final String VOTE_AVERAGE = "vote_average";
            final String VOTE_COUNT = "vote_count";

            JSONObject fetchResult = new JSONObject(resultFromFetch);
            JSONArray results = fetchResult.getJSONArray(RESULTS);

            Movie[] arrayOfMovies = new Movie[results.length()];

            for (int i = 0; i < arrayOfMovies.length; ++i) {
                JSONObject movieToParse = results.getJSONObject(i);


                String overview = movieToParse.getString(OVERVIEW);
                int id = movieToParse.getInt(ID);
                String originalTitle = movieToParse.getString(ORIGINAL_TITLE);
                String originalLanguage = movieToParse.getString(ORIGINAL_LANGUAGE);
                String title = movieToParse.getString(TITLE);

                double voteAverage = movieToParse.getDouble(VOTE_AVERAGE);
                int voteCount = movieToParse.getInt(VOTE_COUNT);

                Uri uriBackdrop = new Uri.Builder().scheme(SCHEME)
                        .authority(AUTHORITY_IMAGE)
                        .appendPath(T)
                        .appendPath(P)
                        .appendPath(dimension)
                        .appendEncodedPath(movieToParse.getString(BACKDROP_PATH))
                        .build();
                String backdropPath = uriBackdrop.toString();

                Uri uriPoster = new Uri.Builder().scheme(SCHEME)
                        .authority(AUTHORITY_IMAGE)
                        .appendPath(T)
                        .appendPath(P)
                        .appendPath(dimension)
                        .appendEncodedPath(movieToParse.getString(POSTER_PATH))
                        .build();
                String posterPath = uriPoster.toString();
                arrayOfMovies[i] = new Movie(title,
                                            originalTitle,
                                            originalLanguage,
                                            overview,
                                            posterPath,
                                            backdropPath,
                                            id,
                                            voteCount,
                                            voteAverage);
            }

            return arrayOfMovies;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies != null) {
                MovieArrayAdapter movieArrayAdapter = new MovieArrayAdapter(getActivity(),
                        Arrays.asList(movies));

                gridView.setAdapter(movieArrayAdapter);
            }
        }
    }
}
