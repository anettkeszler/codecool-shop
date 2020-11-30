package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.mems.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import junit.framework.TestCase;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoTest extends TestCase {
    DataSource dataSource = connect();
    ProductDao productDao = new ProductDaoMem();

    ProductCategory dog = new ProductCategory("dog", "cute dog");
    Supplier supplier1 = new Supplier("netti-shelter", "Animal");

    public ProductDaoTest() throws SQLException {
    }

    public void testAdd() {
        Product product = new Product(dog, supplier1,"dog",100, "USD", "dog is in the air...");
        productDao.add(product);
        List<Product> products = productDao.getAll();
        assertEquals(product.getName(), products.get(0).getName());
    }

    public void testFind() {
        int id = 1;
        Product result = productDao.find(id);
        if (productDao.getAll().size() == 0) {
            assertNull(result);
        } else {
            assertNotNull(result);
        }
    }

    public void testRemove() {
        Product product1 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");
        Product product2 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");
        Product product3 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");

        productDao.add(product1);
        productDao.add(product2);
        productDao.add(product3);

        List<Product> products = productDao.getAll();

        int id = 1;
        productDao.remove(id);

        assertEquals(products.size(), 2);
    }

    public void testGetAll() {
        Product product1 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");
        Product product2 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");
        Product product3 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");

        productDao.add(product1);
        productDao.add(product2);
        productDao.add(product3);

        List<Product> products = productDao.getAll();

        assertEquals(products.size(), 3);
    }

    public void testGetBySupplier() {
        Product product1 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");
        Product product2 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");
        Product product3 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");

        productDao.add(product1);
        productDao.add(product2);
        productDao.add(product3);

        List<Product> productsByGivenShelter = productDao.getBy(supplier1);
        assertEquals(productsByGivenShelter.size(), 3);

    }

    public void testGetByProductCategory() {
        Product product1 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");
        Product product2 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");
        Product product3 = new Product(dog, supplier1, "dog",100, "USD", "dog is in the air...");

        productDao.add(product1);
        productDao.add(product2);
        productDao.add(product3);

        List<Product> productsByGivenProductCategory = productDao.getBy(dog);
        assertEquals(productsByGivenProductCategory.size(), 3);

    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "codecoolshop";
        String user = "AnettKeszler";
        String password = "Almafa22";


        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}