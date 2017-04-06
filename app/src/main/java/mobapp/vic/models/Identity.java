package mobapp.vic.models;

/**
 * Created by VicDeveloper on 2/22/2017.
 */

public class Identity {
    private int empid;
    private String apiurl;
    private Cliente clie;

    public Identity(){

    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getApiurl() {
        return apiurl;
    }

    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }

    public Cliente getClie() {
        return clie;
    }

    public void setClie(Cliente clie) {
        this.clie = clie;
    }
}
