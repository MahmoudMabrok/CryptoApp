package com.mahmoudmabrok.mo3sec.feature.SecretFragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mahmoudmabrok.mo3sec.Algo.Ceaser;
import com.mahmoudmabrok.mo3sec.Algo.OneTimePad;
import com.mahmoudmabrok.mo3sec.Algo.PlayFair;
import com.mahmoudmabrok.mo3sec.Algo.RailFence;
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
public class SecretFragment extends Fragment {


    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.spAlgo)
    Spinner spAlgo;
    @BindView(R.id.edKey)
    EditText edKey;
    @BindView(R.id.editText)
    EditText edInput;
    @BindView(R.id.editText2)
    EditText edCipherText;
    Unbinder unbinder;
    @BindView(R.id.btnEncrypt)
    Button btnEncrypt;
    @BindView(R.id.btnDecrypt)
    Button btnDecrypt;
    Toast toast;
    String inputText, cipherText, key;

    public SecretFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_secret_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initSpinner();
        initTextWatcher();

        toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);

        return view;
    }

    private void initTextWatcher() {
        edInput.addTextChangedListener(new TextWatcher() {
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
                    btnDecrypt.setVisibility(View.VISIBLE);

                    cofigurResultEdit(false);
                } else {
                    btnEncrypt.setVisibility(View.GONE);
                    btnDecrypt.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initSpinner() {
        List<String> list = new ArrayList<>(Constants.symmtricAlgo);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        spAlgo.setAdapter(adapter);

        spAlgo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                blankFields();
                if (spAlgo.getSelectedItem().toString().equals(Constants.OTP)) {
                    edKey.setText("No Key  need it will be generated");
                } else {
                    edKey.setText("");
                }
                cofigurResultEdit(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void encryptData() {
        inputText = edInput.getText().toString();
        String algoName = (String) spAlgo.getSelectedItem();
        key = edKey.getText().toString();
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(inputText)) {
            switch (algoName) {
                case Constants.Ceaser:
                    encryptDataCipher();
                    break;
                case Constants.PlayFair:
                    encryptDataPlayFair();
                    break;
                case Constants.RailFence:
                    encryptDataRail();
                    break;

                case Constants.DES:
                    encryptDataDES();
                    break;

                case Constants.Tap:
                    encryptDataTap();
                    break;
                case Constants.OTP:
                    encryptDataOTP();
                    break;
            }

        } else {
            showMessage("Please Enter data");
        }

    }

    private void encryptDataOTP() {
        OneTimePad oneTimePad = new OneTimePad();
        if (isAllAlpha(inputText)) {
            String encrpted = oneTimePad.cipher(inputText);
            setResultInView(encrpted);
            String keyOTP = oneTimePad.getKey();
            edKey.setText(keyOTP);
            //   showMessage("Key is  grenated Random and added to Key Field");
            showEncrptMsg();
        } else {
            showAlphaError();
        }
    }

    private void showEncrptMsg() {
        showMessage("Encrypted");
    }

    private void showAlphaError() {
        showMessage("text must consists only from  alphabatic");
        cofigurResultEdit(false);
    }

    private void encryptDataTap() {
    }

    private void encryptDataDES() {
    }

    private void encryptDataRail() {
        try {
            int keyValue = Integer.parseInt(key);
            int textLength = inputText.length();
            if (keyValue < textLength) {
                RailFence railFence = new RailFence();
                String encrypted = railFence.cipher(inputText, keyValue);
                setResultInView(encrypted);
                showEncrptMsg();
            } else {
                showKeyMustBeLowerLength(textLength - 1);

            }

        } catch (NumberFormatException e) {
            showMessage("Key must be number");
        }
    }

    private void encryptDataPlayFair() {
        if (isAllAlpha(key)) {
            PlayFair playFair = new PlayFair(key);
            if (isAllAlpha(inputText)) {
                String encrypted = playFair.cipherText(inputText);
                setResultInView(encrypted);
                showEncrptMsg();
            } else {
                showAlphaError();
            }
        } else {
            showMessage("Key must be text");
        }
    }

    private boolean isAllAlpha(@NonNull String key) {
        boolean state = true;
        key = key.toLowerCase();
        int min = (int) 'a';
        int max = (int) 'z';
        int value;

        for (char c : key.toCharArray()) {
            value = (int) c;
            if (Character.isDigit(c) || !(value >= min && value <= max)) {
                state = false;
                break;
            }
        }
        return state;
    }

    private void encryptDataCipher() {
        try {
            int keyValue = Integer.parseInt(key);
            if (isAllAlpha(inputText)) {
                Ceaser ceaser = new Ceaser(keyValue);
                String encrypted = ceaser.ceaserText(inputText);
                setResultInView(encrypted);
                showEncrptMsg();
            } else {
                showMessage("plain must be alphabet only");
            }
        } catch (NumberFormatException e) {
            showMessage("Key must be num");
        }
    }


    private void decyrpt() {
        String algoName = (String) spAlgo.getSelectedItem();
        key = edKey.getText().toString();
        if (!TextUtils.isEmpty(key)) {
            inputText = edInput.getText().toString();
            switch (algoName) {
                case Constants.Ceaser:
                    decyrptCeaser();
                    break;
                case Constants.DES:
                    decyrptDES();
                    break;
                case Constants.PlayFair:
                    decyrptPlayFair();
                    break;
                case Constants.RailFence:
                    decyrptRailFence();
                    break;
                case Constants.OTP:
                    decyrptOTP();
                    break;
            }
        } else {
            showKeyEmpty();

        }

    }

    private void decyrptOTP() {
        if (isAllAlpha(key)) {
            if (key.length() == inputText.length()) {
                OneTimePad oneTimePad = new OneTimePad();
                String plain = oneTimePad.deCipher(inputText, key);
                setResultInView(plain);
                showDecrptMsg();
            } else {
                showSameLength();
            }
        } else {
            showAlphaError();
        }
    }

    private void showSameLength() {
        showMessage("key and cipher must be same length");
        cofigurResultEdit(false);
    }

    private void showKeyEmpty() {
        showMessage("Enter Key");
        cofigurResultEdit(false);
    }

    private void decyrptRailFence() {
        try {
            int keyValue = Integer.parseInt(key);
            if (keyValue < inputText.length()) {
                RailFence railFence = new RailFence();
                String plain = railFence.deCipher(inputText, keyValue);
                setResultInView(plain);
                showDecrptMsg();
            } else {
                showKeyMustBeLowerLength(inputText.length() - 1);
            }
        } catch (NumberFormatException e) {
            showMessage("Key must be number");
        }
    }

    private void showKeyMustBeLowerLength(int level) {
        showMessage("key must be lower than length ( 1 - " + level + ") ");
        cofigurResultEdit(false);
    }

    private void decyrptPlayFair() {
        if (isAllAlpha(key)) {
            PlayFair playFair = new PlayFair(key);

            if (isAllAlpha(inputText)) {
                String plain = playFair.decipherText(inputText);
                setResultInView(plain);
                showDecrptMsg();
            } else {
                showAlphaError();
            }
        } else {
            showMessage("Key must be text");
        }
    }

    private void decyrptDES() {
    }

    private void decyrptCeaser() {
        try {
            int keyValue = Integer.parseInt(key);
            if (isAllAlpha(inputText)) {
                Ceaser ceaser = new Ceaser(keyValue);
                String plain = ceaser.decrypt(inputText);
                setResultInView(plain);
                showDecrptMsg();
            } else {
                showAlphaError();
            }

        } catch (NumberFormatException e) {
            showMessage("Key must be number");
        }
    }

    private void setResultInView(String plain) {
        edCipherText.setText(plain);
    }

    private void showDecrptMsg() {
        showMessage("Decrypted Successfully");
    }

    private void blankFields() {
        edKey.setText("");
        edInput.setText("");
    }

    private void showMessage(String msg) {
       /* toast.cancel();
        toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        toast.show();*/

    }


    //// TODO: 10/18/2018 add Thread 

    /**
     * iam sure that as button  is clicked --> cipher has a value
     *
     * @param view
     */
    @OnClick({R.id.btnEncrypt, R.id.btnDecrypt})
    public void onViewClicked(View view) {
        cofigurResultEdit(true);
        switch (view.getId()) {
            case R.id.btnEncrypt:
                encryptData();
                break;
            case R.id.btnDecrypt:
                decyrpt();
                break;
        }

        //// TODO: 10/18/2018
        hideKeport();
    }

    private void hideKeport() {
    }

    public void cofigurResultEdit(boolean state) {
        if (state) {
            edCipherText.setVisibility(View.VISIBLE);
        } else {
            edCipherText.setVisibility(View.GONE);
        }
    }
}
