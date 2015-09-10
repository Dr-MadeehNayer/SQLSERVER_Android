package com.madeeh.json;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.net.URLEncoder;


public class CustomList extends ActionBarActivity {

    ProgressDialog pDialog;
    Handler handler;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);

        handler=new Handler();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        listView = (ListView) findViewById(R.id.list);

        showpDialog();

        String url="http://restapi.somee.com/connect.aspx?query=";

        try {
            String param = URLEncoder.encode("select * from userdetails", "UTF-8");
            Process(url + param, true);
        }catch(Exception Ex){}

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
                    EX.printStackTrace();
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

    private void showData(UserDetails[] list){

        ListAdapter adapter=new CustomListAdapter(this,list);

        listView.setAdapter(adapter);
    }

    private UserDetails[] executeQuery(String jsonRespone){
        UserDetails[] list=JsonParser.getUsers(jsonRespone);
        return list;

    }


}
