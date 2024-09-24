package cropulse.io.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection = "cropTypes")
@Data
public class CropType {

   @Id
    private String cropTypeId;
    
    private String cropName;

	
}
