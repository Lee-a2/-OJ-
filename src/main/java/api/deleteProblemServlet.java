package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ProblemDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@WebServlet("/deleteProblem")
public class deleteProblemServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    private static String readBody(HttpServletRequest req) throws UnsupportedEncodingException {
        int contentLength = req.getContentLength();
        byte[] bytes = new byte[contentLength];
        try (InputStream inputStream = req.getInputStream()) {
            inputStream.read(bytes);
            inputStream.close();
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
        if (body == null) {
            resp.setContentType("text/html; charset=utf8");
            String html = "<h3>无法删除成功！</h3>";
            resp.getWriter().write(html);
            return;
        }
        Accept accept = objectMapper.readValue(body, Accept.class);
        ProblemDAO problemDAO = new ProblemDAO();
        problemDAO.delete(accept.id);

        Result result = new Result();
        result.result = 1;
        String html = objectMapper.writeValueAsString(result);
        resp.getWriter().write(html);
    }

    static class Accept {
        public int id;
    }

    static class Result {
        public int result;
    }
}
