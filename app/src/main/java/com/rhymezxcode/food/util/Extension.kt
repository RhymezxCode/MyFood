package com.rhymezxcode.food.util

import android.content.Context
import android.widget.Toast

// For Toast
fun Context.showToast(message: String) {
    Toast
        .makeText(this, message, Toast.LENGTH_LONG)
        .show()
}

fun launchCamera() {
    // Implement camera launch logic here
}

fun openFilePicker() {
    // Implement file picker logic here
}

fun addFood() {
    // Implement food addition logic here
}
