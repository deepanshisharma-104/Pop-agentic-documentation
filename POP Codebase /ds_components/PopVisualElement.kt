package com.pop.components.ds_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import app.rive.runtime.kotlin.core.Alignment
import app.rive.runtime.kotlin.core.Fit
import app.rive.runtime.kotlin.core.Loop
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.pop.components.models.VisualElement
import com.pop.components.models.VisualElementProperties
import com.pop.components.models.isValid

/**
 * Composable to render a VisualElement (Image, Rive, ColorComponent)
 * 
 * This composable handles different types of visual elements and renders them appropriately.
 * 
 * @param modifier Modifier for the composable
 * @param visualElement The visual element to render
 * @param contentScale Content scale for images (default: ContentScale.Fit)
 * @param contentDescription Content description for accessibility
 */
@Composable
fun PopVisualElement(
    modifier: Modifier = Modifier,
    visualElement: VisualElement?,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null
) {
    if (visualElement == null || !visualElement.isValid()) {
        return
    }

    when (visualElement) {
        is VisualElement.Image -> {
            PopVisualElementImage(
                modifier = modifier,
                imageElement = visualElement,
                contentScale = contentScale,
                contentDescription = contentDescription
            )
        }
        is VisualElement.Rive -> {
            PopVisualElementRive(
                modifier = modifier,
                riveElement = visualElement,
                contentDescription = contentDescription
            )
        }
        is VisualElement.ColorComponent -> {
            PopVisualElementColor(
                modifier = modifier,
                colorElement = visualElement
            )
        }
        is VisualElement.UnRecognised -> {
            // Do nothing
        }
    }
}

/**
 * Renders an Image VisualElement
 */
@Composable
private fun PopVisualElementImage(
    modifier: Modifier,
    imageElement: VisualElement.Image,
    contentScale: ContentScale,
    contentDescription: String?
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    
    // Apply properties
    val propertiesModifier = modifier.applyVisualElementProperties(
        properties = imageElement.properties,
        density = density
    )
    
    // Apply tint if needed
    val colorFilter = when (val renderingType = imageElement.imageRenderingType) {
        is VisualElement.Image.ImageRenderingType.TintedImage ->
            renderingType.tintColor?.let { ColorFilter.tint(it) }
        else -> null
    }
    
    // Apply background color if needed
    val backgroundModifier = when (val renderingType = imageElement.imageRenderingType) {
        is VisualElement.Image.ImageRenderingType.TintedImage ->
            renderingType.bgColor?.let { Modifier.background(it) } ?: Modifier
        else -> Modifier
    }

    when (val source = imageElement.source) {
        is VisualElement.Image.Source.DataUrl -> {
            val isContentUri = source.url.startsWith("content://")
            Log.d("PopVisualElement", "Loading image from URL: ${source.url}, isContentUri: $isContentUri")
            
            if (isContentUri) {
                // For content:// URIs (like contact photos), load directly using ContentResolver
                var imageBitmap by remember(source.url) { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }
                
                LaunchedEffect(source.url) {
                    // Load bitmap on IO thread
                    val loadedBitmap = withContext(Dispatchers.IO) {
                        try {
                            val uri = Uri.parse(source.url)
                            val inputStream = context.contentResolver.openInputStream(uri)
                            if (inputStream != null) {
                                val bitmap = BitmapFactory.decodeStream(inputStream)
                                inputStream.close()
                                if (bitmap != null) {
                                    Log.d("PopVisualElement", "Image loaded successfully via ContentResolver: ${source.url}")
                                    bitmap.asImageBitmap()
                                } else {
                                    Log.e("PopVisualElement", "Image decode failed: ${source.url}")
                                    null
                                }
                            } else {
                                Log.e("PopVisualElement", "InputStream null for: ${source.url}")
                                null
                            }
                        } catch (e: Exception) {
                            Log.e("PopVisualElement", "Image load failed: ${source.url}, error: ${e.message}", e)
                            null
                        }
                    }
                    // Update state on main thread
                    imageBitmap = loadedBitmap
                }
                
                // Only show image if successfully loaded
                imageBitmap?.let { bitmap ->
                    Log.d("PopVisualElement", "Rendering bitmap for: ${source.url}")
                    Image(
                        bitmap = bitmap,
                        contentDescription = contentDescription,
                        modifier = propertiesModifier.then(backgroundModifier),
                        contentScale = contentScale,
                        colorFilter = colorFilter
                    )
                }
                // If loading or failed, show nothing - parent's fallback (initials) will show through
            } else {
                // For regular URLs, use Coil as normal
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(source.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = contentDescription,
                    modifier = propertiesModifier.then(backgroundModifier),
                    contentScale = contentScale,
                    colorFilter = colorFilter
                )
            }
        }
        is VisualElement.Image.Source.DataBase64String -> {
            // TODO: Handle base64 images if needed
            Box(modifier = propertiesModifier)
        }
        is VisualElement.Image.Source.IconRes -> {
            Image(
                painter = painterResource(id = source.resId),
                contentDescription = contentDescription,
                modifier = propertiesModifier.then(backgroundModifier),
                contentScale = contentScale,
                colorFilter = colorFilter
            )
        }
        is VisualElement.Image.Source.UnRecognised -> {
            // Do nothing
        }
    }
}

