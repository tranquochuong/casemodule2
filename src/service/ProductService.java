package service;

import model.Product;
import utils.CSVUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {

    public final static String PATH_PRODUCT ="D:\\CodeGym\\CaseStudy_Java\\untitled\\data\\products.csvv";
    private static ProductService instance;

    private ProductService() {
    }

    public static ProductService getInstance() {
        if (instance == null) instance = new ProductService();
        return instance;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        List<String> lines = CSVUtils.read(PATH_PRODUCT);
        for (String line : lines) {
            products.add(Product.parseProduct(line));
        }
        return products;
    }

    @Override
    public void add(Product newProduct) {
        newProduct.setCreatedAt(Instant.now());
        List<Product> products = findAll();
        products.add(newProduct);
        CSVUtils.write(PATH_PRODUCT, products);
    }

    @Override
    public void update(Product newProduct) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getId() == newProduct.getId()) {
                product.setName(newProduct.getName());
                product.setPrice(newProduct.getPrice());
                product.setQuantity(newProduct.getQuantity());
                product.setUpdatedAt(Instant.now());
                CSVUtils.write(PATH_PRODUCT, products);
                break;
            }
        }
    }
    @Override
    public void removeById(long id) {
        List<Product> products = findAll();
        for (int i = 0; i < products.size(); i++) {
            if ((products.get(i)).getId() == id) {
                products.remove(products.get(i));
            }
        }
        CSVUtils.write(PATH_PRODUCT, products);
    }

    @Override
    public boolean existsByName(String productName) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getName().equals(productName))
                return true;
        }
        return false;
    }

    @Override
    public Product findById(long id) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getId() == id)
                return product;
        }
        return null;
    }

    @Override
    public boolean existById(long id) {
        return findById(id) != null;
    }

    @Override
    public List<Product> findByName(String value) {
        List<Product> products = findAll();
        List<Product> productsFind = new ArrayList<>();
        for (Product item : products) {
            if ((item.getName().toUpperCase()).contains(value.toUpperCase())) {
                productsFind.add(item);
            }
        }
        if (productsFind.isEmpty()) {
            return null;
        }
        return productsFind;
    }

    @Override
    public Product findProductById(long id) {
        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}