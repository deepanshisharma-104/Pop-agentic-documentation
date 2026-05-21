# Master Prompt: Inside "bridge_doc" folder add a file named "inputfield_bridgefile.md"

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "molecules.md"** (Input Field section 11 — all sub-components 10a through 10k) with the **POP Codebase Agentic Documentation - POP Codebase/ds_components/PopInputFieldV2.kt, POP Codebase/ds_components/PopInputFieldHelpers.kt, POP Codebase/models/PopInputFieldConfig.kt, POP Codebase/models/InputFieldStatus.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

---

## A: Component Type Mapping Table

##### A. Input Field Type Mapping [molecules.md → PopInputFieldConfig.kt]

Map every design component variant to its corresponding config class and `PopInputFieldV2` invocation.

Columns:
- Design System File (e.g., molecules.md)
- Design Component Name (e.g., `Input field`, `Input field - Small`, `Input field - Naked Large`)
- Design Section (e.g., 10a, 10b)
- Codebase Config Class (e.g., `BasicInputFieldConfig`, `UnderlineNakedSmallConfig`)
- Codebase File Name
- `PopInputFieldType` enum value (legacy param — for reference only)
- Notes
- Code Pattern

Include mappings for all components:
- `Input field` (10a) → `BasicInputFieldConfig` — standard rounded-rectangle form input with floating `hintText`, start/end icons, end slot, status message
- `Input field - Small` (10b) → `SmallInputFieldConfig` — compact form input; `title` animates to top on focus
- `Input field - Naked Large` (10c) → `UnderlineNakedLargeConfig` — large borderless underline input for amount/currency; supports `useCustomKeypad = true` for PopKeyPad integration
- `Input field - Naked Small` (10d) → `UnderlineNakedSmallConfig` — compact borderless underline input; `title` is **required** (compile-time enforced)
- `Input - Search` (10h) → `SearchInputFieldConfig` — search bar; includes built-in clear button, animated placeholder, `endSlot` for trailing chip; `searchBorderStyle` controls border appearance
- `Input - Mobile` (10i) → `MobileInputFieldConfig` — phone number input; flag + `+91` prefix built-in; always uses `KeyboardType.Phone`; do not add prefix separately
- `Input item - Discrete` (10e) → ⚠️ Not covered by `PopInputFieldV2` — single OTP cell; document gap
- `Input organism - Discrete` (10f) → ⚠️ Not covered by `PopInputFieldV2` — full OTP organism; document gap
- `Input organism - Discrete - Naked` (10g) → ⚠️ Not covered by `PopInputFieldV2` — naked OTP organism; document gap
- `Help Text` (10j) → `StatusMessage(status, message)` in `PopInputFieldHelpers.kt` — rendered automatically below input when `statusMessage` is set; can also be used standalone
- `Input stack (horizontal)` (10k) → ⚠️ No dedicated composable — assemble as a `Row { PopInputFieldV2(...); Spacer; PopInputFieldV2(...) }` in the caller

> **Rule:** Always use the config-based overload `PopInputFieldV2(config = YourConfig(...))` — the parameter-based overload is `@Deprecated` and should not be used in new code.

---

## B: Design State → Codebase Status Mapping Tables

##### B. Input Field State Mapping [molecules.md → InputFieldStatus.kt]

Map every design `State` value to its codebase equivalent.

Columns:
- Design System File
- Design `State` Value (e.g., `Default`, `Error`, `Success`, `Disabled`, `Warning`)
- Codebase Param / Value (e.g., `status = null`, `status = InputFieldStatus.Error`, `enabled = false`)
- Status Icon (from `PopInputFieldHelpers.kt`)
- Status Text Color Token
- Codebase File Name
- Notes
- Code Pattern

Include mappings for:
- `State=Default` → `status = null` — no status message shown; field is idle
- `State=Error` → `status = InputFieldStatus.Error` + `statusMessage = "Error message"` — `Icons.AlertHexagon` icon, `TextColor.Destructive` text
- `State=Success` → `status = InputFieldStatus.Success` + `statusMessage = "Success message"` — `Icons.CheckVerified` icon, `TextColor.Success` text
- `State=Warning` → `status = InputFieldStatus.Warning` + `statusMessage = "Warning message"` — `Icons.AlertCircle` icon, `TextColor.Warning` text — **available only on `Input field - Naked Large` (10c)**
- `State=Disabled` → `enabled = false` in config — field is non-interactive; disabled visual treatment applied internally

Note: `Info` (`InputFieldStatus.Info`) is a codebase-only status (used in `Help Text` standalone) — not a design variant on the input field itself. Maps to `Icons.InfoSquare` + `TextColor.Tertiary`.

