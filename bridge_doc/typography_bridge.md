# POP Design System — Typography Bridge Documentation

> **Purpose:** 1:1 mapping between POP Design System typographic tokens (`type-scale.md`) and their Kotlin/Jetpack Compose implementations (`PopTypography.kt`).
> Use this file to translate any Figma type decision directly to production-ready code.

---

## Typeface Overview

| Typeface | Role | Codebase Font Family | Usage |
|---|---|---|---|
| **Figtree** | Functional UI typeface | `PopTypography.figtree` | All UI text — headings, labels, paragraphs, links, inputs |
| **Awesome Serif Italic** (Extra Tall) | Primary expressive variant | `PopTypography.awesomeSerifItalicExtraTall` | Display and heading expressive moments |
| **Awesome Serif Italic** (Tall) | Secondary expressive variant | `PopTypography.awesomeSerifItalicTall` | Smaller expressive labels |
| **Awesome Serif Italic** (Regular) | Lightest expressive variant | `PopTypography.awesomeSerifItalic` | SemiBold regular variant only |

---

## Section 1.1 — Font Weight Mapping

| Design Weight Name | Numeric Value | Kotlin `FontWeight` |
|---|---|---|
| Medium | 500 | `FontWeight.Medium` |
| SemiBold | 600 | `FontWeight.SemiBold` |
| Bold | 700 | `FontWeight.Bold` |
| ExtraBold | 800 | `FontWeight.ExtraBold` |

---

## Section 1.2 — Type Scale Token Mapping

> **How to read the Code Pattern column:**
> - `TypographySpecs.<Name>` — the raw spec object (fontSize, fontWeight, lineHeight, letterSpacing)
> - `PopTypography.figtreeStyles.<name>` — the resolved `TextStyle` ready for use in Compose
> - `PopTypography.getStyle(TextStyleName.<Name>)` — type-safe accessor (Figtree default)

---

### Part A — Figtree Type Scale

---

#### A.1 Display Styles

| Design System File | Design/Typescale Token | Font Size | Weight | Line Height | Letter Spacing | Codebase File Name | Codebase Variable Name | Code Pattern |
|---|---|---|---|---|---|---|---|---|
| type-scale.md | `Display/X Large-700` | 44px | Bold (700) | 52px | -2% | PopTypography.kt | `TypographySpecs.DisplayLarge` | `val DisplayLarge = StyleSpec(fontSize = 44, fontWeight = FontWeight.Bold, lineHeight = 52, letterSpacing = -2f)` |
| type-scale.md | `Display/X Large-800` | 44px | ExtraBold (800) | 52px | -2% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 44, fontWeight = FontWeight.ExtraBold, lineHeight = 52, letterSpacing = -2f)` as custom override via `FontFamilyStyleConfig` |
| type-scale.md | `Display/Large-700` | 36px | Bold (700) | 40px | -2% | PopTypography.kt | `TypographySpecs.DisplayMedium` | `val DisplayMedium = StyleSpec(fontSize = 36, fontWeight = FontWeight.Bold, lineHeight = 40, letterSpacing = -2f)` |
| type-scale.md | `Display/Large-800` | 36px | ExtraBold (800) | 40px | -2% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 36, fontWeight = FontWeight.ExtraBold, lineHeight = 40, letterSpacing = -2f)` as custom override |
| type-scale.md | `Display/Medium-700` | 32px | Bold (700) | 38px | -2% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 32, fontWeight = FontWeight.Bold, lineHeight = 38, letterSpacing = -2f)` as custom override |
| type-scale.md | `Display/Medium-800` | 32px | ExtraBold (800) | 38px | -2% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 32, fontWeight = FontWeight.ExtraBold, lineHeight = 38, letterSpacing = -2f)` as custom override |
| type-scale.md | `Display/Small-700` | 24px | Bold (700) | 30px | -2% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 24, fontWeight = FontWeight.Bold, lineHeight = 30, letterSpacing = -2f)` as custom override |
| type-scale.md | `Display/Small-800` | 24px | ExtraBold (800) | 30px | -2% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 24, fontWeight = FontWeight.ExtraBold, lineHeight = 30, letterSpacing = -2f)` as custom override |

