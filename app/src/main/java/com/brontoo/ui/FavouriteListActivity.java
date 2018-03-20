package com.brontoo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.brontoo.R;
import com.brontoo.adapters.FavouriteListAdapter;
import com.brontoo.common.CommonMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavouriteListActivity extends AppCompatActivity {
    private static final String TAG="FavouriteListActivity";
    private ArrayList<JSONObject> favouriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        try{
            initializeActivityControl();

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void initializeActivityControl(){
        ImageView back;
        TextView header;
        RecyclerView recyclerView;
        FavouriteListAdapter adapter;
        DividerItemDecoration mDividerItemDecoration;
        try{
            recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
            favouriteList=getFavouriteList();
            if(favouriteList!=null&&favouriteList.size()>0){
                adapter=new FavouriteListAdapter(this,favouriteList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                mDividerItemDecoration = new DividerItemDecoration(
                        recyclerView.getContext(),
                        DividerItemDecoration.VERTICAL
                );
                recyclerView.addItemDecoration(mDividerItemDecoration);
                back=(ImageView)findViewById(R.id.back);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
                header=(TextView)findViewById(R.id.header_Text);
                CommonMethod.setFontBold(header);
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private ArrayList<JSONObject> getFavouriteList(){
        ArrayList<JSONObject> list=new ArrayList<>();
        try{
            String favouriteList= CommonMethod.getFavouriteMovieList(getBaseContext());
            if(!favouriteList.equalsIgnoreCase("")&&!favouriteList.equalsIgnoreCase("[]")){
                JSONArray array=new JSONArray( favouriteList);
                if(array!=null&&array.length()>0){
                    for (int count=0;count<array.length();count++){
                        list.add(array.getJSONObject(count));
                    }
                }
            }

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
