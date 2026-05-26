# Avatar Bridge File — POP Design System → Codebase Mapping

**Design Source:** `DS_markdown/atoms2.md` (Avatar section)  
**Codebase Files:** `PopAvatar.kt`, `AvatarType.kt`, `FavIcon.kt`, `OverlappingAvatarIcons.kt`, `FavIconColor.kt`, `FavIconSize.kt`, `PopDot.kt`  
**Purpose:** 1:1 design-to-code translation reference for Avatar atoms.

---

## Section A — Type & Fill Enum Mapping

### Table A: Avatar Type Mapping [atoms2.md → PopAvatar.kt]

| Design System File | Design Property | Design Value | Codebase Enum / Param | Codebase File | Code Pattern |
|---|---|---|---|---|---|
| atoms2.md | Type: People - Initials | `AvatarType.People(name = "...")` | PopAvatar.kt | `PopAvatar(type = AvatarType.People(name = "Riya Sharma"), size = AvatarSize.Medium)` |
| atoms2.md | Type | People - Image | `AvatarType.People(name = "...", imageModel = VisualElement.buildFrom(url))` | AvatarType.kt | `PopAvatar(type = AvatarType.People(name = "Riya", imageModel = VisualElement.buildFrom(imageUrl)), size = AvatarSize.Medium)` |
| atoms2.md | Type | Brand | `AvatarType.Brand(imageModel = VisualElement.buildFrom(url))` | PopAvatar.kt | `PopAvatar(type = AvatarType.Brand(imageModel = VisualElement.buildFrom("https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/U12ICICI.png")), size = AvatarSize.Medium)` |
| atoms2.md | Type | Icon | `AvatarType.Icon(fill = AvatarIconFill.*, icon = IconName.*)` | PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = IconName.Star), size = AvatarSize.Medium)` |
| atoms2.md | Type | Illsutration | `AvatarType.Illustration(imageModel = VisualElement.buildFrom(...))` | PopAvatar.kt | `PopAvatar(type = AvatarType.Illustration(imageModel = VisualElement.buildFrom(illustrationUrl)), size = AvatarSize.Medium)` |
| atoms2.md | Is disabled | False | `isDisabled = false` | PopAvatar.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.Medium, isDisabled = false)` |
| atoms2.md | Is disabled | True | `isDisabled = true` | PopAvatar.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.Medium, isDisabled = true)` |
| atoms2.md | Dot | False | `topRightComposable = null` (omit param) | PopAvatar.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.Medium)` |
| atoms2.md | Dot | True | `topRightComposable = { PopDot(...) }` | PopAvatar.kt / PopDot.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.Medium, topRightComposable = { PopDot(color = PopDotColor.Green, size = PopDotSize.Large) })` |
| atoms2.md | Fave | False | Use `AvatarType.People` (no fave badge) | AvatarType.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.Medium)` |
| atoms2.md | Fave | True | `AvatarType.Favorite(name = "...")` — permanently attaches fave badge at bottom-end | AvatarType.kt | `PopAvatar(type = AvatarType.Favorite(name = "Raj"), size = AvatarSize.Medium)` |
| atoms2.md | Flag | False | `topRightComposable = null` (omit param) | PopAvatar.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.Medium)` |
| atoms2.md | Flag | True | `topRightComposable = { /* Flag composable */ }` | PopAvatar.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.Medium, topRightComposable = { FlagComposable() })` |

> **Note on `AvatarType.Favorite`:** Structurally identical to `AvatarType.People` but additionally renders a permanent fave icon badge at the bottom-end position. Use `AvatarType.Favorite` only when the fave badge is permanently part of the avatar — not for dynamic on/off fave states.

> **Note on `AvatarType.Brand` image source:** The `imageModel` passed to `AvatarType.Brand` must be sourced from `bridge_doc/icon_files.md` under the **`Brand logo`** heading. Use `VisualElement.buildFrom(url)` — never hardcode a drawable resource ID.

