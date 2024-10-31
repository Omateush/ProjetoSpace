// Ship.java (Classe Base)
package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

public abstract class Ship {
    protected float x, y;
    protected float width, height;
    protected int health;
    protected ArrayList<Laser> lasers;

    public Ship(float x, float y, float width, float height, int health) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.lasers = new ArrayList<>(); // Inicializa a lista de lasers
    }

    // Métodos abstratos para serem implementados pelas subclasses
    public abstract void render(SpriteBatch batch);
    public abstract void shoot();
    public abstract void dispose();

    // Método para acessar os lasers da nave
    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, width, height);
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public int getHealth() {
        return health;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
