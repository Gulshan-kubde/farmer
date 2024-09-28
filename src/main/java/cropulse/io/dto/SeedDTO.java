package cropulse.io.dto;

import org.springframework.data.annotation.Id;

import lombok.Data;
@Data
public class SeedDTO {

	@Id
    private String seedId;
    private String seedName;

  
}
