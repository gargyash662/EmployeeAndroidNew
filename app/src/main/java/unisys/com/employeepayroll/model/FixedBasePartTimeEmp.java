package unisys.com.employeepayroll.model;

public class FixedBasePartTimeEmp extends PartTimeEmp {
    public double getFixedAmount() {
        return fixedAmount;
    }

    private double fixedAmount;


    @Override
    public double calcEarnings() {
        return getEmpHrRate()*getEmpWorkedHrs() + fixedAmount;
    }

    public void setFixedAmount(double fixedAmount) {
        this.fixedAmount = fixedAmount;
    }
}
