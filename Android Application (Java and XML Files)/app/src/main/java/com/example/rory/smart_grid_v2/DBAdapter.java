package com.example.rory.smart_grid_v2;
//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//Developer. (N.D) Available from http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html [Accessed 05/03/2015] SQLite database
//stackoverflow. 2013 Available from http://stackoverflow.com/questions/10600670/sqlitedatabase-query-method [Accessed 05/03/2015] SQLite query
//YouTube. February 2012 Available from https://www.youtube.com/watch?v=j-IV87qQ00M [accessed 06/03/2015] SQLite database

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

public class DBAdapter {

    public static final String KEY_DB_ID = "DB_ID";
    public static final String KEY_NAME = "Name";
    public static final String KEY_LOCATION = "Location";
    public static final String KEY_DATATYPE = "DataType";
    public static final String KEY_UNITS = "Units";
    public static final String KEY_WEBID = "WebID";
    public static final String KEY_ID = "ID";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_XCOORDINATES = "XCoordinates";
    public static final String KEY_YCOORDINATES = "YCoordinates";
    private static final String DATABASE_NAME = "dataInformationDB";
    private static final String DATABASE_TABLE = "dataInformation";
    //After getting issues having downgraded the DB on the emulator, the version needed to be set to the version downgraded from
    private static final int DATABASE_VERSION = 5;
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_CREATE =
        "create table if not exists dataInformation ("
            + "DB_ID INT not null, Name VARCHAR not null, Location VARCHAR not null, DataType VARCHAR not null, Units VARCHAR not null, WebID VARCHAR not null, ID INT not null, " +
                "Status VARCHAR not null, XCoordinates DOUBLE not null, YCoordinates DOUBLE not null);";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase dataBase;

    public DBAdapter(Context context){
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS dataInformation");
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data" );
            db.execSQL("DROP TABLE IF EXISTS dataInformation");
            onCreate(db);
        }
    }


    //Open the database
    public DBAdapter open() throws SQLException{
        dataBase = DBHelper.getWritableDatabase();
        return this;
    }

    //Close the database
    public void close(){
        DBHelper.close();
    }

    //Insert a record into the database
    public long insertRecord(int DB_ID, String Name, String Location, String DataType, String Units, String WebID, int ID, String Status, double XCoordinates, double YCoordinates){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DB_ID, DB_ID);
        initialValues.put(KEY_NAME, Name);
        initialValues.put(KEY_LOCATION, Location);
        initialValues.put(KEY_DATATYPE, DataType);
        initialValues.put(KEY_UNITS, Units);
        initialValues.put(KEY_WEBID, WebID);
        initialValues.put(KEY_ID, ID);
        initialValues.put(KEY_STATUS, Status);
        initialValues.put(KEY_XCOORDINATES, XCoordinates);
        initialValues.put(KEY_YCOORDINATES, YCoordinates);

        return dataBase.insert(DATABASE_TABLE, null, initialValues);
    }

    //Deleting a certain record
    public boolean deleteRecord(long rowID){
        return dataBase.delete(DATABASE_TABLE, KEY_DB_ID + "=" + rowID, null) > 0;
    }

    public void deleteTable(){
           int i = 1;
           while((DatabaseUtils.queryNumEntries(dataBase, DATABASE_TABLE) > 0)){
                dataBase.delete(DATABASE_TABLE, KEY_DB_ID + "=" + i, null);
                i++;
           }
    }

    //Retrieve all the records
    public Cursor getAllRecords(){
        return dataBase.query(DATABASE_TABLE, new String[] {KEY_DB_ID, KEY_NAME, KEY_LOCATION, KEY_DATATYPE, KEY_UNITS, KEY_WEBID, KEY_ID, KEY_STATUS, KEY_XCOORDINATES, KEY_YCOORDINATES}, null, null, null, null, null);
    }

    //Retrieve a particular data type from all records with a certain field
    public Cursor getParticularDataFromAllRecordsWithAParticularField(String dataToReturn, String dataFieldToSearch, String fieldContains) throws SQLException{

        String whereClause = dataFieldToSearch + "= ?";
        String whereArgs[] = new String [] {fieldContains};
        Cursor mCursor = dataBase.query(true, DATABASE_TABLE, new String[] {dataToReturn}, whereClause, whereArgs, null, null, null, null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //Retrieve a particular data type from a certain record
    public Cursor getParticularDataFromACertainRecord(String dataToReturn, String locationName, String dataType) throws SQLException{
        String whereClause = "location" + "= ?" + " AND " + "dataType" + "= ?";
        String whereArgs[] = new String [] {locationName, dataType};
        Cursor mCursor = dataBase.query(true, DATABASE_TABLE, new String[] {dataToReturn}, whereClause, whereArgs, null, null, null, null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //Retrieve a particular data type from all records
    public Cursor getParticularDataFromAllRecords(String dataToReturn) throws SQLException{
        Cursor mCursor = dataBase.query(true, DATABASE_TABLE, new String[] {dataToReturn}, null, null, null, null, null, null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

/*    //Update a certain record
    public boolean updateRecord(long rowsID, String location, String dataType, String webID){
        ContentValues args = new ContentValues();
        args.put(KEY_LOCATION, location);
        args.put(KEY_DATATYPE, dataType);
        args.put(KEY_WEBID, webID);

        return dataBase.update(DATABASE_TABLE, args, KEY_DB_ID + "=" + rowsID, null) > 0;
    }*/

    public int getDatabaseVersion(){
        return dataBase.getVersion();
    }

    public void incDatabaseVersion(int version){
        dataBase.setVersion(version);
    }

}
