# POP Design System — Spacing, Radius & Stroke Bridge Documentation

> **Purpose:** 1:1 mapping between POP Design System dimensional tokens (`spacing-radius-strokes-effects.md`) and their Kotlin/Jetpack Compose implementations (`PopSpacing.kt`).
> Use this file to translate any Figma spacing, radius, or stroke decision directly to production-ready code.

---

## Token Architecture

```
COMPONENT PROPERTY (padding, gap, border-radius, border-width)
    ↓  references
DESIGN TOKEN  (e.g. Spacing/16, Radius/12, Strokes/x2)
    ↓  maps to
KOTLIN VARIABLE  (e.g. PopSpacing.Spacing16, PopRadius.Medium, PopStroke.Default)
    ↓  raw value
dp VALUE  (e.g. 16.dp, 12.dp, 0.5.dp)
```

> **Rule:** Always reference the Kotlin token variable. Never hardcode raw `.dp` values in components.

---

## Section 1 — Spacing Token Mapping

| Design System File | Spacing Token | Value | Codebase File Name | Codebase Variable Name | Code Pattern |
|---|---|---|---|---|---|
| spacing-radius-strokes-effects.md | `Spacing/0` | `0px` | PopSpacing.kt | `PopSpacing.Spacing0` | `val Spacing0: Dp = 0.dp` |
| spacing-radius-strokes-effects.md | `Spacing/2` | `2px` | PopSpacing.kt | `PopSpacing.Spacing2` | `val Spacing2: Dp = 2.dp` |
| spacing-radius-strokes-effects.md | `Spacing/4` | `4px` | PopSpacing.kt | `PopSpacing.Spacing4` | `val Spacing4: Dp = 4.dp` |
| spacing-radius-strokes-effects.md | `Spacing/6` | `6px` | PopSpacing.kt | `PopSpacing.Spacing6` | `val Spacing6: Dp = 6.dp` |
| spacing-radius-strokes-effects.md | `Spacing/8` | `8px` | PopSpacing.kt | `PopSpacing.Spacing8` | `val Spacing8: Dp = 8.dp` |
| spacing-radius-strokes-effects.md | `Spacing/10` | `10px` | PopSpacing.kt | `PopSpacing.Spacing10` | `val Spacing10: Dp = 10.dp` |
| spacing-radius-strokes-effects.md | `Spacing/12` | `12px` | PopSpacing.kt | `PopSpacing.Spacing12` | `val Spacing12: Dp = 12.dp` |
| spacing-radius-strokes-effects.md | `Spacing/16` | `16px` | PopSpacing.kt | `PopSpacing.Spacing16` | `val Spacing16: Dp = 16.dp` |
| spacing-radius-strokes-effects.md | `Spacing/20` | `20px` | PopSpacing.kt | `PopSpacing.Spacing20` | `val Spacing20: Dp = 20.dp` |
| spacing-radius-strokes-effects.md | `Spacing/24` | `24px` | PopSpacing.kt | `PopSpacing.Spacing24` | `val Spacing24: Dp = 24.dp` |
| spacing-radius-strokes-effects.md | `Spacing/28` | `28px` | PopSpacing.kt | `PopSpacing.Spacing28` | `val Spacing28: Dp = 28.dp` |
| spacing-radius-strokes-effects.md | `Spacing/32` | `32px` | PopSpacing.kt | `PopSpacing.Spacing32` | `val Spacing32: Dp = 32.dp` |
| spacing-radius-strokes-effects.md | `Spacing/36` | `36px` | PopSpacing.kt | `PopSpacing.Spacing36` | `val Spacing36: Dp = 36.dp` |
| spacing-radius-strokes-effects.md | `Spacing/40` | `40px` | — | No codebase equivalent ⚠️ | Add `val Spacing40: Dp = 40.dp` to `PopSpacing` |
| spacing-radius-strokes-effects.md | `Spacing/44` | `44px` | PopSpacing.kt | `PopSpacing.Spacing44` | `val Spacing44: Dp = 44.dp` |
| spacing-radius-strokes-effects.md | `Spacing/48` | `48px` | PopSpacing.kt | `PopSpacing.Spacing48` | `val Spacing48: Dp = 48.dp` |
| spacing-radius-strokes-effects.md | `Spacing/56` | `56px` | PopSpacing.kt | `PopSpacing.Spacing56` | `val Spacing56: Dp = 56.dp` |
| spacing-radius-strokes-effects.md | `Spacing/64` | `64px` | — | No codebase equivalent ⚠️ | Add `val Spacing64: Dp = 64.dp` to `PopSpacing` |
| spacing-radius-strokes-effects.md | `Spacing/72` | `72px` | — | No codebase equivalent ⚠️ | Add `val Spacing72: Dp = 72.dp` to `PopSpacing` |
| spacing-radius-strokes-effects.md | `Spacing/92` | `92px` | — | No codebase equivalent ⚠️ | Add `val Spacing92: Dp = 92.dp` to `PopSpacing` |
| spacing-radius-strokes-effects.md | `Spacing/120` | `120px` | PopSpacing.kt | `PopSpacing.Spacing120` | `val Spacing120: Dp = 120.dp` |

