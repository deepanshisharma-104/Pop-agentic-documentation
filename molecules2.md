# Molecules 2 — POP Design System

> Components prefixed with `.` (e.g. `.Shop list item`, `.Vertical Timeline Unit`) are **internal building blocks** — they are not placed directly in UI screens. They are composed into higher-level stack or organism components automatically. Never use them directly in final screen designs.

---

## Component Index

| # | Component Group | Figma Page |
|---|---|---|
| 1 | PL Card (Product Listing Card, Offer Variations, P3 Intro Card) | `↪️ ❖ PL Card` |
| 2 | Accordion | `↪️ ❖ Accordion ✅` |
| 3 | List (all variants) | `↪️ ❖ List ✅` |
| 4 | Table (all variants) | `↪️ ❖ Table ✅` |
| 5 | Pagination (all variants) | `↪️ ❖ Pagination ✅` |
| 6 | Badge, Content Card | `↪️ ❖ Badge ✅` |
| 7 | Vertical Button, Benefit Callout, Text Divider | `↪️ ❖ Vertical button ✅` |
| 8 | Button (all variants) | `↪️ ❖ Button ✅` |
| 9 | Timeline (all variants) | `↪️ ❖ Timeline ✅` |
| 10 | Quantity Stepper, Amount + Quantity Stepper | `↪️ ❖ Quantity Stepper 🚧` |

---
---

# 1. PL Card — POP Design System

The PL Card page houses product listing components: a local offer variation component for PL Card, the main Product Listing Card, and the P3 intro card. These are used together in the Shop / product listing contexts.

---

## 1a. Offer Variations

> ⚠️ **Local component** — `Offer variations` is a local component scoped to the **PL Card (Product Listing Card)** only. It is not used by or shared with the P3 Intro Card. Do not place it standalone or inside any other component.

**Component name:** `Offer variations`
**Figma page:** `↪️ ❖ PL Card`
**Node ID:** `20274:72899`
**Use when:** Displaying an offer badge inside the Product Listing Card only.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `type` | Variant | Pop coin | Pop coin, Payin3 | Type of offer to display |
| `Amount` | Text | "₹90" | Any string | Offer amount or value |
| `Xmon` | Text | "x3mo" | Any string | Instalment label (e.g. "x3mo" = 3 month split) |

| Type | Description |
|---|---|
| **Pop coin** | Shows a POP coin reward on the product |
| **Payin3** | Shows a Pay-in-3 instalment offer on the product |

---

## 1b. Product Listing Card

**Component name:** `Product Listing Card`
**Figma page:** `↪️ ❖ PL Card`
**Node ID:** `20424:106795`
**Use when:** Displaying a product in a grid or list on the Shop / listing screen.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Image ratio` | Variant | Default | 1:1, Default, 4:1 | Product image aspect ratio |
| `Size` | Variant | Medium | Medium, Small | Card size |
| `Badge` | Boolean | False | True, False | Shows a badge (e.g. "New", "Sale") on the image |
| `R-slot` | Boolean | False | True, False | Right slot for an action (e.g. wishlist heart, quick add) |
| `Offer%` | Boolean | False | True, False | Shows an offer percentage label on the card |
| `Spacer` | Boolean | True | True, False | Bottom spacer — maintains consistent card height in a grid |
| `Offer text` | Text | "10% off" | Any string | Offer label text |
| `Product Name` | Text | "Bombay Shaving Co." | Any string | Product brand or name |
| `Description` | Text | "Power Trimmer Trimmer" | Any string | Product description line |

### Image Ratio

| Ratio | Use case |
|---|---|
| 1:1 | Square product image — apparel, accessories |
| Default | Standard product card ratio |
| 4:1 | Wide banner-style product card |

### Nested Component — Offer Variations

The `Offer variations` local component is embedded inside every variant of the Product Listing Card. Its properties are configurable directly from the card context:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `type` | Variant | Payin3 | Pop coin, Payin3 | Type of offer badge shown on the card |
| `Amount` | Text | "₹90" | Any string | Offer amount or reward value |
| `Xmon` | Text | "x3mo" | Any string | Instalment label — e.g. "x3mo" = 3-month split |

> Always set `type`, `Amount`, and `Xmon` to reflect the actual offer on the product — never leave the defaults as production content.

---

## 1c. P3 Intro Card

**Component name:** `P3 intro card`
**Figma page:** `↪️ ❖ PL Card`
**Node ID:** `21137:213614`
**Use when:** Introducing the Pay-in-3 feature to users — shown in listing or onboarding contexts.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Unverified | Unverified, Eligible | User's P3 eligibility state |
| `Size` | Variant | 4:5 | 4:5, 1:1 | Card aspect ratio |
| `Spacer` | Boolean | False | True, False | Bottom spacer for grid alignment |

| State | Description |
|---|---|
| **Unverified** | User has not completed P3 verification — shows a prompt to verify |
| **Eligible** | User is verified and eligible for Pay-in-3 |

---

## Rules — PL Card

- `Offer variations` is a **local component of PL Card only** — always place it inside the Product Listing Card, never standalone and never inside P3 Intro Card or any other component.
- Always populate `Product Name` and `Description` with real content.
- `Spacer=True` keeps card heights consistent in a grid — only turn it off when you need variable-height cards.
- Use `P3 intro card` only in Shop/listing screens where Pay-in-3 is contextually relevant.

---
---

# 2. Accordion — POP Design System

The Accordion is a collapsible content block with a header and expandable body. Used for FAQs, detail sections, settings, and any content that benefits from progressive disclosure.

**Component name:** `Accordion`
**Figma page:** `↪️ ❖ Accordion ✅`
**Node ID:** `1811:16803`

---

## Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Enclosed` | Variant | True | True, False | Border/stroke on the accordion container |
| `BG` | Variant | False | False, True | Background fill on the accordion |
| `Open` | Variant | False | False, True | Expanded/collapsed state |
| `is disabled` | Variant | False | False, True | Disabled state |
| `Title` | Boolean | True | True, False | Shows the main title text |
| `Body` | Boolean | False | True, False | Shows body/description text in the header |
| `L slot` | Boolean | False | True, False | Left slot in the header (e.g. icon, avatar) |
| `L-icon - Title` | Boolean | False | True, False | Icon left of the title |
| `R-icon - Title` | Boolean | False | True, False | Icon right of the title |
| `L-icon - Body` | Boolean | False | True, False | Icon left of the body text |
| `R-icon - Body` | Boolean | False | True, False | Icon right of the body text |
| `R-slot - Header` | Boolean | False | True, False | Right slot in the header (e.g. badge, tag) |
| `R-overflow - Header` | Boolean | False | True, False | Gradient overflow on the header right edge |
| `End slot` | Boolean | False | True, False | Slot at the end of the expanded content area |
| `Title text` | Text | "Title" | Any string | Header title copy |
| `Body text` | Text | "Body" | Any string | Header subtitle copy |

---

## State Combinations

| Open | Enclosed | BG | is disabled | Description |
|---|---|---|---|---|
| False | True | False | False | Collapsed, bordered, no background — default |
| True | True | False | False | Expanded, bordered, no background |
| False | True | True | False | Collapsed, bordered, with background fill |
| True | True | True | False | Expanded, bordered, with background fill |
| False | False | False | False | Collapsed, frameless — used inside already-contained surfaces |
| True | False | False | False | Expanded, frameless |
| False | True | False | True | Disabled, collapsed, bordered — non-interactive |
| False | True | True | True | Disabled, collapsed, bordered, with background |

> `Enclosed=False` has no disabled state — the disabled treatment only exists for `Enclosed=True` variants.

---

## Rules

- Use `Open=False` as the default state in design — let the interaction model control the expanded state.
- ⛔ **A disabled accordion always stays closed** — never set `Open=True` and `is disabled=True` together. When `is disabled=True`, `Open` must always be `False`.
- `is disabled=True` is only available for `Enclosed=True` variants — there is no disabled state for `Enclosed=False`.
- Use `Enclosed=True` for standalone accordions. Use `Enclosed=False` only inside a card or section that already provides the container border.
- `Body=True` in the header is for a subtitle under the title — not for the expanded content body.
- Always set `Title text` — never leave "Title" as production copy.
- The expanded body content (below the header) is placed in the Slot — always fill it with the appropriate content component.

