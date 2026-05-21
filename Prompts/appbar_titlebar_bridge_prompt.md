# Master Prompt: Inside "bridge_doc" folder add a file named "appbar_titlebar_bridgefile.md"

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "molecules.md"** (App bar and Title bar sections only) with the **POP Codebase Agentic Documentation - POP Codebase/ds_components/PopAppBar.kt, POP Codebase/ds_components/PopAppBarDsl.kt, POP Codebase/ds_components/PopAppBarManager.kt, POP Codebase/ds_components/PopTitleBar.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

---

## A: App Bar Property & Slot Mapping Tables

##### A. App Bar Type Mapping [molecules.md → PopAppBar.kt / PopAppBarManager.kt]

Columns:
- Design System File (e.g., molecules.md)
- Design Property Name (e.g., `Type=Default/Profile`, `R-Slot=False/True, R-icon=true/false`)
- Codebase Value / Config (e.g., `PopAppBarConfig`, `leftSlot`, `showNavigationIcon`)
- Codebase File Name (e.g., PopAppBar.kt)
- Code Pattern

##### B. App Bar Icon Slot Mapping [molecules.md → PopAppBarDsl.kt]

> The design `Icon button` sub-component maps to the `AppBarIconSlotsBuilder` DSL (slot1–slot4). Icon size is always fixed at 24px on the left navigation icon and 20px on the right action slots — never override this.

Columns:
- Design System File
- Design Property (e.g., `Count (L-icon) = 1`, `Count (R-icon) = 3`)
- Codebase DSL Method (e.g., `LeftSlot`, `config.iconSlots.slot3`)
- Codebase File Name (e.g., PopAppBarDsl.kt)
- Icon Size (px/dp)
- Code Pattern

Include mappings for:
- Count (L-icon) = 1 → `showNavigationIcon = true` + `navigationIcon = Icons.ChevronLeft` in `ConfigureAppBar`
- Count (R-icon) = 1 → `slot1(icon) { onClick() }` inside `ConfigureAppBarIcons { }`
- Count (R-icon) = 2 → `slot1(...)` + `slot2(...)` inside `ConfigureAppBarIcons { }`
- Count (R-icon) = 3 → `slot1(...)` + `slot2(...)` + `slot3(...)` inside `ConfigureAppBarIcons { }`
- Count (R-icon) = 4 → `slot1(...)` + `slot2(...)` + `slot3(...)` + `slot4(...)` inside `ConfigureAppBarIcons { }`

Note: Left navigation icon is always a single icon (back/close). `Count` on L-icon refers only to the navigation icon showing or being hidden — map to `showNavigationIcon: Boolean`.

---

## B: Title Bar Property Mapping Tables

##### D. Title Bar Property Mapping [molecules.md → PopTitleBar.kt]

Map every design property in molecules.md to its `PopTitleBarConfig` field.

Columns:
- Design System File (molecules.md)
- Design Property Name (e.g., `Title text`, `Body`, `L-icon Title`, `R-icon - Title, L-icon Body, R-icon Body, L-slot, R-slot`)
- Codebase enum Field (e.g., `text = config.titleText`, `config.bodyText`, `config.titleLeftIcon`, `config.titleRightIcon`,`config.bodyRightIcon`, `config.bodyLeftIcon` , `leftSlot` , `rightSlot`)
- Codebase File Name
- Code Pattern

Include all 10 design properties:
- `Title text` (Text) → `PopTitleBarConfig.titleText: String` — always required, never leave default "Title"
- `Body text` (Text) → `PopTitleBarConfig.bodyText: String?` — null when `Body=False`
- `Body` (Boolean) → `bodyText = null` when False; `bodyText = "..."` when True
- `L-slot` (Boolean) → `leftSlot` composable param on `PopTitleBar()` — typically `Icons.ChevronLeft` or `Icons.Cross`; no dedicated param in config, place via `PopTopBar`
- `L-icon - Title` (Boolean) → `PopTitleBarConfig.titleLeftIcon: IconName?` — null when False
- `R-icon - Title` (Boolean) → `PopTitleBarConfig.titleRightIcon: IconName?` — null when False
- `L-icon - Body` (Boolean) → `PopTitleBarConfig.bodyLeftIcon: IconName?` — null when False
- `R-icon - Body` (Boolean) → `PopTitleBarConfig.bodyRightIcon: IconName?` — call `onBodyRightIconClick` param on `PopTitleBar()`
- `R-slot` (Boolean) → `rightSlot` composable param on `PopTitleBar()` — accepts any `@Composable` (CTA button, avatar, icon)
- `R-overflow` (Boolean) → `PopTitleBarConfig.gradientRight = true` — activates right gradient fade for long text


## Detailed Mapping Instructions

### For Each Mapping Table:

