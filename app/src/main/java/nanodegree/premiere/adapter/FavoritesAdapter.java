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

import java.util.List;

import nanodegree.premiere.Activities.MovieLauncher;
import nanodegree.premiere.AppController;
import nanodegree.premiere.R;
import nanodegree.premiere.json_loaders.Post;
import nanodegree.premiere.json_loaders.favorite;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavViewHolder>
{
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    DataHolder d1 = new DataHolder();


    public  class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        //CardView cv_favorite;
        //TextView MovieName_fav;
        NetworkImageView MoviePhoto_fav;



        FavViewHolder(View itemView) {
            super(itemView);
           // cv_favorite = (CardView)itemView.findViewById(R.id.card_favorite);
           // MovieName_fav = (TextView)itemView.findViewById(R.id.movie_name_fav);
            MoviePhoto_fav = (NetworkImageView)itemView.findViewById(R.id.movie_photo_fav);
            MoviePhoto_fav.setScaleType(ImageView.ScaleType.FIT_START);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(itemView.getContext(), MovieLauncher.class);
            Bundle extras = new Bundle();
            extras.putString("MOVIE_TITLE",d1.favList.get(getLayoutPosition()).getMovieName());
            extras.putInt("ID", d1.favList.get(getLayoutPosition()).getID());
            extras.putString("RELEASE_DATE", d1.favList.get(getLayoutPosition()).getRelease_date());
            extras.putString("POSTER", d1.favList.get(getLayoutPosition()).getPoster_link());
            extras.putDouble("VOTE_AVG", d1.favList.get(getLayoutPosition()).getVote_avg());
            extras.putString("SYNOPSIS", d1.favList.get(getLayoutPosition()).getSynopsis());
            extras.putString("TRAILER",d1.favList.get(getLayoutPosition()).getTrailer());
            intent.putExtras(extras);
            itemView.getContext().startActivity(intent);
        }


    }

    private class DataHolder
    {
        List<favorite> favList;

    }
    FavoritesAdapter(List<favorite> fav){
        this.d1.favList = fav;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FavViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_card, viewGroup, false);
        FavViewHolder pvh = new FavViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FavViewHolder movieViewHolder, int i)
    {

       // movieViewHolder.MovieName_fav.setText(d1.favList.get(i).getMovieName());
        Log.w("TAG",""+d1.favList.get(i).getMovieName());

        if(d1.favList.get(i).getPoster_link()!=null)
        {
            movieViewHolder.MoviePhoto_fav.setImageUrl("http://image.tmdb.org/t/p/w500"+d1.favList.get(i).getPoster_link(),imageLoader);

        }



    }

    @Override
    public int getItemCount()
    {

        if(d1.favList!=null)
        {
            return d1.favList.size();
        }
        else
        {
            return 0;
        }
    }



}
