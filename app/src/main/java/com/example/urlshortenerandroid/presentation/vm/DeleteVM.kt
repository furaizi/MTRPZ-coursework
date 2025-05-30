package com.example.urlshortenerandroid.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urlshortenerandroid.domain.usecase.DeleteLinkUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Одноразовая задача — удалить ссылку и сообщить результат.
 * Здесь используем Channel, а не StateFlow, чтобы событие не повторялось при
 * recreation-ах экрана (например, после поворота).
 */
@HiltViewModel
class DeleteVM @Inject constructor(
    savedState: SavedStateHandle,
    private val deleteLink: DeleteLinkUC
) : ViewModel() {

    private val linkId: String = requireNotNull(savedState["id"])

    sealed interface Event {
        data object Success : Event
        data class Error(val msg: String) : Event
    }

    private val _events = Channel<Event>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun delete() = viewModelScope.launch {
        deleteLink(linkId).fold(
            onSuccess = { _events.send(Event.Success) },
            onFailure = { _events.send(Event.Error(it.message ?: "Ошибка удаления")) }
        )
    }
}