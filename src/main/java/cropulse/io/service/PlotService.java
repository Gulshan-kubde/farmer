package cropulse.io.service;

import java.util.List;
import java.util.Optional;

import cropulse.io.dto.PlotDTO;
import cropulse.io.entity.Plot;

public interface PlotService {

    String addPlot(PlotDTO plotDTO);

    List<Plot> getAllPlots();

    Optional<Plot> getPlotById(String plotId);

    String updatePlot(String plotId, PlotDTO plotDTO);

    String deletePlot(String plotId);
}
