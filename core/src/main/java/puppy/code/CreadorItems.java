package puppy.code;

public class CreadorItems {

    private GameLluvia.CharacterChoice personajeActual;
    private GestorAssets assets;

    //Strategy
    private IEstrategiaGeneracion estrategia;

    public CreadorItems(GameLluvia.CharacterChoice personaje, IEstrategiaGeneracion estrategiaInicial) {
        this.personajeActual = personaje;
        this.assets = GestorAssets.getInstance();
        this.estrategia = estrategiaInicial;
    }


    public void setEstrategia(IEstrategiaGeneracion estrategia) {
        this.estrategia = estrategia;
    }

    public ItemCaido crearItemAleatorio() {
        return estrategia.generar(assets, personajeActual);
    }
}
