# POP Design System — Patterns

Patterns are pre-composed, screen-ready combinations of molecules and organisms built for specific product contexts. They encode layout, spacing, and component configuration decisions that would otherwise need to be made manually each time.

> ⛔ **Always use patterns instead of assembling individual components.** Patterns ensure consistency across screens, reduce design drift, and reflect the correct interaction model for each context.

---

## Figma Page

| Section | Figma Page |
|---|---|
| All payment patterns | `↪️ ❖ Payments 🚧` |
| All shop patterns | `↪️ ❖ Shop ✅` |
| All recharge patterns | `↪️ ❖ Recharges 🚧` |
| All account management patterns | `↪️ ❖ Account Management ✅` |
| All Profile QR patterns | `↪️ ❖ Profile QR ✅` |

---

# 1. Payments

Payment patterns cover all list, input, payee, transaction result, and action contexts used across payment flows — send money, collect request, RCBP, self transfer, autopay, and UPI Lite.

## Component Overview

| # | Component | Node ID | Type | Use case |
|---|---|---|---|---|
| 1a | `Payment list - Chevron - Pattern` | `8931:174283` | COMPONENT_SET | Payment method row with a right-facing chevron |
| 1b | `Payment list - Label - Pattern` | `11161:773752` | COMPONENT_SET | Payment method row with a label CTA |
| 1c | `Balance list - Pattern` | `9303:64649` | COMPONENT_SET | Payment method row displaying an account balance |
| 1d | `Payment list - Button - Pattern` | `8931:133726` | COMPONENT_SET | Payment method row with a primary or secondary button CTA |
| 1e | `Payment list - Radio - Pattern` | `7628:7999` | COMPONENT_SET | Payment method row with a radio selector |
| 1f | `Transaction list - Pattern` | `9828:173756` | COMPONENT_SET | Transaction history row (brand or person) |
| 1g | `Button + Payment - Pattern` | `7850:42587` | COMPONENT_SET | Payment method selector button (RuPay, Bank, UPI Lite, etc.) |
| 1h | `Input field - Naked Large - Pattern` | `7936:17101` | COMPONENT_SET | Full-width naked amount/text input for payment screens |
| 1i | `Input field - Note - Pattern` | `7936:17245` | COMPONENT_SET | Add a note input field |
| 1j | `Payee - Pattern` *(Payee type)* | `8069:28766` | COMPONENT_SET | Payee display by identity type — People, Brand, Category |
| 1k | `Payee - Pattern` *(Payment flow)* | `8069:72871` | COMPONENT_SET | Payee display by payment flow — P2P, P2M, Bank transfer, etc. |
| 1l | `Payee details - Pattern - Unit` | `8069:29165` | COMPONENT_SET | Payee name + VPA resolution display unit |
| 1m | `RCBP - Pattern - Unit` *(Biller type)* | `12576:126460` | COMPONENT_SET | RCBP biller display — Category or Brand type |
| 1n | `RCBP - Pattern - Unit` *(Biller details)* | `12576:127042` | COMPONENT_SET | RCBP biller display with editable name and VPA text |
| 1o | `Payment list - Left - Small - Pattern` | `6973:9361` | COMPONENT_SET | Compact payment list row — avatar/logo on the left |
| 1p | `Payment list - Right - Small - Pattern` | `6973:11031` | COMPONENT_SET | Compact payment list row — avatar/logo on the right |
| 1q | `Collect request - Popup - Pattern` | `9781:159101` | COMPONENT_SET | Popup for autopay or collect request approval |
| 1r | `.Amount + Status` | `13318:89797` | COMPONENT *(Internal)* | Internal amount + status display — local component of `Tranaction result - Pattern` |
| 1s | `.Tranaction result - Unit` | `13324:90828` | COMPONENT *(Internal)* | Internal transaction result unit — local component of `Tranaction result - Pattern` |
| 1t | `Tranaction result - Pattern` | `13324:91093` | COMPONENT_SET | Full transaction result screen pattern |

