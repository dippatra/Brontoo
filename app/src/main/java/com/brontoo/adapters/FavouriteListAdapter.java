package com.brontoo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brontoo.R;
import com.brontoo.common.AppConstant;
import com.brontoo.common.CommonMethod;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.ViewHolder>{
    private static final String TAG="FavouriteListAdapter";
    private Context context;
    private ArrayList<JSONObject>favouriteList;

    public FavouriteListAdapter(Context context, ArrayList<JSONObject> favouriteList){
        this.context=context;
        this.favouriteList=favouriteList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favourite_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteListAdapter.ViewHolder holder, int position) {
        String imageUrl="";
        JSONObject movieObject;
        try{
            movieObject = favouriteList.get(position);
            imageUrl = AppConstant.IMAGE_BASE_URL + movieObject.getString("poster_path");
            Glide.with(context).asBitmap().load(imageUrl).into(holder.image);
            holder.title.setText(movieObject.getString("title"));
            CommonMethod.setFontMedium(holder.title);
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            title=(TextView)itemView.findViewById(R.id.title);
        }
    }
}
