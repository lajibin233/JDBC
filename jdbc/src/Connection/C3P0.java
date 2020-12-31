package Connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0 {
    @Test
    public void t1() throws Exception {
        //方式1 直接
        //cpds即为c3p0数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "com.mysql.jdbc.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/test" );
        cpds.setUser("root");
        cpds.setPassword("zq5201314");
        //通过设置相关参数对连接池进行管理
        cpds.setInitialPoolSize(10);//初始连接池连接数
        Connection connection = cpds.getConnection();
        System.out.println(connection);

        DataSources.destroy(cpds);//摧毁连接池
    }
    @Test
    public void t2() throws SQLException {
        //方式2  写入到配置文件
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
        Connection connection = cpds.getConnection();
        System.out.println(connection);
    }
}
