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

import cropulse.io.dto.CropTypeDTO;
import cropulse.io.entity.CropType;
import cropulse.io.serviceImpl.CropTypeServiceImpl;

@RestController
@RequestMapping("/api/cropTypes")
public class CropTypeController {

    @Autowired
    private CropTypeServiceImpl cropTypeService;

    
    @PostMapping
    public ResponseEntity<String> createCropType(@RequestBody CropTypeDTO cropTypeDTO) {
        try {
            return new ResponseEntity<>(cropTypeService.addCropType(cropTypeDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  
    @GetMapping
    public ResponseEntity<List<CropType>> getAllCropTypes() {
        List<CropType> cropTypes = cropTypeService.getAllCropTypes();
        return new ResponseEntity<>(cropTypes, HttpStatus.OK);
    }

    
    @GetMapping("/{cropTypeId}")
    public ResponseEntity<CropType> getCropTypeById(@PathVariable String cropTypeId) {
        Optional<CropType> cropType = cropTypeService.getCropTypeById(cropTypeId);

        return cropType.map(ResponseEntity::ok)
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    @PutMapping("/{cropTypeId}")
    public ResponseEntity<String> updateCropType(@PathVariable String cropTypeId, @RequestBody CropTypeDTO cropTypeDTO) {
        try {
            return new ResponseEntity<>(cropTypeService.updateCropType(cropTypeId, cropTypeDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  
    @DeleteMapping("/{cropTypeId}")
    public ResponseEntity<String> deleteCropType(@PathVariable String cropTypeId) {
        try {
            return new ResponseEntity<>(cropTypeService.deleteCropType(cropTypeId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
