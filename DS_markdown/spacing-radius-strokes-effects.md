# POP Design System — Spacing, Radius, Strokes & Effects

> This document covers all dimensional and effect tokens from the POP design system:
> - **Spacing** — layout padding, gap, and margin values
> - **Radius** — corner radius values
> - **Strokes** — border/stroke width values
> - **Effect Styles** — glows, shadows, and background blurs

---

## 1. Spacing

> All spacing values are in **pixels**. Use these tokens for padding, gap, margin, and layout sizing.

| Token | Value |
|---|---|
| Spacing/0 | `0px` |
| Spacing/2 | `2px` |
| Spacing/4 | `4px` |
| Spacing/6 | `6px` |
| Spacing/8 | `8px` |
| Spacing/10 | `10px` |
| Spacing/12 | `12px` |
| Spacing/16 | `16px` |
| Spacing/20 | `20px` |
| Spacing/24 | `24px` |
| Spacing/28 | `28px` |
| Spacing/32 | `32px` |
| Spacing/36 | `36px` |
| Spacing/40 | `40px` |
| Spacing/44 | `44px` |
| Spacing/48 | `48px` |
| Spacing/56 | `56px` |
| Spacing/64 | `64px` |
| Spacing/72 | `72px` |
| Spacing/92 | `92px` |
| Spacing/120 | `120px` |

**Total: 21 spacing tokens** — Scale: 0 → 2 → 4 → 6 → 8 → 10 → 12 → 16 → 20 → 24 → 28 → 32 → 36 → 40 → 44 → 48 → 56 → 64 → 72 → 92 → 120

---

## 2. Radius

> All radius values are in **pixels**. Use these tokens for `border-radius` on all components.

| Token | Value | Usage Hint |
|---|---|---|
| Radius/0 | `0px` | Sharp / no rounding |
| Radius/4 | `4px` | Subtle rounding — tags, chips |
| Radius/6 | `6px` | Small elements |
| Radius/8 | `8px` | Inputs, buttons (small) |
| Radius/10 | `10px` | Buttons (medium) |
| Radius/12 | `12px` | Cards (small) |
| Radius/16 | `16px` | Cards (medium), modals |
| Radius/20 | `20px` | Cards (large) |
| Radius/24 | `24px` | Sheets, bottom drawers |
| Radius/32 | `32px` | Large containers |
| Radius/44 | `44px` | Pill-shaped tall elements |
| Radius/999 | `999px` | Full pill / fully rounded |

**Total: 12 radius tokens**

---

## 3. Strokes

> All stroke values are in **pixels**. Use these tokens for `border-width` / stroke weight on components.

| Token | Value | Usage Hint |
|---|---|---|
| Strokes/x | `0.5px` | Hairline / subtle dividers |
| Strokes/x2 | `1px` | Default border weight |
| Strokes/x4 | `2px` | Emphasis / focus rings |

**Total: 3 stroke tokens**

---

## 4. Effect Styles

### Glow — Primary Invert (White)

> Used on light-surface or invert-context elements.

| Style | Color | Opacity | Blur | Spread | X Position | Y Position |
|---|---|---|---|---|---|---|
| Glow-Primary-Invert/Large | `#FFFFFF` | 25% | 32px | 2px | 0 | 0 |
| Glow-Primary-Invert/Small | `#FFFFFF` | 42% | 9px | 0px | 0 | 0 |

> `Glow-Primary-Invert/Medium` is marked **not used** — avoid in new designs.

---

### Glow — Brand Primary (Orange)

> Used on brand-colored CTAs, active states, and highlighted elements.

| Style | Color | Opacity | Blur | Spread | X Position | Y Position |
|---|---|---|---|---|---|---|
| Glow-Brand Primary/Large | `#D94100` | 30% | 32px | 2px | 0 | 0 |
| Glow-Brand Primary/Small | `#FF500B` | 62% | 9px | 0px | 0 | 0 |

**Glow-Brand Primary/Large** has an additional **Noise** effect. Layer order: Noise is applied first (bottom), with the Drop Shadow layered on top of it:

| Noise Type | Noise Size | Density | Color | Opacity |
|---|---|---|---|---|
| Mono | 0.5 | 75% | `#000000` | 15% |

---

### Glow — Success (Green)

> Used on success states, confirmation badges, and positive indicators.

| Style | Color | Opacity | Blur | Spread | X Position | Y Position |
|---|---|---|---|---|---|---|
| Glow-Success/Large | `#21C321` | 25% | 32px | 2px | 0 | 0 |
| Glow-Success/Small | `#21C321` | 40% | 9px | 0px | 0 | 0 |

---

### Glow — Destructive (Red)

> Used on error states, delete actions, and destructive confirmations.

