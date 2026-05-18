package com.pop.components.theme

import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

/**
 * Generic PopGradient class to handle various gradient types and solid colors.
 * Designed to be reusable across the design system.
 */
sealed interface PopGradient {

    enum class RadiusBoundsMode {
        NearestEdge,
        FarthestEdge
    }

    data class Solid(
        val color: Color
    ) : PopGradient

    data class Linear(
        val colors: List<Color>,
        val stops: List<Float>? = null,
        val angleInDegrees: Float = 0f,
        val useAsCssAngle: Boolean = true, // Defaults to CSS behavior (0deg = up, 90deg = right)
        val tileMode: TileMode = TileMode.Clamp
    ) : PopGradient

    /**
     * Radial (circular) gradient that extends from a center point outward.
     * 
     * ## Center-to-Circumference Radial Gradient (Most Common):
     * 
     * To create a gradient that extends from the center to the circumference:
     * ```kotlin
     * PopGradient.Radial(
     *     colors = listOf(
     *         Color.Red,    // Color at center (0%)
     *         Color.Blue   // Color at circumference (100%)
     *     )
     * )
     * ```
     * 
     * **Output**: Red at the center, transitioning to Blue at the edges.
     * 
     * ## With Custom Color Stops:
     * ```kotlin
     * PopGradient.Radial(
     *     colors = listOf(
     *         Color.Red,      // Center
     *         Color.Yellow,   // Middle
     *         Color.Blue      // Edge
     *     ),
     *     stops = listOf(0.0f, 0.5f, 1.0f) // Red at 0%, Yellow at 50%, Blue at 100%
     * )
     * ```
     * 
     * ## Off-Center Radial Gradient:
     * ```kotlin
     * PopGradient.Radial(
     *     colors = listOf(Color.Red, Color.Blue),
     *     alignment = Alignment.TopStart // Light source from top-left
     * )
     * ```
     * 
     * ## Custom Center Position:
     * ```kotlin
     * PopGradient.Radial(
     *     colors = listOf(Color.Red, Color.Blue),
     *     center = Offset(100f, 100f) // Custom center at (100, 100) pixels
     * )
     * ```
     * 
     * ## Limited Radius (doesn't reach edges):
     * ```kotlin
     * PopGradient.Radial(
     *     colors = listOf(Color.Red, Color.Blue),
     *     radius = 50f // Gradient extends only 50 pixels from center
     * )
     * ```
     * 
     * ## Extended Radius (beyond view boundaries):
     * ```kotlin
     * PopGradient.Radial(
     *     colors = listOf(Color.Red, Color.Blue),
     *     radiusMultiplier = 1.5f // Gradient extends 150% beyond view size
     * )
     * ```
     * 
     * @param colors List of colors for the gradient. Must have at least 2 colors.
     *               - First color: Center color (at 0%)
     *               - Last color: Edge color (at 100%)
     *               - Colors are evenly distributed if [stops] is null
     * 
     * @param stops Optional list of stop positions (0.0 to 1.0) for each color.
     *              - 0.0f = center of the gradient
     *              - 1.0f = circumference/edge of the gradient
     *              - If null, colors are evenly spaced
     *              - Must match the size of [colors] if provided
     * 
     * @param center Custom center position in pixels. 
     *               - Default: [Offset.Unspecified] = center of the area
     *               - Use this for precise pixel-based positioning
     *               - Ignored if [alignment] is provided
     * 
     * @param alignment Center position based on alignment (e.g., [Alignment.TopStart]).
     *                  - Default: null = center of the area
     *                  - Use [Alignment.Center] for true center
     *                  - Use [Alignment.TopStart] for top-left (3D light source effect)
     *                  - Overrides [center] if both are provided
     * 
     * @param radius Distance from center to edge of gradient.
     *               - Default: [Float.POSITIVE_INFINITY] = extends to fill the entire area
     *               - Use infinity for center-to-circumference gradients
     *               - Use a specific value (e.g., 50f) to limit the gradient size
     * 
     * @param radiusMultiplier Multiplier for the calculated radius when [radius] is [Float.POSITIVE_INFINITY].
     *                         - Default: 1.0f = gradient fills exactly to view edges
     *                         - Use 1.5f to extend 50% beyond view boundaries
     *                         - Use 2.0f to extend 100% beyond view boundaries
     *                         - Only applies when [radius] is infinity (auto-calculated)
     * 
     * @param tileMode How to handle areas outside gradient bounds:
     *                 - [TileMode.Clamp]: Extend edge colors (default)
     *                 - [TileMode.Repeated]: Repeat the gradient pattern
     *                 - [TileMode.Mirror]: Mirror the gradient pattern
     * 
     * ## Usage with Modifier:
     * ```kotlin
     * Box(
     *     modifier = Modifier
     *         .size(100.dp)
     *         .popBackground(
     *             gradient = PopGradient.Radial(
     *                 colors = listOf(Color.Red, Color.Blue)
     *             )
     *         )
     * )
     * ```
     */
    data class Radial(
        val colors: List<Color>,
        val stops: List<Float>? = null,
        val center: Offset = Offset.Unspecified, // Unspecified means center of the area
        val alignment: Alignment? = null, // Overrides center with an alignment based offset (e.g. TopStart)
        val radius: Float = Float.POSITIVE_INFINITY, // Infinity means fill largest dimension
        val radiusMultiplier: Float = 1f, // Multiplier for auto-calculated radius (e.g., 1.5f = 150% of view size)
        val radiusMultiplierX: Float = 1f, // Optional X-axis multiplier for elliptical radial gradients
        val radiusMultiplierY: Float = 1f, // Optional Y-axis multiplier for elliptical radial gradients
        val constrainRadiusToBoundsX: Boolean = false, // Clamp X radius to component bounds from center
        val constrainRadiusBoundsModeX: RadiusBoundsMode = RadiusBoundsMode.NearestEdge, // How X clamp is computed
        val constrainRadiusToBoundsY: Boolean = false, // Clamp Y radius to component bounds from center
        val constrainRadiusBoundsModeY: RadiusBoundsMode = RadiusBoundsMode.NearestEdge, // How Y clamp is computed
        val tileMode: TileMode = TileMode.Clamp
    ) : PopGradient
}

