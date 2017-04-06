package mobapp.vic.helpers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.ProdItem;
import mobapp.vic.models.ProdItemBasic;
import mobapp.vic.utils.U;

/**
 * Created by VicDeveloper on 3/9/2017.
 */

public class PedListAdapter extends ArrayAdapter<ProdItemBasic> {
    final private List<ProdItemBasic> list;
    final private Activity context;

    public PedListAdapter(Activity ctx, List<ProdItemBasic> lst){
        super(ctx, R.layout.pedidoitem_ly, lst);
        this.list = lst;
        this.context = ctx;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vw = convertView;
        if (convertView == null){
            LayoutInflater inf = this.context.getLayoutInflater();
            vw = inf.inflate(R.layout.pedidoitem_ly,null);
        }
        ProdItemBasic p = this.getItem(position);
        TextView lbnum = (TextView)vw.findViewById(R.id.lbPedItemNum);
        lbnum.setText((position+1)+")");
        ImageView ivfot = (ImageView)vw.findViewById(R.id.ivPedItemFoto);
        ivfot.setImageBitmap(p.getImg());
        TextView lbcant = (TextView)vw.findViewById(R.id.lbPedItemCantidad);
        String scant = U.rndZero(p.getCantidad()) + " " + p.getPrecioref().getUnidad();
        lbcant.setText(scant);
        TextView lbdescrip = (TextView)vw.findViewById(R.id.lbPedItemDescrip);
        lbdescrip.setText(p.getDescripcioncorta());
        TextView lbprecio = (TextView)vw.findViewById(R.id.lbPedItemPrecio);
        lbprecio.setText(p.getPrecioref().getPrecio()+"");
        TextView lbsubt = (TextView)vw.findViewById(R.id.lbPedItemSubtotal);
        lbsubt.setText(p.getSubtotalStr());
        return vw;
    }
}
