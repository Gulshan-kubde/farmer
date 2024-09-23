package cropulse.io.dto;

import org.springframework.data.annotation.Id;

public class SensorDTO {
	@Id
	private String sensorId;
	private String cropType;

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public String getCropType() {
		return cropType;
	}

	public void setCropType(String cropType) {
		this.cropType = cropType;
	}
}
