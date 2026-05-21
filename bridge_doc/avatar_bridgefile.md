# Avatar — Design-to-Code Bridge

**Design source:** `atoms2.md` — Avatar section (Main component + Dot, Flag, Fave Icon, Avatar Stack sub-components)  
**Codebase sources:** `PopAvatar.kt` · `AvatarType.kt` · `FavIcon.kt` · `PopDot.kt` · `OverlappingAvatarIcons.kt` · `FavIconColor.kt` · `FavIconSize.kt`

---

## A: Type & Fill Enum Mapping

### A. Avatar Type Mapping [atoms2.md → AvatarType.kt]

| Design System File | Design Properties | Codebase Enum / Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| atoms2.md | Type=People - Initials, Is disabled=False | `AvatarType.People(name = "John Doe")`, `isDisabled = false` | AvatarType.kt / PopAvatar.kt | `PopAvatar(type = AvatarType.People(name = "John Doe"), size = AvatarSize.Medium)` |
| atoms2.md | Type=People - Image, Is disabled=False | `AvatarType.People(name = "John Doe", imageModel = VisualElement.buildFrom(url))` | AvatarType.kt | `PopAvatar(type = AvatarType.People(name = "John Doe", imageModel = VisualElement.buildFrom(imageUrl)), size = AvatarSize.Medium)` |
| atoms2.md | Type=People - Initials, Is disabled=True | `AvatarType.People(name = "...")`, `isDisabled = true` | PopAvatar.kt | `PopAvatar(type = AvatarType.People(name = "John Doe"), size = AvatarSize.Medium, isDisabled = true)` |
| atoms2.md | Type=Brand, Is disabled=False, Fill=Not icon - People/Brand | `AvatarType.Brand(imageModel = VisualElement.buildFrom(url))` | AvatarType.kt | `PopAvatar(type = AvatarType.Brand(imageModel = VisualElement.buildFrom("https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/U12ICICI.png")), size = AvatarSize.Medium)` |
| atoms2.md | Type=Brand, Is disabled=True | `AvatarType.Brand(imageModel = ...)`, `isDisabled = true` | PopAvatar.kt | `PopAvatar(type = AvatarType.Brand(imageModel = VisualElement.buildFrom(url)), size = AvatarSize.Small, isDisabled = true)` |
| atoms2.md | Type=Icon, Fill=Primary - Highlighted | `AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Star)` | AvatarType.kt / PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Star), size = AvatarSize.Medium)` |
| atoms2.md | Type=Icon, Fill=Secondary - Highlighted | `AvatarType.Icon(fill = AvatarIconFill.SecondaryHighlighted, icon = Icons.Star)` | AvatarType.kt / PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.SecondaryHighlighted, icon = Icons.Star), size = AvatarSize.Medium)` |
| atoms2.md | Type=Icon, Fill=Tertiary - Transparent | `AvatarType.Icon(fill = AvatarIconFill.TertiaryTransparent, icon = Icons.Star)` | AvatarType.kt / PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.TertiaryTransparent, icon = Icons.Star), size = AvatarSize.Medium)` |
| atoms2.md | Type=Icon, Fill=Only icon | `AvatarType.Icon(fill = AvatarIconFill.OnlyIcon, icon = Icons.Star)` | AvatarType.kt / PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.OnlyIcon, icon = Icons.Star), size = AvatarSize.Medium)` |
| atoms2.md | Type=Icon, Fill=Primary - 50% | `AvatarType.Icon(fill = AvatarIconFill.Primary50Percent, icon = Icons.Star)` | AvatarType.kt / PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.Primary50Percent, icon = Icons.Star), size = AvatarSize.Medium)` |
| atoms2.md | Type=Illustration, Is disabled=False | `AvatarType.Illustration(imageModel = VisualElement.buildFrom(url))` | AvatarType.kt | `PopAvatar(type = AvatarType.Illustration(imageModel = VisualElement.buildFrom(url)), size = AvatarSize.Medium)` |
| atoms2.md | Type=Illustration, Is disabled=True | `AvatarType.Illustration(imageModel = ...)`, `isDisabled = true` | PopAvatar.kt | `PopAvatar(type = AvatarType.Illustration(imageModel = VisualElement.buildFrom(url)), size = AvatarSize.Large, isDisabled = true)` |
| atoms2.md | Type=People - Image (edge-to-edge crop, no fallback) | `AvatarType.Image(imageModel = VisualElement.buildFrom(url))` | AvatarType.kt | `PopAvatar(type = AvatarType.Image(imageModel = VisualElement.buildFrom(url)), size = AvatarSize.Medium)` |

> **`AvatarType.People` vs `AvatarType.Image`:**
> - `AvatarType.People` — shows initials as fallback when no image is available; supports border and background. Use for contacts and user profiles.
> - `AvatarType.Image` — edge-to-edge circular crop, no background, no border, no initials fallback. Use when the image always exists and must fill the full circle.
> - `AvatarType.RawImage` — renders without any shape clipping; no border, no background, no padding. Use only for special non-circular asset display.

> **`AvatarType.Favorite`:** Structurally identical to `AvatarType.People` but permanently renders a fav icon badge at the bottom-end position. Use only when the fave badge is a fixed part of the avatar (e.g. a saved contact row). For togglable fave states, use `AvatarType.People` + `FavIcon` inside `topRightComposable`.

> **`isDisabled` overlay:** When `isDisabled = true`, a `SurfaceColor.PrimaryDisabled70` semi-transparent dark layer is drawn on top of all avatar types uniformly. It is not a separate `AvatarType` — it is a boolean flag on `PopAvatar()`.

> **Brand logo images:** When `Type=Brand`, the `imageModel` must be sourced from `bridge_doc/icon_files.md` under the **`Brand logo`** heading (Supabase PNG URLs). Load with `VisualElement.buildFrom(url)`. Never hardcode a drawable resource ID for brand logos.

---

### B. Icon Fill Mapping [atoms2.md → PopAvatar.kt]

> `Fill` only applies when `Type=Icon`. For People, Brand, and Illustration types this property does not exist.

| Design System File | Design Fill | Codebase Enum | Background | Border | Default Icon Tint | Codebase File | Code Pattern |
|---|---|---|---|---|---|---|---|
| atoms2.md | Primary - Highlighted | `AvatarIconFill.PrimaryHighlighted` | Radial gradient: `SurfaceColor.BrandPrimaryDisabled20Percent` → `BrandPrimaryDisabled21Percent` → `BrandPrimaryDisabled10Percent` | `BorderColor.Secondary` | `IconColor.Tertiary` | PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Star), size = AvatarSize.Medium)` |
| atoms2.md | Secondary - Highlighted | `AvatarIconFill.SecondaryHighlighted` | Radial gradient: `SurfaceColor.Gradient.SecondaryHighlighted.Start` → `Mid` → `End` | `BorderColor.Secondary` | `IconColor.Tertiary` | PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.SecondaryHighlighted, icon = Icons.Star), size = AvatarSize.Medium)` |
| atoms2.md | Tertiary - Transparent | `AvatarIconFill.TertiaryTransparent` | None (transparent) | `BorderColor.Tertiary` | `IconColor.Tertiary` | PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.TertiaryTransparent, icon = Icons.Star), size = AvatarSize.Medium)` |
| atoms2.md | Only icon | `AvatarIconFill.OnlyIcon` | None | None | `IconColor.Tertiary` | PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.OnlyIcon, icon = Icons.Star), size = AvatarSize.Medium)` |
| atoms2.md | Primary - 50% | `AvatarIconFill.Primary50Percent` | `PopGradient.Solid(Color(0x990D0D0D))` — 60% opacity black | None | `IconColor.Primary` | PopAvatar.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.Primary50Percent, icon = Icons.Star), size = AvatarSize.Medium)` |
| atoms2.md | Not icon - Illustration | `AvatarIconFill` not applicable | — | — | — | AvatarType.kt | Use `AvatarType.Illustration(imageModel = ...)` instead — not `AvatarType.Icon` |
| atoms2.md | Not icon - People / Brand | `AvatarIconFill` not applicable | — | — | — | AvatarType.kt | Use `AvatarType.People(...)` or `AvatarType.Brand(...)` — Fill does not apply |

