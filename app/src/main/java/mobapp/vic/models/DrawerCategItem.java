package mobapp.vic.models;

/**
 * Created by VicDeveloper on 3/1/2017.
 */

public class DrawerCategItem {
    //0: Default or Mostrar
    //1: Categoria con etiqueta
    private int type;
    private String symbol;
    private String name;
    private String descrip;

    public DrawerCategItem(){

    }

    public DrawerCategItem(String descrip, String name, String symbol, int type) {
        this.descrip = descrip;
        this.name = name;
        this.symbol = symbol;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
