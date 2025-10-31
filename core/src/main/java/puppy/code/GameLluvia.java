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

    public enum LevelChoice {
        NIVEL_1,
        NIVEL_2
    }

    public LevelChoice nivelSeleccionado = LevelChoice.NIVEL_1;
    public Texture previewNivel1;
    public Texture previewNivel2;

    //Por defecto, el personaje 1 está seleccionado
    public CharacterChoice personajeSeleccionado = CharacterChoice.PERSONAJE_1;


    public Texture idleSheetP1, moveSheetP1, sheetQuemadoP1, sheetRecuperandoseP1;
    public Texture idleSheetP2, moveSheetP2, sheetQuemadoP2, sheetRecuperandoseP2;
    public Texture sheetVidaNoise, sheetVidaPeppino;
    public Texture sheetHurtNoise, sheetHurtPeppino;
    public Texture sheetDashNoise, sheetDashPeppino;
    public Texture fondoSelectPersonaje;
    public Texture sheetFaceNoise, sheetFacePeppino;
    public Texture sheetIndicadorNoise, sheetIndicadorPeppino;
    public Sound hurtNoiseSound, hurtPeppinoSound, burningNoiseSound, burningPeppinoSound;
    public Sound dashSound, atrasSound;

    @Override
    public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("pizzaFont.fnt"));
        font.getData().setScale(1f);
        musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("pDeluxe.mp3"));
        musicaMenu.setLooping(true);

        // Personaje 1
        idleSheetP1 = new Texture(Gdx.files.internal("noiseQuieto.png"));
        moveSheetP1 = new Texture(Gdx.files.internal("noiseMoviendose.png"));
        sheetQuemadoP1 = new Texture(Gdx.files.internal("noiseCapaQuemada.png"));
        sheetRecuperandoseP1 = new Texture(Gdx.files.internal("noiseCapaQuemadaRecuperacion.png"));
        hurtNoiseSound = Gdx.audio.newSound(Gdx.files.internal("noiseHerido.mp3"));
        burningNoiseSound = Gdx.audio.newSound(Gdx.files.internal("TheNoiseBurning.wav"));
        sheetFaceNoise = new Texture(Gdx.files.internal("noiseAngry.png"));
        vidaNoiseSound = Gdx.audio.newSound(Gdx.files.internal("noiseHealthGrab.wav"));
        sheetIndicadorNoise = new Texture(Gdx.files.internal("noiseSelect.png"));

        sheetVidaNoise = new Texture(Gdx.files.internal("noiseHP.png"));
        sheetHurtNoise = new Texture(Gdx.files.internal("hurtNoiseAnim.png"));
        sheetDashNoise = new Texture(Gdx.files.internal("noiseDash.png"));

        fondoSelectPersonaje = new Texture(Gdx.files.internal("chooseCharacter.png"));
        dashSound =  Gdx.audio.newSound(Gdx.files.internal("dash.mp3"));
        atrasSound =   Gdx.audio.newSound(Gdx.files.internal("atrasSound.wav"));

        // Personaje 2
        idleSheetP2 = new Texture(Gdx.files.internal("peppinoQuieto.png"));
        moveSheetP2 = new Texture(Gdx.files.internal("peppinoMoving.png"));
        sheetQuemadoP2 = new Texture(Gdx.files.internal("peppinoQuemandose.png"));
        sheetRecuperandoseP2 = new Texture(Gdx.files.internal("peppinoRecuperacion.png"));
        hurtPeppinoSound = Gdx.audio.newSound(Gdx.files.internal("peppinoHerido.wav"));
        burningPeppinoSound = Gdx.audio.newSound(Gdx.files.internal("PeppinoBurning.wav"));
        sheetFacePeppino = new Texture(Gdx.files.internal("peppinoScream.png"));
        vidaPeppinoSound = Gdx.audio.newSound(Gdx.files.internal("peppinoHealthGrab.wav"));
        sheetVidaPeppino = new Texture(Gdx.files.internal("peppinoHP.png"));
        sheetHurtPeppino = new Texture(Gdx.files.internal("hurtPeppinoAnim.png"));
        sheetDashPeppino = new Texture(Gdx.files.internal("peppinoDash.png"));
        sheetIndicadorPeppino = new Texture(Gdx.files.internal("peppinoSelect.png"));

        previewNivel1 = new Texture(Gdx.files.internal("level1.png"));
        previewNivel2 = new Texture(Gdx.files.internal("level2.png"));

        // Inicia el juego en el Menú
        this.setScreen(new MenuPrincipalScreen(this));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {

        batch.dispose();
        font.dispose();

        idleSheetP1.dispose();
        moveSheetP1.dispose();
        sheetQuemadoP1.dispose();
        sheetRecuperandoseP1.dispose();
        idleSheetP2.dispose();
        moveSheetP2.dispose();
        sheetQuemadoP2.dispose();
        sheetRecuperandoseP2.dispose();
        hurtNoiseSound.dispose();
        hurtPeppinoSound.dispose();
        vidaNoiseSound.dispose();
        vidaPeppinoSound.dispose();
        musicaMenu.dispose();
        sheetVidaNoise.dispose();
        sheetVidaPeppino.dispose();
        sheetHurtNoise.dispose();
        sheetHurtPeppino.dispose();
        sheetDashPeppino.dispose();
        sheetDashNoise.dispose();
        dashSound.dispose();
        previewNivel1.dispose();
        previewNivel2.dispose();
        fondoSelectPersonaje.dispose();
        sheetFaceNoise.dispose();
        sheetFacePeppino.dispose();
        sheetIndicadorNoise.dispose();
        sheetIndicadorPeppino.dispose();
        atrasSound.dispose();
    }
}
