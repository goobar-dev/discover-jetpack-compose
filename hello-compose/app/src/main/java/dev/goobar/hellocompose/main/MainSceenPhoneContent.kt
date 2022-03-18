package dev.goobar.hellocompose.main

import androidx.compose.runtime.Composable
import dev.goobar.hellocompose.AndroidVersionInfo
import dev.goobar.hellocompose.main.androidversiondetails.AndroidVersionDetails
import dev.goobar.hellocompose.main.androidversiondetails.AndroidVersionDetailsViewModel
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsList
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsListViewModel

@Composable
fun MainScreenPhoneContent(
  versionsListState: AndroidVersionsListViewModel.State,
  selectedItem: AndroidVersionInfo?,
  onVersionInfoClick: (AndroidVersionInfo) -> Unit,
  onBackClick: () -> Unit
) {
  when (val currentItem = selectedItem) {
    null -> {
      AndroidVersionsList(
        state = versionsListState,
        onClick = onVersionInfoClick
      )
    }
    else -> {
      val viewModel = AndroidVersionDetailsViewModel(currentItem)
      AndroidVersionDetails(viewModel = viewModel, onBackClick = onBackClick)
    }
  }
}