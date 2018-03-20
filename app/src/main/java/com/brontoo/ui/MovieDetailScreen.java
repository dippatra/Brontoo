package com.brontoo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.brontoo.R;
import com.brontoo.models.Movie;

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
        try{

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
}
