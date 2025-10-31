// Archivo: IJugador.java
package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public interface IJugador {

    // Métodos que GameLluvia necesita en create() y dispose()
    void crear();
    void destruir();

    // Métodos que GameLluvia necesita en render()
    void actualizar(float delta);
    void actualizarMovimiento();
    void dibujar(SpriteBatch batch);
    boolean estaHerido();
    boolean estaEnDash();

    // Métodos para la UI (Puntos, Vidas, Buffs)
    int getVidas();
    int getPuntos();
    boolean estaDoblePuntos();
    float getTiempoDoblePuntos();

    // Métodos que Lluvia e ItemCaido necesitan para colisiones
    Rectangle getArea();
    void dañar(String tipoDano);
    void sumarPuntos(int pp);
    void sumarVida(int cantidad);
    void activarDoblePuntos();
}
