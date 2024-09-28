package cropulse.io.service;

import java.util.List;
import java.util.Optional;

import cropulse.io.dto.SoilDTO;
import cropulse.io.entity.Soil;

public interface SoilService {
    String addSoil(SoilDTO soilDTO);
    List<Soil> getAllSoils();
    Optional<Soil> getSoilById(String id);
    String updateSoil(String id, SoilDTO soilDTO);
    String deleteSoil(String id);
}
