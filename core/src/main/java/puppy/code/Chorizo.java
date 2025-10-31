package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;


public class Chorizo extends ItemComida {

    public Chorizo(Texture sheet, Sound comerSound) {
        super(sheet, 12, 32, 32, 0.05f, 300f, comerSound);
    }
}
