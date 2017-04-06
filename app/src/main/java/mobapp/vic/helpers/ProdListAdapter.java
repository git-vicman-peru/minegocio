package mobapp.vic.helpers;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.ProdForList;
import mobapp.vic.models.ProdItem;
import mobapp.vic.models.ProdItemBasic;

/**
 * Created by VicDeveloper on 2/22/2017.
 */

public class ProdListAdapter extends ArrayAdapter<ProdForList> {
    final private List<ProdForList> list;
    private final Activity context;
    int nitems;
    boolean impar;
    public ProductTileListener prodListener;

    public ProdListAdapter(Activity ctx, List<ProdForList> lst){
        super(ctx, R.layout.lyitemlist_catalog,lst);
        this.context = ctx;
        this.list = lst;
        this.impar = false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vw = convertView;
        if (convertView == null){
            LayoutInflater inflator = this.context.getLayoutInflater();
            vw = inflator.inflate(R.layout.lyitemlist_catalog,null);
        }
        ProdForList pi = this.getItem(position);
        ImageView imvA = (ImageView)vw.findViewById(R.id.imgOne);
        imvA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prodListener != null){
                    ProdItemBasic p = (ProdItemBasic)view.getTag();
                    //if (p.getImg()== null)
                      //  Log.d("image A","is null");
                    prodListener.onProdTileClick(p);
                }
            }
        });
        imvA.setImageBitmap(pi.getProdA().getImg());
        imvA.setTag(pi.getProdA());
        TextView tvA = (TextView)vw.findViewById(R.id.txOne);
        tvA.setText(pi.getProdA().getNombre());

        ImageView imvB = (ImageView)vw.findViewById(R.id.imgTwo);
        imvB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prodListener != null){
                    ProdItemBasic p = (ProdItemBasic)view.getTag();
                    if (p.getImg()== null)
                        Log.d("image B","is null");
                    prodListener.onProdTileClick(p);
                }
            }
        });
        TextView tvB = (TextView)vw.findViewById(R.id.txTwo);
        tvB.setVisibility(View.VISIBLE);
        imvB.setVisibility(View.VISIBLE);
        if (pi.isEsimpar()){
            tvB.setVisibility(View.INVISIBLE);
            imvB.setVisibility(View.INVISIBLE);
        }else{
            imvB.setImageBitmap(pi.getProdB().getImg());
            imvB.setTag(pi.getProdB());
            tvB.setText(pi.getProdB().getNombre());
        }
        vw.setTag(pi);

        return vw;
    }


    public interface ProductTileListener{
        void onProdTileClick(ProdItemBasic qproduct);
    }
}