---

##### C. Is Active / Is Filled State Mapping [molecules.md → PopInputFieldV2.kt]

Map the design `Is active` and `Is filled` properties to their codebase equivalents.

Columns:
- Design System File
- Design Property (`Is active`, `Is filled`)
- Design Value
- Codebase Equivalent
- Codebase File Name
- Notes

Include:
- `Is active=True` → Focus state — managed internally by `BasicTextField`'s `onFocusChanged`; not a config param; the field gains focus via `FocusRequester` or direct tap
- `Is active=False` → Unfocused state — default; no action needed in config
- `Is filled=True` → `value.isNotEmpty()` — derived from `config.value` length; not a separate boolean config param; the floating label / hint animation and cursor visibility are driven by this internally
- `Is filled=False` → `value = ""` — empty string; placeholder shown

Note: `State`, `Is active`, and `Is filled` are **independent axes** — apply all three simultaneously in code. Do not treat them as a switch-else chain. Example: `State=Error + Is filled=True + Is active=True` maps to `status = InputFieldStatus.Error`, non-empty `value`, and `focusRequester.requestFocus()`.

---

## C: Per-Config Property Mapping Tables

##### D. BasicInputFieldConfig — Property Mapping [molecules.md 10a → PopInputFieldConfig.kt]

Map every `Input field` (10a) design property to its `BasicInputFieldConfig` field.

Columns:
- Design Property
- Property Type
- Codebase Field
- Default Value
- Notes
- Code Pattern

Include:
- `Title text` → `hintText: String` — animates to floating label position when field is focused or filled; defaults to `""`
- `Placeholder text` → `placeholder: String?` — hint shown inside field when empty and focused; null hides it
- `L-icon=True` → `startIcon: PopIconConfig?` — leading icon inside the input container
- `L-icon=False` → `startIcon = null` (default)
- `R-icon=True` → `endIcon: PopIconConfig?` — trailing icon inside the container (e.g. eye for password, clear ✕)
- `R-icon=False` → `endIcon = null` (default)
- `R-slot=True` → `endSlot: (@Composable () -> Unit)?` — trailing custom composable (e.g. OTP resend link, inline CTA)
- `R-slot=False` → `endSlot = null` (default)
- `State` → `status: InputFieldStatus?` + `enabled: Boolean` — see Section B
- `Info text` / `Error text` / `Success text` → `statusMessage: String?` — single field; value set to the appropriate message string for the current `status`
- `Pre-filled text` (prefix label) → ⚠️ `BasicInputFieldConfig` has no `prefixText` param — implement as part of `startIcon` slot or use `UnderlineNakedSmallConfig` / `UnderlineNakedLargeConfig` with prefix handling
- `Post-filled text` (suffix label) → ⚠️ No `suffixText` param — implement in `endSlot` or use `hintText` formatting
- `BG=True/False` → ⚠️ Not a config param — background rendering is internal to the composable
- `R-overflow` → ⚠️ Not a config param — horizontal gradient fade is applied internally via `horizontalGradientFade` utility

---

##### E. UnderlineNakedSmallConfig — Property Mapping [molecules.md 10d → PopInputFieldConfig.kt]

Map every `Input field - Naked Small` (10d) design property to `UnderlineNakedSmallConfig`.

Columns:
- Design Property
- Property Type
- Codebase Field
- Default Value
- Notes
- Code Pattern

Include:
- `Title` (Boolean) + `Title text` → `title: String` — **required**; compile-time enforced; pass the field label string even when `Title=False` in design (codebase always requires it)
- `Placeholder text` → `placeholder: String?`
- `L-icon=True` → `startIcon: PopIconConfig?`
- `R-icon=True` → `endIcon: PopIconConfig?`
- `R-slot=True` → `endSlot: (@Composable () -> Unit)?`
- `State` → `status: InputFieldStatus?` + `enabled: Boolean`
- `Info text` / `Error text` / `Success text` → `statusMessage: String?`
- `Pre-filled text` / `Post-filled text` → ⚠️ No dedicated prefix/suffix param — implement in caller or as `startIcon`/`endSlot`

---

##### F. UnderlineNakedLargeConfig — Property Mapping [molecules.md 10c → PopInputFieldConfig.kt]

Map every `Input field - Naked Large` (10c) design property to `UnderlineNakedLargeConfig`.

Columns:
- Design Property
- Property Type
- Codebase Field
- Default Value
- Notes
- Code Pattern

