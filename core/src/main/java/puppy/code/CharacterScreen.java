package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class CharacterScreen implements Screen {

    private final GameLluvia game;
    private OrthographicCamera camera;
    private Texture fondo; // El fondo de esta pantalla

    private Rectangle botonAtras;
    private Rectangle areaPersonaje;

    public CharacterScreen(GameLluvia game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        fondo = new Texture(Gdx.files.internal("characterScreen.png"));
        botonAtras = new Rectangle(10, 420, 100, 50);

        // Define el área donde se dibuja el personaje (para detectar clics)
        areaPersonaje = new Rectangle(300, 140, 200, 200);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(fondo, 0, 0, 800, 480);

        // Dibuja el botón "Atrás"
        game.font.draw(game.batch, "ATRAS", botonAtras.x + 30, botonAtras.y + 30);

        // Dibuja la FOTO ESTÁTICA del Personaje Seleccionado
        Texture fotoActual;
        String nombreActual;

        if (game.personajeSeleccionado == GameLluvia.CharacterChoice.PERSONAJE_1) {
            fotoActual = game.staticNoise;
            nombreActual = "Noise";
        } else {
            fotoActual = game.staticPeppino;
            nombreActual = "Peppino";
        }

        // Dibuja la foto en el área definida (centrada)
        game.batch.draw(fotoActual, areaPersonaje.x, areaPersonaje.y, areaPersonaje.width, areaPersonaje.height);

        // Dibuja el nombre
        game.font.draw(game.batch, "Seleccionado: " + nombreActual, 350, 100);
        game.font.draw(game.batch, "(Haz clic en el personaje para cambiar)", 280, 80);

        game.batch.end();

        // Lógica de Clics
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (botonAtras.contains(touchPos.x, touchPos.y)) {
                // Vuelve al menú
                game.setScreen(new MenuPrincipalScreen(game));
                dispose();
            }

            if (areaPersonaje.contains(touchPos.x, touchPos.y)) {

                if (game.personajeSeleccionado == GameLluvia.CharacterChoice.PERSONAJE_1) {
                    game.personajeSeleccionado = GameLluvia.CharacterChoice.PERSONAJE_2;
                } else {
                    game.personajeSeleccionado = GameLluvia.CharacterChoice.PERSONAJE_1;
                }
            }
        }
    }

    @Override
    public void dispose() {
        fondo.dispose();
    }

    @Override
    public void show() {
        if (!game.musicaMenu.isPlaying())
            game.musicaMenu.play();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
