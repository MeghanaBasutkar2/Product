package electron.com.lighting.controller;

import electron.com.lighting.dto.Product;
import electron.com.lighting.dto.SaveProductsRequest;
import electron.com.lighting.service.ExcelHandlerService;
import electron.com.lighting.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ExcelHandlerService excelHandlerService;
    @Autowired
    ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<List<Product>> saveProducts(@ModelAttribute SaveProductsRequest productsRequest) {
        List<Product> savedProducts = excelHandlerService.saveProductsFromExcel(productsRequest.getFile());
        return ResponseEntity.ok(savedProducts);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
}
