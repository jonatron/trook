package com.example.testy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import com.example.testy.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	int i = 0;
	TextView text1;
	String resp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text1 = (TextView)findViewById(R.id.text1);
		text1.setText("hi");

		Timer timer = new Timer();
		
		final long MINS = 1000 * 60;
		TimerTask task;
		
		
		task = new TimerTask() {
			@Override
			public void run() {				
				i++;
		        Log.w("hi", "i");
		        Log.w("hi", String.valueOf(i));
		        URL url = null;
				try {
					url = new URL(String.format("http://192.168.178.21:8888"));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        
				InputStream stream = null;
		        try {
					stream = url.openStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}

		        resp = readStream(stream);
		        
				MainActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						resp = resp.replace("<br>", System.getProperty("line.separator"));
						text1.setText(resp);
					}
	            	
	            });
		        
			}
		};
		Log.w("hi", "test");
		timer.schedule(task, 0, MINS * 5);
	}
	


	private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }



}

