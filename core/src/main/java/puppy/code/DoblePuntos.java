package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

public class DoblePuntos extends ItemCaido {
    private Sound grabDoblePuntosSound;

    public DoblePuntos(Texture sheet, Sound grabDoblePuntosSound) {
        // (sheet, frameCount, frameWidth, frameHeight, frameDuration, velocidad)
        super(sheet, 24, 64, 64, 0.05f, 200f); // <-- ¡PON TUS NÚMEROS!
        this.grabDoblePuntosSound = grabDoblePuntosSound;
    }

    @Override
    public void onHit(IJugador jugador) {
        jugador.activarDoblePuntos();
        grabDoblePuntosSound.play();
    }
}
