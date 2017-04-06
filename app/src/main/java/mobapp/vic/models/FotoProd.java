package mobapp.vic.models;

/**
 * Created by VicDeveloper on 2/22/2017.
 */

public class FotoProd {
    private int id_fot;

    private int id_emp;

    private int id_prod;

    private String nombre;

    private String tipo;

    private String src64;


    public FotoProd(){

    }

    public FotoProd(String ssdef){
        this.setDef(ssdef);
    }

    public int getId_fot() {
        return id_fot;
    }

    public void setId_fot(int id_fot) {
        this.id_fot = id_fot;
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getSrc64() {
        return src64;
    }

    public void setSrc64(String src64) {
        this.src64 = src64;
    }

    @Override
    public String toString() {
        return "FotoProd [id_fot=" + id_fot + ", id_emp=" + id_emp + ", nombre=" + nombre + ", tipo=" + tipo + ",";// foto="
        //+ foto + "]";
    }

    public String getDef(){
        return this.id_fot+";"+this.id_emp+";"+this.nombre+";"+this.tipo;
    }

    public void setDef(String ssdef){
        String[] a = ssdef.split(";");
        this.id_fot = Integer.parseInt(a[0]);
        this.id_emp = Integer.parseInt(a[1]);
        this.nombre = a[2];
        this.tipo = a[3];
    }
}
