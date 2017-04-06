package mobapp.vic.models;

import java.util.Date;

/**
 * Created by VicDeveloper on 2/22/2017.
 */

public class Cliente {
    private int id_clie;

    private int id_emp;

    private String nombres;

    private String alias;

    private String apellidos;

    private String fechanac;

    private String direccion;

    private String zona;

    private String ciudad;

    private String dni;

    private String sexo;

    private String ecivil;

    private String ruc;

    private String email;

    private String url;

    private String facebook;

    private String fonos;

    private String tipo;

    private String clave;

    private String obs;

    private String foto;

    private String foto64;

    public Cliente(){

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

    public String getNombres() {
        return nombres;
    }

    public String getNombres(String defaultIfNull) {
        return (((nombres=="")||(nombres==null))?defaultIfNull:this.nombres);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getFechanac() {
        return fechanac;
    }

    /*
    public Date getFechanac(Date defaultIfNull){
        return ((this.fechanac != null)?this.fechanac:defaultIfNull);
    }
    */
    public void setFechanac(String fechanac) {
        this.fechanac = fechanac;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEcivil() {
        return ecivil;
    }

    public void setEcivil(String ecivil) {
        this.ecivil = ecivil;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }


    public String getFonos() {
        return fonos;
    }

    public void setFonos(String fonos) {
        this.fonos = fonos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto64() {
        return foto64;
    }

    public void setFoto64(String foto64) {
        this.foto64 = foto64;
    }

    public String getShortRef(){
        return this.apellidos + ", " + this.nombres;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "alias='" + alias + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", clave='" + clave + '\'' +
                ", direccion='" + direccion + '\'' +
                ", dni='" + dni + '\'' +
                ", ecivil='" + ecivil + '\'' +
                ", email='" + email + '\'' +
                ", facebook='" + facebook + '\'' +
                ", fechanac='" + fechanac + '\'' +
                ", fonos='" + fonos + '\'' +
                ", foto='" + foto + '\'' +
                ", id_clie=" + id_clie +
                ", id_emp=" + id_emp +
                ", nombres='" + nombres + '\'' +
                ", obs='" + obs + '\'' +
                ", ruc='" + ruc + '\'' +
                ", sexo='" + sexo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", url='" + url + '\'' +
                ", zona='" + zona + '\'' +
                '}';
    }
}
