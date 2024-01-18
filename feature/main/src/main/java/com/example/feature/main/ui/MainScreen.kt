package com.example.feature.main.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.main.R
import com.example.feature.main.stae.SearchUiState
import com.example.feature.main.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit,
) {
    val context = LocalContext.current
    val searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle().value
    val searchUiState = viewModel.searchUiState.collectAsStateWithLifecycle().value
    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.error.collect {
                Toast.makeText(context, context.getString(R.string.error_message), Toast.LENGTH_SHORT).show()
            }
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        MainSearchBar(
            searchQuery = searchQuery,
            onTextChanged = {
                viewModel.updateSearchQuery(it)
            }
        )
        MainContent(
            searchUiState = searchUiState,
            onItemClick = onItemClick,
            onLoadMore = {
                viewModel.loadMoreSearchResult()
            }
        )
    }
}

@Composable
fun MainContent(
    searchUiState: SearchUiState,
    onItemClick: (String) -> Unit,
    onLoadMore: () -> Unit,
) {
    when (searchUiState) {
        SearchUiState.None -> {}
        SearchUiState.Empty -> {
            MainEmpty()
        }
        SearchUiState.Loading -> {
            MainLoading()
        }
        is SearchUiState.Result -> {
            MainSearchList(searchUiState.beerList, onLoadMore, onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainSearchBar(searchQuery: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        textStyle = TextStyle.Default.copy(fontSize = 18.sp, color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = searchQuery,
        singleLine = true,
        onValueChange = {
            val query = it.trim()
            onTextChanged(query)
        },
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = null
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.input_hint),
                fontSize = 18.sp,
                fontWeight = FontWeight.Thin,
                color = Color.Gray,
            )
        },
    )
}

@Composable
private fun MainEmpty() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.empty_search_result),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun MainLoading() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen {

    }
}