package cropulse.io.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.CultivationDTO;
import cropulse.io.entity.Cultivation;
import cropulse.io.repository.CultivationRepository;
import cropulse.io.service.CultivationService;

@Service
public class CultivationServiceImpl implements CultivationService {

    @Autowired
    private CultivationRepository cultivationRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addCultivation(CultivationDTO cultivationDTO) {
        validateCultivation(cultivationDTO);
        
        Cultivation cultivation = modelMapper.map(cultivationDTO, Cultivation.class);
        
        cultivationRepository.save(cultivation);
        return "Cultivation created successfully.";
    }

    @Override
    public List<Cultivation> getAllCultivations() {
        return cultivationRepository.findAll();
    }

    @Override
    public Optional<Cultivation> getCultivationById(String cultivationId) {
        return cultivationRepository.findById(cultivationId);
    }

    @Override
    public String updateCultivation(String cultivationId, CultivationDTO cultivationDTO) {
        Optional<Cultivation> existingCultivation = cultivationRepository.findById(cultivationId);

        if (!existingCultivation.isPresent()) {
            throw new IllegalArgumentException("Cultivation with ID " + cultivationId + " does not exist.");
        }

        validateCultivation(cultivationDTO);
        Cultivation cultivation = modelMapper.map(cultivationDTO, Cultivation.class);
        
        cultivation.setCultivationId(cultivationId);
        cultivationRepository.save(cultivation);

        return "Cultivation updated successfully.";
    }
    @Override
    public String deleteCultivation(String cultivationId) {
        Optional<Cultivation> existingCultivation = cultivationRepository.findById(cultivationId);

        if (!existingCultivation.isPresent()) {
            throw new IllegalArgumentException("Cultivation with ID " + cultivationId + " does not exist.");
        }

        cultivationRepository.deleteById(cultivationId);
        return "Cultivation with ID " + cultivationId + " deleted successfully.";
    }

   
    private void validateCultivation(CultivationDTO cultivation) {
        String cultivationName = cultivation.getCultivationName();
        if (cultivationName == null || cultivationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Cultivation name cannot be null or empty.");
        }
    }
}
