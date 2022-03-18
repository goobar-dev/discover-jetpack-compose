package dev.goobar.hellocompose.main

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import dev.goobar.hellocompose.AndroidVersionInfo
import dev.goobar.hellocompose.AndroidVersionsRepository
import dev.goobar.hellocompose.design.HelloComposeTheme
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsListViewModel
import dev.goobar.hellocompose.main.androidversionslist.AndroidVersionsListViewModel.State.AndroidVersionViewItem
import org.junit.Rule
import org.junit.Test

class MainScreenPhoneContentTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun versionsListDisplayed() {
    composeTestRule.setContent {
      HelloComposeTheme {
        MainScreenPhoneContent(
          versionsListState = getTestData(),
          selectedItem = null,
          onVersionInfoClick = {},
          onBackClick = {}
        )
      }
    }

    // verify that the MainScreenPhoneContent composable includes
    // an element with testTag == "Versions List"
    composeTestRule
      .onNodeWithTag(testTag = "Versions List")
      .assertExists(errorMessageOnFail = "Versions List Missing")
  }

  @Test
  fun versionsListDisplaysFirstVersionInfo() {
    val testDate = getTestData()
    composeTestRule.setContent {
      HelloComposeTheme {
        MainScreenPhoneContent(
          versionsListState = testDate,
          selectedItem = null,
          onVersionInfoClick = {},
          onBackClick = {}
        )
      }
    }

    // find the list
    // get the first child of the list
    // verify that the child includes the expected title text
  }

  @Test
  fun printTreeToLog() {
    val testDate = getTestData()
    composeTestRule.setContent {
      HelloComposeTheme {
        MainScreenPhoneContent(
          versionsListState = testDate,
          selectedItem = null,
          onVersionInfoClick = {},
          onBackClick = {}
        )
      }
    }

    // print the semantics tree to logcat to examine it
    // add custom testTag to individual list items to make them easier to identify
    // print a third time using unmerged tree
  }

  @Test
  fun versionInfoClickHandlerCalledWhenCardIsClicked() {
    val testDate = getTestData()
    var selectedItem: AndroidVersionInfo? = null

    composeTestRule.setContent {
      HelloComposeTheme {
        MainScreenPhoneContent(
          versionsListState = testDate,
          selectedItem = null,
          onVersionInfoClick = { clickedInfo -> selectedItem = clickedInfo },
          onBackClick = {}
        )
      }
    }

    // verify that the selected item is correctly updated when the first card is clicked
  }
}

private fun getTestData() = AndroidVersionsListViewModel.State(
  versionsList = AndroidVersionsRepository.data.map {
    AndroidVersionViewItem(it.publicName, it.codename, it.details, it)
  }
)