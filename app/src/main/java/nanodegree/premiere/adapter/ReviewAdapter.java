package nanodegree.premiere.adapter;

import android.content.Intent;
import android.os.Bundle;
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
import nanodegree.premiere.json_loaders.ReviewResult;
import nanodegree.premiere.json_loaders.favorite;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder>
{
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    DataHolder d1 = new DataHolder();


    public  class ReviewHolder extends RecyclerView.ViewHolder
    {
        //CardView cv_favorite;
        TextView author;
        TextView content;



        ReviewHolder(View itemView) {
            super(itemView);
            // cv_favorite = (CardView)itemView.findViewById(R.id.card_favorite);
            author = (TextView)itemView.findViewById(R.id.name_of_author);
            content = (TextView)itemView.findViewById(R.id.content);
        }





    }

    private class DataHolder
    {
        List<ReviewResult> revList;

    }
    public ReviewAdapter(List<ReviewResult> rev){
        this.d1.revList = rev;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_card, viewGroup, false);
        ReviewHolder pvh = new ReviewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ReviewHolder ViewHolder, int i)
    {

       ViewHolder.author.setText(d1.revList.get(i).getAuthor());

        ViewHolder.content.setText(d1.revList.get(i).getContent());

    }

    @Override
    public int getItemCount()
    {

        if(d1.revList!=null)
        {
            return d1.revList.size();
        }
        else
        {
            return 0;
        }
    }



}