**Compose usage — Spacing:**
```kotlin
import com.pop.components.theme.PopSpacing

// Padding
Box(modifier = Modifier.padding(PopSpacing.Spacing16))

// Horizontal + vertical padding
Box(modifier = Modifier.padding(
    horizontal = PopSpacing.Spacing16,
    vertical = PopSpacing.Spacing12
))

// Gap between items in a Column/Row
Column(verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing8)) { }

// Spacer
Spacer(modifier = Modifier.height(PopSpacing.Spacing24))
```

---

## Section 2 — Radius Token Mapping

| Design System File | Radius Token | Value | Usage Hint | Codebase File Name | Codebase Variable Name | Code Pattern |
|---|---|---|---|---|---|---|
| spacing-radius-strokes-effects.md | `Radius/0` | `0px` | Sharp / no rounding | PopSpacing.kt | `PopRadius.None` | `val None: Dp = 0.dp` |
| spacing-radius-strokes-effects.md | `Radius/4` | `4px` | Subtle rounding — tags, chips | PopSpacing.kt | `PopRadius.ExtraSmall` | `val ExtraSmall: Dp = 4.dp` |
| spacing-radius-strokes-effects.md | `Radius/6` | `6px` | Small elements | — | No codebase equivalent ⚠️ | Add `val XSmall: Dp = 6.dp` to `PopRadius` |
| spacing-radius-strokes-effects.md | `Radius/8` | `8px` | Inputs, buttons (small) | PopSpacing.kt | `PopRadius.Small` | `val Small: Dp = 8.dp` |
| spacing-radius-strokes-effects.md | `Radius/10` | `10px` | Buttons (medium) | — | No codebase equivalent ⚠️ | Add `val MediumSmall: Dp = 10.dp` to `PopRadius` |
| spacing-radius-strokes-effects.md | `Radius/12` | `12px` | Cards (small) | PopSpacing.kt | `PopRadius.Medium` | `val Medium: Dp = 12.dp` |
| spacing-radius-strokes-effects.md | `Radius/16` | `16px` | Cards (medium), modals | PopSpacing.kt | `PopRadius.Large` | `val Large: Dp = 16.dp` |
| spacing-radius-strokes-effects.md | `Radius/20` | `20px` | Cards (large) | PopSpacing.kt | `PopRadius.XLarge` | `val XLarge: Dp = 20.dp` |
| spacing-radius-strokes-effects.md | `Radius/24` | `24px` | Sheets, bottom drawers | — | No codebase equivalent ⚠️ | Add `val XXLarge: Dp = 24.dp` to `PopRadius` |
| spacing-radius-strokes-effects.md | `Radius/32` | `32px` | Large containers | — | No codebase equivalent ⚠️ | Add `val Jumbo: Dp = 32.dp` to `PopRadius` |
| spacing-radius-strokes-effects.md | `Radius/44` | `44px` | Pill-shaped tall elements | — | No codebase equivalent ⚠️ | Add `val PillTall: Dp = 44.dp` to `PopRadius` |
| spacing-radius-strokes-effects.md | `Radius/999` | `999px` | Full pill / fully rounded | PopSpacing.kt | `PopRadius.XLLarge` | `val XLLarge: Dp = 999.dp` |

