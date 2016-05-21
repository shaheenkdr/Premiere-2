package nanodegree.premiere.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import nanodegree.premiere.AppController;
import nanodegree.premiere.Keys.Key;
import nanodegree.premiere.json_loaders.Movie_Skeleton;
import nanodegree.premiere.json_loaders.Post;
import nanodegree.premiere.R;


public class TopRatedFragment extends Fragment
{
    public static String URL2;
    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.top_rated, container, false);
        URL2 = "http://api.themoviedb.org/3/movie/top_rated?api_key="+Key.getKey();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvTop);
        callVolleyForRated();
        GridLayoutManager glm_rated = new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(glm_rated);



        return rootView;
    }

    private void callVolleyForRated()
    {

        StringRequest TopMoviesRequest = new StringRequest(Request.Method.GET, URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            SharedPreferences Tempx = getActivity().getSharedPreferences("NetworkCalls", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edx = Tempx.edit();
                            edx.putString("TOP_MOVIES", response.toString());
                            edx.commit();

                            Gson gson = new Gson();
                            Movie_Skeleton movie1 = gson.fromJson(Tempx.getString("TOP_MOVIES", ""), Movie_Skeleton.class);
                            List<Post> posts_popular = movie1.getPosts();
                            mAdapter = new CustomAdapter(posts_popular);
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.setAdapter(mAdapter);
                        }
                        catch (Exception e){}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                try {
                    SharedPreferences Tempx = getActivity().getSharedPreferences("NetworkCalls", Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    Movie_Skeleton movie1 = gson.fromJson(Tempx.getString("TOP_MOVIES", ""), Movie_Skeleton.class);
                    List<Post> posts_popular = movie1.getPosts();
                    mAdapter = new CustomAdapter(posts_popular);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);
                }
                catch (Exception e){}

            }
        });
        //TopMoviesRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(TopMoviesRequest);
    }

}
