import type { RefObject } from 'react';
import type { NaverMapMarkerOverlayRef } from '../component/NaverMapMarkerOverlay';
import type { NaverMapViewRef } from '../component/NaverMapView';
import type { Align } from '../types/Align';
import type { Coord } from '../types/Coord';
import type { Point } from '../types/Point';
export type InfoWindowContent = {
    text: string;
    anchor?: Point;
    offset?: Point;
    alpha?: number;
};
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
export declare const useInfoWindow: (content: InfoWindowContent) => {
    showOnMap: ({ mapRef, position, }: {
        mapRef: RefObject<NaverMapViewRef | null>;
        position: Coord;
    }) => boolean;
    showOnMarker: ({ markerRef, alignType, }: {
        markerRef: RefObject<NaverMapMarkerOverlayRef | null>;
        alignType?: Align;
    }) => boolean;
    close: () => void;
};
//# sourceMappingURL=useInfoWindow.d.ts.map