---

## 1a. Payment list - Chevron - Pattern

**Component name:** `Payment list - Chevron - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `8931:174283`
**Use when:** Displaying a tappable payment method row that navigates to a detail or selection screen. The chevron signals a drill-down interaction.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Med | Large for primary payment screens; Med for compact lists |
| `State` | Variant | Default | Default, Success, Error, Warning, Info | Visual feedback state of the row |
| `Secondary CTA` | Variant | Body text | Body text, Link text, Balance, None | Secondary information shown below the primary title |
| `Is disabled` | Variant | False | False, True | Disabled state — row is non-interactive |

---

## 1b. Payment list - Label - Pattern

**Component name:** `Payment list - Label - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `11161:773752`
**Use when:** Displaying a payment method row where the right-side CTA is a label (status badge or text tag) rather than a chevron or button.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Med | Large for primary screens; Med for compact lists |
| `State` | Variant | Default | Default, Success, Error, Warning, Info | Visual feedback state of the row |
| `Secondary CTA` | Variant | Body text | Body text, None, Balance, Link text | Secondary information below the title |
| `Is disabled` | Variant | False | False, True | Disabled state |

---

## 1c. Balance list - Pattern

**Component name:** `Balance list - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `9303:64649`
**Use when:** Showing a payment method row that displays an account balance (e.g. UPI Lite balance, bank balance). Use `Number=Hidden` for masked balance and `Number=Visible` for revealed balance.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Med | Large for primary screens; Med for compact lists |
| `State` | Variant | Default | Default, Success, Error, Warning, Info | Visual feedback state of the row |
| `Number` | Variant | Hidden | Visible, Hidden | Whether the balance amount is revealed or masked |
| `Body` | Variant | True | True, False | Shows a secondary body line below the title |
| `Is disabled` | Variant | False | False, True | Disabled state |

---

## 1d. Payment list - Button - Pattern

**Component name:** `Payment list - Button - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `8931:133726`
**Use when:** A payment method row requires a button as the primary CTA — e.g. "Verify", "Set up", or a quick action directly in the list row.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Med | Large for primary screens; Med for compact lists |
| `State` | Variant | Default | Default, Success, Error, Warning, Info | Visual feedback state of the row |
| `Primary CTA` | Variant | Secondary button | Secondary button, Primary button, Brand primary button | Type of button shown as the right-side CTA |
| `Secondary CTA` | Variant | Body text | Body text, Link text, Balance, None | Secondary information below the title |
| `Is disabled` | Variant | False | False, True | Disabled state |

---

## 1e. Payment list - Radio - Pattern

**Component name:** `Payment list - Radio - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `7628:7999`
**Use when:** A user must select one payment method from a list. Each row has a radio button — only one can be selected at a time.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Med | Large for primary screens; Med for compact lists |
| `State` | Variant | Default | Default, Success, Error, Warning, Info | Visual feedback state of the row |
| `Secondary CTA` | Variant | Body text | Body text, Link text, Balance, None | Secondary information below the title |
| `Is selected` | Variant | False | False, True | Whether this row's radio is selected |
| `Is disabled` | Variant | False | False, True | Disabled state |
| `Badge` | Variant | False | False, True | Shows a badge indicator on the row |

---

## 1f. Transaction list - Pattern

**Component name:** `Transaction list - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `9828:173756`
**Use when:** Displaying a single row in a transaction history list. Use `Type=Brand` for merchant/brand transactions and `Type=People` for P2P transactions.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Size` | Variant | Large | Large, Med | Large for full transaction screens; Med for compact feeds |
| `State` | Variant | Default | Default, Success, Error, Warning, Info | Transaction status |
| `Type` | Variant | Brand | Brand, People | Brand = merchant/UPI Lite; People = P2P person-to-person |
| `Body` | Variant | True | True, False | Shows a secondary description line |
| `Is disabled` | Variant | False | False, True | Disabled state |
| `Chevron` | Variant | False | False, True | Shows a right chevron for drill-down navigation |

---

## 1g. Button + Payment - Pattern

**Component name:** `Button + Payment - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `7850:42587`
**Use when:** Showing the currently selected payment method as a tappable button — allows the user to switch their payment method. Used in the send money flow, collect request, and similar payment initiation screens.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | RuPay | RuPay, Bank, UPI Lite, Self transfer, Autopay | The active payment method type |
| `State` | Variant | Default | Default, Not initialised, Filled | `Default` = method selected but no amount; `Not initialised` = method not set up; `Filled` = amount entered |
| `Warning` | Variant | False | False, True | Shows a warning indicator — e.g. low balance |
| `Balance CTA` | Variant | Check balance | Check balance, Avl balance, None | Balance action shown alongside the payment method |
| `Is loading` | Variant | False | False, True | Loading state while fetching payment method details |

