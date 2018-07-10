package unisys.com.employeepayroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import unisys.com.employeepayroll.dbhelper.DatabaseHelper;
import unisys.com.employeepayroll.model.Employee;
import unisys.com.employeepayroll.model.Vehicle;

public class EmployeeDetail extends AppCompatActivity {
TextView name,type,earning,dob,schoolName,vehicleDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        setTitle("Employee Details");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Employee employee = (Employee) getIntent().getSerializableExtra("detail");
        name = findViewById(R.id.empname);
        type = findViewById(R.id.type);
        earning = findViewById(R.id.earnings);
        dob = findViewById(R.id.dob);
        schoolName =  findViewById(R.id.schoolName);
        vehicleDetail = findViewById(R.id.vehicleDetail);
        name.setText("Name: "+ employee.getName());
        type.setText("Type: "+employee.getEmpType());
        earning.setText("Earnings: "+employee.getEarnings());
        dob.setText("Dob: "+employee.getDob());

        if (!employee.getSchoolName().equals(""))
        {
            schoolName.setVisibility(View.VISIBLE);
            schoolName.setText("School: "+employee.getSchoolName());
        }
        Vehicle c = new DatabaseHelper(this).getVehicle(employee.getId());
        if (c != null)
        {
            String text = "Type: "+ c.getVehicleType() + "\nMake: "+c.getVehicleMake()+"\nModel:"+c.getVehicleModel()+"\nInsured: "+(c.getVehicleInsured() ?"Yes" : "No");
            vehicleDetail.setText("Vehicle Information:\n"+text);
        }
        else {
            vehicleDetail.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
