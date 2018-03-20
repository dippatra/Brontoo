package com.brontoo.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.brontoo.controllers.Typefaces;
import com.brontoo.models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ABC on 3/17/2018.
 */

public class CommonMethod {
    private static final String TAG="CommonMethod";
    private static final int PREFTYPE_STRING = 0;
    private static final int PREFTYPE_INT = 1;
    private static final int PREFTYPE_BOOLEAN = 2;

    public static boolean isOnline(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    // Store string values in Preferences
    public static void savePreferences(Context context, String strKey, String strValue) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(strKey, strValue);
            editor.commit();
        } catch (Exception e) {
            e.toString();

        }
    }

    // Store boolean values in Preferences
    public static void savePreferences(Context context, String strKey, Boolean blnValue) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(strKey, blnValue);
            editor.commit();
        } catch (Exception e) {
            e.toString();
        }
    }

    // Store integer values in Preferences
    public static void savePreferences(Context context, String strKey, int intValue) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(strKey, intValue);
            editor.commit();
        } catch (Exception e) {
            e.toString();
        }
    }
    public static Object getPreferences(Context context, String key, int preferenceDataType) {
        Object value = null;
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (sharedPreferences.contains(key)) {
                switch (preferenceDataType) {
                    case PREFTYPE_BOOLEAN:
                        value = sharedPreferences.getBoolean(key, false);
                        break;
                    case PREFTYPE_INT:
                        value = sharedPreferences.getInt(key, 0);
                        break;
                    case PREFTYPE_STRING:
                        value = sharedPreferences.getString(key, "");
                        break;
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
        return value;
    }

    public static void saveMovieList(Context context,String movieList){
        try{
            savePreferences(context,"saveMovieList",movieList);

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }

    }
    public static String getMovieList(Context context){
        Object data;
        try{
            data=getPreferences(context,"saveMovieList",PREFTYPE_STRING);
            if(data!=null){
                return String.valueOf(data);
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return "";

    }
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 150);
        return noOfColumns;
    }
    public static String getFullFormLanguage(String language){
        try{
            Locale loc = new Locale(language);
            return loc.getDisplayLanguage(loc);
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return language;
    }
    public static String getYear(String yearString){
        try{

            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            Date yourDate = parser.parse(yearString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourDate);
            return String.valueOf(calendar.get(Calendar.YEAR));

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return "";
    }
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    public static ArrayList<Movie> sortByName(ArrayList<Movie>movies){
        try{
            Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie movie, Movie movie1 ) {
                    return movie.getTitle().compareTo(movie1.getTitle());
                }
            });

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return movies;
    }
    public static ArrayList<Movie> sortByDate(ArrayList<Movie>movies){
        try{
            final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie movie, Movie movie1 ) {
                    try {
                        return format.parse(movie.getReleaseDate().toString()).compareTo(format.parse(movie1.getReleaseDate().toString()));
                    }catch (Exception ex){
                        Log.e(TAG,ex.toString());
                    }
                    return 0;
                }
            });

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return movies;
    }
    public static ArrayList<Movie> sortBypopularity(ArrayList<Movie>movies){
        try{
            Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie movie, Movie movie1 ) {
                    if(movie.getPopularity()>movie1.getPopularity()){
                        return -1;
                    }else if(movie.getPopularity()<movie1.getPopularity()){
                        return 1;
                    }else {
                        return 0;
                    }

                }
            });

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return movies;
    }
    public static void setFontLight(TextView textView) {
        try {

            Typeface typeface = Typefaces.get(textView.getContext(), "fonts/Roboto-Thin.ttf");
            textView.setTypeface(typeface);
        } catch (Exception e) {
            e.toString();
        }
    }
    public static void setFontBold(TextView textView) {
        try {

            Typeface typeface = Typefaces.get(textView.getContext(), "fonts/Roboto-Regular.ttf");
            textView.setTypeface(typeface);
        } catch (Exception e) {
            e.toString();
        }
    }
    public static void setFontMedium(TextView textView) {
        try {
            Typeface typeface = Typefaces.get(textView.getContext(), "fonts/Roboto-Light.ttf");
            textView.setTypeface(typeface);
        } catch (Exception e) {
            e.toString();
        }
    }
    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    public static JSONObject getMovieJsonObject(Context context,int id){
        JSONArray movieArray;
        JSONObject object;
        try{
            object=new JSONObject(CommonMethod.getMovieList(context));
            if(object!=null){
                movieArray=object.getJSONArray("movies");
                if(movieArray!=null&&movieArray.length()>0){
                    for (int count=0;count<movieArray.length();count++){
                        if(movieArray.getJSONObject(count).getInt("id")==id){
                            return movieArray.getJSONObject(count);
                        }
                    }
                }
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return null;
    }
    public static void saveFavouriteMovieList(Context context,String movieList){
        try{
            savePreferences(context,"saveFavouriteMovieList",movieList);

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }

    }
    public static String getFavouriteMovieList(Context context){
        Object data;
        try{
            data=getPreferences(context,"saveFavouriteMovieList",PREFTYPE_STRING);
            if(data!=null){
                return String.valueOf(data);
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return "";

    }
    public static void saveWatchMovieList(Context context,String movieList){
        try{
            savePreferences(context,"saveWatchMovieList",movieList);

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }

    }
    public static String getWatchMovieList(Context context){
        Object data;
        try{
            data=getPreferences(context,"saveWatchMovieList",PREFTYPE_STRING);
            if(data!=null){
                return String.valueOf(data);
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return "";

    }
    public static boolean isMovieInWatchList(Context context,int id){
        String watchList;
        JSONArray watchListArray;
        try{
            watchList=getWatchMovieList(context);
            if(!watchList.equalsIgnoreCase("")){
                watchListArray=new JSONArray(watchList);
                if(watchListArray!=null&&watchListArray.length()>0){
                    for(int count=0;count<watchListArray.length();count++){
                        if(watchListArray.getJSONObject(count).getInt("id")==id){
                            return true;
                        }

                    }
                }

            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return false;
    }
    public static boolean isMovieInFavouriteList(Context context,int id){
        String favList;
        JSONArray favListArray;
        try{
            favList=getFavouriteMovieList(context);
            if(!favList.equalsIgnoreCase("")){
                favListArray=new JSONArray(favList);
                if(favListArray!=null&&favListArray.length()>0){
                    for(int count=0;count<favListArray.length();count++){
                        if(favListArray.getJSONObject(count).getInt("id")==id){
                            return true;
                        }
                    }
                }
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return false;
    }
    public static  void saveToWatchList(Context context,int id){
        String watchList;
        JSONArray watchListArray;
        JSONObject object;
        try{
            watchList=getWatchMovieList(context);
            if(!watchList.equalsIgnoreCase("")){
                watchListArray=new JSONArray(watchList);
                object=getMovieJsonObject(context,id);
                watchListArray.put(object);
                saveWatchMovieList(context,watchListArray.toString());
            }else {
                watchListArray=new JSONArray();
                object=getMovieJsonObject(context,id);
                watchListArray.put(object);
                saveWatchMovieList(context,watchListArray.toString());

            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    public static  void saveToFavouriteList(Context context,int id){
        String favList;
        JSONArray favListArray;
        JSONObject object;
        try{
            favList=getFavouriteMovieList(context);
            if(!favList.equalsIgnoreCase("")){
                favListArray=new JSONArray(favList);
                object=getMovieJsonObject(context,id);
                favListArray.put(object);
                saveFavouriteMovieList(context,favListArray.toString());
            }else {
                favListArray=new JSONArray();
                object=getMovieJsonObject(context,id);
                favListArray.put(object);
                saveFavouriteMovieList(context,favListArray.toString());

            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    public static void removeFromWatchList(Context context,int id){
        String watchList;
        JSONArray watchListArray;
        JSONArray newWatchListArray=new JSONArray();
        try{
            watchList=getWatchMovieList(context);
            if(!watchList.equalsIgnoreCase("")){
                watchListArray=new JSONArray(watchList);
                if(watchListArray!=null&&watchListArray.length()>0){
                    for(int count=0;count<watchListArray.length();count++){
                        if(watchListArray.getJSONObject(count).getInt("id")!=id){
                            newWatchListArray.put(watchListArray.getJSONObject(count));
                        }

                    }
                }
                saveWatchMovieList(context,newWatchListArray.toString());
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }

    public static void removeFromFavouriteList(Context context,int id){
        String favList;
        JSONArray favListArray;
        JSONArray newFavListArray=new JSONArray();
        try{
            favList=getFavouriteMovieList(context);
            if(!favList.equalsIgnoreCase("")){
                favListArray=new JSONArray(favList);
                if(favListArray!=null&&favListArray.length()>0){
                    for(int count=0;count<favListArray.length();count++){
                        if(favListArray.getJSONObject(count).getInt("id")!=id){
                            newFavListArray.put(favListArray.getJSONObject(count));
                        }

                    }
                }
                saveFavouriteMovieList(context,newFavListArray.toString());
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }


}
