package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.game.Animator;
import pt.uma.tpsi.arqd.game.GameHUD;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class Player {
    private int posX, posY;
    private int health;
    private Animator animator;
    private ArrayList<Laser> lasers; // Lista de lasers disparados
    private float laserWidth = 10, laserHeight = 20, laserSpeed = 10; // Dimensões e velocidade do laser
    private GameHUD hud;

    public Player(SpriteBatch batch, int posX, int posY, GameHUD hud) {
        this.posX = posX;
        this.posY = posY;
        this.hud = hud;
        this.health = 100;
        animator = new Animator(batch, "ship.png", 5, 2);
        lasers = new ArrayList<>();
        this.hud.updatePlayerHealth(health); // Atualizando a saúde no HUD inicialmente
    }

    public void render(SpriteBatch batch) {
        handleInput();
        animator.render(batch, posX, posY);

        // Renderizar e atualizar todos os lasers disparados
        Iterator<Laser> iterator = lasers.iterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.update();

            // Se o laser sair da tela, remove-o da lista
            if (laser.getBoundingBox().y > Gdx.graphics.getHeight()) {
                iterator.remove();
            } else {
                laser.render(batch);
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            posX -= 5;
            if (posX < 0) posX = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            posX += 5;
            if (posX > Gdx.graphics.getWidth() - animator.getWidth()) {
                posX = Gdx.graphics.getWidth() - animator.getWidth();
            }
        }

        // Verifica se a barra de espaço foi pressionada para disparar o laser
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shootLaser();
        }
    }

    private void shootLaser() {
        Laser laser = new Laser(posX + (animator.getWidth() / 2) - (laserWidth / 2), posY + animator.getHeight(), laserWidth, laserHeight, laserSpeed);
        lasers.add(laser);
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public void takeDamage() {
        health -= 10;
        hud.updatePlayerHealth(health); // Atualiza a saúde no HUD
        if (health <= 0) {
            System.out.println("Game Over");
        }
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(posX, posY, animator.getWidth(), animator.getHeight());
    }

    public void dispose() {
        animator.dispose();
        for (Laser laser : lasers) {
            laser.dispose();
        }
    }
}
