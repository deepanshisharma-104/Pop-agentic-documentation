# Atoms — POP Design System

Atoms are the lowest-level building blocks of the POP Design System (Layer 1). Every molecule and organism is composed from these. Always start here and compose upward.

---

## 1. Slot & Slot Container

### Slot

A **Slot** is a blank, fixed-size placeholder with no visual style of its own. Its sole purpose is to receive a component swapped into it.

**Rules**
- Slots are used **directly** in layouts — place the Slot component, then swap a component inside it.
- Use the component swap property to replace the Slot with the desired component (icon, avatar, badge, image, etc.).
- A Slot has no visual output on its own — it inherits entirely from the swapped component.

### Slot Container

A **Slot Container** wraps a Slot inside a structural layer. It is never the final drop target.

**Rules**
- Slot Containers are **never** placed or used directly in a layout as a content target.
- To add content: double-click into the container → navigate inside → select the Slot → swap the component inside the Slot.
- The container manages structural layout. The Slot inside it manages the content.
- Never swap content directly onto a Slot Container — always go one level deeper to the Slot.

---

## 2. Icons

### 2.1 Icon Categories

| Category | Figma Prefix | Export Folder | Format | Usage Context |
|---|---|---|---|---|
| Outline Icons | `Icons/` | `icons/outline/` | SVG | Default style — navigation, actions, UI controls, all functional use |
| Filled Icons | `Filled Icons/` | `icons/filled/` | SVG | Active / selected / toggled-on states only |
| Inline Illustration Icons | `Illustration/` | `icons/illustration/` | PNG | Small decorative accents used inline within components — status moments, reward accents, contextual icons. **Not the same as Illustration Assets (Section 4).** |
| Logos | `Logos/` | `icons/logos/` | SVG | UPI, UPI Lite, UPI Autopay, POP brand marks |
| UPI App Logos | `Banks/` | `icons/upi-logos/` | PNG | Payment app identifiers — pickers, transaction history |

---

### 2.2 Icon Wrapper System

The **Icon Wrapper** is the container that sizes and renders every icon. Always place icons inside a wrapper — never drop raw icon components directly into layouts.

#### Wrapper Sizes

| Wrapper Size | Icon Size Inside | Usage Context |
|---|---|---|
| 16px | 16×16 | Inline with small text, compact list rows, tag suffixes |
| 20px | 20×20 | Inline with body text (Label/Small, Paragraph/Small) |
| 24px | 24×24 | **Default** — app bar, section rows, input fields, standard buttons |
| 28px | 28×28 | Slightly emphasised — section headers, feature labels |
| 32px | 32×32 | Medium emphasis — card headers, onboarding steps |
| 40px | 40×40 | Touch target — tappable icon actions (see Icon Button below) |
| 48px | 48×48 | Large emphasis — empty states, hero spots, UPI app logos |

#### Icon Button

The **Icon Button** is the tappable variant of the Icon Wrapper. It provides a 40px touch target and is used wherever an icon triggers an action.

| Variant | Width | Use |
|---|---|---|
| Count = 1 | 40px | Single icon action — close, share, more |
| Count = 2 | 86px | Two icon actions side by side — share + bookmark |
| Count = 3 | 132px | Three icon actions — app bar trailing group |
| Count = 4 | 178px | Four icon actions — full action row |

**Rules**
- Use **Icon Button** (not Icon Wrapper) wherever the icon is tappable.
- Icon Wrapper is display-only. Icon Button provides the correct 40px touch target.
- Never manually resize a raw icon — always use a wrapper size from the table above.
- **Missing icon?** If the required icon does not exist in the library yet, use `Icons/placeholder` as a temporary stand-in. Replace it with the correct icon once it has been added to the library. Never use a random icon as a substitute — `Icons/placeholder` signals intent clearly to reviewers and developers.

---

### 2.3 Icon Name Mapping — Core Set

> Only frequently used icons are mapped here. Do not document the entire library. Each entry gives the Figma asset name and code file name so icon selection can be resolved in a single lookup.
>
> **Default wrapper size is always 24px.** The actual size rendered in a design depends on the Icon Wrapper size used inside the component — the component controls the wrapper, not the icon itself. Do not override the wrapper size manually.

---

### Outline Icons

Outline icons are the default icon style — used for navigation, actions, UI controls, and all functional contexts. Prefix: `Icons/` · Format: SVG · Export folder: `icons/outline/`

