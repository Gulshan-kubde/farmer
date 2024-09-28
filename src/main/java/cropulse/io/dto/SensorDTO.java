package cropulse.io.dto;

import org.springframework.data.annotation.Id;

import lombok.Data;
@Data
public class SensorDTO {
	@Id
	private String sensorId;
	private String cropType;

	
}
