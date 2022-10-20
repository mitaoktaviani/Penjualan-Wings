package com.mita.service;

import com.mita.dto.cart.CartDTO;
import com.mita.dto.login.RegisterDTO;
import com.mita.dto.product.ProductGridDTO;
import com.mita.entity.Product;

import java.util.List;

public interface AccountService {
    Boolean checkExistingUser(String users);

    void register(RegisterDTO dto);

    String getAccountRole(String username);

    List<CartDTO> getListProductByUsername(String username, Integer page);

    void deleteProduct(String productCode);

    long getTotalPages(String username);

    List<CartDTO> getListProductByUsername(String username);
}
