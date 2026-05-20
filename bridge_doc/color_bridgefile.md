# POP Design System — Color Bridge Documentation

> **Purpose:** 1:1 mapping between POP Design System color tokens (`colors.md`) and their Kotlin/Jetpack Compose implementations (`PopColor.kt`).
> Use this file to translate any Figma color decision directly to production-ready code.

---

## Token Architecture

```
COMPONENT
    ↓  references
ALIAS TOKEN  (Color Tokens collection — e.g. Surface/Secondary, Text/Primary)
    ↓  resolves to
PARENT TOKEN  (Colors collection — e.g. Greys/Grey-900, Brand/Brand-600-main)
    ↓  raw value
HEX VALUE  (e.g. #1F1F1F)
    ↓  maps to
KOTLIN VARIABLE  (e.g. SurfaceColor.Secondary / PopColor.Grey.Grey900)
```

> **Rule:** Components must always reference **Alias/Semantic tokens** (`SurfaceColor`, `TextColor`, `BorderColor`, `IconColor`), never raw `PopColor.*` palette values directly.

---

## Section A — Parent Color Token Mapping

### A.1 Grey Palette

| Design System File | Design/Color Token | Hex | Codebase File Name | Codebase Color Variable Name | Code Pattern |
|---|---|---|---|---|---|
| colors.md | `Greys/Grey-1000` | `#0D0D0D` | PopColor.kt | `PopColor.Grey.Grey1000` | `Color = PopColor.Grey.Grey1000` |
| colors.md | `Greys/Grey-900` | `#1F1F1F` | PopColor.kt | `PopColor.Grey.Grey900` | `Color = PopColor.Grey.Grey900` |
| colors.md | `Greys/Grey-800` | `#262626` | PopColor.kt | `PopColor.Grey.Grey800` | `Color = PopColor.Grey.Grey800` |
| colors.md | `Greys/Grey-700` | `#333333` | PopColor.kt | `PopColor.Grey.Grey700` | `Color = PopColor.Grey.Grey700` |
| colors.md | `Greys/Grey-600` | `#4D4D4D` | PopColor.kt | `PopColor.Grey.Grey600` | `Color = PopColor.Grey.Grey600` |
| colors.md | `Greys/Grey-500` | `#666666` | PopColor.kt | `PopColor.Grey.Grey500` | `Color = PopColor.Grey.Grey500` |
| colors.md | `Greys/Grey-400` | `#808080` | PopColor.kt | `PopColor.Grey.Grey400` | `Color = PopColor.Grey.Grey400` |
| colors.md | `Greys/Grey-300` | `#B3B3B3` | PopColor.kt | `PopColor.Grey.Grey300` | `Color = PopColor.Grey.Grey300` |
| colors.md | `Greys/Grey-200` | `#E6E6E6` | PopColor.kt | `PopColor.Grey.Grey200` | `Color = PopColor.Grey.Grey200` |
| colors.md | `Greys/Grey-100` | `#FFFFFF` | PopColor.kt | `PopColor.Grey.Grey100` | `Color = PopColor.Grey.Grey100` |

**XML reference pattern:** `@color/grey_1000` … `@color/grey_100`

---

### A.2 Brand (Orange) Palette

