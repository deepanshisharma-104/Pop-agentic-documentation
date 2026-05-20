package com.pop.components.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.pop.components.theme.IconName
import com.pop.components.theme.IconStyle
import com.pop.components.theme.PopIconResources
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler

/**
 * Represents a visual asset that can be displayed in UI components.
 * Contains visual elements like images, Rive animations, color components, etc.
 * 
 * This model provides a unified way to handle different types of visual content
 * that can be used across various components like PopTopBar, backgrounds, etc.
 * 
 */
@Immutable
sealed interface VisualElement : Parcelable {

    /**
     * Used for unrecognized or invalid visual elements
     */
    @Parcelize
    @Immutable
    data object UnRecognised : VisualElement

    /**
     * Represents an image. Contains either the image data in base64, a URL, or a drawable resource.
     */
    @Parcelize
    @Immutable
    data class Image(
        val source: Source,
        val properties: VisualElementProperties = VisualElementProperties(),
        val imageRenderingType: ImageRenderingType? = null,
        val isHardwareAccelerated: Boolean? = true
    ) : VisualElement {
        /**
         * Abstraction for sources that drive content of [Image].
         */
        @Immutable
        sealed interface Source : Parcelable {
            /**
             * Base64 data of the image
             */
            @Parcelize
            @Immutable
            data class DataBase64String(val data: String) : Source

            /**
             * URL to download the image
             */
            @Parcelize
            @Immutable
            data class DataUrl(val url: String) : Source

            /**
             * Used for unrecognized source
             */
            @Parcelize
            @Immutable
            data object UnRecognised : Source

            /**
             * Used for creating Visual Elements from local drawable resources.
             * Mainly used on client side only.
             */
            @Parcelize
            @Immutable
            data class IconRes(@DrawableRes val resId: Int) : Source
        }

        /**
         * Defines properties for different types of images e.g. [RegularImage], [TintedImage] etc
         */
        @Immutable
        sealed interface ImageRenderingType : Parcelable {
            /**
             * RegularImage defines a normal image, with no extra attributes required for separate
             * rendering of background, tint
             */
            @Parcelize
            @Immutable
            data object RegularImage : ImageRenderingType

            /**
             * TintedImage defines properties of an image to render its background, tint etc.
             */
            @Parcelize
            @Immutable
            @TypeParceler<Color?, ColorParceler>
            data class TintedImage(
                val tintColor: Color?,
                val bgColor: Color?
            ) : ImageRenderingType
        }
    }

    /**
     * Represents a block solid color.
     */
    @Parcelize
    @Immutable
    data class ColorComponent(
        // Color value (in hex code)
        val color: String,
        // Optional visual properties like width, height, etc.
        val properties: VisualElementProperties? = null
    ) : VisualElement

    /**
     * Visual asset for Rive animations.
     * Client should use Rive library to render this component.
     * Contains the source and properties for the Rive animation file.
     */
    @Parcelize
    @Immutable
    data class Rive(
        val source: Source,
        // Optional properties object for rendering this Rive widget.
        // If not provided, defaults to dimensions/properties as per design system
        val properties: VisualElementProperties? = null,
        // Whether the animation should start playing automatically
        val autoplay: Boolean = false,
        // The name of the artboard to display. If null, the default artboard will be used
        val artboardName: String? = null,
        // The name of the animation to play. If null, the default animation will be played
        val animationName: String? = null,
        // The name of the state machine to use. If null, no state machine will be used
        val stateMachineName: String? = null
    ) : VisualElement {
        /**
         * Abstraction for sources that drive content of [Rive].
         */
        @Immutable
        sealed interface Source : Parcelable {
            /**
             * Raw resource ID of the Rive animation file.
             */
            @Parcelize
            @Immutable
            data class ResourceId(@RawRes val resId: Int) : Source

            /**
             * Used for unrecognized source
             */
            @Parcelize
            @Immutable
            data object UnRecognised : Source
        }
    }

