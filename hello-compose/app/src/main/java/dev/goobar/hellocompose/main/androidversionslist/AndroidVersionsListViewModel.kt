package dev.goobar.hellocompose.main.androidversionslist

import androidx.lifecycle.ViewModel
import dev.goobar.hellocompose.AndroidVersionInfo
import dev.goobar.hellocompose.AndroidVersionsRepository
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsListViewModel.Event.SortChanged
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsListViewModel.State.AndroidVersionViewItem
import dev.goobar.hellocompose.main.androidversionslist.Sort.ASCENDING
import dev.goobar.hellocompose.main.androidversionslist.Sort.DESCENDING
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AndroidVersionsListViewModel : ViewModel() {

  private var sort = Sort.ASCENDING

  private val _state = MutableStateFlow(generateState())
  val state = _state.asStateFlow()

  fun onEvent(event: Event) {
    when (event) {
      is SortChanged -> {
        sort = event.sort
        _state.value = generateState()
      }
    }
  }

  private fun generateState() = State(
    versionsList = when (sort) {
      ASCENDING -> AndroidVersionsRepository.data.sortedBy { it.apiVersion }
      DESCENDING -> AndroidVersionsRepository.data.sortedByDescending { it.apiVersion }
    }.map { info ->
      AndroidVersionViewItem(
        title = info.publicName,
        subtitle = "${info.codename} - API ${info.apiVersion}",
        description = info.details,
        info = info
      )
    }
  )

  sealed class Event {
    data class SortChanged(val sort: Sort): Event()
  }

  data class State(
    val versionsList: List<AndroidVersionViewItem>
  ) {
    data class AndroidVersionViewItem(
      val title: String,
      val subtitle: String,
      val description: String,
      val info: AndroidVersionInfo
    )
  }
}