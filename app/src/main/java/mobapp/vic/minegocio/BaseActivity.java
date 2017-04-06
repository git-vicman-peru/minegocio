package mobapp.vic.minegocio;

import android.app.Activity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mobapp.vic.helpers.BolsaFrag;
import mobapp.vic.helpers.CarouselFrag;
import mobapp.vic.helpers.CategoryListAdapter;
import mobapp.vic.helpers.DialogFrag;
import mobapp.vic.helpers.DialogPopupFrag;
import mobapp.vic.helpers.FavoritosFrag;
import mobapp.vic.helpers.LoginFrag;
import mobapp.vic.helpers.PedidosFrag;
import mobapp.vic.helpers.RegistraFrag;
import mobapp.vic.models.DrawerCategItem;
import mobapp.vic.services.ServHandler;
import mobapp.vic.utils.AppVars;
import mobapp.vic.utils.DBAdapter;
import mobapp.vic.utils.U;


public class BaseActivity extends AppCompatActivity implements LoginFrag.ILoginListener,DialogFrag.IDialogListener {

    private DrawerLayout drawer;
    //private DBAdapter dba;
    private List<DrawerCategItem> categorias;

    LoginFrag fragLogin;// = new LoginFrag();
    DialogFrag fragDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final String path = android.os.Environment.DIRECTORY_DCIM;

        setContentView(R.layout.drawer_pane);

        AppVars.dba = new DBAdapter(getApplicationContext());

        fragLogin = new LoginFrag();
        fragLogin.setListener(this);

        fragDlg = new DialogFrag();
        fragDlg.setDialogListener(this,"Desea salir de la sesion iniciada?");

