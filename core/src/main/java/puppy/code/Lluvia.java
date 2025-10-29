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

    // Variables para las 7 hojas y sonidos
    private Texture sheetComida1, sheetComida2, sheetComida3, sheetComida4, sheetComida5;
    private Texture sheetItemHostil;
    private Texture sheetDoblePuntos;
    private Texture sheetVida;
    private Sound comerSound;
    private Music yeehawMusic;
    private Sound grabDoblePuntosSound;
    private Sound vidaSound;

    public Lluvia(Texture c1, Texture c2, Texture c3, Texture c4, Texture c5,
                  Texture itemHostil, Texture DoblePuntos, Texture vida,
                  Sound comerSound, Music yeehawMusic, Sound grabDoblePuntosSound, Sound vidaSound) {

        this.sheetComida1 = c1;
        this.sheetComida2 = c2;
        this.sheetComida3 = c3;
        this.sheetComida4 = c4;
        this.sheetComida5 = c5;
        this.sheetItemHostil = itemHostil;
        this.sheetDoblePuntos = DoblePuntos;
        this.sheetVida = vida;
        this.comerSound = comerSound;
        this.yeehawMusic = yeehawMusic;
        this.grabDoblePuntosSound = grabDoblePuntosSound;
        this.vidaSound = vidaSound;
    }

    public void crear() {
        items = new Array<ItemCaido>();
        crearGotaDeLluvia();
        yeehawMusic.setLooping(true);
        yeehawMusic.play();
    }

    private void crearGotaDeLluvia() {
        ItemCaido nuevoItem;

        // Decide la categoría (Buena, Mala, PowerUp, Vida)
        int categoriaRNG = MathUtils.random(1, 100);

        //  Probabilidades
        // 65% Comida
        // 20% Mala
        // 10% PowerUp
        //  5% Vida

        if (categoriaRNG <= 65) {

            // --- 65% de chance de que sea COMIDA BUENA ---

            int comidaRNG = MathUtils.random(1, 5);
            if (comidaRNG == 1)
                nuevoItem = new Champinhon(sheetComida1, comerSound);

            else if (comidaRNG == 2)
                nuevoItem = new Chorizo(sheetComida2, comerSound);

            else if (comidaRNG == 3)
                nuevoItem = new Pinha(sheetComida3, comerSound);

            else if (comidaRNG == 4)
                nuevoItem = new Queso(sheetComida4, comerSound);

            else
                nuevoItem = new Tomate(sheetComida5, comerSound);


        }

        else if (categoriaRNG <= 85)
            // 20% de chance de que sea GOTA MALA --- (de 66 a 85)
            nuevoItem = new ItemHostil(sheetItemHostil);

        else if (categoriaRNG <= 95)
            // 10% de chance de que sea POWER-UP --- (de 86 a 95)
            nuevoItem = new DoblePuntos(sheetDoblePuntos, grabDoblePuntosSound);

        else
            // 5% de chance de que sea VIDA --- (de 96 a 100)
            nuevoItem = new Vida(sheetVida,vidaSound);


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

    public void destruir() {
        comerSound.dispose();
        yeehawMusic.dispose();
        grabDoblePuntosSound.dispose();
    }

    public void detenerMusica() {
        yeehawMusic.stop();
    }
}
