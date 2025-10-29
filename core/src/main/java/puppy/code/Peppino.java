package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// Peppino TAMBIÉN implementa el "contrato" de IJugador
public class Peppino implements IJugador {


    private Rectangle player;
    private Sound sonidoHerido;
    private Sound vidaSound;
    private int vidas = 5;
    private int puntos = 0;
    private int velx = 500; // Es más rápido
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    private boolean doblePuntos = false;
    private float tiempoDoblePuntosMax = 5.0f;
    private float tiempoDoblePuntos;


    private Texture idleSheet;
    private Texture moveSheet;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> moveAnimation;
    private State currentState;
    private float stateTimer;
    private boolean facingRight;


    private int keyIzquierda;
    private int keyDerecha;

    private enum State {
        IDLE,
        MOVING
    }

    // --- CONSTRUCTOR ---
    public Peppino(Texture idleSheet, Texture moveSheet, Sound ss, Sound vidaSound, int keyIzquierda, int keyDerecha) {
        this.idleSheet = idleSheet;
        this.moveSheet = moveSheet;
        sonidoHerido = ss;
        this.vidaSound = vidaSound;
        this.keyIzquierda = keyIzquierda;
        this.keyDerecha = keyDerecha;
        this.doblePuntos = false;
    }

    // --- MÉTODOS DE LA INTERFAZ (GETTERS/SETTERS) ---

    @Override
    public int getVidas() {
        return vidas;
    }

    @Override
    public int getPuntos() {
        return puntos;
    }

    @Override
    public Rectangle getArea() {
        return player;
    }

    @Override
    public void sumarPuntos(int pp) {
        if (doblePuntos)
            puntos += (pp * 2);
        else
            puntos += pp;
    }

    @Override
    public void activarDoblePuntos() {
        doblePuntos = true;
        tiempoDoblePuntos = tiempoDoblePuntosMax;
    }

    @Override
    public boolean estaDoblePuntos() {
        return doblePuntos;
    }

    @Override
    public float getTiempoDoblePuntos() {
        return tiempoDoblePuntos;
    }

    @Override
    public boolean estaHerido() {
        return herido;
    }

    // --- MÉTODOS DE LA INTERFAZ (ACCIONES) ---

    @Override
    public void actualizar(float delta) {
        if (doblePuntos) {
            tiempoDoblePuntos -= delta;
            if (tiempoDoblePuntos <= 0)
                doblePuntos = false;
        }
    }

    @Override
    public void crear() {
        player = new Rectangle();
        float nuevoAncho = 96;
        float nuevoAlto = 96;
        player.x = 800 / 2 - nuevoAncho / 2;
        player.y = 20;
        player.width = nuevoAncho;
        player.height = nuevoAlto;

        // (¡Asegúrate de que estos números coincidan con "nuevo_personaje_idle_sheet.png"!)
        int idleFrameCount = 3;
        float idleFrameDuration = 0.1f;
        idleAnimation = createAnimationFromSheet(idleSheet, idleFrameCount, 100, 100, idleFrameDuration);

        // (¡Asegúrate de que estos números coincidan con "nuevo_personaje_move_sheet.png"!)
        int moveFrameCount = 12;
        float moveFrameDuration = 0.1f;
        moveAnimation = createAnimationFromSheet(moveSheet, moveFrameCount, 100, 100, moveFrameDuration);

        currentState = State.IDLE;
        stateTimer = 0f;
        facingRight = true;
    }

    @Override
    public void dañar() {

        if (vidas <= 0)
            return;

        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
    }

    @Override
    public void sumarVida(int cantidad) {
        if (vidas >= 10) // Límite de 10 vidas
            return;
        vidas += cantidad;
        vidaSound.play();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        stateTimer += Gdx.graphics.getDeltaTime();
        Animation<TextureRegion> currentAnimation;

        if (currentState == State.MOVING)
            currentAnimation = moveAnimation;
        else
            currentAnimation = idleAnimation;

        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTimer, true);

        if (!facingRight && !currentFrame.isFlipX())
            currentFrame.flip(true, false);
        else if (facingRight && currentFrame.isFlipX())
            currentFrame.flip(true, false);

        if (!herido)
            batch.draw(currentFrame, player.x, player.y, player.width, player.height);
        else {
            batch.draw(currentFrame, player.x, player.y + MathUtils.random(-5, 5), player.width, player.height);
            tiempoHerido--;
            if (tiempoHerido <= 0)
                herido = false;
        }
    }

    @Override
    public void actualizarMovimiento() {
        if (Gdx.input.isKeyPressed(this.keyIzquierda)) {
            player.x -= velx * Gdx.graphics.getDeltaTime(); // ¡Usa su velocidad de 500!
            currentState = State.MOVING;
            facingRight = false;
        }
        else if (Gdx.input.isKeyPressed(this.keyDerecha)) {
            player.x += velx * Gdx.graphics.getDeltaTime(); // ¡Usa su velocidad de 500!
            currentState = State.MOVING;
            facingRight = true;
        }
        else
            currentState = State.IDLE;

        if (player.x < 0)
            player.x = 0;
        if (player.x > 800 - player.width)
            player.x = 800 - player.width;
    }

    @Override
    public void destruir() {
        // No hacemos nada.
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
}