        //AppVars.openDba();
        //AppVars.dba.baseUrl_update("http://192.168.12.18:8080/MiNegocioApi/");
        //dba.baseUrl_update("http://10.0.2.2:8080/MiNegocioApi/");
        //dba.baseUrl_update("http://45.55.72.7:8080/MiNegocioApi/");
        //AppVars.closeDba();
        AppVars.LoadDefaults();

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,null,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (AppVars.existLoginUser()){
                    ImageView iv = (ImageView)drawer.findViewById(R.id.imgLoginUser);
                    iv.setVisibility(View.VISIBLE);
                    String ssi = AppVars.logginUser.getFoto64();
                    if (ssi != null){
                        /*
                        ImageView ivlogo = (ImageView)drawer.findViewById(R.id.dw_imglogo);
                        LinearLayout.LayoutParams prms = new LinearLayout.LayoutParams(0,85,0.6f);
                        ivlogo.setLayoutParams(prms);
                        prms = new LinearLayout.LayoutParams(0,85,0.4f);
                        RoundedImageView ivuser = (RoundedImageView)drawer.findViewById(R.id.imgLoginUser);
                        ivuser.setLayoutParams(prms);
                        */
                        U.setBmpFromBase64(ssi,iv);
                    }
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        this.CategoryList_Start(null);
        this.AppButtons_Start();

        LinearLayout sv = (LinearLayout)findViewById(R.id.startView);
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);

            }
        });

    }

    @Override
    public void onBackPressed() {

        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
            System.exit(0);
        }
        else {
            getFragmentManager().popBackStack();

            if (AppVars.COMINGFROM.equals("bag")){
                try {
                    catalog_frag frag = (catalog_frag) getFragmentManager().findFragmentByTag("catalog");
                    if (frag != null) {
                        frag.bagUpdate();
                    }
                }catch (Exception ex){}
            }

            /*
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null){
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
            */
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppVars.dba = null;
    }

    @Override
    public void Login_Successful() {
        //Log.d("Login was"," Successful");
        setLoginIconOn();
        SoftKeyboarHide(this);
    }

    @Override
    public void Login_Failed() {
        //Log.d("Login was"," a total failure");
        setLoginIconOff();
    }

    @Override
    public void dlgAnswer(boolean byes) {
        if (byes){//Salir de la sesion
            AppVars.resetLoginUser();
            AppVars.bolsa_reset();
            ImageView iv = (ImageView)drawer.findViewById(R.id.imgLoginUser);
            iv.setVisibility(View.INVISIBLE);
            setLoginIconOff();
        }
    }

    private void SoftKeyboarHide(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
        }

        /*
        if (imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
        } else {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // show
        }
        */
    }

    private void CategoryList_Start(String[] srcList){
        ServHandler sh = new ServHandler();
        categorias = new ArrayList<DrawerCategItem>();
        ListView lv = (ListView)findViewById(R.id.dw_categorylist);
        final CategoryListAdapter catadp = new CategoryListAdapter(this,categorias);
        lv.setAdapter(catadp);
        catadp.clear();
        sh.loadGrupos(new ServHandler.CatalogGrupoListener() {
            @Override
            public void onCatalogGrupoLoaded(List<DrawerCategItem> grupos) {
                categorias = grupos;
                catadp.addAll(categorias);
                catadp.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clearFragments(true,"catalog","detail","carou");
                DrawerCategItem cat = (DrawerCategItem)view.getTag();
                catalog_frag frag = new catalog_frag();
                frag.setCatalogSearchGroup(cat.getName());
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.rotate_y_axis,0)
                        .addToBackStack("catalogbk")
                        .replace(R.id.base_fragholder,frag,"catalog")
                        .commit();
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        catadp.setCarouselListener(new CategoryListAdapter.CarouselListener() {
            @Override
            public void onCarouselItemClick(DrawerCategItem qcatItem) {
                clearFragments(true,"carou","catalog");
                CarouselFrag frag = new CarouselFrag();
                frag.setCatSearchGroup(qcatItem.getName());
                getFragmentManager().beginTransaction()
                        .addToBackStack("caroubk")
                        .replace(R.id.base_fragholder,frag,"carou")
                        .commit();
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        /*
        catadp.carouselListener = new CategoryListAdapter.CarouselListener() {
            @Override
            public void onCarouselItemClick(DrawerCategItem qcatItem) {

            }
        };
        */
    }

    private void AppButtons_Start(){

        Button bnregis = (Button)findViewById(R.id.bnRegistrarme);
        bnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFragments(false,"regclie");
                RegistraFrag frag = new RegistraFrag();
                getFragmentManager().beginTransaction()
                        .addToBackStack("regcliebk")
                        .add(R.id.base_fragholder,frag,"regclie")
                        .commit();
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        Button bnlogin = (Button)findViewById(R.id.bnLogearme);
        bnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppVars.existLoginUser()){
                    getFragmentManager().beginTransaction()
                            .addToBackStack("dlgGralbk")
                            .add(R.id.base_fragholder, fragDlg, "dlgGral")
                            .commit();
                }else {
                    getFragmentManager().beginTransaction()
                            .addToBackStack("loginbk")
                            .add(R.id.base_fragholder, fragLogin, "login")
                            .commit();
                }

                drawer.closeDrawer(Gravity.LEFT);

            }
        });

        Button bnpeds = (Button)findViewById(R.id.bnMisPedidos);
        bnpeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFragments(false,"pedidos");
                if (!AppVars.existLoginUser()){
                    U.pushDialogPopup(getFragmentManager(),"No ha iniciado sesion!","dlgUserPedidos");
                }else {
                    PedidosFrag frag = new PedidosFrag();
                    getFragmentManager().beginTransaction()
                            .addToBackStack("pedidosbk")
                            .add(R.id.base_fragholder, frag, "pedidos")
                            .commit();
                }
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        Button bnbag = (Button)findViewById(R.id.bnMiBolsa);
        bnbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFragments(false,"bolsa");
                if (!AppVars.existLoginUser()){
                    U.pushDialogPopup(getFragmentManager(),"No ha iniciado sesion!","dlgUserFav");
                }else {
                    if (!AppVars.bolsa_hasItems()){
                        U.pushDialogPopup(getFragmentManager(),"La bolsa esta vacia!","backcheck");
                    }else {
                        BolsaFrag frag = new BolsaFrag();
                        getFragmentManager().beginTransaction()
                                .addToBackStack("bolsabk")
                                .add(R.id.base_fragholder, frag, "bolsa")
                                .commit();
                    }
                }
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        LinearLayout bnFavorito = (LinearLayout)findViewById(R.id.dw_bnfavoritos);
        bnFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppVars.existLoginUser()){
                    U.pushDialogPopup(getFragmentManager(),"No ha iniciado sesion!","dlgUserFav");
                }else {
                    if (AppVars.fav_existfor()) {
                        FavoritosFrag frag = new FavoritosFrag();
                        U.pushToManager(getFragmentManager(), frag, "fav");
                    }else{
                        U.pushDialogPopup(getFragmentManager(),"No tiene Favoritos!","dlgUserFav");
                    }
                }
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        LinearLayout bnConfig = (LinearLayout)findViewById(R.id.dw_bnconfigura);
        bnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Configura:","click");
            }
        });
        LinearLayout bnSalir = (LinearLayout)findViewById(R.id.dw_bnsalir);
        bnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        ImageView ivrefresh = (ImageView)findViewById(R.id.dw_imglogo);
        ivrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryList_Start(null);
            }
        });

    }

    private void clearFragments(boolean bclerBacklog, String... frags){
        Fragment f;
        String key;
        for(int i=0; i< frags.length; i++){
            key = frags[i];
            f = getFragmentManager().findFragmentByTag(key);
            if (f != null){
                //Log.d("removing fragment",key);
                getFragmentManager().beginTransaction().remove(f).commit();
                if (bclerBacklog) {
                    getFragmentManager().popBackStack(key + "bk", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        }
    }

    private void setLoginIconOn(){
        Button bn = (Button)drawer.findViewById(R.id.bnLogearme);
        bn.setBackgroundResource(R.drawable.llavered);
        Button bnreg = (Button)drawer.findViewById(R.id.bnRegistrarme);
        bnreg.setBackgroundResource(R.drawable.subscribeeditred);
    }

    private void setLoginIconOff(){
        Button bn = (Button)drawer.findViewById(R.id.bnLogearme);
        bn.setBackgroundResource(R.drawable.llaveblue);
        Button bnreg = (Button)drawer.findViewById(R.id.bnRegistrarme);
        bnreg.setBackgroundResource(R.drawable.subscribeblue);
    }

}
