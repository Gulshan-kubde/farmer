package cropulse.io.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.SensorDTO;
import cropulse.io.entity.SensorData;
import cropulse.io.repository.SensorDataRepository;
import cropulse.io.service.SensorDataService;

@Service
public class SensorDataServiceImpl implements SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    private static final Logger logger = LoggerFactory.getLogger(SensorServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;
    private static final String SENSOR_WITH_ID = "Sensor with ID ";
    
    @Override
    public String addSensor(SensorDTO sensorDTO) {
        logger.info("Entering method: addSensor with parameters: {}", sensorDTO);
        SensorData sensorData = modelMapper.map(sensorDTO, SensorData.class);
        sensorDataRepository.save(sensorData);
        logger.info("Exiting method: addSensor. Sensor created successfully.");
        return "Sensor created successfully.";
    }

    @Override
    public List<SensorData> getAllSensors() {
        logger.info("Entering method: getAllSensors");
        List<SensorData> sensors = sensorDataRepository.findAll();
        logger.info("Exiting method: getAllSensors with result size: {}", sensors.size());
        return sensors;
    }

    @Override
    public Optional<SensorData> getSensorById(String sensorDataId) {
        logger.info("Entering method: getSensorById with ID: {}", sensorDataId);
        Optional<SensorData> sensorData = sensorDataRepository.findById(sensorDataId);
        logger.info("Exiting method: getSensorById");
        return sensorData;
    }

    @Override
    public String updateSensor(String sensorDataId, SensorDTO sensorDTO) {
        logger.info("Entering method: updateSensor with ID: {} and data: {}", sensorDataId, sensorDTO);
        
        Optional<SensorData> existingSensor = sensorDataRepository.findById(sensorDataId);
        if (!existingSensor.isPresent()) {
            logger.error("{} {} does not exist",SENSOR_WITH_ID, sensorDataId);
            throw new IllegalArgumentException(SENSOR_WITH_ID + sensorDataId + " does not exist.");
        }

        SensorData sensorData = modelMapper.map(sensorDTO, SensorData.class);
        sensorData.setSensorDataId(sensorDataId);
        sensorDataRepository.save(sensorData);
        logger.info("Exiting method: updateSensor. Sensor updated successfully with ID: {}", sensorDataId);
        return "Sensor updated successfully.";
    }

    @Override
    public String deleteSensor(String sensorDataId) {
        logger.info("Entering method: deleteSensor with ID: {}", sensorDataId);
        
        Optional<SensorData> existingSensor = sensorDataRepository.findById(sensorDataId);
        if (!existingSensor.isPresent()) {
            logger.error("{} {} does not exist",SENSOR_WITH_ID, sensorDataId);
            throw new IllegalArgumentException(SENSOR_WITH_ID + sensorDataId + " does not exist.");
        }

        sensorDataRepository.deleteById(sensorDataId);
        logger.info("Exiting method: deleteSensor. {} {} deleted successfully",SENSOR_WITH_ID, sensorDataId);
        return SENSOR_WITH_ID+ sensorDataId + " deleted successfully.";
    }
}
