package com.example.friendswar;

public class Player {
    private int id;
    private String name;
    private float score;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setPlayerScore(float score) {
        this.score = score;
    }
    public int getPlayerId() {
        return id;
    }
    public String getPlayerName() {
        return name;
    }
    public float getPlayerScore() {
        return score;
    }
}