/**
 * Predefined gradients commonly used in the application.
 */
enum class GradientPreset(val gradient: PopGradient) {
    // Avatar
    AvatarBrand(
        PopGradient.Radial(
            colors = listOf(
                PopColor.Brand.Brand500, // Highlight (Lighter)
                PopColor.Brand.Brand600, // Base
                PopColor.Brand.Brand800  // Shadow (Darker)
            ),
            stops = listOf(0.0f, 0.6f, 1.0f),
            alignment = Alignment.TopCenter // 3D Light source from Top Left
        )
    ),

    AvatarPerson1(
        PopGradient.Radial(
            colors = listOf(
                PopColor.AvatarPerson.PinkStart, // Highlight (Lighter)
                PopColor.AvatarPerson.PinkEnd // Base
            ),
            stops = listOf(0.5f, 1f),
            radiusMultiplier = 1.6f,
            alignment = Alignment.TopCenter // 3D Light source from Top Left
        )
    ),

    AvatarPerson2(
        PopGradient.Radial(
            colors = listOf(
                PopColor.AvatarPerson.SeaGreenStart, // Highlight (Lighter)
                PopColor.AvatarPerson.SeaGreenEnd // Base
            ),
            stops = listOf(0.5f, 1f),
            radiusMultiplier = 1.6f,
            alignment = Alignment.TopCenter // 3D Light source from Top Left
        )
    ),

    AvatarPerson3(
        PopGradient.Radial(
            colors = listOf(
                PopColor.AvatarPerson.PurpleStart, // Highlight (Lighter)
                PopColor.AvatarPerson.PurpleEnd // Base
            ),
            stops = listOf(0.5f, 1f),
            radiusMultiplier = 1.6f,
            alignment = Alignment.TopCenter // 3D Light source from Top Left
        )
    ),

