package com.android.custom_dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CustomDialog extends DialogFragment {

    public static final String TAG = CustomDialog.class.getSimpleName();
    public static final int CUSTOM_DIALOG_TYPE_MULTI_CHOICE = 1;
    public static final int CUSTOM_DIALOG_TYPE_EDIT_TEXT = 2;


    private String title;
    private int titleColor;
    private String subTitle;
    private int subTitleColor;
    private int backgroundColor;
    private Context context;

    private int type;
    private List<MultiChoiceModel> multiChoiceModelList = new ArrayList<>();
    private boolean isRTL;
    private OnFinishEditInterface onFinishEditInterface;
    private OnSelectItemInterface onSelectItemInterface;
    private OnCancelInterface onCancelInterface;

    private ConstraintLayout constTopParent;
    private LinearLayout linearParent;
    private TextView txvTitle;
    private TextView txvSubTitle;
    private Button btnOk;
    private Button btnCancel;
    private String currentText;

    public CustomDialog(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int style;
        int theme = 0;
        style = DialogFragment.STYLE_NO_TITLE;
        setStyle(style, theme);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_custom, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            setDirection(view);
        } catch (Exception e) {
        }

        constTopParent = view.findViewById(R.id.const_top_parent);
        linearParent = view.findViewById(R.id.linear_parent);
        txvTitle = view.findViewById(R.id.txv_title);
        txvSubTitle = view.findViewById(R.id.txv_sub_title);
        btnOk = view.findViewById(R.id.btn_ok);
        btnCancel = view.findViewById(R.id.btn_cancel);
        if (title == null || title.equals("")) {
            Log.e(TAG, "Add a valid title");
        } else {
            txvTitle.setText(title);
        }
        if (titleColor > 0) {
            System.out.println("The Title Color: " + titleColor);
            txvTitle.setTextColor(ContextCompat.getColor(context, titleColor));
        }

        if (subTitle != null && subTitle.equals("")) {
            txvSubTitle.setVisibility(View.VISIBLE);
            txvSubTitle.setText(subTitle);
            if (subTitleColor > 0) {
                txvSubTitle.setTextColor(ContextCompat.getColor(context, subTitleColor));
            }
        } else {
            txvSubTitle.setVisibility(View.GONE);
        }


        if (type < 0) {

            Log.e(TAG, "Select the type of the dialog");
            dismiss();
        }
        switch (type) {
            case CUSTOM_DIALOG_TYPE_MULTI_CHOICE:
                addRecyclerView();
                break;
            case CUSTOM_DIALOG_TYPE_EDIT_TEXT:
                addEditText();
                break;
        }

        if (type == CUSTOM_DIALOG_TYPE_EDIT_TEXT) {
            btnOk.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        } else {
            btnOk.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);

        }

        if (backgroundColor > 0) {
            constTopParent.setBackgroundResource(backgroundColor);
        }


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCancelInterface != null) {
                    onCancelInterface.onCancel();
                }
                dismiss();
            }
        });
    }


    private void addRecyclerView() {
        if (getContext() == null) {
            Log.e(TAG, "Error, context = null");
            return;
        }


        RecyclerView recyclerView = new RecyclerView(getContext());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, (int) context.getResources().getDimension(R.dimen._10sdp), 0, 0);
        recyclerView.setLayoutParams(layoutParams);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        MultiChoiceAdapter multiChoiceAdapter = new MultiChoiceAdapter(getContext(), multiChoiceModelList, onSelectItemInterface);
        recyclerView.setAdapter(multiChoiceAdapter);

        linearParent.addView(recyclerView);
    }


    private void addEditText() {
        if (getContext() == null) {
            Log.e(TAG, "Error, context = null");
            return;
        }

        final EditText editText = new EditText(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int) context.getResources().getDimension(R.dimen._15sdp), (int) context.getResources().getDimension(R.dimen._10sdp), (int) context.getResources().getDimension(R.dimen._15sdp), (int) context.getResources().getDimension(R.dimen._5sdp));
        editText.setLayoutParams(layoutParams);
        editText.setPadding((int) context.getResources().getDimension(R.dimen._8sdp), (int) context.getResources().getDimension(R.dimen._12sdp), (int) context.getResources().getDimension(R.dimen._8sdp), (int) context.getResources().getDimension(R.dimen._12sdp));
        editText.setMinHeight((int) context.getResources().getDimension(R.dimen._60sdp));
        editText.setGravity(Gravity.TOP | Gravity.START);
        editText.setLayoutParams(layoutParams);
        editText.setBackgroundResource(R.drawable.bck_edittext);
        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "29LTBukra-Bold.ttf");
        editText.setTypeface(face);

        editText.setTextSize((int) context.getResources().getDimension(R.dimen._4sdp));
        if (currentText != null) {
            editText.setText(currentText);
        }
        linearParent.addView(editText);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFinishEditInterface.onFinish(editText.getText().toString());
            }
        });
    }


    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setTitle(@NonNull String title, int color) {
        this.title = title;
        this.titleColor = color;
    }

    public void setSubTitle(@NonNull String subTitle) {
        this.subTitle = subTitle;
    }

    public void setSubTitle(@NonNull String subTitle, int color) {
        this.subTitle = subTitle;
        this.subTitleColor = color;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setMultiChoiceType(@NonNull List<MultiChoiceModel> multiChoiceModelList, @NonNull OnSelectItemInterface onSelectItemInterface) {
        type = CUSTOM_DIALOG_TYPE_MULTI_CHOICE;
        this.multiChoiceModelList = multiChoiceModelList;
        this.onSelectItemInterface = onSelectItemInterface;
    }

    public void setOnCancelInterface(@NonNull OnCancelInterface onCancelInterface) {
        this.onCancelInterface = onCancelInterface;
    }

    public void setEditTextType(String currentText, @NonNull OnFinishEditInterface onFinishEditInterface) {
        type = CUSTOM_DIALOG_TYPE_EDIT_TEXT;
        this.onFinishEditInterface = onFinishEditInterface;
        this.currentText = currentText;
    }


    public void setDirection(View v) {

        int direction = View.LAYOUT_DIRECTION_LTR;

        if (isRTL()) {
            direction = View.LAYOUT_DIRECTION_RTL;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            v.setLayoutDirection(direction);
        } else {
            ViewCompat.setLayoutDirection(v, direction);
        }


    }

    public boolean isRTL() {
        return isRTL;
    }

    public void setRTL(boolean RTL) {
        isRTL = RTL;
    }
}
