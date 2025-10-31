package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class JuegoScreen implements Screen {

    private final GameLluvia game;
    private OrthographicCamera camera;

    // Fondos del juego
    private Texture backgroundFrame1, backgroundFrame2, backgroundGolf, floorGolf, homeGolf, adGolf,
    floorSaloon, dontGrabTheMeatBall, bigBeer, heno, pizzaSmart;
    private float animationTimer;
    private final float duracionFotograma = 0.1f;

    // Sheets de Items
    private Texture sheetComida1, sheetComida2, sheetComida3, sheetComida4, sheetComida5, sheetItemHostil, sheetDoblePuntos,
        sheetAntorchaHostil, sheetVidaNoise, sheetVidaPeppino, sheetHurtNoise, sheetHurtPeppino;

    private IJugador jugador;
    private Lluvia lluvia;
    private Animation<TextureRegion> greaseBallAnimation, meatBallAnimation;
    private float greaseBallTimer = 0f;;
    private float meatBallTimer = 0f;

    public Music musicaNivel;
    private final int PLAYER_INITIAL_Y_OFFSET = 20;

    public JuegoScreen(GameLluvia game, GameLluvia.CharacterChoice personajeElegido) {

        this.game = game;
        sheetComida1 = game.sheetComida1;
        sheetComida2 = game.sheetComida2;
        sheetComida3 = game.sheetComida3;
        sheetComida4 = game.sheetComida4;
        sheetComida5 = game.sheetComida5;
        sheetItemHostil = game.sheetItemHostil;
        sheetAntorchaHostil = game.sheetAntorchaHostil;
        sheetDoblePuntos = game.sheetDoblePuntos;
        sheetVidaNoise = game.sheetVidaNoise;
        sheetVidaPeppino = game.sheetVidaPeppino;
        sheetHurtNoise = game.sheetHurtNoise;
        sheetHurtPeppino = game.sheetHurtPeppino;

        if(game.nivelSeleccionado == GameLluvia.LevelChoice.NIVEL_1) {
            backgroundFrame1 = new Texture(Gdx.files.internal("fastFoodSaloon1.png"));
            backgroundFrame2 = new Texture(Gdx.files.internal("fastFoodSaloon2.png"));
            floorSaloon = game.floorSaloon;
            dontGrabTheMeatBall = game.dontGrabTheMeatBall;
            heno = game.heno;
            bigBeer = game.bigBeer;
            pizzaSmart = game.pizzaSmart;
            int meatBallFrameCount = 16;
            int frameWidth = 128;
            int frameHeight = 128;

            meatBallAnimation = createAnimationFromSheet(game.sheetMeatBall,
                meatBallFrameCount, frameWidth, frameHeight, 0.05f);

        }

        else {
            backgroundGolf = new Texture(Gdx.files.internal("golf.png"));
            floorGolf = game.floorGolf;
            homeGolf = game.homeGolf;
            adGolf = game.adGolf;

            int greaseBallFrameCount = 12;
            int frameWidth = 100;
            int frameHeight = 100;

            greaseBallAnimation =  createAnimationFromSheet(game.sheetIdleGreaseball,
                greaseBallFrameCount, frameWidth, frameHeight, 0.05f);


        }

        animationTimer = 0f;

        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("comer.mp3"));

        Sound powerupSound = Gdx.audio.newSound(Gdx.files.internal("doublePoints.wav"));

        if (game.nivelSeleccionado == GameLluvia.LevelChoice.NIVEL_1)
            musicaNivel = Gdx.audio.newMusic(Gdx.files.internal("yeehaw.mp3"));

         else // NIVEL_2
            musicaNivel = Gdx.audio.newMusic(Gdx.files.internal("goodEatin.mp3"));

        musicaNivel.setLooping(true);
        if (personajeElegido == GameLluvia.CharacterChoice.PERSONAJE_1) {
            jugador = new Noise(
                game.idleSheetP1,
                game.moveSheetP1,
                game.sheetQuemadoP1,
                game.sheetRecuperandoseP1,
                game.sheetHurtNoise,
                game.sheetDashNoise,
                game.hurtNoiseSound,
                game.vidaNoiseSound,
                game.vidaNoiseSound2,
                game.vidaNoiseSound3,
                game.vidaNoiseSound4,
                game.vidaNoiseSound5,
                game.burningNoiseSound,
                game.dashSound,
                game.sonidoGolpeNoise,
                game.hurtNoiseSound2,
                Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.X
            );
        }

        else {
            jugador = new Peppino(
                game.idleSheetP2,
                game.moveSheetP2,
                game.sheetQuemadoP2,
                game.sheetRecuperandoseP2,
                game.sheetHurtPeppino,
                game.sheetDashPeppino,
                game.hurtPeppinoSound,
                game.vidaPeppinoSound,
                game.vidaPeppinoSound2,
                game.vidaPeppinoSound3,
                game.burningPeppinoSound,
                game.burningPeppinoSound2,
                game.burningPeppinoSound3,
                game.dashSound,
                game.sonidoGolpe,
                game.hurtPeppinoSound2,
                Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.X
            );
        }

        // Creación de Lluvia
        CreadorItems creadorItems = new CreadorItems(
            sheetComida1, sheetComida2, sheetComida3, sheetComida4, sheetComida5,
            sheetItemHostil, sheetAntorchaHostil,sheetDoblePuntos, sheetVidaNoise, sheetVidaPeppino,
            personajeElegido, dropSound, powerupSound);

        lluvia = new Lluvia(creadorItems);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        jugador.crear();
        lluvia.crear();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();

        jugador.actualizar(delta);

        animationTimer += delta;

        if(animationTimer >= duracionFotograma * 2)
            animationTimer = 0f;

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        if(game.nivelSeleccionado == GameLluvia.LevelChoice.NIVEL_1) {
            meatBallTimer += delta;
            TextureRegion currentMeatBallFrame = meatBallAnimation.getKeyFrame(meatBallTimer, true);

            if (animationTimer < duracionFotograma) {
                game.batch.draw(backgroundFrame1, 0, 0, 800, 480);
                game.batch.draw(floorSaloon, 0, 0, 800, 30);
                game.batch.draw(dontGrabTheMeatBall, 10, 30, 172, 167);
                game.batch.draw(currentMeatBallFrame, 185, -2, 128, 128);
                game.batch.draw(heno, 720, 25, 73, 76);
                game.batch.draw(bigBeer,680, 30, 26, 47);
                game.batch.draw(pizzaSmart, 300, 30, 361, 276);
            }

            else {
                game.batch.draw(backgroundFrame2, 0, 0, 800, 480);
                game.batch.draw(floorSaloon, 0, 0, 800, 30);
                game.batch.draw(dontGrabTheMeatBall, 10, 30, 172, 167);
                game.batch.draw(currentMeatBallFrame, 185, -2, 128, 128);
                game.batch.draw(heno, 720, 25, 73, 76);
                game.batch.draw(bigBeer,680, 30, 26, 47);
                game.batch.draw(pizzaSmart, 300, 30, 361, 276);
            }
        }

        else {
            greaseBallTimer += delta;
            TextureRegion currentGreaseBallAnimation = greaseBallAnimation.getKeyFrame(greaseBallTimer, true);
            if (!currentGreaseBallAnimation.isFlipX())
                currentGreaseBallAnimation.flip(true, false);

            game.batch.draw(backgroundGolf, 0, 0, 800, 480);
            game.batch.draw(floorGolf, 0, 0, 800, 30);
            game.batch.draw(homeGolf, 0, 30, 160, 222);
            game.batch.draw(adGolf, 300, 30, 206, 228);
            game.batch.draw(currentGreaseBallAnimation, 630, 25, 100, 100);
        }


        if(jugador.getVidas() > 0) {
            // ESTADO JUGANDO
            game.font.draw(game.batch, "Puntos: " + jugador.getPuntos(), 5, 475);
            game.font.draw(game.batch, "Vidas: " + jugador.getVidas(), 5, 455);

            if(jugador.estaDoblePuntos())
                game.font.draw(game.batch, "2XP: " + String.format("%.1f", jugador.getTiempoDoblePuntos()), 150, 475);

            if (!jugador.estaHerido()) {
                jugador.actualizarMovimiento();
            }
            jugador.dibujar(game.batch);


            lluvia.actualizarMovimiento(jugador);
            lluvia.actualizarDibujoLluvia(game.batch);

        } else {
            // ESTADO GAME OVER
            game.font.draw(game.batch, "GAME OVER", 360, 280);
            game.font.draw(game.batch, "Puntaje Final: " +jugador.getPuntos(), 350, 250);
            game.font.draw(game.batch, "Presiona R para reiniciar", 330, 190);

            if (musicaNivel != null && musicaNivel.isPlaying())
                musicaNivel.stop();

        }

        game.batch.end();

        // Lógica de Reinicio
        if(jugador.getVidas() <= 0 && Gdx.input.isKeyPressed(Input.Keys.R)) {
            game.setScreen(new MenuPrincipalScreen(game));
            dispose();
        }
    }

    private Animation<TextureRegion> createAnimationFromSheet(Texture sheet, int frameCount, int frameWidth, int frameHeight, float frameDuration) {
        TextureRegion[][] tmp = TextureRegion.split(sheet,
            frameWidth,
            frameHeight);

        TextureRegion[] frames = new TextureRegion[frameCount];
        int index = 0;
        for (int j = 0; j < frameCount; j++)
            frames[index++] = tmp[0][j];

        Animation<TextureRegion> animation = new Animation<TextureRegion>(frameDuration, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);



        return animation;
    }

    @Override
    public void dispose() {

        jugador.destruir();

        if (backgroundFrame1 != null)
            backgroundFrame1.dispose();

        if (backgroundFrame2 != null)
            backgroundFrame2.dispose();

        if (backgroundGolf != null)
            backgroundGolf.dispose();



        if (musicaNivel != null)
            musicaNivel.dispose();

    }

    @Override public void show() {
        if(musicaNivel != null && !musicaNivel.isPlaying())
            musicaNivel.play();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override public void hide() {
        if (musicaNivel != null && musicaNivel.isPlaying())
            musicaNivel.stop();

    }
}
