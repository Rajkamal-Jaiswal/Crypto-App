package com.e.cellpaycrypto.Base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.e.cellpaycrypto.R;

public class DialogUtils {

    public static void makeDialog(Context context, String titleTxt, String messageTxt,
                                  String submitTxt, String cancelTxt, boolean isCancelable,
                                  final onDialogUtilsOkClick okClick,
                                  final onDialogUtilsCancelClick cancelClick) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(isCancelable);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.setContentView(R.layout.dialog_alert_simple);
            TextView titleDialog = (TextView) dialog.findViewById(R.id.titleDialog);
            TextView msgDialog = (TextView) dialog.findViewById(R.id.msgDialog);
            Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
            Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);

            if (TextUtils.isEmpty(titleTxt)) {
                titleDialog.setVisibility(View.GONE);
            } else {
                titleDialog.setVisibility(View.VISIBLE);
                titleDialog.setText(titleTxt);
            }
            msgDialog.setText(messageTxt);
            btn_cancel.setText(cancelTxt);
            btn_submit.setText(submitTxt);

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialog.dismiss();
                    } catch (Exception e) {
                        Log.d("Dialog", "makeDialog: " + e.getMessage());
                    }
                    if (okClick != null)
                        okClick.onOKClick();
                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialog.dismiss();
                    } catch (Exception e) {
                        Log.d("Dialog", "makeDialog: " + e.getMessage());
                    }
                    if (cancelClick != null)
                        cancelClick.onCancelClick();
                }
            });
            try {
                dialog.show();
            } catch (Exception e) {
                Log.d("Dialog", "makeDialog: " + e.getMessage());
            }
        } catch (Exception e) {
            Log.d("Dialog", "main: " + e.getMessage());
        }
    }

    public interface onDialogUtilsOkClick {
        void onOKClick();
    }

    public interface onDialogUtilsOkTestFilterClick {
        void onOKTestFilterClick(boolean one, boolean two, boolean three);
    }

    public interface onDialogUtilsCancelClick {
        void onCancelClick();
    }


}
