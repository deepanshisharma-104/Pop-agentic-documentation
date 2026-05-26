# Input Field Bridge File — POP Design System

Maps **molecules.md** (sections 10a–10k) to the POP Codebase.

## 1. Input OTP — With Background

**Design System Component:** `Input organism - Discrete` | **Figma Node:** `4235:17681` | **DS Section:** `10f`

### Property Mapping Table

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | Digit count: 4 | `otpCount = 4` | `PopOtpFieldConfig.kt` | `DiscreteConfig(otpCount = 4, ...)` |
| molecules.md | Digit count: 6 | `otpCount = 6` | `PopOtpFieldConfig.kt` | `DiscreteConfig(otpCount = 6, ...)` |
| molecules.md | Size: 56 | `boxHeight = null` (default 56 dp) | `PopOtpFieldConfig.kt` | `DiscreteConfig(boxHeight = null, ...)` |
| molecules.md | Size: 44 | `boxHeight = 44.dp` | `PopOtpFieldConfig.kt` | `DiscreteConfig(boxHeight = 44.dp, ...)` |
| molecules.md | State: Default | `status = null, enabled = true` | `PopOtpFieldConfig.kt` | `DiscreteConfig(status = null, enabled = true, ...)` |
| molecules.md | State: Error | `status = InputFieldStatus.Error` | `InputFieldStatus.kt` | `DiscreteConfig(status = InputFieldStatus.Error, ...)` |
| molecules.md | State: Disabled | `enabled = false` | `PopOtpFieldConfig.kt` | `DiscreteConfig(enabled = false, ...)` |
| molecules.md | Filled: 0 | `value = ""` | `PopOtpFieldConfig.kt` | `DiscreteConfig(value = "", ...)` |
| molecules.md | Filled: 1–6 (number of digits entered) | `value = "123"` (string length = filled count) | `PopOtpFieldConfig.kt` | `DiscreteConfig(value = "123", ...)` |
| molecules.md | Primary CTA enabled: True | `resendCountdownSeconds = null` or `0` | `PopOtpFieldConfig.kt` | `DiscreteConfig(resendCountdownSeconds = null, ...)` |
| molecules.md | Primary CTA enabled: False | `resendCountdownSeconds = 29` (positive int) | `PopOtpFieldConfig.kt` | `DiscreteConfig(resendCountdownSeconds = 29, ...)` |
| molecules.md | Body: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `bodyText` or `showBody` param in `DiscreteConfig` |
| molecules.md | Body: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `bodyText` or `showBody` param in `DiscreteConfig` |
| molecules.md | Secondary CTA: True | `onEditClick = { ... }` | `PopOtpFieldConfig.kt` | `DiscreteConfig(onEditClick = { navController.popBackStack() }, ...)` |
| molecules.md | Secondary CTA: False | `onEditClick = null` | `PopOtpFieldConfig.kt` | `DiscreteConfig(onEditClick = null, ...)` |
| molecules.md | Identifier: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `showIdentifier` / `identifier` param in `DiscreteConfig` |
| molecules.md | Identifier: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `showIdentifier` param in `DiscreteConfig` |
| molecules.md | Identifier text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ Not a configurable param in `DiscreteConfig` |
| molecules.md | Body text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ Not a configurable param; `statusMessage` is for status messages only |
| molecules.md | Primary CTA enabled text: \[string\] | `resendText = "Resend OTP"` | `PopOtpFieldConfig.kt` | `DiscreteConfig(resendText = "Resend OTP", ...)` |
| molecules.md | Primary CTA disabled text: \[string\] | Auto-computed: `"Resend in MM:SS"` from `resendCountdownSeconds` | `PopOtpField.kt` | `DiscreteConfig(resendCountdownSeconds = 29, ...)` |
| molecules.md | Secondary CTA text: \[string\] | Hardcoded `"Edit"` (not configurable) | `PopOtpField.kt` | ⚠️ Edit label is always "Edit" — no override param |

### Notes

- `otpCount` must be exactly **4** or **6** — any other value throws an `IllegalArgumentException` at runtime (enforced via `require()` in `PopOtpField`).
- For `Size: 56` (DS default), `boxHeight = null` uses the DS default of 56 dp. For `Size: 44`, pass `boxHeight = 44.dp` explicitly.
- For 4-digit OTP, `boxWidth` defaults to **56 dp**; for 6-digit OTP, `boxWidth` defaults to **44 dp**. Both can be overridden via `boxWidth` param.
- `Body`, `Identifier`, `Body text`, and `Identifier text` from DS have **no code equivalent** in `DiscreteConfig` — these contextual message fields (e.g., "OTP sent on +91 99999 99999") must be implemented as separate `Text` composables above `PopOtpField`.
- `Primary CTA disabled text` is auto-formatted as `"Resend in MM:SS"` — the exact label is not overridable. ⚠️
- `Secondary CTA text` is hardcoded to `"Edit"` in `StatusMessageRow` — cannot be overridden. ⚠️
- **Deprecated Legacy**: `OTPTextField` (compose_components/OTPTextField.kt) — **do not use in new code**. It lacks status support, focus management, and DS-aligned styling.
- **State: Error** renders `InputFieldStatus.Error` → uses `Icons.AlertHexagon` icon path: `https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/alert-hexagon-Filled.png`


## 2. Input OTP — Without Background (Naked)

**Design System Component:** `Input organism - Discrete - Naked` | **Figma Node:** `4453:16354` | **DS Section:** `10g`

