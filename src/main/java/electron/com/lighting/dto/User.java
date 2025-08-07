package electron.com.lighting.dto;

import lombok.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "comments")
public class User {
    @Id
    private String id;

    private String email;
    private String name;
    private String provider; // "google" or "facebook"
    private Instant createdAt;
}