#### Navigation & Structure

| Use Case | Figma Name | Code File Name | Category |
|---|---|---|---|
| Back navigation | `Icons/Type=Back Icon` | `back-icon` | Outline |
| Close / dismiss | `Icons/Type=Cancel` | `cancel` | Outline |
| Cross / remove | `Icons/Cross` | `cross` | Outline |
| Arrow down | `Icons/Arrow` → `Arrow=Down` | `arrow-down` | Outline |
| Arrow up | `Icons/Arrow` → `Arrow=Up` | `arrow-up` | Outline |
| Arrow left | `Icons/Arrow` → `Arrow=Left` | `arrow-left` | Outline |
| Arrow right | `Icons/Arrow` → `Arrow=Right` | `arrow-right` | Outline |
| Chevron down | `Icons/Chevron` → `Chevron=Down` | `chevron-down` | Outline |
| Chevron up | `Icons/Chevron` → `Chevron=Up` | `chevron-up` | Outline |
| Chevron left | `Icons/Chevron` → `Chevron=Left` | `chevron-left` | Outline |
| Chevron right | `Icons/Chevron` → `Chevron=Right` | `chevron-right` | Outline |
| Home | `Icons/home-03` | `home-03` | Outline |
| More options (horizontal) | `Icons/dots-horizontal` | `dots-horizontal` | Outline |
| More options (vertical) | `Icons/dots-vertical` | `dots-vertical` | Outline |
| Menu / hamburger | `Icons/menu-01` | `menu-01` | Outline |

#### Search & Input

| Use Case | Figma Name | Code File Name | Category |
|---|---|---|---|
| Search | `Icons/search-md` | `search-md` | Outline |
| Show password | `Icons/eye` | `eye` | Outline |
| Hide password | `Icons/eye-off` | `eye-off` | Outline |
| Edit / pencil | `Icons/pencil-line-edit` | `pencil-line-edit` | Outline |
| Copy | `Icons/copy-06` | `copy-06` | Outline |
| Delete / trash | `Icons/trash-04` | `trash-04` | Outline |
| Add / plus | `Icons/plus` | `plus` | Outline |
| Minus / subtract | `Icons/minus` | `minus` | Outline |
| Camera | `Icons/camera` | `camera` | Outline |

#### Status & Feedback

| Use Case | Figma Name | Code File Name | Category |
|---|---|---|---|
| Check / success | `Icons/check` | `check` | Outline |
| Check circle (filled) | `Filled Icons/check-circle-Filled` | `check-circle-filled` | Filled |
| Verified badge | `Icons/check-verified-02` | `check-verified-02` | Outline |
| Alert circle | `Icons/alert-circle` | `alert-circle` | Outline |
| Alert triangle | `Icons/alert-triangle` | `alert-triangle` | Outline |
| Info | `Icons/info-circle` | `info-circle` | Outline |
| Pending | `Icons/Pending` | `pending` | Outline |
| Refresh / retry | `Icons/refresh-ccw-01` | `refresh-ccw-01` | Outline |
| Loading | `Icons/loading-02` | `loading-02` | Outline |

#### Payments & Finance

| Use Case | Figma Name | Code File Name | Category |
|---|---|---|---|
| Send money | `Icons/send-01` | `send-01` | Outline |
| Request money | `Icons/Request` | `request` | Outline |
| Transfer | `Icons/transfer` | `transfer` | Outline |
| Rupee / amount | `Icons/currency-rupee` | `currency-rupee` | Outline |
| Bank | `Icons/bank` | `bank` | Outline |
| Credit card | `Icons/credit-card-02` | `credit-card-02` | Outline |
| Autopay | `Icons/Autopay` | `autopay` | Outline |
| UPI | `Icons/upi` | `upi` | Outline |
| UPI (active/filled) | `Filled Icons/upi` | `upi-filled` | Filled |
| Scan QR | `Icons/scan QR` | `scan-qr` | Outline |
| QR code | `Icons/qr-code-01` | `qr-code-01` | Outline |
| Receipt | `Icons/receipt-check` | `receipt-check` | Outline |
| Cashback | `Icons/cashback` | `cashback` | Outline |

#### Shopping & Commerce

