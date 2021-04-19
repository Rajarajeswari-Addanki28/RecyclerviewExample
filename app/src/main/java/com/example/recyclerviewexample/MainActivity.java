package com.example.recyclerviewexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

     RecyclerView rv;
     ArrayList<Stark> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.rv);
        list=new ArrayList<>();
        getSupportLoaderManager().initLoader(0,null,this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Nullable
            @Override
            public String loadInBackground() {
                try {
                    URL u=new URL("https://www.googleapis.com/books/v1/volumes?q=android");
                    HttpURLConnection huc=(HttpURLConnection) u.openConnection();
                    huc.setRequestMethod("GET");
                    huc.connect();
                    InputStream is=huc.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb=new StringBuilder();
                    String line;
                    while ((line=br.readLine())!=null){
                        sb.append(line);
                   }
                    return sb.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject root=new JSONObject(data);
            JSONArray items=root.getJSONArray("items");
            for (int i=0;i<items.length();i++)
            {
                JSONObject obj=items.getJSONObject(i);
                JSONObject volume=obj.getJSONObject("volumeInfo");
                String title=volume.getString("title");

                String p=volume.getString("publisher");
                String pd=volume.getString("publishedDate");


                JSONObject imglink=volume.getJSONObject("imageLinks");
                String img=imglink.getString("smallThumbnail");
                Stark stark=new Stark(title,img,p,pd);
                list.add(stark);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        MyAdapter adapter=new MyAdapter(this,list);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
