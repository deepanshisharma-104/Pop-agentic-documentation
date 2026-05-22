# Master Prompt: Inside "bridge_doc" folder add a file named "chip_bridgefile.md"

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "molecules.md"** (Chip and Chip Stack sections — 8a and 8b only) with the **POP Codebase Agentic Documentation - POP Codebase/ds_components/PopChip.kt, POP Codebase/ds_components/PopChipStack.kt, POP Codebase/models/PopChipConfig.kt , POP Codebase/models/PopChipConfig.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

---

## A: Chip Variant & Mode Mapping Tables

##### A. Chip State → Variant & Mode Mapping [molecules.md → PopChip.kt]

Map each design `State` value to its `PopChipVariant` and `PopChipMode` combination.

Columns:
- Design System File (e.g., molecules.md)
- Design property Name (e.g., [State:`Toggle`, `Cross`, `Dropdown`, `Switch`],[is active:true/false], [is diabled:true/false], [`L-icon:false/true`] )
- Codebase `PopChipVariant` (e.g.,`config.variant == PopChipVariant.WithDropdown` ,`internalActiveState = config.isActive` ,`config.mode = config.enabled` , ` config.leadingIcon`)
- Codebase File Name (`PopChip.kt`, `PopChipConfig.kt` )
- Code Pattern
### Note : Extract icon from this file ('bridge_doc/icon_files.md`)

Note: `PopChipMode.Static` always shows inactive UI regardless of `isActive` — use when the chip is display-only or when selection is managed externally. `PopChipMode.Toggleable` internally tracks and toggles `isActive` on each click.


## C: Chip Stack Property Mapping Table

##### E. Chip Stack Property Mapping [molecules.md → PopChipStack.kt]

Map every `Chip - Stack` design property to its `PopChipStack` parameter.

Columns:
- Design System File (molecules.md)
- Design Property (e.g., `Count`, `Size`, `L-overflow`, `R-overflow`, `Divider`)
- Codebase Enum Value (e.g., ` ` ,  `PopChipStackSize.Large` , `showLeftOverflow: Boolean = false` , `showRightOverflow: Boolean = false` , `showDivider: Boolean = true`)
- Codebase File Name (`PopChipStack.kt`)
- Code Pattern

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
