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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.List;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.Pedido;
import mobapp.vic.models.PedidoFix;
import mobapp.vic.models.ProdItemBasic;
import mobapp.vic.models.ResultItem;
import mobapp.vic.services.ServHandler;
import mobapp.vic.utils.AppVars;
import mobapp.vic.utils.U;

/**
 * Created by VicDeveloper on 3/10/2017.
 */

public class PedidoSend2Frag extends Fragment {
    private SwipeMenuListView lvitems;
    private PedListAdapter laped;
    private TextView lbtotal;
    private ProgressBar pbar;
    private boolean flgSent;

    public PedidoSend2Frag(){
        flgSent = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.pedido_send2_fragly,container,false);
        laped = new PedListAdapter(getActivity(), AppVars.bolsa_getitems());
        lbtotal = (TextView)vw.findViewById(R.id.lbPedSend2Total);
        pbar = (ProgressBar)vw.findViewById(R.id.pbPedSend2PBar);
        return vw;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        lvitems = (SwipeMenuListView)view.findViewById(R.id.lvPedResItems);
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
        lvitems.setMenuCreator(creator);
        lvitems.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch(index){
                    case 0:
                        ProdItemBasic prod = laped.getItem(position);
                        laped.remove(prod);
                        lbtotal.setText("Total: "+AppVars.bolsa_getSubtotalStr());
                        break;
                    default:
                }
                return false;
            }
        });
        lvitems.setAdapter(laped);
        lbtotal.setText("Total: "+AppVars.bolsa_getSubtotalStr());
        Button prev = (Button)view.findViewById(R.id.bnPedSend2Prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        Button enviar = (Button)view.findViewById(R.id.bnPedSend2Enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultItem r = hasErrors();
                if (r.getFlag()){
                    U.pushDialogPopup(getFragmentManager(),r.getMessage(),"beforesendped");
                }else{
                    if (flgSent) return;
                    doEnvio();
                    flgSent = true;
                }
            }
        });
    }

    private ResultItem hasErrors(){
        ResultItem r = new ResultItem();
        r.setFlag(false);
        if (laped.getCount()<1){
            r.setFlag(true);
            r.setMessage("La bolsa no tiene productos!");
            return r;
        }
        return r;
    }

    private void doEnvio(){
        pbar.setVisibility(View.VISIBLE);
        ServHandler svr = new ServHandler();
        AppVars.Pedido_SetMonto(AppVars.bolsa_getSubtotal());
        AppVars.Pedido_SetDetalleFromBag();
        svr.sendPedido(AppVars.order, new ServHandler.PedidoListener() {
            @Override
            public void onPedidoSuccess(boolean flag) {
                String smsg;
                if (flag){
                    smsg = "Su pedido ha sido enviado, correctamente. En breve, recibira un correo de confirmacion. Gracias.";
                    U.pushOkDialog(getFragmentManager(), smsg, "enviopedidodlg", new DialogOkFrag.IDialogOkListener() {
                        @Override
                        public void onOkPressed() {
                            flgSent = false;
                            AppVars.bolsa_reset();
                            getFragmentManager().popBackStack("catalogbk",0);
                        }
                    });
                }else{
                    smsg = "Su pedido no se ha enviado satisfactoriamente. Revise e intente otra vez.";
                    U.pushOkDialog(getFragmentManager(), smsg, "enviopedidodlg", new DialogOkFrag.IDialogOkListener() {
                        @Override
                        public void onOkPressed() {
                            getFragmentManager().popBackStack();
                            flgSent = false;
                        }
                    });
                }
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPedidoFailure(boolean flag, String message) {
                String smsg = "Su pedido no se ha enviado satisfactoriamente. Revise e intente otra vez.";
                U.pushOkDialog(getFragmentManager(), smsg, "enviopedidodlg", new DialogOkFrag.IDialogOkListener() {
                    @Override
                    public void onOkPressed() {
                        pbar.setVisibility(View.INVISIBLE);
                        getFragmentManager().popBackStack();
                    }
                });
            }

            @Override
            public void onPedidoListReceived(List<PedidoFix> src) {

            }
        });

    }
}
