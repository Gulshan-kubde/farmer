package cropulse.io.service;

import cropulse.io.dto.SensorDTO;
import cropulse.io.entity.SensorData;

import java.util.List;
import java.util.Optional;

public interface SensorDataService {
    String addSensor(SensorDTO sensorDTO);
    List<SensorData> getAllSensors();
    Optional<SensorData> getSensorById(String sensorDataId);
    String updateSensor(String sensorDataId, SensorDTO sensorDTO);
    String deleteSensor(String sensorDataId);
}
