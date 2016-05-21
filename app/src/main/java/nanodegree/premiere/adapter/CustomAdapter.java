package nanodegree.premiere.adapter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.greenrobot.event.EventBus;
import nanodegree.premiere.Activities.MovieLauncher;
import nanodegree.premiere.Activities.Splash;
import nanodegree.premiere.AppController;
import nanodegree.premiere.json_loaders.Post;
import nanodegree.premiere.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MovieViewHolder>
{
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    DataHolder d1 = new DataHolder();

    EventBus myEventBus = EventBus.getDefault();


    public  class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
         CardView cv;
        //TextView MovieName;
         NetworkImageView MoviePhoto;



        MovieViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            //MovieName = (TextView)itemView.findViewById(R.id.movie_name);
            MoviePhoto = (NetworkImageView)itemView.findViewById(R.id.movie_photo);
            MoviePhoto.setScaleType(ImageView.ScaleType.FIT_START);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view)
        {

            if(Splash.isTablet(itemView.getContext()))
            {
                Log.w("TAG","IS TAB WORKING");
                DataBus bus = new DataBus();

                bus.setMovieName(d1.mpost.get(getLayoutPosition()).getOriginal_title());
                bus.setRelease_date(d1.mpost.get(getLayoutPosition()).getRelease_date());
                bus.setPoster_link(d1.mpost.get(getLayoutPosition()).getPoster_path());
                bus.setID(d1.mpost.get(getLayoutPosition()).getId());
                bus.setVote_avg(d1.mpost.get(getLayoutPosition()).getVote_average());
                bus.setSynopsis(d1.mpost.get(getLayoutPosition()).getOverview());

                EventBus.getDefault().post(bus);

            }
            else
            {
                Intent intent = new Intent(itemView.getContext(), MovieLauncher.class);
                Bundle extras = new Bundle();
                extras.putString("MOVIE_TITLE", d1.mpost.get(getLayoutPosition()).getOriginal_title());
                extras.putInt("ID", d1.mpost.get(getLayoutPosition()).getId());
                extras.putString("RELEASE_DATE", d1.mpost.get(getLayoutPosition()).getRelease_date());
                extras.putString("POSTER", d1.mpost.get(getLayoutPosition()).getPoster_path());
                extras.putDouble("VOTE_AVG", d1.mpost.get(getLayoutPosition()).getVote_average());
                extras.putString("SYNOPSIS", d1.mpost.get(getLayoutPosition()).getOverview());
                intent.putExtras(extras);
                itemView.getContext().startActivity(intent);
            }

        }


    }

private class DataHolder
{
     List<Post> mpost;

}



    CustomAdapter(List<Post> mpost){
        this.d1.mpost = mpost;
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }








    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        MovieViewHolder pvh = new MovieViewHolder(v);
        return pvh;
    }



    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int i)
    {

        //movieViewHolder.MovieName.setText(d1.mpost.get(i).getTitle());

        if(d1.mpost.get(i).getPoster_path()!=null)
        {
            movieViewHolder.MoviePhoto.setImageUrl("http://image.tmdb.org/t/p/w500"+d1.mpost.get(i).getPoster_path(),imageLoader);

        }



    }

    @Override
    public int getItemCount()
    {

        if(d1.mpost!=null)
        {
            return d1.mpost.size();
        }
        else
        {
            return 0;
        }
    }

}