---
---

# 3. List — POP Design System

The List system covers all row-based list patterns: standard list items, status list items, shop list items, and their respective stacks.

---

## Component Overview

| Component | Use case |
|---|---|
| `List item` | Standard list row — people or brand avatar with labels |
| `List - Stack` | Vertical stack of List items |
| `Status list item` | List row with a semantic state indicator (Success/In-progress/Error) |
| `Status list item-stack` | Vertical stack of Status list item rows (1–5) |
| `Shop list- stack` | Stack of shop list items for the Shop screen |
| `Footer item` | Footer row for a shop list — Label, Amount stepper, or Details+Amount. ⚠️ Local component scoped to `.Shop list item` only |
| `Content item` | Content detail atom used inside shop list items. ⚠️ Local component scoped to `.Shop list item` only |
| `.Shop list item` | *(Internal)* Single shop list row — do not place directly |
| `OLP-list` | Single OLP list row with Pop coin, labels and title |
| `ODP-list` | Single ODP list row — used inside `ODP list- stack` |
| `ODP list- stack` | Stack of ODP list rows with optional dividers |
| `OLD-list` | ⛔ *(Deprecated — not the same as ODP-list)* — do not use |

---

## 3a. List Item

**Component name:** `List item`
**Figma page:** `↪️ ❖ List ✅`
**Node ID:** `3132:26124`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Med | Large, Med, Small | Row height and text size |
| `Type` | Variant | People | People, Brands | Avatar type — People (profile photo/initials) or Brand (logo) |
| `Title above` | Variant | False | True, False | Shows an above-row section title/label |
| `Is disabled` | Variant | False | False, True | Disabled state |
| `Leading Avatar` | Boolean | True | True, False | Shows the leading avatar on the left |
| `Title` | Boolean | True | True, False | Shows the title text line |
| `Body` | Boolean | True | True, False | Shows the body/description text line |
| `L-icon` | Boolean | False | True, False | Leading icon (replaces avatar when needed) |
| `L-icon - Title` | Boolean | False | True, False | Icon left of the title |
| `R-icon - Title` | Boolean | False | True, False | Icon right of the title |
| `L-icon - Body` | Boolean | False | True, False | Icon left of the body text |
| `R-icon - Body` | Boolean | False | True, False | Icon right of the body text |
| `R-icon` | Boolean | False | True, False | Trailing icon (e.g. chevron, disclosure) |
| `R-slot` | Boolean | False | True, False | Trailing slot (e.g. badge, toggle, button) |
| `R-badge` | Boolean | False | True, False | Trailing badge |
| `R-overflow` | Boolean | False | True, False | Right overflow gradient for long text |

### Type

| Type | Avatar shown | Use case |
|---|---|---|
| **People** | Profile photo or initials avatar | Contact lists, friend lists, payer/payee rows |
| **Brands** | Brand logo avatar | Merchant lists, payment method rows, app lists |

---

## 3b. List Stack

**Component name:** `List - Stack`
**Figma page:** `↪️ ❖ List ✅`
**Node ID:** `5388:11312`

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3, 4, 5, Plenty |
| `Size` | Variant | Large | Large, Med |
| `Divider colour` | Variant | Tertiary | Tertiary, Secondary |

> `Plenty` = scrollable overflow for dynamic lists.

---

## 3c. Status List Item

**Component name:** `Status list item`
**Figma page:** `↪️ ❖ List ✅`
**Node ID:** `21157:272361`
**Use when:** A list row needs to communicate a semantic progress/status state (e.g. order tracking, transaction history).

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Success | Success, In-progress, Error | Semantic status of the row |
| `Body` | Boolean | True | True, False | Shows body text below the title |
| `Separator` | Boolean | True | True, False | Bottom divider |
| `R text - Body` | Boolean | True | True, False | Right-side body text |
| `L - slot` | Boolean | False | True, False | Left slot (e.g. custom icon, avatar) |
| `R -  Slot` | Boolean | False | True, False | Right slot |
| `R - icon` | Boolean | False | True, False | Trailing icon |
| `Badge` | Boolean | False | True, False | Badge attached to the row |
| `L icon - Title` | Boolean | False | True, False | Icon left of title |
| `R icon - Title` | Boolean | False | True, False | Icon right of title |
| `L icon - Body` | Boolean | False | True, False | Icon left of body |
| `R icon - Body` | Boolean | False | True, False | Icon right of body |
| `R overflow` | Boolean | False | True, False | Right overflow gradient |
| `Title` | Text | "Title" | Any string | Row title text |
| `R text - body` | Text | "Label" | Any string | Right body text |
| `L text - Body` | Text | "Label" | Any string | Left body text |

| State | Visual | Use case |
|---|---|---|
| **Success** | Green indicator | Completed step, successful transaction |
| **In-progress** | Active/loading indicator | Current step, pending transaction |
| **Error** | Red indicator | Failed step, error state |

---

## 3d. Status List Item Stack

**Component name:** `Status list item-stack`
**Figma page:** `↪️ ❖ List ✅`
**Node ID:** `21157:273685`
**Use when:** Displaying a vertical stack of status list item rows — e.g. order tracking steps, transaction history, or multi-step progress lists.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3, 4, 5 | Number of Status list item rows in the stack |

---

## 3e. Shop List Stack

**Component name:** `Shop list- stack`
**Figma page:** `↪️ ❖ List ✅`
**Node ID:** `20381:162206`
**Use when:** Displaying the list of items in a shopping cart or order summary.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3, 4, 5, Plenty |

---

## 3f. Footer Item *(Shop list footer)*

**Component name:** `Footer item`
**Figma page:** `↪️ ❖ List ✅`
**Node ID:** `20804:226795`
**Use when:** The footer row of a shop list — shows a total, a quantity stepper, or a details+amount summary.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `type` | Variant | Amount stepper | Label, Amount stepper, Details + Amount | Controls the footer layout |

| Type | Description |
|---|---|
| **Label** | Simple text label row — for section labels or summary text |
| **Amount stepper** | Quantity stepper with amount — for cart quantity control |
| **Details + Amount** | Detail labels alongside the total amount |

---

## 3g. Content Item *(Shop list content atom)*

**Component name:** `Content item`
**Figma page:** `↪️ ❖ List ✅`
**Node ID:** `20804:227080`
**Use when:** Displaying content details inside a shop list item (date, size, body copy, POP coins).

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `type` | Variant | Date + Size | Body+pop-coin, Date + Size | Content layout |
| `R Text` | Boolean | True | True, False | Right-side text |
| `Separator` | Boolean | True | True, False | Bullet separator between L and R text |
| `L text` | Text | "Wed, 24 Jan" | Any string | Left label |
| `R text` | Text | "Size M" | Any string | Right label |
| `body` | Text | "Body" | Any string | Body text (used in Body+pop-coin type) |

---

## 3h. .Shop List Item *(Internal — do not use directly)*

**Component name:** `.Shop list item`
**Node ID:** `20804:226775`

