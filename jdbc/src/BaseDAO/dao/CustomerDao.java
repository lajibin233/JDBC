package BaseDAO.dao;

import bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

//用于针对customers表的常用操作
public interface CustomerDao {
    //将cust对象添加到数据库中
    void insert(Connection con, Customer customer);
    //删除
    void deleteById(Connection con,int id);
    //修改
    void updateById(Connection con,Customer customer);
    //查询
    Customer selectById(Connection con,int id );
    //查询所有记录
    List<Customer> getAll(Connection con);
    //返回数据条目数
    Long getCount(Connection con);

    Date getMaxBirth(Connection con);



}