| Design System File | Design/Color Token | Hex | Codebase File Name | Codebase Color Variable Name | Code Pattern |
|---|---|---|---|---|---|
| colors.md | `Brand/Brand-1000` | `#6B2200` | PopColor.kt | `PopColor.Brand.Brand1000` | `Color = PopColor.Brand.Brand1000` |
| colors.md | `Brand/Brand-900` | `#8C2D00` | PopColor.kt | `PopColor.Brand.Brand900` | `Color = PopColor.Brand.Brand900` |
| colors.md | `Brand/Brand-800` | `#B53A00` | PopColor.kt | `PopColor.Brand.Brand800` | `Color = PopColor.Brand.Brand800` |
| colors.md | `Brand/Brand-700` | `#E84B00` | PopColor.kt | `PopColor.Brand.Brand700` | `Color = PopColor.Brand.Brand700` |
| colors.md | `Brand/Brand-600-main` | `#FF5200` | PopColor.kt | `PopColor.Brand.Brand600` | `Color = PopColor.Brand.Brand600` |
| colors.md | `Brand/Brand-500` | `#FF7533` | PopColor.kt | `PopColor.Brand.Brand500` | `Color = PopColor.Brand.Brand500` |
| colors.md | `Brand/Brand-400` | `#FF8B54` | PopColor.kt | `PopColor.Brand.Brand400` | `Color = PopColor.Brand.Brand400` |
| colors.md | `Brand/Brand-300` | `#FFAF8A` | PopColor.kt | `PopColor.Brand.Brand300` | `Color = PopColor.Brand.Brand300` |
| colors.md | `Brand/Brand-200` | `#FFC9B0` | PopColor.kt | `PopColor.Brand.Brand200` | `Color = PopColor.Brand.Brand200` |
| colors.md | `Brand/Brand-100` | `#FFEEE6` | PopColor.kt | `PopColor.Brand.Brand100` | `Color = PopColor.Brand.Brand100` |

**XML reference pattern:** `@color/brand_600` (main), `@color/brand_100` … `@color/brand_1000`

---

### A.3 Success (Green) Palette

| Design System File | Design/Color Token | Hex | Codebase File Name | Codebase Color Variable Name | Code Pattern |
|---|---|---|---|---|---|
| colors.md | `Success/Success-1000` | `#054C22` | PopColor.kt | `PopColor.Success.Success1000` | `Color = PopColor.Success.Success1000` |
| colors.md | `Success/Success-900` | `#07642D` | PopColor.kt | `PopColor.Success.Success900` | `Color = PopColor.Success.Success900` |
| colors.md | `Success/Success-800` | `#09813A` | PopColor.kt | `PopColor.Success.Success800` | `Color = PopColor.Success.Success800` |
| colors.md | `Success/Success-700` | `#0CA64A` | PopColor.kt | `PopColor.Success.Success700` | `Color = PopColor.Success.Success700` |
| colors.md | `Success/Success-600` | `#0DB651` | PopColor.kt | `PopColor.Success.Success600` | `Color = PopColor.Success.Success600` |
| colors.md | `Success/Success-500` | `#3DC574` | PopColor.kt | `PopColor.Success.Success500` | `Color = PopColor.Success.Success500` |
| colors.md | `Success/Success-400` | `#5DCE8A` | PopColor.kt | `PopColor.Success.Success400` | `Color = PopColor.Success.Success400` |
| colors.md | `Success/Success-300` | `#90DDAF` | PopColor.kt | `PopColor.Success.Success300` | `Color = PopColor.Success.Success300` |
| colors.md | `Success/Success-200` | `#B4E8C9` | PopColor.kt | `PopColor.Success.Success200` | `Color = PopColor.Success.Success200` |
| colors.md | `Success/Success-100` | `#E7F8EE` | PopColor.kt | `PopColor.Success.Success100` | `Color = PopColor.Success.Success100` |

**XML reference pattern:** `@color/success_100` … `@color/success_1000`

---

### A.4 Destructive (Red) Palette

