package com.sreesharp.instagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class PhotoActivity extends Activity {
	
	private static final String CLIENT_ID = "38ae18bb2cd043c496bddc2f23de6ae5";
	ArrayList<InstagramPhoto> photos;
	PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        
        fetchPopularPhotos();
        
        adapter = new PhotoAdapter(this, photos);
        ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(adapter);
    }

    private void fetchPopularPhotos() {
		// https://api.instagram.com/v1/media/popular?client_id=38ae18bb2cd043c496bddc2f23de6ae5
    	//data->images->standard-resolution->url
    	photos = new ArrayList<InstagramPhoto>();
    	String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
    	AsyncHttpClient client = new AsyncHttpClient();
    	client.get(url, new JsonHttpResponseHandler(){
    		@Override
    		public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
    			JSONArray photosJSON = null;
    			try{
    				photosJSON = response.getJSONArray("data");
    				for(int i=0; i < photosJSON.length(); i++)
    				{
    					JSONObject photoJSON = photosJSON.getJSONObject(i);
    					InstagramPhoto photo = new InstagramPhoto();
    					photo.userName = photoJSON.getJSONObject("user").getString("username");
    					photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
    					photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
    					if(photoJSON.getString("caption")!= null)
    						photo.caption = photoJSON.getJSONObject("caption").getString("text");
    					photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
    					photo.createdTime = photoJSON.getLong("created_time");
    					photo.userName = photoJSON.getJSONObject("user").getString("full_name");
    					photo.userProfileUrl = photoJSON.getJSONObject("user").getString("profile_picture");
    					photos.add(photo);
    				}
    				
    				adapter.notifyDataSetChanged();
    			}
    			catch(JSONException e)
    			{
    				e.printStackTrace();
    			}
    		}
    	});
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

   

}
