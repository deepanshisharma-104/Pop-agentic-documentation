# Radio, Checkbox & Toggle — Design-to-Code Bridge

**Design source:** `atoms2.md` — Radio, Checkbox, Toggle sections  
**Codebase sources:** `PopRadio.kt` · `PopCheckBoxV2.kt` · `PopToggle.kt` · `PopOfferToggle.kt`

---

## A: Enum & State Mapping Tables

### A. Toggle State & Size Mapping [atoms2.md → PopToggle.kt]

| Design System File | Design Property Name | Codebase Enum / Value | Codebase File | Resolved Dp | Code Pattern |
|---|---|---|---|---|---|
| atoms2.md | State = On | `PopToggleState.On` | PopToggle.kt | — | `PopToggle(state = PopToggleState.On, onStateChange = {})` |
| atoms2.md | State = Off | `PopToggleState.Off` | PopToggle.kt | — | `PopToggle(state = PopToggleState.Off, onStateChange = {})` |
| atoms2.md | State = Indeterminate | `PopToggleState.Indeterminate` | PopToggle.kt | — | `PopToggle(state = PopToggleState.Indeterminate, onStateChange = {})` |
| atoms2.md | Size = Large | `PopToggleSize.Large` | PopToggle.kt | trackWidth=40dp, containerHeight=28dp, knobWidth=24dp | `PopToggle(checked = true, onCheckedChange = {}, size = PopToggleSize.Large)` |
| atoms2.md | Size = Med | `PopToggleSize.Medium` | PopToggle.kt | trackWidth=32dp, containerHeight=24dp, knobWidth=20dp | `PopToggle(checked = true, onCheckedChange = {}, size = PopToggleSize.Medium)` |
| atoms2.md | Is disabled = True | `enabled: Boolean = false` | PopToggle.kt | — | `PopToggle(checked = false, onCheckedChange = {}, enabled = false)` |
| atoms2.md | Is disabled = False | `enabled: Boolean = true` | PopToggle.kt | — | `PopToggle(checked = false, onCheckedChange = {}, enabled = true)` |
| atoms2.md | Promoted = True | `promoted: Boolean = true` | PopToggle.kt | — | `PopToggle(checked = true, onCheckedChange = {}, promoted = true)` |
| atoms2.md | Promoted = False | `promoted: Boolean = false` | PopToggle.kt | — | `PopToggle(checked = true, onCheckedChange = {}, promoted = false)` |

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
| atoms2.md | Align = Left | `PopCheckBoxAlign.Left` | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(..., align = PopCheckBoxAlign.Left)` |
| atoms2.md | Align = Right | `PopCheckBoxAlign.Right` | PopCheckBoxV2.kt | `PopCheckBoxWithTextV2(..., align = PopCheckBoxAlign.Right)` |
| atoms2.md | Is disabled = True | `enabled: Boolean = false` | PopCheckBoxV2.kt | `PopCheckBoxV2(state = ..., onStateChange = {}, enabled = false)` |
| atoms2.md | Is disabled = False | `enabled: Boolean = true` | PopCheckBoxV2.kt | `PopCheckBoxV2(state = ..., onStateChange = {}, enabled = true)` |
| atoms2.md | Promoted = True | `promoted: Boolean = true` | PopCheckBoxV2.kt | `PopCheckBoxV2(state = ..., onStateChange = {}, promoted = true)` |
| atoms2.md | Promoted = False | `promoted: Boolean = false` | PopCheckBoxV2.kt | `PopCheckBoxV2(state = ..., onStateChange = {}, promoted = false)` |

---

## B: Radio Variant Mapping Table

### D. Radio — Base Variants [atoms2.md → PopRadio.kt]

> **Fixed size:** 20dp outer ring, 8dp inner dot. No size enum — Radio has a single fixed size only.

| Design System File | Design Property Name | Codebase Enum / Value | Codebase File | Fixed Size | Code Pattern |
|---|---|---|---|---|---|
| atoms2.md | State=Off, Promoted=False, Is disabled=False | `selected = false, promoted = false, enabled = true` | PopRadio.kt | 20dp outer / 8dp dot | `PopRadio(selected = false, onClick = {})` |
| atoms2.md | State=On, Promoted=False, Is disabled=False | `selected = true, promoted = false, enabled = true` | PopRadio.kt | 20dp outer / 8dp dot | `PopRadio(selected = true, onClick = {})` |
| atoms2.md | State=Off, Promoted=False, Is disabled=True | `selected = false, enabled = false` | PopRadio.kt | 20dp outer / 8dp dot | `PopRadio(selected = false, onClick = {}, enabled = false)` |
| atoms2.md | State=On, Promoted=False, Is disabled=True | `selected = true, enabled = false` | PopRadio.kt | 20dp outer / 8dp dot | `PopRadio(selected = true, onClick = {}, enabled = false)` |
| atoms2.md | State=Off, Promoted=True, Is disabled=False | `selected = false, promoted = true, enabled = true` | PopRadio.kt | 20dp outer / 8dp dot | `PopRadio(selected = false, onClick = {}, promoted = true)` |
| atoms2.md | State=On, Promoted=True, Is disabled=False | `selected = true, promoted = true, enabled = true` | PopRadio.kt | 20dp outer / 8dp dot | `PopRadio(selected = true, onClick = {}, promoted = true)` |

**Visual states (from codebase):**

| State combo | Border | Background | Glow |
|---|---|---|---|
| Unselected, default | `BorderColor.PrimaryInvert` | none | none |
| Unselected, promoted | `BorderColor.Brand` | none | none |
| Unselected, disabled | `BorderColor.Tertiary` | none | none |
| Selected, enabled | none | `GradientPreset.SurfacePrimary.gradient` (white→grey) | `rgba(255,255,255,0.42)` |
| Selected, promoted | none | Brand linear gradient 180° | `#FF500B9E` orange glow |
| Selected, disabled | none | `SurfaceColor.SecondaryDisabled` (`#262626`) | none |

