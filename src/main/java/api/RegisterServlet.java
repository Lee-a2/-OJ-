package api;

import dao.User;
import dao.UserDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setContentType("text/html; charset=utf8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String admin = req.getParameter("admin");

        UserDao userDao = new UserDao();
        if (username == null || password == null || "".equals(username) || "".equals(password)) {
            String h3 = "<h3>输入格式错误</h3>";
            resp.getWriter().write(h3);
            return;
        }

        User user = userDao.selectOneByName(username);
        if (user != null) {
            String h3 = "<h3>该用户名字已被使用！</h3>";
            resp.getWriter().write(h3);
            return;
        }

        User registerUser = new User();
        registerUser.setUsername(username);
        registerUser.setPassword(password);
        if (admin == null || admin.isEmpty()) {
            registerUser.setIsAdmin(0);
            userDao.insert(registerUser);
            resp.sendRedirect("login.html");
        } else {
            registerUser.setIsAdmin(Integer.parseInt(admin));
            userDao.insert(registerUser);
            resp.sendRedirect("function.html");
        }
    }
}
