package com.demo.mvvm.app.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.mvvm.app.data.model.FactModel
import com.demo.mvvm.app.data.repository.MainRepository
import com.demo.mvvm.app.utils.Mockable
import com.demo.mvvm.app.utils.Resource

import kotlinx.coroutines.launch

@Mockable
class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {


    private val _facts = MutableLiveData<Resource<FactModel>>()
    val facts: LiveData<Resource<FactModel>>
        get() = _facts


    fun fetchFacts(renderLoader: Boolean = false) {
        viewModelScope.launch {
            if (renderLoader) {
                _facts.postValue(Resource.loading(null))
            }

            try {

                val factsFromApi = mainRepository.getFacts()
                _facts.postValue(Resource.success(factsFromApi))

            } catch (e: Exception) {
                _facts.postValue(e.message?.let { Resource.error(e.toString(), null) })
            }

        }

    }
}