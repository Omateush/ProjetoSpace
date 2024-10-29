package pt.uma.tpsi.arqd.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;

public class Explosion {
    private Animation<TextureRegion> explosionAnimation;
    private float stateTime;
    private float x, y;
    private boolean finished;
    private Texture explosionTexture;

    public Explosion(float x, float y) {
        this.x = x;
        this.y = y;
        explosionTexture = new Texture("explosion.png");

        // Ajuste o número de frames com base na imagem "explosion.png"
        TextureRegion[][] tmpFrames = TextureRegion.split(explosionTexture, explosionTexture.getWidth() / 5, explosionTexture.getHeight());
        TextureRegion[] frames = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            frames[i] = tmpFrames[0][i];
        }

        explosionAnimation = new Animation<>(0.1f, frames);
        stateTime = 0f;
        finished = false;
    }

    public void render(SpriteBatch batch) {
        if (!finished) {
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = explosionAnimation.getKeyFrame(stateTime, false);
            batch.draw(currentFrame, x, y);

            if (explosionAnimation.isAnimationFinished(stateTime)) {
                finished = true;
            }
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void dispose() {
        explosionTexture.dispose();
    }
}
