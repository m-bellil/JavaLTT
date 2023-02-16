package ly.bellil.lttbalance;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.select.Elements;
import android.os.AsyncTask;
import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.Button;

public class MainActivity extends Activity {
	 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadImage DownloadingImage;
        DownloadingImage = new DownloadImage();
        DownloadingImage.execute();
        Toast.makeText(getBaseContext(), "Loading.  ", Toast.LENGTH_SHORT).show();
        
        Button loginbtn = (Button) findViewById(R.id.Button1);
        loginbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoginLtt LoginJSOUP;
				LoginJSOUP = new LoginLtt();
				LoginJSOUP.execute();
				
			}
		});
        
        ImageButton loginbtnre = (ImageButton) findViewById(R.id.ImageButton1);
        loginbtnre.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DownloadImage DownloadingImage;
		        DownloadingImage = new DownloadImage();
		        DownloadingImage.execute();
		        Toast.makeText(getBaseContext(), "Loading.  ", Toast.LENGTH_SHORT).show();
				
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private class DownloadImage extends AsyncTask<Connection.Response, Integer, Connection.Response> {

        @Override
        protected void onPreExecute() {
            Log.v("JSOUP", "run onPre");
            TextView balance = (TextView) findViewById(R.id.TextView1);
        	balance.setText("LOADING");
            Toast.makeText(getBaseContext(), "Loading . ", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "Loading  .", Toast.LENGTH_SHORT).show();
        }

		@Override
		protected Connection.Response doInBackground(Connection.Response... arg0) {
			// TODO Auto-generated method stub

            Log.v("JSOUP", "run doInBack");
			try {
				 Connection.Response responsez = Jsoup.connect("https://clientstatus.ltt.ly/nser/verifycode")
			                .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
			                           "Chrome/32.0.1667.0 Safari/537.36")
			                .ignoreContentType(true)
			                .method(Connection.Method.GET)
			                .timeout(5000)
			                .execute();

                publishProgress(100);
                              
                if (responsez.cookie("JSESSIONID_NSER")== null){                	
                	OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("JSESSIONID.txt", Context.MODE_PRIVATE));
                    osw.write(responsez.cookie("JSESSIONID"));
                    osw.flush();
                    osw.close();
                }else {                	                	
                	OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("JSESSIONID.txt", Context.MODE_PRIVATE));
                    osw.write(responsez.cookie("JSESSIONID_NSER"));
                    osw.flush();
                    osw.close();
                }
                
                OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("BIGipServerecare_pool.txt", Context.MODE_PRIVATE));
                osw.write(responsez.cookie("BIGipServerecare_pool"));
                osw.flush();
                osw.close();
                
                return responsez;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            publishProgress(0);
            return null;
		}


        protected void onProgressUpdate(Integer... values) {
        	if(values[0] == 100){        	
            Toast.makeText(getBaseContext(), "Loaded "+values[0]+"%", Toast.LENGTH_SHORT).show();}
        	else{
        		Toast.makeText(getBaseContext(), "Connection Bad", Toast.LENGTH_SHORT).show();       		
        	}
        }


        @Override
		protected void onPostExecute(Connection.Response result)  {
            
			if (result == null){
				Toast.makeText(getBaseContext(), "No Connection", Toast.LENGTH_SHORT).show();
                
               }
			else {
				TextView balance = (TextView) findViewById(R.id.TextView1);
				balance.setText("DONE");
				ImageButton imangenum = (ImageButton) findViewById(R.id.ImageButton1);
				Bitmap bitmap = BitmapFactory.decodeByteArray(result.bodyAsBytes(), 0, result.bodyAsBytes().length);
				imangenum.setImageBitmap(bitmap);
				
				
			}
        }
    }
        
    private class LoginLtt extends AsyncTask<Connection.Response, Connection.Response, Connection.Response> {

        @Override
        protected void onPreExecute() {
            Log.v("JSOUP", "run onPre");
            TextView balance = (TextView) findViewById(R.id.TextView1);
        	balance.setText("LOADING");
            Toast.makeText(getBaseContext(), "Loading . ", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "Loading  .", Toast.LENGTH_SHORT).show();
            
            
        }

		@Override
		protected Connection.Response doInBackground(Connection.Response... arg0) {
			// TODO Auto-generated method stub
			EditText CheckNum = (EditText) findViewById(R.id.edittext4);
			String checknum = CheckNum.getText().toString();
            
				
			//---------------------------------------------------------------------------------------------------
//				
//			    BufferedReader NSER = null;
//				try {
//					NSER = new BufferedReader(new InputStreamReader(openFileInput("JSESSIONID.txt")));
//				} catch (FileNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			    String inputString;
//			    StringBuffer stringBuffer = new StringBuffer();                
//			    try {
//					while ((inputString = NSER.readLine()) != null) {
//					    stringBuffer.append(inputString);}
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			    
//			    StringBuffer JSESSIONID_NSER = stringBuffer;
//			    
			  //---------------------------------------------------------------------------------------------------		    
			    
//			    
//			    BufferedReader BIGipS = null;
//				try {
//					BIGipS = new BufferedReader(new InputStreamReader(openFileInput("BIGipServerecare_pool.txt")));
//				} catch (FileNotFoundException e1) {
//					 TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			    String inputStringpool = null;
//			    StringBuffer stringBufferpool = new StringBuffer();                
//			    try {
//					while ((inputString = BIGipS.readLine()) != null) {
//					    stringBuffer.append(inputStringpool);}
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			    
//			    String BIGipServerecare_pool = stringBufferpool.toString();
//			    
			    //---------------------------------------------------------------------------------------------------
			    
			    FileInputStream fis1 = null;
			    try {
					fis1 = openFileInput("JSESSIONID.txt");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    StringBuffer fileContent1 = new StringBuffer("");

			    byte[] buffer1 = new byte[1024];

			    try {
					while (fis1.read(buffer1) != -1) {
					    fileContent1.append(new String(buffer1));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    String JSESSIONID_NSER = fileContent1.toString();
			    
			    //---------------------------------------------------------------------------------------------------
			    
			    FileInputStream fis2 = null;
			    try {
					fis2 = openFileInput("BIGipServerecare_pool.txt");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    StringBuffer fileContent2 = new StringBuffer("");

			    byte[] buffer2 = new byte[1024];

			    try {
					while (fis2.read(buffer2) != -1) {
					    fileContent2.append(new String(buffer2));
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    String BIGipServerecare_pool = fileContent2.toString();
			    
			    //---------------------------------------------------------------------------------------------------
		
			try {
				Connection.Response loginForm;
				loginForm = Jsoup.connect("https://clientstatus.ltt.ly/nser/login/loginToECAction.do?method=loginToEC")
		                .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
		                           "Chrome/32.0.1667.0 Safari/537.36")
		                .data("loginType", "uri")
		                .data("ecid","bellil2009")
		                .data("domain","ltt.ly")
		                .data("password","104205410")
		                .data("pinPassword","3570")
		                .data("checkNum",checknum)
		                .cookie("BIGipServerecare_pool",BIGipServerecare_pool.toString())
                        .cookie("JSESSIONID_NSER",JSESSIONID_NSER.toString())
		                //.cookies(loginCookies)
		                .method(Connection.Method.POST)
		                //.followRedirects(true)
		                .timeout(5000)
		                .execute();

                publishProgress(loginForm);
                return loginForm;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //publishProgress(BIGipServerecare_pool);
            return null;
		}

		@Override
        protected void onProgressUpdate(Connection.Response... values) {
        	if(values == null){        	
            Toast.makeText(getBaseContext(), " Try Refresh the Picture ", Toast.LENGTH_SHORT).show();}
        	else{
        		Toast.makeText(getBaseContext(), " Logged in ", Toast.LENGTH_SHORT).show();       		
        	}
        }

		@Override
        protected void onPostExecute(Connection.Response result)  {
            
			if (result == null){
				Toast.makeText(getBaseContext(), "No Connection", Toast.LENGTH_SHORT).show();
                
               }
			else {
				 
				try {
					Elements lol = result.parse().select("tr:eq(3)");
					Elements lool = result.parse().select("tr:eq(11)");
			        Elements loool = result.parse().select("tr:eq(12)");
					TextView balance = (TextView) findViewById(R.id.TextView1);
					balance.setText(lol.text()+"\n"+lool.text()+"\n"+loool.text());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
				//ImageButton imangenum = (ImageButton) findViewById(R.id.ImageButton1);
				//Bitmap bitmap = BitmapFactory.decodeByteArray(result.bodyAsBytes(), 0, result.bodyAsBytes().length);
				//imangenum.setImageBitmap(bitmap);
				
				
			}
        }
    }
}
/* 

package com.wolfapps.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Connection.Response responsez = Jsoup.connect("https://www.giga.ly/portal/")
                .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/32.0.1667.0 Safari/537.36")
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .timeout(5000)
                .execute();
        OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("ci_session.txt", Context.MODE_PRIVATE));
        osw.write(responsez.cookie("ci_session"));
        osw.flush();
        osw.close();
    }


    Connection.Response loginForm;
    loginForm = Jsoup.connect("https://clientstatus.ltt.ly/nser/login/loginToECAction.do?method=loginToEC")
            .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                               "Chrome/32.0.1667.0 Safari/537.36")
		                .data("username","bellil2009")
		                .data("password","104205410")
		                .cookie("BIGipServerecare_pool",BIGipServerecare_pool.toString())
            .cookie("ci_session",JSESSIONID_NSER.toString())
            //.cookies(loginCookies)
            .method(Connection.Method.POST)
    //.followRedirects(true)
		                .timeout(5000)
		                .execute();
}

*/