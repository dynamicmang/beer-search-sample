package com.example.feature.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.model.Beer
import com.example.feature.main.R

@Composable
fun MainSearchList(beerList: List<Beer>, onLoadMore: () -> Unit, onItemClick: (String) -> Unit) {
    EndlessLazyColumn(
        items = beerList,
        itemKey = { beer -> beer.id },
        itemContent = {
            MainSearchItem(it, onItemClick)
            Divider(thickness = 1.dp, color = Color.LightGray)
        },
        loadingItem = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        loadMore = {
            onLoadMore()
        }
    )
}

@Composable
private fun MainSearchItem(beer: Beer, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onItemClick(beer.id) }
            .padding(10.dp)
    ) {
        AsyncImage(
            model = beer.imageUrl,
            contentDescription = null,
            error = painterResource(R.drawable.error),
            modifier = Modifier.size(70.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Text(
                text = beer.name,
                fontSize = 16.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = beer.tagline,
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            imageVector = if (beer.isBookmark) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(20.dp))
    }
}