package com.studyflix.android.ui.student.videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyflix.android.core.util.Resource
import com.studyflix.android.domain.model.VideoContent
import com.studyflix.android.domain.usecase.student.GetVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VideosUiState(
    val videos: List<VideoContent> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedSeason: Int = 1
) {
    val seasons: List<Int> get() = videos.map { it.season }.distinct().sorted()
    val visibleVideos: List<VideoContent> get() = videos.filter { it.season == selectedSeason }
}

/** Equivalent of VideoManager on web: loads + season-filters approved videos. */
@HiltViewModel
class VideosViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideosUiState())
    val uiState: StateFlow<VideosUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getVideosUseCase().collect { resource ->
                _uiState.value = when (resource) {
                    is Resource.Loading -> _uiState.value.copy(isLoading = true, errorMessage = null)
                    is Resource.Success -> _uiState.value.copy(
                        videos = resource.data,
                        isLoading = false,
                        errorMessage = null
                    )
                    is Resource.Error -> _uiState.value.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    fun selectSeason(season: Int) {
        _uiState.value = _uiState.value.copy(selectedSeason = season)
    }
}
