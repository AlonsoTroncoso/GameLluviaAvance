package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

//Hereda de JugadorBase
public class Peppino extends JugadorBase {

    private Sound sonidoHerido, vidaSound, vidaSound2, vidaSound3, sonidoQuemandose, sonidoQuemandose2,
        sonidoQuemandose3, sonidoGolpe, sonidoHerido2;

    public Peppino(Texture idleSheet, Texture moveSheet, Texture sheetQuemado, Texture sheetRecuperando,
                   Texture sheetGolpeado, Texture sheetDash, Sound ss, Sound vs, Sound vs2, Sound vs3,
                   Sound sq, Sound sq2, Sound sq3, Sound ds, Sound sg, Sound ss2,
                   int keyIzquierda, int keyDerecha, int keyDash) {


        super(idleSheet, moveSheet, sheetQuemado, sheetRecuperando, sheetGolpeado, sheetDash, ds,
            keyIzquierda, keyDerecha, keyDash);

        this.sonidoHerido = ss;
        this.vidaSound = vs;
        this.vidaSound2 = vs2;
        this.vidaSound3 = vs3;
        this.sonidoQuemandose = sq;
        this.sonidoQuemandose2 = sq2;
        this.sonidoQuemandose3 = sq3;
        this.sonidoGolpe = sg;
        this.sonidoHerido2 = ss2;
    }


    @Override protected int getVidasIniciales() { return 5; }
    @Override protected int getVidasMaximas() { return 10; }
    @Override protected float getVelocidadDash() { return 690f; }
    @Override protected float getDuracionInvencible() { return 0.5f; }
    @Override protected float getFlickerRate() { return 0.05f; }

    @Override
    protected void setValoresReboteQuemadura() {
        this.velYRebote = 950f;
        this.velXRebote = facingRight ? -900f : 900f;
    }

    @Override
    protected void setValoresReboteGolpe() {
        this.velYRebote = 250f;
        this.velXRebote = facingRight ? -450f : 450f;
    }

    @Override
    protected void reproducirSonidoVida() {
        // Lógica de 3 sonidos de Peppino
        int sonidoRNG = MathUtils.random(1, 3);
        if(sonidoRNG==1) vidaSound.play();
        else if(sonidoRNG==2) vidaSound2.play();
        else vidaSound3.play();
    }

    @Override
    protected void reproducirSonidoQuemadura() {
        // Lógica de 3 sonidos de quemadura de Peppino
        int sonidoRNG = MathUtils.random(1, 3);
        if(sonidoRNG==1) sonidoQuemandose.play();
        else if(sonidoRNG==2) sonidoQuemandose2.play();
        else sonidoQuemandose3.play();
    }

    @Override
    protected void reproducirSonidoGolpeNormal() {
        // Lógica de golpe de Peppino
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
        batch.draw(currentFrame, player.x, player.y, player.width, player.height);
    }



    @Override protected Animation<TextureRegion> crearAnimacionIdle() {
        return createAnimationFromSheet(idleSheet, 17, 100, 100, 0.05f);
    }
    @Override protected Animation<TextureRegion> crearAnimacionMove() {
        return createAnimationFromSheet(moveSheet, 12, 100, 100, 0.05f);
    }
    @Override protected Animation<TextureRegion> crearAnimacionQuemado() {
        return createAnimationFromSheet(sheetQuemado, 5, 100, 100, 0.05f);
    }
    @Override protected Animation<TextureRegion> crearAnimacionRecuperando() {
        return createAnimationFromSheet(sheetRecuperando, 14, 100, 100, 0.05f);
    }
    @Override protected Animation<TextureRegion> crearAnimacionGolpeado() {
        return createAnimationFromSheet(sheetGolpeado, 11, 100, 100, 0.05f);
    }
    @Override protected Animation<TextureRegion> crearAnimacionDash() {
        return createAnimationFromSheet(sheetDash, 12, 100, 100, 0.05f);
    }
}