| Design System File | Design/Color Token | Hex | Codebase File Name | Codebase Color Variable Name | Code Pattern |
|---|---|---|---|---|---|
| colors.md | `Destructive/Destructive-1000` | `#642017` | PopColor.kt | `PopColor.Destructive.Destructive1000` | `Color = PopColor.Destructive.Destructive1000` |
| colors.md | `Destructive/Destructive-900` | `#832A1E` | PopColor.kt | `PopColor.Destructive.Destructive900` | `Color = PopColor.Destructive.Destructive900` |
| colors.md | `Destructive/Destructive-800` | `#A93727` | PopColor.kt | `PopColor.Destructive.Destructive800` | `Color = PopColor.Destructive.Destructive800` |
| colors.md | `Destructive/Destructive-700` | `#C33A28` | PopColor.kt | `PopColor.Destructive.Destructive700` | `Color = PopColor.Destructive.Destructive700` |
| colors.md | `Destructive/Destructive-600` | `#EE4D37` | PopColor.kt | `PopColor.Destructive.Destructive600` | `Color = PopColor.Destructive.Destructive600` |
| colors.md | `Destructive/Destructive-500-main` | `#F1715F` | PopColor.kt | `PopColor.Destructive.Destructive500` | `Color = PopColor.Destructive.Destructive500` |
| colors.md | `Destructive/Destructive-400` | `#F48879` | PopColor.kt | `PopColor.Destructive.Destructive400` | `Color = PopColor.Destructive.Destructive400` |
| colors.md | `Destructive/Destructive-300` | `#F7ADA3` | PopColor.kt | `PopColor.Destructive.Destructive300` | `Color = PopColor.Destructive.Destructive300` |
| colors.md | `Destructive/Destructive-200` | `#FAC8C1` | PopColor.kt | `PopColor.Destructive.Destructive200` | `Color = PopColor.Destructive.Destructive200` |
| colors.md | `Destructive/Destructive-100` | `#FDEDEB` | PopColor.kt | `PopColor.Destructive.Destructive100` | `Color = PopColor.Destructive.Destructive100` |

**XML reference pattern:** `@color/destructive_500` (main), `@color/destructive_100` … `@color/destructive_1000`

---

### A.5 Warning (Yellow/Amber) Palette

| Design System File | Design/Color Token | Hex | Codebase File Name | Codebase Color Variable Name | Code Pattern |
|---|---|---|---|---|---|
| colors.md | `Warning/Warning-1000` | `#694F04` | PopColor.kt | `PopColor.Warning.Warning1000` | `Color = PopColor.Warning.Warning1000` |
| colors.md | `Warning/Warning-900` | `#8A6705` | PopColor.kt | `PopColor.Warning.Warning900` | `Color = PopColor.Warning.Warning900` |
| colors.md | `Warning/Warning-800` | `#B28506` | PopColor.kt | `PopColor.Warning.Warning800` | `Color = PopColor.Warning.Warning800` |
| colors.md | `Warning/Warning-700` | `#E4AB08` | PopColor.kt | `PopColor.Warning.Warning700` | `Color = PopColor.Warning.Warning700` |
| colors.md | `Warning/Warning-600` | `#FBBC09` | PopColor.kt | `PopColor.Warning.Warning600` | `Color = PopColor.Warning.Warning600` |
| colors.md | `Warning/Warning-500` | `#FCC93A` | PopColor.kt | `PopColor.Warning.Warning500` | `Color = PopColor.Warning.Warning500` |
| colors.md | `Warning/Warning-400` | `#FCD25A` | PopColor.kt | `PopColor.Warning.Warning400` | `Color = PopColor.Warning.Warning400` |
| colors.md | `Warning/Warning-300` | `#FDE08E` | PopColor.kt | `PopColor.Warning.Warning300` | `Color = PopColor.Warning.Warning300` |
| colors.md | `Warning/Warning-200` | `#FEEAB3` | PopColor.kt | `PopColor.Warning.Warning200` | `Color = PopColor.Warning.Warning200` |
| colors.md | `Warning/Warning-100` | `#FFF8E6` | PopColor.kt | `PopColor.Warning.Warning100` | `Color = PopColor.Warning.Warning100` |

**XML reference pattern:** `@color/warning_100` … `@color/warning_1000`

---

### A.6 Black & White

| Design System File | Design/Color Token | Hex | Codebase File Name | Codebase Color Variable Name | Code Pattern |
|---|---|---|---|---|---|
| colors.md | `Black & White/White` | `#FFFFFF` | PopColor.kt | `PopColor.WhiteBlack.White` | `Color = PopColor.WhiteBlack.White` |
| colors.md | `Black & White/Black` | `#000000` | PopColor.kt | `PopColor.WhiteBlack.Black` | `Color = PopColor.WhiteBlack.Black` |

**XML reference pattern:** `@color/ds_white`, `@color/ds_black`

---

## Section B — Alias / Semantic Token Mapping

> These are the tokens that components **must** use. Each maps to a parent token and to a semantic Kotlin object.

### B.1 Surface Tokens

