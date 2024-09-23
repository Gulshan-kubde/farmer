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

import cropulse.io.dto.SeedDTO;
import cropulse.io.entity.Seed;
import cropulse.io.serviceImpl.SeedServiceImpl;

@RestController
@RequestMapping("/api/seeds")
public class SeedController {

    @Autowired
    private SeedServiceImpl seedService;

 
    @PostMapping
    public ResponseEntity<String> createSeed(@RequestBody SeedDTO seedDTO) {
        try {
            return new ResponseEntity<>(seedService.addSeed(seedDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping
    public ResponseEntity<List<Seed>> getAllSeeds() {
        List<Seed> seeds = seedService.getAllSeeds();
        return new ResponseEntity<>(seeds, HttpStatus.OK);
    }

    
    @GetMapping("/{seedId}")
    public ResponseEntity<Seed> getSeedById(@PathVariable String seedId) {
        Optional<Seed> seed = seedService.getSeedById(seedId);

        return seed.map(ResponseEntity::ok)
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{seedId}")
    public ResponseEntity<String> updateSeed(@PathVariable String seedId, @RequestBody SeedDTO seedDTO) {
        try {
            return new ResponseEntity<>(seedService.updateSeed(seedId, seedDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @DeleteMapping("/{seedId}")
    public ResponseEntity<String> deleteSeed(@PathVariable String seedId) {
        try {
            return new ResponseEntity<>(seedService.deleteSeed(seedId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
