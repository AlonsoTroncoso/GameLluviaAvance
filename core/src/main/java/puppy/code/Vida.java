package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

public class Vida extends ItemPowerUp {

    public Vida(Texture sheet, Sound vidaSound) {
        super(sheet, 18, 100, 100, 0.1f, 200f, vidaSound);
    }

    //entonces asi no repito tanto codigo sacando el onHit y metiendo solo el aplicar efecto
    @Override
    public void aplicarEfecto(IJugador jugador){
        jugador.sumarVida(1);
    }
}

