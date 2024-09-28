package cropulse.io.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "soils")
@Data
public class Soil {
    @Id
    private String id;
    private String plotName;
    private String rainMM;
    private String soilColour;
    private String worm;
    private String nitrogenPercentage;
}
