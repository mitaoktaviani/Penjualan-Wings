package com.mita.service;

import com.mita.dto.product.ProductGridDTO;
import com.mita.dto.product.UpsertProductDTO;
import com.mita.entity.Account;
import com.mita.entity.Product;
import com.mita.entity.TransactionDetail;
import com.mita.entity.TransactionHeader;
import com.mita.repository.AccountRepository;
import com.mita.repository.ProductRepository;
import com.mita.repository.TransactionDetailRepository;
import com.mita.repository.TransactionHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final int rowsInPage = 5;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Autowired
    private TransactionHeaderRepository transactionHeaderRepository;

    @Override
    public List<ProductGridDTO> getAll(String productName, Integer page) {
        Pageable pagination = PageRequest.of(page-1,rowsInPage,Sort.by("id"));

        List<ProductGridDTO> grid = productRepository.getAll(productName,pagination);

        return grid;
    }

    @Override
    public long getTotalPages(String productName) {
        double totalData = (double)(productRepository.countProduct(productName));
        long totalPage = (long)(Math.ceil(totalData / rowsInPage));
        return totalPage;
    }

    @Override
    public UpsertProductDTO getUpdateProduct(String productCode) {
        Optional<Product> theProduct = productRepository.findById(productCode);
        Product product = null;
        if(theProduct.isPresent()){
            product = theProduct.get();
        }

        UpsertProductDTO dto = new UpsertProductDTO(
                product.getProductCode(),
                product.getProductName(),
                product.getPrice(),
                product.getCurrency(),
                product.getDiscount(),
                product.getDimension(),
                product.getUnit(),
                product.getImagePath()
        );
        return dto;
    }

    @Override
    public String insertProduct(UpsertProductDTO dto) {
        Product product = new Product(
                dto.getProductCode(),
                dto.getProductName(),
                dto.getPrice(),
                dto.getCurrency(),
                dto.getDiscount(),
                dto.getDimension(),
                dto.getUnit(),
                dto.getImagePath()
        );

        productRepository.save(product);

        return product.getProductCode();
    }

    @Override
    public String updateProduct(UpsertProductDTO dto) {

        Product product = new Product(
                dto.getProductCode(),
                dto.getProductName(),
                dto.getPrice(),
                dto.getCurrency(),
                dto.getDiscount(),
                dto.getDimension(),
                dto.getUnit(),
                dto.getImagePath()
        );

        productRepository.save(product);

        return product.getProductCode();
    }

    @Override
    public void deleteProduct(String productCode) {
        productRepository.deleteById(productCode);
    }

    @Override
    public ProductGridDTO getProduct(String productCode) {

        ProductGridDTO product = productRepository.getProduct(productCode);

        return product;
    }

    @Override
    public void addToCart(String productCode) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findById(username).get();

        Product product = productRepository.findById(productCode).get();
        List<Product> products = account.getProducts();

        products.add(product);

        account.setProducts(products);

        accountRepository.save(account);

    }



}
