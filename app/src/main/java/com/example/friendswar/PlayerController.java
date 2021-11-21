package com.example.friendswar;

import java.util.ArrayList;

public class PlayerController {
    private int playerId = 0;
    private int playerTurn = 0;
    ArrayList<Player> players = new ArrayList<Player>();

    public void createPlayer(String name) {
        Player p = new Player(playerId++,name);
        players.add(p);
    }
    public void deletePlayer(int id) {
        Player player;
        for(int i=0; i<players.size();i++) {
            player = players.get(i);
            if(player.getPlayerId() == id) {
                players.remove(player);
                break;
            }
        }
//        players.remove(id);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayerById(int id) {
        Player player;
        for(int i=0; i<players.size();i++) {
            player = players.get(i);
            if(player.getPlayerId() == id) {
                return player;
            }
        }
        return null;
    }
    public void setToZeroTurn() {
        playerTurn = 0;
    }
    public int getPlayerSize() {
        System.out.println(players.size());
        return players.size();
    }

    public Player getNextTurnPlayer() {
        if(playerTurn >= players.size()) {
            playerTurn = 0;
        }
        return players.get(playerTurn++);
    }
}
