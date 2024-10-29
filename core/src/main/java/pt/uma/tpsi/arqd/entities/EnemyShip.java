package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;

import pt.uma.tpsi.arqd.game.Animator;

import java.util.ArrayList;

public class EnemyShip {
    private float x, y;
    private Animator animator;
    private ArrayList<Laser> lasers;

    public EnemyShip(SpriteBatch batch, float x, float y, float width, float height, String texturePath, int columns, int rows) {
        this.x = x;
        this.y = y;
        this.animator = new Animator(batch, texturePath, columns, rows);
        this.lasers = new ArrayList<>();
    }

    public void render(SpriteBatch batch) {
        animator.render(batch, (int) x, (int) y);
    }

    public void shoot() {
        lasers.add(new Laser(x + 20, y - 10, 5, 10, -10)); // Disparo do laser
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, animator.getWidth(), animator.getHeight());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void dispose() {
        animator.dispose();
        for (Laser laser : lasers) {
            laser.dispose();
        }
    }
}
