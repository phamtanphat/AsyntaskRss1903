package com.ptp.phamtanphat.asyntaskrss1903;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    Button btnDocRss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDocRss = findViewById(R.id.buttonRss);
        btnDocRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Class vo danh
                new DocNoiDungInternetCuaUrl().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
            }
        });
    }
    class DocNoiDungInternetCuaUrl extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser xmldomParser = new XMLDOMParser();
            Document document = xmldomParser.getDocument(s);
            NodeList nodeListItem = document.getElementsByTagName("item");
            String title = "";

            for (int i = 0 ; i <nodeListItem.getLength() ; i++){
                Element element = (Element) nodeListItem.item(i);
                title += xmldomParser.getValue(element,"title") + "/n";
            }

            Log.d("BBB",title);
            super.onPostExecute(s);
        }

        private String docNoiDung_Tu_URL(String theUrl){
            StringBuilder content = new StringBuilder();
            try    {
                // create a url object
                URL url = new URL(theUrl);

                // create a urlconnection object
                URLConnection urlConnection = url.openConnection();

                // wrap the urlconnection in a bufferedreader
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;

                // read from the urlconnection via the bufferedreader
                while ((line = bufferedReader.readLine()) != null){
                    content.append(line + "\n");
                }
                bufferedReader.close();
            }
            catch(Exception e)    {
                e.printStackTrace();
            }
            return content.toString();
        }
    }

}
