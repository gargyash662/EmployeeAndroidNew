package unisys.com.employeepayroll.model;

public class FullTimeEmp extends Employee {
    private double bonus;
    private double salary;
    @Override
    public String getSchoolName() {
        return "";
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public double calcEarnings() {
        return bonus+salary;
    }
}
