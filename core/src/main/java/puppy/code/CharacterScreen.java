package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.lang.annotation.Documented;

public class CharacterScreen implements Screen {

    private final GameLluvia game;
    private OrthographicCamera camera;

    private Texture fondo;
    private Animation<TextureRegion> indicadorAnimPeppino, indicadorAnimNoise, faceAnimPeppino, faceAnimNoise;

    private float stateTimer = 0f;
    private Sound sonidoCambio;

    private Rectangle botonAtras;


    private final float MUGSHOT_X = 100;
    private final float MUGSHOT_Y = 160;
    private final float MUGSHOT_SIZE = 170;

    // Posición del indicador/nombre
    private final float NOISE_INDICADOR_X = 510;
    private final float NOISE_INDICADOR_Y = 270;
    private final float PEPPINO_INDICADOR_X = 390;
    private final float PEPPINO_INDICADOR_Y = 345;

    // Tamaños del indicador
    private final int NOISE_INDICADOR_W = 200;
    private final int NOISE_INDICADOR_H = 200;
    private final int PEPPINO_INDICADOR_W = 154;
    private final int PEPPINO_INDICADOR_H = 149;

    private final float INDICADOR_FRAME_DURATION = 0.05f;

    public CharacterScreen(GameLluvia game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        fondo = game.fondoSelectPersonaje;

        indicadorAnimPeppino = createAnimationFromSheet(game.sheetIndicadorPeppino, 2,
            PEPPINO_INDICADOR_W, PEPPINO_INDICADOR_H,
            INDICADOR_FRAME_DURATION);


        indicadorAnimNoise = createAnimationFromSheet(game.sheetIndicadorNoise, 2,
            NOISE_INDICADOR_W, NOISE_INDICADOR_H,
            INDICADOR_FRAME_DURATION);

        faceAnimPeppino = createAnimationFromSheet(game.sheetFacePeppino, 8, 164, 177, 0.05f);
        faceAnimNoise = createAnimationFromSheet(game.sheetFaceNoise, 17, 164, 177, 0.05f);
        sonidoCambio = Gdx.audio.newSound(Gdx.files.internal("menuSFX.wav"));
        botonAtras = new Rectangle(10, 420, 100, 50);
    }

    @Override
    public void render(float delta) {
        stateTimer += delta;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.atrasSound.play();
            game.setScreen(new MenuPrincipalScreen(game));
            dispose();
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {

            if (game.personajeSeleccionado != GameLluvia.CharacterChoice.PERSONAJE_2) {
                game.personajeSeleccionado = GameLluvia.CharacterChoice.PERSONAJE_2;
                sonidoCambio.play();
            }

        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {

            if (game.personajeSeleccionado != GameLluvia.CharacterChoice.PERSONAJE_1) {
                game.personajeSeleccionado = GameLluvia.CharacterChoice.PERSONAJE_1;
                sonidoCambio.play();
            }

        }



        ScreenUtils.clear(0,0,0,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.draw(fondo, 0, 0, 800, 480);
        game.font.draw(game.batch, "ATRAS (ESC)", botonAtras.x + 30, botonAtras.y + 30);

        TextureRegion currentIndicadorFrame;
        TextureRegion currentFaceFrame;

        if (game.personajeSeleccionado == GameLluvia.CharacterChoice.PERSONAJE_1) {
            currentFaceFrame = faceAnimNoise.getKeyFrame(stateTimer, true);
            game.batch.draw(currentFaceFrame, MUGSHOT_X, MUGSHOT_Y, MUGSHOT_SIZE, MUGSHOT_SIZE);
            currentIndicadorFrame = indicadorAnimNoise.getKeyFrame(stateTimer, true);
            game.batch.draw(currentIndicadorFrame, NOISE_INDICADOR_X, NOISE_INDICADOR_Y, NOISE_INDICADOR_W, NOISE_INDICADOR_H);
        }

        else {
            currentFaceFrame = faceAnimPeppino.getKeyFrame(stateTimer, true);
            game.batch.draw(currentFaceFrame, MUGSHOT_X, MUGSHOT_Y, MUGSHOT_SIZE, MUGSHOT_SIZE);
            currentIndicadorFrame = indicadorAnimPeppino.getKeyFrame(stateTimer, true);
            game.batch.draw(currentIndicadorFrame, PEPPINO_INDICADOR_X, PEPPINO_INDICADOR_Y, PEPPINO_INDICADOR_W, PEPPINO_INDICADOR_H );
        }

        game.batch.end();
    }

    @Override
    public void dispose() {
        sonidoCambio.dispose();
    }

    private Animation<TextureRegion> createAnimationFromSheet(Texture sheet, int frameCount, int frameWidth, int frameHeight, float frameDuration) {

        TextureRegion[][] tmp = TextureRegion.split(sheet, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[frameCount];
        int index = 0;
        for (int j = 0; j < frameCount; j++)
            frames[index++] = tmp[0][j];
        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, frames);
        anim.setPlayMode(Animation.PlayMode.LOOP);
        return anim;
    }

   @Override
    public void show() {
       if (game.musicaMenu != null && !game.musicaMenu.isPlaying())
           game.musicaMenu.play();
   }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void resize(int width, int height) {}
}