Include:
- `Is active` focus → `shouldRequestFocus: Boolean` — set `true` to auto-request focus on composition; `onFocusReceived: () -> Unit` callback fires when focus is granted
- `Placeholder text` → `placeholder: String?`
- `State=Warning` → `status = InputFieldStatus.Warning` — **Warning state is unique to this variant** (10c)
- `Info text` / `Error text` / `Success text` / `Warning text` → `statusMessage: String?`
- `Title=True` + `Title text` → ⚠️ `UnderlineNakedLargeConfig` has no `title` field — title is not rendered by this config; use a separate `Text` composable above `PopInputFieldV2` when `Title=True`
- `Pre-filled` (prefix ₹) / `Post-filled` (.00) → ⚠️ No prefix/suffix param — implement as adjacent `Text` composables in the caller's `Row`
- Custom keypad (payment screens) → `useCustomKeypad = true` — hides system keyboard; connect to `PopKeyPad` via `value`/`onValueChange`; use `handleKeyPadInput` helper to process key presses

---

##### G. SmallInputFieldConfig — Property Mapping [molecules.md 10b → PopInputFieldConfig.kt]

Map every `Input field - Small` (10b) design property to `SmallInputFieldConfig`.

Columns:
- Design Property
- Property Type
- Codebase Field
- Default Value
- Notes
- Code Pattern

Include:
- `Title` text → `title: String?` — nullable; floats to top on focus
- `Placeholder text` → `placeholder: String?`
- `State` → `status: InputFieldStatus?` + `enabled: Boolean`
- `Info text` / `Error text` / `Success text` → `statusMessage: String?`
- `Pre-filled` / `Post-filled` / `L-icon` / `R-icon` / `R-slot` → ⚠️ `SmallInputFieldConfig` has no icon or slot params — implement icons/slots in the caller if required

---

##### H. SearchInputFieldConfig — Property Mapping [molecules.md 10h → PopInputFieldConfig.kt]

Map every `Input - Search` (10h) design property to `SearchInputFieldConfig`.

Columns:
- Design Property
- Property Type
- Codebase Field
- Default Value
- Notes
- Code Pattern

Include:
- `Placeholder text` → `placeholder: String?`
- Animated placeholder → `animatedPlaceholder: (@Composable () -> Unit)?` — custom composable shown in place of static placeholder
- `State` → `status: InputFieldStatus?` + `enabled: Boolean`
- Status message → `statusMessage: String?`
- `Keypad icon=True` → ⚠️ No dedicated param — `Keypad icon` is a design property with no direct codebase equivalent on the config; document gap
- Border style → `searchBorderStyle: SearchBorderStyle` — `SearchBorderStyle.Subtle` (default, soft border) or `SearchBorderStyle.DefinedThin` (visible thin border)
- Trailing component → `endSlot: (@Composable () -> Unit)?` — trailing chip, filter badge, or any composable
- Clear button → `onClearClick: (() -> Unit)?` — custom handler; defaults to `{ onValueChange("") }` when null
- Focus change → `onFocusChanged: (Boolean) -> Unit` — fires `true` on focus gained, `false` on focus lost

---

##### I. MobileInputFieldConfig — Property Mapping [molecules.md 10i → PopInputFieldConfig.kt]

Map every `Input - Mobile` (10i) design property to `MobileInputFieldConfig`.

Columns:
- Design Property
- Property Type
- Codebase Field
- Default Value
- Notes
- Code Pattern

Include:
- `Title text` → `title: String` — **required**; label above the field
- `Placeholder text` → `placeholder: String?`
- `State` → `status: InputFieldStatus?` + `enabled: Boolean`
- Status message → `statusMessage: String?`
- Flag + `+91` prefix → built-in; do NOT add separately; `keyboardType` defaults to `KeyboardType.Phone`
- Focus / blur → `onFocusReceived: () -> Unit` + `onFocusChanged: (Boolean) -> Unit`

---

## D: Help Text (StatusMessage) Mapping Table

##### J. Help Text Property Mapping [molecules.md 10j → PopInputFieldHelpers.kt]

Map every `Help Text` (10j) design property to `StatusMessage()` parameters.

Columns:
- Design System File
- Design Property (e.g., `Type`, `L-Icon`, `R-icon`, `Padding`, `Link text`, `R-Overflow`)
- Property Type
- Codebase Param / Component
- Default Value
- Codebase File Name
- Notes
- Code Pattern

