package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public abstract class ItemPowerUp extends ItemCaido {

    //doble puntos heredaria de aqui
    public ItemPowerUp(Texture sheet, int frameCount, int frameWidth, int frameHeight, float frameDuration, float velocidadCaida) {
        super(sheet, frameCount, frameWidth, frameHeight, frameDuration, velocidadCaida);
    }
    //se vuelve a heredar, al principio parece mala idea heredar 2 veces, se ve ridiculo
    //pero esto es pa que pueda permitir distintos efectos y ADEMAS tener hijos de hijos si es que es necesario
    //en este juegito lo es, tengo 2 personajes y sus vidas son distintas pero tienen el mismo comportamiento
    @Override
    public abstract void onHit(IJugador jugador);
}
