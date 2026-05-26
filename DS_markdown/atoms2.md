# Divider — POP Design System

Dividers are structural separators used to create visual breaks between content areas, list items, and sections. Two orientations are available: **Horizontal** and **Vertical**.

---

## Horizontal Divider

**Component name:** `Horizontal divider`  
**Figma page:** `↪️ ❖ Divider ✅`  
**Stroke weight:** 0.5px (Blured variant: 1px)  
**Width:** 393px — stretches to fill container  
**Height:** 0px — line only, no height footprint

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Type` | Variant | Continuous | Continuous, Dashed, Stylised |
| `Colour` | Variant | Primary | Primary, Secondary, Tertiary, Blured, Mixed |

### Variants

| Variant | Type | Colour Token | Resolves To | Opacity | Weight | Dash Pattern | Effect |
|---|---|---|---|---|---|---|---|
| Type=Continuous, Colour=Primary | Continuous | `Border/Primary` | `Greys/Grey-800` → `#262626` | 100% | 0.5px | — | — |
| Type=Continuous, Colour=Secondary | Continuous | `Border/Secondary` | `Greys/Grey-700` → `#333333` | 100% | 0.5px | — | — |
| Type=Continuous, Colour=Tertiary | Continuous | `Border/Tertiary` | `Greys/Grey-600` → `#4D4D4D` | 100% | 0.5px | — | — |
| Type=Dashed, Colour=Secondary | Dashed | `Border/Secondary` | `Greys/Grey-700` → `#333333` | 100% | 0.5px | 6px dash / 6px gap | — |
| Type=Dashed, Colour=Tertiary | Dashed | `Border/Tertiary` | `Greys/Grey-600` → `#4D4D4D` | 100% | 0.5px | 6px dash / 6px gap | — |
| Type=Continuous, Colour=Blured | Continuous | `Border/Primary-30%` | `Greys/Grey-900` → `#1F1F1F` at 30% | 30% | 1px | — | Background blur (frosted surface) |
| Type=Stylised, Colour=Mixed | Stylised | ⚠️ Not tokenised — see note below | Linear gradient (see below) | — | 0.5px | — | — |

> ⚠️ **Stylised / Mixed — gradient colour note:**
> The gradient used in this variant is **not mapped to a named alias token**. It uses the `Surface-Secondary-Gradient/Small` paint style from Color Styles, which is a linear gradient from `#1F1F1F` → `#262626`. These raw values correspond to `Greys/Grey-900` and `Greys/Grey-800` in the parent token palette, but since no alias token wraps this specific gradient, **Claude must reference the paint style by name** (`Surface-Secondary-Gradient/Small`) rather than constructing it from individual tokens.

### Type

| Type | Visual | When to Use |
|---|---|---|
| **Continuous** | Solid unbroken line | Default — list rows, section breaks, card edges |
| **Dashed** | 6px dash, 6px gap | Optional/inactive zones, form field separators, boundary states |
| **Stylised** | Linear gradient fade | Editorial sections, marketing surfaces — never functional UI |

### Colour

| Colour | Alias Token | Resolves To | Opacity | Notes |
|---|---|---|---|---|
| **Primary** | `Border/Primary` | `Greys/Grey-800` → `#262626` | 100% | Strongest — high-contrast separators on dark surfaces |
| **Secondary** | `Border/Secondary` | `Greys/Grey-700` → `#333333` | 100% | Standard — default for most list rows and section breaks |
| **Tertiary** | `Border/Tertiary` | `Greys/Grey-600` → `#4D4D4D` | 100% | Subtle — light-touch separation, nested content |
| **Blured** | `Border/Primary-30%` | `Greys/Grey-900` → `#1F1F1F` at 30% | 30% | Translucent / glass / frosted surfaces only — also applies a **background blur effect** |
| **Mixed** | ⚠️ Not tokenised | `Surface-Secondary-Gradient/Small` paint style | — | Stylised type only — decorative editorial use — use the named paint style, not a hex value |

---

## Vertical Divider

**Component name:** `Vertical divider`  
**Figma page:** `↪️ ❖ Divider ✅`  
**Stroke weight:** 0.5px  
**Width:** 0px — line only, no width footprint  
**Container height:** 24px

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Type` | Variant | Padded | Padded, Full height |
| `Colour` | Variant | Tertiary | Tertiary, Secondary |

### Variants

| Variant | Type | Colour Token | Resolves To | Line Height | Container | Padding |
|---|---|---|---|---|---|---|
| Type=Padded, Colour=Tertiary | Padded | `Border/Tertiary` | `Greys/Grey-600` → `#4D4D4D` | 16px | 24px | 4px top & bottom |
| Type=Padded, Colour=Secondary | Padded | `Border/Secondary` | `Greys/Grey-700` → `#333333` | 16px | 24px | 4px top & bottom |
| Type=Full height, Colour=Tertiary | Full height | `Border/Tertiary` | `Greys/Grey-600` → `#4D4D4D` | 24px | 24px | None |
| Type=Full height, Colour=Secondary | Full height | `Border/Secondary` | `Greys/Grey-700` → `#333333` | 24px | 24px | None |