> **Note on `topRightComposable`:** This slot is the mechanism for attaching `PopDot` or `Flag` sub-components to any avatar variant. Pass a composable lambda; it will be placed at the `Alignment.TopEnd` position of the avatar container.

---

### Table B: Icon Fill Mapping [atoms2.md → PopAvatar.kt]

> `Fill` applies **only** when `Type = Icon`. For all other types this property is irrelevant.

| Design System File | Design Fill Name | Codebase Enum Value | Background | Border | Icon Tint | Codebase File | Code Pattern |
|---|---|---|---|---|---|---|---|
| atoms2.md | Primary - Highlighted | `AvatarIconFill.PrimaryHighlighted` | Radial gradient: `SurfaceColor.BrandPrimaryDisabled20Percent` → `BrandPrimaryDisabled21Percent` → `BrandPrimaryDisabled10Percent` (stops: 0.0, 0.43, 1.0), centered top | `BorderColor.Secondary` (`#333333`) | `IconColor.Tertiary` | AvatarType.kt / PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = IconName.Star), size = AvatarSize.Medium)` |
| atoms2.md | Secondary - Highlighted | `AvatarIconFill.SecondaryHighlighted` | Radial gradient: `SurfaceColor.Gradient.SecondaryHighlighted.Start` → `.Mid` → `.End` (stops: 0.0, 0.46, 1.0), centered top | `BorderColor.Secondary` (`#333333`) | `IconColor.Tertiary` | AvatarType.kt / PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.SecondaryHighlighted, icon = IconName.Star), size = AvatarSize.Medium)` |
| atoms2.md | Tertiary - Transparent | `AvatarIconFill.TertiaryTransparent` | No background | `BorderColor.Tertiary` (`#4D4D4D`) | `IconColor.Tertiary` | AvatarType.kt / PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.TertiaryTransparent, icon = IconName.Star), size = AvatarSize.Medium)` |
| atoms2.md | Only icon | `AvatarIconFill.OnlyIcon` | No background | No border | `IconColor.Tertiary` | AvatarType.kt / PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.OnlyIcon, icon = IconName.Star), size = AvatarSize.Medium)` |
| atoms2.md | Primary - 50% | `AvatarIconFill.Primary50Percent` | `PopGradient.Solid(Color(0x990D0D0D))` — 60% opacity black solid | No border | `IconColor.Primary` | AvatarType.kt / PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.Primary50Percent, icon = IconName.Star), size = AvatarSize.Medium)` |
| atoms2.md | Not icon - Illustration | Not applicable — use `AvatarType.Illustration` instead | `AvatarIconFill` does not apply | NO CODE EQUIVALENT FOUND in `AvatarIconFill` | NO CODE EQUIVALENT FOUND in `AvatarIconFill` | AvatarType.kt | `PopAvatar(type = AvatarType.Illustration(imageModel = VisualElement.buildFrom(illustrationUrl)), size = AvatarSize.Medium)` |
| atoms2.md | Not icon - People / Brand | Not applicable — use `AvatarType.People` or `AvatarType.Brand` | `AvatarIconFill` does not apply | NO CODE EQUIVALENT FOUND in `AvatarIconFill` | NO CODE EQUIVALENT FOUND in `AvatarIconFill` | AvatarType.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.Medium)` |

---

## Section B — Size Mapping

### Table C: Avatar Size Mapping [atoms2.md → AvatarType.kt]

