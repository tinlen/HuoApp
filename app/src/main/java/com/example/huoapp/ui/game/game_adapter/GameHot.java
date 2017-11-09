package com.example.huoapp.ui.game.game_adapter;

import java.util.List;

/**
 * Created by tinle on 2017/11/8.
 */
public class GameHot {
    private List<GameItem> hots;

    public GameHot() {
    }

    public GameHot(List<GameItem> hots) {
        this.hots = hots;
    }

    public List<GameItem> getHots() {
        return hots;
    }

    public void setHots(List<GameItem> hots) {
        this.hots = hots;
    }
}