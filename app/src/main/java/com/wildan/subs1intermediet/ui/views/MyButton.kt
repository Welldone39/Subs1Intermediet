package com.wildan.subs1intermediet.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.wildan.subs1intermediet.R

class MyButton : AppCompatButton {
    private lateinit var backgroundEnable : Drawable
    private lateinit var backgroundDisable : Drawable
    private lateinit var textDefault : String
    private var colorText : Int = 0

    private fun init() {
        textDefault = text.toString()
        colorText = ContextCompat.getColor(context, R.color.white)
        backgroundEnable = ContextCompat.getDrawable(context, R.drawable.bg_button_enable) as Drawable
        backgroundDisable = ContextCompat.getDrawable(context, R.drawable.bg_button_disabled) as Drawable
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    fun setLoading() {
        isEnabled = false
        text = context.getString(R.string.loading)
    }

    fun resetState() {
        isEnabled = true
        text = textDefault
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setTextColor(colorText)
        background = if (isEnabled) backgroundEnable else backgroundDisable
    }

}