| Design System File | Design Size (px) | Design Use Case | Codebase Enum Value | Container Dp | Initials Font Size | Logo Padding | Corner Radius | Icon Size | Codebase File | Code Pattern |
|---|---|---|---|---|---|---|---|---|---|---|
| atoms2.md | 16px | Micro — inline mention, dense data rows | `AvatarSize.XSmall` | 16dp | 8sp | 2dp | 8dp | 8dp | AvatarType.kt | `PopAvatar(type = AvatarType.People(name = "R"), size = AvatarSize.XSmall)` |
| atoms2.md | 28px | Small — compact lists, comment rows | `AvatarSize.Small` | 28dp | 12sp | 3.5dp | 10dp | 12dp | AvatarType.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.Small)` |
| atoms2.md | 36px | Medium — standard list rows, chat threads | `AvatarSize.Medium` | 36dp | 16sp | 4.5dp | 12dp | 16dp | AvatarType.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.Medium)` |
| atoms2.md | 56px | Large — profile headers, contact cards | `AvatarSize.Large` | 56dp | 24sp | 7dp | 14dp | 20dp | AvatarType.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.Large)` |
| atoms2.md | 64px | XL — prominent profile surfaces | `AvatarSize.XLarge` | 64dp | 28sp | 8dp | 16dp | 24dp | AvatarType.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.XLarge)` |
| atoms2.md | 130px | Hero — full profile page, onboarding | `AvatarSize.XXLarge` | 130dp | 56sp | 16dp | 32dp | 48dp | AvatarType.kt | `PopAvatar(type = AvatarType.People(name = "Raj"), size = AvatarSize.XXLarge)` |

---

## Section C — Colour & Background Token Mapping

### Table D: Avatar Colour Token Mapping [atoms2.md → PopColor.kt / AvatarType.kt]

| Design System File | Usage Context | Design Token / Description | Codebase Color Variable | Hex / Alpha | Codebase File |
|---|---|---|---|---|---|
| atoms2.md | People border | `Border/Tertiary-Transparent-40%` | `AvatarColor.PersonBorder` → `BorderColor.TertiaryTransparent40` | `rgba(77,77,77,0.4)` / `#664D4D4D` | PopColor.kt |
| atoms2.md | Brand background (default) | White background for brand/logo container | `AvatarColor.NonPersonBackground` → `PopColor.WhiteBlack.White` | `#FFFFFF` | PopColor.kt |
| atoms2.md | Brand border (default) | `Border/Tertiary-Transparent-40%` | `AvatarColor.NonPersonBorder` → `BorderColor.TertiaryTransparent40` | `rgba(77,77,77,0.4)` / `#664D4D4D` | PopColor.kt |
| atoms2.md | People / Favorite background (default) | Brand gradient applied to People and Favorite avatar backgrounds | `GradientPreset.AvatarBrand.gradient` | Brand gradient (defined in PopGradient.kt) | PopGradient.kt / AvatarType.kt |
| atoms2.md | Disabled overlay (all types) | Semi-transparent dark overlay on top of all avatar types when `isDisabled = true` | `SurfaceColor.PrimaryDisabled70` | `#B30D0D0D` (70% opacity black) | PopColor.kt / PopAvatar.kt |
| atoms2.md | Icon fill — Primary Highlighted background (center) | Radial gradient start stop | `SurfaceColor.BrandPrimaryDisabled20Percent` | `#1A1A1A` | PopColor.kt |
| atoms2.md | Icon fill — Primary Highlighted background (mid) | Radial gradient mid stop | `SurfaceColor.BrandPrimaryDisabled21Percent` | `#212121` | PopColor.kt |
| atoms2.md | Icon fill — Primary Highlighted background (edge) | Radial gradient end stop | `SurfaceColor.BrandPrimaryDisabled10Percent` | `#0D0D0D` | PopColor.kt |
| atoms2.md | Icon fill — Primary 50% background | Solid 60% opacity black overlay | `Color(0x990D0D0D)` via `PopGradient.Solid(...)` | `#990D0D0D` (~60% opacity black) | PopAvatar.kt |
| atoms2.md | Icon fill — Primary Highlighted border | Border for PrimaryHighlighted fill | `BorderColor.Secondary` | `#333333` | PopColor.kt |
| atoms2.md | Icon fill — Secondary Highlighted border | Border for SecondaryHighlighted fill | `BorderColor.Secondary` | `#333333` | PopColor.kt |
| atoms2.md | Icon fill — Tertiary Transparent border | Border for TertiaryTransparent fill | `BorderColor.Tertiary` | `#4D4D4D` | PopColor.kt |

