package cropulse.io.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "plots")
public class Plot {

  @Id
    private String plotId;
    
    private String plotName;

	
}

