package pt.uma.tpsi.arqd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundManagement {

    private Texture background;
    private Sprite sprite;

    public BackgroundManagement() {
        background = new Texture("space.png");
        sprite = new Sprite(background);

        // Ajusta o tamanho do sprite para a largura e altura da janela
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.setPosition(0, 0);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void dispose() {
        background.dispose();
    }
}
