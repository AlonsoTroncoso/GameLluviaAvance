package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

public class DoblePuntos extends ItemPowerUp {
    
    public DoblePuntos(Texture sheet, Sound grabDoblePuntosSound) {
        // (sheet, frameCount, frameWidth, frameHeight, frameDuration, velocidad)
        super(sheet, 24, 64, 64, 0.1f, 200f, grabDoblePuntosSound);
    }

    @Override
    public void aplicarEfecto(IJugador jugador){
        jugador.activarDoblePuntos();
    } 
}
