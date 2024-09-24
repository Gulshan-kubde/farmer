package cropulse.io.controller;

import java.util.Collections;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cropulse.io.dto.LoginDTO;
import cropulse.io.dto.UserDTO;
import cropulse.io.utility.KeycloakUtitlity;

@RestController
@RequestMapping("/auth/api")
public class AuthController {

	@Value("${keycloak.realm}")
	private String realm;

	@Autowired
	private KeycloakUtitlity keycloakUtitlity;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {

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

		System.out.println("Creating the user...");
		Keycloak keycloak = keycloakUtitlity.createKeycloakInstance();
		Response response = keycloak.realm(realm).users().create(user);

		if (response.getStatus() == 201) {

			String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

			System.out.println("User created with ID: " + userId);

			RoleRepresentation role = keycloak.realm(realm).roles().get(userDTO.getRole()).toRepresentation();
			keycloak.realm(realm).users().get(userId).roles().realmLevel().add(Collections.singletonList(role));

			return ResponseEntity.ok("User registered successfully with role: " + userDTO.getRole());
		} else {

			return ResponseEntity.status(response.getStatus())
					.body("Failed to create user. Status: " + response.getStatus());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginDTO loginDTO) {

		Keycloak keycloakInstance = keycloakUtitlity.getKeycloakInstance(loginDTO);

		AccessTokenResponse tokenResponse = keycloakInstance.tokenManager().grantToken();

		return ResponseEntity.ok(tokenResponse);
		
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody UserDTO updateUserDTO) {
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

			userResource.update(userRepresentation);

			return ResponseEntity.ok("User updated successfully");

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating user: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable String userId) {
		Keycloak keycloak = keycloakUtitlity.createKeycloakInstance();

		try {

			UserResource userResource = keycloak.realm(realm).users().get(userId);

			userResource.remove();

			return ResponseEntity.ok("User deleted successfully.");
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting user: " + e.getMessage());
		}
	}

	@PreAuthorize("hasRole('CLIENT_ADMIN')")
	@GetMapping("/gm")
	public String gm() {
		return "good morning";
	}

	@GetMapping("/gn")
	@PreAuthorize("hasRole('CLIENT_USER')")
	public String gn() {
		return "good night";
	}

}