> ⚠️ **Internal component.** This is the building block used inside `Shop list- stack`. Do not place it directly in screens — use the stack instead. Properties are documented here so nested instances inside `Shop list- stack` can be correctly configured.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Image size` | Variant | 4:5 | 1:1, 4:5 | Product image aspect ratio — 4:5 for portrait, 1:1 for square |
| `Is disabled` | Variant | False | True, False | Disabled state — item cannot be interacted with |
| `Title above` | Variant | True | True, False | Shows a section title label above the list item row |
| `R-slot` | Boolean | False | True, False | Right slot for an action component (e.g. quantity stepper, remove button) |
| `Title` | Text | "Title" | Any string | Item title text |

### Nested Component — Content Item

The `Content item` local component is embedded inside `.Shop list item` and is configurable from the parent context:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `type` | Variant | Date + Size | Date + Size, Body+pop-coin | Content layout — date+size for standard order rows, body+pop-coin for reward content |
| `R Text` | Boolean | True | True, False | Right-side text |
| `Separator` | Boolean | True | True, False | Bullet (·) separator between left and right text |
| `L text` | Text | "Wed, 24 Jan" | Any string | Left label — e.g. order date, category |
| `R text` | Text | "Size M" | Any string | Right label — e.g. size, variant |
| `body` | Text | "Body" | Any string | Body text — used when `type=Body+pop-coin` |

### Nested Component — Footer Item

The `Footer item` local component is embedded inside `.Shop list item` and controls the footer row layout:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `type` | Variant | Amount stepper | Label, Amount stepper, Details + Amount | Footer row layout — stepper for quantity control, label for summary text, details+amount for price breakdown |

---

## 3i. ODP-list

**Component name:** `ODP-list`
**Figma page:** `↪️ ❖ List ✅`
**Node ID:** `21894:26290`
**Use when:** A single row in an ODP (Offer Detail Panel) list — always use inside `ODP list- stack`, not standalone.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Badge` | Boolean | True | True, False | Shows a badge on the list row |

---

## 3j. OLP-list

**Component name:** `OLP-list`
**Figma page:** `↪️ ❖ List ✅`
**Node ID:** `21749:41120`
**Use when:** A single row in an OLP (Offer Listing Panel) list — displays an offer title, left and right labels, and an optional Pop coin indicator.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Pop-coin` | Boolean | True | True, False | Shows the Pop coin indicator on the row |
| `R overflow` | Boolean | True | True, False | Right overflow fade for long content |
| `Title` | Text | "Title" | Any string | Primary title of the offer row |
| `L - label` | Text | "Label" | Any string | Left label — e.g. offer name, category |
| `R - Label` | Text | "Label" | Any string | Right label — e.g. amount, expiry |

---

## 3k. ODP List Stack

**Component name:** `ODP list- stack`
**Figma page:** `↪️ ❖ List ✅`
**Node ID:** `22246:190056`
**Use when:** Displaying a vertical stack of ODP list rows — used as the slot content inside an ODP Card.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3, 4, Plenty | Number of ODP-list rows in the stack |
| `Divider` | Boolean | True | True, False | Shows dividers between rows |

> `Plenty` renders a scrollable overflow stack — use when the row count is dynamic or unknown at design time.

---

## Rules — List

- Always use `List - Stack` for groups of 2 or more list items — never manually stack individual `List item` instances.
- Set `Separator=False` on the **last row** of any list — never show a trailing divider after the final item.
- `Type=People` vs `Type=Brands` controls which avatar is shown — match the content type.
- Use `Status list item` when the row must communicate a progress state — not a plain `List item` with a manual icon.
- `OLD-list` is deprecated — do not use it in any new designs.
- `.Shop list item` is an internal component — always use `Shop list- stack` for shop cart/order lists.
- `OLP-list` is a standalone row component — place it directly or inside a custom stack as needed.
- `ODP-list` is the unit row for ODP lists — always use `ODP list- stack` to compose them, never place `ODP-list` individually.

---
---

# 4. Table — POP Design System

Tables display structured data in rows and columns. The system covers both vertical (stacked rows) and horizontal (side-by-side rows) layouts.

---

## 4a. Table Item *(Cell)*

**Component name:** `Table item`
**Figma page:** `↪️ ❖ Table ✅`
**Node ID:** `3122:14680`
**Use when:** A single table cell — always used inside a `Table row`, never placed standalone.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Small | Small, Medium, Large, X-large | Cell size / text size |
| `Align` | Variant | Left | Left, Center, Right | Text alignment within the cell |
| `Title above` | Variant | False | True, False | Floating label above the cell value |
| `Amount` | Variant | False | False, True | Amount/currency formatting treatment |
| `Title` | Boolean | True | True, False | Shows title text |
| `Body` | Boolean | True | True, False | Shows body text |
| `L-icon - Title` | Boolean | False | True, False | Icon left of title |
| `R-icon - Title` | Boolean | False | True, False | Icon right of title |
| `L-icon - Body` | Boolean | False | True, False | Icon left of body |
| `R-icon - Body` | Boolean | False | True, False | Icon right of body |
| `L-overflow` | Boolean | False | True, False | Left overflow fade |
| `R-overflow` | Boolean | False | True, False | Right overflow fade |
| `Title text` | Text | "Title" | Any string | Cell title/label |
| `Body text` | Text | "Body" | Any string | Cell value |

---

## 4b. Table Row

**Component name:** `Table row`
**Figma page:** `↪️ ❖ Table ✅`
**Node ID:** `3122:15001`
**Use when:** A single row of table cells — always used inside `Table - Stack`.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Column` | Variant | 1 | 1, 2, 3 | Number of columns in the row |
| `Size` | Variant | Small | Small, Large, X-large | Row height |
| `Justify` | Variant | Start | Start, Centre, End, Space between, Space around, Towards centre | Column distribution |
| `Divider` | Boolean | True | True, False | Bottom divider |
| `R-slot` | Boolean | False | True, False | Right slot for an action component |
| `L-slot` | Boolean | False | True, False | Left slot |

### Nested Component — Vertical Divider

A `Vertical divider` is embedded between columns in multi-column `Table row` variants. Its properties are configurable from the row context:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Padded | Padded, Full height | Padded leaves breathing room at top and bottom; Full height spans the entire row height |
| `Colour` | Variant | Tertiary | Tertiary, Secondary | Divider line colour — match the surrounding table's divider colour |

> The vertical divider only appears when `Column=2` or `Column=3`. It separates cells within the row — distinct from the `Divider` boolean which controls the bottom row divider.

---

## 4c. Table Stack (Vertical)

**Component name:** `Table - Stack`
**Figma page:** `↪️ ❖ Table ✅`
**Node ID:** `5945:14215`
**Use when:** A vertical stack of table rows — the standard table pattern.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3, 4, 5, Plenty |
| `Divider colour` | Variant | Tertiary | Tertiary, Secondary |
| `Size` | Variant | Large | Small, Large, X-large |
| `Divider` | Boolean | True | True, False |

---

## 4d. Table Row — Horizontal

