package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class JugadorBase implements IJugador {

    protected Rectangle player;
    protected int vidas;
    protected int puntos = 0;
    protected int velx = 425;
    protected boolean doblePuntos = false;
    protected float tiempoDoblePuntosMax = 5.0f;
    protected float tiempoDoblePuntos;

    protected Texture idleSheet, moveSheet, sheetQuemado, sheetRecuperando, sheetGolpeado, sheetDash;
    protected Animation<TextureRegion> idleAnimation, moveAnimation, quemadoAnimation, recuperandoAnimation, golpeadoAnimation, dashAnimation;

    protected Sound dashSound;

    protected int keyIzquierda, keyDerecha, keyDash;
    protected boolean dashHaciaDerecha;

    protected enum State {
        IDLE, MOVING, QUEMADO_REBOTANDO, GOLPEADO_REBOTANDO, RECUPERANDOSE_SUELO, DASHING
    }

    protected State currentState;
    protected float stateTimer;
    protected boolean facingRight;


    protected float velXRebote;
    protected float velYRebote;
    protected final float GRAVEDAD = -900f;
    protected boolean invencible = false;

    private float tiempoInvencible;
    private float flickerTimer = 0f;

    public JugadorBase(Texture idleSheet, Texture moveSheet, Texture sheetQuemado, Texture sheetRecuperando,
                       Texture sheetGolpeado, Texture sheetDash, Sound dashSound,
                       int keyIzquierda, int keyDerecha, int keyDash) {

        this.idleSheet = idleSheet;
        this.moveSheet = moveSheet;
        this.sheetQuemado = sheetQuemado;
        this.sheetRecuperando = sheetRecuperando;
        this.sheetGolpeado = sheetGolpeado;
        this.sheetDash = sheetDash;
        this.dashSound = dashSound;
        this.keyIzquierda = keyIzquierda;
        this.keyDerecha = keyDerecha;
        this.keyDash = keyDash;
    }

    @Override
    public final void crear() {
        player = new Rectangle();
        float nuevoAncho = 96;
        float nuevoAlto = 96;
        player.x = 800 / 2 - nuevoAncho / 2;
        player.y = 25;
        player.width = nuevoAncho;
        player.height = nuevoAlto;

        this.idleAnimation = crearAnimacionIdle();
        this.moveAnimation = crearAnimacionMove();
        this.quemadoAnimation = crearAnimacionQuemado();
        this.recuperandoAnimation = crearAnimacionRecuperando();
        this.golpeadoAnimation = crearAnimacionGolpeado();
        this.dashAnimation = crearAnimacionDash();

        recuperandoAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        dashAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        this.vidas = getVidasIniciales();

        currentState = State.IDLE;
        stateTimer = 0f;
        facingRight = true;
        invencible = false;
        doblePuntos = false;
    }

    @Override
    public final void actualizar(float delta) {

        if (doblePuntos) {
            tiempoDoblePuntos -= delta;
            if (tiempoDoblePuntos <= 0)
                doblePuntos = false;
        }

        if (invencible) {
            tiempoInvencible -= delta;
            if (tiempoInvencible <= 0)
                invencible = false;
        }
    }

    @Override
    public final void dañar(String tipoDano) {

        if (invencible || currentState == State.QUEMADO_REBOTANDO || currentState == State.GOLPEADO_REBOTANDO || currentState == State.RECUPERANDOSE_SUELO || vidas <= 0)
            return;

        vidas--;

        // PASO ABSTRACTO: Cada hijo define su duración de invencibilidad
        invencible = true;
        tiempoInvencible = getDuracionInvencible();
        stateTimer = 0f;

        if (tipoDano.equals("QUEMADURA")) {
            currentState = State.QUEMADO_REBOTANDO;
            // PASOS ABSTRACTOS: Cada hijo define su física de rebote y sonido
            setValoresReboteQuemadura();
            reproducirSonidoQuemadura();
        } else {
            currentState = State.GOLPEADO_REBOTANDO;
            // PASOS ABSTRACTOS: Cada hijo define su física de rebote y sonido
            setValoresReboteGolpe();
            reproducirSonidoGolpeNormal();
        }
    }

    @Override
    public final void sumarVida(int cantidad) {
        // PASO ABSTRACTO: Cada hijo define sus vidas máximas
        if (vidas >= getVidasMaximas())
            return;

        vidas += cantidad;

        // PASO ABSTRACTO: Cada hijo define su sonido de "sumar vida"
        reproducirSonidoVida();
    }

    @Override
    public final void dibujar(SpriteBatch batch) {
        stateTimer += Gdx.graphics.getDeltaTime();
        Animation<TextureRegion> currentAnimation;

        switch (currentState) {
            case DASHING:           currentAnimation = dashAnimation;         break;
            case GOLPEADO_REBOTANDO:currentAnimation = golpeadoAnimation;    break;
            case QUEMADO_REBOTANDO: currentAnimation = quemadoAnimation;      break;
            case RECUPERANDOSE_SUELO:currentAnimation = recuperandoAnimation; break;
            case MOVING:            currentAnimation = moveAnimation;         break;
            case IDLE:
            default:                currentAnimation = idleAnimation;         break;
        }

        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTimer,
            (currentState != State.RECUPERANDOSE_SUELO)); // Recuperando no loopea

        boolean shouldFaceRight;
        if (currentState == State.QUEMADO_REBOTANDO || currentState == State.GOLPEADO_REBOTANDO)
            shouldFaceRight = (velXRebote >= 0);
        else if (currentState == State.DASHING)
            shouldFaceRight = dashHaciaDerecha;
        else if (currentState == State.RECUPERANDOSE_SUELO)
            shouldFaceRight = true;
        else
            shouldFaceRight = facingRight;

        if (!shouldFaceRight && !currentFrame.isFlipX())
            currentFrame.flip(true, false);
        else if (shouldFaceRight && currentFrame.isFlipX())
            currentFrame.flip(true, false);

        boolean dibujarSprite = true;
        if (invencible && currentState != State.QUEMADO_REBOTANDO && currentState != State.GOLPEADO_REBOTANDO && currentState != State.RECUPERANDOSE_SUELO) {
            flickerTimer += Gdx.graphics.getDeltaTime();
            // PASO ABSTRACTO: Cada hijo define su velocidad de parpadeo
            float cycleTime = getFlickerRate() * 2;

            if ((flickerTimer % cycleTime) > getFlickerRate())
                dibujarSprite = false;
        } else {
            flickerTimer = 0f;
        }


        if (dibujarSprite) {
            // PASO ABSTRACTO: Cada hijo define como dibujarse, esto es porque el Dash de Noise tiene un tamaño diferente
            dibujarSpriteEspecial(batch, currentFrame);
        }
    }

    @Override
    public final void actualizarMovimiento() {
        State previousState = currentState;
        float delta = Gdx.graphics.getDeltaTime();

        switch (currentState) {
            case GOLPEADO_REBOTANDO:
                velYRebote += GRAVEDAD * delta;
                player.x += velXRebote * delta;
                player.y += velYRebote * delta;

                // Lógica común de paredes y suelo
                if (player.x < 0) { player.x = 0; velXRebote *= -0.98f; }
                else if (player.x + player.width > 800) { player.x = 800 - player.width; velXRebote *= -0.98f; }
                if (player.y + player.height > 480) { player.y = 480 - player.height; velYRebote *= -0.9f; }
                if (player.y <= 20) {
                    player.y = 20;
                    currentState = State.IDLE;
                    stateTimer = 0f;

                    invencible = false;
                }
                break;

            case QUEMADO_REBOTANDO:
                velYRebote += GRAVEDAD * delta;
                player.x += velXRebote * delta;
                player.y += velYRebote * delta;


                if (player.x < 0) { player.x = 0; velXRebote *= -0.98f; }
                else if (player.x + player.width > 800) { player.x = 800 - player.width; velXRebote *= -0.98f; }
                if (player.y + player.height > 480) { player.y = 480 - player.height; velYRebote *= -0.9f; }
                if (player.y <= 20) {
                    player.y = 20;
                    currentState = State.RECUPERANDOSE_SUELO;
                    stateTimer = 0f;

                }
                break;

            case RECUPERANDOSE_SUELO:
                if (recuperandoAnimation.isAnimationFinished(stateTimer)) {
                    currentState = State.IDLE;
                    invencible = false;
                }
                break;

            case DASHING:
                // PASO ABSTRACTO: Cada hijo define su velocidad de dash
                float desplazamientoX = getVelocidadDash() * delta;
                player.x += dashHaciaDerecha ? desplazamientoX : -desplazamientoX;

                // Lógica común de dash
                if (player.x < 0) player.x = 0;
                if (player.x > 800 - player.width) player.x = 800 - player.width;

                boolean quiereCancelar = (dashHaciaDerecha && Gdx.input.isKeyPressed(this.keyIzquierda)) ||
                    (!dashHaciaDerecha && Gdx.input.isKeyPressed(this.keyDerecha));
                boolean animacionDashTerminada = dashAnimation.isAnimationFinished(stateTimer);

                if (quiereCancelar)
                    currentState = State.MOVING;
                else if (animacionDashTerminada)
                    currentState = State.IDLE;
                break;

            case IDLE:
            case MOVING:
            default:
                // Lógica común de movimiento
                if (Gdx.input.isKeyJustPressed(this.keyDash)) {
                    currentState = State.DASHING;
                    dashHaciaDerecha = facingRight;
                    dashSound.play();
                    stateTimer = 0f;
                } else {
                    if (Gdx.input.isKeyPressed(this.keyIzquierda)) {
                        player.x -= velx * delta;
                        currentState = State.MOVING;
                        facingRight = false;
                    } else if (Gdx.input.isKeyPressed(this.keyDerecha)) {
                        player.x += velx * delta;
                        currentState = State.MOVING;
                        facingRight = true;
                    } else {
                        if (previousState == State.MOVING || previousState == State.IDLE)
                            currentState = State.IDLE;
                    }
                    if (player.x < 0) player.x = 0;
                    if (player.x > 800 - player.width) player.x = 800 - player.width;
                }
                break;
        }
    }

    @Override
    public final int getVidas() {
        return vidas;
    }

    @Override
    public final int getPuntos() {
        return puntos;
    }

    @Override
    public final Rectangle getArea() {
        return player;
    }

    @Override
    public final boolean estaDoblePuntos() {
        return doblePuntos;
    }

    @Override
    public final float getTiempoDoblePuntos() {
        return tiempoDoblePuntos;
    }

    @Override
    public final boolean estaEnDash() {
        return currentState == State.DASHING;
    }

    @Override public final boolean estaHerido() {
        return false;
    }

    @Override
    public final void sumarPuntos(int pp) {
        if (doblePuntos)
            puntos += (pp * 2);
        else
            puntos += pp;
    }

    @Override
    public final void activarDoblePuntos() {
        if (!doblePuntos) {
            doblePuntos = true;
            tiempoDoblePuntos = tiempoDoblePuntosMax;
        }
    }


    @Override public void destruir() { }

    protected final Animation<TextureRegion> createAnimationFromSheet(Texture sheet, int frameCount, int frameWidth, int frameHeight, float frameDuration) {
        TextureRegion[][] tmp = TextureRegion.split(sheet, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[frameCount];
        int index = 0;
        for (int j = 0; j < frameCount; j++)
            frames[index++] = tmp[0][j];

        Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
        anim.setPlayMode(Animation.PlayMode.LOOP);
        return anim;
    }


    // Estos son los métodos que Noise y Peppino deben implementar.

    // Valores de balance
    protected abstract int getVidasIniciales();
    protected abstract int getVidasMaximas();
    protected abstract float getVelocidadDash();
    protected abstract float getDuracionInvencible();
    protected abstract float getFlickerRate();

    // Lógica de dibujado especial
    protected abstract void dibujarSpriteEspecial(SpriteBatch batch, TextureRegion currentFrame);

    // Física de rebote
    protected abstract void setValoresReboteQuemadura();
    protected abstract void setValoresReboteGolpe();

    // Sonidos
    protected abstract void reproducirSonidoVida();
    protected abstract void reproducirSonidoQuemadura();
    protected abstract void reproducirSonidoGolpeNormal();

    // Creación de Animaciones
    protected abstract Animation<TextureRegion> crearAnimacionIdle();
    protected abstract Animation<TextureRegion> crearAnimacionMove();
    protected abstract Animation<TextureRegion> crearAnimacionQuemado();
    protected abstract Animation<TextureRegion> crearAnimacionRecuperando();
    protected abstract Animation<TextureRegion> crearAnimacionGolpeado();
    protected abstract Animation<TextureRegion> crearAnimacionDash();
}
