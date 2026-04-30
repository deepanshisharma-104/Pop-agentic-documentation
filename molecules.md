# Molecules — POP Design System

Molecules are composed UI components built from atoms. They carry layout, state, and interaction logic. Always use the highest-level component that fits the context — never reconstruct a molecule manually from atoms.

---

## Component Index

| # | Component | Figma Page |
|---|---|---|
| 1 | Status bar | `↪️ ❖ Status bar ✅` |
| 2 | App bar | `↪️ ❖ App bar ✅` |
| 3 | Title bar | `↪️ ❖ Title bar ✅` |
| 4 | Section row item | `↪️ ❖ Section row item ✅` |
| 5 | Nudge | `↪️ ❖ Nudge` |
| 6 | Inline action row | `↪️ ❖ Inline action row ✅` |
| 7 | Section header | `↪️ ❖ Section header ✅` |
| 8 | Chip + Chip Stack | `↪️ ❖ Chip ✅` |
| 9 | Tab (all variants) | `↪️ ❖ Tab ✅` |
| 10 | Input field (all variants) | `↪️ ❖ Input field ✅` |
| 11 | Card + Card Stack | `↪️ ❖ Card 🚧` |
| 12 | ODP Card | `↪️ ❖ Card 🚧` |

---
---

# 1. Status Bar — POP Design System

> **Rationale:** Status bar icons (signal, wifi, battery, time) are rendered by the **native OS**. The design system does not control these. In design files they are represented as-is for documentation purposes only.

> ✅ **Rule:** Status bar icons → use system/OS-rendered values only. When referencing icons as SVG assets, use the table below. Always define a `viewBox` so the icon scales proportionally — never hardcode pixel dimensions.

**Component name:** `Status bar`
**Figma page:** `↪️ ❖ Status bar ✅`
**Node ID:** `32:2490`

---

## Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Theme` | Variant | Transparent | Transparent, Light, Dark | Controls icon and text colour — must match the surface behind the status bar |
| `System` | Variant | iOS | iOS, android | Switches between iOS and Android icon sets and layout conventions |

### Theme

| Value | When to Use |
|---|---|
| **Transparent** | Over images, gradients, or dark hero surfaces — uses `onwhite` icon assets |
| **Light** | Over light/white surfaces — uses `onblack` icon assets |
| **Dark** | Over dark solid surfaces — uses `onwhite` icon assets |

### System

| Value | Platform | Icon Set |
|---|---|---|
| **iOS** | iPhone / iOS | `*-IOS onblack.svg` (Light) or `*-IOS onwhite.svg` (Transparent / Dark) |
| **android** | Android devices | `*-Android onblack.svg` (Light) or `*-Android onwhite.svg` (Transparent / Dark) |

---

## Icon Assets by Platform & Theme

| Platform | Theme | Network / Signal Icon | Wifi Icon | Battery Icon |
|---|---|---|---|---|
| **iOS** | Light | `Icons/Status bar icons > network-IOS onblack.svg` | `Icons/Status bar icons > wifi-IOS onblack.svg` | `Icons/Status bar icons > battery-IOS onblack.svg` |
| **iOS** | Transparent / Dark | `Icons/Status bar icons > network-IOS onwhite.svg` | `Icons/Status bar icons > wifi-IOS onwhite.svg` | `Icons/Status bar icons > battery-IOS onwhite.svg` |
| **Android** | Transparent / Dark | `Icons/Status bar icons > network-Android onblack.svg` | `Icons/Status bar icons > wifi-Android onblack.svg` | `Icons/Status bar icons > Battery-Android onblack.svg` |
| **Android** | Light | `Icons/Status bar icons > network-Android onwhite.svg` | `Icons/Status bar icons > wifi-Android onwhite.svg` | `Icons/Status bar icons > Battery-Android onwhite.svg` |

---

## Rules

- ⛔ **Never place `Status bar` directly on a screen.** Always use the `Top bar` organism (`↪️ ❖ Top bar ✅`) — the Status bar is already embedded inside it.
- The design system does **not** style or override status bar icons — use only the assets listed above.
- Always match the icon set to the surface theme — using the wrong set on a dark or light background will produce invisible or illegible icons.
- Never replace status bar icons with custom artwork.
- Time text in the status bar is always OS-rendered — never mock it with a static text layer in production.
- Use `viewBox` on all SVG assets to ensure correct proportional scaling across device sizes.

---
---

# 2. App Bar — POP Design System

The App bar sits at the very top of the screen content area (below the OS status bar). It provides the primary identity and top-level navigation context for the current app surface.

**Component name:** `App bar`
**Figma page:** `↪️ ❖ App bar ✅`
**Node ID:** `14143:8449`

---

## Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Default | Default, Profile | Controls left-side treatment |
| `R-slot` | Boolean | False | True, False | Reveals a right-side slot for custom components (e.g. search, CTA) |
| `R-icon` | Boolean | False | True, False | Reveals a right-side icon action (e.g. notification bell, settings) |

### Nested Icon Properties — L-icon & R-icon

Both the left icon slot and right icon slot are powered by an **`Icon button`** sub-component. This sub-component exposes a **`Count`** property that controls how many icons appear in that slot. Icon size is always fixed at **24px** and cannot be changed.

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` (L-icon) | Variant | 1 | 1, 2, 3, 4 | Number of icons shown in the left slot — icons stack horizontally |
| `Count` (R-icon) | Variant | 1 | 1, 2, 3, 4 | Number of icons shown in the right slot — icons stack horizontally |

> **Icon size is locked at 24px** across all Count values. Increasing Count adds more icons at the same size — it does not resize them.

---

## Types

| Type | Description | When to use |
|---|---|---|
| **Default** | Standard app bar with logo/wordmark on the left | All standard screens — home, listing, detail pages |
| **Profile** | Left side shows a profile avatar instead of a logo | Screens where the signed-in user's identity is the primary context (e.g. account home, personal feed) |

---

## R-slot vs R-icon

| Property | What it does | Example usage |
|---|---|---|
| `R-slot` | Opens a free slot on the right — place any component inside | Search bar, notification badge with avatar, CTA button |
| `R-icon` | Shows a right-side icon group via `Icon button` — use `Count` to show 1–4 icons | Settings gear, filter icon, notification bell, share icon |

> Use `R-slot` when you need a non-icon component on the right. Use `R-icon` for icon-only actions — set `Count` to add up to 4 icons side by side. Both can be combined.

---

## Rules

- The App bar is always **full-width** and pinned to the top of the content area — never scrolls with content.
- Do not use the App bar inside a bottom sheet or modal — use `Title bar` instead.
- Only one App bar per screen.
- Do not add additional navigation items to the App bar — use `Tab - Stack` or `Section header` for in-page navigation.
- Icon size in both L and R icon slots is always **24px** — never override this.
- Use `Count` on the nested `Icon button` to control how many icons appear; do not stack separate icon instances manually.

---
---

# 3. Title Bar — POP Design System

The Title bar provides a screen or section title, optional body text, and optional flanking icons/slots. Used inside modals, bottom sheets, sub-pages, and anywhere a full App bar is not appropriate.

**Component name:** `Title bar`
**Figma page:** `↪️ ❖ Title bar ✅`
**Node ID:** `90:7176`

> Title bar is a **single component** — it has no variant axis. All configuration is through boolean and text properties.

---

## Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Title text` | Text | "Title" | Any string | The primary screen/section label |
| `Body text` | Text | "Body" | Any string | Supporting subtitle or description |
| `Body` | Boolean | False | True, False | Toggles the body/subtitle text line |
| `L-slot` | Boolean | False | True, False | Left slot — typically a back button or close icon |
| `L-icon - Title` | Boolean | False | True, False | Icon left of the title text |
| `R-icon - Title` | Boolean | False | True, False | Icon right of the title text |
| `R-icon - Body` | Boolean | False | True, False | Icon right of the body text |
| `L-icon - Body` | Boolean | False | True, False | Icon left of the body text |
| `R-slot` | Boolean | False | True, False | Right slot — for a CTA button, avatar, or action component |
| `R-overflow` | Boolean | False | True, False | Gradient overflow fade on the right edge — used when title text is long |

