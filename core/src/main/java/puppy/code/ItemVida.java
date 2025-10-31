package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public abstract class ItemVida extends ItemPowerUp {

    public ItemVida(Texture sheet, int frameCount, int frameWidth, int frameHeight, float frameDuration, float velocidadCaida) {
        super(sheet, frameCount, frameWidth, frameHeight, frameDuration, velocidadCaida);
    }
    //tonces aqui recibo la vida directamente y los hijos reciben este comportamiento
    @Override
    public void onHit(IJugador jugador) {
        jugador.sumarVida(1);
    }
}