---

## 1h. Input field - Naked Large - Pattern

**Component name:** `Input field - Naked Large - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `7936:17101`
**Use when:** A large, borderless amount or text input is needed on a payment screen — typically the primary amount entry field. This is the naked (no border) variant for full-bleed input contexts.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Default | Default, Success, Error, Warning, Disabled | Input validation state |
| `Is active` | Variant | False | False, True | Whether the input is currently focused |
| `Is filled` | Variant | False | False, True | Whether the input has a value entered |
| `Title` | Variant | False | False, True | Shows a floating label/title above the input |
| `Info message` | Variant | False | False, True | Shows a helper or error message below the input |
| `R-icon - Messages` | Variant | False | False, True | Shows an icon alongside the info message |

---

## 1i. Input field - Note - Pattern

**Component name:** `Input field - Note - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `7936:17245`
**Use when:** Allowing the user to add a note or remark to a payment. Typically a secondary input field below the amount input.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Default | Default, Error | Input validation state — only Default and Error apply for note fields |
| `Is active` | Variant | False | False, True | Whether the field is currently focused |
| `Is filled` | Variant | False | False, True | Whether the field has content |
| `Is disabled` | Variant | False | False, True | Disabled state |

---

## 1j. Payee - Pattern *(Payee type)*

**Component name:** `Payee - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `8069:28766`
**Use when:** Showing the resolved payee identity in the payment flow when the payee type is known — People (person-to-person), Brand (merchant), or Category. The `State` controls the VPA resolution stage.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | People | People, Brand, Category | The type of payee |
| `State` | Variant | No VPA | No VPA, Only VPA, QR name + VPA, Verified name, Category type | VPA resolution state |
| `Loader` | Boolean | False | True, False | Shows a loading indicator while resolving the payee |

---

## 1k. Payee - Pattern *(Payment flow)*

**Component name:** `Payee - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `8069:72871`
**Use when:** Showing the resolved payee in the context of a specific payment flow type — P2P, P2M, Bank transfer, Self transfer, Autopay, or UPI Lite. Use this variant when the payment flow drives the payee display logic.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | P2P | P2P, P2M, Bank transfer, Self transfer, Autopay, UPI Lite | The payment flow type driving the payee display |
| `State` | Variant | No VPA | No VPA, Only VPA, QR name + VPA, Verified name, Not initialised, Balance | VPA resolution and payment method state |

---

## 1l. Payee details - Pattern - Unit

**Component name:** `Payee details - Pattern - Unit`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `8069:29165`
**Use when:** Displaying the resolved payee name and VPA as a compact unit — typically shown below the amount input or in the confirmation area. The `State` reflects the current stage of VPA resolution.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | No VPA | No VPA, Only VPA, QR name + VPA, Verified name, Category name | VPA resolution stage |
| `QR name text` | Text | "QR name" | Any string | Name resolved from QR scan |
| `Verified name text` | Text | "Verified name" | Any string | Verified payee name |
| `VPA text` | Text | "9999999999@yespop" | Any string | Payee VPA address |
| `Getting name text` | Text | "Getting name" | Any string | Placeholder shown while name is being fetched |
| `Getting VPA text` | Text | "Getting VPA..." | Any string | Placeholder shown while VPA is being resolved |

