package nanodegree.premiere.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.List;

import nanodegree.premiere.AppController;
import nanodegree.premiere.Keys.Key;
import nanodegree.premiere.R;
import nanodegree.premiere.adapter.CustomAdapter;
import nanodegree.premiere.adapter.FavoritesAdapter;
import nanodegree.premiere.adapter.ReviewAdapter;
import nanodegree.premiere.json_loaders.Movie_Skeleton;
import nanodegree.premiere.json_loaders.Post;
import nanodegree.premiere.json_loaders.ReviewPojo;
import nanodegree.premiere.json_loaders.ReviewResult;

public class MovieReviews extends AppCompatActivity
{
    private int ID;
    private static String URL;
    private RecyclerView rView;
    ReviewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reviews);
        Bundle extras = getIntent().getExtras();
        ID = extras.getInt("ID");
        URL = "http://api.themoviedb.org/3/movie/"+ID+"/reviews?api_key="+Key.getKey();
        rView =(RecyclerView)findViewById(R.id.rvReviews);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rView.setLayoutManager(llm);
        callVolleyForReviews();

    }

    private void callVolleyForReviews()
    {
        StringRequest reviewRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            SharedPreferences Tempx = getSharedPreferences("NetworkCalls", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edx = Tempx.edit();
                            edx.putString("MOVIE_REVIEW:"+ID, response.toString());
                            edx.commit();

                            Gson gson = new Gson();
                            ReviewPojo review = gson.fromJson(Tempx.getString("MOVIE_REVIEW:"+ID, ""), ReviewPojo.class);
                            List<ReviewResult> listOfReview = review.getResults();
                            if(listOfReview.size()==0)
                            {
                                initiateEmpty();
                            }
                            adapter = new ReviewAdapter(listOfReview);
                            adapter.notifyDataSetChanged();
                            rView.setAdapter(adapter);
                        }
                        catch (Exception e){}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                try {
                    Gson gson = new Gson();
                    SharedPreferences Tempx = getSharedPreferences("NetworkCalls", Context.MODE_PRIVATE);
                    ReviewPojo review = gson.fromJson(Tempx.getString("MOVIE_REVIEW:"+ID, ""), ReviewPojo.class);
                    List<ReviewResult> listOfReview = review.getResults();
                    adapter = new ReviewAdapter(listOfReview);
                    adapter.notifyDataSetChanged();
                    rView.setAdapter(adapter);
                }
                catch (Exception e){}

            }
        });
        reviewRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(reviewRequest);
    }

    public void goBackReview(View view)
    {
        finish();
    }

    private  void initiateEmpty()
    {
        Snackbar.make(this.findViewById(android.R.id.content), "No review found :/", Snackbar.LENGTH_LONG).show();
        ImageView abc = (ImageView)findViewById(R.id.ghostPic);
        abc.setVisibility(View.VISIBLE);
    }
}

