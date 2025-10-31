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

    public Sound vidaNoiseSound, vidaNoiseSound2, vidaNoiseSound3, vidaNoiseSound4, vidaNoiseSound5,
        vidaPeppinoSound, vidaPeppinoSound2, vidaPeppinoSound3, confimarSound,
        saloonJingle, golfJingle, sonidoGolpe, sonidoGolpeNoise, hurtPeppinoSound2, hurtNoiseSound, hurtNoiseSound2,
        hurtPeppinoSound, burningNoiseSound, burningPeppinoSound, burningPeppinoSound2, burningPeppinoSound3,
        dashSound, atrasSound, cambioSound;

    public enum CharacterChoice {
        PERSONAJE_1,
        PERSONAJE_2
    }

    public enum LevelChoice {
        NIVEL_1,
        NIVEL_2
    }

    public LevelChoice nivelSeleccionado = LevelChoice.NIVEL_1;

    public Texture sheetComida1, sheetComida2, sheetComida3, sheetComida4, sheetComida5,
        sheetItemHostil, sheetAntorchaHostil, sheetDoblePuntos, previewNivel1, previewNivel2,
        floorGolf, homeGolf, adGolf, sheetIdleGreaseball, golfTitleCard, floorSaloon, dontGrabTheMeatBall, bigBeer, saloonTitleCard,
        sheetMeatBall, heno, pizzaSmart, idleSheetP1, moveSheetP1, sheetQuemadoP1, sheetRecuperandoseP1,
        idleSheetP2, moveSheetP2, sheetQuemadoP2, sheetRecuperandoseP2, sheetVidaNoise, sheetVidaPeppino,
        sheetHurtNoise, sheetHurtPeppino, sheetDashNoise, sheetDashPeppino, fondoSelectPersonaje, sheetFaceNoise, sheetFacePeppino,sheetIndicadorNoise, sheetIndicadorPeppino;

    //Por defecto, el personaje 1 está seleccionado
    public CharacterChoice personajeSeleccionado = CharacterChoice.PERSONAJE_1;

    @Override
    public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("pizzaFont.fnt"));
        font.getData().setScale(1f);
        musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("pDeluxe.mp3"));
        musicaMenu.setLooping(true);

        // Carga de sheets
        sheetComida1 = new Texture(Gdx.files.internal("champinhon.png"));
        sheetComida2 = new Texture(Gdx.files.internal("chorizo.png"));
        sheetComida3 = new Texture(Gdx.files.internal("pinha.png"));
        sheetComida4 = new Texture(Gdx.files.internal("queso.png"));
        sheetComida5 = new Texture(Gdx.files.internal("tomate.png"));
        sheetItemHostil = new Texture(Gdx.files.internal("papaElectrica.png"));
        sheetAntorchaHostil = new Texture(Gdx.files.internal("antorchaHostil.png"));
        sheetDoblePuntos = new Texture(Gdx.files.internal("doblepuntos.png"));

        // Personaje 1
        idleSheetP1 = new Texture(Gdx.files.internal("noiseQuieto.png"));
        moveSheetP1 = new Texture(Gdx.files.internal("noiseMoviendose.png"));
        sheetQuemadoP1 = new Texture(Gdx.files.internal("noiseCapaQuemada.png"));
        sheetRecuperandoseP1 = new Texture(Gdx.files.internal("noiseCapaQuemadaRecuperacion.png"));
        hurtNoiseSound = Gdx.audio.newSound(Gdx.files.internal("noiseHerido.mp3"));
        hurtNoiseSound2 = Gdx.audio.newSound(Gdx.files.internal("noiseHerido2.wav"));;
        burningNoiseSound = Gdx.audio.newSound(Gdx.files.internal("TheNoiseBurning.wav"));
        sheetFaceNoise = new Texture(Gdx.files.internal("noiseAngry.png"));
        vidaNoiseSound = Gdx.audio.newSound(Gdx.files.internal("noiseHealthGrab.wav"));
        vidaNoiseSound2 = Gdx.audio.newSound(Gdx.files.internal("noiseHealthGrab2.wav"));
        vidaNoiseSound3 = Gdx.audio.newSound(Gdx.files.internal("noiseHealthGrab3.wav"));
        vidaNoiseSound4 = Gdx.audio.newSound(Gdx.files.internal("noiseHealthGrab4.wav"));
        vidaNoiseSound5 = Gdx.audio.newSound(Gdx.files.internal("noiseHealthGrab5.wav"));
        sheetIndicadorNoise = new Texture(Gdx.files.internal("noiseSelect.png"));
        sonidoGolpeNoise = Gdx.audio.newSound(Gdx.files.internal("sonidoGolpeNoise.wav"));
        sheetVidaNoise = new Texture(Gdx.files.internal("noiseHP.png"));
        sheetHurtNoise = new Texture(Gdx.files.internal("hurtNoiseAnim.png"));
        sheetDashNoise = new Texture(Gdx.files.internal("noiseDash.png"));

        // Decoraciones, sonidos y fondos
        fondoSelectPersonaje = new Texture(Gdx.files.internal("chooseCharacter.png"));
        sonidoGolpe = Gdx.audio.newSound(Gdx.files.internal("takingDamage.wav"));
        dashSound = Gdx.audio.newSound(Gdx.files.internal("dash.mp3"));
        atrasSound =  Gdx.audio.newSound(Gdx.files.internal("atrasSound.wav"));
        cambioSound = Gdx.audio.newSound(Gdx.files.internal("menuSFX.wav"));
        floorGolf = new Texture(Gdx.files.internal("floorGolf.png"));
        homeGolf = new Texture(Gdx.files.internal("homeGolf.png"));
        adGolf = new Texture(Gdx.files.internal(("welcome2Golf.png")));
        sheetIdleGreaseball = new Texture(Gdx.files.internal("idleGreaseBall.png"));
        floorSaloon = new Texture(Gdx.files.internal("floorFFS.png"));
        dontGrabTheMeatBall = new Texture(Gdx.files.internal("dontGrabTheMeatBall.png"));
        sheetMeatBall = new Texture(Gdx.files.internal("meatBall.png"));
        heno =  new Texture(Gdx.files.internal("heno.png"));
        bigBeer = new Texture(Gdx.files.internal("bigBeer.png"));
        pizzaSmart = new Texture(Gdx.files.internal(("pizzaSmart.png")));
        saloonTitleCard = new Texture(Gdx.files.internal("saloonTitleCard.png"));
        golfTitleCard = new Texture(Gdx.files.internal("golfTitleCard.png"));
        saloonJingle = Gdx.audio.newSound(Gdx.files.internal("saloonJingle.mp3"));
        golfJingle = Gdx.audio.newSound(Gdx.files.internal("golfJingle.mp3"));
        confimarSound = Gdx.audio.newSound(Gdx.files.internal("ok.wav"));
        previewNivel1 = new Texture(Gdx.files.internal("level1.png"));
        previewNivel2 = new Texture(Gdx.files.internal("level2.png"));

        // Personaje 2
        idleSheetP2 = new Texture(Gdx.files.internal("peppinoQuieto.png"));
        moveSheetP2 = new Texture(Gdx.files.internal("peppinoMoving.png"));
        sheetQuemadoP2 = new Texture(Gdx.files.internal("peppinoQuemandose.png"));
        sheetRecuperandoseP2 = new Texture(Gdx.files.internal("peppinoRecuperacion.png"));
        hurtPeppinoSound = Gdx.audio.newSound(Gdx.files.internal("peppinoHerido.wav"));
        hurtPeppinoSound2 = Gdx.audio.newSound(Gdx.files.internal("peppinoHerido2.wav"));
        burningPeppinoSound = Gdx.audio.newSound(Gdx.files.internal("peppinoBurning.wav"));
        burningPeppinoSound2 = Gdx.audio.newSound(Gdx.files.internal("peppinoBurning2.wav"));
        burningPeppinoSound3 = Gdx.audio.newSound(Gdx.files.internal("peppinoBurning3.wav"));
        sheetFacePeppino = new Texture(Gdx.files.internal("peppinoScream.png"));
        vidaPeppinoSound = Gdx.audio.newSound(Gdx.files.internal("peppinoHealthGrab.wav"));
        vidaPeppinoSound2 = Gdx.audio.newSound(Gdx.files.internal("peppinoHealthGrab2.wav"));
        vidaPeppinoSound3 = Gdx.audio.newSound(Gdx.files.internal("peppinoHealthGrab3.wav"));
        sheetVidaPeppino = new Texture(Gdx.files.internal("peppinoHP.png"));
        sheetHurtPeppino = new Texture(Gdx.files.internal("hurtPeppinoAnim.png"));
        sheetDashPeppino = new Texture(Gdx.files.internal("peppinoDash.png"));
        sheetIndicadorPeppino = new Texture(Gdx.files.internal("peppinoSelect.png"));

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
        vidaNoiseSound2.dispose();
        vidaNoiseSound3.dispose();
        vidaNoiseSound4.dispose();
        vidaNoiseSound5.dispose();
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
        cambioSound.dispose();
        floorGolf.dispose();
        homeGolf.dispose();
        adGolf.dispose();
        floorSaloon.dispose();
        dontGrabTheMeatBall.dispose();
        sheetMeatBall.dispose();
        saloonTitleCard.dispose();
        golfTitleCard.dispose();
        saloonJingle.dispose();
        golfJingle.dispose();
        cambioSound.dispose();
        confimarSound.dispose();
        sonidoGolpe.dispose();
        hurtNoiseSound2.dispose();
        hurtPeppinoSound2.dispose();
        burningPeppinoSound2.dispose();
        burningPeppinoSound3.dispose();
        vidaPeppinoSound2.dispose();
        vidaPeppinoSound3.dispose();
    }
}
