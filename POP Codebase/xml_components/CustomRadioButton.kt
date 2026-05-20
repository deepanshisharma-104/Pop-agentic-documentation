package com.pop.components.xml_components
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.IdRes

class CustomRadioGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var mCheckedId = -1
    private var mProtectFromCheckedChange = false
    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null
    private var mPassThroughListener: PassThroughHierarchyChangeListener = PassThroughHierarchyChangeListener()
    private var mChildOnCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CheckedStateTracker()

    init {
        orientation = VERTICAL
        super.setOnHierarchyChangeListener(mPassThroughListener)
    }

    override fun setOnHierarchyChangeListener(listener: OnHierarchyChangeListener?) {
        mPassThroughListener.mOnHierarchyChangeListener = listener
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (mCheckedId != -1) {
            mProtectFromCheckedChange = true
            setCheckedStateForView(mCheckedId, true)
            mProtectFromCheckedChange = false
            setCheckedId(mCheckedId)
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (child is RadioButton) {
            if (child.isChecked) {
                mProtectFromCheckedChange = true
                if (mCheckedId != -1) {
                    setCheckedStateForView(mCheckedId, false)
                }
                mProtectFromCheckedChange = false
                setCheckedId(child.id)
            }
        }
        super.addView(child, index, params)
        // ✅ Make non-RadioButton containers clickable
        child.setOnClickListener {
            val radioButton = findRadioButtonInView(child)
            radioButton?.let {
                check(it.id)
            }
        }
    }

    private fun findRadioButtonInView(view: View): RadioButton? {
        return when (view) {
            is RadioButton -> view
            is ViewGroup -> {
                for (i in 0 until view.childCount) {
                    val found = findRadioButtonInView(view.getChildAt(i))
                    if (found != null) return found
                }
                null
            }
            else -> null
        }
    }

    fun check(@IdRes id: Int) {
        if (id != -1 && id == mCheckedId) return

        if (mCheckedId != -1) setCheckedStateForView(mCheckedId, false)
        if (id != -1) setCheckedStateForView(id, true)

        setCheckedId(id)
    }

    private fun setCheckedId(@IdRes id: Int) {
        mCheckedId = id
        mOnCheckedChangeListener?.onCheckedChanged(this, mCheckedId)
    }

    private fun setCheckedStateForView(viewId: Int, checked: Boolean) {
        (findViewById<View>(viewId) as? RadioButton)?.isChecked = checked
    }

    @IdRes
    fun getCheckedRadioButtonId(): Int = mCheckedId

    fun clearCheck() {
        check(-1)
    }

    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
        mOnCheckedChangeListener = listener
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is RadioGroup.LayoutParams
    }

    override fun generateDefaultLayoutParams(): LinearLayout.LayoutParams {
        return LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun getAccessibilityClassName(): CharSequence {
        return RadioGroup::class.java.name
    }

    class LayoutParams : LinearLayout.LayoutParams {
        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs)
        constructor(w: Int, h: Int) : super(w, h)
        constructor(w: Int, h: Int, initWeight: Float) : super(w, h, initWeight)
        constructor(p: ViewGroup.LayoutParams?) : super(p)
        constructor(source: MarginLayoutParams?) : super(source)

        override fun setBaseAttributes(a: TypedArray, widthAttr: Int, heightAttr: Int) {
            width = if (a.hasValue(widthAttr)) a.getLayoutDimension(widthAttr, "layout_width") else WRAP_CONTENT
            height = if (a.hasValue(heightAttr)) a.getLayoutDimension(heightAttr, "layout_height") else WRAP_CONTENT
        }
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(group: CustomRadioGroup, @IdRes checkedId: Int)
    }

    private inner class CheckedStateTracker : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            if (mProtectFromCheckedChange) return

            mProtectFromCheckedChange = true
            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false)
            }
            mProtectFromCheckedChange = false

            setCheckedId(buttonView.id)
        }
    }

    private inner class PassThroughHierarchyChangeListener : ViewGroup.OnHierarchyChangeListener {
        var mOnHierarchyChangeListener: ViewGroup.OnHierarchyChangeListener? = null

        private fun traverseTree(view: View) {
            if (view is RadioButton) {
                var id = view.id
                if (id == View.NO_ID) {
                    id = View.generateViewId()
                    view.id = id
                }
                view.setOnCheckedChangeListener(mChildOnCheckedChangeListener)
            } else if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    traverseTree(view.getChildAt(i))
                }
            }
        }

        override fun onChildViewAdded(parent: View, child: View) {
            traverseTree(child)
            if (parent === this@CustomRadioGroup && child is RadioButton) {
                var id = child.id
                if (id == View.NO_ID) {
                    id = View.generateViewId()
                    child.id = id
                }
                child.setOnCheckedChangeListener(mChildOnCheckedChangeListener)
            }
            mOnHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        override fun onChildViewRemoved(parent: View, child: View) {
            if (parent === this@CustomRadioGroup && child is RadioButton) {
                child.setOnCheckedChangeListener(null)
            }
            mOnHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }
    }
}