### Property Mapping Table

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | Digit count: 4 | `otpCount = 4` | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(otpCount = 4, ...)` |
| molecules.md | Digit count: 6 | `otpCount = 6` | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(otpCount = 6, ...)` |
| molecules.md | Size: 56 | `boxHeight = null` (default 56 dp) | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(boxHeight = null, ...)` |
| molecules.md | Size: 44 | `boxHeight = 44.dp` | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(boxHeight = 44.dp, ...)` |
| molecules.md | State: Default | `status = null, enabled = true` | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(status = null, enabled = true, ...)` |
| molecules.md | State: Error | `status = InputFieldStatus.Error` | `InputFieldStatus.kt` | `DiscreteNakedConfig(status = InputFieldStatus.Error, ...)` |
| molecules.md | State: Disabled | `enabled = false` | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(enabled = false, ...)` |
| molecules.md | Filled: 0 | `value = ""` | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(value = "", ...)` |
| molecules.md | Filled: 1–6 (number of digits entered) | `value = "123"` (string length = filled count) | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(value = "123", ...)` |
| molecules.md | Primary CTA enabled: True | `resendCountdownSeconds = null` or `0` | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(resendCountdownSeconds = null, ...)` |
| molecules.md | Primary CTA enabled: False | `resendCountdownSeconds = 29` (positive int) | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(resendCountdownSeconds = 29, ...)` |
| molecules.md | Body: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `bodyText` or `showBody` param in `DiscreteNakedConfig` |
| molecules.md | Body: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `bodyText` or `showBody` param in `DiscreteNakedConfig` |
| molecules.md | Secondary CTA: True | `onEditClick = { ... }` | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(onEditClick = { navController.popBackStack() }, ...)` |
| molecules.md | Secondary CTA: False | `onEditClick = null` | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(onEditClick = null, ...)` |
| molecules.md | Identifier: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `showIdentifier` / `identifier` param in `DiscreteNakedConfig` |
| molecules.md | Identifier: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `showIdentifier` param in `DiscreteNakedConfig` |
| molecules.md | Identifier text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ Not a configurable param |
| molecules.md | Body text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ Not a configurable param; use separate `Text` composable |
| molecules.md | Primary CTA enabled text: \[string\] | `resendText = "Resend OTP"` | `PopOtpFieldConfig.kt` | `DiscreteNakedConfig(resendText = "Resend OTP", ...)` |
| molecules.md | Primary CTA disabled text: \[string\] | Auto-computed: `"Resend in MM:SS"` from `resendCountdownSeconds` | `PopOtpField.kt` | `DiscreteNakedConfig(resendCountdownSeconds = 29, ...)` |
| molecules.md | Secondary CTA text: \[string\] | Hardcoded `"Edit"` (not configurable) | `PopOtpField.kt` | ⚠️ Edit label is always "Edit" — no override param |

### Notes

- `DiscreteNakedConfig` renders each OTP digit box with a **transparent background** (`Color.Transparent`) and **no border** — visually "naked" style.
- `DiscreteConfig` renders each box with a solid background (`SurfaceColor.SecondaryFromTokens`) and a visible border — this is the "with background" style.
- All `otpCount`, gap, and status constraints are identical to `DiscreteConfig` — see Category 1 notes.
- `Body`, `Identifier`, `Body text`, `Identifier text` have no code equivalent in either Discrete config. Implement as separate `Text` composables above `PopOtpField`. ⚠️
- **Deprecated Legacy**: `OTPTextField` (compose_components/OTPTextField.kt) — **do not use in new code**.
- `DiscreteNakedConfig` does not have an `isLoading` field (unlike `DiscreteConfig`). ⚠️



## 3. Input Field

**Design System Component:** `Input field` | **Figma Node:** `3821:4239` | **DS Section:** `10a`

### Property Mapping Table

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | State: Default | `status = null, enabled = true` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(status = null, enabled = true, ...)` |
| molecules.md | State: Error | `status = InputFieldStatus.Error` | `InputFieldStatus.kt` | `BasicInputFieldConfig(status = InputFieldStatus.Error, ...)` |
| molecules.md | State: Success | `status = InputFieldStatus.Success` | `InputFieldStatus.kt` | `BasicInputFieldConfig(status = InputFieldStatus.Success, ...)` |
| molecules.md | State: Disabled | `enabled = false` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(enabled = false, ...)` |
| molecules.md | BG: True | NO CODE EQUIVALENT FOUND | — | ⚠️ Background is always rendered in `BasicInputFieldConfig` — no toggle param |
| molecules.md | BG: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `showBackground` param; use `BasicInputFieldConfigNoPadding` for borderless context |
| molecules.md | Is active: False | Internal: `isFocused = false` (derived from focus state) | `PopInputFieldV2.kt` | Managed internally via `onFocusChanged` |
| molecules.md | Is active: True | Internal: `isFocused = true` (derived from focus state) | `PopInputFieldV2.kt` | `Modifier.focusRequester(focusRequester)` on composable |
| molecules.md | Is filled: False | `value = ""` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(value = "", ...)` |
| molecules.md | Is filled: True | `value = "non-empty string"` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(value = "John Doe", ...)` |
| molecules.md | L-icon: True | `startIcon = PopIconConfig(iconName = "...", ...)` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(startIcon = PopIconConfig(iconName = Icons.User03, style = IconStyle.Filled, size = IconSize.Small), ...)` |
| molecules.md | L-icon: False | `startIcon = null` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(startIcon = null, ...)` |
| molecules.md | R-icon: True | `endIcon = PopIconConfig(iconName = "...", ...)` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(endIcon = PopIconConfig(iconName = Icons.EyeOff, style = IconStyle.Filled, size = IconSize.Small), ...)` |
| molecules.md | R-icon: False | `endIcon = null` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(endIcon = null, ...)` |
| molecules.md | R-slot: True | `endSlot = { @Composable ... }` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(endSlot = { Text("Verify") }, ...)` |
| molecules.md | R-slot: False | `endSlot = null` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(endSlot = null, ...)` |
| molecules.md | R-overflow: True | shouldShowRightGradient = true | PopInputFieldV2.kt | if (textOverflows) {
                                    Modifier.horizontalGradientFade(
                                        gradientWidth = 72.dp, // Standard gradient width from PopMarqueeText
                                        shouldShowLeftGradient = false,
                                        shouldShowRightGradient = true,
                                        leftGradientAlpha = 1f
                                    )}|
| molecules.md | R-overflow: False | shouldShowLeftGradient = false | PopInputFieldV2.kt | if (textOverflows) {
                                    Modifier.horizontalGradientFade(
                                        gradientWidth = 72.dp, // Standard gradient width from PopMarqueeText
                                        shouldShowLeftGradient = false,
                                        shouldShowRightGradient = true,
                                        leftGradientAlpha = 1f
                                    )}|
