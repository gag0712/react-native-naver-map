"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.useInfoWindow = void 0;
var _react = require("react");
var _useStableCallback = require("../internal/util/useStableCallback.js");
var _NativeRNCNaverMapUtil = _interopRequireDefault(require("../spec/NativeRNCNaverMapUtil.js"));
function _interopRequireDefault(e) { return e && e.__esModule ? e : { default: e }; }
/**
 * Hook for managing InfoWindow instances on Naver Map.
 * Provides methods to show InfoWindow on map or marker.
 *
 * @param content - Content to display in InfoWindow
 * @returns Object with methods to control InfoWindow
 *
 * @example
 * ```tsx
 * const infoWindow = useInfoWindow({
 *   text: "Location Name"
 * });
 *
 * // Show on map at specific position
 * infoWindow.showOnMap({
 *   mapRef: mapRef,
 *   position: { latitude: 37.5665, longitude: 126.9780 }
 * });
 *
 * // Show on marker
 * infoWindow.showOnMarker({ markerRef });
 * ```
 */
const useInfoWindow = content => {
  const id = (0, _react.useRef)(`info_window_${Date.now()}_${Math.random()}`).current;
  (0, _react.useEffect)(() => {
    // Create InfoWindow instance on mount
    _NativeRNCNaverMapUtil.default.createInfoWindow(id);
    _NativeRNCNaverMapUtil.default.setInfoWindowContent(id, content.text);

    // Cleanup on unmount
    return () => {
      _NativeRNCNaverMapUtil.default.destroyInfoWindow(id);
    };
  }, [id]);
  (0, _react.useEffect)(() => {
    // Update content when it changes
    _NativeRNCNaverMapUtil.default.setInfoWindowContent(id, content.text);
  }, [content.text, id]);
  (0, _react.useEffect)(() => {
    _NativeRNCNaverMapUtil.default.setInfoWindowOptions(id, content.anchor?.x ?? 0.5, content.anchor?.y ?? 1, content.offset?.x ?? 0, content.offset?.y ?? 0, content.alpha ?? 1);
  }, [content.anchor?.x, content.anchor?.y, content.offset?.x, content.offset?.y, content.alpha, id]);
  const showOnMap = (0, _useStableCallback.useStableCallback)(({
    mapRef,
    position
  }) => {
    if (!mapRef.current) {
      console.warn('useInfoWindow: mapRef.current is null');
      return false;
    }
    mapRef.current.showInfoWindow(id, position);
    return true;
  });
  const showOnMarker = (0, _useStableCallback.useStableCallback)(({
    markerRef,
    alignType
  }) => {
    if (!markerRef.current) {
      console.warn('useInfoWindow: markerRef.current is null');
      return false;
    }
    markerRef.current.showInfoWindow(id, alignType);
    return true;
  });
  const close = (0, _useStableCallback.useStableCallback)(() => {
    _NativeRNCNaverMapUtil.default.closeInfoWindow(id);
  });
  return {
    showOnMap,
    showOnMarker,
    close
  };
};
exports.useInfoWindow = useInfoWindow;
//# sourceMappingURL=useInfoWindow.js.map