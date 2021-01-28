#import "CDVWKInAppBrowserImageAnimator.h"
#import "ImageIO/CGImageAnimation.h"

@interface CDVWKInAppBrowserImageAnimator()

@property (nonatomic) CFURLRef imageURL;
@property (nonatomic, strong) UIImageView *imageView;
@property (nonatomic) BOOL shouldStop;

@end

@implementation CDVWKInAppBrowserImageAnimator

- (instancetype)initWithImageURL:(NSURL *)imageURL imageView:(UIImageView *)imageView {
    if (self = [super init]) {
        _imageURL = (__bridge CFURLRef)imageURL;
        _imageView = imageView;
    }
    return self;
}

- (void)dealloc
{
    [self stop];
}

- (CGImageAnimationStatus)start {
    __weak CDVWKInAppBrowserImageAnimator *weakSelf = self;
    return CGAnimateImageAtURLWithBlock(self.imageURL, nil, ^(size_t index, CGImageRef image, bool* stop) {
        *stop = weakSelf.shouldStop;
        weakSelf.imageView.image = [[UIImage alloc] initWithCGImage:image];
    });
}

- (void)stop {
    self.shouldStop = YES;
}

@end
