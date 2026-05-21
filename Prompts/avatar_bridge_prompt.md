# Master Prompt: Inside "bridge_doc" folder add a file named "avatar_bridgefile.md"

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "atoms2.md"** (Avatar section only) with the **POP Codebase Agentic Documentation - POP Codebase/ds_components/PopAvatar.kt, POP Codebase/ds_components/AvatarType.kt, POP Codebase/ds_components/FavIcon.kt, POP Codebase/ds_components/OverlappingAvatarIcons.kt, POP Codebase/theme/FavIconColor.kt, POP Codebase/theme/FavIconSize.kt, POP Codebase/ds_components/PopDot.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

---

## A: Type & Fill Enum Mapping Tables

##### A. Avatar Type Mapping [atoms2.md → AvatarType.kt]

Columns:
- Design System File (e.g., atoms2.md)
- Design Properties  (e.g., `Type: People - Initials, People - Image, Brand, Icon, Illustration`, `Fill: Primary highlighted, Secondary - highlighted etc.`, `Size: 130, 64, 56 etc.` `is disbaled:true/false`, `Dot: false/true`, `fave:false,true`, `Flag:False,True` )
- Codebase enum value (e.g., `AvatarType ,AvatarIconFill.PrimaryHighlighted`, `AvatarSize (AvatarType.kt)` , ` isDisabled: Boolean = false`, )
- Codebase File Name (e.g., AvatarType.kt)
- Code Pattern

Note: `AvatarType.Favorite` is structurally identical to `AvatarType.People` but additionally renders a fav icon badge at the bottom-end position — use `AvatarType.Favorite` only when a fave icon is permanently attached to the avatar.

Note: When `Type=Brand` and `Fill=Not icon - People/Brand`, the `imageModel` passed to `AvatarType.Brand(imageModel = ...)` is a brand logo image. The available brand logo assets and their URLs are listed in **`bridge_doc/icon_files.md`** under the heading named **`Brand logo`**(Supabase PNG URLs). When writing code patterns for `AvatarType.Brand`, reference the image URL from that file using `VisualElement.buildFrom(url)` — do not hardcode a drawable resource ID.

---

##### B. Icon Fill Mapping [atoms2.md → AvatarType.kt / PopAvatar.kt]

> `Fill` only applies when `Type=Icon`. For all other types this property is irrelevant.

Columns:
- Design System File
- Design Fill Name (e.g., Primary - Highlighted, Tertiary - Transparent)
- Codebase Enum Value (e.g., `AvatarIconFill.PrimaryHighlighted`)
- Background Gradient / Color
- Border
- Default Icon Tint
- Codebase File Name
- Code Pattern

Include mappings for:
- Primary - Highlighted → `AvatarIconFill.PrimaryHighlighted` → Radial gradient (`SurfaceColor.BrandPrimaryDisabled20Percent` → `BrandPrimaryDisabled21Percent` → `BrandPrimaryDisabled10Percent`) + `BorderColor.Secondary` border
- Secondary - Highlighted → `AvatarIconFill.SecondaryHighlighted` → Radial gradient (`SurfaceColor.Gradient.SecondaryHighlighted.Start/Mid/End`) + `BorderColor.Secondary` border
- Tertiary - Transparent → `AvatarIconFill.TertiaryTransparent` → no background + `BorderColor.Tertiary` border
- Only icon → `AvatarIconFill.OnlyIcon` → no background, no border
- Primary - 50% → `AvatarIconFill.Primary50Percent` → `PopGradient.Solid(Color(0x990D0D0D))` + `IconColor.Primary` tint, no border
- Not icon - Illustration → `AvatarIconFill` not applicable — use `AvatarType.Illustration` instead
- Not icon- People/ brand


Example Row:
| Design System File | Design Fill | Codebase Enum | Background | Border | Icon Tint | Codebase File | Code Pattern | 
|---|---|---|---|---|---|---|---|
| atoms2.md | Primary - Highlighted | `AvatarIconFill.PrimaryHighlighted` | Radial brand gradient | `BorderColor.Secondary` | `IconColor.Tertiary` | AvatarType.kt | `PopAvatar(type = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Star), size = AvatarSize.Medium)` |

