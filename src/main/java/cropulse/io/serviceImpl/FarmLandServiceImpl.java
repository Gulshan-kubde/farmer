package cropulse.io.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.FarmLandDTO;
import cropulse.io.entity.CropPlanning;
import cropulse.io.entity.FarmLand;
import cropulse.io.repository.FarmLandRepository;
import cropulse.io.service.FarmLandService;

@Service
public class FarmLandServiceImpl implements FarmLandService{

	@Autowired
	private FarmLandRepository farmLandRepository;
	
	  
    @Autowired
    private ModelMapper modelMapper;

    @Override
	public String addFarmLand(FarmLandDTO farmLandDTO) {

		validateFarmLand(farmLandDTO);
		FarmLand farmLand = modelMapper.map(farmLandDTO, FarmLand.class);
		farmLandRepository.save(farmLand);

		return "farmLand created succesfully";
	}
    @Override
	public List<FarmLand> getAllFarmLands() {
		return farmLandRepository.findAll();
	}
    @Override
	public Optional<FarmLand> getFarmLandById(String farmerId) {
		return farmLandRepository.findById(farmerId);
	}
    @Override
	public String updateFarmLand(String farmerId, FarmLandDTO farmLandDTO) {

		Optional<FarmLand> existingFarmLand = farmLandRepository.findById(farmerId);

		if (!existingFarmLand.isPresent()) {
			throw new IllegalArgumentException("FarmLand with Farmer ID " + farmerId + " does not exist.");
		}

		validateFarmLand(farmLandDTO);
		FarmLand farmLand = modelMapper.map(farmLandDTO, FarmLand.class);
		farmLand.setFarmerId(farmerId);
		farmLandRepository.save(farmLand);
		
		return "farmLand update succesfully";
	}
    @Override
	public String deleteFarmLand(String farmerId) {
		
		Optional<FarmLand> existingFarmLand = farmLandRepository.findById(farmerId);

		if (!existingFarmLand.isPresent()) {
			throw new IllegalArgumentException("FarmLand with Farmer ID " + farmerId + " does not exist.");
		}

		farmLandRepository.deleteById(farmerId);
		
		return "FarmLand with Farmer ID " + farmerId + " delete succesfully.";
	}

	private void validateFarmLand(FarmLandDTO farmLand) {
		String name = farmLand.getName();
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty.");
		}

		Integer approxAcre = farmLand.getApproxAcre();
		if (approxAcre == null || approxAcre <= 0) {
			throw new IllegalArgumentException("Approx Acre cannot be null or less than or equal to zero.");
		}

		Integer cents = farmLand.getCents();
		if (cents == null || cents < 0) {
			throw new IllegalArgumentException("Cents cannot be null or less than zero.");
		}
	}
}
