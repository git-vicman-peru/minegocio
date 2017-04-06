package mobapp.vic.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mobapp.vic.models.Favorito;


public class DBAdapter {

	private static final String DATABASENAME="minegocio.db";
    private static final int DATABASE_VERSION=1;

    private static final String CreateConfigAp_t_tbl="CREATE TABLE ConfigAp_t (ID_CA INTEGER PRIMARY KEY AUTOINCREMENT, Categoria TEXT, Variable TEXT, Descripcion TEXT, Valor TEXT, ID NUMERIC, Marca BOOLEAN);";
    private static final String CreateFavoritos_t_tbl="CREATE TABLE Favoritos_t (id_fav INTEGER PRIMARY KEY AUTOINCREMENT, id_emp INTEGER, id_clie INTEGER, id_prod INTEGER,fecha TEXT);";

    private String DBPath;
    private final Context cntx;
    private DatabaseHelper dbhelp;
    private SQLiteDatabase db;
    
    public DBAdapter(Context ctx){
    	DBPath = "/data/data/" + ctx.getPackageName() + "/databases/";
    	this.cntx = ctx;
    	dbhelp = new DatabaseHelper(this.cntx);
    }
    
    public DBAdapter Open() throws SQLException{
    	db = dbhelp.getWritableDatabase();
        return this;
    }
    
    public void Close(){
        dbhelp.close();
    }
    
    public static boolean IsNullStr(String svalue){
        if (svalue == null) return true;
        if (svalue.compareToIgnoreCase("") > 0) return true;
        return false;
    }
    
    public long PeDef_Insert(String snombre){
    	ContentValues cv = new ContentValues();
    	cv.put("Variable", snombre);
        return db.insert("ConfigAp_t", null, cv);
    }
    
    public Cursor PeDef_ReadAll(){
    	return db.query("ConfigAp_t",
                new String[]{"ID_CA","Variable"}, null, null, null, null, null);
    }
    
    public Cursor PeDef_ReadOne(long nid) throws SQLException {
        Cursor c = db.query(true, "ConfigAp_t",
                new String[]{"ID_CA","Variable"}, "ID_CA=" + nid, null, null, null, null, null);
        if (c != null){
            c.moveToFirst();
        }
        return c;
    }
    
    public ArrayList<String> PeDef_ReadStrArr(){
    	ArrayList<String> l = null;

        Cursor c = db.query("ConfigAp_t", new String[]{"ID_CA","Variable"}, null, null, null, null, null);

        l = new ArrayList<String>();
        c.moveToFirst();
        while(c.moveToNext()){
            l.add(c.getString(1));
        }
        c.close();

        return l;
    }
    
    public boolean PeDef_Update(long nid, String snombre){
        ContentValues cv = new ContentValues();
        cv.put("ID_CA", nid);
        return (db.update("ConfigAp_t", cv, "ID_CA="+nid, null)>0);
    }

    public void baseUrl_update(String newUrl){
        int r;
        ContentValues cv = new ContentValues();
        cv.put("Valor", newUrl);
        r = db.update("ConfigAp_t", cv, "Categoria='Config' And Variable='baseUrl'", null);
    }

