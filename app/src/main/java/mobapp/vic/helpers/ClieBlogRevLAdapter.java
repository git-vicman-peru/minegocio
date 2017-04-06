package mobapp.vic.helpers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.ClieBlogRev;
import mobapp.vic.utils.U;

/**
 * Created by VicDeveloper on 3/15/2017.
 */

public class ClieBlogRevLAdapter extends ArrayAdapter<ClieBlogRev> {

    final private Activity activity;
    private List<ClieBlogRev> locallist;

    public ClieBlogRevLAdapter(Activity activity, List<ClieBlogRev> lst){
        super(activity, R.layout.clieblogrevitem_ly,lst);
        this.activity = activity;
        this.locallist =lst;
        Log.d("locallist inside",this.locallist.size()+"");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vw = convertView;
        if (vw == null){
            LayoutInflater inflater = this.activity.getLayoutInflater();
            vw = inflater.inflate(R.layout.clieblogrevitem_ly,null);
        }
        ClieBlogRev cb = locallist.get(position);
        TextView blogger = (TextView)vw.findViewById(R.id.lbBlogClieBlogger);
        TextView fecha = (TextView)vw.findViewById(R.id.lbBlogClieFecha);
        ImageView stars = (ImageView)vw.findViewById(R.id.lbBlogClieStars);
        TextView comment = (TextView)vw.findViewById(R.id.lbBlogClieComment);
        blogger.setText(cb.getBlogger());
        fecha.setText(U.dateToStrFormat(cb.getFecha(),"dd/MM/yyyy hh:mm aa"));
        int rating = Integer.parseInt(cb.getRating());
        switch (rating){
            case 1:
                stars.setImageResource(R.drawable.stars1);
                break;
            case 2:
                stars.setImageResource(R.drawable.stars2);
                break;
            case 3:
                stars.setImageResource(R.drawable.stars3);
                break;
            case 4:
                stars.setImageResource(R.drawable.stars4);
                break;
            case 5:
                stars.setImageResource(R.drawable.stars5);
                break;
            default:
                stars.setImageResource(R.drawable.starsnone);
        }

        comment.setText(cb.getComentario());
        return vw;
    }

    public void setData(List<ClieBlogRev> src){
        this.locallist.clear();
        this.locallist.addAll(src);
    }

}
