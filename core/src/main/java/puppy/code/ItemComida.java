package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

public abstract class ItemComida extends ItemCaido {

    protected Sound comerSound;

    public ItemComida(Texture sheet, int frameCount, int frameWidth, int frameHeight, float frameDuration, float velocidadCaida, Sound comerSound) {
        // heredo todo lo de la padre
        super(sheet, frameCount, frameWidth, frameHeight, frameDuration, velocidadCaida);
        //el sonido es unico
        this.comerSound = comerSound;
    }
    //esta es el metodo que se repite en todas las clases comida
    //la idea es que el codigo no se repita tanto, al menos eso dicen los POO
    //ahora todas las comidas heredan de esta, en vez de estar todo desordenado con una abstracta
    @Override
    public void onHit(IJugador jugador) {
        jugador.sumarPuntos(10); // Lógica centralizada
        comerSound.play();
    }

}