> **Custom icon tint:** To override the default tint, pass `iconTint` explicitly: `AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Star, iconTint = TextColor.Primary)`

---

## B: Size Mapping

### C. Avatar Size Mapping [atoms2.md → AvatarType.kt]

| Design System File | Design Size (px) | Design Use Case | Codebase Enum | Container Dp | Initials Font | Logo Padding | Corner Radius | Icon Size | Codebase File | Code Pattern |
|---|---|---|---|---|---|---|---|---|---|---|
| atoms2.md | 16px | Micro — inline mention, dense data rows | `AvatarSize.XSmall` | 16dp | 8sp | 2dp | 8dp | 8dp | AvatarType.kt | `PopAvatar(type = ..., size = AvatarSize.XSmall)` |
| atoms2.md | 28px | Small — compact lists, comment rows | `AvatarSize.Small` | 28dp | 12sp | 3.5dp | 10dp | 12dp | AvatarType.kt | `PopAvatar(type = ..., size = AvatarSize.Small)` |
| atoms2.md | 36px | Medium — standard list rows, chat threads | `AvatarSize.Medium` | 36dp | 16sp | 4.5dp | 12dp | 16dp | AvatarType.kt | `PopAvatar(type = ..., size = AvatarSize.Medium)` |
| atoms2.md | 56px | Large — profile headers, contact cards | `AvatarSize.Large` | 56dp | 24sp | 7dp | 14dp | 20dp | AvatarType.kt | `PopAvatar(type = ..., size = AvatarSize.Large)` |
| atoms2.md | 64px | XL — prominent profile surfaces | `AvatarSize.XLarge` | 64dp | 28sp | 8dp | 16dp | 24dp | AvatarType.kt | `PopAvatar(type = ..., size = AvatarSize.XLarge)` |
| atoms2.md | 130px | Hero — full profile page, onboarding | `AvatarSize.XXLarge` | 130dp | 56sp | 16dp | 32dp | 48dp | AvatarType.kt | `PopAvatar(type = ..., size = AvatarSize.XXLarge)` |

