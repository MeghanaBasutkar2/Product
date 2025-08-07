package electron.com.lighting.service;

import electron.com.lighting.dto.Product;
import electron.com.lighting.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelHandlerService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> saveProductsFromExcel(MultipartFile file) {
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Product> products = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Start from row 1 (skip header)
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // todo: add imageUrl generator
                Product product = Product.builder()
                        .title(getStringCell(row, 2))
                        .description(getStringCell(row, 3))
                        .orderCode(getStringCell(row, 4))
                        .price(parsePrice(getStringCell(row, 5)))
                        .categoryId(getStringCell(row, 8))
                        .categoryDescription(getStringCell(row, 9))
                        .name(getStringCell(row, 2))
                        .quantity(100)
                        .imageUrl("default.jpg")
                        .build();

                products.add(product);
            }

            productRepository.saveAll(products);
            return products;
        } catch (Exception e) {
            // todo: change to logs
            e.printStackTrace();
            System.out.println("Upload failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private String getStringCell(Row row, int col) {
        Cell cell = row.getCell(col);
        return cell != null ? cell.toString().trim() : "";
    }

    private Double parsePrice(String priceStr) {
        if (priceStr == null || priceStr.isEmpty()) return 0.0;
        return Double.parseDouble(priceStr.replaceAll("[^\\d.]", "").replace(",", ""));
    }
}
