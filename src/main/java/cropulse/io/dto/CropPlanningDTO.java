package cropulse.io.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class CropPlanningDTO {

    private String id;
    private String plot;
    private String cropType;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date sowingDate;
    private String cropYield;
    private String seedsUsed;
    private String revenue;
    private String cultivation;

    
}
