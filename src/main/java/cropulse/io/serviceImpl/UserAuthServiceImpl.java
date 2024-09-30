package cropulse.io.serviceimpl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.keycloak.TokenVerifier;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import cropulse.io.dto.LoginDTO;
import cropulse.io.dto.UserDTO;
import cropulse.io.service.UserAuthService;
import cropulse.io.utility.KeycloakUtitlity;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

	@Value("${keycloak.realm}")
	private String realm;

	@Autowired
	private KeycloakUtitlity keycloakUtitlity;

	@Autowired
	private UserStatusService userStatusService;

	@Override
	public String registerUser(UserDTO userDTO) {
		logger.info("Entering method: registerUser with data: {}", userDTO);
		Keycloak keycloak = keycloakUtitlity.createKeycloakInstance();

		try {
			UserRepresentation user = new UserRepresentation();
			user.setUsername(userDTO.getUsername());
			user.setEmail(userDTO.getEmail());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEnabled(true);

			CredentialRepresentation credential = new CredentialRepresentation();
			credential.setTemporary(false);
			credential.setType(CredentialRepresentation.PASSWORD);
			credential.setValue(userDTO.getPassword());
			user.setCredentials(Collections.singletonList(credential));

			Response response = keycloak.realm(realm).users().create(user);

			if (response.getStatus() == 201) {
				String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

				RoleRepresentation role = keycloak.realm(realm).roles().get(userDTO.getRole()).toRepresentation();
				keycloak.realm(realm).users().get(userId).roles().realmLevel().add(Collections.singletonList(role));

				logger.info("User registered successfully with role: {}", userDTO.getRole());
				return "User registered successfully with role: " + userDTO.getRole();
			} else {
				logger.error("Failed to create user. Status: {}", response.getStatus());
				return "Failed to create user. Status: " + response.getStatus();
			}
		} catch (Exception e) {
			logger.error("Error registering user: {}", e.getMessage(), e);
			throw new RuntimeException("Error registering user: " + e.getMessage(), e);
		} finally {
			keycloak.close();
		}
	}

	@Override
	public AccessTokenResponse login(LoginDTO loginDTO) {
		logger.info("Entering method: login with data: {}", loginDTO);
		Keycloak keycloakInstance = keycloakUtitlity.getKeycloakInstance(loginDTO);

		try {
			AccessTokenResponse tokenResponse = keycloakInstance.tokenManager().grantToken();
			logger.info("User logged in successfully.");

			String accessTokenString = tokenResponse.getToken();

			AccessToken accessToken = TokenVerifier.create(accessTokenString, AccessToken.class).getToken();

			String userId = accessToken.getSubject();
			logger.info("User ID: {}", userId);

			userStatusService.logUserIn(userId);
			
			return tokenResponse;
		} catch (Exception e) {
			logger.error("Error logging in: {}", e.getMessage(), e);
			throw new RuntimeException("Error logging in: " + e.getMessage(), e);
		} finally {
			keycloakInstance.close();
		}
	}

	@Override
	public String updateUser(String userId, UserDTO updateUserDTO) {
		logger.info("Entering method: updateUser with User ID: {} and data: {}", userId, updateUserDTO);
		Keycloak keycloak = keycloakUtitlity.createKeycloakInstance();

		try {
			UserResource userResource = keycloak.realm(realm).users().get(userId);
			UserRepresentation userRepresentation = userResource.toRepresentation();

			if (updateUserDTO.getFirstName() != null) {
				userRepresentation.setFirstName(updateUserDTO.getFirstName());
			}
			if (updateUserDTO.getLastName() != null) {
				userRepresentation.setLastName(updateUserDTO.getLastName());
			}
			if (updateUserDTO.getEmail() != null) {
				userRepresentation.setEmail(updateUserDTO.getEmail());
			}

			if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().isEmpty()) {
				CredentialRepresentation credential = new CredentialRepresentation();
				credential.setTemporary(false);
				credential.setType(CredentialRepresentation.PASSWORD);
				credential.setValue(updateUserDTO.getPassword());
				userResource.resetPassword(credential);
			}

			if (updateUserDTO.getRole() != null) {

				List<RoleRepresentation> existingRoles = userResource.roles().realmLevel().listAll();
				for (RoleRepresentation existingRole : existingRoles) {
					userResource.roles().realmLevel().remove(Collections.singletonList(existingRole));
				}

				RoleRepresentation newRole = keycloak.realm(realm).roles().get(updateUserDTO.getRole())
						.toRepresentation();
				userResource.roles().realmLevel().add(Collections.singletonList(newRole)); // Add new role
			}

			userResource.update(userRepresentation);
			logger.info("User updated successfully.");
			return "User updated successfully";
		} catch (NotFoundException e) {
			logger.error("User not found: {}", e.getMessage());
			throw new RuntimeException("User not found", e);
		} catch (Exception e) {
			logger.error("Error updating user: {}", e.getMessage(), e);
			throw new RuntimeException("Error updating user: " + e.getMessage(), e);
		} finally {
			keycloak.close();
		}
	}

	@Override
	public String deleteUser(String userId) {
		logger.info("Entering method: deleteUser with User ID: {}", userId);
		Keycloak keycloak = keycloakUtitlity.createKeycloakInstance();

		try {
			UserResource userResource = keycloak.realm(realm).users().get(userId);
			userResource.remove();
			logger.info("User deleted successfully.");
			return "User deleted successfully.";
		} catch (NotFoundException e) {
			logger.error("User not found: {}", e.getMessage());
			throw new RuntimeException("User not found.", e);
		} catch (Exception e) {
			logger.error("Error deleting user: {}", e.getMessage(), e);
			throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
		} finally {
			keycloak.close();
		}
	}

	@Override
	public String logout() {
		logger.info("Entering method: logout ");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof JwtAuthenticationToken jwtAuth) {

			Jwt jwt = jwtAuth.getToken();
			String userId = jwt.getClaimAsString("sub");
			userStatusService.logUserOut(userId);
			logger.info("User Logout  successfully with id : {}", userId);
			return "User Logout Succesfully";
		}

		return "User does not logout";

	}
	
	@Override
	public String refreshAccessToken(String refreshToken) {
	        try {
	            String refreshUrl = keycloakUtitlity.getServerUrl() + "/realms/" + keycloakUtitlity.getRealm() + "/protocol/openid-connect/token";

	            
	            Map<Object, Object> data = new HashMap<>();
	            data.put("client_id", keycloakUtitlity.getClientId());
	            data.put("client_secret", keycloakUtitlity.getClientSecret());
	            data.put("grant_type", "refresh_token");
	            data.put("refresh_token", refreshToken);

	            
	            HttpClient client = HttpClient.newHttpClient();

	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(refreshUrl))
	                    .header("Content-Type", "application/x-www-form-urlencoded")
	                    .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(data)))
	                    .build();

	            
	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	           
	            if (response.statusCode() == 200) {
	               
	            	logger.info("Access token refreshed successfully.");
	            	logger.info("Response: " + response.body());
	                return response.body(); 
	            } else {
	              
	            	logger.error("Failed to refresh access token. Response code: " + response.statusCode());
	            	logger.error("Error response: " + response.body());
	                return null;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    
	    private String getFormDataAsString(Map<Object, Object> data) {
	        return data.entrySet()
	                .stream()
	                .map(entry -> entry.getKey() + "=" + entry.getValue())
	                .collect(Collectors.joining("&"));
	    }
	

}
