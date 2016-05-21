package nanodegree.premiere.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmQuery;
import nanodegree.premiere.Activities.MovieLauncher;
import nanodegree.premiere.Activities.MovieReviews;
import nanodegree.premiere.AppController;
import nanodegree.premiere.Keys.Key;
import nanodegree.premiere.R;
import nanodegree.premiere.json_loaders.Result;
import nanodegree.premiere.json_loaders.TrailerPojo;
import nanodegree.premiere.json_loaders.favorite;



public class DetailFragment extends Fragment
{
    private static String m_name;
    private static String release_date;
    private static String poster_link;
    private static double vote_avg;
    private static String synopsis;
    private static int id;
    private static String URL_TO_TRAILER;
    private static String fn_trailer;

    private static ImageView posterImg;
    private static TextView movie_name;
    private static TextView release;
    private static TextView vote_text;
    private static TextView synopsis_text;
    private static ImageView trailerOpener;
    private static Button reviewButton;
    private static ImageView curtains;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        EventBus.getDefault().register(this);

        View rootView = inflater.inflate(R.layout.list_fragment_details, container, false);



        curtains = (ImageView)rootView.findViewById(R.id.curtain);

        CardView card = (CardView)rootView.findViewById(R.id.favCard_tab);


        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Realm realm = Realm.getInstance(getContext());
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
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Successfully added :-)", Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Already added :-)", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        card.setCardBackgroundColor(Color.rgb(255,20,147));
        fn_trailer = "";m_name="";release_date="";poster_link="";
        vote_avg=0;synopsis="";id=0;URL_TO_TRAILER="";fn_trailer="";



        trailerOpener = (ImageView)rootView.findViewById(R.id.trailer_button_tab);
        trailerOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {

                    if (fn_trailer.equals(""))
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Trailer Unavailable", Snackbar.LENGTH_LONG).show();
                    else
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + fn_trailer)));
                }
                catch(Exception e){}

            }
        });

        reviewButton = (Button)rootView.findViewById(R.id.reviews_tab);
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),MovieReviews.class);
                Bundle extras = new Bundle();
                extras.putInt("ID",id);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });
        posterImg = (ImageView)rootView.findViewById(R.id.MoviePoster_tab);
        movie_name = (TextView)rootView.findViewById(R.id.titleTextMovie_tab);
        release = (TextView)rootView.findViewById(R.id.release_date_text_tab);
        vote_text = (TextView)rootView.findViewById(R.id.vote_text_tab);
        synopsis_text = (TextView)rootView.findViewById(R.id.synopsis_text_tab);

        makeItInvisible();

        return rootView;
    }

    public void goBack(View view)
    {
        makeItInvisible();
    }
    private void callVolleyForTrailer()
    {
        StringRequest TrailerRequest = new StringRequest(Request.Method.GET, URL_TO_TRAILER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            Log.w("RESPONSE",""+response);
                            SharedPreferences Tempx = getActivity().getSharedPreferences("NetworkCalls", Context.MODE_PRIVATE);
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
                    Log.w("VOLLEY Error report",""+error);
                    SharedPreferences Tempx = getActivity().getSharedPreferences("NetworkCalls", Context.MODE_PRIVATE);
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

    public void onEventMainThread(DataBus data)
    {
        makeItVisible();
        m_name = data.getMovieName();
        release_date = data.getRelease_date();
        poster_link = data.getPoster_link();
        vote_avg = data.getVote_avg();
        synopsis = data.getSynopsis();
        id = data.getID();

        URL_TO_TRAILER = "http://api.themoviedb.org/3/movie/"+id+"/videos?api_key="+ Key.getKey();
        callVolleyForTrailer();

        posterImg.setScaleType(ImageView.ScaleType.FIT_XY);

        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/original" + poster_link)
                .into(posterImg);


        movie_name.setText(m_name);


        release.setText(release_date);

        String temp = Double.toString(vote_avg);

        vote_text.setText(temp);


        synopsis_text.setText(synopsis);


    }

    private static void makeItInvisible()
    {
        curtains.setVisibility(View.VISIBLE);

    }

    private static void makeItVisible()
    {
        curtains.setVisibility(View.INVISIBLE);
    }





}
