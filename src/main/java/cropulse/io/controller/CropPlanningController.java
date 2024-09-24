package cropulse.io.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import cropulse.io.dto.CropPlanningDTO;
import cropulse.io.entity.CropPlanning;
import cropulse.io.serviceImpl.CropPlanningServiceImpl;

@RestController
@RequestMapping("/api/crop_planning")
public class CropPlanningController {
	
	 
    @Autowired
    private CropPlanningServiceImpl cropPlanningService;

    
    @PostMapping
    public ResponseEntity<String> createCropPlanning(@RequestBody CropPlanningDTO cropPlanningDTO) {
        try {
            return new ResponseEntity<>(cropPlanningService.addCropPlanning(cropPlanningDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<CropPlanning>> getAllCropPlannings() {
        List<CropPlanning> cropPlannings = cropPlanningService.getAllCropPlannings();
        return new ResponseEntity<>(cropPlannings, HttpStatus.OK);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<CropPlanning> getCropPlanningById(@PathVariable String id) {
        Optional<CropPlanning> cropPlanning = cropPlanningService.getCropPlanningById(id);

        return cropPlanning.map(ResponseEntity::ok)
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCropPlanning(@PathVariable String id, @RequestBody CropPlanningDTO cropPlanningDTO) {
        try {
            return new ResponseEntity<>(cropPlanningService.updateCropPlanning(id, cropPlanningDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCropPlanning(@PathVariable String id) {
        try {
            return new ResponseEntity<>(cropPlanningService.deleteCropPlanning(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