Include:
- `Type=Info` → `status = InputFieldStatus.Info` → `Icons.InfoSquare` + `TextColor.Tertiary`
- `Type=Success` → `status = InputFieldStatus.Success` → `Icons.CheckVerified` + `TextColor.Success`
- `Type=Error` → `status = InputFieldStatus.Error` → `Icons.AlertHexagon` + `TextColor.Destructive`
- `Type=Warning` → `status = InputFieldStatus.Warning` → `Icons.AlertCircle` + `TextColor.Warning`
- `L-Icon=True` → `showIcon = true` (default) — icon rendered at `IconSize.Small` with `4dp` spacing
- `L-Icon=False` → `showIcon = false`
- `R-icon` → ⚠️ `StatusMessage` has no `endIcon` param — trailing icon not natively supported; pass via `message` text or custom composable wrapper
- `Padding=12/8/0` → ⚠️ `StatusMessage` applies a fixed `4dp` top padding internally — custom top padding must be applied via `Modifier.padding(top = ...)` on the caller
- `Link text=True` → `onEditClick: (() -> Unit)?` — shows an inline "Edit" label with dotted underline (`InlineDottedUnderlineText`) when non-null
- `R-Overflow` → ⚠️ Not a param — overflow gradient not supported in `StatusMessage`; document gap

Typography:
- Default status text style: `PopTypography.figtreeStyles.labelXSmall` (12sp, Medium) — `StatusTextStyle`
- Custom style: pass `textStyle` param to override
- Horizontal alignment: `horizontalArrangement: Arrangement.Horizontal` — default `Arrangement.Start`; use `Arrangement.Center` for centered help text

---

## E: Typography & Token Mapping Table

##### K. Input Field Typography Mapping [molecules.md → PopInputFieldHelpers.kt]

Columns:
- Design Text Element
- Codebase Token / TextStyle
- Font Size
- Font Weight
- Codebase File
- Notes

Include:
- Title / hint label → `TitleTextStyle` = `PopTypography.figtreeStyles.labelSmall` — 14sp, Medium
- Body / value text → `BodyTextStyle` = `PopTypography.figtreeStyles.paragraphMedium` — 16sp, Medium
- Status / helper text → `StatusTextStyle` = `PopTypography.figtreeStyles.labelXSmall` — 12sp, Medium

---

##### L. Input Field Colour & Token Mapping [molecules.md → PopInputFieldV2.kt / PopInputFieldHelpers.kt]

Columns:
- Design Context / Element
- Codebase Color Token
- Notes

Include:
- Status message — Error text → `TextColor.Destructive`
- Status message — Success text → `TextColor.Success`
- Status message — Info text → `TextColor.Tertiary`
- Status message — Warning text → `TextColor.Warning`
- Status icon size → `IconSize.Small` (`STATUS_ICON_SIZE`)
- Status icon spacing → `PopSpacing.Spacing4` (`STATUS_ICON_SPACING`) = 4dp
- Animation duration (focus state transitions) → `DEFAULT_ANIMATION_DURATION` = 200ms

---

## F: Full Variant Mapping Tables

##### M. Input Field — All Primary Variants [molecules.md 10a → PopInputFieldV2.kt]

Map every named `Input field` (10a) variant to its complete `PopInputFieldV2(config = BasicInputFieldConfig(...))` call.

Columns:
- Design Variant Name
- Config Class
- Key params set
- Full Code Pattern

Include representative variants:
1. State=Default, Is active=False, Is filled=False — empty idle field
2. State=Default, Is active=True, Is filled=False — focused, empty
3. State=Default, Is filled=True — unfocused, has value
4. State=Error, Is filled=True — validation failed with value
5. State=Success, Is filled=True — validated with value
6. State=Disabled, Is filled=False — locked empty
7. State=Disabled, Is filled=True — locked with value
8. State=Default, L-icon=True, R-icon=True — field with both icons
9. State=Default, R-slot=True — field with trailing composable slot
10. State=Default, Pre-filled=True — prefix workaround pattern

---

##### N. All Input Field Types — Representative Variants [molecules.md → PopInputFieldV2.kt]

Map one representative complete code block per config type.

Include:
1. `BasicInputFieldConfig` — standard form field with hint, icons, error state
2. `SmallInputFieldConfig` — compact field with title and success state
3. `UnderlineNakedLargeConfig` — amount input with custom keypad
4. `UnderlineNakedSmallConfig` — naked small with required title, start icon, error
5. `SearchInputFieldConfig` — search with `DefinedThin` border, endSlot chip, clear handler
6. `MobileInputFieldConfig` — phone number input with error state
7. Standalone `StatusMessage` — info and error variants

---

## Detailed Mapping Instructions

### For Each Mapping Table:

1. **Extract from Design System File (molecules.md):**
   - Read section 11 (Input Field) sub-sections 10a through 10k
   - Extract all property names, variant values, state combinations, boolean toggles, and rules
   - Note which properties are shared across types vs. type-specific

