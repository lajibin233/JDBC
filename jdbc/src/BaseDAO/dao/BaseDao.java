package BaseDAO.dao;

import JDBCutil.Connect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao<T> {
    private Class<T> clazz=null;
    {   //创建子类时会调用父类构造器，谁调用this就是谁，this.getClass()=Customer.class
        // 此时父类的泛型跟随子类声明的泛型即customer得到父类的泛型

        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType type= (ParameterizedType) genericSuperclass;
        Type[] types = type.getActualTypeArguments();//获取父类的泛型参数
        clazz= (Class<T>) types[0];//泛型的一个参数
    }
    //封装针对数据表的通用操作
    public  void update(Connection con, String sql, Object... arg_s) {

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < arg_s.length; i++) {
                ps.setObject(i + 1, arg_s[i]);
            }
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.closeResource(null, ps);
        }
    }
    public List<T> getForList(Connection con,String sql, Object ...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Connect.getConnection();

            ps = con.prepareStatement(sql);
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
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field declaredField = clazz.getDeclaredField(columnLabel);
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
            Connect.closeResource(null,ps,rs);
        }
        return null;

    }

    public T getInstance(Connection con,String sql,Object ...args){

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Connect.getConnection();

            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);

            }
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData 获取列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field declaredField = clazz.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t,columnValue);
                }//给customer某个属性赋值为value
                //获取每个列的列名
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            Connect.closeResource(null,ps,rs);
        }
        return null;
    }
    //针对特殊操作的查询
    public<E> E  getValue(Connection con,String sql,Object ...args){
        PreparedStatement ps=null;
        ResultSet rs =null;
        try {
             ps=con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(1+i,args[i]);
            }
             rs = ps.executeQuery();
            if(rs.next()){
                return (E)rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Connect.closeResource(null,ps,rs);
        }
        return null;
    }
}
