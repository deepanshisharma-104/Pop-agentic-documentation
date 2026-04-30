# Organisms — POP Design System

> Organisms are fully composed UI sections assembled from molecules and atoms. They represent complete, screen-ready UI patterns used directly within product screens.

> Components prefixed with `.` (e.g. `.empty state - base`, `.footer action P3 offer item`) are **internal building blocks** — they are not placed directly in UI screens unless noted. They are composed into higher-level organisms automatically.

---

## Component Index

| # | Component Group | Figma Page |
|---|---|---|
| 1 | Top Bar | `↪️ ❖ Top bar ✅` |
| 2 | Toast & Toast Bar | `↪️ ❖ Toast ✅` |
| 3 | Offers (Banner, POPcoin badges, Amount units, Offer toggles, Offer strip, Issuance callout, P3 offer items) | `↪️ ❖ Offers ✅` |
| 4 | Bottom Sheet (Default, Segmented) | `↪️ ❖ Bottom sheet ✅` |
| 5 | Popup | `↪️ ❖ Popup ✅` |
| 6 | Carousel (Standard, Edge to Edge, Fancy Carousel, Fancy Carousel Unit) | `↪️ ❖ Carousel ✅` |
| 7 | Stacks (Content Card Stack) | `↪️ ❖ Stacks 🚧` |
| 8 | Empty State | `↪️ ❖ Empty state ✅` |
| 9 | Bento Grid (all variants + Amount/POPcoin subcomponent) | `↪️ ❖ Bento grid 🚧` |
| 10 | RCBP Card (Leading item, Local Card, Card item, Card set, Background assets) | `↪️ RCBP Cards` |

---
---

# 1. Top Bar — POP Design System

The Top Bar is the primary navigation and branding header at the top of a screen. It supports merchant branding (merch) in large, medium, and small configurations, as well as minimal states for deep-flow screens.

---

## 1a. Top Bar

**Component name:** `Top bar`
**Figma page:** `↪️ ❖ Top bar ✅`
**Node ID:** `1591:15413`
**Use when:** Placing a persistent top navigation bar on any screen. Choose the size and title bar combination that matches the screen hierarchy and available vertical space.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large - Merch | Large - Merch, Med - Merch, Small - Merch, Small - No merch, Minimum | Controls height and merchant branding area |
| `Title bar` | Variant | True | True, False | Shows a secondary title bar row beneath the main bar |
| `show Stroke` | Variant | True | True, False | Bottom border/divider line below the bar |

### Size Options

| Size | Description |
|---|---|
| **Large - Merch** | Full-height bar with merchant branding — use on home / main landing screens |
| **Med - Merch** | Medium height with merchant branding — use on mid-level category screens |
| **Small - Merch** | Compact bar with merchant branding — use on sub-category or list screens |
| **Small - No merch** | Compact bar without merchant branding — use on transactional or utility screens |
| **Minimum** | Bare minimum bar (no title bar) — use on payment flows, full-screen modals, and deep utility screens |

### Variant Availability

| Size | Title bar | show Stroke |
|---|---|---|
| Large - Merch | True, False | True, False |
| Med - Merch | True, False | True, False |
| Small - Merch | True only | True, False |
| Small - No merch | True only | True only |
| Minimum | False only | True, False |

> `Minimum` only exists with `Title bar=False`. `Small - Merch` and `Small - No merch` only exist with `Title bar=True`. `Small - No merch` has no `show Stroke=False` variant — the stroke is always visible on that size.

---

## Rules — Top Bar

- Always match the `Size` to the screen's depth in the navigation hierarchy — use `Large - Merch` on the home screen and step down for deeper screens.
- Use `show Stroke=True` when the screen content begins immediately below the bar. Set `show Stroke=False` when content scrolls underneath and the bar is floating or transparent.
- Never place a `Title bar=True` on the `Minimum` size — that combination does not exist.
- Use `Minimum` only for focused flows (e.g. checkout, payments, deep settings) where merchant branding would distract.

---
---

# 2. Toast & Toast Bar — POP Design System

Toasts are transient feedback banners displayed to the user after an action. The Toast is a compact inline message; the Toast Bar (Toastbar - Organism) is a full-width sticky notification bar.

---

## 2a. Toast

**Component name:** `Toast`
**Figma page:** `↪️ ❖ Toast ✅`
**Node ID:** `6022:29891`
**Use when:** Showing a brief, non-blocking feedback message (success, error, warning, neutral) after a user action. Disappears automatically or on user dismissal.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Orange | Orange, White, Green, Red | Semantic colour of the toast |
| `Cross` | Boolean | False | True, False | Shows a dismiss (X) button |
| `Button` | Boolean | False | True, False | Shows a CTA button inside the toast |
| `Body` | Boolean | False | True, False | Shows body/description text below the title |
| `Title` | Boolean | True | True, False | Shows the main title text |
| `Image` | Boolean | True | True, False | Shows an image/icon on the left |
| `R-overflow - Body` | Boolean | False | True, False | Right overflow gradient for long body text |
| `Title text` | Text | "This is a title and it can span 2 lines" | Any string | Toast title copy |
| `Body text` | Text | "This is body and it spans 1 line" | Any string | Toast body copy |

### State

| State | Semantic use |
|---|---|
| **Orange** | Brand — use for notifications related to POP brand moments, rewards, or brand-specific communications |
| **White** | Neutral / system message |
| **Green** | Success / confirmation |
| **Red** | Error / failure |

---

## 2b. Toast Bar (Toastbar - Organism)

