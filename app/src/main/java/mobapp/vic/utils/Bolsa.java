package mobapp.vic.utils;

import java.util.ArrayList;
import java.util.List;

import mobapp.vic.models.PedidoDet;
import mobapp.vic.models.ProdItemBasic;

/**
 * Created by VicDeveloper on 3/9/2017.
 */

public class Bolsa {
    private List<ProdItemBasic> items;

    public Bolsa(){
        this.items = new ArrayList<ProdItemBasic>();
    }

    public void clean(){
        this.items.clear();
    }

    public void add(ProdItemBasic qitem){
        this.items.add(qitem);
    }

    public float getTotal(){
        float t = 0;
        for (int i=0; i<this.items.size(); i++){
            t += this.items.get(i).getSubtotal();
        }
        return t;
    }

    public String getTotalStr(){
        return String.format("%.2f",this.getTotal());
    }

    public List<ProdItemBasic> getList(){
        return this.items;
    }

    public void remove(int itemIndex){
        this.items.remove(itemIndex);
    }

    public int indexOf(int prodId){
        int res = -1;
        for(int i=0; i<this.items.size(); i++){
            if (this.items.get(i).getId_prod()==prodId){
                res = i;
                break;
            }
        }
        return res;
    }

    public void removeById(int nprodId){
        int i = indexOf(nprodId);
        if (i > -1) this.items.remove(i);
    }

    public List<PedidoDet> ToPedidoDetList(){
        List<PedidoDet> l = new ArrayList<PedidoDet>();
        PedidoDet p;
        for(ProdItemBasic b : items){
            p = new PedidoDet();
            p.setId_prod(b.getId_prod());
            p.setCantidad(b.getCantidad());
            p.setUnidad(b.getPrecioref().getUnidad());
            p.setDescripcion(b.getDescripcioncorta());
            p.setPrecio(b.getPrecioref().getPrecio());
            //p.setStatus("");
            l.add(p);
        }
        return l;
    }
}
