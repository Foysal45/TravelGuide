package com.btb.explorebangladesh.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.view.hide
import com.btb.explorebangladesh.view.show


class CollapsibleCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var titleText: TextView
    private var llHeader: LinearLayoutCompat
    private var llExpandIcon: AppCompatImageView
    private var llBody: LinearLayoutCompat

    init {
        val view =
            LayoutInflater.from(context).inflate(R.layout.control_collapsible_card_view, this, true)
        titleText = view.findViewById(R.id.mtTitle)
        llHeader = view.findViewById(R.id.llExpand)
        llExpandIcon = view.findViewById(R.id.llExpandIcon)
        llBody = view.findViewById(R.id.llBody)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CollapsibleCardView,
            0, 0
        ).apply {
            try {
                titleText.text = getString(R.styleable.CollapsibleCardView_title)
                val isExpand = getBoolean(R.styleable.CollapsibleCardView_expanded, false)
                if (isExpand) {
                    expand()
                }
            } finally {
                recycle()
            }
        }

        //attach listener
        llHeader.setOnClickListener {
            if (llBody.visibility == View.GONE) {
                llExpandIcon.rotation = 270.0F
                llBody.show()
            } else {
                llExpandIcon.rotation = 0.0F
                llBody.hide()
            }
            addCollapseListener?.let { it1 -> it1(llBody.visibility == View.VISIBLE) }
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (child.id == R.id.top || child.id == R.id.llBody) {
            super.addView(child, index, params);
        } else {
            llBody.addView(child, index, params)
        }
    }

    private var addCollapseListener: ((collapsed: Boolean) -> Unit)? = null
    fun setOnCollapseListener(listener: (collapsed: Boolean) -> Unit) {
        this.addCollapseListener = listener
    }

    fun collapse() {
        llExpandIcon.rotation = 0.0F
        llBody.hide()
    }

    fun expand() {
        llExpandIcon.rotation = 270.0F
        llBody.show()
    }

    fun setTitle(title: String) {
        titleText.text = title
    }
}