| molecules.md | Info message: True | `status = InputFieldStatus.Info, statusMessage = "..."` | `InputFieldStatus.kt` | `BasicInputFieldConfig(status = InputFieldStatus.Info, statusMessage = "Tip text", ...)` |
| molecules.md | Info message: False | `status = null` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(status = null, ...)` |
| molecules.md | Pre-filled: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` param in `BasicInputFieldConfig` — use `startIcon` or wrap in `Row` |
| molecules.md | Pre-filled: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` param |
| molecules.md | Post-filled: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` param in `BasicInputFieldConfig` — use `endSlot` as workaround |
| molecules.md | Post-filled: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` param |
| molecules.md | Placeholder text: \[string\] | `placeholder = "..."` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(placeholder = "Enter your name", ...)` |
| molecules.md | Body text: \[string\] | `value = "..."` (the current input value) | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(value = "John Doe", ...)` |
| molecules.md | Title text: \[string\] | `hintText = "..."` (animated label above field) | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(hintText = "Full Name", ...)` |
| molecules.md | Info text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Info` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(statusMessage = "Use your legal name", status = InputFieldStatus.Info, ...)` |
| molecules.md | Error text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Error` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(statusMessage = "Name cannot be empty", status = InputFieldStatus.Error, ...)` |
| molecules.md | Success text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Success` | `PopInputFieldConfig.kt` | `BasicInputFieldConfig(statusMessage = "Name verified!", status = InputFieldStatus.Success, ...)` |
| molecules.md | Pre-filled text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` string param — workaround: use `endSlot` or compose `Row` with static `Text` |
| molecules.md | Post-filled text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` string param — workaround: use `endSlot = { Text("@yespop") }` |

### Notes

- `State`, `Is active`, and `Is filled` are **independent properties** in the DS — in code, `State` maps to `status`/`enabled`, while `Is active` and `Is filled` are **derived internally** from `isFocused` and `value.isNotEmpty()` respectively. Never replicate these with if-else chains.
- `BG: False` has no direct equivalent. Use `BasicInputFieldConfigNoPadding` (removes horizontal padding) for edge-to-edge contexts.
- `Pre-filled` and `Post-filled` (prefix/suffix text like `₹`, `+91`, `@yespop`) are **not available** as string params. Workaround: use `endSlot = { Text(".00") }` for suffix or wrap the composable in a `Row` with a preceding `Text` for prefix. ⚠️
- **`PopInputFieldType.Basic`** was the deprecated enum for this variant. **Migration:** Replace `PopInputFieldV2(type = PopInputFieldType.Basic, ...)` with `PopInputFieldV2(config = BasicInputFieldConfig(...))`.
- **Deprecated Legacy Compose**: `PopInputField` (compose_components/PopInputField.kt) and `PopLabeledInputField` (compose_components/PopLabeledInputField.kt) — **do not use in new code**. They use `OutlinedTextField`/`BasicTextField` without DS token support.
- **Deprecated Legacy XML/View**: `PopInputFieldView` (xml_components/PopInputFieldView.kt) and `PopLabeledInputFieldView` (xml_components/PopLabeledInputFieldView.kt) — **do not use in new code**. XML-based; no Compose interop.
- `StatusMessage()` from `PopInputFieldHelpers.kt` can be used **standalone** below any composable — pair it with any status type without needing `PopInputFieldV2`.

### Full Code Pattern

```kotlin
var name by remember { mutableStateOf("") }
var nameStatus by remember { mutableStateOf<InputFieldStatus?>(null) }

PopInputFieldV2(
    config = BasicInputFieldConfig(
        value = name,
        onValueChange = { name = it },
        hintText = "Full Name",
        placeholder = "Enter your name",
        startIcon = PopIconConfig(
            iconName = Icons.User03,
            style = IconStyle.Filled,
            size = IconSize.Small
        ),
        endIcon = null,
        status = nameStatus,
        statusMessage = when (nameStatus) {
            InputFieldStatus.Error -> "Name is required"
            InputFieldStatus.Success -> "Name verified"
            InputFieldStatus.Info -> "Use your legal name"
            else -> null
        },
        enabled = true,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next,
        onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
    )
)
```

---

## 4. Input Field — Small

**Design System Component:** `Input field - Small` | **Figma Node:** `8220:47609` | **DS Section:** `10b`

### Property Mapping Table

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | State: Default | `status = null, enabled = true` | `PopInputFieldConfig.kt` | `SmallInputFieldConfig(status = null, enabled = true, ...)` |
| molecules.md | State: Error | `status = InputFieldStatus.Error` | `InputFieldStatus.kt` | `SmallInputFieldConfig(status = InputFieldStatus.Error, ...)` |
| molecules.md | State: Success | `status = InputFieldStatus.Success` | `InputFieldStatus.kt` | `SmallInputFieldConfig(status = InputFieldStatus.Success, ...)` |
| molecules.md | State: Disabled | `enabled = false` | `PopInputFieldConfig.kt` | `SmallInputFieldConfig(enabled = false, ...)` |
| molecules.md | BG: True | NO CODE EQUIVALENT FOUND | — | ⚠️ Background is always rendered — no toggle param in `SmallInputFieldConfig` |
| molecules.md | BG: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `showBackground` param |
| molecules.md | Is active: False | Internal: `isFocused = false` (derived from focus state) | `PopInputFieldV2.kt` | Managed internally via `onFocusChanged` |
| molecules.md | Is active: True | Internal: `isFocused = true` (derived from focus state) | `PopInputFieldV2.kt` | `Modifier.focusRequester(focusRequester)` on composable |
| molecules.md | Is filled: False | `value = ""` | `PopInputFieldConfig.kt` | `SmallInputFieldConfig(value = "", ...)` |
| molecules.md | Is filled: True | `value = "non-empty string"` | `PopInputFieldConfig.kt` | `SmallInputFieldConfig(value = "Dinner", ...)` |
| molecules.md | Pre-filled: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` param in `SmallInputFieldConfig` |
| molecules.md | Pre-filled: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` param |
| molecules.md | Post-filled: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` param in `SmallInputFieldConfig` |
| molecules.md | Post-filled: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` param |
| molecules.md | Info message: True | `status = InputFieldStatus.Info, statusMessage = "..."` | `InputFieldStatus.kt` | `SmallInputFieldConfig(status = InputFieldStatus.Info, statusMessage = "Try 'RENT'", ...)` |
| molecules.md | Info message: False | `status = null` | `PopInputFieldConfig.kt` | `SmallInputFieldConfig(status = null, ...)` |
| molecules.md | R-overflow: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No right-overflow fade param in `SmallInputFieldConfig` |
| molecules.md | R-overflow: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No overflow fade param |
| molecules.md | Title: \[string\] | `title = "..."` | `PopInputFieldConfig.kt` | `SmallInputFieldConfig(title = "Add note", ...)` |
| molecules.md | Placeholder text: \[string\] | `placeholder = "..."` | `PopInputFieldConfig.kt` | `SmallInputFieldConfig(placeholder = "Eg. Lunch", ...)` |
| molecules.md | Body text: \[string\] | `value = "..."` (the current input value) | `PopInputFieldConfig.kt` | `SmallInputFieldConfig(value = "Dinner", ...)` |
| molecules.md | Pre-filled text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` string param |
| molecules.md | Post-filled text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` string param |
| molecules.md | Info text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Info` | `PopInputFieldConfig.kt` | `SmallInputFieldConfig(statusMessage = "Try 'RENT'", status = InputFieldStatus.Info, ...)` |
| molecules.md | Error text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Error` | `PopInputFieldConfig.kt` | `SmallInputFieldConfig(statusMessage = "'.' not allowed", status = InputFieldStatus.Error, ...)` |
| molecules.md | Success text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Success` | `PopInputFieldConfig.kt` | `SmallInputFieldConfig(statusMessage = "Promo applied", status = InputFieldStatus.Success, ...)` |

### Notes

