package cropulse.io.dto;

import lombok.Data;

@Data
public class FarmLandDTO {

    private String farmerId;
    private int approxAcre;
    private int cents;
    private String name;
    private double area;

   
}
