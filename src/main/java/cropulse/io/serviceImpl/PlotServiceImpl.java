package cropulse.io.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.PlotDTO;
import cropulse.io.entity.Plot;
import cropulse.io.repository.PlotRepository;
import cropulse.io.service.PlotService;

@Service
public class PlotServiceImpl implements PlotService {

    private static final Logger logger = LoggerFactory.getLogger(PlotServiceImpl.class);

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public String addPlot(PlotDTO plotDTO) {
        logger.info("Entering method: addPlot with data: {}", plotDTO);

        validatePlot(plotDTO);
        Plot plot = modelMapper.map(plotDTO, Plot.class);
        
        plotRepository.save(plot);
        logger.info("Exiting method: addPlot. Plot created successfully.");
        return "Plot created successfully.";
    }

    @Override
    public List<Plot> getAllPlots() {
        logger.info("Entering method: getAllPlots");
        List<Plot> plots = plotRepository.findAll();
        logger.info("Exiting method: getAllPlots with result size: {}", plots.size());
        return plots;
    }

    @Override
    public Optional<Plot> getPlotById(String plotId) {
        logger.info("Entering method: getPlotById with Plot ID: {}", plotId);
        Optional<Plot> plot = plotRepository.findById(plotId);
        
        if (plot.isPresent()) {
            logger.info("Plot found with ID: {}", plotId);
        } else {
            logger.warn("No Plot found with ID: {}", plotId);
        }

        logger.info("Exiting method: getPlotById");
        return plot;
    }

    @Override
    public String updatePlot(String plotId, PlotDTO plotDTO) {
        logger.info("Entering method: updatePlot with Plot ID: {} and data: {}", plotId, plotDTO);
        
        Optional<Plot> existingPlot = plotRepository.findById(plotId);

        if (!existingPlot.isPresent()) {
            logger.error("Plot with ID {} does not exist", plotId);
            throw new IllegalArgumentException("Plot with ID " + plotId + " does not exist.");
        }

        validatePlot(plotDTO);
        Plot plot = modelMapper.map(plotDTO, Plot.class);
        plot.setPlotId(plotId);
        plotRepository.save(plot);

        logger.info("Exiting method: updatePlot. Plot updated successfully with ID: {}", plotId);
        return "Plot updated successfully.";
    }

    @Override
    public String deletePlot(String plotId) {
        logger.info("Entering method: deletePlot with Plot ID: {}", plotId);

        Optional<Plot> existingPlot = plotRepository.findById(plotId);

        if (!existingPlot.isPresent()) {
            logger.error("Plot with ID {} does not exist", plotId);
            throw new IllegalArgumentException("Plot with ID " + plotId + " does not exist.");
        }

        plotRepository.deleteById(plotId);
        logger.info("Exiting method: deletePlot. Plot with ID {} deleted successfully", plotId);
        return "Plot with ID " + plotId + " deleted successfully.";
    }

    private void validatePlot(PlotDTO plot) {
        logger.debug("Validating Plot: {}", plot);

        String plotName = plot.getPlotName();
        if (plotName == null || plotName.trim().isEmpty()) {
            logger.error("Validation error: Plot name cannot be null or empty.");
            throw new IllegalArgumentException("Plot name cannot be null or empty.");
        }

        logger.debug("Validation completed successfully for Plot: {}", plot);
    }
}
