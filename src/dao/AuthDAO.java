package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import databaseutil.DatabaseUtil;
import entities.User;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2;

public class AuthDAO extends DatabaseUtil {

    private static String str = "$argon2id$v=19$m=15360,t=2,p=1";
    private static Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 32, 64);

    public static boolean addUser(User user) throws Exception {
        try {

            String sql = "INSERT INTO tbl_user VALUE (?,?)";

            PreparedStatement ps = createPreparedStatement(sql);

            String hashString = argon2.hash(2, 15 * 1024, 1, user.getPassword().toCharArray());
            String hassPassword = hashString.replace(str, "");

            ps.setString(1, user.getUsername());
            ps.setString(2, hassPassword);

            int kq = ps.executeUpdate();

            System.out.println("Them thanh cong");

            return (kq == 1);
        } catch (Exception e) {
            System.out.println("Loi them du lieu " + e);
        }
        return false;
    }

    public static int login(User user) throws Exception {
        try {
            String sql = "SELECT * FROM tbl_user WHERE username = ?";
            PreparedStatement ps = createPreparedStatement(sql);
            ps.setString(1, user.getUsername());
            ResultSet data = ps.executeQuery();
            while (data.next()) {
                String hassPassword = data.getString(2);

                if (!hassPassword.equals("")) {

                    boolean validPassword = argon2.verify(str + hassPassword, user.getPassword().toCharArray());
                    if (validPassword) {
                        System.out.println("Dang nhap thanh cong");
                        return 1;
                    } else {
                        System.out.println("dang nhap that bai, mat khau khong dung");
                        return 2;
                    }
                }
            }
            return 3; // tai khoan khong ton tai

        } catch (Exception e) {
            System.out.println("Dang nhap that bai");
            return 4;
        }
    }
}