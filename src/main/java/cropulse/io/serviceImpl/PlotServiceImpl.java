package cropulse.io.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.PlotDTO;
import cropulse.io.entity.Plot;
import cropulse.io.repository.PlotRepository;
import cropulse.io.service.PlotService;

@Service
public class PlotServiceImpl implements PlotService{

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public String addPlot(PlotDTO plotDTO) {
        validatePlot(plotDTO);
        Plot plot = modelMapper.map(plotDTO, Plot.class);
        
        plotRepository.save(plot);
        return "Plot created successfully.";
    }

    
    @Override
    public List<Plot> getAllPlots() {
        return plotRepository.findAll();
    }

    @Override
    public Optional<Plot> getPlotById(String plotId) {
        return plotRepository.findById(plotId);
    }

    @Override
    public String updatePlot(String plotId, PlotDTO plotDTO) {
        Optional<Plot> existingPlot = plotRepository.findById(plotId);

        if (!existingPlot.isPresent()) {
            throw new IllegalArgumentException("Plot with ID " + plotId + " does not exist.");
        }

        validatePlot(plotDTO);
        Plot plot = modelMapper.map(plotDTO, Plot.class);
        
        plot.setPlotId(plotId);
        plotRepository.save(plot);

        return "Plot updated successfully.";
    }

    @Override
    public String deletePlot(String plotId) {
        Optional<Plot> existingPlot = plotRepository.findById(plotId);

        if (!existingPlot.isPresent()) {
            throw new IllegalArgumentException("Plot with ID " + plotId + " does not exist.");
        }

        plotRepository.deleteById(plotId);
        return "Plot with ID " + plotId + " deleted successfully.";
    }

    
    private void validatePlot(PlotDTO plot) {
        String plotName = plot.getPlotName();
        if (plotName == null || plotName.trim().isEmpty()) {
            throw new IllegalArgumentException("Plot name cannot be null or empty.");
        }
    }
}
