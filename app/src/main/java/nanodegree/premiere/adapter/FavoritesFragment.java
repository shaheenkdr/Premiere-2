package nanodegree.premiere.adapter;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import nanodegree.premiere.R;
import nanodegree.premiere.json_loaders.favorite;


public class FavoritesFragment extends Fragment
{
    private RecyclerView rView;
    FavoritesAdapter adapter;
    private boolean isViewShown = false;

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible)
        {
            if(getView()!=null)
                adapter.notifyDataSetChanged();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.favorite_fragment, container, false);
        Realm realm = Realm.getInstance(getActivity());
        RealmResults<favorite> results1 =
                realm.where(favorite.class).findAll();

        List<favorite> data  = results1;
        rView =(RecyclerView)rootView.findViewById(R.id.favorite_layout);
        GridLayoutManager glm_fav = new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false);
        rView.setLayoutManager(glm_fav);
        adapter = new FavoritesAdapter(data);

        if(data!=null)
            adapter.notifyDataSetChanged();

        rView.setAdapter(adapter);
        return rootView;
    }


}
