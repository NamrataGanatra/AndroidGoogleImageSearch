package com.mobileapps.googleimagesearch;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {
	
	private int visibleThreshold = 5;
	private int currentPage =  0;
	private int previousTotalItemCount = 0;
	private boolean loading = true;
	private int startIndex = 0;
	
	public EndlessScrollListener()
	{		
	}
	
	public EndlessScrollListener(int visibleThreshold, int startPage)
	{
		this.visibleThreshold = visibleThreshold;
		this.startIndex = startPage;
		this.currentPage = startPage;
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		if(!loading && (totalItemCount < previousTotalItemCount))
		{
			this.currentPage = this.startIndex;
			this.previousTotalItemCount = totalItemCount;
			if(totalItemCount == 0)
				this.loading = true;
		}
		
		if(loading)
		{
			if(totalItemCount > previousTotalItemCount)
			{
				loading = false;
				previousTotalItemCount = totalItemCount;
				currentPage++;
			}
		}
		
		if(!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold))
		{
	         onLoadMore(currentPage + 1, totalItemCount);
	         loading = true;
		}
		
	}
	
	public abstract void onLoadMore(int page, int totalItemsCount);
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		// do nothing
	}
}
