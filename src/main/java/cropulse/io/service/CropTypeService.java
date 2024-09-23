package cropulse.io.service;

import java.util.List;
import java.util.Optional;

import cropulse.io.dto.CropTypeDTO;
import cropulse.io.entity.CropType;

public interface CropTypeService {

    String addCropType(CropTypeDTO cropTypeDTO);

    List<CropType> getAllCropTypes();

    Optional<CropType> getCropTypeById(String cropTypeId);

    String updateCropType(String cropTypeId, CropTypeDTO cropTypeDTO);

    String deleteCropType(String cropTypeId);
}
