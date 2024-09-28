package cropulse.io.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.SoilDTO;
import cropulse.io.entity.Soil;
import cropulse.io.repository.SoilRepository;
import cropulse.io.service.SoilService;

@Service
public class SoilServiceImpl implements SoilService {

    private static final Logger logger = LoggerFactory.getLogger(SoilServiceImpl.class);

    @Autowired
    private SoilRepository soilRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    private static final String SOIL_WITH_ID = "Soil with ID ";
    
    @Override
    public String addSoil(SoilDTO soilDTO) {
        logger.info("Entering method: addSoil with data: {}", soilDTO);
        validateSoil(soilDTO);
        Soil soil = modelMapper.map(soilDTO, Soil.class);
        
        soilRepository.save(soil);
        logger.info("Exiting method: addSoil. Soil created successfully.");
        return "Soil created successfully.";
    }
    
    @Override
    public List<Soil> getAllSoils() {
        logger.info("Entering method: getAllSoils");
        List<Soil> soils = soilRepository.findAll();
        logger.info("Exiting method: getAllSoils with result size: {}", soils.size());
        return soils;
    }

    @Override
    public Optional<Soil> getSoilById(String id) {
        logger.info("Entering method: getSoilById with ID: {}", id);
        Optional<Soil> soil = soilRepository.findById(id);
        
        if (soil.isPresent()) {
            logger.info("Soil found with ID: {}", id);
        } else {
            logger.warn("No Soil found with ID: {}", id);
        }
        
        logger.info("Exiting method: getSoilById");
        return soil;
    }

    @Override
    public String updateSoil(String id, SoilDTO soilDTO) {
        logger.info("Entering method: updateSoil with ID: {} and data: {}", id, soilDTO);
        
        Optional<Soil> existingSoil = soilRepository.findById(id);

        if (!existingSoil.isPresent()) {
            logger.error("{} {} does not exist", SOIL_WITH_ID, id);
            throw new IllegalArgumentException(SOIL_WITH_ID + id + " does not exist.");
        }

        validateSoil(soilDTO);
        Soil soil = modelMapper.map(soilDTO, Soil.class);
        
        soil.setId(id);
        soilRepository.save(soil);
        
        logger.info("Exiting method: updateSoil. Soil updated successfully with ID: {}", id);
        return "Soil updated successfully.";
    }

    @Override
    public String deleteSoil(String id) {
        logger.info("Entering method: deleteSoil with ID: {}", id);
        
        Optional<Soil> existingSoil = soilRepository.findById(id);

        if (!existingSoil.isPresent()) {
            logger.error("{} {} does not exist", SOIL_WITH_ID, id);
            throw new IllegalArgumentException(SOIL_WITH_ID + id + " does not exist.");
        }

        soilRepository.deleteById(id);
        logger.info("Exiting method: deleteSoil. {} {} deleted successfully", SOIL_WITH_ID, id);
        return SOIL_WITH_ID + id + " deleted successfully.";
    }

    private void validateSoil(SoilDTO soil) {
        logger.debug("Validating Soil: {}", soil);
        
       
        String plotName = soil.getPlotName();
        if (plotName == null || plotName.trim().isEmpty()) {
            logger.error("Validation error: PlotName cannot be null or empty.");
            throw new IllegalArgumentException("PlotName cannot be null or empty.");
        }
        
        
        String rainMM = soil.getRainMM();
        if (rainMM == null || rainMM.trim().isEmpty()) {
            logger.error("Validation error: RainMM cannot be null or empty.");
            throw new IllegalArgumentException("RainMM cannot be null or empty.");
        }
        
        
        String soilColour = soil.getSoilColour();
        if (soilColour == null || soilColour.trim().isEmpty()) {
            logger.error("Validation error: SoilColour cannot be null or empty.");
            throw new IllegalArgumentException("SoilColour cannot be null or empty.");
        }

        
        String worm = soil.getWorm();
        if (worm == null || worm.trim().isEmpty()) {
            logger.error("Validation error: Worm cannot be null or empty.");
            throw new IllegalArgumentException("Worm cannot be null or empty.");
        }

       
        String nitrogenPercentage = soil.getNitrogenPercentage();
        if (nitrogenPercentage == null || nitrogenPercentage.trim().isEmpty()) {
            logger.error("Validation error: NitrogenPercentage cannot be null or empty.");
            throw new IllegalArgumentException("NitrogenPercentage cannot be null or empty.");
        }
        
        logger.debug("Validation completed successfully for Soil: {}", soil);
    }

}
