package day1;

import JDBCutil.Connect;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class demo5_批量操作 {
    @Test
    public void t1() throws Exception {
        //批量插入
        Connection connection = null;
        PreparedStatement ps= null;
        try {
            connection = Connect.getConnection();
            String sql="insert into goods (name)values(?)";
            ps = connection.prepareStatement(sql);
            for (int i = 1; i < 20000; i++) {
                    ps.setObject(1,"name_"+i);

                    ps.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,ps);

        }
    }
    @Test
    public void t2(){
        //批量插入快速方法
       /* 修改1： 使用 addBatch() / executeBatch() / clearBatch()
        * 修改2：mysql服务器默认是关闭批处理的，我们需要通过一个参数，让mysql开启批处理的支持。
        * ?rewriteBatchedStatements=true 写在配置文件的url后面
        * 修改3：使用更新的mysql 驱动：mysql-connector-java-5.1.37-bin.jar

        */
        Connection connection = null;
        PreparedStatement ps= null;
        try {
            connection = Connect.getConnection();
            String sql="insert into goods (name)values(?)";
            ps = connection.prepareStatement(sql);
            for (int i = 1; i <= 200000; i++) {
                ps.setObject(1,"name_"+i);
                //把sql语句执行存放在一起
                ps.addBatch();
                if (i % 1000 ==0){
                    //执行batch
                    ps.executeBatch();
                    //清空batch
                    ps.clearBatch();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,ps);

        }
    }
    @Test
    public void t3(){
        //批量插入快速方法
        /* 修改1： 使用 addBatch() / executeBatch() / clearBatch()
         * 修改2：mysql服务器默认是关闭批处理的，我们需要通过一个参数，让mysql开启批处理的支持。
         * ?rewriteBatchedStatements=true 写在配置文件的url后面
         * 修改3：使用更新的mysql 驱动：mysql-connector-java-5.1.37-bin.jar

         */
        Connection connection = null;
        PreparedStatement ps= null;
        try {
            connection = Connect.getConnection();
            String sql="insert into goods (name)values(?)";
            ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (int i = 1; i <= 200000; i++) {
                ps.setObject(1,"name_"+i);
                //把sql语句执行存放在一起
                ps.addBatch();
                if (i % 500 ==0){
                    //执行batch
                    ps.executeBatch();
                    //清空batch
                    ps.clearBatch();
                }
            }
                connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,ps);

        }
    }
}
