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
import com.brontoo.adapters.WatchListAdapter;
import com.brontoo.common.CommonMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WatchListActivity extends AppCompatActivity {

    private static final String TAG="WatchListActivity";
    private ArrayList<JSONObject> watchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
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
        WatchListAdapter adapter;
        DividerItemDecoration mDividerItemDecoration;
        try{
            recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
            watchList=getWatchList();
            if(watchList!=null&&watchList.size()>0){
                adapter=new WatchListAdapter(this,watchList);
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
    private ArrayList<JSONObject> getWatchList(){
        ArrayList<JSONObject> list=new ArrayList<>();
        try{
            String watchList= CommonMethod.getWatchMovieList(getBaseContext());
            if(!watchList.equalsIgnoreCase("")&&!watchList.equalsIgnoreCase("[]")){
                JSONArray array=new JSONArray( watchList);
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