    AvatarPerson4(
        PopGradient.Radial(
            colors = listOf(
                PopColor.AvatarPerson.MaroonStart, // Highlight (Lighter)
                PopColor.AvatarPerson.MaroonEnd // Base
            ),
            stops = listOf(0.5f, 1f),
            radiusMultiplier = 1.6f,
            alignment = Alignment.TopCenter // 3D Light source from Top Left
        )
    ),

    AvatarPerson5(
        PopGradient.Radial(
            colors = listOf(
                PopColor.AvatarPerson.VioletStart, // Highlight (Lighter)
                PopColor.AvatarPerson.VioletEnd // Base
            ),
            stops = listOf(0.5f, 1f),
            radiusMultiplier = 1.6f,
            alignment = Alignment.TopCenter // 3D Light source from Top Left
        )
    ),

    AvatarPerson6(
        PopGradient.Radial(
            colors = listOf(
                PopColor.AvatarPerson.OrangeStart, // Highlight (Lighter)
                PopColor.AvatarPerson.OrangeEnd // Base
            ),
            stops = listOf(0.5f, 1f),
            radiusMultiplier = 1.6f,
            alignment = Alignment.TopCenter // 3D Light source from Top Left
        )
    ),

    AvatarPerson7(
        PopGradient.Radial(
            colors = listOf(
                PopColor.AvatarPerson.GreenStart, // Highlight (Lighter)
                PopColor.AvatarPerson.GreenEnd // Base
            ),
            stops = listOf(0.5f, 1f),
            radiusMultiplier = 1.6f,
            alignment = Alignment.TopCenter // 3D Light source from Top Left
        )
    ),

    AvatarPerson8(
        PopGradient.Radial(
            colors = listOf(
                PopColor.AvatarPerson.RedStart, // Highlight (Lighter)
                PopColor.AvatarPerson.RedEnd // Base
            ),
            stops = listOf(0.5f, 1f),
            radiusMultiplier = 1.6f,
            alignment = Alignment.TopCenter // 3D Light source from Top Left
        )
    ),

    // Surface
    SurfacePrimary(
        PopGradient.Linear(
            colors = listOf(SurfaceColor.Gradient.Primary.Start, SurfaceColor.Gradient.Primary.End),
            angleInDegrees = 180f // Top to Bottom
        )
    ),
    
    // Overflow
    OverflowStart(
         PopGradient.Linear(
            colors = listOf(OverflowColor.Gradient.Start, OverflowColor.Gradient.End),
            angleInDegrees = 90f // Left to Right
        )
    ),
    
    OverflowEnd(
         PopGradient.Linear(
            colors = listOf(OverflowColor.Gradient.End, OverflowColor.Gradient.Start),
            angleInDegrees = 90f // Left to Right
        )
    ),

    // Button Gradients - Large size
    // Brand Primary Button Gradient
    ButtonBrandPrimaryLarge(
        PopGradient.Radial(
            colors = listOf(
                PopColor.BrandPrimaryButton.Color1,
                PopColor.BrandPrimaryButton.Color2,
                PopColor.BrandPrimaryButton.Color3,
                PopColor.BrandPrimaryButton.Color4
            ),
            stops = listOf(0f, 0.3f, 0.82f, 1f),
            radiusMultiplier = 1.05f,
            alignment = Alignment.TopCenter
        )
    ),

    // Button Gradients - Large size
    // Brand Primary Button Gradient
    ButtonBrandPrimaryLargeHorizontal(
        PopGradient.Radial(
            colors = listOf(
                PopColor.BrandPrimaryButton.Color1,
                PopColor.BrandPrimaryButton.Color2,
                PopColor.BrandPrimaryButton.Color3,
                PopColor.BrandPrimaryButton.Color4
            ),
            stops = listOf(0f, 0.3f, 0.82f, 1f),
            alignment = Alignment.TopCenter,
            radiusMultiplier = 1.05f,
            constrainRadiusToBoundsY = true,
            constrainRadiusBoundsModeY = PopGradient.RadiusBoundsMode.FarthestEdge
        )
    ),

    // Primary Invert Button Gradient (White button)
    ButtonPrimaryInvertLarge(
        PopGradient.Linear(
            colors = listOf(
                SurfaceColor.PrimaryInvert,  // White center
                SurfaceColor.Gradient.Primary.End  // Grey edge
            ),
            stops = listOf(0.0f, 1.0f),
            angleInDegrees = 180f
        )
    ),

