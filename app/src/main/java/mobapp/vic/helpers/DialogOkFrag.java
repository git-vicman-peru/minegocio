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
 * Created by VicDeveloper on 3/16/2017.
 */

public class DialogOkFrag extends Fragment {

    private IDialogOkListener mdialogOkListener;
    private String message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.dialogok_fragly,container,false);
        return vw;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView lbmsg = (TextView)view.findViewById(R.id.lbDlgOkMessage);
        lbmsg.setText(message);
        Button bnok = (Button)view.findViewById(R.id.bnDlgOk);
        bnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mdialogOkListener != null){
                    mdialogOkListener.onOkPressed();
                }
            }
        });
    }

    public void setMessage(String smsg){
        this.message = smsg;
    }

    public void setDialogOkListener(IDialogOkListener qdlglistener){
        this.mdialogOkListener = qdlglistener;
    }



    public static DialogOkFrag newInstance(String message){
        DialogOkFrag f = new DialogOkFrag();
        f.setMessage(message);
        return f;
    }


    public interface IDialogOkListener{
        void onOkPressed();
    }
}
