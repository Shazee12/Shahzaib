package shahzaib.com.trafficupdate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shahzaib on 7/25/2017.
 */

public class BaseFragment extends Fragment{
    private View fragmentView;

    public View getFragmentView() {
        return fragmentView;
    }

    public void setFragmentView(View fragmentView) {
        this.fragmentView = fragmentView;
    }

    public View findViewById(int id){
        return getFragmentView().findViewById(id);
    }
}
