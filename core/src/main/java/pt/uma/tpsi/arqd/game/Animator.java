package pt.uma.tpsi.arqd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {

    private int FRAME_COLS, FRAME_ROWS;
    private Animation<TextureRegion> walkAnimation;
    private Texture walkSheet;
    private float stateTime;
    private int width, height;
    private boolean flip;

    public Animator(SpriteBatch batch, String path, int columns, int rows) {
        this.FRAME_COLS = columns;
        this.FRAME_ROWS = rows;
        this.flip = false;
        walkSheet = new Texture(Gdx.files.internal(path));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
            walkSheet.getWidth() / FRAME_COLS,
            walkSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        walkAnimation = new Animation<>(0.095f, walkFrames);
        stateTime = 0f;
        this.width = walkSheet.getWidth() / FRAME_COLS;
        this.height = walkSheet.getHeight() / FRAME_ROWS;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public void render(SpriteBatch batch, int posX, int posY) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);


        batch.draw(currentFrame, flip ? posX + width : posX, posY, flip ? -width : width, height);
    }

    public void dispose() {
        walkSheet.dispose();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
