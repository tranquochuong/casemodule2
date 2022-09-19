package service;

import model.Product;

import java.util.List;


public interface IProductService {
    List<Product> findAll();

    void add(Product newProduct);

    void update(Product newProduct);

    void removeById(long id);

    boolean existsByName(String productName);

    Product findById(long id);

    boolean existById(long id);

    List<Product> findByName(String value);

    Product findProductById(long id);
}