---

## 1m. RCBP - Pattern - Unit *(Biller type)*

**Component name:** `RCBP - Pattern - Unit`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `12576:126460`
**Use when:** Showing the RCBP biller display unit where the biller belongs to a known Category or Brand type. The `State` controls the resolution stage of biller and user details.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Category | Category, Brand | Whether the biller is a generic category or a named brand |
| `State` | Variant | No User Detail | No User Detail, Biller - Loader, Biller + User Details | Resolution stage — no detail, loading, or fully resolved |
| `Loader` | Boolean | False | True, False | Shows a loading indicator while resolving biller details |

---

## 1n. RCBP - Pattern - Unit *(Biller details)*

**Component name:** `RCBP - Pattern - Unit`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `12576:127042`
**Use when:** Showing the RCBP biller with editable name and VPA text — use when biller details (verified name, VPA) need to be surfaced and configured. No biller type distinction in this variant.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | No User Detail | No User Detail, Biller - Loader, Biller + User Details | Resolution stage |
| `Verified name text` | Text | "Verified name" | Any string | Verified biller name |
| `VPA text` | Text | "Sunil • +918376921962" | Any string | Biller VPA or contact detail |
| `Getting name text` | Text | "Getting name" | Any string | Placeholder while name is loading |
| `Getting VPA text` | Text | "Getting VPA..." | Any string | Placeholder while VPA is loading |

---

## 1o. Payment list - Left - Small - Pattern

**Component name:** `Payment list - Left - Small - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `6973:9361`
**Use when:** Displaying a compact payment list row where the avatar or brand logo sits on the left side. Used in smaller card or inline contexts within payment screens.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Brand | Brand, People | Brand = merchant logo; People = user avatar |
| `Title above` | Variant | False | True, False | Shows a section title label above the row |
| `Is disabled` | Variant | False | False, True | Disabled state |
| `Body` | Boolean | True | True, False | Shows a secondary body line |
| `R-icon - Title` | Boolean | True | True, False | Shows an icon to the right of the title |
| `R-slot` | Boolean | False | True, False | Right slot for a custom element |
| `R-icon` | Boolean | False | True, False | Right icon |
| `R-overflow` | Boolean | False | True, False | Right overflow gradient |
| `L-icon - Title` | Boolean | False | True, False | Left icon alongside the title |
| `L-icon - Body` | Boolean | False | True, False | Left icon alongside the body |
| `R-icon - Body` | Boolean | False | True, False | Right icon alongside the body |
| `Dot` | Boolean | False | True, False | Shows a dot indicator on the row |

---

## 1p. Payment list - Right - Small - Pattern

**Component name:** `Payment list - Right - Small - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `6973:11031`
**Use when:** Displaying a compact payment list row where the avatar or brand logo sits on the right side. Used in smaller card or inline contexts within payment screens.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Brand | Brand, People | Brand = merchant logo; People = user avatar |
| `Title above` | Variant | False | True, False | Shows a section title label above the row |
| `Is disabled` | Variant | False | False, True | Disabled state |
| `Body` | Boolean | True | True, False | Shows a secondary body line |
| `L-icon - Title` | Boolean | True | True, False | Left icon alongside the title |
| `L-slot` | Boolean | False | True, False | Left slot for a custom element |
| `L-icon` | Boolean | False | True, False | Left icon |
| `L-overflow` | Boolean | False | True, False | Left overflow gradient |
| `R-icon - Title` | Boolean | False | True, False | Right icon alongside the title |
| `L-icon - Body` | Boolean | False | True, False | Left icon alongside the body |
| `R-icon - Body` | Boolean | False | True, False | Right icon alongside the body |
| `Dot` | Boolean | False | True, False | Shows a dot indicator on the row |

---

## 1q. Collect request - Popup - Pattern

