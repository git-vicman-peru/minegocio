package mobapp.vic.helpers;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import mobapp.vic.minegocio.R;
import mobapp.vic.minegocio.catalog_frag;
import mobapp.vic.models.ProdItemBasic;
import mobapp.vic.utils.AppVars;

/**
 * Created by VicDeveloper on 3/12/2017.
 */

public class BolsaFrag extends Fragment {
    private SwipeMenuListView lvBolsa;
    private TextView lbtotal;
    private PedListAdapter laPed;

    public BolsaFrag(){
        AppVars.setComingFrom("bag");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.bolsa_fragly,container,false);
        this.lvBolsa = (SwipeMenuListView)vw.findViewById(R.id.lvBolsaItems);
        this.lbtotal = (TextView)vw.findViewById(R.id.lbBolsaTotal);
        return vw;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button bnpedir = (Button)view.findViewById(R.id.bnBolsaPedir);
        bnpedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clearFragments(false,"regclie");
                PedidoSend1Frag frag = new PedidoSend1Frag();
                getFragmentManager().beginTransaction()
                        .addToBackStack("pedidosend1bk")
                        .add(R.id.base_fragholder,frag,"pedidosend1")
                        .commit();
                //ServHandler svr = new ServHandler();
                //svr.sendPedido();
            }
        });

        Button bnback = (Button)view.findViewById(R.id.bnBolsaBack);
        bnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catalog_frag frag = (catalog_frag)getFragmentManager().findFragmentByTag("catalog");
                if (frag != null) {
                    frag.bagUpdate();
                }

                getFragmentManager().popBackStack();
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator(){
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,0x3F, 0x25)));
                deleteItem.setWidth(90);
                deleteItem.setIcon(R.drawable.ic_delete_forever_black_24dp);
                menu.addMenuItem(deleteItem);
            }
        };
        lvBolsa.setMenuCreator(creator);
        lvBolsa.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch(index){
                    case 0:
                        ProdItemBasic prod = laPed.getItem(position);
                        laPed.remove(prod);
                        //AppVars.bolsa_remove(position);
                        lbtotal.setText("Total: "+AppVars.bolsa_getSubtotalStr());
                        break;
                    default:
                }
                return false;
            }
        });

        laPed = new PedListAdapter(getActivity(), AppVars.bolsa_getitems());
        lvBolsa.setAdapter(laPed);
        lbtotal.setText("Total: "+AppVars.bolsa_getSubtotalStr());

    }

    public static BolsaFrag newInstance(){
        BolsaFrag f = new BolsaFrag();
        return f;
    }
}
