import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestFile {
    public static void main(String[] args) throws IOException {
        String src="d:/test1.txt";
        String  destPath="d:/test2.txt";
        FileInputStream fileInputStream=new FileInputStream(src);
        FileOutputStream fileOutputStream=new FileOutputStream(destPath);
        while (true){
            int ch = fileInputStream.read();
            if (ch==-1){
                break;
            }
            fileOutputStream.write(ch);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