---

## Slot Usage

| Slot | Position | Common contents |
|---|---|---|
| `L-slot` | Far left | Back arrow icon, Close (✕) icon |
| `R-slot` | Far right | Text CTA (e.g. "Done", "Edit"), Avatar, Icon button |

---

## Use Cases

| Screen type | Typical config |
|---|---|
| Bottom sheet | `L-slot=False`, `R-slot=False` (handle drag area takes the place of navigation) |
| Sub-page / pushed screen | `L-slot=True` (back arrow), optional `R-slot` for action |
| Modal | `L-slot=True` (close icon), `Title text` set to modal name |
| Section with sub-label | `Body=True` with `Body text` set to context info |

---

## Rules

- ⛔ **Never place `Title bar` directly on any screen, layout, modal, or bottom sheet.** Always use the `Top bar` organism (`↪️ ❖ Top bar ✅`) — the Title bar is already embedded inside it. The molecule on its own is a building block, not a placement-ready component.
- ⛔ **Do not use `Title bar` as a section header** inside a page or list — use `Section header` instead.
- ⛔ **Do not use `Title bar` to add a heading or title to a bottom sheet or modal** — those surfaces have their own header patterns; place `Top bar` only at the top of a full screen.
- ⛔ **Do not use `Title bar` as an in-page title or content heading** — it is a navigation component, not a typographic element.
- Always set `Title text` — never leave the default "Title" placeholder in production.
- Set `Body=True` only when a subtitle genuinely adds context; do not use it for decorative purposes.
- `R-overflow=True` is for design articulation only — ensure truncation is handled in code via `text-overflow: ellipsis`.
- `L-slot` and `R-slot` accept any component — use `Icon` (atom) or `Button` (molecule) only.

---
---

# 4. Section Row Item — POP Design System

Section row items are individual rows used inside a section or list that contain a left label, an optional right label, and a separator. They represent an **actionable or informational pairing** between two pieces of data — for example a label and its value, a category and its count, or a setting name and its current state.

**Component name:** `Section row item`
**Figma page:** `↪️ ❖ Section row item ✅`
**Node ID:** `21922:87422`

---

## Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Medium | Medium, Small, Large | Controls row height and text size |
| `Style` | Variant | Primary | Primary, Tertiary | Controls visual weight of the row |
| `Separator` | Boolean | True | True, False | Shows/hides the dot (·) separator between the L label and R label |
| `R Label` | Boolean | True | True, False | Toggles the right-side label |
| `L - Label` | Text | "Label" | Any string | Left-side label — the key / category |
| `R - Label` | Text | "Label" | Any string | Right-side label — the value / action |

---

## Size

| Size | Use case |
|---|---|
| Small | Dense data rows, compact settings lists |
| Medium | Default — standard list rows, settings |
| Large | Prominent actionable rows, touch-target-critical contexts |

---

## Style

| Style | Description | When to use |
|---|---|---|
| **Primary** | Full visual weight — stronger text and border treatment | Default rows, primary content areas |
| **Tertiary** | Lighter visual weight — subdued text and border | Secondary info rows, supplementary content |

---

## Use Cases

> Section row items are the building block for any list of **paired label–value or label–action rows**.

| Design context | L - Label | R - Label | Notes |
|---|---|---|---|
| Settings row | "Notifications" | "On" | `Style=Primary`, `Separator=True` |
| Payment detail | "Amount" | "₹1,200" | `Style=Primary`, `R Label=True` |
| Profile info | "PAN" | "ABCDE1234F" | `Size=Medium` |
| Category filter | "Electronics" | "24" | `R Label=True` for count |
| Rows where labels need no separator | Any | Any | `Separator=False` — hide the dot when the two labels read as a single continuous value |

---

## Rules

- Use `Separator=False` when the dot between the two labels would be visually confusing or redundant — for example when the L and R labels together form a single continuous value.
- Use `R Label=False` only when the row is purely label-only and no value or action is needed.
- `L - Label` and `R - Label` are content properties — always populate them with real content; never leave "Label" as the final text.
- Do not use Section row item as a full-page navigation row — use `List` (organism) for navigation patterns.
- Section row items are stacked vertically — never placed side by side.

---
---

# 5. Nudge — POP Design System

A Nudge is a **floating action bar** that sits fixed at the bottom of the screen. Its exact vertical position depends on the content below it — such as a bottom nav bar, a CTA button, or any bottom-anchored element — and is determined by the designer per screen. It communicates status, warnings, errors, success states, or contextual guidance without interrupting the user with a modal or toast.

**Component name:** `Nudge`
**Figma page:** `↪️ ❖ Nudge`
**Node ID:** `22619:189447`

---

## Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Variant` | Variant | Default | Default, Success, Error, Warning | Sets the semantic colour and icon treatment |
| `L - icon` | Boolean | True | True, False | Leading icon on the left (typically a semantic icon matching the variant) |
| `R - icon` | Boolean | True | True, False | Trailing icon on the right (typically a dismiss/close icon) |
| `Link text` | Boolean | True | True, False | Shows an inline text link/CTA at the end of the label |
| `Label text` | Text | "Label" | Any string | The nudge message content |

---

## Variants

| Variant | Semantic use | Example message |
|---|---|---|
| **Default** | Neutral info / general guidance | "Add UPI ID to enable faster payments" |
| **Success** | Positive confirmation | "KYC verified successfully" |
| **Error** | Validation failure / error state | "UPI ID not found. Please check and retry." |
| **Warning** | Caution / non-blocking alert | "Your session will expire in 2 minutes" |

