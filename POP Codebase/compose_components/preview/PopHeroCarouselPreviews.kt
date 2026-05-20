package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.ds_components.PopHeroCarousel
import com.pop.components.theme.PopTheme

/**
 * Sample data class for hero carousel items
 */
data class HeroCarouselItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: String,
    val offerPrice: String,
    val savings: String,
    val backgroundColor: Color,
    val centerContentColor: Color = Color.White
)

/**
 * Preview with sample carousel items using solid colors
 */
@Preview(
    name = "Hero Carousel - Basic",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PopHeroCarouselBasicPreview() {
    PopTheme {
        val sampleItems = listOf(
            HeroCarouselItem(
                id = "1",
                title = "Run with ease",
                subtitle = "Up to 60% off",
                price = "₹4,856",
                offerPrice = "₹2,656",
                savings = "You save ₹2000",
                backgroundColor = Color(0xFF8B1A1A) // Dark red
            ),
            HeroCarouselItem(
                id = "2",
                title = "Fitness Essentials",
                subtitle = "Up to 50% off",
                price = "₹3,500",
                offerPrice = "₹1,750",
                savings = "You save ₹1750",
                backgroundColor = Color(0xFF1A3A1A) // Dark green
            ),
            HeroCarouselItem(
                id = "3",
                title = "Style Your Way",
                subtitle = "Up to 40% off",
                price = "₹5,200",
                offerPrice = "₹3,120",
                savings = "You save ₹2080",
                backgroundColor = Color(0xFF1A1A3A) // Dark blue
            )
        )

        PopHeroCarousel(
            items = sampleItems,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            backgroundImageContent = { item, opacity ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(item.backgroundColor.copy(alpha = opacity))
                )
            },
            centerContent = { item, opacity ->
                Box(modifier = Modifier.fillMaxSize()) {
                    // Top text overlay (part of center content)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 24.dp)
                            .align(Alignment.TopStart),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = item.title,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = opacity)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.subtitle,
                            fontSize = 18.sp,
                            color = Color.White.copy(alpha = opacity * 0.9f)
                        )
                    }

                    // Center product image/content
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // Simulate product image with colored box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(200.dp)
                                .background(
                                    item.centerContentColor.copy(alpha = opacity * 0.3f)
                                )
                        )
                    }
                }
            },
            bottomContent = { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.price,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.offerPrice,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        )
    }
}

@Preview(
    name = "Hero Carousel - Minimal",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PopHeroCarouselMinimalPreview() {
    PopTheme {
        val sampleItems = listOf(
            HeroCarouselItem(
                id = "1",
                title = "Item 1",
                subtitle = "Description 1",
                price = "₹999",
                offerPrice = "₹499",
                savings = "Save ₹500",
                backgroundColor = Color(0xFF2D1B69) // Purple
            ),
            HeroCarouselItem(
                id = "2",
                title = "Item 2",
                subtitle = "Description 2",
                price = "₹1,299",
                offerPrice = "₹699",
                savings = "Save ₹600",
                backgroundColor = Color(0xFF1B6954) // Teal
            )
        )

        PopHeroCarousel(
            items = sampleItems,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            backgroundImageContent = { item, opacity ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(item.backgroundColor.copy(alpha = opacity))
                )
            },
            centerContent = { item, opacity ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.title,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = opacity)
                    )
                }
            },
            bottomContent = { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.offerPrice,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        )
    }
}

