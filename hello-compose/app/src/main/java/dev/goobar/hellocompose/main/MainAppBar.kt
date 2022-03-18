package dev.goobar.hellocompose.main

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.goobar.hellocompose.AndroidVersionInfo
import dev.goobar.hellocompose.R.drawable

@Composable
fun MainAppBar(
  selectedItem: AndroidVersionInfo?,
  onBackClick: () -> Unit,
  onSortClick: () -> Unit
) {
  TopAppBar(
    contentPadding = PaddingValues(horizontal = 20.dp),
  ) {
    val configuration = LocalConfiguration.current
    if (configuration.smallestScreenWidthDp < 720) {
      MainAppBarPhoneContent(
        selectedItem = selectedItem,
        onBackClick = onBackClick,
        onSortClick = onSortClick
      )
    } else {
      MainAppBarTabletContent(onSortClick)
    }
  }
}

@Composable
private fun MainAppBarPhoneContent(
  selectedItem: AndroidVersionInfo?,
  onBackClick: () -> Unit,
  onSortClick: () -> Unit
) {
  when (selectedItem) {
    null -> {
      Row() {
        Text("Hello Jetpack Compose", modifier = Modifier.weight(1f))
        Icon(
          painter = painterResource(id = drawable.ic_sort),
          contentDescription = "Sort icon",
          modifier = Modifier.clickable { onSortClick() }
        )
      }
    }
    else -> {
      IconButton(onClick = onBackClick) {
        Icon(painter = painterResource(id = drawable.ic_arrow_back), contentDescription = "Back arrow")
      }
      Text(text = selectedItem.publicName, modifier = Modifier.padding(start = 20.dp))
    }
  }
}

@Composable
private fun MainAppBarTabletContent(onSortClick: () -> Unit) {
  Row() {
    Text("Hello Jetpack Compose", modifier = Modifier.weight(1f))
    Icon(
      painter = painterResource(id = drawable.ic_sort),
      contentDescription = "Sort icon",
      modifier = Modifier.clickable { onSortClick() }
    )
  }
}