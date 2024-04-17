package com.mjstudio.reactnativenavermap.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Animatable
import android.net.Uri
import com.facebook.common.references.CloseableReference
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.view.DraweeHolder
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.image.CloseableStaticBitmap
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.react.bridge.ReadableMap
import com.mjstudio.reactnativenavermap.overlay.marker.OverlayImages
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons

internal typealias ImageRequestCanceller = () -> Unit

internal fun getOverlayImage(
    imageHolder: DraweeHolder<GenericDraweeHierarchy>,
    context: Context,
    map: ReadableMap?,
    callback: (OverlayImage?) -> Unit
): ImageRequestCanceller {
    if (map == null) {
        callback(null)
        return {}
    }

    val symbol = map.getString("symbol") ?: ""

    if (symbol.isNotEmpty()) {
        callback(
            when (symbol) {
                "blue" -> MarkerIcons.BLUE
                "gray" -> MarkerIcons.GRAY
                "green" -> MarkerIcons.GREEN
                "lightblue" -> MarkerIcons.LIGHTBLUE
                "pink" -> MarkerIcons.PINK
                "red" -> MarkerIcons.RED
                "yellow" -> MarkerIcons.YELLOW
                "black" -> MarkerIcons.BLACK
                "lowDensityCluster" -> MarkerIcons.CLUSTER_LOW_DENSITY
                "mediumDensityCluster" -> MarkerIcons.CLUSTER_MEDIUM_DENSITY
                "highDensityCluster" -> MarkerIcons.CLUSTER_HIGH_DENSITY
                else -> null
            }
        )
        return {}
    }

    val rnAssetUri = map.getString("rnAssetUri") ?: ""
    // rnAssetUri starts with http if dev environment(metro server)
    // todo - check how handled in release
    val httpUri = map.getString("httpUri") ?: if (rnAssetUri.startsWith("http")) rnAssetUri else ""
    val assetName = map.getString("assetName") ?: ""
    val reuseIdentifier = map.getString("reuseIdentifier") ?: ""
    debugE(rnAssetUri, httpUri, assetName, reuseIdentifier)

    /**
     * http, https, asset, file all works
     */
    if (httpUri.isNotEmpty()) {
        val key = reuseIdentifier.ifEmpty { httpUri }
        val imageRequest = ImageRequestBuilder
            .newBuilderWithSource(Uri.parse(httpUri))
            .build()
        val dataSource =
            Fresco.getImagePipeline()
                .fetchDecodedImage(imageRequest, context)
        val controller = Fresco.newDraweeControllerBuilder()
            .setImageRequest(imageRequest)
            .setControllerListener(object : BaseControllerListener<ImageInfo?>() {
                override fun onFinalImageSet(
                    id: String,
                    imageInfo: ImageInfo?,
                    animatable: Animatable?
                ) {
                    var imageReference: CloseableReference<CloseableImage>? = null
                    var overlayImage: OverlayImage? = null
                    try {
                        imageReference = dataSource.result
                        if (imageReference != null) {
                            val image = imageReference.get()
                            if (image is CloseableStaticBitmap) {
                                var bitmap: Bitmap? = image.underlyingBitmap
                                if (bitmap != null) {
                                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                                    overlayImage = OverlayImage.fromBitmap(bitmap)
                                    OverlayImages.put(key, overlayImage)
                                }
                            }
                        }
                    } finally {
                        dataSource.close()
                        if (imageReference != null) {
                            CloseableReference.closeSafely(imageReference)
                        }
                        callback(overlayImage)
                    }
                }
            })
            .setOldController(imageHolder.controller)
            .build()
        imageHolder.setController(controller)
        return {
            dataSource.close()
        }
    }

    if (rnAssetUri.isNotEmpty() || assetName.isNotEmpty()) {
        val name = rnAssetUri.ifEmpty { assetName }
        val key = reuseIdentifier.ifEmpty { name }
        callback(OverlayImage.fromResource(getDrawableWithName(context, name)).also {
            OverlayImages.put(key, it)
        })
        return {}
    }




    callback(null)
    return {}
}

@SuppressLint("DiscouragedApi")
private fun getDrawableWithName(context: Context, name: String): Int {
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}