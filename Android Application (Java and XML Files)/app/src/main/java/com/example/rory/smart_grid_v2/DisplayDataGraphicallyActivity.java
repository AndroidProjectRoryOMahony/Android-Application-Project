package com.example.rory.smart_grid_v2;
//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//my sample code. (N.D) Available from http://www.mysamplecode.com/2013/06/sftp-apache-commons-file-download.html [Accessed 07/04/2015] SFTP Apache commons
//Android development. February 2014 Available from http://androidtrainningcenter.blogspot.co.uk/2014/02/android-ftp-client-tutorial-with.html [Accessed 07/04/2015] FTP
//graphview. (N.D) Available from http://jjoe64.github.io/GraphView/javadoc/ [Accessed 02/02/2015] Graphview documentation
//stacjoverflow. 2012 Available from http://stackoverflow.com/questions/6242268/repeat-a-task-with-a-time-delay/6242292#6242292 [Accessed 22/02/2015] Repeating a task: handler
//codepath. 2012 Available from https://guides.codepath.com/android/Repeating-Periodic-Tasks [Accessed 22/02/2015] Repeating a task: handler
//YouTube. July 2014 Available from https://www.youtube.com/watch?v=UL0vaJVXYwk for help with the dialog window [Accessed 17/04/2015] Dialog window

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DisplayDataGraphicallyActivity extends ActionBarActivity implements LabelFormatter{

    List<Double> FTPValues = new ArrayList<Double>(); //An array list to store the data values downloaded
    double[] FTPValuesOrdered; //An array list to store the data values downloaded (in order from min to max)
    boolean dataDownloaded = false; //a condition variable to prevent the graph from being displayed before data has been downloaded
    boolean graphDisplayed = false; //a condition variable to prevent data from being downloaded again until the graph has been displayed
    Handler handler = new Handler();    //a handler used to repeat the download of data and the update of graphs
    GraphView graph;    //the graph view, used to hold the graph
    List<String> FTPTimeValues = new ArrayList<String>();   //An array list to hold the time values temporarily for each piece of data downloaded. These values will eventually be stored in an array
    String FTPTimeValuesArray[];    //An array to hold the time values
    DataPoint[] data;   //A collection of data points used to plot the graph
    double min, max, mean, median, mode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data_graphically);

        // Get the message from the intent
        Intent intent = getIntent();
        String location = intent.getStringExtra(DisplayDataOptionsActivity.EXTRA_LOCATION);
        String dataType = intent.getStringExtra(DisplayDataOptionsActivity.EXTRA_DATA_TYPE);
        String dateFrom = intent.getStringExtra(DisplayDataOptionsActivity.EXTRA_DATE_FROM);
        String dateTo = intent.getStringExtra(DisplayDataOptionsActivity.EXTRA_DATE_TO);
        String timeFrom = intent.getStringExtra(DisplayDataOptionsActivity.EXTRA_TIME_FROM);
        String timeTo = intent.getStringExtra(DisplayDataOptionsActivity.EXTRA_TIME_TO);
        String webID = intent.getStringExtra(DisplayDataOptionsActivity.EXTRA_WEB_ID);
        String units = intent.getStringExtra(DisplayDataOptionsActivity.EXTRA_UNITS);
        String Yaxis = dataType + " (" + units + ")";

        //Testing: printing out the messages passed from the previous activity
        System.out.println(location);
        System.out.println(dataType);
        System.out.println(dateFrom);
        System.out.println(dateTo);
        System.out.println(timeFrom);
        System.out.println(timeTo);
        System.out.println(webID);
        System.out.println(units);

        setupStatisticsButton();

        //Creating a view for the graph
        graph = (GraphView) findViewById(R.id.graph);   //assign "graph" to the graph view created previously

        //Initially
        //get data type that the user selected from the previous options
        //get the day, the date, the month and the year the user selected in the previous options
        //set the title of the activity as this data type
        //set the day, date, month and year below the data type

        //Editing graph view setting
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");            //Set the horizontal axis title
        graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize(30);        //Set the horizontal axis title size
        graph.getGridLabelRenderer().setVerticalAxisTitle(dataType);            //Set the vertical axis title
        graph.getGridLabelRenderer().setVerticalAxisTitle(Yaxis);               //Set the vertical axis title
        graph.getGridLabelRenderer().setVerticalAxisTitleTextSize(30);          //Set the vertical axis title size
        graph.getViewport().setScrollable(true);                                //Allow the graph to be scrollable
        //graph.getViewport().setScalable(true);                                //Allow the graph to be scalable
        graph.getViewport().setXAxisBoundsManual(true);                         //Allow the range of values on the x axis to be edited
        graph.getViewport().setYAxisBoundsManual(true);                         //Allow the range of values on the y axis to be edited
        graph.setBackgroundColor(Color.rgb(255, 255, 255));                     //Set the background colour for the graph view

        //Testing
        FTPTimeValues.add("15:25:00");  //Create 20 time values and store in the list
        FTPTimeValues.add("15:25:01");
        FTPTimeValues.add("15:25:02");
        FTPTimeValues.add("15:25:03");
        FTPTimeValues.add("15:25:04");
        FTPTimeValues.add("15:25:05");
        FTPTimeValues.add("15:25:06");
        FTPTimeValues.add("15:25:07");
        FTPTimeValues.add("15:25:08");
        FTPTimeValues.add("15:25:09");
        FTPTimeValues.add("15:25:10");
        FTPTimeValues.add("15:25:11");
        FTPTimeValues.add("15:25:12");
        FTPTimeValues.add("15:25:13");
        FTPTimeValues.add("15:25:14");
        FTPTimeValues.add("15:25:15");
        FTPTimeValues.add("15:25:16");
        FTPTimeValues.add("15:25:17");
        FTPTimeValues.add("15:25:18");
        FTPTimeValues.add("15:25:19");


        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                new downloadData().execute();   //download the data based on the users options
                while (dataDownloaded == false);

                //graph();    //create the graph
            }
        });

        t.start();
        try{
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        graph();
        //new Thread(new downloadAndCreateGraph()).start(); //Start a new thread to perform data download and graph updates
    }

    public void setupStatisticsButton(){
        Button statisticsButton = (Button) findViewById(R.id.statistics_button);

        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Statistics";
                String statistics[] = {"Minimum Value", "Maximum Value", "Mean Value", "Median Value", "Modal Value"};
                String content =    statistics[0] + "\t" + min + "\n" +
                                    statistics[1] + "\t" + max + "\n" +
                                    statistics[2] + "\t" + mean + "\n" +
                                    statistics[3] + "\t" + median + "\n" +
                                    statistics[4] + "\t" + mode;
                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayDataGraphicallyActivity.this);
                builder.setMessage(content);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void graph(){
        runOnUiThread(new Runnable(){
            public void run(){
                createDataAndTimeCollectionsAndArray(); //Creates the data collection and the times array for the graph
                calculateStatistics(); //Calculates various statistics on the data
                graph.getViewport().setMaxY(max); //Set the maximum y value to be the maximum data value
                graph.getViewport().setMinY(min);
                graph.getViewport().setMinX(0); //set the view port to the beginning
                graph.getViewport().setMaxX(FTPValues.size()/2);  //set the max value of the viewport //need to minus 1 for end value if displaying all at the beginning?

                //StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph); //Create a new static label formatter
                //staticLabelsFormatter.setHorizontalLabels(FTPTimeValuesArray);  //Assign the downloaded time values to the x axis


                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {    //This section converts a double into an int, which is used with the "times array" to display the time on the grpah dynamically
                    @Override                                                                   //This allows the graph to be scrollable while allowing the times on the x axis to move as well
                    public String formatLabel(double value, boolean isValueX) {

                        if (isValueX) { //If the value is on the x axis
                            // show normal x values
                            //return super.formatLabel(value, isValueX);

                            //Testing
                            System.out.println("Value = "  + value);
                            System.out.println("Value rounded = "  + ((int) Math.round(value)));

                            DecimalFormat df = new DecimalFormat("#.#");
                            value = Double.valueOf(df.format(value));   //Value now equals a 1dp double
                            //System.out.println(((int) Math.round(value))-1);

                            //if an even number is doubled, we get an even number
                            //if an odd number is doubled, we get an even number
                            //if a value such as 2.5 or 3.5 is doubled, we get an odd number
                            //Test if the value is even
                            if(((value*2)%2) == 0) { //if the value is an int, it can be mapped to the array: display the correct time
                                return FTPTimeValuesArray[((int)value)];
                            } else{ //the value is e.g. 2.5 and can't be mapped to the array, therefore don't display anything
                                return "";
                            }
                        } else {    //if the value is on the y axis
                            return super.formatLabel(value, isValueX);
                        }

                    }
                });

                //graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);  //Set the label formatter
                //graph.getGridLabelRenderer().setNumHorizontalLabels(4); //Set the number of x axis values to display
                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);   //Create a line graph series of the data values
                graph.addSeries(series);    //Add the series to the graph view
                graphDisplayed = true;
            }
        });
    }


    private void createDataAndTimeCollectionsAndArray(){
        data = new DataPoint[FTPValues.size()]; //create a DataPoint array with a size equal to the number of data values downloaded
        FTPTimeValuesArray = new String[FTPTimeValues.size()];  //create a String array with a size equal to the number of data value times downloaded

        //Testing
        System.out.println("Values in the array list:");
        for(int i = 0; i < FTPValues.size(); i++){
            System.out.println(FTPValues.get(i));
        }

        //populate the ordered array and order the data
        FTPValuesOrdered = new double[FTPValues.size()];
        for(int i = 0; i < FTPValues.size(); i++){
            FTPValuesOrdered[i] = FTPValues.get(i);
        }
        Arrays.sort(FTPValuesOrdered);

        for(int i = 0; i<data.length; i++){
            data[i] = new DataPoint(i, FTPValues.get(i));   //Fill in the DataPoint array with the data values
            FTPTimeValuesArray[i] = FTPTimeValues.get(i);   //Fill in the String array with the data times (need to do this for the label formatter)
        }
    }

    public void resetGraph(){
        runOnUiThread(new Runnable(){
            public void run(){
                resetGraphViewSeries();
            }
        });
    }

    private void resetGraphViewSeries() {
        graph.removeAllSeries();
    }

