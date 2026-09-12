"use strict";

import { useEffect, useRef } from 'react';
import { useStableCallback } from "../internal/util/useStableCallback.js";
import NaverMapUtil from "../spec/NativeRNCNaverMapUtil.js";
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
export const useInfoWindow = content => {
  const id = useRef(`info_window_${Date.now()}_${Math.random()}`).current;
  useEffect(() => {
    // Create InfoWindow instance on mount
    NaverMapUtil.createInfoWindow(id);
    NaverMapUtil.setInfoWindowContent(id, content.text);

    // Cleanup on unmount
    return () => {
      NaverMapUtil.destroyInfoWindow(id);
    };
  }, [id]);
  useEffect(() => {
    // Update content when it changes
    NaverMapUtil.setInfoWindowContent(id, content.text);
  }, [content.text, id]);
  useEffect(() => {
    NaverMapUtil.setInfoWindowOptions(id, content.anchor?.x ?? 0.5, content.anchor?.y ?? 1, content.offset?.x ?? 0, content.offset?.y ?? 0, content.alpha ?? 1);
  }, [content.anchor?.x, content.anchor?.y, content.offset?.x, content.offset?.y, content.alpha, id]);
  const showOnMap = useStableCallback(({
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
  const showOnMarker = useStableCallback(({
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
  const close = useStableCallback(() => {
    NaverMapUtil.closeInfoWindow(id);
  });
  return {
    showOnMap,
    showOnMarker,
    close
  };
};
//# sourceMappingURL=useInfoWindow.js.map