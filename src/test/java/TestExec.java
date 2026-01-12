import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestExec {
    public static void main(String[] args) throws IOException, InterruptedException {
        Runtime runtime=Runtime.getRuntime();
        Process process= runtime.exec("javac");
        InputStream stdoutFrom=process.getInputStream();
        FileOutputStream stdoutTo=new FileOutputStream("stdout.txt");
        while(true){
            int ch=stdoutFrom.read();
            if (ch==-1){
                break;
            }
            stdoutTo.write(ch);
        }
        stdoutFrom.close();
        stdoutTo.close();

        InputStream stderrFrom=process.getErrorStream();
        FileOutputStream stderrTo=new FileOutputStream("stderr.txt");
        while(true){
            int ch=stderrFrom.read();
            if (ch==-1){
                break;
            }
            stderrTo.write(ch);
        }
        stderrFrom.close();
        stderrTo.close();
        int exitCode=process.waitFor();
        System.out.println(exitCode);
    }

}