**Component name:** `Table row - Horizontal`
**Figma page:** `↪️ ❖ Table ✅`
**Node ID:** `9536:54270`
**Use when:** A horizontal row in a transposed table layout — used inside `Table - Horizontal - Stack`.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Type` | Variant | Small | Small, Medium, large, X large |
| `Divider` | Boolean | True | True, False |

---

## 4e. Table Horizontal Stack

**Component name:** `Table - Horizontal - Stack`
**Figma page:** `↪️ ❖ Table ✅`
**Node ID:** `7903:4245`
**Use when:** A horizontally-oriented table — columns become rows, useful for comparing items side by side (e.g. plan comparison, feature comparison).

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3, 4, 5, 6, 7, Plenty | Number of rows |
| `Size` | Variant | Med | Med, Large, X-large | Row size |
| `Divider colour` | Variant | Tertiary | Tertiary, Secondary | Divider colour |
| `Divider` | Boolean | True | True, False | Row dividers |
| `Summary - Above` | Boolean | False | True, False | Summary row pinned above the table |
| `Summary - Below` | Boolean | False | True, False | Summary row pinned below the table (e.g. totals row) |

---

## Rules — Table

- Always use `Table - Stack` or `Table - Horizontal - Stack` — never manually arrange individual `Table row` instances.
- Set `Divider=False` on the **last row** — no trailing divider after the final row.
- Use `Amount=True` on `Table item` for all monetary values — it applies correct alignment and formatting.
- Use `Table - Horizontal - Stack` for comparison layouts (2+ columns of items side by side).
- `Summary - Above` / `Summary - Below` on Horizontal Stack adds a pinned summary row — use for totals, headers, or column labels.

---
---

# 5. Pagination — POP Design System

Pagination components indicate position within a set of items — carousel dots, story progress bars, step indicators, and continuous progress bars.

---

## Component Overview

| Component | Use case |
|---|---|
| `Pagination unit` | Single dot/indicator for carousels |
| `Pagination - Discrete` | Dot-based pagination for carousels (up to 7 items) |
| `Pagination - Continuous` | Single continuous progress bar |
| `Pagination - Stack` | Aligned group of pagination indicators |
| `Pagination - Stories - Unit` | Single story progress bar segment |
| `Pagination - Stories` | Full story progress bar (1–5 stories) |
| `.Pagination - Step - Unit` | *(Internal)* Single step progress segment |
| `Pagination - Steps` | Multi-step progress bar (2–3 steps) |

---

## 5a. Pagination Unit *(Single dot)*

**Component name:** `Pagination unit`
**Figma page:** `↪️ ❖ Pagination ✅`
**Node ID:** `2711:2476`
**Use when:** Inside `Pagination - Discrete` — not placed standalone.

### Properties

| Property | Type | Options |
|---|---|---|
| `State` | Variant | Not selected, Selected, Selected - Playing, Overflow |

| State | Description |
|---|---|
| Not selected | Inactive dot |
| Selected | Active/current dot |
| Selected - Playing | Active dot with playback animation |
| Overflow | Condensed overflow dot (when > 6 items) |

---

## 5b. Pagination — Discrete *(Dot carousel pagination)*

**Component name:** `Pagination - Discrete`
**Figma page:** `↪️ ❖ Pagination ✅`
**Node ID:** `5044:11763`
**Use when:** Carousel or swipeable content with a known number of items (up to 7). Shows dots below the carousel.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | Less than 6 | Less than 6, Equal to 6, More than 6 | Determines dot condensation behaviour |
| `Selected` | Variant | 1 | 1, 2, 3, 4, 5, 6, 7 | Currently active slide |
| `With playback` | Variant | False | False, True | Shows a playback/auto-scroll animation on the active dot |

| Count | Behaviour |
|---|---|
| Less than 6 | All dots shown at full size |
| Equal to 6 | All 6 dots shown |
| More than 6 | Overflow dots condensed — only nearby dots shown at full size |

---

## 5c. Pagination — Continuous *(Progress bar)*

**Component name:** `Pagination - Continuous`
**Figma page:** `↪️ ❖ Pagination ✅`
**Node ID:** `5057:14525`
**Use when:** A single linear progress bar — for onboarding flows, loading states, or single-track progress.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Progress` | Variant | 0% | 0%, 25%, 50%, 75%, 100% |

---

## 5d. Pagination Stack

**Component name:** `Pagination - Stack`
**Figma page:** `↪️ ❖ Pagination ✅`
**Node ID:** `5609:9916`
**Use when:** Positioning a pagination group (top or bottom alignment).

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Align` | Variant | Bottom | Bottom, Top |

### Nested Component — Swappable Pagination Instance

`Pagination - Stack` is a positioning wrapper. Its inner pagination instance defaults to `Pagination - Discrete` but can be swapped to `Pagination - Stories` or `Pagination - Steps` depending on the content context. Configure the nested instance properties after swapping.

---

#### Option 1 — Pagination - Discrete *(default)*

Use for carousels and swipeable content with a known number of slides.

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | Less than 6 | Less than 6, Equal to 6, More than 6 | Controls dot condensation behaviour |
| `Selected` | Variant | 1 | 1, 2, 3, 4, 5, 6, 7 | Currently active slide |
| `With playback` | Variant | False | False, True | Playback animation on the active dot |

> Always set `Selected` to the actual current slide index — never leave it at 1 in production.

---

#### Option 2 — Pagination - Stories

Use for story-style content (Instagram-style top progress bar) with 1–5 segments.

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 5 | 1, 2, 3, 4, 5 | Total number of story segments |
| `Playing` | Variant | 1 | 1, 2, 3, 4, 5, End | Which segment is currently playing |
| `Skip` | Boolean | True | True, False | Shows a skip affordance |

---

#### Option 3 — Pagination - Steps

Use for multi-step flows (e.g. checkout steps, onboarding stages) with 2–3 steps.

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 3 | 2, 3 | Total number of steps |
| `Playing` | Variant | 1 | 1, 2, 3 | Current active step |
| `State` | Variant | 0% | 0%, 30%, 60%, 90%, 100% | Fill progress of the current step |

---

## 5e. Pagination — Stories Unit *(Single story bar segment)*

**Component name:** `Pagination - Stories - Unit`
**Figma page:** `↪️ ❖ Pagination ✅`
**Node ID:** `6306:40779`
**Use when:** Inside `Pagination - Stories` — not placed standalone.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `State` | Variant | Not started | Playing, Not started, Completed |

| State | Description |
|---|---|
| Playing | Currently active segment — animates to fill |
| Not started | Upcoming segment — empty |
| Completed | Finished segment — fully filled |

---

## 5f. Pagination — Stories *(Full story progress bar)*

**Component name:** `Pagination - Stories`
**Figma page:** `↪️ ❖ Pagination ✅`
**Node ID:** `6306:40798`
**Use when:** Story-style content (Instagram-style top progress bar) with 1–5 segments.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 5 | 1, 2, 3, 4, 5 | Total number of story segments |
| `Playing` | Variant | 1 | 1, 2, 3, 4, 5, End | Which segment is currently playing |
| `Skip` | Boolean | True | True, False | Shows a skip affordance |

---

## 5g. .Pagination — Step Unit *(Internal)*

**Component name:** `.Pagination - Step - Unit`
**Node ID:** `14808:30525`

> ⚠️ **Internal component.** Building block for `Pagination - Steps`. Do not place directly.

States: `0%`, `30%`, `60%`, `90%`, `100%` (fill progress of a single step)

---

## 5h. Pagination — Steps *(Multi-step progress)*

**Component name:** `Pagination - Steps`
**Figma page:** `↪️ ❖ Pagination ✅`
**Node ID:** `14808:30535`
**Use when:** A multi-step flow progress indicator (e.g. checkout steps, onboarding steps) with 2–3 steps.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 3 | 2, 3 | Total number of steps |
| `Playing` | Variant | 1 | 1, 2, 3 | Current active step |
| `State` | Variant | 0% | 0%, 30%, 60%, 90%, 100% | Fill progress of the current step |

---

## Pagination Selection Guide

| Context | Component |
|---|---|
| Carousel with ≤ 7 slides | `Pagination - Discrete` |
| Single linear progress | `Pagination - Continuous` |
| Story-style top bar (1–5) | `Pagination - Stories` |
| Multi-step flow (2–3 steps) | `Pagination - Steps` |

## Rules — Pagination

- Never place `Pagination unit` or `Pagination - Stories - Unit` directly — always use their parent stack/organism.
- `.Pagination - Step - Unit` is internal — always use `Pagination - Steps`.
- `Pagination - Discrete` with `Count=More than 6` automatically handles overflow condensation — do not manually add more than 7 dots.
- `Pagination - Steps` `State` controls the fill animation of the **current** step — always set it in context with `Playing`.

---
---

# 6. Badge — POP Design System

Badges are compact labels used to convey status, counts, categories, or highlights. They come in multiple semantic colour types with optional background fill and glow effects.

---

## 6a. Badge

**Component name:** `Badge`
**Figma page:** `↪️ ❖ Badge ✅`
**Node ID:** `5529:3928`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Orange | Orange, Grey, Green, Red, Yellow, Primary, White | Semantic colour |
| `BG` | Variant | True | True, False | Filled background vs text-only |
| `Glow` | Variant | False | False, True | Glow effect around the badge |
| `L-Icon` | Boolean | False | True, False | Leading icon inside the badge |
| `R-Icon` | Boolean | False | True, False | Trailing icon inside the badge |
| `Title text` | Text | "Label" | Any string | Badge label content |

### Type Semantics

| Type | Colour | Use case |
|---|---|---|
| **Orange** | Brand orange | Promotional, brand highlights, POP-specific callouts |
| **Grey** | Neutral grey | Default state, unrated, neutral labels |
| **Green** | Success green | Positive status, verified, available |
| **Red** | Destructive red | Error state, urgent, out of stock |
| **Yellow** | Warning yellow | Caution, expiring, pending |
| **Primary** | Primary brand | Primary CTA badges, featured labels |
| **White** | White | Invert — on dark/media surfaces |

### BG + Glow combinations

| BG | Glow | Visual |
|---|---|---|
| True | False | Filled badge — standard |
| True | True | Filled badge with glow — for prominent CTAs |
| False | False | Text-only badge — lightweight label |

> `Glow=True` is only available for Orange, Green, Red, and Primary types. Use it sparingly — maximum one glowing badge per screen zone.

---

## 6b. Content Card

**Component name:** `ContentCard`
**Figma page:** `↪️ ❖ Badge ✅`
**Node ID:** `14928:32534`
**Use when:** Displaying a content item with a current or upcoming state — typically for offers, rewards, or content tiles that have a time-based status.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Property 1` | Variant | Current | Current, Upcoming | Whether the content is active now or coming soon |
| `POPcoin` | Boolean | True | True, False | Shows a POP coin indicator |
| `L-Icon-Title` | Boolean | False | True, False | Icon left of title |
| `R-Icon-Title` | Boolean | False | True, False | Icon right of title |
| `L-Icon-Body` | Boolean | False | True, False | Icon left of body |
| `R-Icon-Body` | Boolean | False | True, False | Icon right of body |

