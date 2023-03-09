package com.chainreaction.sample.view.fragment_factory

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.chainreaction.sample.model.utils.AppUtils
import com.chainreaction.sample.view.interfaces.OnProgressLoadingListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class ParentFragment
constructor(layoutRest: Int) :
    Fragment(layoutRest) {

    @Inject
    lateinit var appUtils: AppUtils

    private lateinit var listener: OnProgressLoadingListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnProgressLoadingListener
//        hideNoDataFound()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        appUtils.loading.observe(viewLifecycleOwner) {
            if (it)
                listener.showProgress()
            else
                listener.hideProgress()

        }

    }


    fun showNoDataFound() {
        listener.showNoDataFound()
    }

    private fun hideNoDataFound() {
        listener.hideNoDataFound()
    }

    override fun onStop() {
        super.onStop()
        hideNoDataFound()
        appUtils.loading.value = false
    }

}