- `SmallInputFieldConfig.title` is optional (`String?`) unlike `UnderlineNakedSmallConfig.title` which is compile-time required.
- `Pre-filled` and `Post-filled` (prefix/suffix like `SBIN`, `@yespop`) have **no code equivalent** in `SmallInputFieldConfig`. ⚠️ Workaround: compose a `Row` with a static `Text` prefix alongside `PopInputFieldV2`.
- **`PopInputFieldType.Small`** was the deprecated enum for this variant. **Migration:** Replace `PopInputFieldV2(type = PopInputFieldType.Small, ...)` with `PopInputFieldV2(config = SmallInputFieldConfig(...))`.
- Used inside `PopEnterAmountHeader` for the "Add a note" field — `SmallInputFieldConfig(title = "Note", placeholder = "Add a note", ...)`.
- **Deprecated Legacy XML/View**: `PopInputFieldView` with `inputSize = InputSize.SMALL` — **do not use in new code**.

### Full Code Pattern

```kotlin
var note by remember { mutableStateOf("") }

PopInputFieldV2(
    config = SmallInputFieldConfig(
        value = note,
        onValueChange = { note = it },
        title = "Add note",
        placeholder = "Eg. Lunch",
        status = if (note.contains(".")) InputFieldStatus.Error else null,
        statusMessage = if (note.contains(".")) "'.' not allowed" else null,
        enabled = true,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done,
        onImeAction = { focusManager.clearFocus() }
    )
)
```

---

## 5. Input — Mobile

**Design System Component:** `Input - Mobile` | **Figma Node:** `4235:14047` | **DS Section:** `10i`

### Property Mapping Table

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | State: Default | `status = null, enabled = true` | `PopInputFieldConfig.kt` | `MobileInputFieldConfig(status = null, enabled = true, ...)` |
| molecules.md | State: Error | `status = InputFieldStatus.Error` | `InputFieldStatus.kt` | `MobileInputFieldConfig(status = InputFieldStatus.Error, ...)` |
| molecules.md | State: Success | `status = InputFieldStatus.Success` | `InputFieldStatus.kt` | `MobileInputFieldConfig(status = InputFieldStatus.Success, ...)` |
| molecules.md | State: Disabled | `enabled = false` | `PopInputFieldConfig.kt` | `MobileInputFieldConfig(enabled = false, ...)` |
| molecules.md | Is active: False | Internal: `isFocused = false` (derived from focus state) | `PopInputFieldV2.kt` | Managed internally via `onFocusChanged` |
| molecules.md | Is active: True | `onFocusReceived = { ... }` callback fires on focus | `PopInputFieldConfig.kt` | `MobileInputFieldConfig(onFocusReceived = { showKeyboard() }, ...)` |
| molecules.md | Is filled: False | `value = ""` | `PopInputFieldConfig.kt` | `MobileInputFieldConfig(value = "", ...)` |
| molecules.md | Is filled: True | `value = "9876543210"` | `PopInputFieldConfig.kt` | `MobileInputFieldConfig(value = "9876543210", ...)` |

### Notes

- `MobileInputFieldConfig.title` is **compile-time required** — always provide a non-empty string.
- The country flag (🇮🇳) and `+91` prefix are **built into the composable** — do not add them as external text or via `prefixText`. They are rendered internally.
- `keyboardType` defaults to `KeyboardType.Phone` — only override for special cases.
- `onFocusChanged: (Boolean) -> Unit` receives `true` when focused, `false` when unfocused (e.g., back press or outside tap).
- **`PopInputFieldType.MobileInputField`** was the deprecated enum. **Migration:** Replace `PopInputFieldV2(type = PopInputFieldType.MobileInputField, ...)` with `PopInputFieldV2(config = MobileInputFieldConfig(...))`.
- **Deprecated Legacy Compose**: `PopPrefixInputField` (compose_components/PopPrefixInputField.kt) — **do not use in new code**. Uses custom cursor blinking with no DS token support.
- **Deprecated Legacy XML/View**: `PopPhoneInputFieldView` (xml_components/PopPhoneInputFieldView.kt) — **do not use in new code**. XML-based, hardcoded `+91` prefix without proper DS styling.

### Full Code Pattern

```kotlin
var phone by remember { mutableStateOf("") }
var phoneStatus by remember { mutableStateOf<InputFieldStatus?>(null) }

PopInputFieldV2(
    config = MobileInputFieldConfig(
        value = phone,
        onValueChange = { phone = it },
        title = "Mobile Number",
        placeholder = "Enter 10-digit number",
        status = phoneStatus,
        statusMessage = when (phoneStatus) {
            InputFieldStatus.Error -> "Invalid mobile number"
            InputFieldStatus.Success -> "Number verified"
            else -> null
        },
        enabled = true,
        keyboardType = KeyboardType.Phone,
        imeAction = ImeAction.Done,
        onFocusReceived = { /* request focus side-effects */ },
        onFocusChanged = { isFocused -> if (!isFocused) validatePhone(phone) },
        onImeAction = { focusManager.clearFocus() }
    )
)
```

---

## 6. Input — Search

**Design System Component:** `Input - Search` | **Figma Node:** `4536:12367` | **DS Section:** `10h`

### Property Mapping Table

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | State: Default | `status = null, enabled = true` | `PopInputFieldConfig.kt` | `SearchInputFieldConfig(status = null, enabled = true, ...)` |
| molecules.md | State: Error | `status = InputFieldStatus.Error` | `InputFieldStatus.kt` | `SearchInputFieldConfig(status = InputFieldStatus.Error, ...)` |
| molecules.md | State: Success | `status = InputFieldStatus.Success` | `InputFieldStatus.kt` | `SearchInputFieldConfig(status = InputFieldStatus.Success, ...)` |
| molecules.md | State: Disabled | `enabled = false` | `PopInputFieldConfig.kt` | `SearchInputFieldConfig(enabled = false, ...)` |
| molecules.md | BG: True | NO CODE EQUIVALENT FOUND | — | ⚠️ Background is always rendered — no `BG` toggle in `SearchInputFieldConfig` |
| molecules.md | BG: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `showBackground` param |
| molecules.md | Is active: False | Internal: `isFocused = false` (derived via `onFocusChanged`) | `PopInputFieldConfig.kt` | `SearchInputFieldConfig(onFocusChanged = { isFocused -> ... }, ...)` |
| molecules.md | Is active: True | Internal: `isFocused = true` (derived via `onFocusChanged`) | `PopInputFieldConfig.kt` | `SearchInputFieldConfig(onFocusChanged = { isFocused -> ... }, ...)` |
| molecules.md | Is filled: False | `value = ""` | `PopInputFieldConfig.kt` | `SearchInputFieldConfig(value = "", ...)` |
| molecules.md | Is filled: True | `value = "non-empty string"` | `PopInputFieldConfig.kt` | `SearchInputFieldConfig(value = "Zomato", ...)` |
| molecules.md | Keypad icon: True | `useCustomKeypad = true` | `PopInputFieldConfig.kt` | `SearchInputFieldConfig(useCustomKeypad = true, ...)` |
| molecules.md | Keypad icon: False | `useCustomKeypad = false` (default) | `PopInputFieldConfig.kt` | `SearchInputFieldConfig(useCustomKeypad = false, ...)` |

