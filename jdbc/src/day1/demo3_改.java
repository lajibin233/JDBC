package day1;

import JDBCutil.Connect;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class demo3_改 {
    //增删改统一语句
    public static void update(String sql, Object... arg_s) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Connect.getConnection();
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < arg_s.length; i++) {
                ps.setObject(i + 1, arg_s[i]);
            }
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.closeResource(connection, ps);
        }
    }
    @Test
    public void t(){

    }

    @Test
    public void t0() {
//        String sql="delete from customers where id = ?";
//        update(sql,3);
        String sql = "update `order` set order_name = ? where order_id=?";
        update(sql,"DD",2);


    }

    @Test
    public void t1() {

        try {
            Connection connection = Connect.getConnection();
            String sql = "update customers set name= ? where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "莫扎特2");
            ps.setInt(2, 18);
            ps.execute();
            Connect.closeResource(connection, ps);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
