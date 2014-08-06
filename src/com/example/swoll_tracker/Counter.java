package com.example.swoll_tracker;



import java.text.SimpleDateFormat;
import java.util.Date;



import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class Counter extends ActionBarActivity  implements SensorEventListener {
	Button buttonplus;
	Button buttonminus;
	String exer;
	int count;
	EditText mCount;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	String date = sdf.format(new Date());
	
	
	private float mLastX, mLastY, mLastZ, mAll;
	private boolean mInitialized;
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private final float NOISE = (float) 2.0;
    private boolean firstDone = false;
	private boolean firstSet = false;
	private boolean firstNeg = false;
	private boolean firstPos = false;
	private boolean directionSet = false;
	private boolean usingX = false;
	private boolean usingY = false;
	private boolean usingZ = false;
	
	private double thresh = 2;
	private boolean usingRepCounter = false;
	MediaPlayer mPlayer;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		if (savedInstanceState != null) {
			count = savedInstanceState.getInt("count");
			usingRepCounter = savedInstanceState.getBoolean("usingrepcounter");
		}

		buttonplus = (Button) findViewById(R.id.button1);
		buttonminus = (Button) findViewById(R.id.button2);
		mCount = (EditText) findViewById(R.id.editText1);
		mCount.setText("0");
		Bundle bundle = getIntent().getExtras();
		exer = bundle.getString("ex");
		

		mInitialized = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorManager.registerListener(this, mAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		mPlayer = MediaPlayer.create(this, R.raw.pit);
		 
		buttonplus.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				count += 1;
				mCount.setText(Integer.toString(count));
			}
 
		});
		
		buttonminus.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				count -= 1;
				if(count < 0) count = 0;
				mCount.setText(Integer.toString(count));
			}
 
		});

	}
	
	@Override
	public void onDestroy() {

	    mPlayer.stop();
	    super.onDestroy();

	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		  super.onSaveInstanceState(savedInstanceState);
		  savedInstanceState.putInt("count", count);
		  savedInstanceState.putBoolean("usingrepcounter", usingRepCounter);
		}
	
	public void EndWorkout(View V) {
		Intent intent = new Intent(this, MainMenu.class);
		startActivity(intent);
	}
	
	public void NextSet(View V) {
		
		//do something to store the current number in the EditText
		//box in the data structure
		SharedPreferences settings = getSharedPreferences("log", 0);
		SharedPreferences.Editor editor = settings.edit();
		String key = date + exer;
		System.out.println(key);
		editor.putInt(key, count);
		
		editor.commit();
		
		count = 0;
		mCount.setText(Integer.toString(count));
		
	}
	
	public void NextExercise(View V) {
		
		Intent intent = new Intent(this, ExerciseList.class);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter, menu);
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
	
	@Override
	protected void onResume() {
		
		super.onResume();
		mPlayer = MediaPlayer.create(this, R.raw.pit);
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mPlayer.stop();
        mSensorManager.unregisterListener(this);
	}
	
	public void onToggleClicked(View view) {
	    // Is the toggle on?
	    boolean on = ((ToggleButton) view).isChecked();
	    
	    if (on) {
	        usingRepCounter = true;
	    } else {
	    	usingRepCounter = false;
	    }
	}
	
	public void onMotivationClicked(View view) {
	    // Is the toggle on?
	    boolean on = ((ToggleButton) view).isChecked();
	    
	    if (on) {
	    	try{
	    		mPlayer.start();
	    	}catch(Exception e){
                e.printStackTrace();
            }
	    } else {
	    	mPlayer.pause();
	    }
	}
	
	
	public void repCounter(float dirAccel){
		//if acceleration exceeds threshold
		if(Math.abs((double)dirAccel) > thresh){
			//find + or - direction of first motion
			if(dirAccel < 0 && !firstSet){
				firstNeg = true;
				firstSet = true;
			}else if(dirAccel > 0  && !firstSet){
				firstPos = true;
				firstSet = true;
			}
			
			//for when motion starts moving on opposite direction
			if(firstPos && dirAccel < 0){
				firstDone = true;
			}else if(firstNeg && dirAccel > 0){
				firstDone = true;
			}
			
			//completes 1 rep
			//resets everything for next rep
			if(firstDone){
				if(firstPos && dirAccel > 0){
					count++;
					mCount.setText(Integer.toString(count));
					firstPos = false;
					firstSet = false;
					firstDone = false;
					directionSet = false;
					usingX = false;
					usingY = false;
					usingZ = false;
				}else if (firstNeg && dirAccel < 0){
					count++;
					mCount.setText(Integer.toString(count));
					firstNeg = false;
					firstSet = false;
					firstDone = false;
					directionSet = false;
					usingX = false;
					usingY = false;
					usingZ = false;
				}
			}
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if (!mInitialized) {
			mLastX = x;
			mLastY = y;
			mLastZ = z;
 
			mInitialized = true;
			mAll = (float) Math.sqrt((x * x) + (y * y) + (z * z));
			mInitialized = true; //now everything is initialized
		} else {
			float deltaX = Math.abs(mLastX - x);
			float deltaY = Math.abs(mLastY - y);
			float deltaZ = Math.abs(mLastZ - z);
			if (deltaX < NOISE) deltaX = (float)0.0;
			if (deltaY < NOISE) deltaY = (float)0.0;
			if (deltaZ < NOISE) deltaZ = (float)0.0;
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			
			//print out acceleration
//			EditText acc = (EditText) findViewById(R.id.accel);
//			mAll = (float) Math.sqrt((x * x) + (y * y) + (z * z));
//    		acc.setText(Double.toString((double)Math.round(x * 10) / 10));
    		
    		//find which direction is undergoing the most acceleration
    		if (!directionSet && usingRepCounter ) {
    			if (x > y && x > z && (Math.abs((double)x) > thresh)) {
    				usingX = true;
    				directionSet = true;
    			} else if (y > x && y > z && (Math.abs((double)y) > thresh)) {
    				usingY = true;
    				directionSet = true;
    			} else if (z > x && z > y && (Math.abs((double)z) > thresh)) {
    				usingZ = true;
					directionSet = true;
				}
			} else {
				if (usingX) {
					repCounter(x);
				} else if (usingY) {
					repCounter(y);
				} else if (usingZ) {
					repCounter(z);
				}
			}

		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}



}
