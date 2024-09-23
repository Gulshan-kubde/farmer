package cropulse.io.service;

import java.util.List;
import java.util.Optional;

import cropulse.io.dto.RoleDTO;
import cropulse.io.entity.Role;

public interface RoleService {
    
    String addRole(RoleDTO roleDTO);

    List<Role> getAllRoles();

    Optional<Role> getRoleById(String roleId);

    String updateRole(String roleId, RoleDTO roleDTO);

    String deleteRole(String roleId);
}
