package cropulse.io.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "seeds")
public class Seed {
    
   @Id
    private String seedId;  
    private String seedName;
	
}
