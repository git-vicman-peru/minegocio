package mobapp.vic.helpers;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.DrawerCategItem;

/**
 * Created by VicDeveloper on 3/1/2017.
 */

public class CategoryListAdapter extends ArrayAdapter<DrawerCategItem> {
    final private List<DrawerCategItem> lst;
    private final Activity ctx;

    public CarouselListener carouselListener;

    public CategoryListAdapter(Activity act, List<DrawerCategItem>list){
        super(act, R.layout.dw_categ_groupitem_ly,list);
        this.ctx = act;
        this.lst = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vw = convertView;
        if (convertView == null){
            LayoutInflater inflator = this.ctx.getLayoutInflater();
            vw = inflator.inflate(R.layout.dw_categ_groupitem_ly,null);
        }
        DrawerCategItem item = this.getItem(position);
        TextView txIcon = (TextView)vw.findViewById(R.id.txDwCategGroupLabelItem);
        TextView bnCaru = (TextView)vw.findViewById(R.id.bnDwCategGroupCarousel);

        bnCaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carouselListener != null){
                    DrawerCategItem q = (DrawerCategItem)view.getTag();
                    carouselListener.onCarouselItemClick(q);
                }
            }
        });
        bnCaru.setTag(item);

        if (item.getSymbol().equals("T")){
            txIcon.setBackgroundResource(R.drawable.grp_grupodos);
            txIcon.setText("");
        }else{
            txIcon.setBackgroundResource(R.drawable.grp_greentag);
            txIcon.setTextColor(Color.BLACK);
            txIcon.setText(item.getSymbol());
        }

        TextView txLabel = (TextView)vw.findViewById(R.id.txDwCategGroupDescripItem);
        txLabel.setTextColor(Color.WHITE);
        txLabel.setText(item.getDescrip());
        vw.setTag(item);
        return vw;
    }

    public void setCarouselListener(CarouselListener qcarlistener){
        this.carouselListener = qcarlistener;
    }

    public interface CarouselListener{
        public void onCarouselItemClick(DrawerCategItem qcatItem);
    }
}