| Design System File | Design/Color Token | Alias → Parent Token | Resolved Hex | Codebase File Name | Codebase Color Variable Name | Code Pattern |
|---|---|---|---|---|---|---|
| colors.md | `Surface/Primary` | `Greys/Grey-1000` | `#0D0D0D` | PopColor.kt | `SurfaceColor.Primary` | `background = SurfaceColor.Primary` |
| colors.md | `Surface/Secondary` | `Greys/Grey-900` | `#1F1F1F` | PopColor.kt | `SurfaceColor.Secondary` | `background = SurfaceColor.Secondary` |
| colors.md | `Surface/Tertiary` | `Greys/Grey-600` | `#4D4D4D` | PopColor.kt | `SurfaceColor.Tertiary` | `background = SurfaceColor.Tertiary` |
| colors.md | `Surface/Primary-50%` | `Greys/Grey-1000 / 50%` | `#0D0D0D` @ 50% | PopColor.kt | `SurfaceColor.PrimaryTransparent50` | `background = SurfaceColor.PrimaryTransparent50` |
| colors.md | `Surface/Primary-70%` | `Greys/Grey-1000 / 70%` | `#0D0D0D` @ 70% | PopColor.kt | `SurfaceColor.PrimaryDisabled70` | `background = SurfaceColor.PrimaryDisabled70` |
| colors.md | `Surface/Primary-Disabled-70%` | `Greys/Grey-1000 / 70%` | `#0D0D0D` @ 70% | PopColor.kt | `SurfaceColor.PrimaryDisabled70` | `background = SurfaceColor.PrimaryDisabled70` |
| colors.md | `Surface/Secondary Disabled` | `Greys/Grey-800` | `#262626` | PopColor.kt | `SurfaceColor.SecondaryDisabled` | `background = SurfaceColor.SecondaryDisabled` |
| colors.md | `Surface/Secondary-60%` | `Greys/Grey-900 / 60%` | `#1F1F1F` @ 60% | PopColor.kt | `SurfaceColor.SecondaryTransparent60` | `background = SurfaceColor.SecondaryTransparent60` |
| colors.md | `Surface/Primary-Invert` | `Greys/Grey-100` | `#FFFFFF` | PopColor.kt | `SurfaceColor.PrimaryInvert` | `background = SurfaceColor.PrimaryInvert` |
| colors.md | `Surface/Success` | `Success/Success-900` | `#07642D` | PopColor.kt | `PopColor.Success.Success900` | `background = PopColor.Success.Success900` |
| colors.md | `Surface/Brand` | `Brand/Brand-500` | `#FF7533` | PopColor.kt | `PopColor.Brand.Brand500` | `background = PopColor.Brand.Brand500` |

---

### B.2 Text Tokens