**Component name:** `Toastbar - Organism`
**Figma page:** `↪️ ❖ Toast ✅`
**Node ID:** `6022:30248`
**Use when:** Displaying a sticky, full-width notification bar that persists until dismissed or auto-expires. Used for promotions, system alerts, and time-sensitive nudges anchored to the top or bottom of a screen.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Colour` | Variant | Orange | Orange, Red, Green, White | Semantic colour of the bar |
| `Icon` | Variant | True | True, False | Shows a leading icon |
| `CTA` | Variant | None | None, Cross, Button | Right-side call-to-action type |
| `Auto dismiss 3s` | Boolean | True | True, False | Bar auto-dismisses after 3 seconds |
| `Swipe up dismiss` | Boolean | True | True, False | Bar can be swiped up to dismiss |

### CTA Options

| CTA | Use case |
|---|---|
| **None** | Informational-only bar — no user action required |
| **Cross** | User can manually dismiss the bar |
| **Button** | Bar contains an actionable CTA button |

---

## Rules — Toast & Toast Bar

- ⛔ **Never place `Toast` directly in screens.** Always use `Toastbar - Organism` wherever a toast notification is needed — it handles positioning, dismissal behaviour, and animation correctly.
- Use `Toastbar - Organism` for all toast notifications — brief post-action feedback (payment confirmed, error, copy) as well as persistent screen-level notifications.
- Always set `Title text` — never leave the placeholder copy in production.
- `Auto dismiss 3s=True` is the default behaviour — only disable it when the notification is critical and must remain visible until user action.
- `Swipe up dismiss=True` always stays enabled unless the toast action is mandatory.
- Match the `State` / `Colour` to the semantic context — use `Orange` only for brand-related notifications, `Green` for success, `Red` for errors, and `White` for neutral system messages. Never use `Green` for errors or `Red` for success.

---
---

# 3. Offers — POP Design System

The Offers system is a suite of components for displaying reward communications, POPcoin values, offer toggles, banners, and P3 (Pay-in-3) offer items across the product.

---

## Component Overview

| Component | Use case |
|---|---|
| `Banner` | Full-width contextual offer/promo banner |
| `POPcoin badge- new` | Inline POPcoin badge (new version) |
| `POPcoin - Unit New` | POPcoin value + coin unit display (new version) |
| `Amount with POPcoin New` | Amount paired with POPcoin reward |
| `Amount unit - 1` | Price amount display with savings, slash price, MRP |
| `Issuance callout` | Pill callout showing cashback/POPcoin issuance |
| `Product listing - Tag` | Tag chip for product listing cards |
| `Offer highlight` | Highlighted offer callout in multiple sizes and colours |
| `Offer toggle new` | New-style toggle for activating/deactivating an offer |
| `Offer toggle` | Legacy offer toggle (old version — `Offer toggle`) |
| `Offer strip` | Full-width offer strip bar |
| `.footer action P3 offer item` | Footer CTA for P3 offer items — local/internal component |
| `P3 offer item new` | New P3 (Pay-in-3) offer line item |

---

## 3a. Banner

**Component name:** `Banner`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `7675:20520`
**Use when:** Displaying a contextual promotional or offer banner on a screen — supports primary, secondary, brand, offer, and image-based layouts.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Primary | Primary, Secondary, Brand primary, Offer, Image - Transparent, Image | Visual style of the banner |
| `Icon align` | Variant | Right | Right, Left | Side where the image/icon is aligned |
| `CTA group` | Boolean | True | True, False | Shows the CTA button group |
| `R-overflow - Body` | Boolean | False | True, False | Right overflow gradient on body text |
| `Body` | Boolean | True | True, False | Shows the body/description text |
| `Image` | Boolean | True | True, False | Shows the image/illustration |
| `Overline` | Boolean | False | True, False | Shows an overline label above the title |
| `Title text` | Text | "This is a title and it can span 2 lines" | Any string | Banner title copy |
| `Body text` | Text | "This is body and it spans 1 line" | Any string | Banner body copy |
| `Title` | Boolean | True | True, False | Shows the title text |
| `CTA 2` | Boolean | True | True, False | Shows a second CTA button |
| `CTA 1` | Boolean | True | True, False | Shows the primary CTA button |
| `CTA - Overline` | Boolean | False | True, False | Shows an overline above the CTA group |

### Type

| Type | Use case |
|---|---|
| **Primary** | Default brand-coloured banner |
| **Secondary** | Subdued secondary banner |
| **Brand primary** | Strong brand-identity banner |
| **Offer** | Offer-specific colour treatment |
| **Image - Transparent** | Image banner with transparent background |
| **Image** | Full image banner |

---

## 3b. Offer Highlight

**Component name:** `Offer highlight`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `6877:3501`
**Use when:** Showing a highlighted offer callout — e.g. "5% cashback", "₹50 POPcoins" — in a card, banner, or listing surface.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Med, Small | Size of the callout |
| `Colour` | Variant | Blue | Blue, Translucent - Dark, Translucent - Light | Background colour treatment |
| `Align` | Variant | Left | Left, Center, Right | Text alignment |
| `Icon align` | Variant | Right | Left, Right | Side where the icon appears |
| `Type` | Variant | Offer | Offer, POPcoin | Offer type — standard offer or POPcoin reward |

### Nested Component — Amount with POPcoin New

`Amount with POPcoin New` is embedded inside `Offer highlight` and is configurable from the component context:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | X-small | Large, Med, Small, X-small, X Large | Size of the amount+POPcoin display |
| `Type` | Variant | Primary | Primary, Secondary, Obsoloete | Visual style — use Primary or Secondary only |
| `Pop coin` | Boolean | True | True, False | Shows the POPcoin icon and value |
| `Amount` | Boolean | True | True, False | Shows the amount value |
| `Separator` | Boolean | True | True, False | Shows the separator between amount and POPcoin |
| `R-icon` | Boolean | False | True, False | Right-side icon |
| `Month` | Boolean | False | True, False | Shows the month/period label |

> ⛔ Do not use `Type=Obsoloete` — it is a deprecated style retained for backward compatibility only.

---

## 3c. POPcoin - Unit New

**Component name:** `POPcoin - Unit New`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `18038:10196`
**Use when:** Displaying a POPcoin value alongside the POPcoin icon in new-design contexts. Replaces the legacy `POPcoin - Unit`.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Small, X-small, Medium | Display size |
| `color` | Variant | White | White, Black | Colour of the coin unit text/icon |
| `Pop-coin` | Variant | Small | Small, Large | Size of the POPcoin icon |

---

## 3d. POPcoin badge- new

**Component name:** `POPcoin badge- new`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `18038:11383`
**Use when:** Showing an inline POPcoin badge (pill/tag) in new-design contexts.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Fill type` | Variant | Secondary | Secondary, Primary, Transluscent, Transparent, Gradient | Visual fill style of the badge |

---

## 3e. Amount with POPcoin New

**Component name:** `Amount with POPcoin New`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `20781:193584`
**Use when:** Displaying a price amount paired with a POPcoin reward value. The new version of the combined amount + POPcoin display.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | X Large | Large, Med, Small, X-small, X Large | Display size |
| `Type` | Variant | Primary | Primary, Secondary, Obsoloete | Visual style |
| `Pop coin` | Boolean | True | True, False | Shows the POPcoin element |
| `Amount` | Boolean | True | True, False | Shows the amount value |
| `Separator` | Boolean | True | True, False | Shows the separator between amount and POPcoin |
| `R-icon` | Boolean | False | True, False | Right-side icon |
| `Month` | Boolean | True | True, False | Shows the month/period label |

---

## 3f. Amount unit - 1

