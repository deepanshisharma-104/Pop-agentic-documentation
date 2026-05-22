# Master Prompt: Inside "bridge_doc" folder add a file named "tab_bridgefile.md"

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "molecules.md"** (Tab sections 9a and 9b — Tab Unit and Tab Stack only) with the **POP Codebase Agentic Documentation - POP Codebase/ds_components/PopTabLayout.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

> **Scope:** Map only `Tab Unit` (9a) and `Tab Stack` (9b) — the underline-style tab bar that `PopTabLayout.kt` implements. Sections 9c–9g (Pill, Visual, Fancy variants) are out of scope as they do not map to `PopTabLayout.kt`.

---

## A: Tab Item Property Mapping Tables

##### A. Tab Unit Property Mapping [molecules.md → PopTabLayout.kt]

Map every design property of the `Tab Unit` (9a) to its field on `PopTabItem`.

Columns:
- Design System File (e.g., `molecules.md`)
- Design Property Name (e.g., `Is active`, `Is disabled`, `Title text`, `L-icon - Title`, `R-icon - Title`)
- Codebase Enum Name (e.g.,`enabled: Boolean = true` `PopTabItem.enabled`,` text = tab.label` ,`tab.leftIcon` ,`tab.rightIcon`)
- Codebase File Name(`PopTabLayout.kt`)
- Code Pattern

##### B. Tab Stack Width Mode Mapping [molecules.md → PopTabLayout.kt]

Map design layout behaviour to `PopTabLayoutWidth`.

Columns:
- Design System File (e.g., `molecules.md`)
- Design Property name (e.g. `Count:1,2,3 etc.`, `R overflow: False/True`, `L overflow- True/False`, `Switch tab:True/False`)
- Codebase Enum Value (e.g.,`take variable name from this file PopTabBar.kt inside internal library` )
- Codebase File Name (`PopTabBar.kt`)
- Code Pattern

## Detailed Mapping Instructions

### For Each Mapping Table:

1. **Extract from Design System File (molecules.md):**
   - Read sections 9a (Tab Unit) and 9b (Tab Stack) only
   - Extract all property names, variant values, state options, layout modes, and rules
   - Note which properties belong to the individual tab unit (`PopTabItem`) vs. the stack container (`PopTabLayout`)

2. **Match to Codebase (`PopTabLayout.kt`):**
   - **Tab layout composable**: `PopTabLayout(tabs, selectedTabId, onTabSelected, modifier, widthMode, colors, enableSwipe, swipeThreshold, showDivider)`
   - **Tab item data class**: `PopTabItem(id, label, enabled, leftIcon, rightIcon)`
   - **Width mode enum**: `PopTabLayoutWidth` — `Equal` (fixed-width `TabRow`) / `Wrap` (scrollable `ScrollableTabRow`)
   - **Colours**: `PopTabLayoutColors` via `PopTabLayoutDefaults.colors(indicatorColor, selectedTextColor, unselectedTextColor, disabledTextColor)`
   - **Dimension constants**: `PopTabLayoutDefaults` — `TabHeight`, `HorizontalPadding`, `VerticalPadding`, `TextHorizontalPadding`, `IconSize`, `EdgePadding`

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Always use `PopTabLayout(tabs = listOf(...), selectedTabId = ..., onTabSelected = ...)` — never use raw Material3 `TabRow` or `ScrollableTabRow` directly
   - Every tab must be a `PopTabItem` — never a raw `Text` or custom composable in a `Row`
   - Always supply a unique `id` per `PopTabItem` — never use index as ID
   - Use `PopTabLayoutDefaults.colors(...)` to override colours — never pass raw `Color(...)` directly to `PopTabLayout` outside of `colors`
   - Use `PopTabLayoutDefaults.*` constants for dimension reference — never hardcode `56.dp`, `20.dp`, etc.
   - Show real code examples, not pseudocode

### Mapping Priorities:

1. **MUST include 1:1 mappings** showing exact design-to-code translation for every property
2. **MUST show code patterns** for every variant row
3. **MUST document `PopTabLayoutWidth.Equal` vs `PopTabLayoutWidth.Wrap`** — Equal uses `TabRow` (fixed widths); Wrap uses `ScrollableTabRow` (content-width tabs, scrollable); choosing the wrong mode causes layout bugs
4. **MUST document selection model** — selection is driven by `selectedTabId: String` on the layout composable, not a boolean on `PopTabItem`; always manage `selectedTabId` in the caller's state
5. **MUST document `Switch tab - Page swipe` mapping** — maps to `enableSwipe = true`; must be paired with a `HorizontalPager` or equivalent in the screen to keep the tab selection and the paged content in sync
6. **MUST document `L-overflow` / `R-overflow` gap** — design shows explicit overflow fade indicators; `PopTabLayout` does not expose gradient params — overflow is implicit in `ScrollableTabRow`; flag this gap clearly
7. **MUST document `Is disabled` model** — `PopTabItem.enabled = false` prevents clicks and applies disabled color; clicking a disabled tab does nothing; a disabled tab cannot be the `selectedTabId`
8. **MUST document icon resource type** — `leftIcon` / `rightIcon` are `@DrawableRes Int?` (not `IconName`); pass `R.drawable.*` resource IDs
9. **MUST document `showDivider`** — renders `PopDivider(Horizontal)` below tabs; defaults to `false`; do not add a manual divider when using `showDivider = true`
10. **MUST document `NoRippleIndicationNodeFactory`** — tap ripple is suppressed on all tabs; the underline indicator is the only active-state visual feedback

---

## Quality Standards

✓ **Accuracy:** All mappings verifiable against molecules.md (9a, 9b) and PopTabLayout.kt
✓ **Completeness:** All Tab Unit states + all Tab Stack variants + all design properties mapped
✓ **Clarity:** Design property → codebase param chains always shown; colour hex values included
✓ **Usability:** Tables searchable by design property name and by codebase param/composable
✓ **Practical:** Every row includes a runnable `PopTabLayout(...)` / `PopTabItem(...)` code pattern
✓ **Consistency:** Same column names and ordering throughout all tables
✓ **Gap documentation:** `L-overflow`/`R-overflow` gradient and icon resource type (`@DrawableRes Int`) gaps clearly flagged

---

## Output Format

- **File Type:** Markdown (.md)
- **Structure:** Hierarchical with clear sections — A (Tab item / stack properties), B (Colour tokens), C (Dimensions / Typography), D (Full variants)
- **Tables:** Markdown table format for all mappings
- **Code blocks:** Kotlin code blocks (```kotlin) for all multi-line patterns
- **Length:** Comprehensive but organized — one table per mapping concern

---

## End of Master Prompt

**Generated bridge file should be named:** `ctab_bridgefile.md`
