package unisys.com.employeepayroll.model;

import java.io.Serializable;

public abstract class Employee implements IPrintable,Serializable{
    private String id;
    private String name;
    private String age;
    private double earnings;
    private Vehicle vehicle;
    private String dob;
    private String empType;
    public abstract String getSchoolName();
    public Integer calcBirthYear(int age)
    {
        return 2018 - age;
    }
    public double calcEarnings()
    {
        return 1000;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private String schoolName;
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String printMyData()
    {
        return "id: "+id+" name: " +name+" age: "+age +" earnings: "+ earnings +" vehicle:"+ vehicle +" dob: "+dob +" empType: "+empType;

    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
