package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color; // <-- ¡IMPORTANTE!
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class LevelSelectScreen implements Screen {

    private final GameLluvia game;
    private OrthographicCamera camera;
    private GestorAssets assets;
    private Rectangle botonAtras;

    // Nivel 1 (Fastfood Saloon)
    private final float NIVEL1_W = 200;
    private final float NIVEL1_H = 120;
    private final float NIVEL1_X = 200;
    private final float NIVEL1_Y = 200;

    // Nivel 2 (Golf)
    private final float NIVEL2_W = 150;
    private final float NIVEL2_H = 200;
    private final float NIVEL2_X = 450;
    private final float NIVEL2_Y = 170;

    private State currentState = State.SELECTING;

    private enum State {
        SELECTING,
        CONFIRMING
    }

    private float confirmationTimer = 0f;
    private final float CONFIRMATION_DURATION = 1.1f;
    private final float FLICKER_RATE = 0.04f;
    private GameLluvia.LevelChoice nivelConfirmado;

    public LevelSelectScreen(GameLluvia game) {

        this.game = game;
        this.assets = GestorAssets.getInstance();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        botonAtras = new Rectangle(10, 420, 100, 50);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if (currentState == State.SELECTING) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                assets.atrasSound.play();
                game.setScreen(new MenuPrincipalScreen(game));
                dispose();
                return;
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                if (game.nivelSeleccionado != GameLluvia.LevelChoice.NIVEL_1) {
                    game.nivelSeleccionado = GameLluvia.LevelChoice.NIVEL_1;
                    assets.cambioSound.play();
                }
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                if (game.nivelSeleccionado != GameLluvia.LevelChoice.NIVEL_2) {
                    game.nivelSeleccionado = GameLluvia.LevelChoice.NIVEL_2;
                    assets.cambioSound.play();
                }
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                iniciarConfirmacion();

            if (Gdx.input.justTouched()) {
                Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);

                if (botonAtras.contains(touchPos.x, touchPos.y)) {
                    assets.atrasSound.play();
                    game.setScreen(new MenuPrincipalScreen(game));
                    dispose();
                }


                Rectangle hitboxNivel1 = new Rectangle(NIVEL1_X, NIVEL1_Y, NIVEL1_W, NIVEL1_H);
                if (hitboxNivel1.contains(touchPos.x, touchPos.y)) {
                    game.nivelSeleccionado = GameLluvia.LevelChoice.NIVEL_1;
                    iniciarConfirmacion();
                }

                Rectangle hitboxNivel2 = new Rectangle(NIVEL2_X, NIVEL2_Y, NIVEL2_W, NIVEL2_H);
                if (hitboxNivel2.contains(touchPos.x, touchPos.y)) {
                    game.nivelSeleccionado = GameLluvia.LevelChoice.NIVEL_2;
                    iniciarConfirmacion();
                }
            }
        }

        if (currentState == State.CONFIRMING) {
            confirmationTimer += delta;


            if (confirmationTimer >= CONFIRMATION_DURATION) {
                assets.musicaMenu.stop();

                // Va a la Title Card
                game.setScreen(new TitleCardScreen(game, nivelConfirmado, game.personajeSeleccionado));
                dispose();
                return;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            assets.atrasSound.play();
            game.setScreen(new MenuPrincipalScreen(game));
            dispose();
            return;
        }

        game.batch.begin();
        game.batch.draw(assets.fondoLevelSelect, 0, 0, 800, 480);
        assets.font.draw(game.batch, "ATRAS (ESC)", botonAtras.x + 20, botonAtras.y + 30);
        boolean isVisible = true;

        if (currentState == State.CONFIRMING) {
            float cycleTime = FLICKER_RATE * 2;
            if ((confirmationTimer % cycleTime) > FLICKER_RATE)
                isVisible = false;
        }

        if (game.nivelSeleccionado == GameLluvia.LevelChoice.NIVEL_1) {
            if(currentState == State.CONFIRMING && !isVisible)
                game.batch.setColor(Color.BLACK);

            else
                game.batch.setColor(Color.WHITE);

            game.batch.draw(assets.previewNivel1, NIVEL1_X, NIVEL1_Y, NIVEL1_W, NIVEL1_H);
            game.batch.setColor(Color.GRAY);
            game.batch.draw(assets.previewNivel2, NIVEL2_X, NIVEL2_Y, NIVEL2_W, NIVEL2_H);

        }

        else {
            game.batch.setColor(Color.GRAY);
            game.batch.draw(assets.previewNivel1, NIVEL1_X, NIVEL1_Y, NIVEL1_W, NIVEL1_H);

            if(currentState == State.CONFIRMING && !isVisible)
                game.batch.setColor(Color.BLACK);

            else
                game.batch.setColor(Color.WHITE);

            game.batch.draw(assets.previewNivel2, NIVEL2_X, NIVEL2_Y, NIVEL2_W, NIVEL2_H);
        }


        game.batch.setColor(Color.WHITE);

        game.batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (botonAtras.contains(touchPos.x, touchPos.y)) {
                assets.atrasSound.play();
                game.setScreen(new MenuPrincipalScreen(game));
                dispose();
            }

            Rectangle hitboxNivel1 = new Rectangle(NIVEL1_X, NIVEL1_Y, NIVEL1_W, NIVEL1_H);
            if (hitboxNivel1.contains(touchPos.x, touchPos.y)) {
                game.nivelSeleccionado = GameLluvia.LevelChoice.NIVEL_1;
                assets.musicaMenu.stop();
                game.setScreen(new TitleCardScreen(game, game.nivelSeleccionado, game.personajeSeleccionado));
                dispose();
            }


            Rectangle hitboxNivel2 = new Rectangle(NIVEL2_X, NIVEL2_Y, NIVEL2_W, NIVEL2_H);
            if (hitboxNivel2.contains(touchPos.x, touchPos.y)) {
                game.nivelSeleccionado = GameLluvia.LevelChoice.NIVEL_2;
                assets.musicaMenu.stop();
                game.setScreen(new TitleCardScreen(game, game.nivelSeleccionado, game.personajeSeleccionado));
                dispose();
            }
        }
    }

    private void iniciarConfirmacion() {
        if (currentState == State.SELECTING) {
            currentState = State.CONFIRMING;
            nivelConfirmado = game.nivelSeleccionado;
            assets.confimarSound.play();
            confirmationTimer = 0f;
        }
    }

    @Override
    public void dispose() {
    }

    @Override public void show() {
        if (assets.musicaMenu != null && !assets.musicaMenu.isPlaying())
            assets.musicaMenu.play();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
