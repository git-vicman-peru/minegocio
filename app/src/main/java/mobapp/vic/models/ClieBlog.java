package mobapp.vic.models;

/**
 * Created by VicDeveloper on 3/14/2017.
 */

public class ClieBlog {
    private int id_clieb;

    private int id_clie;

    private int id_emp;

    private int id_prod;

    private String nivel;

    private String comentario;

    private String rate;

    private String fecha;


    public ClieBlog(){

    }

    public int getId_clieb() {
        return id_clieb;
    }

    public void setId_clieb(int id_clieb) {
        this.id_clieb = id_clieb;
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

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "ClieBlog{" +
                "comentario='" + comentario + '\'' +
                ", id_clieb=" + id_clieb +
                ", id_clie=" + id_clie +
                ", id_emp=" + id_emp +
                ", id_prod=" + id_prod +
                ", nivel='" + nivel + '\'' +
                ", rate='" + rate + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
