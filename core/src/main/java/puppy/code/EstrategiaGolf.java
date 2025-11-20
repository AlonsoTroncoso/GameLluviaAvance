package puppy.code;

import com.badlogic.gdx.math.MathUtils;

public class EstrategiaGolf implements IEstrategiaGeneracion {

    @Override
    public ItemCaido generar(GestorAssets assets, GameLluvia.CharacterChoice personaje) {
        ItemCaido nuevoItem;
        int categoriaRNG = MathUtils.random(1, 100);


        // 40% Comida (Menos comida)
        // 50% Hostil (Muchos más enemigos)
        // 8% PowerUp
        // 2% Vida (Muy poca vida)

        if (categoriaRNG <= 40) {
            int comidaRNG = MathUtils.random(1, 5);
            if (comidaRNG == 1)
                nuevoItem = new Champinhon(assets.sheetComida1, assets.comerSound);
            else if (comidaRNG == 2)
                nuevoItem = new Chorizo(assets.sheetComida2, assets.comerSound);
            else if (comidaRNG == 3)
                nuevoItem = new Pinha(assets.sheetComida3, assets.comerSound);
            else if (comidaRNG == 4)
                nuevoItem = new Queso(assets.sheetComida4, assets.comerSound);
            else
                nuevoItem = new Tomate(assets.sheetComida5, assets.comerSound);
        }

        else if (categoriaRNG <= 90) {
            if (MathUtils.randomBoolean())
                nuevoItem = new ItemHostil(assets.sheetItemHostil);
            else
                nuevoItem = new antorchaHostil(assets.sheetAntorchaHostil);
        }

        else if (categoriaRNG <= 98)
            nuevoItem = new DoblePuntos(assets.sheetDoblePuntos, assets.powerupSound);

        else {
            if (personaje == GameLluvia.CharacterChoice.PERSONAJE_1)
                nuevoItem = new VidaNoise(assets.sheetVidaNoise);
            else
                nuevoItem = new VidaPeppino(assets.sheetVidaPeppino);
        }
        return nuevoItem;
    }
}
