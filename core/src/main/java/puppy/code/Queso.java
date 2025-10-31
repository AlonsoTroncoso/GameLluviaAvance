package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;


public class Queso extends ItemCaido {
    private Sound comerSound;

    public Queso(Texture sheet, Sound comerSound) {
        super(sheet, 16, 32, 32, 0.05f, 300f);
        this.comerSound = comerSound;
    }

    @Override
    public void onHit(IJugador jugador) {
        jugador.sumarPuntos(10); // Dan puntos
        comerSound.play();
    }
}
