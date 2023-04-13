package com.teb.wordpressapp.ui.screen.main.categories

import com.teb.wordpressapp.data.model.Category

interface CategoryListFragmentActionListenerActivity {
    fun onCategorySelected(category: Category)
}