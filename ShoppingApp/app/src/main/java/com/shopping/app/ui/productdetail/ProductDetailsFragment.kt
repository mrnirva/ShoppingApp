package com.shopping.app.ui.productdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shopping.app.R
import com.shopping.app.data.model.DataState
import com.shopping.app.data.model.Product
import com.shopping.app.data.model.ProductBasket
import com.shopping.app.data.repository.basket.BasketRepositoryImpl
import com.shopping.app.databinding.FragmentProductDetailsBinding
import com.shopping.app.ui.basket.viewmodel.BasketViewModelFactory
import com.shopping.app.ui.loadingprogress.LoadingProgressBar
import com.shopping.app.ui.main.product.adapter.ProductAdapter
import com.shopping.app.ui.productdetail.viewmodel.ProductDetailViewModel
import com.shopping.app.ui.productdetail.viewmodel.ProductDetailViewModelFactory
import com.shopping.app.utils.Constants
import com.shopping.app.utils.Constants.PRODUCT_MODEL_NAME

class ProductDetailsFragment : Fragment() {

    private lateinit var bnd: FragmentProductDetailsBinding
    private lateinit var loadingProgressBar: LoadingProgressBar
    private val viewModel by viewModels<ProductDetailViewModel>(){
        ProductDetailViewModelFactory(
            BasketRepositoryImpl()
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)
        bnd.viewModel = viewModel
        bnd.productDetailsFragment = this
        return bnd.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleArguments()
        init()
    }

    private fun getBundleArguments(){

        arguments?.let {

            val productData = it.getString(PRODUCT_MODEL_NAME)

            // null state check of data
            productData?.let { safeData ->

                val product = Product.fromJson(safeData)
                bnd.dataHolder = product

                viewModel.productCountLiveData.observe(viewLifecycleOwner){ value ->

                    bnd.basketCount = value

                }

            }
        }

    }

    private fun init(){

        loadingProgressBar = LoadingProgressBar(requireContext())

        viewModel.addBasketLiveData.observe(viewLifecycleOwner){

            when (it) {
                is DataState.Success -> {
                    loadingProgressBar.hide()
                    Toast.makeText(requireContext(), getString(R.string.product_added_basket_message), Toast.LENGTH_SHORT).show()
                }
                is DataState.Error -> {
                    loadingProgressBar.hide()
                    Snackbar.make(bnd.root, it.message, Snackbar.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    loadingProgressBar.show()
                }
            }

        }

    }

    fun goBack(){
        findNavController().popBackStack()
    }

}