1. **Extract from Design System File (molecules.md):**
   - Read the App bar section and Title bar section
   - Extract all property names, types (Text / Boolean / Variant), default values, slot behaviour, and usage rules
   - Note which properties are boolean toggles vs. variant selectors vs. text content

2. **Match to Codebase:**
   - **App bar composable**: `PopAppBar.kt` — `PopAppBar(modifier, config: PopAppBarConfig, rightSlot, leftSlot)`
   - **App bar DSL**: `PopAppBarDsl.kt` — `ConfigureAppBar`, `ConfigureAppBarIcons`, `ConfigureAppBarRightSlot`, `ConfigureAppBarLeftSlot`, `SetAppBarIcons`, `HideAppBarIcons`, `HideNavigationIcon`, `HideAppBarNavigation`, `ConfigureAppBarScrollScrim`
   - **App bar state manager**: `PopAppBarManager.kt` — `PopAppBarManager` class, `LocalPopAppBarManager`, `LocalPopAppBarHeight` (default 56dp), `GlobalPopAppBarContentHeight = 56.dp`
   - **App bar icon slots**: `AppBarIconSlotsBuilder` in `PopAppBarDsl.kt` — `slot1(icon, onClick)` through `slot4(icon, onClick)`; up to 4 right-side action icons, each 20dp
   - **Title bar composable**: `PopTitleBar.kt` — `PopTitleBar(modifier, config: PopTitleBarConfig, rightSlot, bodyTrailingSlot, onBodyRightIconClick)`
   - **Title bar config**: `PopTitleBarConfig` — all text, icon, marquee, gradient, spacing fields

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Always use `ConfigureAppBar` / `ConfigureAppBarIcons` DSL composables for setting up app bar in a screen — never call `PopAppBarManager` methods directly from UI code
   - Always use `PopTitleBarConfig` data class to configure `PopTitleBar` — never pass raw params
   - Use `Icons.*` for icon names — never hardcode resource IDs
   - `PopTitleBar` is a building block — it lives inside `PopTopBar` organism in final screens. Document standalone usage for bridge reference only
   - Show real code examples, not pseudocode

### Mapping Priorities:

1. **MUST include 1:1 mappings** showing exact design-to-code translation for every property
2. **MUST show code patterns** for every variant row
3. **MUST document the DSL hierarchy** — `ConfigureAppBar` (full) vs. `ConfigureAppBarIcons` (slots only) vs. `SetAppBarIcons` (simplified) — and when to use each
4. **MUST document lifecycle awareness** — DSL helpers only apply config when lifecycle is STARTED or RESUMED; auto-clean on STOP and disposal
5. **MUST document `Type=Profile` vs `Type=Default`** — Profile replaces the left navigation icon with a composable via `leftSlot`; use `ConfigureAppBarLeftSlot { PopAvatar(...) }`
6. **MUST document `R-slot` vs `R-icon`** distinction — `R-slot` maps to `rightSlot: @Composable` param; `R-icon` maps to `ConfigureAppBarIcons { slot1..slot4 }` DSL
7. **MUST document `PopTitleBar` placement rule** — Title bar is a molecule building block embedded inside the `PopTopBar` organism; document `PopTitleBar` standalone usage for reference but note it is never placed directly on screen
8. **MUST document `R-overflow`** — maps to `PopTitleBarConfig.gradientRight = true`; used for long/marquee titles
9. **MUST document `PopAppBarManager`** — accessed via `LocalPopAppBarManager`, provides `LocalPopAppBarHeight` and `GlobalPopAppBarContentHeight` for sticky component positioning

---

## Quality Standards

✓ **Accuracy:** All mappings verifiable against molecules.md, PopAppBar.kt, PopAppBarDsl.kt, PopAppBarManager.kt, PopTitleBar.kt
✓ **Completeness:** All App bar variants + all Title bar variants + all DSL helpers mapped
✓ **Clarity:** Design property → codebase param/config field chains always shown
✓ **Usability:** Tables searchable by design property name and by codebase composable/param
✓ **Practical:** Every row includes a runnable Kotlin code pattern
✓ **Consistency:** Same column names and ordering throughout all tables
✓ **Distinction:** `ConfigureAppBar` (full DSL) vs `ConfigureAppBarIcons` (slots only) vs `SetAppBarIcons` (simplified) are clearly differentiated

---

## Output Format

- **File Type:** Markdown (.md)
- **Structure:** Hierarchical with clear sections — A (App bar properties/slots/DSL), B (Title bar properties/typography), C (Full variants)
- **Tables:** Markdown table format for all mappings
- **Code blocks:** Kotlin code blocks (```kotlin) for all multi-line patterns
- **Length:** Comprehensive but organized — one table per mapping concern

---

## End of Master Prompt

**Generated bridge file should be named:** `appbar_titlebar_bridgefile.md`
