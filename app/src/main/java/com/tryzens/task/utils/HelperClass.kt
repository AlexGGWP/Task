package com.tryzens.task.utils

import android.R
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.tryzens.task.rest.data.RepositoriesData
import java.util.*
import kotlin.collections.ArrayList

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, backStackTag: String? = null) {
    supportFragmentManager.inTransaction {
        add(frameId, fragment)
        backStackTag?.let { addToBackStack(fragment.javaClass.name) }
    }
}

fun AppCompatActivity.addAndReplaceFragment(
    fragment: Fragment,
    frameId: Int,
    backStackTag: String? = null
) {
    supportFragmentManager.inTransaction {
        replace(frameId, fragment)
        backStackTag?.let { addToBackStack(fragment.javaClass.name) }
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun filterList(
    searchInformation: String,
    list: List<RepositoriesData>
): List<RepositoriesData> {
    val sortedList = ArrayList<RepositoriesData>()

    //Add all repos that has the char sequence in their names
    for (repo in list) {
        if (repo.name.lowercase(Locale.getDefault())
                .contains(searchInformation.lowercase(Locale.getDefault()).trim())
        ) {
            sortedList.add(repo)
        }
    }
    return sortedList
}

//Hide keyboard from Activity
fun hideKeyboard(activity: Activity) {
    val view = activity.findViewById<View>(R.id.content)
    if (view != null) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}