**Compose usage — Radius:**
```kotlin
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopShapes

// Shape on a composable
Box(modifier = Modifier.clip(RoundedCornerShape(PopRadius.Medium)))

// Using PopShapes helpers
Button(shape = PopShapes.rounded(PopRadius.Medium)) { }

// Pill-shaped button
Button(shape = PopShapes.pill()) { }

// Bottom sheet — top corners only
Surface(shape = PopShapes.topRounded(PopRadius.Large)) { }

// Per-corner control
Surface(
    shape = PopShapes.rounded(
        topStart = PopRadius.Large,
        topEnd = PopRadius.Large,
        bottomStart = PopRadius.None,
        bottomEnd = PopRadius.None
    )
) { }

// Left / right rounded (e.g. segmented controls)
Box(modifier = Modifier.clip(PopShapes.leftRounded(PopRadius.Medium)))
Box(modifier = Modifier.clip(PopShapes.rightRounded(PopRadius.Medium)))
```

---

## Section 3 — Stroke Token Mapping

| Design System File | Stroke Token | Value | Usage Hint | Codebase File Name | Codebase Variable Name | Code Pattern |
|---|---|---|---|---|---|---|
| spacing-radius-strokes-effects.md | `Strokes/x` | `0.5px` | Hairline / subtle dividers | PopSpacing.kt | `PopStroke.Default` | `val Default: Dp = 0.5.dp` |
| spacing-radius-strokes-effects.md | `Strokes/x2` | `1px` | Default border weight | — | No codebase equivalent ⚠️ | Add `val Medium: Dp = 1.dp` to `PopStroke` |
| spacing-radius-strokes-effects.md | `Strokes/x4` | `2px` | Emphasis / focus rings | — | No codebase equivalent ⚠️ | Add `val Emphasis: Dp = 2.dp` to `PopStroke` |

**Compose usage — Stroke:**
```kotlin
import com.pop.components.theme.PopStroke
import com.pop.components.theme.BorderColor

// Standard border via popBorder extension
Box(modifier = Modifier.popBorder(color = BorderColor.Primary))

// Border with radius
Box(modifier = Modifier.popBorder(
    color = BorderColor.Brand,
    radius = PopRadius.Medium
))

// Pill border
Button(modifier = Modifier.popBorderPill(color = BorderColor.Primary)) { }

// Top-rounded border (bottom sheets)
Surface(modifier = Modifier.popBorderTopRounded(
    color = BorderColor.Secondary,
    radius = PopRadius.Large
)) { }

// Explicit width override (when Strokes/x2 or Strokes/x4 are needed)
Box(modifier = Modifier.border(
    width = 1.dp,       // Strokes/x2 — use PopStroke.Medium once added
    color = BorderColor.Brand,
    shape = RoundedCornerShape(PopRadius.Medium)
))
```

---

## Section 4 — Known Codebase Gaps

| Gap | Design Tokens Affected | Recommended Fix |
|---|---|---|
| Missing spacing values | `Spacing/40`, `Spacing/64`, `Spacing/72`, `Spacing/92` | Add `val Spacing40`, `Spacing64`, `Spacing72`, `Spacing92` to `object PopSpacing` |
| Missing radius values | `Radius/6`, `Radius/10`, `Radius/24`, `Radius/32`, `Radius/44` | Add `val XSmall = 6.dp`, `MediumSmall = 10.dp`, `XXLarge = 24.dp`, `Jumbo = 32.dp`, `PillTall = 44.dp` to `object PopRadius` |
| Missing stroke weights | `Strokes/x2` (1px), `Strokes/x4` (2px) | Add `val Medium: Dp = 1.dp` and `val Emphasis: Dp = 2.dp` to `object PopStroke` |
| No glow token objects | All glow effect styles | Glow effects have no dedicated Kotlin object — implement via `Modifier.shadow()` with `.copy(alpha = …)` per the patterns in Section 4.1 |
| No blur token objects | All scrim background blur styles | No Compose API equivalent for progressive blur — implement via custom `RenderEffect` |

---

*Generated from: `spacing-radius-strokes-effects.md` (POP Design System) + `PopSpacing.kt` (POP Codebase)*
*Codebase package: `com.pop.components.theme`*
*Codebase objects: `PopSpacing` · `PopRadius` · `PopStroke` · `PopShapes`*
