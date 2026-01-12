package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.Util;
import compile.Answer;
import compile.Question;
import compile.Task;
import dao.Problem;
import dao.ProblemDAO;
import dao.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@WebServlet("/compile")
public class CompileServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    private static String mergeCode(String requestCode, String testCode) {
        int pos = requestCode.lastIndexOf("}");
        if (pos == -1) {
            return null;
        }
        String string = requestCode.substring(0, pos);
        string += testCode + "\n}";
        return string;
    }

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
        resp.setContentType("application/json;charset=utf8");
        User user = Util.checkUser(req);
        if (user == null) {
            resp.setStatus(403);
            String html = "<h3>当前用户未登录</h3>";
            resp.getWriter().write(html);
            return;
        }

        CompileResponse compileResponse = new CompileResponse();
        CompileRequest compileRequest = null;
        Problem problem;
        try {
            resp.setStatus(200);
            String body = readBody(req);
            compileRequest = objectMapper.readValue(body, CompileRequest.class);

            ProblemDAO problemDAO = new ProblemDAO();
            problem = problemDAO.selectOne(compileRequest.id);
            if (problem == null) {
                throw new RuntimeException();
            }

            String testCode = problem.getTestCode();
            String requestCode = compileRequest.code;
            String finalCode = mergeCode(requestCode, testCode);
            if (finalCode == null) {
                throw new RuntimeException();
            }
            System.out.println(finalCode);

            Task task = new Task();
            Question question = new Question();
            question.setCode(finalCode);

            Answer answer = task.compileAndRun(question);
            compileResponse.error = answer.getError();
            compileResponse.reason = answer.getReason();
            compileResponse.stdout = answer.getStdout();
        } catch (RuntimeException e) {
            compileResponse.error = 3;
            compileResponse.reason = "提交的代码不符合要求";
        } finally {
            String respString = objectMapper.writeValueAsString(compileResponse);
            resp.getWriter().write(respString);
        }
    }

    static class CompileRequest {
        public int id;
        public String code;
    }

    static class CompileResponse {
        public int error;
        public String reason;
        public String stdout;
    }
}
