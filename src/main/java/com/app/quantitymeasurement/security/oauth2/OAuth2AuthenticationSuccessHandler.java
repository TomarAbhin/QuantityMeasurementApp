package com.app.quantitymeasurement.security.oauth2;

import com.app.quantitymeasurement.security.JwtUtils;
import com.app.quantitymeasurement.security.UserDetailsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        // Generate JWT based on the authenticated user's username
        String token = jwtUtils.generateTokenFromUsername(userPrincipal.getUsername());

        // Redirect to React frontend app with JWT as a URL variable or hash
        // We will pass the username, email, and id too to mock the /signin JWT response locally if needed.
        // Easiest is to just pass token, username, email in URL query parameters so frontend can construct standard localStorage "user" object.
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/oauth2/redirect")
                .queryParam("token", token)
                .queryParam("id", userPrincipal.getId())
                .queryParam("username", userPrincipal.getUsername())
                .queryParam("email", userPrincipal.getEmail())
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
