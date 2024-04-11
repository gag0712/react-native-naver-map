//
//  RNCNaverMapPath.h
//  mj-studio-react-native-naver-map
//
//  Created by mj on 4/6/24.
//

#import "FnUtil.h"
#import "MacroUtil.h"
#import "RCTConvert+NMFMapView.h"
#import "Utils.h"
#import <Foundation/Foundation.h>
#import <NMapsMap/NMFPath.h>
#import <NMapsMap/NMFPathColor.h>
#import <React/RCTImageLoader.h>
#import <React/RCTUtils.h>
#import <React/RCTView.h>
#import <UIKit/UIKit.h>

#ifdef RCT_NEW_ARCH_ENABLED
#import "RCTFabricComponentsPlugins.h"
#import <React/RCTViewComponentView.h>
#import <react/renderer/components/RNCNaverMapSpec/ComponentDescriptors.h>
#import <react/renderer/components/RNCNaverMapSpec/EventEmitters.h>
#import <react/renderer/components/RNCNaverMapSpec/Props.h>
#import <react/renderer/components/RNCNaverMapSpec/RCTComponentViewHelpers.h>
@interface RNCNaverMapPath : RCTViewComponentView
#else
@interface RNCNaverMapPath : RCTView
#endif

@property(nonatomic, strong) NMFPath* inner;

@property(nonatomic, assign) NSInteger zIndexValue;
@property(nonatomic, assign) BOOL isHidden;
@property(nonatomic, assign) double minZoom;
@property(nonatomic, assign) double maxZoom;
@property(nonatomic, assign) BOOL isMinZoomInclusive;
@property(nonatomic, assign) BOOL isMaxZoomInclusive;
@property(nonatomic, copy) RCTDirectEventBlock onTapOverlay;

@property(nonatomic, copy) NSArray* coords;
@property(nonatomic, assign) double width;
@property(nonatomic, assign) double outlineWidth;
@property(nonatomic, assign) NSInteger patternInterval;
@property(nonatomic, assign) double progress;
@property(nonatomic, assign) NSInteger color;
@property(nonatomic, assign) NSInteger passedColor;
@property(nonatomic, assign) NSInteger outlineColor;
@property(nonatomic, assign) NSInteger passedOutlineColor;
@property(nonatomic, assign) BOOL isHideCollidedSymbols;
@property(nonatomic, assign) BOOL isHideCollidedMarkers;
@property(nonatomic, assign) BOOL isHideCollidedCaptions;

@end
