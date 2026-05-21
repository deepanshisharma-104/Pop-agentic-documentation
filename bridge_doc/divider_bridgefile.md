# Divider — Design-to-Code Bridge

**Design source:** `atoms2.md` — Horizontal Divider and Vertical Divider sections  
**Codebase sources:** `compose_components/PopDivider.kt` · `theme/PopColor.kt`

---

## A: Orientation & Style Enum Mapping

### A. Divider Orientation Mapping [atoms2.md → PopDivider.kt]

| Design System File | Design Orientation / Type | Codebase Enum Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| atoms2.md | Horizontal divider | `PopDividerOrientation.Horizontal` | PopDivider.kt | `PopDivider(orientation = PopDividerOrientation.Horizontal)` |
| atoms2.md | Vertical divider | `PopDividerOrientation.Vertical` | PopDivider.kt | `PopDivider(orientation = PopDividerOrientation.Vertical)` |

> **Default:** `PopDividerOrientation.Horizontal` — calling `PopDivider()` with no params produces a horizontal divider.

---

### B. Divider Style / Type Mapping [atoms2.md → PopDivider.kt]

| Design System File | Design Type | Codebase Enum Value | Codebase File | Notes | Code Pattern |
|---|---|---|---|---|---|
| atoms2.md | Continuous | `PopDividerStyle.Solid` | PopDivider.kt | Default solid line | `PopDivider(style = PopDividerStyle.Solid)` |
| atoms2.md | Dashed | `PopDividerStyle.Dashed` | PopDivider.kt | Canvas-based dash pattern. Design spec: 6dp dash / 6dp gap — override defaults | `PopDivider(style = PopDividerStyle.Dashed, dashLength = 6.dp, gapLength = 6.dp)` |
| atoms2.md | Stylised | ⚠️ No direct enum — custom implementation | PopDivider.kt | `PopDivider` accepts only a `Color`, not a `Brush`. Gradient cannot be passed to `color` param — requires a custom Canvas composable. See Section D-F for implementation note. | See Section F — Variant 7 |
| atoms2.md | Padded (vertical) | `PopDividerStyle.Solid` + padding wrapper | PopDivider.kt | No native padding param — wrap `PopDivider` in a `Box` with `padding(vertical = 4.dp)` and `height(24.dp)` | See Section G — Variants 1 & 2 |
| atoms2.md | Full height (vertical) | `PopDividerStyle.Solid` + `fillMaxHeight` | PopDivider.kt | Default vertical behaviour fills max height — no wrapper needed | See Section G — Variants 3 & 4 |

**Codebase color defaults:**

| Color param | Default value | Hex | Notes |
|---|---|---|---|
| `color` | `PopColors.Neutral.N5` | `#31312E` | Codebase default — not a standard design variant; always pass explicit `BorderColor.*` in production |

---

## B: Colour Token Mapping

### C. Horizontal Divider Colour Mapping [atoms2.md → PopColor.kt → PopDivider.kt]

| Design System File | Colour Variant | Alias Token | Resolves To | Opacity | Codebase Color Variable | Codebase File | Code Pattern |
|---|---|---|---|---|---|---|---|
| atoms2.md | Primary | `Border/Primary` | `Greys/Grey-800` → `#262626` | 100% | `BorderColor.Primary` | PopColor.kt | `PopDivider(color = BorderColor.Primary)` |
| atoms2.md | Secondary | `Border/Secondary` | `Greys/Grey-700` → `#333333` | 100% | `BorderColor.Secondary` | PopColor.kt | `PopDivider(color = BorderColor.Secondary)` |
| atoms2.md | Tertiary | `Border/Tertiary` | `Greys/Grey-600` → `#4D4D4D` | 100% | `BorderColor.Tertiary` | PopColor.kt | `PopDivider(color = BorderColor.Tertiary)` |
| atoms2.md | Blured | `Border/Primary-30%` | `Greys/Grey-900` → `#1F1F1F` at 30% | 30% | `SurfaceColor.Secondary.copy(alpha = 0.30f)` | PopColor.kt | `PopDivider(color = SurfaceColor.Secondary.copy(alpha = 0.30f), thickness = 1.dp)` |
| atoms2.md | Mixed | ⚠️ Not tokenised — `Surface-Secondary-Gradient/Small` paint style | Linear gradient: `#1F1F1F` → `#262626` | — | No alias token — gradient from `SurfaceColor.Secondary` to `BorderColor.Primary` | PopColor.kt | ⚠️ Cannot use `PopDivider()` — see note below |

> ⚠️ **Blured variant:** Applies `SurfaceColor.Secondary` (`#1F1F1F` = Grey-900) at 30% opacity. Also requires a **background blur effect** on the composable — use `Modifier.blur(...)` or a platform blur modifier on the parent surface. The divider itself is rendered at 30% opacity; the blur is an effect on the containing surface, not on the line itself.

