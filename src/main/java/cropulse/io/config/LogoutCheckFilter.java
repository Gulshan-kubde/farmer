package cropulse.io.config;

import java.io.IOException;

import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cropulse.io.serviceimpl.UserStatusService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class LogoutCheckFilter extends OncePerRequestFilter {

	private final UserStatusService userStatusService;

	public LogoutCheckFilter(UserStatusService userStatusService) {
		this.userStatusService = userStatusService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader("Authorization");

		if (token != null && token.startsWith("Bearer ")) {

			String accessTokenString = token.substring(7);

			String userId = null;
			try {
				AccessToken accessToken = TokenVerifier.create(accessTokenString, AccessToken.class).getToken();
				userId = accessToken.getSubject();
			} catch (VerificationException e) {

				e.printStackTrace();
			}

			if (!userStatusService.isUserLoggedIn(userId)) {

				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is logged out");
				return;
			}
		}

		filterChain.doFilter(request, response);

	}
}
