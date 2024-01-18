package com.example.feature.main.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.domain.model.BeerDetail
import com.example.feature.main.R
import com.example.feature.main.viewmodel.MainDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDetailScreen(
    viewModel: MainDetailViewModel = hiltViewModel(),
    onBackListener: () -> Unit,
) {
    val context = LocalContext.current
    val beerDetail = viewModel.beerDetail.collectAsStateWithLifecycle().value
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
        TopAppBar(
            title = { },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .clickable { onBackListener() }
                )
            },
            actions = {
                Icon(
                    imageVector = if (beerDetail.isBookmark) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable {
                            viewModel.updateBookmark()
                        }
                )
            }
        )
        BeerDetailScreen(beerDetail)
    }
}

@Composable
fun BeerDetailScreen(beerDetail: BeerDetail) {
    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        AsyncImage(
            model = beerDetail.imageUrl,
            contentDescription = null,
            error = painterResource(R.drawable.error),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = beerDetail.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = beerDetail.tagline,
            fontSize = 18.sp,
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = beerDetail.description,
            fontSize = 16.sp,
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}