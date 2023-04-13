package com.teb.wordpressapp.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.teb.wordpressapp.databinding.ViewPaginationBinding

typealias OnPageChangeRequestListener = (page : Int) -> Unit

class PaginationView : RelativeLayout {


    private lateinit var binding: ViewPaginationBinding

    constructor(context: Context?) : super(context) {
        init()
    }


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }


    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        init()
    }

    var onPageChangeRequestListener : OnPageChangeRequestListener? = null

    private var currentPage: Int = 1
    private var lastPage: Int = 1

    private fun init() {
        val inflater = LayoutInflater.from(context)
        binding = ViewPaginationBinding.inflate(inflater, this, true)



        binding.btnPaginationFirst.setOnClickListener {
            gotoPage(1)
        }
        binding.btnPaginationLast.setOnClickListener {
            gotoPage(lastPage)
        }

        binding.btnPaginationNext.setOnClickListener {
            gotoPage(currentPage + 1)
        }
        binding.btnPaginationPrev.setOnClickListener {
            gotoPage(currentPage - 1)
        }

        if (!isInEditMode) {
            this.visibility = View.GONE
        }

    }

    private fun gotoPage(i: Int) {
        onPageChangeRequestListener?.invoke(i)
    }

    fun setPageCounts(currentPage: Int, header_wp_totalpages: Int){

        if(header_wp_totalpages < 2){
            this.visibility = View.GONE
        }else{
            this.visibility = View.VISIBLE
        }

        this.currentPage = currentPage
        lastPage = header_wp_totalpages

        binding.txtPaginationInfo.text = "$currentPage/$lastPage"

        binding.btnPaginationFirst.isEnabled = 1 != currentPage
        binding.btnPaginationPrev.isEnabled = 1 != currentPage
        binding.btnPaginationLast.isEnabled = currentPage != header_wp_totalpages
        binding.btnPaginationNext.isEnabled = currentPage != header_wp_totalpages

    }


}