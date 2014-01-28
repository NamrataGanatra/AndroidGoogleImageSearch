package com.mobileapps.googleimagesearch;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ImageResult implements Serializable {

	private static final long serialVersionUID = 1527482347774216376L;
	private String fullImageUrl;
	private String thumbImageUrl;
	private String imageId;
	
	public ImageResult(JSONObject json)
	{
		try
		{
			this.fullImageUrl = json.getString("url");
			this.thumbImageUrl = json.getString("tbUrl");
			this.imageId = json.getString("imageId");
			
		}
		catch(JSONException e)
		{
			Log.d("GridImageSearch", "error occurred parsing json response ", e);
		}
	}
	
	public String getFullImageUrl() {
		return fullImageUrl;
	}
	public void setFullImageUrl(String fullImageUrl) {
		this.fullImageUrl = fullImageUrl;
	}
	public String getThumbImageUrl() {
		return thumbImageUrl;
	}
	public void setThumbImageUrl(String thumbImageUrl) {
		this.thumbImageUrl = thumbImageUrl;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
    public String toString()
    {
    	return this.thumbImageUrl;
    }
	

}
