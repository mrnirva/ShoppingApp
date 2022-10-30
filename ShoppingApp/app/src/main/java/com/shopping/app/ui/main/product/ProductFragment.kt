package com.shopping.app.ui.main.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.shopping.app.R
import com.shopping.app.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private lateinit var bnd: FragmentProductBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        init()
        return bnd.root

    }

    private fun init(){



    }

}