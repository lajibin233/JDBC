package Connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBCP {
    @Test//方式1
    public void test() throws SQLException {
        BasicDataSource source=new BasicDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test");
        source.setUsername("root");
        source.setPassword("zq5201314");
        //设置管理的相关属性
        source.setInitialSize(10);
        source.setMaxActive(10);
        Connection connection = source.getConnection();
        System.out.println(connection);


    }
    //方式2 配置文件
    @Test
    public void t2() throws Exception {
        Properties properties = new Properties();
        //方式1
    //    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        //方式2
        InputStream is=new FileInputStream("src/dbcp.properties");
        properties.load(is);

        DataSource source = BasicDataSourceFactory.createDataSource(properties);
        Connection connection = source.getConnection();
        System.out.println(connection);

    }
}
