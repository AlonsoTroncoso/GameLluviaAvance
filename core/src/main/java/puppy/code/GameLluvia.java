package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameLluvia extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Music musicaMenu;
    public Sound vidaNoiseSound, vidaPeppinoSound;

    public enum CharacterChoice {
        PERSONAJE_1,
        PERSONAJE_2
    }

    //Por defecto, el personaje 1 está seleccionado
    public CharacterChoice personajeSeleccionado = CharacterChoice.PERSONAJE_1;


    public Texture idleSheetP1, moveSheetP1, staticNoise;
    public Texture idleSheetP2, moveSheetP2, staticPeppino;
    public Sound hurtNoiseSound, hurtPeppinoSound;

    @Override
    public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();

        musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("pDeluxe.wav"));
        musicaMenu.setLooping(true);

        // Personaje 1
        idleSheetP1 = new Texture(Gdx.files.internal("noiseQuieto.png"));
        moveSheetP1 = new Texture(Gdx.files.internal("noiseMoviendose.png"));
        hurtNoiseSound = Gdx.audio.newSound(Gdx.files.internal("noiseHerido.mp3"));
        vidaNoiseSound = Gdx.audio.newSound(Gdx.files.internal("noiseHealthGrab.wav"));
        staticNoise = new Texture(Gdx.files.internal("staticNoise.png"));

        // Personaje 2
        idleSheetP2 = new Texture(Gdx.files.internal("peppinoQuieto.png"));
        moveSheetP2 = new Texture(Gdx.files.internal("peppinoMoving.png"));
        hurtPeppinoSound = Gdx.audio.newSound(Gdx.files.internal("peppinoHerido.wav"));
        staticPeppino = new Texture(Gdx.files.internal("staticPeppino.png"));
        vidaPeppinoSound = Gdx.audio.newSound(Gdx.files.internal("peppinoHealthGrab.wav"));

        // Inicia el juego en el Menú
        this.setScreen(new MenuPrincipalScreen(this));
    }

    @Override
    public void render () {
        super.render(); // Llama al render de la pantalla activa
    }

    @Override
    public void dispose () {

        batch.dispose();
        font.dispose();

        idleSheetP1.dispose();
        moveSheetP1.dispose();
        idleSheetP2.dispose();
        moveSheetP2.dispose();
        staticNoise.dispose();
        staticPeppino.dispose();
        hurtNoiseSound.dispose();
        hurtPeppinoSound.dispose();
        vidaNoiseSound.dispose();
        vidaPeppinoSound.dispose();
        musicaMenu.dispose();
    }
}