2. **Match to Codebase:**
   - **Primary entry point**: `PopInputFieldV2(config: PopInputFieldConfig, modifier, focusRequester)` — config-based overload only; ignore deprecated parameter-based overload
   - **Config classes** (all in `PopInputFieldConfig.kt`): `BasicInputFieldConfig`, `SmallInputFieldConfig`, `UnderlineNakedSmallConfig`, `UnderlineNakedLargeConfig`, `SearchInputFieldConfig`, `MobileInputFieldConfig`, `BasicInputFieldConfigNoPadding`
   - **Status enum**: `InputFieldStatus` — `Success`, `Error`, `Info`, `Warning`
   - **Helper composable**: `StatusMessage(status, message, modifier, showIcon, textStyle, onEditClick)` in `PopInputFieldHelpers.kt`
   - **Typography constants**: `TitleTextStyle`, `BodyTextStyle`, `StatusTextStyle` in `PopInputFieldHelpers.kt`
   - **Animation constant**: `DEFAULT_ANIMATION_DURATION = 200` in `PopInputFieldHelpers.kt`

3. **Provide Code Pattern:**
   - Always use `PopInputFieldV2(config = XxxConfig(...))` — never the deprecated parameter overload
   - All config classes are `@Immutable` — create once, hoist to remember/ViewModel; never create inline inside a Composable lambda that recomposes frequently
   - Use `InputFieldStatus.*` enum — never pass raw strings or color tokens for status
   - Use `PopTypography.*` tokens — never hardcode font size or weight
   - `statusMessage` and `status` must always be set together — setting one without the other produces no visible output
   - For `UnderlineNakedLargeConfig` with custom keypad: always pair with `PopKeyPad` component via `value`/`onValueChange`; use `handleKeyPadInput` to process key presses
   - Show real code examples, not pseudocode

### Mapping Priorities:

1. **MUST include 1:1 mappings** showing exact design-to-code translation for every property across all six config types
2. **MUST show code patterns** for every variant row
3. **MUST document `State` + `Is active` + `Is filled` as independent axes** — they must be applied simultaneously, never as a switch chain
4. **MUST document deprecated overload** — parameter-based `PopInputFieldV2(value, onValueChange, type, ...)` is `@Deprecated`; always use config-based overload
5. **MUST document `title` requirement gap** — `UnderlineNakedSmallConfig.title` is required (compile-time); `MobileInputFieldConfig.title` is required; `UnderlineNakedLargeConfig` has no `title` field at all
6. **MUST document prefix/suffix gaps** — `Pre-filled` and `Post-filled` design properties have no dedicated config params in most types; document per-type workaround
7. **MUST document OTP gaps** — 10e, 10f, 10g are not covered by `PopInputFieldV2`; flag clearly with ⚠️
8. **MUST document `Is active` mapping** — not a config boolean; driven by `FocusRequester` and `BasicTextField` internal focus state; use `shouldRequestFocus` on `UnderlineNakedLargeConfig` or `focusRequester` param on the composable
9. **MUST document `StatusMessage` standalone usage** — can be placed below any custom input or reused independently of `PopInputFieldV2`
10. **MUST document `useCustomKeypad`** — only functional on `UnderlineNakedLargeConfig`; marked as not supported on all other config types

---

## Quality Standards

✓ **Accuracy:** All mappings verifiable against molecules.md, PopInputFieldV2.kt, PopInputFieldHelpers.kt, PopInputFieldConfig.kt, InputFieldStatus.kt
✓ **Completeness:** All six config types + Help Text + all design properties mapped; all gaps documented
✓ **Clarity:** Design property → config field chains always shown; gaps flagged with ⚠️
✓ **Usability:** Tables searchable by design property name and by codebase config class / param
✓ **Practical:** Every row includes a runnable `PopInputFieldV2(config = ...)` code pattern
✓ **Consistency:** Same column names and ordering throughout all tables
✓ **Gap documentation:** OTP components, prefix/suffix, `BG`, `R-overflow`, `Keypad icon`, `Padding` (Help Text) gaps clearly flagged

---

## Output Format

- **File Type:** Markdown (.md)
- **Structure:** Hierarchical with clear sections — A (Type mapping), B (State mapping), C (Per-config property tables), D (Help Text), E (Typography/tokens), F (Full variants)
- **Tables:** Markdown table format for all mappings
- **Code blocks:** Kotlin code blocks (```kotlin) for all multi-line patterns
- **Length:** Comprehensive but organized — one table per mapping concern

---

## End of Master Prompt

**Generated bridge file should be named:** `inputfield_bridgefile.md`
