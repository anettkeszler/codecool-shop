package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.mems.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ProductCategoryDaoTest extends TestCase {
    DataSource dataSource = connect();
    ProductCategoryDao productCategoryDao = new ProductCategoryDaoMem();
    public ProductCategoryDaoTest() throws SQLException {
    }

    @Test
    public void testAdd() {
        ProductCategory productCategory1 = new ProductCategory("dog", "cute dog");
        productCategoryDao.add(productCategory1);
        List<ProductCategory> productCategories = productCategoryDao.getAll();
        assertEquals(productCategory1.getName(), productCategories.get(0).getName());
    }

    public void testFind() {
        int id = 1;
        ProductCategory result = productCategoryDao.find(id);
        if (productCategoryDao.getAll().size() == 0) {
            assertNull(result);
        } else {
            assertNotNull(result);
        }
    }

    public void testRemove() {
        ProductCategory productCategory1 = new ProductCategory("dog", "cute dog");
        ProductCategory productCategory2 = new ProductCategory("cat", "cute cat");
        ProductCategory productCategory3 = new ProductCategory("fish", "cute fish");

        productCategoryDao.add(productCategory1);
        productCategoryDao.add(productCategory2);
        productCategoryDao.add(productCategory3);

        List<ProductCategory> productCategories = productCategoryDao.getAll();

        int id = 1;
        productCategoryDao.remove(id);
        if (productCategoryDao.getAll().size() == 0) {
            assertNull(productCategories);
        } else {
            assertEquals(productCategories.size(), 2);
        }
    }

    public void testGetAll() {
        ProductCategory productCategory1 = new ProductCategory("dog", "cute dog");
        ProductCategory productCategory2 = new ProductCategory("cat", "cute cat");
        ProductCategory productCategory3 = new ProductCategory("fish", "cute fish");

        productCategoryDao.add(productCategory1);
        productCategoryDao.add(productCategory2);
        productCategoryDao.add(productCategory3);

        List<ProductCategory> productCategories = productCategoryDao.getAll();

        assertEquals(productCategories.size(), 3);
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