package com.example.data.model

import com.example.domain.model.Beer
import com.example.domain.model.BeerDetail

fun BeerResponse.asBeer() = Beer(
    id = this.id.toString(),
    name = this.name,
    tagline = this.tagline,
    description = this.description,
    imageUrl = this.imageUrl,
)

fun List<BeerResponse>.asBeerList(): List<Beer> {
    return map {
        it.asBeer()
    }
}

fun BeerDetailResponse.asBeerDetail() = BeerDetail(
    id = this.id.toString(),
    name = this.name,
    tagline = this.tagline,
    description = this.description,
    imageUrl = this.imageUrl,
)