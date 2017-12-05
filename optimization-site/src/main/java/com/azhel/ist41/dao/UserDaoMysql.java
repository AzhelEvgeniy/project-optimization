package com.azhel.ist41.dao;

import com.azhel.ist41.dao.exception.DuplicateUserNameException;
import com.azhel.ist41.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDaoMysql implements UserDao {
    @Autowired
    DataSource dataSource;

    @Override
    public void addUser(User user) throws DuplicateUserNameException{
        final String INSERT_SQL = "INSERT INTO users (username, password, enabled) VALUE (?, ?, ?)";
        final String INSERT_SQL_ROLE = "INSERT INTO authorities (username, authority) VALUE (?, \"ROLE_USER\")";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            //users
            ps = conn.prepareStatement(INSERT_SQL);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getEnabled());
            ps.executeUpdate();
            ps.close();
            //authorities
            ps = conn.prepareStatement(INSERT_SQL_ROLE);
            ps.setString(1, user.getUsername());
            ps.executeUpdate();
            ps.close();
        } catch (MySQLIntegrityConstraintViolationException ex){
            throw new DuplicateUserNameException("Dublicate user name " + user.getUsername(), ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
}