> **Note on `FavIcon` colour context:** `FavIcon` is rendered standalone (via `topRightComposable`) or permanently attached via `AvatarType.Favorite`. When standalone, use `FavIcon(color = FavIconColor.Orange, size = FavIconSize.Large, isActive = true)`. The `AvatarType.Favorite` type internally renders the fave icon at the bottom-end position with a fixed drawable (`ic_fav_white_large_active`).

---

## Section F — isDisabled Behaviour

`isDisabled` applies uniformly to **all avatar types**. It overlays `SurfaceColor.PrimaryDisabled70` (`#B30D0D0D` — 70% opacity black) on top of the rendered avatar content. It is **not** a separate `AvatarType` — it is a Boolean parameter on `PopAvatar`.

```kotlin
// Disabled People avatar
PopAvatar(
    type = AvatarType.People(name = "Raj"),
    size = AvatarSize.Medium,
    isDisabled = true
)

// Disabled Brand avatar
PopAvatar(
    type = AvatarType.Brand(imageModel = VisualElement.buildFrom(logoUrl)),
    size = AvatarSize.Medium,
    isDisabled = true
)

// Disabled Icon avatar
PopAvatar(
    type = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = IconName.Star),
    size = AvatarSize.Medium,
    isDisabled = true
)
```

---

## Section G — Size Pairing Rules

### Dot size pairing

| Avatar Size (Design) | Avatar Size (Code) | Dot Size (Design) | Dot Size (Code) |
|---|---|---|---|
| 16px | `AvatarSize.XSmall` | 6px | `PopDotSize.Med` |
| 28px | `AvatarSize.Small` | 6px | `PopDotSize.Med` |
| 36px | `AvatarSize.Medium` | 8px | `PopDotSize.Large` |
| 56px | `AvatarSize.Large` | 8px | `PopDotSize.Large` |
| 64px | `AvatarSize.XLarge` | 10px | NO CODE EQUIVALENT FOUND |
| 130px | `AvatarSize.XXLarge` | 10px | NO CODE EQUIVALENT FOUND |

### Fave icon size pairing

| Avatar Size (Design) | Avatar Size (Code) | Fave Icon Size (Design) | Fave Icon Size (Code) |
|---|---|---|---|
| 36px | `AvatarSize.Medium` | 20px | `FavIconSize.Med` (18dp — closest available) |
| 56px | `AvatarSize.Large` | 24px | `FavIconSize.Large` (22dp — closest available) |
| 64px | `AvatarSize.XLarge` | 28px | NO CODE EQUIVALENT FOUND |
| 130px | `AvatarSize.XXLarge` | 32px | NO CODE EQUIVALENT FOUND |


## Quick Reference — Component to File Map

| Composable / Enum | Codebase File |
|---|---|
| `PopAvatar(...)` | `ds_components/PopAvatar.kt` |
| `AvatarType.*` | `ds_components/AvatarType.kt` |
| `AvatarSize.*` | `ds_components/AvatarType.kt` |
| `AvatarIconFill.*` | `ds_components/PopAvatar.kt` |
| `FavIcon(...)` | `ds_components/FavIcon.kt` |
| `FavIconColor.*` | `theme/FavIconColor.kt` |
| `FavIconSize.*` | `theme/FavIconSize.kt` |
| `PopDot(...)` | `ds_components/PopDot.kt` |
| `PopDotColor.*` | `theme/PopDotColor.kt` |
| `PopDotSize.*` | `theme/PopDotSize.kt` |
| `OverlappingImageStack(...)` | `ds_components/OverlappingAvatarIcons.kt` |
| `AvatarColor.*` | `theme/PopColor.kt` |
| `BorderColor.*` | `theme/PopColor.kt` |
| `SurfaceColor.*` | `theme/PopColor.kt` |
| `GradientPreset.AvatarBrand` | `theme/PopGradient.kt` |
| Brand logo URLs | `bridge_doc/icon_files.md` (under `Brand logo` heading) |
