package mobapp.vic.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VicDeveloper on 3/14/2017.
 */

public class FavoritoLVGroup {
    private List<FavoritoLVItem> favs;

    public FavoritoLVGroup(){
        this.favs = new ArrayList<FavoritoLVItem>();
    }

    public boolean hasItems(){
        return (this.favs.size()>0);
    }

    public void clear(){
        this.favs.clear();
    }

    public int count(){
        return this.favs.size();
    }

    public FavoritoLVItem getFav(int index){
        return this.favs.get(index);
    }

    public void add(FavoritoLVItem qfav){
        this.favs.add(qfav);
    }
}
