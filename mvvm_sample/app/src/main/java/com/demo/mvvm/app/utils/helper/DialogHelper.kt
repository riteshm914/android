package com.demo.mvvm.app.utils.helper

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.demo.mvvm.app.R


class DialogHelper {

    companion object {
        fun showProgressDialog(context: Context): AlertDialog {

            val li = LayoutInflater.from(context)
            val promptsView: View = li.inflate(R.layout.progress_dialog, null)
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setView(promptsView)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

           // val progressBar = promptsView.findViewById(R.id.pb_loading) as ProgressBar
            val loadingMessage = promptsView.findViewById(R.id.tv_loading) as TextView

            loadingMessage.visibility = View.GONE


            if (alertDialog.window != null) {
                alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            alertDialog.setCancelable(false)
            alertDialog.setCanceledOnTouchOutside(false)
            return alertDialog
        }

        fun showProgressDialog(context: Context, msg: String?): AlertDialog {
            val li = LayoutInflater.from(context)
            val promptsView: View = li.inflate(R.layout.progress_dialog, null)
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setView(promptsView)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

          //  val progressBar = promptsView.findViewById(R.id.pb_loading) as ProgressBar
            val loadingMessage = promptsView.findViewById(R.id.tv_loading) as TextView

            loadingMessage.visibility = View.VISIBLE
            loadingMessage.text = msg ?: ""

            if (alertDialog.window != null) {
                alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            alertDialog.setCancelable(false)
            alertDialog.setCanceledOnTouchOutside(false)
            return alertDialog
        }
    }
}