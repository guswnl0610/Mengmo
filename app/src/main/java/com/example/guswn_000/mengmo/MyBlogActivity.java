package com.example.guswn_000.mengmo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.os.Handler;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MyBlogActivity extends AppCompatActivity
{
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> datas = new ArrayList<>();
    ImageButton imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blog);
        setTitle("개발자 블로그");
        listView = (ListView)findViewById(R.id.bloglistview);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);
        imageView = (ImageButton) findViewById(R.id.blogimage);
    }

    Handler handler = new Handler();
    Thread thread = new Thread()
    {
        @Override
        public void run()
        {
            super.run();
            try {
                URL url = new URL("http://mengkkimon.tistory.com/rss");
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    int itemCount = readData(urlConnection.getInputStream());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    urlConnection.disconnect();
                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public int readData (InputStream is)
    {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(is);
            int datacount = parseDocument(document);
            return datacount;
        }

        catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int parseDocument(Document doc)
    {
        Element docEle = doc.getDocumentElement();
        NodeList nodelist = docEle.getElementsByTagName("item");
        int count = 0;
        if((nodelist != null) && (nodelist.getLength() > 0))
        {
            for (int i = 0; i< nodelist.getLength() ; i++)
            {
                String newsItem = getTagData(nodelist,i);
                if(newsItem != null)
                {
                    datas.add(newsItem);
                    count++;
                }
            }
        }
        return count;
    }

    public String getTagData(NodeList nodelist, int index)
    {
        String newsItem = null;

        try
        {
            Element entry = (Element)nodelist.item(index);
            Element title = (Element)entry.getElementsByTagName("title").item(0);
            Element pubDate = (Element)entry.getElementsByTagName("pubDate").item(0);

            String titleValue = null;
            String pubDateValue = null;
            if(title != null)
            {
                Node firstChild = title.getFirstChild();
                if(firstChild != null) titleValue = firstChild.getNodeValue();
            }
            if(pubDate != null)
            {
//                Node firstChild = pubDate.getFirstChild();
//                if(firstChild != null) pubDateValue = firstChild.getNodeValue();

                Node firstChild2 = pubDate.getFirstChild();
                if(firstChild2 != null) pubDateValue = firstChild2.getNodeValue();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MM/dd");
            Date date = new Date();
            newsItem = titleValue + "   " + simpleDateFormat.format(date.parse(pubDateValue));
        }
        catch (DOMException e)
        {
            e.printStackTrace();
        }
        return newsItem;
    }

    @Override
    public void onBackPressed() {
        thread.interrupt();
        imageView.setImageResource(R.drawable.blog1);
        finish();
    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.blogimage)
        {
            imageView.setImageResource(R.drawable.blog2);
            thread.start();
        }
    }

}