**Component name:** `Amount unit - 1`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `6973:18831`
**Use when:** Displaying a price with optional MRP, slash price, and savings label. Used in product and offer contexts.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | X Large | X Large, Medium, Small, Large | Display size |
| `Color` | Variant | Secondary | Tertiary, Secondary, Primary | Text colour treatment |
| `Slash Price Before` | Variant | True | True, False | Position of the slash/MRP price (before or after) |
| `Savings` | Boolean | True | True, False | Shows the savings label (e.g. "Save ₹50") |
| `Slash price` | Boolean | True | True, False | Shows the crossed-out original price |
| `Separator` | Boolean | False | True, False | Separator between elements |
| `MRP` | Boolean | True | True, False | Shows the MRP label |

---

## 3g. Issuance Callout

**Component name:** `Issuance callout`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `23368:112877`
**Use when:** Showing a pill-style callout that communicates POPcoin or cashback issuance at checkout or offer screens.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Variant` | Variant | Pill | Pill | Currently only the Pill shape is available |
| `Cashback` | Boolean | True | True, False | Shows the cashback value |
| `POPcoin` | Boolean | True | True, False | Shows the POPcoin value |

---

## 3h. Product listing - Tag

**Component name:** `Product listing - Tag`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `13561:13179`
**Use when:** Attaching a tag chip to a product listing card — e.g. "New", "Sale", "Top Pick".

### Properties

This component has no configurable properties (no variants or boolean props). It is a fixed-layout tag chip whose content is set directly on the text layers inside the component.

---

## 3i. Offer Toggle (Old)

**Component name:** `Offer toggle`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `8095:32712`
**Use when:** Allowing the user to toggle an offer on or off. This is the legacy version — use `Offer toggle new` for new screens.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Offer | Pop coin, Offer, Cash, Pay in 3 | Type of offer the toggle controls |
| `Is disabled` | Variant | False | False, True | Disabled state |
| `Size` | Variant | Large | Large, Small | Toggle size |
| `Info icon` | Boolean | True | True, False | Shows an info icon for offer detail |

---

## 3j. Offer Toggle New

**Component name:** `Offer toggle new`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `21154:215041`
**Use when:** Allowing the user to toggle an offer on or off. Use this new version for all current screen designs.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Is disabled` | Variant | false | true, false | Disabled state |
| `Info icon` | Boolean | False | True, False | Shows an info icon |
| `Heading` | Text | "Label" | Any string | Main heading label |
| `R text - Body` | Text | "Label" | Any string | Right-side body text |
| `Popcoin` | Boolean | True | True, False | Shows the POPcoin element |
| `L text - Body` | Text | "Label" | Any string | Left-side body text |

---

## 3k. Offer Strip

**Component name:** `Offer strip`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `14052:94937`
**Use when:** Displaying a full-width strip banner for an active offer — typically at the top of a product or checkout screen.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Offer | Pop coin, Offer, Cash, Pay in 3 | Type of offer displayed in the strip |
| `Is disabled` | Variant | False | False, True | Disabled state |
| `Size` | Variant | Large | Large, Small | Strip height/size |
| `Info icon` | Boolean | True | True, False | Shows an info icon |

---

## 3l. .footer action P3 offer item

> ⚠️ **Local component** — `.footer action P3 offer item` is an internal footer CTA component scoped to `P3 offer item new` only. Do not place it standalone or outside its parent offer surface.

**Component name:** `.footer action P3 offer item`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `23486:73529`
**Use when:** Providing the footer CTA row at the bottom of a P3 offer item — supports a primary button or a link button.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Footer type` | Variant | Button | Button, Link button | Type of footer CTA |

---

## 3m. P3 Offer Item New

**Component name:** `P3 offer item new`
**Figma page:** `↪️ ❖ Offers ✅`
**Node ID:** `23486:72689`
**Use when:** Displaying a Pay-in-3 offer line item in a checkout or offer summary surface.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Variant` | Variant | Pain 3 | Popcoin, Pain 3 | Type of P3 offer displayed |
| `Offer label` | Text | "Label" | Any string | Offer label text |
| `Label` | Text | "Label" | Any string | Secondary label text |

### Nested Component — .footer action P3 offer item

