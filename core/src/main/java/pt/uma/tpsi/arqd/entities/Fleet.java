package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.Gdx; // Import necessário para Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Fleet {
    private ArrayList<EnemyShip> enemyShips;
    private final int enemiesPerRow = 10;
    private final float enemyWidth = 50, enemyHeight = 50;
    private final float spacingX = 100, spacingY = 100;
    private float elapsedTime = 0;
    private static final float SHOOT_INTERVAL = 2.0f; // Intervalo de 2 segundos

    public Fleet(SpriteBatch batch) {
        enemyShips = new ArrayList<>();

        // Cria as fileiras de naves inimigas
        for (int i = 0; i < enemiesPerRow; i++) {
            float x = i * (enemyWidth + spacingX);
            float yBig = 800 - 100;
            float yMedium = 800 - 200;
            float ySmall = 800 - 300;

            enemyShips.add(new EnemyShip(batch, x, yBig, enemyWidth, enemyHeight, "enemy-big.png", 2, 1));
            enemyShips.add(new EnemyShip(batch, x, yMedium, enemyWidth, enemyHeight, "enemy-medium.png", 2, 1));
            enemyShips.add(new EnemyShip(batch, x, ySmall, enemyWidth, enemyHeight, "enemy-small.png", 2, 1));
        }
    }

    public void render(SpriteBatch batch, ArrayList<Laser> playerLasers) {
        elapsedTime += Gdx.graphics.getDeltaTime(); // Atualiza o tempo decorrido

        // Disparo de inimigos a cada intervalo
        if (elapsedTime >= SHOOT_INTERVAL) {
            shootRandomEnemy();
            elapsedTime = 0;
        }

        Iterator<EnemyShip> iterator = enemyShips.iterator();
        while (iterator.hasNext()) {
            EnemyShip enemyShip = iterator.next();
            enemyShip.render(batch);

            // Verifica colisões com os lasers do jogador
            Iterator<Laser> laserIterator = playerLasers.iterator();
            while (laserIterator.hasNext()) {
                Laser laser = laserIterator.next();
                if (laser.getBoundingBox().overlaps(enemyShip.getBoundingBox())) {
                    laserIterator.remove(); // Remove o laser que atingiu
                    iterator.remove(); // Remove a nave atingida
                    break;
                }
            }
        }
    }

    private void shootRandomEnemy() {
        if (!enemyShips.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(enemyShips.size());
            EnemyShip randomEnemy = enemyShips.get(randomIndex);
            randomEnemy.shoot(); // Inimigo dispara
        }
    }

    public void dispose() {
        for (EnemyShip enemyShip : enemyShips) {
            enemyShip.dispose();
        }
    }
}
