package mobapp.vic.models;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by VicDeveloper on 3/14/2017.
 */

public class FavoritoLVSubitem {
    private int id_prod;
    private String nombre;
    private String descripcioncorta;
    private String descripcionlarga;
    private Date fecha;
    private String estado;
    private Bitmap img;

    private Producto prodref;

    public FavoritoLVSubitem(){

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Producto getProdref() {
        return prodref;
    }

    public void setProdref(Producto prodref) {
        this.prodref = prodref;
    }
}
