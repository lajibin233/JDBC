package day1;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class demo1_connection {
    @Test
    public void t1() throws SQLException {
        Driver driver=new com.mysql.jdbc.Driver();
        String url="jdbc:mysql://localhost:3306/test";
        Properties info=new Properties();
        info.setProperty("user","root");
        info.setProperty("password","zq5201314");

        Connection connect = driver.connect(url, info);
        System.out.println(connect);

    }
    @Test
    public void t2() throws Exception {
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
         Driver driver= (Driver) clazz.getDeclaredConstructor().newInstance();
         String url ="jdbc:mysql://localhost:3306/test";
         Properties info =new Properties();
         info.setProperty("user","root");
         info.setProperty("password","zq5201314");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);

    }
    @Test
    public void t3() throws Exception{
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver= (Driver) clazz.getDeclaredConstructor().newInstance();

        DriverManager.registerDriver(driver);
        String url="jdbc:mysql://localhost:3306/test";
        String user="root";
        String password="zq5201314";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
    @Test
    public void t4() throws Exception{
        String url="jdbc:mysql://localhost:3306/test";
        String user="root";
        String password="zq5201314";
       //加载驱动，
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
  //      Driver driver= (Driver) clazz.getDeclaredConstructor().newInstance();

  //   DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
    @Test
    public void t5() throws Exception {
        //1.读取配置文件
        InputStream resourceAsStream = demo1_connection.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(resourceAsStream);
        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String url=properties.getProperty("url");
        String driverClass=properties.getProperty("driverClass");
        Class.forName(driverClass);

        Connection connection = DriverManager.getConnection(url, user, password);

        System.out.println(connection);

    }
}
