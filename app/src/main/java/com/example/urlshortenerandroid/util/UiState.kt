package com.example.urlshortenerandroid.util

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<out T>(val data: T) : UiState<T>
    data class Error(val msg: String, val code: Int? = null): UiState<Nothing>
}