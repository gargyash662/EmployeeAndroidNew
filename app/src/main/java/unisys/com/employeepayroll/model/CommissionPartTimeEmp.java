package unisys.com.employeepayroll.model;

public  class CommissionPartTimeEmp extends PartTimeEmp {
    public double getCommision() {
        return commision;
    }

    private double commision;

    @Override
    public double calcEarnings() {
        return getEmpHrRate()*getEmpWorkedHrs() + commision;
    }

    public void setCommision(double commision) {
        this.commision = commision;
    }
    public String printMyData()
    {
        return "commison "+ commision;
    }
}
