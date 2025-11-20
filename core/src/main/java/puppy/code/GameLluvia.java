package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameLluvia extends Game {

    public SpriteBatch batch;

    public enum CharacterChoice {
        PERSONAJE_1,
        PERSONAJE_2
    }

    public enum LevelChoice {
        NIVEL_1,
        NIVEL_2
    }

    public LevelChoice nivelSeleccionado = LevelChoice.NIVEL_1;
    //Por defecto, el personaje 1 está seleccionado
    public CharacterChoice personajeSeleccionado = CharacterChoice.PERSONAJE_1;

    @Override
    public void create () {
        batch = new SpriteBatch();
        GestorAssets.getInstance().load();

        Music musicaMenu = GestorAssets.getInstance().musicaMenu;
        musicaMenu.setLooping(true);

        this.setScreen(new MenuPrincipalScreen(this));

    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        batch.dispose();
        GestorAssets.getInstance().dispose();
    }
}
