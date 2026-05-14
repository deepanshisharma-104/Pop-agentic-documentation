# POP Design System — Color Documentation

> This document covers all color tokens from the POP design system:
> - **Parent Tokens** — raw palette values from the `Colors` variable collection
> - **Alias Tokens** — semantic tokens from the `Color Tokens` variable collection
> - **Gradient Styles** — paint styles from `Color Styles`

---

## 1. Parent Tokens — `Colors` Collection

### Greys

| Token | Hex |
|---|---|
| Greys/Grey-1000 | `#0D0D0D` |
| Greys/Grey-900 | `#1F1F1F` |
| Greys/Grey-800 | `#262626` |
| Greys/Grey-700 | `#333333` |
| Greys/Grey-600 | `#4D4D4D` |
| Greys/Grey-500 | `#666666` |
| Greys/Grey-400 | `#808080` |
| Greys/Grey-300 | `#B3B3B3` |
| Greys/Grey-200 | `#E6E6E6` |
| Greys/Grey-100 | `#FFFFFF` |

### Brand

| Token | Hex |
|---|---|
| Brand/Brand-1000 | `#6B2200` |
| Brand/Brand-900 | `#8C2D00` |
| Brand/Brand-800 | `#B53A00` |
| Brand/Brand-700 | `#E84B00` |
| Brand/Brand-600-main | `#FF5200` |
| Brand/Brand-500 | `#FF7533` |
| Brand/Brand-400 | `#FF8B54` |
| Brand/Brand-300 | `#FFAF8A` |
| Brand/Brand-200 | `#FFC9B0` |
| Brand/Brand-100 | `#FFEEE6` |

### Success

| Token | Hex |
|---|---|
| Success/Success-1000 | `#054C22` |
| Success/Success-900 | `#07642D` |
| Success/Success-800 | `#09813A` |
| Success/Success-700 | `#0CA64A` |
| Success/Success-600 | `#0DB651` |
| Success/Success-500 | `#3DC574` |
| Success/Success-400 | `#5DCE8A` |
| Success/Success-300 | `#90DDAF` |
| Success/Success-200 | `#B4E8C9` |
| Success/Success-100 | `#E7F8EE` |

### Destructive

| Token | Hex |
|---|---|
| Destructive/Destructive-1000 | `#642017` |
| Destructive/Destructive-900 | `#832A1E` |
| Destructive/Destructive-800 | `#A93727` |
| Destructive/Destructive-700 | `#C33A28` |
| Destructive/Destructive-600 | `#EE4D37` |
| Destructive/Destructive-500-main | `#F1715F` |
| Destructive/Destructive-400 | `#F48879` |
| Destructive/Destructive-300 | `#F7ADA3` |
| Destructive/Destructive-200 | `#FAC8C1` |
| Destructive/Destructive-100 | `#FDEDEB` |

### Warning

| Token | Hex |
|---|---|
| Warning/Warning-1000 | `#694F04` |
| Warning/Warning-900 | `#8A6705` |
| Warning/Warning-800 | `#B28506` |
| Warning/Warning-700 | `#E4AB08` |
| Warning/Warning-600 | `#FBBC09` |
| Warning/Warning-500 | `#FCC93A` |
| Warning/Warning-400 | `#FCD25A` |
| Warning/Warning-300 | `#FDE08E` |
| Warning/Warning-200 | `#FEEAB3` |
| Warning/Warning-100 | `#FFF8E6` |

### Black & White

| Token | Hex |
|---|---|
| Black & White/White | `#FFFFFF` |
| Black & White/Black | `#000000` |

---

## 2. Alias Tokens — `Color Tokens` Collection

> Alias tokens reference parent tokens from the `Colors` collection. Where no alias exists, a direct RGBA value is listed.

### Surface

| Token | Alias → Parent Token | Direct Value |
|---|---|---|
| Surface/Primary | Greys/Grey-1000 | — |
| Surface/Secondary | Greys/Grey-900 | — |
| Surface/Tertiary | Greys/Grey-600 | — |
| Surface/Primary-50% |  Greys/Grey-1000 / 50%` |
| Surface/Primary-70% | Greys/Grey-1000 / 70%` |
| Surface/Primary-Disabled-70% | Greys/Grey-1000 / 70%` |
| Surface/Secondary Disabled | Greys/Grey-800 | — |
| Surface/Secondary-60% | Greys/Grey-900 / 60%` |
| Surface/Primary-Invert | Greys/Grey-100 | — |
| Surface/Success | Success/Sucess-900 | — |
| Surface/Brand | Brand/Brand-500 | — |

### Text

