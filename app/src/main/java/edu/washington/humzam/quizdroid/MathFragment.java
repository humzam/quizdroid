package edu.washington.humzam.quizdroid;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by humzamangrio on 5/3/15.
 */
public class MathFragment extends Fragment implements Serializable{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.math_overview_layout,
                container, false);
        return view;

    }

}
