package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

//se simplifica harto cada clase
public class Tomate extends ItemComida {

    public Tomate(Texture sheet, Sound comerSound) {
        super(sheet, 10, 32, 32, 0.05f, 300f, comerSound);
    }
}
