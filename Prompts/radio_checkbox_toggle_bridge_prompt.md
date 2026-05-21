# Master Prompt: Inside "bridge_doc" folder add a file named "radio_checkbox_toggle_bridgefile.md"

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "atoms2.md"** (Radio, Checkbox, and Toggle sections only) with the **POP Codebase Agentic Documentation - POP Codebase/ds_components/PopToggle.kt, POP Codebase/ds_components/PopOfferToggle.kt, POP Codebase/ds_components/PopRadio.kt, POP Codebase/ds_components/PopCheckBoxV2.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

---

## A: Enum & State Mapping Tables

##### A. Toggle State & Size Mapping [atoms2.md → PopToggle.kt]

Columns:
- Design System File Name (e.g., atoms2.md)
- Design Property Name (e.g., State=On, Size=Large , enable/disable, promoted )
- Codebase Enum / Value (e.g., `PopToggleState.On`, `PopToggleSize.Large`)
- Codebase File Name (e.g., PopToggle.kt)
- Resolved Dp Value (e.g., `48×28dp`)
- Code Pattern

Include mappings for:
- State: On → `PopToggleState.On` (map for both in design system and codebase) 
- State: Off → `PopToggleState.Off`(map for both design system and codebase)
- State: Indeterminate → `PopToggleState.Indeterminate` (animated pill, use state-based overload)
- Size: Large → `PopToggleSize.Large` → trackWidth=40dp, containerHeight=28dp, knobWidth=24dp
- Size: Medium → `PopToggleSize.Medium` → trackWidth=32dp, containerHeight=24dp, knobWidth=20dp
- disable/enable (Boolean) 
- Promoted (Boolean)


##### B. Checkbox State & Align Mapping [atoms2.md → PopCheckBoxV2.kt]

Columns:
- Design System File
- Design Property Name (e.g., State=Selected, Align=Left , enable/disable=true, promoted=true )
- Codebase Enum / Value (e.g., `PopCheckBoxState.Selected`, `PopCheckBoxAlign.Left`, `enabled: Boolean = true`)
- Codebase File Name (e.g., PopCheckBoxV2.kt)
- Code Pattern

Include mappings for:
- State: Selected → `PopCheckBoxState.Selected`
- State: Unselected → `PopCheckBoxState.Unselected`
- State: Indeterminate → `PopCheckBoxState.Indeterminate`
- disable/enable (Boolean) 
- Promoted (Boolean)
---


## B: Radio Variant Mapping Table

##### D. Radio — Base Variants [atoms2.md → PopRadio.kt]

Map every named base Radio variant from atoms2.md to its exact `PopRadio()` call.

Columns:
- Design System File Name (e.g., atoms2.md)
- Design Property Name (as shown in atoms2.md, e.g., `State=On/Off, Promoted=True/False, disabled=true/false`)
- Codebase Enum / Value (e.g., selected: Boolean, promoted: Boolean = false , enabled: Boolean = true )
- Codebase File Name (e.g., PopToggle.kt)
- Fixed Size 
- Code Pattern

Include mappings for:
- State: On → `PopRadio.Selected`
- State: Off → `PopRadio.Unselected`
- disable/enable (Boolean) 
- Promoted (Boolean)

Note: RadioButton has a single fixed size only — 20dp outer ring, 8dp inner dot. There is no size enum for Radio.

---

##### E. Radio with Text — Variants [atoms2.md → PopRadio.kt]

Note: Map `PopRadioButton.kt to atoms2.md (Radio with text)`


## C: Checkbox Variant Mapping Tables

##### F. Checkbox — Base Variants [atoms2.md → PopCheckBoxV2.kt]

Map every named base Checkbox variant from atoms2.md to its exact `PopCheckBoxV2()` call.

Columns:
- Design file name(`atom2.kt`)
- Design Variant Name (e.g., `State=On/Off, Promoted=False` , `Is disabled = False/True` , `Indeterminate=False/True`)
- Codebase enum value (e.g.,`checked: Boolean`, `Promoted=False` , `enabled: Boolean = true/false` , `no code equivalent`)
- Codebase File Name (e.g., `PopCheckbox.kt`)
- Fixed Size
- Code Pattern

Include mappings for above columns

Note: Shape is always `RoundedCornerShape(PopRadius.ExtraSmall)` = 4dp corners. Control size is always 20dp (8dp icon + 6dp padding each side). No size enum.

