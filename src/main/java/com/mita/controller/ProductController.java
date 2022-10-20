package com.mita.controller;

import com.mita.dto.product.ProductGridDTO;
import com.mita.dto.product.UpsertProductDTO;
import com.mita.entity.TransactionDetail;
import com.mita.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String index(@RequestParam(defaultValue = "1")Integer page,
                        @RequestParam(defaultValue = "")String productName, Model model){

        List<ProductGridDTO> grid = productService.getAll(productName,page);

        System.out.println(grid);

        long totalPages = productService.getTotalPages(productName);

        model.addAttribute("currentPage",page);
        model.addAttribute("grid",grid);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("productName", productName);
        model.addAttribute("breadCrumbs","Product List");
        return "product/product-list";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true)String productCode,
                         Model model){

        ProductGridDTO product = productService.getProduct(productCode);

        System.out.println(product);

        model.addAttribute("product",product);
        model.addAttribute("breadCrumbs","Product Detail");

        return "product/product-detail";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false)String productCode,
                             Model model) {

        if (productCode != null) {
            UpsertProductDTO dto = productService.getUpdateProduct(productCode);

            model.addAttribute("product", dto);
            model.addAttribute("type", "update");
            model.addAttribute("breadCrumbs", "Product / Update Product");
        } else {
            UpsertProductDTO dto = new UpsertProductDTO();

            model.addAttribute("product", dto);
            model.addAttribute("type", "insert");
            model.addAttribute("breadCrumbs", "Product / Insert Product");
        }
        return "product/product-form";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("product")UpsertProductDTO dto,
                         BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("type", "insert");
            model.addAttribute("breadCrumbs", "Product / Insert Product");

            return "product/product-form";
        }else{
            productService.insertProduct(dto);

            return "redirect:/product/list";
        }
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("product")UpsertProductDTO dto,
                         BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("type", "insert");
            model.addAttribute("breadCrumbs", "Product / Insert Product");

            return "product/product-form";
        }else{
            productService.updateProduct(dto);

            return "redirect:/product/list";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true)String productCode){
        productService.deleteProduct(productCode);

        return "redirect:/product/list";
    }


    @GetMapping("/buy")
    public String buyProduct(@RequestParam(required = true)String productCode,
                             Model model){

        productService.addToCart(productCode);
        return "redirect:/cart/list";
    }

}
