package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.game.Animator;

public class SmallShip extends Ship {
    private Animator animator;

    public SmallShip(SpriteBatch batch, float x, float y, float width, float height) {
        super(x, y, width, height, 50); // Define saúde inicial para SmallShip
        this.animator = new Animator(batch, "enemy-small.png", 2, 1); // Imagem e configuração para SmallShip
    }

    @Override
    public void render(SpriteBatch batch) {
        animator.render(batch, (int) x, (int) y);
    }

    @Override
    public void shoot() {
        lasers.add(new Laser(x, y - height, 5, 10, -10, 10)); // Tiro padrão com 10 de dano
    }

    @Override
    public void dispose() {
        animator.dispose();
    }
}
