package com.jaroidx.mapdemo;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class SearchMapBottomSheetFragment extends BottomSheetDialogFragment {

    List<String> mListCity;
    BottomSheetDialog dialog;
    BottomSheetBehavior<View> behavior;
    View rootView;

    public SearchMapBottomSheetFragment(List<String> listCity) {
        this.mListCity = listCity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_city_bottom_sheet, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        behavior = BottomSheetBehavior.from((View) view.getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        CoordinatorLayout coordinatorLayout = view.findViewById(R.id.cdlCity);
        coordinatorLayout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels/3);
    }
}
