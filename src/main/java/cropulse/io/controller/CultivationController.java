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

import cropulse.io.dto.CultivationDTO;
import cropulse.io.entity.Cultivation;
import cropulse.io.serviceImpl.CultivationServiceImpl;

@RestController
@RequestMapping("/api/cultivations")
public class CultivationController {

    @Autowired
    private CultivationServiceImpl cultivationService;

    
    @PostMapping
    public ResponseEntity<String> createCultivation(@RequestBody CultivationDTO cultivationDTO) {
        try {
            return new ResponseEntity<>(cultivationService.addCultivation(cultivationDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   
    @GetMapping
    public ResponseEntity<List<Cultivation>> getAllCultivations() {
        List<Cultivation> cultivations = cultivationService.getAllCultivations();
        return new ResponseEntity<>(cultivations, HttpStatus.OK);
    }

    @GetMapping("/{cultivationId}")
    public ResponseEntity<Cultivation> getCultivationById(@PathVariable String cultivationId) {
        Optional<Cultivation> cultivation = cultivationService.getCultivationById(cultivationId);

        return cultivation.map(ResponseEntity::ok)
                          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    @PutMapping("/{cultivationId}")
    public ResponseEntity<String> updateCultivation(@PathVariable String cultivationId, @RequestBody CultivationDTO cultivationDTO) {
        try {
            return new ResponseEntity<>(cultivationService.updateCultivation(cultivationId, cultivationDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cultivationId}")
    public ResponseEntity<String> deleteCultivation(@PathVariable String cultivationId) {
        try {
            return new ResponseEntity<>(cultivationService.deleteCultivation(cultivationId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
