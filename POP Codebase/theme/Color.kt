package com.pop.components.theme

import androidx.compose.ui.graphics.Color

@Deprecated("old Design System", replaceWith = ReplaceWith("PopColor"))
object PopColors {
    // Orange (O) colors
    object Orange {
        val O1 = Color(0xFF17100E)
        val O2 = Color(0xFF201511)
        val O3 = Color(0xFF38190F)
        val O4 = Color(0xFF4F1603)
        val O5 = Color(0xFF5F1D06)
        val O6 = Color(0xFF6F2B14)
        val O7 = Color(0xFF883B22)
        val O8 = Color(0xFFAF4C2C)
        val O9 = Color(0xFFFF5200)
        val O10 = Color(0xFFF14300)
        val O11 = Color(0xFFFF9874)
        val O12 = Color(0xFFFFD7C9)
        val O13 = Color(0xFF9E3300)
    }

    // Neutral (N) colors
    object Neutral {
        val N1 = Color(0xFF111110)
        val N2 = Color(0xFF191918)
        val N3 = Color(0xFF222221)
        val N4 = Color(0xFF2A2A28)
        val N5 = Color(0xFF31312E)
        val N6 = Color(0xFF3B3A37)
        val N7 = Color(0xFF494844)
        val N8 = Color(0xFF62605B)
        val N9 = Color(0xFF6F6D66)
        val N10 = Color(0xFF7C7B74)
        val N11 = Color(0xFFB5B3AD)
        val N12 = Color(0xFFFFFFFF)
    }

    // Red (R) colors
    object Red {
        val R1 = Color(0xFF191113)
        val R2 = Color(0xFF1E1517)
        val R3 = Color(0xFF3A141E)
        val R4 = Color(0xFF4E1325)
        val R5 = Color(0xFF5E1A2E)
        val R6 = Color(0xFF6F2539)
        val R7 = Color(0xFF883447)
        val R8 = Color(0xFFB3445A)
        val R9 = Color(0xFFE54666)
        val R10 = Color(0xFFEC5A72)
        val R11 = Color(0xFFFF949D)
        val R12 = Color(0xFFFED2E1)
        val R13 = Color(0xFFE5484D)
    }

    // Green (G) colors
    object Green {
        val G1 = Color(0xFF0D130E)
        val G2 = Color(0xFF131A15)
        val G3 = Color(0xFF192B1E)
        val G4 = Color(0xFF1A3A24)
        val G5 = Color(0xFF21482D)
        val G6 = Color(0xFF295737)
        val G7 = Color(0xFF326742)
        val G8 = Color(0xFF397A4E)
        val G9 = Color(0xFF22914F)
        val G10 = Color(0xFF24B468)
        val G11 = Color(0xFF6AD18A)
        val G12 = Color(0xFFB9F1C6)
        val G13 = Color(0x8022914F)
    }

    // Misc colors
    object Misc {
        val White = Color(0xFFF7F7F7)

        val White_EC = Color(0xFFEEEEEC)
        val MutedGray = Color(0xFFD4D4D4)
        val WhiteOpacity10 = Color(0x1AFFFFFF)  // white-o-10
        val WhiteOpacity33 = Color(0x54FFFFFF)  // white-o-33
        val WhiteOpacity66 = Color(0xA8FFFFFF)  // white-o-66
        val BlackOpacity33 = Color(0x54111110)  // b-33
        val BlackOpacity66 = Color(0xA8111110)  // b-66
        val BlackOpacity88 = Color(0xE0111110)  // b-88
        val Golden = Color(0xFFA48634)
        val BottomSheetSurfaceColor = Color(0xFF222222)
        val BlackOpacity60 = Color(0x99111110)
        
        object OnTop {
            val Default = Color(0x12FFFFFF)  // on-top.default
            val Disabled = Color(0x08FFFFFF)  // on-top.disabled
        }
    }

    object GreenPulsing {
        val FirstPulse = Color(0xFF35C771)
        val SecondPulse = Color(0xFF2BA35C)
    }

    object GreenShader {
        val color1 = Color(0xFF256345)
        val color2 = Color(0xFF182921)
        val color3 = Color(0x11182921)
    }
    object RedShader{
        val color1 = Color(0xFF532728)
        val color2 = Color(0xFF2D1E1D)
        val color3 = Color(0x112D1E1D)
    }
    object YellowShader{
        val color1 = Color(0xFF5C4928)
        val color2 = Color(0xFF28231C)
        val color3 = Color(0x1128231C)
    }
}

// Surface colors based on semantic tokens
object SurfaceColors {
    val Level0 = PopColors.Neutral.N2
    val Level1 = PopColors.Neutral.N3
    val Level2 = PopColors.Neutral.N5
    val Level3 = PopColors.Neutral.N7
    val Error = Color(0xFFE5484D)
    val Yellow = Color(0xFFE3AD41)
    val Violet = Color(0xFF8A6CFF)

    
    // Button backgrounds
    object Button {
        object Primary {
            val Default = PopColors.Orange.O9
            val Disabled = PopColors.Orange.O3
        }
        object Secondary {
            val Default = PopColors.Neutral.N12
            val Disabled = PopColors.Misc.OnTop.Disabled
        }
        object Tertiary {
            val Default = PopColors.Misc.OnTop.Default
            val Disabled = PopColors.Misc.OnTop.Disabled
        }
    }
    
    object Accent {
        val Default = PopColors.Orange.O9
        val Disabled = PopColors.Orange.O3
    }
    
    val Secondary = PopColors.Neutral.N2
    val SecondaryDisabled = Color(0x21FFFFFF)
    val Tertiary = PopColors.Misc.OnTop.Default
    val TertiaryDisabled = Color.Transparent
    val AccentDisabled = PopColors.Orange.O3
    val Frosted = Color(0x54FFFFFF)
    val Overlay = PopColors.Misc.BlackOpacity88
}

// Font/Text colors based on semantic tokens
object TextColors {
    object Primary {
        val Default = PopColors.Neutral.N12
        val Disabled = PopColors.Misc.OnTop.Default
    }
    
    object Secondary {
        val Default = PopColors.Neutral.N10
        val Disabled = PopColors.Misc.OnTop.Default
    }
    
    object Accent {
        val Default = PopColors.Orange.O9
        val Disabled = PopColors.Orange.O5
    }
    
    object Inverted {
        val Default = PopColors.Neutral.N1
        val Disabled = PopColors.Neutral.N1
    }
    object Tabs {
        val Selected = PopColors.Neutral.N2
        val Unselected = PopColors.Neutral.N11
    }
} 