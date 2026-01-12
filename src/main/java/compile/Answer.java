package compile;

import lombok.Data;

@Data
public class Answer {
    private int error;
    private String reason;
    private String stdout;
    private String stderr;
}
