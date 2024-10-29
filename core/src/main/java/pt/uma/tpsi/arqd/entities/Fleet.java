package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import pt.uma.tpsi.arqd.game.GameHUD;

import java.util.ArrayList;
import java.util.Iterator;

public class Fleet {
    private ArrayList<EnemyShip> enemyShips;

    public Fleet(SpriteBatch batch, GameHUD hud) {
        enemyShips = new ArrayList<>();

        // Exemplo de inicialização de naves inimigas com largura e altura
        float enemyWidth = 60;
        float enemyHeight = 60;

        for (int i = 0; i < 10; i++) {
            float x = i * 60; // Ajuste de posição
            float y = 500; // Posição Y das naves
            enemyShips.add(new EnemyShip(batch, x, y, enemyWidth, enemyHeight, "enemy-big.png", 2, 1));
        }

        // Programação do disparo a cada 2 segundos
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                shootAtPlayer(); // Chama o método para atirar no jogador
            }
        }, 2, 1); // Atraso inicial de 2 segundos, repete a cada 2 segundos
    }

    private void shootAtPlayer() {
        if (!enemyShips.isEmpty()) {
            int randomIndex = MathUtils.random(enemyShips.size() - 1);
            EnemyShip randomEnemy = enemyShips.get(randomIndex);
            randomEnemy.shoot();
        }
    }

    public void render(SpriteBatch batch, ArrayList<Laser> playerLasers, Player player) {
        Iterator<EnemyShip> enemyIterator = enemyShips.iterator();

        while (enemyIterator.hasNext()) {
            EnemyShip enemy = enemyIterator.next();
            enemy.render(batch);

            // Verificar colisão entre os lasers do player e as naves inimigas
            boolean enemyHit = false;
            for (Laser laser : playerLasers) {
                if (laser.collidesWith(enemy.getBoundingBox())) {
                    enemyIterator.remove(); // Remove a nave se atingida
                    enemyHit = true;
                    break;
                }
            }
            if (enemyHit) continue;

            // Verificar colisão entre os lasers inimigos e o player
            Iterator<Laser> laserIterator = enemy.getLasers().iterator();
            while (laserIterator.hasNext()) {
                Laser laser = laserIterator.next();
                laser.update();
                laser.render(batch);
                if (laser.collidesWith(player.getBoundingBox())) {
                    player.takeDamage();
                    laserIterator.remove(); // Remove o laser se colidir com o player
                }
            }
        }
    }

    public void dispose() {
        for (EnemyShip enemy : enemyShips) {
            enemy.dispose();
        }
    }
}
