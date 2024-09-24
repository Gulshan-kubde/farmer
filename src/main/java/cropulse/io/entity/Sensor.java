package cropulse.io.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "sensors")
@Data
public class Sensor {
  
    @Id
    private String sensorId;     
    private String cropType;
    
	 
    
    
}
