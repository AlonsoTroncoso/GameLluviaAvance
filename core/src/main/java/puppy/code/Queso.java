package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;


public class Queso extends ItemComida {


    public Queso(Texture sheet, Sound comerSound) {
        super(sheet, 16, 32, 32, 0.05f, 300f, comerSound);
    }

}
