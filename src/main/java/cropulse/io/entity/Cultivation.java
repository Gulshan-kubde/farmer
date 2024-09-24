package cropulse.io.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "cultivations")
public class Cultivation {
    
   @Id
    private String cultivationId;  
    private String cultivationName;
    
	
}
