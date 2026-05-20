package com.pop.components.ds_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pop.components.models.VisualElement
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor

@Composable
fun OverlappingImageStack(
    resIdList: List<Int>,
    modifier: Modifier = Modifier
) {
    val sizes = listOf(28.dp, 25.dp, 22.dp)
    val overlapOffset = 20.dp
    val maxSize = sizes.first()
    val count = resIdList.size.coerceAtMost(3)

    if (count == 0) {
        return
    }

    // Calculate total width: last avatar's offset + last avatar's size
    val totalWidth = overlapOffset * (count - 1) + sizes[count - 1]

    Box(
        modifier = modifier
            .height(maxSize)
            .width(totalWidth),
        contentAlignment = Alignment.CenterStart
    ) {
        when {
            resIdList.size <= 3 -> {
                resIdList.forEachIndexed { index, res ->
                    PopAvatar(
                        modifier = Modifier
                            .size(sizes[index])
                            .offset(x = overlapOffset * index)
                            .zIndex((3 - index).toFloat()), // 👈 FIX
                        type = AvatarType.Brand(
                            imageModel = VisualElement.buildFrom(res)
                        ),
                        size = AvatarSize.Small
                    )
                }
            }

            else -> {
                // First two avatars
                resIdList.take(2).forEachIndexed { index, res ->
                    PopAvatar(
                        modifier = Modifier
                            .size(sizes[index])
                            .offset(x = overlapOffset * index)
                            .zIndex((3 - index).toFloat()), // 👈 FIX
                        type = AvatarType.Brand(
                            imageModel = VisualElement.buildFrom(res)
                        ),
                        size = AvatarSize.Small
                    )
                }

                // +N indicator
                Box(
                    modifier = Modifier
                        .size(sizes[2])
                        .offset(x = overlapOffset * 2)
                        .background(
                            color = PopColor.WhiteBlack.White,
                            shape = RoundedCornerShape(5.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+${resIdList.size - 2}",
                        color = TextColor.SecondaryInvert,
                        fontSize = 12.sp,
                        style = PopTypography.figtreeStyles.labelSmall
                    )
                }
            }
        }
    }
}