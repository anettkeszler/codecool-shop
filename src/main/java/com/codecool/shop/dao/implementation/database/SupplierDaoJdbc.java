package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class SupplierDaoJdbc implements SupplierDao {

        private static SupplierDaoJdbc instance;
        private DatabaseManager databaseManager;
        private DataSource dataSource;

        public SupplierDaoJdbc(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        public void add(Supplier supplier) {
            try(Connection conn = dataSource.getConnection()) {
                String sql = "INSERT INTO supplier (name, description) VALUES (?, ?)";
                PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, supplier.getName());
                st.setString(2, supplier.getDescription());
                st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();
                rs.next();
                supplier.setId(rs.getInt(1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public Supplier find(int id) {
            try (Connection connection = dataSource.getConnection()) {
                String sql = "SELECT name, description FROM supplier WHERE id = ?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (!rs.next()) {
                    return null;
                }
                Supplier supplier = new Supplier(rs.getString(1), rs.getString(2));
                supplier.setId(id);
                return supplier;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void remove(int id) {
        }

        @Override
        public List<Supplier> getAll() {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "SELECT id, name, description FROM supplier";
                ResultSet rs = conn.createStatement().executeQuery(sql);
                List<Supplier> result = new ArrayList<>();
                while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                    Supplier supplier = new Supplier(rs.getString(2), rs.getString(3));
                    supplier.setId(rs.getInt(1));
                    result.add(supplier);
                }

                return result;
            } catch (SQLException e) {
                throw new RuntimeException("Error while reading all supplier", e);
            }
        }
    }


