
package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public abstract class ItemPowerUp extends ItemCaido{
    private Sound PowerUpSound;
    
    public abstract void aplicarEfecto(IJugador jugador); //obvio no devuelve nada es un efecto 
    
    //tipico constructor, y recibe todo del padre con el super exceptuando el sonido que lo saca directamente
    public ItemPowerUp(Texture sheet, int frameCount, int frameWidth, int frameHeight, float frameDuration, float velocidadCaida, Sound sound){
        
        super(sheet, frameCount, frameWidth, frameHeight, frameDuration, velocidadCaida);
        this.PowerUpSound = sound;
    }
    
    @Override
    public void onHit(IJugador jugador) {  
        PowerUpSound.play();
        aplicarEfecto(jugador); //esta es funcion abstracta le da a cada power up el efecto que debe aplicar
    }
    
}
