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

    public PlayerShip(SpriteBatch batch, float x, float y, float width, float height, int health, GameHUD hud) {
        super(x, y, width, height, health);
        this.hud = hud;
        this.animator = new Animator(batch, "ship.png", 5, 2); // Configuração da imagem para PlayerShip
        this.hud.updatePlayerHealth(health); // Inicializa o HUD com a saúde do jogador
    }

    @Override
    public void render(SpriteBatch batch) {
        handleInput(); // Movimenta e dispara
        animator.render(batch, (int) x, (int) y); // Renderiza o player na tela, convertendo float para int

        // Renderiza os lasers e remove os que saem da tela
        Iterator<Laser> laserIterator = lasers.iterator();
        while (laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            laser.update();
            laser.render(batch);
            if (laser.getY() > 800) { // Limite da tela
                laserIterator.remove();
            }
        }
    }

    private void handleInput() {
        // Movimento para a esquerda e direita, com limites da tela
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
        lasers.add(new Laser(x + width / 2 - 2.5f, y + height, 5, 10, 10)); // Configura o laser do player
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        hud.updatePlayerHealth(health); // Atualiza o HUD com a nova saúde do jogador
    }

    @Override
    public void dispose() {
        animator.dispose();
        for (Laser laser : lasers) {
            laser.dispose();
        }
    }
}
