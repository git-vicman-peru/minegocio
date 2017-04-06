package mobapp.vic.models;

/**
 * Created by VicDeveloper on 3/10/2017.
 */

public class PedidoDet {
    private int id_pdet;

    private int id_ped;

    private int id_prod;

    private float cantidad;

    private String unidad;

    private String descripcion;

    private float precio;

    private String status;

    public PedidoDet(){

    }

    public int getId_pdet() {
        return id_pdet;
    }

    public void setId_pdet(int id_pdet) {
        this.id_pdet = id_pdet;
    }

    public int getId_ped() {
        return id_ped;
    }

    public void setId_ped(int id_ped) {
        this.id_ped = id_ped;
    }

    public int getId_prod() {
        return id_prod;
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

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getSubtotal(){
        return this.cantidad*this.getPrecio();
    }

    public String getSubtotalStr(){
        return String.format("%.2f",this.cantidad*this.getPrecio());
    }

    @Override
    public String toString() {
        return "PedidoDet [id_pdet=" + id_pdet + ", id_ped=" + id_ped + ", id_prod=" + id_prod + ", cantidad="
                + cantidad + ", unidad=" + unidad + ", descripcion=" + descripcion + ", precio=" + precio + ", status="
                + status + "]";
    }

}
