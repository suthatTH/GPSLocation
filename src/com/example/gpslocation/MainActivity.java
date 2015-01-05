package com.example.gpslocation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Address;
import android.location.Geocoder;


public class MainActivity extends Activity //implements OnClickListener
{
	LocationManager mlm;
	Location lo ;
	TextView gps;
	TextView addressBox;
	Button submit;
	String lati;
	String longti;
	EditText laIn;
	EditText longIn;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		showGPS();
		showAddress();
		submit = (Button) findViewById(R.id.button1);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
				laIn = (EditText)findViewById(R.id.latiIn);
				longIn = (EditText)findViewById(R.id.longtiIn);
				showAddress(laIn.getText().toString(), longIn.getText().toString());
				
				gps = (TextView)findViewById(R.id.textView1);
				
				
			}
		});
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
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
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showGPS();
		//showAddress();
	}



//  my method
	private void showGPS(){
		gps = (TextView)findViewById(R.id.textView1);
		mlm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		Criteria cr = new Criteria();
		cr.setAccuracy(Criteria.ACCURACY_FINE);
		cr.setPowerRequirement(Criteria.POWER_LOW);
		String locaprovide = mlm.getBestProvider(cr, true);
		lo = mlm.getLastKnownLocation(locaprovide);
		String gpsValue = ""+lo.getLatitude()+",\n"+lo.getLongitude();
		gps.setText(gpsValue);
	}
	
	private void showAddress(){
		addressBox = (TextView)findViewById(R.id.addr);
		List<Address> address;
		try{
			Geocoder gc = new Geocoder(this, Locale.ENGLISH);
			address = gc.getFromLocation(lo.getLatitude(), lo.getLongitude(), 1);
			if(address != null){
				Address currentAddr = address.get(0);
				StringBuilder sb = new StringBuilder("Address:\n");
				for(int i = 0;i < currentAddr.getMaxAddressLineIndex();i++){
					sb.append(currentAddr.getAddressLine(i)).append("\n");
				}
				addressBox.setText(sb.toString());
			}
		}catch(IOException e){
			addressBox.setText(e.getMessage());
		}
	}
	
	private void showAddress(String latitude, String longtitude){
		double la = Double.parseDouble(latitude);
		double longti = Double.parseDouble(longtitude);
		addressBox = (TextView)findViewById(R.id.addr);
		List<Address> address;
		try{
			Geocoder gc = new Geocoder(this, Locale.ENGLISH);
			address = gc.getFromLocation(la, longti, 1);
			if(address != null){
				Address currentAddr = address.get(0);
				StringBuilder sb = new StringBuilder("Address:\n");
				for(int i = 0;i < currentAddr.getMaxAddressLineIndex();i++){
					sb.append(currentAddr.getAddressLine(i)).append("\n");
				}
				addressBox.setText(sb.toString());
			}
		}catch(IOException e){
			addressBox.setText(e.getMessage());
		}
	}

	
	
	
}