### Type

| Type | Line Height | Container | Description | When to Use |
|---|---|---|---|---|
| **Padded** | 16px | 24px | Line is shorter than its container — 4px breathing room top and bottom | Between chips, tags, icon-label pairs, inline items needing visual space |
| **Full height** | 24px | 24px | Line fills the full container height flush | Between equal-height blocks, action rows, tab bar items |

### Colour

| Colour | Alias Token | Resolves To | Usage |
|---|---|---|---|
| **Secondary** | `Border/Secondary` | `Greys/Grey-700` → `#333333` | Stronger inline separator — higher contrast |
| **Tertiary** | `Border/Tertiary` | `Greys/Grey-600` → `#4D4D4D` | Default — subtle inline separation in list rows and action groups |

---

## Rules

### General
- Prefer spacing tokens over dividers wherever whitespace alone creates sufficient separation.
- Always default to **Continuous / Secondary** for list rows and section breaks unless context requires otherwise.

### Horizontal
- Use **Dashed** only to signal optional, inactive, or boundary states — not as decoration.
- Use **Blured** only on translucent or glass-effect surfaces. Do not use on solid backgrounds.
- Use **Stylised** only on editorial or marketing surfaces — never in functional UI such as lists, forms, or navigation.

### Vertical
- Vertical dividers are for **inline separation only** — between sibling elements in a horizontal row.
- Do not use vertical dividers to separate vertically stacked content.
- Use **Padded** between items that need visual breathing room.
- Use **Full height** only when adjacent items share the same height and a flush line is intentional.

---
---

# Radio & Checkbox — POP Design System

Radio and Checkbox controls follow a strict hierarchy. The base components are atoms; they combine with text to form labelled controls; and those labelled controls stack into lists. **Always use the highest-level component that fits the context.**

## Component Hierarchy

```
Base Radio / Base Checkbox          ← atom — use inside slots only
       ↓
Radio with Text / Checkbox with Text  ← labelled control — use standalone
       ↓
Radio with Text (Stack) / Checkbox with Text (Stack)  ← list — default for final design
```

> **Rule:** Use the Stack in all final screen designs. Drop to "with Text" only when a single standalone control is needed. Drop to the Base only when the control sits inside a Slot.

---

## 1. Base Radio

**Component name:** `Radio`
**Figma page:** `↪️ ❖ Radio & Check box ✅`
**Use when:** The radio sits inside a Slot and the parent component controls layout and label.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `State` | Variant | Off | Off, On |
| `Promoted` | Variant | False | False, True |
| `Is disabled` | Variant | False | False, True |

### States

| State | Promoted | Is disabled | Description |
|---|---|---|---|
| Off | False | False | Default unselected |
| On | False | False | Selected |
| Off | False | True | Unselected + disabled |
| On | False | True | Selected + disabled |
| Off | True | False | Promoted unselected |
| On | True | False | Promoted selected |

---

## 2. Base Checkbox

**Component name:** `Checkbox`
**Figma page:** `↪️ ❖ Radio & Check box ✅`
**Use when:** The checkbox sits inside a Slot and the parent component controls layout and label.

### Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `State` | Variant | Off | Off, On |
| `Promoted` | Variant | False | False, True |
| `Is disabled` | Variant | False | False, True |
| `Indeterminate` | Variant | False | False, True |

### States

| State | Indeterminate | Promoted | Is disabled | Description |
|---|---|---|---|---|
| Off | False | False | False | Default unselected |
| On | False | False | False | Checked |
| Off | False | False | True | Unselected + disabled |
| On | False | False | True | Checked + disabled |
| On | True | False | False | Partial / Indeterminate |
| On | True | False | True | Indeterminate + disabled |
| ON | True | True | False | Indeterminate + promoted |
| On | False | True | False | Promoted Checked |
| Off | False | True | False | Promoted unselected |
| Off/On | — | True | True | Promoted + disabled — **uses the same disabled visual as the default (non-promoted) disabled state** |

### Promoted