| Use Case | Figma Name | Code File Name | Category |
|---|---|---|---|
| Shopping cart | `Icons/shopping-cart-03` | `shopping-cart-03` | Outline |
| Shopping bag | `Icons/shopping-bag-02` | `shopping-bag-02` | Outline |
| Gift / offers | `Icons/gift-01` | `gift-01` | Outline |
| Sale | `Icons/sale-01` | `sale-01` | Outline |
| Wishlist off | `Icons/Wishlist` → `State=False` | `wishlist-off` | Outline |
| Wishlist on | `Icons/Wishlist` → `State=True` | `wishlist-on` | Filled |
| Heart | `Icons/Heart` | `heart` | Outline |
| Heart (filled) | `Filled Icons/Heart` | `heart-filled` | Filled |
| Store | `Icons/store` | `store` | Outline |
| Delivery | `Icons/delivery` | `delivery` | Outline |

#### Account & Profile

| Use Case | Figma Name | Code File Name | Category |
|---|---|---|---|
| User / profile | `Icons/user-01` | `user-01` | Outline |
| Settings | `Icons/settings-02` | `settings-02` | Outline |
| Help / support | `Icons/help-circle` | `help-circle` | Outline |
| Headphones | `Icons/Headphones` | `headphones` | Outline |
| Notifications | `Icons/bell-ringing-02` | `bell-ringing-02` | Outline |
| Log out | `Icons/log-out-01` | `log-out-01` | Outline |
| Transaction history | `Icons/transaction history` | `transaction-history` | Outline |
| Saved | `Icons/saved` | `saved` | Outline |
| Share | `Icons/share-01` | `share-01` | Outline |
| Download | `Icons/download-01` | `download-01` | Outline |

#### Security & Auth

| Use Case | Figma Name | Code File Name | Category |
|---|---|---|---|
| Passcode lock | `Icons/passcode-lock` | `passcode-lock` | Outline |
| Shield / secure | `Icons/shield-tick` | `shield-tick` | Outline |
| Face ID | `Icons/face-id` | `face-id` | Outline |
| Fingerprint | `Icons/fingerprint` | `fingerprint` | Outline |
| Block | `Icons/ block` | `block` | Outline |

---

### Filled Icons

Filled icons are the active/selected/toggled-on counterpart of outline icons. Prefix: `Filled Icons/` · Format: SVG · Export folder: `icons/filled/`

**When to use:**
- ✅ Use a filled icon **only when it is explicitly specified in the UI design** or the design file shows the filled variant at that position.
- ⛔ **Never default to a filled icon** — if the design does not explicitly call for it, always use the outline (`Icons/`) version as the fallback.

#### Status & Feedback

| Use Case | Figma Name | Code File Name |
|---|---|---|
| Success / task completed | `Filled Icons/check-circle-Filled` | `check-circle-filled` |
| Alert circle (error/warning active) | `Filled Icons/alert-circle-Filled` | `alert-circle-filled` |
| Alert triangle (warning active) | `Filled Icons/alert-triangle-Filled` | `alert-triangle-filled` |
| Alert hexagon (critical warning active) | `Filled Icons/alert-hexagon-Filled` | `alert-hexagon-filled` |
| Verified badge (active) | `Filled Icons/check-verified-02-Filled` | `check-verified-02-filled` |
| Quick action / instant (active) | `Filled Icons/zap-Filled` | `zap-filled` |

#### Notifications

| Use Case | Figma Name | Code File Name |
|---|---|---|
| Bell / notifications (active) | `Filled Icons/bell-02-Filled` | `bell-02-filled` |
| Bell ringing / new notification (active) | `Filled Icons/bell-ringing-02-Filled` | `bell-ringing-02-filled` |
| Bell ringing alt (active) | `Filled Icons/bell-ringing-04-Filled` | `bell-ringing-04-filled` |
| Notifications off (active) | `Filled Icons/bell-off-02-Filled` | `bell-off-02-filled` |

#### Payments & Finance

| Use Case | Figma Name | Code File Name |
|---|---|---|
| UPI active / selected | `Filled Icons/upi` | `upi-filled` |
| Bank (active / selected) | `Filled Icons/bank-Filled` | `bank-filled` |
| Credit card (active) | `Filled Icons/credit-card-02-Filled` | `credit-card-02-filled` |
| Credit card blocked (active) | `Filled Icons/credit-card-x-Filled` | `credit-card-x-filled` |
| Receipt (active) | `Filled Icons/receipt-check-Filled` | `receipt-check-filled` |
| Gift / offers (active) | `Filled Icons/gift-01-Filled` | `gift-01-filled` |

