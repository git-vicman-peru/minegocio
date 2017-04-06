package mobapp.vic.utils;

import java.util.Date;
import java.util.List;

import mobapp.vic.models.Cliente;
import mobapp.vic.models.Favorito;
import mobapp.vic.models.Pedido;
import mobapp.vic.models.ProdItemBasic;

/**
 * Created by VicDeveloper on 2/22/2017.
 */

public class AppVars {

    public static String COMINGFROM;

    public static String baseUrl;

    public static DBAdapter dba;

    public static int EmpId;

    public static Cliente logginUser;

    public static Bolsa bag;

    public static Pedido order;

    public static void openDba(){
        dba.Open();
    }

    public static void closeDba(){
        dba.Close();
    }

    public static void LoadDefaults(){
        dba.Open();
        EmpId = dba.empid_read();
        baseUrl = dba.baseUrl_read();
        dba.Close();

        bag = new Bolsa();
        order = new Pedido();
    }

    public static boolean existLoginUser(){
        if (logginUser == null) return false;
        return (logginUser.getId_clie()>0);
    }

    public static void resetLoginUser(){
        logginUser = null;
    }

    public static boolean fav_existfor(){
        AppVars.openDba();
        String ids = AppVars.dba.Fav_GetProdIds(AppVars.EmpId,AppVars.logginUser.getId_clie());
        //Log.d("ids",ids);
        //List<Favorito> lfav = AppVars.dba.Fav_Read(AppVars.EmpId,AppVars.logginUser.getId_clie());
        //Log.d("lfav",lfav.size()+"");
        AppVars.closeDba();
        return (!ids.isEmpty() && (ids != null));
    }

    public static List<ProdItemBasic> bolsa_getitems(){
        return bag.getList();
    }

    public static String bolsa_getSubtotalStr(){
        return bag.getTotalStr();
    }

    public static float bolsa_getSubtotal(){
        return bag.getTotal();
    }

    public static void bolsa_add(ProdItemBasic qprod){
        bag.add(qprod);
    }

    public static void bolsa_reset(){
        bag = new Bolsa();
    }

    public static void bolsa_remove(int pos){
        bag.remove(pos);
    }

    public static void bolsa_removeById(int productId){
        bag.removeById(productId);
    }

    public static String bolsa_getItemsCountStr(){
        int c = 0;
        try{
            c = bag.getList().size();
        }catch (Exception ex){}
        return c+"";
    }

    public static int bolsa_getItemsCount(){
        int c = 0;
        try{
            c = bag.getList().size();
        }catch (Exception ex){}
        return c;
    }

    public static boolean bolsa_hasItems(){
        int c;
        if (bag==null) return false;
        try{
            c = bag.getList().size();
        }catch (Exception ex){ c = 0; }
        return (c > 0);
    }

    public static void setComingFrom(String mdlname){
        COMINGFROM = mdlname;
    }

    public static void Pedido_Reset(){
        order = new Pedido();

    }

    public static void Pedido_SetIDEmp(int nidemp){
        order.setId_emp(nidemp);
    }

    public static void Pedido_SetIDClie(int nidclie){
        order.setId_clie(nidclie);
    }

    public static void Pedido_SetSerie(String qserie){
        order.setSerie(qserie);
    }

    public static void Pedido_SetNumero(String qnumero){
        order.setNumero(qnumero);
    }

    public static void Pedido_SetFechaStr(Date qfecha){
        order.setFechaStr(U.dateToMillis(qfecha));
    }

    public static void Pedido_SetFechaEntregaStr(Date qfecha){
        order.setFechaentregaStr(U.dateToMillis(qfecha));
    }

    public static void Pedido_SetDirecEntrega(String qdirec){
        order.setDirecentrega(qdirec);
    }

    public static void Pedido_SetCorreo(String qcorreo){
        order.setNotiemail(qcorreo);
    }

    public static void Pedido_SetFono(String qfono){
        order.setNotifono(qfono);
    }

    public static void Pedido_SetObs(String qobs){
        order.setObs(qobs);
    }

    public static void Pedido_SetEstado(String qestado){
        order.setEstado(qestado);
    }

    public static void Pedido_SetMonto(float nmonto){
        order.setMonto(nmonto);
    }

    public static void Pedido_SetDetalleFromBag(){
        order.setDetalles(bag.ToPedidoDetList());
    }

}
