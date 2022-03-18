package dev.goobar.hellocompose.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import dev.goobar.hellocompose.AndroidVersionInfo
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsListViewModel

@Composable
fun MainScreenContent(
  versionsListState: AndroidVersionsListViewModel.State,
  selectedItem: AndroidVersionInfo?,
  onVersionInfoClick: (AndroidVersionInfo) -> Unit,
  onBackClick: () -> Unit
) {
  val configuration = LocalConfiguration.current
  if (configuration.smallestScreenWidthDp < 720) {
    MainScreenPhoneContent(
      versionsListState = versionsListState,
      selectedItem = selectedItem,
      onVersionInfoClick = onVersionInfoClick,
      onBackClick = onBackClick
    )
  } else {
    MainScreenTabletContent(
      versionsListState = versionsListState,
      selectedItem = selectedItem,
      onVersionInfoClick = onVersionInfoClick,
      onBackClick = onBackClick
    )
  }
}