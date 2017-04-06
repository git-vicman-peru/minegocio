package mobapp.vic.models;

/**
 * Created by VicDeveloper on 3/15/2017.
 */

public class ClieBlogRev {
    private int id_clieb;
    private int id_clie;
    private String blogger;
    private String avatar;
    private String fecha;
    private String nivel;
    private String comentario;
    private String rating;

    public ClieBlogRev(){

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

    public String getBlogger() {
        return blogger;
    }

    public void setBlogger(String blogger) {
        this.blogger = blogger;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ClieBlogRev{" +
                "id_clieb=" + id_clieb +
                ", id_clie=" + id_clie +
                ", blogger='" + blogger + '\'' +
                ", fecha='" + fecha + '\'' +
                ", nivel='" + nivel + '\'' +
                ", comentario='" + comentario + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
