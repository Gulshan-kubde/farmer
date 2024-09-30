package cropulse.io.entity;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Document(collection = "crop_planning")
@Data
public class CropPlanning {

    @Id
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
