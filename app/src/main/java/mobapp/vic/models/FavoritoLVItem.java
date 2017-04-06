package mobapp.vic.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VicDeveloper on 3/14/2017.
 */

public class FavoritoLVItem {
    private String grupo;
    private List<FavoritoLVSubitem> items;

    public FavoritoLVItem(){
        this.items = new ArrayList<FavoritoLVSubitem>();
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public List<FavoritoLVSubitem> getItems() {
        return items;
    }

    public void setItems(List<FavoritoLVSubitem> items) {
        this.items = items;
    }

    public void add(FavoritoLVSubitem favsubi){
        this.items.add(favsubi);
    }
}
