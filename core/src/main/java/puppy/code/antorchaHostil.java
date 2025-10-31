package puppy.code;
import com.badlogic.gdx.graphics.Texture;

public class antorchaHostil extends ItemCaido {

    public antorchaHostil(Texture sheet) {
        super(sheet, 7, 93, 97, 0.05f, 400f);
    }

    @Override
    public void onHit(IJugador jugador) {
        jugador.dañar("QUEMADURA");
    }
}