#### Shopping & Commerce

| Use Case | Figma Name | Code File Name |
|---|---|---|
| Shopping cart (active) | `Filled Icons/shopping-cart-03-Filled` | `shopping-cart-03-filled` |
| Cart (active) | `Filled Icons/Cart-Filled` | `cart-filled` |
| Sale tag (active) | `Filled Icons/sale-01-Filled` | `sale-01-filled` |
| Sale alt (active) | `Filled Icons/sale-03-Filled` | `sale-03-filled` |
| Liked / favourited | `Filled Icons/Heart` | `heart-filled` |
| Star / rating (active) | `Filled Icons/star-01` | `star-01-filled` |
| Flagged (active) | `Filled Icons/flag-02` | `flag-02-filled` |

#### Account & Profile

| Use Case | Figma Name | Code File Name |
|---|---|---|
| User / profile (active) | `Filled Icons/user-03-Filled` | `user-03-filled` |
| User circle (active) | `Filled Icons/user-circle-Filled` | `user-circle-filled` |
| Settings (active) | `Filled Icons/settings-02-Filled` | `settings-02-filled` |

#### Actions

| Use Case | Figma Name | Code File Name |
|---|---|---|
| Share (active) | `Filled Icons/share-06-Filled` | `share-06-filled` |
| Delete (active) | `Filled Icons/trash-04-Filled` | `trash-04-filled` |
| Add / plus circle (active) | `Filled Icons/plus-circle-Filled` | `plus-circle-filled` |
| Upload (active) | `Filled Icons/upload-03-Filled` | `upload-03-filled` |
| Show / reveal (active) | `Filled Icons/eye-Filled` | `eye-filled` |
| Hide (active) | `Filled Icons/eye-off-Filled` | `eye-off-filled` |

#### Placeholder

| Use Case | Figma Name | Code File Name |
|---|---|---|
| Placeholder (filled variant) | `Filled Icons/Placeholder - filled` | `placeholder-filled` |

---

### Inline Illustration Icons

> ⚠️ **Do not confuse with Illustration Assets (Section 4).** Inline Illustration Icons are small PNG accents used inside components — they appear as instance swaps within UI elements (e.g. an icon slot inside a card, a status accent inside a list row). Illustration Assets are large full-screen PNGs placed directly on a page as standalone visual moments.

Inline Illustration Icons are expressive PNG assets used as decorative accents, status moments, and reward illustrations embedded directly within UI components. They are not functional icons — never use them as interactive controls. Prefix: `Illustration/` · Format: PNG · Export folder: `icons/illustration/`

| Use Case | Figma Name | Notes |
|---|---|---|
| Generic fallback / default slot | `Illustration/Default` | Use when no specific illustration applies |
| Success / verified | `Illustration/Check-02` | Task completed, verification passed |
| Pending / in-progress | `Illustration/Pending` | Awaiting action or processing |
| Error / failed | `Illustration/Error` | Failure states |
| Offer percentage | `Illustration/Offers-%` | Discount / cashback offer contexts |
| Cash / money moment | `Illustration/Cash-Money` | Savings, cashback credited, reward screens |
| POPcoin bag | `Illustration/POPcoin bag` | POPcoin earned / reward moments |
| Coin tilted | `Illustration/coin tilted` | Coin-related contextual moments |
| UPI Lite | `Illustration/UPI Lite` | UPI Lite onboarding and feature screens |
| Shield / security | `Illustration/Shield` | Security, privacy, or protection contexts |
| Payin3 split | `Illustration/Payin3-split` | Buy-now-pay-later split screens |
| Decorative star | `Illustration/Decorative star` | Celebratory or reward accent |
| Star shape | `Illustration/Star-Shape` | Generic decorative star accent |
| Faves | `Illustration/Faves` | Favourites / saved items context |

> ⛔ Never re-color illustration icons — they are static PNG assets. Do not apply color tokens or CSS filters.
> If the required illustration is not yet in the library, use `Illustration/Default` as a temporary placeholder.

---

### Logos

Logos are brand mark SVGs for UPI payment rails and the POP brand. Use only in their exact form — no recoloring, no resizing outside the wrapper system. Prefix: `Logos/` · Format: SVG · Export folder: `icons/logos/`

