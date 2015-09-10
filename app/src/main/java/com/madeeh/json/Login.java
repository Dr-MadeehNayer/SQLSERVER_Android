package com.madeeh.json;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;


public class Login extends ActionBarActivity {

    private ProgressDialog pDialog;
    Handler handler;
    final String url="http://restapi.somee.com/connect.aspx?query=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handler=new Handler();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        final EditText ctrl_username=(EditText)findViewById(R.id.et_username);
        final EditText ctrl_pwd=(EditText)findViewById(R.id.et_pwd);



        Button btn=(Button)findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpDialog();

                final String username=ctrl_username.getText().toString();
                final String password=ctrl_pwd.getText().toString();

                Authorize(username,password);

            }
        });
    }

    void Authorize(String username,String password){
        try {
//            String param = URLEncoder.encode("select * from userdetails where username='"+ username +"' and password='"+ password + "'", "UTF-8");
//            Process(url + param, true);

            Process("http://restapi.somee.com/login.aspx?user="+ username + "&pwd="+ password,true);
        }catch(Exception Ex){

        }

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

                            hidepDialog();

                            check(list);

                        }
                    });
                }
                catch(Exception EX){

                }
            }
        });

        thread.start();

    }

    private UserDetails[] executeQuery(String jsonRespone){
        UserDetails[] list=JsonParser.getUsers(jsonRespone);
        return list;
    }

    private void check(UserDetails[] list){
       if(list.length>0){
           Intent intent=new Intent(this,MainActivity.class);
           startActivity(intent);
       }
       else{
           Toast.makeText(getApplicationContext(),"Wrong username or password",Toast.LENGTH_SHORT).show();
       }
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



}