---

## 6c. Content Card — Shop

**Component name:** `Content Card - shop`
**Figma page:** `↪️ ❖ Badge ✅`
**Node ID:** `21766:2850`
**Use when:** A shop-specific content card showing amount and date — used in transaction/order history contexts.

### Properties

| Property | Type | Default | Notes |
|---|---|---|---|
| `Amount` | Text | "₹299" | Transaction or item amount |
| `Date` | Text | "12 Jan" | Date of the transaction |

---

## Rules — Badge

- Always set `Title text` — never leave "Label" as production copy.
- Use `Glow=True` only for high-emphasis moments (e.g. active CTA badge, limited-time offer) — not for informational badges.
- `BG=False` (text-only) is for low-emphasis labels where a filled pill would feel heavy.
- Do not use `Glow=True` with `BG=False` — glow requires a filled background to work correctly.

---
---

# 7. Vertical Button — POP Design System

Vertical buttons are icon-above-label controls used for quick action grids (e.g. home screen action rows, shortcut grids). They differ from horizontal buttons — the icon sits above the text, not beside it.

---

## 7a. Vertical Button (Unit)

**Component name:** `Vertical button`
**Figma page:** `↪️ ❖ Vertical button ✅`
**Node ID:** `4858:1047`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Med | Button size |
| `Type` | Variant | Icon | Icon, People, Brand, Illustration | What appears above the label |
| `Text lines` | Variant | 1 | 1, 2 | Number of label lines |
| `Is disabled` | Variant | False | False, True | Disabled state |
| `Title` | Boolean | True | True, False | Shows the label text |
| `L-overflow - Title` | Boolean | False | True, False | Left overflow fade on the label |
| `R-overflow - Title` | Boolean | True | True, False | Right overflow fade on the label |
| `Title text` | Text | (varies by lines) | Any string | The button label |

### Type

| Type | Description | Example |
|---|---|---|
| **Icon** | System icon above the label | Send Money, Recharge, Pay bills |
| **People** | People avatar above the label | Frequent contacts, recent payees |
| **Brand** | Brand logo above the label | Merchant shortcuts, app quick-links |
| **Illustration** | Illustration above the label | Feature callouts, onboarding shortcuts |

---

## 7b. Vertical Button Stack

