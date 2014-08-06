package com.example.swoll_tracker;

import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class WeightGraph extends ActionBarActivity {

	int[] weight = new int[32];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weight_graph);
		java.util.Calendar c = java.util.Calendar.getInstance();
		int days = 31;
		int yr = c.get(java.util.Calendar.YEAR);
		int month = c.get(java.util.Calendar.MONTH) + 1;
		String month1 = "" + month;
		String year1 = "/" + yr;
		if(month < 10){
			month1 = "0" + month;
		}
		getDataFromMonth(days, month1, year1);
		GraphViewData[] data = new GraphViewData[32];
		for(int i = 0; i < 32; ++i){
			data[i] = new GraphViewData(i, weight[i]);
		}
		GraphViewSeriesStyle green = new GraphViewSeriesStyle(Color.rgb(90, 250, 00), 3);
		GraphViewSeries seriesWeight = new GraphViewSeries("Weight" ,green , data);
		LineGraphView graphView = new LineGraphView(
			this,
			"Weight"
		);
		graphView.addSeries(seriesWeight);
		LinearLayout layout = (LinearLayout) findViewById(R.id.myWLayout);
		layout.setBackgroundColor(Color.rgb(00, 00, 00));
		//System.out.println("HELLO OUT THERE");
		graphView.setViewPort(2, 10);
		graphView.setScalable(true);
		graphView.setShowLegend(true);
		graphView.setLegendWidth(200);
		graphView.setLegendAlign(LegendAlign.BOTTOM);
		graphView.setBackgroundColor(Color.rgb(0, 0, 0));
		layout.addView(graphView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weight_graph, menu);
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
	void getDataFromMonth(int days, String month, String year){
		SharedPreferences settings = getSharedPreferences("log", 0);
		//curls = settings.getInt(date + "Curls", 0);
		for(int i = 1; i < days + 1; ++i){
			String d = Integer.toString(i);
			String date;
			if(i < 10){
				date = month + "/0" + d + year;
			}
			else{
				date = month + "/" + d + year;
			}
			weight[i] = settings.getInt(date + "weight", 0);
		}	
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_weight_graph,
					container, false);
			return rootView;
		}
	}

}