> **Never use** `Modifier.size(36.dp)` to size an avatar — always pass the `AvatarSize.*` enum to `PopAvatar()`. The size enum controls container dp, initials font, logo padding, corner radius, and icon size simultaneously.

---

## C: Colour & Background Token Mapping

### D. Avatar Colour Token Mapping [atoms2.md → PopColor.kt]

| Design System File | Usage Context | Design Token / Description | Codebase Color Variable | Hex / Alpha | Codebase File |
|---|---|---|---|---|---|
| atoms2.md | People border (default) | `Border/Tertiary-Transparent-40%` | `AvatarColor.PersonBorder` → `BorderColor.TertiaryTransparent40` | `rgba(77,77,77,0.4)` / `#664D4D4D` | PopColor.kt |
| atoms2.md | Brand background (default) | White background behind logo | `AvatarColor.NonPersonBackground` → `PopColor.WhiteBlack.White` | `#FFFFFF` | PopColor.kt |
| atoms2.md | Brand border (default) | `Border/Tertiary-Transparent-40%` | `AvatarColor.NonPersonBorder` → `BorderColor.TertiaryTransparent40` | `rgba(77,77,77,0.4)` / `#664D4D4D` | PopColor.kt |
| atoms2.md | People / Favorite background (default) | Brand gradient background | `GradientPreset.AvatarBrand.gradient` | Brand gradient (defined in PopGradient.kt) | PopColor.kt / PopGradient.kt |
| atoms2.md | Disabled overlay (all types) | Semi-transparent dark layer when `isDisabled = true` | `SurfaceColor.PrimaryDisabled70` | Semi-transparent dark | PopColor.kt |
| atoms2.md | Icon fill — Primary 50% | 60% opacity black fill | `Color(0x990D0D0D)` (via `PopGradient.Solid`) | `#0D0D0D` at ~60% opacity | PopAvatar.kt |
| atoms2.md | Icon fill — PrimaryHighlighted | Radial brand gradient | `SurfaceColor.BrandPrimaryDisabled20Percent` → `BrandPrimaryDisabled21Percent` → `BrandPrimaryDisabled10Percent` | Brand radial gradient | PopAvatar.kt |
| atoms2.md | Icon fill — SecondaryHighlighted | Radial secondary gradient | `SurfaceColor.Gradient.SecondaryHighlighted.Start/Mid/End` | Secondary radial gradient | PopAvatar.kt |

> **FavIcon placement note:** `FavIcon` is rendered via `AvatarType.Favorite` (permanent badge at bottom-end) or standalone inside the `topRightComposable` slot. When using the slot: `PopAvatar(type = ..., size = ..., topRightComposable = { FavIcon(color = FavIconColor.Orange, size = FavIconSize.Med, isActive = true) })`.

