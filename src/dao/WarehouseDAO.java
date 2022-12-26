package dao;

import java.util.ArrayList;

import databaseutil.DatabaseUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import entities.Warehouse;

public class WarehouseDAO extends DatabaseUtil {
    public static ArrayList<Warehouse> getWarehouse() throws Exception {

        ArrayList<Warehouse> ds = new ArrayList<>();

        try {

            String sql = "SELECT tbl_product.product_id, product_name, quantity FROM tbl_warehouse, tbl_product WHERE tbl_warehouse.product_id = tbl_product.product_id";

            PreparedStatement ps = createPreparedStatement(sql);

            // int kq = ps.executeUpdate();
            ResultSet data = ps.executeQuery();

            while (data.next()) {
                Warehouse item = new Warehouse(
                        data.getString(1),
                        data.getString(2),
                        Integer.parseInt(data.getString(3)));

                ds.add(item);
            }

            System.out.println("Lay thanh cong");

        } catch (Exception e) {
            System.out.println("Loi lay du lieu " + e);
        }

        return ds;
    }

    public static int getWarehouseQuantiy(String id) throws Exception {

        int quantity = -1;

        try {

            String sql = "SELECT quantity FROM tbl_warehouse WHERE product_id = ?";

            PreparedStatement ps = createPreparedStatement(sql);

            ps.setString(1, id);

            // int kq = ps.executeUpdate();
            ResultSet data = ps.executeQuery();

            while (data.next()) {
                quantity = Integer.parseInt(data.getString(1));
            }

            System.out.println("Lay thanh cong");

        } catch (Exception e) {
            System.out.println("Loi lay du lieu " + e);
        }

        return quantity;
    }

    public static boolean editWarehouse(String productId, Warehouse warehouse) throws Exception {
        try {

            String sql = "UPDATE tbl_warehouse SET quantity = ? WHERE product_id = ?";

            PreparedStatement ps = createPreparedStatement(sql);

            ps.setInt(1, warehouse.getQuantity());
            ps.setString(2, productId);

            int kq = ps.executeUpdate();

            System.out.println("Sua thanh cong");

            return (kq == 1);
        } catch (Exception e) {
            System.out.println("Loi sua du lieu " + e);
        }
        return false;
    }

    public static boolean addWarehouse(Warehouse warehouse) throws Exception {
        try {

            String sql = "INSERT INTO tbl_warehouse VALUE (?,?)";

            PreparedStatement ps = createPreparedStatement(sql);

            ps.setString(1, warehouse.getProductId());
            ps.setInt(2, warehouse.getQuantity());

            int kq = ps.executeUpdate();

            System.out.println("thêm thanh cong");

            return (kq == 1);
        } catch (Exception e) {
            System.out.println("thêm sua du lieu " + e);
        }
        return false;
    }

    public static boolean deleteWarehouse(String productId) throws Exception {
        try {

            String sql = "DELETE FROM tbl_warehouse WHERE product_id = ?";

            PreparedStatement ps = createPreparedStatement(sql);

            ps.setString(1, productId);

            int kq = ps.executeUpdate();

            System.out.println("Sua thanh cong");

            return (kq == 1);
        } catch (Exception e) {
            System.out.println("Loi sua du lieu " + e);
        }
        return false;
    }

    public static ArrayList<Warehouse> searchWarehouse(String keyword) throws Exception {

        ArrayList<Warehouse> ds = new ArrayList<>();

        try {

            String sql = "SELECT tbl_product.product_id, product_name, quantity FROM tbl_warehouse, tbl_product "
                    + "WHERE tbl_warehouse.product_id = tbl_product.product_id and product_name like \'%" + keyword
                    + "%\'";

            PreparedStatement ps = createPreparedStatement(sql);

            // ps.setString(1, tuKhoa);

            // int kq = ps.executeUpdate();
            ResultSet data = ps.executeQuery();

            while (data.next()) {
                Warehouse item = new Warehouse(
                        data.getString(1),
                        data.getString(2),
                        Integer.parseInt(data.getString(3)));

                ds.add(item);
            }

            System.out.println("Lay thanh cong");

        } catch (Exception e) {
            System.out.println("Loi lay du lieu " + e);
        }

        return ds;
    }
}
