package com.brontoo.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.brontoo.R;
import com.brontoo.common.AppConstant;
import com.brontoo.common.CommonMethod;

import org.json.JSONObject;

public class SplashScreen extends Activity {
    private static final String TAG = "SplashScreen";
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        try {
            initializeActivityControl();

        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    private void initializeActivityControl() {
        try {
            mRequestQueue = Volley.newRequestQueue(this);
            if (CommonMethod.isOnline(getBaseContext())) {
                fetchData();
            }else {
                showNoInternetDialog();
            }

        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    private void fetchData() {

        try {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, AppConstant.API_URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                hideProgressBar();
                                CommonMethod.saveMovieList(getBaseContext(), response.toString());
                                openMovieLisScreen();
                            } catch (Exception e) {
                                Log.e(TAG, e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

		/* Add your Requests to the RequestQueue to execute */
            showProgressBar();
            mRequestQueue.add(req);

        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    private void hideProgressBar() {
        try {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    private void showProgressBar() {
        try {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            if (progressBar.getVisibility() == View.GONE) {
                progressBar.setVisibility(View.VISIBLE);
            }

        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    private void openMovieLisScreen() {
        Intent intent;
        try {
            intent = new Intent(getApplicationContext(), MovieListActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.open_next, R.anim.close_main);
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    private void showNoInternetDialog(){
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogStyle);
            builder.setMessage(getString(R.string.no_internet_message));
            builder.setCancelable(false);
            builder.setNegativeButton(
                    getString(R.string.exit_text),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finishAffinity();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();

        }catch (Exception ex){
            Log.e(TAG,ex.toString());

        }
    }
}
