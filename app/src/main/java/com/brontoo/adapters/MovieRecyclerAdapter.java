package com.brontoo.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.brontoo.models.Movie;
import com.bumptech.glide.Glide;


import java.util.ArrayList;


public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>{
    private static final String TAG="RecyclerAdapter";
    private ArrayList<Movie>movieList;
    private Context context;


    public MovieRecyclerAdapter(Context context,ArrayList<Movie>movieList){
        this.context=context;
        this.movieList=movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String imageUrl="";
        Movie movie;
        try{
            movie = movieList.get(position);
            imageUrl = AppConstant.IMAGE_BASE_URL + movie.getPosterImagePath();
            holder.movieImage.setImageDrawable (null);
            /*Glide
                    .with(context)
                    .load(imageUrl)
                    .into(new BaseTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            holder.movieImage.setBackground(resource);
                        }

                        @Override
                        public void getSize(SizeReadyCallback cb) {
                            cb.onSizeReady(250, 250);

                        }

                        @Override
                        public void removeCallback(SizeReadyCallback cb) {

                        }
                    });*/
            Glide.with(context).asBitmap().load(imageUrl).into(holder.movieImage);
            holder.headerText.setText(movie.getTitle());
            holder.detailText.setText(CommonMethod.getFullFormLanguage(movie.getLanguage()) + ", " +
                    CommonMethod.getYear(movie.getReleaseDate()));

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImage,more;
        TextView headerText,detailText;

        public ViewHolder(View itemView) {
            super(itemView);
            movieImage=(ImageView)itemView.findViewById(R.id.movieImage);
            more=(ImageView)itemView.findViewById(R.id.more);
            headerText=(TextView)itemView.findViewById(R.id.movie_header);
            headerText.setSelected(true);
            detailText=(TextView)itemView.findViewById(R.id.movie_detail);
            detailText.setSelected(true);
        }
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);


    }
}
