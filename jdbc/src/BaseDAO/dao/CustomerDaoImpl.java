package BaseDAO.dao;

import bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomerDaoImpl extends BaseDao<Customer> implements CustomerDao {


    @Override
    public void insert(Connection con, Customer customer) {
        String sql="insert into Customers (name,email,birth)values(?,?,?)";
        update(con,sql,customer.getName(),customer.getEmail(),customer.getBirth());
    }

    @Override
    public void deleteById(Connection con, int id) {
        String sql="delete from customers where id = ?";
        update(con,sql,id);


    }

    @Override
    public void updateById(Connection con, Customer customer) {
        String sql="update customers set name=?,email=?,birth=? where id = ?";
        update(con,sql,customer.getName(),customer.getEmail(),customer.getBirth(),customer.getId());
    }

    @Override
    public Customer selectById(Connection con, int id) {
       String sql="select id,name,email,birth from Customers where id = ?";

        Customer customer = getInstance(con,sql, id);
        return customer;
    }

    @Override
    public List<Customer> getAll(Connection con) {
        String sql="select id,name,email,birth from Customers ";
        List<Customer> list = getForList(con,sql);
        return list;

    }

    @Override
    public Long getCount(Connection con) {
        String sql="select count(*) from Customers";
        return getValue(con,sql);
    }

    @Override
    public Date getMaxBirth(Connection con) {
        String sql="select max(birth) from customers";

        return getValue(con,sql);
    }
}