### Notes

- `Keypad icon: True` in the DS indicates the search field triggers a **custom keypad** instead of the OS keyboard. In code, `useCustomKeypad = true` suppresses the system IME — pair with `PopKeyPad` composable.
- **Animated placeholder** (flipping/scrolling hint text) maps to `animatedPlaceholder: (@Composable () -> Unit)?` in `SearchInputFieldConfig`. Pass a composable that implements the animated text effect.
- `searchBorderStyle: SearchBorderStyle` controls border treatment: `SearchBorderStyle.Subtle` (default, no visible border) or `SearchBorderStyle.DefinedThin` (thin visible border). DS `BG: True/False` has no direct equivalent — `searchBorderStyle` is the closest analog.
- `endSlot: (@Composable () -> Unit)?` can hold a trailing chip, filter tag, or icon after the search text.
- `onClearClick` is called when the built-in clear (✕) button is tapped — if `null`, clear is handled automatically.
- **`PopInputFieldType.Search`** was the deprecated enum. **Migration:** Replace with `SearchInputFieldConfig(...)`.
- **Deprecated Legacy Compose**: `PopSearchBar` (compose_components/PopSearchBar.kt) — **do not use in new code**. Uses `SearchBarSize` enum (`Small`/`Big`) and `flippingHint`/`flippingTexts` for animated placeholder. Does not use DS tokens (`TextColors`, `PopColors` only).
- `PopSearchBar.SearchBarSize.Small` → `SearchInputFieldConfig` (standard); `SearchBarSize.Big` → no direct equivalent in DS config.

### Full Code Pattern

```kotlin
var query by remember { mutableStateOf("") }

PopInputFieldV2(
    config = SearchInputFieldConfig(
        value = query,
        onValueChange = { query = it },
        placeholder = "Search merchants, UPI IDs…",
        animatedPlaceholder = null,
        searchBorderStyle = SearchBorderStyle.Subtle,
        onClearClick = { query = "" },
        status = null,
        statusMessage = null,
        enabled = true,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Search,
        onImeAction = { performSearch(query) },
        onFocusChanged = { isFocused -> onSearchFocusChanged(isFocused) }
    )
)
```

---

## 7. Input Field — Naked Small

**Design System Component:** `Input field - Naked Small` | **Figma Node:** `3825:5885` | **DS Section:** `10d`

### Property Mapping Table

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | State: Default | `status = null, enabled = true` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(status = null, enabled = true, ...)` |
| molecules.md | State: Error | `status = InputFieldStatus.Error` | `InputFieldStatus.kt` | `UnderlineNakedSmallConfig(status = InputFieldStatus.Error, ...)` |
| molecules.md | State: Success | `status = InputFieldStatus.Success` | `InputFieldStatus.kt` | `UnderlineNakedSmallConfig(status = InputFieldStatus.Success, ...)` |
| molecules.md | State: Disabled | `enabled = false` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(enabled = false, ...)` |
| molecules.md | Is active: False | Internal: `isFocused = false` (derived from focus state) | `PopInputFieldV2.kt` | Managed internally via `onFocusChanged` |
| molecules.md | Is active: True | Internal: `isFocused = true` (derived from focus state) | `PopInputFieldV2.kt` | `Modifier.focusRequester(focusRequester)` on composable |
| molecules.md | Is filled: False | `value = ""` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(value = "", ...)` |
| molecules.md | Is filled: True | `value = "non-empty string"` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(value = "SBIN", ...)` |
| molecules.md | L-icon: True | `startIcon = PopIconConfig(...)` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(startIcon = PopIconConfig(iconName = Icons.Bank, style = IconStyle.Filled, size = IconSize.Small), ...)` |
| molecules.md | L-icon: False | `startIcon = null` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(startIcon = null, ...)` |
| molecules.md | R-icon: True | `endIcon = PopIconConfig(...)` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(endIcon = PopIconConfig(iconName = Icons.Close, style = IconStyle.Filled, size = IconSize.Small), ...)` |
| molecules.md | R-icon: False | `endIcon = null` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(endIcon = null, ...)` |
| molecules.md | R-slot: True | `endSlot = { @Composable ... }` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(endSlot = { TextButton("Verify") {} }, ...)` |
| molecules.md | R-slot: False | `endSlot = null` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(endSlot = null, ...)` |
| molecules.md | R-overflow: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No right-overflow fade param in `UnderlineNakedSmallConfig` |
| molecules.md | R-overflow: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No overflow fade param |
| molecules.md | Pre-filled: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` param — use `startIcon` or external `Row` with static `Text` |
| molecules.md | Pre-filled: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` param |
| molecules.md | Post-filled: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` param — use `endSlot = { Text("@yespop") }` as workaround |
| molecules.md | Post-filled: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` param |
| molecules.md | Title: True | `title = "..."` (required param) | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(title = "IFSC Code", ...)` |
| molecules.md | Title: False | NO CODE EQUIVALENT FOUND | — | ⚠️ `title` is a compile-time required `String` — cannot be hidden; pass `""` as workaround |
| molecules.md | Info message: True | `status = InputFieldStatus.Info, statusMessage = "..."` | `InputFieldStatus.kt` | `UnderlineNakedSmallConfig(status = InputFieldStatus.Info, statusMessage = "Info message", ...)` |
| molecules.md | Info message: False | `status = null` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(status = null, ...)` |
| molecules.md | Title text: \[string\] | `title = "..."` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(title = "IFSC Code", ...)` |
| molecules.md | Body text: \[string\] | `value = "..."` (the current input value) | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(value = "SBIN0001234", ...)` |
| molecules.md | Placeholder text: \[string\] | `placeholder = "..."` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(placeholder = "Eg. SBIN0001234", ...)` |
| molecules.md | Pre-filled text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` string param |
| molecules.md | Post-filled text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` string param |
| molecules.md | Info text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Info` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(statusMessage = "Info message", status = InputFieldStatus.Info, ...)` |
| molecules.md | Error text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Error` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(statusMessage = "Error message", status = InputFieldStatus.Error, ...)` |
| molecules.md | Success text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Success` | `PopInputFieldConfig.kt` | `UnderlineNakedSmallConfig(statusMessage = "Success message", status = InputFieldStatus.Success, ...)` |

### Notes

