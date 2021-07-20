package com.gompa.remotehttpcommand.screens.editor

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.*
import com.gompa.models.HttpMethod
import com.gompa.models.Request
import com.gompa.remotehttpcommand.screens.editor.repository.IconRepository
import com.gompa.remotehttpcommand.screens.editor.repository.RequestRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class RequestEditorViewModel(
    private val iconRepository: IconRepository,
    private val requestRepository: RequestRepository
) : ViewModel() {

    private val _timeOut = MutableLiveData<String>()
    val timeOut: LiveData<String> = _timeOut

    val icon: LiveData<ImageVector> = IconRepository.icon

    private var title: String = ""
    private var url: String = ""
    private var method: String? = null
    private var retry: Boolean = false
    private var followRedirect: Boolean = false
    private var headers: MutableList<Header> = mutableListOf()
    private var requestBody: RequestBody = RequestBody()

    fun onSaveIcon(icon: ImageVector) { // TODO replace ImageVector
        iconRepository.onSaveIcon(icon)
    }

    fun onTitleChanged(title: String) {
        this.title = title
    }

    fun onUrlChanged(url: String) {
        this.url = url
    }

    fun onMethodSelected(methodItem: MethodItem?) {
        methodItem?.let { method = methodItem.name }
    }

    fun onRetryChanged(retry: Boolean) {
        this.retry = retry
    }

    fun onFollowRedirectsChanged(followRedirect: Boolean) {
        this.followRedirect = followRedirect
    }

    fun onTimeOutChanged(timeOut: String) {
        _timeOut.value = timeOut
    }

    fun onHeaderAdded(header: Header) {
        if (!headers.contains(header)) {
            headers.add(header)
        }
    }

    fun onHeaderRemoved(header: Header) {
        if (headers.isNotEmpty() && headers.contains(header)) {
            headers.remove(header)
        }
    }

    fun onRequestBodyChanged(body: String, isEnabled: Boolean = false) {
        requestBody = RequestBody(body, isEnabled)
    }

    fun saveRequest(action: ()-> Unit) {
        viewModelScope.launch(context = Dispatchers.IO) {
            requestRepository.saveRequest(request = buildRequest())
            viewModelScope.launch(context = Dispatchers.Main) { action() }
        }
    }

    private fun buildRequest(): Request = Request(
        title = title,
        url = url,
        method = HttpMethod.valueOf(method ?: HttpMethod.GET.name),
        timeout = timeOut.value?.toLong() ?: 0, // validation
        retry = retry,
        followRedir = followRedirect,
        headers = headers.associate { it.name to it.value },
        body = if (requestBody.isEnabled) requestBody.body else null,
    )

    fun deleteRequest() {
        TODO("Not yet implemented - if exist delete in room")
    }

    data class ScreenState(
        val icon: String, // this will map ImageVector,
        val request: Request
    )
}

@Suppress("UNCHECKED_CAST")
object RequestEditorViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RequestEditorViewModel::class.java)) {
            return RequestEditorViewModel(
                RequestEditorModule.iconRepository(),
                RequestEditorModule.requestRepository()
            ) as T
        }
        throw IllegalArgumentException("Cannot create view model from class: $modelClass")
    }
}

data class Header(val name: String = "", val value: String = "")

data class MethodItem(val name: String, var isSelected: Boolean = false)

data class RequestBody(val body: String = "", val isEnabled: Boolean = false)
