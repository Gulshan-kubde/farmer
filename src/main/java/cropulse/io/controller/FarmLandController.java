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

import cropulse.io.dto.FarmLandDTO;
import cropulse.io.entity.FarmLand;
import cropulse.io.serviceImpl.FarmLandServiceImpl;

@RestController
@RequestMapping("/api/farmlands")
public class FarmLandController {

    @Autowired
    private FarmLandServiceImpl farmLandService;

    
    @PostMapping
    public ResponseEntity<String> createFarmLand(@RequestBody FarmLandDTO farmLand) {
       try {
        return new ResponseEntity<>(farmLandService.addFarmLand(farmLand), HttpStatus.CREATED);
       }catch(Exception e) {
    	   return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);  
       }
    }

    
    @GetMapping
    public ResponseEntity<List<FarmLand>> getAllFarmLands() {
        List<FarmLand> farmlands = farmLandService.getAllFarmLands();
        return new ResponseEntity<>(farmlands, HttpStatus.OK);
    }

    @GetMapping("/{farmerId}")
    public ResponseEntity<FarmLand> getFarmLandById(@PathVariable String farmerId) {
        Optional<FarmLand> farmLand = farmLandService.getFarmLandById(farmerId);

        return farmLand.map(ResponseEntity::ok)
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    @PutMapping("/{farmerId}")
    public ResponseEntity<String> updateFarmLand(@PathVariable String farmerId, @RequestBody FarmLandDTO farmLandDTO) {
        try {
        return new ResponseEntity<>(farmLandService.updateFarmLand(farmerId, farmLandDTO), HttpStatus.OK);
        }catch(Exception e) {
     	   return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);  
        }
        
    }

   
    @DeleteMapping("/{farmerId}")
    public ResponseEntity<String> deleteFarmLand(@PathVariable String farmerId) {
       
        try {
        	return new ResponseEntity<>( farmLandService.deleteFarmLand(farmerId), HttpStatus.OK);
        }
        catch(Exception e) {
     	   return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);  
        }
        
    }
}
