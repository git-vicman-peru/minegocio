package mobapp.vic.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.FavoritoLVGroup;
import mobapp.vic.models.FavoritoLVItem;
import mobapp.vic.models.FavoritoLVSubitem;
import mobapp.vic.utils.U;

/**
 * Created by VicDeveloper on 3/14/2017.
 */

public class FavListAdapter extends BaseExpandableListAdapter {
    private FavoritoLVGroup data;
    private LayoutInflater inflater;

    public FavListAdapter(){

    }

    public FavListAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
    }

    public FavListAdapter(Context context, FavoritoLVGroup list){
        this.inflater = LayoutInflater.from(context);
        this.data = list;
    }

    public void setContext(Context context){
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        View vw = convertView;
        if (vw == null){
            vw = inflater.inflate(R.layout.favorito_itemly,null);
        }
        FavoritoLVSubitem h = getChild(groupPos,childPos);
        ImageView foto = (ImageView)vw.findViewById(R.id.imgFavItemFoto);
        TextView nombre = (TextView)vw.findViewById(R.id.lbFavItemNombre);
        TextView fecha = (TextView)vw.findViewById(R.id.lbFavItemFecha);
        foto.setImageBitmap(h.getImg());
        nombre.setText(h.getNombre());
        fecha.setText(U.dateToStr(h.getFecha(),"dd/MM/yyyy"));
        vw.setTag(h.getProdref());
        return vw;
    }

    @Override
    public boolean isChildSelectable(int groupPos, int childPos) {
        return true;
    }

    @Override
    public int getGroupCount() {
        int r;
        try{
            r = this.data.count();
        }catch (Exception ex){
            r = 0;
            ex.printStackTrace();
        }
        return r;
    }

    @Override
    public int getChildrenCount(int groupPos) {
        int r;
        try{
            r = this.data.getFav(groupPos).getItems().size();
        }catch (Exception ex){
            r = 0;
            ex.printStackTrace();
        }
        return r;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public FavoritoLVSubitem getChild(int groupPos, int childPos) {
        return this.data.getFav(groupPos).getItems().get(childPos);
    }

    @Override
    public long getGroupId(final int groupPosition) {
        return groupPosition;
    }

    @Override
    public FavoritoLVItem getGroup(int groupPos) {
        return this.data.getFav(groupPos);
    }

    @Override
    public long getChildId(int groupPos, int childPos) {
        return childPos;
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertview, ViewGroup viewGroup) {
        View vw = convertview;
        if (vw == null){
            vw = inflater.inflate(R.layout.favorito_grouply,null);
        }
        FavoritoLVItem g = getGroup(groupPos);
        TextView nom = (TextView)vw.findViewById(R.id.lbFavGrpNombre);
        nom.setText(g.getGrupo());
        //vw.setTag(g);
        return vw;
    }

    public void setData(FavoritoLVGroup favgrp){
        this.data = favgrp;
    }
}
