package pt.uma.tpsi.arqd.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameHUD {
    private int playerHealth;

    public void setPlayerHealth(int health) {
        this.playerHealth = health;
    }

    public void updatePlayerHealth(int health) {
        this.playerHealth = health;
    }

    public void render(SpriteBatch batch) {
        BitmapFont.drawText(10, 780, "Health: " + playerHealth, batch);
    }
}
