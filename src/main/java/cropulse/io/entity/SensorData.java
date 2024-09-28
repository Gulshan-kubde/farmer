package cropulse.io.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "sensorData")
public class SensorData {

    @Id
    private String sensorDataId;
    private String sensorName;
    private String cropName;

  
}
