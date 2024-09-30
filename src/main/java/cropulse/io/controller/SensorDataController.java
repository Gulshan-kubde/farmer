package cropulse.io.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cropulse.io.dto.SensorDataDTO;
import cropulse.io.entity.SensorData;
import cropulse.io.serviceimpl.SensorDataServiceImpl;


@RestController
@RequestMapping("/api/sensors/data")
public class SensorDataController {

	@Autowired
	private SensorDataServiceImpl sensorService;

	@PostMapping
	public ResponseEntity<String> createSensor(@RequestBody SensorDataDTO sensorDTO) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof JwtAuthenticationToken jwtAuth) {
			
			Jwt jwt = jwtAuth.getToken();
			String userId = jwt.getClaimAsString("sub");
			
			String username = jwt.getClaimAsString("preferred_username");
		
		}

		try {
			return new ResponseEntity<>(sensorService.addSensor(sensorDTO), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<SensorData>> getAllSensors() {
		List<SensorData> sensors = sensorService.getAllSensors();
		return new ResponseEntity<>(sensors, HttpStatus.OK);
	}

	@GetMapping("/{sensorDataId}")
	public ResponseEntity<SensorData> getSensorById(@PathVariable String sensorDataId) {
		Optional<SensorData> sensor = sensorService.getSensorById(sensorDataId);
		return sensor.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping("/{sensorDataId}")
	public ResponseEntity<String> updateSensor(@PathVariable String sensorDataId,
			@RequestBody SensorDataDTO sensorDTO) {
		try {
			return new ResponseEntity<>(sensorService.updateSensor(sensorDataId, sensorDTO), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{sensorDataId}")
	public ResponseEntity<String> deleteSensor(@PathVariable String sensorDataId) {
		try {
			return new ResponseEntity<>(sensorService.deleteSensor(sensorDataId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