**Component name:** `Collect request - Popup - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `9781:159101`
**Use when:** Presenting an in-app popup for a collect request or autopay mandate approval. The popup surfaces payee details, amount, and approve/decline actions.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Autopay request | Autopay request, Collect request | `Autopay request` = recurring mandate approval; `Collect request` = one-time payment request |
| `Expiring` | Variant | False | False, True | Indicates the request is expiring soon |
| `Verified` | Variant | False | True, False | Whether the requesting payee is verified |

---

## 1r. .Amount + Status *(Internal)*

> ⚠️ **Internal component** — `.Amount + Status` is a local building block of `Tranaction result - Pattern`. Do not use it independently — always use `Tranaction result - Pattern`.

**Component name:** `.Amount + Status`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `13318:89797`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Title` | Boolean | True | True, False | Shows the transaction amount title |
| `Subtitle` | Boolean | True | True, False | Shows a subtitle line below the amount |
| `Body` | Boolean | True | True, False | Shows a body description line |
| `Info` | Boolean | False | True, False | Shows an info/helper line |
| `L-Overflow` | Boolean | False | True, False | Left overflow gradient on the amount text |
| `R-Overflow` | Boolean | False | True, False | Right overflow gradient on the amount text |

---

## 1s. .Tranaction result - Unit *(Internal)*

> ⚠️ **Internal component** — `.Tranaction result - Unit` is a local building block of `Tranaction result - Pattern`. Do not use it independently — always use `Tranaction result - Pattern`.

