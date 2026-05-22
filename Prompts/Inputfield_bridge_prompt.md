# Master Prompt: Inside "bridge_doc" folder add a file named "inputfield_bridgefile.md"

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "molecules.md"** (Input Field sections 10a–10k) with the POP Codebase. The bridge is organized by **input field category**. For each category, produce a property mapping table followed by notes.

**Codebase files to read and map:**
- `POP Codebase/ds_components/PopInputFieldV2.kt`
- `POP Codebase/ds_components/PopInputFieldHelpers.kt`
- `POP Codebase/ds_components/PopOtpField.kt`
- `POP Codebase/models/PopInputFieldConfig.kt`
- `POP Codebase/models/PopInputFieldType.kt`
- `POP Codebase/models/InputFieldStatus.kt`
- `POP Codebase/models/PopOtpFieldConfig.kt`
- `POP Codebase/compose_components/PopInputField.kt`
- `POP Codebase/compose_components/PopLabeledInputField.kt`
- `POP Codebase/compose_components/PopPrefixInputField.kt`
- `POP Codebase/compose_components/OTPTextField.kt`
- `POP Codebase/compose_components/PopEnterAmountHeader.kt`
- `POP Codebase/compose_components/PopSearchBar.kt`
- `POP Codebase/xml_components/PopInputFieldView.kt`
- `POP Codebase/xml_components/PopLabeledInputFieldView.kt`
- `POP Codebase/xml_components/PopPhoneInputFieldView.kt`


## Output Structure

For **each category** below, generate:

1. A **Property Mapping Table** with these exact columns: 
   - **Design System File** — e.g., `molecules.md`
   - **Design Property** — property name + all possible values (e.g., `State: Default / Error / Success / Disabled`, `is active: True/False`, `Placeholder text: [string]`, `` )
   - **Codebase Value** — matching parameter / enum / config field with example values (e.g.,  `status = InputFieldStatus.Error` , ` val isActive = isFocused` , `placeholder: String?`  )
   - **Codebase File name** — file where the param/enum is defined
   - **Code Pattern** — one-line inlipne Kotlin snippet showing the param in context

2. A **Notes** block below the table for:
   - Key constraints or rules for this category
   - Design-to-codebase gaps (flag with ⚠️)
   - Legacy Compose and XML/View equivalents (where applicable)

3. A **Full Code Pattern** — one complete runnable `kotlin` code block for the most representative variant of the category

### Note: Extract icon names from `bridge_doc/icon_files.md` and for icon path refer this file `bridge_doc/icon_files.md` .

---

## Categories

### 1. Input OTP — With Background

### 2. Input OTP — Without Background (Naked)

### 3. Input Field

### 4. Input Field — Small

### 5. Input — Mobile

### 6. Input — Search

### 7. Input Field — Naked Small

### 8. Input Field — Naked Large

### 9. Help text

## Detailed Mapping Instructions

### For Each Mapping Table:

1. **Extract from Design System File (molecules.md):**
   - Read sections 10a through 10k
   - Extract all property names, variant names, state values, boolean toggles, and rules
   - Note which properties apply to the individual input field vs. the OTP organism vs. the stack layout

2. **Match to Codebase:**
   - **DS-Standard input composable**: `PopInputFieldV2.kt` — `PopInputFieldV2(modifier, config: PopInputFieldConfig)`
   - **Input configs**: `PopInputFieldConfig.kt` — `BasicInputFieldConfig`, `SmallInputFieldConfig`, `UnderlineNakedLargeConfig`, `UnderlineNakedSmallConfig`, `SearchInputFieldConfig`, `MobileInputFieldConfig`
   - **OTP composable**: `PopOtpField.kt` — `PopOtpField(modifier, config: PopOtpFieldConfig)`
   - **OTP configs**: `PopOtpFieldConfig.kt` — `DiscreteConfig`, `DiscreteNakedConfig`
   - **Status enum**: `InputFieldStatus.kt` — `Success`, `Error`, `Info`, `Warning`
   - **Helper composable**: `PopInputFieldHelpers.kt` — `StatusMessage(status, message, ...)`
   - **Legacy Compose**: `PopInputField.kt`, `PopLabeledInputField.kt`, `PopPrefixInputField.kt`, `OTPTextField.kt`, `PopEnterAmountHeader.kt`, `PopSearchBar.kt`
   - **XML/View**: `PopInputFieldView.kt`, `PopLabeledInputFieldView.kt`, `PopPhoneInputFieldView.kt`

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Always use `PopInputFieldV2(config = XxxConfig(...))` — never the deprecated parameter-based overload
   - Always use `PopOtpField(config = DiscreteConfig(...))` — never legacy `OTPTextField`
   - Use `InputFieldStatus.*` enum — never raw strings or hardcoded color overrides for status
   - Use `Icons.*` for icon names — never hardcode resource IDs
   - Show real code examples, not pseudocode

