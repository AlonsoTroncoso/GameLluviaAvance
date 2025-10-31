package puppy.code;
import com.badlogic.gdx.graphics.Texture;

public class ItemHostil extends ItemCaido {

    public ItemHostil(Texture sheet) {
        // (sheet, frameCount, frameWidth, frameHeight, frameDuration, velocidad)
        super(sheet, 10, 106, 125, 0.05f, 400f);
    }

    @Override
    public void onHit(IJugador jugador) {
        jugador.dañar("NORMAL");
    }
}
