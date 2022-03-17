package dev.goobar.hellocompose

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize

@Parcelize
data class AndroidVersionInfo(
  val apiVersion: Int,
  val publicName: String,
  val codename: String,
  val details: String
): Parcelable