    public String baseUrl_read(){
        String res = null;
        try{
            Cursor cur = db.query(true,"ConfigAp_t",new String[]{"ID_CA","Valor"},"Categoria='Config' And Variable='baseUrl'", null, null, null, null, null);
            cur.moveToFirst();
            res = cur.getString(1);
            cur.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return res;
    }

    public void empid_update(int nempid){
        int r;
        ContentValues cv = new ContentValues();
        cv.put("ID", nempid);
        r = db.update("ConfigAp_t", cv, "Categoria='Config' And Variable='empid'", null);
    }

    public int empid_read(){
        int res = 0;
        try {
            Cursor cur = db.query(true,"ConfigAp_t",new String[]{"ID_CA","ID"},"Categoria='Config' And Variable='empid'", null, null, null, null, null);
            cur.moveToFirst();
            res = cur.getInt(1);
            cur.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return res;
    }

    public long Fav_Insert(Favorito qfav){
        ContentValues cv = new ContentValues();
        cv.put("id_emp",qfav.getId_emp());
        cv.put("id_clie",qfav.getId_clie());
        cv.put("id_prod",qfav.getId_prod());
        cv.put("fecha",qfav.getFecha());
        return db.insert("Favoritos_t",null,cv);
    }

    public long Fav_InsertCheckFirst(Favorito qfav){
        long r = 0;
        boolean b = Fav_Exist(qfav.getId_emp(),qfav.getId_clie(),qfav.getId_prod());
        if (!b){
            r = Fav_Insert(qfav);
        }
        return  r;
    }

    public boolean Fav_Exist(int empId, int clieId, int prodId){
        boolean res = false;
        String sql = "Select * From Favoritos_t Where (id_emp="+empId+")And(id_clie="+clieId+")And(id_prod="+prodId+")";
        Cursor cur = db.rawQuery(sql,null);
        if (cur.getCount()>0){
            res = true;
        }
        cur.close();
        return res;
    }

    public List<Favorito> Fav_Read(int empId, int clieId){
        List<Favorito> l = new ArrayList<Favorito>();
        Favorito f;
        try{
            Cursor cur = db.query(true,"Favoritos_t",new String[]{"id_fav","id_emp","id_clie","id_prod","fecha"},"id_emp="+empId +" And id_clie="+clieId, null, null, null, null, null);
            if (cur.moveToFirst()){
                do{
                    f = new Favorito();
                    f.setId_fav(cur.getInt(0));
                    f.setId_emp(cur.getInt(1));
                    f.setId_clie(cur.getInt(2));
                    f.setId_prod(cur.getInt(3));
                    f.setFecha(cur.getString(4));
                    l.add(f);
                }while (cur.moveToNext());
            }
            cur.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return l;
    }

    public String Fav_GetProdIds(int empId, int clieId){
        String res = "";
        try{
            Cursor cur = db.query(true,"Favoritos_t",new String[]{"id_prod"},"id_emp="+empId +" And id_clie="+clieId, null, null, null, null, null);
            if (cur.moveToFirst()){
                do{
                    res += cur.getString(0)+"-";
                }while (cur.moveToNext());
                res = res.substring(0,res.length()-1);
            }
            cur.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return res;
    }

    public long Fav_update(Favorito qfav){
        ContentValues cv = new ContentValues();
        cv.put("id_emp",qfav.getId_emp());
        cv.put("id_clie",qfav.getId_clie());
        cv.put("id_prod",qfav.getId_prod());
        cv.put("fecha",qfav.getFecha());
        return db.update("Favoritos_t", cv, "id_fav="+qfav.getId_fav(), null);
    }

    public long Fav_delete(int empId, int clieId){
        return db.delete("Favoritos_t","(id_emp="+empId+")And(id_clie="+clieId+")",null);
    }

    /*
    public boolean PeDef_UpdPeso(Peso qpeso){
    	ContentValues cv = new ContentValues();
    	cv.put("Libras", qpeso.getLibras());
    	cv.put("Cantidad", qpeso.getCantidad());
    	cv.put("Kilos", qpeso.getKilos());
    	return (db.update("Pesos_t", cv, "ID_Pe="+qpeso.getID_Pe(), null)>0);
    }

    public ArrayList<Peso> PeDef_LoadDefaults(){
        ArrayList<Peso> l = null;
        String[] args = new String[]{"1","1"};

        Cursor c = db.query("Pesos_t", new String[]{"ID_Pe","Peso","Libras","Cantidad","Kilos","Fijo", "Tipo"}, "Defecto=? And Vigente=?", args, null, null, null);
        Peso p;
        l = new ArrayList<Peso>();

        if (c.moveToFirst()){
            do{
                p = new Peso();
                p.setID_Pe(c.getInt(0));
                p.setNombre(c.getString(1));
                p.setLibras(c.getFloat(2));
                p.setCantidad(c.getFloat(3));
                p.setKilos(c.getFloat(4));
                p.setFijo((c.getString(5).equalsIgnoreCase("0")?false:true));
                p.setTipo((byte)c.getInt(6));
                l.add(p);
                p = null;
            } while(c.moveToNext());
        }
        return l;
    }

    public ArrayList<Peso> PeDef_LoadNoDefaults(){
        ArrayList<Peso> l = null;
        String[] args = new String[]{"0","1"};

        Cursor c = db.query("Pesos_t", new String[]{"ID_Pe","Peso","Libras","Cantidad","Kilos","Fijo","Tipo"}, "Defecto=? And Vigente=?", args, null, null, null);
        Peso p;
        l = new ArrayList<Peso>();

        if (c.moveToFirst()){
            do{
                p = new Peso();
                p.setID_Pe(c.getInt(0));
                p.setNombre(c.getString(1));
                p.setLibras(c.getFloat(2));
                p.setCantidad(c.getFloat(3));
                p.setKilos(c.getFloat(4));
                p.setFijo((c.getString(5).equalsIgnoreCase("0")?false:true));
                p.setTipo((byte)c.getInt(6));
                l.add(p);
                p = null;
            } while(c.moveToNext());
        }
        return l;
    }

    public ArrayList<String> PeDef_LoadNoDefaultsAStr(){
        ArrayList<String> l = null;
        String[] args = new String[]{"0","1"};

        Cursor c = db.query("Pesos_t", new String[]{"ID_Pe","Peso","Libras","Cantidad","Kilos","Fijo", "Tipo"}, "Defecto=? And Vigente=?", args, null, null, null);
        Peso p;
        l = new ArrayList<String>();

        if (c.moveToFirst()){
            do{
                l.add(c.getString(1));
            } while(c.moveToNext());
        }
        c.close();
        return l;
    }
*/
    public long PeDef_AddAsNoDefault(String snombrepeso, float nlibras, float ncant, float nkilos, boolean bfijo, byte ntipo){
        ContentValues cv = new ContentValues();
        cv.put("Peso", snombrepeso);
        cv.put("Libras", nlibras);
        cv.put("Cantidad", ncant);
        cv.put("Kilos", nkilos);
        cv.put("Fijo", bfijo);
        cv.put("Defecto", false);
        cv.put("Vigente", true);
        cv.put("Tipo", ntipo);
        return db.insert("Pesos_t", null, cv);
    }
    /*
    public long PeDef_AddAsNoDefault(Peso qpeso, boolean bfijo, byte ntipo){
        ContentValues cv = new ContentValues();
        cv.put("Peso", qpeso.getNombre());
        cv.put("Cantidad", qpeso.getCantidad());
        cv.put("Libras", qpeso.getLibras());
        cv.put("Kilos", qpeso.getKilos());
        cv.put("Fijo", bfijo);
        cv.put("Defecto", false);
        cv.put("Vigente", true);
        cv.put("Tipo", ntipo);
        return db.insert("Pesos_t", null, cv);
    }
*/
    public void PeDef_SetAsDefault(boolean bvalue, int nid){
        int r;
        ContentValues cv = new ContentValues();
        cv.put("Defecto", bvalue);
        r = db.update("Pesos_t", cv, "ID_Pe="+nid, null);
    }

    public void PeDef_UpdCants(int nidpe, float ncant, float nkgs){
        int r;
        ContentValues cv = new ContentValues();
        cv.put("Cantidad", ncant);
        cv.put("Kilos", nkgs);
        r = db.update("Pesos_t", cv, "ID_Pe="+nidpe, null);
    }

    public float PeDef_SumaDefaults(){
        float s=0;
        String[] args = new String[]{"1","1"};

        Cursor c = db.query("Pesos_t", new String[]{"Cantidad","Kilos"}, "Defecto=? And Vigente=?", args, null, null, null);

        if (c.moveToFirst()){
            do{
                s += 2.2 * (c.getFloat(0)*c.getFloat(1));//libras
            } while(c.moveToNext());
        }
        return s;
    }
    
  /*  
    public void DataIn_SaveDB(DataIn qsrc){
    	String s;
    	
    	s = "Update ConfigAp_t Set Valor='" + qsrc.getNmotorStr() +"' Where (Categoria='DataIn') And (Variable='NMotor')" ;
    	db.execSQL(s);
    	s = "Update ConfigAp_t Set Valor='" + qsrc.getPesototalStr() +"' Where (Categoria='DataIn') And (Variable='PesoTotal')" ;
    	db.execSQL(s);
    	s = "Update ConfigAp_t Set Valor='" + qsrc.getAltitudStr() +"' Where (Categoria='DataIn') And (Variable='Altitud')" ;
    	db.execSQL(s);
    	s = "Update ConfigAp_t Set Valor='" + qsrc.getTemperaturaStr() +"' Where (Categoria='DataIn') And (Variable='Temperatura')" ;
    	db.execSQL(s);
    	s = "Update ConfigAp_t Set Valor='" + qsrc.getHeaterStr() +"' Where (Categoria='DataIn') And (Variable='Heater')" ;
    	db.execSQL(s);
    }
    
    public DataIn DataIn_ReadDB(){
    	DataIn v;
    	
    	v = new DataIn();
        Cursor c = db.query("ConfigAp_t", new String[]{"Variable","Valor"}, "Categoria='DataIn'", null, null, null, null);
        if (c.getCount()<1) return null;
        c.moveToFirst();
        try{
        	v.setNmotor(Integer.parseInt(c.getString(1)));
        }catch(Error er){}
        c.moveToNext();
        //--------------------
        try{
        	v.setPesototal(Float.parseFloat(c.getString(1)));
        }catch(Error er){}
        c.moveToNext();
        //---------------------
        try{
        	v.setAltitud(Float.parseFloat(c.getString(1)));
        }catch(Error er){}
        c.moveToNext();
        //---------------------
        try{
        	v.setTemperatura(Float.parseFloat(c.getString(1)));
        }catch(Error er){}
        c.moveToNext();
        //----------------------
        try{
        	v.setHeater(Boolean.parseBoolean(c.getString(1)));
        }catch(Error er){}
        c.close();
        
    	return v;
    }
   */
    
    
    
    private static class DatabaseHelper extends SQLiteOpenHelper{
    	
    	DatabaseHelper(Context qctx){
            super(qctx,DATABASENAME,null,DATABASE_VERSION);
        }

		@Override
		public void onCreate(SQLiteDatabase dbs) {
			/*
			String s;
			char dsi = '1', dno = '0'; // defecto_si_no
			char fsi = '1', fno = '0'; // fijo_si_no
			
			//       Peso, Libras,Cantidad,Kilos, Vigente,  Defecto,Fijo,Tipo
            String[] a = {
            		"'Peso b�sico del helic�ptero',0,0,0,'1',"+dsi+","+fsi+"," + Peso.PESOMAP_LIBRAS,
                    "'Lubricantes',0,0,0,'1',"+dsi+","+fno+"," + Peso.PESOMAP_LIBRAS,
                    "'Malet�n de herramientas helic�ptero',0,0,0,'1',"+dsi+","+fno+"," + Peso.PESOMAP_TODOS,
                    "'Piloto',0,0,0,'1',"+dsi+","+fsi+"," + Peso.PESOMAP_LIBkg,
                    "'Copiloto',0,0,0,'1',"+dsi+","+fno+"," + Peso.PESOMAP_LIBkg,
                    "'Mec�nico',0,0,0,'1',"+dsi+","+fno+"," + Peso.PESOMAP_TODOS,
                    "'Operador de FLIR',0,0,0,'1',"+dsi+","+fno+"," + Peso.PESOMAP_TODOS,
                    "'DOE SAR',0,0,0,'1',"+dsi+","+fno+"," + Peso.PESOMAP_TODOS,
                    "'Combustible utilizable',0,0,0,'1',"+dsi+","+fno+"," + Peso.PESOMAP_LIBRAS,
                    "'Equipos de supervivencia DOE',0,0,0,'1',"+dsi+","+fno+"," + Peso.PESOMAP_TODOS,
                    "'Blindaje de piso',0,0,0,'1',"+dsi+","+fno+"," + Peso.PESOMAP_TODOS,
                    "'Montantes Ametralladora y Munici�n MAG',0,0,0,'1',"+dsi+","+fno+","+ Peso.PESOMAP_TODOS,
                    "'Montantes POD y Munici�n ametralladora/cohetera',0,0,0,'1',"+dsi+","+fno+","+Peso.PESOMAP_TODOS,
                    "'Chalecos',0,0,0,'1',"+dno+","+fno+","+Peso.PESOMAP_TODOS,
                    "'Domo FLIR',0,0,0,'1',"+dno+","+fno+","+Peso.PESOMAP_TODOS,
                    "'Consola FLIR',0,0,0,'1',"+dno+","+fno+","+Peso.PESOMAP_TODOS};
*/

            dbs.execSQL(CreateConfigAp_t_tbl);
            dbs.execSQL(CreateFavoritos_t_tbl);
            //ConfigAp_t (ID_CA INTEGER PRIMARY KEY AUTOINCREMENT,
            // Categoria TEXT, Variable TEXT, Descripcion TEXT, Valor TEXT, ID NUMERIC, Marca BOOLEAN
            String sql = "Insert Into ConfigAp_t (Categoria,Variable,Valor)Values('Config','baseUrl','http://45.55.72.7:8080/MiNegocioApi/');";
            //10.0.2.2
            dbs.execSQL(sql);
            sql = "Insert Into ConfigAp_t (Categoria,Variable,ID)Values('Config','empid',11);";
            dbs.execSQL(sql);

            /*
            int nf,f;

            nf = a.length;
            for(f=0; f<nf; f++){
                s = a[f];
                s = "Insert Into Pesos_t (Peso,Libras,Cantidad,Kilos,Vigente,Defecto,Fijo,Tipo)Values(" + s + ");";
                dbs.execSQL(s);
            }
            
            //DataIn default values:
            s = "'DataIn','NMotor'," + "'1'";
            s = "Insert Into ConfigAp_t (Categoria,Variable,Valor)Values(" + s + ");";
            dbs.execSQL(s);
            
            s = "'DataIn','PesoTotal'," + "'0'";
            s = "Insert Into ConfigAp_t (Categoria,Variable,Valor)Values(" + s + ");";
            dbs.execSQL(s);
            
            s = "'DataIn','Altitud'," + "'0'";
            s = "Insert Into ConfigAp_t (Categoria,Variable,Valor)Values(" + s + ");";
            dbs.execSQL(s);
            
            s = "'DataIn','Temperatura'," + "'0'";
            s = "Insert Into ConfigAp_t (Categoria,Variable,Valor)Values(" + s + ");";
            dbs.execSQL(s);
            
            s = "'DataIn','Heater'," + "'1'";
            s = "Insert Into ConfigAp_t (Categoria,Variable,Valor)Values(" + s + ");";
            dbs.execSQL(s);
            */
		}

		@Override
		public void onUpgrade(SQLiteDatabase dbs, int arg1, int arg2) {

		}
    	
    }
}
