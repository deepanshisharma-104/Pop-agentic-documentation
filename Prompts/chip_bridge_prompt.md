# Master Prompt: Inside "bridge_doc" folder add a file named "chip_bridgefile.md"

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "molecules.md"** (Chip and Chip Stack sections — 8a and 8b only) with the **POP Codebase Agentic Documentation - POP Codebase/ds_components/PopChip.kt, POP Codebase/ds_components/PopChipStack.kt, POP Codebase/models/PopChipConfig.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

---

## A: Chip Variant & Mode Mapping Tables

##### A. Chip State → Variant & Mode Mapping [molecules.md → PopChip.kt]

Map each design `State` value to its `PopChipVariant` and `PopChipMode` combination.

Columns:
- Design System File (e.g., molecules.md)
- Design `State` Value (e.g., `Toggle`, `Cross`, `Dropdown`, `Switch`)
- Codebase `PopChipVariant` (e.g., `PopChipVariant.Basic`)
- Codebase `PopChipMode` (e.g., `PopChipMode.Toggleable`)
- Codebase File Name
- Notes
- Code Pattern

Include mappings for:
- `State=Toggle` → `PopChipVariant.Basic` + `PopChipMode.Toggleable` — simple on/off chip; no trailing indicator; active border appears when `isActive = true`
- `State=Cross` → `PopChipVariant.WithClose` + `PopChipMode.Toggleable` — shows dismiss (✕) icon when active; `onCloseClick` removes the chip or deselects; active border appears when `isActive = true`
- `State=Dropdown` → `PopChipVariant.WithDropdown` + `PopChipMode.Static` or `Toggleable` — trailing chevron icon; click handled via `onClick`; use `PopChipMode.Static` when tapping opens a picker (chip itself does not toggle), `Toggleable` when the chip also selects
- `State=Switch` → ⚠️ No direct `PopChipVariant` equivalent — `PopChipVariant` has `Basic`, `WithClose`, `WithDropdown` only; document the gap and recommended workaround

Note: `PopChipMode.Static` always shows inactive UI regardless of `isActive` — use when the chip is display-only or when selection is managed externally. `PopChipMode.Toggleable` internally tracks and toggles `isActive` on each click.

---

##### B. Chip State Flags Mapping [molecules.md → PopChipConfig]

Map `Is active` and `Is disabled` design properties to their config fields.

Columns:
- Design System File
- Design Property (e.g., `Is active`, `Is disabled`)
- Codebase Config Field (e.g., `PopChipConfig.isActive`, `PopChipConfig.enabled`)
- Default Value
- Codebase File Name
- Notes
- Code Pattern

Include mappings for:
- `Is active=True` → `isActive = true` — only meaningful in `PopChipMode.Toggleable`; shows animated border (`BorderColor.PrimaryInvert`) when active and enabled
- `Is active=False` → `isActive = false` (default)
- `Is disabled=True` → `enabled = false` — applies `SurfaceColor.SecondaryDisabled` background, `TextColor.Disabled`, `IconColor.Disabled`; click interactions removed
- `Is disabled=False` → `enabled = true` (default)

Note: When `Is disabled=True`, active border never shows regardless of `isActive` — `shouldShowActiveBorder = effectiveActiveState && config.enabled`. Disabled always overrides active styling.

---

##### C. Chip Leading Icon Mapping [molecules.md → PopChipConfig]

Columns:
- Design System File
- Design Property (`L-Icon`)
- Design Value (True / False)
- Codebase Config Field (`PopChipConfig.leadingIcon: PopIconConfig?`)
- Codebase File Name
- Notes
- Code Pattern

Include mappings for:
- `L-Icon=True` → `leadingIcon = PopIconConfig(iconName = Icons.*, style = IconStyle.Outline)` — icon is always 16dp (`IconSize.Small`); tint follows `enabled` state (`IconColor.Primary` / `IconColor.Disabled`)
- `L-Icon=False` → `leadingIcon = null` (default) — no leading icon rendered

---