| Token | Alias → Parent Token | Direct Value |
|---|---|---|
| Text/Primary | Greys/Grey-200 | — |
| Text/Secondary | Greys/Grey-300 | — |
| Text/Secondary-Invert | Greys/Grey-500 | — |
| Text/Tertiary | Greys/Grey-400 | — |
| Text/Primary-Invert | Greys/Grey-1000 | — |
| Text/Primary-40% | Greys/Grey-200 / 40%` |
| Text/Primary-80% (offers) | Greys/Grey-100 / 80%` |
| Text/Primary-Disabled | Greys/Grey-600 | — |
| Text/Secondary-Disabled | Greys/Grey-700 | — |
| Text/Brand | Brand/Brand-500 | — |
| Text/Brand-Disabled | Brand/Brand-1000 | — |
| Text/Success | Success/Sucess-500 | — |
| Text/Success-Invert | Success/Sucess-800 | — |
| Text/Warning | Warning/Warning-500 | — |
| Text/Warning-Invert | Warning/Warning-900 | — |
| Text/Destructive | Destructive/Destructive-600 | — |
| Text/Destructive-Invert | Destructive/Destructive-700 | — |

### Icons

| Token | Alias → Parent Token | Direct Value |
|---|---|---|
| Icons/Primary | Greys/Grey-200 | — |
| Icons/Primary-40% | Greys/Grey-200 / 40%` |
| Icons/Secondary | Greys/Grey-300 | — |
| Icons/Secondary-Invert | Greys/Grey-500 | — |
| Icons/Tertiary | Greys/Grey-400 | — |
| Icons/Primary-Invert | Greys/Grey-1000 | — |
| Icons/Primary-Disabled | Greys/Grey-600 | — |
| Icons/Secondary-Disabled | Greys/Grey-800 | — |
| Icons/Success | Success/Sucess-400 | — |
| Icons/Success-Invert | Success/Sucess-700 | — |
| Icons/Warning | Warning/Warning-400 | — |
| Icons/Warning-Invert | Warning/Warning-700 | — |
| Icons/Destructive | Destructive/Destructive-600 | — |
| Icons/Destructive-Invert | Destructive/Destructive-700 | — |
| Icons/Brand | Brand/Brand-500 | — |
| Icons/Brand-Disabled | Brand/Brand-1000 | — |

### Border

| Token | Alias → Parent Token | Direct Value |
|---|---|---|
| Border/Primary | Greys/Grey-800 | — |
| Border/Secondary | Greys/Grey-700 | — |
| Border/Tertiary | Greys/Grey-600 | — |
| Border/Primary-Invert | Greys/Grey-200 | — |
| Border/Destructive | Destructive/Destructive-600 | — |
| Border/Warning | Warning/Warning-500 | — |
| Border/Success | Success/Sucess-500 | — |
| Border/Success-Invert | Success/Sucess-300 | — |
| Border/Primary-Invert-10% | Greys/Grey-200 / 20%` |
| Border/Primary-30% | Greys/Grey-900 / 30%` |
| Border/Brand-40% | Brand/Brand-600-main / 40%` |
| Border/Tertiary-40% | Greys/Grey-600 / 40%` |
| Border/Brand | Brand/Brand-600-main | — |

---

## 3. Color Styles — Gradients & Paints

# GLOBSAL_RULE : TOKEN | GRADIENT_TYPR | `HEX_CODE1` STOPING POINT% -> `HEX_CODE2` STOPING POINT%....

### Surface — Primary (Dark)

| Style | Type | Gradient Stops |
|---|---|---|
| Surface-Primary-Gradient/Large | Radial | `#1D1D1D` 0% → `#121212` 100% |
| Surface-Primary-Gradient/Medium | Radial | `#303030` 0% → `#0D0D0D` 100% |

### Surface — Primary Invert (Light)

| Style | Type | Gradient Stops |
|---|---|---|
| Surface-Primary Invert-Gradient/Large | Linear | `#FFFFFF` 0% → `#999999` 100% |
| Surface-Primary Invert-Gradient/Medium | Radial | `#EBEBEB` 37% → `#EBEBEB` 46% → `#DADADA` 55% → `#9B9B9B` 80% |
| Surface-Primary Invert-Gradient/Small | Radial | `#FFFFFF` 0% → `#DFDFDF` 30% → `#B7B7B7` 53% → `#999999` 73% → `#7B7B7B` 100% |

### Surface — Secondary

| Style | Type | Gradient Stops |
|---|---|---|
| Surface-Secondary-Gradient/Large | Radial | `#464646` 17% → `#1F1F1F` 93% |
| Surface-Secondary-Gradient/Medium | Radial | `#323232` 0% → `#1C1C1C` 46% → `#0D0D0D` 100% |
| Surface-Secondary-Gradient/Small | Linear | `#1F1F1F` 0% → `#262626` 100% |
| Surface-Secondary-Gradient/Slider button | Linear | `#0E0E0E` 0% → `#222222` 100% |

