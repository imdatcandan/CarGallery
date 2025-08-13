package com.imdatcandan.mobilede.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CarListScreen(
    modifier: Modifier = Modifier,
    viewModel: CarListViewModel = koinViewModel()
) {

    val state = viewModel.state.collectAsState()

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (state.value) {
                is UiState.Loading -> {
                    // Show loading indicator
                    CircularProgressIndicator()
                }

                is UiState.Success -> {
                    // Show car details
                    val images = (state.value as UiState.Success).data

                    var selectedImage by remember { mutableStateOf<String?>(null) }

                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(images.size) { index ->
                            CarImage(
                                modifier = Modifier
                                    .clickable {
                                        selectedImage = images[index].getLargeUrl()
                                    },
                                imageUrl = images[index].getThumbnailUrl()
                            )
                        }
                    }

                    if (selectedImage != null) {
                        Dialog(
                            onDismissRequest = { selectedImage = null }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CarImage(
                                    imageUrl = selectedImage
                                )
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    // Show error message
                    val errorMessage = (state.value as UiState.Error).message
                    Text(text = errorMessage)
                }

            }
        }

}



@Composable
private fun CarImage(modifier: Modifier = Modifier, imageUrl: String?) {
    AsyncImage(
        modifier = modifier,
        model = imageUrl,
        contentDescription = null,
    )
}

