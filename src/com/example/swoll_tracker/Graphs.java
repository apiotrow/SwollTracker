package com.example.swoll_tracker;

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.GraphViewStyle;
//import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Graphs extends ActionBarActivity {
	
	int[] curls = new int[32];
	int[] bench = new int[32];
	int[] squats = new int[32];
	int[] deadlifts = new int[32];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graphs);
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
			data[i] = new GraphViewData(i, curls[i]);
		}
		GraphViewSeriesStyle green = new GraphViewSeriesStyle(Color.rgb(90, 250, 00), 3);
		GraphViewSeries seriesCurls = new GraphViewSeries("Curls" ,green , data);
		data = new GraphViewData[32];
		for(int i = 0; i < 32; ++i){
			data[i] = new GraphViewData(i, bench[i]);
		}
		GraphViewSeriesStyle blue = new GraphViewSeriesStyle(Color.rgb(90, 00, 250), 3);
		GraphViewSeries seriesBench = new GraphViewSeries("Bench Press", blue, data);
		data = new GraphViewData[32];
		
		for(int i = 0; i < 32; ++i){
			data[i] = new GraphViewData(i, squats[i]);
		}
		GraphViewSeriesStyle red = new GraphViewSeriesStyle(Color.rgb(250, 00, 90), 3);
		GraphViewSeries seriesSquats = new GraphViewSeries("Squats", red, data);
		data = new GraphViewData[32];
		
		for(int i = 0; i < 32; ++i){
			data[i] = new GraphViewData(i, deadlifts[i]);
		}
		GraphViewSeriesStyle purple = new GraphViewSeriesStyle(Color.rgb(250, 00, 250), 3);
		GraphViewSeries seriesDead = new GraphViewSeries("Deadlifts", purple, data);
		//data = new GraphViewData[31];
		LineGraphView graphView = new LineGraphView(
			this,
			"Workouts"
		);
		graphView.addSeries(seriesDead);
		graphView.addSeries(seriesSquats);
		graphView.addSeries(seriesBench);
		graphView.addSeries(seriesCurls);
		LinearLayout layout = (LinearLayout) findViewById(R.id.myLayout);
		layout.setBackgroundColor(Color.rgb(00, 00, 00));
		//System.out.println("HELLO OUT THERE");
		graphView.setViewPort(2, 10);
		graphView.setScalable(true);
		graphView.setShowLegend(true);
		graphView.setLegendWidth(200);
		graphView.setLegendAlign(LegendAlign.BOTTOM);
		graphView.setBackgroundColor(Color.rgb(0, 0, 0));
		layout.addView(graphView);
		
		//for(int i = 1; i < 32; ++i){
		//	System.out.println(curls[i]);
		//}
		//TextView text = (TextView) findViewById(R.id.textView1);
		//SharedPreferences settings = getSharedPreferences("log", 0);
		//int reps = settings.getInt("testKey", 1);
		//System.out.println(reps);
		//text.setText(Integer.toString(reps));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graphs, menu);
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
			//System.out.println(date);
			curls[i] = settings.getInt(date + "Curls", 0);
			bench[i] = settings.getInt(date + "Bench Press", 0);
			squats[i] = settings.getInt(date + "Squats", 0);
			deadlifts[i] = settings.getInt(date + "Deadlift", 0);
		}	
	}
}
