package com.example.rory.smart_grid_v2;

//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//Developers. (N.D) Available from http://developer.android.com/training/basics/firstapp/starting-activity.html [Accessed 03/04/2015] Starting another activity: Intents
//Developers blog. July 2010 Available from http://android-developers.blogspot.co.uk/2010/07/multithreading-for-performance.html [Accessed 06/12/2014] Multi threading: Async class
//Developers. (N.D) Available from https://developer.android.com/training/basics/network-ops/connecting.html [Accessed 03/04/2015] Connecting to a network and Async class
//Developers. (N.D) http://developer.android.com/reference/android/os/AsyncTask.html [Accessed 03/04/2015] Async class
//apache. (N.D) Available from http://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientAuthentication.java [Accessed 22/11/2014] Network connection
//apache. (N.D) Available from http://commons.apache.org/proper/commons-net/examples/ftp/FTPClientExample.java [Accessed 22/11/2014] Network connection
//android hive. Jan 2012 Available from http://www.androidhive.info/2012/01/android-json-parsing-tutorial/ [Accessed 01/03/2015] JSON Parsing
//mkyong. August 2011 Available from http://www.mkyong.com/tutorials/java-json-tutorials/ [Accessed 02/03/2015] JSON Parsing
//w3schools. (N.D) Available from http://www.w3schools.com/json/json_http.asp [Accessed 05/03/2015] JSON Parsing and HTTP request
//tutorials point. (N.D) Available from http://www.tutorialspoint.com/java/io/inputstreamreader_read_char.htm [Accessed 10/04/2015] Input streams
//oracle. (N.D) Available from https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html [Accessed 10/04/2015] Input streams
//oracle. (N.D) Available from http://docs.oracle.com/javase/7/docs/api/java/io/InputStreamReader.html [Accessed 10/04/2015] Input streams

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;


public class MainActivity extends ActionBarActivity {

    //Testing the database
    DBAdapter db = new DBAdapter(this);
    int onlineDatabaseVersion = 0;
    int localDatabaseVersion = 0;
    boolean dataDownloaded = false;
    boolean dataBaseVersionChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("App Started");

        //Get the version of the online database and assign the value to the variable onlineDatabaseVersion
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println("Checking online database version");
                new checkDatabaseVersion().execute();
            }
        });
        t.start();
        try{
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Get the version of the local database
        try {
            System.out.println("Opening database");
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Testing
        //System.out.println(db.getDatabaseVersion());
        //db.incDatabaseVersion((db.getDatabaseVersion())+1);
        //System.out.println(db.getDatabaseVersion());
        localDatabaseVersion = db.getDatabaseVersion();
        System.out.println("Local database = " + localDatabaseVersion);
        System.out.println("Closing database");
        db.close();

        //Testing database
        //printDatabase();

        //Compare the two versions
        System.out.println("Comparing the database versions");
        while(dataBaseVersionChecked == false);
        if(localDatabaseVersion!=onlineDatabaseVersion){
            System.out.println("The database versions are different");
            //download online database and populate the local database
            Thread downloadDatabase = new Thread(new Runnable(){
                @Override
                public void run() {
                    new downloadOnlineDatabase().execute();
                }
            });
            downloadDatabase.start();
            try{
                downloadDatabase.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Toast.makeText(MainActivity.this, "Data added to the database", Toast.LENGTH_LONG).show();
        }else{
            System.out.println("The database versions are the same");
        }

        //Testing database
        //printDatabase();
        System.out.println("The local database version in now " + localDatabaseVersion);
        System.out.println("The online database version is " + onlineDatabaseVersion);



        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/



    }

    public void printDatabase(){
        //Testing printing the database
        try {
            System.out.println("Opening database to print values");
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        Cursor c = db.getAllRecords();
        if(c.moveToFirst()){
            do{
                System.out.print(c.getString(0) + ", "); //Print out the selected record data field
                System.out.print(c.getString(1) + ", ");
                System.out.print(c.getString(2) + ", ");
                System.out.print(c.getString(3) + ", ");
                System.out.print(c.getString(4) + ", ");
                System.out.print(c.getString(5) + ", ");
                System.out.print(c.getString(6) + ", ");
                System.out.print(c.getString(7) + ", ");
                System.out.print(c.getString(8) + ", ");
                System.out.print(c.getString(9) + "\n");
            }while((c.moveToNext()));
        }
        System.out.println("Closing database");
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Called when the "Display Data" button is clicked
    public void display_data_options_activity(View view){
        Intent intent = new Intent(this, DisplayDataOptionsActivity.class);
        startActivity(intent);
    }

    public class checkDatabaseVersion extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... arg0) {

            String server = "http://uomsmartgrid.t15.org";
            String result = "";
            String link = server + "/DB_CheckDBVersion.php";
            InputStream is = null;

            try{
                HttpClient client = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(link);
                HttpResponse response = client.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                System.out.println("No Errors");
            }catch(Exception e){
                System.out.println("Error");
                System.out.println(e);
            }

            //convert response to string
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();

                result=sb.toString();
                System.out.println(result);
            }catch(Exception e){
                Log.e("log_tag", "Error converting result "+e.toString());
            }

            //parse json data
            try{
                JSONArray jArray = new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    onlineDatabaseVersion = json_data.getInt("Version_ID");
                    System.out.println("Online version = " + onlineDatabaseVersion);
                }
            }catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
            }

            System.out.println("Boolean changed");
            dataBaseVersionChecked = true;

            return null;
        }
    }

    public class downloadOnlineDatabase extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... arg0) {

            String server = "http://uomsmartgrid.t15.org";
            String result = "";
            String link = server + "/downloadDatabase.php";
            InputStream is = null;

            try{
                HttpClient client = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(link);
                HttpResponse response = client.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent(); //Assign the content to an input stream
            }catch(Exception e){
                System.out.println("Error with obtaining the database version");
                System.out.println(e);
            }
            //convert response to string
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) { //Read the contents
                    sb.append(line + "\n"); //Append the contents to a StringBuilder
                }
                is.close(); //Close the input stream
                result=sb.toString(); //Store content as a String
                System.out.println(result);
            }catch(Exception e){
                Log.e("log_tag", "Error converting result "+e.toString());
            }

            //parse json data
            try{
                db.open();
                db.deleteTable();
                JSONArray jArray = new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    System.out.println(json_data);
                    db.insertRecord(json_data.getInt("DB_ID"), json_data.getString("Name"), json_data.getString("Location"),
                                    json_data.getString("DataType"),json_data.getString("Units"), json_data.getString("WebID"),
                                    json_data.getInt("ID"),json_data.getString("Status"), json_data.getDouble("XCoordinates"),
                                    json_data.getDouble("YCoordinates"));
                }

                try{
                    //db.incDatabaseVersion(onlineDatabaseVersion);
                }catch (Exception e){
                    Log.e("log_tag", "Database version error");
                }

            }catch(JSONException e){
                Log.e("log_tag", "Error parsing data " + e.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            db.close();

            System.out.println("Boolean changed");
            dataDownloaded = true;

            return null;
        }
    }
}
