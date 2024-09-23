package cropulse.io.dto;

import org.springframework.data.annotation.Id;

public class SeedDTO {

	@Id
    private String seedId;
    private String seedName;

  
    public String getSeedId() {
        return seedId;
    }

    public void setSeedId(String seedId) {
        this.seedId = seedId;
    }

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }
}
