package cropulse.io.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "cultivations")
public class Cultivation {
    
   @Id
    private String cultivationId;  
    private String cultivationName;
    
	public String getCultivationId() {
		return cultivationId;
	}
	public void setCultivationId(String cultivationId) {
		this.cultivationId = cultivationId;
	}
	public String getCultivationName() {
		return cultivationName;
	}
	public void setCultivationName(String cultivationName) {
		this.cultivationName = cultivationName;
	} 
}
