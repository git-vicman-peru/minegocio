package mobapp.vic.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobapp.vic.models.ClieBlog;
import mobapp.vic.models.ClieBlogRev;
import mobapp.vic.models.Cliente;
import mobapp.vic.models.DrawerCategItem;
import mobapp.vic.models.Favorito;
import mobapp.vic.models.FavoritoLVGroup;
import mobapp.vic.models.FavoritoLVItem;
import mobapp.vic.models.FavoritoLVSubitem;
import mobapp.vic.models.FotoProd;
import mobapp.vic.models.Pedido;
import mobapp.vic.models.PedidoFix;
import mobapp.vic.models.PrecioItemGrp;
import mobapp.vic.models.ProdForList;
import mobapp.vic.models.ProdItemBasic;
import mobapp.vic.models.Producto;
import mobapp.vic.models.UserLogin;
import mobapp.vic.utils.AppVars;
import mobapp.vic.utils.U;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by VicDeveloper on 3/3/2017.
 */

public class ServHandler {

    private String selgrupo;

    private CatalogListener mCatListener;
    private CatalogSimpleListener mCatSimpleListener;
    private CatalogGrupoListener mCatGrupoListener;
    private ClientListener mClientListener;
    private LoginListener mLoginListener;
    private PedidoListener mPedidoListener;
    private BlogListener mBlogListener;
    private FavoritoListener mFavoritoListener;


    public ServHandler(){

    }


    public void setCatSearchGroup(String sgroup){
        this.selgrupo = sgroup;
    }

    public String getSelgrupo() {
        return selgrupo;
    }

