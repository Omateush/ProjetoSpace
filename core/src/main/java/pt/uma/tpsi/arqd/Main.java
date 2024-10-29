package pt.uma.tpsi.arqd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.entities.Fleet;
import pt.uma.tpsi.arqd.entities.Player;
import pt.uma.tpsi.arqd.game.BackgroundManagement;
import pt.uma.tpsi.arqd.game.GameHUD;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private BackgroundManagement backgroundManagement;
    private GameHUD hud;
    private Player player;
    private Fleet fleet;

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundManagement = new BackgroundManagement(); // Inicializa o fundo
        hud = new GameHUD(); // Inicializa o HUD
        player = new Player(batch, 100, 20, hud); // Passa o HUD para o player
        fleet = new Fleet(batch,hud); // Passa o HUD para a Fleet
    }

    @Override
    public void render() {
        // Removendo a linha de Gdx.gl.glClearColor para não aplicar cor de fundo
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundManagement.render(batch); // Renderiza o fundo
        hud.render(batch); // Renderiza o HUD
        player.render(batch);
        fleet.render(batch, player.getLasers(), player); // Renderiza a Fleet e passa o player para colisões
        batch.end();
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