> **What Promoted is for:** `Promoted=True` applies a highlighted ring or emphasis treatment to draw the user's attention to a specific option — used when an option should stand out within a group (e.g. a recommended plan, a pre-selected preference, or a featured option).

> **Disabled behaviour for Promoted:** There is **no separate disabled style for the Promoted variant**. When `Promoted=True` and `Is disabled=True` are combined, the checkbox falls back to the same disabled visual as `Promoted=False, Is disabled=True`. The promoted emphasis is not shown in a disabled state — **disabled always overrides promoted styling**.

---

## 3. Radio with Text

**Component name:** `Radio with text`
**Figma page:** `↪️ ❖ Radio & Check box ✅`
**Use when:** A single standalone labelled radio is needed (not inside a list).

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Small | Small, Med | — |
| `State` | Variant | Off | Off, On | — |
| `Promoted` | Variant | False | False, True | — |
| `Is disabled` | Variant | False | False, True | — |
| `Align` | Variant | Left | Left, Right | — |
| `Title` | Boolean | True | True, False | Toggles the title text line on/off |
| `Body` | Boolean | True | True, False | Toggles the body/description text line on/off |
| `L-Slot` | Boolean | False | True, False | Toggles the leading (left) slot on/off |
| `R-Slot` | Boolean | False | True, False | Toggles the trailing (right) slot on/off |
| `Title text` | Text | *(placeholder)* | Any string | Content only — set per design context, not a fixed value |
| `Body text` | Text | *(placeholder)* | Any string | Content only — set per design context, not a fixed value |

> **On Title text / Body text:** These are content properties, not structural ones. They hold the label and description copy specific to each use case. Claude should populate these with the appropriate text from the design context — there is no single correct default.

### Size

| Size | Use case |
|---|---|
| Small | Dense lists, compact forms |
| Med | Standard forms, settings |

### Align

| Align | Description |
|---|---|
| Left | Radio control on the left, label on the right — default |
| Right | Label on the left, radio control on the right — use for right-aligned selection rows |

### L-Slot and R-Slot

Both slots are **off by default**. Set `L-Slot=True` or `R-Slot=True` to reveal the slot placeholder, then place a component inside it.

> **This is how Claude knows to use a slot:** If the design requires supplementary visual context alongside the radio label — such as identifying which bank, app, or option is being selected — enable the relevant slot and place the appropriate atom inside it. The slot itself does not dictate what goes in it; Claude must infer the correct component from the design intent.

| Slot | Position | When to enable | What goes inside |
|---|---|---|---|
| `L-Slot` | Left of the label | When the option needs a visual identifier | `Avatar` (Brand type) for bank/app logos · `Avatar` (People type) for contacts · `Icon` for category indicators |
| `R-Slot` | Right of the label | When the option needs trailing metadata | `Badge` for counts or status · `Icon` for disclosure · Inline text for pricing or metadata |

**Examples of L-Slot usage in context:**

| Design context | L-Slot component | Config |
|---|---|---|
| Select a bank account | `Avatar` | Type=Brand, Size=36 |
| Select a UPI app | `Avatar` | Type=Brand, Size=28 |
| Select a contact | `Avatar` | Type=People - Image, Size=36 |
| Select a payment method type | `Icon` | 20px wrapper, outline icon |

---

## 4. Checkbox with Text

**Component name:** `Checkox with text` *(note: typo is intentional — matches Figma)*
**Figma page:** `↪️ ❖ Radio & Check box ✅`
**Use when:** A single standalone labelled checkbox is needed (not inside a list).

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Small | Small, Med, Large | — |
| `State` | Variant | Off | Off, On | — |
| `Promoted` | Variant | False | False, True | — |
| `Is disabled` | Variant | False | False, True | — |
| `Align` | Variant | Left | Left, Right | — |
| `Title` | Boolean | True | True, False | Toggles the title text line on/off |
| `Body` | Boolean | True | True, False | Toggles the body/description text line on/off |
| `L-Slot` | Boolean | False | True, False | Toggles the leading (left) slot on/off |
| `R-Slot` | Boolean | False | True, False | Toggles the trailing (right) slot on/off |
| `Title text` | Text | *(placeholder)* | Any string | Content only — set per design context |
| `Body text` | Text | *(placeholder)* | Any string | Content only — set per design context |

> **On Title text / Body text:** Same as Radio with Text — these hold the label and description copy for each specific use case. Claude should populate them from design context, not from a fixed default.

### Size

| Size | Use case |
|---|---|
| Small | Dense lists, compact forms |
| Med | Standard forms, settings, filters |
| Large | Prominent opt-ins, permissions, terms |

### L-Slot and R-Slot

