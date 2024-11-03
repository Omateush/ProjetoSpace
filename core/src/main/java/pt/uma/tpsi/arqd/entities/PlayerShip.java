package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.game.Animator;
import pt.uma.tpsi.arqd.game.GameHUD;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayerShip extends Ship {
    private Animator animator;
    private GameHUD hud;
    private ArrayList<Explosion> explosions;

    public PlayerShip(SpriteBatch batch, float x, float y, float width, float height, int health, GameHUD hud) {
        super(x, y, width, height, health);
        this.hud = hud;
        this.animator = new Animator(batch, "ship.png", 5, 2);
        this.explosions = new ArrayList<>();
        this.hud.updatePlayerHealth(health);
    }

    @Override
    public void render(SpriteBatch batch) {
        handleInput();
        animator.render(batch, (int) x, (int) y);

        Iterator<Laser> laserIterator = lasers.iterator();
        while (laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            laser.update();
            laser.render(batch);
            if (laser.getY() > 800) {
                laserIterator.remove();
            }
        }

        renderExplosions(batch); // Renderiza as explosões
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= 5;
            if (x < 0) {
                x = 0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += 5;
            if (x + width > Gdx.graphics.getWidth()) {
                x = Gdx.graphics.getWidth() - width;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shoot();
        }
    }

    @Override
    public void shoot() {
        lasers.add(new Laser(x + width / 2 - 2.5f, y + height, 5, 10, 10, 10));
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        hud.updatePlayerHealth(health);
        explosions.add(new Explosion(x, y)); // Adiciona uma explosão na posição atual do jogador
    }

    private void renderExplosions(SpriteBatch batch) {
        Iterator<Explosion> explosionIterator = explosions.iterator();
        while (explosionIterator.hasNext()) {
            Explosion explosion = explosionIterator.next();
            explosion.render(batch);
            if (explosion.isFinished()) {
                explosionIterator.remove();
            }
        }
    }

    @Override
    public void dispose() {
        animator.dispose();
        for (Laser laser : lasers) {
            laser.dispose();
        }
    }
}