    // Secondary Button Gradient
    ButtonSecondaryLarge(
        PopGradient.Radial(
            colors = listOf(
                SurfaceColor.Gradient.SecondaryButton.Start,  // Base
                SurfaceColor.Gradient.SecondaryButton.End  // Darker edge
            ),
            stops = listOf(0.13f, 0.9f),
            radiusMultiplier = 1.3f,
            alignment = Alignment.TopCenter
        )
    ),

    // Secondary Button Gradient
    ButtonSecondaryLargeHorizontal(
        PopGradient.Radial(
            colors = listOf(
                SurfaceColor.Gradient.SecondaryButton.Start,  // Base
                SurfaceColor.Gradient.SecondaryButton.End  // Darker edge
            ),
            stops = listOf(0.13f, 0.9f),
            alignment = Alignment.TopCenter,
            radiusMultiplier = 1.3f,
            constrainRadiusToBoundsY = true,
            constrainRadiusBoundsModeY = PopGradient.RadiusBoundsMode.FarthestEdge
        )
    ),

    // Success Button Gradient
    // Surface-Success-Gradient from Figma
    // radial-gradient(74.79% 159.82% at 50.14% -28.57%, #26D526 17.49%, #059505 51.04%, #015001 100%)
    // Note: The center position (50.14%, -28.57%) will be calculated dynamically in the brush
    // The elliptical size (74.79% x 159.82%) is approximated using the radius
    ButtonSuccessLarge(
        PopGradient.Linear(
            colors = listOf(
                Color(0xFF26D526),  // Bright green at 17.49%
                Color(0xFF059505),  // Medium green at 51.04%
                Color(0xFF015001)   // Dark green at 100%
            ),
            stops = listOf(0.174f, 0.51f, 1.0f),
            angleInDegrees = 180f
        )
    ),

    // Destructive/Error Button Gradient
    // Surface-Destructive-Gradient/Large from Figma
    // radial-gradient(74.79% 159.82% at 50.14% -28.57%, #F27373 17.49%, #E22626 51.04%, #8A0F0F 100%)
    // Note: The center position (50.14%, -28.57%) will be calculated dynamically in the brush
    // The elliptical size (74.79% x 159.82%) is approximated using the radius
    ButtonDestructiveLarge(
        PopGradient.Linear(
            colors = listOf(
                Color(0xFFF27373),  // Light red at 17.49%
                Color(0xFFE22626),  // Medium red at 51.04%
                Color(0xFF8A0F0F)   // Dark red at 100%
            ),
            stops = listOf(0.174f, 0.51f, 1.0f),
            // Center will be calculated in toBrush() based on size
            angleInDegrees = 180f
        )
    ),

    // White Button Gradient (for BrandPrimary Loading state)
    ButtonWhiteLarge(
        PopGradient.Radial(
            colors = listOf(
                PopColor.WhiteBlack.White,  // White center
                PopColor.Grey.Grey200       // Light grey edge
            ),
            stops = listOf(0.0f, 1.0f),
            alignment = Alignment.TopStart
        )
    ),

    // Primary Disabled Gradient
    // Combines: linear-gradient(0deg, rgba(13, 13, 13, 0.90) 0%, rgba(13, 13, 13, 0.90) 100%), 
    //           linear-gradient(180deg, #FFF 0%, #999 100%)
    // This is a white-to-grey gradient with a 90% dark overlay
    // Blend formula: result = (1 - alpha) * foreground + alpha * background
    // Top: 0.1 * #FFFFFF + 0.9 * #0D0D0D = (25.5,25.5,25.5) + (11.7,11.7,11.7) ≈ #252525
    // Bottom: 0.1 * #999999 + 0.9 * #0D0D0D = (15.3,15.3,15.3) + (11.7,11.7,11.7) ≈ #1B1B1B
    ButtonPrimaryDisabled(
        PopGradient.Linear(
            colors = listOf(
                Color(0xFF252525),  // Top: White blended with 90% dark overlay
                Color(0xFF1B1B1B)   // Bottom: Grey blended with 90% dark overlay
            ),
            angleInDegrees = 180f // Top to bottom (180deg)
        )
    ),