### Surface — Brand

| Style | Type | Gradient Stops |
|---|---|---|
| Surface-Brand Primary-Gradient/Large | Radial | `#FF9858` 4% → `#FF5200` 28% → `#CD3401` 55% → `#4B0000` 100% |
| Surface-Brand Primary-Gradient/Medium | Linear | `#B31E00` 0% → `#E54D00` 36% → `#E54D00` 58% → `#AF1700` 100% |
| Surface-Brand Primary-Gradient/Small | Radial | `#FCC7B2` 0% → `#F7AD8F` 20% → `#FF672B` 46% → `#D14009` 73% → `#B13407` 100% |

### Surface — Success

| Style | Type | Gradient Stops |
|---|---|---|
| Surface-Success-Gradient/Large | Radial | `#26D526` 17% → `#059505` 51% → `#015001` 100% |
| Surface-Success-Gradient/Small | Radial | `#58FF58` 0% → `#21C321` 30% → `#059505` 53% → `#007700` 73% → `#015001` 100% |

### Surface — Destructive

| Style | Type | Gradient Stops |
|---|---|---|
| Surface-Destructive-Gradient/Large | Radial | `#F27373` 17% → `#E22626` 51% → `#8A0F0F` 100% |
| Surface-Destructive-Gradient/Small | Radial | `#FF9090` 0% → `#FF5B5B` 20% → `#E22626` 53% → `#C40808` 73% → `#8A0F0F` 100% |

### Surface — Warning

| Style | Type | Gradient Stops |
|---|---|---|
| Surface-Warning-Gradient | Radial | `#FEEAB3` 0% → `#FCD25A` 20% → `#FBBC09` 46% → `#B28506` 73% → `#694F04` 100% |

### Surface — Blue & Purple

| Style | Type | Gradient Stops |
|---|---|---|
| Surface-Blue-Gradient | Radial | `#469DFF` 0% → `#1B70FF` 34% → `#005EFF` 50% → `#004ED2` 75% → `#003FAA` 100% |
| Surface-purple-gradient | Radial | `#4C008B` 16% → `#31005B` 77% |

### Scrim

| Style | Type | Gradient Stops / Value |
|---|---|---|
| Scrim/Block-70% | Solid | `#0D0D0D` at 70% opacity |
| Scrim/Block-1% | Solid | `#0D0D0D` at 1% opacity |
| Scrim/Top Gradient (100-0%) | Linear | `#0D0D0D` 100% opacity at 0% → 100% opacity at 40% → 0% opacity at 100% |
| Scrim/Top Gradient(80%-0) | Linear | `#0D0D0D` 90% opacity at 0% → 60% opacity at 56% → 40% opacity at 79% → 0% opacity at 100% |
| Scrim/Top Gradient-(70-0%) | Linear | `#0D0D0D` 70% opacity at 0% → 70% opacity at 40% → 0% opacity at 100% |
| Scrim/Top Gradient (1-0%) | Linear | `#0D0D0D` 1% opacity → 0% opacity at 100% |
| Scrim/Bottom Gradient (0-100%) | Linear | `#0D0D0D` 0% opacity at 0% → 100% opacity at 60% → 100% opacity at 100% |
| Scrim/Bottom Gradient (0-70%) | Linear | `#0D0D0D` 0% opacity at 0% → 70% opacity at 60% → 70% opacity at 100% |
| Scrim/Bottom Gradient(0-80%) | Linear | `#0D0D0D` 0% opacity at 63% → 60% opacity at 80% → 90% opacity at 94% |

### Overflow Gradients

| Style | Type | Gradient Stops |
|---|---|---|
| Overflow gradient/Right | Linear | `#0D0D0D` 0% opacity at 0% → 100% opacity at 100% |
| Overflow gradient/Left | Linear | `#0D0D0D` 100% opacity at 0% → 0% opacity at 100% |

### Blue Highlight

| Style | Type | Gradient Stops |
|---|---|---|
| Blue-Highlight/Right | Linear | `#0461FF` 0% opacity at 0% → `#0461FF` 100% opacity at 54% → `#0043B6` 100% opacity at 100% |
| Blue-Highlight/Left | Linear | `#0043B6` 100% opacity at 0% → `#0461FF` 100% opacity at 46% → `#0461FF` 0% opacity at 100% |
| Blue-Highlight/Center | Linear | `#0461FF` 0% opacity at 0% → `#0461FF` 100% opacity at 50% → `#0461FF` 0% opacity at 100% |

### Skeleton Loader

