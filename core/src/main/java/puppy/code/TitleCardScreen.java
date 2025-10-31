package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class TitleCardScreen implements Screen {

    private final GameLluvia game;
    private final GameLluvia.LevelChoice nivel;
    private final GameLluvia.CharacterChoice personaje;

    private OrthographicCamera camera;
    private Texture titleCardTexture;
    private Sound jingle;

    private float stateTimer = 0f;


    private final float FADE_IN_DURATION = 0.5f;  // Esta es la duración del primer fade
    private final float SHOW_DURATION = 2.8f;     // Cuánto tiempo se muestra la imagen
    private final float FADE_OUT_DURATION = 0.3f; // Duración del segundo fade
    private Texture logoTexture;
    private float logoWidth;
    private float logoHeight;
    private float logoX;
    private float logoY;

    private enum State {
        FADING_IN,
        SHOWING,
        FADING_OUT
    }
    private State currentState = State.FADING_IN;

    public TitleCardScreen(GameLluvia game, GameLluvia.LevelChoice nivel, GameLluvia.CharacterChoice personaje) {
        this.game = game;
        this.nivel = nivel;
        this.personaje = personaje;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Selecciona qué imagen y sonido usar
        if (nivel == GameLluvia.LevelChoice.NIVEL_1) {
            titleCardTexture = game.saloonTitleCard;
            jingle = game.saloonJingle;
            logoTexture = game.previewNivel1;
            logoWidth = 225;
            logoHeight = 122;
            logoX = 35;
            logoY = 325;
        }

        else { // NIVEL 2
            titleCardTexture = game.golfTitleCard;
            jingle = game.golfJingle;
            logoTexture = game.previewNivel2;
            logoWidth = 200;
            logoHeight = 250;
            logoX = 570;
            logoY = 25;
        }
    }

    @Override
    public void show() {
        jingle.play();
    }

    @Override
    public void render(float delta) {
        stateTimer += delta;


        ScreenUtils.clear(0, 0, 0, 1);


        Gdx.gl.glEnable(GL20.GL_BLEND);
       Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        float alpha = 1.0f;
        switch (currentState) {
            case FADING_IN:
                alpha = Math.min(1.0f, stateTimer / FADE_IN_DURATION);
                if (alpha >= 1.0f) {
                    currentState = State.SHOWING;
                    stateTimer = 0f;
                }
                break;

            case SHOWING:
                alpha = 1.0f;
                if (stateTimer >= SHOW_DURATION) {
                    currentState = State.FADING_OUT;
                    stateTimer = 0f;
                }
                break;

            case FADING_OUT:
                alpha = Math.max(0.0f, 1.0f - (stateTimer / FADE_OUT_DURATION));
                if (alpha <= 0.0f) {
                    game.setScreen(new JuegoScreen(game, personaje));
                    dispose();

                }
                break;
        }

        game.batch.setColor(1, 1, 1, alpha);
        game.batch.draw(titleCardTexture, 0, 0, 800, 480);

        if (logoTexture != null) {
            float vibration = 1.0f;
            float vibX = MathUtils.random(-vibration, vibration);
            float vibY = MathUtils.random(-vibration, vibration);

            game.batch.draw(logoTexture,
                logoX + vibX,
                logoY + vibY,
                logoWidth,
                logoHeight);
        }

        game.batch.setColor(Color.WHITE);
        game.batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    @Override
    public void dispose() {}

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
