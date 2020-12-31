package day1;

import JDBCutil.Connect;
import bean.Customer;
import org.junit.Test;

import java.io.*;
import java.sql.*;

public class demo4_blob {
    @Test
    public void t1() throws Exception{
        //向customer插入blob文件
        Connection connection = Connect.getConnection();
        String sql="insert into customers(name,email,birth,photo)values(?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1,"张三");
        ps.setObject(2,"张三@qq.com");
        ps.setObject(3,"1995-5-5");
        InputStream is =new FileInputStream("E://fengj.jpg");
        ps.setBlob(4,is);

        ps.execute();
        JDBCutil.Connect.closeResource(connection,ps);

    }
    //查询数据库中的blob文件
    @Test
    public void t2() throws Exception{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        OutputStream os= null;
        try {
            connection = Connect.getConnection();

            String sql="select id,name,email,birth,photo from customers where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1,20);
            rs = ps.executeQuery();
            if (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date birth = rs.getDate(4);
                Customer customer=new Customer(id,name,email,birth);
                System.out.println(customer);
                Blob photo = rs.getBlob(5);
                 is = photo.getBinaryStream();
                 os=new FileOutputStream("fengj.jpg");
                byte[] buf =new byte[1024];
                int len;
                while ((len=is.read(buf))!= -1){
                    os.write(buf,0,len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null ) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JDBCutil.Connect.closeResource(connection,ps,rs);

        }

    }
}
