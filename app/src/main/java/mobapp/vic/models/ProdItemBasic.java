package mobapp.vic.models;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by VicDeveloper on 2/22/2017.
 */

public class ProdItemBasic {
    private int id_prod;
    private float cantidad;
    private String nombre;
    private String descripcioncorta;
    private String descripcionlarga;
    private PrecioItem precioref;
    private String foto;

    private Bitmap img;

    public ProdItemBasic(){

    }


    public int getId_prod() {
        return id_prod;
    }

    public ProdItemBasic(String nombre, String descripcioncorta) {
        this.nombre = nombre;
        this.descripcioncorta = descripcioncorta;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreRef() {
        return ((nombre==null)?"nulo":nombre);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcioncorta() {
        return descripcioncorta;
    }

    public void setDescripcioncorta(String descripcioncorta) {
        this.descripcioncorta = descripcioncorta;
    }

    public String getDescripcionlarga() {
        return descripcionlarga;
    }

    public void setDescripcionlarga(String descripcionlarga) {
        this.descripcionlarga = descripcionlarga;
    }

    public PrecioItem getPrecioref() {
        return precioref;
    }

    public void setPrecioref(PrecioItem precioref) {
        this.precioref = precioref;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public boolean isImgNull(){
        return (this.img == null);
    }

    public String getPriceTip(){
        String s = this.precioref.getPrecio()+"";
        String[] a;
        String pp = "";
        a = s.split("\\.");
        if (a.length==0){
            pp = s;

        }else{
            if (a[1].substring(0,1).equals("0")){
                pp = a[0];
            }else{
                pp = String.format("%.2f",this.precioref.getPrecio());
            }
        }
        a = null;
        return this.precioref.getMoneda() + " " + pp + " " + this.precioref.getUnidad();
    }

    public float getSubtotal(){
        return this.cantidad*this.getPrecioref().getPrecio();
    }

    public String getSubtotalStr(){
        return String.format("%.2f",this.cantidad*this.getPrecioref().getPrecio());
    }

    @Override
    public String toString() {
        return "ProdItemBasic{" +
                "id_prod=" + id_prod +
                ", cantidad=" + cantidad +
                ", nombre='" + nombre + '\'' +
                ", descripcioncorta='" + descripcioncorta + '\'' +
                ", descripcionlarga='" + descripcionlarga + '\'' +
                ", precioref=" + precioref +
                '}';
    }
}
