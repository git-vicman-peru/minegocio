package mobapp.vic.minegocio;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobapp.vic.helpers.BolsaFrag;
import mobapp.vic.helpers.CatalogDetailItemFrag;
import mobapp.vic.helpers.PedidosFrag;
import mobapp.vic.helpers.ProdListAdapter;
import mobapp.vic.models.ProdForList;
import mobapp.vic.models.ProdItemBasic;
import mobapp.vic.services.ServHandler;
import mobapp.vic.utils.AppVars;
import mobapp.vic.utils.U;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link catalog_frag OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link catalog_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class catalog_frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lv;
    private ProdListAdapter adapter;

    private ProgressBar progbar;

    private ServHandler hServ;

    private LinearLayout bnSearch, lycatsearch;
    private EditText txsearch;
    private String srchGroup;

    public catalog_frag() {
        // Required empty public constructor
        this.hServ = new ServHandler();
        AppVars.setComingFrom("catalog");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment catalog_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static catalog_frag newInstance(String param1, String param2) {
        catalog_frag fragment = new catalog_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        this.loadItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.catalog_fraglayout, container,false);
        progbar = (ProgressBar)vw.findViewById(R.id.pbCatalogList);
        progbar.setVisibility(View.VISIBLE);
        txsearch = (EditText)vw.findViewById(R.id.txCatalogSearch);
        return vw;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout bnshowbag = (LinearLayout)view.findViewById(R.id.bnCatShowBag);
        bnshowbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppVars.bolsa_hasItems()){
                    U.pushToManager(getFragmentManager(), BolsaFrag.newInstance(),"bag");
                }else{
                    U.pushDialogPopup(getFragmentManager(),"Su Bolsa esta vacia!","bolsadlgnoti");
                }
            }
        });

        //---------------------------------------------------
        lv = (ListView)view.findViewById(R.id.lstCatalog);
        adapter = new ProdListAdapter(getActivity(), new ArrayList<ProdForList>());
        adapter.prodListener=new ProdListAdapter.ProductTileListener() {
            @Override
            public void onProdTileClick(ProdItemBasic qproduct) {
                ShowDetails(qproduct);
            }
        };
        lv.setAdapter(adapter);
        bagUpdate();

        bnSearch = (LinearLayout)view.findViewById(R.id.bnCatSearch);
        bnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lycatsearch.getVisibility()==View.VISIBLE){
                    lycatsearch.setVisibility(View.INVISIBLE);
                    lycatsearch.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_out));
                    U.hideSoftKeyboard(getActivity());
                }else {
                    lycatsearch.setVisibility(View.VISIBLE);
                    lycatsearch.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.left_in));
                    //txsearch.requestFocus();
                }
            }
        });
        lycatsearch = (LinearLayout)view.findViewById(R.id.lyCatalogSearch);
        lycatsearch.setVisibility(View.INVISIBLE);
        txsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                progbar.setVisibility(View.VISIBLE);
                loadSearch(srchGroup,charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txsearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bHasFocus) {
                if (bHasFocus){
                    U.SoftKeyboard(getActivity(),view,true);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadItems();
    }

    private void loadItems(){
        this.hServ.loadCatalog(new ServHandler.CatalogListener() {
            @Override
            public void onCatalogListLoaded(String[] groupnames, List<ProdForList> lstProds) {
                adapter.clear();
                adapter.addAll(lstProds);
                adapter.notifyDataSetChanged();
                progbar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCatalogListFailed() {
                progbar.setVisibility(View.INVISIBLE);
            }
        },null);
    }

    private void loadSearch(String sgrupo, String scrit){
        this.hServ.Catalog_Search(AppVars.EmpId, sgrupo, scrit, new ServHandler.CatalogListener() {
            @Override
            public void onCatalogListLoaded(String[] groupnames, List<ProdForList> lstProds) {
                adapter.clear();
                adapter.addAll(lstProds);
                adapter.notifyDataSetChanged();
                progbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCatalogListFailed() {

            }
        });
    }

    public void bagUpdate(){
        TextView v = (TextView)this.getView().findViewById(R.id.lbCatBagItemCount);
        v.setText(AppVars.bolsa_getItemsCountStr());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

        /*
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        */
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.hServ = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
   */



    private void ShowDetails(ProdItemBasic qproduct){
        CatalogDetailItemFrag frag = new CatalogDetailItemFrag();
        frag.setProduct(qproduct);
        getFragmentManager().beginTransaction()
                .add(R.id.base_fragholder,frag,"detail")
                .addToBackStack("detailbk")
                .commit();
        //.setCustomAnimations(R.animator.card_flip_right_in,R.animator.card_flip_right_out,R.animator.card_flip_left_in,R.animator.card_flip_left_out)
    }

    public void setCatalogSearchGroup(String sgroup){
        this.srchGroup = sgroup;
        this.hServ.setCatSearchGroup(sgroup);
    }

}
