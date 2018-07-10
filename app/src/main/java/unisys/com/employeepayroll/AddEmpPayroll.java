package unisys.com.employeepayroll;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.design.widget.TextInputLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import unisys.com.employeepayroll.dbhelper.DatabaseHelper;
import unisys.com.employeepayroll.dbhelper.Utils;
import unisys.com.employeepayroll.model.Car;
import unisys.com.employeepayroll.model.CommissionPartTimeEmp;
import unisys.com.employeepayroll.model.Employee;
import unisys.com.employeepayroll.model.FixedBasePartTimeEmp;
import unisys.com.employeepayroll.model.FullTimeEmp;
import unisys.com.employeepayroll.model.InternEmp;
import unisys.com.employeepayroll.model.PartTimeEmp;
import unisys.com.employeepayroll.model.Vehicle;

public class AddEmpPayroll extends AppCompatActivity {
    EditText empName,empDob,commisionAmout,vehicleMake,vehicleModel,vehicleNumber,schoolName,employeeSalary,employeeBonus;
    EditText emp_hr_rate,emp_worked_hrs;
    CheckBox vehicleAvailable,isPer_Com_Emp,vehicleInsured;
    RadioGroup vehicleTypeRadioGroup,empType;
    LinearLayout vehicleTypeView;
    TextInputLayout internView;
    LinearLayout fullTimeView,partTimeView;
    Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp_payroll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        empName = findViewById(R.id.empname);
        empDob = findViewById(R.id.empdob);
        vehicleAvailable = findViewById(R.id.vehicleAvailable);
        isPer_Com_Emp= findViewById(R.id.ispercentage);
        vehicleInsured= findViewById(R.id.vehicleInsured);
        vehicleTypeRadioGroup = findViewById(R.id.vehicleTypeRadioGroup);
        vehicleTypeView =  findViewById(R.id.vehicleTypeView);
        internView =  findViewById(R.id.schoolNameView);
        fullTimeView =  findViewById(R.id.fullTimeEmp);
        partTimeView =  findViewById(R.id.partTimeView);
        empType = findViewById(R.id.empType);
        vehicleMake = findViewById(R.id.vehicleMake);
        vehicleModel = findViewById(R.id.vehicleModel);
        vehicleNumber = findViewById(R.id.vehicleNumber);
        schoolName = findViewById(R.id.schoolName);
        employeeSalary = findViewById(R.id.employeeSalary);
        employeeBonus = findViewById(R.id.empBonus);
        emp_hr_rate= findViewById(R.id.emp_hr_rate);
        emp_worked_hrs= findViewById(R.id.emp_worked_hrs);
        commisionAmout = findViewById(R.id.fixedCommision);
        empDob.setText(Utils.getDateTimeWithoutTime());
        setUpListener();
    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        empDob.setText(sdf.format(myCalendar.getTime()));
    }
    private void setUpListener() {
        empDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        vehicleAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleVehicleView();
            }
        });
        handleVehicleView();
        handleEmpTypeRadioGroup();
        empType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                handleEmpTypeRadioGroup();

            }
        });

    }

    private void showDatePicker() {
        DatePickerDialog dd = new DatePickerDialog(AddEmpPayroll.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dd.show();
    }

    private void handleEmpTypeRadioGroup() {


        internView.setVisibility(View.GONE);
        fullTimeView.setVisibility(View.GONE);
        partTimeView.setVisibility(View.GONE);
        switch (empType.getCheckedRadioButtonId())
        {
            case R.id.partTime:
                partTimeView.setVisibility(View.VISIBLE);
                break;
            case R.id.intern:
                internView.setVisibility(View.VISIBLE);
                break;
            case R.id.fullTime:
                fullTimeView.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void handleVehicleView() {
        if (vehicleAvailable.isChecked())
        {
            vehicleTypeView.setVisibility(View.VISIBLE);
        }else{
            vehicleTypeView.setVisibility(View.GONE);
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
    private void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void savePayroll(View v)
    {
        if (checkValidation())
        {
            return;
        }
        Employee employee = null;
        int id = empType.getCheckedRadioButtonId();
        if (id == R.id.partTime)
        {
            employee = createPartTimeEmployee();
        }
        else if (id == R.id.intern)
        {
            InternEmp iemp = new InternEmp();
            iemp.setSchoolName(schoolName.getText().toString());
            employee = iemp;
            employee.setEmpType(Utils.intern);
        }
        else  //(id == R.id.fullTime)
        {
            FullTimeEmp  fullTimeEmp = new FullTimeEmp();
            fullTimeEmp.setBonus(Double.parseDouble(employeeBonus.getText().toString()));
            fullTimeEmp.setSalary(Double.parseDouble(employeeBonus.getText().toString()));
            employee = fullTimeEmp;
            employee.setEmpType(Utils.Fulltime);
        }
        employee.setName(empName.getText().toString());
        employee.setDob(empDob.getText().toString());
        if(vehicleAvailable.isChecked())
        {
            Vehicle vehicle = new Car();
            vehicle.setVehicleMake(vehicleMake.getText().toString());
            vehicle.setVehicleNumber(vehicleNumber.getText().toString());
            vehicle.setVehicleInsured(vehicleInsured.isChecked());
            vehicle.setVehicleModel(vehicleModel.getText().toString());
            String type = ((RadioButton)findViewById(vehicleTypeRadioGroup.getCheckedRadioButtonId())).getText().toString();
            vehicle.setVehicleType(type);
            employee.setVehicle(vehicle);
        }
        DatabaseHelper db = new DatabaseHelper(this);
        long saveid = db.addEmployee(employee);
        if (saveid != -1){
        showToast("New Payroll Saved");
        clearFields();
        }else
        {
            showToast("Something went wrong");
        }
    }

    private Employee createPartTimeEmployee() {
        PartTimeEmp partTimeEmp;
        if (isPer_Com_Emp.isChecked())
        {
            FixedBasePartTimeEmp fixedBasePartTimeEmp = new FixedBasePartTimeEmp();
            fixedBasePartTimeEmp.setFixedAmount(Double.parseDouble(commisionAmout.getText().toString()));
            fixedBasePartTimeEmp.setEmpType(Utils.PartTime_Fixed);
            partTimeEmp = fixedBasePartTimeEmp;
        }else{
            CommissionPartTimeEmp commissionPartTimeEmp = new CommissionPartTimeEmp();
            commissionPartTimeEmp.setCommision(Double.parseDouble(commisionAmout.getText().toString()));
            commissionPartTimeEmp.setEmpType(Utils.PartTime_Com);
            partTimeEmp = commissionPartTimeEmp;
        }
        partTimeEmp.setEmpHrRate(Double.parseDouble(emp_hr_rate.getText().toString()));
        partTimeEmp.setEmpWorkedHrs(Float.parseFloat(emp_worked_hrs.getText().toString()));
        return partTimeEmp;
    }


    private boolean checkValidation() {
        if (TextUtils.isEmpty(empName.getText()))
        {
            showToast("Name can't be left empty");
            empName.requestFocus();
            return true;
        }
        if (TextUtils.isEmpty(empDob.getText()))
        {
            showToast("Enter Valid Date of Birth");
            showDatePicker();
            return true;
        }
        if (vehicleAvailable.isChecked())
        {
            if (TextUtils.isEmpty(vehicleMake.getText()))
            {
                showToast("Enter vehicle make");
                vehicleMake.requestFocus();
                return true;
            }
            if (TextUtils.isEmpty(vehicleModel.getText()))
            {
                showToast("Enter vehicle model");
                vehicleModel.requestFocus();
                return true;
            }
            if (TextUtils.isEmpty(vehicleNumber.getText()))
            {
                showToast("Enter vehicle number");
                vehicleNumber.requestFocus();
                return true;
            }
        }
            int id = empType.getCheckedRadioButtonId();
            if (id == R.id.partTime)
               return partTimeEmpValidation();
            else if (id == R.id.intern)
                return internEmpValidation();
            else if (id == R.id.fullTime)
                return fullTimeEmpValidation();

        return false;
    }
    private Boolean partTimeEmpValidation()
    {
        if (TextUtils.isEmpty(emp_hr_rate.getText()))
        {
            showToast("Enter employee hours rate");
            employeeSalary.requestFocus();
            return true;
        }
        if (TextUtils.isEmpty(emp_worked_hrs.getText()))
        {
            showToast("Enter employee worked hours");
            employeeBonus.requestFocus();
            return true;
        }
        if (TextUtils.isEmpty(commisionAmout.getText()))
        {
            showToast("Enter commision Amount");
            employeeBonus.requestFocus();
            return true;
        }
        return false;
    }
    private Boolean internEmpValidation()
    {
        if (TextUtils.isEmpty(schoolName.getText()))
        {
            showToast("Enter school name");
            schoolName.requestFocus();
            return true;
        }

        return false;
    }
    private Boolean fullTimeEmpValidation()
    {

        if (TextUtils.isEmpty(employeeSalary.getText()))
        {
            showToast("Enter employee salary");
            employeeSalary.requestFocus();
            return true;
        }
        if (TextUtils.isEmpty(employeeBonus.getText()))
        {
            showToast("Enter employee bonus");
            employeeBonus.requestFocus();
            return true;
        }
        return false;
    }
    private void clearFields()
    {
        empName.setText("");empDob.setText("");commisionAmout.setText("");vehicleMake.setText("");vehicleModel.setText("");vehicleNumber.setText("");schoolName.setText("");employeeSalary.setText("");employeeBonus.setText("");
         emp_hr_rate.setText("");emp_worked_hrs.setText("");
    }
}
