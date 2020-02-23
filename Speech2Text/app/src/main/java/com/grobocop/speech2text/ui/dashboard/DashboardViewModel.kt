package com.grobocop.speech2text.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grobocop.speech2text.Model.SampleModel

class DashboardViewModel : ViewModel() {
    fun changeText() {
        model.text = "some another text"
        _text.value = model.text
    }

    companion object {
        val model = SampleModel()
    }

    val _text = MutableLiveData<String>().apply {
        value = model.text
    }

    val text: LiveData<String> = _text
}