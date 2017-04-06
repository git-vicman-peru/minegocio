package mobapp.vic.helpers;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.ClieBlog;
import mobapp.vic.models.ClieBlogRev;
import mobapp.vic.models.ProdItemBasic;
import mobapp.vic.services.ServHandler;
import mobapp.vic.utils.AppVars;
import mobapp.vic.utils.U;

/**
 * Created by VicDeveloper on 3/12/2017.
 */

public class BlogItFrag extends Fragment {

    private ProdItemBasic selectedProd;
    private EditText txblogit;
    private Button bn1,bn2,bn3,bn4,bn5;
    private int rating;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.blogit_fagly,container,false);
        txblogit = (EditText)vw.findViewById(R.id.txBlogIt);
        txblogit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                U.showSoftKeyboard(getActivity(),view);
            }
        });
        LinearLayout ly = (LinearLayout)vw.findViewById(R.id.llyBlogIt);
        ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                U.hideSoftKeyboard(getActivity(),view);
            }
        });
        bn1 = (Button)vw.findViewById(R.id.bnBlogStar1);
        bn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStars();
                bn1.setBackgroundResource(R.drawable.starselected);

                rating = 1;
            }
        });
        bn2 = (Button)vw.findViewById(R.id.bnBlogStar2);
        bn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStars();
                bn1.setBackgroundResource(R.drawable.starselected);
                bn2.setBackgroundResource(R.drawable.starselected);

                rating = 2;
            }
        });
        bn3 = (Button)vw.findViewById(R.id.bnBlogStar3);
        bn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStars();
                bn1.setBackgroundResource(R.drawable.starselected);
                bn2.setBackgroundResource(R.drawable.starselected);
                bn3.setBackgroundResource(R.drawable.starselected);

                rating = 3;
            }
        });
        bn4 = (Button)vw.findViewById(R.id.bnBlogStar4);
        bn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStars();
                bn1.setBackgroundResource(R.drawable.starselected);
                bn2.setBackgroundResource(R.drawable.starselected);
                bn3.setBackgroundResource(R.drawable.starselected);
                bn4.setBackgroundResource(R.drawable.starselected);

                rating = 4;
            }
        });
        bn5 = (Button)vw.findViewById(R.id.bnBlogStar5);
        bn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStars();
                bn1.setBackgroundResource(R.drawable.starselected);
                bn2.setBackgroundResource(R.drawable.starselected);
                bn3.setBackgroundResource(R.drawable.starselected);
                bn4.setBackgroundResource(R.drawable.starselected);
                bn5.setBackgroundResource(R.drawable.starselected);
                rating = 5;
            }
        });
        Button bnpub = (Button)vw.findViewById(R.id.bnBlogPublish);
        bnpub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServHandler srv;
                ClieBlog bi;
                if (U.editTextHasNullStr(txblogit)){
                    U.pushDialogPopup(getFragmentManager(),"Debe escribir un comentario!","msgDlgBlog");
                    return;
                }
                bi = new ClieBlog();
                bi.setId_emp(AppVars.EmpId);
                bi.setId_clie(AppVars.logginUser.getId_clie());
                bi.setId_prod(selectedProd.getId_prod());
                bi.setFecha(U.nowStrMillis());
                bi.setComentario(txblogit.getText().toString());
                bi.setRate(rating+"");
                srv = new ServHandler();
                srv.Blog_SaveComment(bi, new ServHandler.BlogListener() {
                    @Override
                    public void onBlogSaved() {
                        U.pushDialogPopup(getFragmentManager(), "Comentario grabado, correctamente!", "msgDlgBlogOK", new DialogPopupFrag.DialogPopupListener() {
                            @Override
                            public void onDialogPopupDone() {
                                getFragmentManager().popBackStack();
                            }
                        });
                    }

                    @Override
                    public void onBlogFailed() {
                        U.pushDialogPopup(getFragmentManager(), "No se pudo grabar su Comentario, intente de nuevo!", "msgDlgBlogWrong", new DialogPopupFrag.DialogPopupListener() {
                            @Override
                            public void onDialogPopupDone() {
                                getFragmentManager().popBackStack();
                            }
                        });
                    }

                    @Override
                    public void onBlogsReceived(List<ClieBlogRev> blogs) {

                    }
                });

            }
        });
        return vw;
    }

    private void resetStars(){
        bn1.setBackgroundResource(R.drawable.starnone);
        bn2.setBackgroundResource(R.drawable.starnone);
        bn3.setBackgroundResource(R.drawable.starnone);
        bn4.setBackgroundResource(R.drawable.starnone);
        bn5.setBackgroundResource(R.drawable.starnone);
    }

    public void setProduct(ProdItemBasic qprod){
        this.selectedProd = qprod;
    }

    public static BlogItFrag newInstance(){
        BlogItFrag f = new BlogItFrag();
        return f;
    }

    public static BlogItFrag newInstance(ProdItemBasic qprod){
        BlogItFrag f = new BlogItFrag();
        f.setProduct(qprod);
        return f;
    }
}