**Component name:** `Vertical button - Stack`
**Figma page:** `↪️ ❖ Vertical button ✅`
**Node ID:** `5835:17437`
**Use when:** A grid of vertical buttons — the standard pattern for home screen action rows.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Rows` | Variant | 1 | 1, 2, 3 | Number of button rows |
| `Count` | Variant | 4 | 2, 3, 4, 8, 12 | Number of buttons per row |
| `Size` | Variant | Large | Large | — |
| `Text lines` | Variant | 1 | 1, 2 | Label line count |
| `Header` | Boolean | True | True, False | Section header above the grid |
| `Pagination` | Boolean | True | True, False | Pagination dots below the grid (for swipeable rows) |
| `CTA - Header` | Boolean | True | True, False | CTA in the header (e.g. "See all") |
| `R-overflow - Header` | Boolean | False | True, False | Header right overflow fade |
| `L-icon - Title` | Boolean | False | True, False | Icon left of header title |
| `R-icon - Title` | Boolean | False | True, False | Icon right of header title |
| `R-overflow - Buttons` | Boolean | False | True, False | Right overflow on the button row |
| `L-overflow - Buttons` | Boolean | False | True, False | Left overflow on the button row |
| `Offer - Header` | Boolean | False | True, False | Offer label in the header |
| `Title text` | Text | "Title" | Any string | Header title |

---

## 7c. .Benefit Callout Units *(Internal)*

**Component name:** `.Benefit callout - Units`
**Node ID:** `10750:48313`

> ⚠️ **Internal component** — hidden from publishing. Building block used inside `Benefit callout - Stack`. Do not place directly in screens. Properties are documented here because they are configurable as nested instances from within the stack.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Icon | Icon, Illustration | Visual above the label — icon for functional benefits, illustration for feature callouts |
| `Size` | Variant | Large | Large | Fixed — only Large is available |
| `Text lines` | Variant | 2 | 2 | Fixed — always 2 lines |
| `Is disabled` | Variant | False | False, True | Disabled state |
| `Title` | Boolean | True | True, False | Shows the label text below the icon/illustration |
| `Title text` | Text | "2 or more line label goes here" | Any string | Benefit label copy |

| Type | Description |
|---|---|
| **Icon** | System icon above the label — for functional or action-based benefits |
| **Illustration** | Illustration above the label — for feature callouts, onboarding, and promotional benefits |

---

## 7d. Benefit Callout Stack

**Component name:** `Benefit callout - Stack`
**Figma page:** `↪️ ❖ Vertical button ✅`
**Node ID:** `10760:28505`
**Use when:** Displaying 2–3 benefit highlights side by side — used in feature intro screens, onboarding, and promotional sections.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 2 | 2, 3 | Number of benefit items |
| `Divider` | Boolean | True | True, False | Vertical dividers between items |

### Nested Component — .Benefit Callout Units

Each benefit slot inside the stack is a `.Benefit callout - Units` instance. Configure each unit's properties directly from the stack context:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Icon | Icon, Illustration | Visual style for this benefit unit |
| `Size` | Variant | Large | Large | Fixed — do not change |
| `Text lines` | Variant | 2 | 2 | Fixed — do not change |
| `Is disabled` | Variant | False | False, True | Disabled state for this unit |
| `Title` | Boolean | True | True, False | Shows the label below the icon/illustration |
| `Title text` | Text | "2 line label goes here" | Any string | Benefit label — always replace with real copy |

> With `Count=2` the stack contains 2 units; `Count=3` adds a third. Configure each unit independently — `Type` and `Title text` will typically differ across units.

### Nested Component — Text Divider

A `Text Divider` is embedded at the bottom of `Benefit callout - Stack`. Its properties are configurable from the stack context:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Medium, Small, Size4 | Divider text size and weight |
| `Fancy divider` | Variant | True | False, True | Decorative gradient divider — `True` by default in this context |

---

## 7e. Text Divider

**Component name:** `Text Divider`
**Figma page:** `↪️ ❖ Vertical button ✅`
**Node ID:** `14558:149229`
**Use when:** A horizontal divider with a centre text label (e.g. "or", "also pay with") — used between sections or between payment options.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Medium, Small, Size4 | Controls text size and divider weight |
| `Fancy divider` | Variant | True | False, True | Decorative gradient divider instead of a plain line |

---

## Rules — Vertical Button

- Always use `Vertical button - Stack` for grids — never place individual `Vertical button` units manually in a row.
- `Count=4` with `Rows=1` is the standard home screen action row — 4 icons across.
- `Count=8` or `Count=12` is used for expanded grids (e.g. "All services" screens).
- `Pagination=True` + `Rows > 1` signals a swipeable multi-row grid — wire the pagination dots to the swipe gesture in code.
- `Text Divider` is only for separating distinct content zones — not as a decorative element inside a form or list.

---
---

# 8. Button — POP Design System

Buttons are the primary interactive controls. The system covers standard buttons, slide buttons, link buttons, icon buttons, stacks, and specialised compound variants.

---

## 8a. Button (Primary)

**Component name:** `Button`
**Figma page:** `↪️ ❖ Button ✅`
**Node ID:** `1248:823`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Med, Small, X-small | Button height and padding |
| `Type` | Variant | Primary | Primary, Secondary, Tertiary, Brand - Primary, Flat - White, Ghost + R Icon, Ghost + L Icon, Brand Ghost + R Icon, Brand Ghost + L Icon | Visual style |
| `State` | Variant | Active | Active, Disabled, Loading, Loading - Success, Loading - Failed, Destructive | Interaction state |
| `Icon only` | Variant | False | False, True | Renders the button as an icon-only circular button |
| `R-icon` | Boolean | False | True, False | Trailing icon |
| `L-Icon` | Boolean | False | True, False | Leading icon |
| `Title` | Boolean | False | True, False | Title/subtitle above the button label |
| `Text` | Text | "Label" | Any string | Button label text |

### Type Guide

| Type | Use case |
|---|---|
| **Primary** | Main CTA — one per screen, highest emphasis |
| **Secondary** | Supporting action — alongside a Primary |
| **Tertiary** | Low-emphasis action — destructive confirmations, subtle options |
| **Brand - Primary** | Brand-coloured CTA — for brand-specific flows |
| **Flat - White** | White button on dark/image surfaces |
| **Ghost + R/L Icon** | Outlined ghost button with icon — medium emphasis |
| **Brand Ghost + R/L Icon** | Brand-outlined ghost button with icon |

### State Guide

| State | Description |
|---|---|
| Active | Default interactive state |
| Disabled | Non-interactive — greyed out |
| Loading | Spinner animation — async action in progress |
| Loading - Success | Spinner resolved to success |
| Loading - Failed | Spinner resolved to failure |
| Destructive | Red-coloured — for irreversible or dangerous actions |

---

## 8b. Slide Button

**Component name:** `Slide Button`
**Figma page:** `↪️ ❖ Button ✅`
**Node ID:** `22439:6418`
**Use when:** A high-commitment action that requires a deliberate gesture to confirm — e.g. "Slide to pay". Prevents accidental taps on critical payment actions.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Active | Active, Loading, Disabled | — |
| `L icon` | Boolean | False | True, False | Leading icon |
| `R icon` | Boolean | False | True, False | Trailing icon |
| `Text` | Text | "Slide to pay ₹1" | Any string | Default label (shown before slide) |
| `Loading text` | Text | "Paying ₹1" | Any string | Label shown during loading after slide |

---

## 8c. Button Stack

**Component name:** `Button - Stack`
**Figma page:** `↪️ ❖ Button ✅`
**Node ID:** `5241:1913`
**Use when:** 1–3 buttons stacked vertically or horizontally — the standard CTA area at the bottom of a screen or sheet.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3 | Number of buttons |
| `Axis` | Variant | Vertical | Vertical, Horizontal | Stack direction |
| `Padding` | Variant | True | True, False | Outer padding on the stack container |
| `Checkbox` | Boolean | False | True, False | Consent checkbox above the CTA |
| `Badge - Below` | Boolean | False | True, False | Badge below the CTA (e.g. "No hidden charges") |
| `Badge - Above` | Boolean | False | True, False | Badge above the CTA |
| `Footer images` | Boolean | False | True, False | Certification/trust images below the CTA |
| `Offer highlight - Above` | Boolean | False | True, False | Offer label above the CTA |
| `Offer highlight - Below` | Boolean | False | True, False | Offer label below the CTA |
| `Table row` | Boolean | False | True, False | Amount/detail table row above the CTA |

### Nested Component — Button

Each slot in `Button - Stack` contains a `Button` instance. All button properties are configurable directly from the stack context:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Primary | Primary, Brand - Primary | Visual style — constrained to Primary and Brand - Primary only within this stack |
| `State` | Variant | Active | Active, Disabled, Loading, Loading - Success, Loading - Failed, Destructive | Interaction state |
| `L-Icon` | Boolean | False | True, False | Leading icon |
| `R-icon` | Boolean | False | True, False | Trailing icon |
| `Title` | Boolean | False | True, False | Title/subtitle above the button label |
| `Text` | Text | "Label" | Any string | Button label — always replace with real copy |

> ⛔ Do not change `Size` from the stack context — button size is controlled by the stack and must remain consistent. `Icon only` is not configurable from `Button - Stack`.
> With `Count=2` or `Count=3`, configure each button independently — use `Type=Primary` for the primary action and `Type=Brand - Primary` for brand-specific flows.

---

## 8d. Button + Payment Mode

**Component name:** `Button + Payment mode`
**Figma page:** `↪️ ❖ Button ✅`
**Node ID:** `6973:23507`
**Use when:** A CTA area that includes a primary payment button and optionally a secondary payment method selector.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Primary CTA` | Variant | 1 | 1, 2 | Number of primary CTA buttons |
| `Secondary CTA` | Variant | 1 | 1, 2 | Number of secondary CTA buttons |
| `Axis` | Variant | Vertical | Horizontal, Vertical | Stack direction |
| `Tertiary CTA` | Variant | Button | Button, Badge, None, Secondary CTA | Tertiary action treatment |
| `Offer highlight` | Boolean | False | False, True | Offer label on the CTA |
| `Footer images` | Boolean | False | False, True | Trust/certification images |

### Nested Component — Primary Button

The primary `Button` instance (Large) is the main payment CTA inside this component:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Primary | Primary, Brand - Primary | Constrained to Primary and Brand - Primary only |
| `State` | Variant | Active | Active, Disabled, Loading, Loading - Success, Loading - Failed, Destructive | Interaction state |
| `L-Icon` | Boolean | False | True, False | Leading icon |
| `R-icon` | Boolean | False | True, False | Trailing icon |
| `Title` | Boolean | False | True, False | Title/subtitle above the button label |
| `Text` | Text | "Label" | Any string | Button label — always replace with real copy |

### Nested Component — Secondary Button

The secondary `Button` instance (X-small) is the supporting action (e.g. "Check limit", "View details"):

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Ghost + R Icon | Ghost + R Icon, Ghost + L Icon | Constrained to Ghost types for secondary hierarchy |
| `State` | Variant | Active | Active, Disabled | Interaction state |
| `L-Icon` | Boolean | False | True, False | Leading icon |
| `R-icon` | Boolean | False | True, False | Trailing icon |
| `Title` | Boolean | False | True, False | Title/subtitle above the button label |
| `Text` | Text | "Check limit" | Any string | Secondary action label — always replace with real copy |

> ⛔ Do not change `Size` on either button — sizes are set by the component and must remain consistent. `Icon only` is not configurable from this context.

---

## 8e. Button + Amount

**Component name:** `Button + Amount`
**Figma page:** `↪️ ❖ Button ✅`
**Node ID:** `6973:16474`
**Use when:** A CTA that shows the payment amount alongside the button — used in cart, checkout, and payment confirmation screens.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | X-large | X-large, Large, Small, X-small, Medium | — |
| `Button fill` | Variant | False | True, False | Fills the button to full width |
| `With POPcoins` | Variant | True | True, False | Shows POP coin reward on the amount |
| `Primary action` | Variant | Button | Button, Chevron | CTA style — full button or a chevron tap |
| `Original price` | Boolean | True | True, False | Shows struck-through original price |

### Nested Component — Button

