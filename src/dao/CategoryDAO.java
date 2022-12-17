/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import databaseutil.DatabaseUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import entities.Category;

/**
 *
 * @author Long
 */
public class CategoryDAO extends DatabaseUtil {

    public static boolean addCategory(Category category) throws Exception {
        try {

            String sql = "INSERT INTO tbl_category VALUE (?,?)";

            PreparedStatement ps = createPreparedStatement(sql);

            ps.setString(1, category.getCategoryId());
            ps.setString(2, category.getCategoryName());

            int kq = ps.executeUpdate();

            System.out.println("Them thanh cong");

            return (kq == 1);
        } catch (Exception e) {
            System.out.println("Loi them du lieu " + e);
        }
        return false;
    }

    public static boolean deleteCategory(String categoryId) throws Exception {
        try {

            String sql = "DELETE FROM tbl_category WHERE category_id = ?";

            PreparedStatement ps = createPreparedStatement(sql);

            ps.setString(1, categoryId);

            int kq = ps.executeUpdate();

            System.out.println("Xoa thanh cong");

            return (kq == 1);
        } catch (Exception e) {
            System.out.println("Loi xoa du lieu " + e);
        }
        return false;
    }

    public static boolean editCategory(String categoryId, Category category) throws Exception {
        try {

            String sql = "UPDATE tbl_category SET category_name = ? WHERE category_id = ?";

            PreparedStatement ps = createPreparedStatement(sql);

            ps.setString(1, category.getCategoryName());
            ps.setString(2, categoryId);

            int kq = ps.executeUpdate();

            System.out.println("Sua thanh cong");

            return (kq == 1);
        } catch (Exception e) {
            System.out.println("Loi sua du lieu " + e);
        }
        return false;
    }

    public static ArrayList<Category> getCategories() throws Exception {

        ArrayList<Category> ds = new ArrayList<>();

        try {

            String sql = "SELECT * FROM tbl_category";

            PreparedStatement ps = createPreparedStatement(sql);

            // int kq = ps.executeUpdate();
            ResultSet data = ps.executeQuery();

            while (data.next()) {
                Category loai = new Category(
                        data.getString(1),
                        data.getString(2));

                ds.add(loai);
            }

            System.out.println("Lay thanh cong");

        } catch (Exception e) {
            System.out.println("Loi lay du lieu " + e);
        }

        return ds;
    }

}
