package cropulse.io.service;

import java.util.List;
import java.util.Optional;

import cropulse.io.dto.SensorDTO;
import cropulse.io.entity.Sensor;

public interface SensorService {

    String addSensor(SensorDTO sensorDTO);

    List<Sensor> getAllSensors();

    Optional<Sensor> getSensorById(String sensorId);

    String updateSensor(String sensorId, SensorDTO sensorDTO);

    String deleteSensor(String sensorId);
}
