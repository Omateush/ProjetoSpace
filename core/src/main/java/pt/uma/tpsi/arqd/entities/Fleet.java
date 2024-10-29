package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import pt.uma.tpsi.arqd.game.GameHUD;

import java.util.ArrayList;
import java.util.Iterator;

public class Fleet {
    private ArrayList<EnemyShip> enemyShips;
    private ArrayList<Explosion> explosions;
    private SpriteBatch batch;

    public Fleet(SpriteBatch batch, GameHUD hud) {
        this.batch = batch;
        enemyShips = new ArrayList<>();
        explosions = new ArrayList<>();

        float enemyWidth = 60;
        float enemyHeight = 60;

        for (int i = 0; i < 8; i++) {
            float x = i * 100;
            float y = 600;
            enemyShips.add(new EnemyShip(batch, x, y, enemyWidth, enemyHeight, "enemy-big.png", 2, 1));
        }

        for (int i = 0; i < 8; i++) {
            float x = i * 100;
            float y = 550;
            enemyShips.add(new EnemyShip(batch, x, y, enemyWidth, enemyHeight, "enemy-medium.png", 2, 1));
        }

        for (int i = 0; i < 8; i++) {
            float x = i * 100;
            float y = 500;
            enemyShips.add(new EnemyShip(batch, x, y, enemyWidth, enemyHeight, "enemy-small.png", 2, 1));
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                shootAtPlayer();
            }
        }, 2, 1);
    }

    public ArrayList<EnemyShip> getEnemyShips() { //Lista de naves inimigas em arraylist
        return enemyShips;
    }

    private void shootAtPlayer() {
        if (!enemyShips.isEmpty()) {
            int randomIndex = MathUtils.random(enemyShips.size() - 1);
            EnemyShip randomEnemy = enemyShips.get(randomIndex);
            randomEnemy.shoot(); // chamando o metodo shoot();
        }
    }

    public void render(SpriteBatch batch, ArrayList<Laser> playerLasers, Player player) {
        Iterator<EnemyShip> enemyIterator = enemyShips.iterator();

        while (enemyIterator.hasNext()) {
            EnemyShip enemy = enemyIterator.next();
            enemy.render(batch);

            // Iterador para verificar colisao entre o player e as naves
            Iterator<Laser> laserIterator = playerLasers.iterator();
            while (laserIterator.hasNext()) {
                Laser laser = laserIterator.next();

                if (laser.collidesWith(enemy.getBoundingBox())) {
                    explosions.add(new Explosion(enemy.getX(), enemy.getY())); // adiciona explosao
                    enemyIterator.remove(); // remove a nave atingida
                    laserIterator.remove(); // removendo o laser
                    break; // Finalizando lop
                }
            }

            // Renderizar os lasers disparados pelas naves inimigas
            for (Laser laser : enemy.getLasers()) {
                laser.update();
                laser.render(batch);
                if (laser.collidesWith(player.getBoundingBox())) {
                    player.takeDamage();
                    enemy.getLasers().remove(laser);
                    break;
                }
            }
        }

        // Renderizar explosões
        Iterator<Explosion> explosionIterator = explosions.iterator();
        while (explosionIterator.hasNext()) {
            Explosion explosion = explosionIterator.next();
            explosion.render(batch);
            if (explosion.isFinished()) {
                explosionIterator.remove(); // Remove a explosão após finalizar
            }
        }
    }

    public void dispose() {
        for (EnemyShip enemy : enemyShips) {
            enemy.dispose();
        }
        for (Explosion explosion : explosions) {
            explosion.dispose();
        }
    }
}
