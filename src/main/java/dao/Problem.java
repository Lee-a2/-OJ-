package dao;

import lombok.Data;

@Data
public class Problem {
    private int id;
    private String level;
    private String description;
    private String title;
    private String templateCode;
    private String testCode;
}