**Component name:** `.Tranaction result - Unit`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `13324:90828`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Offer Split Card` | Boolean | True | True, False | Shows the offer/split card area within the result unit |

---

## 1t. Tranaction result - Pattern

**Component name:** `Tranaction result - Pattern`
**Figma page:** `↪️ ❖ Payments 🚧`
**Node ID:** `13324:91093`
**Use when:** Displaying the full transaction result screen — success, processing, pending, or failed. This pattern composes `.Amount + Status` and `.Tranaction result - Unit` into the complete result layout.

> ⛔ Never place `.Amount + Status` or `.Tranaction result - Unit` directly on screens. Always use `Tranaction result - Pattern`.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Success | Success, Processing, Pending, Failed | The transaction outcome state |

---

## Rules — Payments Patterns

- ⛔ **Always use patterns.** Never assemble individual list items, input fields, or payee displays from raw molecules. Patterns encode the correct layout, spacing, and component configuration for each payment context.
- Use `Payment list - Chevron - Pattern` for drill-down rows, `Payment list - Radio - Pattern` for selection rows, and `Payment list - Button - Pattern` for rows with inline actions.
- `Balance list - Pattern` must be used whenever a balance figure is displayed inside a list row — do not use a generic list item and manually add an amount.
- The two `Payee - Pattern` variants serve different purposes: use the *Payee type* variant (`8069:28766`) when the payee identity category (People/Brand/Category) drives the display; use the *Payment flow* variant (`8069:72871`) when the flow type (P2P/P2M/Bank transfer/etc.) drives the display.
- The two `RCBP - Pattern - Unit` variants serve different purposes: use the *Biller type* variant (`12576:126460`) when Category/Brand distinction is needed; use the *Biller details* variant (`12576:127042`) when configuring verified name and VPA text.
- `Button + Payment - Pattern` is the only correct way to surface the active payment method selector — do not build a custom payment button from raw button components.
- Use `Tranaction result - Pattern` for all transaction result screens. `.Amount + Status` and `.Tranaction result - Unit` are internal components and must never appear on screens independently.
- `Collect request - Popup - Pattern` must be used inside a Popup organism — do not place it directly on a screen frame.

---

# 2. Shop

Shop patterns cover the offer application bar, order list page card, and order history carousel used across shopping and checkout flows.

## Component Overview

| # | Component | Node ID | Type | Use case |
|---|---|---|---|---|
| 2a | `P3 offer application` | `21157:274038` | COMPONENT_SET | Offer application bar shown on the checkout/payment screen |
| 2b | `OLP card- Pattern` | `21919:74193` | COMPONENT_SET | Order list page card showing an individual order's status |
| 2c | `Order history carousel` | `21904:20680` | COMPONENT_SET | Horizontally scrollable carousel of past orders |

---

## 2a. P3 offer application

**Component name:** `P3 offer application`
**Figma page:** `↪️ ❖ Shop ✅`
**Node ID:** `21157:274038`
**Use when:** Showing the offer application row on the checkout or payment screen. This pattern communicates whether an offer has been applied, is in progress, failed, or is unavailable.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Applied | Default, Success, In-progress, Not applied, Applied, Disabled | Current state of the offer application |
| `Heading` | Text | "POPchop" | Any string | Offer name or label shown in the row |
| `Separator` | Boolean | True | True, False | Shows a divider separator in the row |
| `text - L` | Boolean | True | True, False | Shows the left text element |
| `text - R` | Boolean | True | True, False | Shows the right text element |

#### State meanings

| State | When to use |
|---|---|
| `Default` | No offer is active or selected yet |
| `Not applied` | An offer exists but has not been applied |
| `Applied` | Offer has been successfully applied to the order |
| `Success` | User has been validated and is eligible for POPchop |
| `In-progress` | User validation for POPchop is in progress |
| `Disabled` | Offer is unavailable for the current order |

---

## 2b. OLP card- Pattern

**Component name:** `OLP card- Pattern`
**Figma page:** `↪️ ❖ Shop ✅`
**Node ID:** `21919:74193`
**Use when:** Displaying a single order card on the Order List Page (OLP). Each card represents one order and communicates its fulfilment status and whether it involves a POPsplit payment.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `State` | Variant | Success | Success, In progress, Default, Cancelled | Order fulfilment status |
| `is Popsplit?` | Variant | False | True, False | Whether the order was paid using POPsplit |

#### State meanings

| State | When to use |
|---|---|
| `Default` | Order placed, no specific status yet |
| `In progress` | Order is being processed or shipped |
| `Success` | Order delivered or completed |
| `Cancelled` | Order was cancelled |

---

## 2c. Order history carousel

**Component name:** `Order history carousel`
**Figma page:** `↪️ ❖ Shop ✅`
**Node ID:** `21904:20680`
**Use when:** Displaying a horizontal carousel of recent or past orders — typically shown on a home screen widget, profile page, or the top of the order history screen. `Count` controls how many order cards are visible in the carousel.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3, Plenty | Number of order cards in the carousel. `Plenty` = 4 or more items (scrollable) |
| `Header` | Boolean | True | True, False | Shows the section header above the carousel |
| `Body` | Boolean | False | True, False | Shows a body/description line in the header |
| `CTA header` | Boolean | False | True, False | Shows a CTA link in the header (e.g. "View all") |
| `L icon - title` | Boolean | False | True, False | Left icon alongside the header title |
| `R icon - title` | Boolean | False | True, False | Right icon alongside the header title |
| `L icon - body` | Boolean | False | True, False | Left icon alongside the header body |
| `R icon - body` | Boolean | False | True, False | Right icon alongside the header body |
| `R slot` | Boolean | False | True, False | Right slot in the header for a custom element |

---

## Rules — Shop Patterns

- ⛔ **Always use patterns.** Never build offer rows, order cards, or order carousels from raw list items or card molecules.
- `P3 offer application` must reflect the real-time offer state — always set `State` to match the actual backend offer status, never leave it at `Default` on a production screen.
- `OLP card- Pattern` is the only correct component for individual order rows on the Order List Page — do not reuse generic list items.
- Use `Order history carousel` with `Count=Plenty` when there are 4 or more orders — this activates the scrollable carousel layout. Use exact counts (1, 2, 3) only when the number of orders is known and fixed.

---

# 3. Recharges

Recharge patterns cover the redirection loading state and the list + body text layout used in bottom sheet contexts.

## Component Overview

| # | Component | Node ID | Type | Use case |
|---|---|---|---|---|
| 3a | `Redirection loader` | `21916:13786` | COMPONENT | Full-screen loader shown while the user is being redirected |
| 3b | `List + body text` | `22319:191001` | COMPONENT_SET | List of items with body text — used inside a Bottom Sheet slot |

---

## 3a. Redirection loader

**Component name:** `Redirection loader`
**Figma page:** `↪️ ❖ Recharges 🚧`
**Node ID:** `21916:13786`
**Use when:** A user is being redirected to an external page or third-party flow (e.g. a biller website, bank page, or payment gateway). The loader communicates that navigation is in progress and prevents the user from interacting with the screen.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Label` | Text | "Label text" | Any string | Descriptive text shown below the loader — always replace with context-appropriate copy (e.g. "Redirecting to biller…") |

