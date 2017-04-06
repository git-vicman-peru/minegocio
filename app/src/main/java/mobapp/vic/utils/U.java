package mobapp.vic.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mobapp.vic.helpers.DialogFrag;
import mobapp.vic.helpers.DialogOkFrag;
import mobapp.vic.helpers.DialogPopupFrag;
import mobapp.vic.minegocio.R;

/**
 * Created by VicDeveloper on 3/6/2017.
 */

public class U {

    private static DialogPopupFrag.DialogPopupListener mDialogPopupListener;
    private static DialogFrag.IDialogListener mYesNoDlgListener;
    private static DialogOkFrag.IDialogOkListener mOkDlgListener;

    public static void pushToManager(FragmentManager mngr, Fragment qfragment, String key){
        mngr.beginTransaction()
                .addToBackStack(key+"bk")
                .add(R.id.base_fragholder, qfragment, key)
                .commit();
    }

    public static void pushDialogPopup(FragmentManager mngr, String message, String key){
        DialogPopupFrag dlg = new DialogPopupFrag();
        dlg.setDefinition(message, DialogPopupFrag.PopupDlg_Long,false);
        mngr.beginTransaction()
                .addToBackStack(key+"bk")
                .add(R.id.base_fragholder, dlg, key)
                .commit();
    }

    public static void pushWarningPopup(FragmentManager mngr, String message, String key){
        DialogPopupFrag dlg = new DialogPopupFrag();
        dlg.setDefinition(message, DialogPopupFrag.PopupDlg_Long,true);
        mngr.beginTransaction()
                .addToBackStack(key+"bk")
                .add(R.id.base_fragholder, dlg, key)
                .commit();
    }

    public static void pushShortDlgPopup(FragmentManager mngr, String message, String key){
        DialogPopupFrag dlg = new DialogPopupFrag();
        dlg.setDefinition(message, DialogPopupFrag.PopupDlg_Short,false);
        mngr.beginTransaction()
                .addToBackStack(key+"bk")
                .add(R.id.base_fragholder, dlg, key)
                .commit();
    }

    public static void pushShortWarningPopup(FragmentManager mngr, String message, String key){
        DialogPopupFrag dlg = new DialogPopupFrag();
        dlg.setDefinition(message, DialogPopupFrag.PopupDlg_Short,true);
        mngr.beginTransaction()
                .addToBackStack(key+"bk")
                .add(R.id.base_fragholder, dlg, key)
                .commit();
    }

    public static void pushDialogPopup(FragmentManager mngr, String message, String key, DialogPopupFrag.DialogPopupListener qdlgListener){
        mDialogPopupListener = qdlgListener;
        DialogPopupFrag dlg = new DialogPopupFrag();
        dlg.setDefinition(message, DialogPopupFrag.PopupDlg_Long,false);
        dlg.setListener(new DialogPopupFrag.DialogPopupListener() {
            @Override
            public void onDialogPopupDone() {
                if (mDialogPopupListener != null){
                    mDialogPopupListener.onDialogPopupDone();
                }
            }
        });
        mngr.beginTransaction()
                .addToBackStack(key+"bk")
                .add(R.id.base_fragholder, dlg, key)
                .commit();
    }

    public static void pushYesNoDialog(FragmentManager mngr, String smessage, String key, DialogFrag.IDialogListener qlistener){
        mYesNoDlgListener = qlistener;
        DialogFrag dlg = new DialogFrag();
        dlg.setMessage(smessage);
        dlg.setDialogListener(new DialogFrag.IDialogListener() {
            @Override
            public void dlgAnswer(boolean byes) {
                if (mYesNoDlgListener != null){
                    mYesNoDlgListener.dlgAnswer(byes);
                }
            }
        });
        mngr.beginTransaction()
                .addToBackStack(key+"bk")
                .add(R.id.base_fragholder, dlg, key)
                .commit();
    }

    public static void pushOkDialog(FragmentManager mngr, String smessage, String key, DialogOkFrag.IDialogOkListener qlistener){
        mOkDlgListener = qlistener;
        DialogOkFrag dlg = new DialogOkFrag();
        dlg.setMessage(smessage);
        dlg.setDialogOkListener(new DialogOkFrag.IDialogOkListener() {
            @Override
            public void onOkPressed() {
                if (mOkDlgListener != null){
                    mOkDlgListener.onOkPressed();
                }
            }
        });
        mngr.beginTransaction()
                .addToBackStack(key+"bk")
                .add(R.id.base_fragholder, dlg, key)
                .commit();
    }


