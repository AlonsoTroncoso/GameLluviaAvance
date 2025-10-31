package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuPrincipalScreen implements Screen {

    private final GameLluvia game;
    private OrthographicCamera camera;

    private Texture fondoJugar;
    private Texture fondoCambiar;
    private Texture fondoSalir;
    private Sound sonidoSeleccion;

    private int opcionSeleccionada; // 0 = Jugar, 1 = Cambiar, 2 = Salir


    public MenuPrincipalScreen(GameLluvia game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);


        fondoJugar = new Texture(Gdx.files.internal("backgroundMenu1.png"));
        fondoCambiar = new Texture(Gdx.files.internal("backgroundMenu3.png"));
        fondoSalir = new Texture(Gdx.files.internal("backgroundMenu2.png"));


        sonidoSeleccion = Gdx.audio.newSound(Gdx.files.internal("menuSFX.wav"));


        opcionSeleccionada = 0;
    }

    @Override
    public void show() {

        if (game.musicaMenu!=null && !game.musicaMenu.isPlaying()) {
            game.musicaMenu.play();
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        int opcionAnterior = opcionSeleccionada;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            opcionSeleccionada = Math.max(0, opcionSeleccionada - 1);

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            opcionSeleccionada = Math.min(2, opcionSeleccionada + 1);

        if (opcionAnterior != opcionSeleccionada)
            sonidoSeleccion.play();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            switch (opcionSeleccionada) {
                case 0: // JUGAR

                    game.setScreen(new LevelSelectScreen(game)); // Va a la selección de nivel
                    dispose();
                    break;
                case 1: // CAMBIAR PLAYER
                    game.setScreen(new CharacterScreen(game)); // Va a la selección de personaje
                    dispose();
                    break;
                case 2: // SALIR
                    Gdx.app.exit(); // Cierra el juego
                    break;
            }
        }


        game.batch.begin();


        if (opcionSeleccionada == 0)
            game.batch.draw(fondoJugar, 0, 0, 800, 480);

        else if (opcionSeleccionada == 1)
            game.batch.draw(fondoCambiar, 0, 0, 800, 480);

        else
             game.batch.draw(fondoSalir, 0, 0, 800, 480);


        game.batch.end();
    }

    @Override
    public void dispose() {

        fondoJugar.dispose();
        fondoCambiar.dispose();
        fondoSalir.dispose();
        sonidoSeleccion.dispose();

    }


    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
