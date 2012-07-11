package com.example.sr_adrobot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

public class UIScreen extends FragmentActivity 
{
    //SectionsPagerAdapter mSectionsPagerAdapter;
    //ViewPager mViewPager;
	public static final String PREFS_NAME = "SR-AdRobot";
	
    private PagerAdapter mPagerAdapter;
    private PopupWindow popUp;
    private String remoteAddr;
    private String[] SerialData;
    private String[] SoundData;
    private View layout;
    
    Frag1 frag1;
    Frag2 frag2;
    Frag3 frag3;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		if (android.os.Build.VERSION.SDK_INT > 9) 
		{
		      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		      StrictMode.setThreadPolicy(policy);
		}
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		remoteAddr = settings.getString("remoteAddr", "http://1.23.205.210/~sramanujan/Serial/");
		SerialData = new String[48];
		SoundData = new String[48];
		frag1 = new Frag1();
		frag2 = new Frag2();
		frag3 = new Frag3();
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_uiscreen);
		this.initialisePaging();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	menu.add(1,1,Menu.FIRST,"Change Server IP");
    	menu.add(1,2,Menu.FIRST+1,"Update Buttons");
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
		switch(item.getItemId())
		{
			case 1:
		        LayoutInflater inflater = (LayoutInflater) UIScreen.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        layout = inflater.inflate(R.layout.layout_settings,(ViewGroup) findViewById(R.id.settingslayout));
		        ((EditText)layout.findViewById(R.id.iptext)).setText(remoteAddr);
		        popUp = new PopupWindow(layout, 550, 280, true);
		        popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
		        Button saveButton = (Button) layout.findViewById(R.id.savebtn);
		        saveButton.setOnClickListener(new OnClickListener() 
		        {
		            public void onClick(View v) 
		            {
		            	remoteAddr = ((EditText) layout.findViewById(R.id.iptext)).getText().toString();
		                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		                SharedPreferences.Editor editor = settings.edit();
		                editor.putString("remoteAddr", remoteAddr);
		                editor.commit();
		        		popUp.dismiss();
		            }
		        });
			return true;
			case 2:
				try 
				{  
				    URL url = new URL(remoteAddr+"buttons.xml");
		    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    		DocumentBuilder db = dbf.newDocumentBuilder();
		    		Document doc = db.parse(new InputSource(url.openStream()));
		    		doc.getDocumentElement().normalize();

		    		NodeList nodeList = doc.getElementsByTagName("button");
				    int i,j;
				    
				    for(j=0;j<48;j++)
				    {
				    	SerialData[j]=((Element)nodeList.item(j)).getAttribute("string");
				    	SoundData[j]=((Element)nodeList.item(j)).getAttribute("sound");
				    }
				    
				    j=0;
				    for(i=0;i<16;i++)
				    	((Button)frag1.layout1.findViewById(R.id.b000+i)).setText(((Element)nodeList.item(j++)).getAttribute("name"));
				    for(i=0;i<16;i++)
				    	((Button)frag2.layout2.findViewById(R.id.b100+i)).setText(((Element)nodeList.item(j++)).getAttribute("name"));
				    for(i=0;i<16;i++)
				    	((Button)frag3.layout3.findViewById(R.id.b200+i)).setText(((Element)nodeList.item(j++)).getAttribute("name"));
				    
				} 
				catch (MalformedURLException e) 
				{
					Toast.makeText(this, "Pleaes check the URL -> "+remoteAddr, Toast.LENGTH_LONG).show();
				} 
				catch (IOException e) 
				{
					Toast.makeText(this, "File buttons.xml not accessible at "+remoteAddr, Toast.LENGTH_LONG).show();
				} 
				catch (SAXException e) 
				{
					Toast.makeText(this, "The xml at "+remoteAddr+"buttons.xml is not well formed!", Toast.LENGTH_LONG).show();
				} 
				catch (ParserConfigurationException e) 
				{
					Toast.makeText(this, "Remote address44 is: "+remoteAddr, Toast.LENGTH_SHORT).show();
				}


			return true;
		}
		return super.onOptionsItemSelected(item);
    }

	private void initialisePaging() 
	{
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(frag1);
		fragments.add(frag2);
		fragments.add(frag3);
		this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
		ViewPager pager = (ViewPager)super.findViewById(R.id.pager);
		pager.setAdapter(this.mPagerAdapter);
		pager.setOffscreenPageLimit(2);
	}
	
	public class PagerAdapter extends FragmentPagerAdapter 
	{
		private List<Fragment> fragments;
		public PagerAdapter(FragmentManager fm, List<Fragment> fragments) 
		{
			super(fm);
			this.fragments = fragments;
		}
		@Override
		public Fragment getItem(int position) 
		{
			return this.fragments.get(position);
		}
		@Override
		public int getCount() 
		{
			return this.fragments.size();
		}
	}
	public void onClick(View v) throws ClientProtocolException, IOException 
	{
		
		HttpClient client = new DefaultHttpClient(); 
        HttpPost post = new HttpPost(remoteAddr+"landingPage.php"); 
        List<NameValuePair> nvps = new ArrayList<NameValuePair>(2); 
        nvps.add(new BasicNameValuePair("SerialData", SerialData[v.getId()-R.id.b000])); 
        nvps.add(new BasicNameValuePair("SoundData", SoundData[v.getId()-R.id.b000])); 
        post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); 
        client.execute(post); 
		Toast.makeText(this, "Done! "+remoteAddr+": Ready!!", Toast.LENGTH_SHORT).show();
	}	

}