## B: Chip Size Mapping Table

##### D. Chip Size Mapping [molecules.md → PopChip.kt / PopChipStack.kt]

Columns:
- Design System File
- Design Size Value (e.g., `Large`, `Med`)
- Codebase Enum Value (e.g., `PopChipStackSize.Large`)
- Vertical Padding (dp)
- Horizontal Padding (dp)
- Min Height (dp)
- Codebase File Name
- Notes
- Code Pattern

Include mappings for:
- `Size=Large` → `PopChipStackSize.Large` — `16dp` vertical padding in stack; individual chip min height `28dp` via `PopSpacing.Spacing28`
- `Size=Med` → `PopChipStackSize.Med` — `12dp` vertical padding in stack; individual chip min height `28dp`

Note: `PopChip` itself has no `size` parameter — chip size is a property of `PopChipStack` via `PopChipStackSize`. When placing a standalone `PopChip`, size is controlled by the chip's internal spacing tokens (`horizontalPadding = PopSpacing.Spacing10`, `verticalPadding = PopSpacing.Spacing6`, `minHeight = PopSpacing.Spacing28`) which are not overridable through config. Size selection via `PopChipStackSize` only takes effect inside `PopChipStack`.

---

## C: Chip Stack Property Mapping Table

##### E. Chip Stack Property Mapping [molecules.md → PopChipStack.kt]

Map every `Chip - Stack` design property to its `PopChipStack` parameter.

Columns:
- Design System File
- Design Property (e.g., `Count`, `Size`, `L-overflow`, `R-overflow`, `Divider`)
- Property Type (Variant / Boolean)
- Codebase Parameter (e.g., `chips: List<PopChipConfig>`, `size`, `showLeftOverflow`)
- Default Value
- Codebase File Name
- Notes
- Code Pattern

Include mappings for:
- `Count=2/3/4/5` → `chips = listOf(...)` with the corresponding number of `PopChipConfig` items — `PopChipStack` accepts a `List<PopChipConfig>` and renders each chip in sequence
- `Count=Plenty` → `chips = listOf(...)` with a dynamic/unknown-length list — stack is horizontally scrollable by default; use with `showRightOverflow = true` to indicate more chips off-screen
- `Size=Large` → `size = PopChipStackSize.Large` — `16dp` vertical padding around chips
- `Size=Med` → `size = PopChipStackSize.Med` — `12dp` vertical padding around chips
- `L-overflow=True` → `showLeftOverflow = true` — renders a `72dp` wide left gradient overlay (`SurfaceColor.Primary` → transparent) to indicate off-screen chips to the left
- `L-overflow=False` → `showLeftOverflow = false` (default)
- `R-overflow=True` → `showRightOverflow = true` — renders a `72dp` wide right gradient overlay (transparent → `SurfaceColor.Primary`) to indicate off-screen chips to the right
- `R-overflow=False` → `showRightOverflow = false` (default)
- `Divider=True` → `showDivider = true` (default) — renders a `PopDivider(Horizontal, Solid)` below the chip row
- `Divider=False` → `showDivider = false` — no divider below chip row

---

##### F. Chip Stack Selection Mode Mapping [molecules.md → PopChipStack.kt]

Columns:
- Design Context / Use Case
- Codebase Parameter (`singleSelect`)
- Codebase Value
- Codebase File Name
- Notes
- Code Pattern

Include mappings for:
- Single-select (e.g., sort options, mutually exclusive category filter) → `singleSelect = true` — selecting a chip deselects all others; deselects current chip if tapped again
- Multi-select (e.g., multiple filters active simultaneously) → `singleSelect = false` (default) — each chip toggles independently
- Selection callback → `onChipSelected: ((index: Int, chipConfig: PopChipConfig) -> Unit)?` — called after each tap with the updated chip config and its index in the list

---

## D: Colour & Token Mapping Table

##### G. Chip Colour Token Mapping [molecules.md → PopChip.kt]

Map all colour tokens used by `PopChip` to their design system equivalents.

