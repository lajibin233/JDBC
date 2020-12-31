package dbutils;

import Connection.JDBCutil.JDBCUtils;
import JDBCutil.Connect;
import bean.Customer;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class QueryRunnerTest {
    @Test //增删改
    public void t1() {

        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection2();
            String sql = "insert into customers (name,email,birth)values(?,?,?)";
            int i = runner.update(connection, sql, "蔡徐坤", "caixuken@qq.com", "1992-3-2");
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection);
        }
    }

    @Test
    public void Q1() {
        //查询
        //BeanHandle 是ResultSetHandler 接口的实现类，用于封装表中的一条记录
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection2();
            String sql = "select id,name,email,birth from customers where id =?";
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);

            Customer customer = runner.query(connection, sql, handler, 21);
            System.out.println(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.closeResource(connection);


    }

    @Test
    public void Q2() {
        //查询
        //BeanListHandle 是ResultSetHandler 接口的实现类，用于封装表中的多条记录构成的集合
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection2();
            String sql = "select id,name,email,birth from customers where id <?";
            BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);

            List<Customer> list = runner.query(connection, sql, handler, 22);
            list.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JDBCUtils.closeResource(connection);
    }

    @Test
    public void Q3() {
        //查询
        //MapHandle 是ResultSetHandler 接口的实现类，用于对应表中的一条记录，字段及值作为key=value
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection2();
            String sql = "select id,name,email,birth from customers where id =?";
            MapHandler handler = new MapHandler();

            Map<String, Object> map = runner.query(connection, sql, handler, 21);
            System.out.println(map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.closeResource(connection);


    }

    @Test
    public void Q4() {
        //查询
        //MapListHandle 是ResultSetHandler 接口的实现类，用于对应表中的多条记录，
        // 返回包含多个Map集合的List集合  字段及值作为key=value
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection2();
            String sql = "select id,name,email,birth from customers where id <?";
            MapListHandler handler = new MapListHandler();

            List<Map<String, Object>> list = runner.query(connection, sql, handler, 21);
            list.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.closeResource(connection);


    }

    @Test
    public void Q5() {
        //查询
        //ScalarHandle 是ResultSetHandler 接口的实现类，用于对应表中的一个数值，

        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection2();
            String sql = "select count(*) from customers ";
            ScalarHandler handler = new ScalarHandler();

            Object query = runner.query(connection, sql, handler);
            System.out.println(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.closeResource(connection);
    }

    @Test
    public void Q6() {
        //查询
        //ScalarHandle 是ResultSetHandler 接口的实现类，用于对应表中的一个数值，

        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection2();
            String sql = "select max(birth) from customers ";
            ScalarHandler handler = new ScalarHandler();

            Object query = runner.query(connection, sql, handler);
            System.out.println(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.closeResource(connection);
    }


    @Test
    public void Q7() {

        //自定义 ResultSetHandler 接口的实现类，用于对应表中的一个数值，

        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection2();
            String sql = "select id,name,email,birth from customers where id=?";

            ResultSetHandler<Customer> handler=new ResultSetHandler<Customer>() {
                @Override
                public Customer handle(ResultSet resultSet) throws SQLException {
                    return null;
                }
            };
            Customer customer = runner.query(connection, sql, handler, 21);
            System.out.println(customer);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                DbUtils.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

}