| Style | Color | Opacity | Blur | Spread | X Position | Y Position |
|---|---|---|---|---|---|---|
| Glow-Destructive/Large | `#EE4D37` | 30% | 32px | 2px | 0 | 0 |
| Glow-Destructive/Small | `#E22626` | 62% | 9px | 0px | 0 | 0 |

---

### Glow — Warning (Yellow)

> Used on warning states and cautionary indicators.

| Style | Color | Opacity | Blur | Spread | X Position | Y Position |
|---|---|---|---|---|---|---|
| Glow-Warning | `#FCC93A` | 50% | 10px | 0px | 0 | 0 |

---

### Glow — Blue

> Marked **not used** — avoid in new designs.

| Style | Color | Opacity | Blur | Spread | X Position | Y Position |
|---|---|---|---|---|---|---|
| Glow-Blue-Small *(not used)* | `#005EFF` | 92% | 9px | 0px | 0 | 0 |

---

### Background Blur — Scrim

> Used for frosted-glass / backdrop blur effects on overlays and modals.

| Style | Type | Blur Type | Start Value | End Value |
|---|---|---|---|---|
| Scrim-Bg Blur/Block 30% | Background Blur | Uniform | 30 *(Figma unit)* | — |
| Scrim-Bg Blur/Bottom Gradient (0-30%) | Background Blur | Progressive | 0 *(Figma unit)* | 30 *(Figma unit)* |
| Scrim-Bg Blur/Top Gradient (30-0%) | Background Blur | Progressive | 30 *(Figma unit)* | 0 *(Figma unit)* |

> ⚠️ All blur values are **Figma-specific units**, not CSS pixel values. Do not translate these directly to `blur(Npx)` in code — confirm the equivalent CSS values with the dev handoff.
> - **Uniform** — applies the same blur intensity across the entire layer.
> - **Progressive** — blur transitions gradually from the Start value to the End value across the layer.

---

### Border Shadow

> Used to simulate a subtle lit border effect on elevated surfaces. `Border-shadow` is composed of two stacked effects — a Drop Shadow and a Layer Blur.

**Effect 1 — Drop Shadow**

| Color | Opacity | X Position | Y Position | Blur | Spread |
|---|---|---|---|---|---|
| `#FFFFFF` | 100% | 0 | 2px | 1px | 0 |

**Effect 2 — Layer Blur**

| Blur |
|---|
| 1px |

---

## Quick Reference Summary

| Category | Token / Style Count |
|---|---|
| Spacing | 21 |
| Radius | 12 |
| Strokes | 3 |
| Glow Effects | 9 (+ 2 deprecated) |
| Background Blurs | 3 |
| Border Shadow | 1 |
| **Total** | **51** |

---

<div style="background-color:#e8f4fd;border-left:6px solid #0461FF;padding:16px 20px;border-radius:6px;margin-top:24px;">

## ℹ️ USAGE RULES — Spacing, Radius & Strokes

<div style="background-color:#cce4f7;padding:12px 16px;border-radius:4px;margin-bottom:12px;">

> 🔵 **Always reference tokens by name. Never hardcode raw pixel values in components.**

</div>

### Token Usage Chain

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   COMPONENT PROPERTY (padding, gap, border-radius, etc.)   │
│       ↓  references                                         │
│   SPACING / RADIUS / STROKE TOKEN                           │
│   e.g. Spacing/16, Radius/12, Strokes/x2                   │
│       ↓  raw value                                          │
│   PIXEL VALUE  e.g. 16px, 12px, 1px                        │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### ✅ Correct vs ❌ Incorrect Usage

| | Used | Example |
|---|---|---|
| ✅ **Correct** | Token name | Button padding → `Spacing/16` |
| ❌ **Incorrect** | Raw value | Button padding → `16px` |
| ✅ **Correct** | Token name | Card radius → `Radius/16` |
| ❌ **Incorrect** | Raw value | Card radius → `16px` |
| ✅ **Correct** | Token name | Border width → `Strokes/x2` |
| ❌ **Incorrect** | Raw value | Border width → `1px` |

### Effect Style Rules

- Use **Small** glow variants for inline elements, badges, and icons.
- Use **Large** glow variants for full cards, surfaces, and prominent CTAs.
- Do **not** use deprecated styles marked **"not used"** in new component designs.
- Background blur styles are only for overlay/modal contexts — not flat surfaces.

<div style="background-color:#ffd6d6;border-left:4px solid #cc0000;padding:10px 14px;border-radius:4px;margin-top:12px;">

❌ **Violation rule for Claude:** If a raw pixel value appears directly on a component property (padding, gap, radius, stroke) instead of a token name, flag it as a violation and replace it with the correct token from the tables above.

</div>

</div>