Both slots are **off by default**. Same slot logic as Radio with Text applies — enable the slot and place the correct component inside based on design intent.

| Slot | Position | When to enable | What goes inside |
|---|---|---|---|
| `L-Slot` | Left of the label | When the option needs a visual identifier | `Avatar` (Brand/People) · `Icon` · Thumbnail image |
| `R-Slot` | Right of the label | When the option needs trailing context | `Badge` · Pricing text · `Icon` for info/disclosure |

**Examples of L-Slot usage in context:**

| Design context | L-Slot component | Config |
|---|---|---|
| Select a subscription plan with logo | `Avatar` | Type=Brand, Size=36 |
| Select a product category | `Icon` | 20px wrapper, outline icon |
| Consent with partner brand | `Avatar` | Type=Brand, Size=28 |

---

## 5. Radio with Text (Stack)

**Component name:** `Radio with text - Stack`
**Figma page:** `↪️ ❖ Radio & Check box ✅`
**Use when:** Displaying a group of radio options in a vertical list — **default for all final screen designs.**

### Properties

| Property | Type | Options |
|---|---|---|
| `Count` | Variant | 1, 2, 3, 4, 5, Plenty |
| `Size` | Variant | Small, Med |

> `Plenty` renders a scrollable overflow state. Use when the count is dynamic or unknown at design time.

### Structure
Each stack item is a `Radio with text` instance separated by a `Horizontal divider`. The divider is built into the stack — do not add dividers manually.

---

## 6. Checkbox with Text (Stack)

**Component name:** `Checkbox with text - Stack`
**Figma page:** `↪️ ❖ Radio & Check box ✅`
**Use when:** Displaying a group of checkbox options in a vertical list — **default for all final screen designs.**

### Properties

| Property | Type | Options |
|---|---|---|
| `Count` | Variant | 1, 2, 3, 4, 5, Plenty |
| `Size` | Variant | Small, Med, Large |

> `Plenty` renders a scrollable overflow state. Use when the count is dynamic or unknown at design time.

### Structure
Each stack item is a `Checkox with text` instance separated by a `Horizontal divider`. The divider is built into the stack — do not add dividers manually.

---

## 7. Toggle

**Component name:** `Toggle`
**Figma page:** `↪️ ❖ Radio & Check box ✅`
**Use when:** The user needs to switch a single setting or preference on or off — an immediate binary action with no confirmation step required.

> Toggle is a **standalone base atom only** — there is no Toggle with Text or Toggle Stack variant. When a toggle needs a label and description, it is placed inside a parent layout component (e.g. a List row or Settings row) that provides the surrounding text and structure.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Med | — |
| `State` | Variant | Off | Off, On | Off = inactive, On = active |
| `Promoted` | Variant | False | False, True | Highlights toggle to grab user attention |
| `Is disabled` | Variant | False | False, True | — |
| `Indeterminate` | Variant | False | False, True | Loading or unknown state — neither On nor Off |

### States

| State | Indeterminate | Promoted | Is disabled | Description |
|---|---|---|---|---|
| Off | False | False | False | Default — inactive |
| On | False | False | False | Active |
| Off | False | False | True | Inactive + disabled |
| On | False | False | True | Active + disabled |
| Off | False | True | False | Promoted inactive — highlighted to grab attention |
| On | False | True | False | Promoted active |
| On | True | False | False | Indeterminate — state is loading or unknown |
| On | True | False | True | Indeterminate + disabled |
| On | True | False | True | Promoted + disabled — **falls back to the same disabled visual as non-promoted disabled state** |

### Size

| Size | Use case |
|---|---|
| Large | Default — settings screens, preference rows |
| Med | Dense rows, inline toggles with limited vertical space |

### Promoted
> `Promoted=True` adds a highlighted ring to draw the user's attention to the toggle (e.g. a recommended setting, a featured preference). When `Promoted=True` and `Is disabled=True` are combined, the promoted styling is suppressed — **disabled always overrides promoted**.

### Indeterminate
> Used when the toggle's current state cannot be determined — typically during a loading state or when the underlying value is being fetched. It is neither On nor Off. Always resolve to a definitive On or Off state once data is available.

### Toggle vs Checkbox — when to use which

| Scenario | Use |
|---|---|
| Immediate on/off setting (e.g. enable notifications) | `Toggle` |
| Selection within a form submitted later | `Checkbox` |
| Single consent or agreement | `Checkbox` |
| Live preference that takes effect instantly | `Toggle` |

---

## Rules