---

### E. Radio with Text — Variants [atoms2.md → PopRadio.kt]

> **Assembly note:** `PopRadio.kt` provides the control only. "Radio with Text" is assembled by the caller — place `PopRadio()` inside a `Row` with `Text()` composables for title and body. There is no dedicated `PopRadioWithText` composable.

| Design Variant | Align | Control position | Title | Body | L-Slot | R-Slot | Recommended Compose Pattern |
|---|---|---|---|---|---|---|---|
| Size=Small, Align=Left, Title=True, Body=False | Left | Leading | ✓ | — | — | — | `Row { PopRadio(...); Text(title) }` |
| Size=Small, Align=Left, Title=True, Body=True | Left | Leading | ✓ | ✓ | — | — | `Row { PopRadio(...); Column { Text(title); Text(body) } }` |
| Size=Small, Align=Right, Title=True, Body=False | Right | Trailing | ✓ | — | — | — | `Row { Text(title); Spacer(Modifier.weight(1f)); PopRadio(...) }` |
| Size=Small, Align=Right, Title=True, Body=True | Right | Trailing | ✓ | ✓ | — | — | `Row { Column { Text(title); Text(body) }; Spacer(Modifier.weight(1f)); PopRadio(...) }` |
| Size=Med, Align=Left, L-Slot=True, Title=True, Body=True | Left | Leading | ✓ | ✓ | ✓ (Avatar/Icon) | — | `Row { PopRadio(...); Avatar(...); Column { Text(title); Text(body) } }` |
| Size=Small, Align=Left, R-Slot=True, Title=True, Body=False | Left | Leading | ✓ | — | — | ✓ (Badge/Icon) | `Row { PopRadio(...); Text(title); Spacer(Modifier.weight(1f)); Badge(...) }` |

**Slot usage guidance:**

| Slot | Position | When to enable | What goes inside |
|---|---|---|---|
| `L-Slot` | Left of label | Visual identifier for the option | `PopAvatar(type = AvatarType.Brand, ...)` for bank/app logos; `PopAvatar(type = AvatarType.People, ...)` for contacts; Icon for category |
| `R-Slot` | Right of label | Trailing metadata | `PopBadge` for counts/status; Icon for disclosure; inline price text |

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

---

## C: Checkbox Variant Mapping Tables

