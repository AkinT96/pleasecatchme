package io.github.pleasecatchme;

import com.badlogic.gdx.ApplicationAdapter;

import io.github.pleasecatchme.rendering.GameRenderer;


public class Main extends ApplicationAdapter {
    private GameRenderer renderer;



    @Override
    public void create() {
        renderer = new GameRenderer();
    }

    @Override
    public void render() {
        renderer.render();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
