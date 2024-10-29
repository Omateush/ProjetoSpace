package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator;
import java.util.ArrayList;
import java.util.Iterator;

public class EnemyShip {
    private int x, y;
    private Animator animator;
    private ArrayList<Laser> lasers;

    public EnemyShip(int x, int y, int width, int height, String texturePath) {
        this.x = x;
        this.y = y;
        this.animator = new Animator(new SpriteBatch(), texturePath, 2, 1); // Configuração do Animator
        this.lasers = new ArrayList<>();
    }

    public void render(SpriteBatch batch) {
        animator.render(batch, x, y); // Renderiza a nave inimiga
        Iterator<Laser> laserIterator = lasers.iterator();
        while (laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            laser.update();
            laser.render(batch);
            if (laser.getY() < 0) { // Limite da tela para lasers dos inimigos
                laserIterator.remove();
            }
        }
    }

    public void shoot() {
        lasers.add(new Laser(x + 20, y - 10, 5, 10, -10)); // Laser disparado na direção do player
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, animator.getWidth(), animator.getHeight());
    }

    public void dispose() {
        animator.dispose();
        for (Laser laser : lasers) {
            laser.dispose();
        }
    }
}
