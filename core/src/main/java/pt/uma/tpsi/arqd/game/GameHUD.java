package pt.uma.tpsi.arqd.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class GameHUD {
    private int playerHealth;

    public GameHUD() {
        this.playerHealth = 100; // Valor inicial da saúde do jogador
    }

    public void updatePlayerHealth(int health) {
        this.playerHealth = health;
    }

    public void render(SpriteBatch batch) {
        // Renderiza o texto na tela usando a altura da tela
        BitmapFont.drawText(10, Gdx.graphics.getHeight() - 10, "Health: " + playerHealth, batch);
    }
}
