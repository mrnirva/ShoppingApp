package com.shopping.app.ui.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.shopping.app.R
import com.shopping.app.data.model.DataState
import com.shopping.app.data.model.ProductBasket
import com.shopping.app.data.repository.basket.BasketRepositoryImpl
import com.shopping.app.databinding.FragmentBasketBinding
import com.shopping.app.ui.basket.adapter.BasketProductsAdapter
import com.shopping.app.ui.basket.viewmodel.BasketViewModel
import com.shopping.app.ui.basket.viewmodel.BasketViewModelFactory
import com.shopping.app.ui.loadingprogress.LoadingProgressBar

class BasketFragment : BottomSheetDialogFragment(), ProductPieceUpdateListener {

    private lateinit var bnd: FragmentBasketBinding
    private lateinit var loadingProgressBar: LoadingProgressBar
    private val viewModel by viewModels<BasketViewModel> {
        BasketViewModelFactory(
            BasketRepositoryImpl()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME,R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_basket, container, false)
        return bnd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        bnd.basketFragment = this
        loadingProgressBar = LoadingProgressBar(requireContext())

        viewModel.basketLiveData.observe(viewLifecycleOwner) {

            when (it) {
                is DataState.Success -> {
                    loadingProgressBar.hide()

                    it.data?.let { safeData ->

                        val basketProductsAdapter = BasketProductsAdapter(safeData, this)
                        bnd.rvBasketProducts.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        bnd.rvBasketProducts.setHasFixedSize(true)
                        bnd.rvBasketProducts.adapter = basketProductsAdapter

                    } ?: run {
                        Snackbar.make(bnd.root, getString(R.string.no_data), Snackbar.LENGTH_LONG)
                            .show()
                    }

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


        viewModel.basketTotalLiveData.observe(viewLifecycleOwner) {
            bnd.total = it
        }


        viewModel.updateProductPieceLiveData.observe(viewLifecycleOwner) {

            when (it) {
                is DataState.Success -> {
                    loadingProgressBar.hide()
                    // Toast.makeText(requireContext(), getString(it.data), Toast.LENGTH_SHORT).show()
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


        viewModel.purchaseLiveData.observe(viewLifecycleOwner) {

            when (it) {
                is DataState.Success -> {
                    loadingProgressBar.hide()
                    Toast.makeText(requireContext(), getString(it.data), Toast.LENGTH_SHORT).show()
                    this.dialog?.cancel()
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

    fun clearTheBasket() {

        if(viewModel.basketList.isNotEmpty()){

            AlertDialog.Builder(requireContext())
                .setMessage(resources.getString(R.string.purchase_message))
                .setPositiveButton(resources.getString(R.string.continue_)) { dialog, _ ->
                    dialog.cancel()
                    viewModel.clearTheBasket()
                }.setNegativeButton(resources.getString(R.string.cancel)){ dialog, _ ->
                    dialog.cancel()
                }
                .show()

        }else{
            Toast.makeText(requireContext(), getString(R.string.basket_empty_message), Toast.LENGTH_SHORT).show()
        }

    }

    override fun increaseProduct(productBasket: ProductBasket) {
        viewModel.increaseProduct(productBasket)
    }

    override fun reduceProduct(productBasket: ProductBasket) {
        viewModel.reduceProduct(productBasket)
    }

}

interface ProductPieceUpdateListener{

    fun increaseProduct(productBasket: ProductBasket)

    fun reduceProduct(productBasket: ProductBasket)

}