    public static void hideSoftKeyboard(Activity activity, View qview) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        imm.hideSoftInputFromInputMethod(qview.getWindowToken(),0);
    }

    public static void hideSoftKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Log.d("isActive",imm.isActive()+"");
        if (imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
            imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
        }

        /*
        if (imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
        } else {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // show
        }
        */
    }

    public static void showSoftKeyboard(Activity activity, View qview){
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null){
            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            imm.showSoftInput(qview, 0);
        }
    }

    public static void SoftKeyboard(Activity activity, View qview, boolean HasFocus){
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null){
            if (HasFocus){
                imm.showSoftInput(qview, 0);
            }else{
                imm.hideSoftInputFromInputMethod(qview.getWindowToken(),0);
            }

        }
    }


    public static boolean isFloat(String svalue){
        boolean r = false;
        try{
            Float.parseFloat(svalue);
        }catch (Exception e){
            r = false;
        }
        return r;
    }

    public static float tryFloat(String svalue, float defaultval){
        float r = 0;
        try{
            r = Float.parseFloat(svalue);
        }catch (Exception e){
            r = defaultval;
        }
        return r;
    }

    public static boolean isEMailOk(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static Date dateTryParse(String strDateValue){
        //entry: dd/mm/year
        //goal : mm-dd-year (server format)
        Date dt = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int d,m,y;
        String[] a;
        int i = strDateValue.indexOf("/");
        if (i>-1){
            try {
                a = strDateValue.split("/");
                d = Integer.parseInt(a[0]);
                m = Integer.parseInt(a[1]);
                y = Integer.parseInt(a[2]);
                strDateValue = String.format("%s-%s-%s",y,m,d);
                Log.d("strDateValue",strDateValue);
                dt = df.parse(strDateValue);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }else{
            i = strDateValue.indexOf("-");
            if (i>-1){
                try {
                    a = strDateValue.split("-");
                    d = Integer.parseInt(a[0]);
                    m = Integer.parseInt(a[1]);
                    y = Integer.parseInt(a[2]);
                    strDateValue = String.format("%s-%s-%s",y,m,d);
                    dt = df.parse(strDateValue);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
/*
        if (dt != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
        }
*/
        return dt;
    }

    public static String dateToStrFormat(String millis, String format){
        long ml = Long.parseLong(millis);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ml);
        Date d = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(d);
    }

    public static String dateToMillis(Date qfecha){
        Calendar cal = Calendar.getInstance();
        cal.setTime(qfecha);
        return cal.getTimeInMillis()+"";
    }

    public static Date milliToDate(String millis){
        long ml = Long.parseLong(millis);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ml);
        return cal.getTime();
    }

    public static Date milliToDate(long millisecs){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millisecs);
        return cal.getTime();
    }

    public static long nowMillis(){
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }

    public static String nowStr(String dateFormat){
        Calendar cal = Calendar.getInstance();
        Date d = new Date(cal.getTimeInMillis());
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(d);
    }

    public static String nowStrMillis(){
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis()+"";
    }

    public static String nowPlus(int addDays, String dateFormat){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,addDays);
        Date d = new Date(cal.getTimeInMillis());
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(d);
    }

    public static Date strToDate(String strDate, String sep){
        Date d = new Date();

        return d;
    }

    public static String dateToStr(Date qdate, String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(qdate);
    }

    public static void setBmpFromBase64(String ssbase64, ImageView qiview){
        try {
            byte[] bytes = Base64.decode(ssbase64.getBytes(), Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            qiview.setImageBitmap(bmp);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static boolean editTextHasDate(EditText qet){
        String v = qet.getText().toString().trim();
        Date d = dateTryParse(v);
        if (d == null)
            return false;
        return true;
    }

    public static boolean editTextHasNullStr(EditText qet){
        if (qet == null) return true;
        if (qet.getText()==null) return true;
        String r = qet.getText().toString().trim();
        return (r.isEmpty());
    }

    public static boolean editTextHasValidEMail(EditText qet){
        if (qet == null) return false;
        if (qet.getText()==null) return false;
        String r = qet.getText().toString().trim();
        return isEMailOk(r);
    }

    public static boolean editTextIsDateAfter(EditText dateAfter, EditText baseDate){
        Date dafter = dateTryParse(dateAfter.getText().toString());
        Date bdate = dateTryParse(baseDate.getText().toString());
        return dafter.after(bdate);
    }

    public static float editTextGetDecimal(EditText qet){
        float d = Float.parseFloat(qet.getText().toString());
        return d;
    }

    public static int editTextGetInt(EditText qet){
        int d = Integer.parseInt(qet.getText().toString());
        return d;
    }

    public static String editTextGetStr(EditText qet){
        String d = qet.getText().toString();
        return d;
    }

    public static boolean editTextHasNumber(EditText qet){
        String d;
        try {
            d = qet.getText().toString().trim();
        }catch (Exception ex){
            return false;
        }
        if (d == "") return false;
        try {
            float f = Float.parseFloat(d);
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public static boolean IsNetworkAvailable(Activity aty){
        ConnectivityManager connectivityManager = (ConnectivityManager)aty.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Bitmap getBmpFromBase64(String ssbase64){
        byte[] bytes = Base64.decode(ssbase64,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    public static String rndZero(float qval){
        if (qval == (long)qval){
            return String.format("%d",(long)qval);
        }else{
            return String.format("%s",qval);
        }
    }
}