---

## B: Size Mapping Table

##### C. Avatar Size Mapping [atoms2.md → AvatarType.kt]

Columns:
- Design System File
- Design Size (px)
- Design Use Case
- Codebase Enum Value (e.g., `AvatarSize.Medium`)
- Container Dp
- Initials Font Size
- Logo Padding (Brand/Illustration inset)
- Corner Radius (Icon/Brand shape)
- Icon Size (for `AvatarType.Icon`)
- Codebase File Name
- Code Pattern

Include all 6 sizes:
| Design Size | Codebase Enum | Container | Initials Font | Logo Padding | Corner Radius | Icon Size |
|---|---|---|---|---|---|---|
| 16px | `AvatarSize.XSmall` | 16dp | 8sp | 2dp | 8dp | 8dp |
| 28px | `AvatarSize.Small` | 28dp | 12sp | 3.5dp | 10dp | 12dp |
| 36px | `AvatarSize.Medium` | 36dp | 16sp | 4.5dp | 12dp | 16dp |
| 56px | `AvatarSize.Large` | 56dp | 24sp | 7dp | 14dp | 20dp |
| 64px | `AvatarSize.XLarge` | 64dp | 28sp | 8dp | 16dp | 24dp |
| 130px | `AvatarSize.XXLarge` | 130dp | 56sp | 16dp | 32dp | 48dp |

---

## C: Colour & Background Token Mapping Table

##### D. Avatar Colour Token Mapping [atoms2.md → PopColor.kt / AvatarType.kt]

Columns:
- Design System File
- Usage Context (e.g., People border, Brand background, Brand border)
- Design Token / Description
- Codebase Color Variable
- Hex / Alpha
- Codebase File Name

Include:
- People border → `AvatarColor.PersonBorder` → `BorderColor.TertiaryTransparent40` → `rgba(77,77,77,0.4)` / `#664D4D4D`
- Brand background (default) → `AvatarColor.NonPersonBackground` → `PopColor.WhiteBlack.White` → `#FFFFFF`
- Brand border (default) → `AvatarColor.NonPersonBorder` → `BorderColor.TertiaryTransparent40` → `rgba(77,77,77,0.4)` / `#664D4D4D`
- People / Favorite background (default) → `GradientPreset.AvatarBrand.gradient` → brand gradient (defined in PopGradient.kt)
- Disabled overlay → `SurfaceColor.PrimaryDisabled70` → semi-transparent dark overlay applied on top of all types when `isDisabled = true`
- Icon fill PrimaryHighlighted → see Section B for full gradient spec
- Icon fill Primary50Percent → `Color(0x990D0D0D)` — 60% opacity black overlay


Note: `FavIcon` is rendered standalone or via `AvatarType.Favorite` which attaches it permanently at the bottom-end of the avatar. When used standalone inside `topRightComposable`, use `FavIcon(color = ..., size = ..., isActive = ...)` directly.

---

## Detailed Mapping Instructions

### For Each Mapping Table:

1. **Extract from Design System File (atoms2.md):**
   - Read the Avatar section (main component + all 4 sub-components)
   - Extract all property names, variant names, type options, fill options, size values, colour semantics, and size pairing rules
   - Note which properties belong only to `Type=Icon` (`Fill`) vs. all types (`Size`, `Is disabled`)

