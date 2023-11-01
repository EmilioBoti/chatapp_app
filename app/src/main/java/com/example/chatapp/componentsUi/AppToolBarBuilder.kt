package com.example.chatapp.componentsUi

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.isVisible
import com.example.chatapp.R
import com.example.chatapp.databinding.AppMainToolbarBinding
import com.example.chatapp.views.home.HomeActivity
import com.google.android.material.appbar.MaterialToolbar

class AppToolBarBuilder(private val context: Context?) {
    private lateinit var rootView: ViewGroup
    private var activity: Activity? = null
    private var toolBarListener: AppToolBarListener? = null

    private var toolbar: MaterialToolbar? = null
    private var searcherInput: AppCompatEditText? = null
    private lateinit var keyboard: InputMethodManager

    private var isActionSearch: Boolean = false
    private var isActionNotification: Boolean = false

    fun withActivity(activity: Activity): AppToolBarBuilder {
        this.activity = activity
        this.rootView = activity.findViewById<View>(android.R.id.content) as ViewGroup
        return this
    }

    fun setToolBarListener(toolBarListener: AppToolBarListener): AppToolBarBuilder {
        this.toolBarListener = toolBarListener
        return this
    }

    fun setSearchAction(isAction: Boolean): AppToolBarBuilder {
        this.isActionSearch = isAction
        return this
    }

    fun setSearchNotification(isAction: Boolean): AppToolBarBuilder {
        this.isActionNotification = isAction
        return this
    }

    fun build() {
        context?.let {
            val appToolbarView = activity?.layoutInflater?.inflate(R.layout.app_main_toolbar, rootView, false) as ViewGroup
            appToolbarView.tag =  "APP_TOOLBAR"

            keyboard = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            toolbar =  appToolbarView.findViewById<MaterialToolbar>(R.id.main_toolbar)
            searcherInput =  appToolbarView.findViewById<AppCompatEditText>(R.id.searcherInput)


            if (isActionSearch) toolbar?.menu?.findItem(R.id.search)?.isVisible = isActionSearch
            if (isActionNotification) toolbar?.menu?.findItem(R.id.notifications)?.isVisible = isActionSearch


            toolBarEventUi()
            checkIspreviuosToolbar()

            val v = rootView.findViewById<LinearLayoutCompat>(R.id.toolbar)
            v.addView(appToolbarView)
        }
    }

    private fun toolBarEventUi() {
        toolbar?.let { toolbar ->
            toolbar.setOnMenuItemClickListener { menu ->
                when(menu.itemId) {
                    R.id.logout -> { toolBarListener?.onClickLogout() }
                    R.id.notifications -> { toolBarListener?.onClickNotification() }
                    R.id.search -> {
                        if (searcherInput?.isVisible != true) {
                            showSearcherInput()
                        }
                    }
                }
                true
            }
            toolbar.setNavigationOnClickListener {
                hideSearcherInput()
            }
        }

        searcherInput?.let { searcherInput ->
            searcherInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    toolBarListener?.onSeachListener(input.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}

            })

            searcherInput.setOnFocusChangeListener { view, b ->
                if (!b) {
                    searcherInput.visibility = View.GONE
                }
            }
        }
    }

    private fun showSearcherInput() {
        searcherInput?.run {
            visibility = View.VISIBLE
            requestFocus()
        }
        toolbar?.setNavigationIcon(R.drawable.arrow_back_24)
        keyboard.showSoftInput(searcherInput, InputMethodManager.RESULT_SHOWN)
    }

    private fun hideSearcherInput() {
        searcherInput?.setText("")
        searcherInput?.visibility = View.GONE
        keyboard.hideSoftInputFromWindow(searcherInput?.windowToken, 0)
        toolbar?.navigationIcon = null
    }

    private fun checkIspreviuosToolbar() {
        val previuos = rootView.findViewWithTag<View>("APP_TOOLBAR")
        previuos?.let {
            rootView.removeView(previuos)
        }
    }

}