package com.chainreaction.sample.view.fragment_factory

import androidx.fragment.app.FragmentFactory
import com.chainreaction.sample.model.utils.AppUtils
import javax.inject.Inject

class CustomFragmentFactory
@Inject constructor(
    private val appUtils: AppUtils
) : FragmentFactory() {

}