package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

// Hereda de JugadorBase
public class Noise extends JugadorBase {

    //  Almacena sus sonidos únicos
    private Sound sonidoHerido, vidaSound, vidaSound2, vidaSound3, vidaSound4, vidaSound5,
        sonidoQuemandose, sonidoGolpe, sonidoHerido2;


    public Noise(Texture idleSheet, Texture moveSheet, Texture sheetQuemado, Texture sheetRecuperando,
                 Texture sheetGolpeado, Texture sheetDash, Sound ss, Sound vs, Sound vs2, Sound vs3, Sound vs4, Sound vs5,
                 Sound sq, Sound ds, Sound sg, Sound ss2,
                 int keyIzquierda, int keyDerecha, int keyDash) {

        super(idleSheet, moveSheet, sheetQuemado, sheetRecuperando, sheetGolpeado, sheetDash, ds,
            keyIzquierda, keyDerecha, keyDash);


        this.sonidoHerido = ss;
        this.vidaSound = vs;
        this.vidaSound2 = vs2;
        this.vidaSound3 = vs3;
        this.vidaSound4 = vs4;
        this.vidaSound5 = vs5;
        this.sonidoQuemandose = sq;
        this.sonidoGolpe = sg;
        this.sonidoHerido2 = ss2;
    }



    @Override
    protected int getVidasIniciales() {
        return 3;
    }

    @Override
    protected int getVidasMaximas() {
        return 5;
    }

    @Override
    protected float getVelocidadDash() {
        return 650f;
    }

    @Override
    protected float getDuracionInvencible() {
        return 1.0f;
    }

    @Override
    protected float getFlickerRate() {
        return 0.1f;
    }

    @Override
    protected void setValoresReboteQuemadura() {
        this.velYRebote = 1000f;
        this.velXRebote = facingRight ? -700f : 700f;
    }

    @Override
    protected void setValoresReboteGolpe() {
        this.velYRebote = 300f;
        this.velXRebote = facingRight ? -450f : 450f;
    }

    @Override
    protected void reproducirSonidoVida() {
        int sonidoRNG = MathUtils.random(1, 5);
        if(sonidoRNG==1) vidaSound.play();
        else if(sonidoRNG==2) vidaSound2.play();
        else if(sonidoRNG==3) vidaSound3.play();
        else if(sonidoRNG==4) vidaSound4.play();
        else vidaSound5.play();
    }

    @Override
    protected void reproducirSonidoQuemadura() {
        sonidoQuemandose.play();
    }

    @Override
    protected void reproducirSonidoGolpeNormal() {

        if(MathUtils.randomBoolean()) {
            sonidoHerido.play();
            sonidoGolpe.play();
        } else {
            sonidoHerido2.play();
            sonidoGolpe.play();
        }
    }

    @Override
    protected void dibujarSpriteEspecial(SpriteBatch batch, TextureRegion currentFrame) {

        if (currentState == State.DASHING) {
            float drawWidth = currentFrame.getRegionWidth();
            float drawHeight = currentFrame.getRegionHeight();
            float offsetX = (drawWidth - player.width) / 2f;
            float offsetY = (drawHeight - player.height) / 2f;
            float drawX = player.x - offsetX;
            float drawY = player.y - offsetY;
            batch.draw(currentFrame, drawX, drawY, drawWidth, drawHeight);
        }
        else
            batch.draw(currentFrame, player.x, player.y, player.width, player.height);

    }

    @Override
    protected Animation<TextureRegion> crearAnimacionIdle() {
        return createAnimationFromSheet(idleSheet, 15, 100, 100, 0.05f);
    }

    @Override
    protected Animation<TextureRegion> crearAnimacionMove() {
        return createAnimationFromSheet(moveSheet, 12, 100, 100, 0.05f);
    }

    @Override
    protected Animation<TextureRegion> crearAnimacionQuemado() {
        return createAnimationFromSheet(sheetQuemado, 6, 100, 100, 0.05f);
    }

    @Override
    protected Animation<TextureRegion> crearAnimacionRecuperando() {
        return createAnimationFromSheet(sheetRecuperando, 5, 100, 100, 0.05f);
    }

    @Override
    protected Animation<TextureRegion> crearAnimacionGolpeado() {
        return createAnimationFromSheet(sheetGolpeado, 3, 100, 100, 0.05f);
    }

    @Override
    protected Animation<TextureRegion> crearAnimacionDash() {
        return createAnimationFromSheet(sheetDash, 12, 200, 100, 0.05f);
    }
}
