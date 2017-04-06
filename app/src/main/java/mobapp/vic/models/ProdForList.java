package mobapp.vic.models;

/**
 * Created by VicDeveloper on 2/22/2017.
 */

public class ProdForList {
    private boolean esimpar;
    private ProdItemBasic prodA;
    private ProdItemBasic prodB;

    public ProdForList(){

    }

    public boolean isEsimpar() {
        return esimpar;
    }

    public void setEsimpar(boolean esimpar) {
        this.esimpar = esimpar;
    }

    public ProdItemBasic getProdA() {
        return prodA;
    }

    public void setProdA(ProdItemBasic prodA) {
        this.prodA = prodA;
    }

    public ProdItemBasic getProdB() {
        return prodB;
    }

    public void setProdB(ProdItemBasic prodB) {
        this.prodB = prodB;
    }
}
