package cropulse.io.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "cropTypes")
public class CropType {

   @Id
    private String cropTypeId;
    
    private String cropName;

	public String getCropTypeId() {
		return cropTypeId;
	}

	public void setCropTypeId(String cropTypeId) {
		this.cropTypeId = cropTypeId;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	} 
}