/**
 * Renders a Rive VisualElement
 */
@Composable
private fun PopVisualElementRive(
    modifier: Modifier,
    riveElement: VisualElement.Rive,
    contentDescription: String?
) {
    val density = LocalDensity.current
    
    // Apply properties
    val propertiesModifier = modifier.applyVisualElementProperties(
        properties = riveElement.properties,
        density = density
    )

    when (val source = riveElement.source) {
        is VisualElement.Rive.Source.ResourceId -> {
            RiveAnimationCompose(
                modifier = propertiesModifier,
                resourceId = source.resId,
                autoplay = riveElement.autoplay,
                artboardName = riveElement.artboardName,
                animationName = riveElement.animationName,
                stateMachineName = riveElement.stateMachineName,
                fit = Fit.CONTAIN,
                alignment = Alignment.CENTER,
                loop = Loop.AUTO,
                contentDescription = contentDescription,
                isPlayingLambda = { riveElement.autoplay }
            )
        }
        is VisualElement.Rive.Source.UnRecognised -> {
            // Do nothing
        }
    }
}

/**
 * Renders a ColorComponent VisualElement
 */
@Composable
private fun PopVisualElementColor(
    modifier: Modifier,
    colorElement: VisualElement.ColorComponent
) {
    val density = LocalDensity.current
    
    // Apply properties
    val propertiesModifier = modifier.applyVisualElementProperties(
        properties = colorElement.properties,
        density = density
    )

    val color = Color(colorElement.color.toColorInt())
    Box(
        modifier = propertiesModifier.background(color)
    )

}

/**
 * Applies VisualElementProperties to a modifier
 */
@Composable
private fun Modifier.applyVisualElementProperties(
    properties: VisualElementProperties?,
    density: Density
): Modifier {
    if (properties == null) {
        return this
    }
    
    var result = this
    
    // Apply dimensions
    if (properties.widthInDp > 0) {
        result = result.width(with(density) { properties.widthInDp.dp })
    }
    if (properties.heightInDp > 0) {
        result = result.height(with(density) { properties.heightInDp.dp })
    }
    
    // Apply padding
    if (properties.paddingProperties.start > 0 || 
        properties.paddingProperties.top > 0 ||
        properties.paddingProperties.end > 0 ||
        properties.paddingProperties.bottom > 0) {
        result = result.padding(
            start = with(density) { properties.paddingProperties.start.dp },
            top = with(density) { properties.paddingProperties.top.dp },
            end = with(density) { properties.paddingProperties.end.dp },
            bottom = with(density) { properties.paddingProperties.bottom.dp }
        )
    }
    
    // Apply aspect ratio
    properties.aspectRatio?.let { aspectRatio ->
        result = result.aspectRatio(
            ratio = aspectRatio.aspectRatio,
            matchHeightConstraintsFirst = aspectRatio.androidMatchHeightConstraintFirst
        )
    }
    
    // Apply corner radius
    if (properties.cornerRadius > 0) {
        result = result.background(
            color = Color.Transparent,
            shape = RoundedCornerShape(with(density) { properties.cornerRadius.dp })
        )
    }
    
    return result
}

