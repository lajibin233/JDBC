package JDBCutil;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Connect {
    public static Connection getConnection()throws Exception{
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(resourceAsStream);
        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String url=properties.getProperty("url");
        String driverClass=properties.getProperty("driverClass");
        Class.forName(driverClass);

        java.sql.Connection connection = DriverManager.getConnection(url, user, password);

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
    //关闭
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
}