---

## Mapping Priorities

1. **MUST produce one section per category** — each with a property mapping table, notes, and a full code pattern
2. **MUST map all design `State` values** per category — Default, Disabled, Error, Success, Warning where applicable
3. **MUST map `Is active` and `Is filled`** in every input field category
4. **MUST document `Warning` state exclusivity** — `InputFieldStatus.Warning` is only valid on `UnderlineNakedLargeConfig` (Category 8)
5. **MUST document `otpCount` constraint** — must be exactly 4 or 6; any other value is unsupported
6. **MUST document `title` requirement gaps** — `UnderlineNakedSmallConfig.title` is compile-time required; `MobileInputFieldConfig.title` is required; `UnderlineNakedLargeConfig` has no `title` field at all
7. **MUST document `useCustomKeypad`** — only on `UnderlineNakedLargeConfig`; always pair with `PopKeyPad`; use `handleKeyPadInput` helper
8. **MUST document legacy Compose and XML/View equivalents** in Notes of each relevant category — with explicit "do not use in new code" label
9. **MUST document `PopSearchBar`** in Category 6 Notes — `SearchBarSize` enum, `flippingHint` / `flippingTexts` for animated placeholder, visual spec
10. **MUST document `PopEnterAmountHeader`** in Category 8 Notes as a screen-level organism that uses `UnderlineNakedLargeConfig` internally
11. **MUST flag prefix/suffix gaps** with ⚠️ in Categories 3, 7, 8 — no `prefixText`/`suffixText` param; document workaround
12. **MUST document `10e Input item - Discrete` gap** — no standalone single-box composable; refer to full `PopOtpField` organism
13. **MUST document `10k Input stack horizontal` gap** — no dedicated stack composable; use `Row { PopInputFieldV2(...) }` with `Modifier.weight()`
14. **MUST document deprecated `PopInputFieldType` enum** — used only by the deprecated parameter-based `PopInputFieldV2` overload; show the `PopInputFieldType.*` → config class migration in each relevant Notes block
15. **MUST document `StatusMessage()` standalone usage** — composable from `PopInputFieldHelpers.kt`; can be used below any custom composable independently of `PopInputFieldV2`

---

## Quality Standards

✓ **Accuracy:** All mappings verifiable against molecules.md and all listed codebase files
✓ **Completeness:** All 8 categories covered; all design properties mapped; all gaps flagged
✓ **Clarity:** One table per category; design property → codebase param chain always shown
✓ **Practical:** Every category includes a complete runnable Kotlin code block
✓ **Consistency:** Same 5 table columns across all sections
✓ **Gap documentation:** ⚠️ used for every design property with no direct codebase equivalent

---

## Output Format

- **File Type:** Markdown (.md)
- **Structure:** One `##` section per category; within each: property mapping table → notes → full code pattern
- **Tables:** Markdown table format — 5 columns: Design System File | Design Property | Codebase Value | Codebase File | Code Pattern
- **Code blocks:** Kotlin (` ```kotlin `) for all multi-line patterns
- **Length:** Comprehensive but organized — one table per category, notes concise

---

## End of Master Prompt

**Generated bridge file should be named:** `inputfield_bridgefile.md`
