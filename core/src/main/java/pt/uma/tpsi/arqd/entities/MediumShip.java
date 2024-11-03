package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.game.Animator;

public class MediumShip extends Ship {
    private Animator animator;

    public MediumShip(SpriteBatch batch, float x, float y, float width, float height) {
        super(x, y, width, height, 100); // Define saúde inicial para MediumShip
        this.animator = new Animator(batch, "enemy-medium.png", 2, 1); // Imagem e configuração para MediumShip
    }

    @Override
    public void render(SpriteBatch batch) {
        animator.render(batch, (int) x, (int) y);
    }

    @Override
    public void shoot() {
        lasers.add(new Laser(x, y - height, 5, 10, -10, 20)); // Tiro padrão com 20 de dano
    }

    @Override
    public void dispose() {
        animator.dispose();
    }
}
