package api;

import common.Util;
import dao.User;
import dao.UserDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LoginoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setContentType("text/txt; charset=utf-8");
            String html = "<h3>当前尚未登录</h3>";
            resp.getWriter().write(html);
            return;
        }
        session.removeAttribute("user");
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        String username = req.getParameter("username");
        resp.setContentType("text/html; charset=utf8");
        User user = Util.checkUser(req);
        if (user == null) {
            String html = "<h3> 当前用户未登录 </h3>";
            resp.getWriter().write(html);
            return;
        }
        if (user.getUsername().equals(username)) {
            String html = "<h3>不能删除自己</h3>";
            resp.getWriter().write(html);
            return;
        }

        UserDao userDao = new UserDao();
        User user1 = userDao.selectOneByName(username);
        if (user1 == null) {
            String html = "<h3>删除用户不存在</h3>";
            resp.getWriter().write(html);
            return;
        }
        userDao.delete(user1.getId());
        resp.sendRedirect("function.html");
    }
}