Columns:
- Design State / Context
- Codebase Token
- Resolved Colour / Description
- Codebase File
- Notes

Include:
- Background — enabled → `SurfaceColor.Secondary`
- Background — disabled → `SurfaceColor.SecondaryDisabled`
- Label text — enabled → `TextColor.Primary`
- Label text — disabled → `TextColor.Disabled`
- Leading / trailing icon — enabled → `IconColor.Primary`
- Leading / trailing icon — disabled → `IconColor.Disabled`
- Active border — active + enabled → `BorderColor.PrimaryInvert` (animated with `PopStroke.Default` width and 1000ms dissolve)
- Active border — inactive or disabled → `Color.Transparent` (border width animates to `0.dp`)
- Chip gap (between chips in stack) → `PopSpacing.Spacing8` (8dp)
- Chip horizontal padding → `PopSpacing.Spacing10` (10dp)
- Chip vertical padding → `PopSpacing.Spacing6` (6dp)
- Icon size → `IconSize.Small` = 16dp (`PopIcons.sizeSmall`)

---

## E: Full Variant Mapping Tables

##### H. Chip Unit — All Variants [molecules.md → PopChip.kt]

Map every named chip variant to its complete `PopChip(config = PopChipConfig(...))` call.

Columns:
- Design Variant Name (e.g., `State=Toggle, Is active=False, Is disabled=False, L-Icon=False`)
- `variant`
- `mode`
- `isActive`
- `enabled`
- `leadingIcon`
- Full Code Pattern

Include representative variants:
1. State=Toggle, Is active=False, Is disabled=False, L-Icon=False — default chip
2. State=Toggle, Is active=True, Is disabled=False, L-Icon=False — selected filter chip
3. State=Toggle, Is active=False, Is disabled=True, L-Icon=False — disabled chip
4. State=Toggle, Is active=False, Is disabled=False, L-Icon=True — chip with leading icon
5. State=Toggle, Is active=True, Is disabled=False, L-Icon=True — selected chip with leading icon
6. State=Cross, Is active=True, Is disabled=False — applied filter with dismiss icon (active)
7. State=Cross, Is active=False, Is disabled=False — cross chip inactive (no border, close icon visible but chip not selected)
8. State=Dropdown, Is active=False, Is disabled=False — dropdown chip (Static mode, opens picker)
9. State=Dropdown, Is active=True, Is disabled=False — dropdown chip (Toggleable mode, selected + chevron)
10. State=Switch, Is active=False — ⚠️ no `PopChipVariant.Switch`; document gap + workaround

---

##### I. Chip Stack — All Variants [molecules.md → PopChipStack.kt]

Map every named stack variant to its complete `PopChipStack(...)` call.

Columns:
- Design Variant Name (e.g., `Count=3, Size=Large, Divider=True, L-overflow=False, R-overflow=False`)
- `chips` (list size / description)
- `size`
- `showDivider`
- `showLeftOverflow`
- `showRightOverflow`
- `singleSelect`
- Full Code Pattern

Include representative variants:
1. Count=2, Size=Large, Divider=True — default stack, 2 chips
2. Count=3, Size=Large, Divider=True — 3-chip filter row
3. Count=5, Size=Med, Divider=False — compact stack, no divider
4. Count=Plenty, Size=Large, Divider=True, R-overflow=True — scrollable stack with right fade
5. Count=Plenty, Size=Large, Divider=True, L-overflow=True, R-overflow=True — scrolled mid-position, both overflow fades
6. Count=3, Size=Large, Divider=True, singleSelect=True — single-select filter bar (sort chips)
7. Count=4, Size=Large, Divider=True, singleSelect=False — multi-select filter bar (category chips)

---

## Detailed Mapping Instructions

### For Each Mapping Table:

1. **Extract from Design System File (molecules.md):**
   - Read sections 8a (Chip) and 8b (Chip Stack)
   - Extract all property names, variant names, state values, boolean toggles, size options, and rules
   - Note which properties apply to the individual chip vs. the stack container