**Compose usage — Display:**
```kotlin
// Display/X Large-700 (Figtree)
Text(
    text = "Hero Title",
    style = PopTypography.figtreeStyles.displayLarge
)

// Type-safe accessor
Text(
    text = "Hero Title",
    style = PopTypography.getStyle(TextStyleName.DisplayLarge)
)
```

---

#### A.2 Heading Styles

| Design System File | Design/Typescale Token | Font Size | Weight | Line Height | Letter Spacing | Codebase File Name | Codebase Variable Name | Code Pattern |
|---|---|---|---|---|---|---|---|---|
| type-scale.md | `Heading/Large-600` | 20px | SemiBold (600) | 26px | 0% | PopTypography.kt | `TypographySpecs.HeadingLarge` | `val HeadingLarge = StyleSpec(fontSize = 20, fontWeight = FontWeight.SemiBold, lineHeight = 26)` |
| type-scale.md | `Heading/Large-700` | 20px | Bold (700) | 26px | 0% | — | No codebase equivalent ⚠️ | Codebase `HeadingLarge` uses SemiBold (600), not Bold (700). Override via `FontFamilyStyleConfig(headingLarge = StyleSpec(fontSize = 20, fontWeight = FontWeight.Bold, lineHeight = 26))` |
| type-scale.md | `Heading/Medium-600` | 18px | SemiBold (600) | 24px | 0% | PopTypography.kt | `TypographySpecs.HeadingMedium` ⚠️ | `val HeadingMedium = StyleSpec(fontSize = 18, fontWeight = FontWeight.Medium, lineHeight = 24)` — **weight mismatch: codebase uses Medium (500), design specifies SemiBold (600)** |
| type-scale.md | `Heading/Small-500` | 16px | Medium (500) | 24px | 0% | PopTypography.kt | `TypographySpecs.HeadingSmall` | `val HeadingSmall = StyleSpec(fontSize = 16, fontWeight = FontWeight.Medium, lineHeight = 24)` |

> **Codebase-only style (no design token equivalent):**
> `TypographySpecs.HeadingXTall` = `StyleSpec(fontSize = 30, fontWeight = FontWeight.Normal, lineHeight = 40)` — used internally for the Awesome Serif Italic expressive heading scale.

**Compose usage — Heading:**
```kotlin
// Heading/Large-600 (Figtree)
Text(
    text = "Section Title",
    style = PopTypography.figtreeStyles.headingLarge
)

// Heading/Small-500 (Figtree)
Text(
    text = "Card Title",
    style = PopTypography.figtreeStyles.headingSmall
)
```

---

#### A.3 Label Styles

> **Naming note:** The codebase shifts label size names up by one tier relative to the design doc.
> Design `Label/Large` (18px) → Codebase `LabelXLarge`; Design `Label/Medium` (16px) → Codebase `LabelLarge`/`LabelMedium`.

