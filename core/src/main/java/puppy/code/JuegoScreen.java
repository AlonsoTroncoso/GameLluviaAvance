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
    private GestorAssets assets;
    private float animationTimer;
    private final float duracionFotograma = 0.1f;


    private IJugador jugador;
    private Lluvia lluvia;
    private Animation<TextureRegion> greaseBallAnimation, meatBallAnimation;
    private float greaseBallTimer = 0f;;
    private float meatBallTimer = 0f;

    public Music musicaNivel;
    private final int PLAYER_INITIAL_Y_OFFSET = 20;

    public JuegoScreen(GameLluvia game, GameLluvia.CharacterChoice personajeElegido) {

        this.game = game;
        this.assets = GestorAssets.getInstance();

        if(game.nivelSeleccionado == GameLluvia.LevelChoice.NIVEL_1) {
            musicaNivel = assets.musicaNivelSaloon;
            int meatBallFrameCount = 16;
            int frameWidth = 128;
            int frameHeight = 128;

            meatBallAnimation = createAnimationFromSheet(assets.sheetMeatBall,
                meatBallFrameCount, frameWidth, frameHeight, 0.05f);

        }

        else {
            musicaNivel = assets.musicaNivelGolf;
            int greaseBallFrameCount = 12;
            int frameWidth = 100;
            int frameHeight = 100;

            greaseBallAnimation =  createAnimationFromSheet(assets.sheetIdleGreaseball,
                greaseBallFrameCount, frameWidth, frameHeight, 0.05f);


        }

        animationTimer = 0f;

        musicaNivel.setLooping(true);
        if (personajeElegido == GameLluvia.CharacterChoice.PERSONAJE_1) {
            jugador = new Noise(
                assets.idleSheetP1,
                assets.moveSheetP1,
                assets.sheetQuemadoP1,
                assets.sheetRecuperandoseP1,
                assets.sheetHurtNoise,
                assets.sheetDashNoise,
                assets.hurtNoiseSound,
                assets.vidaNoiseSound,
                assets.vidaNoiseSound2,
                assets.vidaNoiseSound3,
                assets.vidaNoiseSound4,
                assets.vidaNoiseSound5,
                assets.burningNoiseSound,
                assets.dashSound,
                assets.sonidoGolpeNoise,
                assets.hurtNoiseSound2,
                Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.X
            );
        }

        else {
            jugador = new Peppino(
                assets.idleSheetP2,
                assets.moveSheetP2,
                assets.sheetQuemadoP2,
                assets.sheetRecuperandoseP2,
                assets.sheetHurtPeppino,
                assets.sheetDashPeppino,
                assets.hurtPeppinoSound,
                assets.vidaPeppinoSound,
                assets.vidaPeppinoSound2,
                assets.vidaPeppinoSound3,
                assets.burningPeppinoSound,
                assets.burningPeppinoSound2,
                assets.burningPeppinoSound3,
                assets.dashSound,
                assets.sonidoGolpe,
                assets.hurtPeppinoSound2,
                Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.X
            );
        }

        //Aplicacion de Strategy
        IEstrategiaGeneracion estrategiaNivel;

        if (game.nivelSeleccionado == GameLluvia.LevelChoice.NIVEL_1)
            estrategiaNivel = new EstrategiaSaloon(); // Estrategia Fácil
        else
            estrategiaNivel = new EstrategiaGolf();   // Estrategia Difícil

        CreadorItems creadorItems = new CreadorItems(personajeElegido, estrategiaNivel);

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
                game.batch.draw(assets.saloonBG1, 0, 0, 800, 480);
                game.batch.draw(assets.floorSaloon, 0, 0, 800, 30);
                game.batch.draw(assets.dontGrabTheMeatBall, 10, 30, 172, 167);
                game.batch.draw(currentMeatBallFrame, 185, -2, 128, 128);
                game.batch.draw(assets.heno, 720, 25, 73, 76);
                game.batch.draw(assets.bigBeer,680, 30, 26, 47);
                game.batch.draw(assets.pizzaSmart, 300, 30, 361, 276);
            }

            else {
                game.batch.draw(assets.saloonBG2, 0, 0, 800, 480);
                game.batch.draw(assets.floorSaloon, 0, 0, 800, 30);
                game.batch.draw(assets.dontGrabTheMeatBall, 10, 30, 172, 167);
                game.batch.draw(currentMeatBallFrame, 185, -2, 128, 128);
                game.batch.draw(assets.heno, 720, 25, 73, 76);
                game.batch.draw(assets.bigBeer,680, 30, 26, 47);
                game.batch.draw(assets.pizzaSmart, 300, 30, 361, 276);
            }
        }

        else {
            greaseBallTimer += delta;
            TextureRegion currentGreaseBallAnimation = greaseBallAnimation.getKeyFrame(greaseBallTimer, true);
            if (!currentGreaseBallAnimation.isFlipX())
                currentGreaseBallAnimation.flip(true, false);

            game.batch.draw(assets.golfBG, 0, 0, 800, 480);
            game.batch.draw(assets.floorGolf, 0, 0, 800, 30);
            game.batch.draw(assets.homeGolf, 0, 30, 160, 222);
            game.batch.draw(assets.adGolf, 300, 30, 206, 228);
            game.batch.draw(currentGreaseBallAnimation, 630, 25, 100, 100);
        }


        if(jugador.getVidas() > 0) {
            // ESTADO JUGANDO
            assets.font.draw(game.batch, "Puntos: " + jugador.getPuntos(), 5, 475);
            assets.font.draw(game.batch, "Vidas: " + jugador.getVidas(), 5, 455);

            if(jugador.estaDoblePuntos())
                assets.font.draw(game.batch, "2XP: " + String.format("%.1f", jugador.getTiempoDoblePuntos()), 150, 475);

            if (!jugador.estaHerido()) {
                jugador.actualizarMovimiento();
            }
            jugador.dibujar(game.batch);


            lluvia.actualizarMovimiento(jugador);
            lluvia.actualizarDibujoLluvia(game.batch);

        } else {
            // ESTADO GAME OVER
            assets.font.draw(game.batch, "GAME OVER", 360, 280);
            assets.font.draw(game.batch, "Puntaje Final: " +jugador.getPuntos(), 350, 250);
            assets.font.draw(game.batch, "Presiona R para reiniciar", 330, 190);

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

        if (musicaNivel != null)
            musicaNivel.stop();

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
