package cropulse.io.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.SensorDTO;
import cropulse.io.entity.Sensor;
import cropulse.io.repository.SensorRepository;
import cropulse.io.service.SensorService;

@Service
public class SensorServiceImpl implements SensorService {

    private static final Logger logger = LoggerFactory.getLogger(SensorServiceImpl.class);

    @Autowired
    private SensorRepository sensorRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    private static final String SENSOR_WITH_ID = "Sensor with ID ";
    
    @Override
    public String addSensor(SensorDTO sensorDTO) {
        logger.info("Entering method: addSensor with data: {}", sensorDTO);

        validateSensor(sensorDTO);
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        sensorRepository.save(sensor);

        logger.info("Exiting method: addSensor. Sensor created successfully.");
        return "Sensor created successfully.";
    }

    @Override
    public List<Sensor> getAllSensors() {
        logger.info("Entering method: getAllSensors");
        List<Sensor> sensors = sensorRepository.findAll();
        logger.info("Exiting method: getAllSensors with result size: {}", sensors.size());
        return sensors;
    }

    @Override
    public Optional<Sensor> getSensorById(String sensorId) {
        logger.info("Entering method: getSensorById with Sensor ID: {}", sensorId);
        Optional<Sensor> sensor = sensorRepository.findById(sensorId);

        if (sensor.isPresent()) {
            logger.info("Sensor found with ID: {}", sensorId);
        } else {
            logger.warn("No Sensor found with ID: {}", sensorId);
        }

        logger.info("Exiting method: getSensorById");
        return sensor;
    }

    @Override
    public String updateSensor(String sensorId, SensorDTO sensorDTO) {
        logger.info("Entering method: updateSensor with Sensor ID: {} and data: {}", sensorId, sensorDTO);

        Optional<Sensor> existingSensor = sensorRepository.findById(sensorId);

        if (!existingSensor.isPresent()) {
            logger.error("{} {} does not exist",SENSOR_WITH_ID, sensorId);
            throw new IllegalArgumentException(SENSOR_WITH_ID + sensorId + " does not exist.");
        }

        validateSensor(sensorDTO);
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        sensor.setSensorId(sensorId);
        sensorRepository.save(sensor);

        logger.info("Exiting method: updateSensor. Sensor updated successfully with ID: {}", sensorId);
        return "Sensor updated successfully.";
    }

    @Override
    public String deleteSensor(String sensorId) {
        logger.info("Entering method: deleteSensor with Sensor ID: {}", sensorId);

        Optional<Sensor> existingSensor = sensorRepository.findById(sensorId);

        if (!existingSensor.isPresent()) {
            logger.error(SENSOR_WITH_ID + "{} does not exist", sensorId);
            throw new IllegalArgumentException(SENSOR_WITH_ID + sensorId + " does not exist.");
        }

        sensorRepository.deleteById(sensorId);
        logger.info("Exiting method: deleteSensor. " + SENSOR_WITH_ID + "{} deleted successfully", sensorId);
        return SENSOR_WITH_ID + sensorId + " deleted successfully.";
    }

    private void validateSensor(SensorDTO sensorDTO) {
        logger.debug("Validating Sensor: {}", sensorDTO);

        String sensorName = sensorDTO.getSensorName();
        if (sensorName == null || sensorName.trim().isEmpty()) {
            logger.error("Validation error: CropType cannot be null or empty.");
            throw new IllegalArgumentException("CropType cannot be null or empty.");
        }

        logger.debug("Validation completed successfully for Sensor: {}", sensorDTO);
    }
}
