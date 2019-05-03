package com.ice.weather.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * 底部弹窗封装类
 */
public class SnackBarUtil {
    private static final int color_danger = 0xffff4d40;
    private static final int color_success = 0xff87cefa;
    private static final int color_info = 0xff31708f;
    private static final int color_warning = 0xff8a6d3b;
    private static final int action_color = 0xffCDC5BF;
    private Snackbar mSnackbar;

    private SnackBarUtil(Snackbar snackbar) {
        mSnackbar = snackbar;
    }
    public static SnackBarUtil makeShort(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        return new SnackBarUtil(snackbar);
    }
    public static SnackBarUtil makeLong(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        return new SnackBarUtil(snackbar);
    }
    private View getSnackBarLayout(Snackbar snackbar) {
        if (snackbar != null) {
            return snackbar.getView();
    } return null;
    }
    private Snackbar setSnackBarBackColor(int colorId) {
        View snackBarView = getSnackBarLayout(mSnackbar);
        if (snackBarView != null) {
            snackBarView.setBackgroundColor(colorId);
        } return mSnackbar;
    }
    public void info() {
        setSnackBarBackColor(color_info);
        show();
    }
    public void info(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_info);
        show(actionText, listener);
    }
    public void warning() {
        setSnackBarBackColor(color_warning);
        show();
    }
    public void warning(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_warning);
        show(actionText, listener);
    }
    public void danger() {
        setSnackBarBackColor(color_danger);
        show();
    }
    public void danger(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_danger);
        show(actionText, listener);
    }
    public void confirm() {
        setSnackBarBackColor(color_success);
        show();
    }
    public void confirm(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_success);
        show(actionText, listener);
    }
    private void show() {
        mSnackbar.show();
    }
    private void show(String actionText, View.OnClickListener listener) {
        mSnackbar.setActionTextColor(action_color);
        mSnackbar.setAction(actionText, listener).show();
    }
}
