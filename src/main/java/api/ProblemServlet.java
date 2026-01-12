package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.Util;
import dao.Problem;
import dao.ProblemDAO;
import dao.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/problem")
public class ProblemServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf8");
        User user = Util.checkUser(req);
        if (user == null) {
            resp.setContentType("text/html; charset=utf-8");
            resp.setStatus(403);
            String html = "<h3>当前用户未登录</h3>";
            resp.getWriter().write(html);
            return;
        }

        resp.setStatus(200);
        String id = req.getParameter("id");
        ProblemDAO problemDAO = new ProblemDAO();
        if (id == null || id.isEmpty()) {
            List<Problem> list = problemDAO.selectAll();
            String respString = objectMapper.writeValueAsString(list);
            resp.getWriter().write(respString);
        } else {
            Problem problem = problemDAO.selectOne(Integer.parseInt(id));
            String respString = objectMapper.writeValueAsString(problem);
            resp.getWriter().write(respString);
        }
    }
}
