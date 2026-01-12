package compile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Task {
    private static String WORK_DIR = null;
    private static String CLASS = null;
    private static String CODE = null;
    private static String COMPILE_ERROR = null;
    private static String STDOUT = null;
    private static String STDERR = null;

    public Task() {
        WORK_DIR = "./tmp/" + UUID.randomUUID().toString() + "/";
        CODE = WORK_DIR + "Solution.java";
        CLASS = "Solution";
        COMPILE_ERROR = WORK_DIR + "compileError.txt";
        STDOUT = WORK_DIR + "stdout.txt";
        STDERR = WORK_DIR + "stderr.txt";
    }

    public static void main(String[] args) {
        Task task = new Task();
        Question question = new Question();
        question.setCode("public class Solution {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"sdfasdasd\");\n" +
                "    }\n" +
                "}\n");
        Answer answer = task.compileAndRun(question);
        System.out.println(answer.toString());
    }

    public Answer compileAndRun(Question question) {
        Answer answer = new Answer();
        File workDir = new File(WORK_DIR);
        if (!workDir.exists()) {
            workDir.mkdirs();
        }
        if (!checkCodeSafe(question.getCode())) {
            System.out.println("用户提交了不安全代码");
            answer.setError(3);
            answer.setReason("你提交的代码不安全！");
            return answer;
        }

        FileUtil.writeFile(CODE, question.getCode());
        String compileCmd = String.format("javac -encoding utf8 %s -d %s", CODE, WORK_DIR);
        System.out.println("编译命令：" + compileCmd);
        CommandUtil.run(compileCmd, null, COMPILE_ERROR);
        String compileError = FileUtil.readFile(COMPILE_ERROR);
        System.out.println(compileError);
        if (!compileError.equals("")) {
            System.out.println("编译出错");
            answer.setError(1);
            answer.setReason(compileError);
            return answer;
        }

        String runCmd = String.format("java -classpath %s %s", WORK_DIR, CLASS);
        System.out.println("运行命令：" + runCmd);
        int exitCode = CommandUtil.run(runCmd, STDOUT, STDERR);
        String runError = FileUtil.readFile(STDERR);
        if (exitCode != 0) {
            System.out.println("运行出错");
            answer.setError(2);
            answer.setReason(runError);
            return answer;
        }
        answer.setError(0);
        answer.setStdout(FileUtil.readFile(STDOUT));
        return answer;
    }

    private boolean checkCodeSafe(String code) {
        List<String> blackList = new ArrayList<>();
        blackList.add("Runtime");
        blackList.add("exec");
        blackList.add("java.io");
        blackList.add("java.net");
        for (String string : blackList) {
            int pos = code.indexOf(string);
            if (pos >= 0) {
                return false;
            }
        }
        return true;
    }
}
