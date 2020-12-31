package day1;

import JDBCutil.Connect;
import bean.Customer;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class QueryTest {
    //使用preparedstatement实现针对不同表的通用查询操作(一行数据)
    public <T> T getInstance(Class<T> tClass,String sql,Object ...args){
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
                T t = tClass.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field declaredField = tClass.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t,columnValue);
                }//给customer某个属性赋值为value
                //获取每个列的列名
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,ps,rs);
        }
        return null;
    }
    @Test
    public void t(){
        String sql="select id,name,email from Customers where id < ?";

        List<Customer> list = getInstance1(Customer.class, sql, 5);
        list.forEach(System.out::println);

    }
    //使用preparedstatement实现针对不同表的通用查询操作(所有数据)
    public<T> List<T> getInstance1(Class<T> tClass,String sql,Object ...args){
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
            List<T> list=new ArrayList<>();
            while (rs.next()){
                T t = tClass.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field declaredField = tClass.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t,columnValue);
                }//给customer某个属性赋值为value
                //获取每个列的列名
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutil.Connect.closeResource(connection,ps,rs);
        }
        return null;

    }
}
