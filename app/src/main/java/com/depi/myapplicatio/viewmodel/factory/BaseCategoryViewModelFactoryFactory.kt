package com.depi.myapplicatio.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.depi.myapplicatio.viewmodel.shopping.CategoryViewModel
import com.depi.myapplicatio.data.models.Category
import com.depi.myapplicatio.data.remote.FirebaseUtility

class BaseCategoryViewModelFactoryFactory(

    private val category: Category,
    private val firebaseUtility: FirebaseUtility
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(category,firebaseUtility) as T
    }
}