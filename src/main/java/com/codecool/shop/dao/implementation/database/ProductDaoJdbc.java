package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    public Object object;
    private DataSource dataSource;
    private SupplierDao supplierDao;
    private ProductCategoryDao productCategoryDao;
    private Product product;

    public ProductDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
        this.supplierDao = new SupplierDaoJdbc(dataSource);
        this.productCategoryDao = new ProductCategoryDaoJdbc(dataSource);
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public void add(Product product) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO product (product_category_id, supplier_id, name, price, currency, description) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, product.getProductCategory().getId());
            st.setInt(2, product.getSupplier().getId());
            st.setString(3, product.getName());
            st.setFloat(4, product.getDefaultPrice());
            st.setString(5, product.getDefaultCurrency());
            st.setString(6, product.getDescription());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            product.setId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT product_category_id, supplier_id, name, price, currency, description FROM product WHERE product_category_id = ? AND supplier_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            int productCategoryId = rs.getInt(1);
            int supplierId = rs.getInt(2);
            Supplier supplier = supplierDao.find(supplierId);
            ProductCategory productCategory = productCategoryDao.find(productCategoryId);
            Product product = new Product(productCategory, supplier, rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getString(6));
            product.setId(rs.getInt(1));
            return product;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, product_category_id, supplier_id, name, price, currency, description FROM product";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read products
                int id = rs.getInt(1);
                int productCategoryId = rs.getInt(2);
                int supplierId = rs.getInt(3);
                String name = rs.getString(4);
                float price = rs.getFloat(5);
                String currency = rs.getString(6);
                String description = rs.getString(7);

                Supplier supplier = supplierDao.find(supplierId);
                ProductCategory productCategory = productCategoryDao.find(productCategoryId);
                Product product = new Product(productCategory, supplier, name, price,currency, description);
                product.setId(id);
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all supplier", e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }

}