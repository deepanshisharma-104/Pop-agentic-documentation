@file:JvmName("TextIconTextViewBindingAdapters")

package com.pop.components.xml_components
import androidx.databinding.BindingAdapter

@BindingAdapter("startText")
fun setStartText(view: TextIconTextView, text: String?) {
    view.setStartText(text)
}

@BindingAdapter("endText")
fun setEndText(view: TextIconTextView, text: String?) {
    view.setEndText(text)
}

@BindingAdapter("iconSrc")
fun setIcon(view: TextIconTextView, drawableRes: Int) {
    view.setIcon(drawableRes)
}

@BindingAdapter("startBold")
fun setStartBold(view: TextIconTextView, isBold: Boolean) {
    view.setStartBold(isBold)
}

@BindingAdapter("endBold")
fun setEndBold(view: TextIconTextView, isBold: Boolean) {
    view.setEndBold(isBold)
}