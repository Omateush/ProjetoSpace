package pt.uma.tpsi.arqd.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.entities.Fleet;
import pt.uma.tpsi.arqd.entities.Player;

public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private BackgroundManagement backgroundManagement;
    private Player player;
    private Fleet fleet;
    private GameHUD hud;

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundManagement = new BackgroundManagement();
        hud = new GameHUD(); // Inicializando o HUD
        player = new Player(batch, 100, 20, hud); // Passando o HUD para o player
        fleet = new Fleet(batch);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundManagement.render(batch);
        player.render(batch);
        fleet.render(batch, player.getLasers(), player);
        hud.render(batch); // Renderizando o HUD
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundManagement.dispose();
        player.dispose();
        fleet.dispose();
    }
}
