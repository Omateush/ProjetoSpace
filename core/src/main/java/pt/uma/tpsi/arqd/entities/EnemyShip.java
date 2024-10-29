package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator;

import java.util.ArrayList;
import java.util.Iterator;

public class EnemyShip {
    private float x, y;
    private float width, height;
    private Animator animator;
    private ArrayList<Laser> lasers;

    public EnemyShip(SpriteBatch batch, float x, float y, float width, float height, String spriteSheetPath, int frameCols, int frameRows) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.animator = new Animator(batch, spriteSheetPath, frameCols, frameRows);
        this.lasers = new ArrayList<>();
    }

    public void render(SpriteBatch batch) {
        animator.render(batch, (int) x, (int) y); // Converte x e y para int

        Iterator<Laser> laserIterator = lasers.iterator();
        while (laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            laser.update();
            laser.render(batch);

            // Remove o laser se ele sair da tela
            if (laser.getY() < 0) {
                laserIterator.remove();
            }
        }
    }

    public void shoot() {
        lasers.add(new Laser(x + width / 2, y - 10, 5, 10, -10)); // Cria um novo laser que dispara para baixo
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, width, height);
    }

    public void dispose() {
        animator.dispose();
        for (Laser laser : lasers) {
            laser.dispose();
        }
    }
}
