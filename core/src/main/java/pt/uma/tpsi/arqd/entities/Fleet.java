package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import pt.uma.tpsi.arqd.game.GameHUD;

import java.util.ArrayList;
import java.util.Iterator;

public class Fleet {
    private ArrayList<Ship> enemyShips;
    private ArrayList<Explosion> explosions; // Lista de explosões

    public Fleet(SpriteBatch batch, GameHUD hud) {
        enemyShips = new ArrayList<>();
        explosions = new ArrayList<>(); // Inicializa a lista de explosões

        float shipWidth = 50;
        float shipHeight = 50;
        float spacing = 80;
        float xOffset = 200;

        // Adiciona naves com espaçamento centralizado
        for (int i = 0; i < 5; i++) {
            float x = xOffset + i * spacing;
            float y = 400;
            enemyShips.add(new MediumShip(batch, x, y, shipWidth, shipHeight));
        }
        for (int i = 0; i < 3; i++) {
            float x = xOffset + i * spacing;
            float y = 500;
            enemyShips.add(new LargeShip(batch, x, y, shipWidth, shipHeight));
        }

        // Disparo aleatório a cada 2 segundos
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
            Ship randomEnemy = enemyShips.get(randomIndex);
            randomEnemy.shoot();
        }
    }

    public void render(SpriteBatch batch, ArrayList<Laser> playerLasers, PlayerShip player) {
        Iterator<Ship> enemyIterator = enemyShips.iterator();

        while (enemyIterator.hasNext()) {
            Ship enemy = enemyIterator.next();
            enemy.render(batch);

            Iterator<Laser> laserIterator = playerLasers.iterator();
            while (laserIterator.hasNext()) {
                Laser laser = laserIterator.next();

                if (laser.collidesWith(enemy.getBoundingBox())) {
                    enemy.takeDamage(10);
                    laserIterator.remove();

                    if (enemy.getHealth() <= 0) {
                        explosions.add(new Explosion(enemy.getX(), enemy.getY())); // Adiciona a explosão
                        enemyIterator.remove(); // Remove a nave atingida
                    }
                    break;
                }
            }

            for (Laser laser : enemy.getLasers()) {
                laser.update();
                laser.render(batch);
                if (laser.collidesWith(player.getBoundingBox())) {
                    player.takeDamage(20);
                    enemy.getLasers().remove(laser);
                    explosions.add(new Explosion(player.getX(), player.getY())); // Adiciona explosão no jogador
                    break;
                }
            }
        }

        renderExplosions(batch); // Renderiza as explosões após as colisões
    }

    private void renderExplosions(SpriteBatch batch) {
        Iterator<Explosion> explosionIterator = explosions.iterator();
        while (explosionIterator.hasNext()) {
            Explosion explosion = explosionIterator.next();
            explosion.render(batch);
            if (explosion.isFinished()) {
                explosionIterator.remove(); // Remove a explosão após finalizar
            }
        }
    }

    public ArrayList<Ship> getEnemyShips() {
        return enemyShips;
    }

    public void dispose() {
        for (Ship enemy : enemyShips) {
            enemy.dispose();
        }
        for (Explosion explosion : explosions) {
            explosion.dispose();
        }
    }
}