### F. Checkbox — Base Variants [atoms2.md → PopCheckBoxV2.kt]

> **Fixed size:** 20dp (8dp icon + 6dp padding each side). Shape: `RoundedCornerShape(PopRadius.ExtraSmall)` = 4dp corners. No size enum.

| Design System File | Design Variant | Codebase Enum / Value | Codebase File | Fixed Size | Code Pattern |
|---|---|---|---|---|---|
| atoms2.md | State=Off, Indeterminate=False, Promoted=False, Is disabled=False | `state = Unselected, promoted = false, enabled = true` | PopCheckBoxV2.kt | 20dp | `PopCheckBoxV2(state = PopCheckBoxState.Unselected, onStateChange = {})` |
| atoms2.md | State=On, Indeterminate=False, Promoted=False, Is disabled=False | `state = Selected, promoted = false, enabled = true` | PopCheckBoxV2.kt | 20dp | `PopCheckBoxV2(state = PopCheckBoxState.Selected, onStateChange = {})` |
| atoms2.md | State=Off, Indeterminate=False, Promoted=False, Is disabled=True | `state = Unselected, enabled = false` | PopCheckBoxV2.kt | 20dp | `PopCheckBoxV2(state = PopCheckBoxState.Unselected, onStateChange = {}, enabled = false)` |
| atoms2.md | State=On, Indeterminate=False, Promoted=False, Is disabled=True | `state = Selected, enabled = false` | PopCheckBoxV2.kt | 20dp | `PopCheckBoxV2(state = PopCheckBoxState.Selected, onStateChange = {}, enabled = false)` |
| atoms2.md | State=On, Indeterminate=True, Promoted=False, Is disabled=False | `state = Indeterminate, promoted = false, enabled = true` | PopCheckBoxV2.kt | 20dp | `PopCheckBoxV2(state = PopCheckBoxState.Indeterminate, onStateChange = {})` |
| atoms2.md | State=On, Indeterminate=True, Promoted=False, Is disabled=True | `state = Indeterminate, enabled = false` | PopCheckBoxV2.kt | 20dp | `PopCheckBoxV2(state = PopCheckBoxState.Indeterminate, onStateChange = {}, enabled = false)` |
| atoms2.md | State=On, Indeterminate=True, Promoted=True, Is disabled=False | `state = Indeterminate, promoted = true, enabled = true` | PopCheckBoxV2.kt | 20dp | `PopCheckBoxV2(state = PopCheckBoxState.Indeterminate, onStateChange = {}, promoted = true)` |
| atoms2.md | State=On, Indeterminate=False, Promoted=True, Is disabled=False | `state = Selected, promoted = true, enabled = true` | PopCheckBoxV2.kt | 20dp | `PopCheckBoxV2(state = PopCheckBoxState.Selected, onStateChange = {}, promoted = true)` |
| atoms2.md | State=Off, Indeterminate=False, Promoted=True, Is disabled=False | `state = Unselected, promoted = true, enabled = true` | PopCheckBoxV2.kt | 20dp | `PopCheckBoxV2(state = PopCheckBoxState.Unselected, onStateChange = {}, promoted = true)` |
| atoms2.md | Promoted=True, Is disabled=True (any state) | `promoted = true, enabled = false` → falls back to disabled visual | PopCheckBoxV2.kt | 20dp | `PopCheckBoxV2(state = ..., onStateChange = {}, promoted = true, enabled = false)` |

> **Promoted + Disabled rule:** When both `promoted = true` and `enabled = false`, the promoted styling is suppressed. The component renders the same visual as `promoted = false, enabled = false`. Disabled always overrides promoted.

**Visual states (from codebase):**

| State combo | Border | Background | Glow |
|---|---|---|---|
| Unselected, default | `BorderColor.PrimaryInvert` | none | none |
| Unselected, disabled | `BorderColor.Tertiary` | none | none |
| Selected, enabled | none | `GradientPreset.SurfacePrimary` (white→grey) | `rgba(255,255,255,0.42)` blur 9dp |
| Selected, promoted | none | Brand linear gradient 180° | `rgba(255,80,11,0.62)` blur 9dp |
| Selected, disabled | none | `SurfaceColor.SecondaryDisabled` | none |
| Indeterminate, enabled | — | same as Selected, enabled | same as Selected |

