package mobapp.vic.models;

/**
 * Created by VicDeveloper on 3/14/2017.
 */

public class Favorito {
    private int id_fav;
    private int id_emp;
    private int id_clie;
    private int id_prod;
    private String fecha;

    public Favorito(){

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_clie() {
        return id_clie;
    }

    public void setId_clie(int id_clie) {
        this.id_clie = id_clie;
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    public int getId_fav() {
        return id_fav;
    }

    public void setId_fav(int id_fav) {
        this.id_fav = id_fav;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    @Override
    public String toString() {
        return "Favorito{" +
                "fecha='" + fecha + '\'' +
                ", id_fav=" + id_fav +
                ", id_emp=" + id_emp +
                ", id_clie=" + id_clie +
                ", id_prod=" + id_prod +
                '}';
    }
}