---

## D: Full Variant Mapping — Sub-components

### E. Dot Sub-component [atoms2.md → PopDot.kt]

> The `Dot` sub-component is placed via the `topRightComposable` slot on `PopAvatar`, or standalone as `PopDot()`.

| Design System File | Design Variant | `color` param | `size` param | `isActive` | Paired Avatar Size | Code Pattern |
|---|---|---|---|---|---|---|
| atoms2.md | Colour=Green, Size=6 | `PopDotColor.Green` | `PopDotSize.Med` | `true` | 16px, 28px avatar | `PopDot(color = PopDotColor.Green, size = PopDotSize.Med)` |
| atoms2.md | Colour=Orange, Size=6 | `PopDotColor.Orange` | `PopDotSize.Med` | `true` | 16px, 28px avatar | `PopDot(color = PopDotColor.Orange, size = PopDotSize.Med)` |
| atoms2.md | Colour=Green, Size=8 | `PopDotColor.Green` | `PopDotSize.Large` | `true` | 36px, 56px avatar | `PopDot(color = PopDotColor.Green, size = PopDotSize.Large)` |
| atoms2.md | Colour=Orange, Size=8 | `PopDotColor.Orange` | `PopDotSize.Large` | `true` | 36px, 56px avatar | `PopDot(color = PopDotColor.Orange, size = PopDotSize.Large)` |
| atoms2.md | Colour=White, Size=8 | `PopDotColor.White` | `PopDotSize.Large` | `true` | 36px, 56px avatar | `PopDot(color = PopDotColor.White, size = PopDotSize.Large)` |
| atoms2.md | Colour=Blue, Size=8 | `PopDotColor.Blue` | `PopDotSize.Large` | `true` | 36px, 56px avatar | `PopDot(color = PopDotColor.Blue, size = PopDotSize.Large)` |
| atoms2.md | Colour=Green, Size=10 | `PopDotColor.Green` | `PopDotSize.Large` | `true` | 64px, 130px avatar | `PopDot(color = PopDotColor.Green, size = PopDotSize.Large)` |
| atoms2.md | Colour=Orange, Size=10 | `PopDotColor.Orange` | `PopDotSize.Large` | `true` | 64px, 130px avatar | `PopDot(color = PopDotColor.Orange, size = PopDotSize.Large)` |

**Size pairing rule:**

| Design Dot Size | `PopDotSize` enum | Resolved Dp | Paired Avatar Size |
|---|---|---|---|
| 6px | `PopDotSize.Med` | 6dp | 16px, 28px avatar |
| 8px | `PopDotSize.Large` | 8dp | 36px, 56px avatar |
| 10px | `PopDotSize.Large` | 8dp (nearest) | 64px, 130px avatar |

**Dot colour semantics:**

| Design Colour | Meaning | `PopDotColor` |
|---|---|---|
| Green | Online / active | `PopDotColor.Green` |
| Orange | Away / busy (brand) | `PopDotColor.Orange` |
| White | Invert — for use on dark avatar rings | `PopDotColor.White` |
| Blue | In a call / focused | `PopDotColor.Blue` |

**Assembly with avatar (topRightComposable):**

```kotlin
PopAvatar(
    type = AvatarType.People(name = "Jane Smith"),
    size = AvatarSize.Medium, // 36dp → use PopDotSize.Large
    topRightComposable = {
        PopDot(
            color = PopDotColor.Green,
            size = PopDotSize.Large,
            isActive = true
        )
    }
)
```

---

### F. Fave Icon Sub-component [atoms2.md → FavIcon.kt]

