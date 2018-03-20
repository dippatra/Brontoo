package com.brontoo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brontoo.R;
import com.brontoo.common.AppConstant;
import com.brontoo.common.CommonMethod;
import com.brontoo.models.Movie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MovieDetailScreen extends AppCompatActivity {
    private static final String TAG="MovieDetailScreen";
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_screen);
        try{
            getIntentData();
            initializeActivityControl();
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void getIntentData(){
        try{
            if(getIntent().hasExtra("movie")){
                movie=(Movie)getIntent().getParcelableExtra("movie");
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void initializeActivityControl(){
        TextView header,movieTitle,year,rating,language,summary;
        final ImageView back,posterImage,watchList,favourite;
        try{
            header=(TextView)findViewById(R.id.header_Text);
            header.setText(movie.getTitle());
            header.setSelected(true);
            CommonMethod.setFontBold(header);
            back=(ImageView)findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            posterImage=(ImageView)findViewById(R.id.posterImage);
            String imageUrl = AppConstant.IMAGE_BASE_URL + movie.getBackDropPath();
            Glide.with(this)
                    .asBitmap()
                    .apply(new RequestOptions().placeholder(R.drawable.place_holder))
                    .load(imageUrl)
                    .into(posterImage);
            movieTitle=(TextView)findViewById(R.id.title);
            movieTitle.setText(movie.getTitle());
            movieTitle.setSelected(true);
            CommonMethod.setFontMedium(movieTitle);
            watchList=(ImageView)findViewById(R.id.watchList);
            watchList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(CommonMethod.isMovieInWatchList(getBaseContext(),movie.getID())){
                        CommonMethod.removeFromWatchList(getBaseContext(),movie.getID());
                        watchList.setImageResource(R.drawable.ic_playlist_unmarked);
                        Toast.makeText(MovieDetailScreen.this, movie.getTitle()+" Removed from your watchList", Toast.LENGTH_SHORT).show();

                    }else {
                        CommonMethod.saveToWatchList(getBaseContext(),movie.getID());
                        watchList.setImageResource(R.drawable.ic_playlist_add);
                        Toast.makeText(MovieDetailScreen.this, movie.getTitle()+" Added to your watchList", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            favourite=(ImageView)findViewById(R.id.favourite);
            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        if(CommonMethod.isMovieInFavouriteList(getBaseContext(),movie.getID())){
                            CommonMethod.removeFromFavouriteList(getBaseContext(),movie.getID());
                            favourite.setImageResource(R.drawable.ic_favorite_unmarkerd);
                            Toast.makeText(MovieDetailScreen.this, movie.getTitle()+" Removed from your Favourite", Toast.LENGTH_SHORT).show();

                        }else {
                            CommonMethod.saveToFavouriteList(getBaseContext(),movie.getID());
                            favourite.setImageResource(R.drawable.ic_favorite);
                            Toast.makeText(MovieDetailScreen.this, movie.getTitle()+" Added to your Favourite", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception ex){
                        Log.e(TAG,ex.toString());
                    }
                }
            });
            year=(TextView)findViewById(R.id.year);
            year.setText(CommonMethod.getYear(movie.getReleaseDate()));
            CommonMethod.setFontMedium(year);
            rating=(TextView)findViewById(R.id.rating);
            rating.setText(String.valueOf(movie.getVoteAverage())+" / "+"10");
            CommonMethod.setFontMedium(rating);
            language=(TextView)findViewById(R.id.language);
            language.setText(CommonMethod.getFullFormLanguage(movie.getLanguage()));
            CommonMethod.setFontMedium(language);
            summary=(TextView)findViewById(R.id.summary);
            summary.setText(movie.getSummary());
            CommonMethod.setFontLight(summary);
            if(!CommonMethod.isMovieInFavouriteList(getBaseContext(),movie.getID())){
                favourite.setImageResource(R.drawable.ic_favorite_unmarkerd);
            }else {
                favourite.setImageResource(R.drawable.ic_favorite);
            }
            if(!CommonMethod.isMovieInWatchList(getBaseContext(),movie.getID())){
                watchList.setImageResource(R.drawable.ic_playlist_unmarked);
            }else {
                watchList.setImageResource(R.drawable.ic_playlist_add);
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }
}