- **Always use Stack** for lists of 1 or more options in final screens.
- **Use "with Text"** only for a single isolated option (e.g. a single "I agree" checkbox).
- **Use Base** only when the control sits inside a Slot — the slot's parent component provides the label and layout.
- **Toggle has no Stack or with Text variant** — always place it inside a parent layout component when a label is needed.
- Never mix Radio and Checkbox in the same group.
- Promoted variant is for highlighted or recommended options — use sparingly, maximum one per group.
- Indeterminate state is valid on both Checkbox and Toggle — never on Radio.
- Disabled states are visual only — ensure the disabled logic is also enforced in code.

---

# Ratings — POP Design System

The Ratings system has two independent tracks: a star display track built from atoms (`rating - star` → `rating - star group`), and a completely separate badge component (`Rating badge`) that stands alone.

## Component Hierarchy

```
rating - star          ← base atom (full / half / empty state)
       ↓
rating - star group    ← composed star display (0 – 5 in 0.5 increments)


Rating badge           ← INDEPENDENT component — not connected to the star chain above
                          Has its own single built-in star + numeric score text
                          Source of the star and text is internal to the badge (Badge property)
```

> **Rating Badge is not derived from `rating - star` or `rating - star group`.** It is a self-contained component with its own built-in single star icon and score text. Do not use `rating - star group` to construct a Rating Badge.

---

## 1. Rating Star *(Base Atom)*

**Component name:** `rating - star`
**Figma page:** `↪️ ❖ Ratings ✅`
**Use when:** Never used directly in screens — it is only the building block for `rating - star group`.

### Properties

| Property | Type | Options |
|---|---|---|
| `state` | Variant | full, half, empty |

| State | Description |
|---|---|
| full | Fully filled star — contributes 1.0 to score |
| half | Half-filled star — contributes 0.5 to score |
| empty | Unfilled star — contributes 0 to score |

---

## 2. Rating Star Group

**Component name:** `rating - star group`
**Figma page:** `↪️ ❖ Ratings ✅`
**Use when:** Displaying a star-based rating score on product cards, reviews, and listing rows.

### Properties

| Property | Type | Options |
|---|---|---|
| `rating` | Variant | 0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5 |

### How it works
`rating - star group` is composed of five `rating - star` atoms. The `rating` prop drives which combination of full / half / empty stars is shown. All 11 values (0–5 in 0.5 steps) are pre-built as variants — always use the variant, never manually arrange individual stars.

| Rating value | Star pattern |
|---|---|
| 5 | ★ ★ ★ ★ ★ |
| 4.5 | ★ ★ ★ ★ ½ |
| 4 | ★ ★ ★ ★ ☆ |
| 3.5 | ★ ★ ★ ½ ☆ |
| 3 | ★ ★ ★ ☆ ☆ |
| 2.5 | ★ ★ ½ ☆ ☆ |
| 2 | ★ ★ ☆ ☆ ☆ |
| 1.5 | ★ ½ ☆ ☆ ☆ |
| 1 | ★ ☆ ☆ ☆ ☆ |
| 0.5 | ½ ☆ ☆ ☆ ☆ |
| 0 | ☆ ☆ ☆ ☆ ☆ |

---

## 3. Rating Badge

**Component name:** `Rating badge`
**Figma page:** `↪️ ❖ Ratings ✅`
**Use when:** Displaying a compact numeric rating score — on product cards, thumbnails, and listing rows where a full star group doesn't fit.

### Properties

| Property | Type | Options |
|---|---|---|
| `Type` | Variant | Translucent, Green, Grey, Transparent |

### Types

| Type | Background | Use case |
|---|---|---|
| Translucent | Semi-transparent dark | Over image surfaces, media cards |
| Green | Success green fill | High ratings (≥ 4.0) on light surfaces |
| Grey | Neutral grey fill | Mid or unrated states on light surfaces |
| Transparent | No background | Inline text contexts, dense data rows |

---

## Rules

- Never place individual `rating - star` atoms directly in a screen — always use `rating - star group`.
- Use `Rating badge` when horizontal space is constrained and only the score number is needed.
- Use `rating - star group` when the full star spread is needed for clarity (product detail pages, review sections).
- Rating values are always a number between 0–5 in 0.5 increments — never render a 4.3 or 3.7.

---
---

# Autopay Merch — POP Design System

The Autopay Merch component renders a merchant brand image inside a standardised card. Each brand is a separate component variant in the `New` frame. The brand image is always a background image asset — never recoloured or overlaid.

**Component name:** `Autopay Merch componenet` *(typo matches Figma)*
**Figma page:** `↪️ ❖ Autopay Merch ✅`
**Node ID:** `17595:21186`

## Structure

