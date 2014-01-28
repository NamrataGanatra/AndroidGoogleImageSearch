package com.mobileapps.googleimagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

public class ImageResultAdapter extends ArrayAdapter<ImageResult> {
	public ImageResultAdapter(Context context, List<ImageResult> images)
	{
		// for simple text view use the below layout
		// super(context, android.R.layout.simple_list_item_1, images);
		
		// the line below specifies that 
		super(context, R.layout.image_result_view, images);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// take the ImageResult object and convert it into a view (SmartImageView in this case) for GridView
		// parent in this case is GridView
		ImageResult imageInfo = this.getItem(position);
		SmartImageView ivImage;
		if(convertView == null)
		{
			// if view does not exist then create it
			// creating a view from layout file (or XML file) is called as inflating
			LayoutInflater inflater = LayoutInflater.from(getContext());
			ivImage = (SmartImageView) inflater.inflate(R.layout.image_result_view, parent, false);
		}
		else
		{
			// if view is already instantiated
			ivImage = (SmartImageView) convertView;
			// clear the image if there was already some previous data in there by setting the background to transparent
			ivImage.setImageResource(android.R.color.transparent);			
		}
		// now set the image url
		ivImage.setImageUrl(imageInfo.getThumbImageUrl());
		return ivImage;
	}
}
