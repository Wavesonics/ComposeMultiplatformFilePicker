import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.MultipleFilePicker
import com.darkrockstudios.libraries.mpfilepicker.SaveFilePicker
import platform.AppKit.NSApp
import platform.AppKit.NSApplication

fun main() {
	NSApplication.sharedApplication()
	Window(title = "Youtube history") {
		MaterialTheme(colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()) {
			Scaffold {
				var showSingleFile by remember { mutableStateOf(false) }
				var showMultipleFile by remember { mutableStateOf(false) }
				var singleFilePathChosen by remember { mutableStateOf("") }
				var multipleFilesPathsChosen by remember { mutableStateOf(listOf("")) }

				var showDirPicker by remember { mutableStateOf(false) }
				var dirChosen by remember { mutableStateOf("") }

				var showSaveFilePicker by remember { mutableStateOf(false) }
				var savedFile by remember { mutableStateOf(false) }

				Column {
					Button(onClick = {
						showSingleFile = true
					}) {
						Text("Choose File")
					}
					Text("File Chosen: $singleFilePathChosen")

					/////////////////////////////////////////////////////////////////


					Button(onClick = {
						showMultipleFile = true
					}) {
						Text("Choose Multiple Files")
					}
					Text("File Chosen: $multipleFilesPathsChosen")

					/////////////////////////////////////////////////////////////////

					Button(onClick = {
						showDirPicker = true
					}) {
						Text("Choose Directory")
					}
					Text("Directory Chosen: $dirChosen")

					/////////////////////////////////////////////////////////////////

					Button(onClick = {
						showSaveFilePicker = true
					}) {
						Text("Choose Save File")
					}
					Text("Saved File: $savedFile")
				}

				FilePicker(showSingleFile, fileExtensions = listOf("jpg", "png", "plist")) { file ->
					singleFilePathChosen = file?.path ?: "none selected"
					showSingleFile = false
				}

				MultipleFilePicker(
					showMultipleFile,
					fileExtensions = listOf("jpg", "png", "plist")
				) { files ->
					multipleFilesPathsChosen = files?.map { it.path + "\n" } ?: listOf()
					showMultipleFile = false
				}

				DirectoryPicker(showDirPicker) { path ->
					dirChosen = path ?: "none selected"
					showDirPicker = false
				}

				SaveFilePicker(
					show = showSaveFilePicker,
					title = "Some title",
					filename = "newTextFile",
					fileExtension = "txt",
					contents = "some nice text for our file",
				) { result ->
					savedFile = result.getOrNull() == true
					showSaveFilePicker = false
				}
			}
		}
	}
	NSApp?.run()
}
