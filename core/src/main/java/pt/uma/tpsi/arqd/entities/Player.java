package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.arqd.game.Animator;
import pt.uma.tpsi.arqd.game.GameHUD;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {
    private int x, y;
    private Animator animator;
    private GameHUD hud;
    private int health = 100;
    private ArrayList<Laser> lasers;
    private ArrayList<Explosion> explosions; // Lista de explosões para o player

    public Player(SpriteBatch batch, int x, int y, GameHUD hud) {
        this.x = x;
        this.y = y;
        this.hud = hud;
        this.health = 100;
        this.animator = new Animator(batch, "ship.png", 5, 2); // Configuração do Animator
        this.lasers = new ArrayList<>();
        this.explosions = new ArrayList<>(); // Inicializa a lista de explosões
        this.hud.updatePlayerHealth(health); // Inicializa a saúde no HUD
    }

    public void render(SpriteBatch batch) {
        handleInput(); // Movimenta e dispara
        animator.render(batch, x, y); // Renderiza o player na tela

        // Renderiza e atualiza os lasers
        Iterator<Laser> laserIterator = lasers.iterator();
        while (laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            laser.update();
            laser.render(batch);
            if (laser.getY() > 800) { // Limite da tela
                laserIterator.remove();
            }
        }

        // Renderiza as explosões
        Iterator<Explosion> explosionIterator = explosions.iterator();
        while (explosionIterator.hasNext()) {
            Explosion explosion = explosionIterator.next();
            explosion.render(batch);
            if (explosion.isFinished()) {
                explosionIterator.remove(); // Remove a explosão após finalizar
            }
        }
    }

    private void handleInput() {
        // Movimento para a esquerda
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= 5;
            if (x < 0) {  // Limite esquerdo da tela
                x = 0;
            }
        }

        // Movimento para a direita
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += 5;
            if (x + animator.getWidth() > Gdx.graphics.getWidth()) {  // Limite direito da tela
                x = Gdx.graphics.getWidth() - animator.getWidth();
            }
        }

        // Disparo
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shoot();
        }
    }

    private void shoot() {
        lasers.add(new Laser(x + 20, y + 40, 5, 10, 10)); // Configura o laser
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public void takeDamage() {
        health -= 10;
        hud.updatePlayerHealth(health); // Atualiza o HUD
        explosions.add(new Explosion(x, y)); // Adiciona uma explosão na posição atual do player
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
