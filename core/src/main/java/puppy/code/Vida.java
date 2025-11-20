package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

public class Vida extends ItemCaido {

    public Vida(Texture sheet) {
        super(sheet, 18, 100, 100, 0.1f, 200f);

    }

    public void onHit(IJugador jugador) {
        jugador.sumarVida(1);
    }
}