| Design System File | Design/Color Token | Alias → Parent Token | Resolved Hex | Codebase File Name | Codebase Color Variable Name | Code Pattern |
|---|---|---|---|---|---|---|
| colors.md | `Text/Primary` | `Greys/Grey-200` | `#E6E6E6` | PopColor.kt | `TextColor.Primary` | `color = TextColor.Primary` |
| colors.md | `Text/Secondary` | `Greys/Grey-300` | `#B3B3B3` | PopColor.kt | `TextColor.Secondary` | `color = TextColor.Secondary` |
| colors.md | `Text/Secondary-Invert` | `Greys/Grey-500` | `#666666` | PopColor.kt | `TextColor.SecondaryInvert` | `color = TextColor.SecondaryInvert` |
| colors.md | `Text/Tertiary` | `Greys/Grey-400` | `#808080` | PopColor.kt | `TextColor.Tertiary` | `color = TextColor.Tertiary` |
| colors.md | `Text/Primary-Invert` | `Greys/Grey-1000` | `#0D0D0D` | PopColor.kt | `TextColor.PrimaryInvert` | `color = TextColor.PrimaryInvert` |
| colors.md | `Text/Primary-40%` | `Greys/Grey-200 / 40%` | `#E6E6E6` @ 40% | PopColor.kt | `TextColor.PrimaryTransparent40` | `color = TextColor.PrimaryTransparent40` |
| colors.md | `Text/Primary-80% (offers)` | `Greys/Grey-100 / 80%` | `#FFFFFF` @ 80% | PopColor.kt | `TextColor.PrimaryTransparent60` | `color = TextColor.PrimaryTransparent60` |
| colors.md | `Text/Primary-Disabled` | `Greys/Grey-600` | `#4D4D4D` | PopColor.kt | `TextColor.Disabled` | `color = TextColor.Disabled` |
| colors.md | `Text/Secondary-Disabled` | `Greys/Grey-700` | `#333333` | PopColor.kt | `TextColor.SecondaryDisabled` | `color = TextColor.SecondaryDisabled` |
| colors.md | `Text/Brand` | `Brand/Brand-500` | `#FF7533` | PopColor.kt | `TextColor.Brand` | `color = TextColor.Brand` |
| colors.md | `Text/Brand-Disabled` | `Brand/Brand-1000` | `#6B2200` | PopColor.kt | `TextColor.BrandDisabled` | `color = TextColor.BrandDisabled` |
| colors.md | `Text/Success` | `Success/Success-500` | `#3DC574` | PopColor.kt | `TextColor.Success` | `color = TextColor.Success` |
| colors.md | `Text/Success-Invert` | `Success/Success-800` | `#09813A` | PopColor.kt | `TextColor.SuccessInvert` | `color = TextColor.SuccessInvert` |
| colors.md | `Text/Warning` | `Warning/Warning-500` | `#FCC93A` | PopColor.kt | `TextColor.Warning` | `color = TextColor.Warning` |
| colors.md | `Text/Warning-Invert` | `Warning/Warning-900` | `#8A6705` | PopColor.kt | `TextColor.WarningInvert` | `color = TextColor.WarningInvert` |
| colors.md | `Text/Destructive` | `Destructive/Destructive-600` | `#EE4D37` | PopColor.kt | `TextColor.Destructive` | `color = TextColor.Destructive` |
| colors.md | `Text/Destructive-Invert` | `Destructive/Destructive-700` | `#C33A28` | PopColor.kt | `TextColor.DestructiveInvert` | `color = TextColor.DestructiveInvert` |

---

### B.3 Icon Tokens

| Design System File | Design/Color Token | Alias → Parent Token | Resolved Hex | Codebase File Name | Codebase Color Variable Name | Code Pattern |
|---|---|---|---|---|---|---|
| colors.md | `Icons/Primary` | `Greys/Grey-200` | `#E6E6E6` | PopColor.kt | `IconColor.Primary` | `tint = IconColor.Primary` |
| colors.md | `Icons/Primary-40%` | `Greys/Grey-200 / 40%` | `#E6E6E6` @ 40% | PopColor.kt | `IconColor.PrimaryTransparent40` | `tint = IconColor.PrimaryTransparent40` |
| colors.md | `Icons/Secondary` | `Greys/Grey-300` | `#B3B3B3` | PopColor.kt | `IconColor.Secondary` | `tint = IconColor.Secondary` |
| colors.md | `Icons/Secondary-Invert` | `Greys/Grey-500` | `#666666` | PopColor.kt | `TextColor.SecondaryInvert` | `tint = TextColor.SecondaryInvert` ⚠️ |
| colors.md | `Icons/Tertiary` | `Greys/Grey-400` | `#808080` | PopColor.kt | `IconColor.Tertiary` | `tint = IconColor.Tertiary` |
| colors.md | `Icons/Primary-Invert` | `Greys/Grey-1000` | `#0D0D0D` | PopColor.kt | `IconColor.PrimaryInvert` | `tint = IconColor.PrimaryInvert` |
| colors.md | `Icons/Primary-Disabled` | `Greys/Grey-600` | `#4D4D4D` | PopColor.kt | `IconColor.Disabled` | `tint = IconColor.Disabled` |
| colors.md | `Icons/Secondary-Disabled` | `Greys/Grey-800` | `#262626` | PopColor.kt | `IconColor.SecondaryDisabled` | `tint = IconColor.SecondaryDisabled` |
| colors.md | `Icons/Success` | `Success/Success-400` | `#5DCE8A` | PopColor.kt | `IconColor.Success` | `tint = IconColor.Success` |
| colors.md | `Icons/Success-Invert` | `Success/Success-700` | `#0CA64A` | PopColor.kt | `IconColor.SuccessInvert` | `tint = IconColor.SuccessInvert` |
| colors.md | `Icons/Warning` | `Warning/Warning-400` | `#FCD25A` | PopColor.kt | `IconColor.Warning` | `tint = IconColor.Warning` |
| colors.md | `Icons/Warning-Invert` | `Warning/Warning-700` | `#E4AB08` | PopColor.kt | `IconColor.WarningInvert` | `tint = IconColor.WarningInvert` |
| colors.md | `Icons/Destructive` | `Destructive/Destructive-600` | `#EE4D37` | PopColor.kt | `IconColor.Destructive` | `tint = IconColor.Destructive` |
| colors.md | `Icons/Destructive-Invert` | `Destructive/Destructive-700` | `#C33A28` | PopColor.kt | `IconColor.DestructiveFromTokens` ⚠️ | `tint = IconColor.DestructiveFromTokens` |
| colors.md | `Icons/Brand` | `Brand/Brand-500` | `#FF7533` | PopColor.kt | `IconColor.Brand` | `tint = IconColor.Brand` |
| colors.md | `Icons/Brand-Disabled` | `Brand/Brand-1000` | `#6B2200` | PopColor.kt | `IconColor.BrandDisabled` | `tint = IconColor.BrandDisabled` |

