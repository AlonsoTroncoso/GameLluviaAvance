package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class JuegoScreen implements Screen {

    private final GameLluvia game;
    private OrthographicCamera camera;

    // Fondos del juego
    private Texture backgroundFrame1, backgroundFrame2;
    private Texture backgroundGolf;
    private float animationTimer;
    private final float duracionFotograma = 0.1f;

    // Sheets de Items
    private Texture sheetComida1, sheetComida2, sheetComida3, sheetComida4, sheetComida5;
    private Texture sheetItemHostil;
    private Texture sheetDoblePuntos;
    private Texture sheetAntorchaHostil;
    private Texture sheetVidaNoise, sheetVidaPeppino;
    private Texture sheetHurtNoise, sheetHurtPeppino;
    private IJugador jugador;
    private Lluvia lluvia;

    public Music musicaNivel;

    public JuegoScreen(GameLluvia game, GameLluvia.CharacterChoice personajeElegido) {
        this.game = game;

        sheetComida1 = new Texture(Gdx.files.internal("champinhon.png"));
        sheetComida2 = new Texture(Gdx.files.internal("chorizo.png"));
        sheetComida3 = new Texture(Gdx.files.internal("pinha.png"));
        sheetComida4 = new Texture(Gdx.files.internal("queso.png"));
        sheetComida5 = new Texture(Gdx.files.internal("tomate.png"));
        sheetItemHostil = new Texture(Gdx.files.internal("papaElectrica.png"));
        sheetAntorchaHostil = new Texture(Gdx.files.internal("antorchaHostil.png"));
        sheetDoblePuntos = new Texture(Gdx.files.internal("doblepuntos.png"));
        sheetVidaNoise = game.sheetVidaNoise;
        sheetVidaPeppino = game.sheetVidaPeppino;
        sheetHurtNoise = game.sheetHurtNoise;
        sheetHurtPeppino = game.sheetHurtPeppino;

        if(game.nivelSeleccionado == GameLluvia.LevelChoice.NIVEL_1) {
            backgroundFrame1 = new Texture(Gdx.files.internal("fastFoodSaloon1.png"));
            backgroundFrame2 = new Texture(Gdx.files.internal("fastFoodSaloon2.png"));
        }

        else
            backgroundGolf = new Texture(Gdx.files.internal("golf.png"));

        animationTimer = 0f;

        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("comer.mp3"));

        Sound powerupSound = Gdx.audio.newSound(Gdx.files.internal("doublePoints.wav"));

        if (game.nivelSeleccionado == GameLluvia.LevelChoice.NIVEL_1)
            musicaNivel = Gdx.audio.newMusic(Gdx.files.internal("yeehaw.mp3"));

         else  // NIVEL_2
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
                game.burningNoiseSound,
                game.dashSound,
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
                game.burningPeppinoSound,
                game.dashSound,
                Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.X
            );
        }

        // Creación de Lluvia
        lluvia = new Lluvia(
            sheetComida1, sheetComida2, sheetComida3, sheetComida4, sheetComida5,
            sheetItemHostil, sheetAntorchaHostil,sheetDoblePuntos, sheetVidaNoise, sheetVidaPeppino,
            personajeElegido, dropSound, powerupSound
        );

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
            if(animationTimer<duracionFotograma)
                game.batch.draw(backgroundFrame1,0, 0, 800, 480);
            else
                game.batch.draw(backgroundFrame2, 0, 0, 800, 480);
        }

        else {
                game.batch.draw(backgroundGolf, 0, 0, 800, 480);
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

    @Override
    public void dispose() {

        jugador.destruir();

        backgroundFrame1.dispose();
        backgroundFrame2.dispose();

        sheetComida1.dispose();
        sheetComida2.dispose();
        sheetComida3.dispose();
        sheetComida4.dispose();
        sheetComida5.dispose();

        sheetItemHostil.dispose();
        sheetAntorchaHostil.dispose();
        sheetDoblePuntos.dispose();

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