> ⚠️ **Mixed / Stylised variant:** `PopDivider` accepts a `Color` parameter only — it does not accept a `Brush` or gradient. The `Surface-Secondary-Gradient/Small` paint style (`#1F1F1F → #262626`) cannot be passed directly. Use a custom Canvas-based composable for this variant (see Section F, Variant 7). Never reference raw hex values — use `SurfaceColor.Secondary` and `BorderColor.Primary` as gradient stops.

---

### D. Vertical Divider Colour Mapping [atoms2.md → PopColor.kt → PopDivider.kt]

| Design System File | Colour Variant | Alias Token | Resolves To | Codebase Color Variable | Codebase File | Code Pattern |
|---|---|---|---|---|---|---|
| atoms2.md | Tertiary | `Border/Tertiary` | `Greys/Grey-600` → `#4D4D4D` | `BorderColor.Tertiary` | PopColor.kt | `PopDivider(orientation = PopDividerOrientation.Vertical, color = BorderColor.Tertiary)` |
| atoms2.md | Secondary | `Border/Secondary` | `Greys/Grey-700` → `#333333` | `BorderColor.Secondary` | PopColor.kt | `PopDivider(orientation = PopDividerOrientation.Vertical, color = BorderColor.Secondary)` |

---

## C: Stroke / Thickness Token Mapping

### E. Stroke Weight Mapping [atoms2.md → PopSpacing.kt → PopDivider.kt]

| Design System File | Stroke Context | Design Stroke Weight | Codebase Token | Resolved Dp | Codebase File | Code Pattern |
|---|---|---|---|---|---|---|
| atoms2.md | Default — all non-Blured variants | 0.5px | `PopStroke.Default` | `0.5.dp` | PopSpacing.kt | `PopDivider(thickness = PopStroke.Default)` |
| atoms2.md | Blured variant only | 1px | `1.dp` (explicit override — no named token) | `1.dp` | PopDivider.kt | `PopDivider(thickness = 1.dp)` |

> **Rule:** Always use `PopStroke.Default` for 0.5dp thickness — never hardcode `0.5.dp`. The Blured variant is the only case where `1.dp` is specified explicitly, as no named `PopStroke.Thick` or equivalent token exists for 1dp dividers.

---

## D: Full Variant Mapping

### F. Horizontal Divider — All 7 Variants [atoms2.md → PopDivider.kt]

| Design Variant | `orientation` | `style` | `color` | `thickness` | `dashLength` / `gapLength` | Notes | Code Pattern |
|---|---|---|---|---|---|---|---|
| Type=Continuous, Colour=Primary | `Horizontal` | `Solid` | `BorderColor.Primary` | `PopStroke.Default` | — | Strongest separator — high contrast | `PopDivider(color = BorderColor.Primary)` |
| Type=Continuous, Colour=Secondary | `Horizontal` | `Solid` | `BorderColor.Secondary` | `PopStroke.Default` | — | Standard list row separator | `PopDivider(color = BorderColor.Secondary)` |
| Type=Continuous, Colour=Tertiary | `Horizontal` | `Solid` | `BorderColor.Tertiary` | `PopStroke.Default` | — | Subtle / nested content | `PopDivider(color = BorderColor.Tertiary)` |
| Type=Dashed, Colour=Secondary | `Horizontal` | `Dashed` | `BorderColor.Secondary` | `PopStroke.Default` | `6.dp` / `6.dp` | Override defaults (codebase defaults 8dp/4dp — design spec is 6dp/6dp) | `PopDivider(style = PopDividerStyle.Dashed, color = BorderColor.Secondary, dashLength = 6.dp, gapLength = 6.dp)` |
| Type=Dashed, Colour=Tertiary | `Horizontal` | `Dashed` | `BorderColor.Tertiary` | `PopStroke.Default` | `6.dp` / `6.dp` | Override defaults (codebase defaults 8dp/4dp — design spec is 6dp/6dp) | `PopDivider(style = PopDividerStyle.Dashed, color = BorderColor.Tertiary, dashLength = 6.dp, gapLength = 6.dp)` |
| Type=Continuous, Colour=Blured | `Horizontal` | `Solid` | `SurfaceColor.Secondary.copy(alpha = 0.30f)` | `1.dp` | — | 30% opacity + background blur on parent surface. Blur is not a param of `PopDivider` — apply `Modifier.blur(...)` on the containing surface. | `PopDivider(color = SurfaceColor.Secondary.copy(alpha = 0.30f), thickness = 1.dp)` |
| Type=Stylised, Colour=Mixed | — | — | — | — | — | ⚠️ `PopDivider` cannot render a gradient — use custom Canvas. See code block below. | See code block below |

