package com.ramonapp.pixabay.component

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import com.ramonapp.pixabay.app.R
import com.ramonapp.pixabay.app.databinding.ToolbarGeneralBinding
import java.util.*
import kotlin.concurrent.schedule

class GeneralToolbar : ConstraintLayout {

    private lateinit var binding: ToolbarGeneralBinding

    private var menuItemClick: ((item: MenuItem) -> Unit)? = null
    private var searchListener: IToolbarSearchListener? = null

    private var searchIsActive = false
    private var searchIsOpen = false
        set(value) {
            field = value
            if (value) {
                binding.toolbarSearchEdt.addTextChangedListener(textWatcher)
                searchListener?.onSearchStateChanged(true)
                binding.toolbarAction1Btn.visibility = View.GONE
                binding.toolbarSearchEdt.visibility = View.VISIBLE
                binding.toolbarTitleTxt.visibility = View.INVISIBLE
            }
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        binding = ToolbarGeneralBinding.inflate(LayoutInflater.from(context), this, false)
        addView(binding.root)

        val a = context.obtainStyledAttributes(attrs, R.styleable.GeneralToolbar, defStyleAttr, 0)

        if (a.getBoolean(R.styleable.GeneralToolbar_hideBackButton, false)) {
            binding.toolbarBackBtn.visibility = View.INVISIBLE
        }
        if (a.getBoolean(R.styleable.GeneralToolbar_showCloseButton, false)) {
            binding.toolbarBackBtn.setImageResource(R.drawable.ic_clear)
        }
        a.getResourceId(R.styleable.GeneralToolbar_action1Icon, -1).takeIf { it != -1 }?.let {
            binding.toolbarAction1Btn.visibility = View.VISIBLE
            binding.toolbarAction1Btn.setImageResource(it)
        }
        a.getResourceId(R.styleable.GeneralToolbar_action2Icon, -1).takeIf { it != -1 }?.let {
            binding.toolbarAction2Btn.visibility = View.VISIBLE
            binding.toolbarAction2Btn.setImageResource(it)
        }
        a.getString(R.styleable.GeneralToolbar_toolbarTitle)?.let {
            binding.toolbarTitleTxt.text = it
        }
        a.getString(R.styleable.GeneralToolbar_subTitle)?.let {
            binding.toolbarSubTitleTxt.text = it
            binding.toolbarSubTitleTxt.visibility = View.VISIBLE
        }
        a.getResourceId(R.styleable.GeneralToolbar_popUpMenuItems, -1).takeIf { it != -1 }?.let {
            binding.toolbarPopUpBtn.visibility = View.VISIBLE
        }
        searchIsActive = a.getBoolean(R.styleable.GeneralToolbar_searchIsActive, false)

        a.recycle()


        if (searchIsActive) {
            binding.toolbarAction1Btn.setOnClickListener {
                searchIsOpen = true
            }
            binding.toolbarSearchEdt.imeOptions = EditorInfo.IME_ACTION_DONE
        }
        binding.toolbarClearBtn.setOnClickListener {
            binding.toolbarSearchEdt.setText("")
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding.toolbarSearchEdt.removeTextChangedListener(textWatcher)
    }


    fun setOnAction1Click(onclick: OnClickListener?) {
        if (searchIsActive) return
        binding.toolbarAction1Btn.setOnClickListener(onclick)
    }

    fun setOnAction2Click(onclick: OnClickListener?) {
        binding.toolbarAction2Btn.setOnClickListener(onclick)
    }

    fun setOnBackOrCloseClick(onclick: OnClickListener?) {
        binding.toolbarBackBtn.setOnClickListener(onclick)
    }

    fun setOnMenuItemClick(itemClick: (item: MenuItem) -> Unit) {
        menuItemClick = itemClick
    }

    fun setOnSearchListener(listener: IToolbarSearchListener) {
        searchListener = listener
    }

    private val textWatcher = object : TextWatcher {

        private var timer = Timer()
        private var previousSearch = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.toolbarClearBtn.visibility = if(s?.isNotEmpty() == true) View.VISIBLE else View.GONE

            val searchText = s.toString().trim()

            timer.cancel()
            timer = Timer()
            timer.schedule(500) {
                binding.toolbarSearchEdt.handler.post {
                    if (searchText.isBlank() || searchText == previousSearch)
                        return@post
                    searchListener?.onQueryChanged(searchText)
                    previousSearch = searchText
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    interface IToolbarSearchListener {
        fun onQueryChanged(query: String)
        fun onSearchStateChanged(isOpen: Boolean)
    }
}