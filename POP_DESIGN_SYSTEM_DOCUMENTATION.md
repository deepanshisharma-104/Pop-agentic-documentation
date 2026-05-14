# POP Design System — Complete Agentic Documentation

> **Purpose:** This document is a complete, LLM-readable reference for the POP Android design system written in Kotlin/Jetpack Compose. Every token, component, variant, state, and usage pattern is documented here so that any LLM (or developer) can fully understand and correctly use the codebase without reading source files.

---

## Table of Contents

1. [Codebase Structure](#1-codebase-structure)
2. [Theme System Overview](#2-theme-system-overview)
3. [Color System](#3-color-system)
4. [Typography System](#4-typography-system)
5. [Spacing System](#5-spacing-system)
6. [Gradient System](#6-gradient-system)
7. [Icon System](#7-icon-system)
8. [FavIcon System](#8-favicon-system)
9. [Dot System](#9-dot-system)
10. [Shape & Stroke Tokens](#10-shape--stroke-tokens)
11. [Grid System](#11-grid-system)
12. [Responsive Scaling](#12-responsive-scaling)
13. [PopTheme (Compose Theme Entry Point)](#13-poptheme-compose-theme-entry-point)
14. [Components — Button](#14-components--button)
15. [Components — Avatar](#15-components--avatar)
16. [Components — Badge](#16-components--badge)
17. [Components — Chip](#17-components--chip)
18. [Components — Input Field](#18-components--input-field)
19. [Components — OTP Field](#19-components--otp-field)
20. [Components — Bottom Sheet](#20-components--bottom-sheet)
21. [Components — Top Bar](#21-components--top-bar)
22. [Components — Toggle](#22-components--toggle)
23. [Components — Shimmer](#23-components--shimmer)
24. [Components — List Item](#24-components--list-item)
25. [Components — Carousel & Hero Carousel](#25-components--carousel--hero-carousel)
26. [Components — Tab Layout](#26-components--tab-layout)
27. [Components — Bottom Bar](#27-components--bottom-bar)
28. [Components — Accordion](#28-components--accordion)
29. [Components — Offers & Pills](#29-components--offers--pills)
30. [Components — Checkbox & Radio](#30-components--checkbox--radio)
31. [Components — Keypad](#31-components--keypad)
32. [Components — Toast](#32-components--toast)
33. [Components — Misc Visual Components](#33-components--misc-visual-components)
34. [Scrim System](#34-scrim-system)
35. [Preview Files](#35-preview-files)

---

## 1. Codebase Structure

```
POP Codebase/
├── theme/                          ← Design tokens (colors, typography, spacing, icons, gradients)
│   ├── Color.kt                    ← Legacy PopColors + semantic SurfaceColors / TextColors
│   ├── PopColor.kt                 ← New design system colors (Figma-sourced, 2024 DS)
│   ├── PopGradient.kt              ← Gradient sealed class + GradientPreset enum
│   ├── PopSpacing.kt               ← Spacing tokens (PopSpacing), corner radius (PopRadius), stroke (PopStroke), shape utilities (PopShapes)
│   ├── Spacing.kt                  ← Legacy FlashSpacing / FlashThemeSpacing + Radius/Height/Gap/Padding/Stroke objects
│   ├── Grid.kt                     ← FlashGrid layout constants
│   ├── PopTypography.kt            ← New typography system (PopTypography, TypographySpecs, FontFamilyStyles)
│   ├── Typography.kt               ← Legacy FlashTypography (h0–h5, body1–3, label1–2)
│   ├── Theme.kt                    ← PopTheme composable, FlashColor/FlashDimensions, NoRipple
│   ├── IconName.kt                 ← Sealed interface IconName + Icons object (all icon definitions)
│   ├── PopIcons.kt                 ← Icon sizes, IconStyle enum, IconSize enum, PopIconConfig
│   ├── PopIconResources.kt         ← (referenced) Maps IconName + IconStyle to drawable resource IDs
│   ├── FavIconColor.kt             ← FavIconColor enum (Orange, White, Green, Blue)
│   ├── FavIconSize.kt              ← FavIconSize enum (Large=22dp, Med=18dp)
│   ├── FavIconResources.kt         ← FavIconResources utility (maps color+size+isActive → drawable ID)
│   ├── PopDotColor.kt              ← PopDotColor enum (Orange, White, Green, Blue)
│   ├── PopDotSize.kt               ← PopDotSize enum (Large=8dp, Med=6dp)
│   └── ScaledDimensions.kt         ← Responsive scaling utilities (Responsive object)
│
├── ds_components/                  ← UI components
│   ├── PopButtonV2.kt              ← Main button (PopButtonV2, PopIconButtonV2)
│   ├── ButtonVariant.kt            ← ButtonVariant enum
│   ├── ButtonState.kt              ← ButtonState enum
│   ├── ButtonLoadingState.kt       ← ButtonLoadingState enum
│   ├── ButtonSpecs.kt              ← All button dimensions, font specs, gradient references
│   ├── PopAvatar.kt                ← PopAvatar composable (People, Brand, Icon, Illustration, Image, RawImage, Favorite)
│   ├── AvatarType.kt               ← AvatarType sealed interface + AvatarSize
│   ├── PopBadge.kt                 ← PopBadge composable + BadgeType enum
│   ├── PopChip.kt                  ← PopChip composable + PopChipVariant/PopChipMode enums
│   ├── PopChipStack.kt             ← Chip group/stack component
│   ├── PopInputFieldV2.kt          ← Text input field
│   ├── PopInputFieldHelpers.kt     ← Input field helper types
│   ├── PopOtpField.kt              ← OTP input field
│   ├── PopKeyPad.kt                ← Custom numeric keypad
│   ├── PopKeyPadHelpers.kt         ← Keypad helper types
│   ├── PopBottomSheet.kt           ← Bottom sheet with animations
│   ├── PopTopBar.kt                ← Collapsing top bar
│   ├── PopAppBar.kt                ← App bar
│   ├── PopAppBarDsl.kt             ← DSL builder for app bar
│   ├── PopAppBarManager.kt         ← App bar state manager
│   ├── PopTitleBar.kt              ← Title bar component
│   ├── PopTopBarWithContent.kt     ← Top bar with content variants
│   ├── PopTopBarWithCustomContent.kt
│   ├── PopTopBarWithXmlContent.kt
│   ├── PopTopBarScrimType.kt       ← Scrim type enum
│   ├── PopTopBarScrollBehavior.kt  ← Scroll behavior config
│   ├── PopToggle.kt                ← Toggle switch (On/Off/Indeterminate)
│   ├── PopRadio.kt                 ← Radio button
│   ├── PopCheckBoxV2.kt            ← Checkbox V2
│   ├── PopShimmer.kt               ← Shimmer loading placeholders
│   ├── PopShimmerBlocks.kt         ← Shimmer block presets
│   ├── PopListItem.kt              ← List item component
│   ├── PopCarousel.kt              ← Horizontal carousel
│   ├── PopHeroCarousel.kt          ← Hero image carousel
│   ├── PopTabLayout.kt             ← Tab layout
│   ├── PopBottomBar.kt             ← Bottom navigation bar
│   ├── PopAccordion.kt             ← Accordion / expandable section
│   ├── PopPills.kt                 ← Pill badges
│   ├── PopOfferHighlight.kt        ← Offer highlight component
│   ├── PopOfferToggle.kt           ← Offer toggle component
│   ├── PopBadge.kt                 ← Badge
│   ├── PopDot.kt                   ← Dot indicator
│   ├── PopVisualElement.kt         ← Generic visual element renderer
│   ├── PopLazyColumn.kt            ← Lazy column wrapper
│   ├── PopLazyVerticalGrid.kt      ← Lazy grid wrapper
│   ├── PopStickyHeader.kt          ← Sticky section header
│   ├── PopStickyHeaderGrid.kt      ← Sticky header for grids
│   ├── PopToastView.kt             ← Toast/snackbar UI
│   ├── PopMarqueeText.kt           ← Auto-scrolling marquee text
│   ├── RotatingText.kt             ← Text with rotation animation
│   ├── AlternatingSubtitleText.kt  ← Alternating subtitle animation
│   ├── SquircleShape.kt            ← Custom Squircle shape
│   ├── Scrim.kt                    ← Scrim overlay
│   ├── RiveAnimationCompose.kt     ← Rive animation wrapper
│   ├── OverlappingAvatarIcons.kt   ← Stacked/overlapping avatars
│   ├── PopVerticalButton.kt        ← Vertical icon+label button
│   ├── EmptyStateComponent.kt      ← Empty state screen
│   ├── FavIcon.kt                  ← Fav icon composable
│   └── scrim/                      ← Advanced blur/scrim system
│
└── preview/                        ← Compose @Preview files for each component
```

---

## 2. Theme System Overview

The POP app uses a **dual-layer design system**:

- **Legacy layer** (`FlashTypography`, `FlashSpacing`, `PopColors`, `SurfaceColors`, `TextColors`) — older, still widely used
- **New DS layer** (`PopColor`, `PopTypography`, `PopSpacing`, `PopRadius`, `PopStroke`) — Figma-sourced, 2024 design system

Both layers live under `com.pop.components.theme`. The theme entry point is `PopTheme { }`.

**Material3** dark color scheme is used under the hood. Ripple effects are globally disabled via `NoRippleIndicationNodeFactory`.

---

## 3. Color System

### 3.1 Legacy Color Palette — `PopColors` *(deprecated, use `PopColor` instead)*

**File:** `theme/Color.kt`  
**Package:** `com.pop.components.theme`  
**Status:** `@Deprecated` — replace with `PopColor`

```kotlin
// Orange scale (O1–O13)
PopColors.Orange.O9   // #FF5200 — primary brand orange
PopColors.Orange.O10  // #F14300
PopColors.Orange.O11  // #FF9874
PopColors.Orange.O12  // #FFD7C9

// Neutral / Grey (N1–N12)
PopColors.Neutral.N1  // #111110 — darkest
PopColors.Neutral.N12 // #FFFFFF — white

// Red (R1–R13)
PopColors.Red.R9      // #E54666
PopColors.Red.R13     // #E5484D

// Green (G1–G13)
PopColors.Green.G9    // #22914F
PopColors.Green.G13   // #8022914F (50% alpha)

// Misc
PopColors.Misc.White          // #F7F7F7
PopColors.Misc.WhiteOpacity10 // white at 10% opacity
PopColors.Misc.BlackOpacity88 // black at 88% opacity
PopColors.Misc.Golden         // #A48634
PopColors.Misc.OnTop.Default  // #12FFFFFF
PopColors.Misc.OnTop.Disabled // #08FFFFFF

// Shader objects for status-colored effects
PopColors.GreenShader.color1 / .color2 / .color3
PopColors.RedShader.color1 / .color2 / .color3
PopColors.YellowShader.color1 / .color2 / .color3
```

---

### 3.2 New Design System Colors — `PopColor`

**File:** `theme/PopColor.kt`  
**Package:** `com.pop.components.theme`  
**Source:** Figma POP-DS design file

This is the **single source of truth** for colors in the new DS. XML equivalent in `colors.xml`.

#### White / Black
```kotlin
PopColor.WhiteBlack.White  // #FFFFFF
PopColor.WhiteBlack.Black  // #000000
```

#### Grey (100–1000)
```kotlin
PopColor.Grey.Grey100  // #FFFFFF
PopColor.Grey.Grey200  // #E6E6E6
PopColor.Grey.Grey300  // #B3B3B3
PopColor.Grey.Grey400  // #808080
PopColor.Grey.Grey500  // #666666
PopColor.Grey.Grey600  // #4D4D4D
PopColor.Grey.Grey700  // #333333
PopColor.Grey.Grey800  // #262626
PopColor.Grey.Grey900  // #1F1F1F
PopColor.Grey.Grey1000 // #0D0D0D
```

#### Simple Gray (100–1000)
```kotlin
PopColor.SimpleGray.SimpleGray100  // #F4F4F4
// ... through SimpleGray1000 = #0D0D0D
```

#### Brand / Orange (100–1000)
```kotlin
PopColor.Brand.Brand100  // #FFEEE6
PopColor.Brand.Brand200  // #FFC9B0
PopColor.Brand.Brand300  // #FFAF8A
PopColor.Brand.Brand400  // #FF8B54
PopColor.Brand.Brand500  // #FF7533
PopColor.Brand.Brand600  // #FF5200  ← MAIN BRAND COLOR
PopColor.Brand.Brand700  // #E84B00
PopColor.Brand.Brand800  // #B53A00
PopColor.Brand.Brand900  // #8C2D00
PopColor.Brand.Brand1000 // #6B2200
```

#### Brand Primary Button Colors (gradient stops)
```kotlin
PopColor.BrandPrimaryButton.Color1  // #FF813B
PopColor.BrandPrimaryButton.Color2  // #FF5200
PopColor.BrandPrimaryButton.Color3  // #CD3401
PopColor.BrandPrimaryButton.Color4  // #B32A01
```

#### Destructive / Red (100–1000)
```kotlin
PopColor.Destructive.Destructive100  // #FDEDEB
PopColor.Destructive.Destructive500  // #F1715F  ← Main
PopColor.Destructive.Destructive600  // #EE4D37
PopColor.Destructive.Destructive700  // #C33A28
// ...
```

#### Success / Green (100–1000)
```kotlin
PopColor.Success.Success100  // #E7F8EE
PopColor.Success.Success500  // #3DC574
PopColor.Success.Success600  // #0DB651  ← Main
PopColor.Success.Success700  // #0CA64A
// ...
```

#### Warning / Yellow (100–1000)
```kotlin
PopColor.Warning.Warning100  // #FFF8E6
PopColor.Warning.Warning500  // #FCC93A
PopColor.Warning.Warning600  // #FBBC09  ← Main
// ...
```

#### Avatar Person Gradients (Start/End colors)
```kotlin
PopColor.AvatarPerson.PinkStart    / .PinkEnd
PopColor.AvatarPerson.SeaGreenStart / .SeaGreenEnd
PopColor.AvatarPerson.PurpleStart   / .PurpleEnd
PopColor.AvatarPerson.MaroonStart   / .MaroonEnd
PopColor.AvatarPerson.VioletStart   / .VioletEnd
PopColor.AvatarPerson.OrangeStart   / .OrangeEnd
PopColor.AvatarPerson.GreenStart    / .GreenEnd
PopColor.AvatarPerson.RedStart      / .RedEnd
```

#### Brand Brown, GeneralGreen, DestructiveRed, WarningYellow, Accent
Each has a scale from 50/100 to 900/1000. Example:
```kotlin
PopColor.BrandBrown.BrandBrown50   // #FFEBCE
PopColor.BrandBrown.BrandBrown900  // #4D2D09

PopColor.GeneralGreen.GeneralGreen100 // #F2FFF2
PopColor.GeneralGreen.GeneralGreen600 // #2CBE2C

PopColor.Accent.Accent10   // #1E8626
PopColor.Accent.Accent60   // #00CD00
```

---

### 3.3 Semantic Token Colors

These are the tokens to use in components — NOT raw palette values.

#### Surface Colors — `SurfaceColor`
```kotlin
SurfaceColor.Transparent            // #00000000
SurfaceColor.Primary                // #0D0D0D  ← app background
SurfaceColor.PrimaryTransparent50   // #800D0D0D
SurfaceColor.PrimaryTransparent10   // #1A0D0D0D
SurfaceColor.PrimaryDisabled70      // #B30D0D0D (70% opacity)
SurfaceColor.Secondary              // #1F1F1F
SurfaceColor.SecondaryFromTokens    // #1E1E1E
SurfaceColor.SecondaryTransparent60 // #991F1F1F
SurfaceColor.SecondaryDisabled      // #262626
SurfaceColor.Tertiary               // #4D4D4D
SurfaceColor.TertiaryFromTokens     // #2A2A2A
SurfaceColor.PrimaryInvert          // #FFFFFF  ← white surfaces
SurfaceColor.BrandPrimary05Percent  // #141414
SurfaceColor.BrandPrimaryLight      // #E0E0E0

// Gradient color stops (used in GradientPreset)
SurfaceColor.Gradient.Primary.Start   // #FFFFFF
SurfaceColor.Gradient.Primary.End     // #999999
SurfaceColor.Gradient.Secondary.Start // #1F1F1F
SurfaceColor.Gradient.Secondary.End   // #262626
SurfaceColor.Gradient.SecondaryHighlighted.Start // #323232
SurfaceColor.Gradient.SecondaryHighlighted.Mid   // #1C1C1C
SurfaceColor.Gradient.SecondaryHighlighted.End   // #0D0D0D
SurfaceColor.Gradient.SecondaryButton.Start // #464646
SurfaceColor.Gradient.SecondaryButton.End   // #262525
SurfaceColor.Gradient.Brand.Start       // #FF7533
SurfaceColor.Gradient.Brand.End         // #E84B00
SurfaceColor.Gradient.Destructive.Start // #EE4D37
SurfaceColor.Gradient.Destructive.End   // #C33A28
SurfaceColor.Gradient.Success.Start     // #26D526
SurfaceColor.Gradient.Success.End       // #015001
```

#### Text Colors — `TextColor`
```kotlin
TextColor.Primary                  // #E6E6E6 — main body text
TextColor.PrimaryFromTokens        // #FFFFFF — from Figma tokens
TextColor.PrimaryTransparent40     // #66E6E6E6 — 40% opacity
TextColor.PrimaryTransparent60     // #99E6E6E6
TextColor.PrimaryTransparent10     // #1AFFFFFF
TextColor.PrimaryInactive          // #7F7F7F
TextColor.Secondary                // #B3B3B3
TextColor.SecondaryFromTokens      // #A0A0A0
TextColor.SecondaryDisabled        // #333333
TextColor.Tertiary                 // #808080
TextColor.TertiaryFromTokens       // #909090
TextColor.PrimaryInvert            // #0D0D0D — dark text on white
TextColor.SecondaryInvert          // #666666
TextColor.Disabled                 // #4D4D4D
TextColor.Neutral                  // #B5B3AD
TextColor.Brand                    // #FF7533
TextColor.BrandFromTokens          // #FC7833
TextColor.BrandDisabled            // #6B2200
TextColor.BrandDisabledFromTokens  // #994A20
TextColor.Success                  // #3DC574
TextColor.SuccessFromTokens        // #2ED23A
TextColor.SuccessInvert            // #09813A
TextColor.Warning                  // #FCC93A
TextColor.WarningFromTokens        // #FFD43B
TextColor.WarningInvert            // #8A6705
TextColor.Destructive              // #EE4D37
TextColor.DestructiveFromTokens    // #E63D3D
TextColor.DestructiveInvert        // #C33A28
TextColor.Error                    // #FFFFFF — error state text (white on red)
```

#### Icon Colors — `IconColor`
Mirrors `TextColor` but for icon tints. Key tokens:
```kotlin
IconColor.Primary            // #E6E6E6
IconColor.PrimaryFromTokens  // #0D0D0D
IconColor.Secondary          // #B3B3B3
IconColor.Tertiary           // #808080
IconColor.PrimaryInvert      // #0D0D0D
IconColor.Disabled           // #4D4D4D
IconColor.Brand              // #FF7533
IconColor.BrandFromTokens    // #FC7833
IconColor.BrandDisabledFromTokens // #994A20
IconColor.Success            // #5DCE8A
IconColor.SuccessFromTokens  // #2ED23A
IconColor.Warning            // #FCD25A
IconColor.Destructive        // #EE4D37
```

#### Border Colors — `BorderColor`
```kotlin
BorderColor.Primary                  // #262626
BorderColor.Secondary                // #333333
BorderColor.BottomBarBorder          // #4D7A7A7A (30% opaque)
BorderColor.Tertiary                 // #4D4D4D
BorderColor.TertiaryTransparent40    // #664D4D4D
BorderColor.PrimaryTransparent30     // #4D1F1F1F
BorderColor.PrimaryInvert            // #E6E6E6
BorderColor.PrimaryInvertTransparent10 // #33E6E6E6 (20% opacity)
BorderColor.Brand                    // #FF5200
BorderColor.BrandFromTokens          // #FC7833
BorderColor.BrandDisabled            // #994A20
BorderColor.BrandTransparent40       // #66FF5200
BorderColor.Success                  // #3DC574
BorderColor.SuccessFromTokens        // #2ED23A
BorderColor.Destructive              // #EE4D37
BorderColor.Warning                  // #FCC93A
BorderColor.Error                    // #FFFFFF
```

#### Avatar Colors — `AvatarColor`
```kotlin
AvatarColor.PersonBorder         // = BorderColor.TertiaryTransparent40
AvatarColor.NonPersonBackground  // = PopColor.WhiteBlack.White
AvatarColor.NonPersonBorder      // = BorderColor.TertiaryTransparent40
AvatarColor.Text                 // = PopColor.WhiteBlack.White
```

#### Overflow Colors — `OverflowColor`
Used for scroll fade effects:
```kotlin
OverflowColor.Gradient.Start  // #0D0D0D (opaque dark)
OverflowColor.Gradient.End    // #000D0D0D (transparent)
```

#### TSS (Transaction Status Screen) Colors — `TssColor`
```kotlin
TssColor.Timer.Background       // #D4A745 (golden)
TssColor.Success.AccentGreen    // #2ED23A
TssColor.Pending.AccentYellow   // #FFD43B
TssColor.Failure.AccentRed      // #E63D3D
TssColor.Failure.RemarkError    // #FF7B7B
TssColor.Table.Background       // #1F1F1F
TssColor.Table.Border           // #333333
TssColor.Badge.VerifiedGreen    // #2ED23A
TssColor.Badge.VerifiedBackground // #1E8626

// Background gradient colors per TSS state
TssColor.Background.Success.GradientStart   // #2ED23A
TssColor.Background.Pending.GradientStart   // #FFD43B
TssColor.Background.Failure.GradientStart   // #E63D3D
TssColor.Background.Processing.GradientStart // #FC7833
// All states fade to GradientEnd = #0D0D0D
```

#### Legacy Semantic Colors — `SurfaceColors` / `TextColors` (deprecated)
```kotlin
// Still used inside PopTheme's FlashColor
SurfaceColors.Level0   // N2 dark surface
SurfaceColors.Button.Primary.Default  // O9 orange
TextColors.Primary.Default  // N12 white
TextColors.Accent.Default   // O9 orange
```

---

## 4. Typography System

There are **two typography systems** — use the new `PopTypography` for all new code.

### 4.1 New Typography — `PopTypography`

**File:** `theme/PopTypography.kt`  
**Font families:** Figtree (primary), Awesome Serif Italic (display/decorative)

#### Font Families

```kotlin
PopTypography.figtree                    // Figtree Regular/Medium/SemiBold/Bold
PopTypography.awesomeSerifItalic         // Regular variants
PopTypography.awesomeSerifItalicTall     // Tall variants
PopTypography.awesomeSerifItalicExtraTall // ExtraTall variants
```

#### How to Use

```kotlin
// Recommended: Access via FontFamilyStyles object
Text(style = PopTypography.figtreeStyles.headingLarge)
Text(style = PopTypography.awesomeSerifItalicTallStyles.displayLarge)

// Type-safe access
Text(style = PopTypography.getStyle(TextStyleName.HeadingLarge))
Text(style = PopTypography.getStyle(TextStyleName.HeadingLarge, PopTypography.figtree))
```

#### Complete Type Scale (`TypographySpecs`)

| Name | Font Size | Weight | Line Height | Letter Spacing |
|------|-----------|--------|-------------|----------------|
| `DisplayLarge` | 44sp | Bold (700) | 52sp | -2sp |
| `DisplayMedium` | 36sp | Bold (700) | 40sp | -2sp |
| `HeadingXTall` | 30sp | Normal (400) | 40sp | 0 |
| `HeadingLarge` | 20sp | SemiBold (600) | 26sp | 0 |
| `HeadingMedium` | 18sp | Medium (500) | 24sp | 0 |
| `HeadingSmall` | 16sp | Medium (500) | 24sp | 0 |
| `LabelXLarge` | 18sp | Medium (500) | 26sp | 0 |
| `LabelLarge` | 16sp | Bold (700) | 24sp | 0 |
| `LabelMedium` | 16sp | Medium (500) | 24sp | 0 |
| `LabelSmall` | 14sp | Medium (500) | 20sp | 0 |
| `LabelXSmall` | 12sp | Medium (500) | 16sp | 0 |
| `LabelXXSmall` | 10sp | Medium (500) | 12sp | 0 |
| `ParagraphLarge` | 24sp | Bold (700) | 32sp | 0 |
| `ParagraphMedium` | 16sp | Medium (500) | 24sp | 0 |
| `ParagraphSmall` | 14sp | Medium (500) | 22sp | 0 |
| `ParagraphXSmall` | 12sp | Normal (400) | 20sp | 0 |
| `LinkLarge` | 14sp | SemiBold (600) | 20sp | 0 |
| `LinkMedium` | 12sp | Medium (500) | 16sp | 0 |

#### Sealed TextStyleName (for type-safe access)
```kotlin
TextStyleName.DisplayLarge
TextStyleName.HeadingLarge / HeadingMedium / HeadingSmall
TextStyleName.LabelXLarge / LabelLarge / LabelMedium / LabelSmall / LabelXSmall / LabelXXSmall
TextStyleName.ParagraphLarge / ParagraphMedium / ParagraphSmall / ParagraphXSmall
TextStyleName.LinkLarge / LinkMedium
```

---

### 4.2 Legacy Typography — `FlashTypography`

**File:** `theme/Typography.kt`  
**Font:** Figtree (medium weight mapped to Normal)

```kotlin
FlashTypography.h0       // 28sp Bold, -0.04 tracking
FlashTypography.h1       // 24sp Bold, -0.04 tracking
FlashTypography.h2       // 20sp Bold, -0.02 tracking
FlashTypography.h3       // 18sp Bold, -0.02 tracking
FlashTypography.h4       // 15sp Bold, -0.02 tracking
FlashTypography.h5       // 13sp Bold, -0.02 tracking
FlashTypography.body1    // 18sp Medium, -0.02 tracking
FlashTypography.body2    // 15sp Medium, -0.02 tracking
FlashTypography.body3    // 13sp Medium, -0.01 tracking
FlashTypography.label1   // 11sp Medium, -0.01 tracking
FlashTypography.label2   // 12sp Medium, -0.01 tracking
FlashTypography.upiNumber       // 64sp SemiBold (Figtree)
FlashTypography.upiRupeeSymbol  // 60sp Normal (Oswald font)
```

Access inside composable: `val type = FlashTheme.type` (via `LocalFlashTypography`)

---

## 5. Spacing System

### 5.1 New Spacing Tokens — `PopSpacing`

**File:** `theme/PopSpacing.kt`

```kotlin
PopSpacing.Spacing0   = 0.dp
PopSpacing.Spacing2   = 2.dp
PopSpacing.Spacing4   = 4.dp
PopSpacing.Spacing6   = 6.dp
PopSpacing.Spacing8   = 8.dp
PopSpacing.Spacing10  = 10.dp
PopSpacing.Spacing12  = 12.dp
PopSpacing.Spacing16  = 16.dp
PopSpacing.Spacing20  = 20.dp
PopSpacing.Spacing24  = 24.dp
PopSpacing.Spacing28  = 28.dp
PopSpacing.Spacing32  = 32.dp
PopSpacing.Spacing36  = 36.dp
PopSpacing.Spacing44  = 44.dp
PopSpacing.Spacing48  = 48.dp
PopSpacing.Spacing56  = 56.dp
PopSpacing.Spacing120 = 120.dp
```

### 5.2 Corner Radius Tokens — `PopRadius`

```kotlin
PopRadius.None       = 0.dp     // Sharp corners
PopRadius.ExtraSmall = 4.dp     // Small badges/tags
PopRadius.Small      = 8.dp     // Chips, compact buttons, small cards
PopRadius.Medium     = 12.dp    // Standard buttons, inputs, standard cards ← MOST COMMON
PopRadius.Large      = 16.dp    // Large cards, bottom sheets, modals
PopRadius.XLarge     = 20.dp    // Extra large components
PopRadius.XLLarge    = 999.dp   // Pill/capsule shapes
```

### 5.3 Stroke Tokens — `PopStroke`

```kotlin
PopStroke.Default = 0.5.dp  // Standard border width for all UI elements
```

### 5.4 Shape Utilities — `PopShapes`

```kotlin
PopShapes.rounded(PopRadius.Medium)           // Single radius all corners
PopShapes.rounded(topStart, topEnd, bottomStart, bottomEnd)  // Per-corner
PopShapes.pill()                              // Fully rounded (50%)
PopShapes.topRounded(radius)                 // Only top corners rounded (bottom sheets)
PopShapes.bottomRounded(radius)              // Only bottom corners rounded
PopShapes.leftRounded(radius)                // Only left corners rounded
PopShapes.rightRounded(radius)               // Only right corners rounded
```

### 5.5 Border Modifier Extensions

```kotlin
Modifier.popBorder(color, width = PopStroke.Default, shape)
Modifier.popBorder(color, radius, width)
Modifier.popBorderPill(color, width)
Modifier.popBorderTopRounded(color, radius = PopRadius.Large, width)
Modifier.popBorderBottomRounded(color, radius = PopRadius.Large, width)
```

### 5.6 Legacy Spacing — `FlashThemeSpacing`

```kotlin
FlashThemeSpacing.scale000 = 0.dp
FlashThemeSpacing.scale050 = 1.dp
FlashThemeSpacing.scale100 = 2.dp
FlashThemeSpacing.scale200 = 4.dp
FlashThemeSpacing.scale300 = 8.dp
FlashThemeSpacing.scale350 = 10.dp
FlashThemeSpacing.scale400 = 16.dp
FlashThemeSpacing.scale450 = 18.dp
FlashThemeSpacing.scale500 = 24.dp
FlashThemeSpacing.scale600 = 32.dp
FlashThemeSpacing.scale650 = 36.dp
FlashThemeSpacing.scale700 = 44.dp
FlashThemeSpacing.scale800 = 56.dp
FlashThemeSpacing.scale900 = 64.dp
FlashThemeSpacing.scale950 = 86.dp
```

### 5.7 Legacy Dimension Objects (from `Spacing.kt`)

```kotlin
// Radius
Radius.ExtraSmall = 4.dp   // scale200
Radius.Small      = 10.dp  // scale350
Radius.Default    = 16.dp  // scale400
Radius.Full       = 32.dp  // scale600
Radius.Rounding10 / Rounding16 / Rounding24 / Rounding30

// Height
Height.ExtraSmall = 32.dp  // scale600
Height.Small      = 36.dp  // scale650
Height.Medium     = 44.dp  // scale700
Height.Large      = 56.dp  // scale800
Height.ExtraLarge = 86.dp  // scale950
Height.IconMedium = 18.dp  // scale450
Height.H32 / H36 / H44 / H56

// Gap
Gap.None             = 0.dp
Gap.ExtraExtraSmall  = 2.dp
Gap.ExtraSmall       = 4.dp
Gap.Small            = 8.dp
Gap.Medium           = 16.dp
Gap.Large            = 32.dp

// Padding
Padding.None    = 0.dp
Padding.Default = 16.dp
Padding.Large   = 32.dp

// Stroke
Stroke.WeightLarge  = 2.dp
Stroke.WeightMedium = 1.dp

// Simple Spacing object
Spacing.Scale0  = 0.dp
Spacing.Scale2  = 2.dp
Spacing.Scale4  = 4.dp
Spacing.Scale8  = 8.dp
Spacing.Scale16 = 16.dp
Spacing.Scale24 = 24.dp
Spacing.Scale32 = 32.dp
Spacing.Scale64 = 64.dp
```

---

## 6. Gradient System

**File:** `theme/PopGradient.kt`  
**Package:** `com.pop.components.theme`

### 6.1 PopGradient Sealed Interface

Three types of gradient:

```kotlin
// 1. Solid color (no gradient)
PopGradient.Solid(color: Color)

// 2. Linear gradient
PopGradient.Linear(
    colors: List<Color>,
    stops: List<Float>? = null,         // null = evenly distributed
    angleInDegrees: Float = 0f,         // 0 = up, 90 = right, 180 = down (CSS angle)
    useAsCssAngle: Boolean = true,
    tileMode: TileMode = TileMode.Clamp
)

// 3. Radial gradient
PopGradient.Radial(
    colors: List<Color>,
    stops: List<Float>? = null,
    center: Offset = Offset.Unspecified, // default = center of area
    alignment: Alignment? = null,        // overrides center (e.g. Alignment.TopCenter)
    radius: Float = Float.POSITIVE_INFINITY, // infinity = fill area
    radiusMultiplier: Float = 1f,        // 1.5f = extend 50% beyond bounds
    radiusMultiplierX: Float = 1f,
    radiusMultiplierY: Float = 1f,
    constrainRadiusToBoundsX: Boolean = false,
    constrainRadiusBoundsModeX: RadiusBoundsMode = NearestEdge,
    constrainRadiusToBoundsY: Boolean = false,
    constrainRadiusBoundsModeY: RadiusBoundsMode = NearestEdge,
    tileMode: TileMode = TileMode.Clamp
)
```

Apply to a composable via:
```kotlin
Modifier.popBackground(gradient = myGradient)
Modifier.popBackground(gradient = myGradient, shape = CircleShape)
```

### 6.2 GradientPreset Enum — Pre-defined Gradients

All production gradients are defined as `GradientPreset` enum entries. Access with `.gradient`:

```kotlin
val myGradient = GradientPreset.ButtonBrandPrimaryLarge.gradient
```

#### Avatar Gradients
| Preset | Description |
|--------|-------------|
| `AvatarBrand` | Orange radial gradient — for brand/logo avatars (Brand500→Brand600→Brand800) |
| `AvatarPerson1` | Pink radial (PinkStart→PinkEnd, radiusMultiplier=1.6) |
| `AvatarPerson2` | Sea Green radial |
| `AvatarPerson3` | Purple radial |
| `AvatarPerson4` | Maroon radial |
| `AvatarPerson5` | Violet radial |
| `AvatarPerson6` | Orange-brown radial |
| `AvatarPerson7` | Green radial |
| `AvatarPerson8` | Red radial |

**Getting avatar gradient by name (deterministic, hash-based):**
```kotlin
GradientPreset.getAvatarGradientForName("John Doe") // always same color
GradientPreset.getAvatarGradientByIndex(2)
```

#### Surface Gradients
| Preset | Type | Description |
|--------|------|-------------|
| `SurfacePrimary` | Linear 180° | White (#FFF) → Grey (#999) |
| `SurfacePrimaryLarge` | Radial | #1D1D1D → #121212 (card bg) |
| `SurfaceSecondaryGradientLarge` | Radial | #464646 → #1F1F1F |

#### Overflow Gradients (scroll fades)
| Preset | Description |
|--------|-------------|
| `OverflowStart` | Left-to-right: #0D0D0D → transparent |
| `OverflowEnd` | Left-to-right: transparent → #0D0D0D |

#### Button Gradients
| Preset | Used for |
|--------|---------|
| `ButtonBrandPrimaryLarge` | Brand primary button (radial, center-constrained) |
| `ButtonBrandPrimaryLargeHorizontal` | Brand primary — horizontal layout (constrainRadiusToBoundsY) |
| `ButtonPrimaryInvertLarge` | White/primary button (linear top→bottom) |
| `ButtonSecondaryLarge` | Secondary button (radial) |
| `ButtonSecondaryLargeHorizontal` | Secondary — horizontal (constrainRadiusToBoundsY) |
| `ButtonSuccessLarge` | Green success button (linear, green stops) |
| `ButtonDestructiveLarge` | Red destructive button (linear, red stops) |
| `ButtonWhiteLarge` | White button (for BrandPrimary loading) |
| `ButtonPrimaryDisabled` | Disabled primary (pre-blended dark) |
| `ButtonBrandPrimaryDisabled` | Disabled brand (dark reddish-brown) |

#### Badge Gradients
| Preset | Colors |
|--------|--------|
| `BadgeBrandPrimary` | Orange (Brand500→Brand600→Brand700) |
| `BadgePopCoins` | Deep red (#942A04 → #390F00) |
| `BadgeSuccess` | Green (#26D526 → #059505 → #015001) |
| `BadgeDestructive` | Red (#F27373 → #E22626 → #8A0F0F) |
| `BadgeWhitePrimary` | White → Grey (linear) |

#### Offer / Product Card Gradients
| Preset | Colors | Direction |
|--------|--------|-----------|
| `PopOfferGradient` | Blue gradient with opacity wave | 90° (LTR) |
| `OfferGradient` | Blue (#0043B6 → #0461FF → transparent) | 90° |
| `CashGradient` | Green (#25620C → transparent) at 30% opacity | 90° |
| `PayIn3Gradient` | Purple (#8000EA → transparent) at 30% opacity | 90° |
| `PopCoinGradient` | Orange (#A93205 → transparent) at 30% opacity | 90° |
| `OfferGradientDisabled` | Same as Offer at 10% opacity | 90° |
| `CashGradientDisabled` | Same as Cash at 10% opacity | 90° |
| `PayIn3GradientDisabled` | Same as PayIn3 at 10% opacity | 90° |
| `PopCoinGradientDisabled` | Same as PopCoin at 10% opacity | 90° |

#### General-Purpose Named Gradients
| Preset | Description |
|--------|-------------|
| `GradientGreen` | Bright green radial (3-stop: #20C920→#059505→#036E03) |
| `GradientRed` | Red radial (3-stop: #F17272→#E22626→#AC1818) |
| `GradientOrange` | Orange radial (4-stop brand button gradient) |
| `GradientOrangeLoaderView` | Extended orange radial for loading views |
| `GradientBlack` | Black radial (#303030→#0D0D0D) |
| `GradientBlackLinear` | Black linear top-to-bottom |
| `GradientWhiteGray` | White-to-dark-gray radial (5 stops) |
| `GradientBlue` | Blue radial (5-stop: #469DFF→#1B70FF→#005EFF→#004ED2→#003FAA) |
| `CardBackground` | Card dark radial (#1D1D1D→#121212) |

---

## 7. Icon System

**Files:** `theme/IconName.kt`, `theme/PopIcons.kt`

### 7.1 Icon Sizes — `PopIcons`

```kotlin
PopIcons.sizeSmall   = 16.dp  // Small icons
PopIcons.sizeMedium  = 24.dp  // Standard (default)
PopIcons.sizeLarge   = 32.dp  // Large icons
PopIcons.sizeXLarge  = 48.dp  // XLarge

PopIcons.buttonSizeSmall  = 32.dp  // Icon button sizes
PopIcons.buttonSizeMedium = 40.dp
PopIcons.buttonSizeLarge  = 48.dp

PopIcons.defaultSize = 24.dp
```

### 7.2 Icon Style

```kotlin
enum class IconStyle { Outline, Filled }
enum class IconSize { Small, Medium, Large, XLarge }
fun IconSize.toDp(): Dp  // → 16, 24, 32, 48 dp
```

### 7.3 PopIconConfig

```kotlin
data class PopIconConfig(
    val iconName: IconName,
    val style: IconStyle,
    val size: IconSize,
    val tint: Color,
    val contentDescription: String? = null
)
```

### 7.4 IconName Sealed Interface

Icons are defined as `object` types inside the `Icons` object. Each icon has:
- `value: String` — string identifier
- `outlineRes: Int?` — drawable resource ID for outline variant
- `filledRes: Int?` — drawable resource ID for filled variant

### 7.5 All Defined Icons (in `Icons` object)

| Icon Object | value | Notes |
|-------------|-------|-------|
| `Icons.Share05` | `ic_share_05` | Outline + Filled |
| `Icons.Share01` | `ic_share_01` | Outline only |
| `Icons.Share06` | `ic_share_06` | Outline + Filled |
| `Icons.ShareAppBar` | `ic_share_appbar` | App bar share |
| `Icons.Copy06` | `ic_copy_06` | Copy icon |
| `Icons.ChevronLeft` | `chevron_left` | Back arrow |
| `Icons.ChevronDown` | `chevron_down` | Down arrow |
| `Icons.ChevronDown16` | `ic_chevron_down_16` | 16px down |
| `Icons.ChevronDown12` | `ic_chevron_down_12` | 12px down |
| `Icons.ChevronRight` | `chevron_right` | Right arrow |
| `Icons.Cross` | `cross` | Close/dismiss |
| `Icons.Check` | `check` | Checkmark |
| `Icons.CheckBoxSelected` | `check` | Filled checkbox |
| `Icons.CheckBoxIndeterminate` | `check` | Partial checkbox |
| `Icons.CheckIcon` | `check` | Filled check circle |
| `Icons.CheckVerified` | `check_verified` | Verified badge |
| `Icons.Mobile` | `mobile` | Phone icon |
| `Icons.AlertHexagon` | `alert_hexagon` | Hex alert |
| `Icons.AlertCircle` | `alert_circle` | Circle alert |
| `Icons.AlertWarning` | `alert_triangle` | Triangle warning |
| `Icons.BackSpaceIcon` | `back_space` | Backspace (keypad) |
| `Icons.Search` | `search` | Search/magnifier |
| `Icons.WhatsApp` | `whatsapp_square` | WhatsApp icon |
| `Icons.Download` | `download` | Download arrow |
| `Icons.Download01` | `ic_download_01` | Download variant |
| `Icons.HelpCircle` | `ic_help_circle` | Help circle |
| `Icons.HELP_CIRCLE` | `help_circle` | Help circle (alias) |
| `Icons.HomeBottomNav` | `ic_home_bottom_nav` | Bottom nav home |
| `Icons.ShopBottomNav` | `ic_shop_bottom_nav` | Bottom nav shop |
| `Icons.CardBottomNav` | `ic_card_bottom_nav` | Bottom nav card |
| `Icons.Flag` | `ic_flag` | Country flag |
| `Icons.InfoSquare` | `info_square` | Info badge |
| `Icons.RupeeSymbol` | `rupee_symbol` | ₹ symbol |
| `Icons.RupeeIcon` | `RupeeIcon` | Rupee (alias) |
| `Icons.User01` | `user_01` | Person/user |
| `Icons.Trash` | `trash` | Delete/trash |
| `Icons.AddIcon` | `add` | Plus / add |
| `Icons.NoResultsFound` | `no_results_found` | Empty search |
| `Icons.Settings` | `add` | Settings gear |
| `Icons.VpaVerified` | `vpa_verified` | VPA verified badge |
| `Icons.ViewHistory` | `view_history` | Transaction history |
| `Icons.Edit` | `edit` | Pencil edit |
| `Icons.EditTertiary` | `edit` | Edit (tertiary color) |
| `Icons.QR` | `qrCode` | QR code icon |
| `Icons.ScanQR` | `scanQrCode` | Scan QR FAB |
| `Icons.Reset` | `reset` | Timer reset |
| `Icons.Reset01` | `ic_reset` | Reset variant |
| `Icons.Bank` | `bank` | Bank icon |
| `Icons.BankIcon` | `BankIcon` | Bank (grey variant) |
| `Icons.Support` | `Help & Support` | Support/headphone |
| `Icons.Headphone` | `headphone` | Headphone |
| `Icons.SendMoneyArrow` | `SendMoneyArrow` | Send arrow |
| `Icons.ReceiptCheck` | `ReceiptCheck` | Receipt tick |
| `Icons.ArrowLoop` | `ArrowLoop` | Refresh/loop |
| `Icons.PendingHourGlass` | `PendingHourGlass` | Hourglass |
| `Icons.GreyPin` | `GreyPin` | Location pin |
| `Icons.GiftWrap` | `GiftWrap` | Gift icon |
| `Icons.HumanAvatar` | `HumanAvatar` | Default person |
| `Icons.GreyCard` | `GreyCard` | Card icon |
| `Icons.UpiLiteFlash` | `UpiLiteFlash` | UPI Lite flash |
| `Icons.UpiLiteTss` | `UpiLiteTss` | UPI Lite TSS |
| `Icons.BlockedMessage` | `BlockedMessage` | Blocked chat |
| `Icons.Message` | `Message` | Chat/message |
| `Icons.QueryClosed` | `query_closed` | Complaint closed |
| `Icons.QueryFailed` | `query_failed` | Complaint failed |
| `Icons.QueryReview` | `query_review` | Complaint in review |
| `Icons.Refresh` | `refresh` | Refresh/reload |
| `Icons.SMS` | `sms` | SMS message |
| `Icons.Notification` | `notification` | Bell |
| `Icons.DefaultCircle` | `default_circle` | Default placeholder |
| `Icons.Link` | `link` | Hyperlink |
| `Icons.Cart` | `link` | Shopping cart (outline/filled differ) |
| `Icons.Heart` | `link` | Wishlist heart |
| `Icons.BharatAssured` | `bharat_assured` | Bharat Assured seal |
| `Icons.Close` | `close` | Close X (white) |
| `Icons.Bright` | `bright` | Brightness |
| `Icons.UserSquare` | `userSquare` | Square user |
| `Icons.Block` | `block` | Block user |
| `Icons.Spam` | `spam` | Spam flag |
| `Icons.Star` | `star` | Rating star |
| `Icons.VkycUnderReview` | `vkyc_under_review` | VKYC review |
| `Icons.Contacts` | `contacts` | Contacts/offers |
| `Icons.ArrowCredited` | `arrow_credited` | Credit arrow ↓ |
| `Icons.ArrowDebited` | `arrow_credited` | Debit arrow ↑ |
| `Icons.TransactionHistory` | `transaction_history` | History icon |
| `Icons.ProfileCard` | `card` | Profile card |
| `Icons.EverythingUpi` | `everything_upi` | UPI umbrella |
| `Icons.Logout` | `logout` | Logout door |
| `Icons.MyBills` | `my_bills` | Bills icon |
| `Icons.Orders` | `orders` | Orders icon |
| `Icons.RateUs` | `rate_us` | Rate/review |
| `Icons.SavedAddress` | `saved_address` | Address pin |
| `Icons.TermsAndConditions` | `terms_n_conditions` | T&C doc |
| `Icons.ProfileWishlist` | `wishlist` | Wishlist |
| `Icons.PayBills` | `pay_bills` | Pay bills |
| `Icons.BlockedVpa` | `blocked_vpa` | Blocked VPA |
| `Icons.CheckBalance` | `check_balance` | Balance check |
| `Icons.ManageAutoPay` | `manage_auto_pay` | AutoPay |
| `Icons.MyQueries` | `my_queries` | Queries list |
| `Icons.MyUpiId` | `my_upi_id` | UPI ID |
| `Icons.PayFriends` | `pay_friends` | Pay friends |
| `Icons.PendingRequests` | `pending_requests` | Pending |
| `Icons.EverythingUpiLite` | `upi_lite` | UPI Lite |
| `Icons.UpiNumber` | `upi_number` | UPI number |
| `Icons.UpiSupport` | `upi_support` | UPI support |
| `Icons.CashbackLedgerClaimed` | `cashback_ledger_claimed` | Claimed cashback |
| `Icons.CashbackLedgerUPIShop` | `cashback_upi_txn` | UPI cashback |
| `Icons.CashbackLedgerReferralBonus` | `cashback_referral` | Referral bonus |

---

## 8. FavIcon System

**Files:** `theme/FavIconColor.kt`, `theme/FavIconSize.kt`, `theme/FavIconResources.kt`

Used for "favourite" heart icon with active/inactive states and multiple color variants.

```kotlin
enum class FavIconColor { Orange, White, Green, Blue }
enum class FavIconSize { Large, Med }

// Get Dp value from size
FavIconSize.Large.toIconDp() // → 22.dp
FavIconSize.Med.toIconDp()   // → 18.dp
```

#### Getting the correct drawable resource

```kotlin
FavIconResources.getFavIconResourceId(
    color = FavIconColor.White,
    size = FavIconSize.Large,
    isActive = true
)
// Returns Int? resource ID, or null if not found

FavIconResources.hasFavIcon(color, size, isActive)   // Boolean check
FavIconResources.getFavIconResourceName(color, size, isActive)  // "ic_fav_white_large_active"
```

#### Available variants (drawable resources)

| Color | Size | Active | Inactive |
|-------|------|--------|---------|
| Orange | Large | ✅ | ❌ |
| Orange | Med | ✅ | ❌ |
| White | Large | ✅ | ✅ |
| White | Med | ✅ | ✅ |
| Green | Large | ✅ | ❌ |
| Green | Med | ✅ | ❌ |
| Blue | Large | ✅ | ❌ |

---

## 9. Dot System

**Files:** `theme/PopDotColor.kt`, `theme/PopDotSize.kt`

Small status/indicator dots.

```kotlin
enum class PopDotColor { Orange, White, Green, Blue }
enum class PopDotSize { Large, Med }

PopDotSize.Large.toDotDp()  // → 8.dp
PopDotSize.Med.toDotDp()    // → 6.dp
```

---

## 10. Shape & Stroke Tokens

See [Section 5.2](#52-corner-radius-tokens--popradius) and [Section 5.3](#53-stroke-tokens--popstroke).

### Special Shape: `SquircleShape`

**File:** `ds_components/SquircleShape.kt`

A custom "squircle" (super-ellipse) shape used for brand avatars, badges, and chips.

```kotlin
SquircleShape(
    cornerRadius: Dp? = null,  // null = pill-shaped (fully rounded)
    smoothing: Float = 1.0f    // Squircle smoothing factor (1.0 = smooth, 1.8 = more rounded)
)

// Usage examples:
SquircleShape()                              // Pill shape
SquircleShape(cornerRadius = 20.dp, smoothing = 1.8f)  // Brand avatar shape
SquircleShape(cornerRadius = null, smoothing = 1.0f)   // Chip shape
```

---

## 11. Grid System

**File:** `theme/Grid.kt`

```kotlin
FlashGrid.MobileGrid.horizontalPadding = 16.dp
FlashGrid.MobileGrid.verticalGutter    = 27.dp
FlashGrid.MobileGrid.contentGutter     = 20.dp
FlashGrid.MobileGrid.rowOffset         = 43.dp
FlashGrid.MobileGrid.itemSpacing       = 20.dp
FlashGrid.MobileGrid.contentPadding    // PaddingValues(horizontal=16, vertical=27)

// Shortcut defaults
GridDefaults.DefaultHorizontalPadding  = 16.dp
GridDefaults.DefaultVerticalGutter     = 27.dp
GridDefaults.DefaultContentGutter      = 20.dp
GridDefaults.DefaultItemSpacing        = 20.dp
```

---

## 12. Responsive Scaling

**File:** `theme/ScaledDimensions.kt`

Base reference screen: **390dp wide**, 420 DPI.

```kotlin
// All functions are @Composable

// Scale dp proportional to screen width
Responsive.scaledDp(baseDp: Dp): Dp

// Scale dp ignoring display zoom level
Responsive.scaledDpIgnoringZoom(baseDp: Dp): Dp

// Scale sp proportional to screen width
Responsive.scaledSp(baseSp: TextUnit): TextUnit

// Scale sp ignoring system font scale
Responsive.scaledSpIgnoringFontScale(baseSp: TextUnit): TextUnit

// Scale sp ignoring both font scale and zoom
Responsive.scaledSpIgnoringAll(baseSp: TextUnit): TextUnit

// Non-composable: get display zoom level
Responsive.getDisplayZoomLevel(): Float  // 1.0 = normal
```

---

## 13. PopTheme (Compose Theme Entry Point)

**File:** `theme/Theme.kt`

```kotlin
@Composable
fun PopTheme(content: @Composable () -> Unit)
```

`PopTheme` wraps content with:
- `LocalFlashColors` — `FlashColor(SurfaceColors, TextColors)`
- `LocalFlashDimensions` — `FlashDimensions(Radius, Height, Padding, Stroke, Gap, FlashGrid)`
- `LocalFlashGrid` — grid constants
- `LocalFlashTypography` — `FlashTypography`
- `LocalOverscrollConfiguration = null` — disables overscroll glow
- `LocalIndication = NoRippleIndicationNodeFactory` — no ripple effects
- `MaterialTheme` with dark color scheme

**Dark Color Scheme mapping:**
```
primary   = PopColors.Orange.O9 (#FF5200)
secondary = PopColors.Neutral.N12 (white)
tertiary  = PopColors.Misc.OnTop.Default
background/surface = SurfaceColor.Primary (#0D0D0D)
error     = PopColors.Red.R9 (#E54666)
```

**Accessing theme values in composables:**
```kotlin
val colors = FlashTheme.colors          // FlashColor
val type   = FlashTheme.type            // FlashTypography
val dims   = FlashTheme.dimensions      // FlashDimensions
val grid   = FlashTheme.grid            // FlashGrid
```

---

## 14. Components — Button

**Files:** `ds_components/PopButtonV2.kt`, `ds_components/ButtonVariant.kt`, `ds_components/ButtonState.kt`, `ds_components/ButtonLoadingState.kt`, `ds_components/ButtonSpecs.kt`

### 14.1 Main Button — `PopButtonV2`

```kotlin
@Composable
fun PopButtonV2(
    text: CharSequence,               // Supports String or AnnotatedString
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Primary,
    state: ButtonState = ButtonState.Active,
    buttonLoadingState: ButtonLoadingState? = null,  // Only when state == Loading
    size: ButtonSize = ButtonSize.Large,
    leftIcon: VisualElement? = null,
    rightIcon: VisualElement? = null,
    widthType: ButtonWidthType = ButtonWidthType.Wrap,
    onDisabledClick: (() -> Unit)? = null,  // fires even when disabled
    onClick: () -> Unit
)
```

### 14.2 Icon-Only Button — `PopIconButtonV2`

```kotlin
@Composable
fun PopIconButtonV2(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconRes: VisualElement? = null,
    variant: ButtonVariant = ButtonVariant.Primary,
    state: ButtonState = ButtonState.Active,
    buttonLoadingState: ButtonLoadingState? = null,
    size: ButtonSize = ButtonSize.Large,
    onDisabledClick: (() -> Unit)? = null
)
// Always circular shape
```

### 14.3 ButtonVariant

```kotlin
enum class ButtonVariant {
    Primary,      // White/light button (gradient: white→grey linear, black text)
    Secondary,    // Dark grey button (gradient: dark radial, white text, grey border)
    Tertiary,     // Pure black (#0D0D0D) button (no gradient, white text)
    BrandPrimary, // Orange brand button (gradient: BrandPrimaryButton radial, white text)
    White,        // White button (same gradient as Primary, black text)
    Ghost,        // Transparent, no border, text-only button
    BrandGhost    // Transparent with brand-color border + brand-color text
}
```

### 14.4 ButtonState

```kotlin
enum class ButtonState {
    Active,      // Normal interactive state
    Disabled,    // Grayed out, non-interactive (but onDisabledClick can still fire)
    Loading,     // Shows spinner or loading icon; requires buttonLoadingState
    Destructive  // Red/error styling (Secondary/Tertiary show red text; Primary shows red gradient bg)
}
```

### 14.5 ButtonLoadingState

```kotlin
enum class ButtonLoadingState {
    Default,      // Circular spinner
    Success,      // Check icon (✓) appears before text; green gradient bg
    Destructive,  // Cross icon (✗) appears before text; red gradient bg
    Intermediate  // Reserved for future use
}
```

### 14.6 ButtonSize

```kotlin
enum class ButtonSize { XSmall, Small, Medium, Large }
enum class ButtonWidthType { Wrap, Fill }
```

**Exact dimensions from Figma (`ButtonSpecs`):**

| Size | Height | H.Padding | Font Size | Line Height | Icon Size |
|------|--------|-----------|-----------|-------------|-----------|
| XSmall | 32dp | 12dp | 12sp | 16sp | 16dp |
| Small | 36dp | 16dp | 14sp | 20sp | 18dp |
| Medium | 44dp | 20dp | 16sp | 24sp | 20dp |
| Large | 56dp | 28dp | 18sp | 24sp | 24dp |

Font weight: Medium (500) for XSmall/Small/Medium, SemiBold (600) for Large.

Corner radius: pill-shaped (buttonHeight / 2).

Border width: `PopStroke.Default` = 0.5dp.

### 14.7 Button Visuals per Variant/State

| Variant | Active BG | Disabled BG | Text | Border |
|---------|-----------|-------------|------|--------|
| Primary | White→Grey gradient (linear) | Dark pre-blended gradient | Black | None |
| Secondary | Dark radial gradient | PrimaryDisabled70 | White / Red (destructive) | #333333 |
| Tertiary | #0D0D0D solid | PrimaryDisabled70 | White / Red (destructive) | None |
| BrandPrimary | Orange radial gradient | Dark reddish-brown gradient | White | Subtle white 10% |
| White | White→Grey gradient | PrimaryDisabled70 | Black | None |
| Ghost | Transparent | Transparent | Primary text | None |
| BrandGhost | Transparent | Transparent | Brand color | Brand border |

**Effects:**
- `BrandPrimary` + Active/Loading: noise texture overlay + orange glow
- `Primary` + Active/Loading: white glow effect
- `BrandPrimary` loading with `Default` state: switches to white gradient background

### 14.8 Debounce

Clicks are automatically debounced with a 600ms cooldown (prevents double-taps).

---

## 15. Components — Avatar

**Files:** `ds_components/PopAvatar.kt`, `ds_components/AvatarType.kt`

### 15.1 `PopAvatar` Composable

```kotlin
@Composable
fun PopAvatar(
    modifier: Modifier = Modifier,
    type: AvatarType,
    size: AvatarSize,
    contentDescription: String? = null,
    isDisabled: Boolean = false,
    topRightComposable: @Composable (() -> Unit)? = null  // Badge overlay slot
)
```

### 15.2 AvatarType (Sealed Interface)

```kotlin
// People — circular, shows initials + optional image + optional border
AvatarType.People(
    name: String,                          // Used for initials extraction (up to 2 letters)
    imageModel: VisualElement? = null,     // If provided, overlays initials
    background: PopGradient = GradientPreset.AvatarBrand.gradient,  // Radial by default
    showBorder: Boolean = true,
    borderGradient: PopGradient = PopGradient.Solid(AvatarColor.PersonBorder),
    textColor: Color = TextColor.Primary.copy(alpha = 0.8f)
)

// Favorite — same as People but shows fav heart icon in bottom-right
AvatarType.Favorite(name, imageModel, background, showBorder, borderGradient, textColor)

// Brand — squircle, for logos/brand images
AvatarType.Brand(
    imageModel: VisualElement? = null,
    background: PopGradient = PopGradient.Solid(AvatarColor.NonPersonBackground),
    showBorder: Boolean = true,
    borderGradient: PopGradient = PopGradient.Solid(AvatarColor.NonPersonBorder),
    shape: Shape? = null,         // Default: SquircleShape(cornerRadius=20dp, smoothing=1.8)
    iconPadding: Dp? = null       // Custom padding around image
)

// Icon — DS icon avatars with fill variants
AvatarType.Icon(
    fill: AvatarIconFill,          // PrimaryHighlighted, SecondaryHighlighted, TertiaryTransparent, OnlyIcon, Primary50Percent
    icon: IconName? = null,
    iconStyle: IconStyle = IconStyle.Outline,
    iconTint: Color? = null,       // null = default from fill
    showBorder: Boolean = true,
    borderColor: Color = BorderColor.Tertiary,
    shape: Shape? = null
)

// Illustration — image with rounded shape
AvatarType.Illustration(imageModel: VisualElement, shape: Shape? = null)

// Image — edge-to-edge circular image (no padding/border/background)
AvatarType.Image(imageModel: VisualElement)

// RawImage — renders VisualElement as-is, no clipping
AvatarType.RawImage(imageModel: VisualElement)
```

### 15.3 AvatarIconFill Variants

```kotlin
enum class AvatarIconFill {
    PrimaryHighlighted,    // Dark gradient bg (#1A→#21→#0D), Secondary border
    SecondaryHighlighted,  // Gradient (#323232→#1C1C1C→#0D0D0D), Secondary border
    TertiaryTransparent,   // No bg, Tertiary border
    OnlyIcon,              // No bg, no border
    Primary50Percent       // 60% opaque dark (#990D0D0D bg), primary icon tint
}
```

### 15.4 AvatarSize

(Defined in `AvatarType.kt` — details include `.size` Dp, `.initialsStyle` TextStyle, `.initialsMaxWidth` Dp, `.iconSize` Dp, `.logoPadding` Dp, `.cornerRadius` Dp)

---

## 16. Components — Badge

**File:** `ds_components/PopBadge.kt`

```kotlin
@Composable
fun PopBadge(
    modifier: Modifier = Modifier,
    label: String,
    type: BadgeType,
    hasBg: Boolean = true,         // Show background gradient
    hasGlow: Boolean = false,      // Show glow effect (only with hasBg=true)
    leftIcon: IconName? = null,
    rightIcon: IconName? = null
)
```

### BadgeType

```kotlin
enum class BadgeType {
    Orange,       // Brand gradient bg / orange text (no bg)
    Green,        // Success gradient bg / green text (no bg)
    Red,          // Destructive gradient bg / red text (no bg)
    WhitePrimary, // White linear gradient bg / dark text on bg, white text off bg
    WhiteTertiary,// No bg only; grey text
    Warning       // No bg only; yellow text
}
```

**Visual rules:**
- Shape: `SquircleShape()` (pill)
- Padding: 8dp horizontal + 4dp vertical (with bg), 0dp horizontal (no bg)
- Font: Figtree Medium 12sp / 16sp line height
- `WhiteTertiary` and `Warning` always `hasBg = false` regardless of parameter

**Glow colors (when `hasGlow = true`):**
```kotlin
Orange glow: rgba(255,80,11, 0.62)
Green glow:  rgba(33,195,33, 0.40)
Red glow:    rgba(226,38,38, 0.62)
White glow:  rgba(255,255,255, 0.42)
```

---

## 17. Components — Chip

**File:** `ds_components/PopChip.kt`

```kotlin
@Composable
fun PopChip(
    modifier: Modifier = Modifier,
    config: PopChipConfig
)
```

`PopChipConfig` fields:
```kotlin
data class PopChipConfig(
    val text: String,
    val isActive: Boolean = false,
    val mode: PopChipMode = PopChipMode.Toggleable,
    val variant: PopChipVariant = PopChipVariant.Basic,
    val enabled: Boolean = true,
    val leadingIcon: PopIconConfig? = null,
    val onClick: (() -> Unit)? = null,
    val onCloseClick: (() -> Unit)? = null   // Only for WithClose variant
)
```

### PopChipVariant

```kotlin
enum class PopChipVariant {
    Basic,        // Label only
    WithClose,    // Label + close (X) icon
    WithDropdown  // Label + chevron-down icon
}
```

### PopChipMode

```kotlin
enum class PopChipMode {
    Toggleable,  // Tapping toggles active/inactive state
    Static       // Always shows inactive UI (no state change on tap)
}
```

**Visual spec:**
- Shape: `SquircleShape(smoothing = 1.0f)` — pill
- BG: `SurfaceColor.Secondary` (#1F1F1F) active/enabled, `SurfaceColor.SecondaryDisabled` (#262626) disabled
- Active state: animated white border (0.5dp)
- Padding: 10dp horizontal, 6dp vertical
- Min height: 28dp
- Icon size: 16dp (`PopIcons.sizeSmall`)
- Text style: `PopTypography.figtreeStyles.labelXSmall` (12sp Medium)
- Gap between elements: 8dp
- All state changes animate with `CubicBezierEasing(0.7f, 0f, 0.3f, 1f)` over 1000ms

---

## 18. Components — Input Field

**File:** `ds_components/PopInputFieldV2.kt`  
**Helpers:** `ds_components/PopInputFieldHelpers.kt`

The input field system uses a **config-based API** (preferred). Pass a typed config object to the single `PopInputFieldV2(config)` overload. A deprecated multi-parameter overload still exists for backwards compatibility.

### PopInputFieldType / Config objects

| Type | Config class | Description |
|------|-------------|-------------|
| `UnderlineNakedSmall` | `UnderlineNakedSmallConfig` | Title above, underline border, small text. *Requires title.* |
| `UnderlineNakedLarge` | `UnderlineNakedLargeConfig` | Large amount/number entry (64sp), no title. Transparent BG, underline only. Supports custom keypad. |
| `MobileInputField` | `MobileInputFieldConfig` | Rounded box, country-code prefix, phone icon. *Requires title.* |
| `Basic` | `BasicInputFieldConfig` | Rounded rectangle with optional start/end icons or end slot. Hint text floats above on focus. |
| `Search` | `SearchInputFieldConfig` | Search bar with magnifier icon, clear button. Supports `SearchBorderStyle` (Subtle or DefinedThin). |
| `Small` | `SmallInputFieldConfig` | Compact input with title. |

### Config-based API example

```kotlin
// Preferred
val config = BasicInputFieldConfig(
    value = email,
    onValueChange = { email = it },
    hintText = "Email",
    startIcon = startIconConfig,   // PopIconConfig?
    endIcon = endIconConfig,       // PopIconConfig?
    status = InputFieldStatus.Error,
    statusMessage = "Invalid email"
)
PopInputFieldV2(config = config)

// UnderlineNakedLarge + custom keypad
val config = UnderlineNakedLargeConfig(
    value = amount,
    onValueChange = { amount = it },
    placeholder = "0",
    useCustomKeypad = true           // Hides system keyboard
)
PopInputFieldV2(config = config)
// Render PopKeyPad separately; connect via value/onValueChange
```

### InputFieldStatus

```kotlin
enum class InputFieldStatus { Success, Error, Info, Warning }
```

Used for validation feedback. Each status maps to an icon + color:
- `Success` → `Icons.CheckVerified`, `TextColor.Success` (green)
- `Error` → `Icons.AlertHexagon`, `TextColor.Destructive` (red)
- `Info` → `Icons.InfoSquare`, `TextColor.Tertiary` (grey)
- `Warning` → `Icons.AlertCircle`, `TextColor.Warning` (yellow)

### StatusMessage composable (internal)

Renders icon + status text in a row. Used internally by all input field types. Available text styles:
```kotlin
TitleTextStyle  = PopTypography.figtreeStyles.labelSmall    // 14sp Medium
BodyTextStyle   = PopTypography.figtreeStyles.paragraphMedium // 16sp Medium
StatusTextStyle = PopTypography.figtreeStyles.labelXSmall   // 12sp Medium
```

### SearchBorderStyle

```kotlin
sealed class SearchBorderStyle {
    object Subtle : SearchBorderStyle()        // No visible border (default)
    object DefinedThin : SearchBorderStyle()   // 0.5dp border
}
```

### Key visual specs

- Animation duration: `DEFAULT_ANIMATION_DURATION = 200ms`
- Status icon size: `IconSize.Small` (`STATUS_ICON_SIZE`)
- Status icon spacing: `PopSpacing.Spacing4`
- Border radius (Basic/Search/Mobile): `PopRadius.Medium` (12dp)
- All field backgrounds: `SurfaceColor.SecondaryFromTokens` (#1F1F1F)
- Focused border: `BorderColor.PrimaryInvert` (white at full opacity)
- Error border: `TextColor.DestructiveFromTokens` (orange-red)
- Success border: `TextColor.SuccessFromTokens` (green)
- Cursor color: `TextColor.Brand` (#FF7533 orange)

---

## 19. Components — OTP Field

**File:** `ds_components/PopOtpField.kt`

Config-based OTP/PIN entry. Accepts either `DiscreteConfig` or `DiscreteNakedConfig`.

```kotlin
@Composable
fun PopOtpField(
    config: PopOtpFieldConfig,   // DiscreteConfig or DiscreteNakedConfig
    modifier: Modifier = Modifier
)
```

### PopOtpFieldType

```kotlin
enum class PopOtpFieldType {
    Discrete,       // Individual boxes with background + border
    DiscreteNaked   // Individual boxes with transparent background, no border
}
```

### DiscreteConfig fields

```kotlin
data class DiscreteConfig(
    val value: String,                        // Current OTP string (max otpCount chars)
    val onValueChange: (String, Boolean) -> Unit, // (newValue, isComplete)
    val otpCount: Int = 6,                    // Must be 4 or 6
    val enabled: Boolean = true,
    val status: InputFieldStatus? = null,
    val statusMessage: String? = null,
    val onDone: () -> Unit = {},
    val onResendOtp: (() -> Unit)? = null,
    val onEditClick: (() -> Unit)? = null,    // "Edit" link in status message
    val resendText: String = "Resend OTP",
    val onErrorCleared: (() -> Unit)? = null,
    val autoFocus: Boolean = true,
    val autoSubmit: Boolean = true,           // Calls onDone when length == otpCount
    val isLoading: Boolean = false,
    val clearOnError: Boolean = false,        // Auto-clears field when status = Error
    val focusable: Boolean = true,            // Set false when using PopKeyPad
    val resendCountdownSeconds: Int? = null,  // Shows "Resend in MM:SS" countdown
    val editUnderlineSpacing: Dp = 2.dp
) : PopOtpFieldConfig
```

### Box dimensions

| `otpCount` | Box width | Box height |
|------------|-----------|------------|
| 4 | `PopSpacing.Spacing56` (56dp) | 56dp (square) |
| 6 | `PopSpacing.Spacing44` (44dp) | 56dp |

Box spacing: `PopSpacing.Spacing12` (12dp between boxes).

### Visual specs (Discrete variant)

- Shape: `SquircleShape(cornerRadius = PopRadius.Medium, smoothing = 1f)`
- Background: `SurfaceColor.SecondaryFromTokens` (#1F1F1F), disabled: `SurfaceColor.SecondaryDisabled` (#262626)
- Border: `PopStroke.Default` (0.5dp)
- Border color mapping:
  - Focused (empty): `BorderColor.PrimaryInvert` (white)
  - Success: `TextColor.SuccessFromTokens` (green)
  - Error: `TextColor.DestructiveFromTokens` (red-orange)
  - Unfocused: `BorderColor.PrimaryInvert.copy(alpha = 0.5f)`
  - Disabled: `BorderColor.PrimaryInvert.copy(alpha = 0.3f)`
- Digit style: Figtree Medium 22sp
- Blinking cursor when focused + empty: 2dp × 24dp `TextColor.Primary` bar, 500ms blink

### Visual specs (DiscreteNaked variant)

Same layout but `backgroundColor = Color.Transparent` and no border drawn.

### Resend countdown

When `resendCountdownSeconds` is provided, a countdown timer shows "Resend in MM:SS". Timer is lifecycle-aware (pauses when backgrounded). When it reaches 0, the resend text becomes clickable in `TextColor.Brand`.

---

## 20. Components — Bottom Sheet

**File:** `ds_components/PopBottomSheet.kt`

### Entry Point

```kotlin
// Preferred: config-based
@Composable
fun PopBottomSheet(
    showSheet: Boolean = false,
    onDismissRequest: () -> Unit,
    config: PopBottomSheetConfig,
    onCloseButtonClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
)
```

### PopBottomSheetConfig

```kotlin
data class PopBottomSheetConfig(
    val cornerRadius: Dp = 16.dp,
    val isDraggable: Boolean = true,
    val isCancellable: Boolean = true,          // tap-outside and back-press dismiss
    val showFloatingCloseButton: Boolean = false,
    val backgroundType: BottomSheetBackgroundType = BottomSheetBackgroundType.Transparent,
    val animationConfig: BottomSheetAnimationConfig = ...,  // see below
    val scrimConfig: BottomSheetScrimConfig = ...,
    val topVisualElement: VisualElement? = null             // Straddles top edge
)
```

### Background Types

```kotlin
sealed class BottomSheetBackgroundType {
    object Transparent : BottomSheetBackgroundType()  // Caller controls background
    data class Gradient(val shape: BottomSheetGradientShape) : BottomSheetBackgroundType()
}

enum class BottomSheetGradientShape { Rounded, Squircle }
```

When `Gradient` is used:
- Background: `GradientPreset.SurfacePrimaryLarge` (#1D1D1D → #121212 radial)
- Border: `BorderColor.Tertiary` at `PopStroke.Default`
- Shape: `RoundedCornerShape(cornerRadius)` or `SquircleShape(cornerRadius, smoothing=1f)`

### Animation System

Internal state machine: `Hidden → Entering → Visible → Exiting → Hidden`

**Effects (all configurable):**
- **Scale**: Activity root view shrinks to 0.99 scale while open (depth illusion) + rounded corners on root view
- **Slide**: Sheet enters from +50dp below, exits to +50dp
- **Fade**: Content fades in/out
- **Scrim**: 80% opaque `#0D0D0D` overlay that fades in/out

**Constants:**
```
ENTER_SLIDE_OFFSET_DP = 100
EXIT_SLIDE_OFFSET_DP  = 50
BOTTOM_MARGIN_DP      = 24   (keeps sliver of background visible)
CLOSE_BUTTON_SIZE_DP  = 40
VISIBLE_SCALE         = 0.99
SHEET_ENTER_SCALE_START = 1.1 (zooms in slightly on enter)
```

### Floating Close Button

When `showFloatingCloseButton = true`:
- Circular 40dp button centred above the sheet
- Background: `SurfaceColor.Secondary`
- Icon: `Icons.Cross`, tint: `TextColor.Primary`, size 16dp

---

## 21. Components — Top Bar

**File:** `ds_components/PopTopBar.kt`

```kotlin
@Composable
fun PopTopBar(
    modifier: Modifier = Modifier,
    config: PopTopBarConfig = PopTopBarConfig.default(),
    scrollState: ScrollState? = null,           // Column scroll awareness
    lazyListState: LazyListState? = null,       // LazyColumn scroll awareness
    scrollBehavior: PopTopBarScrollBehaviorState? = null,
    scrollBehaviorConfig: PopTopBarScrollBehaviorConfig = PopTopBarScrollBehaviorConfig(),
    contentNeedsScrolling: Boolean = true,      // If false, top bar won't collapse
    centerRightSlot: (@Composable () -> Unit)? = null,
    scrimState: ScrimState? = null,
    isContentScrollingBehind: Boolean = false   // For blur without merchant image
)
```

**Features:**
- Status bar theme control (Light/Dark/Transparent)
- App bar with navigation and action icons
- Optional merchant image background (Large/Medium/Small sizes, aspect-ratio-based height)
- Collapsing title bar (fades out on scroll)
- Blur scrim effect when collapsed
- Extends to status bar when merchant image present

---

## 22. Components — Toggle

**File:** `ds_components/PopToggle.kt`

```kotlin
enum class PopToggleSize { Large, Medium }
enum class PopToggleState { On, Off, Indeterminate }

// Animation constants
TOGGLE_INDETERMINATE_FLASH_MS = 100L
TOGGLE_TO_CENTER_MS = 70   // thumb moves to center
TOGGLE_TO_EDGE_MS   = 180  // thumb moves to edge
```

Toggle thumb slides with animation. Indeterminate state flashes then settles.

---

## 23. Components — Shimmer

**File:** `ds_components/PopShimmer.kt`

Animated shimmer loaders with different placeholder shapes. Uses an infinite sweep animation (`LinearEasing`, left-to-right gradient sweep).

**Available shimmer types:**
- People circles: 28dp, 36dp, 56dp, 64dp
- Brand squares: 16dp, 28dp, 36dp, 56dp, 64dp
- List items: Small, Med, Large
- Payment list: Med, Large (+ Left-aligned Small, Right-aligned Small)
- Body text placeholders
- Title placeholders: Med, Large
- Table row placeholders
- Card placeholders

Color: Animated gradient sweep over dark surface background (inherits from `PopColor.SimpleGray`).

**Pre-built blocks in `PopShimmerBlocks.kt`** — ready-to-drop shimmer layouts for common patterns.

---

## 24. Components — List Item

**File:** `ds_components/PopListItem.kt`

```kotlin
@Composable
fun PopListItem(
    headlineText: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    leadingAvatar: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    onHeadlineTextClick: (() -> Unit)? = null,
    onSupportingTextClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    headlineColor: Color = TextColor.Primary,
    supportingTextColor: Color = TextColor.Secondary,
    headlineTextStyle: TextStyle = PopTypography.figtreeStyles.labelLarge,
    supportingTextStyle: TextStyle = PopTypography.figtreeStyles.paragraphSmall,
    // Marquee options
    enableHeadlineMarquee: Boolean = false,
    enableSupportingTextMarquee: Boolean = false,
    marqueeSpeedPxPerSecond: Float = 60f,
    marqueeDelayMillis: Long = 1500L,
    marqueeLoopCount: Int? = null,
    marqueeGradientWidth: Dp = 56.dp,
    showMarqueeLeftGradient: Boolean = false,
    showMarqueeRightGradient: Boolean = false,
    // Gradient options for text labels
    showHeadlineLeftGradient: Boolean = false,
    showHeadlineRightGradient: Boolean = false,
    headlineTextFillMaxWidth: Boolean = false,
    // Icon slots next to text
    headlineLeftIcon: VisualElement? = null,
    headlineRightIcon: VisualElement? = null,
    supportingTextLeftIcon: VisualElement? = null,
    supportingTextRightIcon: VisualElement? = null,
    headlineIconSizeDp: Dp = 16.dp,
    supportingTextIconSizeDp: Dp = 12.dp,
    iconGapDp: Dp = 6.dp,
    // Divider
    showDivider: Boolean = true,
    dividerColor: Color = BorderColor.Tertiary,
    verticalPadding: Dp = 12.dp,
    // ... more options
)
```

---

## 25. Components — Carousel & Hero Carousel

**Files:** `ds_components/PopCarousel.kt`, `ds_components/PopHeroCarousel.kt`

- `PopCarousel`: Horizontal paging carousel for content cards
- `PopHeroCarousel`: Full-bleed hero image carousel with dots/indicators

---

## 26. Components — Tab Layout

**File:** `ds_components/PopTabLayout.kt`

Horizontal scrollable tab row. Active tab indicated with animated underline or indicator. Supports:
- Text-only tabs
- Icon + Text tabs
- Badge on tabs

---

## 27. Components — Bottom Bar

**File:** `ds_components/PopBottomBar.kt`

Bottom navigation bar. Contains navigation destinations mapped to `Icons.HomeBottomNav`, `Icons.ShopBottomNav`, `Icons.CardBottomNav`. Has animated selection indicator. Uses `BorderColor.BottomBarBorder` for top border.

---

## 28. Components — Accordion

**File:** `ds_components/PopAccordion.kt`

Expandable/collapsible section. Animated height change with chevron rotation.

---

## 29. Components — Offers & Pills

**Files:** `ds_components/PopOfferHighlight.kt`, `ds_components/PopOfferToggle.kt`, `ds_components/PopPills.kt`

- **PopPills**: Small labelled pills for categorization (uses `GradientPreset.OfferGradient`, `CashGradient`, `PayIn3Gradient`, `PopCoinGradient` with disabled variants)
- **PopOfferHighlight**: Card/banner showing an active offer
- **PopOfferToggle**: Toggleable offer selection card

---

## 30. Components — Checkbox & Radio

### PopCheckBoxV2

**File:** `ds_components/PopCheckBoxV2.kt`  
Figma: node `114:2926` (checkbox control) + `5345:1987` (with text)

```kotlin
// Control-only (20dp)
@Composable
fun PopCheckBoxV2(
    state: PopCheckBoxState,
    onStateChange: (PopCheckBoxState) -> Unit,
    modifier: Modifier = Modifier,
    promoted: Boolean = false,    // Orange brand highlight mode
    enabled: Boolean = true,
)

// With title + optional body text (full-width row)
@Composable
fun PopCheckBoxWithTextV2(
    state: PopCheckBoxState,
    onStateChange: (PopCheckBoxState) -> Unit,
    title: AnnotatedString,
    body: String?,
    modifier: Modifier = Modifier,
    promoted: Boolean = false,
    enabled: Boolean = true,
    align: PopCheckBoxAlign = PopCheckBoxAlign.Left,  // Left or Right
    onTitleClick: ((Int) -> Unit)? = null,
)
```

#### PopCheckBoxState

```kotlin
enum class PopCheckBoxState { Selected, Unselected, Indeterminate }
```

#### Visual specs

- Size: **20dp** outer (8dp icon + 6dp padding each side)
- Shape: `RoundedCornerShape(PopRadius.ExtraSmall)` — 4dp corner
- Icon used: `Icons.CheckBoxSelected` (filled 8dp), `Icons.CheckBoxIndeterminate` for indeterminate

| State | Border | Background | Glow |
|-------|--------|-----------|------|
| Unselected | `BorderColor.PrimaryInvert` | none | none |
| Selected (enabled) | none | `GradientPreset.SurfacePrimary` (white→grey) | `rgba(255,255,255,0.42)` blur 9dp |
| Selected + promoted | none | Brand linear gradient (180°) | `rgba(255,80,11,0.62)` blur 9dp |
| Selected + disabled | none | `SurfaceColor.SecondaryDisabled` | none |
| Unselected + disabled | `BorderColor.Tertiary` | none | none |

Text layout in `PopCheckBoxWithTextV2`:
- Row gap: 12dp
- Text column gap: 2dp  
- Title: Figtree 500, 14sp, lineHeight 20sp
- Body: Figtree 400, 12sp, lineHeight 20sp
- Body color: success text when selected, brand text when promoted, secondary otherwise

---

### PopRadio

**File:** `ds_components/PopRadio.kt`  
Figma: node `5345:2830`

```kotlin
@Composable
fun PopRadio(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    promoted: Boolean = false,
    enabled: Boolean = true,
)
```

- Size: **20dp** outer, **8dp** dot inner
- Shape: `CircleShape`
- Same visual states as checkbox (border/background/glow mapping is identical)
- Inner dot color when selected: `SurfaceColor.Primary` (#0D0D0D)

---

## 31. Components — Keypad

### PopKeyPad

**File:** `ds_components/PopKeyPad.kt`  
Figma: node `6424-9514`

```kotlin
@Composable
fun PopKeyPad(
    onKeyPress: (KeyPadKey) -> Unit,
    modifier: Modifier = Modifier
)
```

#### KeyPadKey (sealed class)

```kotlin
sealed class KeyPadKey {
    data class Number(val value: String) : KeyPadKey()
    data object Decimal : KeyPadKey()
    data object Backspace : KeyPadKey()
}
```

#### Layout

4×3 grid (rows of 3, 4 rows):
```
1   2   3
4   5   6
7   8   9
.   0   ⌫
```

#### Key visual specs

| Key type | Background color |
|----------|-----------------|
| Number (1-9, 0) | `SurfaceColor.SecondaryTransparent60` |
| Decimal (`.`) | `SurfaceColor.PrimaryTransparent50` |
| Backspace (`⌫`) | `SurfaceColor.PrimaryTransparent50` |

- Button height: `PopSpacing.Spacing56` (56dp)
- Max button width: `PopSpacing.Spacing120` (120dp)
- Button spacing: `PopSpacing.Spacing8` (8dp)
- Row spacing: `PopSpacing.Spacing8` (8dp)
- Shape: `PopShapes.rounded(PopRadius.Medium)` (12dp)
- Number text style: `PopTypography.figtreeStyles.headingLarge`, `TextColor.Primary`
- Backspace icon: `Icons.BackSpaceIcon`, `IconStyle.Outline`, `IconSize.Medium`
- Haptic feedback on every key press (`HapticFeedbackType.VirtualKey`)
- Backspace supports **long-press**: initial 500ms delay then repeats every 100ms

#### Integration with PopInputFieldV2

```kotlin
// In the screen:
var amount by remember { mutableStateOf("") }

PopInputFieldV2(
    config = UnderlineNakedLargeConfig(
        value = amount,
        onValueChange = { amount = it },
        useCustomKeypad = true   // Disables system keyboard
    )
)

PopKeyPad { key ->
    amount = handleKeyPadInput(amount, key)   // PopKeyPadHelpers.kt
}
```

---

## 32. Components — Toast

### PopToastView

**File:** `ds_components/PopToastView.kt`

```kotlin
@Composable
fun PopToastView(
    config: ToastConfig,
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
)
```

#### ToastConfig

```kotlin
data class ToastConfig(
    val title: String,
    val type: ToastType,
    val startIcon: PopIconConfig? = null,
    val showCloseIcon: Boolean = false,
    val endCta: EndCtaConfig? = null        // mutually exclusive with showCloseIcon
)
```

#### ToastType

```kotlin
enum class ToastType { MESSAGE, INFO, SUCCESS, ERROR }
```

| Type | Background gradient | Text/icon color |
|------|--------------------|-----------------| 
| `MESSAGE` | `GradientPreset.ButtonBrandPrimaryLargeHorizontal` (orange horizontal) | White |
| `INFO` | `GradientPreset.ButtonPrimaryInvertLarge` (white/light) | Black |
| `SUCCESS` | `GradientPreset.GradientGreen` | White |
| `ERROR` | `GradientPreset.GradientRed` | White |

#### Visual specs

- Full-width pill, 12dp horizontal + 12dp vertical padding
- Border: `BorderColor.PrimaryInvert.copy(alpha = 0.1f)` at 1dp
- Start icon: 40dp squircle container with `SurfaceColor.PrimaryTransparent50` BG
- Text: `PopTypography.headingMedium`, centered only when no icon + no close + no CTA
- Max text lines: 2
- End CTA uses `PopButtonV2` with `ButtonVariant.Secondary`, `ButtonSize.XSmall`

---

## 33. Components — Misc Visual Components

### PopDot

**File:** `ds_components/PopDot.kt`

```kotlin
@Composable
fun PopDot(
    modifier: Modifier = Modifier,
    color: PopDotColor = PopDotColor.Orange,
    size: PopDotSize = PopDotSize.Large,
    isActive: Boolean = true,
    contentDescription: String? = null,
)
```

Active dots are **25% larger** than their base size and have a glow effect.

| `color` | Gradient colors | Glow color (active) |
|---------|----------------|---------------------|
| Orange | Brand400 → Brand600 → Brand700 | `Brand600.copy(alpha=0.3f)` |
| White | White → Grey200 → Grey300 | `White.copy(alpha=0.3f)` |
| Green | Success400 → Success600 → Success700 | `Success600.copy(alpha=0.3f)` |
| Blue | `#4A90E2` → `#2E5CFF` → `#1E3A8A` | `#2E5CFF.copy(alpha=0.3f)` |

Gradient type: `PopGradient.Radial` with stops at 0.0f, 0.6f, 1.0f centered.  
Glow: `blurRadius = 4.dp`, `spreadRadius = 3.dp`.  
Inactive dots have `alpha = 0.5f` on gradient colors and no glow.

### FavIcon

**File:** `ds_components/FavIcon.kt`

```kotlin
@Composable
fun FavIcon(
    modifier: Modifier = Modifier,
    color: FavIconColor = FavIconColor.Orange,
    size: FavIconSize = FavIconSize.Large,
    isActive: Boolean = true,
    contentDescription: String? = null,
)
```

Renders the star/favourite icon as a static drawable resource. The resource ID is resolved via `FavIconResources.getFavIconResourceId(color, size, isActive)`. Icon is rendered via `Image` at `size.toIconDp()` (Large=22dp, Med=18dp).

### PopVerticalButton

**File:** `ds_components/PopVerticalButton.kt`  
Figma: node `4858-1047`

```kotlin
@Composable
fun PopVerticalButton(
    avatarType: AvatarType,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: VerticalButtonSize = VerticalButtonSize.Large,
    maxLines: Int = 1,
    isDisabled: Boolean = false,
    enableMarquee: Boolean = true,
    showCornerIndicator: Boolean? = false,  // Adds orange PopDot badge in top-right corner
)
```

#### VerticalButtonSize

```kotlin
enum class VerticalButtonSize(val width: Dp, val cornerRadius: Dp, val avatarSize: AvatarSize) {
    Large(width = 64.dp, cornerRadius = PopRadius.Medium, avatarSize = AvatarSize.XLarge),
    Medium(width = 56.dp, cornerRadius = PopRadius.Medium, avatarSize = AvatarSize.Large),
}
```

- Layout: `Column(horizontalAlignment = Center)` — avatar on top, 8dp spacer, label below
- Text style: Figtree Medium 12sp / 16sp lineHeight, centered
- When `maxLines = 1` + `enableMarquee = true`: auto-detects text overflow using `TextMeasurer` and applies `PopMarqueeText` with right gradient fade only when text actually overflows
- `showCornerIndicator = true`: renders `PopDot(Orange, Med, isActive=true)` offset at top-right corner of the avatar (+2dp x, -2dp y, 10dp size)

### PopVisualElement

**File:** `ds_components/PopVisualElement.kt`  
Generic renderer for `VisualElement` objects. A `VisualElement` can be:
- A drawable resource ID  
- A Coil image URL (loaded asynchronously)
- A Compose `Painter`

Used throughout the system to pass arbitrary icons/images as parameters without a specific type dependency.

```kotlin
VisualElement.buildFrom(
    iconName = Icons.CheckVerified,
    style = IconStyle.Filled,
    tintColor = TextColor.Success,
    heightDp = 16,
    widthDp = 16
)
```

### RotatingText / AlternatingSubtitleText

- **`RotatingText`**: Cycles through a `List<String>` with a slide+fade transition on each interval
- **`AlternatingSubtitleText`**: Cross-fades between two text values

### PopMarqueeText

Auto-scrolling text when content overflows. Parameters: speed (px/sec), delay (ms), loop count, gradient edge width, and independent left/right gradient control.

### OverlappingAvatarIcons

Horizontally stacked avatar icons with configurable overlap. When count exceeds a threshold, shows a "+N" indicator circle.

### EmptyStateComponent

**File:** `ds_components/EmptyStateComponent.kt`  
Full-screen or inline empty state with illustration, title, subtitle, and optional CTA button.

### RiveAnimationCompose

**File:** `ds_components/RiveAnimationCompose.kt`  
Wrapper for Rive vector animations (`.riv` files) inside Compose.

### SquircleShape (detailed)

**File:** `ds_components/SquircleShape.kt`

```kotlin
class SquircleShape(
    private val cornerRadius: Dp? = null,   // null = full squircle (50% of min dimension)
    private val smoothing: Float = 0.6f      // 0.0 = standard, 1.0 = full Figma squircle
) : Shape
```

Algorithm: Uses cubic Bézier curves with a `baseExtension = 1.8f` factor, meaning the curve starts much earlier into each edge than a standard rounded rectangle. This matches Figma's "Corner Smoothing" algorithm exactly.

Key formulas:
```
extensionFactor = 1 + smoothing × (1.8 - 1)   // 1.0 to 1.8
anchorLen       = cornerRadius × extensionFactor  // How far from corner the curve starts
cpOffset        = cornerRadius × (0.08 to 0.11)  // Control point distance from corner
```

Common usages in codebase:
```kotlin
SquircleShape()                                  // Full squircle (avatar backgrounds)
SquircleShape(smoothing = 1.0f)                  // Max Figma smoothing (badges, chips — pill-like)
SquircleShape(cornerRadius = PopRadius.Medium, smoothing = 1f)  // OTP boxes, bottom sheet (Squircle variant)
```

---

## 34. Scrim System

**Directory:** `ds_components/scrim/`

A full-featured background blur + color scrim system.

**Core files:**
- `ScrimCore.kt` — Core blur logic
- `ScrimEffect.kt` / `ScrimEffectNode.kt` — Compose modifier node
- `ScrimBlurRenderEffect.kt` — Android 12+ RenderEffect blur
- `ScrimBlurVisualEffect.kt` — Pre-12 software fallback
- `ScrimProgressive.kt` — Progressive (gradient) scrim
- `BlurModifierExtensions.kt` — `Modifier.blurModifier(...)` extension
- `ScrimUtils.kt` — Utility functions
- `ScrimDefaults.kt` / `ScrimBlurDefaults.kt` — Default values

**Key API:**
```kotlin
@ExperimentalScrimApi
fun Modifier.blurModifier(scrimState: ScrimState): Modifier

fun rememberScrimState(): ScrimState
```

The scrim system is used by `PopTopBar` for the collapsed-with-blur state.

---

## 35. Preview Files

**Directory:** `preview/`

Every major component has a corresponding `@Preview`-annotated composable in the `preview/` folder. These are:

| File | Previews |
|------|---------|
| `PopButtonPreviews.kt` | All button variants/states/sizes |
| `PopAvatarPreviews.kt` | People, Brand, Icon, Illustration, Image |
| `PopBadgePreviews.kt` | All badge types with/without bg/glow |
| `PopChipPreviews.kt` | Basic, WithClose, WithDropdown; active/disabled |
| `PopChipStackPreviews.kt` | Chip groups |
| `PopInputFieldPreviews.kt` | Input field variants |
| `PopOtpFieldPreviews.kt` | OTP field lengths and states |
| `PopKeyPadPreviews.kt` | Numeric keypad |
| `PopShimmerPreviews.kt` | Shimmer types |
| `PopShimmerBlocksPreviews.kt` | Shimmer block presets |
| `PopListItemPreviews.kt` | List items with all combinations |
| `PopTabLayoutPreviews.kt` | Tab configurations |
| `PopBottomBarPreviews.kt` | Bottom nav bar |
| `PopAccordionPreviews.kt` | Accordion states |
| `PopCarouselPreviews.kt` | Carousel |
| `PopHeroCarouselPreviews.kt` | Hero carousel |
| `PopOfferHighlightPreviews.kt` | Offer highlight |
| `PopOfferTogglePreviews.kt` | Offer toggle |
| `PopPillsPreviews.kt` | Pills |
| `PopSelectionControlsPreviews.kt` | Radio + Checkbox |
| `PopTitleBarPreviews.kt` | Title bar |
| `PopTopBarPreviews.kt` | Top bar (initial/collapsing/collapsed) |
| `PopTypographyPreviews.kt` | All text styles |
| `PopVerticalButtonPreviews.kt` | Vertical button |
| `PopEnterAmountHeaderPreviews.kt` | Amount entry header |
| `CardPreviews.kt` | Card components |
| `svg_icons.md` | SVG icon catalogue |

---

## Quick Reference: "What to use when"

| Task | Use |
|------|-----|
| Main background color | `SurfaceColor.Primary` (`#0D0D0D`) |
| Card/elevated surface | `SurfaceColor.Secondary` (`#1F1F1F`) |
| Dividers | `BorderColor.Tertiary` or `BorderColor.Secondary` |
| Primary body text | `TextColor.Primary` (`#E6E6E6`) |
| Secondary/supporting text | `TextColor.Secondary` (`#B3B3B3`) |
| Brand/accent text | `TextColor.Brand` (`#FF7533`) |
| Success text | `TextColor.Success` (`#3DC574`) |
| Warning text | `TextColor.Warning` (`#FCC93A`) |
| Error/destructive text | `TextColor.Destructive` (`#EE4D37`) |
| Main brand color | `PopColor.Brand.Brand600` (`#FF5200`) |
| Primary CTA button | `PopButtonV2` with `variant = BrandPrimary` |
| Secondary action | `PopButtonV2` with `variant = Secondary` |
| Destructive action | `PopButtonV2` with `state = Destructive` |
| Loading state | `state = Loading`, `buttonLoadingState = Default` |
| Person avatar | `AvatarType.People(name = "...")` |
| Brand/logo avatar | `AvatarType.Brand(imageModel = ...)` |
| Status badge | `PopBadge(label, type = BadgeType.Green/Orange/Red)` |
| Filter chip | `PopChip(config = PopChipConfig(text, mode = Toggleable))` |
| Bottom sheet | `PopBottomSheet(showSheet, config = PopBottomSheetConfig(...))` |
| Page title | `PopTypography.figtreeStyles.headingLarge` |
| Card title | `PopTypography.figtreeStyles.headingMedium` |
| Body text | `PopTypography.figtreeStyles.paragraphSmall` |
| Standard padding | `PopSpacing.Spacing16` (16dp) |
| Card corner radius | `PopRadius.Large` (16dp) |
| Button corner radius | Automatic (height/2, pill shape) |
| Standard border | `PopStroke.Default` (0.5dp) |
| Orange radial button gradient | `GradientPreset.ButtonBrandPrimaryLarge.gradient` |

---

*Documentation generated from source: POP Codebase (Kotlin/Jetpack Compose, Design System v2)*  
*Figma source: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS*
