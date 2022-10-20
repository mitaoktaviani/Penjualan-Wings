package com.mita.controller;

import com.mita.dto.cart.CartDTO;
import com.mita.service.AccountService;
import com.mita.service.ProductService;
import com.mita.service.TransactionHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionHeaderService transactionHeaderService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1")Integer page, Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<CartDTO> products = accountService.getListProductByUsername(username, page);

        long totalPages = accountService.getTotalPages(username);

        System.out.println(products);

        model.addAttribute("breadCrumbs", "Cart");
        model.addAttribute("cart",products);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",totalPages);

        return "cart/cart-list";
    }

    @GetMapping("/buy")
    public String buyProduct(@RequestParam(required = true)String productCode,
                             Model model){

        productService.addToCart(productCode);
        return "redirect:/cart/list";
    }

    @GetMapping("/delete")
    public String deleteCart(@RequestParam(required = true)String productCode){

        accountService.deleteProduct(productCode);
        return "redirect:/cart/list";
    }


    @GetMapping("/checkOut")
    public String checkOut(@RequestParam(defaultValue = "1")Integer page,Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<CartDTO> products = accountService.getListProductByUsername(username, page);

        BigDecimal total = new BigDecimal(0);

        for (CartDTO cart:products) {
            total = total.add(cart.getSubTotal());
        }
        long totalPages = accountService.getTotalPages(username);

        model.addAttribute("product",products);
        model.addAttribute("breadCrumbs","Check Out");
        model.addAttribute("total",total);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage", page);

        return "cart/checkout";
    }

    @GetMapping("/confirm")
    public String confirm(Model model){

        transactionHeaderService.confirm();

        return "redirect:/product/list";
    }

}
