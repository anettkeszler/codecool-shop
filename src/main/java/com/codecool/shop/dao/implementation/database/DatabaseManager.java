package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {
    private static final String DB_PROPERTIES_FILE = "connection.properties";
    SupplierDao supplierDao;
    ProductDao productDao;
    ProductCategoryDao productCategoryDao;

    public void setup() throws SQLException, IOException {
        DataSource dataSource = connect();
        supplierDao = new SupplierDaoJdbc(dataSource);
        productCategoryDao = new ProductCategoryDaoJdbc(dataSource);
        productDao = new ProductDaoJdbc(dataSource);
    }

    public void saveSupplier(Supplier supplier){
        List<Supplier> suppliersList = supplierDao.getAll();
        if (suppliersList.size() < 3) {
            supplierDao.add(supplier);
        }
    }

    public void saveProductCategory(ProductCategory productCategory){
        List<ProductCategory> productCategoriesList = productCategoryDao.getAll();
        if (productCategoriesList.size() < 4) {
            productCategoryDao.add(productCategory);
        }
    }

    public void saveProduct(Product newProduct){
        List<Product> productList = productDao.getAll();
        if (productList.size() < 13) {
            productDao.add(newProduct);
        }
    }

    public SupplierDao getSupplierDao() {
        return supplierDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public ProductCategoryDao getProductCategoryDao() {
        return productCategoryDao;
    }

    private Properties readProperties() throws IOException {
        Properties properties = new Properties();
        System.out.println(properties);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE);
        System.out.println(inputStream);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("file does not exists.");
        }
        return properties;
    }

    private DataSource connect() throws SQLException, IOException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        Properties dbProperties = readProperties();
        String dbName = dbProperties.getProperty("database");
        String user = dbProperties.getProperty("user");
        String password = dbProperties.getProperty("password");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
