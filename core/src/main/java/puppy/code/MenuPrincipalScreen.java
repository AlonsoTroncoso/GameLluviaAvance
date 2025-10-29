package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuPrincipalScreen implements Screen {

    private Texture fondoMenu1;
    private Texture fondoMenu2;
    private Texture fondoMenu3;
    private float animationTimer;
    private final float duracionFotograma = 0.5f;
    private final GameLluvia game; // Referencia al "Jefe"
    private OrthographicCamera camera;

    // Hitboxes para los botones
    private Rectangle botonJugar;
    private Rectangle botonCambiarPersonaje;

    public MenuPrincipalScreen(GameLluvia game) {
        this.game = game; // Guarda la referencia al "Jefe"

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // --- Carga los assets de ESTA pantalla ---
        // (¡Reemplaza con los nombres de tus archivos!)
        fondoMenu1 = new Texture(Gdx.files.internal("bgBeach1.png"));
        fondoMenu2 = new Texture(Gdx.files.internal("bgBeach2.png"));
        fondoMenu3 = new Texture(Gdx.files.internal("bgBeach3.png"));


        animationTimer = 0f;

        // --- Define las áreas de los botones ---
        // (x, y, ancho, alto)
        botonJugar = new Rectangle(300, 250, 200, 50); // Centrado
        botonCambiarPersonaje = new Rectangle(300, 180, 200, 50); // Abajo
    }

    @Override
    public void show() {
        if(!game.musicaMenu.isPlaying())
            game.musicaMenu.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        animationTimer += delta;
        float cicloCompleto = duracionFotograma*3;

        if (animationTimer >= cicloCompleto)
            animationTimer -= cicloCompleto;

        game.batch.begin();

        if (animationTimer < duracionFotograma)
            // Frame 1 (Ej. 0s a 0.5s)
            game.batch.draw(fondoMenu1, 0, 0, 800, 480);

        else if (animationTimer < duracionFotograma * 2)
            // Frame 2 (Ej. 0.5s a 1.0s)
            game.batch.draw(fondoMenu2, 0, 0, 800, 480);
        else
            // Frame 3 (Ej. 1.0s a 1.5s)
            game.batch.draw(fondoMenu3, 0, 0, 800, 480);

        // Dibuja los botones (texto)
        game.font.draw(game.batch, "JUGAR", botonJugar.x + 75, botonJugar.y + 30);
        game.font.draw(game.batch, "CAMBIAR PERSONAJE", botonCambiarPersonaje.x + 30, botonCambiarPersonaje.y + 30);

        game.batch.end();

        if(Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (botonJugar.contains(touchPos.x, touchPos.y)) {
                game.musicaMenu.stop();
                game.setScreen(new JuegoScreen(game, game.personajeSeleccionado));
                dispose();
            }

            if (botonCambiarPersonaje.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new CharacterScreen(game));
                dispose();
            }
        }
    }

    @Override
    public void dispose() {
        // Libera los assets de ESTA pantalla
        fondoMenu1.dispose();
        fondoMenu2.dispose();
        fondoMenu3.dispose();
    }

    //
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
}
