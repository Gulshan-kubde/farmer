package cropulse.io.service;

import java.util.List;
import java.util.Optional;

import cropulse.io.dto.FarmLandDTO;
import cropulse.io.entity.FarmLand;

public interface FarmLandService {

    String addFarmLand(FarmLandDTO farmLand);

    List<FarmLand> getAllFarmLands();

    Optional<FarmLand> getFarmLandById(String farmerId);

    String updateFarmLand(String farmerId, FarmLandDTO farmLand);

    String deleteFarmLand(String farmerId);
}
