package nanodegree.premiere.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import nanodegree.premiere.AppController;
import nanodegree.premiere.Keys.Key;
import nanodegree.premiere.R;
import nanodegree.premiere.json_loaders.Result;
import nanodegree.premiere.json_loaders.TrailerPojo;
import nanodegree.premiere.json_loaders.favorite;

public class MovieLauncher extends AppCompatActivity {

    private static String m_name;
    private static String release_date;
    private static String poster_link;
    private static double vote_avg;
    private static String synopsis;
    private static int id;
    private static String URL_TO_TRAILER;
    private static String fn_trailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_launcher);
        CardView card = (CardView)findViewById(R.id.favCard);
        card.setCardBackgroundColor(Color.rgb(255,20,147));
        fn_trailer = "";m_name="";release_date="";poster_link="";
        vote_avg=0;synopsis="";id=0;URL_TO_TRAILER="";fn_trailer="";
        Bundle extras = getIntent().getExtras();
        m_name = extras.getString("MOVIE_TITLE");
        release_date =extras.getString("RELEASE_DATE");
        poster_link = extras.getString("POSTER");
        id = extras.getInt("ID");
        vote_avg = extras.getDouble("VOTE_AVG");
        synopsis = extras.getString("SYNOPSIS");
        fn_trailer = extras.getString("TRAILER");
        URL_TO_TRAILER = "http://api.themoviedb.org/3/movie/"+id+"/videos?api_key="+ Key.getKey();
        callVolleyForTrailer();

        ImageView posterImg = (ImageView)findViewById(R.id.MoviePoster);
        posterImg.setScaleType(ImageView.ScaleType.FIT_XY);

        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/original" + poster_link)
                .into(posterImg);

        TextView movie_name = (TextView)findViewById(R.id.titleTextMovie);
        movie_name.setText(m_name);

        TextView release = (TextView)findViewById(R.id.release_date_text);
        release.setText(release_date);

        String temp = Double.toString(vote_avg);
        TextView vote_text = (TextView)findViewById(R.id.vote_text);
        vote_text.setText(temp);

        TextView synopsis_text = (TextView)findViewById(R.id.synopsis_text);
        synopsis_text.setText(synopsis);

    }

    public void goBack(View view)
    {
        finish();
    }
    private void callVolleyForTrailer()
    {
        StringRequest TrailerRequest = new StringRequest(Request.Method.GET, URL_TO_TRAILER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            SharedPreferences Tempx = getSharedPreferences("NetworkCalls", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edx = Tempx.edit();
                            edx.putString("TRAILER:"+id, response.toString());
                            edx.commit();

                            Gson gson = new Gson();
                            TrailerPojo trailer = gson.fromJson(Tempx.getString("TRAILER:"+id, ""), TrailerPojo.class);
                            List<Result> result_of_videos = trailer.getResults();
                            fn_trailer = result_of_videos.get(0).getKey();
                        }
                        catch (Exception e){}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                try {
                    SharedPreferences Tempx = getSharedPreferences("NetworkCalls", Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    TrailerPojo trailer = gson.fromJson(Tempx.getString("TRAILER:"+id, ""), TrailerPojo.class);
                    List<Result> result_of_videos = trailer.getResults();
                    fn_trailer = result_of_videos.get(0).getKey();
                }
                catch (Exception e){}

            }
        });
        //TrailerRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(TrailerRequest);

    }
    public void launchYouTube(View view)
    {
        try
        {

            if (fn_trailer.equals(""))
                Snackbar.make(this.findViewById(android.R.id.content), "Trailer Unavailable", Snackbar.LENGTH_LONG).show();
            else
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + fn_trailer)));
        }
        catch(Exception e){}
    }

    public void markAsFavorite(View view)
    {
            Realm realm = Realm.getInstance(this);
        RealmQuery<favorite> query = realm.where(favorite.class) .equalTo("ID", id);
        if(query.count()==0)
        {
            realm.beginTransaction();
            favorite f1 = realm.createObject(favorite.class);
            f1.setMovieName(m_name);
            f1.setRelease_date(release_date);
            f1.setPoster_link(poster_link);
            f1.setID(id);
            f1.setVote_avg(vote_avg);
            f1.setSynopsis(synopsis);
            f1.setTrailer(fn_trailer);
            realm.commitTransaction();
            Snackbar.make(this.findViewById(android.R.id.content), "Successfully added :-)", Snackbar.LENGTH_LONG).show();
        }
        else
        {
            Snackbar.make(this.findViewById(android.R.id.content), "Already added :-)", Snackbar.LENGTH_LONG).show();
        }
    }

    public void reviewOpen(View view)
    {
        Intent intent = new Intent(MovieLauncher.this,MovieReviews.class);
        Bundle extras = new Bundle();
        extras.putInt("ID",id);
        intent.putExtras(extras);
        startActivity(intent);

    }
}
