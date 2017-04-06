package mobapp.vic.helpers;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import mobapp.vic.minegocio.R;

/**
 * Created by VicDeveloper on 3/11/2017.
 */

public class DialogFrag extends Fragment {

    private IDialogListener mdialogListener;
    private String message;

    public void DialogFrag(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.dialog_gralfragly,container,false);
        return vw;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView lbmessage = (TextView)view.findViewById(R.id.lbDlgMessage);
        lbmessage.setText(message);
        Button bnyes = (Button)view.findViewById(R.id.bnDlgYes);
        bnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mdialogListener != null){
                    mdialogListener.dlgAnswer(true);
                }
                getFragmentManager().popBackStack();
            }
        });
        Button bnno = (Button)view.findViewById(R.id.bnDlgNo);
        bnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mdialogListener != null){
                    mdialogListener.dlgAnswer(false);
                }
                getFragmentManager().popBackStack();
            }
        });
    }

    public void setMessage(String smsg){
        this.message = smsg;
    }

    public void setDialogListener(IDialogListener qlistener){
        this.mdialogListener = qlistener;
    }

    public void setDialogListener(IDialogListener qlistener, String smessage){
        this.mdialogListener = qlistener;
        this.message = smessage;
    }

    public static DialogFrag newInstance(){
        DialogFrag f = new DialogFrag();
        return f;
    }

    public static DialogFrag newInstance(String message){
        DialogFrag f = new DialogFrag();
        f.setMessage(message);
        return f;
    }

    public interface IDialogListener{
        void dlgAnswer(boolean byes);
    }
}