---

## 3b. List + body text

**Component name:** `List + body text`
**Figma page:** `↪️ ❖ Recharges 🚧`
**Node ID:** `22319:191001`
**Use when:** Showing a list of items each accompanied by a body text description — placed inside a Bottom Sheet slot. Typical use cases include plan details, feature breakdowns, or step-by-step instructions surfaced in a sheet.

> Use wherever a list with accompanying body text is needed. When placed inside a Bottom Sheet, use a slot (not a slot container) so the sheet height adjusts to the content.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Count` | Variant | 1 | 1, 2, 3, 4 | Number of list rows with body text to display |

---

## Rules — Recharges Patterns

- ⛔ **Always use patterns.** Never build a redirection loading state or a list-with-body layout from raw components.
- `Redirection loader` must always have a meaningful `Label` — never ship it with the default placeholder "Label text".
- `List + body text` can be placed wherever a list with body text is needed — in a Bottom Sheet slot, a screen section, or any other layout context.
- Use the smallest `Count` value that accurately represents the number of items — do not pad with empty rows.

---

# 4. Account Management

Account management patterns cover the linked payment account card used across account management screens — showing bank accounts and RuPay cards with their status, balance, and authentication settings.

## Component Overview

| # | Component | Node ID | Type | Use case |
|---|---|---|---|---|
| 4a | `Account Management - Pattern` | `10221:134233` | COMPONENT_SET | Linked payment account card — Bank or RuPay |

---

## 4a. Account Management - Pattern

**Component name:** `Account Management - Pattern`
**Figma page:** `↪️ ❖ Account Management ✅`
**Node ID:** `10221:134233`
**Use when:** Displaying a linked bank account or RuPay card in the account management screen. The pattern handles the collapsed/expanded state, primary account designation, balance visibility, and biometric auth status.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Bank | Bank, RuPay | The type of linked account |
| `Open` | Variant | False | True, False | Whether the account card is expanded to show full details |
| `Primary` | Variant | True | True, False | Whether this is the user's primary payment account. Only applicable to `Type=Bank` — RuPay accounts are always non-primary |
| `Balance` | Variant | False | True, False | Whether the account balance is visible |
| `isBioauth Enabled` | Variant | False | True, False | Whether biometric authentication is enabled for this account |

### Variant constraints

| Type | Primary=True available? | Notes |
|---|---|---|
| Bank | ✅ Yes | Bank accounts can be set as primary |
| RuPay | ❌ No | RuPay cards are never the primary account — always use `Primary=False` |

---

## Rules — Account Management Patterns

- ⛔ **Always use patterns.** Never build an account management card from raw list items or card molecules.
- `Primary=True` is only valid for `Type=Bank` — never set `Primary=True` on a RuPay account.
- Use `Open=True` only for the currently selected or active account card. All other cards in the list should default to `Open=False`.
- `Balance=True` should reflect whether the user has chosen to reveal their balance — mirror the actual visibility state, do not default to visible.

---

# 5. Profile QR

Profile QR patterns cover the QR code card, the profile card display pattern, and their internal building blocks — used on the Profile QR screen where users share their payment identity via QR or a profile card.

## Component Overview

| # | Component | Node ID | Type | Use case |
|---|---|---|---|---|
| 5a | `QR - Card` | `10490:122903` | COMPONENT_SET | QR code display card — plain QR or amount-prefixed QR |
| 5b | `.Component` | `10579:136935` | COMPONENT *(Internal)* | Internal icon-slot building block — local component of `Profile Card Pattern` |
| 5c | `.Profile Card Unit` | `10490:108425` | COMPONENT *(Internal)* | Internal profile display unit — local component of `Profile Card Pattern` |
| 5d | `Profile Card Pattern` | `13139:14480` | COMPONENT_SET | Full profile card — avatar, name, phone number, and UPI ID |

---

## 5a. QR - Card

**Component name:** `QR - Card`
**Figma page:** `↪️ ❖ Profile QR ✅`
**Node ID:** `10490:122903`
**Use when:** Displaying the user's UPI QR code on the Profile QR screen. Use `Card=QR Code` for a standard scannable QR and `Card=Amount - QR Code` when a preset amount is embedded in the QR.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Card` | Variant | QR Code | QR Code, Amount - QR Code | Type of QR card — standard or amount-prefixed |
| `Buttons` | Variant | 2 | 2, Plenty | Number of action buttons below the QR. `Plenty` = 3 or more |

