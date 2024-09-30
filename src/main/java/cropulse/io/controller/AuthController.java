package cropulse.io.controller;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cropulse.io.dto.LoginDTO;
import cropulse.io.dto.RefreshTokenDto;
import cropulse.io.dto.UserDTO;
import cropulse.io.service.UserAuthService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth/api")
public class AuthController {

	@Autowired
	private UserAuthService userAuthService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
		try {
			return new ResponseEntity<>(userAuthService.registerUser(userDTO), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginDTO loginDTO) {
		try {
			AccessTokenResponse tokenResponse = userAuthService.login(loginDTO);
			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser() {
		try {
			return ResponseEntity.ok(userAuthService.logout());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@PostMapping("/refreshAccessToken")
	public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenDto refreshToken){
		return ResponseEntity.ok(userAuthService.refreshAccessToken(refreshToken.getToken()));
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody UserDTO updateUserDTO) {
		try {
			return new ResponseEntity<>(userAuthService.updateUser(userId, updateUserDTO), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable String userId) {
		try {
			return new ResponseEntity<>(userAuthService.deleteUser(userId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
