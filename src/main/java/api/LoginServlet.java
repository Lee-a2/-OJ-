package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.User;
import dao.UserDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setContentType("text/html; charset=utf8");
        UserDao userDao = new UserDao();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || password == null || "".equals(username) || "".equals(password)) {
            String h3 = "<h3>输入格式错误</h3>";
            resp.getWriter().write(h3);
            return;
        }

        User user = userDao.selectOneByName(username);
        if (user == null) {
            String h3 = "<h3>当前用户为空</h3>";
            resp.getWriter().write(h3);
            return;
        }
        if (!user.getUsername().equals(username) || !user.getPassword().equals(password)) {
            String h3 = "<h3>当前密码或者账号 错误！</h3>";
            resp.getWriter().write(h3);
            return;
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("user", user);
        resp.sendRedirect("index.html");
    }
}