The `.footer action P3 offer item` local component is embedded inside `P3 offer item new` and is configurable from the parent context:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Footer type` | Variant | Button | Button, Link button | Type of footer CTA — Button for primary action, Link button for a lower-emphasis text action |

---

## Rules — Offers

- Always use `Offer toggle new` for new screens — `Offer toggle` (old) is retained for legacy screens only.
- `Product listing - Tag` has no configurable props — edit text layers directly inside the component.
- Always set `Title text` and `Body text` on `Banner` — never leave the placeholder copy in production.
- `Issuance callout` currently only supports the `Pill` variant — do not attempt to apply other shapes.
- `.footer action P3 offer item` is a **local component** — always use it inside its parent P3 offer item context.
- Match `Color` on `Amount unit - 1` to the surface it sits on — use `Primary` on light backgrounds, `Secondary` or `Tertiary` on offer/coloured surfaces.

---
---

# 4. Bottom Sheet — POP Design System

The Bottom Sheet is a modal panel that slides up from the bottom of the screen. Two variants exist: the standard `Bottom sheet` and the `Bottom sheet - Segmented` for multi-category tabbed content.

---

## Slots vs Slot Containers

Understanding the difference between a **Slot** and a **Slot Container** is critical when placing content inside a Bottom Sheet.

| Concept | Behaviour | Height |
|---|---|---|
| **Slot** | A dynamic area inside a component where content is placed. The slot stretches to match the height of the component placed inside it, which in turn changes the height of the parent component. | Dynamic — grows or shrinks with the content placed inside |
| **Slot Container** | A fixed-size wrapper that holds a slot. You always place your component **inside the slot** within the container — never directly on the container frame itself. The container height stays fixed regardless of what is inside. | Fixed — does not change based on content |

### In Bottom Sheet

- `Slot 1` and `Slot 2` in `Bottom sheet` are **slots** — their height adapts to the component placed inside them, which changes the total height of the sheet.
- `Sheet content` and `Slot` in `Bottom sheet - Segmented` are also **slots** — each tab's content area height changes based on what is placed inside.
- ⚠️ Always place your content component **inside the slot**, not directly on the slot container frame. Placing on the container rather than inside the slot breaks the height behaviour.
- ⛔ **There is currently no inside scroll in Bottom Sheet.** Since slot container height is fixed, content that exceeds the container will clip — there is no scrollable region within the sheet. Design content to fit within the available height.

---

## 4a. Bottom Sheet

**Component name:** `Bottom sheet`
**Figma page:** `↪️ ❖ Bottom sheet ✅`
**Node ID:** `7409:4391`
**Use when:** Presenting a focused set of actions, details, or confirmations in a modal overlay anchored to the bottom of the screen.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Default | Default, Transparent | Background fill type |
| `Image - Outside` | Boolean | True | True, False | Image outside/above the sheet |
| `image - Inside` | Boolean | True | True, False | Image inside the sheet |
| `Slot 1` | Boolean | True | True, False | First content slot |
| `Slot 2` | Boolean | True | True, False | Second content slot |
| `Image - Center` | Boolean | True | True, False | Centre-aligned image |
| `↪️ Buttons` | Boolean | True | True, False | Shows the button group |
| `Tap outside dismiss` | Boolean | True | True, False | Dismisses sheet when tapping outside |
| `Title + Body` | Boolean | True | True, False | Shows the title and body text block |
| `Divider - Slots` | Boolean | False | True, False | Divider line between content slots |
| `Spacer - Slots` | Boolean | False | True, False | Spacer between content slots |
| `Body` | Boolean | True | True, False | Shows body text within the sheet |

### Type

| Type | Use case |
|---|---|
| **Default** | Solid-background sheet — standard confirmations, forms, detail panels |
| **Transparent** | Transparent background — overlays on media or image-heavy screens |

---

## 4b. Bottom Sheet - Segmented

**Component name:** `Bottom sheet - Segmented`
**Figma page:** `↪️ ❖ Bottom sheet ✅`
**Node ID:** `8069:130396`
**Use when:** Displaying multi-category tabbed content in a bottom sheet — e.g. payment method categories, filter tabs, account sections.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Button` | Variant | False | False, True | Shows a CTA button in the sheet |
| `Cat 1` | Boolean | True | True, False | Shows Category 1 tab |
| `Cat 2` | Boolean | True | True, False | Shows Category 2 tab |
| `DON'T USE - Buttons` | Boolean | True | True, False | Legacy button slot — do not use |
| `Tap outside dismiss` | Boolean | True | True, False | Dismisses sheet when tapping outside |
| `Cat 3` | Boolean | True | True, False | Shows Category 3 tab |
| `Cat 4` | Boolean | True | True, False | Shows Category 4 tab |
| `CTA - Cat 1` | Boolean | True | True, False | CTA for Category 1 |
| `CTA - Cat 2` | Boolean | True | True, False | CTA for Category 2 |
| `CTA - Cat 3` | Boolean | True | True, False | CTA for Category 3 |
| `CTA - Cat 4` | Boolean | True | True, False | CTA for Category 4 |
| `Footer images` | Boolean | False | True, False | Footer image row |
| `Header - Cat 1` | Boolean | True | True, False | Header for Category 1 |
| `Header - Cat 2` | Boolean | True | True, False | Header for Category 2 |
| `Header - Cat 3` | Boolean | True | True, False | Header for Category 3 |
| `Header - Cat 4` | Boolean | True | True, False | Header for Category 4 |
| `Sheet content` | Slot | — | Component set | Content slot for the active tab |
| `Slot` | Slot | — | Component set | Additional content slot |

> ⚠️ **Do not use** `DON'T USE - Buttons` — this is a legacy slot retained for backward compatibility only. Use the `Button` variant and `CTA - Cat` props instead.

---

## Rules — Bottom Sheet

- Always set `Tap outside dismiss=True` unless the action inside the sheet is mandatory and cannot be abandoned.
- Use `Bottom sheet - Segmented` only when content has 2–4 distinct categories that benefit from tab navigation. For single-category content, always use the standard `Bottom sheet`.
- `Type=Transparent` is for special visual contexts only — default to `Type=Default` for all standard flows.
- Never populate `DON'T USE - Buttons` on the Segmented variant — it is a deprecated prop.
- ⛔ **No inside scroll** — Bottom Sheet does not support scrollable content. Slot container height is fixed; content that exceeds it will clip. Avoid placing long lists or dynamic-length stacks that could overflow the slot container.
- Always place content **inside the slot**, not directly on the slot container frame — placing on the container breaks the height-hugging behaviour and will produce incorrect sheet heights.

---
---

# 5. Popup — POP Design System

The Popup is a modal dialog that overlays the entire screen with a scrim. Used for high-priority confirmations, permission prompts, and offer highlights that require explicit user response.

---

## 5a. Popup

**Component name:** `Popup`
**Figma page:** `↪️ ❖ Popup ✅`
**Node ID:** `9732:108759`
**Use when:** Presenting a critical prompt that requires user decision — e.g. autopay approval, permission request, offer acceptance. Blocks interaction with the screen behind.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Default | Default, Success | Visual state of the popup |
| `Expiry highlight` | Variant | True | True, False | Shows an expiry/urgency highlight |
| `Secondary CTA` | Variant | True | True, False | Shows a secondary CTA button |
| `Title` | Boolean | True | True, False | Shows the popup title |
| `Offer highlight` | Boolean | False | True, False | Shows an offer highlight callout |
| `Tertiary CTA` | Boolean | True | True, False | Shows a tertiary CTA (e.g. "Not now") |
| `Tap outside to dismiss` | Boolean | False | True, False | Dismisses popup on tap outside |
| `Title text` | Text | "Autopay request" | Any string | Popup title copy |
| `Chevron` | Boolean | True | True, False | Shows a chevron/arrow indicator |

### State

| State | Use case |
|---|---|
| **Default** | Standard popup — confirmations, permissions, offers |
| **Success** | Post-action success state — e.g. autopay set up, payment confirmed |

---

## Rules — Popup

- `Tap outside to dismiss=False` is the default — only enable it for non-critical informational popups where abandonment is acceptable.
- Always provide at least one CTA — never show a Popup without an actionable button.
- Use `State=Success` only after a successful transaction or setup — never as the initial state.
- `Expiry highlight=True` is for time-sensitive offers only — turn it off for non-expiring content.
- Keep `Title text` concise — popup titles should be one line wherever possible.

---
---

# 6. Carousel — POP Design System

The Carousel system covers standard scrollable card carousels, edge-to-edge full-bleed carousels, and the layered Fancy Carousel with its unit building block.

---

## Component Overview

| Component | Use case |
|---|---|
| `Carousel` | Standard in-page scrollable carousel |
| `Carousel - Edge to edge` | Full-bleed edge-to-edge carousel |
| `Fancy carousel - Unit` | ⚠️ Local component — individual layer unit scoped to Fancy Carousel |
| `Fancy carousel` | Layered parallax/animated carousel |

---

## 6a. Carousel