- `UnderlineNakedSmallConfig.title` is a **compile-time required `String`** — it cannot be hidden. If the design calls for `Title: False`, pass an empty string `""` as a workaround (but note this will render as an empty label area). ⚠️
- `Pre-filled` and `Post-filled` prefix/suffix text (e.g., `SBIN`, `@yespop`) have **no code equivalent** in `UnderlineNakedSmallConfig`. ⚠️ Workaround for suffix: use `endSlot = { Text("@yespop", style = ...) }`. Workaround for prefix: wrap in `Row` with a preceding static `Text`.
- `editable: Boolean` controls whether the field behaves as read-only even when `readOnly = false` — used for display-only fields that should look editable.
- **`PopInputFieldType.UnderlineNakedSmall`** was the deprecated enum. **Migration:** Replace with `UnderlineNakedSmallConfig(...)`.
- `Warning` state is **not available** on this variant — use only `Default`, `Error`, `Success`, or `Disabled`.

### Full Code Pattern

```kotlin
var ifscCode by remember { mutableStateOf("") }
var ifscStatus by remember { mutableStateOf<InputFieldStatus?>(null) }

PopInputFieldV2(
    config = UnderlineNakedSmallConfig(
        value = ifscCode,
        onValueChange = { ifscCode = it.uppercase() },
        title = "IFSC Code",
        placeholder = "Eg. SBIN0001234",
        status = ifscStatus,
        statusMessage = when (ifscStatus) {
            InputFieldStatus.Error -> "Invalid IFSC code"
            InputFieldStatus.Success -> "Bank verified"
            InputFieldStatus.Info -> "Found on your cheque book"
            else -> null
        },
        startIcon = null,
        endIcon = null,
        enabled = true,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done,
        onImeAction = { validateIfsc(ifscCode) }
    )
)
```

---

## 8. Input Field — Naked Large

**Design System Component:** `Input field - Naked Large` | **Figma Node:** `4354:21420` | **DS Section:** `10c`

### Property Mapping Table

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | State: Default | `status = null, enabled = true` | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(status = null, enabled = true, ...)` |
| molecules.md | State: Error | `status = InputFieldStatus.Error` | `InputFieldStatus.kt` | `UnderlineNakedLargeConfig(status = InputFieldStatus.Error, ...)` |
| molecules.md | State: Success | `status = InputFieldStatus.Success` | `InputFieldStatus.kt` | `UnderlineNakedLargeConfig(status = InputFieldStatus.Success, ...)` |
| molecules.md | State: Disabled | `enabled = false` | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(enabled = false, ...)` |
| molecules.md | State: Warning | `status = InputFieldStatus.Warning` | `InputFieldStatus.kt` | `UnderlineNakedLargeConfig(status = InputFieldStatus.Warning, ...)` |
| molecules.md | Is active: False | Internal: `isFocused = false`; `shouldRequestFocus = false` | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(shouldRequestFocus = false, ...)` |
| molecules.md | Is active: True | `shouldRequestFocus = true` + `onFocusReceived = { ... }` | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(shouldRequestFocus = showKeypad, onFocusReceived = { ... }, ...)` |
| molecules.md | Is filled: False | `value = ""` | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(value = "", ...)` |
| molecules.md | Is filled: True | `value = "1,234"` | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(value = "1234", ...)` |
| molecules.md | Pre-filled: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` param — the `₹` prefix is implemented by the parent screen (e.g., `PopEnterAmountHeader`) |
| molecules.md | Pre-filled: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` param |
| molecules.md | Post-filled: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` param in `UnderlineNakedLargeConfig` |
| molecules.md | Post-filled: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` param |
| molecules.md | Title: True | NO CODE EQUIVALENT FOUND | — | ⚠️ `UnderlineNakedLargeConfig` has **no `title` field** — implement as a separate `Text` composable above `PopInputFieldV2` |
| molecules.md | Title: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No `title` field |
| molecules.md | L-overflow: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No left-overflow fade param |
| molecules.md | L-overflow: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No overflow fade param |
| molecules.md | R-overflow: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No right-overflow fade param |
| molecules.md | R-overflow: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No overflow fade param |
| molecules.md | Info message: True | `status = InputFieldStatus.Info, statusMessage = "..."` | `InputFieldStatus.kt` | `UnderlineNakedLargeConfig(status = InputFieldStatus.Info, statusMessage = "Info message", ...)` |
| molecules.md | Info message: False | `status = null` | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(status = null, ...)` |
| molecules.md | L-icon - Info: True | `showIcon = true` (default in `StatusMessage`) | `PopInputFieldHelpers.kt` | `StatusMessage(status = InputFieldStatus.Info, message = "...", showIcon = true)` |
| molecules.md | L-icon - Info: False | `showIcon = false` | `PopInputFieldHelpers.kt` | `StatusMessage(status = InputFieldStatus.Info, message = "...", showIcon = false)` |
| molecules.md | R-icon - Info: True | NO CODE EQUIVALENT FOUND | — | ⚠️ `StatusMessage` / `StatusMessageRow` has no trailing icon slot |
| molecules.md | R-icon - Info: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No trailing icon in status row |
| molecules.md | L-icon - Error: True | `showIcon = true` (default) | `PopInputFieldHelpers.kt` | `StatusMessage(status = InputFieldStatus.Error, message = "...", showIcon = true)` |
| molecules.md | L-icon - Error: False | `showIcon = false` | `PopInputFieldHelpers.kt` | `StatusMessage(status = InputFieldStatus.Error, message = "...", showIcon = false)` |
| molecules.md | R-icon - Error: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No trailing icon in `StatusMessageRow` |
| molecules.md | R-icon - Error: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No trailing icon |
| molecules.md | L-icon - Success: True | `showIcon = true` (default) | `PopInputFieldHelpers.kt` | `StatusMessage(status = InputFieldStatus.Success, message = "...", showIcon = true)` |
| molecules.md | L-icon - Success: False | `showIcon = false` | `PopInputFieldHelpers.kt` | `StatusMessage(status = InputFieldStatus.Success, message = "...", showIcon = false)` |
| molecules.md | R-icon - Success: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No trailing icon in `StatusMessageRow` |
| molecules.md | R-icon - Success: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No trailing icon |
| molecules.md | L-icon - Warning: True | `showIcon = true` (default) | `PopInputFieldHelpers.kt` | `StatusMessage(status = InputFieldStatus.Warning, message = "...", showIcon = true)` |
| molecules.md | L-icon - Warning: False | `showIcon = false` | `PopInputFieldHelpers.kt` | `StatusMessage(status = InputFieldStatus.Warning, message = "...", showIcon = false)` |
| molecules.md | R-icon - Warning: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No trailing icon in `StatusMessageRow` |
| molecules.md | R-icon - Warning: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No trailing icon |
| molecules.md | Title text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ `UnderlineNakedLargeConfig` has no `title` param — implement as a `Text` composable outside |
| molecules.md | Body text: \[string\] | `value = "1,234"` (the current amount value) | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(value = "1234", ...)` |
| molecules.md | Pre-filled text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ No `prefixText` param — `₹` is rendered by the parent composable (e.g., `PopEnterAmountHeader`) |
| molecules.md | Post-filled text: \[string\] | NO CODE EQUIVALENT FOUND | — | ⚠️ No `suffixText` param |
| molecules.md | Info text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Info` | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(statusMessage = "Info message", status = InputFieldStatus.Info, ...)` |
| molecules.md | Error text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Error` | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(statusMessage = "Error message", status = InputFieldStatus.Error, ...)` |
| molecules.md | Success text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Success` | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(statusMessage = "Success message", status = InputFieldStatus.Success, ...)` |
| molecules.md | Warning text: \[string\] | `statusMessage = "..."` with `status = InputFieldStatus.Warning` | `PopInputFieldConfig.kt` | `UnderlineNakedLargeConfig(statusMessage = "Warning message", status = InputFieldStatus.Warning, ...)` |

