package com.example.myzone;

public class Compare implements Comparable<Mission> {
    @Override
    public int compareTo(Mission mission) {
        if(mission.isComplete()){
        return 0;
        }
        else
            return 1;
    }
}
