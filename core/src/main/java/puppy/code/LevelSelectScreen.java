package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class LevelSelectScreen implements Screen {

    private final GameLluvia game;
    private OrthographicCamera camera;
    private Texture fondoSeleccionNivel;


    private Rectangle areaNivel1;
    private Rectangle areaNivel2;
    private Rectangle botonAtras;

    public LevelSelectScreen(GameLluvia game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);


        fondoSeleccionNivel = new Texture(Gdx.files.internal("characterScreen.png"));
        float previewWidth = 270;
        float previewHeight = 200;
        float padding = 50;
        float totalWidth = previewWidth * 2 + padding;
        float startX = (800 - totalWidth) / 2; // Centrar horizontalmente
        float yPos = (480 - previewHeight) / 2; // Centrar verticalmente

        areaNivel1 = new Rectangle(startX, yPos, previewWidth, previewHeight);
        areaNivel2 = new Rectangle(startX + previewWidth + padding, yPos, previewWidth, previewHeight);

        botonAtras = new Rectangle(10, 420, 100, 50); // Botón de "Atrás"
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // --- LÓGICA DE TECLADO (ESCAPE) ---
        // (La ponemos aquí arriba para que se detecte primero)
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.atrasSound.play(); // <-- Reproduce el sonido
            game.setScreen(new MenuPrincipalScreen(game)); // Vuelve al menú
            dispose();
            return; // Salimos del render
        }
        // ---------------------------------
        game.batch.begin();
        // Dibuja el fondo
        game.batch.draw(fondoSeleccionNivel, 0, 0, 800, 480);

        // Dibuja las previews usando las texturas del "Jefe"
        game.batch.draw(game.previewNivel1, areaNivel1.x, areaNivel1.y, areaNivel1.width, areaNivel1.height);
        game.batch.draw(game.previewNivel2, areaNivel2.x, areaNivel2.y, areaNivel2.width, areaNivel2.height);

        // Dibuja texto debajo (opcional)
        game.font.draw(game.batch, "Nivel 1", areaNivel1.x + 70, areaNivel1.y - 10);
        game.font.draw(game.batch, "Nivel 2", areaNivel2.x + 70, areaNivel2.y - 10);

        // Dibuja botón "Atrás"
        game.font.draw(game.batch, "ATRAS (ESC)", botonAtras.x + 30, botonAtras.y + 30);

        game.batch.end();

        // --- Lógica de Clics ---
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            // Clic en Nivel 1
            if (areaNivel1.contains(touchPos.x, touchPos.y)) {
                game.nivelSeleccionado = GameLluvia.LevelChoice.NIVEL_1; // Guarda la elección
                game.musicaMenu.stop(); // Detiene música del menú
                game.setScreen(new JuegoScreen(game, game.personajeSeleccionado)); // Inicia el juego
                dispose();
            }

            // Clic en Nivel 2
            if (areaNivel2.contains(touchPos.x, touchPos.y)) {
                game.nivelSeleccionado = GameLluvia.LevelChoice.NIVEL_2; // Guarda la elección
                game.musicaMenu.stop(); // Detiene música del menú
                game.setScreen(new JuegoScreen(game, game.personajeSeleccionado)); // Inicia el juego
                dispose();
            }

        }
    }

    @Override
    public void dispose() {
        fondoSeleccionNivel.dispose();
        // Las previews se liberan en GameLluvia
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
