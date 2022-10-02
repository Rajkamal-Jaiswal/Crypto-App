package com.e.cellpaycrypto.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityPaymentGatewayBinding;
import com.google.android.material.snackbar.Snackbar;
import com.payu.base.models.ErrorResponse;
import com.payu.base.models.PayUPaymentParams;
import com.payu.base.models.PayUSIParams;
import com.payu.checkoutpro.PayUCheckoutPro;
import com.payu.checkoutpro.models.PayUCheckoutProConfig;
import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
import com.payu.ui.model.listeners.PayUCheckoutProListener;
import com.payu.ui.model.listeners.PayUHashGenerationListener;

import org.json.JSONObject;

import java.security.Key;
import java.security.MessageDigest;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PaymentGatewayActivity extends AppCompatActivity {

    ActivityPaymentGatewayBinding binding;
    private long mLastClickTime;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentGatewayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        Helper.enableScreenShot(activity);
        myToolbar();


        binding.btnSubmit.setOnClickListener(v -> {
            if (!binding.etAmt.getText().toString().trim().isEmpty()) {
                // Preventing multiple clicks, using threshold of 1 second
                if (!binding.etAmt.getText().toString().trim().equalsIgnoreCase("0")) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                        return;
                    mLastClickTime = SystemClock.elapsedRealtime();
                    initUiSdk(preparePayUBizParams());
                }
            } else {
                Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private PayUCheckoutProConfig getCheckoutProConfig() {
        PayUCheckoutProConfig checkoutProConfig = new PayUCheckoutProConfig();
//        checkoutProConfig.setPaymentModesOrder(getCheckoutOrderList());
//        checkoutProConfig.setOfferDetails(getOfferDetailsList());
        // uncomment below code for performing enforcement
//        checkoutProConfig.setEnforcePaymentList(getEnforcePaymentList());
        checkoutProConfig.setShowCbToolbar(true);
        checkoutProConfig.setAutoSelectOtp(true);
//        checkoutProConfig.setAutoApprove(binding.switchAutoApprove.isChecked());
//        checkoutProConfig.setSurePayCount(Integer.parseInt(binding.etSurePayCount.getText().toString()));
        checkoutProConfig.setShowExitConfirmationOnPaymentScreen(true);
        checkoutProConfig.setShowExitConfirmationOnCheckoutScreen(true);
        checkoutProConfig.setMerchantName("Sellpay");
        checkoutProConfig.setMerchantLogo(R.drawable.sellpayicon);
        checkoutProConfig.setWaitingTime(30000);
        checkoutProConfig.setMerchantResponseTimeout(30000);
//        checkoutProConfig.setCustomNoteDetails(getCustomeNoteList());
//        if (reviewOrderAdapter != null)
//            checkoutProConfig.setCartDetails(reviewOrderAdapter.getOrderDetailsList());
        return checkoutProConfig;
    }

    private void initUiSdk(PayUPaymentParams payUPaymentParams) {
        PayUCheckoutPro.open(
                this,
                payUPaymentParams,
                getCheckoutProConfig(),
                new PayUCheckoutProListener() {

                    @Override
                    public void onPaymentSuccess(Object response) {
                        showAlertDialog(response);
                    }

                    @Override
                    public void onPaymentFailure(Object response) {
                        showAlertDialog(response);
                    }

                    @Override
                    public void onPaymentCancel(boolean isTxnInitiated) {
                        showSnackBar(getResources().getString(R.string.transaction_cancelled_by_user));
                    }

                    @Override
                    public void onError(ErrorResponse errorResponse) {
                        String errorMessage = errorResponse.getErrorMessage();
                        if (TextUtils.isEmpty(errorMessage))
                            errorMessage = getResources().getString(R.string.some_error_occurred);
                        showSnackBar(errorMessage);
                    }

                    @Override
                    public void setWebViewProperties(@Nullable WebView webView, @Nullable Object o) {
                        //For setting webview properties, if any. Check Customized Integration section for more details on this
                    }

                    @Override
                    public void generateHash(HashMap<String, String> valueMap, PayUHashGenerationListener hashGenerationListener) {
                        String hashName = valueMap.get(PayUCheckoutProConstants.CP_HASH_NAME);
                        String hashData = valueMap.get(PayUCheckoutProConstants.CP_HASH_STRING);
                        if (!TextUtils.isEmpty(hashName) && !TextUtils.isEmpty(hashData)) {
                            //Generate Hash from your backend here
                            String salt = "wia56q6O";
                            if (valueMap.containsKey(PayUCheckoutProConstants.CP_POST_SALT))
                                salt = salt + "" + (valueMap.get(PayUCheckoutProConstants.CP_POST_SALT));


                            String hash = null;
                            if (hashName.equalsIgnoreCase(PayUCheckoutProConstants.CP_LOOKUP_API_HASH)) {
                                //Calculate HmacSHA1 HASH for calculating Lookup API Hash
                                ///Do not generate hash from local, it needs to be calculated from server side only. Here, hashString contains hash created from your server side.

                                hash = calculateHmacSHA1Hash(hashData, "gtKFFx");
                            } else {

                                //Calculate SHA-512 Hash here
                                hash = calculateHash(hashData + salt);
                            }

                            HashMap<String, String> dataMap = new HashMap<>();
                            dataMap.put(hashName, hash);
                            hashGenerationListener.onHashGenerated(dataMap);
                        }
                    }
                }
        );
    }

    private String calculateHash(String hashString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            return getHexString(mdbytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String calculateHmacSHA1Hash(String data, String key) {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        String result = null;

        try {
            Key signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = getHexString(rawHmac);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getHexString(byte[] array) {
        StringBuilder hash = new StringBuilder();
        for (byte hashByte : array) {
            hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
        }
        return hash.toString();
    }

    private void showSnackBar(String message) {
        Snackbar.make(binding.clMain, message, Snackbar.LENGTH_LONG).show();
    }

    private void showAlertDialog(Object response) {
        HashMap<String, Object> result = (HashMap<String, Object>) response;
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(
                        "Payu's Data : " + result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE) + "\n\n\n Merchant's Data: " + result.get(
                                PayUCheckoutProConstants.CP_MERCHANT_RESPONSE
                        )
                )
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private PayUPaymentParams preparePayUBizParams() {
        HashMap<String, Object> additionalParams = new HashMap<>();
        additionalParams.put(PayUCheckoutProConstants.CP_UDF1, "udf1");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF2, "udf2");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF3, "udf3");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF4, "udf4");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF5, "udf5");

        //Below params should be passed only when integrating Multi-currency support
        //TODO Please pass your own Merchant Access Key below as provided by your Key Account Manager at PayU.
//        additionalParams.put(PayUCheckoutProConstants.CP_MERCHANT_ACCESS_KEY,CP_MERCHANT_ACCESS_KEY testMerchantAccessKey);

//        Below params should be passed only when sodexo payment option is enabled and want to show saved sodexo card
        //TODO Please pass sodexosrcid for sodexo card which will be recieved in new sodexo card txn response
//        additionalParams.put(PayUCheckoutProConstants.SODEXO_SOURCE_ID, sodexosrcid);

        PayUSIParams siDetails = null;


        JSONObject splitPaymentDetails = new JSONObject();

        PayUPaymentParams.Builder builder = new PayUPaymentParams.Builder();
        builder.setAmount(binding.etAmt.getText().toString().trim())
                .setIsProduction(false)
                .setProductInfo("Macbook Pro")
                .setKey("gtKFFx")
                .setPhone("9118919678")
                .setTransactionId(String.valueOf(System.currentTimeMillis()))
                .setFirstName("John")
                .setEmail("sellpayuser@gmail.com")
                .setSurl("https://payu.herokuapp.com/success")
                .setFurl("https://payu.herokuapp.com/failure")
                .setUserCredential("gtKFFx" + ":john@yopmail.com")
                .setAdditionalParams(additionalParams)
                .setPayUSIParams(siDetails)
                .setSplitPaymentDetails(splitPaymentDetails.toString());
        PayUPaymentParams payUPaymentParams = builder.build();
        return payUPaymentParams;
    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
        toolbar.setNavigationIcon(R.drawable.ic_back);

//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbarTitleTV.setText("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}