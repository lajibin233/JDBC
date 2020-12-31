package BaseDAO.Junit;

import BaseDAO.dao.CustomerDaoImpl;
import JDBCutil.Connect;
import Connection.JDBCutil.JDBCUtils;
import bean.Customer;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerDaoImplTest {

    CustomerDaoImpl dao= new CustomerDaoImpl();

    @Test
    public void insert() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            Customer customer = new Customer(1,"张飞","sda@123.com",new Date(123123123L));
            dao.insert(connection,customer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,null);

        }
    }

    @Test
    public void deleteById() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
             dao.deleteById(connection,20);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,null);

        }
    }

    @Test
    public void updateById() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            Customer customer = new Customer(21,"张飞","zhangfei@123.com",new Date(1231123123L));
            dao.updateById(connection,customer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,null);

        }
    }

    @Test
    public void selectById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection2();
            System.out.println(dao.selectById(connection,13));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,null);

        }
    }

    @Test
    public void getAll() {Connection connection = null;
        try {
            connection = Connect.getConnection();
            List<Customer> list=new ArrayList<>();
            list=dao.getAll(connection);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,null);

        }
    }

    @Test
    public void getCount() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            System.out.println(dao.getCount(connection));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,null);

        }
    }

    @Test
    public void getMaxBirth() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            System.out.println(dao.getMaxBirth(connection));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,null);

        }
    }
}