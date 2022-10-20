package com.mita.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @Column(name = "Username", length = 50)
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Role")
    private String role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Cart",
            joinColumns = @JoinColumn(name = "Username"),
            inverseJoinColumns = @JoinColumn(name = "ProductCode")
    )
    private List<Product> products;

    public Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public List<Product> setProducts(List<Product> products) {
        return this.products = products;
    }


    public void deleteProduct(Product product) {
        this.products.remove(product);
    }

    public void removeProduct() {
        this.products.clear();
    }
}
