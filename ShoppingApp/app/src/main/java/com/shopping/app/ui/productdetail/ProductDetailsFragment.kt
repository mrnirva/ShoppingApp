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
import com.shopping.app.R
import com.shopping.app.data.model.Product
import com.shopping.app.databinding.FragmentProductDetailsBinding
import com.shopping.app.ui.productdetail.viewmodel.ProductDetailViewModel
import com.shopping.app.utils.Constants.PRODUCT_MODEL_NAME

class ProductDetailsFragment : Fragment() {

    private lateinit var bnd: FragmentProductDetailsBinding
    private val viewModel by viewModels<ProductDetailViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)
        bnd.viewModel = viewModel
        bnd.productDetailsFragment = this
        return bnd.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleArguments()
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

    fun goBack(){
        findNavController().popBackStack()
    }

}