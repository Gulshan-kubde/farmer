package cropulse.io.dto;

public class FarmLandDTO {

    private String farmerId;
    private int approxAcre;
    private int cents;
    private String name;

    // Getters and Setters
    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public int getApproxAcre() {
        return approxAcre;
    }

    public void setApproxAcre(int approxAcre) {
        this.approxAcre = approxAcre;
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