---

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

| Design System File | Design Variant | `state` | `align` | `body` | `promoted` | `enabled` | Code Pattern |
|---|---|---|---|---|---|---|---|
| atoms2.md | Size=Small, Align=Left, Title=True, Body=False, State=Off | `Unselected` | `Left` | `null` | `false` | `true` | `PopCheckBoxWithTextV2(state = PopCheckBoxState.Unselected, onStateChange = {}, title = AnnotatedString("Label"), body = null)` |
| atoms2.md | Size=Small, Align=Left, Title=True, Body=True, State=Off | `Unselected` | `Left` | `"Description"` | `false` | `true` | `PopCheckBoxWithTextV2(state = PopCheckBoxState.Unselected, onStateChange = {}, title = AnnotatedString("Label"), body = "Description")` |
| atoms2.md | Size=Small, Align=Left, Title=True, Body=True, State=On | `Selected` | `Left` | `"Description"` | `false` | `true` | `PopCheckBoxWithTextV2(state = PopCheckBoxState.Selected, onStateChange = {}, title = AnnotatedString("Label"), body = "Description")` |
| atoms2.md | Size=Small, Align=Right, Title=True, Body=False, State=Off | `Unselected` | `Right` | `null` | `false` | `true` | `PopCheckBoxWithTextV2(state = PopCheckBoxState.Unselected, onStateChange = {}, title = AnnotatedString("Label"), body = null, align = PopCheckBoxAlign.Right)` |
| atoms2.md | Size=Small, Align=Right, Title=True, Body=True, State=On | `Selected` | `Right` | `"Description"` | `false` | `true` | `PopCheckBoxWithTextV2(state = PopCheckBoxState.Selected, onStateChange = {}, title = AnnotatedString("Label"), body = "Description", align = PopCheckBoxAlign.Right)` |
| atoms2.md | Size=Med, Align=Left, Title=True, Body=True, Promoted=True, State=On | `Selected` | `Left` | `"Description"` | `true` | `true` | `PopCheckBoxWithTextV2(state = PopCheckBoxState.Selected, onStateChange = {}, title = AnnotatedString("Label"), body = "Description", promoted = true)` |
| atoms2.md | Size=Small, Align=Left, Title=True, Body=True, Is disabled=True | `Unselected` | `Left` | `"Description"` | `false` | `false` | `PopCheckBoxWithTextV2(state = PopCheckBoxState.Unselected, onStateChange = {}, title = AnnotatedString("Label"), body = "Description", enabled = false)` |
| atoms2.md | Indeterminate=True, Align=Left, Title=True, Body=True | `Indeterminate` | `Left` | `"Description"` | `false` | `true` | `PopCheckBoxWithTextV2(state = PopCheckBoxState.Indeterminate, onStateChange = {}, title = AnnotatedString("Label"), body = "Description")` |

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

## D: Toggle Variant Mapping Tables

### H. Toggle — Base Variants [atoms2.md → PopToggle.kt]

