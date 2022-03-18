package dev.goobar.hellocompose.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import dev.goobar.hellocompose.AndroidVersionInfo
import dev.goobar.hellocompose.main.androidversiondetails.AndroidVersionDetails
import dev.goobar.hellocompose.main.androidversiondetails.AndroidVersionDetailsViewModel
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsList
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsListViewModel

@Composable
fun MainScreenTabletContent(
  versionsListState: AndroidVersionsListViewModel.State,
  selectedItem: AndroidVersionInfo?,
  onVersionInfoClick: (AndroidVersionInfo) -> Unit,
  onBackClick: () -> Unit
) {
  TabletContentScaffold(
    listContent = {
      AndroidVersionsList(
        state = versionsListState,
        onClick = onVersionInfoClick
      )
    },
    detailsContent = {
      selectedItem?.let {
        val viewModel = AndroidVersionDetailsViewModel(it)
        AndroidVersionDetails(viewModel = viewModel, onBackClick = onBackClick)
      }
    }
  )
}

@Composable
private fun TabletContentScaffold(
  listContent: @Composable BoxScope.() -> Unit,
  detailsContent: @Composable BoxScope.() -> Unit
) {
  val configuration = LocalConfiguration.current
  when (configuration.orientation) {
    Configuration.ORIENTATION_PORTRAIT -> {
      Row() {
        Box(modifier = Modifier.weight(1f), content = listContent)
        Box(modifier = Modifier.weight(1f), content = detailsContent)
      }
    }
    else -> {
      Column() {
        Box(modifier = Modifier.weight(2f), content = detailsContent)
        Box(modifier = Modifier.weight(1f), content = listContent)
      }
    }
  }
}