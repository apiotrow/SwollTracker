package com.example.swoll_tracker;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Log extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		int curls = 0;
		int bench = 0;
		int squats = 0;
		int dead = 0;
		TextView tvBench = (TextView) findViewById(R.id.textView2);
		TextView tvCurl = (TextView) findViewById(R.id.textView6);
		TextView tvSquats = (TextView) findViewById(R.id.textView7);
		TextView tvDead = (TextView) findViewById(R.id.textView8);
		TextView tvDate = (TextView) findViewById(R.id.textView9);
		Bundle bundle = getIntent().getExtras();
		String date = bundle.getString("date");
		tvDate.setText(date);
		SharedPreferences settings = getSharedPreferences("log", 0);
		curls = settings.getInt(date + "Curls", 0);
		bench = settings.getInt(date + "Bench Press", 0);
		squats = settings.getInt(date + "Squats", 0);
		dead = settings.getInt(date + "Deadlift", 0);
		tvBench.setText(Integer.toString(bench));
		tvCurl.setText(Integer.toString(curls));
		tvSquats.setText(Integer.toString(squats));
		tvDead.setText(Integer.toString(dead));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log, menu);
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
