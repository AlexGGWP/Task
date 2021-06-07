package com.tryzens.task.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.TextView
import com.tryzens.task.R
import com.tryzens.task.ui.MainActivity

class CustomErrorDialog {

    private lateinit var errorDialog: Dialog

    fun showErrorDialog(activity: MainActivity, message: String) {
        errorDialog = Dialog(activity)
        errorDialog.setContentView(R.layout.custom_error_dialog)
        errorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        errorDialog.show()

        val errorMessage = errorDialog.findViewById<TextView>(R.id.error_message)
        errorMessage.text = message

        val cancelButton = errorDialog.findViewById<Button>(R.id.dialog_cancel_button)
        cancelButton.setOnClickListener { errorDialog.dismiss() }

    }
}