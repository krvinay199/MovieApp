package com.example.movieapp.presentation.view.details.uiState

import com.example.movieapp.domain.model.Details

data class DetailsState(
    val data: Details? = null,
    val error: String = " ",
    val isLoading: Boolean = false
)