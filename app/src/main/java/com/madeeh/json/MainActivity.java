package com.madeeh.json;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.transform.sax.TemplatesHandler;


public class MainActivity extends ActionBarActivity {

    private ProgressDialog pDialog;
    Handler handler;
    ListView listView;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler=new Handler();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        btn=(Button)findViewById(R.id.btn_save);

        showpDialog();

        String url="http://restapi.somee.com/connect.aspx?query=";

        //delete a row
        //Process("http://restapi.somee.com/connect.aspx?query=delete%20from%20userdetails%20where%20username%3D%27hh%27",false);

        //insert a new row
        //Process("http://restapi.somee.com/connect.aspx?query=insert%20into%20userdetails(username%2Cpassword%2Cfirstname%2Clastname)%20values(%27hh%27%2C%27hh%27%2C%27hh%27%2C%27hh%27)",false);


        //get all rows in userdetails table
        try {
            String param = URLEncoder.encode("select * from userdetails", "UTF-8");
            Process(url + param, true);
        }catch(Exception Ex){}

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> selectedItems=getSelectedItems(listView);
                Toast.makeText(getBaseContext(), selectedItems+"", Toast.LENGTH_SHORT).show();
            }

        });

       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/



    }

    private void Process(final String TheUrl, final boolean IsShow){


        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // String url="http://restapi.somee.com/connect.aspx?query=SELECT * FROM UserDetails";
                    String url= TheUrl;

                    final String response=JsonParser.getJSON(url,10000);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            UserDetails[] list= executeQuery(response);

                            if(IsShow) {
                                hidepDialog();
                                showData(list);
                            }
                        }
                    });
                }
                catch(Exception EX){
                }
            }
        });

        thread.start();

    }

    private void showpDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private UserDetails[] executeQuery(String jsonRespone){
        UserDetails[] list=JsonParser.getUsers(jsonRespone);
        return list;
    }

    private void showData(UserDetails[] list){
        String[] values = new String[list.length];

        for(int i=0;i<list.length;i++){
            UserDetails user= list[i];
            values[i]=user.getUserName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, values);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }

    private ArrayList<String> getSelectedItems(ListView lst){
        ArrayList<String> selectedChildren = new ArrayList<String>();

        for(int i = 0;i<lst.getChildCount();i++)
        {
            CheckedTextView c = (CheckedTextView) lst.getChildAt(i);
            if(c.isChecked())
            {
                String child = c.getText().toString();
                selectedChildren.add(child);
            }
        }

        return selectedChildren;
    }


}