---

## Use Cases

> Nudges are floating and bottom-anchored — their position above other bottom-fixed elements (nav bar, CTA, etc.) is set by the designer per screen.

| Context | Variant | Config |
|---|---|---|
| Form field validation error | Error | `L-icon=True`, `R-icon=False`, `Link text=False` |
| Successful action confirmation below a button | Success | `L-icon=True`, `R-icon=True` (dismiss) |
| Informational tip in a settings section | Default | `L-icon=True`, `Link text=True` (e.g. "Learn more") |
| Expiry warning on a limited-time offer | Warning | `L-icon=True`, `R-icon=False` |

---

## Rules

- `Nudge` is a **bottom-floating bar** — it is never placed inline within content flow. Its vertical offset above other bottom elements (nav bar, sticky CTA, etc.) is defined by the designer for each screen.
- Always set `Label text` to the actual message — never leave the default placeholder.
- Use `L-icon=True` as the default — the semantic icon reinforces the variant meaning. Only turn it off when space is critically constrained.
- `Link text=True` is for a single actionable link only — do not use it as a general CTA button; use a `Button` atom for primary actions.
- `R-icon` is typically a dismiss/close affordance — only use it when the nudge can be manually dismissed by the user.
- A screen should contain **at most one Nudge at a time** per content zone.

---
---

# 6. Inline Action Row — POP Design System

The Inline action row is a horizontal container that holds a row of inline action items — typically icon-label pairs, quick-action chips, or compact navigation items. It is used inside cards, sections, and panels where a compact row of actions is needed.

**Component name:** `Inline action row`
**Figma page:** `↪️ ❖ Inline action row ✅`
**Node ID:** `21200:197929`

---

## Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `L + R padding` | Variant | false | false, true | Adds left and right padding when `true` — use when the row lives inside a padded container |
| `Divider` | Boolean | True | True, False | Shows/hides the top or bottom divider line separating the action row from adjacent content |

---

## Usage

| `L + R padding` | When to use |
|---|---|
| `false` | Row is edge-to-edge (e.g. inside a full-bleed card or a section with no outer padding) |
| `true` | Row has its own horizontal breathing room (e.g. inside a padded card or list section) |

---

## Use Cases

| Context | Config |
|---|---|
| Quick action row at the bottom of a card | `L + R padding=false`, `Divider=True` |
| Action row inside a padded settings section | `L + R padding=true`, `Divider=True` |
| Last action row in a panel (no trailing separator) | `Divider=False` |

---

## Rules

- The Inline action row is a **layout container** — populate its internal slots with the correct action atoms (`Icon`, `Button`, or `Chip`).
- Set `Divider=False` on the last row in a section — a trailing divider is incorrect.
- Use `L + R padding=true` only when the row's parent container does not already provide horizontal padding — do not double-pad.
- Do not use the Inline action row as a primary navigation element — it is for compact supplementary actions only.

---
---

# 7. Section Header — POP Design System

The Section header labels a content group and optionally provides a body/subtitle line, flanking icons, a right slot, and an overflow treatment. It is placed directly above a list or grid of related items.

**Component name:** `Seaction header` *(typo is intentional — matches Figma)*
**Figma page:** `↪️ ❖ Section header ✅`
**Node ID:** `94:1422`

> Section header is a **single component** — it has no variant axis. All configuration is through boolean and text properties.

---

## Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Title text` | Text | "Title" | Any string | The section label / group name |
| `Body text` | Text | "Body" | Any string | Optional supporting subtitle or description |
| `Body` | Boolean | False | True, False | Toggles the body/subtitle text line |
| `L-icon - Title` | Boolean | False | True, False | Icon left of the title |
| `R-icon - Title` | Boolean | False | True, False | Icon right of the title (e.g. info icon, status indicator) |
| `L-icon - Body` | Boolean | False | True, False | Icon left of the body text |
| `R-icon - Body` | Boolean | False | True, False | Icon right of the body text |
| `R-slot` | Boolean | False | True, False | Right-side slot — for a "See all" CTA, count badge, or icon button |
| `R-overflow` | Boolean | False | True, False | Gradient fade on the right edge of the title — for long or scrolling labels |

---

## Use Cases

| Context | Config |
|---|---|
| Simple section label | `Title text` set, all booleans off |
| Section with "See all" CTA | `R-slot=True`, place a text `Button` or `Link` inside the slot |
| Section with a count or badge | `R-slot=True`, place a `Badge` inside the slot |
| Section with description | `Body=True`, `Body text` set |
| Section title with info icon | `R-icon - Title=True` |

---

## Rules

- Always set `Title text` — never leave "Title" as the production label.
- Use `Body=True` only when a subtitle genuinely helps the user — not as decoration.
- `R-slot` accepts any component — prefer `Button` (text variant), `Badge`, or `Icon`.
- `R-overflow=True` is for design articulation only — handle truncation in code.
- Section headers are not interactive themselves — any action belongs in the `R-slot`.
- Do not use Section header to replace `Title bar` inside modals or sheets.

---
---

# 8. Chip — POP Design System

Chips are compact interactive controls used for filtering, selection, and categorical navigation. They come as individual units and as a horizontal stack.

---

## 8a. Chip (Unit)

**Component name:** `Chip`
**Figma page:** `↪️ ❖ Chip ✅`
**Node ID:** `1526:12162`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Toggle | Toggle, Cross, Dropdown, Switch | Controls the chip's interaction type |
| `Is active` | Variant | False | False, True | Selected/active state |
| `Is disabled` | Variant | False | False, True | Disabled state |
| `Size` | Variant | Large | Large, Med | — |
| `L-Icon` | Boolean | False | True, False | Leading icon inside the chip |

### States (Interaction type)

| State | Description | When to use |
|---|---|---|
| **Toggle** | Simple on/off selection — no trailing indicator | Filter chips, category selection |
| **Cross** | Active state shows a dismiss (✕) icon to deselect | Applied filters that can be removed inline |
| **Dropdown** | Trailing chevron — opens a picker or menu | Multi-value filters, sort options |
| **Switch** | Trailing toggle — binary on/off within the chip | Feature toggles presented as chips |

### Size

| Size | Use case |
|---|---|
| Large | Default — filter bars, category rows |
| Med | Dense layouts, nested filter areas |

---

## 8b. Chip Stack

**Component name:** `Chip - Stack`
**Figma page:** `↪️ ❖ Chip ✅`
**Node ID:** `5835:7001`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 2 | 2, 3, 4, 5, Plenty | Number of chips in the row |
| `Size` | Variant | Large | Large, Med | Matches the individual Chip size |
| `L-overflow` | Boolean | False | True, False | Left edge fade — indicates chips are scrollable to the left |
| `R-overflow` | Boolean | False | True, False | Right edge fade — indicates chips are scrollable to the right |
| `Divider` | Boolean | True | True, False | Vertical divider between chips in the stack |

