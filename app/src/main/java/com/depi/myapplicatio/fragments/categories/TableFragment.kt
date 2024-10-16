package com.depi.myapplicatio.fragments.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depi.myapplicatio.R
import com.depi.myapplicatio.util.Resource
import com.depi.myapplicatio.viewmodel.CategoryViewModel
import com.depi.myapplicatio.viewmodel.factory.BaseCategoryViewModelFactoryFactory
import com.depi.myapplication.data.Category
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint

class TableFragment : BaseCategoryFragment() {

    @Inject
    lateinit var firestore: FirebaseFirestore

    val viewModel by viewModels<CategoryViewModel> {
        BaseCategoryViewModelFactoryFactory(firestore, Category.Table)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.offerProducts.collect {
                    when (it) {
                        is Resource.Loading<*> -> {
                            showOfferLoading()
                        }

                        is Resource.Success<*> -> {
                            offerAdapter.differ.submitList(it.data)
                            hideOfferLoading()
                        }

                        is Resource.Error<*> -> {
                            Snackbar.make(
                                requireView(),
                                it.message.toString(),
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                            hideOfferLoading()
                        }

                        else -> Unit
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.bestProducts.collect {
                    when (it) {
                        is Resource.Loading<*> -> {
                            showBestProductsLoading()
                        }

                        is Resource.Success<*> -> {
                            bestProductsAdapter.differ.submitList(it.data)
                            hideBestProductsLoading()
                        }

                        is Resource.Error<*> -> {
                            Snackbar.make(
                                requireView(),
                                it.message.toString(),
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                            hideBestProductsLoading()
                        }

                        else -> Unit
                    }
                }
            }
        }
    }



    override fun onBestProductsPagingRequest() {
    }

    override fun onOfferPagingRequest() {
    }

}