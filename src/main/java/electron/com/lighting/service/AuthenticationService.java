package electron.com.lighting.service;

import electron.com.lighting.dto.User;
import electron.com.lighting.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Value("${facebook.app-token}")
    private String facebookAppToken;

    private final WebClient webClient = WebClient.create();

    public User verifyOAuthToken(String idToken, String provider) {
        return switch (provider.toLowerCase()) {
            case "google" -> verifyGoogle(idToken);
            case "facebook" -> verifyFacebook(idToken);
            default -> throw new RuntimeException("Unsupported provider: " + provider);
        };
    }

    // todo: change the object to specific type, tbd
    private User verifyGoogle(String idToken) {
        Map<String, Object> response = webClient
                .get()
                .uri("https://oauth2.googleapis.com/tokeninfo?id_token={idToken}", idToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null || response.get("email") == null)
            throw new RuntimeException("Invalid Google token");

        String email = (String) response.get("email");
        String name = (String) response.getOrDefault("name", "Google User");

        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(email)
                        .name(name)
                        .provider("google")
                        .createdAt(Instant.now())
                        .build()));
    }

    private User verifyFacebook(String idToken) {
        // Validate token
        Map<String, Object> debugResponse = webClient
                .get()
                .uri("https://graph.facebook.com/debug_token?input_token={token}&access_token={appToken}", idToken, facebookAppToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Object> data = (Map<String, Object>) debugResponse.get("data");
        if (data == null || !(Boolean) data.get("is_valid"))
            throw new RuntimeException("Invalid Facebook token");

        // Get user info
        Map<String, Object> userInfo = webClient
                .get()
                .uri("https://graph.facebook.com/me?fields=id,name,email&access_token={token}", idToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(email)
                        .name(name)
                        .provider("facebook")
                        .createdAt(Instant.now())
                        .build()));
    }
}