The component contains a single `Background Image` rectangle. The brand artwork fills this rectangle. There are no text layers inside — brand name is always rendered externally by the parent component.

## Usage Rule

> Always map the Figma brand label to the corresponding code asset name using the table below. Never use the Figma label directly as a file name — casing and spacing differ between design and code.

---

## Brand Mapping Table

| # | Figma Label | Code Asset Name |
|---|---|---|
| 1 | Swiggy | `swiggy` |
| 2 | Campus | `campus` |
| 3 | Souledstore | `souledstore` |
| 4 | Plum | `plum` |
| 5 | Bewakoof | `bewakoof` |
| 6 | Snackible | `snackible` |
| 7 | YogaBar | `yogabar` |
| 8 | Portronics | `portronics` |
| 9 | Bombay Shaving Company | `bombay-shaving-company` |
| 10 | Comet | `comet` |
| 11 | Nike | `nike` |
| 12 | Puma | `puma` |
| 13 | Bonkers Corner | `bonkers-corner` |
| 14 | The Man Company | `the-man-company` |
| 15 | Neeman's | `neemans` |
| 16 | Boat | `boat` |
| 17 | Jar Gold | `jar-gold` |
| 18 | MPOKKET | `mpokket` |
| 19 | Spotify India | `spotify-india` |
| 20 | Amazon | `amazon` |
| 21 | APPLE MEDIA SERVICES | `apple-media-services` |
| 22 | SAFE GOLD | `safe-gold` |
| 23 | Google | `google` |
| 24 | Google Play | `google-play` |
| 25 | NETFLIX COM | `netflix` |
| 26 | JioHotstar | `jio-hotstar` |
| 27 | KukuFM | `kukufm` |
| 28 | GULLAKMONEY | `gullak-money` |
| 29 | Stage | `stage` |
| 30 | Amazon Pay | `amazon-pay` |
| 31 | PocketFM | `pocketfm` |
| 32 | Seekho | `seekho` |
| 33 | SANATANAPP PRIVATE LIMITED | `sanatan-app` |
| 34 | Story TV | `story-tv` |
| 35 | AUGMONT GOLDTECH PRIVATE LIMITED | `augmont-goldtech` |
| 36 | OpenAI LLC | `openai` |
| 37 | Sharechat | `sharechat` |
| 38 | Razorpay Software Private Limited | `razorpay` |
| 39 | Gamma Gaana Limited | `gaana` |
| 40 | Instamoney | `instamoney` |
| 41 | AuraGold | `aura-gold` |
| 42 | Axis | `axis` |
| 43 | Minipix Entertainment Private Limit | `minipix` |
| 44 | JioCinema | `jio-cinema` |
| 45 | Viralo | `viralo` |
| 46 | SAAVNMEDIALIMITED | `saavn` |
| 47 | Rblbank | `rbl-bank` |
| 48 | Winzo TV Entertainment | `winzo` |
| 49 | AIRTEL DIGITAL LIMITED | `airtel-digital` |
| 50 | FATAKPAY DIGITAL PRIVATE LIMITED | `fatakpay` |
| 51 | Cashfree Payments | `cashfree` |
| 52 | MONEYVIEWGOLD | `moneyview-gold` |
| 53 | KreditBee | `kreditbee` |
| 54 | Lenden Club | `lenden-club` |
| 55 | Snapmint Financial Services Pvt Ltd | `snapmint` |
| 56 | OOLKASUBS | `oolka-subs` |
| 57 | PAYTM MONEY LIMITED | `paytm-money` |
| 58 | NAMMAYATRI | `namma-yatri` |
| 59 | HOSTINGER PTE LTD | `hostinger` |
| 60 | CrunchyrollPrivateLimited | `crunchyroll` |
| 61 | Piramal Capital and Housing Finance | `piramal-capital` |
| 62 | COLD BREW TECH PRIVATE LIMITED | `cold-brew-tech` |
| 63 | DASHVERSE INDIA PRIVATE LIMITED | `dashverse` |
| 64 | SHRIRAM FINANCE LIMITED | `shriram-finance` |
| 65 | DECIML APP | `deciml` |
| 66 | ZEE5 | `zee5` |
| 67 | NAVI AMC LIMITED | `navi-amc` |
| 68 | INDmoney | `indmoney` |
| 69 | linkedin | `linkedin` |
| 70 | Simpl | `simpl` |
| 71 | No brand icon | `no-brand-icon` |

## Rules

- **Always use the Brand Mapping Table** to translate Figma label → code asset file name.
- **Never recolour** the brand image. It is a brand asset — render at full colour only.
- **Never overlay text** directly on the brand image inside the component.
- When a brand is not in the list, use the `No brand icon` fallback variant.
- The component does not contain a visible label — the merchant name is always rendered by the parent layout.

