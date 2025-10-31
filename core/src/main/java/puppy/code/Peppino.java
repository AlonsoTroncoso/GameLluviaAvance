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


public class Peppino implements IJugador {

    private Rectangle player;
    private Sound sonidoHerido, vidaSound, vidaSound2, vidaSound3, sonidoQuemandose, sonidoQuemandose2,
        sonidoQuemandose3, sonidoGolpe, dashSound, sonidoHerido2;

    private int vidas = 5;
    private int puntos = 0;
    private int velx = 425;

    private float tiempoInvencibleMax = 0.5f;
    private float tiempoInvencible;
    private float flickerTimer = 0f;
    private final float FLICKER_RATE = 0.05f;


    private boolean doblePuntos = false;
    private float tiempoDoblePuntosMax = 5.0f;
    private float tiempoDoblePuntos;


    private Texture idleSheet, moveSheet, sheetQuemado, sheetRecuperando, sheetGolpeado, sheetDash;
    private Animation<TextureRegion> idleAnimation, moveAnimation, quemadoAnimation, recuperandoAnimation,
        golpeadoAnimation, dashAnimation;

    private int keyDash;
    private float dashVelX = 690f;
    private boolean dashHaciaDerecha;

    private enum State {
        IDLE,
        MOVING,
        QUEMADO_REBOTANDO,
        GOLPEADO_REBOTANDO,
        RECUPERANDOSE_SUELO,
        DASHING
    }

    private State currentState;
    private float stateTimer;
    private boolean facingRight;


    private float velXRebote;
    private float velYRebote;
    private final float GRAVEDAD = -900f;
    private boolean invencible = false;

    private int keyIzquierda;
    private int keyDerecha;

    public Peppino(Texture idleSheet, Texture moveSheet, Texture sheetQuemado, Texture sheetRecuperando,
                   Texture sheetGolpeado, Texture sheetDash, Sound ss, Sound vs, Sound vs2, Sound vs3,
                   Sound sq, Sound sq2, Sound sq3, Sound ds, Sound sg, Sound ss2,
                   int keyIzquierda, int keyDerecha, int keyDash) {

        this.idleSheet = idleSheet;
        this.moveSheet = moveSheet;
        this.sheetQuemado = sheetQuemado;
        this.sheetRecuperando = sheetRecuperando;
        this.sheetGolpeado = sheetGolpeado;
        this.sheetDash = sheetDash;
        this.sonidoHerido = ss;
        this.vidaSound = vs;
        this.vidaSound2 = vs2;
        this.vidaSound3 = vs3;
        this.sonidoQuemandose = sq;
        this.sonidoQuemandose2 = sq2;
        this.sonidoQuemandose3 = sq3;
        this.dashSound = ds;
        this.sonidoGolpe = sg;
        this.sonidoHerido2 = ss2;
        this.keyIzquierda = keyIzquierda;
        this.keyDerecha = keyDerecha;
        this.keyDash = keyDash;
        this.doblePuntos = false;
    }


    @Override public int getVidas() { return vidas; }
    @Override public int getPuntos() { return puntos; }
    @Override public Rectangle getArea() { return player; }
    @Override public boolean estaDoblePuntos() { return doblePuntos; }
    @Override public float getTiempoDoblePuntos() { return tiempoDoblePuntos; }
    @Override public boolean estaHerido() { return false; }

    @Override
    public boolean estaEnDash() {
        return currentState == State.DASHING;
    }

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
        player.y = 25;
        player.width = nuevoAncho;
        player.height = nuevoAlto;



        idleAnimation = createAnimationFromSheet(idleSheet, 17, 100, 100, 0.05f); // Tus números
        moveAnimation = createAnimationFromSheet(moveSheet, 12, 100, 100, 0.05f); // Tus números
        quemadoAnimation = createAnimationFromSheet(sheetQuemado, 5, 100, 100, 0.05f); // Tus números
        recuperandoAnimation = createAnimationFromSheet(sheetRecuperando, 14, 100, 100, 0.05f); // Tus números
        recuperandoAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        int golpeadoFrameCount = 11;
        float golpeadoFrameDuration = 0.05f;
        golpeadoAnimation = createAnimationFromSheet(sheetGolpeado, golpeadoFrameCount, 100, 100, golpeadoFrameDuration); // Ejemplo tamaño

