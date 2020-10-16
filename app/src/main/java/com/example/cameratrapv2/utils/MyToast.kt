package com.example.cameratrapv2.utils

import android.content.Context
import com.example.cameratrapv2.R
import es.dmoral.toasty.Toasty

class MyToast() {

    companion object {
        fun cancel(context: Context) {
            Toasty.custom(
                context,
                R.string.toast_cancel,
                R.drawable.ic_cancelled,
                R.color.toast_color_cancelled,
                Toasty.LENGTH_SHORT,
                true,
                true
            ).show();
        }
        fun cancel(context: Context, text: String) {
            Toasty.custom(
                context,
                text,
                R.drawable.ic_cancelled,
                R.color.toast_color_cancelled,
                Toasty.LENGTH_SHORT,
                true,
                true
            ).show();
        }
        fun succes(context: Context) {
            Toasty.custom(
                context, R.string.toast_succes,
                R.drawable.ic_succes,
                R.color.toast_color_succes,
                Toasty.LENGTH_SHORT,
                true,
                true
            ).show();
        }
        fun succes(context: Context, text: String) {
            Toasty.custom(
                context,
                text,
                R.drawable.ic_succes,
                R.color.toast_color_succes,
                Toasty.LENGTH_SHORT,
                true,
                true
            ).show();
        }
        fun error(context: Context) {
            Toasty.custom(
                context, R.string.toast_error,
                R.drawable.ic_error,
                R.color.toast_color_error,
                Toasty.LENGTH_SHORT,
                true,
                true
            ).show();
        }
        fun error(context: Context, text: String) {
            Toasty.custom(
                context,
                text,
                R.drawable.ic_error,
                R.color.toast_color_error,
                Toasty.LENGTH_SHORT,
                true,
                true
            ).show();
        }
    }
}