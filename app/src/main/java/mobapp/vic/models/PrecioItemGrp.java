package mobapp.vic.models;

import java.util.ArrayList;
import java.util.List;

import mobapp.vic.utils.U;

/**
 * Created by VicDeveloper on 3/6/2017.
 */

public class PrecioItemGrp {
    private List<PrecioItem> items;

    public PrecioItemGrp(){
        items = new ArrayList<PrecioItem>();
    }

    public void parseFrom(String ssdef){
        String[] a = ssdef.split("\\?");
        String[] b;
        int nt = a.length;
        PrecioItem p;
        for(int i=0; i<nt; i++){
            b = a[i].split(":");
            p = new PrecioItem();
            p.setUnidad(b[0]);
            p.setDescrip(b[1]);
            p.setPrecio(U.tryFloat(b[2],0));
            b = null;
            this.items.add(p);
            p = null;
        }
        a = null;
    }

    public PrecioItem get(int nindex){
        return this.items.get(nindex);
    }
}
