package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {

    private Array<ItemCaido> items;
    private long lastDropTime;
    private CreadorItems creadorItems; //clase nueva, se usa para POO DIP y SRP



    public Lluvia(CreadorItems creadorItems) {
        this.creadorItems = creadorItems;
    }

    public void crear() {
        items = new Array<ItemCaido>();
        crearGotaDeLluvia();

    }

    private void crearGotaDeLluvia() {
        ItemCaido nuevoItem = creadorItems.crearItemAleatorio();

        items.add(nuevoItem);
        lastDropTime = TimeUtils.nanoTime();
    }


    public void actualizarMovimiento(IJugador jugador) {
        if(TimeUtils.nanoTime() - lastDropTime > 100000000)
            crearGotaDeLluvia();

        for (int i = items.size - 1; i >= 0; i--) {
            ItemCaido item = items.get(i);
            item.actualizarMovimiento();

            if (item.getArea().overlaps(jugador.getArea())) {
                item.onHit(jugador);
                items.removeIndex(i);
            }

            else if (item.estaFueraDePantalla())
                items.removeIndex(i);

        }
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (ItemCaido item : items) {
            item.dibujar(batch);
        }
    }

}
