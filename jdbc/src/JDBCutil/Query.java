package JDBCutil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class Query {
    public<T> List<T> getInstance1(Connection con,Class<T> tClass, String sql, Object ...args){
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
            JDBCutil.Connect.closeResource(null,ps,rs);
        }
        return null;

    }
}