2. **Match to Codebase:**
   - **Chip composable**: `PopChip.kt` — `PopChip(modifier, config: PopChipConfig)`
   - **Chip config**: `PopChipConfig` — `text`, `enabled`, `isActive`, `variant`, `mode`, `leadingIcon`, `onClick`, `onCloseClick`
   - **Chip variant enum**: `PopChipVariant` — `Basic`, `WithClose`, `WithDropdown`
   - **Chip mode enum**: `PopChipMode` — `Toggleable`, `Static`
   - **Chip stack**: `PopChipStack.kt` — `PopChipStack(chips, size, showDivider, showLeftOverflow, showRightOverflow, singleSelect, onChipSelected)`
   - **Stack size enum**: `PopChipStackSize` — `Large`, `Med`

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Always use `PopChip(config = PopChipConfig(...))` — never raw `Box` + `Text` manually
   - Always use `PopChipStack(chips = listOf(...))` for groups of chips — never place individual `PopChip` composables side-by-side manually
   - Use `PopChipVariant.*` and `PopChipMode.*` enums — never hardcode strings
   - Use `PopChipStackSize.*` for stack size — never raw `padding(vertical = 16.dp)` directly
   - Use `Icons.*` for leading icon names — never hardcode resource IDs
   - Show real code examples, not pseudocode

### Mapping Priorities:

1. **MUST include 1:1 mappings** showing exact design-to-code translation for every property
2. **MUST show code patterns** for every variant row
3. **MUST document `PopChipMode.Toggleable` vs `PopChipMode.Static`** — Toggleable manages internal active state and shows border; Static always shows inactive UI regardless of `isActive`
4. **MUST document `State=Switch` gap** — no `PopChipVariant.Switch` exists in the codebase; flag this clearly and document the nearest workaround
5. **MUST document `State=Cross` click model** — two separate callbacks: `onClick` (root chip tap) and `onCloseClick` (the ✕ icon tap); both should be wired
6. **MUST document active border animation** — 1000ms dissolve with `CubicBezierEasing(0.7f, 0f, 0.3f, 1f)`; never replicate manually
7. **MUST document chip size gap** — `PopChip` has no size param; size is exclusively controlled through `PopChipStackSize` on `PopChipStack`; standalone chip uses fixed internal tokens
8. **MUST document `Count=Plenty`** — maps to a dynamic-length `chips` list with horizontal scrolling; always pair with `showRightOverflow = true` at initial render
9. **MUST document `singleSelect` vs multi-select** — `singleSelect = true` for mutually exclusive choices (sort, tabs); `singleSelect = false` (default) for independent toggles (filters)
10. **MUST document overflow gradient spec** — gradient overlay is `72dp` wide, actual gradient fade is `56dp`; background uses `SurfaceColor.Primary`

---

## Quality Standards

✓ **Accuracy:** All mappings verifiable against molecules.md, PopChip.kt, PopChipStack.kt, PopChipConfig.kt
✓ **Completeness:** All chip variants + all stack variants + all design properties mapped
✓ **Clarity:** Design property → codebase param/config field chains always shown
✓ **Usability:** Tables searchable by design property name and by codebase param/composable
✓ **Practical:** Every row includes a runnable `PopChip(...)` / `PopChipStack(...)` code pattern
✓ **Consistency:** Same column names and ordering throughout all tables
✓ **Gap documentation:** `State=Switch` and chip size-on-standalone gaps clearly flagged with ⚠️

---

## Output Format

- **File Type:** Markdown (.md)
- **Structure:** Hierarchical with clear sections — A (Variant/Mode/State), B (Size), C (Stack properties), D (Colour tokens), E (Full variants)
- **Tables:** Markdown table format for all mappings
- **Code blocks:** Kotlin code blocks (```kotlin) for all multi-line patterns
- **Length:** Comprehensive but organized — one table per mapping concern

---

## End of Master Prompt

**Generated bridge file should be named:** `chip_bridgefile.md`
