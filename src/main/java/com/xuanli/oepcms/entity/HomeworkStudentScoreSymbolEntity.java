package com.xuanli.oepcms.entity;

public class HomeworkStudentScoreSymbolEntity {
    private Long id;

    private Long homeworkId;

    private Long homeworkDetailId;

    private Long studentId;

    private Double score;

    private String symbol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Long homeworkId) {
        this.homeworkId = homeworkId;
    }

    public Long getHomeworkDetailId() {
        return homeworkDetailId;
    }

    public void setHomeworkDetailId(Long homeworkDetailId) {
        this.homeworkDetailId = homeworkDetailId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }
}