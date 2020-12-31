package day1;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class demo2_preparedstatement {
    @Test
            //增删改
    public void t1() throws Exception{
        InputStream resourceAsStream = demo2_preparedstatement.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(resourceAsStream);
        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String url=properties.getProperty("url");
        String driverClass=properties.getProperty("driverClass");
        //2加载驱动
        Class.forName(driverClass);
        //3获取链接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
        //4.编译sql语句
        String sql="insert into customers (name,email,birth)values (?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        //5填充占位符
        ps.setString(1,"哪吒");
        ps.setString(2,"nazha@qq.com");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d=sdf.parse("1995-2-28");

        ps.setDate(3,new java.sql.Date(d.getTime()));
        //6执行sql
        ps.execute();
        ps.close();
        connection.close();
    }
}
