package com.example.lttbalance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
//import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	public static final String JSESSIONID="jesscook";
	public static final String BIGipServerecare_pool="bigpolcook";
	public static final String SAVEDATA="saveinfo";
	
	
	public static EditText CheckNum ;
	public static EditText PinNum ;
	public static EditText PassWd ;
	public static EditText UserNms ;
    public static TextView balance;
    public static Button Loginbtn;
	public static ImageButton loginbtnre;


	//private SharedPreferences cookies;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //cookies = getPreferences(MODE_PRIVATE);
        balance = (TextView) findViewById(R.id.textView1);
        DownloadImage DownloadingImage = new DownloadImage();
        DownloadingImage.execute();
        
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
	
	@Override
	protected void onStop() {
		LogoutLtt LogoutJSOUP = new LogoutLtt();
		LogoutJSOUP.execute();
		
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        	
        }
       
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    
    private class DownloadImage extends AsyncTask<Connection.Response, Connection.Response, Connection.Response> {

        @Override
        protected void onPreExecute() {
            Log.v("JSOUP", "run onPre");
            Toast.makeText(getBaseContext(), "Loading...", Toast.LENGTH_SHORT).show();
        }

		@Override
		protected Connection.Response doInBackground(Connection.Response... arg0) {
			// TODO Auto-generated method stub

            Log.v("JSOUP", "run doInBack");
            Connection.Response responsez = null;
			try {
				            responsez = Jsoup.connect("https://clientstatus.ltt.ly/nser/verifycode")
			                .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
			                           "Chrome/32.0.1667.0 Safari/537.36")
			                .ignoreContentType(true)
			                .method(Connection.Method.GET)
			                .timeout(5*1000)
			                .execute();

                publishProgress(responsez);
                              
                if (responsez.cookie("JSESSIONID_NSER")== null){
                	makeFile("JSESSIONID", responsez.cookie("JSESSIONID").toString());
                }else {
                	makeFile("JSESSIONID", responsez.cookie("JSESSIONID_NSER").toString());
                }
                
                makeFile("BIGipServerecare_pool", responsez.cookie("BIGipServerecare_pool").toString());
                               
                return responsez;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            publishProgress(responsez);
            return null;
		}


        protected void onProgressUpdate(Connection.Response... values) {
        	if(values[0].statusCode() == 200){        	
            Toast.makeText(getBaseContext(), "Loaded..."+values[0].statusCode()+"%", Toast.LENGTH_SHORT).show();
        	}
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
				balance = (TextView) findViewById(R.id.textView1);
				balance.setText(result.cookies().toString());
				ImageButton imangenum = (ImageButton) findViewById(R.id.imageButton1);
				Bitmap bitmap = BitmapFactory.decodeByteArray(result.bodyAsBytes(), 0, result.bodyAsBytes().length);
				imangenum.setImageBitmap(bitmap);
				
				
			}
        }
    }
    
    
    private class LoginLtt extends AsyncTask<Connection.Response, Connection.Response, Connection.Response> {

        @Override
        protected void onPreExecute() {
            Log.v("JSOUP", "run onPre");
            Toast.makeText(getBaseContext(), "Loging in...", Toast.LENGTH_SHORT).show();
                       
        }

		@Override
		protected Connection.Response doInBackground(Connection.Response... arg0) {
			// TODO Auto-generated method stub
			Log.v("JSOUP", "run doInBackground");
			EditText CheckNum = (EditText) findViewById(R.id.no_code);
			String checknum = CheckNum.getText().toString();
			EditText PinNum = (EditText) findViewById(R.id.pinz);
			String Pinnum = PinNum.getText().toString();
			EditText PassWd = (EditText) findViewById(R.id.password);
			String Passwd = PassWd.getText().toString();
			EditText UserNms = (EditText) findViewById(R.id.username);
			String Usernms = UserNms.getText().toString();
				

			    FileInputStream fis1 = null;
			    try {
					fis1 = openFileInput("JSESSIONID");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    StringBuffer fileContent1 = new StringBuffer("");

			    byte[] buffer1 = new byte[2];

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
					fis2 = openFileInput("BIGipServerecare_pool");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    StringBuffer fileContent2 = new StringBuffer("");

			    byte[] buffer2 = new byte[2];

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
		                .data("ecid",Usernms)
		                .data("domain","ltt.ly")
		                .data("password",Passwd)
		                .data("pinPassword",Pinnum)
		                .data("checkNum",checknum)
		                .cookie("BIGipServerecare_pool",BIGipServerecare_pool.toString())
                        .cookie("JSESSIONID_NSER",JSESSIONID_NSER.toString())
		                //.cookies(loginCookies)
		                .method(Connection.Method.POST)
		                //.followRedirects(true)
		                .timeout(5*1000)
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
			Log.v("JSOUP", "run onProgressUpdate");
        	if(values == null){        	
            Toast.makeText(getBaseContext(), " Try Refresh the Picture ", Toast.LENGTH_SHORT).show();}
        	else{
        		Toast.makeText(getBaseContext(), "Logged in", Toast.LENGTH_SHORT).show();       		
        	}
        }

		@Override
        protected void onPostExecute(Connection.Response result)  {
            
			if (result == null){
				Toast.makeText(getBaseContext(), "No Connection", Toast.LENGTH_SHORT).show();
				TextView balance = (TextView) findViewById(R.id.textView1);
	        	balance.setText("Refresh Image");
               }
			else {
				 
				try {
					Elements lol = result.parse().select("tr:eq(3)");
					Elements lool = result.parse().select("tr:eq(11)");
			        Elements loool = result.parse().select("tr:eq(12)");
			        Elements looool = result.parse().select("tr:eq(9)");
					TextView balance = (TextView) findViewById(R.id.textView1);
					balance.setText(lol.text()+"\n"+lool.text()+"\n"+loool.text()+"\n"+looool.text());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        }
    }
    
    
    private class LogoutLtt extends AsyncTask<Connection.Response, Connection.Response, Connection.Response> {

        @Override
        protected void onPreExecute() {
            Log.v("JSOUP", "run onPre");
            Toast.makeText(getBaseContext(), "LogOut...", Toast.LENGTH_SHORT).show();
                       
        }

		@Override
		protected Connection.Response doInBackground(Connection.Response... arg0) {
			// TODO Auto-generated method stub
			Log.v("JSOUP", "run doInBackground");
			    FileInputStream fis1 = null;
			    try {
					fis1 = openFileInput("JSESSIONID");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    StringBuffer fileContent1 = new StringBuffer("");

			    byte[] buffer1 = new byte[2];

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
					fis2 = openFileInput("BIGipServerecare_pool");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    StringBuffer fileContent2 = new StringBuffer("");

			    byte[] buffer2 = new byte[2];

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
				Connection.Response logoutForm;
				logoutForm = Jsoup.connect("https://clientstatus.ltt.ly/nser/OV/OVLogoutAction.do")
		                .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
		                           "Chrome/32.0.1667.0 Safari/537.36")
		                .cookie("BIGipServerecare_pool",BIGipServerecare_pool.toString())
                        .cookie("JSESSIONID_NSER",JSESSIONID_NSER.toString())
		                //.cookies(loginCookies)
		                .method(Connection.Method.POST)
		                //.followRedirects(true)
		                .timeout(5*1000)
		                .execute();

                publishProgress(logoutForm);
				return logoutForm;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //publishProgress(BIGipServerecare_pool);
			return null;
      	}

		@Override
        protected void onProgressUpdate(Connection.Response... values) {
			Log.v("JSOUP", "run onProgressUpdate");
        	if(values == null){        	
            Toast.makeText(getBaseContext(), " LogOut Error", Toast.LENGTH_SHORT).show();}
        	else{
        		Toast.makeText(getBaseContext(), "LogOut Okay", Toast.LENGTH_SHORT).show();       		
        	}
        }

		@Override
        protected void onPostExecute(Connection.Response result)  {
            
			if (result == null){
	            Toast.makeText(getBaseContext(), " LogOut Error", Toast.LENGTH_SHORT).show();
               }
			else{
				Toast.makeText(getBaseContext(), result.statusCode(), Toast.LENGTH_SHORT).show();
			}
    	}
	}
    
    
    public void makeFile(String... files) throws IOException{
    	
    	OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(files[0], Context.MODE_PRIVATE));
        osw.write(files[1]);
        osw.flush();
        osw.close();
   	
    }

    public void reloadImageButtonClicked(View v)
    {
    	DownloadImage DownloadingImage = new DownloadImage();
        DownloadingImage.execute();
    }
    
    public void loginButtonClicked(View v)
    {
    	LoginLtt LoginJSOUP = new LoginLtt();
		LoginJSOUP.execute();
    }
    
}