| Use Case | Figma Name | Code File Name | Notes |
|---|---|---|---|
| UPI payment rail mark | `Logos/upi` | `upi` | Used in payment method pickers, transaction confirmation, and UPI identity surfaces |
| UPI Lite mark | `Logos/upi lite` | `upi-lite` | Used on UPI Lite balance, setup, and feature surfaces |
| UPI Autopay mark | `Logos/upi autopay` | `upi-autopay` | Used on autopay setup, mandate management, and recurring payment surfaces |
| POP brand mark | `Logos/Pop` | `pop` | Used for POP brand moments, POP identity surfaces, and co-branding |

> ⛔ Never re-color logos — they are governed brand marks. Always use at the exact size specified in the design.
> ⛔ Do not substitute a logo with an icon or illustration — they serve different purposes and are not interchangeable.

---

### 2.4 How AI Should Use Icons in Code

When selecting and rendering an icon, follow these steps in order:

**Step 1 — Look up the icon in the mapping table**
Find the plain-language use case (e.g. "Search", "Send money"). Read off the Figma name, code file name, category, and default wrapper size from the table above.

**Step 2 — Identify the category and import path**

| Category | Import From |
|---|---|
| `Icons/` | `icons/outline/` |
| `Filled Icons/` | `icons/filled/` |
| `Illustration/` | `icons/illustration/` (PNG) — Inline Illustration Icons only; for full-screen Illustration Assets see Section 4 |
| `Logos/` | `icons/logos/` |
| `Banks/` | `icons/upi-logos/` (PNG) |

**Step 3 — Apply the correct wrapper size**
Icons default to a **24px** wrapper. The actual size used in a design is always determined by the Icon Wrapper size placed inside the component — not by the icon itself. Use the nearest value from: `16 / 20 / 24 / 28 / 32 / 40 / 48`. Never freehand resize an icon outside the wrapper system.

**Step 4 — Apply colour via token, never hardcoded**
```
// ✅ Correct
color: token('icon/primary')

// ❌ Wrong
color: '#1A1A1A'
```

**Step 5 — Tappable icon? Use Icon Button, not Icon Wrapper**
Icon Wrapper = display only.
Icon Button = tappable, 40px touch target.

---

## 3. UPI App Logos

UPI app logos are **third-party brand assets**. They represent external payment applications and must be used with strict fidelity.

### Rules

- **Never re-color** — these are third-party brand marks. Do not apply any color token, tint, or CSS filter.
- **Always fixed size** — always render at **48×48px**. Do not scale up or down.
- **No color tokens** — do not bind to any design token. They are static assets.
- **PNG only** — do not convert to SVG or attempt to edit the asset.
- Figma prefix: `Banks/` · Export folder: `icons/upi-logos/`

### UPI App Logo Table

