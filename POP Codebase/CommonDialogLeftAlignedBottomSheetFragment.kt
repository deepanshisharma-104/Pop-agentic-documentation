package com.pop.components

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.pop.compose_components.databinding.LeftAlignedCommonBottomSheetBinding
import com.pop.components.UIConstants.Companion.LEFT_TEXT
import com.pop.components.UIConstants.Companion.RIGHT_TEXT
import com.pop.components.UIConstants.Companion.SUB_TITLE
import com.pop.components.UIConstants.Companion.TITLE
import com.pop.components.animation.AnimationManager
import com.pop.components.animation.event.AnimationEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


var mTitle: String = ""
var mSubTitle: String = ""
var mLeftBtnText: String = ""
var mRightBtnText: String = ""
var mListener: OnCommonDialogClickListener? = null

@AndroidEntryPoint
class CommonDialogLeftAlignedBottomSheetFragment : BaseBottomSheetDialogFragment() {
    private lateinit var binding: LeftAlignedCommonBottomSheetBinding

    @Inject
    lateinit var animationManager: AnimationManager

    override fun onScaleProgress(scale: Float) {
        animationManager.emitEvent(AnimationEvent.ChangeWindowScale(scale))
    }

    override fun shouldUseGradientBackgroundForContent(): Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LeftAlignedCommonBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.background = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        initListeners()
        setData()
        
        // Apply navigation bar inset padding to avoid overlapping with system navigation buttons
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
            val originalPadding = resources.getDimensionPixelSize(com.pop.compose_components.R.dimen.radius_24)
            v.updatePadding(bottom = originalPadding + insets.bottom)
            windowInsets
        }
        binding.root.requestApplyInsets()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.setBackgroundColor(Color.TRANSPARENT)
        }
        return dialog
    }

    fun setData() {
        if (!TextUtils.isEmpty(mTitle)) {
            binding.headerTv.text = mTitle
        } else {
            binding.headerTv.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(mSubTitle)) {
            binding.subHeaderTv.text = mSubTitle
        } else {
            binding.subHeaderTv.visibility = View.GONE
        }
        binding.leftBtn.setText(mLeftBtnText)
        binding.rightBtn.setText(mRightBtnText)
        if(mLeftBtnText.isEmpty()) {
            binding.leftBtn.gone()
        }
        if(mRightBtnText.isEmpty()) {
            binding.rightBtn.gone()
        }
        var singleBtnText = ""
        if(mLeftBtnText.isEmpty() && mRightBtnText.isNotEmpty()) {
            singleBtnText = mRightBtnText
            binding.rightBtn.gone()
            binding.leftBtn.gone()
            binding.btnProceed.visible()
            binding.btnProceed.setOnClickListener {
                mListener?.onRightBtnClickListener()
                dismiss()
            }
        }else if(mRightBtnText.isEmpty() && mLeftBtnText.isNotEmpty()) {
            singleBtnText = mLeftBtnText
            binding.rightBtn.gone()
            binding.leftBtn.gone()
            binding.btnProceed.visible()
            binding.btnProceed.setOnClickListener {
                mListener?.onLeftBtnClickListener()
                dismiss()
            }
        }
        binding.btnProceed.setText(singleBtnText)
    }

    fun initListeners() {
        binding.closeButton.setOnClickListener {
            mListener?.onCloseBtnClickListener()
            dismiss()
        }

        binding.leftBtn.setOnClickListener {
            mListener?.onLeftBtnClickListener()
            dismiss()
        }

        binding.rightBtn.setOnClickListener {
            mListener?.onRightBtnClickListener()
            dismiss()
        }

    }

    companion object {
        @JvmField
        var TAG= CommonDialogLeftAlignedBottomSheetFragment::class.simpleName
        fun newInstance(
            bundle: Bundle,
            listener: OnCommonDialogClickListener
        ): CommonDialogLeftAlignedBottomSheetFragment {
            mTitle = bundle.getString(TITLE) ?: ""
            mSubTitle = bundle.getString(SUB_TITLE) ?: ""
            mLeftBtnText = bundle.getString(LEFT_TEXT) ?: ""
            mRightBtnText = bundle.getString(RIGHT_TEXT) ?: ""
            mListener = listener
            return CommonDialogLeftAlignedBottomSheetFragment()
        }
    }
}

interface OnCommonDialogClickListener {
    fun onLeftBtnClickListener()
    fun onRightBtnClickListener()
    fun onCloseBtnClickListener()
}

interface UIConstants{
    companion object {
        const val TITLE = "title"
        const val SUB_TITLE = "sub_title"
        const val LEFT_TEXT = "left_text"
        const val RIGHT_TEXT = "right_text"
    }
}