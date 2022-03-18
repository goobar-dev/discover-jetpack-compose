package dev.goobar.hellocompose.main.androidversiondetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun AndroidVersionDetails(viewModel: AndroidVersionDetailsViewModel, onBackClick: () -> Unit) {

  val state: AndroidVersionDetailsViewModel.State by viewModel.state.collectAsState()

  BackHandler(enabled = true, onBack = onBackClick)
  Column(modifier = Modifier.padding(20.dp)) {
    Text(text = state.title, style = MaterialTheme.typography.h3)
    Text(text = state.subtitle, style = MaterialTheme.typography.subtitle2)
    Text(
      modifier = Modifier.padding(top = 20.dp),
      text = state.description,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis
    )
  }
}