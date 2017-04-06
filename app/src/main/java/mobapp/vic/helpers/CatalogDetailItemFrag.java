package mobapp.vic.helpers;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import mobapp.vic.minegocio.R;
import mobapp.vic.minegocio.catalog_frag;
import mobapp.vic.models.Favorito;
import mobapp.vic.models.ProdItemBasic;
import mobapp.vic.utils.AppVars;
import mobapp.vic.utils.U;

/**
 * Created by VicDeveloper on 3/2/2017.
 */

public class CatalogDetailItemFrag extends Fragment {
    private ProdItemBasic producto;
    private ImageView imFoto;
    private TextView txNombre,txDescrip,txPrecio;
    //private LinearLayout bnCanasta;
    private EditText txCantidad;
    private Button bnBlog, bnBlogPeople, bnFavorito;

    public CatalogDetailItemFrag(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.catalog_details_dlgly,container,false);
        this.imFoto = (ImageView)vw.findViewById(R.id.imgItemDetailFoto);
        this.txNombre = (TextView)vw.findViewById(R.id.txItemDetailName);
        this.txDescrip = (TextView)vw.findViewById(R.id.txItemDetailDescrip);
        this.txPrecio = (TextView)vw.findViewById(R.id.txItemDetailPrecio);

        this.txCantidad = (EditText)vw.findViewById(R.id.txItemDetCantidad);
        this.txCantidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                U.showSoftKeyboard(getActivity(),view);
            }
        });
        LinearLayout bnCanasta = (LinearLayout)vw.findViewById(R.id.bnItemDetCanasta);
        bnCanasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppVars.existLoginUser()){
                    U.pushDialogPopup(getFragmentManager(),"Debe iniciar sesion!","pdialog");
                }else {
                    if (!U.editTextHasNumber(txCantidad)) {
                        return;
                    }
                    producto.setCantidad(U.editTextGetDecimal(txCantidad));
                    AppVars.bolsa_add(producto);
                    try {
                        catalog_frag frag = (catalog_frag) getFragmentManager().findFragmentByTag("catalog");
                        frag.bagUpdate();
                    }catch (Exception ex){}
                    getFragmentManager().popBackStack();
                }
            }
        });
        this.bnBlog = (Button)vw.findViewById(R.id.bnItemDetBlog);
        this.bnBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppVars.existLoginUser()){
                    U.pushDialogPopup(getFragmentManager(),"Para comentar, debe iniciar sesion!","pdialog");
                }else{
                    //Log.d("prodid",producto.getId_prod()+"");
                    U.pushToManager(getFragmentManager(),BlogItFrag.newInstance(producto),"blogit");
                }
            }
        });
        this.bnBlogPeople = (Button)vw.findViewById(R.id.bnItemDetBlogPeople);
        this.bnBlogPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppVars.existLoginUser()){
                    U.pushDialogPopup(getFragmentManager(),"Debe iniciar sesion!","pdialog");
                }else{
                    U.pushToManager(getFragmentManager(),ClieBlogRevFrag.newInstance(AppVars.EmpId,producto.getId_prod()),"clieblogs");
                }
            }
        });
        this.bnFavorito = (Button)vw.findViewById(R.id.bnItemDetFavorito);
        this.bnFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppVars.existLoginUser()){
                    U.pushDialogPopup(getFragmentManager(),"Para guardar en Favoritos, debe iniciar sesion!","pdialog");
                }else {
                    String s = "Desea agregar: " + producto.getNombre() + ", a Mis Favoritos";
                    U.pushYesNoDialog(getFragmentManager(), s, "yndialogfav", new DialogFrag.IDialogListener() {
                        @Override
                        public void dlgAnswer(boolean byes) {
                            if (byes) {
                                Favorito fav = new Favorito();
                                fav.setId_emp(AppVars.EmpId);
                                fav.setId_clie(AppVars.logginUser.getId_clie());
                                fav.setId_prod(producto.getId_prod());
                                fav.setFecha(U.nowStrMillis());
                                AppVars.openDba();
                                AppVars.dba.Fav_InsertCheckFirst(fav);
                                AppVars.closeDba();
                            }
                        }
                    });
                }
            }
        });
        Button bnback = (Button)vw.findViewById(R.id.bnItemDetBack);
        bnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    catalog_frag frag = (catalog_frag) getFragmentManager().findFragmentByTag("catalog");
                    frag.bagUpdate();
                }catch (Exception ex){}
                getFragmentManager().popBackStack();
            }
        });

        return vw;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            this.imFoto.setImageBitmap(this.producto.getImg());
            this.txNombre.setText(this.producto.getNombre());
            this.txDescrip.setText(this.producto.getDescripcionlarga());
            this.txPrecio.setText(this.producto.getPriceTip());
            //Log.d("producto elegido",producto.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void setProduct(ProdItemBasic qprod){
        this.producto = qprod;
        /*

        */
    }
}
