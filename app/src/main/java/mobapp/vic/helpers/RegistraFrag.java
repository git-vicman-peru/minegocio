package mobapp.vic.helpers;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.Cliente;
import mobapp.vic.models.ResultItem;
import mobapp.vic.services.ServHandler;
import mobapp.vic.utils.AppVars;
import mobapp.vic.utils.U;

/**
 * Created by VicDeveloper on 3/3/2017.
 */

public class RegistraFrag extends Fragment {

    //private static final String TAG = this.getClass().getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;

    private Uri uri;
    private String fotoname;
    private ImageView imgfoto;
    private byte[] fotoBytes;
    private EditText txnombres,txalias,txapellidos,txfechanac,txdirec,txzona,txciudad,txdni,txsexo,txecivil,txfono,txcorreo,txfacebook,txclave;
    private Button bngrabar;
    private ProgressBar pbar;

    public RegistraFrag(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.registra_ly,container,false);
        pbar = (ProgressBar)vw.findViewById(R.id.pbSubscribePBar);
        imgfoto = (ImageView)vw.findViewById(R.id.imgClientFoto);
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gal = new Intent(Intent.ACTION_PICK);
                gal.setType("image/*");
                startActivityForResult(gal, 200);
            }
        });
        txnombres = (EditText)vw.findViewById(R.id.txClieNombres);
        final TextView lbnombres = (TextView)vw.findViewById(R.id.lbClieNombres);
        txnombres.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbnombres.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txalias = (EditText)vw.findViewById(R.id.txClieAlias);
        final TextView lbalias = (TextView)vw.findViewById(R.id.lbClieAlias);
        txalias.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbalias.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txapellidos = (EditText)vw.findViewById(R.id.txClieApellidos);
        final TextView lbape = (TextView)vw.findViewById(R.id.lbClieApellidos);
        txapellidos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbape.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txfechanac = (EditText)vw.findViewById(R.id.txClieFechaNac);
        final TextView lbfechanac = (TextView)vw.findViewById(R.id.lbClieFechaNac);
        txfechanac.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbfechanac.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txdirec = (EditText)vw.findViewById(R.id.txClieDirec);
        final TextView lbdirec = (TextView)vw.findViewById(R.id.lbClieDirec);
        txdirec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbdirec.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txzona = (EditText)vw.findViewById(R.id.txClieZona);
        final TextView lbzona = (TextView)vw.findViewById(R.id.lbClieZona);
        txzona.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbzona.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txciudad = (EditText)vw.findViewById(R.id.txClieCiudad);
        final TextView lbciudad = (TextView)vw.findViewById(R.id.lbClieCiudad);
        txciudad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbciudad.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txdni = (EditText)vw.findViewById(R.id.txClieDni);
        final TextView lbdni = (TextView)vw.findViewById(R.id.lbClieDni);
        txdni.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbdni.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txsexo = (EditText)vw.findViewById(R.id.txClieSexo);
        final TextView lbsexo = (TextView)vw.findViewById(R.id.lbClieSexo);
        txsexo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbsexo.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txecivil = (EditText)vw.findViewById(R.id.txClieECivil);
        final TextView lbecivil = (TextView)vw.findViewById(R.id.lbClieECivil);
        txecivil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbecivil.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txfono = (EditText)vw.findViewById(R.id.txClieFono);
        final TextView lbfono = (TextView)vw.findViewById(R.id.lbClieFono);
        txfono.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbfono.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txcorreo = (EditText)vw.findViewById(R.id.txClieCorreo);
        final TextView lbcorreo = (TextView)vw.findViewById(R.id.lbClieCorreo);
        txcorreo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbcorreo.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txfacebook = (EditText)vw.findViewById(R.id.txClieFacebook);
        final TextView lbface = (TextView)vw.findViewById(R.id.lbClieFacebook);
        txfacebook.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbface.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });
        txclave = (EditText)vw.findViewById(R.id.txClieClave);
        final TextView lbclave = (TextView)vw.findViewById(R.id.lbClieClave);
        txclave.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lbclave.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                }
                U.SoftKeyboard(getActivity(),view,hasFocus);
            }
        });

        bngrabar = (Button)vw.findViewById(R.id.bnClieGrabar);
        bngrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pbar.setVisibility(View.VISIBLE);
                    ResultItem resi = hasErrors();

                    if (resi.getFlag()) {
                        U.pushDialogPopup(getFragmentManager(), resi.getMessage(), "regerrorcheck");
                        pbar.setVisibility(View.INVISIBLE);
                        return;
                    }

                    ServHandler srv = new ServHandler();
                    final Cliente c = readFrom();
                    c.setId_emp(AppVars.EmpId);
                    if (fotoBytes != null) {
                        Log.d("foto size", (fotoBytes.length * 0.001) + "");
                        c.setFoto64(Base64.encodeToString(fotoBytes, Base64.DEFAULT));
                        c.setFoto(fotoname);
                    }

                    if (AppVars.existLoginUser()) {
                        AppVars.logginUser = c;
                        srv.updateCliente(c, new ServHandler.ClientListener() {
                            @Override
                            public void onClientUploaded(Cliente qcli) {

                            }

                            @Override
                            public void onClientFailUpload(Cliente qclie, Throwable terr) {
                                pbar.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onClientUpdated(Boolean bresult) {
                                pbar.setVisibility(View.INVISIBLE);
                                if (bresult) {
                                    U.pushDialogPopup(getFragmentManager(), "Los Datos se Actualizados Correctamente!", "dlgclieupdate");
                                } else {
                                    U.pushWarningPopup(getFragmentManager(), "Hubo errores al Actualizar, revise e intente de nuevo!", "dlgclieupdate");
                                }

                            }
                        });
                    } else {
                        srv.sendCliente(c, new ServHandler.ClientListener() {
                            @Override
                            public void onClientUploaded(Cliente qcli) {
                                //Log.d("Cliente",qcli.toString());
                                c.setId_clie(qcli.getId_clie());
                                AppVars.logginUser = c;
                                pbar.setVisibility(View.INVISIBLE);
                                getFragmentManager().popBackStack();
                            }

                            @Override
                            public void onClientFailUpload(Cliente qclie, Throwable terr) {
                                Log.d("Cliente error", qclie.toString());
                                Log.d("Cliente message", terr.getMessage());
                            }

                            @Override
                            public void onClientUpdated(Boolean bresult) {

                            }
                        });
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                    pbar.setVisibility(View.INVISIBLE);
                }
            }
        });
        Button bnback = (Button)vw.findViewById(R.id.bnClieBack);
        bnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbar.setVisibility(View.INVISIBLE);
                getFragmentManager().popBackStack();
            }
        });

        LinearLayout ly = (LinearLayout)vw.findViewById(R.id.lyBnRegistrar);
        ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                U.hideSoftKeyboard(getActivity(),getView());
            }
        });
        return vw;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!AppVars.existLoginUser()) return;
        Cliente k = AppVars.logginUser;
        imgfoto.setImageBitmap(U.getBmpFromBase64(k.getFoto64()));
        txnombres.setText(k.getNombres());
        txalias.setText(k.getAlias());
        txapellidos.setText(k.getApellidos());
        txfechanac.setText(U.dateToStrFormat(k.getFechanac(),"dd/MM/yyy"));
        txdirec.setText(k.getDireccion());
        txzona.setText(k.getZona());
        txciudad.setText(k.getCiudad());
        txdni.setText(k.getDni());
        txsexo.setText(k.getSexo());
        txecivil.setText(k.getEcivil());
        txfono.setText(k.getFonos());
        txcorreo.setText(k.getEmail());
        txfacebook.setText(k.getFacebook());
        txclave.setText(k.getClave());
        bngrabar.setText("Actualizar");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.getData();

            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(uri,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //Log.d("PicturePath",picturePath);
            int l = picturePath.length();
            String ext = picturePath.substring((l-3),l).toLowerCase();
            //Log.d("extension",ext.toLowerCase());
            String[] a = picturePath.split("/");
            fotoname = a[a.length-1];
//Log.d("fotoname",fotoname);
            try{
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                this.imgfoto.setImageBitmap(bm);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if (ext.equals("png")){
                    bm.compress(Bitmap.CompressFormat.PNG,100,baos);
                }else{
                    bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
                }
                fotoBytes = baos.toByteArray();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    private ResultItem hasErrors(){
        ResultItem r = new ResultItem();
        r.setFlag(false);
        if (U.editTextHasNullStr(txnombres)){
            r.setFlag(true);
            r.setControl(txnombres);
            r.setMessage("Falta ingresar Nombres!");
            return r;
        }
        if (U.editTextHasNullStr(txalias)){
            r.setFlag(true);
            r.setControl(txalias);
            r.setMessage("Falta ingresar Alias o Nick!");
            return r;
        }
        if (U.editTextHasNullStr(txapellidos)){
            r.setFlag(true);
            r.setControl(txapellidos);
            r.setMessage("Falta ingresar Apellidos!");
            return r;
        }
        if (U.editTextHasNullStr(txfechanac)){
            r.setFlag(true);
            r.setControl(txfechanac);
            r.setMessage("Falta ingresar FechaNac!");
            return r;
        }
        if (!U.editTextHasDate(txfechanac)){
            r.setFlag(true);
            r.setControl(txfechanac);
            r.setMessage("El formato de fecha es incorrecto!");
            return r;
        }
        if (U.editTextHasNullStr(txdirec)){
            r.setFlag(true);
            r.setControl(txdirec);
            r.setMessage("Falta ingresar Direccion!");
            return r;
        }
        if (U.editTextHasNullStr(txzona)){
            r.setFlag(true);
            r.setControl(txzona);
            r.setMessage("Falta ingresar Zona o Distrito!");
            return r;
        }
        if (U.editTextHasNullStr(txciudad)){
            r.setFlag(true);
            r.setControl(txciudad);
            r.setMessage("Falta ingresar Ciudad!");
            return r;
        }
        if (U.editTextHasNullStr(txdni)){
            r.setFlag(true);
            r.setControl(txdni);
            r.setMessage("Falta ingresar el numero del DNI");
            return r;
        }
        if (U.editTextHasNullStr(txsexo)){
            r.setFlag(true);
            r.setControl(txsexo);
            r.setMessage("Falta ingresar Sexo !");
            return r;
        }
        if (U.editTextHasNullStr(txecivil)){
            r.setFlag(true);
            r.setControl(txecivil);
            r.setMessage("Falta ingresar Estado Civil !");
            return r;
        }
        if (U.editTextHasNullStr(txfono)){
            r.setFlag(true);
            r.setControl(txfono);
            r.setMessage("Falta ingresar Telefono!");
            return r;
        }
        if (U.editTextHasNullStr(txcorreo)){
            r.setFlag(true);
            r.setControl(txcorreo);
            r.setMessage("Falta ingresar Correo Electronico!");
            return r;
        }
        if (!U.editTextHasValidEMail(txcorreo)){
            r.setFlag(true);
            r.setControl(txcorreo);
            r.setMessage("El Correo tiene formato incorrecto!");
            return r;
        }
        if (U.editTextHasNullStr(txclave)){
            r.setFlag(true);
            r.setControl(txclave);
            r.setMessage("Falta ingresar una Clave !");
            return r;
        }
        /*
        if (fotoBytes == null){
            r.setFlag(true);
            r.setControl(imgfoto);
            r.setMessage("Falta Foto !");
            return r;
        }*/

        return r;
    }

    private Cliente readFrom(){
        Cliente c = new Cliente();
        try {
            c.setId_clie(AppVars.logginUser.getId_clie());
        }catch (Exception ex){}
        c.setNombres(txnombres.getText().toString());
        c.setAlias(txalias.getText().toString());
        c.setApellidos(txapellidos.getText().toString());
        Date d = U.dateTryParse(txfechanac.getText().toString());
        if (d==null) d = new Date(Calendar.getInstance().getTimeInMillis());
        c.setFechanac(d.getTime()+"");
        c.setDireccion(txdirec.getText().toString());
        c.setZona(txzona.getText().toString());
        c.setCiudad(txciudad.getText().toString());
        c.setDni(txdni.getText().toString());
        c.setSexo(txsexo.getText().toString());
        c.setEcivil(txecivil.getText().toString());
        c.setFonos(txfono.getText().toString());
        c.setEmail(txcorreo.getText().toString());
        c.setFacebook(txfacebook.getText().toString());
        c.setClave(txclave.getText().toString());

        /*
        c.setNombres("Kim");
        c.setAlias("Kimie");
        c.setApellidos("Kardasian");
        c.setDireccion("Jr Anadu #829");
        c.setZona("Calatayu");
        c.setCiudad("Novato");
        c.setDni("00939388");
        c.setSexo("Femenino");
        c.setEcivil("Soltera");
        c.setFonos("83929983");
        c.setEmail("kim@gmail.com");
        c.setFacebook("kimie298");
        */
        return c;
    }



}
