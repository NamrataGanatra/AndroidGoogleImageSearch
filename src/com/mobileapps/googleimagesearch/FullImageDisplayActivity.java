package com.mobileapps.googleimagesearch;

import com.loopj.android.image.SmartImageView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FullImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image_display);
		// parameters from intent are passed into the bundle
		// accessing the input parameters

		// following line below is for getting string extra if we were passing that
		// String url = getIntent().getStringExtra("url");
		ImageResult imageResult = (ImageResult) getIntent().getSerializableExtra("image");
		SmartImageView smartImage = (SmartImageView) findViewById(R.id.my_image);
		smartImage.setImageUrl(imageResult.getFullImageUrl());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.full_image_display, menu);
		return true;
	}

}
