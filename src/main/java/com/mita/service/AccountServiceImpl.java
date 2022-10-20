package com.mita.service;

import com.mita.ApplicationUserDetails;
import com.mita.dto.cart.CartDTO;
import com.mita.dto.login.RegisterDTO;
import com.mita.dto.product.ProductGridDTO;
import com.mita.entity.Account;
import com.mita.entity.Product;
import com.mita.repository.AccountRepository;
import com.mita.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final int rowsInPage = 5;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Boolean checkExistingUser(String username) {
        Long totalUser = accountRepository.countUser(username);
        return (totalUser > 0) ? true : false;
    }

    @Override
    public void register(RegisterDTO dto) {
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        String role = "Customer";
        Account account = new Account(
                dto.getUsername(),
                hashPassword,
                role);

        account.setProducts(new LinkedList<>());

        accountRepository.save(account);
    }

    @Override
    public String getAccountRole(String username) {
        Optional<Account> nullableEntity = accountRepository.findById(username);
        Account account = nullableEntity.get();
        return account.getRole();
    }


    @Override
    public List<CartDTO> getListProductByUsername(String username, Integer page) {
        Pageable pagination = PageRequest.of(page-1, rowsInPage, Sort.by("id"));
        List<CartDTO> cartDTOS = productRepository.getProductByUsername(username, pagination);

        BigDecimal discountPrice = new BigDecimal(0);
        BigDecimal subTotal = new BigDecimal(0);
        for (CartDTO cartDTO:cartDTOS) {
            if(cartDTO.getDiscount() != null){
                discountPrice = cartDTO.getPrice().subtract(cartDTO.getPrice().multiply(new BigDecimal(cartDTO.getDiscount())).divide(new BigDecimal(100)));

                subTotal = discountPrice.multiply(new BigDecimal(cartDTO.getQuantity()));

                cartDTO.setDiscountPrice(discountPrice);
                cartDTO.setSubTotal(subTotal);

            }else{
                cartDTO.setDiscountPrice(cartDTO.getPrice());
                cartDTO.setSubTotal(cartDTO.getPrice().multiply(new BigDecimal(cartDTO.getQuantity())));
            }
        }

        return cartDTOS;
    }

    @Override
    public void deleteProduct(String productCode) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findById(username).get();
        Product product = productRepository.findById(productCode).get();
        account.deleteProduct(product);
        productRepository.save(product);
    }

    @Override
    public long getTotalPages(String username) {
        double totalData = (double)(productRepository.countProductByUsername(username));
        long totalPage = (long)(Math.ceil(totalData / rowsInPage));
        return totalPage;
    }

    @Override
    public List<CartDTO> getListProductByUsername(String username) {
        List<CartDTO> cartDTOS = productRepository.getProductByUsername(username);

        return cartDTOS;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> nullableEntity = accountRepository.findById(username);
        Account account = nullableEntity.get();

        System.out.println("Login"+ account);
        return new ApplicationUserDetails(account);
    }
}