        int dashFrameCount = 12;
        float dashFrameDuration = 0.05f;
        dashAnimation = createAnimationFromSheet(sheetDash, dashFrameCount, 100, 100, dashFrameDuration);
        dashAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        currentState = State.IDLE;
        stateTimer = 0f;
        facingRight = true;
        invencible = false;
    }

    @Override
    public void dañar(String tipoDano) {

        if (invencible || currentState == State.QUEMADO_REBOTANDO || currentState == State.GOLPEADO_REBOTANDO || currentState == State.RECUPERANDOSE_SUELO || vidas <= 0)
            return;

        vidas--;

        if (tipoDano.equals("QUEMADURA")) {

            currentState = State.QUEMADO_REBOTANDO;
            invencible = true;
            stateTimer = 0f;

            velYRebote = 950f;
            velXRebote = facingRight ? -900f : 900f;

            int sonidoRNG = MathUtils.random(1, 3);
            if(sonidoRNG==1)
                sonidoQuemandose.play();

            else if(sonidoRNG==2)
                sonidoQuemandose2.play();

            else
                sonidoQuemandose3.play();

        }

        else {
            currentState = State.GOLPEADO_REBOTANDO;
            invencible = true;
            stateTimer = 0f;
            velYRebote = 250f;
            velXRebote = facingRight ? -450f : 450f;

            if(MathUtils.randomBoolean()) {
                sonidoHerido.play();
                sonidoGolpe.play();
            }

            else {
                sonidoHerido2.play();
                sonidoGolpe.play();
            }
        }
    }

    @Override
    public void sumarPuntos(int pp) {
        if (doblePuntos)
            puntos += (pp * 2);
        else
            puntos += pp;
    }

    @Override
    public void sumarVida(int cantidad) {
        if (vidas >= 10)
            return;
        vidas += cantidad;

        int sonidoRNG = MathUtils.random(1, 3);
        if(sonidoRNG==1)
            vidaSound.play();

        else if(sonidoRNG==2)
            vidaSound2.play();

        else
            vidaSound3.play();
    }

    @Override
    public void activarDoblePuntos() {

        if (!doblePuntos) {
            doblePuntos = true;
            tiempoDoblePuntos = tiempoDoblePuntosMax;

        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        stateTimer += Gdx.graphics.getDeltaTime();
        Animation<TextureRegion> currentAnimation;


        switch (currentState) {
            case DASHING:
                currentAnimation = dashAnimation;
                break;
            case GOLPEADO_REBOTANDO:
                currentAnimation = golpeadoAnimation;
                break;
            case QUEMADO_REBOTANDO:
                currentAnimation = quemadoAnimation;
                break;
            case RECUPERANDOSE_SUELO:
                currentAnimation = recuperandoAnimation;
                break;
            case MOVING:
                currentAnimation = moveAnimation;
                break;
            case IDLE:
            default:
                currentAnimation = idleAnimation;
                break;
        }

        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTimer,
            (currentState != State.RECUPERANDOSE_SUELO));

        boolean shouldFaceRight;
        if (currentState == State.QUEMADO_REBOTANDO || currentState == State.GOLPEADO_REBOTANDO)
            shouldFaceRight = (velXRebote >= 0);

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
            float cycleTime = FLICKER_RATE * 2;
            if ((flickerTimer % cycleTime) > FLICKER_RATE)
                dibujarSprite = false;

        }
        else
            flickerTimer = 0f;


        if (dibujarSprite)
            batch.draw(currentFrame, player.x, player.y, player.width, player.height);

    }

    @Override
    public void actualizarMovimiento() {

        Peppino.State previousState = currentState;
        float delta = Gdx.graphics.getDeltaTime();


        switch (currentState) {

            case GOLPEADO_REBOTANDO:
                velYRebote += GRAVEDAD * delta;
                player.x += velXRebote * delta;
                player.y += velYRebote * delta;

                if (player.x < 0) {
                    player.x = 0;
                    velXRebote *= -0.98f;
                }

                else if (player.x + player.width > 800) {
                    player.x = 800 - player.width;
                    velXRebote *= -0.98f;
                }


                if (player.y + player.height > 480) {
                    player.y = 480 - player.height;
                    velYRebote *= -0.9f;
                }

                if (player.y <= 20) {
                    player.y = 20;
                    currentState = Peppino.State.IDLE;
                    invencible = false;
                    stateTimer = 0f;
                }
                break;

            case QUEMADO_REBOTANDO:

                velYRebote += GRAVEDAD * delta;
                player.x += velXRebote * delta;
                player.y += velYRebote * delta;

                if (player.x < 0) {
                    player.x = 0;
                    velXRebote *= -0.98f;
                }

                else if (player.x + player.width > 800) {
                    player.x = 800 - player.width;
                    velXRebote *= -0.98f;
                }

                if (player.y + player.height > 480) {
                    player.y = 480 - player.height;
                    velYRebote *= -0.9f;
                }

                if (player.y <= 20) {
                    player.y = 20;
                    currentState = Peppino.State.RECUPERANDOSE_SUELO;
                    stateTimer = 0f;
                }
                break;

            case RECUPERANDOSE_SUELO:

                if (recuperandoAnimation.isAnimationFinished(stateTimer)) {
                    currentState = Peppino.State.IDLE;
                    invencible = false;
                }
                break;

            case DASHING:

                float desplazamientoX = dashVelX * delta;
                player.x += dashHaciaDerecha ? desplazamientoX : -desplazamientoX;

                if (player.x < 0) player.x = 0;
                if (player.x > 800 - player.width) player.x = 800 - player.width;

                boolean quiereCancelar = (dashHaciaDerecha && Gdx.input.isKeyPressed(this.keyIzquierda)) ||
                    (!dashHaciaDerecha && Gdx.input.isKeyPressed(this.keyDerecha));

                boolean animacionDashTerminada = dashAnimation.isAnimationFinished(stateTimer);

                if (quiereCancelar)
                    currentState = Peppino.State.MOVING;

                else if(animacionDashTerminada)
                    currentState = Peppino.State.IDLE;

                break;

            case IDLE:
            case MOVING:
            default:

                if (Gdx.input.isKeyJustPressed(this.keyDash)) {
                    currentState = Peppino.State.DASHING;
                    dashHaciaDerecha = facingRight;
                    dashSound.play();
                    stateTimer = 0f;
                }

                else {
                    if (Gdx.input.isKeyPressed(this.keyIzquierda)) {
                        player.x -= velx * delta;
                        currentState = Peppino.State.MOVING;
                        facingRight = false;
                    }

                    else if (Gdx.input.isKeyPressed(this.keyDerecha)) {
                        player.x += velx * delta;
                        currentState = Peppino.State.MOVING;
                        facingRight = true;
                    }

                    else {
                        if (previousState == Peppino.State.MOVING || previousState == Peppino.State.IDLE)
                            currentState = Peppino.State.IDLE;
                    }

                    if (player.x < 0)
                        player.x = 0;

                    if (player.x > 800 - player.width)
                        player.x = 800 - player.width;
                }
                break;
        }
    }

    @Override
    public void destruir(){ }

    private Animation<TextureRegion> createAnimationFromSheet(Texture sheet, int frameCount, int frameWidth, int frameHeight, float frameDuration) {

        TextureRegion[][] tmp = TextureRegion.split(sheet, frameWidth, frameHeight);

        TextureRegion[] frames = new TextureRegion[frameCount];

        int index = 0;

        for (int j = 0; j < frameCount; j++)

            frames[index++] = tmp[0][j];

        Animation<TextureRegion> animation = new Animation<TextureRegion>(frameDuration, frames);

        animation.setPlayMode(Animation.PlayMode.LOOP);
        if (sheet == sheetRecuperando) {
            animation.setPlayMode(Animation.PlayMode.NORMAL);
        }

        return animation;

    }

}

