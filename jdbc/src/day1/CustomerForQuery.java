package day1;

import JDBCutil.Connect;
import bean.Customer;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * 通用查询方法
 */
public class CustomerForQuery {
    @Test
    public void t11(){
        String sql="select id,name,email,birth from customers where id = ?";
        String sql1="select id,name,email from customers where id = ?";
        Customer customer = queryForCustomer(sql1, 13);
        System.out.println(customer);
    }

    public Customer queryForCustomer (String sql,Object ...args ){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = Connect.getConnection();

            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);

            }

            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData 获取列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                Customer customer=new Customer();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnName = rsmd.getColumnName(i + 1);
                    Field declaredField = Customer.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(customer,columnValue);
                }//给customer某个属性赋值为value
                 //获取每个列的列名
                    return customer;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,ps,rs);
        }

        return null;
    }


    @Test
    public void testQuery(){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        try {
            connection = Connect.getConnection();
            String sql="select id,name,email,birth from customers where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1,1);
            //执行返回结果集
            rset = ps.executeQuery();
            if (rset.next()){   //判断结果集的下一条是否有数据，如果有则返回true指针下移，没有则返回false
                    //获取结果集当前一行数据
                int id = rset.getInt(1);
                String name = rset.getString(2);
                String email = rset.getString(3);
                Date birth = rset.getDate(4);
                Customer customer=new Customer(id,name,email,birth);
                System.out.println(customer);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutil.Connect.closeResource(connection,ps,rset);
        }

    }
}
