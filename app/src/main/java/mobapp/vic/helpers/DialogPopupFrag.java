package mobapp.vic.helpers;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mobapp.vic.minegocio.R;

/**
 * Created by VicDeveloper on 3/12/2017.
 */

public class DialogPopupFrag extends Fragment {
    public static int PopupDlg_Short=1500;
    public static int PopupDlg_Long=3000;

    private String message;
    private int timeout;
    private boolean flgwarning;
    private DialogPopupListener mDialogPopupListener;

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getFragmentManager().popBackStack();
            if (mDialogPopupListener != null){
                mDialogPopupListener.onDialogPopupDone();
            }
        }
    };

    public void setDefinition(String smessage, int duration, boolean warning){
        this.message = smessage;
        this.timeout = duration;
        this.flgwarning = warning;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.dialogpopup_fragly,container,false);
        TextView lb = (TextView)vw.findViewById(R.id.lbPDlgMessage);
        lb.setText(this.message);
        if (flgwarning){
            lb.setTextColor(Color.RED);
        }else{
            lb.setTextColor(Color.BLACK);
        }
        return vw;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //handler.postAtTime(runnable,timeout);
        handler.postDelayed(runnable,timeout);
    }

    public void setListener(DialogPopupListener qlistener){
        this.mDialogPopupListener = qlistener;
    }

    public interface DialogPopupListener{
        void onDialogPopupDone();
    }
}