**Component name:** `Carousel`
**Figma page:** `↪️ ❖ Carousel ✅`
**Node ID:** `6130:2609`
**Use when:** Displaying a horizontal scroll of cards (banners, product cards, offers) with an optional header and pagination indicators.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3, Plenty | Number of visible cards |
| `Title Size` | Variant | Small | Small, Medium | Size of the carousel header title |
| `Title` | Boolean | True | True, False | Shows the header title |
| `Pagination` | Boolean | True | True, False | Shows pagination dots |
| `CTA - Header` | Boolean | False | True, False | CTA link in the header |
| `R-overflow - Header` | Boolean | False | True, False | Right overflow gradient on header |
| `L-icon - Title` | Boolean | False | True, False | Left icon on the title |
| `R-icon - Title` | Boolean | False | True, False | Right icon on the title |
| `R-overflow - Banners` | Boolean | False | True, False | Right overflow gradient on banner cards |
| `R-slot - Header` | Boolean | False | True, False | Right slot in the header |
| `L-overflow - Banners` | Boolean | False | True, False | Left overflow gradient on banner cards |
| `Header` | Boolean | True | True, False | Shows the header row |
| `Body` | Boolean | False | True, False | Shows body text in the header |
| `Title text` | Text | "Title" | Any string | Header title copy |
| `Body text` | Text | "Body" | Any string | Header body copy |
| `L-icon - Body` | Boolean | False | True, False | Left icon on body text |
| `R-icon - Body` | Boolean | False | True, False | Right icon on body text |

### Count Options

| Count | Description |
|---|---|
| **1** | Single card visible — full-width card |
| **2** | Two cards visible — side-by-side |
| **3** | Three cards visible — compact grid |
| **Plenty** | Many cards — scrollable overflow list |

---

## 6b. Carousel - Edge to Edge

**Component name:** `Carousel - Edge to edge`
**Figma page:** `↪️ ❖ Carousel ✅`
**Node ID:** `14311:67507`
**Use when:** Displaying a full-bleed, edge-to-edge carousel where cards extend to the screen edges without padding. Used for hero banners and immersive offer surfaces.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3, Plenty | Number of visible cards |
| `Title Size` | Variant | Small | Small, Medium | Header title size |
| `Title` | Boolean | True | True, False | Shows the header title |
| `Pagination` | Boolean | True | True, False | Shows pagination dots |
| `CTA - Header` | Boolean | False | True, False | CTA link in header |
| `R-overflow - Header` | Boolean | False | True, False | Right overflow on header |
| `L-icon - Title` | Boolean | False | True, False | Left icon on title |
| `R-icon - Title` | Boolean | False | True, False | Right icon on title |
| `R-overflow - Banners` | Boolean | True | True, False | Right overflow gradient on banners (on by default for edge bleed) |
| `R-slot - Header` | Boolean | False | True, False | Right slot in header |
| `L-overflow - Banners` | Boolean | True | True, False | Left overflow gradient on banners (on by default for edge bleed) |
| `Header` | Boolean | True | True, False | Shows the header row |
| `Body` | Boolean | False | True, False | Shows body text in header |
| `Title text` | Text | "Title" | Any string | Header title copy |
| `Body text` | Text | "Body" | Any string | Header body copy |
| `L-icon - Body` | Boolean | False | True, False | Left icon on body |
| `R-icon - Body` | Boolean | False | True, False | Right icon on body |

> The key difference from standard `Carousel`: `R-overflow - Banners` and `L-overflow - Banners` default to `True` to create the edge-to-edge bleed effect.

---

## 6c. Fancy Carousel - Unit

> ⚠️ **Local component** — `Fancy carousel - Unit` is scoped to `Fancy carousel` only. Do not place it standalone or outside the fancy carousel context.

**Component name:** `Fancy carousel - Unit`
**Figma page:** `↪️ ❖ Carousel ✅`
**Node ID:** `14306:52609`
**Use when:** Composing individual layer units (Top, Foreground, Background, Bottom) that build up a Fancy Carousel. Each unit represents one layer in the parallax stack.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Top | Top, FG, Bottom, BG | Layer type — Top, Foreground, Bottom, Background |
| `Size` | Variant | Med | Small, Med | Unit size |
| `Product` | Variant | Shop | Shop, UPI, Card, RCBP, Shop • Red | Product context for the carousel |

### Layer Types

| Type | Role |
|---|---|
| **Top** | Topmost content layer — titles, CTAs |
| **FG** | Foreground layer — product imagery, card art |
| **Bottom** | Bottom content layer — supporting text |
| **BG** | Background layer — fills, gradients, images |

### Product Contexts

| Product | Use case |
|---|---|
| **Shop** | Shop/e-commerce carousel (default) |
| **UPI** | UPI payments carousel |
| **Card** | Credit/debit card carousel |
| **RCBP** | RCBP (rewards card) carousel |
| **Shop • Red** | Shop carousel with red colour treatment |

---

## 6d. Fancy Carousel

**Component name:** `Fancy carousel`
**Figma page:** `↪️ ❖ Carousel ✅`
**Node ID:** `14306:52598`
**Use when:** Building an animated, layered carousel with parallax or foreground/background layer separation. Used for premium product showcases (e.g. credit card hero carousels).

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Med | Med | Currently only medium size available |
| `Top slot` | Boolean | True | True, False | Shows the top content slot |
| `Bottom slot` | Boolean | True | True, False | Shows the bottom content slot |

---

## Rules — Carousel

- Always set `Title text` when `Header=True` — never leave "Title" as production copy.
- Use `Carousel - Edge to edge` only when the screen layout has no horizontal padding — it requires the component to stretch to the device edges.
- `Fancy carousel` and `Fancy carousel - Unit` are for premium product hero surfaces only — do not use for standard offer carousels.
- `Count=Plenty` creates a scrollable overflow — ensure the content list is dynamic and not hardcoded.
- Always enable `Pagination=True` when the carousel has more than 1 item and auto-advances.

---
---

# 7. Stacks — POP Design System

The Stacks section houses composite content card stacking patterns. Currently only one component is published.

---

## 7a. Content Card Stack

**Component name:** `Content card stack`
**Figma page:** `↪️ ❖ Stacks 🚧`
**Node ID:** `21773:33261`
**Use when:** Displaying a stacked set of content cards — used for layered card presentations, e.g. reward card stacks, account card stacks.

### Properties

This component has no configurable properties. Its internal layout and card count are controlled by the design layer structure inside the component.

> ⚠️ This page is marked `🚧` (work in progress) — additional stack components may be added. Do not use this component in production without confirming with the design system team.

---

## Rules — Stacks

- `Content card stack` has no props — edit internal layers directly if customisation is needed.
- This page is work-in-progress — check with the design system team before introducing this component in production designs.

---
---

# 8. Empty State — POP Design System