| Style | Type | Gradient Stops |
|---|---|---|
| Skeleton Loader/Large | Linear | `#171717` 100% opacity at 0% → `#1D1D1D` 100% opacity at 52%  → `#171717` 100% opacity at 100% |
| Skeleton Loader/Medium | Linear | `#171717` 100% opacity at 0% → `#262626` 100% opacity at 52% → `#171717` 100% opacity at 100% |
| Skeleton Loader/Small | Linear | `#FFFFFF` 30% opacity at 0% → 30% opacity at 10% → 70% opacity at 50% → 30% opacity at 90% → 30% opacity at 100% |

### Offer Strip

| Style | Type | Gradient Stops |
|---|---|---|
| Offer-Strip-Black/Large | Linear | `#0D0D0D` 0% opacity at 0% → 70% opacity at 10% → 70% opacity at 50% → 70% opacity at 90% → 0% opacity at 100% |
| Offer-Strip-Black/Small | Linear | `#0D0D0D` 0% opacity at 0% → 0% opacity at 10% → 70% opacity at 50% → 0% opacity at 90% → 0% opacity at 100% |

### Strips

| Style | Type | Gradient Stops |
|---|---|---|
| Strip/Green | Linear | `#25620C` 100% opacity at 0% → `#235D0A` 100% opacity 46% → `#235D0A` 0% opacity at 100% |
| Strip/Orange | Linear | `#A93205` 100% opacity at 0% → `#DD4F00` 100% opacity at 46% → `#DD4F00` 0% opacity at 100% |
| Strip/Orange - Dark | Radial | `#5B2715` 100% opacity at 0% → `#221613` 100% opacity at 100% |
| Strip/Purple-intense | Linear | `#8000EA` 100% opacity at 0% → `#5900A2`100% opacity at 46% → `#5900A2` 0% opacity at 100% |
| Strip/Purple-subtle | Linear | `#8000EA` 60% opacity at 0% → `#5900A2` 40% opacity at 46% → `#5900A2` 0% opacity at 100% |

### Misc

| Style | Type | Gradient Stops / Value |
|---|---|---|
| Tap Interaction | Radial | `#FFFFFF` 8% at 0% → `#FFFFFF` 0% at 100% |
| text-purple-highlight | Solid | `#D762FE` |

---

## Quick Reference Summary

| Collection | Category | Count |
|---|---|---|
| Colors (Parent) | Greys | 10 |
| Colors (Parent) | Brand | 10 |
| Colors (Parent) | Success | 10 |
| Colors (Parent) | Destructive | 10 |
| Colors (Parent) | Warning | 10 |
| Colors (Parent) | Black & White | 2 |
| **Colors Total** | | **52** |
| Color Tokens (Alias) | Surface | 11 |
| Color Tokens (Alias) | Text | 17 |
| Color Tokens (Alias) | Icons | 16 |
| Color Tokens (Alias) | Border | 13 |
| **Color Tokens Total** | | **57** |
| Color Styles | Gradients & Paints | 43 |

---

<div style="background-color:#fff3cd;border-left:6px solid #FF5200;padding:16px 20px;border-radius:6px;margin-top:24px;">

## ⚠️ REMINDER — Token Usage Rule (Repeated for Visibility)

<div style="background-color:#ffe0cc;padding:12px 16px;border-radius:4px;margin-bottom:12px;">

> 🚨 **Every color on every component MUST use an Alias Token. Never use a Parent Token directly.**

</div>

### The 3-Layer Token Chain

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   COMPONENT                                                 │
│       ↓  references                                         │
│   ALIAS TOKEN  (Color Tokens collection)                    │
│   e.g. Surface/Secondary, Text/Primary, Border/Brand        │
│       ↓  resolves to                                        │
│   PARENT TOKEN  (Colors collection)                         │
│   e.g. Greys/Grey-900, Brand/Brand-600-main                 │
│       ↓  raw value                                          │
│   HEX VALUE  e.g. #1F1F1F                                 │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### ✅ Correct vs ❌ Incorrect Usage

| | Token Used | Example |
|---|---|---|
| ✅ **Correct** | Alias Token | Input background → `Surface/Secondary` |
| ❌ **Incorrect** | Parent Token | Input background → `Greys/Grey-900` |
| ❌ **Incorrect** | Raw Hex | Input background → `#1F1F1F` |

<div style="background-color:#ffd6d6;border-left:4px solid #cc0000;padding:10px 14px;border-radius:4px;margin-top:12px;">

❌ **Violation rule for Claude:** If a raw hex value or a parent token name (e.g. `Greys/Grey-900`, `Brand/Brand-600-main`) appears directly on a component, flag it as a violation and replace it with the correct alias token from the lookup table above.

</div>

</div>