### Notes

- **`InputFieldStatus.Warning` is exclusive to `UnderlineNakedLargeConfig`** among all input field variants. Using `Warning` on other config classes (`BasicInputFieldConfig`, `SmallInputFieldConfig`, etc.) is technically allowed by the enum but has no dedicated DS design backing — avoid it.
- `UnderlineNakedLargeConfig` has **no `title` field** — the DS `Title` toggle and `Title text` must be implemented as a separate `Text` composable placed above `PopInputFieldV2`. ⚠️
- `Pre-filled` (e.g., `₹`) and `Post-filled` (e.g., `.00`) have **no code equivalent** — the prefix `₹` symbol is implemented by the parent composable (e.g., `PopEnterAmountHeader`). ⚠️
- **`useCustomKeypad = true`** suppresses the OS keyboard — always pair with `PopKeyPad` composable and use `handleKeyPadInput(key, currentValue, allowDecimal, maxLength)` helper to update the value.
- `shouldRequestFocus = true` drives the `Is active` / focused state externally — set it when the screen programmatically wants to open the keypad.
- **`PopEnterAmountHeader`** (compose_components/PopEnterAmountHeader.kt) is a **screen-level organism** that uses `UnderlineNakedLargeConfig` internally for the amount input. It handles the `₹` prefix, keypad integration, note field, and avatar section. Do not implement this manually — use `PopEnterAmountHeader` for UPI/payment amount screens.
- L-overflow and R-overflow fade (design overflow fade for long amounts) have **no code equivalent** in the config. ⚠️
- **`PopInputFieldType.UnderlineNakedLarge`** was the deprecated enum. **Migration:** Replace with `UnderlineNakedLargeConfig(...)`.
- Trailing icons (`R-icon - Info/Error/Success/Warning`) are not supported by `StatusMessageRow`. ⚠️

### Full Code Pattern

```kotlin
var amount by remember { mutableStateOf("") }
var amountStatus by remember { mutableStateOf<InputFieldStatus?>(null) }

Column(horizontalAlignment = Alignment.CenterHorizontally) {
    // Title (no title param in config — render separately)
    Text(
        text = "Enter Amount",
        style = PopTypography.labelSmall.copy(color = TextColor.Secondary)
    )

    PopInputFieldV2(
        config = UnderlineNakedLargeConfig(
            value = amount,
            onValueChange = { amount = it },
            placeholder = "0",
            status = amountStatus,
            statusMessage = when (amountStatus) {
                InputFieldStatus.Error -> "Amount exceeds limit"
                InputFieldStatus.Success -> "Amount confirmed"
                InputFieldStatus.Warning -> "Amount is below minimum"
                InputFieldStatus.Info -> "Max ₹1,00,000 per transaction"
                else -> null
            },
            enabled = true,
            useCustomKeypad = true,
            shouldRequestFocus = showKeypad,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
            onImeAction = { /* handled by custom keypad */ },
            onFocusReceived = { showKeypad = true }
        ),
        modifier = Modifier.width(IntrinsicSize.Min)
    )

    // Custom keypad
    if (showKeypad) {
        PopKeyPad(
            modifier = Modifier.fillMaxWidth(),
            onKeyPress = { key ->
                amount = handleKeyPadInput(
                    key = key,
                    currentValue = amount,
                    allowDecimal = true,
                    maxLength = 999999999
                )
            }
        )
    }
}
```

---

## 9. Help Text

**Design System Component:** `Help Text` | **Figma Node:** `7765:36103` | **DS Section:** `10j`

### Property Mapping Table

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | Type: Info | `status = InputFieldStatus.Info` | `InputFieldStatus.kt` | `StatusMessage(status = InputFieldStatus.Info, message = "...", ...)` |
| molecules.md | Type: Success | `status = InputFieldStatus.Success` | `InputFieldStatus.kt` | `StatusMessage(status = InputFieldStatus.Success, message = "...", ...)` |
| molecules.md | Type: Error | `status = InputFieldStatus.Error` | `InputFieldStatus.kt` | `StatusMessage(status = InputFieldStatus.Error, message = "...", ...)` |
| molecules.md | Type: Warning | `status = InputFieldStatus.Warning` | `InputFieldStatus.kt` | `StatusMessage(status = InputFieldStatus.Warning, message = "...", ...)` |
| molecules.md | Padding: 12 | NO CODE EQUIVALENT FOUND | — | ⚠️ `StatusMessage` uses a fixed `PopSpacing.Spacing4` top padding — custom top padding must be applied via `Modifier.padding(top = ...)` on the caller side |
| molecules.md | Padding: 8 | NO CODE EQUIVALENT FOUND | — | ⚠️ Apply `Modifier.padding(top = 8.dp)` externally on `StatusMessage` |
| molecules.md | Padding: 0 | NO CODE EQUIVALENT FOUND | — | ⚠️ Apply `Modifier.padding(top = 0.dp)` externally |
| molecules.md | L-Icon: True | `showIcon = true` (default) | `PopInputFieldHelpers.kt` | `StatusMessage(status = ..., message = "...", showIcon = true)` |
| molecules.md | L-Icon: False | `showIcon = false` | `PopInputFieldHelpers.kt` | `StatusMessage(status = ..., message = "...", showIcon = false)` |
| molecules.md | R-icon: True | NO CODE EQUIVALENT FOUND | — | ⚠️ `StatusMessageRow` has no trailing icon slot |
| molecules.md | R-icon: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No trailing icon param |
| molecules.md | Link text: True | `onEditClick = { ... }` (renders inline `"Edit"` link) | `PopInputFieldHelpers.kt` | `StatusMessage(status = ..., message = "...", onEditClick = { navController.popBackStack() })` |
| molecules.md | Link text: False | `onEditClick = null` | `PopInputFieldHelpers.kt` | `StatusMessage(status = ..., message = "...", onEditClick = null)` |
| molecules.md | R-Overflow: True | NO CODE EQUIVALENT FOUND | — | ⚠️ No overflow fade in `StatusMessageRow` |
| molecules.md | R-Overflow: False | NO CODE EQUIVALENT FOUND | — | ⚠️ No overflow fade param |

### Notes