> ⚠️ **Note:** `Icons/Secondary-Invert` and `Icons/Destructive-Invert` do not have a dedicated `IconColor` variable matching those exact names in the current codebase. The closest value-matched variables are listed above. Flag for codebase alignment.

---

### B.4 Border Tokens

| Design System File | Design/Color Token | Alias → Parent Token | Resolved Hex | Codebase File Name | Codebase Color Variable Name | Code Pattern |
|---|---|---|---|---|---|---|
| colors.md | `Border/Primary` | `Greys/Grey-800` | `#262626` | PopColor.kt | `BorderColor.Primary` | `border(width = 1.dp, color = BorderColor.Primary)` |
| colors.md | `Border/Secondary` | `Greys/Grey-700` | `#333333` | PopColor.kt | `BorderColor.Secondary` | `border(width = 1.dp, color = BorderColor.Secondary)` |
| colors.md | `Border/Tertiary` | `Greys/Grey-600` | `#4D4D4D` | PopColor.kt | `BorderColor.Tertiary` | `border(width = 1.dp, color = BorderColor.Tertiary)` |
| colors.md | `Border/Primary-Invert` | `Greys/Grey-200` | `#E6E6E6` | PopColor.kt | `BorderColor.PrimaryInvert` | `border(width = 1.dp, color = BorderColor.PrimaryInvert)` |
| colors.md | `Border/Destructive` | `Destructive/Destructive-600` | `#EE4D37` | PopColor.kt | `BorderColor.Destructive` | `border(width = 1.dp, color = BorderColor.Destructive)` |
| colors.md | `Border/Warning` | `Warning/Warning-500` | `#FCC93A` | PopColor.kt | `BorderColor.Warning` | `border(width = 1.dp, color = BorderColor.Warning)` |
| colors.md | `Border/Success` | `Success/Success-500` | `#3DC574` | PopColor.kt | `BorderColor.Success` | `border(width = 1.dp, color = BorderColor.Success)` |
| colors.md | `Border/Success-Invert` | `Success/Success-300` | `#90DDAF` | PopColor.kt | `BorderColor.SuccessInvert` | `border(width = 1.dp, color = BorderColor.SuccessInvert)` |
| colors.md | `Border/Primary-Invert-10%` | `Greys/Grey-200 / 20%` | `#E6E6E6` @ 20% | PopColor.kt | `BorderColor.PrimaryInvertTransparent10` | `border(width = 1.dp, color = BorderColor.PrimaryInvertTransparent10)` |
| colors.md | `Border/Primary-30%` | `Greys/Grey-900 / 30%` | `#1F1F1F` @ 30% | PopColor.kt | `BorderColor.PrimaryTransparent30` | `border(width = 1.dp, color = BorderColor.PrimaryTransparent30)` |
| colors.md | `Border/Brand-40%` | `Brand/Brand-600-main / 40%` | `#FF5200` @ 40% | PopColor.kt | `BorderColor.BrandTransparent40` | `border(width = 1.dp, color = BorderColor.BrandTransparent40)` |
| colors.md | `Border/Tertiary-40%` | `Greys/Grey-600 / 40%` | `#4D4D4D` @ 40% | PopColor.kt | `BorderColor.TertiaryTransparent40` | `border(width = 1.dp, color = BorderColor.TertiaryTransparent40)` |
| colors.md | `Border/Brand` | `Brand/Brand-600-main` | `#FF5200` | PopColor.kt | `BorderColor.Brand` | `border(width = 1.dp, color = BorderColor.Brand)` |