> `Plenty` renders a scrollable overflow state. Use when the chip count is dynamic or unknown at design time.

---

## Rules

- Always use `Chip - Stack` for groups of chips — never place individual `Chip` atoms side-by-side manually.
- Use `Cross` state for **applied / removable** filters — not for general selection.
- Use `Dropdown` only when tapping the chip opens a picker — not as a decorative indicator.
- Use `L-overflow` and `R-overflow` together when a chip row is horizontally scrollable.
- `Divider=False` should only be used when a visually clean separation-free row is required (rare).

---
---

# 9. Tabs — POP Design System

The Tab system provides horizontal navigation between content sections or views. Multiple tab styles are available — from standard underline tabs to pill, visual, and fancy variants.

---

## 9a. Tab Unit (Base)

**Component name:** `.Tab Unit`
**Figma page:** `↪️ ❖ Tab ✅`
**Node ID:** `3018:1630`
**Use when:** The tab is placed inside a custom layout or inside `Tab - Stack`. Not used standalone in final screens.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Is active` | Variant | True | True, False | Active/selected state |
| `Is disabled` | Variant | False | False, True | Disabled state |
| `R-icon - Title` | Boolean | False | True, False | Icon to the right of the tab label |
| `L-icon - Title` | Boolean | False | True, False | Icon to the left of the tab label |
| `Title text` | Text | "Label • 0" | Any string | Tab label — set the text per tab context |

---

## 9b. Tab Stack

**Component name:** `Tab - Stack`
**Figma page:** `↪️ ❖ Tab ✅`
**Node ID:** `6022:17794`
**Use when:** Displaying a horizontal row of standard underline tabs. Default tab pattern for in-page navigation.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 2 | 2, 3, 4, 5, Plenty | Number of tabs |
| `R-overflow` | Boolean | False | True, False | Right edge fade — scrollable tabs overflow indicator |
| `L-overflow` | Boolean | False | True, False | Left edge fade — scrollable tabs overflow indicator |
| `Switch tab - Page swipe` | Boolean | False | True, False | Indicates the tab is linked to a horizontal page swipe — active tab follows swipe gesture |

> `Plenty` renders a scrollable overflow state. Use when tab count is dynamic.

---

## 9c. Tab Pill Unit

**Component name:** `Tab - Pill - Units`
**Figma page:** `↪️ ❖ Tab ✅`
**Node ID:** `4575:15060`
**Use when:** A single pill-style tab unit — used inside `Tab - Pill - Stack`.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Is active` | Variant | False | False, True |
| `Is disabled` | Variant | False | False, True |
| `Title text` | Text | "00" | Any string |

---

## 9d. Tab Pill Stack

**Component name:** `Tab - Pill - Stack`
**Figma page:** `↪️ ❖ Tab ✅`
**Node ID:** `4575:15065`
**Use when:** A compact pill-style tab group — typically 2–3 options. Common for binary or ternary views (e.g. "Monthly / Yearly", "All / Income / Expense").

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 2 | 2, 3 | Number of pill tabs |
| `BG` | Variant | False | False, True | Background fill on the pill container |
| `Selected` | Variant | Left | Left, Centre, Right | Which tab is currently selected |
| `Is disabled` | Variant | False | False, True | Disabled state for the whole group |

---

## 9e. Tab Fancy Unit

**Component name:** `Tab - Fancy - Unit`
**Figma page:** `↪️ ❖ Tab ✅`
**Node ID:** `13799:24700`

> ⚠️ This is an **atom** — a building block only. Never place `Tab - Fancy - Unit` directly on a screen. Always use `Image Tab w bg` which composes these units into a ready-to-use tab bar.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Selected` | Variant | True | True, False |

| Selected | Description |
|---|---|
| True | The tab is in the active/selected state |
| False | The tab is inactive |

---

## 9f. Tab Visual

**Component name:** `Tab - Visual`
**Figma page:** `↪️ ❖ Tab ✅`
**Node ID:** `13734:19715`
**Use when:** Tabs that include an image, icon, or rich visual indicator alongside the label.

> ⚠️ This is an **atom** — a building block only. Never place `Tab - Visual` directly on a screen. Always use `Tab - Visual - Stack` instead.

### Properties

| Property | Type | Options |
|---|---|---|
| `Type` | Variant | W Image, W Icon, W Tab |

| Type | Description |
|---|---|
| W Image | Tab with an image thumbnail |
| W Icon | Tab with an icon |
| W Tab | Tab with a nested sub-tab indicator |

---

## 9g. Tab Visual Stack

**Component name:** `Tab - Visual - Stack`
**Figma page:** `↪️ ❖ Tab ✅`
**Node ID:** `13455:174081`

### Properties

| Property | Type | Options |
|---|---|---|
| `Count` | Variant | 1, 2, 3, 4, 5, 6, Plenty |
| `Padding` | Variant | True, False |

---

## Tab Selection Guide

| Pattern | Use |
|---|---|
| In-page section navigation (2–5 tabs) | `Tab - Stack` |
| Binary or ternary view toggle | `Tab - Pill - Stack` |
| Media/category browsing with visuals | `Tab - Visual - Stack` |
| Fancy image-backed tabs | `Image Tab w bg` |
| Dynamic/unknown tab count | Any stack with `Count=Plenty` |

---

## Rules

- Always use a Stack component for final screens — never place individual `Tab Unit` atoms manually in a row.
- ⛔ **Never use `Tab - Fancy - Unit` directly** — always use `Image Tab w bg`, which composes these units into a complete tab bar.
- ⛔ **Never use `Tab - Visual` directly** — always use `Tab - Visual - Stack` instead.
- The active tab must always be visually distinguishable — never set all tabs to `Is active=False` or `Selected=False`.
- Use `Plenty` with overflow indicators (`L-overflow`, `R-overflow`) when tabs scroll horizontally.
- `Switch tab - Page swipe=True` must be wired to a horizontal scroll/swipe container in code — not purely decorative.
- `Tab - Pill - Stack` supports maximum **3 tabs** — for more options use `Tab - Stack`.

---
---

# 10. Bottom Nav 2 — POP Design System

`Bottom nav 2` is the primary bottom navigational bar of the app. It sits fixed at the bottom of the screen and provides top-level destination switching across the three core app sections. It carries a `Cart FAB` that surfaces conditionally when the user is on the Shop tab and has pending items in their cart.

**Component name:** `Bottom nav 2`
**Figma page:** `↪️ ❖ Tab ✅`
**Node ID:** `14290:55619`

---

## Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Tab` | Variant | Home | Home, Shop, Card | Sets which tab destination is currently active |
| `Collapsed` | Variant | False | False, True | Collapses the nav bar — used when the screen needs full vertical space (e.g. scrolled state) |

