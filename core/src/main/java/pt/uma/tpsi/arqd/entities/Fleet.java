package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import pt.uma.tpsi.arqd.game.GameHUD;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Fleet {
    private ArrayList<EnemyShip> enemyShips;
    private float elapsedTime;
    private GameHUD hud;

    public Fleet(SpriteBatch batch, GameHUD hud) {
        this.hud = hud;
        enemyShips = new ArrayList<>();
        elapsedTime = 0;

        // Adiciona inimigos ao Fleet (exemplo simplificado)
        for (int i = 0; i < 5; i++) {
            float x = i * 100 + 400;
            float y = 700;
            enemyShips.add(new EnemyShip(batch, x, y, 100, 100, "enemy-big.png", 2, 1));
        }

        // Adiciona inimigos ao Fleet (exemplo simplificado)
        for (int i = 0; i < 5; i++) {
            float x = i * 100 + 400;
            float y = 500;
            enemyShips.add(new EnemyShip(batch, x, y, 100, 100, "enemy-medium.png", 2, 1));
        }

        // Adiciona inimigos ao Fleet (exemplo simplificado)
        for (int i = 0; i < 5; i++) {
            float x = i * 100 + 400;
            float y = 300;
            enemyShips.add(new EnemyShip(batch, x, y, 100, 100, "enemy-small.png", 2, 1));
        }


        // Agendar disparos aleatórios das naves inimigas
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Random random = new Random();
                if (!enemyShips.isEmpty()) {
                    EnemyShip randomEnemy = enemyShips.get(random.nextInt(enemyShips.size()));
                    randomEnemy.shoot();
                }
            }
        }, 2, 2);
    }

    public void render(SpriteBatch batch, ArrayList<Laser> playerLasers, Player player) {
        for (EnemyShip enemyShip : enemyShips) {
            enemyShip.render(batch);
        }

        // Verificar colisões entre lasers dos inimigos e o jogador
        for (EnemyShip enemy : enemyShips) {
            Iterator<Laser> laserIterator = enemy.getLasers().iterator();
            while (laserIterator.hasNext()) {
                Laser laser = laserIterator.next();
                laser.update();
                laser.render(batch);

                // Verificar se o laser colide com o jogador
                if (laser.getBoundingBox().overlaps(player.getBoundingBox())) {
                    player.takeDamage();
                    laserIterator.remove(); // Remove o laser após acertar o jogador para evitar múltiplas colisões
                }
            }
        }
    }

    public void dispose() {
        for (EnemyShip enemyShip : enemyShips) {
            enemyShip.dispose();
        }
    }
}
