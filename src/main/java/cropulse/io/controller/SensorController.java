package cropulse.io.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cropulse.io.dto.SensorDTO;
import cropulse.io.entity.Sensor;
import cropulse.io.serviceImpl.SensorServiceImpl;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorServiceImpl sensorService;

    
    @PostMapping
    public ResponseEntity<String> createSensor(@RequestBody SensorDTO sensorDTO) {
        try {
            return new ResponseEntity<>(sensorService.addSensor(sensorDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping
    public ResponseEntity<List<Sensor>> getAllSensors() {
        List<Sensor> sensors = sensorService.getAllSensors();
        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }

  
    @GetMapping("/{sensorId}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable String sensorId) {
        Optional<Sensor> sensor = sensorService.getSensorById(sensorId);

        return sensor.map(ResponseEntity::ok)
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    @PutMapping("/{sensorId}")
    public ResponseEntity<String> updateSensor(@PathVariable String sensorId, @RequestBody SensorDTO sensorDTO) {
        try {
            return new ResponseEntity<>(sensorService.updateSensor(sensorId, sensorDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @DeleteMapping("/{sensorId}")
    public ResponseEntity<String> deleteSensor(@PathVariable String sensorId) {
        try {
            return new ResponseEntity<>(sensorService.deleteSensor(sensorId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
