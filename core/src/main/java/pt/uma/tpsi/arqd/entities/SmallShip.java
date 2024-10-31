package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.arqd.game.Animator;

public class SmallShip extends Ship {
    private Animator animator;

    public SmallShip(SpriteBatch batch, float x, float y) {
        super(x, y, 60, 60, 100);
        this.animator = new Animator(batch, "enemy-small.png", 2, 1);
    }

    @Override
    public void render(SpriteBatch batch) {
        animator.render(batch, (int) x, (int) y);
    }

    @Override
    public void shoot() {
        lasers.add(new Laser(x + width / 2, y, 5, 10, -10));
    }

    @Override
    public void dispose() {
        animator.dispose();
    }
}