---

## Section C — Gradient Token Mapping

> Gradients are implemented via the `GradientPreset` enum in `PopGradient.kt`. Each entry wraps a `PopGradient.Linear` or `PopGradient.Radial` instance. Apply them using `.popBackground(gradient = GradientPreset.<Name>.gradient)`.


## Section E — Quick Reference: Semantic Token → Kotlin Object

| Token Group | Kotlin Object | Import |
|---|---|---|
| Surface | `SurfaceColor` | `com.pop.components.theme.SurfaceColor` |
| Text | `TextColor` | `com.pop.components.theme.TextColor` |
| Icons | `IconColor` | `com.pop.components.theme.IconColor` |
| Border | `BorderColor` | `com.pop.components.theme.BorderColor` |
| Grey palette | `PopColor.Grey` | `com.pop.components.theme.PopColor` |
| Brand palette | `PopColor.Brand` | `com.pop.components.theme.PopColor` |
| Success palette | `PopColor.Success` | `com.pop.components.theme.PopColor` |
| Destructive palette | `PopColor.Destructive` | `com.pop.components.theme.PopColor` |
| Warning palette | `PopColor.Warning` | `com.pop.components.theme.PopColor` |
| Black & White | `PopColor.WhiteBlack` | `com.pop.components.theme.PopColor` |
| Overflow | `OverflowColor` | `com.pop.components.theme.OverflowColor` |
| Avatar | `AvatarColor` | `com.pop.components.theme.AvatarColor` |

---

## Section F — Usage Rules & Violation Guide

### Correct vs. Incorrect

| Usage | Token | Example |
|---|---|---|
| ✅ Correct | Alias/Semantic token | `background = SurfaceColor.Secondary` |
| ❌ Incorrect | Parent palette token directly | `background = PopColor.Grey.Grey900` |
| ❌ Incorrect | Raw hex value | `background = Color(0xFF1F1F1F)` |

### Token Violation Detection

If a raw hex value or `PopColor.*` parent palette token appears directly on a component, it is a violation. Replace it with the corresponding alias token using the tables above.

**Example fix:**

```kotlin
// ❌ Violation — raw parent token used on component
Box(modifier = Modifier.background(PopColor.Grey.Grey900))

// ✅ Correct — semantic alias token
Box(modifier = Modifier.background(SurfaceColor.Secondary))
```

---

## Section G — Known Codebase Gaps

| Design Token | Issue |
|---|---|
| `Icons/Secondary-Invert` | No dedicated `IconColor.SecondaryInvert` in codebase; currently resolved via `TextColor.SecondaryInvert` (same hex `#666666`) |
| `Icons/Destructive-Invert` | No `IconColor.DestructiveInvert`; closest match is `IconColor.DestructiveFromTokens` (`#E63D3D`, slightly different from design `#C33A28`) |
| `Surface/Success` | No `SurfaceColor.Success` solid; currently must reference `PopColor.Success.Success900` directly |
| `Surface/Brand` | No `SurfaceColor.Brand` solid; currently must reference `PopColor.Brand.Brand500` directly |

---

*Generated from: `colors.md` (POP Design System) + `PopColor.kt` + `Theme.kt` (POP Codebase)*
*Codebase package: `com.pop.components.theme`*
