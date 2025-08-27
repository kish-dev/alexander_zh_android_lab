package com.example.database_api.mappers

import com.example.database_api.models.ProductInListDbDTO
import com.example.database_api.models.ProductInListEntity

interface Mapper {
    fun toEntity(dto: ProductInListDbDTO): ProductInListEntity
}