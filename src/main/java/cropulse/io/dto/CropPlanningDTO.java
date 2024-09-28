package cropulse.io.dto;

import java.util.Date;

import lombok.Data;
@Data
public class CropPlanningDTO {

    private String id;
    private String plot;
    private String cropType;
    private Date sowingDate;
    private String cropYield;
    private String seedsUsed;
    private String revenue;
    private String cultivation;

    
}
