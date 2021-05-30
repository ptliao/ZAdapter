package com.hcanyz.zadapter.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcanyz.zadapter.registry.IHolderCreater

class EventViewModel : ViewModel() {

    val clickEvent: MutableLiveData<IHolderCreater> = MutableLiveData()
}