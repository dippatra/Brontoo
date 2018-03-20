package com.brontoo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brontoo.R;
import com.brontoo.adapters.MovieRecyclerAdapter;
import com.brontoo.common.AppConstant;
import com.brontoo.common.CommonMethod;
import com.brontoo.interfaces.GridListener;
import com.brontoo.models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;



public class MovieListActivity extends Activity implements GridListener{
    private static final String TAG="MovieListActivity";
    private static final int DETAIL_CODE=101;
    private static final int WATCHLIST_CODE=102;
    private static final int FAVOURITE_CODE=103;
    private ArrayList<Movie>movieList=new ArrayList<>();
    private MovieRecyclerAdapter movieRecyclerAdapter;
    private Handler mHandler=new Handler();
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private boolean doubleBackToExitPressedOnce = false;
    private EditText searchEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        try{
            populateMovieList();
            initializeActivityControl();
            initializeRecyclerView();
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void initializeActivityControl(){
        ImageView menu,search,searchBack;
        TextView headerText;
        try{
            drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
            searchEditText=(EditText)findViewById(R.id.search_edit);
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try{
                        if(charSequence.length()==0){
                            updateRecycleView(movieList);
                        }else {
                            searchTask(charSequence.toString());
                        }

                    }catch (Exception ex){
                        Log.e(TAG,ex.toString());
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            menu=(ImageView)findViewById(R.id.menu);
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDrawerMenu();
                }
            });
            search=(ImageView)findViewById(R.id.search);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openSearchContainer();
                }
            });
            searchBack=(ImageView)findViewById(R.id.search_back);
            searchBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchEditText.setText("");
                    hideSearchContainer();
                    hideNoSearchResultView();
                    updateRecycleView(movieList);
                    CommonMethod.hideKeyboard(getBaseContext(),findViewById(R.id.search_edit));
                }
            });
            headerText=(TextView)findViewById(R.id.header_text);
            CommonMethod.setFontBold(headerText);

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void openSearchContainer(){
        LinearLayout searchContainer;
        try{
            searchContainer=(LinearLayout)findViewById(R.id.search_container);
            if(searchContainer.getVisibility()==View.GONE){
                searchContainer.setVisibility(View.VISIBLE);
                searchEditText.requestFocus();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CommonMethod.showKeyboard(getBaseContext());
                    }
                },500);
            }


        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void hideSearchContainer(){
        LinearLayout searchContainer;
        try{
            searchContainer=(LinearLayout)findViewById(R.id.search_container);
            if(searchContainer.getVisibility()==View.VISIBLE){
                searchEditText.setText("");
                CommonMethod.hideKeyboard(getBaseContext(),searchContainer);
                sortTask();
                searchContainer.setVisibility(View.GONE);

            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void populateMovieList(){
        String movieList="";
        JSONArray movies=null;
        JSONObject listObject=null;
        JSONObject object;
        Movie movieObject;
        JSONArray genreArray;
        ArrayList<Integer>genreIDs=new ArrayList<>();
        try{
            movieList=CommonMethod.getMovieList(getBaseContext());
            if(!movieList.equalsIgnoreCase("")){
                listObject=new JSONObject(movieList);
                if(listObject!=null){
                    movies=listObject.getJSONArray("movies");
                    if(movies!=null&&movies.length()>0){
                        for (int count=0;count<movies.length();count++){
                            object=movies.getJSONObject(count);
                            movieObject=new Movie();
                            movieObject.setVoteCount(object.getInt("vote_count"));
                            movieObject.setID(object.getInt("id"));
                            movieObject.setVideo(object.getBoolean("video"));
                            movieObject.setVoteAverage(BigDecimal.valueOf(object.getDouble("vote_average")).floatValue());
                            movieObject.setTitle(object.getString("title"));
                            movieObject.setPopularity(BigDecimal.valueOf(object.getDouble("popularity")).floatValue());
                            movieObject.setPosterImagePath(object.getString("poster_path"));
                            movieObject.setLanguage(object.getString("original_language"));
                            movieObject.setBackDropPath(object.getString("backdrop_path"));
                            movieObject.setForAdult(object.getBoolean("adult"));
                            movieObject.setSummary(object.getString("overview"));
                            movieObject.setReleaseDate(object.getString("release_date"));
                            this.movieList.add(movieObject);

                        }
                    }
                }

            }else {

            }

        }catch (Exception ex){
            Log.d(TAG,ex.toString());
        }
    }
    private void initializeRecyclerView(){
        try{
            hideNoSearchResultView();
            recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
            movieRecyclerAdapter=new MovieRecyclerAdapter(this,movieList,this);
            GridLayoutManager manager = new GridLayoutManager(this, CommonMethod.calculateNoOfColumns(getBaseContext()), GridLayoutManager.VERTICAL, false);
            recyclerView.setAdapter(movieRecyclerAdapter);
            recyclerView.setLayoutManager(manager);
            AppConstant.sortedMechanism=2;
            sortTask();
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void hideRecyclerView(){
        try {
            if(recyclerView!=null&&recyclerView.getVisibility()==View.VISIBLE){
                recyclerView.setVisibility(View.GONE);
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void showRecyclerView(){
        try {
            if(recyclerView!=null&&recyclerView.getVisibility()==View.GONE){
                recyclerView.setVisibility(View.VISIBLE);
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void showNoSearchResultView(){
        try{
            RelativeLayout relative=(RelativeLayout)findViewById(R.id.no_search_container);
            if(relative.getVisibility()==View.GONE){
                relative.setVisibility(View.VISIBLE);
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void hideNoSearchResultView(){
        try{
            RelativeLayout relative=(RelativeLayout)findViewById(R.id.no_search_container);
            if(relative.getVisibility()==View.VISIBLE){
                relative.setVisibility(View.GONE);
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void updateRecycleView(ArrayList<Movie>movieList){
        try{
            if(recyclerView!=null && movieList!=null&&movieList.size()>0){
                recyclerView.setAdapter(new MovieRecyclerAdapter(this,movieList,this));
                recyclerView.invalidate();
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private AsyncTask<Void,ArrayList<Movie>,ArrayList<Movie>> searchTask;

    private void searchTask(final String searchString){
        try{
            if(searchTask!=null&&searchTask.getStatus()==AsyncTask.Status.RUNNING){
                return;
            }
            searchTask=new AsyncTask<Void, ArrayList<Movie>, ArrayList<Movie>>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    showProgressBar();
                }

                @Override
                protected ArrayList<Movie> doInBackground(Void... voids) {
                    ArrayList<Movie> searchList=new ArrayList<Movie>();
                    try{
                        for (Movie movie:movieList){
                            if(movie.getTitle().toLowerCase().contains(searchString.toLowerCase())){
                                if(!searchList.contains(movie)){
                                    searchList.add(movie);
                                }
                            }
                        }
                        if(movieList!=null&&movieList.size()>0){
                            CommonMethod.sortBypopularity(movieList);
                        }



                    }catch (Exception ex){
                        Log.e(TAG,ex.toString());
                    }
                    return searchList;
                }

                @Override
                protected void onPostExecute(ArrayList<Movie> movies) {
                    super.onPostExecute(movies);
                    hideProgressBar();
                    if(movies!=null&&movies.size()>0){
                        hideNoSearchResultView();
                        showRecyclerView();
                        updateRecycleView(movies);
                    }else {
                        hideRecyclerView();
                        showNoSearchResultView();
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }

    private void showProgressBar(){
        try{
            ProgressBar progressBar=(ProgressBar) findViewById(R.id.progress_bar);
            if(progressBar.getVisibility()==View.GONE){
                progressBar.setVisibility(View.VISIBLE);
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void hideProgressBar(){
        try{
            ProgressBar progressBar=(ProgressBar) findViewById(R.id.progress_bar);
            if(progressBar.getVisibility()==View.VISIBLE){
                progressBar.setVisibility(View.GONE);
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private AsyncTask<Void,Void,Void> sortTask;

    private void sortTask(){
        try{
            if(sortTask!=null&&sortTask.getStatus()==AsyncTask.Status.RUNNING){
                return;
            }
            sortTask=new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    showProgressBar();
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try{
                        if(AppConstant.sortedMechanism==3){
                            movieList=CommonMethod.sortByName(movieList);
                        }else if(AppConstant.sortedMechanism==2){
                            movieList=CommonMethod.sortByDate(movieList);
                        }else if(AppConstant.sortedMechanism==1){
                            movieList=CommonMethod.sortBypopularity(movieList);
                        }
                    }catch (Exception ex){
                        Log.e(TAG,ex.toString());
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    updateRecycleView(movieList);
                    hideProgressBar();
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void initializeDrawerMenu(){
        ImageView drawerBack;
        TextView header,sortText,favouriteText,watchList;
        RelativeLayout favouriteContainer,WatchlistContainer;
        RadioGroup sortGroup;
        try{
            drawerBack=(ImageView)findViewById(R.id.menu_back);
            drawerBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeDrawerMenu();
                }
            });
            header=(TextView)findViewById(R.id.menu_header_text);
            sortText=(TextView)findViewById(R.id.sort_text);
            favouriteText=(TextView)findViewById(R.id.favourite_text);
            watchList=(TextView)findViewById(R.id.watchList_text);
            CommonMethod.setFontMedium(header);
            CommonMethod.setFontMedium(sortText);
            CommonMethod.setFontMedium(favouriteText);
            CommonMethod.setFontMedium(watchList);
            favouriteContainer=(RelativeLayout)findViewById(R.id.favourite_container);
            favouriteContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(MovieListActivity.this, getString(R.string.wip_message), Toast.LENGTH_SHORT).show();
                    if(!isFavouriteListEmpty()){
                        closeDrawerMenu();
                        openFavouriteLisScreen();
                    }else {
                        Toast.makeText(MovieListActivity.this, getString(R.string.empty_favouriteList), Toast.LENGTH_SHORT).show();
                    }

                }
            });
            WatchlistContainer=(RelativeLayout)findViewById(R.id.watchList_container);
            WatchlistContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isWatchListEmpty()){
                        closeDrawerMenu();
                        openWatchLisScreen();
                    }else {
                        Toast.makeText(MovieListActivity.this, getString(R.string.empty_watchList), Toast.LENGTH_SHORT).show();
                    }

                }
            });
            sortGroup=(RadioGroup)findViewById(R.id.radioGroup);
            if(AppConstant.sortedMechanism==1){
                sortGroup.check(R.id.sortByPopuLarityRadio);
            }else if(AppConstant.sortedMechanism==2){
                sortGroup.check(R.id.sortByDate);
            }else if(AppConstant.sortedMechanism==3)
                sortGroup.check(R.id.sortByNameRadio);
            sortGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                    try{
                        if(checkedId==R.id.sortByPopuLarityRadio){
                            closeDrawerMenu();
                            AppConstant.sortedMechanism=1;
                            sortTask();
                        }else if(checkedId==R.id.sortByDate){
                            closeDrawerMenu();
                            AppConstant.sortedMechanism=2;
                            sortTask();
                        }else if(checkedId==R.id.sortByNameRadio){
                            closeDrawerMenu();
                            AppConstant.sortedMechanism=3;
                            sortTask();
                        }

                    }catch (Exception ex){
                        Log.e(TAG,ex.toString());
                    }
                }
            });

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }

    }
    private void openDrawerMenu(){
        try{
            if(!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
                initializeDrawerMenu();
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void closeDrawerMenu(){
        try{
            if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }

    @Override
    public void onBackPressed() {
        try{
            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                closeDrawerMenu();
                return;
            }
            if(findViewById(R.id.search_container).getVisibility()==View.VISIBLE){
                hideSearchContainer();
                return;
            }
            if (doubleBackToExitPressedOnce) {
                this.finishAffinity();
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.exit_app_text), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);


        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }


    }
    private PopupWindow morePopUpWindow;
    public void showPopup(View v, final Movie movie) {
        try {
            LinearLayout favourite,watchList;
            final ImageView favouriteImage,watchListImage;
            final TextView favouriteText,watchListText;

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.more_popup_layout, null);
            morePopUpWindow = new PopupWindow(popupView, CommonMethod.dpToPx(getBaseContext(),200),
                    CommonMethod.dpToPx(getBaseContext(),81));
            morePopUpWindow.setOutsideTouchable(true);
            morePopUpWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            morePopUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
            });
            favouriteText=(TextView)popupView.findViewById(R.id.favourite_more_text);
            CommonMethod.setFontMedium(favouriteText);
            watchListText=(TextView)popupView.findViewById(R.id.wathchlist_more_text);
            CommonMethod.setFontMedium(watchListText);
            favouriteImage=(ImageView)popupView.findViewById(R.id.favourite_image);
            watchListImage=(ImageView)popupView.findViewById(R.id.watchlist_image);
            favourite=(LinearLayout)popupView.findViewById(R.id.favourite_container);
            if(!CommonMethod.isMovieInFavouriteList(getBaseContext(),movie.getID())){
                favouriteText.setText(getString(R.string.add_favourite));
                favouriteImage.setImageResource(R.drawable.ic_favorite_unmarkerd);

            }else {
                favouriteText.setText(getString(R.string.remove_favourite));
                favouriteImage.setImageResource(R.drawable.ic_favorite);
            }
            if(!CommonMethod.isMovieInWatchList(getBaseContext(),movie.getID())){
                watchListText.setText(getString(R.string.add_watchList));
                watchListImage.setImageResource(R.drawable.ic_playlist_unmarked);

            }else {
                watchListText.setText(getString(R.string.remove_watchList));
                watchListImage.setImageResource(R.drawable.ic_playlist_add);
            }
            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        if(CommonMethod.isMovieInFavouriteList(getBaseContext(),movie.getID())){
                            CommonMethod.removeFromFavouriteList(getBaseContext(),movie.getID());
                            favouriteText.setText(getString(R.string.add_favourite));
                            favouriteImage.setImageResource(R.drawable.ic_favorite_unmarkerd);

                        }else {
                            CommonMethod.saveToFavouriteList(getBaseContext(),movie.getID());
                            favouriteText.setText(getString(R.string.remove_favourite));
                            favouriteImage.setImageResource(R.drawable.ic_favorite);
                        }
                    }catch (Exception ex){
                        Log.e(TAG,ex.toString());
                    }

                }
            });
            watchList=(LinearLayout)popupView.findViewById(R.id.watchlist_container);
            watchList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        if(CommonMethod.isMovieInWatchList(getBaseContext(),movie.getID())){
                            CommonMethod.removeFromWatchList(getBaseContext(),movie.getID());
                            watchListText.setText(getString(R.string.add_watchList));
                            watchListImage.setImageResource(R.drawable.ic_playlist_unmarked);

                        }else {
                            CommonMethod.saveToWatchList(getBaseContext(),movie.getID());
                            watchListText.setText(getString(R.string.remove_watchList));
                            watchListImage.setImageResource(R.drawable.ic_playlist_add);
                        }
                    }catch (Exception ex){
                        Log.e(TAG,ex.toString());
                    }
                }
            });
            morePopUpWindow.showAsDropDown(v);
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }

    }

    @Override
    public void onItemListener(Movie movie) {
        try{
            hideSearchContainer();
            openDetailScreen(movie);
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }


    }

    @Override
    public void onMoreListener(Movie movie, View view) {
        try{
            hideSearchContainer();
            showPopup(view,movie);
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void openFavouriteLisScreen() {
        Intent intent;
        try {
            intent = new Intent(getApplicationContext(), FavouriteListActivity.class);
            startActivityForResult(intent,FAVOURITE_CODE);
            overridePendingTransition(R.anim.open_next, R.anim.close_main);
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }
    private void openWatchLisScreen() {
        Intent intent;
        try {
            intent = new Intent(getApplicationContext(), WatchListActivity.class);
            startActivityForResult(intent,WATCHLIST_CODE);
            overridePendingTransition(R.anim.open_next, R.anim.close_main);
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }
    private void openDetailScreen(Movie movie) {
        Intent intent;
        try {
            intent = new Intent(getApplicationContext(), MovieDetailScreen.class);
            intent.putExtra("movie",movie);
            startActivityForResult(intent,DETAIL_CODE);
            overridePendingTransition(R.anim.open_next, R.anim.close_main);
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }
    private boolean isFavouriteListEmpty(){
        String favouriteList="";
        try{
            favouriteList=CommonMethod.getFavouriteMovieList(getBaseContext());
            if(favouriteList.equalsIgnoreCase("")||favouriteList.equalsIgnoreCase("[]")){
                return true;
            }else {
                JSONArray array=new JSONArray(favouriteList);
                if(array.length()>0){
                    return false;
                }else {
                    return true;
                }
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return true;
    }
    private boolean isWatchListEmpty(){
        String list="";
        try{
            list=CommonMethod.getWatchMovieList(getBaseContext());
            if(list.equalsIgnoreCase("")||list.equalsIgnoreCase("[]")){
                return true;
            }else {
                JSONArray array=new JSONArray(list);
                if(array.length()>0){
                    return false;
                }else {
                    return true;
                }
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
