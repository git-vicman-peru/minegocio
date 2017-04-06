package mobapp.vic.helpers;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.Pedido;
import mobapp.vic.models.PedidoFix;
import mobapp.vic.models.ProdItemBasic;
import mobapp.vic.services.ServHandler;
import mobapp.vic.utils.AppVars;

/**
 * Created by VicDeveloper on 3/9/2017.
 */

public class PedidosFrag extends Fragment {
    private ExpandableListView lvPeds;
    private PedidoResListAdapter adapter;

    private ServHandler srv;
    private ProgressBar pbar;

    public PedidosFrag(){
        srv = new ServHandler();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.pedidos_fragly,container,false);
        lvPeds = (ExpandableListView) vw.findViewById(R.id.elvMisPedidos);
        pbar = (ProgressBar)vw.findViewById(R.id.pbMPedPBar);
        pbar.setVisibility(View.VISIBLE);
        srv.Pedidos_Load(AppVars.EmpId, AppVars.logginUser.getId_clie(), new ServHandler.PedidoListener() {
            @Override
            public void onPedidoListReceived(List<PedidoFix> src) {
                adapter = new PedidoResListAdapter(getActivity(),src);
                lvPeds.setAdapter(adapter);
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPedidoSuccess(boolean flag) {
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPedidoFailure(boolean flag, String message) {
                pbar.setVisibility(View.INVISIBLE);
            }
        });

        return vw;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //adapter.notifyDataSetChanged();

    }


}