---

## Tab States

| Tab | Active destination | When to use |
|---|---|---|
| **Home** | Home feed / dashboard | User is on the home screen |
| **Shop** | Shop / marketplace | User is browsing the shop |
| **Card** | Card / payments | User is on the card or payments screen |

---

## Collapsed State

| Collapsed | Behaviour |
|---|---|
| `False` | Full nav bar visible — default for all standard screens |
| `True` | Nav bar collapses — use when scrolling or full-bleed content requires reclaiming vertical space; defined by designer per screen |

---

## Embedded Components

Bottom nav 2 has the following sub-components pre-wired inside it:

| Sub-component | Purpose |
|---|---|
| `Cart FAB` | Floating cart action — appears **only** when the user is on the **Shop tab** and has **at least one pending item in their cart**. Hidden in all other states. |
| `Avatar` (Brand type, 28px) | Displays the **product images of the pending cart items** alongside the Cart FAB — gives the user a visual preview of what's in their cart without opening it. |
| `Chevron` (inside Cart FAB) | Right-pointing chevron icon on the Cart FAB. Whether to show it and **when to show it is a product/design decision** — the designer defines the specific condition (e.g. expand cart panel, navigate to checkout) per screen. |
| `Icon Wrapper` | Tab icon — size 20px |

---

## Rules

- Bottom nav 2 is always **fixed to the bottom of the screen** — never scroll it with content.
- Only one instance of `Bottom nav 2` per screen.
- Always set `Tab` to the active destination — never leave all tabs in an inactive state.
- `Collapsed=True` is set by the designer per screen based on scroll behaviour — do not default to collapsed.
- `Cart FAB` is **only shown on the Shop tab when the user has pending cart items** — do not show it on Home, Card, or any empty-cart state.
- The `Avatar` next to the Cart FAB shows **product images of the pending cart items** — it is not a user profile avatar.
- `Chevron` visibility on the Cart FAB is a **product decision** — the designer specifies the condition under which it appears (e.g. expandable cart drawer, proceed to checkout). Do not apply it without a defined interaction.
- Do not detach or manually modify the embedded `Cart FAB` or `Avatar` sub-components — configure them through the exposed properties only.

---
---

# 11. Input Field — POP Design System

The Input field system covers all text entry patterns. Multiple component types exist for different input contexts — from standard form inputs to OTP entry, search, and naked (borderless) amount inputs.

---

## Component Overview

| Component | Use case |
|---|---|
| `Input field` | Standard form text input — the primary input component |
| `Input field - Small` | Compact form input for dense layouts |
| `Input field - Naked Small` | Borderless compact input for tags, notes, inline edit |
| `Input field - Naked Large` | Borderless large input for amount / currency entry |
| `Input item - Discrete` | Single OTP digit box |
| `Input organism - Discrete` | Full OTP input (4 or 6 digits) with resend CTA |
| `Input organism - Discrete - Naked` | Naked full OTP input |
| `Input - Search` | Search bar with optional keyboard icon |
| `Input - Mobile` | Mobile number entry (with flag + prefix) |
| `Input stack (horizontal)` | Two inputs side by side |
| `Help Text` | Helper / error / success / warning message below input |

---

## 10a. Input Field (Primary)

**Component name:** `Input field`
**Figma page:** `↪️ ❖ Input field ✅`
**Node ID:** `3821:4239`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Default | Default, Error, Success, Disabled | Semantic input state |
| `BG` | Variant | True | True, False | Background fill on the input container |
| `Is active` | Variant | False | False, True | Focus/active state (cursor visible) |
| `Is filled` | Variant | False | False, True | Whether user has entered a value |
| `L-icon` | Boolean | False | True, False | Leading icon inside the input |
| `R-icon` | Boolean | False | True, False | Trailing icon (e.g. eye for password, clear ✕) |
| `R-slot` | Boolean | False | True, False | Trailing slot for a component (e.g. OTP resend link, inline CTA) |
| `R-overflow` | Boolean | False | True, False | Right overflow fade for long pre-filled content |
| `Info message` | Boolean | False | True, False | Shows an info helper message below the field |
| `Pre-filled` | Boolean | False | True, False | Shows a prefix label inside the field (e.g. country code, ₹) |
| `Post-filled` | Boolean | False | True, False | Shows a suffix label inside the field (e.g. `.00`, `@yespop`) |
| `Placeholder text` | Text | "Placeholder" | Any string | Hint text shown when the field is empty |
| `Body text` | Text | "Filled" | Any string | The entered/filled value text |
| `Title text` | Text | "Title" | Any string | Field label above the input |
| `Info text` | Text | "Info message" | Any string | Helper text shown below (requires `Info message=True`) |
| `Error text` | Text | "Error message" | Any string | Error message (shown in `State=Error`) |
| `Success text` | Text | "Success message" | Any string | Success message (shown in `State=Success`) |
| `Pre-filled text` | Text | "Prefilled" | Any string | Prefix content (e.g. "+91", "₹") |
| `Post-filled text` | Text | "@yespop" | Any string | Suffix content (e.g. ".00", "@yespop") |

### States

`State`, `Is active`, and `Is filled` are **independent properties** — in code all three must be read and applied simultaneously, never as a switch/if-else chain.

| State | Description |
|---|---|
| Default | Idle, empty — field is waiting for input |
| Default + Is Active | Focused, empty — cursor is active, keyboard is open |
| Default + Is Filled | Unfocused, has value — user typed and moved away |
| Default + Is Active + Is Filled | Focused, has value — user is editing existing content |
| Error | Validation failed, empty |
| Error + Is Filled | Validation failed, has value |
| Error + Is Active + Is Filled | Validation failed, focused — user is editing to correct the error |
| Success | Validated, empty |
| Success + Is Filled | Validated, has value |
| Success + Is Active + Is Filled | Validated, focused — user is editing a validated field |
| Disabled | Locked, empty — cannot be interacted with |
| Disabled + Is Filled | Locked, has value — shows content but not editable |

> `Error + Is Active` and `Success + Is Active` (focused but empty) are not valid states — error and success only appear after a user has entered a value. A focused empty field is always `Default + Is Active`.

---

## 10b. Input Field — Small