| Design System File | Design/Typescale Token | Font Size | Weight | Line Height | Letter Spacing | Codebase File Name | Codebase Variable Name | Code Pattern |
|---|---|---|---|---|---|---|---|---|
| type-scale.md | `Label/Large-500` | 18px | Medium (500) | 24px | 0% | PopTypography.kt | `TypographySpecs.LabelXLarge` ⚠️ | `val LabelXLarge = StyleSpec(fontSize = 18, fontWeight = FontWeight.Medium, lineHeight = 26)` — **lineHeight mismatch: codebase 26sp vs design 24px** |
| type-scale.md | `Label/Large-600` | 18px | SemiBold (600) | 24px | 0% | — | No codebase equivalent ⚠️ | No 18px SemiBold label in codebase. Use `StyleSpec(fontSize = 18, fontWeight = FontWeight.SemiBold, lineHeight = 24)` as custom override |
| type-scale.md | `Label/Medium-700` | 16px | Bold (700) | 22px | 0% | PopTypography.kt | `TypographySpecs.LabelLarge` ⚠️ | `val LabelLarge = StyleSpec(fontSize = 16, fontWeight = FontWeight.Bold, lineHeight = 24)` — **lineHeight mismatch: codebase 24sp vs design 22px** |
| type-scale.md | `Label/Medium-600` | 16px | SemiBold (600) | 22px | 0% | — | No codebase equivalent ⚠️ | No 16px SemiBold label. Use `StyleSpec(fontSize = 16, fontWeight = FontWeight.SemiBold, lineHeight = 22)` as custom override |
| type-scale.md | `Label/Medium-500` | 16px | Medium (500) | 24px | 0% | PopTypography.kt | `TypographySpecs.LabelMedium` | `val LabelMedium = StyleSpec(fontSize = 16, fontWeight = FontWeight.Medium, lineHeight = 24)` |
| type-scale.md | `Label/Small-500` | 14px | Medium (500) | 20px | 0% | PopTypography.kt | `TypographySpecs.LabelSmall` | `val LabelSmall = StyleSpec(fontSize = 14, fontWeight = FontWeight.Medium, lineHeight = 20)` |
| type-scale.md | `Label/Small-600` | 14px | SemiBold (600) | 20px | 0% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 14, fontWeight = FontWeight.SemiBold, lineHeight = 20)` as custom override |
| type-scale.md | `Label/Small-700` | 14px | Bold (700) | 20px | 0% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 14, fontWeight = FontWeight.Bold, lineHeight = 20)` as custom override |
| type-scale.md | `Label/X Small-500` | 12px | Medium (500) | 16px | 0% | PopTypography.kt | `TypographySpecs.LabelXSmall` | `val LabelXSmall = StyleSpec(fontSize = 12, fontWeight = FontWeight.Medium, lineHeight = 16)` |
| type-scale.md | `Label/X Small-600` | 12px | SemiBold (600) | 16px | 0% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 12, fontWeight = FontWeight.SemiBold, lineHeight = 16)` as custom override |
| type-scale.md | `Label/X Small-700` | 12px | Bold (700) | 16px | 0% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 12, fontWeight = FontWeight.Bold, lineHeight = 16)` as custom override |
| type-scale.md | `Label/XX Small-500` | 10px | Medium (500) | 12px | 0% | PopTypography.kt | `TypographySpecs.LabelXXSmall` | `val LabelXXSmall = StyleSpec(fontSize = 10, fontWeight = FontWeight.Medium, lineHeight = 12)` |

**Compose usage — Label:**
```kotlin
// Label/Medium-500 (Figtree)
Text(
    text = "Form Label",
    style = PopTypography.figtreeStyles.labelMedium
)

// Label/X Small-500 (Figtree)
Text(
    text = "Fine print",
    style = PopTypography.figtreeStyles.labelXSmall
)

// Type-safe accessor
Text(
    text = "Caption",
    style = PopTypography.getStyle(TextStyleName.LabelSmall)
)
```

---

#### A.4 Paragraph Styles

| Design System File | Design/Typescale Token | Font Size | Weight | Line Height | Letter Spacing | Codebase File Name | Codebase Variable Name | Code Pattern |
|---|---|---|---|---|---|---|---|---|
| type-scale.md | `Paragraph/X Large-700` | 24px | Bold (700) | 32px | 0% | PopTypography.kt | `TypographySpecs.ParagraphLarge` | `val ParagraphLarge = StyleSpec(fontSize = 24, fontWeight = FontWeight.Bold, lineHeight = 32)` |
| type-scale.md | `Paragraph/Large-500` | 24px | Medium (500) | 32px | 0% | — | No codebase equivalent ⚠️ | No 24px Medium paragraph. Use `StyleSpec(fontSize = 24, fontWeight = FontWeight.Medium, lineHeight = 32)` as custom override |
| type-scale.md | `Paragraph/Medium-500` | 16px | Medium (500) | 24px | 0% | PopTypography.kt | `TypographySpecs.ParagraphMedium` | `val ParagraphMedium = StyleSpec(fontSize = 16, fontWeight = FontWeight.Medium, lineHeight = 24)` |
| type-scale.md | `Paragraph/Small-500` | 14px | Medium (500) | 22px | 0% | PopTypography.kt | `TypographySpecs.ParagraphSmall` | `val ParagraphSmall = StyleSpec(fontSize = 14, fontWeight = FontWeight.Medium, lineHeight = 22)` |
| type-scale.md | `Paragraph/X Small-400` | 12px | Regular (400) | 20px | 0% | PopTypography.kt | `TypographySpecs.ParagraphXSmall` | `val ParagraphXSmall = StyleSpec(fontSize = 12, fontWeight = FontWeight.Normal, lineHeight = 20)` |

