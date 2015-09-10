package com.madeeh.json;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;


public class CustomListAdapter extends ArrayAdapter<UserDetails> {

    UserDetails[] users;
    ImageView img;
    Bitmap bitmap;

    public CustomListAdapter(Context context, UserDetails[] users) {
        super(context,R.layout.customrow , users);
        this.users=users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());

        View view=inflater.inflate(R.layout.customrow,parent,false);

        UserDetails item = users[position];

        TextView tv=(TextView)view.findViewById(R.id.tv_custom);
        img=(ImageView)view.findViewById(R.id.img_custom);

        tv.setText(item.getFirstName() + "-" + item.getLastName());

        try {

                new LoadImage().execute("http://findicons.com/files/icons/2770/ios_7_icons/128/student.png");

        }
        catch(Exception ex){}

        return view;
    }

    private class LoadImage extends AsyncTask<String,String,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(strings[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                img.setImageBitmap(bitmap);
            }
        }
    }

}
