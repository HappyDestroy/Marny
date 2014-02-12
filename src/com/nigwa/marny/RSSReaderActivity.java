package com.nigwa.marny;
 
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 




import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
 




import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
 




import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
 
public class RSSReaderActivity extends ListActivity {
	
	private ArrayList<RSSItem> itemlist = null;
	private RSSListAdaptor rssAdaptor = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rssreader);
		
	    itemlist = new ArrayList<RSSItem>();
	    
	    new RetrieveRSSFeeds().execute();
	}
	
	@Override
	    protected void onListItemClick(ListView l, View v, int position, 
	    		long id) {
				
				super.onListItemClick(l, v, position, id);
				
				RSSItem data = itemlist.get(position);
				
				Intent intentLien = new Intent(
						Intent.ACTION_VIEW,Uri.parse(data.link));
				
				startActivity(intentLien);
	    }
	
	private void retrieveRSSFeed(String urlToRssFeed,ArrayList<RSSItem> list)
	{
		try
		{
			URL url = new URL(urlToRssFeed);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader xmlreader = parser.getXMLReader();
			RSSParser theRssHandler = new RSSParser(list);
			
			xmlreader.setContentHandler(theRssHandler);
			
			InputSource is = new InputSource(url.openStream());
			
			xmlreader.parse(is);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private class RetrieveRSSFeeds extends AsyncTask<Void, Void, Void>
	{
		private ProgressDialog progress = null;
		
		@Override
		protected Void doInBackground(Void... params) {
			retrieveRSSFeed("http://portesmonstrestresors.fr/feed/", 
                		itemlist);
			
			rssAdaptor = new RSSListAdaptor(RSSReaderActivity.this, 
                		R.layout.rssitemview, itemlist);
			
			return null;
        }
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
		
        @Override
        protected void onPreExecute() {
        	progress = ProgressDialog.show(
        			RSSReaderActivity.this, null, 
        			"Chargement du flux RSS...");
        	
        	super.onPreExecute();
        }
        
        @Override
        protected void onPostExecute(Void result) {
        	setListAdapter(rssAdaptor);
        	
        	progress.dismiss();
        	
        	super.onPostExecute(result);
        }
        
        @Override
        protected void onProgressUpdate(Void... values) {
        	super.onProgressUpdate(values);
        }
	}
	
	private class RSSListAdaptor extends ArrayAdapter<RSSItem>{
		private List<RSSItem> objects = null;
		
		public RSSListAdaptor(Context context, int textviewid, 
				List<RSSItem> objects) {
			super(context, textviewid, objects);
			
			this.objects = objects;
		}
		
		@Override
		public int getCount() {
			return ((null != objects) ? objects.size() : 0);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public RSSItem getItem(int position) {
			return ((null != objects) ? objects.get(position) : null);
		}
		
		public View getView(int position, View convertView, 
				ViewGroup parent) {
			
			View view = convertView;
			
			if(null == view)
			{
				LayoutInflater vi = 
						(LayoutInflater)RSSReaderActivity
						.this.getSystemService(
								Context.LAYOUT_INFLATER_SERVICE);
				
				view = vi.inflate(R.layout.rssitemview, null);
			}
			
			RSSItem data = objects.get(position);
			
			if(null != data)
			{
				TextView title = (TextView)view.findViewById(
						R.id.txtTitle);
				TextView date = (TextView)view.findViewById(
						R.id.txtDate);
				TextView description = (TextView)view.findViewById(
						R.id.txtDescription);
				
				title.setText(data.title);
				
				/*SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				Date myDate = format.parse(data.date);
				date.setText("le " + dateFormat.format(myDate));*/
				description.setText(data.description);
			}
			
			return view;
		}
	}
}