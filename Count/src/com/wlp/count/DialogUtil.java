package com.wlp.count;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class DialogUtil {

    public static Dialog createWeekDialog(View view,Context context,boolean cancelVisible, 
    		String titleText, boolean cancelable,OnClickListener sureOnClickListener){
	
	    LayoutInflater inflater = LayoutInflater.from(context);
	    View parentView = inflater.inflate(R.layout.dialog_nubber_layout, null);// 得到加载view
	    DisplayMetrics dm = context.getResources().getDisplayMetrics();
	    LinearLayout linear = (LinearLayout) parentView.findViewById(R.id.view_content);// 加载布局
	    linear.addView(view, new LinearLayout.LayoutParams(
	    		dm.widthPixels*4/5,
	    		LinearLayout.LayoutParams.MATCH_PARENT));
	    Button sureButton = (Button) parentView.findViewById(R.id.dialog_ensure_button_sure);
	    RelativeLayout cancelLayout = (RelativeLayout) parentView.findViewById(R.id.dialog_ensure_layout_cancel);
	    sureButton.setOnClickListener(sureOnClickListener);
	
	    final Dialog loadingDialog = new Dialog(context, R.style.custom_dialog);// 创建自定义样式dialog
	    
	        Button cancelButton = (Button) parentView.findViewById(R.id.dialog_ensure_button_cancel);
	        cancelButton.setOnClickListener(new OnClickListener() {
	
	            @Override
	            public void onClick(View v) {
	                // TODO Auto-generated method stub
	                loadingDialog.dismiss();
	
	            }
	        });
	    
	    loadingDialog.setCancelable(true);// 不可以用“返回键”取消
	    loadingDialog.setContentView(parentView, new LinearLayout.LayoutParams(
	    		dm.widthPixels*4/5,
	            LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
	    return loadingDialog;
	}
}
