package cropulse.io.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.SensorDTO;
import cropulse.io.entity.Sensor;
import cropulse.io.repository.SensorRepository;
import cropulse.io.service.SensorService;

@Service
public class SensorServiceImpl implements SensorService{

    @Autowired
    private SensorRepository sensorRepository;
    
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public String addSensor(SensorDTO sensorDTO) {
        validateSensor(sensorDTO);
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        
        sensorRepository.save(sensor);
        return "Sensor created successfully.";
    }

    @Override
    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    @Override
    public Optional<Sensor> getSensorById(String sensorId) {
        return sensorRepository.findById(sensorId);
    }

    @Override
    public String updateSensor(String sensorId, SensorDTO sensorDTO) {
        Optional<Sensor> existingSensor = sensorRepository.findById(sensorId);

        if (!existingSensor.isPresent()) {
            throw new IllegalArgumentException("Sensor with ID " + sensorId + " does not exist.");
        }

        validateSensor(sensorDTO);
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        
        sensor.setSensorId(sensorId);
        sensorRepository.save(sensor);

        return "Sensor updated successfully.";
    }

    @Override
    public String deleteSensor(String sensorId) {
        Optional<Sensor> existingSensor = sensorRepository.findById(sensorId);

        if (!existingSensor.isPresent()) {
            throw new IllegalArgumentException("Sensor with ID " + sensorId + " does not exist.");
        }

        sensorRepository.deleteById(sensorId);
        return "Sensor with ID " + sensorId + " deleted successfully.";
    }

    
    private void validateSensor(SensorDTO sensor) {
        String cropType = sensor.getCropType();
        if (cropType == null || cropType.trim().isEmpty()) {
            throw new IllegalArgumentException("CropType cannot be null or empty.");
        }
    }
}
