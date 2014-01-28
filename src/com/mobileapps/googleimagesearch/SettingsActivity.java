package com.mobileapps.googleimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class SettingsActivity extends Activity {

	String imageSize, imageType, imageColor, siteFilter;
	Spinner sizeSpinner, typeSpinner, colorSpinner;
	EditText edtSiteFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		setElements();
	}
	
	private void setElements()
	{
		// get the data sent from the parent activity
		imageSize = getIntent().getStringExtra("image_size");
		sizeSpinner = (Spinner)findViewById(R.id.sizeSpinner);
		setSpinnerToValue(sizeSpinner, imageSize);
		
		imageType = getIntent().getStringExtra("image_type");
		typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
		setSpinnerToValue(typeSpinner, imageType);
		
		imageColor = getIntent().getStringExtra("image_color");
		colorSpinner = (Spinner)findViewById(R.id.colorSpinner);
		setSpinnerToValue(colorSpinner, imageColor);
		
		siteFilter = getIntent().getStringExtra("site_filter");
		edtSiteFilter = (EditText)findViewById(R.id.edtSiteFilter);
		edtSiteFilter.setText(siteFilter);
		
	}
	
	private void setSpinnerToValue(Spinner spinner, String value)
	{
		int index = 0;
		if(value != null && !value.isEmpty())
		{
			SpinnerAdapter adapter = spinner.getAdapter();
			for(int i=0; i < adapter.getCount(); i++)
			{
				if(adapter.getItem(i).equals(value))
				{
					index = i;
				}
			}
		}
		spinner.setSelection(index);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public void onSaveClick(View v)
	{
		Intent data = new Intent();
		imageSize = sizeSpinner.getSelectedItem().toString();
		imageType = typeSpinner.getSelectedItem().toString();
		imageColor = colorSpinner.getSelectedItem().toString();
		
		data.putExtra("image_size", imageSize);
		data.putExtra("image_type", imageType);
		data.putExtra("image_color", imageColor);
		data.putExtra("site_filter", siteFilter);
		
		setResult(RESULT_OK, data);
		// close this activity after the settings are saved
		finish();
	}

}