The Empty State system provides screen layouts for zero-data and error states. The current published components include a base layout and a new-style frame, plus a deprecated component set for legacy screens.

---

## Component Overview

| Component | Status | Use case |
|---|---|---|
| `.empty state - base` | ⚠️ Local component of `empty state new` | Structural base frame — never use independently |
| `empty state new` | Active (Frame) | New-style empty state — always use this |
| `(Deprecated) Empty state` | Deprecated | Legacy component set — do not use in new designs |

---

## 8a. .empty state - base

> ⚠️ **Local component** — `.empty state - base` is scoped to `empty state new` only. It is the structural foundation that `empty state new` is built upon. Do not place it directly in screens or use it independently — always use `empty state new` instead.

**Component name:** `.empty state - base`
**Figma page:** `↪️ ❖ Empty state ✅`
**Node ID:** `15539:17626`
**Use when:** Never used directly — it exists as the internal base layer of `empty state new`.

### Components within .empty state - base

`.empty state - base` contains two internal components that power the different empty state contexts:

---

#### Empty State full paged

**Component name:** `Empty State full paged`
**Node ID:** `15539:17837`
**Use when:** Full-screen empty states — used when there is no content to show on an entire page (e.g. no search results, no orders, no transactions).

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Default | Default, Compact | Layout type — Default is full vertical layout, Compact is a condensed version |
| `Size` | Variant | Large | Large, Medium | Size of the empty state |
| `show title` | Boolean | True | True, False | Shows the title text |
| `show body` | Boolean | True | True, False | Shows the body/description text |
| `show Illustration` | Boolean | True | True, False | Shows the illustration |
| `R icon` | Boolean | True | True, False | Right icon on the title |
| `CTAs` | Boolean | True | True, False | Shows the CTA button group |
| `CTA 1` | Boolean | True | True, False | Primary CTA button |
| `CTA 2` | Boolean | True | True, False | Secondary CTA button |
| `Illustrations` | Instance Swap | — | See preferred values in Figma | Swappable illustration — choose from the preferred illustration set |
| `Title` | Text | "No result to show" | Any string | Title copy — always replace with real content |
| `Body` | Text | "Empty state body goes here" | Any string | Body copy — always replace with real content |

| Type | Use case |
|---|---|
| **Default** | Full vertical layout with illustration, title, body and CTAs |
| **Compact** | Condensed layout for smaller spaces where a full empty state is too large |

---

#### Empty state new/ bottom sheet

**Component name:** `Empty state new/ bottom sheet`
**Node ID:** `16275:64415`
**Use when:** Empty states inside a bottom sheet — used when a sheet's content area has no data to show.

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Medium | Size of the empty state |
| `show title` | Boolean | True | True, False | Shows the title text |
| `show body` | Boolean | True | True, False | Shows the body/description text |
| `show Illustration` | Boolean | True | True, False | Shows the illustration |
| `CTAs` | Boolean | True | True, False | Shows the CTA button group |
| `Spacer - slot` | Boolean | True | True, False | Spacer slot below the content |
| `CTA 1` | Boolean | True | True, False | Primary CTA button |
| `CTA2` | Boolean | True | True, False | Secondary CTA button |
| `Illustrations (bs)` | Instance Swap | — | See preferred values in Figma | Swappable illustration for bottom sheet context |
| `Title` | Text | "No result to show" | Any string | Title copy — always replace with real content |
| `Body` | Text | "Empty state body goes here" | Any string | Body copy — always replace with real content |

---

## 8b. empty state new

