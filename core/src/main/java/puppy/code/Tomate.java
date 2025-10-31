package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;


public class Tomate extends ItemCaido {
    private Sound comerSound;

    public Tomate(Texture sheet, Sound comerSound) {
        super(sheet, 10, 32, 32, 0.05f, 300f);
        this.comerSound = comerSound;
    }

    @Override
    public void onHit(IJugador jugador) {
        jugador.sumarPuntos(10); // Dan puntos
        comerSound.play();
    }
}
