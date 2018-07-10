package unisys.com.employeepayroll.model;

public abstract class Vehicle {
    private String vehicleMake;
    private String vehicleModel;
    private  String vehicleNumber;
    private  Boolean vehicleInsured;
    private String vehicleType;
    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }


    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Boolean getVehicleInsured() {
        return vehicleInsured;
    }

    public void setVehicleInsured(Boolean vehicleInsured) {
        this.vehicleInsured = vehicleInsured;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
