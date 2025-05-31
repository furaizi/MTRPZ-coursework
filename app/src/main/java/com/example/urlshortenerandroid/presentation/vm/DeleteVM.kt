package com.example.urlshortenerandroid.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urlshortenerandroid.domain.usecase.DeleteLinkUC
import com.example.urlshortenerandroid.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * One-time task — delete the link and report the result.
 * Here we use Channel instead of StateFlow so that the event
 * is not re-emitted on screen recreation (for example, after rotation).
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
        // Start deletion and handle NetworkResult
        when (val result = deleteLink(linkId)) {
            is NetworkResult.Success -> {
                _events.send(Event.Success)
            }
            is NetworkResult.Error -> {
                _events.send(Event.Error(result.message))
            }
            // We do not handle Loading here because deleteLink is a single call
            else -> {
                _events.send(Event.Error("Unknown error"))
            }
        }
    }
}