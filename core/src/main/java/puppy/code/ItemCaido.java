package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class ItemCaido {

    protected Rectangle area;
    protected float velocidadCaida, stateTimer;
    private Animation<TextureRegion> animation;

    public ItemCaido(Texture sheet, int frameCount, int frameWidth, int frameHeight, float frameDuration, float velocidadCaida) {

        this.animation = createAnimationFromSheet(sheet, frameCount, frameWidth, frameHeight, frameDuration);
        this.stateTimer = 0f;
        this.velocidadCaida = velocidadCaida;
        this.area = new Rectangle();
        area.x = MathUtils.random(0, 800 - frameWidth);
        area.y = 480;
        area.width = frameWidth;
        area.height = frameHeight;
    }

    private Animation<TextureRegion> createAnimationFromSheet(Texture sheet, int frameCount, int frameWidth, int frameHeight, float frameDuration) {

        TextureRegion[][] tmp = TextureRegion.split(sheet, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[frameCount];

        int index = 0;
        for (int j = 0; j < frameCount; j++)
            frames[index++] = tmp[0][j];

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, frames);
        anim.setPlayMode(Animation.PlayMode.LOOP);
        return anim;
    }

    public void actualizarMovimiento() {
        area.y -= velocidadCaida * Gdx.graphics.getDeltaTime();
    }


    public void dibujar(SpriteBatch batch) {

        stateTimer += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(stateTimer, true);
        batch.draw(currentFrame, area.x, area.y, area.width, area.height);
    }



    public Rectangle getArea() {
        return area;
    }

    public boolean estaFueraDePantalla() {
        return area.y<=30;
    }

    public abstract void onHit(IJugador jugador);
}
