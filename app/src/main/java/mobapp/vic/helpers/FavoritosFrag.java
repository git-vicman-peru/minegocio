package mobapp.vic.helpers;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import java.util.List;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.Favorito;
import mobapp.vic.models.FavoritoLVGroup;
import mobapp.vic.models.ProdItemBasic;
import mobapp.vic.models.Producto;
import mobapp.vic.services.ServHandler;
import mobapp.vic.utils.AppVars;

/**
 * Created by VicDeveloper on 3/14/2017.
 */

public class FavoritosFrag extends Fragment {

    private ExpandableListView elv;
    private FavListAdapter adp;
    private ServHandler srv;

    public FavoritosFrag(){
        adp = new FavListAdapter();
        srv = new ServHandler();
        AppVars.openDba();
        String ids = AppVars.dba.Fav_GetProdIds(AppVars.EmpId,AppVars.logginUser.getId_clie());
        //Log.d("ids",ids);
        List<Favorito> lfav = AppVars.dba.Fav_Read(AppVars.EmpId,AppVars.logginUser.getId_clie());
        //Log.d("lfav",lfav.size()+"");
        AppVars.closeDba();
        srv.Favorito_Load(ids, lfav, new ServHandler.FavoritoListener() {
            @Override
            public void onFavoritoLoaded(FavoritoLVGroup favs) {
                //Log.d("favs-ws",favs.count()+"");
                adp.setData(favs);
                adp.notifyDataSetChanged();
            }

            @Override
            public void onFavoritoFailed() {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        adp.setContext(getActivity());

        View vw = inflater.inflate(R.layout.favoritos_fragly,container,false);
        elv = (ExpandableListView)vw.findViewById(R.id.elvFav);

        elv.setAdapter(adp);
        return vw;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adp.notifyDataSetChanged();
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long l) {
                Producto p = (Producto)view.getTag();
                ShowProductDetails(p);
                return true;
            }
        });
    }

    private void ShowProductDetails(Producto qproduct){
        CatalogDetailItemFrag frag = new CatalogDetailItemFrag();
        frag.setProduct(qproduct.getProdItemBasic());
        getFragmentManager().beginTransaction()
                .add(R.id.base_fragholder,frag,"detail")
                .addToBackStack("detailbk")
                .commit();
        //.setCustomAnimations(R.animator.card_flip_right_in,R.animator.card_flip_right_out,R.animator.card_flip_left_in,R.animator.card_flip_left_out)
    }

}
