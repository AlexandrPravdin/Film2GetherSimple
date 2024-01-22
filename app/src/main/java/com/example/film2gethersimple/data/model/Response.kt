package com.example.film2gethersimple.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val kind: String,
    val totalItems: Long,
    val items: List<Item>,
)

@Serializable
data class Item(
    val kind: String,
    val id: String,
    val etag: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo,
    val accessInfo: AccessInfo,
    val searchInfo: SearchInfo,
)

@Serializable
data class VolumeInfo(
    val title: String,
    val subtitle: String?,
    val authors: List<String>? = null,
    val publisher: String?,
    val publishedDate: String = "No date",
    val description: String? = null,
    val industryIdentifiers: List<IndustryIdentifier>? = null,
    val readingModes: ReadingModes,
    val pageCount: Long,
    val printType: String,
    val categories: List<String>,
    val maturityRating: String,
    val allowAnonLogging: Boolean,
    val contentVersion: String,
    val panelizationSummary: PanelizationSummary,
    val imageLinks: ImageLinks,
    val language: String,
    val previewLink: String,
    val infoLink: String,
    val canonicalVolumeLink: String,
)

@Serializable
data class IndustryIdentifier(
    val type: String,
    val identifier: String,
)

@Serializable
data class ReadingModes(
    val text: Boolean,
    val image: Boolean,
)

@Serializable
data class PanelizationSummary(
    val containsEpubBubbles: Boolean,
    val containsImageBubbles: Boolean,
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
)

@Serializable
data class SaleInfo(
    val country: String,
    val saleability: String,
    val isEbook: Boolean,
    val listPrice: ListPrice?,
    val retailPrice: RetailPrice?,
    val buyLink: String?,
    val offers: List<Offer>?,
)

@Serializable
data class ListPrice(
    val amount: Double,
    val currencyCode: String,
)

@Serializable
data class RetailPrice(
    val amount: Double,
    val currencyCode: String,
)

@Serializable
data class Offer(
    val finskyOfferType: Long,
    val listPrice: ListPrice2,
    val retailPrice: RetailPrice2,
)

@Serializable
data class ListPrice2(
    val amountInMicros: Long,
    val currencyCode: String,
)

@Serializable
data class RetailPrice2(
    val amountInMicros: Long,
    val currencyCode: String,
)

@Serializable
data class AccessInfo(
    val country: String,
    val viewability: String,
    val embeddable: Boolean,
    val publicDomain: Boolean,
    val textToSpeechPermission: String,
    val epub: Epub,
    val pdf: Pdf,
    val webReaderLink: String,
    val accessViewStatus: String,
    val quoteSharingAllowed: Boolean,
)

@Serializable
data class Epub(
    val isAvailable: Boolean,
    val acsTokenLink: String?,
)

@Serializable
data class Pdf(
    val isAvailable: Boolean,
    val acsTokenLink: String?,
)

@Serializable
data class SearchInfo(
    val textSnippet: String,
)

