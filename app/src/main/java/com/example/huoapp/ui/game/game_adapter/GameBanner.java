package com.example.huoapp.ui.game.game_adapter;

import java.util.List;

/**
 * Created by tinle on 2017/11/8.
 */
public class GameBanner {
    private List<String> urls;

    public GameBanner(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }
}