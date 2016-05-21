package nanodegree.premiere.adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import java.util.List;
import nanodegree.premiere.AppController;
import nanodegree.premiere.Keys.Key;
import nanodegree.premiere.json_loaders.Movie_Skeleton;
import nanodegree.premiere.json_loaders.Post;
import nanodegree.premiere.R;

public class PopularFragment extends Fragment {

    public static String URL1;
    private RecyclerView mRecyclerViewPop;
    private CustomAdapter mAdapterPop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.popular, container, false);

        URL1 = "http://api.themoviedb.org/3/movie/popular?api_key="+Key.getKey();


        mRecyclerViewPop = (RecyclerView) rootView.findViewById(R.id.rv);
        callVolleyForPopular();
        GridLayoutManager glm_pop = new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false);
        mRecyclerViewPop.setLayoutManager(glm_pop);
        return rootView;
    }
    private void callVolleyForPopular()
    {

        StringRequest popularMoviesRequest = new StringRequest(Request.Method.GET, URL1,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            SharedPreferences Tempx = getActivity().getSharedPreferences("NetworkCalls", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edx = Tempx.edit();
                            edx.putString("POPULAR_MOVIES", response.toString());
                            edx.commit();

                            Gson gson = new Gson();
                            Movie_Skeleton movie1 = gson.fromJson(Tempx.getString("POPULAR_MOVIES", ""), Movie_Skeleton.class);
                            List<Post> posts_popular = movie1.getPosts();
                            mAdapterPop = new CustomAdapter(posts_popular);
                            mAdapterPop.notifyDataSetChanged();
                            mRecyclerViewPop.setAdapter(mAdapterPop);
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
                    Movie_Skeleton movie1 = gson.fromJson(Tempx.getString("POPULAR_MOVIES", ""), Movie_Skeleton.class);
                    List<Post> posts_popular = movie1.getPosts();
                    mAdapterPop = new CustomAdapter(posts_popular);
                    mAdapterPop.notifyDataSetChanged();
                    mRecyclerViewPop.setAdapter(mAdapterPop);
                }
                catch (Exception e){}

            }
        });
        //popularMoviesRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(popularMoviesRequest);
    }
}
