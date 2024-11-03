package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.game.Animator;

public class LargeShip extends Ship {
    private Animator animator;

    public LargeShip(SpriteBatch batch, float x, float y, float width, float height) {
        super(x, y, width, height, 150); // Define saúde inicial para LargeShip
        this.animator = new Animator(batch, "enemy-big.png", 2, 1); // Imagem e configuração para LargeShip
    }

    @Override
    public void render(SpriteBatch batch) {
        animator.render(batch, (int) x, (int) y);
    }

    @Override
    public void shoot() {
        lasers.add(new Laser(x, y - height, 5, 10, -10, 30)); // Tiro padrão com 30 de dano
    }

    @Override
    public void dispose() {
        animator.dispose();
    }
}
