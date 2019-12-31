package com.hcanyz.zadapter.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcanyz.zadapter.registry.IHolderCreatorName

class EventViewModel : ViewModel() {

    val clickEvent: MutableLiveData<IHolderCreatorName> = MutableLiveData()
}