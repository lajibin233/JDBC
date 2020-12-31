package day2_事务;

import JDBCutil.Connect;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class demo1 {
    @Test
    public void t1()  {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            //更改链接的自动提交
            connection.setAutoCommit(false);
            String sql1="update user_table set balance=balance-100 where user=?";
            JDBCutil.Update.update(connection,sql1,"AA");
            //模拟网络异常
            System.out.println(10/0);
            String sql2="update user_table set balance=balance+100 where user=?";
            JDBCutil.Update.update(connection,sql2,"BB");
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                //使用完连接后 修改回true  以便下次使用
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JDBCutil.Connect.closeResource(connection,null);

        }


    }
}
