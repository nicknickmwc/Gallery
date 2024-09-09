package ru.own.gallery.Presentation

import android.content.Context

class ContextProvider {

    companion object {
        lateinit var context: Context

        fun provideContext(context: Context): Unit {
            this.context = context
        }
    }
}