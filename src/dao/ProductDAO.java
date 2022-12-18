/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import databaseutil.DatabaseUtil;
import entities.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class ProductDAO extends DatabaseUtil {

    public static boolean addProduct(Product product) throws Exception {
        try {

            String sql = "INSERT INTO tbl_product VALUE (?,?,?,?,?)";

            PreparedStatement ps = createPreparedStatement(sql);

            ps.setString(1, product.getProductId());
            ps.setString(2, product.getCategoryId());
            ps.setString(3, product.getProductName());
            ps.setString(4, product.getDescription());
            ps.setDouble(5, product.getPrice());

            int kq = ps.executeUpdate();

            System.out.println("Them thanh cong");

            return (kq == 1);
        } catch (Exception e) {
            System.out.println("Loi them du lieu " + e);
        }
        return false;
    }

    public static boolean deleteProduct(String productId) throws Exception {
        try {

            String sql = "DELETE FROM tbl_product WHERE product_id = ?";

            PreparedStatement ps = createPreparedStatement(sql);

            ps.setString(1, productId);

            int kq = ps.executeUpdate();

            System.out.println("Xoa thanh cong");

            return (kq == 1);
        } catch (Exception e) {
            System.out.println("Loi xoa du lieu " + e);
        }
        return false;
    }

    public static boolean editProduct(String productId, Product product) throws Exception {
        try {

            String sql = "UPDATE tbl_product SET "
                    + "category_id = ?, "
                    + "product_name = ?, "
                    + "description = ?, "
                    + "price = ?"
                    + "WHERE product_id = ?";

            PreparedStatement ps = createPreparedStatement(sql);

            ps.setString(1, product.getCategoryId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());
            ps.setString(5, productId);

            int kq = ps.executeUpdate();

            System.out.println("Sua thanh cong");

            return (kq == 1);
        } catch (Exception e) {
            System.out.println("Loi sua du lieu " + e);
        }
        return false;
    }

    public static ArrayList<Product> getAllProduct() throws Exception {

        ArrayList<Product> ds = new ArrayList<>();

        try {

            String sql = "SELECT * FROM tbl_product";

            PreparedStatement ps = createPreparedStatement(sql);

            // int kq = ps.executeUpdate();
            ResultSet data = ps.executeQuery();

            while (data.next()) {
                Product sp = new Product(
                        data.getString(1),
                        data.getString(2),
                        data.getString(3),
                        data.getString(4),
                        Double.parseDouble(data.getString(5)));

                ds.add(sp);
            }

            System.out.println("Lay thanh cong");

        } catch (Exception e) {
            System.out.println("Loi lay du lieu " + e);
        }

        return ds;
    }

    public static ArrayList<Product> getAllProductByTypeId(String categoryId) throws Exception {

        ArrayList<Product> ds = new ArrayList<>();

        try {

            String sql = "SELECT * FROM tbl_product where category_id = ?";

            PreparedStatement ps = createPreparedStatement(sql);

            ps.setString(1, categoryId);

            // int kq = ps.executeUpdate();
            ResultSet data = ps.executeQuery();

            while (data.next()) {
                Product sp = new Product(
                        data.getString(1),
                        data.getString(2),
                        data.getString(3),
                        data.getString(4),
                        Double.parseDouble(data.getString(5)));

                ds.add(sp);
            }

            System.out.println("Lay thanh cong");

        } catch (Exception e) {
            System.out.println("Loi lay du lieu " + e);
        }

        return ds;
    }

    public static ArrayList<Product> searchProduct(String keyword) throws Exception {

        ArrayList<Product> ds = new ArrayList<>();

        try {

            String sql = "SELECT * FROM tbl_product where product_name like \'%" + keyword + "%\'";

            PreparedStatement ps = createPreparedStatement(sql);

            // ps.setString(1, tuKhoa);

            // int kq = ps.executeUpdate();
            ResultSet data = ps.executeQuery();

            while (data.next()) {
                Product sp = new Product(
                        data.getString(1),
                        data.getString(2),
                        data.getString(3),
                        data.getString(4),
                        Double.parseDouble(data.getString(5)));

                ds.add(sp);
            }

            System.out.println("Lay thanh cong");

        } catch (Exception e) {
            System.out.println("Loi lay du lieu " + e);
        }

        return ds;
    }
}
