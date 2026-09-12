import { type HostComponent, type ViewProps } from 'react-native';
import type { DirectEventHandler, Double, Int32, WithDefault } from 'react-native/Libraries/Types/CodegenTypes';
interface BaseOverlay {
    zIndexValue: Int32;
    globalZIndexValue: Int32;
    isHidden?: WithDefault<boolean, false>;
    minZoom: Double;
    maxZoom: Double;
    isMinZoomInclusive?: WithDefault<boolean, true>;
    isMaxZoomInclusive?: WithDefault<boolean, true>;
}
export type NativeCaptionProp = {
    key: string;
    text: string;
    requestedWidth?: Double;
    align?: Int32;
    offset?: Double;
    color?: Int32;
    haloColor?: Int32;
    textSize?: Double;
    minZoom?: Double;
    maxZoom?: Double;
};
export type NativeSubCaptionProp = {
    key: string;
    text: string;
    color?: Int32;
    haloColor?: Int32;
    textSize?: Double;
    requestedWidth?: Double;
    minZoom?: Double;
    maxZoom?: Double;
};
export type NativeImageProp = Readonly<{
    symbol?: string;
    rnAssetUri?: string;
    httpUri?: string;
    assetName?: string;
    reuseIdentifier?: string;
}>;
interface Props extends BaseOverlay, ViewProps {
    coord: Readonly<{
        latitude: Double;
        longitude: Double;
    }>;
    onTapOverlay?: DirectEventHandler<Readonly<{}>>;
    width?: Double;
    height?: Double;
    anchor?: Readonly<{
        x: Double;
        y: Double;
    }>;
    angle?: Double;
    isFlatEnabled?: WithDefault<boolean, false>;
    isIconPerspectiveEnabled?: WithDefault<boolean, false>;
    alpha?: Double;
    isHideCollidedSymbols?: WithDefault<boolean, false>;
    isHideCollidedMarkers?: WithDefault<boolean, false>;
    isHideCollidedCaptions?: WithDefault<boolean, false>;
    isForceShowIcon?: WithDefault<boolean, false>;
    tintColor?: Int32;
    image?: Readonly<NativeImageProp>;
    caption?: Readonly<NativeCaptionProp>;
    subCaption?: Readonly<NativeSubCaptionProp>;
}
type ComponentType = HostComponent<Props>;
interface NativeCommands {
    showInfoWindow: (ref: React.ElementRef<ComponentType>, infoWindowId: string, alignType: Int32) => void;
}
declare const _default: HostComponent<Props>;
export default _default;
export declare const Commands: NativeCommands;
//# sourceMappingURL=RNCNaverMapMarkerNativeComponent.d.ts.map