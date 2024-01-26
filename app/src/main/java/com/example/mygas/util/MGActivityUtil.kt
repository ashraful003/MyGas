package com.example.mygas.util

class MGActivityUtil(private var activityListener: ActivityListener) {
    fun hideBottomNavigation(hide: Boolean) {
        activityListener.hideBottomNavigation(hide)
    }

    fun setFullScreenLoading(short: Boolean) {
        activityListener.setFullScreenLoading(short)
    }

    fun closeKeyboard() {
        activityListener.closeKeyboard()
    }

    interface ActivityListener {
        fun hideBottomNavigation(hide: Boolean)
        fun setFullScreenLoading(short: Boolean)
        fun closeKeyboard()
    }
}