package com.mita.repository;

import com.mita.dto.cart.CartDTO;
import com.mita.dto.product.ProductGridDTO;
import com.mita.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String > {

    @Query("""
            SELECT new com.mita.dto.product.ProductGridDTO(
            pro.productCode, pro.productName, pro.price, pro.currency,(pro.price - (pro.price * pro.discount / 100)), pro.dimension, pro.unit, pro.imagePath)
            FROM Product AS pro
            WHERE pro.productName LIKE %:productName%
            """)
    List<ProductGridDTO> getAll(@Param("productName") String productName, Pageable pagination);

    @Query("""
            SELECT COUNT(*)
            FROM Product AS pro
            WHERE pro.productName LIKE %:productName%
            """)
    long countProduct(@Param("productName") String productName);

    @Query("""
            SELECT new com.mita.dto.product.ProductGridDTO(
            pro.productCode, pro.productName, pro.price, pro.currency,(pro.price - (pro.price * pro.discount / 100)), pro.dimension, pro.unit, pro.imagePath)
            FROM Product AS pro
            WHERE pro.productCode = :productCode
            """)
    ProductGridDTO getProduct(@Param("productCode") String productCode);

    @Query("""
            SELECT new com.mita.dto.cart.CartDTO(
            pro.productCode, acc.username, pro.productName, COUNT(pro.productCode), pro.price,pro.discount, pro.unit)
            FROM Product AS pro
            INNER JOIN pro.accounts AS acc
            WHERE acc.username = :username
            GROUP BY pro.productCode, acc.username, pro.productName,  pro.price,pro.discount, pro.unit
            """)
    List<CartDTO> getProductByUsername(@Param("username") String username, Pageable pagination);

    @Query("""
            SELECT COUNT(*)
            FROM Product AS pro
            INNER JOIN pro.accounts AS acc
            WHERE acc.username = :username
            """)
    long countProductByUsername(@Param("username") String username);

    @Query("""
            SELECT new com.mita.dto.cart.CartDTO(
            pro.productCode, acc.username, pro.productName, COUNT(pro.productCode), pro.price,pro.discount, pro.unit)
            FROM Product AS pro
            INNER JOIN pro.accounts AS acc
            WHERE acc.username = :username
            GROUP BY pro.productCode, acc.username, pro.productName,  pro.price,pro.discount, pro.unit
           
            """)
    List<CartDTO> getProductByUsername(String username);
}
