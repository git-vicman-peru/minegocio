package mobapp.vic.helpers;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.ResultItem;
import mobapp.vic.utils.AppVars;
import mobapp.vic.utils.U;

/**
 * Created by VicDeveloper on 3/10/2017.
 */

public class PedidoSend1Frag extends Fragment{

    private EditText txfechaped, txclie, txfechaentrega, txfono, txdirec, txcorreo, txobs;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.pedido_send1_fragly,container,false);
        return vw;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txfechaped = (EditText)view.findViewById(R.id.txPedFechaPedido);
        txfechaped.setText(U.nowStr("dd/MM/yyy"));
        txclie = (EditText)view.findViewById(R.id.txPedCliente);
        txclie.setText(AppVars.logginUser.getShortRef());
        txfechaentrega = (EditText)view.findViewById(R.id.txPedFechaEntrega);
        txfechaentrega.setText(U.nowPlus(1,"dd/MM/yyy"));
        txfechaentrega.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bHasFocus) {
                if (bHasFocus) {
                    U.showSoftKeyboard(getActivity(),view);
                }/*else{
                    U.hideSoftKeyboard(getActivity(),view);
                }*/
            }
        });
        txfono = (EditText)view.findViewById(R.id.txPedFono);
        txfono.setText(AppVars.logginUser.getFonos());
        txfono.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bHasFocus) {
                if (bHasFocus) {
                    U.showSoftKeyboard(getActivity(),view);
                }
            }
        });

        txdirec = (EditText)view.findViewById(R.id.txPedDirecEntrega);
        txdirec.setText(AppVars.logginUser.getDireccion());
        txdirec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bHasFocus) {
                if (bHasFocus) {
                    U.showSoftKeyboard(getActivity(),view);
                }/*else{
                    U.hideSoftKeyboard(getActivity(),view);
                }*/
            }
        });
        txcorreo = (EditText)view.findViewById(R.id.txPedCorreo);
        txcorreo.setText(AppVars.logginUser.getEmail());
        txcorreo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bHasFocus) {
                if (bHasFocus) {
                    U.showSoftKeyboard(getActivity(),view);
                }/*else{
                    U.hideSoftKeyboard(getActivity(),view);
                }*/
            }
        });
        txobs = (EditText)view.findViewById(R.id.txPedObs);
        txobs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bHasFocus) {
                if (bHasFocus) {
                    U.showSoftKeyboard(getActivity(),view);
                }/*else{
                    U.hideSoftKeyboard(getActivity(),view);
                }*/
            }
        });

        Button bnprev = (Button)view.findViewById(R.id.bnPedSendPrev);
        bnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        Button bncancel = (Button)view.findViewById(R.id.bnPedSendCancel);
        bncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        Button bnnext = (Button)view.findViewById(R.id.bnPedSendNext);
        bnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultItem r = hasErrors();
                if (r.getFlag()){
                    U.pushDialogPopup(getFragmentManager(),r.getMessage(),"PedCheckError");
                }else {
                    AppVars.Pedido_SetIDEmp(AppVars.EmpId);
                    AppVars.Pedido_SetIDClie(AppVars.logginUser.getId_clie());
                    AppVars.Pedido_SetFechaStr(U.dateTryParse(txfechaped.getText().toString()));
                    AppVars.Pedido_SetFechaEntregaStr(U.dateTryParse(txfechaentrega.getText().toString()));
                    AppVars.Pedido_SetFono(txfono.getText().toString());
                    AppVars.Pedido_SetDirecEntrega(txdirec.getText().toString());
                    AppVars.Pedido_SetCorreo(txcorreo.getText().toString());
                    AppVars.Pedido_SetObs(txobs.getText().toString());
                    AppVars.Pedido_SetEstado("Enviando");

                    PedidoSend2Frag frag = new PedidoSend2Frag();
                    getFragmentManager().beginTransaction()
                            .addToBackStack("pedsend2bk")
                            .replace(R.id.base_fragholder, frag, "pedsend2")
                            .commit();
                }
            }
        });
    }

    private ResultItem hasErrors(){
        ResultItem r = new ResultItem();
        r.setFlag(false);
        if (U.editTextHasNullStr(txdirec)){
            r.setFlag(true);
            r.setControl(txdirec);
            r.setMessage("Falta indicar una direccion valida de entrega");
            return r;
        }
        if (U.editTextHasNullStr(txfechaentrega)){
            r.setFlag(true);
            r.setControl(txfechaentrega);
            r.setMessage("Falta indicar la fecha de entrega");
            return r;
        }
        if (!U.editTextHasDate(txfechaentrega)){
            r.setFlag(true);
            r.setControl(txfechaentrega);
            r.setMessage("La fecha de entrega tiene formato incorrecto");
            return r;
        }
        if (U.editTextHasNullStr(txfono)){
            r.setFlag(true);
            r.setControl(txfono);
            r.setMessage("Falta indicar un numero telefonico valido");
            return r;
        }
        if (U.editTextHasNullStr(txcorreo)){
            r.setFlag(true);
            r.setControl(txcorreo);
            r.setMessage("Falta indicar un Correo Electronico");
            return r;
        }
        if (!U.editTextHasValidEMail(txcorreo)){
            r.setFlag(true);
            r.setControl(txcorreo);
            r.setMessage("El Correo tiene formato incorrecto");
            return r;
        }
        if (!U.editTextIsDateAfter(txfechaentrega,txfechaped)){
            r.setFlag(true);
            r.setControl(txfechaentrega);
            r.setMessage("Fecha de entrega tiene que ser mayor que la fecha de pedido.");
            return r;
        }
        return r;
    }
}
