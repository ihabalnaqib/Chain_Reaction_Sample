package com.chainreaction.sample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chainreaction.sample.model.database.InventoryDao
import com.chainreaction.sample.model.model.InventoryModel
import com.chainreaction.sample.model.network_utils.APIServices
import com.chainreaction.sample.model.network_utils.remote.NetworkResponse
import com.chainreaction.sample.model.utils.AppUtils
import com.chainreaction.sample.view.di.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val apiServices: APIServices,
    private val appUtils: AppUtils,
    private val defaultDispatcher: DispatcherProvider,
    private val inventoryDao: InventoryDao
) : ViewModel() {

    private val _mInventoriesFlow: MutableStateFlow<InventoryStatus> by lazy {
        MutableStateFlow(InventoryStatus.EmptyInventory)
    }
    val mInventoriesFlow by lazy { _mInventoriesFlow }

    fun getInventoriesFromDatabase() = viewModelScope.launch(
        defaultDispatcher.io
    ) {

        inventoryDao.getInventories().collect {

            if (it.isNullOrEmpty())
                getInventories()
            else {
                _mInventoriesFlow.emit(InventoryStatus.Success(it))

            }

        }

    }

    private fun getInventories(
    ) = viewModelScope.launch(
        defaultDispatcher.io
    ) {

        if (_mInventoriesFlow.value != InventoryStatus.EmptyInventory)
            return@launch


        if (appUtils.loading.value == true) return@launch

        appUtils.loading.postValue(true)

        val response =
            apiServices.getInventories()

        appUtils.loading.postValue(false)

        if (response is NetworkResponse.Success && !response.body.isNullOrEmpty()) {
            viewModelScope.launch(defaultDispatcher.default) {
                inventoryDao.addInventories(response.body)
            }
            _mInventoriesFlow.emit(InventoryStatus.Success(response.body))
        } else if (response is NetworkResponse.ApiError) {
            _mInventoriesFlow.emit(InventoryStatus.Failed(response.body.errorMessage ?: ""))
        } else {
            _mInventoriesFlow.emit(InventoryStatus.Failed("No Inventory found"))
        }


    }





    override fun onCleared() {
        super.onCleared()
        defaultDispatcher.io.cancelChildren()
    }

    sealed class InventoryStatus {

        data class Failed(val error: String) : InventoryStatus()
        object EmptyInventory : InventoryStatus()
        data class Success(val model: List<InventoryModel>) : InventoryStatus()
        object SuccessNoDataFound : InventoryStatus()


    }

}

