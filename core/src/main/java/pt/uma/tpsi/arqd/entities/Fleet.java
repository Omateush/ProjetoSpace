package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class Fleet {
    private ArrayList<EnemyShip> enemyShips;
    private final int enemiesPerRow = 10;
    private final float enemyWidth = 50, enemyHeight = 50;
    private final float spacingX = 100, spacingY = 100;

    public Fleet(SpriteBatch batch) {
        enemyShips = new ArrayList<>();

        // Linha 1: Inimigos grandes (enemy-big.png)
        for (int i = 0; i < enemiesPerRow; i++) {
            int x = (int) (i * (enemyWidth + spacingX));
            int y = (int) (800 - 100); // Posição na tela para a primeira fileira
            enemyShips.add(new EnemyShip(batch, x, y, (int) enemyWidth, (int) enemyHeight, "enemy-big.png", 2, 1));
        }

        // Linha 2: Inimigos médios (enemy-medium.png)
        for (int i = 0; i < enemiesPerRow; i++) {
            int x = (int) (i * (enemyWidth + spacingX));
            int y = (int) (800 - 200); // Posição na tela para a segunda fileira
            enemyShips.add(new EnemyShip(batch, x, y, (int) enemyWidth, (int) enemyHeight, "enemy-medium.png", 2, 1));
        }

        // Linha 3: Inimigos pequenos (enemy-small.png)
        for (int i = 0; i < enemiesPerRow; i++) {
            int x = (int) (i * (enemyWidth + spacingX));
            int y = (int) (800 - 300); // Posição na tela para a terceira fileira
            enemyShips.add(new EnemyShip(batch, x, y, (int) enemyWidth, (int) enemyHeight, "enemy-small.png", 2, 1));
        }
    }

    public void render(SpriteBatch batch, ArrayList<Laser> lasers) {
        Iterator<EnemyShip> enemyIterator = enemyShips.iterator();
        while (enemyIterator.hasNext()) {
            EnemyShip enemyShip = enemyIterator.next();
            enemyShip.render(batch);

            // Verifica colisões com lasers
            for (Laser laser : lasers) {
                if (laser.getBoundingBox().overlaps(enemyShip.getBoundingBox())) {
                    enemyIterator.remove(); // Remove a nave atingida
                    lasers.remove(laser); // Remove o laser que atingiu
                    break; // Sai do loop após encontrar a colisão
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
