package com.mita.service;

import com.mita.dto.product.ProductGridDTO;
import com.mita.dto.product.UpsertProductDTO;
import com.mita.entity.TransactionDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<ProductGridDTO> getAll(String productName, Integer page);

    long getTotalPages(String productName);

    UpsertProductDTO getUpdateProduct(String productCode);

    String insertProduct(UpsertProductDTO dto);

    String updateProduct(UpsertProductDTO dto);

    void deleteProduct(String productCode);

    ProductGridDTO getProduct(String productCode);

    TransactionDetail buyProduct(String productCode, String username);

    void addToCart(String productCode);
}