**Compose usage — Paragraph:**
```kotlin
// Paragraph/Medium-500 (Figtree)
Text(
    text = "Body content here",
    style = PopTypography.figtreeStyles.paragraphMedium
)

// Paragraph/X Small-400 (fine print / Figtree)
Text(
    text = "Terms and conditions",
    style = PopTypography.figtreeStyles.paragraphXSmall
)
```

---

#### A.5 Link Styles

| Design System File | Design/Typescale Token | Font Size | Weight | Line Height | Letter Spacing | Codebase File Name | Codebase Variable Name | Code Pattern |
|---|---|---|---|---|---|---|---|---|
| type-scale.md | `Link/Small-500` | 14px | SemiBold (600) | 20px | 0% | PopTypography.kt | `TypographySpecs.LinkLarge` | `val LinkLarge = StyleSpec(fontSize = 14, fontWeight = FontWeight.SemiBold, lineHeight = 20)` |
| type-scale.md | `Link/X Small-500` | 12px | Medium (500) | 16px | 0% | PopTypography.kt | `TypographySpecs.LinkMedium` | `val LinkMedium = StyleSpec(fontSize = 12, fontWeight = FontWeight.Medium, lineHeight = 16)` |
| type-scale.md | `Link/Large-500` | 18px | SemiBold (600) | 24px | 0% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 18, fontWeight = FontWeight.SemiBold, lineHeight = 24)` as custom override |
| type-scale.md | `Link/Medium-600` | 16px | SemiBold (600) | 22px | 0% | — | No codebase equivalent ⚠️ | Use `StyleSpec(fontSize = 16, fontWeight = FontWeight.SemiBold, lineHeight = 22)` as custom override |

> **Naming note:** Design's `Link/Small` (14px) maps to codebase `LinkLarge`; design's `Link/X Small` (12px) maps to codebase `LinkMedium`. The codebase naming follows size-up convention.

**Compose usage — Link:**
```kotlin
// Link/Small-500 → LinkLarge (Figtree + underline decoration)
Text(
    text = "View all",
    style = PopTypography.figtreeStyles.linkLarge.copy(
        textDecoration = TextDecoration.Underline
    )
)