**Component name:** `Input field - Small`
**Figma page:** `↪️ ❖ Input field ✅`
**Node ID:** `8220:47609`
**Use when:** Compact form input for dense layouts — same behaviour as the primary Input field but at a smaller scale.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Default | Default, Error, Success, Disabled | Semantic input state |
| `BG` | Variant | True | True, False | Background fill on the input container |
| `Is active` | Variant | False | False, True | Focus/active state — cursor visible, keyboard open |
| `Is filled` | Variant | False | False, True | Whether user has entered a value |
| `Pre-filled` | Boolean | False | True, False | Shows prefix label inside the field |
| `Post-filled` | Boolean | False | True, False | Shows suffix label inside the field |
| `Info message` | Boolean | False | True, False | Shows an info helper message below the field |
| `R-overflow` | Boolean | False | True, False | Right overflow fade for long content |
| `Title` | Text | "Add note" | Any string | Field label above the input |
| `Placeholder text` | Text | "Eg. Lunch" | Any string | Hint text shown when the field is empty |
| `Body text` | Text | "Dinner" | Any string | The entered/filled value |
| `Pre-filled text` | Text | "Prefilled" | Any string | Prefix content |
| `Post-filled text` | Text | "@yespop" | Any string | Suffix content |
| `Info text` | Text | "Try 'RENT'" | Any string | Helper text shown below (requires `Info message=True`) |
| `Error text` | Text | "'.' not allowed" | Any string | Error message shown in `State=Error` |
| `Success text` | Text | "Promo applied" | Any string | Success message shown in `State=Success` |

---

## 10c. Input Field — Naked Large

**Component name:** `Input field - Naked Large`
**Figma page:** `↪️ ❖ Input field ✅`
**Node ID:** `4354:21420`
**Use when:** Large borderless amount/currency entry — the primary pattern for payment amount inputs.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Default | Default, Error, Success, Disabled, Warning | Semantic input state — Warning is unique to this variant |
| `Is active` | Variant | False | False, True | Focus/active state — cursor visible, keyboard open |
| `Is filled` | Variant | False | False, True | Whether user has entered a value |
| `Pre-filled` | Boolean | True | True, False | Shows prefix label inside the field (e.g. ₹) — on by default |
| `Post-filled` | Boolean | False | True, False | Shows suffix label inside the field (e.g. .00) |
| `Title` | Boolean | False | True, False | Shows a label above the input field |
| `L-overflow` | Boolean | False | True, False | Left overflow fade for long content |
| `R-overflow` | Boolean | False | True, False | Right overflow fade for long content |
| `Info message` | Boolean | False | True, False | Shows an info helper message below the field |
| `L-icon - Info` | Boolean | True | True, False | Leading icon on the info helper message |
| `R-icon - Info` | Boolean | False | True, False | Trailing icon on the info helper message |
| `L-icon - Error` | Boolean | True | True, False | Leading icon on the error message |
| `R-icon - Error` | Boolean | False | True, False | Trailing icon on the error message |
| `L-icon - Success` | Boolean | True | True, False | Leading icon on the success message |
| `R-icon - Success` | Boolean | False | True, False | Trailing icon on the success message |
| `L-icon - Warning` | Boolean | True | True, False | Leading icon on the warning message |
| `R-icon - Warning` | Boolean | False | True, False | Trailing icon on the warning message |
| `Title text` | Text | "Title" | Any string | Field label above the input |
| `Body text` | Text | "1,234" | Any string | The entered/filled value |
| `Pre-filled text` | Text | "₹" | Any string | Prefix content (e.g. ₹, +91) |
| `Post-filled text` | Text | ".00" | Any string | Suffix content (e.g. .00, %) |
| `Info text` | Text | "Info message" | Any string | Helper text shown below (requires `Info message=True`) |
| `Error text` | Text | "Error message" | Any string | Error message shown in `State=Error` |
| `Success text` | Text | "Success message" | Any string | Success message shown in `State=Success` |
| `Warning text` | Text | "Warning message" | Any string | Warning message shown in `State=Warning` |

---

## 10d. Input Field — Naked Small

**Component name:** `Input field - Naked Small`
**Figma page:** `↪️ ❖ Input field ✅`
**Node ID:** `3825:5885`
**Use when:** Inline borderless input — for add-note fields, tags, compact edit contexts.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Default | Default, Error, Success, Disabled | Semantic input state |
| `Is active` | Variant | False | False, True | Focus/active state — cursor visible, keyboard open |
| `Is filled` | Variant | False | False, True | Whether user has entered a value |
| `L-icon` | Boolean | False | True, False | Leading icon inside the input |
| `R-icon` | Boolean | False | True, False | Trailing icon (e.g. clear ✕) |
| `R-slot` | Boolean | False | True, False | Trailing slot for an inline component |
| `R-overflow` | Boolean | False | True, False | Right overflow fade for long content |
| `Pre-filled` | Boolean | False | True, False | Shows prefix label inside the field |
| `Post-filled` | Boolean | False | True, False | Shows suffix label inside the field |
| `Title` | Boolean | True | True, False | Shows a label above the input field |
| `Info message` | Boolean | False | True, False | Shows an info helper message below the field |
| `Title text` | Text | "Title" | Any string | Field label above the input |
| `Body text` | Text | "Filled" | Any string | The entered/filled value |
| `Placeholder text` | Text | "Placeholder" | Any string | Hint text shown when the field is empty |
| `Pre-filled text` | Text | "SBIN" | Any string | Prefix content (e.g. bank code, +91) |
| `Post-filled text` | Text | "@yespop" | Any string | Suffix content (e.g. @handle, %) |
| `Info text` | Text | "Info message" | Any string | Helper text shown below (requires `Info message=True`) |
| `Error text` | Text | "Error message" | Any string | Error message shown in `State=Error` |
| `Success text` | Text | "Success message" | Any string | Success message shown in `State=Success` |

---

## 10e. Input Item — Discrete (OTP Single Cell)

**Component name:** `Input item - Discrete`
**Figma page:** `↪️ ❖ Input field ✅`
**Node ID:** `4189:12557`
**Use when:** A single OTP digit box — always used as part of `Input organism - Discrete`, not standalone.

### Properties

| Property | Type | Options |
|---|---|---|
| `Size` | Variant | 56, 44 |
| `State` | Variant | Default, Error, Disabled |
| `BG` | Variant | True, False |
| `Is active` | Variant | False, True |
| `Is filled` | Variant | False, True |

---

## 10f. Input Organism — Discrete (Full OTP Input)

