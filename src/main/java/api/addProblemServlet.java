package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.Problem;
import dao.ProblemDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@WebServlet("/addProblem")
public class addProblemServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    private static String readBody(HttpServletRequest req) throws UnsupportedEncodingException {
        int contentLength = req.getContentLength();
        byte[] bytes = new byte[contentLength];
        try (InputStream inputStream = req.getInputStream()) {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(bytes, "utf8");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setContentType("application/json; charset=utf8");
        String body = readBody(req);
        isCorrect correct = new isCorrect();

        if (body == null) {
            resp.setContentType("text/html; charset=utf8");
            String html = "<h3>提交的题目有问题，无法新加成功！</h3>";
            resp.getWriter().write(html);
            return;
        }

        Problem problem = objectMapper.readValue(body, Problem.class);
        ProblemDAO problemDAO = new ProblemDAO();
        problemDAO.insert(problem);
        correct.correct = 1;
        String html = objectMapper.writeValueAsString(correct);
        resp.getWriter().write(html);
    }

    static class isCorrect {
        public int correct;
    }
}