**Component name:** `empty state new`
**Figma page:** `↪️ ❖ Empty state ✅`
**Node ID:** `16287:76532`
**Use when:** Using the new-style empty state layout for zero-data screens, error screens, and permission-required states.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Variant` | Variant | Full screen | Full screen, Bottom sheet | Switches which internal empty state component is active. Full screen = `Empty State full paged`; Bottom sheet = `Empty state new/ bottom sheet` |

---

### Structure

`empty state new` wraps `.empty state - base` and surfaces one of two nested instances depending on the `Variant` property. Set `Variant` first — the relevant nested component properties then become available to configure.

---

### Nested Component — Empty State full paged

> Active when `Variant = Full screen`. Use when the empty state occupies a full page or a primary content region.

**Node ID:** `15539:17837`

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Default | Default, Compact | Default = full layout with illustration; Compact = reduced vertical footprint |
| `Size` | Variant | Large | Large, Medium | Controls overall scale of the empty state |
| `show title` | Boolean | True | True, False | Toggle the title text |
| `show body` | Boolean | True | True, False | Toggle the body text |
| `show Illustration` | Boolean | True | True, False | Toggle the illustration |
| `R icon` | Boolean | True | True, False | Right icon in the title row |
| `CTAs` | Boolean | True | True, False | Toggle the CTA button group |
| `CTA 1` | Boolean | True | True, False | Show/hide primary CTA button |
| `CTA 2` | Boolean | True | True, False | Show/hide secondary CTA button |
| `Illustrations` | Instance Swap | — | See preferred values in Figma | Swap illustration asset |
| `Title` | Text | "No result to show" | Any string | Title copy — always replace with real content |
| `Body` | Text | "Empty state body goes here" | Any string | Body copy — always replace with real content |

---

### Nested Component — Empty state new/ bottom sheet

> Active when `Variant = Bottom sheet`. Use when the empty state appears inside a Bottom Sheet.

**Node ID:** `16275:64415`

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Medium | Controls overall scale of the empty state |
| `show title` | Boolean | True | True, False | Toggle the title text |
| `show body` | Boolean | True | True, False | Toggle the body text |
| `show Illustration` | Boolean | True | True, False | Toggle the illustration |
| `CTAs` | Boolean | True | True, False | Toggle the CTA button group |
| `Spacer - slot` | Boolean | True | True, False | Adds spacing above the empty state content |
| `CTA 1` | Boolean | True | True, False | Show/hide primary CTA button |
| `CTA2` | Boolean | True | True, False | Show/hide secondary CTA button |
| `Illustrations (bs)` | Instance Swap | — | See preferred values in Figma | Swap illustration asset (bottom-sheet variant) |
| `Title` | Text | "No result to show" | Any string | Title copy — always replace with real content |
| `Body` | Text | "Empty state body goes here" | Any string | Body copy — always replace with real content |

---

## 8c. (Deprecated) Empty State

> ⚠️ **Deprecated.** Do not use in new designs. This component set is retained for backward compatibility with existing screens only. Migrate to `empty state new` when updating legacy screens.

**Component name:** `(Deprecated) Empty state`
**Figma page:** `↪️ ❖ Empty state ✅`
**Node ID:** `7466:22640`

### Properties (for reference only)

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Screen • Large | Screen • Large, Screen • Small, No results, No results + Body, No RuPay | Empty state type |
| `Size` | Variant | Large | Large, Med | Display size |
| `image - Inside` | Boolean | True | True, False | Shows an illustration/image |
| `CTAs` | Boolean | True | True, False | Shows CTA buttons |
| `Title + Body` | Boolean | True | True, False | Shows title and body text |
| `CTA 1` | Boolean | True | True, False | Primary CTA button |
| `CTA 2` | Boolean | True | True, False | Secondary CTA button |
| `Title text` | Text | "Empty state title here" | Any string | Title copy |
| `Body text` | Text | "Empty state body desc goes here" | Any string | Body copy |

---

## Rules — Empty State

- ⛔ **Never use `.empty state - base` directly** — it is a local component of `empty state new`. Always use `empty state new` wherever an empty state is needed.
- Use `empty state new` for all new screen designs. Do not use `(Deprecated) Empty state` in any new work.
- Both frames have no configurable component properties — populate content by editing layers within the frame.
- Always write meaningful empty state copy — never leave placeholder text in production.
- Every empty state must have at least one recovery action (CTA) unless the state is truly unrecoverable.

---
---

# 9. Bento Grid — POP Design System

The Bento Grid system provides tile-based grid layouts for home screen or feature highlight surfaces. Multiple compositions are available depending on grid count and product context.

---

## Component Overview

| Component | Type | Use case |
|---|---|---|
| `. new Bento grid` (Component) | Component | Single bento cell — internal building block |
| `. new Bento grid` (Component Set) | COMPONENT_SET | Bento cell with size and image position variants |
| `.RCBP TSS bento` | Component | RCBP TSS-specific bento grid composition |
| `Bento grid- 2` | Component | 2-cell bento grid layout |
| `Bento Grid - 3` | Component | 3-cell bento grid layout |
| `Bento grid -2 TSS` | Component | 2-cell TSS bento grid layout |
| `Amount/ Popcoin` | COMPONENT_SET | Amount or POPcoin display unit inside the bento — local subcomponent |

---

## 9a. . new Bento grid (Cell)

> ⚠️ **Internal component.** The standalone `. new Bento grid` COMPONENT is a single bento cell building block. It is composed into higher-level grid layouts (`Bento grid- 2`, `Bento Grid - 3`). Do not place it standalone in product screens.

**Component name:** `. new Bento grid` (standalone component)
**Figma page:** `↪️ ❖ Bento grid 🚧`
**Node ID:** `19069:74602`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `R-icon` | Boolean | True | True, False | Right-side icon |
| `Body` | Boolean | True | True, False | Shows body text |
| `Offer` | Boolean | True | True, False | Shows offer element |

---

## 9b. . new Bento grid (Component Set)

**Component name:** `. new Bento grid` (component set)
**Figma page:** `↪️ ❖ Bento grid 🚧`
**Node ID:** `19069:74390`
**Use when:** Placing a single bento grid cell with configurable size and image position. Use this to compose custom bento grid layouts.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `ImageRight` | Variant | True | False, True | Whether the image is on the right side |
| `Size` | Variant | Medium | Small, X-Small, Medium | Cell size |
| `R-icon` | Boolean | True | True, False | Right-side icon |
| `Offer` | Boolean | True | True, False | Shows the offer element |
| `body` | Boolean | True | True, False | Shows body text |

---

## 9c. .RCBP TSS bento

> ⚠️ **Internal component.** `.RCBP TSS bento` is a pre-composed bento grid layout specifically for the RCBP TSS (Rewards Card Benefits Page / Total Savings Summary) surface. Do not use outside that context.

**Component name:** `.RCBP TSS bento`
**Figma page:** `↪️ ❖ Bento grid 🚧`
**Node ID:** `23425:84615`

### Properties

No configurable component properties on the parent. Layout is fixed for the RCBP TSS context. Configure content via the nested `Amount/ Popcoin` instance below.

---

### Nested Component — Amount/ Popcoin

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Amount | Amount, POPcoin - Unit | `Amount` = displays a price/rupee amount; `POPcoin - Unit` = displays a POPcoin value with icon |

> ⛔ The internal `POPcoin - Unit` properties (`Size`, `Colour`, `POPcoin`) are not configurable from within `.RCBP TSS bento` — only `Type` can be changed.

---

## 9d. Bento grid- 2

**Component name:** `Bento grid- 2`
**Figma page:** `↪️ ❖ Bento grid 🚧`
**Node ID:** `19079:74706`
**Use when:** Displaying a two-cell bento grid on home screen or feature highlight surfaces.

### Properties

No configurable component properties. Internal cells are controlled by swapping/editing the nested `. new Bento grid` cells.

---

## 9e. Bento Grid - 3

**Component name:** `Bento Grid - 3`
**Figma page:** `↪️ ❖ Bento grid 🚧`
**Node ID:** `19079:75250`
**Use when:** Displaying a three-cell bento grid on home screen or feature highlight surfaces.

### Properties

No configurable component properties. Internal cells are controlled by swapping/editing the nested `. new Bento grid` cells.

---

## 9f. Bento grid -2 TSS

**Component name:** `Bento grid -2 TSS`
**Figma page:** `↪️ ❖ Bento grid 🚧`
**Node ID:** `23425:86773`
**Use when:** Displaying a two-cell bento grid in the TSS (Total Savings Summary) context.

### Properties

No configurable component properties.

---

## 9g. Amount/ Popcoin (Bento subcomponent)

> ⚠️ **Local component** — `Amount/ Popcoin` is scoped to the Bento Grid system. It is a display unit used inside bento cells to show either a price amount or a POPcoin value. Do not place it outside bento grid contexts.

**Component name:** `Amount/ Popcoin`
**Figma page:** `↪️ ❖ Bento grid 🚧`
**Node ID:** `23425:84609`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Amount | POPcoin - Unit, Amount | Whether to show a price amount or a POPcoin unit |

---

## Rules — Bento Grid

- This page is marked `🚧` (work in progress) — confirm with the design system team before shipping new bento grid patterns.
- Use `Bento grid- 2` for two-tile layouts and `Bento Grid - 3` for three-tile layouts — do not manually stack individual cells.
- `.RCBP TSS bento` is scoped to the RCBP TSS surface — do not repurpose for other screens.
- `Amount/ Popcoin` is a local subcomponent — always use it within a bento cell, never standalone.

---
---

# 10. RCBP Card — POP Design System

The RCBP Card system provides the complete Rewards Card Benefits Page card pattern, including the card set, individual card items, leading item variants, local card display, and background image assets.

---

## Component Overview

| Component | Type | Use case |
|---|---|---|
| `Leading item` | COMPONENT_SET | Left leading element — card illustration or slot |
| `Local Card` | COMPONENT | Local non-POPCard display card |
| `Card item` | COMPONENT_SET | Individual card item row (1–3 items) |
| `Card set` | COMPONENT | Full card set composition with swappable background |
| `RCBP/POP Coin issuance` | COMPONENT | Full RCBP card for POP Coin issuance |
| `RCBP/POPCard` | COMPONENT | Full RCBP card for POPCard |
| `Background/POP Coin issuance` | COMPONENT | Background image asset — POP Coin issuance |
| `Background/NONpopcard` | COMPONENT | Background image asset — non-POPCard |
| `Background/POPCard` | COMPONENT | Background image asset — POPCard |

---

## 10a. Leading Item

**Component name:** `Leading item`
**Figma page:** `↪️ RCBP Cards`
**Node ID:** `17403:2480`
**Use when:** Providing the left-side leading element in an RCBP card row — either a card illustration or a custom slot.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Leading item` | Variant | Card illustration | Card illustration, Slot | Type of leading element |
| `Illustration` | Instance Swap | `17375:77920` | See preferred values | Swappable card illustration |

