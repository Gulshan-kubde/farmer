package cropulse.io.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cropulse.io.dto.SoilDTO;
import cropulse.io.entity.Soil;
import cropulse.io.serviceimpl.SoilServiceImpl;

@RestController
@RequestMapping("/api/soils")
public class SoilController {

    @Autowired
    private SoilServiceImpl soilService;

    @PostMapping
    public ResponseEntity<String> createSoil(@RequestBody SoilDTO soilDTO) {
        try {
            return new ResponseEntity<>(soilService.addSoil(soilDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Soil>> getAllSoils() {
        List<Soil> soils = soilService.getAllSoils();
        return new ResponseEntity<>(soils, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Soil> getSoilById(@PathVariable String id) {
        Optional<Soil> soil = soilService.getSoilById(id);
        return soil.map(ResponseEntity::ok)
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSoil(@PathVariable String id, @RequestBody SoilDTO soilDTO) {
        try {
            return new ResponseEntity<>(soilService.updateSoil(id, soilDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSoil(@PathVariable String id) {
        try {
            return new ResponseEntity<>(soilService.deleteSoil(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
