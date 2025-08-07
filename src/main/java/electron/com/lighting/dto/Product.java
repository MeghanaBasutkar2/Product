package electron.com.lighting.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;

    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String category;
    private String imageUrl;
    private String title;
    private String orderCode;
    private String categoryId;
    private String categoryDescription;
}
