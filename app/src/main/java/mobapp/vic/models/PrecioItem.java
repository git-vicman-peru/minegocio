package mobapp.vic.models;

/**
 * Created by VicDeveloper on 3/6/2017.
 */

public class PrecioItem {
    private String moneda;
    private String unidad;
    private String descrip;
    private float precio;

    public PrecioItem(){

        this.moneda = "S/.";
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @Override
    public String toString() {
        return "PrecioItem{" +
                "descrip='" + descrip + '\'' +
                ", unidad='" + unidad + '\'' +
                ", precio=" + precio +
                '}';
    }
}
