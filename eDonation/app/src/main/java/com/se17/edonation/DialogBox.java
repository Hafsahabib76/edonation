package com.se17.edonation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class DialogBox extends DialogFragment {

    public interface onMultiChoiceListner{
        void onPositiveButtonClicked(String[] text, ArrayList<String> selectedItemList);
        void onNegativeButtonClicked();
    }

    onMultiChoiceListner mListner;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListner = (onMultiChoiceListner) context;
        }catch (Exception e){
            throw new ClassCastException(getActivity().toString()+"onMultiChoiceListner must implemented");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final ArrayList<String> selectedItemList = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String[] text=getActivity().getResources().getStringArray(R.array.agree);

        builder.setMultiChoiceItems(text, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i, boolean b) {
               if (b) {
                   selectedItemList.add(text[i]);
               }else {
                   selectedItemList.remove(text[i]);
               }
            }
        })
                .setPositiveButton("I AGREE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mListner.onPositiveButtonClicked(text,selectedItemList);

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListner.onNegativeButtonClicked();
                    }
                });

        return builder.create();
    }




}
