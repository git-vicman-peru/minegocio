package mobapp.vic.helpers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.Pedido;
import mobapp.vic.models.PedidoDet;
import mobapp.vic.models.PedidoFix;
import mobapp.vic.utils.U;

/**
 * Created by VicDeveloper on 3/18/2017.
 */

public class PedidoResListAdapter extends BaseExpandableListAdapter {
    private List<PedidoFix> data;
    //private LayoutInflater inflater;
    private Context context;

    public PedidoResListAdapter(){

    }

    public PedidoResListAdapter(Context context){
        //this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public PedidoResListAdapter(Context context, List<PedidoFix> srclist){
        //this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = srclist;
    }

    public void setContext(Context context){
        //this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public PedidoDet getChild(int groupPos, int childPos) {
        return this.data.get(groupPos).getDetalles().get(childPos);
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        View vw = convertView;
        if (vw == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            vw = inflater.inflate(R.layout.pedidosdetalle_fragly,null);
        }
        PedidoDet pdet = this.data.get(groupPos).getDetalles().get(childPos);
        TextView lbnum = (TextView)vw.findViewById(R.id.lbPedResNum);
        lbnum.setText((childPos+1)+")");
        //ImageView ivfot = (ImageView)vw.findViewById(R.id.ivPedResFoto);
        //ivfot.setImageBitmap(pdet.ge);
        TextView lbcant = (TextView)vw.findViewById(R.id.lbPedResCantidad);
        String scant = U.rndZero(pdet.getCantidad()) + " " + pdet.getUnidad();
        lbcant.setText(scant);
        TextView lbdescrip = (TextView)vw.findViewById(R.id.lbPedResDescrip);
        lbdescrip.setText(pdet.getDescripcion());
        TextView lbprecio = (TextView)vw.findViewById(R.id.lbPedResPrecio);
        lbprecio.setText(pdet.getPrecio()+"");
        TextView lbsubt = (TextView)vw.findViewById(R.id.lbPedResSubtotal);
        lbsubt.setText(pdet.getSubtotalStr());
        return vw;
    }

    @Override
    public int getGroupCount() {
        int r;
        try{
            r = this.data.size();
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
            r = this.data.get(groupPos).getDetalles().size();
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
    public long getGroupId(int groupPos) {
        return groupPos;
    }

    @Override
    public PedidoFix getGroup(int groupPos) {
        return this.data.get(groupPos);
    }

    @Override
    public long getChildId(int groupPos, int childPos) {
        return childPos;
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertview, ViewGroup viewGroup) {
        View vw = convertview;
        if (vw == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            vw = inflater.inflate(R.layout.pedidosgralpart_fragly,null);
        }
        PedidoFix p = getGroup(groupPos);

        TextView num = (TextView)vw.findViewById(R.id.lbMPedResNumero);
        num.setText(p.getNumero());

        TextView fecha = (TextView)vw.findViewById(R.id.lbMPedResFecha);
        Date d = U.milliToDate(p.getFechaStr());
        fecha.setText(U.dateToStrFormat(p.getFechaStr(),"dd/MM/yyy"));

        TextView monto = (TextView)vw.findViewById(R.id.lbMPedResMonto);
        monto.setText(U.rndZero(p.getMonto()));

        TextView estado = (TextView)vw.findViewById(R.id.lbMPedResEstado);
        estado.setText(p.getEstado());

        return vw;
    }

    public void setData(List<PedidoFix> srcData){
        this.data = srcData;
    }
}
