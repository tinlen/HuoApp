package com.example.huoapp.ui.game.game_adapter;

import java.util.List;

/**
 * Created by tinle on 2017/11/8.
 */
public class GameNewOrder {
    private String name;

    private List<GameItem> gameItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GameItem> getGameItems() {
        return gameItems;
    }

    public void setGameItems(List<GameItem> gameItems) {
        this.gameItems = gameItems;
    }
}