2. **Match to Codebase:**
   - **Main avatar**: `PopAvatar.kt` — `PopAvatar(type, size, isDisabled, topRightComposable)`
   - **Type variants**: `AvatarType.kt` — sealed interface with `People`, `Favorite`, `Brand`, `Icon`, `Illustration`, `Image`, `RawImage`
   - **Fill variants**: `AvatarIconFill` enum in `PopAvatar.kt` — used only inside `AvatarType.Icon(fill = ...)`
   - **Sizes**: `AvatarSize` enum in `AvatarType.kt` — XSmall(16dp) through XXLarge(130dp)
   - **Fave icon**: `FavIcon.kt` — `FavIcon(color, size, isActive)` using `FavIconColor` and `FavIconSize` enums
   - **Brand logo images**: When `Type=Brand`, the image passed to `AvatarType.Brand(imageModel = ...)` must be sourced from **`bridge_doc/icon_files.md`** — this file contains the full list of available brand/app logo assets with their Supabase PNG URLs. Use `VisualElement.buildFrom(url)` to load the image from a URL. Never hardcode a drawable resource ID for brand logos.
   - **Avatar stack**: `OverlappingAvatarIcons.kt` — `OverlappingImageStack(resIdList)` for brand logos; manual `Column`/`Row` of `PopAvatar` for icon stacks
   - **Colour tokens**: `AvatarColor` object in `PopColor.kt` — `PersonBorder`, `NonPersonBackground`, `NonPersonBorder`

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Every avatar must use `PopAvatar()` — never raw `Box` + `Image` + `CircleShape` manually
   - Every fave icon must use `FavIcon()` — never raw `Image(painterResource(...))`
   - Use `AvatarSize.*` enum — never raw `Modifier.size(36.dp)` for avatar sizing
   - Use `AvatarIconFill.*` enum — never raw color values for icon fill
   - Use `FavIconColor.*` and `FavIconSize.*` enums for fave icon
   - Show real code examples, not pseudocode

### Mapping Priorities:

1. **MUST include 1:1 mappings** showing exact design-to-code translation for every variant
2. **MUST show code patterns** for every row
3. **MUST document `AvatarType.Image` vs `AvatarType.People`** distinction — `Image` is edge-to-edge circular crop with no background/border; `People` shows initials as fallback and supports border
4. **MUST document `isDisabled` overlay** — applies `SurfaceColor.PrimaryDisabled70` over all types uniformly; not a separate AvatarType
5. **MUST document `Fill` scoping** — `AvatarIconFill` is only valid inside `AvatarType.Icon`; for other types it does not exist
6. **MUST document `topRightComposable` slot** — the mechanism for attaching Dot or Flag sub-components to any avatar variant
7. **MUST document `AvatarType.Favorite`** — used exclusively when a permanent fave badge is part of the avatar; not for dynamic on/off fave states
8. **MUST document Avatar Stack** — `OverlappingImageStack` is the codebase equivalent of `Avatar stack` from atoms2.md; the "+N" More indicator is built in when `resIdList.size > 3`
9. **MUST document size pairing rules** — Dot sizes (6/8/10px) and Fave icon sizes (20/24/28/32px) must be paired with their corresponding avatar sizes per the design tables

---

## Quality Standards

✓ **Accuracy:** All mappings verifiable against atoms2.md, PopAvatar.kt, AvatarType.kt, FavIcon.kt, OverlappingAvatarIcons.kt
✓ **Completeness:** All avatar types + all fill variants + all 6 sizes + all sub-components mapped
✓ **Clarity:** Token chains shown where applicable: Design property → Codebase enum → resolved dp/color
✓ **Usability:** Tables searchable by design variant name and by codebase param/composable
✓ **Practical:** Every row includes a runnable `PopAvatar(...)` / `FavIcon(...)` / `OverlappingImageStack(...)` code pattern
✓ **Consistency:** Same column names and ordering throughout all tables
✓ **Distinction:** `AvatarType.Image` (edge-to-edge), `AvatarType.People` (initials fallback), and `AvatarType.RawImage` (no clipping) are clearly differentiated

---

## Output Format

- **File Type:** Markdown (.md)
- **Structure:** Hierarchical with clear sections — A (Type/Fill enums), B (Size), C (Colour tokens), D (Full variants)
- **Tables:** Markdown table format for all mappings
- **Code blocks:** Kotlin code blocks (```kotlin) for all multi-line patterns
- **Length:** Comprehensive but organized — one table per mapping concern

---

## End of Master Prompt

**Generated bridge file should be named:** `avatar_bridgefile.md`
