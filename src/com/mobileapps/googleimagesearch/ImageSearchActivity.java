package com.mobileapps.googleimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class ImageSearchActivity extends Activity {
	
	EditText edtSearch;
	Button btnSearch;
	GridView gvImageResults;
	ArrayList<ImageResult> images = new ArrayList<ImageResult>();
	ImageResultAdapter imageAdapter;
	String imageSize = "";
	String imageType = "";
	String imageColor = "";
	String siteFilter = "";
	
	// REQUEST_CODE can be anything
	final int REQUEST_CODE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		setupElements();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);
		return true;
	}
	
	public void setupElements()
	{
		this.edtSearch = (EditText) findViewById(R.id.edtSearch);
		this.btnSearch = (Button) findViewById(R.id.btnSearch);
		this.gvImageResults = (GridView) findViewById(R.id.gvImageResults);
		this.imageAdapter = new ImageResultAdapter(this, this.images);
		this.gvImageResults.setAdapter(this.imageAdapter);
		// on click of grid view item, we want to display the full image
		this.gvImageResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				// intent is request to bring new activity
				// on click of an Item, you want to open up a new activity
				Intent i = new Intent(getApplicationContext(), FullImageDisplayActivity.class);
				ImageResult image = images.get(position);
				// pass the key/value pairs of the parameter names that you want to set in the activity
				i.putExtra("image", image);
				startActivity(i);
			}
			
		});
		
		// set the onscroll listener for the grid view
		this.gvImageResults.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				String query = edtSearch.getText().toString();
				loadGridViewImages(query, page);
			}
		});
	} 
	
	private void loadGridViewImages(String query, final int offset)
	{
		ImageSearchClient.get(query, offset, imageSize, imageType, imageColor, siteFilter, new AsyncHttpResponseHandler()
		{
			@Override
			public void onSuccess(String response)
			{
				try
				{
					JSONObject json = new JSONObject(response);
					JSONArray results =  ((JSONObject)json.get("responseData")).getJSONArray("results");
					// since its a new search term, clear everything
					if(offset == 0)
						images.clear();
					for(int i=0; i < results.length(); i++)
					{
						JSONObject imageObject = results.getJSONObject(i);
						// note: adding it to the adapter directly adds to an array as well as refreshes the adapter
						// so that you don't have to explicitly refresh the grid view.
						imageAdapter.add(new ImageResult(imageObject));
						//images.add(new ImageResult(imageObject));
						// refreshing the grid view
						// 1. either call imageAdapter.notify() OR gvImageResults.invalidateViews()
					}
					Log.d("GridImageSearch", "The length of images is " + images.size());
				}
				catch(JSONException e)
				{
					Log.e("GridImageSearch", "error parsing the response ", e);
				}
			}
		});
	}
	
	// onsettings button click
	public void onSettingsClick(MenuItem mi)
	{
		 // call the Settings Activity and expect the result back
		 Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		 i.putExtra("image_size", imageSize);
		 i.putExtra("image_type", imageType);
		 i.putExtra("image_color", imageColor);
		 i.putExtra("site_filter", siteFilter);
		 
		 startActivityForResult(i, REQUEST_CODE);
	}
	
	// This method handles the result of Sub-Activity that gets launched using StartActivityForResult
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// settings activity
		if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
		{
			imageSize = data.getExtras().getString("image_size");
			imageType = data.getExtras().getString("image_type");
			imageColor = data.getExtras().getString("image_color");
			siteFilter = data.getExtras().getString("site_filter");
			//Toast.makeText(this, "image size is " + imageSize, Toast.LENGTH_LONG).show();
		}
	}
	
	public void onSearchClick(View v)
	{
		String query = this.edtSearch.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG).show();
		// start with the first set of results when the search button is clicked
		loadGridViewImages(query, 0);		
	}

}
