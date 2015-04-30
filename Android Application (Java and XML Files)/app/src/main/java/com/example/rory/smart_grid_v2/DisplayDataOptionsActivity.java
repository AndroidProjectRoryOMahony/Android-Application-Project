package com.example.rory.smart_grid_v2;
//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//Developers. (N.D) Available from http://developer.android.com/training/basics/firstapp/starting-activity.html [Accessed 03/04/2015] Starting another activity: Intents
//Android open tutorials (N.D) Available from http://androidopentutorials.com/android-datepickerdialog-on-edittext-click-event/ [Accessed 08/04/2015] date picker
//stackoverflow. 2014 Available from http://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext [Accessed 15/04/2015] Timepicker
//stackoverflow. 2011 Available from http://stackoverflow.com/questions/2784081/android-create-spinner-programmatically-from-array [Accessed 16/04/2015] for populating the spinner dynamically
//Developers. (N.D) Available from http://developer.android.com/guide/topics/ui/controls/spinner.html [Accessed 07/04/2015] Spinners
//Developers. (N.D) Available from http://developer.android.com/guide/topics/ui/controls/pickers.html [Accessed 07/04/2015] Pickers
//Knowledge by experience. August 2012 Available from http://wptrafficanalyzer.in/blog/displaying-timepickerdialog-using-dialogfragment-in-android-with-backward-compatibilty-support-library/ [Accessed 17/02/2015] Dialogs
//stackoverflow. 2012 Available from http://stackoverflow.com/questions/6936800/android-change-strings-resource-programmatically [Accessed 17/02/2015] Changing the value of a string through Java code

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.DatePickerDialog.OnDateSetListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class DisplayDataOptionsActivity extends ActionBarActivity{

    private Spinner spinnerLocations;
    private Spinner spinnerDataTypes;
    private ArrayAdapter<CharSequence> adapter;
    ArrayList<String> spinnerDataTypeArray;
    ArrayList<String> spinnerLocationsArray;
    private DatePickerDialog fromDatePickerDialog, toDatePickerDialog;
    private TimePickerDialog toTimePickerDialog, fromTimePickerDialog;
    private SimpleDateFormat dateFormatter;
    //Testing the database
    DBAdapter db = new DBAdapter(this);

    //Strings for passing data to another activity
    public final static String EXTRA_LOCATION = "com.example.rory.smart_grid_v2.LOCATION";
    public final static String EXTRA_DATA_TYPE = "com.example.rory.smart_grid_v2.DATA_TYPE";
    public final static String EXTRA_DATE_FROM = "com.example.rory.smart_grid_v2.DATE_FROM";
    public final static String EXTRA_DATE_TO = "com.example.rory.smart_grid_v2.DATE_TO";
    public final static String EXTRA_TIME_FROM = "com.example.rory.smart_grid_v2.TIME_FROM";
    public final static String EXTRA_TIME_TO = "com.example.rory.smart_grid_v2.TIME_TO";
    public final static String EXTRA_WEB_ID = "";
    public final static String EXTRA_UNITS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data_options);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        setupSpinners();
        setupEventHandler();
        setUpDatePicker();
        setUpTimePicker();

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }

    /*Called when the user clicks the Send button*/
    public void passUserGraphicalOptions(View view){

        Intent intent = new Intent(this, DisplayDataGraphicallyActivity.class);
        //An intent can carry key-value pairs ("extras")
        //Need to find the values you want to send over first
        Spinner spinnerLocation = (Spinner) findViewById(R.id.pi_server_locations_spinner);
        Spinner spinnerDataType = (Spinner) findViewById(R.id.select_data_type_to_display_spinner);
        //Use the "putExtra()" method to add to the intent
        intent.putExtra(EXTRA_LOCATION, spinnerLocation.getSelectedItem().toString());//key, value
        intent.putExtra(EXTRA_DATA_TYPE,spinnerDataType.getSelectedItem().toString());
        //Call "startActivity(intent)" once all data has been passed to the intent

        TextView dateFrom = (TextView) findViewById(R.id.selected_data_to_display_date_from);
        TextView dateTo = (TextView) findViewById(R.id.selected_data_to_display_date_to);
        TextView timeFrom = (TextView) findViewById(R.id.selected_data_to_display_time_from);
        TextView timeTo = (TextView) findViewById(R.id.selected_data_to_display_time_to);


        intent.putExtra(EXTRA_DATE_FROM, dateFrom.getText().toString());
        intent.putExtra(EXTRA_DATE_TO, dateTo.getText().toString());
        intent.putExtra(EXTRA_TIME_FROM, timeFrom.getText().toString());
        intent.putExtra(EXTRA_TIME_TO, timeTo.getText().toString());
        intent.putExtra(EXTRA_WEB_ID, getWebID());
        intent.putExtra(EXTRA_UNITS, getUnits());

        //start the activity
        startActivity(intent);
    }

    private String getWebID(){
        String webID = "";
        Spinner spinnerLocation = (Spinner) findViewById(R.id.pi_server_locations_spinner);
        Spinner spinnerDataType = (Spinner) findViewById(R.id.select_data_type_to_display_spinner);

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Cursor c = db.getParticularDataFromACertainRecord("webID", spinnerLocation.getSelectedItem().toString(), spinnerDataType.getSelectedItem().toString());
            if(c.moveToFirst()){
                do{
                    System.out.println(c.getString(0)); //Print out the selected record data field
                    webID += c.getString(0);
                }while((c.moveToNext()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();

        return webID;
    }

    private String getUnits(){
        String units = "";
        Spinner spinnerLocation = (Spinner) findViewById(R.id.pi_server_locations_spinner);
        Spinner spinnerDataType = (Spinner) findViewById(R.id.select_data_type_to_display_spinner);

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Cursor c = db.getParticularDataFromACertainRecord("units", spinnerLocation.getSelectedItem().toString(), spinnerDataType.getSelectedItem().toString());
            if(c.moveToFirst()){
                do{
                    System.out.println(c.getString(0)); //Print out the selected record data field
                    units += c.getString(0);
                }while((c.moveToNext()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();

        return units;
    }

    private void setupEventHandler() {

        /** Getting an instance of the buttons */

        Button select_time_to = (Button)findViewById(R.id.select_time_to);
        Button select_date_from = (Button) findViewById(R.id.select_date_from);
        Button select_date_to = (Button) findViewById(R.id.select_date_to);

        //Get an instance of the button
        Button select_time_from = (Button)findViewById(R.id.select_time_from);
        //Setting click event listener for the button
        select_time_from.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //On click: show the "time picker" dialog
                fromTimePickerDialog.show();
            }
        });

        select_time_to.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                toTimePickerDialog.show();
            }
        });

        //date picker button listeners
        //when the select_date_x button is clicked, the method below is called
        select_date_from.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                fromDatePickerDialog.show();    //display the date picker dialog, allowing the user to pick a date
            }
        });
        select_date_to.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                toDatePickerDialog.show();    //display the date picker dialog, allowing the user to pick a date
            }
        });
    }

    private void setUpTimePicker(){
        Calendar newCalender = Calendar.getInstance();
        int hour = newCalender.get(Calendar.HOUR_OF_DAY);
        int minute = newCalender.get(Calendar.MINUTE);

        fromTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute){
                String hr = Integer.toString(selectedHour);
                String min = Integer.toString(selectedMinute);
                if(selectedHour < 10){
                    hr = "0" + selectedHour;
                }
                if(selectedMinute < 10){
                    min = "0" + selectedMinute;
                }
                ((TextView) findViewById(R.id.selected_data_to_display_time_from)).setText(hr + ":" + min + ":00");
            }
        }, hour, minute, true);

        toTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute){
                String hr = Integer.toString(selectedHour);
                String min = Integer.toString(selectedMinute);
                if(selectedHour < 10){
                    hr = "0" + selectedHour;
                }
                if(selectedMinute < 10){
                    min = "0" + selectedMinute;
                }
                ((TextView) findViewById(R.id.selected_data_to_display_time_to)).setText(hr + ":" + min + ":00");
            }
        }, hour, minute, true);
    }

    private void setUpDatePicker(){
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ((TextView) findViewById(R.id.selected_data_to_display_date_from)).setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ((TextView) findViewById(R.id.selected_data_to_display_date_to)).setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setupSpinners(){

        spinnerLocationsArray = new ArrayList<String>();

        //Query the database for the locations available
        //System.out.println("Printing out only the table entries with the location as Test Street A");
        //get certain records that have a certain location
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Cursor c = db.getParticularDataFromAllRecords("location");
            if(c.moveToFirst()){
                do{
                    System.out.println(c.getString(0)); //Testing: Print out the selected record data field
                    spinnerLocationsArray.add(c.getString(0));
                }while((c.moveToNext()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();

        //spinnerLocationsArray is now populated with the different locations from the database

        spinnerLocations = (Spinner) findViewById(R.id.pi_server_locations_spinner); //get the spinner
        ArrayAdapter<String> spinnerLocationsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerLocationsArray); //Create a spinner adapter with the spinnerLocationsArray
        spinnerLocationsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Set the drop down view resource
        spinnerLocations.setAdapter(spinnerLocationsArrayAdapter);    //set the adapter for the spinner
        spinnerLocations.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                populateDataTypeSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void populateDataTypeSpinner(){
        spinnerDataTypeArray = new ArrayList<String>();
        String locationToSearch = spinnerLocations.getSelectedItem().toString();

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            Cursor c = db.getParticularDataFromAllRecordsWithAParticularField("dataType", "location", locationToSearch);
            if(c.moveToFirst()){
                do{
                    System.out.println(c.getString(0)); //Print out the selected record data field
                    spinnerDataTypeArray.add(c.getString(0));
                }while((c.moveToNext()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();

        //spinnerLocationsArray is now populated with the different locations from the database

        spinnerDataTypes = (Spinner) findViewById(R.id.select_data_type_to_display_spinner); //get the spinner
        ArrayAdapter<String> spinnerDataTypesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerDataTypeArray); //Create a spinner adapter with the spinnerLocationsArray
        spinnerDataTypesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Set the drop down view resource
        spinnerDataTypes.setAdapter(spinnerDataTypesArrayAdapter);    //set the adapter for the spinner
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_data_options, menu);
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
}
