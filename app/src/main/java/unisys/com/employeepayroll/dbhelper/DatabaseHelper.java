package unisys.com.employeepayroll.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import unisys.com.employeepayroll.model.Car;
import unisys.com.employeepayroll.model.Employee;
import unisys.com.employeepayroll.model.FullTimeEmp;
import unisys.com.employeepayroll.model.Vehicle;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "empManager";
    private static final String TABLE_EMPLOYEE = "employe";
    private static final String EMP_ID = "id";
    private static final String EMP_NAME = "name";
    private static final String EMP_TYPE = "type";
    private static final String EMP_EARNINGS = "earnings";
    private static final String EMP_DOB = "dob";
    private static final String EMP_School = "school";

    private static final String TABLE_VEHICLE = "vehicle";
    private static final String emp_ID = "emp_id";
   // private static final String vehicle_NAME = "v_name";
    private static final String vehicle_TYPE = "v_type";
    private static final String vehicle_MAKE = "v_make";
    private static final String vehicle_model = "v_model";
    private static final String vehicle_number = "v_number";
    private static final String vehicle_ins = "v_ins";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMP_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE + "("
                + EMP_ID + " INTEGER PRIMARY KEY," + EMP_NAME + " TEXT,"
                + EMP_TYPE + " TEXT, "+ EMP_EARNINGS + " FLOAT, "+ EMP_DOB + " TEXT, "+EMP_School+" TEXT " + ")";
        String CREATE_Vehicle_TABLE = "CREATE TABLE " + TABLE_VEHICLE+ "("
                + emp_ID + " INTEGER," + vehicle_TYPE + " TEXT, "+ vehicle_MAKE + " TEXT, "+ vehicle_model + " TEXT, "+ vehicle_number + " TEXT , "+ vehicle_ins + " INTEGER " + ")";
        db.execSQL(CREATE_EMP_TABLE);
        db.execSQL(CREATE_Vehicle_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);

        // Create tables again
        onCreate(db);
    }

    // code to add the new employee
    public long addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMP_NAME, employee.getName());
        values.put(EMP_TYPE, employee.getEmpType());
        values.put(EMP_EARNINGS, employee.calcEarnings());
        values.put(EMP_DOB, employee.getDob());
        values.put(EMP_School, employee.getSchoolName());
        // Inserting Row
        Long id = db.insert(TABLE_EMPLOYEE, null, values);
        //2nd argument is String containing nullColumnHack
        // Closing database connection
         if (employee.getVehicle() != null) {
            Vehicle vehicle = employee.getVehicle();
             ContentValues values1 = new ContentValues();
             values1.put(emp_ID, id);
             //values1.put(vehicle_NAME, vehicle.g;
             values1.put(vehicle_TYPE, vehicle.getVehicleType());
             values1.put(vehicle_MAKE, vehicle.getVehicleMake());
             values1.put(vehicle_model, vehicle.getVehicleModel());
             values1.put(vehicle_number, vehicle.getVehicleNumber());
             values1.put(vehicle_ins, (vehicle.getVehicleInsured())?1:0);
              id = db.insert(TABLE_VEHICLE, null, values1);
        }
        db.close();
        return id;
    }

    // code to get the single contact
  public  Vehicle getVehicle(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VEHICLE, new String[] {
        vehicle_TYPE,
        vehicle_MAKE,
        vehicle_model,
        vehicle_number,
        vehicle_ins}, emp_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            if (cursor.getCount()==0){
            return  null;
            }
        }
        else return  null;


        Vehicle vehicle = new Car();
        //values1.put(vehicle_NAME, vehicle.g;
        vehicle.setVehicleType( cursor.getString(0));
        vehicle.setVehicleMake(cursor.getString(1));
        vehicle.setVehicleModel(cursor.getString(2));
        vehicle.setVehicleNumber(cursor.getString(3));
        vehicle.setVehicleInsured ((cursor.getInt(4)==1));
        return vehicle;
    }

    // code to get all contacts in a list view
    public List<Employee> getAllEmployess() {
        List<Employee> empList = new ArrayList<Employee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new FullTimeEmp();
                employee.setId(cursor.getString(0));
                Log.d("id",cursor.getString(0));
                employee.setName(cursor.getString(1));
                employee.setEmpType(cursor.getString(2));
                employee.setEarnings(cursor.getFloat(3));
                employee.setDob(cursor.getString(4));
                employee.setSchoolName(cursor.getString(5));
                empList.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return empList;
    }

/*
    // code to update the single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
*/
}