### Illustration Preferred Values (Instance Swap)

The `Illustration` slot accepts the following preferred component keys:

| Key | Notes |
|---|---|
| `be002ae5c394d7b6ff6301aa841fc22eb7c16059` | Illustration option 1 |
| `73c25f3014eefc3919947e3164de26f88ab0bdea` | Illustration option 2 |
| `7876ddd4c635805e257bac3daffdcf402b4c15d8` | Illustration option 3 |
| `e3ee0200828745c0226ca27cd7d0311855628db1` | Illustration option 4 |
| `5aa4113114fd0f62668a98468093c120e46a8979` | Illustration option 5 |

---

## 10b. Local Card

**Component name:** `Local Card`
**Figma page:** `↪️ RCBP Cards`
**Node ID:** `17478:61551`
**Use when:** Displaying the local (non-POPCard) card state inside the RCBP card system — used for users who do not have a POPCard.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Heading` | Boolean | True | True, False | Shows the heading text |
| `Subtext` | Boolean | True | True, False | Shows the subtext |
| `Heading-T` | Text | "Non POPCard user" | Any string | Heading copy |
| `Subtext-T` | Text | "Placeholder text vfsdhvfhvhsvhdshdshvdhsv" | Any string | Subtext copy |
| `R-overflow heading` | Boolean | False | True, False | Right overflow gradient on heading |
| `R-overflow Subtext` | Boolean | False | True, False | Right overflow gradient on subtext |

> Always replace `Heading-T` and `Subtext-T` with real copy — the defaults are placeholder text.

---

### Nested Component — Leading item

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Leading item` | Variant | Card illustration | Card illustration, Slot | Switches the left leading element between a card illustration and an empty slot |
| `Illustration` | Instance Swap | — | See preferred values in Figma | Swappable card illustration asset — only applicable when `Leading item = Card illustration` |

> ⛔ `Illustration` is only relevant when `Leading item = Card illustration`. When `Leading item = Slot`, the illustration is hidden and the slot is available for custom content.

---

## 10c. Card Item

**Component name:** `Card item`
**Figma page:** `↪️ RCBP Cards`
**Node ID:** `17471:127305`
**Use when:** Displaying an individual card benefit item row within the RCBP card set. Supports up to 3 item variants.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Value` | Variant | 1 | 1, 2, 3 | Which item variant to display |

---

## 10d. Card Set

**Component name:** `Card set`
**Figma page:** `↪️ RCBP Cards`
**Node ID:** `17471:63838`
**Use when:** Composing a full RCBP card with a swappable background image. The `Card set` assembles `Card item` rows over a background asset.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Background` | Instance Swap | `17375:75696` | See preferred values | Swappable background image component |

### Background Preferred Values (Instance Swap)

| Key | Notes |
|---|---|
| `dfbe90b30a12a217acbc498be1df5809abb705aa` | Background option 1 |
| `f9dc5c39371dc57d84218cbcb0a723585d27f2fa` | Background option 2 |

---

## 10e. RCBP/POP Coin issuance

**Component name:** `RCBP/POP Coin issuance`
**Figma page:** `↪️ RCBP Cards`
**Node ID:** `17478:130082`
**Use when:** Displaying the full RCBP card for POP Coin issuance contexts — includes the `Background/POP Coin issuance` asset.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Background` | Instance Swap | `17556:37867` | — | Background image — defaults to POP Coin issuance background |

[View in Figma](https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=17478-130082)

---

## 10f. RCBP/POPCard

**Component name:** `RCBP/POPCard`
**Figma page:** `↪️ RCBP Cards`
**Node ID:** `17478:129144`
**Use when:** Displaying the full RCBP card for POPCard users.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Background` | Instance Swap | `17574:26042` | See preferred values | Background image — defaults to POPCard background |

### Background Preferred Values (Instance Swap)

| Key | Notes |
|---|---|
| `79e41004c55fc0562287dd7e24cae5a165436c43` | POPCard background option 1 |
| `e54e3c820edc2a38ae9ff99dba19f1f7a0d497e6` | POPCard background option 2 |

[View in Figma](https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=17478-129144)

---

## 10g. Background Image Assets

These are local asset components used as swappable backgrounds inside RCBP card compositions. They have no configurable properties.

> ⚠️ **Local asset components** — these are image fill components scoped to the RCBP Card system. Do not use them outside RCBP card contexts.

| Component | Node ID | Figma Link | Use case |
|---|---|---|---|
| `Background/POP Coin issuance` | `17556:37867` | [View in Figma](https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=17556-37867) | Background for POP Coin issuance card |
| `Background/NONpopcard` | `17556:37866` | [View in Figma](https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=17556-37866) | Background for non-POPCard users |
| `Background/POPCard` | `17574:26042` | [View in Figma](https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=17574-26042) | Background for POPCard users |

---

## Rules — RCBP Card

- Always swap the `Background` instance to the correct variant for the user's card state: `Background/POPCard` for POPCard users, `Background/NONpopcard` for non-POPCard users, `Background/POP Coin issuance` for issuance flows.
- Replace all placeholder text in `Local Card` — `Heading-T` and `Subtext-T` defaults are non-production copy.
- Use `Card set` for composed card layouts — do not manually layer individual `Card item` rows over a background outside of `Card set`.
- Background image assets are local to this system — never reuse them as decorative backgrounds outside the RCBP card context.
- `Leading item=Card illustration` is the default — use `Slot` only when a custom leading element is required.
