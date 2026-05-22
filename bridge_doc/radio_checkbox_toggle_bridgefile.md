# Radio, Checkbox & Toggle — Design-to-Code Bridge

**Design source:** `atoms2.md` — Radio, Checkbox, Toggle sections  
**Codebase sources:** `PopRadio.kt` · `PopCheckBoxV2.kt` · `PopToggle.kt`

---

## A: Enum & State Mapping Tables

### A. Toggle State & Size Mapping [atoms2.md → PopToggle.kt]

| Design System File | Design Property Name | Codebase Enum / Value | Codebase File  | Code Pattern |
|---|---|---|---|---|---|
| atoms2.md | State = On | `PopToggleState.On` | PopToggle.kt | `PopToggle(state = PopToggleState.On, onStateChange = {})` |
| atoms2.md | State = Off | `PopToggleState.Off` | PopToggle.kt | `PopToggle(state = PopToggleState.Off, onStateChange = {})` |
| atoms2.md | Indeterminate = True/ False | `PopToggleState.Indeterminate` | PopToggle.kt | `PopToggle(state = PopToggleState.Indeterminate, onStateChange = {})` |
| atoms2.md | Size = Large | `PopToggleSize.Large` | PopToggle.kt  | `PopToggle(checked = true, onCheckedChange = {}, size = PopToggleSize.Large)` |
| atoms2.md | Size = Med | `PopToggleSize.Medium` | PopToggle.kt  | `PopToggle(checked = true, onCheckedChange = {}, size = PopToggleSize.Medium)` |
| atoms2.md | Is disabled = True | `enabled: Boolean = false` | PopToggle.kt | `PopToggle(checked = false, onCheckedChange = {}, enabled = false)` |
| atoms2.md | Is disabled = False | `enabled: Boolean = true` | PopToggle.kt | `PopToggle(checked = false, onCheckedChange = {}, enabled = true)` |
| atoms2.md | Promoted = True | `promoted: Boolean = true` | PopToggle.kt | `PopToggle(checked = true, onCheckedChange = {}, promoted = true)` |
| atoms2.md | Promoted = False | `promoted: Boolean = false` | PopToggle.kt | `PopToggle(checked = true, onCheckedChange = {}, promoted = false)` |

> **Two overloads available:**
> - `PopToggle(checked: Boolean, onCheckedChange: (Boolean) -> Unit, ...)` — use for simple On/Off
> - `PopToggle(state: PopToggleState, onStateChange: (PopToggleState) -> Unit, ...)` — use when Indeterminate state is needed

---

### B. Checkbox State & Align Mapping [atoms2.md → PopCheckBoxV2.kt]

| Design System File | Design Property Name | Codebase Enum / Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| atoms2.md | State = On (Checked) | `PopCheckBoxState.Selected` | PopCheckBoxV2.kt | `PopCheckBoxV2(state = PopCheckBoxState.Selected, onStateChange = {})` |
| atoms2.md | State = Off (Unchecked) | `PopCheckBoxState.Unselected` | PopCheckBoxV2.kt | `PopCheckBoxV2(state = PopCheckBoxState.Unselected, onStateChange = {})` |
| atoms2.md | Indeterminate = True | `PopCheckBoxState.Indeterminate` | PopCheckBoxV2.kt | `PopCheckBoxV2(state = PopCheckBoxState.Indeterminate, onStateChange = {})` |

| atoms2.md | Is disabled = True | `enabled: Boolean = false` | PopCheckBoxV2.kt | `PopCheckBoxV2(state = ..., onStateChange = {}, enabled = false)` |
| atoms2.md | Is disabled = False | `enabled: Boolean = true` | PopCheckBoxV2.kt | `PopCheckBoxV2(state = ..., onStateChange = {}, enabled = true)` |
| atoms2.md | Promoted = True | `promoted: Boolean = true` | PopCheckBoxV2.kt | `PopCheckBoxV2(state = ..., onStateChange = {}, promoted = true)` |
| atoms2.md | Promoted = False | `promoted: Boolean = false` | PopCheckBoxV2.kt | `PopCheckBoxV2(state = ..., onStateChange = {}, promoted = false)` |

> **Promoted + Disabled rule:** When both `promoted = true` and `enabled = false`, the promoted styling is suppressed. The component renders the same visual as `promoted = false, enabled = false`. Disabled always overrides promoted.
---

## B: Radio Variant Mapping Table

### D. Radio — Base Variants [atoms2.md → PopRadio.kt]