**Component name:** `Input organism - Discrete`
**Figma page:** `↪️ ❖ Input field ✅`
**Node ID:** `4235:17681`
**Use when:** Full OTP entry experience — includes the digit boxes, identifier text, body message, and resend/edit CTAs.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Digit count` | Variant | 4 | 4, 6 | Number of OTP digits |
| `Size` | Variant | 56 | 56, 44 | Height of each digit box |
| `State` | Variant | Default | Default, Error, Disabled | — |
| `Filled` | Variant | 0 | 0–6 | How many digits are filled |
| `Primary CTA enabled` | Variant | True | True, False | Whether the resend CTA is active or in countdown mode |
| `Body` | Boolean | True | True, False | Shows the "OTP sent on…" message |
| `Secondary CTA` | Boolean | True | True, False | Shows the "Edit" CTA to change the phone number |
| `Identifier` | Boolean | True | True, False | Shows the identifier (e.g. phone number) |
| `Identifier text` | Text | "99999 99999" | Any string | The masked phone number |
| `Body text` | Text | "OTP sent on +91" | Any string | Helper message below the OTP boxes |
| `Primary CTA enabled text` | Text | "Resend OTP" | Any string | Active resend label |
| `Primary CTA disabled text` | Text | "Resend in 00:29" | Any string | Countdown label when resend is disabled |
| `Secondary CTA text` | Text | "Edit" | Any string | Edit CTA label |

---

## 10g. Input Organism — Discrete — Naked (Full OTP Naked)

**Component name:** `Input organism - Discrete - Naked`
**Figma page:** `↪️ ❖ Input field ✅`
**Node ID:** `4453:16354`
**Use when:** Naked (borderless) full OTP input — same structure as the standard Discrete OTP organism but without the background/border treatment.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Digit count` | Variant | 4 | 4, 6 | Number of OTP digit cells |
| `Size` | Variant | 56 | 56, 44 | Height of each digit cell in px |
| `State` | Variant | Default | Default, Error, Disabled | Semantic state |
| `Filled` | Variant | 0 | 0, 1, 2, 3, 4, 5, 6 | How many digits are currently filled |
| `Primary CTA enabled` | Variant | False | True, False | Whether the resend CTA is in active/enabled state |
| `Body` | Boolean | True | True, False | Shows the helper body text below the OTP cells |
| `Identifier` | Boolean | True | True, False | Shows the identifier (e.g. masked phone number) alongside body text |
| `Secondary CTA` | Boolean | True | True, False | Shows the secondary action (e.g. Edit) |
| `Body text` | Text | "OTP sent on +91" | Any string | Helper message below the OTP cells |
| `Identifier text` | Text | "99999 99999" | Any string | The masked phone/email identifier shown in body |
| `Primary CTA enabled text` | Text | "Resend OTP" | Any string | Label shown on resend CTA when enabled |
| `Primary CTA disabled text` | Text | "Resend in 00:29" | Any string | Label shown on resend CTA when on cooldown |
| `Secondary CTA text` | Text | "Edit" | Any string | Label for the secondary action |

---

## 10h. Input — Search

**Component name:** `Input - Search`
**Figma page:** `↪️ ❖ Input field ✅`
**Node ID:** `4536:12367`

### Properties

| Property | Type | Options |
|---|---|---|
| `State` | Variant | Default, Error, Success, Disabled |
| `BG` | Variant | True, False |
| `Is active` | Variant | False, True |
| `Is filled` | Variant | False, True |
| `Keypad icon` | Variant | False, True |

> `Keypad icon=True` shows a keyboard icon — used when the search field triggers a custom keypad rather than the OS keyboard.

---

## 10i. Input — Mobile

**Component name:** `Input - Mobile`
**Figma page:** `↪️ ❖ Input field ✅`
**Node ID:** `4235:14047`
**Use when:** Mobile number entry — includes a country flag and dialling prefix (+91) pre-wired on the left. Use for all phone number input fields.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Default | Default, Error, Success, Disabled | Semantic input state |
| `Is active` | Variant | False | False, True | Focus/active state — cursor visible, keyboard open |
| `Is filled` | Variant | False | False, True | Whether the user has entered a number |

> The country flag and `+91` prefix are built into the component — do not add them separately. This component has no additional boolean or text properties beyond the three variant axes.

---

## 10j. Help Text

**Component name:** `Help Text`
**Figma page:** `↪️ ❖ Input field ✅`
**Node ID:** `7765:36103`
**Use when:** Displaying helper, error, success, or warning text below an input field. Can be used standalone or is built into the input field components.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Info | Info, Success, Error, Warning | Semantic colour treatment |
| `Padding` | Variant | 12 | 12, 8, 0 | Top padding from the input above it |
| `L-Icon` | Boolean | True | True, False | Leading semantic icon |
| `R-icon` | Boolean | False | True, False | Trailing icon |
| `Link text` | Boolean | False | True, False | Inline link/CTA in the helper text |
| `R-Overflow` | Boolean | False | True, False | Right overflow fade for long messages |

---

## 10k. Input Stack (Horizontal)

**Component name:** `Input stack (horizontal)`
**Figma page:** `↪️ ❖ Input field ✅`
**Node ID:** `21910:9493`
**Use when:** Two inputs need to sit side by side (e.g. First name + Last name, Day + Month).

### Properties

| Property | Type | Options |
|---|---|---|
| `Variant` | Variant | Large + Small, Small + Large, Equal (Fill container) |

---

## Input Field Selection Guide

| Context | Component to use |
|---|---|
| Standard form text input | `Input field` |
| Currency / amount entry | `Input field - Naked Large` |
| Add note / tag / inline edit | `Input field - Naked Small` |
| OTP entry (4 or 6 digit) | `Input organism - Discrete` |
| Search bar | `Input - Search` |
| Two inputs side by side | `Input stack (horizontal)` |
| Helper/error message below input | `Help Text` |

---

## Rules

- Always set `Title text` — never leave "Title" as the production label.
- Always set `Placeholder text` to contextual hint copy — never leave "Placeholder".
- Use `State=Error` + `Error text` for validation failures — never use `Nudge` or `Help Text` independently for inline field errors unless the input field itself doesn't have an error state.
- `Pre-filled` and `Post-filled` are for **non-editable** prefix/suffix content (e.g. ₹, +91, .00) — not for default-filled values.
- `BG=False` is for inputs on surfaces that already have a container background — prevents double-layering.
- Use `Input field - Naked Large` for all currency amount entry — never a standard `Input field` for amount screens.
- `Input organism - Discrete` must always use `Digit count` matching the OTP length from the backend — never hardcode 4 digits for a 6-digit OTP flow.

---
---

# 11. Card & Card Stack — POP Design System

Cards are contained content blocks with optional header, slot rows, background, and border treatment. The Card Stack groups multiple cards in a vertical list.

---

## 11a. Card

