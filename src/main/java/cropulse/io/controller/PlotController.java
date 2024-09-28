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

import cropulse.io.dto.PlotDTO;
import cropulse.io.entity.Plot;
import cropulse.io.serviceimpl.PlotServiceImpl;

@RestController
@RequestMapping("/api/plots")
public class PlotController {

    @Autowired
    private PlotServiceImpl plotService;


    @PostMapping
    public ResponseEntity<String> createPlot(@RequestBody PlotDTO plotDTO) {
        try {
            return new ResponseEntity<>(plotService.addPlot(plotDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping
    public ResponseEntity<List<Plot>> getAllPlots() {
        List<Plot> plots = plotService.getAllPlots();
        return new ResponseEntity<>(plots, HttpStatus.OK);
    }

  
    @GetMapping("/{plotId}")
    public ResponseEntity<Plot> getPlotById(@PathVariable String plotId) {
        Optional<Plot> plot = plotService.getPlotById(plotId);

        return plot.map(ResponseEntity::ok)
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    @PutMapping("/{plotId}")
    public ResponseEntity<String> updatePlot(@PathVariable String plotId, @RequestBody PlotDTO plotDTO) {
        try {
            return new ResponseEntity<>(plotService.updatePlot(plotId, plotDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  
    @DeleteMapping("/{plotId}")
    public ResponseEntity<String> deletePlot(@PathVariable String plotId) {
        try {
            return new ResponseEntity<>(plotService.deletePlot(plotId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
