package pt.uma.tpsi.arqd.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.game.BitmapFont; // Atualizando o caminho para BitmapFont

public class GameHUD {
    private int playerHealth;

    public GameHUD() {
        this.playerHealth = 100; // Saúde inicial do jogador
    }

    public void render(SpriteBatch batch) {
        BitmapFont.drawText(10, 780, "Health: " + playerHealth, batch); // Exibe a saúde no canto superior esquerdo
    }

    public void updatePlayerHealth(int health) {
        this.playerHealth = health; // Atualiza o valor da saúde
    }

    public void dispose() {
        // Libere recursos, se necessário
    }
}
