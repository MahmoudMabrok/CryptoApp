package com.mahmoudmabrok.mo3sec.feature.PublicFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mahmoudmabrok.mo3sec.R;
import com.mahmoudmabrok.mo3sec.Util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicFragmet extends Fragment {


    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.spAlgo)
    Spinner spAlgo;
    @BindView(R.id.edKey)
    EditText edKey;
    @BindView(R.id.btnEncrypt)
    Button btnEncrypt;

    @BindView(R.id.btnDecrypt)
    Button btnDecrypt;
    Unbinder unbinder;
    @BindView(R.id.editPlain)
    EditText editPlain;
    @BindView(R.id.edCipher)
    EditText edCipher;
    String plain, key, cipher;


    public PublicFragmet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_public_fragmet, container, false);
        unbinder = ButterKnife.bind(this, view);
        initSpinner();
        initTextWatcher();
        return view;
    }

    private void initTextWatcher() {
        editPlain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    btnEncrypt.setVisibility(View.VISIBLE);
                } else {
                    btnEncrypt.setVisibility(View.GONE);
                }
            }
        });

        edCipher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    btnDecrypt.setVisibility(View.VISIBLE);
                } else {
                    btnDecrypt.setVisibility(View.GONE);
                }
            }
        });


    }

    private void initSpinner() {
        List<String> list = new ArrayList<>(Constants.asymmtricAlgo);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        spAlgo.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnEncrypt, R.id.btnDecrypt})
    public void onViewClicked(View view) {
        key = edKey.getText().toString();

        if (!TextUtils.isEmpty(key)) {
            switch (view.getId()) {
                case R.id.btnEncrypt:
                    encryptData();
                    break;
                case R.id.btnDecrypt:
                    decryptData();
                    break;
            }
        } else {
            showMessage("Enter Key");
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void encryptData() {
        plain = editPlain.getText().toString();
        if (!TextUtils.isEmpty(plain)) {
            //
        } else {
            showMessage("Enter Plain Text");
        }

    }

    private void decryptData() {
        cipher = edCipher.getText().toString();
        if (!TextUtils.isEmpty(cipher)) {

        } else {
            showMessage("Enter Cipher Text");
        }

    }
}