The `Button` instance inside `Button + Amount` is fully configurable. Unlike other button contexts, `Type` has no constraints here — choose based on the screen's visual hierarchy:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Primary | Primary, Secondary, Tertiary, Brand - Primary, Flat - White, Ghost + R Icon, Ghost + L Icon, Brand Ghost + R Icon, Brand Ghost + L Icon | Free to select — choose based on context |
| `State` | Variant | Active | Active, Disabled, Loading, Loading - Success, Loading - Failed, Destructive | Interaction state |
| `L-Icon` | Boolean | False | True, False | Leading icon |
| `R-icon` | Boolean | False | True, False | Trailing icon |
| `Title` | Boolean | False | True, False | Title/subtitle above the button label |
| `Text` | Text | "Label" | Any string | Button label — always replace with real copy |

> ⛔ Do not change `Size` — it is controlled by the component's `Size` property. `Icon only` is not configurable from this context.

---

## 8f. Button + Slot

**Component name:** `Button + Slot`
**Figma page:** `↪️ ❖ Button ✅`
**Node ID:** `7907:13145`
**Use when:** A button with a free slot — for placing custom content (payment logo, trust badge, or icon) alongside the CTA.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Type` | Variant | Large | X-large, Large, Med, Small, X-small |
| `Button fill` | Variant | False | True, False |
| `POPcoin value` | Variant | True | True, False |

### Nested Component — Button

The `Button` instance inside `Button + Slot` is configurable from this context:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Primary | Primary, Secondary, Tertiary, Brand - Primary, Flat - White, Ghost + R Icon, Ghost + L Icon, Brand Ghost + R Icon, Brand Ghost + L Icon | Free to select — choose based on context |
| `State` | Variant | Active | Active, Disabled, Loading, Loading - Success, Loading - Failed, Destructive | Interaction state |
| `L-Icon` | Boolean | False | True, False | Leading icon |
| `R-icon` | Boolean | False | True, False | Trailing icon |
| `Title` | Boolean | False | True, False | Title/subtitle above the button label |
| `Text` | Text | "Label" | Any string | Button label — always replace with real copy |

> ⛔ Do not change `Size` — it is driven by the `Type` property of `Button + Slot` and will adjust automatically. `Icon only` is not configurable from this context.

---

## 8g. Button Stack Horizontal

**Component name:** `Button Stack Horizontal`
**Figma page:** `↪️ ❖ Button ✅`
**Node ID:** `10490:121953`
**Use when:** A horizontal row of 2–4+ compact buttons (e.g. filter pills that act as buttons, quick-action rows).

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Count` | Variant | Plenty | 2, 3, 4, Plenty |
| `Fill` | Variant | False | True, False |

### Nested Component — Button

Each button slot in `Button Stack Horizontal` contains a `Button` instance. Configure each independently:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Secondary | Primary, Secondary, Tertiary, Brand - Primary, Flat - White, Ghost + R Icon, Ghost + L Icon, Brand Ghost + R Icon, Brand Ghost + L Icon | Free to select — choose based on context |
| `State` | Variant | Active | Active, Disabled, Loading, Loading - Success, Loading - Failed, Destructive | Interaction state |
| `L-Icon` | Boolean | False | True, False | Leading icon |
| `R-icon` | Boolean | False | True, False | Trailing icon |
| `Title` | Boolean | False | True, False | Title/subtitle above the button label |
| `Text` | Text | "Label" | Any string | Button label — always replace with real copy |

> ⛔ Do not change `Size` — it is locked to `Med` across all buttons in the horizontal stack. `Icon only` is not configurable from this context.

---

## 8h. Cart FAB

**Component name:** `Cart FAB`
**Figma page:** `↪️ ❖ Button ✅`
**Node ID:** `14286:52946`
**Use when:** Floating cart button overlaying a listing or shop screen — shows items in cart with a quick-add affordance.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3 |
| `Chevron` | Variant | False | True, False |

---

## 8i. Link Button

**Component name:** `Link button`
**Figma page:** `↪️ ❖ Button ✅`
**Node ID:** `22639:192061`
**Use when:** An inline text link action — lower emphasis than a `Button`, used for secondary or contextual actions within copy.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Size` | Variant | Large | Large, Medium, Small, X Small |
| `L - Icon` | Boolean | False | False, True |
| `R - Icon` | Boolean | False | False, True |

---

## 8j. Icon Button *(via Button — Icon only)*

Use the `Button` component with `Icon only=True` for icon-only circular button actions. This is not a separate component — it is a variant of `Button`.

---

## 8k. Icon Button

> ⚠️ **Local component** — `Icon button` is scoped to `Floating Action Rail` only. Do not place it standalone or use it outside of the rail context.

**Component name:** `Icon button`
**Figma page:** `↪️ ❖ Button ✅`
**Node ID:** `16886:5171`
**Use when:** Inside `Floating Action Rail` only — provides a compact icon-only tap target for each action slot in the rail.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Default | Default, Active, Disabled | Interaction state |
| `Size` | Variant | Small | Small, Medium | Button size |

| State | Description |
|---|---|
| **Default** | Resting state — no interaction |
| **Active** | Currently selected or highlighted |
| **Disabled** | Non-interactive |

---

## 8l. Floating Action Rail

**Component name:** `Floating Action Rail`
**Figma page:** `↪️ ❖ Button ✅`
**Node ID:** `16918:14131`
**Use when:** A floating row of 2–5 small action icons at the edge of the screen — used for contextual quick actions overlaying content (e.g. share, edit, bookmark).

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `count` | Variant | 2 | 2, 3, 4, 5 |
| `size` | Variant | Small | Small, Medium |

### Nested Component — Icon Button

Each action slot in `Floating Action Rail` contains an `Icon button` instance. Its properties are configurable from the rail context:

| Nested Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Default | Default, Active, Disabled | Interaction state for this icon button |
| `Size` | Variant | Medium | Small, Medium | Size of the icon button — driven by the rail's `size` property; do not change independently |

> ⛔ Do not change `Size` independently — it is controlled by the `size` property of `Floating Action Rail` and must stay consistent across all slots.

---

## Button Selection Guide

| Context | Component |
|---|---|
| Primary CTA at bottom of screen | `Button` (Primary, Large) or `Button - Stack` |
| High-commitment payment action | `Slide Button` |
| Amount + pay CTA | `Button + Amount` |
| Multiple payment modes | `Button + Payment mode` |
| Cart overlay on shop screen | `Cart FAB` |
| Inline text link | `Link button` |
| Icon-only circular action | `Button` with `Icon only=True` |
| Floating contextual actions | `Floating Action Rail` |

## Rules — Button

- **One Primary button per screen** — never show two Primary buttons simultaneously.
- Always set `Text` — never leave "Label" as production copy.
- Use `State=Disabled` when the action cannot proceed — never visually hide a button to block an action; disable it instead.
- Use `Slide Button` only for irreversible, high-value actions (e.g. payment, deletion) — not for regular navigation.
- `Button - Stack` with `Axis=Vertical` is the standard bottom CTA pattern — Primary on top, Secondary below.
- `Icon only=True` on `Button` is the icon button — do not create a custom icon button from scratch.
- `Cart FAB` always floats above content — ensure it has correct z-index in code and does not block critical UI below.

---
---

# 9. Timeline — POP Design System

Timeline components display sequential steps, progress through a flow, or a history of events. Two orientations are available: Vertical (transactional steps) and Horizontal (compact step progress).

---

## Component Overview

| Component | Hidden | Use case |
|---|---|---|
| `.Vertical Timeline Unit` | ✅ Internal | Building block for `.Vertical Timeline Block` |
| `.Vertical Timeline Block` | ✅ Internal | Building block for `Vertical Timeline Block - Stack` |
| `Vertical Timeline Block - Stack` | No | Full vertical timeline for order/transaction tracking |
| `Horizontal timeline` | No | Compact horizontal step progress bar |
| `.Timeline unit` | ✅ Internal | Building block for `Horizontal timeline` |
| `_Progress indicator item` | ✅ Internal | Compact progress indicator step atom — used inside cards and tight spaces |
| `Progress indicator unit` | No | Compact multi-step progress indicator for tight spaces (2–4 steps) |

---

## 9a. .Timeline Unit *(Internal)*

**Component name:** `.Timeline unit`
**Node ID:** `14595:150712`

> ⚠️ **Internal component.** Building block for `Horizontal timeline`. Never place directly.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Complete | Complete, Current, upcoming | Step state — controls the visual indicator |
| `Color` | Variant | Orange | Orange, Green | Progress colour theme — Green for general timelines, Orange for brand-related or promoted timelines. Must match the parent `Horizontal timeline` colour |
| `Body` | Boolean | True | True, False | Shows body text below the step indicator |

| Type | Description |
|---|---|
| **Complete** | Step is finished |
| **Current** | Step is actively in progress |
| **upcoming** | Step has not been reached yet |

---

## 9b. .Vertical Timeline Unit *(Internal)*

**Component name:** `.Vertical Timeline Unit`
**Node ID:** `13420:13900`

> ⚠️ **Internal component.** Building block inside `.Vertical Timeline Block`. Never place directly.

| Variant axis | Options |
|---|---|
| `Stage` | First, Second, Middle, Before pending, Last |
| `State` | Done, Pending, Upcoming |

---

## 9c. .Vertical Timeline Block *(Internal)*

**Component name:** `.Vertical Timeline Block`
**Node ID:** `13426:11959`

> ⚠️ **Internal component.** Building block inside `Vertical Timeline Block - Stack`. Never place directly.

| Variant axis | Options |
|---|---|
| `Position` | Start, Middle, End, Before pending, Second |
| `Stage` | Pending, Done, Upcoming |

---

## 9d. Vertical Timeline Block — Stack

**Component name:** `Vertical Timeline Block - Stack`
**Figma page:** `↪️ ❖ Timeline ✅`
**Node ID:** `13426:12654`
**Use when:** Displaying the full vertical step timeline — order tracking, payment status steps, delivery progress.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 2 | 2, 3, 4, 5, 6, Plenty | Number of timeline steps |
| `Stage` | Variant | Done | Done, Pending | Overall completion stage of the timeline |

| Stage | Description |
|---|---|
| **Done** | All steps are completed |
| **Pending** | Timeline has steps still in progress or upcoming |

---

## 9e. Horizontal Timeline

**Component name:** `Horizontal timeline`
**Figma page:** `↪️ ❖ Timeline ✅`
**Node ID:** `14595:151872`
**Use when:** A compact horizontal step indicator — for checkout steps, onboarding stages, or short multi-step flows.

### Properties

| Property | Type | Options | Notes |
|---|---|---|---|
| `Count` | Variant | 2, 3, 4, 5 | Number of steps |
| `Completed` | Variant | 0, 1, 2, 3, 4, 5 | How many steps are completed |
| `Color` | Variant | Green, Orange | Progress colour theme |

| Color | Use case |
|---|---|
| **Green** | General timelines |
| **Orange** | Brand-related or promoted timelines |

---

## 9f. _Progress Indicator Item *(Internal)*

**Component name:** `_Progress indicator item`
**Figma page:** `↪️ ❖ Timeline ✅`
**Node ID:** `21862:25750`
**Use when:** Inside `Progress indicator unit` only — the single step atom for a compact progress indicator. Used in tight spaces such as inside cards where a full-size timeline would not fit.

> ⚠️ **Internal component.** Building block for `Progress indicator unit`. Do not place directly in screens.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Position` | Variant | Start | Start, Center, End | Position of this step within the indicator row |
| `State` | Variant | Current | Incomplete, Current, Error, Next state is error | Step state |
| `Optional label` | Boolean | True | True, False | Shows an optional label below the step — must be the same value across all steps (Start, Center, End) in the indicator |
| `Label` | Text | "Label" | Any string | Step label text |
| `Body` | Text | "24 Jan" | Any string | Secondary body text below the label (e.g. date, subtitle) |

