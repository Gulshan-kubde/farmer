package cropulse.io.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "farmlands")

public class FarmLand {
    
 @Id
   private String farmerId;
  
    private int approxAcre;
    private int cents;
    private String name;
    private double area;
    
	

   
}
