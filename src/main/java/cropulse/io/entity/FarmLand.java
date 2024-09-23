package cropulse.io.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "farmlands")

public class FarmLand {
    
 @Id
   private String farmerId;
  
    private int approxAcre;
    private int cents;
    private String name;
    
    
	public String getFarmerId() {
		return farmerId;
	}
	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}
	public int getApproxAcre() {
		return approxAcre;
	}
	public void setApproxAcre(int approxAcre) {
		this.approxAcre = approxAcre;
	}
	public int getCents() {
		return cents;
	}
	public void setCents(int cents) {
		this.cents = cents;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

   
}
