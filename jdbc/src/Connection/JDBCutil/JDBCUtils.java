package Connection.JDBCutil;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {

 private static ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
    //c3p0获取数据库连接池
    public static Connection getConnection() throws Exception {
        Connection connection = cpds.getConnection();
        System.out.println("连接池连接成功");
        return connection;
    }
    //dbcp获取数据库连接池
    private static DataSource source;
    static {
        try {
            Properties properties = new Properties();
            //方式2
            InputStream is=new FileInputStream("src/dbcp.properties");
            properties.load(is);
             source = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection1()throws Exception{
       Connection connection = source.getConnection();
        System.out.println("success");
        return connection;
    }
    //使用druid创建连接池
    private static DataSource source1;
    static {
        try {
            Properties pros=new Properties();
            InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("Druid.properties");
            pros.load(is);
             source1 = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection2() throws SQLException {

        Connection connection = source.getConnection();
        System.out.println("success");
        return connection;
    }
    public static void closeResource(Connection con, Statement ps){
        try {
            if (ps!=null){
                ps.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        try{
            if(con != null) {

                con.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public static void closeResource(Connection con, Statement ps, ResultSet r){
        try {
            if (ps!=null){
                ps.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        try{
            if(con != null) {

                con.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        try{
            if(r != null) {

                r.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public static void closeResource(Connection con){

        try{
            if(con != null) {

                con.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
