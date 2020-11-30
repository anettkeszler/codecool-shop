package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.mems.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import junit.framework.TestCase;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class SupplierDaoTest extends TestCase {
    DataSource dataSource = connect();
    SupplierDao supplierDao = new SupplierDaoMem();
    public SupplierDaoTest() throws SQLException {
    }


    public void testAdd() {
        Supplier supplier1 = new Supplier("netti-shelter", "Animal");
        supplierDao.add(supplier1);
        List<Supplier> suppliers = supplierDao.getAll();
        assertEquals(supplier1.getName(), suppliers.get(0).getName());
    }

    // get/find supplier by id
    public void testFind() {
        int id = 1;
         Supplier result = supplierDao.find(id);
         if (supplierDao.getAll().size() == 0) {
             assertNull(result);
         } else {
             assertNotNull(result);
         }
    }

    public void testRemove() {

        Supplier supplier1 = new Supplier("netti-shelter", "Animal");
        Supplier supplier2 = new Supplier("mag-shelter", "Animal");

        supplierDao.add(supplier1);
        supplierDao.add(supplier2);
        List<Supplier> suppliers = supplierDao.getAll();


        int id = 1;
        supplierDao.remove(id);
        if (supplierDao.getAll().size() == 0) {
            assertNull(suppliers);
        } else {
            assertEquals(suppliers.size(), 1);
        }
    }

    public void testGetAll() {
        Supplier supplier1 = new Supplier("netti-shelter", "Animal");
        Supplier supplier2 = new Supplier("mag-shelter", "Animal");

        supplierDao.add(supplier1);
        supplierDao.add(supplier1);
        List<Supplier> suppliers = supplierDao.getAll();

        assertEquals(suppliers.size(), 2);

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