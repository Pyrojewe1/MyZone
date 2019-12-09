package com.example.myzone;

public class Mission {
    private int id;
    private String finishTime;
    private String startTime;
    private String doWhat;
    private boolean Important =false;
    private String owner;
    private boolean today = false;
    private String remarks = "null";
    private boolean complete =false;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isToday() {
        return today;
    }

    public void setToday(boolean today) {
        this.today = today;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getFinishTime() {
        return finishTime;
    }

    public String getDoWhat() {
        return doWhat;
    }

    public boolean isImportant() {
        return Important;
    }

    public String getOwner() {
        return owner;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public void setDoWhat(String doWhat) {
        this.doWhat = doWhat;
    }

    public void setImportant(boolean important) {
        Important = important;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
