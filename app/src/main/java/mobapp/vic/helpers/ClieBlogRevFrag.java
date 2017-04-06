package mobapp.vic.helpers;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mobapp.vic.minegocio.R;
import mobapp.vic.models.ClieBlogRev;
import mobapp.vic.services.ServHandler;

/**
 * Created by VicDeveloper on 3/15/2017.
 */

public class ClieBlogRevFrag extends Fragment {

    private ServHandler srv;
    private ListView lvblogs;
    private ClieBlogRevLAdapter adp;
    private int empid, prodid;

    public ClieBlogRevFrag(){
        this.srv = new ServHandler();

    }

    public void setCriteria(int empid, int prodid){
        this.empid = empid;
        this.prodid = prodid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.clieblogrev_fragly,container,false);
        return vw;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        lvblogs = (ListView)view.findViewById(R.id.lvBlogs);
        adp = new ClieBlogRevLAdapter(getActivity(),new ArrayList<ClieBlogRev>());
        lvblogs.setAdapter(adp);
        srv.Blogs_Load(this.empid, this.prodid, new ServHandler.BlogListener() {
            @Override
            public void onBlogsReceived(List<ClieBlogRev> blogs) {
                Log.d("blogs",blogs.size()+"");
                adp.setData(blogs);
                Log.d("blogs adp",adp.getCount()+"");
                adp.notifyDataSetChanged();
            }

            @Override
            public void onBlogSaved() {

            }

            @Override
            public void onBlogFailed() {

            }
        });
    }

    public static ClieBlogRevFrag newInstance(){
        ClieBlogRevFrag f = new ClieBlogRevFrag();
        return f;
    }

    public static ClieBlogRevFrag newInstance(int idemp, int idprod){
        ClieBlogRevFrag f = new ClieBlogRevFrag();
        f.setCriteria(idemp,idprod);
        return f;
    }
}
