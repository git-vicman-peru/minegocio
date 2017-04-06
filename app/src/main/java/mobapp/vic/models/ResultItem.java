package mobapp.vic.models;

import android.util.Log;
import android.view.View;

/**
 * Created by VicDeveloper on 3/13/2017.
 */

public class ResultItem {
    private boolean flag;
    private View control;
    private String message;

    public ResultItem(){

    }

    public View getControl() {
        return control;
    }

    public void setControl(View control) {
        this.control = control;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
