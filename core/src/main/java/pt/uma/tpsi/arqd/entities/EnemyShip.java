package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator;
import java.util.ArrayList;

public class EnemyShip {
    private float x, y, width, height;
    private Animator animator;
    private Rectangle boundingBox;
    private ArrayList<Laser> lasers;
    private float laserWidth = 10, laserHeight = 20, laserSpeed = 5;

    public EnemyShip(SpriteBatch batch, float x, float y, float width, float height, String spriteSheetPath, int frameCols, int frameRows) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        animator = new Animator(batch, spriteSheetPath, frameCols, frameRows);
        boundingBox = new Rectangle(x, y, width, height);
        lasers = new ArrayList<>();
    }

    public void render(SpriteBatch batch) {
        updateBoundingBox();
        animator.render(batch, (int) x, (int) y);

        // Renderiza os lasers disparados pelo inimigo
        for (Laser laser : lasers) {
            laser.update(); // Atualiza a posição do laser
            laser.render(batch); // Renderiza o laser
        }
    }

    private void updateBoundingBox() {
        boundingBox.setPosition(x, y);
    }

    public void shoot() {
        // Cria um novo laser na posição atual do inimigo
        Laser laser = new Laser(x + (width / 2) - (laserWidth / 2), y, laserWidth, laserHeight, -laserSpeed);
        lasers.add(laser); // Adiciona o laser à lista de lasers disparados
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void dispose() {
        animator.dispose();
        for (Laser laser : lasers) {
            laser.dispose();
        }
    }
}
