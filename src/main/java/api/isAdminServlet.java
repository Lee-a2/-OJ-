package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.Util;
import dao.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin")
public class isAdminServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf8");
        User user = Util.checkUser(req);
        if (user == null) {
            resp.setStatus(403);
            String html = "<h3>当前用户未登录</h3>";
            resp.getWriter().write(html);
            return;
        }
        String html = objectMapper.writeValueAsString(user);
        resp.getWriter().write(html);
    }
}