**Icon Mappings (from `PopInputFieldHelpers.kt` → `bridge_doc/icon_files.md`):**

| Status Type | Icon Used in Code | Icon Path |
|---|---|---|
| `InputFieldStatus.Success` | `Icons.CheckVerified` | `https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/check-verified-02-Filled.png` |
| `InputFieldStatus.Error` | `Icons.AlertHexagon` | `https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/alert-hexagon-Filled.png` |
| `InputFieldStatus.Info` | `Icons.InfoSquare` | `https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/info-circle.png` |
| `InputFieldStatus.Warning` | `Icons.AlertCircle` | `https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/alert-circle-Filled.png` |

- `StatusMessage()` is **internal** to `PopInputFieldHelpers.kt` (`internal fun`) — it is used automatically inside `PopInputFieldV2` when `statusMessage` is set. For **standalone** use outside of `PopInputFieldV2`, use it directly only within the same module.
- `Padding` (12, 8, 0) has no direct param — the composable applies a fixed `PopSpacing.Spacing4` top padding internally. Apply additional padding via `Modifier.padding(top = 12.dp)` passed to the `modifier` argument. ⚠️
- `R-icon` (trailing icon on the help text) has **no code equivalent** in `StatusMessageRow`. ⚠️
- `Link text: True` maps to `onEditClick` — the link text is always hardcoded as `"Edit"` with a dotted underline. Custom link labels are not supported. ⚠️
- `R-Overflow` (gradient overflow fade) has **no code equivalent**. ⚠️
- `horizontalArrangement` param controls alignment — use `Arrangement.Center` for OTP context (as done inside `PopOtpField`) or `Arrangement.Start` (default) for form fields.

### Full Code Pattern

```kotlin
// Standalone usage of StatusMessage (within module)
StatusMessage(
    status = InputFieldStatus.Error,
    message = "Invalid account number. Please re-enter.",
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp),
    showIcon = true,
    horizontalArrangement = Arrangement.Start,
    onEditClick = { navController.popBackStack() }
)

// Or via PopInputFieldV2 — statusMessage is auto-rendered
PopInputFieldV2(
    config = BasicInputFieldConfig(
        value = accountNumber,
        onValueChange = { accountNumber = it },
        hintText = "Account Number",
        status = InputFieldStatus.Warning,
        statusMessage = "Double-check your account number before proceeding",
        enabled = true
    )
)
```

---

## Additional Design System Coverage

### 10e. Input Item — Discrete (Single OTP Cell)
**DS Section:** `10e` | **Figma Node:** `4189:12557`

> ⚠️ **No standalone single-box composable in the codebase.** The `DiscreteOtpBox` and `DiscreteNakedOtpBox` composables are `private` inside `PopOtpField.kt` — they cannot be used independently. Always use the full `PopOtpField(config = DiscreteConfig(...))` organism. The single-cell design token (Size: 56/44, State, BG, Is active, Is filled) is fully handled by `DiscreteConfig` settings.

### 10k. Input Stack (Horizontal)
**DS Section:** `10k` | **Figma Node:** `21910:9493`

> ⚠️ **No dedicated stack composable.** Use a `Row` with `Modifier.weight()` on each `PopInputFieldV2`:

```kotlin
Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing12)
) {
    // Variant: Large + Small
    PopInputFieldV2(
        config = BasicInputFieldConfig(value = firstName, onValueChange = { firstName = it }, hintText = "First Name"),
        modifier = Modifier.weight(2f)
    )
    PopInputFieldV2(
        config = BasicInputFieldConfig(value = lastName, onValueChange = { lastName = it }, hintText = "Last Name"),
        modifier = Modifier.weight(1f)
    )
}

// Variant: Equal (Fill container)
Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing12)) {
    PopInputFieldV2(
        config = BasicInputFieldConfig(value = day, onValueChange = { day = it }, hintText = "Day"),
        modifier = Modifier.weight(1f)
    )
    PopInputFieldV2(
        config = BasicInputFieldConfig(value = month, onValueChange = { month = it }, hintText = "Month"),
        modifier = Modifier.weight(1f)
    )
}
```

| DS Variant | `Modifier.weight()` values |
|---|---|
| Large + Small | `weight(2f)` + `weight(1f)` |
| Small + Large | `weight(1f)` + `weight(2f)` |
| Equal (Fill container) | `weight(1f)` + `weight(1f)` |

---

## Deprecated API Reference

| Deprecated API | File | Replacement |
|---|---|---|
| `PopInputFieldV2(type = PopInputFieldType.Basic, ...)` | `PopInputFieldV2.kt` | `PopInputFieldV2(config = BasicInputFieldConfig(...))` |
| `PopInputFieldV2(type = PopInputFieldType.Small, ...)` | `PopInputFieldV2.kt` | `PopInputFieldV2(config = SmallInputFieldConfig(...))` |
| `PopInputFieldV2(type = PopInputFieldType.UnderlineNakedSmall, ...)` | `PopInputFieldV2.kt` | `PopInputFieldV2(config = UnderlineNakedSmallConfig(...))` |
| `PopInputFieldV2(type = PopInputFieldType.UnderlineNakedLarge, ...)` | `PopInputFieldV2.kt` | `PopInputFieldV2(config = UnderlineNakedLargeConfig(...))` |
| `PopInputFieldV2(type = PopInputFieldType.MobileInputField, ...)` | `PopInputFieldV2.kt` | `PopInputFieldV2(config = MobileInputFieldConfig(...))` |
| `PopInputFieldV2(type = PopInputFieldType.Search, ...)` | `PopInputFieldV2.kt` | `PopInputFieldV2(config = SearchInputFieldConfig(...))` |
| `OTPTextField(otpText, otpCount, onOtpTextChange, onDone)` | `OTPTextField.kt` | `PopOtpField(config = DiscreteConfig(...))` |
| `PopInputField(value, onValueChange, ...)` | `PopInputField.kt` | `PopInputFieldV2(config = BasicInputFieldConfig(...))` |
| `PopLabeledInputField(value, onValueChange, label, ...)` | `PopLabeledInputField.kt` | `PopInputFieldV2(config = BasicInputFieldConfig(hintText = label, ...))` |
| `PopPrefixInputField(value, onValueChange, prefixText, ...)` | `PopPrefixInputField.kt` | `PopInputFieldV2(config = MobileInputFieldConfig(...))` |
| `PopInputFieldView` (XML) | `PopInputFieldView.kt` | `PopInputFieldV2(config = BasicInputFieldConfig(...))` |
| `PopLabeledInputFieldView` (XML) | `PopLabeledInputFieldView.kt` | `PopInputFieldV2(config = BasicInputFieldConfig(...))` |
| `PopPhoneInputFieldView` (XML) | `PopPhoneInputFieldView.kt` | `PopInputFieldV2(config = MobileInputFieldConfig(...))` |