| App Name | Figma Name | File Name |
|---|---|---|
| PhonePe | `Banks/phonepe-square` | `phonepe-square` |
| Google Pay | `Banks/googlepay-square` | `googlepay-square` |
| Paytm | `Banks/paytm-square` | `paytm-square` |
| CRED | `Banks/cred-square` | `cred-square` |
| Amazon Pay | `Banks/amazonpay-square` | `amazonpay-square` |
| WhatsApp Pay | `Banks/whatsapp-square` | `whatsapp-square` |
| BHIM | `Banks/bhim-square` | `bhim-square` |
| Flipkart | `Banks/flipkart-square` | `flipkart-square` |
| Groww | `Banks/groww-square` | `groww-square` |
| Slice | `Banks/slice-square` | `slice-square` |
| Jupiter | `Banks/jupiter-square` | `jupiter-square` |
| Samsung Pay | `Banks/samsung-pay-square` | `samsung-pay-square` |
| Navi | `Banks/navi-square` | `navi-square` |
| FamPay | `Banks/fampay-square` | `fampay-square` |
| Tata Neu | `Banks/tataneu-square` | `tataneu-square` |
| MobiKwik | `Banks/mobiqwik-square` | `mobiqwik-square` |
| Bajaj Finserv | `Banks/bajaj-finserv-square` | `bajaj-finserv-square` |
| Kiwi | `Banks/kiwi-square` | `kiwi-square` |
| Dhani | `Banks/dhani-square` | `dhani-square` |
| Make My Trip | `Banks/make-my-trip-square` | `make-my-trip-square` |
| HDFC Bank | `Banks/hdfc-bank-apps-square` | `hdfc-bank-apps-square` |
| ICICI Bank | `Banks/icici-bank-apps-square` | `icici-bank-apps-square` |
| Axis Bank | `Banks/axis-bank-apps-square` | `axis-bank-apps-square` |
| Kotak Mahindra | `Banks/kotak-mahindra-bank-apps-square` | `kotak-mahindra-bank-apps-square` |
| State Bank of India | `Banks/state-bank-of-india-square` | `state-bank-of-india-square` |
| Yes Bank | `Banks/yes-bank-apps-square` | `yes-bank-apps-square` |
| IDFC Bank | `Banks/idfc-bank-apps-square` | `idfc-bank-apps-square` |
| IndusInd Bank | `Banks/indusind-bank-apps-square` | `indusind-bank-apps-square` |
| Bank of Baroda | `Banks/bank-of-baroda-apps-square` | `bank-of-baroda-apps-square` |
| Canara Bank | `Banks/canara-bank-apps-square` | `canara-bank-apps-square` |
| Punjab National Bank | `Banks/punjab-national-bank-square` | `punjab-national-bank-square` |
| Union Bank | `Banks/union-bank-apps-square` | `union-bank-apps-square` |
| Bank of India | `Banks/bank-of-india-apps-square` | `bank-of-india-apps-square` |
| Central Bank of India | `Banks/central-bank-of-india-square` | `central-bank-of-india-square` |
| AU Small Finance Bank | `Banks/au-small-finance-bank-apps-square` | `au-small-finance-bank-apps-square` |
| Federal Bank | `Banks/federal-bank-apps-square` | `federal-bank-apps-square` |
| South Indian Bank | `Banks/south-indian-bank-apps-square` | `south-indian-bank-apps-square` |
| IDBI Bank | `Banks/idbi-bank-apps-square` | `idbi-bank-apps-square` |
| DBS Digibank | `Banks/dbs-digibank-apps-square` | `dbs-digibank-apps-square` |
| RBL Bank | `Banks/rbl-bank-apps-square` | `rbl-bank-apps-square` |
| Airtel Payments Bank | `Banks/airtel-payments-bank-square` | `airtel-payments-bank-square` |
| Jio Payments Bank | `Banks/jio-payments-bank-apps-square` | `jio-payments-bank-apps-square` |
| Fino Payment Bank | `Banks/fino-payment-bank-apps-square` | `fino-payment-bank-apps-square` |
| RuPay | `Banks/RuPay` | `rupay` |
| UPI | `Banks/UPI` | `upi` |
| BBPS | `Banks/BBPS` | `bbps` |
| Jio | `Banks/Jio` | `jio` |
| Airtel | `Banks/airtel` | `airtel` |
| Vodafone | `Banks/Vodafone` | `vodafone` |
| MTNL | `Banks/Mtnl` | `mtnl` |
| BSNL | `Banks/BSNL` | `bsnl` |
| POP | `Bank/POP` | `pop` |
| Placeholder | `Banks/Placeholder` | `placeholder` |

---

## 4. Illustration Assets

> ⚠️ **Do not confuse with Inline Illustration Icons (Section 2.3).** Illustration Assets are static PNG illustrations placed as instances on a page or inside a bottom sheet slot. They are **not** used as instance swaps inside general component icon slots.

Illustration Assets come in **two fixed sizes** — each is a **distinct image**, not just a scaled version of the other. They differ in both size and background treatment. Both sizes are static and must never be scaled.

### Sizes

| Type | Size | Background | Where Used |
|---|---|---|---|
| **Full-page empty state** | `393 × 209 px` | ✅ With background | Placed on full-screen empty states — e.g. no transactions, no saved cards, onboarding illustration moments |
| **Bottom sheet empty state** | `100 × 100 px` | ⛔ No background (transparent) | Placed inside the bottom sheet slot image area for bottom sheet empty states |

> ⛔ **Never scale either size.** Both dimensions are fixed. Do not resize, crop, or stretch an illustration asset — always use it at its defined size.
> ⛔ **Do not substitute one size for the other.** They are different images — using the 393×209 asset in a bottom sheet or the 100×100 asset on a full-page empty state is incorrect.

### Rules

- **PNG only** — always use as PNG. Do not export or use as SVG.
- **Never re-color** — do not apply color tokens or CSS filters.
- **No color tokens** — static assets, no token binding.
- **Not for functional use** — never replace an outline or filled icon with an illustration asset.
- **Two fixed sizes only** — `393×209px` (with background) for full-page empty states; `100×100px` (no background) for bottom sheet empty states. No other sizes are permitted.
- **Each size is a separate asset** — they are not the same image at different scales. Always pick the asset that matches the context.
- Figma prefix: `Illustration/` · Export folder: `icons/illustration/`

