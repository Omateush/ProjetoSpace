package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import pt.uma.tpsi.arqd.game.GameHUD;

import java.util.ArrayList;
import java.util.Iterator;

public class Fleet {
    private ArrayList<Ship> enemyShips;
    private ArrayList<Explosion> explosions;
    private SpriteBatch batch;

    public Fleet(SpriteBatch batch, GameHUD hud) {
        this.batch = batch;
        enemyShips = new ArrayList<>();
        explosions = new ArrayList<>();

        float enemyWidth = 60;
        float enemyHeight = 60;

        // Adiciona naves de diferentes tipos
        for (int i = 0; i < 5; i++) {
            float x = i * 70 + 50;
            float y = 500;
            enemyShips.add(new LargeShip(batch, x, y));
        }

        for (int i = 0; i < 5; i++) {
            float x = i * 70 + 50;
            float y = 400;
            enemyShips.add(new MediumShip(batch, x, y));
        }

        for (int i = 0; i < 5; i++) {
            float x = i * 70 + 50;
            float y = 300;
            enemyShips.add(new SmallShip(batch, x, y));
        }




        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                shootAtPlayer();
            }
        }, 2, 2);
    }

    private void shootAtPlayer() {
        if (!enemyShips.isEmpty()) {
            int randomIndex = MathUtils.random(enemyShips.size() - 1);
            enemyShips.get(randomIndex).shoot(); // Chama o método shoot() diretamente
        }
    }

    public void render(SpriteBatch batch, ArrayList<Laser> playerLasers, PlayerShip player) {
        Iterator<Ship> enemyIterator = enemyShips.iterator();
        while (enemyIterator.hasNext()) {
            Ship enemy = enemyIterator.next();
            enemy.render(batch);

            // Verificar colisão entre os lasers do player e as naves inimigas
            Iterator<Laser> laserIterator = playerLasers.iterator();
            while (laserIterator.hasNext()) {
                Laser laser = laserIterator.next();
                if (laser.collidesWith(enemy.getBoundingBox())) {
                    explosions.add(new Explosion(enemy.getX(), enemy.getY()));
                    enemyIterator.remove();
                    laserIterator.remove();
                    break;
                }
            }

            // Renderizar os lasers das naves inimigas e verificar colisão com o player
            for (Laser laser : enemy.getLasers()) {
                laser.update();
                laser.render(batch);
                if (laser.collidesWith(player.getBoundingBox())) {
                    player.takeDamage(10);
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
                explosionIterator.remove();
            }
        }
    }

    public ArrayList<Ship> getEnemyShips() {
        return enemyShips;
    }

}