| Design System File | Design Variant | Overload | `size` | `checked` / `state` | `promoted` | `enabled` | `indeterminate` | Code Pattern |
|---|---|---|---|---|---|---|---|---|
| atoms2.md | Size=Large, State=Off, Promoted=False, Is disabled=False | Boolean | `Large` | `false` | `false` | `true` | `false` | `PopToggle(checked = false, onCheckedChange = {}, size = PopToggleSize.Large)` |
| atoms2.md | Size=Large, State=On, Promoted=False, Is disabled=False | Boolean | `Large` | `true` | `false` | `true` | `false` | `PopToggle(checked = true, onCheckedChange = {}, size = PopToggleSize.Large)` |
| atoms2.md | Size=Large, State=On, Promoted=True, Is disabled=False | Boolean | `Large` | `true` | `true` | `true` | `false` | `PopToggle(checked = true, onCheckedChange = {}, size = PopToggleSize.Large, promoted = true)` |
| atoms2.md | Size=Large, State=Off, Promoted=False, Is disabled=True | Boolean | `Large` | `false` | `false` | `false` | `false` | `PopToggle(checked = false, onCheckedChange = {}, size = PopToggleSize.Large, enabled = false)` |
| atoms2.md | Size=Large, State=On, Promoted=False, Is disabled=True | Boolean | `Large` | `true` | `false` | `false` | `false` | `PopToggle(checked = true, onCheckedChange = {}, size = PopToggleSize.Large, enabled = false)` |
| atoms2.md | Size=Large, State=Off, Promoted=True, Is disabled=False | Boolean | `Large` | `false` | `true` | `true` | `false` | `PopToggle(checked = false, onCheckedChange = {}, size = PopToggleSize.Large, promoted = true)` |
| atoms2.md | Size=Large, Indeterminate=True, Is disabled=False | State | `Large` | `Indeterminate` | `false` | `true` | — | `PopToggle(state = PopToggleState.Indeterminate, onStateChange = {}, size = PopToggleSize.Large)` |
| atoms2.md | Size=Large, Indeterminate=True, Is disabled=True | State | `Large` | `Indeterminate` | `false` | `false` | — | `PopToggle(state = PopToggleState.Indeterminate, onStateChange = {}, size = PopToggleSize.Large, enabled = false)` |
| atoms2.md | Size=Med, State=Off, Promoted=False, Is disabled=False | Boolean | `Medium` | `false` | `false` | `true` | `false` | `PopToggle(checked = false, onCheckedChange = {}, size = PopToggleSize.Medium)` |
| atoms2.md | Size=Med, State=On, Promoted=False, Is disabled=False | Boolean | `Medium` | `true` | `false` | `true` | `false` | `PopToggle(checked = true, onCheckedChange = {}, size = PopToggleSize.Medium)` |
| atoms2.md | Size=Med, State=On, Promoted=True, Is disabled=False | Boolean | `Medium` | `true` | `true` | `true` | `false` | `PopToggle(checked = true, onCheckedChange = {}, size = PopToggleSize.Medium, promoted = true)` |
| atoms2.md | Size=Med, State=Off, Promoted=False, Is disabled=True | Boolean | `Medium` | `false` | `false` | `false` | `false` | `PopToggle(checked = false, onCheckedChange = {}, size = PopToggleSize.Medium, enabled = false)` |

**Visual states (from codebase):**

| State combo | Track background | Border | Glow |
|---|---|---|---|
| Off, default | Secondary gradient | `BorderColor.Tertiary` | none |
| On, default | `GradientPreset.SurfacePrimary.gradient` | `BorderColor.Secondary` | white glow |
| On, promoted | Brand gradient | `PopColor.Brand.Brand500` | `#FF500B9E` orange glow |
| Disabled (any) | disabled gradient | muted | none |
| Indeterminate | animated center pill | — | none |

> **Promoted + Disabled rule:** Same as Checkbox — when `promoted = true` + `enabled = false`, the promoted styling is suppressed and the component renders as standard disabled. Disabled always overrides promoted.

> **Indeterminate rule:** Only valid on Toggle and Checkbox — **never on Radio.** Use the state-based overload `PopToggle(state = PopToggleState.Indeterminate, ...)` when indeterminate is needed.

---

### I. OfferToggle — All Variants [atoms2.md → PopOfferToggle.kt]

> **OfferToggle is a separate specialized component** — not interchangeable with `PopToggle`. It is used exclusively in offer/payment contexts. Its internal toggle is always `PopToggleSize.Medium` and is not configurable by the caller.

```kotlin
@Composable
fun PopOfferToggle(
    title: String,
    bodyText: String,
    type: OfferType,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onInfoClick: (() -> Unit)? = null,
    @DrawableRes iconRes: Int? = null,
    @DrawableRes infoIconRes: Int? = null,
    bodyAnnotatedText: AnnotatedString? = null,
    bodyInlineContent: Map<String, InlineTextContent>? = null
)
```

**Container specs:** Height = 72dp · Shape = `TicketShape(cornerRadius = PopRadius.XLarge, cutoutRadius = 10dp, rightEdgeOffset = 94dp)` · Gradient left panel = 187dp wide · Internal toggle = `PopToggle(size = PopToggleSize.Medium)`

