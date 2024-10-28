package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator; // Importando a classe Animator
import java.util.ArrayList;

public class EnemyShip {
    private float x, y;
    private int width, height;
    private Animator animator;
    private ArrayList<Laser> lasers; // Armazena os lasers disparados por esta nave

    public EnemyShip(SpriteBatch batch, float x, float y, int width, int height, String spriteSheetPath, int frameCols, int frameRows) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.animator = new Animator(batch, spriteSheetPath, frameCols, frameRows);
        this.lasers = new ArrayList<>();
    }

    public void render(SpriteBatch batch) {
        animator.render(batch, (int)x, (int)y);
        for (Laser laser : lasers) {
            laser.update();
            laser.render(batch);
        }
    }

    public void shoot() {
        float laserX = x + width / 2;
        float laserY = y - 10; // Ajusta a posição do laser para disparar abaixo da nave
        Laser laser = new Laser(laserX, laserY, 5, 10, -5); // Velocidade negativa para descer
        lasers.add(laser);
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, width, height);
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public void dispose() {
        animator.dispose();
        for (Laser laser : lasers) {
            laser.dispose();
        }
    }
}