    // Brand Primary Disabled Gradient (Figma: Brand Primary / Disabled)
    // Brand gradient with 90% dark overlay for dark desaturated reddish-brown
    // Blend: 0.1 * BrandPrimaryButton colors + 0.9 * #0D0D0D
    ButtonBrandPrimaryDisabled(
        PopGradient.Linear(
            colors = listOf(
                Color(0xFF251912),  // Top: Lighter dark red-brown
                Color(0xFF1E100C)   // Bottom: Darker dark red-brown
            ),
            angleInDegrees = 180f // Top to bottom (180deg)
        )
    ),

    // Card Background Gradient
    // radial-gradient(104.17% 104.17% at 30.35% 21.05%, #1D1D1D 0%, #121212 100%)
    // Note: The center position (30.35%, 21.05%) will be calculated dynamically in the brush
    // The radius (104.17%) extends slightly beyond the bounds
    CardBackground(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFF1D1D1D),  // Start color at 0%
                Color(0xFF121212)    // End color at 100%
            ),
            stops = listOf(0f, 1f),
            // Center will be calculated in toBrush() based on size
            // Position: 30.35% horizontal, 21.05% vertical
            center = Offset(Float.NaN, Float.NaN), // Marker for custom percentage-based center
            alignment = null, // Custom position, not using standard alignment
            radius = Float.POSITIVE_INFINITY // Will be calculated as 104.17% in toBrush()
        )
    ),

    // Badge Gradients - per Figma node 5529-3928
    // Badge Brand Primary (Orange)
    // Surface-Brand Primary-Gradient/Large
    BadgeBrandPrimary(
        PopGradient.Radial(
            colors = listOf(
                PopColor.Brand.Brand500,  // Lighter center (highlight)
                PopColor.Brand.Brand600,  // Base color
                PopColor.Brand.Brand700   // Darker edge (shadow)
            ),
            stops = listOf(0.0f, 0.6f, 1.0f),
            alignment = Alignment.TopStart
        )
    ),

    BadgePopCoins(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFF942A04),
                Color(0xFF390F00)
            ),
            stops = listOf(0.15f, 0.45f),
            radiusMultiplier = 2.5f,
            center = Offset(0f, 0f)
        )
    ),

    // Badge Success (Green)
    // Surface-Success-Gradient/Large
    BadgeSuccess(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFF26D526),  // Bright green
                Color(0xFF059505),  // Medium green
                Color(0xFF015001)   // Dark green
            ),
            stops = listOf(0.1749f, 0.5104f, 1.0f),
            alignment = Alignment.TopStart
        )
    ),

    // Badge Destructive (Red)
    // Surface-Destructive-Gradient/Large
    BadgeDestructive(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFFF27373),  // Light red
                Color(0xFFE22626),  // Medium red
                Color(0xFF8A0F0F)   // Dark red
            ),
            stops = listOf(0.1749f, 0.5104f, 1.0f),
            alignment = Alignment.TopStart
        )
    ),

    // Badge White Primary
    // Surface-Primary Invert-Gradient/Large: linear-gradient from #FFFFFF to #999999
    BadgeWhitePrimary(
        PopGradient.Linear(
            colors = listOf(
                Color(0xFFFFFFFF),  // White
                Color(0xFF999999)   // Grey
            ),
            angleInDegrees = 180f // Top to bottom
        )
    ),

    // Offer Gradients - Horizontal gradients with opacity variations
    // Offer (Blue) gradient - from #0043B6 to transparent via #0461FF
    PopOfferGradient(
        PopGradient.Linear(
            colors = listOf(
                Color(0x000461FF),
                Color(0xFF0043B6),  // #0043B6 with 30% opacity
                Color(0xFF0461FF),  // #0461FF with 30% opacity (via at 46.154%)
                Color(0xFF0043B6),  // #0043B6 with 30% opacity
                Color(0x000461FF)   // Transparent
            ),
            stops = listOf(0.0f, 0.1f, 0.5f, 0.9f, 1.0f),
            angleInDegrees = 90f // Left to right
        )
    ),

    OfferGradient(
        PopGradient.Linear(
            colors = listOf(
                Color(0xFF0043B6),  // #0043B6 with 30% opacity
                Color(0xFF0461FF),  // #0461FF with 30% opacity (via at 46.154%)
                Color(0x000461FF)   // Transparent
            ),
            stops = listOf(0.0f, 0.46154f, 1.0f),
            angleInDegrees = 90f // Left to right
        )
    ),

    // Cash (Green) gradient - from #25620C to transparent via #235D0A
    CashGradient(
        PopGradient.Linear(
            colors = listOf(
                Color(0x4D25620C),  // #25620C with 30% opacity
                Color(0x4D235D0A),  // #235D0A with 30% opacity (via at 46.154%)
                Color(0x00235D0A)   // Transparent
            ),
            stops = listOf(0.0f, 0.46154f, 1.0f),
            angleInDegrees = 90f // Left to right
        )
    ),

    // Pay in 3 (Purple) gradient - from #8000EA to transparent via #5900A2
    PayIn3Gradient(
        PopGradient.Linear(
            colors = listOf(
                Color(0x4D8000EA),  // #8000EA with 30% opacity
                Color(0x4D5900A2),  // #5900A2 with 30% opacity (via at 46.154%)
                Color(0x005900A2)   // Transparent
            ),
            stops = listOf(0.0f, 0.46154f, 1.0f),
            angleInDegrees = 90f // Left to right
        )
    ),

    // Pop Coin (Orange) gradient - from #A93205 to transparent via #DD4F00
    PopCoinGradient(
        PopGradient.Linear(
            colors = listOf(
                Color(0x4DA93205),  // #A93205 with 30% opacity
                Color(0x4DDD4F00),  // #DD4F00 with 30% opacity (via at 46.154%)
                Color(0x00DD4F00)   // Transparent
            ),
            stops = listOf(0.0f, 0.46154f, 1.0f),
            angleInDegrees = 90f // Left to right
        )
    ),

    // Offer Gradients - Disabled state (10% opacity)
    OfferGradientDisabled(
        PopGradient.Linear(
            colors = listOf(
                Color(0x1A0043B6),  // #0043B6 with 10% opacity
                Color(0x1A0461FF),  // #0461FF with 10% opacity
                Color(0x000461FF)   // Transparent
            ),
            stops = listOf(0.0f, 0.46154f, 1.0f),
            angleInDegrees = 90f
        )
    ),

    CashGradientDisabled(
        PopGradient.Linear(
            colors = listOf(
                Color(0x1A25620C),  // #25620C with 10% opacity
                Color(0x1A235D0A),  // #235D0A with 10% opacity
                Color(0x00235D0A)   // Transparent
            ),
            stops = listOf(0.0f, 0.46154f, 1.0f),
            angleInDegrees = 90f
        )
    ),

    PayIn3GradientDisabled(
        PopGradient.Linear(
            colors = listOf(
                Color(0x1A8000EA),  // #8000EA with 10% opacity
                Color(0x1A5900A2),  // #5900A2 with 10% opacity
                Color(0x005900A2)   // Transparent
            ),
            stops = listOf(0.0f, 0.46154f, 1.0f),
            angleInDegrees = 90f
        )
    ),

    PopCoinGradientDisabled(
        PopGradient.Linear(
            colors = listOf(
                Color(0x1AA93205),  // #A93205 with 10% opacity
                Color(0x1ADD4F00),  // #DD4F00 with 10% opacity
                Color(0x00DD4F00)   // Transparent
            ),
            stops = listOf(0.0f, 0.46154f, 1.0f),
            angleInDegrees = 90f
        )
    ),
    SurfacePrimaryLarge(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFF1D1D1D),
                Color(0xFF121212),
            ),
        )
    ),

    GradientGreen(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFF20C920),
                Color(0xFF059505),
                Color(0xFF036E03)
            ),
            radiusMultiplier = 1.4f,
            constrainRadiusToBoundsY = true,
            constrainRadiusBoundsModeY = PopGradient.RadiusBoundsMode.FarthestEdge,
            stops = listOf(0.17f,0.51f,1f),
            alignment = Alignment.TopCenter
        )
    ),

    GradientRed(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFFF17272),
                Color(0xFFE22626),
                Color(0xFFAC1818)
            ),
            radiusMultiplier = 1.4f,
            constrainRadiusToBoundsY = true,
            constrainRadiusBoundsModeY = PopGradient.RadiusBoundsMode.FarthestEdge,
            stops = listOf(0f,0.51f,1f),
            alignment = Alignment.TopCenter
        )
    ),

    GradientOrange(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFFFF813B),
                Color(0xFFFF5200),
                Color(0xFFCD3401),
                Color(0xFFB32A01)
            ),
            stops = listOf(0f,0.30f, 0.82f, 1f),
            alignment = Alignment.TopCenter
        )
    ),

    GradientOrangeLoaderView(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFFFCC7B2),
                Color(0xFFF7AD8F),
                Color(0xFFFF672B),
                Color(0xFFD14009),
                Color(0xFFB13407)
            ),
            stops = listOf(0f,0.20f, 0.46f, 0.73f, 1f),
        )
    ),

    GradientBlack(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFF303030),
                Color(0xFF0D0D0D)
            ),
            stops = listOf(0f, 1f),
            alignment = Alignment.TopCenter
        )
    ),

    GradientBlackLinear(
        PopGradient.Linear(
            colors = listOf(
                Color(0xFF303030),
                Color(0xFF0D0D0D)
            ),
            stops = listOf(0f, 1f),
            angleInDegrees = 180f
        )
    ),

    GradientWhiteGray(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFFFFFFFF),  // #FFFFFF - white at 0%
                Color(0xFFDFDFDF),  // #DFDFDF - light gray at 30%
                Color(0xFFB7B7B7),  // #B7B7B7 - medium-light gray at 53%
                Color(0xFF999999),  // #999999 - medium-dark gray at 73%
                Color(0xFF7B7B7B)   // #7B7B7B - dark gray at 100%
            ),
            stops = listOf(0f, 0.30f, 0.53f, 0.73f, 1f),
            radiusMultiplier = 1.4f,
            constrainRadiusToBoundsY = true,
            constrainRadiusBoundsModeY = PopGradient.RadiusBoundsMode.FarthestEdge,
            alignment = Alignment.TopCenter // Radial center at top-left creates angled sheen effect
        )
    ),

    GradientBlue(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFF469DFF),
                Color(0xFF1B70FF),
                Color(0xFF005EFF),
                Color(0xFF004ED2),
                Color(0xFF003FAA)
            ),
            stops = listOf(0f, 0.34f, 0.50f, 0.75f, 1f),
            alignment = Alignment.TopStart
        )
    ),

    SurfaceSecondaryGradientLarge(
        PopGradient.Radial(
            colors = listOf(
                Color(0xFF464646),
                Color(0xFF1F1F1F)
            ),
            stops = listOf(0f, 1f),
            alignment = Alignment.TopCenter
        )
    )

    // Add more presets here as they are defined in the Design System
    ;

    companion object {
        /**
         * List of avatar person gradients for cycling through different background colors.
         */
        val avatarPersonGradients = listOf(
            AvatarPerson1,
            AvatarPerson2,
            AvatarPerson3,
            AvatarPerson4,
            AvatarPerson5,
            AvatarPerson6,
            AvatarPerson7,
            AvatarPerson8
        )

        /**
         * Get an avatar gradient based on a name or identifier.
         * Uses the hash of the string to deterministically select a gradient,
         * so the same name always gets the same color.
         */
        fun getAvatarGradientForName(name: String): PopGradient {
            val index = kotlin.math.abs(name.hashCode()) % avatarPersonGradients.size
            return avatarPersonGradients[index].gradient
        }

        /**
         * Get an avatar gradient by index (cycles through available gradients).
         */
        fun getAvatarGradientByIndex(index: Int): PopGradient {
            return avatarPersonGradients[index % avatarPersonGradients.size].gradient
        }
    }
}
