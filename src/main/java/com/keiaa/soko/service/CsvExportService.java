package com.keiaa.soko.service;

import com.keiaa.soko.model.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class CsvExportService {

    @Autowired
    private ProductService productService;

    public void writeProductsToCsv(Writer writer, String name, String category, Double minPrice, Double maxPrice, String sortBy, String sortOrder) throws IOException {
        List<Product> products = productService.findProducts(name, category, minPrice, maxPrice, sortBy, sortOrder);

        String[] headers = {"ID", "Item ID", "Name", "Quantity", "Price", "Category"};

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers))) {
            for (Product product : products) {
                csvPrinter.printRecord(
                    product.getId(),
                    product.getItemId(),
                    product.getItemName(),
                    product.getItemQty(),
                    product.getItemPrice(),
                    product.getItemCategory()
                );
            }
        }
    }
}