---

## 5b. .Component *(Internal)*

> ⚠️ **Internal component** — `.Component` is a local building block of `Profile Card Pattern`. Do not use it independently.

**Component name:** `.Component`
**Figma page:** `↪️ ❖ Profile QR ✅`
**Node ID:** `10579:136935`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `R-Icon - Title` | Boolean | False | True, False | Right icon alongside the title |
| `L-Icon - Title` | Boolean | False | True, False | Left icon alongside the title |
| `R-Icon - Body` | Boolean | False | True, False | Right icon alongside the body |
| `L-Icon - Body` | Boolean | False | True, False | Left icon alongside the body |

---

## 5c. .Profile Card Unit *(Internal)*

> ⚠️ **Internal component** — `.Profile Card Unit` is a local building block of `Profile Card Pattern`. Do not use it independently — always use `Profile Card Pattern`.

**Component name:** `.Profile Card Unit`
**Figma page:** `↪️ ❖ Profile QR ✅`
**Node ID:** `10490:108425`

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `UPI ID` | Boolean | True | True, False | Shows the user's UPI ID below their name or number |

---

## 5d. Profile Card Pattern

**Component name:** `Profile Card Pattern`
**Figma page:** `↪️ ❖ Profile QR ✅`
**Node ID:** `13139:14480`
**Use when:** Displaying the user's profile card on the Profile QR screen — combining their avatar, display name (if available), phone number, and UPI ID.

### Properties

| Property | Type | Default | Options | Notes |
|---|---|---|---|---|
| `Type` | Variant | Name + Number | Name + Number, Number Only | Whether to show the user's name alongside their number, or number only |
| `Linked` | Variant | True | True, False | Whether a payment account is linked to this profile |
| `Avatar` | Variant | Picture | Picture, Initials, POP | Avatar display style — see constraints below |

### Avatar variant constraints

| Type | Available Avatar options |
|---|---|
| `Name + Number` | Picture, Initials |
| `Number Only` | Picture, POP |

> `Initials` is only available when `Type=Name + Number`. `POP` avatar is only available when `Type=Number Only`.

---

## Rules — Profile QR Patterns

- ⛔ **Never use `.Component` or `.Profile Card Unit` directly** — they are internal local components of `Profile Card Pattern`. Always use `Profile Card Pattern` on screens.
- Set `Avatar` only to values valid for the selected `Type` — do not use `Initials` with `Number Only` or `POP` with `Name + Number`.
- Use `QR - Card` with `Card=Amount - QR Code` only when a specific payment amount is being requested — for general receive-money flows, use `Card=QR Code`.
- `Buttons=Plenty` accommodates 3 or more action buttons below the QR card. Use `Buttons=2` for the standard two-button layout.

---
