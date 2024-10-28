package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Fleet {
    private ArrayList<EnemyShip> enemyShips;
    private final int enemiesPerRow = 10;
    private final float enemyWidth = 50, enemyHeight = 50;
    private final float spacingX = 100, spacingY = 100;
    private Random random;

    public Fleet(SpriteBatch batch) {
        enemyShips = new ArrayList<>();
        random = new Random();

        // Inicializando as fileiras de inimigos
        for (int i = 0; i < enemiesPerRow; i++) {
            float x = i * (enemyWidth + spacingX);
            float yBig = 800 - 100; // Posição na tela para a primeira fileira
            float yMedium = 800 - 200; // Posição na tela para a segunda fileira
            float ySmall = 800 - 300; // Posição na tela para a terceira fileira

            enemyShips.add(new EnemyShip(batch, x, yBig, (int) enemyWidth, (int) enemyHeight, "enemy-big.png", 2, 1));
            enemyShips.add(new EnemyShip(batch, x, yMedium, (int) enemyWidth, (int) enemyHeight, "enemy-medium.png", 2, 1));
            enemyShips.add(new EnemyShip(batch, x, ySmall, (int) enemyWidth, (int) enemyHeight, "enemy-small.png", 2, 1));
        }

        // Iniciar disparos das naves inimigas
        scheduleEnemyShots();
    }

    public void render(SpriteBatch batch, ArrayList<Laser> playerLasers, Player player) {
        for (EnemyShip enemyShip : enemyShips) {
            enemyShip.render(batch);
        }

        // Verificar colisão entre lasers inimigos e o jogador
        for (EnemyShip enemy : enemyShips) {
            for (Laser laser : enemy.getLasers()) {
                if (laser.getBoundingBox().overlaps(player.getBoundingBox())) {
                    player.takeDamage();
                }
            }
        }
    }

    private void scheduleEnemyShots() {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> {
                    if (!enemyShips.isEmpty()) {
                        int randomIndex = random.nextInt(enemyShips.size());
                        EnemyShip randomEnemy = enemyShips.get(randomIndex);
                        randomEnemy.shoot();
                    }
                });
            }
        }, 0, 2000); // A cada 2 segundos
    }

    public void dispose() {
        for (EnemyShip enemyShip : enemyShips) {
            enemyShip.dispose();
        }
    }
}
