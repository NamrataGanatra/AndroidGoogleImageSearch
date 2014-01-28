package com.mobileapps.googleimagesearch;

import android.net.Uri;
import android.util.Log;

import com.loopj.android.http.*;

public class ImageSearchClient {

	private static AsyncHttpClient client = new AsyncHttpClient();
	private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&start=%s&imgsz=%s&imgtype=%s&imgcolor=%s&q=%s";
	
	
	public static void get(String query, int offset, String imageSize, String imageType, String imageColor, String siteFilter, AsyncHttpResponseHandler handler)
	{
		client.get(getFullUrl(query, offset, imageSize, imageType, imageColor, siteFilter), null, handler);
	}
	
	 
	private static String getFullUrl(String query, int offset, String imageSize, String imageType, String imageColor, String siteFilter)
	{
		String fullUrl = String.format(BASE_URL, offset, getGoogleImageSize(imageSize), imageType, imageColor, Uri.encode(query));
		Log.d("GridImageSearch", "Google Query Url is : " + fullUrl);
		return fullUrl;
	}

	private static String getGoogleImageSize(String imageSize)
	{
		if(imageSize == null || imageSize.isEmpty())
		{
			return "";
		}
		else if(imageSize.equalsIgnoreCase("small"))
		{
			return "icon";
		}
		else if(imageSize.equalsIgnoreCase("medium"))
		{
			return "medium";
		}
		else if(imageSize.equalsIgnoreCase("large"))
		{
			return "xxlarge";
		}
		else
		{
			return "huge";
		}
	}
	
}
