package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;

public class Fleet {
    private ArrayList<EnemyShip> enemyShips;

    public Fleet(SpriteBatch batch) {
        this.enemyShips = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            enemyShips.add(new EnemyShip(100 + i * 60, 500, 50, 50, "enemy-big.png"));
        }
    }

    public void render(SpriteBatch batch, ArrayList<Laser> playerLasers) {
        Iterator<EnemyShip> enemyIterator = enemyShips.iterator();
        while (enemyIterator.hasNext()) {
            EnemyShip enemy = enemyIterator.next();
            enemy.render(batch);
            Iterator<Laser> laserIterator = playerLasers.iterator();
            while (laserIterator.hasNext()) {
                Laser laser = laserIterator.next();
                if (laser.collidesWith(enemy.getBoundingBox())) {
                    laserIterator.remove();
                    enemyIterator.remove();
                    break;
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
