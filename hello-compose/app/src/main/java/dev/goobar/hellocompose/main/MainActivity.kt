package dev.goobar.hellocompose.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue.Collapsed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.goobar.hellocompose.AndroidVersionInfo
import dev.goobar.hellocompose.R.drawable
import dev.goobar.hellocompose.main.androidversiondetails.AndroidVersionDetails
import dev.goobar.hellocompose.main.androidversiondetails.AndroidVersionDetailsViewModel
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsList
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsListViewModel
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsListViewModel.Event.SortChanged
import dev.goobar.hellocompose.main.androidversionslist.Sort
import dev.goobar.hellocompose.main.androidversionslist.Sort.ASCENDING
import dev.goobar.hellocompose.main.androidversionslist.Sort.DESCENDING
import dev.goobar.hellocompose.design.HelloComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloComposeTheme() {
        MainActivityScreen()
      }
    }
  }


  @OptIn(ExperimentalMaterialApi::class)
  @Composable
  private fun MainActivityScreen() {
    var selectedItem by rememberSaveable { mutableStateOf<AndroidVersionInfo?>(null) }
    val versionsListViewModel by viewModels<AndroidVersionsListViewModel>()
    val versionsListState by versionsListViewModel.state.collectAsState()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(Collapsed))
    val coroutineScope = rememberCoroutineScope()

    MainActivityContent(
      versionsListState,
      selectedItem,
      bottomSheetScaffoldState,
      { selectedItem = null },
      {
        coroutineScope.launch {
          if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
            bottomSheetScaffoldState.bottomSheetState.expand()
          } else {
            bottomSheetScaffoldState.bottomSheetState.collapse()
          }
        }
      },
      onCloseClicked = {
        coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
      },
      onSortSelected = { selectedSort ->
        coroutineScope.launch {
          bottomSheetScaffoldState.bottomSheetState.collapse()
          versionsListViewModel.onEvent(SortChanged(selectedSort))
          bottomSheetScaffoldState.snackbarHostState.showSnackbar(
            message = when (selectedSort) {
              ASCENDING -> "Showing oldest first"
              DESCENDING -> "Showing newest first"
            }
          )
        }
      },
      onVersionInfoClick = { clickedInfo -> selectedItem = clickedInfo }
    )

  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainActivityContent(
  versionsListState: AndroidVersionsListViewModel.State,
  selectedItem: AndroidVersionInfo? = null,
  scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(Collapsed)),
  onBackClick: () -> Unit = {},
  onSortClick: () -> Unit = {},
  onCloseClicked: () -> Unit = {},
  onSortSelected: (Sort) -> Unit = {},
  onVersionInfoClick: (AndroidVersionInfo) -> Unit = {}
) {
  BottomSheetScaffold(
    scaffoldState = scaffoldState,
    topBar = {
      MainAppBar(
        selectedItem = selectedItem,
        onBackClick = onBackClick,
        onSortClick = onSortClick,
      )
    },
    sheetContent = { MainBottomSheetContent(
      onCloseClicked = onCloseClicked,
      onSortSelected = onSortSelected
    ) },
    sheetPeekHeight = 0.dp
  ) {
    MainScreenContent(
      versionsListState = versionsListState,
      selectedItem = selectedItem,
      onVersionInfoClick = onVersionInfoClick,
      onBackClick = onBackClick
    )
  }
}

@Composable
private fun MainBottomSheetContent(
  onSortSelected: (Sort) -> Unit,
  onCloseClicked: () -> Unit
) {
  Column(modifier = Modifier
    .defaultMinSize(minHeight = 160.dp)
    .fillMaxWidth(1f)
    .padding(20.dp)) {
    Text(
      text = "Close",
      modifier = Modifier
        .align(Alignment.End)
        .clickable { onCloseClicked() },
      color = MaterialTheme.colors.error
    )
    Text(
      text = "Newest first",
      modifier = Modifier
        .padding(vertical = 8.dp)
        .clickable { onSortSelected(DESCENDING) }
    )
    Text(
      text = "Oldest first",
      modifier = Modifier
        .padding(vertical = 8.dp)
        .clickable { onSortSelected(ASCENDING) }
    )
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun MainActivityContent_EmptyData_Preview() {
  HelloComposeTheme() {
    MainActivityContent(
      versionsListState = AndroidVersionsListViewModel.State(emptyList())
    )
  }
}