// Type-safe accessor
Text(
    text = "See details",
    style = PopTypography.getStyle(TextStyleName.LinkLarge).copy(
        textDecoration = TextDecoration.Underline
    )
)
```

---

### Part B — Awesome Serif Italic Type Scale

> Awesome Serif Italic is **always italic** (inherent to the typeface). Never apply italic as a separate modifier.
> The codebase exposes three font family variants:
> - `PopTypography.awesomeSerifItalicExtraTall` — for **Bold Extra Tall** (primary expressive weight, 20px+)
> - `PopTypography.awesomeSerifItalicTall` — for **SemiBold Tall** (secondary expressive weight, 16px and below)
> - `PopTypography.awesomeSerifItalic` — for standard Regular variant

#### B.1 Awesome Serif Italic — Main Type Scale

| Design System File | Design/Typescale Token | Font Size | Font Style | Line Height | Letter Spacing | Codebase File Name | Codebase Variable Name | Code Pattern |
|---|---|---|---|---|---|---|---|---|
| type-scale.md | `Display/X Large` | 44px | Bold Extra Tall | 52px | 0% | PopTypography.kt | `PopTypography.awesomeSerifItalicExtraTallStyles.displayLarge` | `Text(style = PopTypography.awesomeSerifItalicExtraTallStyles.displayLarge)` |
| type-scale.md | `Display/Large` | 36px | Bold Extra Tall | 42px | 0% | PopTypography.kt | `PopTypography.awesomeSerifItalicExtraTallStyles.displayMedium` ⚠️ | Closest match; **lineHeight mismatch: codebase 40sp vs design 42px**. `Text(style = PopTypography.awesomeSerifItalicExtraTallStyles.displayMedium)` |
| type-scale.md | `Display/Medium` | 32px | Bold Extra Tall | 38px | 0% | — | No 32px display spec in codebase ⚠️ | No `TypographySpecs` entry for 32px. Create via `FontFamilyStyleConfig(displayLarge = StyleSpec(fontSize = 32, fontWeight = FontWeight.Bold, lineHeight = 38))` |
| type-scale.md | `Display/Small` | 24px | Bold Extra Tall | 32px | 0% | PopTypography.kt | `PopTypography.awesomeSerifItalicExtraTallStyles.paragraphLarge` ⚠️ | Closest by spec (24sp Bold 32sp); category name differs. `Text(style = PopTypography.awesomeSerifItalicExtraTallStyles.paragraphLarge)` |
| type-scale.md | `Heading/Large` | 20px | Bold Extra Tall | 26px | 0% | PopTypography.kt | `PopTypography.awesomeSerifItalicExtraTallStyles.headingLarge` | `Text(style = PopTypography.awesomeSerifItalicExtraTallStyles.headingLarge)` |
| type-scale.md | `Heading/Small` | 16px | Semibold Regular | 24px | 2% | PopTypography.kt | `PopTypography.awesomeSerifItalicTallStyles.headingSmall` ⚠️ | Closest match; **letterSpacing not applied in StyleSpec** — add `.copy(letterSpacing = 0.32.sp)`. `Text(style = PopTypography.awesomeSerifItalicTallStyles.headingSmall.copy(letterSpacing = 0.32.sp))` |
| type-scale.md | `Label/Small` | 12px | Semibold Regular | 20px | 2% | PopTypography.kt | `PopTypography.awesomeSerifItalicTallStyles.labelXSmall` ⚠️ | **lineHeight mismatch: codebase 16sp vs design 20px**. Add `.copy(lineHeight = 20.sp, letterSpacing = 0.24.sp)`. `Text(style = PopTypography.awesomeSerifItalicTallStyles.labelXSmall.copy(lineHeight = 20.sp, letterSpacing = 0.24.sp))` |

#### B.2 Awesome Serif Italic — Additional Observed Styles

| Design System File | Usage Context | Font Size | Font Style | Line Height | Letter Spacing | Codebase File Name | Codebase Variable Name | Code Pattern |
|---|---|---|---|---|---|---|---|---|
| type-scale.md | Section label / intro tag | 16px | Bold Extra Tall | ~18.7px | 2% | PopTypography.kt | `PopTypography.awesomeSerifItalicExtraTallStyles.headingSmall` ⚠️ | Closest; adjust letter spacing: `.copy(letterSpacing = 0.32.sp)` |
| type-scale.md | Compact heading | 24px | Bold Extra Tall | AUTO | -1% | PopTypography.kt | `PopTypography.awesomeSerifItalicExtraTallStyles.paragraphLarge` ⚠️ | Add `.copy(letterSpacing = (-0.24).sp)` |
| type-scale.md | Specimen / editorial display | 28px | Bold Extra Tall | 150% | 0% | — | No 28px spec in codebase ⚠️ | Use `FontFamilyStyleConfig` override: `StyleSpec(fontSize = 28, fontWeight = FontWeight.Bold, lineHeight = 42)` |

---

## Section 2 — How to Apply a Style in Compose

### Method 1 — Direct font family access (Recommended)
```kotlin
import com.pop.components.theme.PopTypography

// Figtree (default UI typeface)
Text(text = "Hello", style = PopTypography.figtreeStyles.headingLarge)

// Awesome Serif Italic (expressive, extra tall variant)
Text(text = "Hello", style = PopTypography.awesomeSerifItalicExtraTallStyles.headingLarge)

// Awesome Serif Italic (tall variant — smaller sizes)
Text(text = "Hello", style = PopTypography.awesomeSerifItalicTallStyles.labelXSmall)
```

### Method 2 — Type-safe accessor (Recommended for dynamic selection)
```kotlin
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextStyleName

// Figtree (default)
val style = PopTypography.getStyle(TextStyleName.HeadingLarge)

// Specific font family
val style = PopTypography.getStyle(TextStyleName.HeadingLarge, PopTypography.figtree)
```

### Method 3 — Custom font family config (for missing design tokens)
```kotlin
import com.pop.components.theme.FontFamilyStyleConfig
import com.pop.components.theme.TypographySpecs