**Variant 7 — Stylised / Mixed (custom implementation):**

```kotlin
// Stylised divider — Surface-Secondary-Gradient/Small paint style
// PopDivider() cannot accept a Brush — use Canvas directly
Canvas(
    modifier = Modifier
        .fillMaxWidth()
        .height(PopStroke.Default)
) {
    drawLine(
        brush = Brush.linearGradient(
            colors = listOf(SurfaceColor.Secondary, BorderColor.Primary),
            // #1F1F1F → #262626
        ),
        start = Offset(0f, size.height / 2f),
        end = Offset(size.width, size.height / 2f),
        strokeWidth = size.height
    )
}
```

> ⚠️ Use Stylised/Mixed **only on editorial or marketing surfaces** — never in functional UI such as lists, forms, or navigation.

---

### G. Vertical Divider — All 4 Variants [atoms2.md → PopDivider.kt]

> **Padding note:** `PopDivider` has no native `padding` parameter. For the `Padded` type (4px top & bottom), wrap the divider in a `Box` with `height(24.dp)` and `padding(vertical = 4.dp)`. The divider itself then renders at 16dp line height inside the 24dp container. For `Full height`, call `PopDivider` directly with `Modifier.height(24.dp)` — no padding wrapper needed.

| Design Variant | `orientation` | `style` | `color` | `thickness` | Height / Padding | Code Pattern |
|---|---|---|---|---|---|---|
| Type=Padded, Colour=Tertiary | `Vertical` | `Solid` | `BorderColor.Tertiary` | `PopStroke.Default` | Container: 24dp · Line: 16dp · Padding: 4dp top & bottom | See code block below |
| Type=Padded, Colour=Secondary | `Vertical` | `Solid` | `BorderColor.Secondary` | `PopStroke.Default` | Container: 24dp · Line: 16dp · Padding: 4dp top & bottom | See code block below |
| Type=Full height, Colour=Tertiary | `Vertical` | `Solid` | `BorderColor.Tertiary` | `PopStroke.Default` | Container: 24dp · Line: 24dp · No padding | `PopDivider(orientation = PopDividerOrientation.Vertical, color = BorderColor.Tertiary, modifier = Modifier.height(24.dp))` |
| Type=Full height, Colour=Secondary | `Vertical` | `Solid` | `BorderColor.Secondary` | `PopStroke.Default` | Container: 24dp · Line: 24dp · No padding | `PopDivider(orientation = PopDividerOrientation.Vertical, color = BorderColor.Secondary, modifier = Modifier.height(24.dp))` |

**Padded vertical divider — code pattern:**

```kotlin
// Type=Padded, Colour=Tertiary — 16px line / 24px container / 4px top+bottom padding
Box(
    modifier = Modifier.height(24.dp),
    contentAlignment = Alignment.Center
) {
    PopDivider(
        orientation = PopDividerOrientation.Vertical,
        color = BorderColor.Tertiary,
        modifier = Modifier
            .height(16.dp)
            .padding(vertical = 4.dp)
    )
}

// Type=Padded, Colour=Secondary — same pattern, different color
Box(
    modifier = Modifier.height(24.dp),
    contentAlignment = Alignment.Center
) {
    PopDivider(
        orientation = PopDividerOrientation.Vertical,
        color = BorderColor.Secondary,
        modifier = Modifier
            .height(16.dp)
            .padding(vertical = 4.dp)
    )
}
```

---

## Quick Reference — Token Chain

| Colour Variant | Alias Token | Parent Token | Hex | Codebase Variable |
|---|---|---|---|---|
| Primary | `Border/Primary` | `Greys/Grey-800` | `#262626` | `BorderColor.Primary` |
| Secondary | `Border/Secondary` | `Greys/Grey-700` | `#333333` | `BorderColor.Secondary` |
| Tertiary | `Border/Tertiary` | `Greys/Grey-600` | `#4D4D4D` | `BorderColor.Tertiary` |
| Blured | `Border/Primary-30%` | `Greys/Grey-900` | `#1F1F1F` @ 30% | `SurfaceColor.Secondary.copy(alpha = 0.30f)` |
| Mixed | ⚠️ Not tokenised | `Surface-Secondary-Gradient/Small` paint style | `#1F1F1F → #262626` | No alias — custom Canvas with `SurfaceColor.Secondary` + `BorderColor.Primary` |

> **Rule:** Never hardcode hex values — always use `BorderColor.*` or `SurfaceColor.*` from `PopColor.kt`. Never use raw `Divider()` from Material3 — always use `PopDivider()`.
