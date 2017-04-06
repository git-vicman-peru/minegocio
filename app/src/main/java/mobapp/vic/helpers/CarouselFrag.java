package mobapp.vic.helpers;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import java.util.List;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.ProdItemBasic;
import mobapp.vic.services.ServHandler;

/**
 * Created by VicDeveloper on 3/3/2017.
 */

public class CarouselFrag extends Fragment {

    private ServHandler hServ;
    private ImageSwitcher ims;
    private List<ProdItemBasic> prods;
    private int proIdx;
    private Button bnprev, bnnext;
    private boolean visibleNavButtons, flgSwiping;

    private Animation leftin, leftout, rightin, rightout;
    private int aniIndex;

    private ProgressDialog progbar;

    public CarouselFrag(){
        this.hServ = new ServHandler();
        this.proIdx = -1;
        this.visibleNavButtons = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.hServ.loadCatalog(null, new ServHandler.CatalogSimpleListener() {
            @Override
            public void onCatalogSimpleLoaded(List<ProdItemBasic> lstSProds) {
                prods = lstSProds;
                if (prods != null) {
                    proIdx = 0;
                    showImage(proIdx,prods);
                }
                progbar.dismiss();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.carousel_fragly,container,false);
        this.ims = (ImageSwitcher)vw.findViewById(R.id.iswCarou);
        this.ims.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
                return imageView;
            }
        });
        final GestureDetector gest = new GestureDetector(getActivity(),new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //Log.i("APP_TAG", "onFling has been called!");
                final int SWIPE_MIN_DISTANCE = 120;
                final int SWIPE_MAX_OFF_PATH = 250;
                final int SWIPE_THRESHOLD_VELOCITY = 200;
                try {
                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                        return false;
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        //Log.i("APP_TAG", "Right to Left "+proIdx);
                        flgSwiping = true;
                        if ((proIdx + 1) < prods.size()){
                            if (aniIndex != 1) {
                                ims.setOutAnimation(leftin);
                                ims.setOutAnimation(leftout);
                            }
                            aniIndex = 1;
                            proIdx++;
                            showImage(proIdx,prods);
                        }
                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        //Log.i("APP_TAG", "Left to Right " + proIdx);
                        flgSwiping = true;
                        if ((proIdx - 1) >= 0){
                            if (aniIndex != 2) {
                                ims.setOutAnimation(rightin);
                                ims.setOutAnimation(rightout);
                            }
                            aniIndex = 2;
                            proIdx--;
                            showImage(proIdx,prods);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });



        vw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //Log.d("Touch event",motionEvent.getAction()+"");
                if (!flgSwiping) {
                    if (motionEvent.getAction() == 0) {
                        visibleNavButtons = !visibleNavButtons;
                        navButtons(visibleNavButtons);
                    }
                }
                flgSwiping = false;
                return gest.onTouchEvent(motionEvent);
            }
        });
        return vw;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progbar = new ProgressDialog(view.getContext());
        progbar.setMessage("Cargando Carusel ...");
        progbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progbar.show();
        //---------------------------------------------------
        bnprev = (Button)view.findViewById(R.id.bnCarouPrev);
        bnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((proIdx - 1) >= 0){
                    if (aniIndex != 2) {
                        ims.setOutAnimation(rightin);
                        ims.setOutAnimation(rightout);
                    }
                    aniIndex = 2;
                    proIdx--;
                    showImage(proIdx,prods);
                }
            }
        });
        bnnext = (Button)view.findViewById(R.id.bnCarouNext);
        bnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((proIdx + 1) < prods.size()){
                    if (aniIndex != 1) {
                        ims.setOutAnimation(leftin);
                        ims.setOutAnimation(leftout);
                    }
                    aniIndex = 1;
                    proIdx++;
                    showImage(proIdx,prods);
                }
            }
        });

        leftin = AnimationUtils.loadAnimation(getActivity(),R.anim.left_in);
        leftout = AnimationUtils.loadAnimation(getActivity(),R.anim.left_out);
        rightin = AnimationUtils.loadAnimation(getActivity(),R.anim.right_in);
        rightout = AnimationUtils.loadAnimation(getActivity(),R.anim.right_out);
        /*
        leftin = AnimationUtils.loadAnimation(getActivity(),R.animator.card_flip_left_in);
        leftout = AnimationUtils.loadAnimation(getActivity(),R.animator.card_flip_left_out);
        rightin = AnimationUtils.loadAnimation(getActivity(),R.animator.card_flip_right_in);
        rightout = AnimationUtils.loadAnimation(getActivity(),R.animator.card_flip_right_out);
*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.hServ = null;
    }

    public void setCatSearchGroup(String qcatsearch){
        this.hServ.setCatSearchGroup(qcatsearch);
    }

    private void showImage(int imgIdx, List<ProdItemBasic> srcList){
        ProdItemBasic itm = srcList.get(imgIdx);
        if (!itm.isImgNull()) {
            BitmapDrawable bmd = new BitmapDrawable(itm.getImg());
            ims.setImageDrawable(bmd);
        }else{
            ims.setImageResource(R.drawable.noimage);
        }
    }

    private void navButtons(boolean bshow){
        int r;
        r = (bshow?View.VISIBLE:View.INVISIBLE);
        this.bnprev.setVisibility(r);
        this.bnnext.setVisibility(r);
    }
}
