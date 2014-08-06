package com.example.swoll_tracker;





import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainMenu extends ActionBarActivity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	public void goCalendar(View V) {
		Intent intent = new Intent(this, Calendar.class);
		startActivity(intent);
	}
	
	public void goExerciseList(View V) {
		Intent intent = new Intent(this, ExerciseList.class);
		startActivity(intent);
	}
	
	public void goTakeSnapshot(View V) {
		Intent intent = new Intent(this, TakeSnapshot.class);
		startActivity(intent);
	}
	
	public void goGraphs(View V) {
		Intent intent = new Intent(this, Graphs.class);
		startActivity(intent);
	}
	public void goWeightTracker(View v){
		Intent intent = new Intent(this, WeightTracker.class);
		startActivity(intent);
	}


}
