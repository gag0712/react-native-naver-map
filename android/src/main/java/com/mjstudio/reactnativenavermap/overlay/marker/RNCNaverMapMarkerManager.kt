package com.mjstudio.reactnativenavermap.overlay.marker

import android.graphics.Color
import android.view.View
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.PixelUtil
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.mjstudio.reactnativenavermap.RNCNaverMapMarkerManagerSpec
import com.mjstudio.reactnativenavermap.event.NaverMapOverlayTapEvent
import com.mjstudio.reactnativenavermap.util.getLatLng
import com.mjstudio.reactnativenavermap.util.getPoint
import com.mjstudio.reactnativenavermap.util.isValidNumber
import com.mjstudio.reactnativenavermap.util.registerDirectEvent
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Marker.SIZE_AUTO


class RNCNaverMapMarkerManager : RNCNaverMapMarkerManagerSpec<RNCNaverMapMarker>() {
    override fun getName(): String {
        return NAME
    }

    override fun createViewInstance(context: ThemedReactContext): RNCNaverMapMarker {
        return RNCNaverMapMarker(context)
    }

    override fun onDropViewInstance(view: RNCNaverMapMarker) {
        super.onDropViewInstance(view)
        view.onDropViewInstance()
    }

    override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any> =
        (super.getExportedCustomDirectEventTypeConstants() ?: mutableMapOf()).apply {
            registerDirectEvent(this, NaverMapOverlayTapEvent.EVENT_NAME)
        }

    private fun RNCNaverMapMarker?.withOverlay(fn: (Marker) -> Unit) {
        this?.overlay?.run(fn)
    }

    override fun addView(parent: RNCNaverMapMarker?, child: View, index: Int) {
        parent?.setCustomView(child, index)
    }

    override fun removeView(parent: RNCNaverMapMarker?, view: View) {
    }

    override fun removeViewAt(parent: RNCNaverMapMarker?, index: Int) {
        parent?.removeCustomView(index)
    }

    @ReactProp(name = "position")
    override fun setPosition(view: RNCNaverMapMarker, value: ReadableMap?) = view.withOverlay {
        value.getLatLng()?.run {
            it.position = this
        }
    }

    @ReactProp(name = "zIndexValue")
    override fun setZIndexValue(view: RNCNaverMapMarker?, value: Int) = view.withOverlay {
        it.zIndex = value
    }

    @ReactProp(name = "isHidden")
    override fun setIsHidden(view: RNCNaverMapMarker?, value: Boolean) = view.withOverlay {
        it.isVisible = !value
    }

    @ReactProp(name = "minZoom")
    override fun setMinZoom(view: RNCNaverMapMarker?, value: Double) = view.withOverlay {
        it.minZoom = value
    }

    @ReactProp(name = "maxZoom")
    override fun setMaxZoom(view: RNCNaverMapMarker?, value: Double) = view.withOverlay {
        it.maxZoom = value
    }

    @ReactProp(name = "isMinZoomInclusive")
    override fun setIsMinZoomInclusive(view: RNCNaverMapMarker?, value: Boolean) =
        view.withOverlay {
            it.isMinZoomInclusive = value
        }

    @ReactProp(name = "isMaxZoomInclusive")
    override fun setIsMaxZoomInclusive(view: RNCNaverMapMarker?, value: Boolean) =
        view.withOverlay {
            it.isMaxZoomInclusive = value
        }

    @ReactProp(name = "width")
    override fun setWidth(view: RNCNaverMapMarker?, value: Double) = view.withOverlay {
        it.width = if (isValidNumber(value)) PixelUtil.toPixelFromDIP(value).toInt() else SIZE_AUTO
    }

    @ReactProp(name = "height")
    override fun setHeight(view: RNCNaverMapMarker?, value: Double) = view.withOverlay {
        it.height = if (isValidNumber(value)) PixelUtil.toPixelFromDIP(value).toInt() else SIZE_AUTO
    }

    @ReactProp(name = "anchor")
    override fun setAnchor(view: RNCNaverMapMarker?, value: ReadableMap?) = view.withOverlay {
        value.getPoint()?.run {
            it.anchor = this
        }
    }

    @ReactProp(name = "angle")
    override fun setAngle(view: RNCNaverMapMarker?, value: Double) = view.withOverlay {
        it.angle = value.toFloat()
    }

    @ReactProp(name = "isFlatEnabled")
    override fun setIsFlatEnabled(view: RNCNaverMapMarker?, value: Boolean) = view.withOverlay {
        it.isFlat = value
    }

    @ReactProp(name = "isIconPerspectiveEnabled")
    override fun setIsIconPerspectiveEnabled(view: RNCNaverMapMarker?, value: Boolean) =
        view.withOverlay {
            it.isIconPerspectiveEnabled = value
        }

    @ReactProp(name = "alpha")
    override fun setAlpha(view: RNCNaverMapMarker?, value: Double) = view.withOverlay {
        it.alpha = value.toFloat()
    }

    @ReactProp(name = "isHideCollidedSymbols")
    override fun setIsHideCollidedSymbols(view: RNCNaverMapMarker?, value: Boolean) =
        view.withOverlay {
            it.isHideCollidedSymbols = value
        }

    @ReactProp(name = "isHideCollidedMarkers")
    override fun setIsHideCollidedMarkers(view: RNCNaverMapMarker?, value: Boolean) =
        view.withOverlay {
            it.isHideCollidedMarkers = value
        }

    @ReactProp(name = "isHideCollidedCaptions")
    override fun setIsHideCollidedCaptions(view: RNCNaverMapMarker?, value: Boolean) =
        view.withOverlay {
            it.isHideCollidedCaptions = value
        }

    @ReactProp(name = "isForceShowIcon")
    override fun setIsForceShowIcon(view: RNCNaverMapMarker?, value: Boolean) = view.withOverlay {
        it.isForceShowIcon = value
    }

    @ReactProp(name = "tintColor")
    override fun setTintColor(view: RNCNaverMapMarker?, value: Int?) = view.withOverlay {
        value?.run {
            it.iconTintColor =
                Color.argb(Color.alpha(this), Color.red(this), Color.green(this), Color.blue(this))
        }
    }

    @ReactProp(name = "image")
    override fun setImage(view: RNCNaverMapMarker?, value: String?) {
        view?.setImage(value)
    }


    // region PROPS

    companion object {
        const val NAME = "RNCNaverMapMarker"
    }
}
