package mobapp.vic.models;

/**
 * Created by VicDeveloper on 2/3/2017.
 */

public class Usuario {
    private int id_acc;
    private String usuario;
    private String clave;
    private boolean vigente;

    public Usuario(){

    }

    public Usuario(String uname, String uclave){
        this.usuario = uname;
        this.clave = uclave;
    }

    public int getId_acc() {
        return id_acc;
    }

    public void setId_acc(int id_acc) {
        this.id_acc = id_acc;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_acc=" + id_acc +
                ", usuario='" + usuario + '\'' +
                ", clave='" + clave + '\'' +
                ", vigente=" + vigente +
                '}';
    }
}