    public void loadCatalog(CatalogListener qcatListener, CatalogSimpleListener qcatsimple){
        this.mCatListener = qcatListener;
        this.mCatSimpleListener = qcatsimple;

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().addHeader("Accept","Application/JSON").build();
                        return chain.proceed(req);
                    }
                }).build();
        Retrofit retro = new Retrofit.Builder().baseUrl(AppVars.baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IProducto service = retro.create(IProducto.class);
        //Log.d("selgrupo",this.selgrupo);
        Call<List<Producto>> kall = service.getProdByEmpGrupo(AppVars.EmpId,this.selgrupo);
        kall.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, retrofit2.Response<List<Producto>> response) {

                if (response.isSuccessful()){
                    List<Producto> mlist = response.body();

                    List<ProdForList> ls = getProds(mlist);

                    if (mCatSimpleListener != null){
                        List<ProdItemBasic> pilst = getProdBasic(mlist);
                        mCatSimpleListener.onCatalogSimpleLoaded(pilst);
                    }
                    if (mCatListener != null){
                        String ss = "";
                        Producto p;
                        for(int i=0; i<mlist.size(); i++){
                            p = mlist.get(i);
                            if (ss.indexOf(p.getGrupo())<0){
                                ss += p.getGrupo()+';';
                            }
                        }
                        ss = ss.substring(0,ss.length()-1);
                        mCatListener.onCatalogListLoaded(ss.split(";"),ls);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                if (mCatListener != null){
                    mCatListener.onCatalogListFailed();
                }
            }
        });
    }

    public void Catalog_Search(int idemp, String qgrupo, String qcrit, CatalogListener qlistener){
        this.mCatListener = qlistener;
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().addHeader("Accept","Application/JSON").build();
                        return chain.proceed(req);
                    }
                }).build();
        Retrofit retro = new Retrofit.Builder().baseUrl(AppVars.baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IProducto serv = retro.create(IProducto.class);
        Call<List<Producto>> kall = serv.getProdEmpGrupoCrit(idemp,qgrupo,qcrit);
        kall.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, retrofit2.Response<List<Producto>> response) {
                if (response.isSuccessful()){
                    List<Producto> mlist = response.body();
                    List<ProdForList> ls = getProds(mlist);
                    if (mCatListener != null){
                        mCatListener.onCatalogListLoaded(null,ls);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {

            }
        });
    }

    private final List<ProdItemBasic> getProdBasic(List<Producto> qlist){
        List<ProdItemBasic> lst;
        ProdItemBasic p;
        ProdForList pf;
        Producto prod;
        FotoProd fot;
        byte[] decImg;
        Bitmap bmp;
        lst = new ArrayList<ProdItemBasic>();
        for(int i=0; i<qlist.size(); i++){
            prod = qlist.get(i);
            p = new ProdItemBasic(prod.getNombre(),prod.getDescripcioncorta());
            if (prod.getFotos().size()>0){
                fot = prod.getFotos().get(0);
                if (fot.getSrc64()!= null) {
                    decImg = Base64.decode(fot.getSrc64().getBytes(), Base64.DEFAULT);
                    bmp = BitmapFactory.decodeByteArray(decImg, 0, decImg.length);
                    p.setImg(bmp);
                }
            }
            try{
                int uidx = prod.getUnidadidx();
                PrecioItemGrp ps = new PrecioItemGrp();
                String ssprec = prod.getUnidadprecios();
                ps.parseFrom(ssprec);
                p.setPrecioref(ps.get(uidx-1));
                ps = null;
            }catch (Exception ex){
                ex.printStackTrace();
            }
            lst.add(p);
        }
        return lst;
    }

    private final List<ProdForList> getProds(List<Producto> qlist){
        ProdItemBasic p;
        List<ProdForList> lst;
        /*
        List<ProdItemBasic> l = new ArrayList<ProdItemBasic>();
        p = new ProdItemBasic("Bananas","Platanos de seda"); l.add(p);
        p = new ProdItemBasic("Mangos","Mangos papaya de piura"); l.add(p);
        p = new ProdItemBasic("Fideo Entrefino","Fideos luchetta finos"); l.add(p);
        p = new ProdItemBasic("Mermelada","Mermelada natural"); l.add(p);
        p = new ProdItemBasic("Cocacola","Bebida light de cocacola"); l.add(p);
        p = new ProdItemBasic("Harina Blancaflor","Platanos de seda"); l.add(p);
        p = new ProdItemBasic("Terno Dama","Terno de seda para damas"); l.add(p);
        p = new ProdItemBasic("SunGlasses","Lentes UV para el verano"); l.add(p);
        p = new ProdItemBasic("Mocasines","Zapatos ligeros negros"); l.add(p);
        */
        lst = new ArrayList<ProdForList>();
        int nitems = qlist.size();
        boolean impar = ((nitems%2!=0)?true:false);
        //Log.d("nitems:",impar?"impar":"par");
        ProdForList pf;
        Producto prod;
        FotoProd fot;
        byte[] decImg;
        Bitmap bmp;
        ImageView imgv;

        int uidx;
        PrecioItemGrp ps;
        String ssprec;

        for(int i=1; i<nitems; i+=2){
            //Log.d("item index: ",i + "");
            pf = new ProdForList();

            pf.setEsimpar(false);
            prod = qlist.get(i-1);
            p = new ProdItemBasic(prod.getNombre(),prod.getDescripcioncorta());
            p.setId_prod(prod.getId_prod());
            if (prod.getFotos().size()>0){
                fot = prod.getFotos().get(0);
                if (fot.getSrc64()!= null) {
                    decImg = Base64.decode(fot.getSrc64().getBytes(), Base64.DEFAULT);
                    bmp = BitmapFactory.decodeByteArray(decImg, 0, decImg.length);
                    p.setImg(bmp);
                }
            }

            try{
                uidx = prod.getUnidadidx();
                ps = new PrecioItemGrp();
                ssprec = prod.getUnidadprecios();
                ps.parseFrom(ssprec);
                p.setPrecioref(ps.get(uidx-1));
                ps = null;
            }catch (Exception ex){
                ex.printStackTrace();
            }
            pf.setProdA(p);

            prod = qlist.get(i);
            p = new ProdItemBasic(prod.getNombre(),prod.getDescripcioncorta());
            p.setId_prod(prod.getId_prod());
            if (prod.getFotos().size()>0){
                fot = prod.getFotos().get(0);
                if (fot.getSrc64()!= null) {
                    decImg = Base64.decode(fot.getSrc64().getBytes(), Base64.DEFAULT);
                    bmp = BitmapFactory.decodeByteArray(decImg, 0, decImg.length);
                    p.setImg(bmp);
                }
            }
            try{
                uidx = prod.getUnidadidx();
                ps = new PrecioItemGrp();
                ssprec = prod.getUnidadprecios();
                ps.parseFrom(ssprec);
                p.setPrecioref(ps.get(uidx-1));
                ps = null;
            }catch (Exception ex){
                ex.printStackTrace();
            }
            pf.setProdB(p);

            lst.add(pf);
        }
        if (impar){
            pf = new ProdForList();
            pf.setEsimpar(true);
            prod = qlist.get(nitems-1);
            p = new ProdItemBasic(prod.getNombre(),prod.getDescripcioncorta());
            p.setId_prod(prod.getId_prod());
            if (prod.getFotos().size()>0){
                fot = prod.getFotos().get(0);
                if (fot.getSrc64()!= null) {
                    decImg = Base64.decode(fot.getSrc64().getBytes(), Base64.DEFAULT);
                    bmp = BitmapFactory.decodeByteArray(decImg, 0, decImg.length);
                    p.setImg(bmp);
                }
            }
            try{
                uidx = prod.getUnidadidx();
                ps = new PrecioItemGrp();
                ssprec = prod.getUnidadprecios();
                ps.parseFrom(ssprec);
                p.setPrecioref(ps.get(uidx-1));
                ps = null;
            }catch (Exception ex){
                ex.printStackTrace();
            }
            pf.setProdA(p);
            lst.add(pf);
        }
        /*
        Log.d("item","Inicio....");
        for(ProdForList fl : lst){
            if (fl.getProdB() != null){
                Log.d("item", fl.isEsimpar() + " " + fl.getProdA().getNombreRef() + " " + fl.getProdB().getNombreRef());
            }else {
                Log.d("item", fl.isEsimpar() + " " + fl.getProdA().getNombreRef() );
            }
        }
        */
        return lst;
    }

    public void sendCliente(Cliente qclie, ClientListener qclientlistener){
        this.mClientListener = qclientlistener;
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().addHeader("Accept","Application/JSON").build();
                        return chain.proceed(req);
                    }
                }).build();
        Retrofit retro = new Retrofit.Builder().baseUrl(AppVars.baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ICliente service = retro.create(ICliente.class);
        Call<Cliente> kall = service.saveCliente(qclie);
        kall.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, retrofit2.Response<Cliente> response) {
                Cliente c = response.body();
                if (mClientListener != null){
                    mClientListener.onClientUploaded(c);
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                if (mClientListener != null){
                    Cliente c = (Cliente)call;
                    mClientListener.onClientFailUpload(c, t);
                }
            }
        });
    }

    public void updateCliente(Cliente qclie, ClientListener qclientlistener){
        this.mClientListener = qclientlistener;
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().addHeader("Accept","Application/JSON").build();
                        return chain.proceed(req);
                    }
                }).build();
        Retrofit retro = new Retrofit.Builder().baseUrl(AppVars.baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ICliente service = retro.create(ICliente.class);
        Call<Boolean> kall = service.updateCliente(qclie);
        kall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                Boolean c = response.body();
                if (mClientListener != null){
                    mClientListener.onClientUpdated(c);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void loadGrupos(CatalogGrupoListener qgrupolis){
        this.mCatGrupoListener = qgrupolis;

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().addHeader("Accept","Application/JSON").build();
                        return chain.proceed(req);
                    }
                }).build();
        Retrofit retro = new Retrofit.Builder().baseUrl(AppVars.baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ICatGrupo serv = retro.create(ICatGrupo.class);
        Call<List<String>> kall = serv.getGrupo(AppVars.EmpId);
        kall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {
                if (response.isSuccessful()){
                    List<String> l = response.body();
                    if (mCatGrupoListener != null){
                        DrawerCategItem dwItem;
                        List<DrawerCategItem> glst = new ArrayList<DrawerCategItem>();
                        glst.add(new DrawerCategItem("Todos los productos","Todos","T",0));
                        for (int i=0; i<l.size(); i++){
                            dwItem = new DrawerCategItem(l.get(i),l.get(i),l.get(i).substring(0,2),1);
                            glst.add(dwItem);
                        }
                        mCatGrupoListener.onCatalogGrupoLoaded(glst);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    public void checkLogin(UserLogin loginuser, LoginListener loginlisten){
        this.mLoginListener = loginlisten;
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().addHeader("Accept","Application/JSON").build();
                        return chain.proceed(req);
                    }
                }).build();
        Retrofit retro = new Retrofit.Builder().baseUrl(AppVars.baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IUserLogin serv = retro.create(IUserLogin.class);
        Call<Cliente> kall = serv.checkUser(loginuser);
        kall.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, retrofit2.Response<Cliente> response) {
                Cliente c = (Cliente)response.body();
                if (mLoginListener != null){
                    mLoginListener.onLoginReceived(c);
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                if (mLoginListener != null){
                    String errmsg = t.getMessage();
                    mLoginListener.onLoginFailed(errmsg);
                }
            }
        });
    }

    public void sendPedido(Pedido ped, PedidoListener qpedlistener){
        this.mPedidoListener = qpedlistener;
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().addHeader("Accept","Application/JSON").build();
                        return chain.proceed(req);
                    }
                }).build();
        Retrofit retro = new Retrofit.Builder().baseUrl(AppVars.baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IPedido serv = retro.create(IPedido.class);
        Call<Boolean> kall = serv.hacerPedido(ped);
        kall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                Boolean b;
                b = (Boolean)response.body();
                if (mPedidoListener != null){
                    mPedidoListener.onPedidoSuccess(b);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                if (mPedidoListener != null){
                    mPedidoListener.onPedidoFailure(false, t.getMessage());
                }
            }
        });
    }

    public void Blog_SaveComment(ClieBlog qcom, BlogListener qbloglistener){
        this.mBlogListener = qbloglistener;
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().addHeader("Accept","Application/JSON").build();
                        return chain.proceed(req);
                    }
                }).build();
        Retrofit retro = new Retrofit.Builder().baseUrl(AppVars.baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IClieBlog serv = retro.create(IClieBlog.class);
        Call<Void> kall = serv.saveComment(qcom);
        kall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (mBlogListener != null){
                    mBlogListener.onBlogSaved();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (mBlogListener != null){
                    mBlogListener.onBlogFailed();
                }
            }
        });
    }

    public void Favorito_Load(String ssids, final List<Favorito> localfavs, FavoritoListener qlistener){
        this.mFavoritoListener = qlistener;
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().addHeader("Accept","Application/JSON").build();
                        return chain.proceed(req);
                    }
                }).build();
        Retrofit retro = new Retrofit.Builder().baseUrl(AppVars.baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IFavoritos serv = retro.create(IFavoritos.class);
        Call<List<Producto>> kall = serv.getFavoritosList(AppVars.EmpId, ssids);
        kall.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, retrofit2.Response<List<Producto>> response) {
                List<Producto> l = (List<Producto>)response.body();
                String grp="";
                FavoritoLVGroup fvs = new FavoritoLVGroup();
                FavoritoLVItem fi = null;
                FavoritoLVSubitem fsubi;
                for(Producto p : l){
                    if (!grp.equals(p.getGrupo())) {
                        grp = p.getGrupo();
                        fi = new FavoritoLVItem();
                        fi.setGrupo(grp);
                        fvs.add(fi);
                        grp = p.getGrupo();
                    }
                    fsubi = new FavoritoLVSubitem();
                    fsubi.setId_prod(p.getId_prod());
                    fsubi.setNombre(p.getNombre());
                    fsubi.setDescripcioncorta(p.getDescripcioncorta());
                    fsubi.setDescripcionlarga(p.getDescripcionlarga());
                    fsubi.setImg(U.getBmpFromBase64(p.getFotos().get(0).getSrc64()));
                    fsubi.setProdref(p);
                    for(Favorito favo : localfavs){
                        if (favo.getId_prod()==fsubi.getId_prod()){
                            fsubi.setFecha(U.milliToDate(favo.getFecha()));
                            break;
                        }
                    }

                    fi.add(fsubi);
                }

                if (mFavoritoListener != null){
                    mFavoritoListener.onFavoritoLoaded(fvs);
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                if (mFavoritoListener != null){
                    mFavoritoListener.onFavoritoFailed();
                }
            }
        });
    }

    public void Blogs_Load(int empid, int proid, BlogListener qlistener){
        this.mBlogListener = qlistener;
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().addHeader("Accept","Application/JSON").build();
                        return chain.proceed(req);
                    }
                }).build();
        Retrofit retro = new Retrofit.Builder().baseUrl(AppVars.baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IClieBlog service = retro.create(IClieBlog.class);
        Call<List<ClieBlogRev>> kall = service.getComments(empid,proid);
        kall.enqueue(new Callback<List<ClieBlogRev>>() {
            @Override
            public void onResponse(Call<List<ClieBlogRev>> call, retrofit2.Response<List<ClieBlogRev>> response) {
                List<ClieBlogRev> lst = (List<ClieBlogRev>)response.body();
                if (mBlogListener != null){
                    mBlogListener.onBlogsReceived(lst);
                }
            }

            @Override
            public void onFailure(Call<List<ClieBlogRev>> call, Throwable t) {
                if (mBlogListener != null){
                    mBlogListener.onBlogFailed();
                }
            }
        });
    }

    public void Pedidos_Load(int empid, int clieid, PedidoListener qlistener){
        this.mPedidoListener = qlistener;
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder().addHeader("Accept","Application/JSON").build();
                        return chain.proceed(req);
                    }
                }).build();
        Retrofit retro = new Retrofit.Builder().baseUrl(AppVars.baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IPedido service = retro.create(IPedido.class);
        Call<List<PedidoFix>> kall = service.leerTodoPedido(empid,clieid);
        kall.enqueue(new Callback<List<PedidoFix>>() {
            @Override
            public void onResponse(Call<List<PedidoFix>> call, retrofit2.Response<List<PedidoFix>> response) {
                List<PedidoFix> lst = (List<PedidoFix>)response.body();
                if (mPedidoListener != null){
                    mPedidoListener.onPedidoListReceived(lst);
                }
            }

            @Override
            public void onFailure(Call<List<PedidoFix>> call, Throwable t) {
                if (mPedidoListener != null){
                    mPedidoListener.onPedidoFailure(false,t.getMessage());
                }
            }
        });
    }


    public interface FavoritoListener{
        void onFavoritoLoaded(FavoritoLVGroup favs);
        void onFavoritoFailed();
    }

    public interface  PedidoListener{
        void onPedidoListReceived(List<PedidoFix> src);
        void onPedidoSuccess(boolean flag);
        void onPedidoFailure(boolean flag, String message);
    }


    public interface BlogListener{
        void onBlogsReceived(List<ClieBlogRev> blogs);
        void onBlogSaved();
        void onBlogFailed();
    }

    public interface LoginListener{
        void onLoginReceived(Cliente qcli);
        void onLoginFailed(String errmsg);
    }

    public interface  ClientListener{
        void onClientUploaded(Cliente qcli);
        void onClientFailUpload(Cliente qclie, Throwable terr);
        void onClientUpdated(Boolean bresult);
    }

    public interface CatalogListener{
        void onCatalogListLoaded(String[] groupnames,List<ProdForList> lstProds);
        void onCatalogListFailed();
    }

    public interface CatalogSimpleListener{
        void onCatalogSimpleLoaded(List<ProdItemBasic> lstSProds);
    }

    public interface CatalogGrupoListener{
        void onCatalogGrupoLoaded(List<DrawerCategItem> grupos);
    }
}
