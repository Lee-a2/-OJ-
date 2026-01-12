package dao;

import common.DBUtil;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDao {
    private static void insertUser() {
        User user = new User();
        UserDao userDao = new UserDao();
        user.setIsAdmin(1);
        user.setUsername("大型软件");
        user.setPassword("123");
        userDao.insert(user);
        user.setIsAdmin(0);
        user.setUsername("Common");
        user.setPassword("123");
        userDao.insert(user);
    }

    private static void selectall() {
        UserDao userDao = new UserDao();
        List<User> list = userDao.selectAll();
        System.out.println(list);
    }

    private static void deleteone() {
        UserDao userDao = new UserDao();
        userDao.delete(3);
    }

    private static void selectone() {
        UserDao userDao = new UserDao();
        User user = userDao.selectOneByName("大型软件");
        System.out.println(user);
    }

    public static void main(String[] args) {
        selectone();
    }

    public void insert(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "insert into oj_user values(null,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getIsAdmin());

            int ret = statement.executeUpdate();
            if (ret == 1) {
                System.out.println("插入成功！");
            } else {
                System.out.println("插入失败！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                DBUtil.close(connection, statement, null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "delete from oj_user where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int ret = statement.executeUpdate();
            if (ret == 1) {
                System.out.println("删除成功！");
            } else {
                System.out.println("删除失败！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                DBUtil.close(connection, statement, null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public List<User> selectAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> list = new ArrayList<>();
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from oj_user";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setIsAdmin(resultSet.getInt("isAdmin"));
                list.add(user);
            }
            if (list == null) {
                return null;
            } else {
                return list;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                DBUtil.close(connection, statement, resultSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public User selectOneByName(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from oj_user where username=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setIsAdmin(resultSet.getInt("isAdmin"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                DBUtil.close(connection, statement, resultSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public User selectOneById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from oj_user where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setIsAdmin(resultSet.getInt("isAdmin"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                DBUtil.close(connection, statement, resultSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }
}