| Design System File | Design Variant | `type` | `checked` | `enabled` | Gradient Preset | Code Pattern |
|---|---|---|---|---|---|---|
| atoms2.md | Type=Offer, State=On, enabled | `OfferType.Offer` | `true` | `true` | `GradientPreset.OfferGradient` | `PopOfferToggle(title = "Offer", bodyText = "Save ₹100", type = OfferType.Offer, checked = true, onCheckedChange = {})` |
| atoms2.md | Type=Offer, State=Off, enabled | `OfferType.Offer` | `false` | `true` | `GradientPreset.OfferGradient` | `PopOfferToggle(title = "Offer", bodyText = "Save ₹100", type = OfferType.Offer, checked = false, onCheckedChange = {})` |
| atoms2.md | Type=Offer, disabled | `OfferType.Offer` | `false` | `false` | disabled variant | `PopOfferToggle(title = "Offer", bodyText = "Save ₹100", type = OfferType.Offer, checked = false, onCheckedChange = {}, enabled = false)` |
| atoms2.md | Type=Cash, State=On, enabled | `OfferType.Cash` | `true` | `true` | `GradientPreset.CashGradient` | `PopOfferToggle(title = "Cashback", bodyText = "₹50 back", type = OfferType.Cash, checked = true, onCheckedChange = {})` |
| atoms2.md | Type=Cash, State=Off, enabled | `OfferType.Cash` | `false` | `true` | `GradientPreset.CashGradient` | `PopOfferToggle(title = "Cashback", bodyText = "₹50 back", type = OfferType.Cash, checked = false, onCheckedChange = {})` |
| atoms2.md | Type=Cash, disabled | `OfferType.Cash` | `false` | `false` | disabled variant | `PopOfferToggle(title = "Cashback", bodyText = "₹50 back", type = OfferType.Cash, checked = false, onCheckedChange = {}, enabled = false)` |
| atoms2.md | Type=PayIn3, State=On, enabled | `OfferType.PayIn3` | `true` | `true` | `GradientPreset.PayIn3Gradient` | `PopOfferToggle(title = "Pay in 3", bodyText = "3 easy EMIs", type = OfferType.PayIn3, checked = true, onCheckedChange = {})` |
| atoms2.md | Type=PayIn3, State=Off, enabled | `OfferType.PayIn3` | `false` | `true` | `GradientPreset.PayIn3Gradient` | `PopOfferToggle(title = "Pay in 3", bodyText = "3 easy EMIs", type = OfferType.PayIn3, checked = false, onCheckedChange = {})` |
| atoms2.md | Type=PayIn3, disabled | `OfferType.PayIn3` | `false` | `false` | disabled variant | `PopOfferToggle(title = "Pay in 3", bodyText = "3 easy EMIs", type = OfferType.PayIn3, checked = false, onCheckedChange = {}, enabled = false)` |
| atoms2.md | Type=PopCoin, State=On, enabled | `OfferType.PopCoin` | `true` | `true` | `GradientPreset.PopCoinGradient` | `PopOfferToggle(title = "PopCoins", bodyText = "Use 500 coins", type = OfferType.PopCoin, checked = true, onCheckedChange = {})` |
| atoms2.md | Type=PopCoin, State=Off, enabled | `OfferType.PopCoin` | `false` | `true` | `GradientPreset.PopCoinGradient` | `PopOfferToggle(title = "PopCoins", bodyText = "Use 500 coins", type = OfferType.PopCoin, checked = false, onCheckedChange = {})` |
| atoms2.md | Type=PopCoin, disabled | `OfferType.PopCoin` | `false` | `false` | disabled variant | `PopOfferToggle(title = "PopCoins", bodyText = "Use 500 coins", type = OfferType.PopCoin, checked = false, onCheckedChange = {}, enabled = false)` |

**Typography inside `PopOfferToggle`:**

| Text element | Token | Notes |
|---|---|---|
| Title | `PopTypography.labelMedium` | Single line, truncated |
| Body | `PopTypography.labelXSmall` | Can use `bodyAnnotatedText` for inline links/spans |

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
