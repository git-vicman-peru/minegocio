package mobapp.vic.helpers;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.Cliente;
import mobapp.vic.models.UserLogin;
import mobapp.vic.services.ServHandler;
import mobapp.vic.utils.AppVars;
import mobapp.vic.utils.U;

/**
 * Created by VicDeveloper on 3/2/2017.
 */

public class LoginFrag extends Fragment {

    private EditText txusuario, txpass;
    private ILoginListener mloginlistener;
    private boolean loginOk;

    public LoginFrag(){
        AppVars.setComingFrom("login");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return inflater.inflate(R.layout.login_ly, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txusuario = (EditText)view.findViewById(R.id.txLoginUsuario);
        txusuario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                U.showSoftKeyboard(getActivity(),view);
            }
        });
        txpass = (EditText)view.findViewById(R.id.txLoginClave);
        txpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                U.showSoftKeyboard(getActivity(),view);
            }
        });
        Button bningre = (Button)view.findViewById(R.id.bnLoginOk);
        bningre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (U.editTextHasNullStr(txusuario)){
                    return;
                }
                if (U.editTextHasNullStr(txpass)){
                    return;
                }

                ServHandler srv = new ServHandler();
                UserLogin lu = new UserLogin();
                lu.setUsername(txusuario.getText().toString());
                lu.setPass(txpass.getText().toString());

                srv.checkLogin(lu, new ServHandler.LoginListener() {
                    @Override
                    public void onLoginReceived(Cliente qcli) {
                        loginOk = false;
                        if (qcli != null){
                            if (qcli.getId_clie()>0) {
                                AppVars.logginUser = qcli;
                                //Log.d("login client",qcli.toString());
                                loginOk = true;
                                if (mloginlistener != null) {
                                    mloginlistener.Login_Successful();
                                }
                                txpass.setText("");

                                getFragmentManager().popBackStack();
                            }
                        }else{
                            Log.d("login client","failed!!");
                        }
                    }

                    @Override
                    public void onLoginFailed(String errmsg) {
                        loginOk = false;
                        if (mloginlistener != null) {
                            mloginlistener.Login_Failed();
                        }
                        Log.d("login client","failed xx!!");
                    }
                });
            }
        });
        Button bncancel = (Button)view.findViewById(R.id.bnLoginCancel);
        bncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        LinearLayout lybase = (LinearLayout)view.findViewById(R.id.lyLoginBase);
        lybase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                U.hideSoftKeyboard(getActivity());
            }
        });
    }

    public void setListener(ILoginListener loginListener){
        this.mloginlistener = loginListener;
    }



    public interface ILoginListener{
        void Login_Successful();
        void Login_Failed();
    }
}