---
---

# Scrim — POP Design System (Without Blur)

Scrims are overlay layers placed between content and backgrounds. This document covers the **Without Blur** section only — the flat/gradient scrim variants. Blur scrims are documented separately.

**Component name:** `Scrim`
**Figma page:** `↪️ ❖ Scrim ✅`
**Section documented:** Without Blur (node `13658:26423`)

---

## Properties

| Property | Type | Default | Options |
|---|---|---|---|
| `Type` | Variant | Block | Block, Top, Bottom, Bottom sheet |
| `Opacity` | Variant | 100% | 100%, 70%, 1% |
| `Use` | Variant | True | True, False |

> `Use` toggles the scrim layer visibility. Set to `False` to hide the scrim while keeping the node in the layer structure — useful for states where the scrim conditionally appears.

---

## Types

| Type | Gradient Direction | Use case |
|---|---|---|
| **Block** | None — solid fill | Full-surface overlay: modals, blocking states |
| **Top** | Top → transparent (fades down) | Fading content into the top edge — navigation bars, header overlays |
| **Bottom** | Transparent → bottom (fades up) | Fading content into the bottom edge — CTAs above content, tab bar overlays |
| **Bottom sheet** | Transparent → bottom (steep fade) | Bottom sheet drag handle area — strong fade at the base |

---

## Opacity

| Opacity | Hex | Use case |
|---|---|---|
| **100%** | `#0D0D0D` full | Maximum blocking — critical dialogs, full-screen overlays |
| **70%** | `#0D0D0D` at 70% | Standard modal/sheet overlay — most common usage |
| **1%** | `#0D0D0D` at 1% | Near-invisible layer — used to capture tap events without visual occlusion |

---

## Variants in "Without Blur" Section

The `Without Blur` section contains 8 scrim instances placed over a scanner background — these represent the standard usage contexts:

| Position | Scrim Instance | Purpose |
|---|---|---|
| 1 | Block 70% | Standard modal overlay |
| 2 | Top 100% | Header/nav fade |
| 3 | Top 70% | Softer header fade |
| 4 | Bottom 100% | CTA / tab bar strong fade |
| 5 | Bottom 70% | CTA / tab bar standard fade |
| 6 | Bottom sheet 70% | Bottom sheet drag overlay |

---

## Rules

- **Almost all scrims are embedded inside the component they belong to** — you will rarely need to place a Scrim atom manually. When using a Bottom Sheet, Modal, Card, or any overlay component, the scrim is already built into that component. Only place the Scrim atom directly when building a custom overlay or a pattern that has no parent component encapsulating it.
- Always place Scrim **above** the content it overlays and **below** any interactive UI (buttons, close icons).
- Use **Block** for full-page overlays and modals.
- Use **Top** or **Bottom** gradient variants for partial fades — never stretch a Block scrim and reduce opacity to simulate a gradient.
- Use **1% opacity** only when an invisible tap-capture layer is required — never for visible overlays.
- Never apply additional colour fills or effects on top of the Scrim component.
- The `Use=False` toggle is for design articulation only — in code, conditionally render or hide the scrim based on state.

---
---

# Avatar — POP Design System

Avatars represent people, brands, or system icons in a circular container. The Avatar system has one main component and four sub-components that compose into richer presentations.

**Figma page:** `↪️ ❖ Avatar ✅`

## Component Overview

| Component | Description |
|---|---|
| `Avatar` | Main avatar circle — people, brand, or icon |
| `Dot` | Small status indicator attached to the avatar |
| `Flag` | Coloured flag badge on the avatar |
| `Fave icon` | Favourite/heart action attached to the avatar |
| `Avatar stack` | Overlapping group of multiple avatars |

---

## 1. Avatar

**Component name:** `Avatar`
**Variants:** 72 total

### Properties

| Property | Type | Options |
|---|---|---|
| `Type` | Variant | People - Initials, People - Image, Brand, Icon, Illsutration |
| `Fill (for 'Icon' only)` | Variant | Not icon - People / Brand, Primary - Highlighted, Secondary - Highlighted, Tertiary - Transparent, Only icon, Primary - 50%, Not icon - Illustration |
| `Size` | Variant | 16, 28, 36, 56, 64, 130 |
| `Is disabled` | Variant | False, True |

### Types

| Type | Description | When to use |
|---|---|---|
| **People - Initials** | Circular container with initials text | When no profile image is available |
| **People - Image** | Circular container with photo | When a profile photo is available |
| **Brand** | Square container with brand logo | Merchant, partner, or app icons |
| **Icon** | Square container with a system icon | Generic/system contexts, anonymous users |
| **Illustration** | Square container with a system illustration | Generic/system contexts, offers |