> **Fixed size:** 20dp outer ring, 8dp inner dot. No size enum — Radio has a single fixed size only.

| Design System File | Design Property | Codebase Param / Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| atoms2.md | `State: Off` | `selected = false` | PopRadio.kt | `PopRadio(selected = false, onClick = {})` |
| atoms2.md | `State: On` | `selected = true` | PopRadio.kt | `PopRadio(selected = true, onClick = {})` |
| atoms2.md | `Promoted: True` | `promoted = true` | PopRadio.kt | `PopRadio(selected = false, onClick = {}, promoted = true)` |
| atoms2.md | `Promoted: False` | `promoted = false` (default) | PopRadio.kt | `PopRadio(selected = false, onClick = {} ,promoted = false)` |
| atoms2.md | `Is disabled: True` | `enabled = false` | PopRadio.kt | `PopRadio(selected = false, onClick = {}, enabled = false)` |
| atoms2.md | `Is disabled: False` | `enabled = true` (default) | PopRadio.kt | `PopRadio(selected = false, onClick = {} , enabled = true)` |


### E. Radio with Text — Variants [atoms2.md → PopRadio.kt]

> **Assembly note:** `PopRadio.kt` provides the control only. "Radio with Text" is assembled by the caller — place `PopRadio()` inside a `Row` with `Text()` composables for title and body. There is no dedicated `PopRadioWithText` composable.

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| atoms2.md | `Align: Left` |  No codebase equivalent ⚠️ | PopRadio.kt | No codebase equivalent ⚠️ |
| atoms2.md | `Align: Right` |  No codebase equivalent ⚠️ | PopRadio.kt | No codebase equivalent ⚠️ |
| atoms2.md | `Title: True` |  No codebase equivalent ⚠️ | PopRadio.kt |No codebase equivalent ⚠️ |
| atoms2.md | `Title: False` |  No codebase equivalent ⚠️ | PopRadio.kt |No codebase equivalent ⚠️ |
| atoms2.md | `Body: True` |  No codebase equivalent ⚠️` | PopRadio.kt | No codebase equivalent ⚠️ |
| atoms2.md | `Body: False` |  No codebase equivalent ⚠️ | PopRadio.kt | No codebase equivalent ⚠️ |
| atoms2.md | `L-Slot: True` | No codebase equivalent ⚠️ | PopRadio.kt | No codebase equivalent ⚠️|
| atoms2.md | `L-Slot: False` | No codebase equivalent ⚠️ | PopRadio.kt | No codebase equivalent ⚠️|
| atoms2.md | `R-Slot: True` | No codebase equivalent ⚠️ | PopRadio.kt | No codebase equivalent ⚠️ |
| atoms2.md | `R-Slot: False` | No codebase equivalent ⚠️ | PopRadio.kt | No codebase equivalent ⚠️|


**Stack variant:** `Radio with text - Stack` → assemble as a `Column` of `Row { PopRadio(...) + Text }` items separated by `PopDivider()`. No dedicated stack composable exists.

```kotlin
// Radio Stack example (Count=3)
Column {
    Row(verticalAlignment = Alignment.CenterVertically) {
        PopRadio(selected = selectedIndex == 0, onClick = { selectedIndex = 0 })
        Spacer(Modifier.width(12.dp))
        Text("Option A")
    }
    PopDivider()
    Row(verticalAlignment = Alignment.CenterVertically) {
        PopRadio(selected = selectedIndex == 1, onClick = { selectedIndex = 1 })
        Spacer(Modifier.width(12.dp))
        Text("Option B")
    }
    PopDivider()
    Row(verticalAlignment = Alignment.CenterVertically) {
        PopRadio(selected = selectedIndex == 2, onClick = { selectedIndex = 2 })
        Spacer(Modifier.width(12.dp))
        Text("Option C")
    }
}
```

### G. Checkbox with Text — Variants [atoms2.md → PopCheckBoxWithTextV2.kt]

> **Native composable:** Unlike Radio with Text (caller-assembled), Checkbox with Text has a dedicated composable: `PopCheckBoxWithTextV2()`.

```kotlin
@Composable
fun PopCheckBoxWithTextV2(
    state: PopCheckBoxState,
    onStateChange: (PopCheckBoxState) -> Unit,
    title: AnnotatedString,
    body: String?,
    modifier: Modifier = Modifier,
    promoted: Boolean = false,
    enabled: Boolean = true,
    align: PopCheckBoxAlign = PopCheckBoxAlign.Left,
    onTitleClick: ((Int) -> Unit)? = null,
)
```

| Design System File | Design Property | Codebase Param / Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| atoms2.md | `State: Off` | `state = PopCheckBoxState.Unselected` | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(state = PopCheckBoxState.Unselected, onStateChange = {}, title = AnnotatedString("Label"), body = null)` |
| atoms2.md | `State: On` | `state = PopCheckBoxState.Selected` | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(state = PopCheckBoxState.Selected, onStateChange = {}, title = AnnotatedString("Label"), body = null)` |
| atoms2.md | `Indeterminate: True` | `state = PopCheckBoxState.Indeterminate` | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(state = PopCheckBoxState.Indeterminate, onStateChange = {}, title = AnnotatedString("Label"), body = null)` |
| atoms2.md | `Align: Left` | `align = PopCheckBoxAlign.Left` (default) | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(..., align = PopCheckBoxAlign.Left)` |
| atoms2.md | `Align: Right` | `align = PopCheckBoxAlign.Right` | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(..., align = PopCheckBoxAlign.Right)` |
| atoms2.md | `Title: True` | `title = AnnotatedString("Label")` — required param; always set real copy | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(..., title = AnnotatedString("Your Label"), body = null)` |
| atoms2.md | `Body: True` | `body = "Description"` | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(..., title = AnnotatedString("Label"), body = "Description")` |
| atoms2.md | `Body: False` | `body = null` | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(..., title = AnnotatedString("Label"), body = null)` |
| atoms2.md | `Promoted: True` | `promoted = true` | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(..., promoted = true)` |
| atoms2.md | `Promoted: False` | `promoted = false` (default) | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(..., promoted = false)` |
| atoms2.md | `Is disabled: True` | `enabled = false` | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(..., enabled = false)` |
| atoms2.md | `Is disabled: False` | `enabled = true` (default) | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(..., enabled = true)` |
| atoms2.md | `L-Slot: False` |  | PopCheckBoxV2.kt | No codebase equivalent found ⚠️ |


**Typography inside `PopCheckBoxWithTextV2`:**

| Text element | Font | Weight | Size | Line Height | Color (selected) | Color (promoted) | Color (default) |
|---|---|---|---|---|---|---|---|
| Title | Figtree | 500 | 14sp | 20sp | success text | brand text | `TextColor.Primary` |
| Body | Figtree | 400 | 12sp | 20sp | success text | brand text | `TextColor.Secondary` |

Layout: Row gap between control and text column = **12dp** · Gap between title and body = **2dp**

**Stack variant:** `Checkbox with text - Stack` → assemble as a `Column` of `PopCheckBoxWithTextV2` items separated by `PopDivider()`. No dedicated stack composable exists.

```kotlin
// Checkbox Stack example (Count=3)
Column {
    PopCheckBoxWithTextV2(
        state = states[0], onStateChange = { states[0] = it },
        title = AnnotatedString("Option A"), body = null
    )
    PopDivider()
    PopCheckBoxWithTextV2(
        state = states[1], onStateChange = { states[1] = it },
        title = AnnotatedString("Option B"), body = "Description"
    )
    PopDivider()
    PopCheckBoxWithTextV2(
        state = states[2], onStateChange = { states[2] = it },
        title = AnnotatedString("Option C"), body = null
    )
}
```

---

## Quick Reference — Component Selection

| Design component | Use case | Codebase composable |
|---|---|---|
| Base Radio | Radio inside a slot | `PopRadio()` |
| Radio with Text | Single standalone labelled radio | `Row { PopRadio(); Text() }` (caller-assembled) |
| Radio with Text - Stack | Group of radio options in a list | `Column` of `PopRadio` rows + `PopDivider()` |
| Base Checkbox | Checkbox inside a slot | `PopCheckBoxV2()` |
| Checkbox with Text | Single standalone labelled checkbox | `PopCheckBoxWithTextV2()` |
| Checkbox with Text - Stack | Group of checkbox options in a list | `Column` of `PopCheckBoxWithTextV2` + `PopDivider()` |
| Toggle | Binary on/off setting | `PopToggle()` |
| Toggle (indeterminate) | Loading / unknown state | `PopToggle(state = PopToggleState.Indeterminate, ...)` |
| Offer Toggle | Offer/payment context toggle | `PopOfferToggle()` |

> **Never use:** `RadioButton()`, `Checkbox()`, or `Switch()` from Jetpack Compose Material3 directly — always use the POP system equivalents above.

