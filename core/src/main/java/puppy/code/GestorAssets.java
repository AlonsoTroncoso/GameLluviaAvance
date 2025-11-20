package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

// 1. La clase Singleton
public class GestorAssets {

    // 2. La única instancia (privada y estática)
    private static GestorAssets instance;

    // 3. Todas tus texturas, sonidos y fuentes
    public BitmapFont font;
    public Music musicaMenu;

    // Texturas de Items
    public Texture sheetComida1, sheetComida2, sheetComida3, sheetComida4, sheetComida5,
        sheetItemHostil, sheetAntorchaHostil, sheetDoblePuntos;

    // Texturas de Niveles y UI
    public Texture previewNivel1, previewNivel2, floorGolf, homeGolf, adGolf,
        floorSaloon, dontGrabTheMeatBall, bigBeer, heno, pizzaSmart,
        saloonTitleCard, golfTitleCard, fondoSelectPersonaje,
        fondoMenu1, fondoMenu2, fondoMenu3, fondoLevelSelect,
        fondoTitleSaloon, fondoTitleGolf, golfBG, saloonBG1, saloonBG2;

    // Texturas de Entidades
    public Texture sheetIdleGreaseball, sheetMeatBall;

    // Texturas Personaje 1 (Noise)
    public Texture idleSheetP1, moveSheetP1, sheetQuemadoP1, sheetRecuperandoseP1,
        sheetVidaNoise, sheetHurtNoise, sheetDashNoise, sheetFaceNoise, sheetIndicadorNoise;

    // Texturas Personaje 2 (Peppino)
    public Texture idleSheetP2, moveSheetP2, sheetQuemadoP2, sheetRecuperandoseP2,
        sheetVidaPeppino, sheetHurtPeppino, sheetDashPeppino, sheetFacePeppino, sheetIndicadorPeppino;

    // Sonidos
    public Sound vidaNoiseSound, vidaNoiseSound2, vidaNoiseSound3, vidaNoiseSound4, vidaNoiseSound5,
        vidaPeppinoSound, vidaPeppinoSound2, vidaPeppinoSound3, confimarSound,
        saloonJingle, golfJingle, sonidoGolpe, sonidoGolpeNoise, hurtPeppinoSound2, hurtNoiseSound, hurtNoiseSound2,
        hurtPeppinoSound, burningNoiseSound, burningPeppinoSound, burningPeppinoSound2, burningPeppinoSound3,
        dashSound, atrasSound, cambioSound, comerSound, powerupSound;

    // Música de Niveles
    public Music musicaNivelSaloon, musicaNivelGolf;

    // 4. El constructor PRIVADO (nadie más puede crearlo)
    private GestorAssets() { }

    // 5. El método público estático para obtener la instancia
    public static GestorAssets getInstance() {
        if (instance == null) {
            instance = new GestorAssets();
        }
        return instance;
    }

    // 6. Método para cargar todos los assets
    public void load() {
        // Fuente y Música Menú
        font = new BitmapFont(Gdx.files.internal("pizzaFont.fnt"));
        font.getData().setScale(1f);
        musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("pDeluxe.mp3"));
        musicaMenu.setLooping(true);

        // Carga de sheets Items
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

        // Decoraciones, sonidos y fondos (Niveles)
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

        // Menús
        fondoMenu1 = new Texture(Gdx.files.internal("backgroundMenu1.png"));
        fondoMenu2 = new Texture(Gdx.files.internal("backgroundMenu2.png"));
        fondoMenu3 = new Texture(Gdx.files.internal("backgroundMenu3.png"));
        fondoLevelSelect = new Texture(Gdx.files.internal("characterScreen.png")); // Re-usada de LevelSelectScreen

        // Fondos JuegoScreen y Title Cards
        fondoTitleSaloon = new Texture(Gdx.files.internal("saloonTitleCard.png")); // Asumo este nombre
        fondoTitleGolf = new Texture(Gdx.files.internal("golfTitleCard.png")); // Asumo este nombre
        golfBG = new Texture(Gdx.files.internal("golf.png"));
        saloonBG1 = new Texture(Gdx.files.internal("fastFoodSaloon1.png"));
        saloonBG2 = new Texture(Gdx.files.internal("fastFoodSaloon2.png"));

        // Sonidos JuegoScreen
        comerSound = Gdx.audio.newSound(Gdx.files.internal("comer.mp3"));
        powerupSound = Gdx.audio.newSound(Gdx.files.internal("doublePoints.wav"));

        // Música JuegoScreen
        musicaNivelSaloon = Gdx.audio.newMusic(Gdx.files.internal("yeehaw.mp3"));
        musicaNivelGolf = Gdx.audio.newMusic(Gdx.files.internal("goodEatin.mp3"));
    }

    // 7. Método para liberar todos los assets
    public void dispose() {
        // Libera todo
        font.dispose();
        musicaMenu.dispose();
        sheetComida1.dispose();
        sheetComida2.dispose();
        // ... (agrega .dispose() para CADA asset cargado en load())
        // ...
        // ...
        idleSheetP1.dispose();
        moveSheetP1.dispose();
        // ...
        idleSheetP2.dispose();
        moveSheetP2.dispose();
        // ...
        hurtNoiseSound.dispose();
        vidaNoiseSound.dispose();
        // ... (todos los sounds)
        // ...
        musicaNivelSaloon.dispose();
        musicaNivelGolf.dispose();
        // ... (etc. Tienes que listar todos los assets aquí)
    }
}