| State | Description |
|---|---|
| **Incomplete** | Step not yet reached |
| **Current** | Actively in progress step |
| **Error** | Step has an error |
| **Next state is error** | Current step is fine but the next step will be in error — used to visually signal an upcoming failure |

| Position | Description |
|---|---|
| **Start** | First step in the row |
| **Center** | Middle step(s) |
| **End** | Last step in the row |

---

## 9g. Progress Indicator Unit

**Component name:** `Progress indicator unit`
**Figma page:** `↪️ ❖ Timeline ✅`
**Node ID:** `21864:6199`
**Use when:** A compact multi-step progress indicator for tight spaces — e.g. inside a card, a list row, or an inline status section where a full `Horizontal timeline` would be too large.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 4 | 2, 3, 4 | Number of steps in the progress indicator |

---

## Rules — Timeline

- Always use `Vertical Timeline Block - Stack` for vertical step timelines — never manually stack `.Vertical Timeline Block` instances.
- Always use `Horizontal timeline` for horizontal step progress — never construct manually from atoms.
- `Count` must match the actual number of steps in the flow — never show more steps than exist in the real flow.
- `Completed` on `Horizontal timeline` must equal the number of steps the user has actually completed — never set it higher than `Count`.
- Use `Color=Green` for general timelines and `Color=Orange` for brand-related or promoted timelines.
- ⛔ `Optional label` must be consistent across **all** steps in a `Progress indicator unit` — either all steps have it on or all steps have it off. Never mix `Optional label=True` on some steps and `Optional label=False` on others within the same indicator.

---
---

# 10. Quantity Stepper — POP Design System

Quantity steppers allow users to increment or decrement a numeric value — used for item quantity in cart/shop contexts.

---

## 10a. Quantity Stepper Item

**Component name:** `Quantity Stepper item`
**Figma page:** `↪️ ❖ Quantity Stepper 🚧`
**Node ID:** `20285:150919`
**Use when:** A standalone quantity control for a single item — placed inside a shop list item or product detail screen.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Default | 1 selected, Default | Interaction state |
| `Is disabled` | Variant | False | True, False | Disabled state |
| `Quantity` | Text | "12" | Any number string | Current quantity value |

| State | Description |
|---|---|
| **Default** | No item selected — shows "Add" state |
| **1 selected** | One item in cart — shows quantity with +/- controls |

---

## 10b. Amount + Quantity Stepper

**Component name:** `Amount + Quantity Stepper`
**Figma page:** `↪️ ❖ Quantity Stepper 🚧`
**Node ID:** `20312:151341`
**Use when:** Displaying the item amount alongside the quantity stepper — used in cart rows and product detail pages where price and quantity are shown together.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Medium | X Small, Small, Medium | Component size |
| `Pop-coin` | Boolean | True | True, False | Shows POP coin reward alongside the amount |

---

## Rules — Quantity Stepper

- Use `Amount + Quantity Stepper` (not `Quantity Stepper item` alone) whenever price and quantity need to appear together — e.g. in cart rows.
- Always set `Quantity` to the real current quantity — never leave "12" as production content.
- When a quantity update is being processed, loading feedback is delivered via a scrim loader overlaid on the page — do not use `State=Loading` on the stepper itself.
- `Is disabled=True` should be used when the item cannot be added (e.g. out of stock, max limit reached).
- This component is marked 🚧 (in progress) — check for updates before using in production.

---
---

## General Rules — Molecules 2

- **Never use internal (`.` prefix) components directly in screens.** They are building blocks — always use their parent stack or organism instead.
- **Always use Stack variants** for repeated units — `List - Stack`, `Table - Stack`, `Vertical Timeline Block - Stack`, etc.
- **Deprecated components** (`OLD-list`, `Benefit callout - Stack` with `Align=Center - Don't use`) must not appear in any new designs.
- **Always set real content** in all text properties — never leave defaults like "Title", "Label", "Body", or "12".
- **Disabled states are visual only** — always enforce the disabled logic in code as well.
- **Count/Plenty** — use `Plenty` for all lists with dynamic or unknown item counts at design time.