/*    class downloadAndCreateGraph implements Runnable{ //class to download data and then create the graph. Replaced but left in to show progress

        public void run() {
            new downloadData().execute();   //download the data based on the users options
            while (dataDownloaded == false);

            graph();    //create the graph

            handler.post(new Runnable() {   //handler to repeat an action every x milliseconds
                @Override
                public void run() {
                    //reset graph
                    //resetGraph();
                    //System.out.println("Handler in action");
                    //new getFile().execute();
                    //while (dataDownloaded == false);

                    //graph();
                    //handler.postDelayed(this, 5000);
                }
            });
        }
    }*/

    public void calculateStatistics() {
        Calculations calc = new Calculations();
        min = calc.calculateMinValue(FTPValuesOrdered);
        max = calc.calculateMaxValue(FTPValuesOrdered);
        mean = calc.calculateMean(FTPValuesOrdered);
        median = calc.calculateMedian(FTPValuesOrdered);
        mode = calc.calculateMode(FTPValuesOrdered);

        //Testing
        System.out.println("min = " + min);
        System.out.println("max = " + max);
        System.out.println("mean = " + mean);
        System.out.println("median = " + median);
        System.out.println("mode = " + mode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_data_graphically, menu);
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

    @Override
    public String formatLabel(double v, boolean b) {
        return null;
    }

    @Override
    public void setViewport(Viewport viewport) {

    }

    public class downloadData extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... arg0) {

            String dataReceived = "";
            //Need to create the url for getting the data here
            //String serverAddress = "UoM server address";
            //use the values passed in from previous activity for dates and times and webID
            //server += webId + "/" + times and dates from etc.

            FTPValues.clear();
            FTPClient ftp = null;
            boolean localActive = false, binaryTransfer = false, useEpsvWithIPv4 = false;

            int portNumber = 21;                            //FTP details
            String server = "ftp.uomsmartgrid.t15.org";
            String user = "u703673749";
            String password = "UoM3rdyearproject";
            String filename = "Data.txt";

            try {
                System.out.println("Attempting to connect...");
                ftp = new FTPClient();
                int reply;
                ftp.connect(server);
                System.out.println("Connected to " + server + " on " + (portNumber > 0 ? portNumber : ftp.getDefaultPort()));
                reply = ftp.getReplyCode(); //get reply code to verify success
                System.out.println(reply);
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect();
                    System.err.println("FTP server refused connection.");
                }
            } catch (IOException e) {
                if (ftp.isConnected()) {
                    try {
                        ftp.disconnect();
                    } catch (IOException f) {
                        // do nothing
                    }
                }
                System.err.println("Could not connect to server.");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(ftp.getReplyCode());
                System.out.println("Error");
            }

            try {
                boolean success = ftp.login(user, password); //Attempt to login
                if (!success) { //If the login failed
                    ftp.logout();
                    System.out.println("Could not log in");
                }
                System.out.println("Remote system is " + ftp.getSystemType());
                if (binaryTransfer) {
                    ftp.setFileType(FTP.BINARY_FILE_TYPE);
                } else {
                    // "in theory this should not be necessary as servers should default to ASCII
                    // but they don't all do so - see NET-500"
                    ftp.setFileType(FTP.ASCII_FILE_TYPE);
                }

                // "Use passive mode as default because most of us are behind firewalls these days."
                if (localActive) {
                    ftp.enterLocalActiveMode();
                } else {
                    ftp.enterLocalPassiveMode();
                }
                ftp.setUseEPSVwithIPv4(useEpsvWithIPv4);

                try {
                    InputStream inStream = ftp.retrieveFileStream(filename);
                    InputStreamReader isr = new InputStreamReader(inStream, "UTF8");
                    int j;
                    while ((j = isr.read()) != -1) { //Loop through the file printing out the contents while storing in a string
                        if(j != 10) { //if the read in value is not a line feed: store in a string
                            dataReceived += (char) j;
                        } else{ //value was a line feed: store dataReceived value in a temporary array
                            FTPValues.add(Double.parseDouble(dataReceived));
                            dataReceived = ""; //reset string
                        }
                    }
                    isr.close(); //Close the input stream
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ftp.noop(); // check that control connection is working OK

                ftp.logout();
            } catch (FTPConnectionClosedException e) {
                //error = true;
                System.err.println("Server closed connection.");
                e.printStackTrace();
            } catch (IOException e) {
                //error = true;
                e.printStackTrace();
            } finally {
                if (ftp.isConnected()) {
                    try {
                        ftp.disconnect();
                    } catch (IOException f) {
                        // do nothing
                    }
                }
            }
            // end main

            System.out.println(dataReceived);
            System.out.println("Finished downloading data");
            dataDownloaded = true;

            return null;
        }
    }
}
