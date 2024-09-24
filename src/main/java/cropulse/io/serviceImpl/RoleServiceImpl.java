package cropulse.io.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.RoleDTO;
import cropulse.io.entity.Role;
import cropulse.io.repository.RoleRepository;
import cropulse.io.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addRole(RoleDTO roleDTO) {
        logger.info("Entering method: addRole with data: {}", roleDTO);
        
        validateRole(roleDTO);
        Role role = modelMapper.map(roleDTO, Role.class);
        roleRepository.save(role);
        
        logger.info("Exiting method: addRole. Role created successfully.");
        return "Role created successfully.";
    }

    @Override
    public List<Role> getAllRoles() {
        logger.info("Entering method: getAllRoles");
        List<Role> roles = roleRepository.findAll();
        logger.info("Exiting method: getAllRoles with result size: {}", roles.size());
        return roles;
    }

    @Override
    public Optional<Role> getRoleById(String roleId) {
        logger.info("Entering method: getRoleById with Role ID: {}", roleId);
        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent()) {
            logger.info("Role found with ID: {}", roleId);
        } else {
            logger.warn("No Role found with ID: {}", roleId);
        }

        logger.info("Exiting method: getRoleById");
        return role;
    }

    @Override
    public String updateRole(String roleId, RoleDTO roleDTO) {
        logger.info("Entering method: updateRole with Role ID: {} and data: {}", roleId, roleDTO);

        Optional<Role> existingRole = roleRepository.findById(roleId);

        if (!existingRole.isPresent()) {
            logger.error("Role with ID {} does not exist", roleId);
            throw new IllegalArgumentException("Role with ID " + roleId + " does not exist.");
        }

        validateRole(roleDTO);
        Role role = modelMapper.map(roleDTO, Role.class);
        role.setRoleId(roleId);
        roleRepository.save(role);

        logger.info("Exiting method: updateRole. Role updated successfully with ID: {}", roleId);
        return "Role updated successfully.";
    }

    @Override
    public String deleteRole(String roleId) {
        logger.info("Entering method: deleteRole with Role ID: {}", roleId);

        Optional<Role> existingRole = roleRepository.findById(roleId);

        if (!existingRole.isPresent()) {
            logger.error("Role with ID {} does not exist", roleId);
            throw new IllegalArgumentException("Role with ID " + roleId + " does not exist.");
        }

        roleRepository.deleteById(roleId);
        logger.info("Exiting method: deleteRole. Role with ID {} deleted successfully", roleId);
        return "Role with ID " + roleId + " deleted successfully.";
    }

    private void validateRole(RoleDTO roleDTO) {
        logger.debug("Validating Role: {}", roleDTO);

        if (roleDTO.getRoleType() == null || roleDTO.getRoleType().trim().isEmpty()) {
            logger.error("Validation error: RoleType cannot be null or empty.");
            throw new IllegalArgumentException("RoleType cannot be null or empty.");
        }

        logger.debug("Validation completed successfully for Role: {}", roleDTO);
    }
}
