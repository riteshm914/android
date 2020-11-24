package com.demo.mvvm.app.ui.base

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.SparseArray
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.demo.mvvm.app.R
import com.demo.mvvm.app.utils.helper.DialogHelper
import com.google.android.material.snackbar.Snackbar
import java.util.*


abstract class BaseActivity : AppCompatActivity() {

    private var progressAlertDialog: AlertDialog? = null
    private val viewClickTimeStampSparseArray = SparseArray<Long>()

    //Activity stack, holding only class not the activity references (No leak issue)
    private val activityClassStack = LinkedList<Class<*>>();



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onActivityCreatedPushToStack()
    }

    /**
     * show toast message with short duration
     * @param msg Input message to show on toast
     * @return Toast Message
     */
    protected fun showShortToast(msg: String?) {
        if (TextUtils.isEmpty(msg)) {
            return
        }

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * show toast message with long duration
     * @param msg Input message to show on toast
     * @return Toast Message
     */
    protected fun showLongToast(msg: String?) {
        if (TextUtils.isEmpty(msg)) {
            return
        }

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * Show SnackBar
     * @param msg display message
     * @return SnackBar
     */
    protected fun showSnackBar(msg: String?) {
        if (TextUtils.isEmpty(msg))
            return

        displaySnackBar(msg!!)
    }

    /**
     * Show SnackBar
     * @param msgRes display message from string resource
     * @return SnackBar
     */
    protected fun showSnackBar(msgRes: Int?) {
        if (null == msgRes)
            return

        displaySnackBar(getString(msgRes))
    }

    /**
     * Display SnackBar
     * @param message Input message text
     * @return SnackBar
     */
    private fun displaySnackBar(message: String) {
        val snackBar = Snackbar.make(
                findViewById<View>(android.R.id.content),
                message, Snackbar.LENGTH_LONG
        )
        val sbView = snackBar.view
        val textView = sbView
                .findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackBar.show()
    }

    /**
     * Preventing multiple clicks.
     * @param view need to prevent multiple clicks on.
     * @param delayMillis millis delta to validate multiple click events
     */
    private fun isTooEarlyMultipleClicks(@NonNull view: View, delayMillis: Int): Boolean {
        val lastClickTime = viewClickTimeStampSparseArray.get(view.id, 0L)
        val timeStamp = System.currentTimeMillis()
        if (lastClickTime + delayMillis > timeStamp) {
            return true
        }
        viewClickTimeStampSparseArray.put(view.id, timeStamp)
        return false
    }

    /**
     * Preventing multiple clicks. (validate within 1000 millis)
     * @param view need to prevent multiple clicks on.
     */
    protected fun isTooEarlyMultipleClicks(@NonNull view: View): Boolean {
        return isTooEarlyMultipleClicks(view, 1000) //default 1 sec
    }

    protected fun showLoading() {
        hideLoading();
        progressAlertDialog = DialogHelper.showProgressDialog(this)
    }

    protected fun showLoading(msg: Int?) {
        hideLoading();
        progressAlertDialog = DialogHelper.showProgressDialog(this, getString(msg!!))
    }

    protected fun showLoading(msg: String) {
        hideLoading();
        progressAlertDialog = DialogHelper.showProgressDialog(this, msg)
    }

    protected fun hideLoading() {
        if (progressAlertDialog != null && progressAlertDialog!!.isShowing) {
            progressAlertDialog!!.cancel()
        }
    }

    private fun onActivityCreatedPushToStack() { //stack
        val peek = activityClassStack.peek()
        val clazz = javaClass
        if (peek == null || peek != clazz) { /*Avoid double push of same, its hack, may fail in case of multiple instances*/
            activityClassStack.push(clazz)
            //log.debug("activityClassStack pushed "+clazz.getSimpleName());
        }
    }

    private fun onActivityDestroyedPopStack() {
        val peek = activityClassStack.peek()
        val clazz = javaClass
        if (peek != null) {
            if (peek == clazz) {
                val popped = activityClassStack.pop()
                //log.debug("activityClassStack popped " + popped.getSimpleName());
            } else {
                val index = activityClassStack.indexOf(clazz)
                if (index != -1) {
                    val removed = activityClassStack.removeAt(index)
                    //log.debug("activityClassStack removed " + removed.getSimpleName());
                }
            }
        }
    }

    /**
     * @param clazz Activity class to be check in stack
     * @return true if Activity class is present in current stack.
     */
    fun isActivityClassInStack(clazz: Class<*>): Boolean {
        val index = activityClassStack.indexOf(clazz)
        //log.debug("isActivityClassInStack activityStack class "+clazz.getSimpleName()+ " index: "+index);
        return index != -1
    }

    override fun onDestroy() {
        super.onDestroy()
        onActivityDestroyedPopStack()
    }

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected open fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected open fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

}