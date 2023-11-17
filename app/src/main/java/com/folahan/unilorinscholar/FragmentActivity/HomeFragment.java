package com.folahan.unilorinscholar.FragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.folahan.unilorinscholar.Activity.CheckLevelActivity;
import com.folahan.unilorinscholar.Activity.Cropper_Activity;
import com.folahan.unilorinscholar.Activity.GP_Activity;
import com.folahan.unilorinscholar.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private RoundedImageView mView;
    private CardView mCardView, mCardViewImgText, mCardViewUpdate,
    mCardViewNotes;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mCardView = view.findViewById(R.id.cardViewTest);
        mCardViewImgText = view.findViewById(R.id.cardViewImgText);
        mCardViewUpdate = view.findViewById(R.id.cardViewUpdate);
        mCardViewNotes = view.findViewById(R.id.cardViewNote);

        mCardViewNotes.setOnClickListener(field -> {
            Uri uri = Uri.parse("https://drive.google.com/folderview?id=1-0qtHWaFHZj94gureZRyaQk00dKdeHje");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });
        mCardViewUpdate.setOnClickListener(task -> {
            Uri uri = Uri.parse("https://uilugportal.unilorin.edu.ng");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });
        mCardViewImgText.setOnClickListener(task -> {
            Intent data = new Intent(requireActivity(), Cropper_Activity.class);
            startActivity(data);
        });
        mCardView.setOnClickListener(task -> {
            Intent data = new Intent(requireActivity(), CheckLevelActivity.class);
            startActivity(data);
        });

        return view;
    }
}



