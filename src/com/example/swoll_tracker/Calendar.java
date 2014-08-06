package com.example.swoll_tracker;

import android.support.v7.app.ActionBarActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

public class Calendar extends ActionBarActivity {
	
	private DatePicker dp;
	Button buttonPick;
	String date;
	int selYear;
	int selMonth;
	int selDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		dp = (DatePicker) findViewById(R.id.datePicker1);
		buttonPick = (Button) findViewById(R.id.GoToDate);
		buttonPick.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				goLog(v);
				
			}
 
		});
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			selYear = selectedYear;
			selMonth = selectedMonth;
			selDay = selectedDay;

			//store this date information
			//so when the Go To Date button is
			//pressed, the date information can be sent
			//to the Log activity that gets opened up, allowing
			//the Log activity to open up the log for
			//that particular date
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);
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
	
	public void goMainMenu(View V) {
		Intent intent = new Intent(this, MainMenu.class);
		startActivity(intent);
	}
	
	//public void OnDateChangedListener(View v){
	//	final DatePicker = DatePicker.
		
	//}
	
	public void goLog(View V) {
		Intent intent = new Intent(this, Log.class);
		//datePickerListener.onDateSet(V, arg1, arg2, arg3);
		//final Calendar c = Calendar.getInstance();
		//int selYear = c.getYear();
		selYear = dp.getYear();
		selMonth = dp.getMonth() + 1;  //For some reason months start at zero WTF
		selDay = dp.getDayOfMonth();
		//System.out.println("Year: " + selYear);
		//System.out.println("Month: " + selMonth);
		//System.out.println("Day: " + selDay);
		if(selDay < 10 && selMonth < 10){
			date = "0" + selMonth + "/0" + selDay + "/" + selYear;
		}
		else if(selDay < 10 && selMonth >= 10){
			date = selMonth + "/0" + selDay + "/" + selYear;
		}
		else if(selDay >= 10 && selMonth >= 10){
			date = selMonth + "/" + selDay + "/" + selYear;
		}
		else if(selDay >= 10 && selMonth < 10){
			date = "0" + selMonth + "/" + selDay + "/" + selYear;
		}
		//System.out.println(date);
		intent.putExtra("date", date);
		startActivity(intent);
	}

	//public static Calendar getInstance() {
		// TODO Auto-generated method stub
	//	return null;
	//}



}