    companion object {
        /**
         * Function to create an instance of [VisualElement] from a [resId] local drawable resource
         */
        fun buildFrom(
            @DrawableRes resId: Int,
            heightDp: Int = 0,
            widthDp: Int = 0,
            imageRenderingType: Image.ImageRenderingType? = null
        ): VisualElement = Image(
            Image.Source.IconRes(resId),
            VisualElementProperties(heightDp, widthDp),
            imageRenderingType
        )

        /**
         * Function to create an instance of [VisualElement] from a [url] image resource
         */
        fun buildFrom(
            url: String,
            heightDp: Int = 0,
            widthDp: Int = 0,
            imageRenderingType: Image.ImageRenderingType? = null
        ): VisualElement = Image(
            Image.Source.DataUrl(url),
            VisualElementProperties(heightDp, widthDp),
            imageRenderingType
        )

        /**
         * Function to create an instance of [VisualElement] from a type-safe [iconName] with [style].
         * This method handles the resource lookup internally using [PopIconResources].
         * 
         * @param iconName The type-safe icon name (e.g., Icons.Check, Icons.Cross)
         * @param style The icon style (Outline or Filled), defaults to [IconStyle.Outline]
         * @param fallbackResId Optional fallback drawable resource ID if the icon resource is not found
         * @param tintColor Optional tint color to apply to the icon. If provided, creates a [TintedImage] rendering type.
         * @param bgColor Optional background color for the icon. Only used if [tintColor] is provided.
         * @param heightDp Height in dp
         * @param widthDp Width in dp
         * @param imageRenderingType Optional image rendering type. If [tintColor] is provided, this parameter is ignored.
         */
        fun buildFrom(
            iconName: IconName,
            style: IconStyle = IconStyle.Outline,
            @DrawableRes fallbackResId: Int? = null,
            tintColor: Color? = null,
            bgColor: Color? = null,
            heightDp: Int = 0,
            widthDp: Int = 0,
            imageRenderingType: Image.ImageRenderingType? = null
        ): VisualElement {
            val resId = PopIconResources.getIconResourceId(iconName, style) ?: fallbackResId ?: 0
            val renderingType = if (tintColor != null) {
                Image.ImageRenderingType.TintedImage(tintColor = tintColor, bgColor = bgColor)
            } else {
                imageRenderingType
            }
            return buildFrom(resId, heightDp, widthDp, renderingType)
        }

        /**
         * Function to create a ColorComponent [VisualElement] from [color] hex string
         */
        fun buildFromColor(
            color: String,
            heightDp: Int = 0,
            widthDp: Int = 0
        ): VisualElement = ColorComponent(
            color = color,
            properties = VisualElementProperties(heightInDp = heightDp, widthInDp = widthDp)
        )

        /**
         * Function to create a Rive [VisualElement] from [resId] raw resource
         */
        fun buildFromRive(
            @RawRes resId: Int,
            heightDp: Int = 0,
            widthDp: Int = 0,
            autoplay: Boolean = false,
            artboardName: String? = null,
            animationName: String? = null,
            stateMachineName: String? = null
        ): VisualElement = Rive(
            source = Rive.Source.ResourceId(resId),
            properties = VisualElementProperties(heightInDp = heightDp, widthInDp = widthDp),
            autoplay = autoplay,
            artboardName = artboardName,
            animationName = animationName,
            stateMachineName = stateMachineName
        )

        /**
         * Creates a default unrecognized instance
         */
        fun newDefaultInstance() = UnRecognised
    }
}

object ColorParceler : Parceler<Color?> {
    override fun create(parcel: Parcel): Color? {
        val hasColor = parcel.readInt() != 0
        return if (hasColor) Color(parcel.readLong().toULong()) else null
    }
    override fun Color?.write(parcel: Parcel, flags: Int) {
        if (this == null) {
            parcel.writeInt(0)
        } else {
            parcel.writeInt(1)
            parcel.writeLong(this.value.toLong())
        }
    }
}

/**
 * Holds common properties for [VisualElement] like width, height, corner radius, padding, etc.
 */
@Parcelize
@Immutable
data class VisualElementProperties(
    val heightInDp: Int = 0,
    val widthInDp: Int = 0,
    val paddingProperties: PaddingProperties = PaddingProperties(),
    val aspectRatio: AspectRatio? = null,
    val cornerRadius: Int = 0,
) : Parcelable

/**
 * Padding properties for visual elements
 */
@Parcelize
@Immutable
data class PaddingProperties(
    val start: Int = 0,
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0,
) : Parcelable

/**
 * AspectRatio dictates client Image widgets to allow sizing based on the provided aspect ratio.
 * Android client uses the Jetpack Compose modifier:
 * https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier#(androidx.compose.ui.Modifier).aspectRatio(kotlin.Float,kotlin.Boolean)
 */
@Parcelize
data class AspectRatio(
    val aspectRatio: Float,
    // Android specific field. Jetpack Compose Modifier changes the aspect ratio
    val androidMatchHeightConstraintFirst: Boolean = false,
) : Parcelable

/**
 * Extension function to check if VisualElementProperties has valid values
 */
fun VisualElementProperties?.isValid(): Boolean =
    (this?.heightInDp ?: 0) >= 0 && (this?.widthInDp ?: 0) >= 0 && (this?.cornerRadius ?: 0) >= 0

/**
 * Extension function to get URL from VisualElement if it's an image with URL source
 */
fun VisualElement.getUrl(): String? = when (this) {
    is VisualElement.Image -> (source as? VisualElement.Image.Source.DataUrl)?.url
    is VisualElement.ColorComponent -> null
    is VisualElement.Rive -> null
    VisualElement.UnRecognised -> null
}

/**
 * Extension function to determine if this [VisualElement] has any valid content or not
 */
fun VisualElement?.isValid(): Boolean = when (this) {
    is VisualElement.Image -> when (source) {
        is VisualElement.Image.Source.DataBase64String -> source.data.isNotBlank()
        is VisualElement.Image.Source.DataUrl -> source.url.isNotBlank()
        is VisualElement.Image.Source.IconRes -> source.resId != 0
        VisualElement.Image.Source.UnRecognised -> false
    }
    is VisualElement.ColorComponent -> color.isNotBlank()
    is VisualElement.Rive -> when (source) {
        is VisualElement.Rive.Source.ResourceId -> source.resId != 0
        VisualElement.Rive.Source.UnRecognised -> false
    }
    VisualElement.UnRecognised -> false
    null -> false
}

/**
 * Extension function to convert String URL to VisualElement.Image
 */
fun String.toVisualElementImageUrl(): VisualElement = VisualElement.Image(
    source = VisualElement.Image.Source.DataUrl(this),
    properties = VisualElementProperties(0, 0),
    imageRenderingType = null
)


