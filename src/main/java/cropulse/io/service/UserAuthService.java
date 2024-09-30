package cropulse.io.service;

import org.keycloak.representations.AccessTokenResponse;

import cropulse.io.dto.LoginDTO;
import cropulse.io.dto.UserDTO;

public interface UserAuthService {
    String registerUser(UserDTO userDTO);
    AccessTokenResponse login(LoginDTO loginDTO);
    String updateUser(String userId, UserDTO updateUserDTO);
    String deleteUser(String userId);
    String logout() ;
    String refreshAccessToken(String refreshToken);
}
