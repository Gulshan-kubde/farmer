package cropulse.io.service;

import java.util.List;
import java.util.Optional;

import cropulse.io.dto.CultivationDTO;
import cropulse.io.entity.Cultivation;

public interface CultivationService {

    String addCultivation(CultivationDTO cultivationDTO);

    List<Cultivation> getAllCultivations();

    Optional<Cultivation> getCultivationById(String cultivationId);

    String updateCultivation(String cultivationId, CultivationDTO cultivationDTO);

    String deleteCultivation(String cultivationId);
}
