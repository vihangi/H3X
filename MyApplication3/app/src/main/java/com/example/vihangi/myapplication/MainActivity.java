package com.example.vihangi.myapplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button button2 ;
    private Button contacts ;
    private TextView number;
    private TextView listcontact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        button2 = (Button)findViewById(R.id.button2);
        contacts = (Button) findViewById(R.id.contact);
        number =(TextView)findViewById(R.id.number);
        listcontact =(TextView)findViewById(R.id.listcontact);



        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadcontacts();
            }
        });

    }

    private void loadcontacts(){

        StringBuilder builder= new StringBuilder();
        ContentResolver contentResolver= getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null,null, null);
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){


            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            int hasPhonenumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));


            if(hasPhonenumber>0){
                Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=? ",
                        new String[]{id},null);

                while(cursor2.moveToNext()){
                    String phonenumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    builder.append("Contact: ").append(name).append(". Phone Number : ").append(phonenumber).append("\n\n");
                }

                cursor2.close();
            }
            }

        }

        cursor.close();
        listcontact.setText(builder.toString() );
    }






}
