package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.database.DatabaseManager;
import com.codecool.shop.dao.implementation.database.SupplierDaoJdbc;
import com.codecool.shop.dao.implementation.mems.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.mems.ProductDaoMem;
import com.codecool.shop.dao.implementation.mems.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebListener
public class Initializer implements ServletContextListener {
    private DatabaseManager databaseManager;
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SupplierDao supplierDao = new SupplierDaoJdbc(dataSource);

        try {
            setupDbManager();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sce.getServletContext().setAttribute("dbManager", databaseManager);

        //setting up a new supplier
        Supplier mag_shelter = new Supplier("Mag_shelter", "Nice cats");
        Supplier netti_shelter = new Supplier("Netti_shelter", "Nice dogs");
        Supplier marci_shelter = new Supplier("Marci_shelter", "Nice rabbits and fishes");

        // save suppliers to db
        databaseManager.saveSupplier(mag_shelter);
        databaseManager.saveSupplier(netti_shelter);
        databaseManager.saveSupplier(marci_shelter);

        //setting up a new product category
        ProductCategory dog = new ProductCategory("Dog", "The dog is a domesticated carnivoran of the family Canidae. They are best friends of people");
        ProductCategory cat = new ProductCategory("Cat", "ASdsa");
        ProductCategory rabbit = new ProductCategory("Rabbit", "ASd");
        ProductCategory fish = new ProductCategory("Fish",  "ASd");

        // save product category to db
        databaseManager.saveProductCategory(dog);
        databaseManager.saveProductCategory(cat);
        databaseManager.saveProductCategory(rabbit);
        databaseManager.saveProductCategory(fish);

        // save product to db
        // dogs
        databaseManager.saveProduct(new Product(dog, netti_shelter,"Max", 300, "USD", "Cute dog"));
        databaseManager.saveProduct(new Product(dog, netti_shelter,"Buddy", 100, "USD", "Cute dog"));
        databaseManager.saveProduct(new Product(dog, netti_shelter,"Ginger", 300, "USD", "Cute dog"));
        databaseManager.saveProduct(new Product(dog, netti_shelter,"Sam", 300, "USD", "Cute dog"));
        databaseManager.saveProduct(new Product(dog, netti_shelter,"Mask", 300, "USD", "Cute dog"));

        // cats
        databaseManager.saveProduct(new Product(cat, mag_shelter,"Cat", 300, "USD", "Cute cat"));
        databaseManager.saveProduct(new Product(cat, mag_shelter,"Cat", 300, "USD", "Cute cat"));
        databaseManager.saveProduct(new Product(cat, mag_shelter,"Cat", 300, "USD", "Cute cat"));
        databaseManager.saveProduct(new Product(cat, mag_shelter,"Cat", 300, "USD", "Cute cat"));

        // fish
        databaseManager.saveProduct(new Product(fish, marci_shelter,"Fish", 300, "USD", "Cute fish"));

        // rabbits
        databaseManager.saveProduct(new Product(rabbit, marci_shelter,"Rabbit", 300, "USD", "Cute rabbit"));
        databaseManager.saveProduct(new Product(rabbit, marci_shelter,"Rabbit", 300, "USD", "Cute rabbit"));
        databaseManager.saveProduct(new Product(rabbit, marci_shelter, "Rabbit", 300, "USD", "Cute rabbit"));
    }

    private void setupDbManager() throws SQLException, IOException {
        databaseManager = new DatabaseManager();
        databaseManager.setup();
    }
}


