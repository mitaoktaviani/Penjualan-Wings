package com.mita.service;

import com.mita.dto.cart.CartDTO;
import com.mita.entity.Account;
import com.mita.entity.Product;
import com.mita.entity.TransactionDetail;
import com.mita.entity.TransactionHeader;
import com.mita.repository.AccountRepository;
import com.mita.repository.ProductRepository;
import com.mita.repository.TransactionDetailRepository;
import com.mita.repository.TransactionHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionHeaderServiceImpl implements TransactionHeaderService{

    @Autowired
    private TransactionHeaderRepository transactionHeaderRepository;

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void confirm() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Account account = accountRepository.findById(username).get();

        List<CartDTO> products = productRepository.getProductByUsername(username);

        BigDecimal discountPrice = new BigDecimal(0);
        BigDecimal subTotal = new BigDecimal(0);
        for (CartDTO cartDTO:products) {
            if(cartDTO.getDiscount() != null){
                discountPrice = cartDTO.getPrice().subtract(cartDTO.getPrice().multiply(new BigDecimal(cartDTO.getDiscount())).divide(new BigDecimal(100)));
                System.out.println(discountPrice);

                subTotal = discountPrice.multiply(new BigDecimal(cartDTO.getQuantity()));
                System.out.println(subTotal);

                cartDTO.setDiscountPrice(discountPrice);
                cartDTO.setSubTotal(subTotal);
            }else{
                cartDTO.setDiscountPrice(cartDTO.getPrice());
                cartDTO.setSubTotal(cartDTO.getPrice().multiply(new BigDecimal(cartDTO.getQuantity())));
            }
        }

        BigDecimal total = new BigDecimal(0);
        for (CartDTO c:products) {
            total = total.add(c.getSubTotal());
        }


        TransactionHeader transactionHeader = new TransactionHeader(
                generateDocumentNumber(),
                "TRX",
                account,
                total,
                LocalDate.now()
        );
        transactionHeaderRepository.save(transactionHeader);

        for (CartDTO cartDTO:products) {
            Optional<Product> theProduct = productRepository.findById(cartDTO.getProductCode());
            Product product = null;
            if(theProduct.isPresent()){
                product = theProduct.get();
            }

            TransactionHeader transactionHeader1 = transactionHeaderRepository.findById(transactionHeader.getDocumentNumber()).get();

            TransactionDetail transactionDetail = new TransactionDetail(
                    transactionHeader.getDocumentCode(), transactionHeader1,
                    product,product.getPrice(),product.getUnit(),
                    cartDTO.getQuantity(), cartDTO.getSubTotal() );

            transactionHeader.addTransactionDetail(transactionDetail);
            transactionDetailRepository.save(transactionDetail);
        }
        account.removeProduct();
        accountRepository.save(account);
    }


    public String generateDocumentNumber(){
        String lastDocumentNumber = transactionHeaderRepository.getHighestDocNumber();
        String documentNumber = "";
        if(lastDocumentNumber == null){
            documentNumber = "001";
        }else{
            int number = Integer.parseInt(lastDocumentNumber) + 1;

            if(number <= 9){
                documentNumber = "00" + number;
            }else if(number <= 99){
                documentNumber = "0" + number;
            }else if(number <= 999){
                documentNumber = Integer.toString(number);
            }
        }
        return documentNumber;
    }
}
