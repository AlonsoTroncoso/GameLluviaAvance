package puppy.code;
import com.badlogic.gdx.graphics.Texture;

public class ItemHostil extends ItemCaido {

    public ItemHostil(Texture sheet) {
        // (sheet, frameCount, frameWidth, frameHeight, frameDuration, velocidad)
        super(sheet, 10, 106, 125, 0.15f, 400f); // <-- ¡PON TUS NÚMEROS!
    }

    @Override
    public void onHit(IJugador jugador) {
        jugador.dañar();
    }
}