### Illustration Assets Table

#### Transaction & Payment States

| Name | Figma Component | Available Sizes (size · bg) | Primary Use Case |
|---|---|---|---|
| Completed | `Completed` | 100×100px (no bg) · 393×209px (with bg) | Payment success, transaction complete |
| Pending | `Pending` | 100×100px (no bg) · 393×209px (with bg) | Payment in progress, processing |
| Failed | `Failed` | 100×100px only (no bg) | Payment failed |
| Error | `Error` | 100×100px (no bg) · 393×209px (with bg) | Generic error state |
| Something went wrong | `Something went wrong` | 100×100px (no bg) · 393×209px (with bg) | System error, fallback empty state |

#### Banking & Finance

| Name | Figma Component | Available Sizes (size · bg) | Primary Use Case |
|---|---|---|---|
| Vault | `Vault` / `vault` | 100×100px (no bg) · 393×209px (with bg) | Savings, secure storage |
| Bank | `Bank` | 100×100px (no bg) · 393×209px (with bg) | Bank account linking, bank details |
| Passbook | `Passbook` | 100×100px (no bg) · 393×209px (with bg) | Transaction history, statement view |
| Bank transfer | `Bank transfer` | 100×100px (no bg) · 393×209px (with bg) | NEFT / IMPS transfer flow |
| Card stack | `Card stack` | 100×100px (no bg) · 393×209px (with bg) | Card management, multiple cards |
| Self transfer | `Self transfer` | 100×100px (no bg) · 393×209px (with bg) | Own account transfer |

#### KYC & Verification

| Name | Figma Component | Available Sizes (size · bg) | Primary Use Case |
|---|---|---|---|
| Application declined | `Application declined` | 100×100px (no bg) · 393×209px (with bg) | KYC / credit application rejected |
| Application withheld | `Application withheld` | 100×100px (no bg) · 393×209px (with bg) | Application on hold |
| Video KYC under review | `Video KYC under review` | 100×100px (no bg) · 393×209px (with bg) | vKYC submitted, awaiting review |
| KYC not completed | `KYC not completed` / `vKYC not completed` | 100×100px (no bg) · 393×209px (with bg) | Incomplete KYC |
| Aadhaar service unavailable | `Aadhaar service unavailable` | 100×100px (no bg) · 393×209px (with bg) | Aadhaar API down |
| Bank servers unavailable | `Bank servers unavailable` | 100×100px (no bg) · 393×209px (with bg) | Bank API timeout |
| Blocked VPA | `Blocked VPA` | 100×100px (no bg) · 393×209px (with bg) | UPI ID blocked state |

#### Utility & Contextual

| Name | Figma Component | Available Sizes (size · bg) | Primary Use Case |
|---|---|---|---|
| Mailbox | `Mailbox` | 100×100px (no bg) · 393×209px (with bg) | Email verification, empty inbox |
| Notepad | `Notepad` | 100×100px (no bg) · 393×209px (with bg) | Notes, terms and conditions |
| Phonebook | `Phonebook` | 100×100px (no bg) · 393×209px (with bg) | Contacts, phone number entry |
| Maps | `Maps` | 100×100px (no bg) · 393×209px (with bg) | Address selection, location features |
| OTP | `OTP` | 100×100px (no bg) · 393×209px (with bg) | OTP entry screen |
| Router | `Router` | 100×100px (no bg) · 393×209px (with bg) | Network / connectivity error |
| Camera | `Camera` | 100×100px (no bg) · 393×209px (with bg) | Camera permission request |
| Torch | `Torch` | 100×100px (no bg) · 393×209px (with bg) | Torch / flashlight feature |
| Folder | `Folder` | 100×100px (no bg) · 393×209px (with bg) | Empty list or document state |
| Exit | `Exit` | 100×100px (no bg) · 393×209px (with bg) | Logout, exit confirmation |
| Stopwatch | `Stopwatch` | 100×100px (no bg) · 393×209px (with bg) | Session timeout, expiry |
| Record player | `Record-player` / `Recordplayer` | 100×100px (no bg) · 393×209px (with bg) | Brand / lifestyle editorial moment |
| Settings | `Settings` | 100×100px only (no bg) | Settings empty state |