// Create a style set with a custom Display/Medium-700 override
val customStyles = PopTypography.createFontFamilyStyles(
    fontFamily = PopTypography.figtree,
    config = FontFamilyStyleConfig(
        displayLarge = TypographySpecs.StyleSpec(
            fontSize = 32,
            fontWeight = FontWeight.Bold,
            lineHeight = 38,
            letterSpacing = -2f
        )
    )
)
Text(text = "Custom Display", style = customStyles.displayLarge)
```

---

## Section 3 — Quick Reference: Design Token → Codebase Variable

| Design Token | `TypographySpecs` Variable | `FontFamilyStyles` Property | Match Quality |
|---|---|---|---|
| `Display/X Large-700` | `TypographySpecs.DisplayLarge` | `.displayLarge` | ✅ Exact |
| `Display/X Large-800` | — | — | ❌ Missing (no ExtraBold variant) |
| `Display/Large-700` | `TypographySpecs.DisplayMedium` | `.displayMedium` | ✅ Exact |
| `Display/Large-800` | — | — | ❌ Missing |
| `Display/Medium-700` | — | — | ❌ Missing |
| `Display/Medium-800` | — | — | ❌ Missing |
| `Display/Small-700` | — | — | ❌ Missing |
| `Display/Small-800` | — | — | ❌ Missing |
| `Heading/Large-600` | `TypographySpecs.HeadingLarge` | `.headingLarge` | ✅ Exact |
| `Heading/Large-700` | — | — | ❌ Missing (HeadingLarge is SemiBold, not Bold) |
| `Heading/Medium-600` | `TypographySpecs.HeadingMedium` | `.headingMedium` | ⚠️ Weight mismatch (code: Medium 500) |
| `Heading/Small-500` | `TypographySpecs.HeadingSmall` | `.headingSmall` | ✅ Exact |
| `Label/Large-500` | `TypographySpecs.LabelXLarge` | `.labelXLarge` | ⚠️ LineHeight mismatch (code: 26sp vs design: 24px) |
| `Label/Large-600` | — | — | ❌ Missing |
| `Label/Medium-700` | `TypographySpecs.LabelLarge` | `.labelLarge` | ⚠️ LineHeight mismatch (code: 24sp vs design: 22px) |
| `Label/Medium-600` | — | — | ❌ Missing |
| `Label/Medium-500` | `TypographySpecs.LabelMedium` | `.labelMedium` | ✅ Exact |
| `Label/Small-500` | `TypographySpecs.LabelSmall` | `.labelSmall` | ✅ Exact |
| `Label/Small-600` | — | — | ❌ Missing |
| `Label/Small-700` | — | — | ❌ Missing |
| `Label/X Small-500` | `TypographySpecs.LabelXSmall` | `.labelXSmall` | ✅ Exact |
| `Label/X Small-600` | — | — | ❌ Missing |
| `Label/X Small-700` | — | — | ❌ Missing |
| `Label/XX Small-500` | `TypographySpecs.LabelXXSmall` | `.labelXXSmall` | ✅ Exact |
| `Paragraph/X Large-700` | `TypographySpecs.ParagraphLarge` | `.paragraphLarge` | ✅ Exact |
| `Paragraph/Large-500` | — | — | ❌ Missing (no 24px Medium paragraph) |
| `Paragraph/Medium-500` | `TypographySpecs.ParagraphMedium` | `.paragraphMedium` | ✅ Exact |
| `Paragraph/Small-500` | `TypographySpecs.ParagraphSmall` | `.paragraphSmall` | ✅ Exact |
| `Paragraph/X Small-400` | `TypographySpecs.ParagraphXSmall` | `.paragraphXSmall` | ✅ Exact |
| `Link/Small-500` | `TypographySpecs.LinkLarge` | `.linkLarge` | ✅ Exact (naming shifted) |
| `Link/X Small-500` | `TypographySpecs.LinkMedium` | `.linkMedium` | ✅ Exact (naming shifted) |
| `Link/Large-500` | — | — | ❌ Missing |
| `Link/Medium-600` | — | — | ❌ Missing |

---


*Generated from: `type-scale.md` (POP Design System) + `PopTypography.kt` (POP Codebase)*
*Codebase package: `com.pop.components.theme`*
