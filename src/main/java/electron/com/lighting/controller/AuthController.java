package electron.com.lighting.controller;

import electron.com.lighting.dto.User;
import electron.com.lighting.service.AuthenticationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Data
    public static class AuthRequest {
        public String token;
        public String provider;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody AuthRequest request) {
        try {
            User user = authenticationService.verifyOAuthToken(request.token, request.provider);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}