| Design System File | Design Variant | `color` param | `size` param | `isActive` | Resolved Icon Dp | Paired Avatar Size | Codebase File | Code Pattern |
|---|---|---|---|---|---|---|---|---|
| atoms2.md | Colour=Orange, Size=20px, Is active=True | `FavIconColor.Orange` | `FavIconSize.Med` | `true` | 18dp | 36px avatar | FavIcon.kt | `FavIcon(color = FavIconColor.Orange, size = FavIconSize.Med, isActive = true)` |
| atoms2.md | Colour=Orange, Size=20px, Is active=False | `FavIconColor.Orange` | `FavIconSize.Med` | `false` | 18dp | 36px avatar | FavIcon.kt | `FavIcon(color = FavIconColor.Orange, size = FavIconSize.Med, isActive = false)` |
| atoms2.md | Colour=White, Size=20px, Is active=True | `FavIconColor.White` | `FavIconSize.Med` | `true` | 18dp | 36px avatar | FavIcon.kt | `FavIcon(color = FavIconColor.White, size = FavIconSize.Med, isActive = true)` |
| atoms2.md | Colour=Green, Size=20px, Is active=True | `FavIconColor.Green` | `FavIconSize.Med` | `true` | 18dp | 36px avatar | FavIcon.kt | `FavIcon(color = FavIconColor.Green, size = FavIconSize.Med, isActive = true)` |
| atoms2.md | Colour=Blue, Size=20px, Is active=True | `FavIconColor.Blue` | `FavIconSize.Med` | `true` | 18dp | 36px avatar | FavIcon.kt | `FavIcon(color = FavIconColor.Blue, size = FavIconSize.Med, isActive = true)` |
| atoms2.md | Colour=Orange, Size=24px, Is active=True | `FavIconColor.Orange` | `FavIconSize.Large` | `true` | 22dp | 56px avatar | FavIcon.kt | `FavIcon(color = FavIconColor.Orange, size = FavIconSize.Large, isActive = true)` |
| atoms2.md | Colour=Orange, Size=24px, Is active=False | `FavIconColor.Orange` | `FavIconSize.Large` | `false` | 22dp | 56px avatar | FavIcon.kt | `FavIcon(color = FavIconColor.Orange, size = FavIconSize.Large, isActive = false)` |
| atoms2.md | Colour=Orange, Size=28px, Is active=True | `FavIconColor.Orange` | `FavIconSize.Large` | `true` | 22dp | 64px avatar | FavIcon.kt | `FavIcon(color = FavIconColor.Orange, size = FavIconSize.Large, isActive = true)` |
| atoms2.md | Colour=Orange, Size=32px, Is active=True | `FavIconColor.Orange` | `FavIconSize.Large` | `true` | 22dp | 130px avatar | FavIcon.kt | `FavIcon(color = FavIconColor.Orange, size = FavIconSize.Large, isActive = true)` |

**Fave icon size pairing rule:**

| Design Size | `FavIconSize` enum | Resolved Dp | Paired Avatar Size |
|---|---|---|---|
| 20px | `FavIconSize.Med` | 18dp | 36px avatar |
| 24px | `FavIconSize.Large` | 22dp | 56px avatar |
| 28px | `FavIconSize.Large` | 22dp | 64px avatar |
| 32px | `FavIconSize.Large` | 22dp | 130px avatar |

> `isActive = true` → filled icon (item is favourited) · `isActive = false` → outline icon (not favourited)

**Assembly with avatar — standalone via topRightComposable:**

```kotlin
PopAvatar(
    type = AvatarType.People(name = "Jane Smith"),
    size = AvatarSize.Medium, // 36dp → use FavIconSize.Med
    topRightComposable = {
        FavIcon(
            color = FavIconColor.Orange,
            size = FavIconSize.Med,
            isActive = true
        )
    }
)
```

**Assembly with avatar — permanent via AvatarType.Favorite:**

```kotlin
// Use AvatarType.Favorite when the fave badge is always shown (e.g. saved contacts list)
PopAvatar(
    type = AvatarType.Favorite(name = "Jane Smith"),
    size = AvatarSize.Medium
)
```

---

### G. Avatar Stack [atoms2.md → OverlappingAvatarIcons.kt]

> `OverlappingImageStack` is the codebase equivalent of `Avatar stack` from atoms2.md.

**Component:** `OverlappingImageStack(resIdList: List<Int>)`  
**File:** `OverlappingAvatarIcons.kt`  
**Overlap offset:** 20dp · Avatar sizes in stack: 28dp / 25dp / 22dp (index 0 / 1 / 2)