### Fill — Icon type only

> `Fill` only applies when `Type=Icon`. For People and Brand types, the value is always `Not icon - People / Brand` and should not be changed.

| Fill value | Description | When to use |
|---|---|---|
| `Not icon - People / Brand` | No fill treatment — default for non-icon types | Always set for People - Initials, People - Image, Brand |
| `Primary - Highlighted` | Strong filled background — high emphasis | Primary action icons, prominent system indicators |
| `Secondary - Highlighted` | Medium filled background — moderate emphasis | Secondary action icons, supporting system indicators |
| `Tertiary - Transparent` | Transparent / no background — low emphasis | Subtle icons, inline contexts, dense layouts |
| `Only icon` | Icon rendered without any container background | Bare icon treatment — no circular container fill |
| `Illustration` | Illustration rendered without any container background | Bare illustration treatment — no circular container fill |

### Sizes

| Size | px | Use case |
|---|---|---|
| 16 | 16px | Micro — inline mention, dense data rows |
| 28 | 28px | Small — compact lists, comment rows |
| 36 | 36px | Medium — standard list rows, chat threads |
| 56 | 56px | Large — profile headers, contact cards |
| 64 | 64px | XL — prominent profile surfaces |
| 130 | 130px | Hero — full profile page, onboarding |

---

## 2. Dot

**Component name:** `Dot`
**Use when:** Showing a small presence or status indicator on an avatar.

### Properties

| Property | Type | Options |
|---|---|---|
| `Colour` | Variant | Orange, White, Red, Blue, Green, Yellow |
| `Size` | Variant | 6, 8, 10 |

### Colour semantics

| Colour | Meaning |
|---|---|
| Green | Online / active |
| Orange | Away / busy (brand) |
| Red | Do not disturb / alert |
| Blue | In a call / focused |
| Yellow | Idle / pending |
| White | Invert — for use on dark avatar rings |

### Size guidance

| Size | Paired avatar size |
|---|---|
| 6px | 16px, 28px |
| 8px | 36px, 56px |
| 10px | 64px, 130px |

---

## 3. Flag

**Component name:** `Flag`
**Use when:** Marking an avatar with a categorical flag (e.g. premium, verified, new).

### Properties

| Property | Type | Options |
|---|---|---|
| `Type` | Variant | Orange, White, Green, Red |

| Type | Use case |
|---|---|
| Orange | Brand / promoted |
| Green | Verified / success |
| Red | Alert / flagged |
| White | Invert — light surface contexts |

---

## 4. Fave Icon

**Component name:** `Fave icon`
**Use when:** Attaching a favourite/bookmark action to an avatar (e.g. saved contacts, wishlisted merchants).

### Properties

| Property | Type | Options |
|---|---|---|
| `Colour` | Variant | Orange, White, Green, Blue, Black, New fave, fave icon |
| `Size` | Variant | 20px, 24px, 28px, 32px |
| `Is active` | Variant | True, False |

### Size guidance

| Size | Paired avatar size |
|---|---|
| 20px | 36px avatar |
| 24px | 56px avatar |
| 28px | 64px avatar |
| 32px | 130px avatar |

| Is active | Meaning |
|---|---|
| True | Item is favourited — filled icon |
| False | Item is not favourited — outline icon |

---

## 5. Avatar Stack

**Component name:** `Avatar stack`
**Use when:** Showing a compact group of people (participants, contributors, followers).

### Properties

| Property | Type | Options |
|---|---|---|
| `Count` | Variant | 1, 2, 3 |
| `Type` | Variant | Logo, Icon |
| `More` | Variant | True, False |

| Property | Description |
|---|---|
| Count | Number of visible avatars in the overlap (max 3) |
| Type | Logo — brand/app logos; Icon — people/generic icons |
| More | When True, appends a "+N" overflow indicator after the visible avatars |

---

## Rules

- Always pair a `Dot`, `Flag`, or `Fave icon` sub-component with the correct avatar size using the size guidance tables.
- Never manually scale an Avatar — always use the nearest available size variant.
- Use **People - Initials** as the fallback when no image is available — never show an empty circle.
- Use **Brand** type only for logos — never for people profiles.
- Use `Avatar stack` with `More=True` when the total count exceeds 3 — do not add more than 3 visible avatars manually.
- Sub-components (Dot, Flag, Fave icon) are positioned and sized by the design system — do not reposition them relative to the Avatar circle.
