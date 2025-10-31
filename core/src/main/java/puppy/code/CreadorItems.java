package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;


public class CreadorItems {
    // Variables para las 7 hojas y sonidos
    private Texture sheetComida1, sheetComida2, sheetComida3, sheetComida4, sheetComida5, sheetItemHostil,
        sheetAntorchaHostil, sheetDoblePuntos, sheetVidaNoise, sheetVidaPeppino;

    private Sound comerSound,grabDoblePuntosSound;
    private GameLluvia.CharacterChoice personajeActual;

    public CreadorItems(Texture c1, Texture c2, Texture c3, Texture c4, Texture c5, Texture itemHostil, Texture antorchaHostil, Texture DoblePuntos, Texture vidaNoise, Texture vidaPeppino, GameLluvia.CharacterChoice personaje, Sound comerSound, Sound grabDoblePuntosSound) {
        this.sheetComida1 = c1;
        this.sheetComida2 = c2;
        this.sheetComida3 = c3;
        this.sheetComida4 = c4;
        this.sheetComida5 = c5;
        this.sheetItemHostil = itemHostil;
        this.sheetAntorchaHostil = antorchaHostil;
        this.sheetDoblePuntos = DoblePuntos;
        this.sheetVidaNoise = vidaNoise;
        this.sheetVidaPeppino = vidaPeppino;
        this.personajeActual = personaje;
        this.comerSound = comerSound;
        this.grabDoblePuntosSound = grabDoblePuntosSound;
    }

    public ItemCaido crearItemAleatorio() {
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
        else if (categoriaRNG <= 85) {
            // --- 20% de chance de que sea ITEM MALO ---
            if(MathUtils.randomBoolean())
                nuevoItem = new ItemHostil(sheetItemHostil);
            else
                nuevoItem = new antorchaHostil(sheetAntorchaHostil);
        }
        else if (categoriaRNG <= 95) {
            // 10% de chance de que sea POWER-UP ---
            nuevoItem = new DoblePuntos(sheetDoblePuntos, grabDoblePuntosSound);
        }
        else {
            // 5% de chance de que sea VIDA ---
            if(personajeActual == GameLluvia.CharacterChoice.PERSONAJE_1)
                nuevoItem = new VidaNoise(sheetVidaNoise);
            else
                nuevoItem = new VidaPeppino(sheetVidaPeppino);
        }

        return nuevoItem;
    }

}
