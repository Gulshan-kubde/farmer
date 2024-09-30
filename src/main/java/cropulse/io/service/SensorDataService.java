package cropulse.io.service;

import java.util.List;
import java.util.Optional;

import cropulse.io.dto.SensorDataDTO;
import cropulse.io.entity.SensorData;

public interface SensorDataService {
    String addSensor(SensorDataDTO sensorDTO);
    List<SensorData> getAllSensors();
    Optional<SensorData> getSensorById(String sensorDataId);
    String updateSensor(String sensorDataId, SensorDataDTO sensorDTO);
    String deleteSensor(String sensorDataId);
}