---

##### G. Checkbox with Text — Variants [atoms2.md → PopCheckBoxWithTextV2.kt]

Note: Map `PopCheckBoxV2.kt to atoms2.md (CheckBox with text)`

---


## Detailed Mapping Instructions

### For Each Mapping Table:

1. **Extract from Design System File (atoms2.md):**
   - Read the Radio, Checkbox, and Toggle sections
   - Extract all variant names, property names (State, Size, Promoted, Is disabled, Align, Slot, Count, Type), and any sizing or spacing rules
   - Note which properties are present on the base control vs. the "with Text" variant vs. the Stack variant

2. **Match to Codebase:**
   - **Radio**: `PopRadio.kt` — `PopRadio(selected, onClick, promoted, enabled)` only; no size param; single fixed 20dp size
   - **Checkbox**: `PopCheckBoxV2.kt` — `PopCheckBoxState`, `PopCheckBoxAlign`; `PopCheckBoxV2()` for control only; `PopCheckBoxWithTextV2()` for text layout
   - **Toggle**: `PopToggle.kt` — `PopToggleSize`, `PopToggleState`; two overloads (Boolean and State); promoted and indeterminate flags
   - **OfferToggle**: `PopOfferToggle.kt` — `OfferType`; wraps `PopToggle(size=Medium)` internally; not a base toggle

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Every pattern must use the correct composable (`PopRadio`, `PopCheckBoxV2`, `PopCheckBoxWithTextV2`, `PopToggle`, `PopOfferToggle`) — never raw Material `Checkbox()`, `RadioButton()`, or `Switch()`
   - Use `PopToggleSize.*` and `PopToggleState.*` enums — never raw dp for toggle sizing
   - Use `PopCheckBoxState.*` and `PopCheckBoxAlign.*` enums
   - Use `OfferType.*` enum — never pass raw string for offer type
   - Show real code examples, not pseudocode

### Mapping Priorities:

1. **MUST include 1:1 mappings** showing exact design-to-code translation for every variant listed above
2. **MUST show code patterns** for every variant row
3. **MUST document Indeterminate state** for both Toggle (via `PopToggleState.Indeterminate`) and Checkbox (via `PopCheckBoxState.Indeterminate`) — Radio has no indeterminate state
4. **MUST document promoted behaviour** for all three components: Radio, Checkbox, and Toggle each have a `promoted` flag that activates brand gradient + glow
5. **MUST document OfferToggle** as a separate, specialized component — not interchangeable with `PopToggle`; its internal toggle size is always Medium and not configurable by the caller
6. **MUST document "with Text" assembly** for Radio (caller-assembled Row) vs. Checkbox (native `PopCheckBoxWithTextV2` composable) — these differ structurally
7. **MUST document Stack variants** from atoms2.md (Radio Stack, Checkbox Stack) as a `Column` of `PopRadio`/`PopCheckBoxV2` composables — no dedicated Stack composable exists in codebase
8. **MUST note size availability**: Toggle has `PopToggleSize` enum (Large/Medium); Radio has no size enum (single fixed size); Checkbox has no size enum (single fixed 20dp control)

---

## Quality Standards

✓ **Accuracy:** All mappings verifiable against atoms2.md, PopToggle.kt, PopRadio.kt, PopCheckBoxV2.kt, PopOfferToggle.kt
✓ **Completeness:** All base variants + "with Text" variants + promoted/disabled/indeterminate states mapped
✓ **Clarity:** Enum chains always shown: Design property → Codebase enum → visual effect
✓ **Usability:** Tables searchable by design variant name and by codebase composable/param
✓ **Practical:** Every row includes a runnable Kotlin code pattern
✓ **Consistency:** Same column names and ordering throughout all tables
✓ **Distinction:** OfferToggle clearly separated from base Toggle — different use case, different composable

---

## Output Format

- **File Type:** Markdown (.md)
- **Structure:** Hierarchical with clear sections — A (Enum/State), B (Radio), C (Checkbox), D (Toggle)
- **Tables:** Markdown table format for all mappings
- **Code blocks:** Kotlin code blocks (```kotlin) for all multi-line patterns
- **Length:** Comprehensive but organized — one table per mapping concern

---

## End of Master Prompt

**Generated bridge file should be named:** `radio_checkbox_toggle_bridgefile.md`