**Component name:** `Card`
**Figma page:** `↪️ ❖ Card 🚧`
**Node ID:** `5344:1233`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Slot` | Variant | 0 | 0, 1, 2, 3, 4, 5, Plenty | Number of content slot rows inside the card |
| `BG` | Boolean | True | True, False | Background gradient fill on the card |
| `Enclosed` | Boolean | True | True, False | Border/stroke on the card container |
| `Header` | Boolean | False | True, False | Toggles the card header (title + optional body + optional right slot) |
| `Title` | Boolean | True | True, False | Title text in the header |
| `Body` | Boolean | False | True, False | Body/subtitle text in the header |
| `Title text` | Text | "Title" | Any string | Header title content |
| `Body text` | Text | "Body" | Any string | Header subtitle content |
| `L-icon - Title` | Boolean | False | True, False | Icon left of the header title |
| `R-icon - Title` | Boolean | False | True, False | Icon right of the header title |
| `L-icon - Body` | Boolean | False | True, False | Icon left of the header body |
| `R-icon - Body` | Boolean | False | True, False | Icon right of the header body |
| `R-slot - Header` | Boolean | False | True, False | Right slot in the header (for a CTA, avatar, or icon) |
| `R-overflow - Header` | Boolean | False | True, False | Gradient overflow fade on the header title |
| `Divider - Header` | Boolean | False | True, False | Divider below the header |
| `Divider - Body` | Boolean | False | True, False | Dividers between slot rows in the card body |

### Nested Component — Horizontal Divider

When `Divider - Header=True` or `Divider - Body=True`, a `Horizontal divider` atom is rendered inside the card. This nested divider exposes two configurable properties directly from the card context:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Continuous | Continuous, Dashed, Stylised | Controls the line style of the divider |
| `Colour` | Variant | Secondary | Primary, Secondary, Tertiary, Blured, Mixed | Controls the colour token of the divider |

> The default `Colour=Secondary` uses `Border/Secondary` → `Greys/Grey-700` (`#333333`). Only change `Type` or `Colour` if the card surface requires a different treatment — for most cards the defaults are correct.

### Slot Count

| Slot | Description |
|---|---|
| 0 | Header only — no content rows |
| 1–5 | Fixed number of content slot rows |
| Plenty | Scrollable overflow — use when slot count is dynamic |

### BG + Enclosed combinations

| BG | Enclosed | Visual |
|---|---|---|
| True | True | Gradient background + border — default card appearance |
| False | True | No background + border — outlined flat card |
| False | False | No background, no border — frameless/ghost card |

---

## 11b. Card Stack

**Component name:** `Card - Stack`
**Figma page:** `↪️ ❖ Card 🚧`
**Node ID:** `6690:5925`
**Use when:** Displaying a vertical list of cards with consistent spacing.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3, 4, 5, Plenty |

> `Plenty` renders a scrollable overflow of cards. Use for dynamic content lists.

---

## Rules

- Use `Card - Stack` for all lists of multiple cards — never manually stack individual `Card` instances.
- Always populate `Title text` and `Body text` with real content — never leave "Title" or "Body" as production text.
- `Slot=0` is for header-only cards (e.g. a simple label card with no content rows).
- `Enclosed=False` with `BG=False` is a ghost card — use only when the card must be visually frameless (e.g. inside a tinted surface that provides its own container).
- `Divider - Header=True` separates the header from the first content slot — always use when both `Header=True` and `Slot ≥ 1`.

---

### Local Components

#### .Header Item

> ⚠️ **Local component** — `.Header item` is scoped to ODP Card only and must not be used outside of it.

| Property | Type | Default | Notes |
|---|---|---|---|
| `with bg` | Variant | False | Background fill on header row |
| `L - Label` | Text | "Label" | Left label — typically category or merchant name |
| `R - Label` | Text | "Label" | Right label — typically a date, ID, or secondary context |

#### .Footer Item

> ⚠️ **Local component** — `.footer item` is scoped to ODP Card only and must not be placed or used outside of it.

**Component name:** `.footer item`
**Visible when:** `Footer=True` on the parent ODP Card

The footer item renders a **Rating bar item** row at the bottom of the card — a label pair with an optional right slot for a supplementary component (e.g. a rating star, badge, or icon).

| Property | Type | Default | Notes |
|---|---|---|---|
| `L-label` | Text | "Label" | Left label text — e.g. rating score, offer tag |
| `R - Label` | Text | "Label" | Right label text — e.g. review count, expiry date |
| `R-label` | Boolean | True | Toggles the right label on/off |
| `Separator` | Boolean | True | Shows/hides the dot (·) separator between L and R labels |

```
.footer item
  └── Rating bar item
        ├── L-label text
        ├── · (separator)
        ├── R-label text
        └── Slot  ← free slot — place a rating star, badge, or icon here
```

> The `Slot` inside Rating bar item is a free placement slot — put the relevant component (e.g. `Rating badge`, `Badge`) directly into it. Do not leave it empty when `Footer=True`.

---

## 11c. ODP Card

The ODP Card (Offer Detail Panel card) is a structured card used to display an offer, product, or detail entry with a header and an optional rating/info footer.

**Component name:** `ODP card`
**Figma page:** `↪️ ❖ Card 🚧`
**Node ID:** `21904:39171`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Footer` | Variant | False | False, True | Shows/hides the footer bar below the card |
| `Slot` | Slot | — | Any component | Content slot inside the card body — preferred instance is `OLP-list` |

### Structure

```
ODP Card
  ├── .Header item       ← top bar (L-Label • R-Label + chevron) — local component
  ├── PDP Card slot      ← content slot (place product/offer component here)
  └── .Footer item       ← (only when Footer=True) — local component
          └── Rating bar item  ← L-Label • R-Label + slot
```

### Use Cases

| Context | Footer | Notes |
|---|---|---|
| Offer card without rating | False | Standard offer/deal card |
| Offer card with rating/review info | True | Shows footer with rating bar |
| Product detail entry in a list | False | `.Header item` shows product category + ID |

### Rules

- Always populate `L - Label` and `R - Label` in `.Header item` with real content — these are the primary identifiers for the card.
- Use `Footer=True` only when there is actual rating or supplementary data to display — do not use the footer as decoration.
- The PDP Card slot is a free slot — place the appropriate product/offer component from the design library.
- The `Rating bar item` inside the footer is display-only — do not wire an interaction to it directly; use a parent tap target wrapping the card.
- Border radius: top-only radius when `Footer=True` — the footer attaches flush below the card body with bottom radius on the footer strip.

---
---

## General Molecule Rules

- **Never reconstruct a molecule from atoms manually** — always use the highest-level component that fits.
- **Always use Stack variants** for lists of 2 or more repeating units (chips, tabs, cards).
- **Never leave placeholder text** (`Title`, `Label`, `Placeholder`, `Body`) as production content — always set real copy.
- **Slot properties** (L-slot, R-slot) are off by default — only enable them when you have a specific component to place inside.
- **Boolean properties** that toggle text lines (`Body`, `Title`, `R Label`) should be set to `False` when that content is not needed — never show empty text lines.
- **BG and Enclosed** on cards follow the surface context — match the card treatment to the surrounding screen surface.
- **Dividers** on the last item in any group must be `False` — trailing dividers after the final row are always incorrect.
