package pt.uma.tpsi.arqd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.entities.Fleet;
import pt.uma.tpsi.arqd.entities.Player;
import pt.uma.tpsi.arqd.game.BackgroundManagement;
import pt.uma.tpsi.arqd.game.GameHUD;
import pt.uma.tpsi.arqd.game.BitmapFont;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private BackgroundManagement backgroundManagement;
    private GameHUD hud;
    private Player player;
    private Fleet fleet;
    private boolean gameEnded;
    private String endMessage;

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundManagement = new BackgroundManagement();
        hud = new GameHUD();
        player = new Player(batch, 100, 20, hud); // Passa o HUD para o player
        fleet = new Fleet(batch, hud); // Passa o HUD para a Fleet
        gameEnded = false;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundManagement.render(batch);
        hud.render(batch);


        if (!gameEnded) {
            player.render(batch);
            fleet.render(batch, player.getLasers(), player); // renderiza o espaço onde o player vai estar e colisoes
            checkGameEndConditions(); // Verifica se jogo terminou boolean sim ou nao
        } else {
            // Mensagem do fim de jogo.
            BitmapFont.drawText(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2, endMessage, batch);
        }

        batch.end();
    }

    private void checkGameEndConditions() {
        if (player.getHealth() <= 0) {
            endMessage = "Derrota! Sua vida chegou a zero!";
            gameEnded = true;
        } else if (fleet.getEnemyShips().isEmpty()) {
            endMessage = "Parabens! voce destruiu todas as naves inimigas!";
            gameEnded = true;
        }
    }

}
