package electron.com.lighting.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SaveProductsRequest {
    private MultipartFile file;
}