| Design Variant | `Count` | `Type` | `More` | Code Pattern |
|---|---|---|---|---|
| Count=1, Type=Logo, More=False | 1 | `AvatarType.Brand` | False | `OverlappingImageStack(resIdList = listOf(R.drawable.logo_icici))` |
| Count=2, Type=Logo, More=False | 2 | `AvatarType.Brand` | False | `OverlappingImageStack(resIdList = listOf(R.drawable.logo_icici, R.drawable.logo_sbi))` |
| Count=3, Type=Logo, More=False | 3 | `AvatarType.Brand` | False | `OverlappingImageStack(resIdList = listOf(R.drawable.logo_icici, R.drawable.logo_sbi, R.drawable.logo_hdfc))` |
| Count=3, Type=Logo, More=True (overflow) | 3 visible + "+N" | `AvatarType.Brand` | True | `OverlappingImageStack(resIdList = listOf(R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4))` — "+N" chip auto-renders when `resIdList.size > 3` |

> **More=True behaviour:** When `resIdList.size > 3`, `OverlappingImageStack` automatically shows the first 2 avatars and replaces the third slot with a `+N` indicator on a white rounded background (`PopColor.WhiteBlack.White`). No manual handling needed.

**Icon stack (Type=Icon) — manual assembly:**

`OverlappingImageStack` supports only drawable resource IDs (brand logos). For `Type=Icon` stacks, assemble manually:

```kotlin
// Count=3, Type=Icon, More=False
Row {
    Box(modifier = Modifier.zIndex(3f)) {
        PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.SecondaryHighlighted, icon = Icons.User01), size = AvatarSize.Small)
    }
    Box(modifier = Modifier.offset(x = (-8).dp).zIndex(2f)) {
        PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.SecondaryHighlighted, icon = Icons.User01), size = AvatarSize.Small)
    }
    Box(modifier = Modifier.offset(x = (-16).dp).zIndex(1f)) {
        PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.SecondaryHighlighted, icon = Icons.User01), size = AvatarSize.Small)
    }
}
```

---

## Quick Reference — Design Property → Codebase

| Design Property | Codebase Equivalent | Notes |
|---|---|---|
| `Type=People - Initials` | `AvatarType.People(name = "...")` | Initials auto-generated from name |
| `Type=People - Image` | `AvatarType.People(name = "...", imageModel = VisualElement.buildFrom(url))` | Image overlays initials; initials shown if image fails |
| `Type=Brand` | `AvatarType.Brand(imageModel = VisualElement.buildFrom(url))` | URL from `bridge_doc/icon_files.md` → Brand logo section |
| `Type=Icon` | `AvatarType.Icon(fill = AvatarIconFill.*, icon = Icons.*)` | Fill drives background; required param |
| `Type=Illustration` | `AvatarType.Illustration(imageModel = VisualElement.buildFrom(url))` | No tint, padded inset |
| `Fill=Primary - Highlighted` | `AvatarIconFill.PrimaryHighlighted` | Only valid inside `AvatarType.Icon` |
| `Fill=Secondary - Highlighted` | `AvatarIconFill.SecondaryHighlighted` | Only valid inside `AvatarType.Icon` |
| `Fill=Tertiary - Transparent` | `AvatarIconFill.TertiaryTransparent` | Only valid inside `AvatarType.Icon` |
| `Fill=Only icon` | `AvatarIconFill.OnlyIcon` | Only valid inside `AvatarType.Icon` |
| `Fill=Primary - 50%` | `AvatarIconFill.Primary50Percent` | Only valid inside `AvatarType.Icon` |
| `Size=16` | `AvatarSize.XSmall` | 16dp container |
| `Size=28` | `AvatarSize.Small` | 28dp container |
| `Size=36` | `AvatarSize.Medium` | 36dp container |
| `Size=56` | `AvatarSize.Large` | 56dp container |
| `Size=64` | `AvatarSize.XLarge` | 64dp container |
| `Size=130` | `AvatarSize.XXLarge` | 130dp container |
| `Is disabled=True` | `isDisabled = true` on `PopAvatar()` | Applies `SurfaceColor.PrimaryDisabled70` overlay |
| `Dot` sub-component | `PopDot(color, size, isActive)` | Place in `topRightComposable` slot |
| `Fave icon` sub-component | `FavIcon(color, size, isActive)` | Place in `topRightComposable` or use `AvatarType.Favorite` |
| `Avatar stack` | `OverlappingImageStack(resIdList)` | Auto "+N" when `size > 3` |

> **Never use:** raw `Box + Image + CircleShape` for avatars · raw `Image(painterResource(...))` for fave icons · `Modifier.size(dp)` for avatar sizing · hardcoded hex colors for fills or borders.
