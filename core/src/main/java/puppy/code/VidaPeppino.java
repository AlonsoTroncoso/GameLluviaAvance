package puppy.code;
import com.badlogic.gdx.graphics.Texture;

public class VidaPeppino extends ItemCaido {

    public VidaPeppino(Texture sheet) {
        super(sheet, 19, 64, 64, 0.05f, 200f);
    }

    @Override
    public void onHit(IJugador jugador) {
        jugador.sumarVida(1); // El jugador se encarga del sonido
    }
}
