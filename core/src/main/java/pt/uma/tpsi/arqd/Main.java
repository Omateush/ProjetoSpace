package pt.uma.tpsi.arqd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.entities.Fleet;
import pt.uma.tpsi.arqd.entities.Player;
import pt.uma.tpsi.arqd.game.BackgroundManagement;
import pt.uma.tpsi.arqd.game.GameHUD;
import pt.uma.tpsi.arqd.game.BitmapFont; // Certifique-se de que a classe BitmapFont está corretamente importada

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private BackgroundManagement backgroundManagement;
    private GameHUD hud;
    private Player player;
    private Fleet fleet;
    private boolean gameEnded; // Verifica se o jogo terminou
    private String endMessage; // Mensagem de fim de jogo

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundManagement = new BackgroundManagement(); // Inicializa o fundo
        hud = new GameHUD(); // Inicializa o HUD
        player = new Player(batch, 100, 20, hud); // Passa o HUD para o player
        fleet = new Fleet(batch, hud); // Passa o HUD para a Fleet
        gameEnded = false;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundManagement.render(batch); // Renderiza o fundo
        hud.render(batch); // Renderiza o HUD

        // Verifica se o jogo terminou antes de continuar
        if (!gameEnded) {
            player.render(batch);
            fleet.render(batch, player.getLasers(), player); // Renderiza a Fleet e passa o player para colisões
            checkGameEndConditions(); // Verifica condições de vitória ou derrota
        } else {
            // Exibe a mensagem de fim de jogo
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

    @Override
    public void dispose() {
        batch.dispose();
        backgroundManagement.dispose(); // Libera o fundo
        hud.dispose();
        player.dispose();
        fleet.dispose();
    }
}
