package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class VidaNoise extends ItemCaido {

    public VidaNoise(Texture sheet) {
        super(sheet, 18, 100, 100, 0.05f, 200f);

    }

    public void onHit(IJugador jugador) {

        jugador.sumarVida(1);
    }
}
