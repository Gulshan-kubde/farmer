package cropulse.io.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cropulse.io.dto.RoleDTO;
import cropulse.io.entity.Role;
import cropulse.io.repository.RoleRepository;
import cropulse.io.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addRole(RoleDTO roleDTO) {
        validateRole(roleDTO);
        Role role = modelMapper.map(roleDTO, Role.class);
        roleRepository.save(role);
        return "Role created successfully.";
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleById(String roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public String updateRole(String roleId, RoleDTO roleDTO) {
        Optional<Role> existingRole = roleRepository.findById(roleId);

        if (!existingRole.isPresent()) {
            throw new IllegalArgumentException("Role with ID " + roleId + " does not exist.");
        }

        validateRole(roleDTO);
        Role role = modelMapper.map(roleDTO, Role.class);
        role.setRoleId(roleId);
        roleRepository.save(role);

        return "Role updated successfully.";
    }

    @Override
    public String deleteRole(String roleId) {
        Optional<Role> existingRole = roleRepository.findById(roleId);

        if (!existingRole.isPresent()) {
            throw new IllegalArgumentException("Role with ID " + roleId + " does not exist.");
        }

        roleRepository.deleteById(roleId);
        return "Role with ID " + roleId + " deleted successfully.";
    }

    private void validateRole(RoleDTO roleDTO) {
        if (roleDTO.getRoleType() == null || roleDTO.getRoleType().trim().isEmpty()) {
            throw new IllegalArgumentException("RoleType cannot be null or empty.");
        }
    }
}
