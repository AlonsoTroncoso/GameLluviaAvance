package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

//la idea es que en cada comida hace lo mismo pero cambia el sprite y el frame count
//entonces asi la comida quedá mas cortita y es fácil incluir nueva comida o clases con el mismo comportamiento

public abstract class ItemComida extends ItemCaido {
    
    private Sound comerSound;
    
    public ItemComida(Texture sheet, int frameCount, Sound comerSound) {
        super(sheet, frameCount, 32, 32, 0.1f, 300f); //estos numeros no cambian asi que entran asi nomas
        this.comerSound = comerSound;
    }
    
    //efecto onHit que se repite en todas las comidas
    @Override
    public void onHit(IJugador jugador) {
        jugador.sumarPuntos(10); // Dan puntos
        comerSound.play();
    }
}
