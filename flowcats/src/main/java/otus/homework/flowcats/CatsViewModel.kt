package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result>(Result.Progress)
    val catsFlow = _catsFlow.asStateFlow()

    init {
        catsRepository.listenForCatFacts()
            .onEach { _catsFlow.value = Result.Success(it) }
            .catch { _catsFlow.value = Result.Error(it.message) }
            .launchIn(viewModelScope)
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(CatsRepository::class.java).newInstance(catsRepository)
}

sealed class Result {
    data object Progress : Result()
    data class Success(val fact: Fact) : Result()
    data class Error(val message: String?) : Result()
}