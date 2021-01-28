#import "Foundation/Foundation.h"
#import "UIKit/UIKit.h"

@interface CDVWKInAppBrowserImageAnimator: NSObject

- (instancetype)initWithImageURL:(NSURL *)imageURL imageView:(UIImageView *)imageView;
- (CGImageAnimationStatus)start;
- (void)stop;

@end
