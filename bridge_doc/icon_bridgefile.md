# POP Design System — Icon Bridge File

> **Purpose:** Maps the POP Design System icon specifications (Figma / `atoms.md`) to the Kotlin/Jetpack Compose codebase (`PopIcons.kt`, `IconName.kt`, `PopIconResources.kt`).
> **Source — Design:** `atoms.md` (Sections 2–4)
> **Source — Code:** `POP Codebase/theme/PopIcons.kt`, `POP Codebase/theme/IconName.kt`, `POP Codebase/theme/PopIconResources.kt`
> **Icon previews:** extracted from `bridge_doc/icon_files.md`

## 1. Icon Size Token Mapping

> **Design rule (atoms.md §2.2):** Always place icons inside an Icon Wrapper — never drop raw icon components directly into layouts. Default wrapper is always 24px. The component controls the wrapper size, not the icon.

### 1.1 Icon Display Sizes

| Icon Size Variable Name (Codebase) | Icon Size (Codebase) | Codebase File | Icon Wrapper Size in Design System | Design System File | Icon Path |
|---|---|---|---|---|---|
| `PopIcons.sizeSmall` | `16.dp` (16×16dp) | `PopIcons.kt` | 16px — inline with small text, compact list rows, tag suffixes | `atoms.md` | [bridge_doc/icon_files.md] |
| *(no direct token — use custom)* ⚠️ | `20.dp` | `PopIcons.kt` | 20px — inline with body text (Label/Small, Paragraph/Small) | `atoms.md` | [bridge_doc/icon_files.md] |
| `PopIcons.sizeMedium` / `PopIcons.defaultSize` | `24.dp` (24×24dp)() | `PopIcons.kt` | 24px — **default** — app bar, section rows, input fields, standard buttons | `atoms.md` | [bridge_doc/icon_files.md] |
|*(no direct token — use custom)* ⚠️ | `28.dp` | `PopIcons.kt` | 28px — slightly emphasised — section headers, feature labels | `atoms.md` | [bridge_doc/icon_files.md] |
| `PopIcons.sizeLarge` | `32.dp` (32×32dp) | `PopIcons.kt` | 32px — medium emphasis — card headers, onboarding steps | `atoms.md` | [bridge_doc/icon_files.md] |
| `PopIcons.buttonSizeMedium` | `40.dp` (40×40dp) | `PopIcons.kt` | 40px — **touch target** — tappable icon actions (Icon Button) | `[bridge_doc/icon_files.md] |
| `PopIcons.sizeXLarge` | `48.dp` (48×48dp) | `PopIcons.kt` | 48px — large emphasis — empty states, hero spots, UPI app logos | `atoms.md` | `[bridge_doc/icon_files.md] |


### 1.3 IconSize Enum

| `IconSize` Enum Value | Resolved Dp (via `toDp()`) | Codebase File | Design System Equivalent |
|---|---|---|---|
| `IconSize.Small` | `16.dp` | `PopIcons.kt` | 16px wrapper |
| `IconSize.Medium` | `24.dp` | `PopIcons.kt` | 24px wrapper (default) |
| `IconSize.Large` | `32.dp` | `PopIcons.kt` | 32px wrapper |
| `IconSize.XLarge` | `48.dp` | `PopIcons.kt` | 48px wrapper |

---

## 2. Icon Style Enum Mapping

> **Design rule (atoms.md §2.3):** Use outline as default. Use filled **only** when explicitly shown in the design — never default to filled.

| `IconStyle` Enum Value | Codebase File | Figma Category | Figma Prefix | Export Folder | Rule |
|---|---|---|---|---|---|
| `IconStyle.Outline` | `PopIcons.kt` | Outline Icons | `Icons/` | `icons/outline/` | **Default.** Navigation, actions, UI controls, all functional contexts. Resolves to `iconName.outlineRes` |
| `IconStyle.Filled` | `PopIcons.kt` | Filled Icons | `Filled Icons/` | `icons/filled/` | **Active/selected states only.** Use only when the design file explicitly shows the filled variant. Resolves to `iconName.filledRes` |

---

## 3. Code Patterns

### 3.1 `IconName` Sealed Interface — Icon Definition Pattern

Every icon in the POP codebase is defined as an `object` nested inside the `Icons` namespace, implementing the `IconName` sealed interface. This is the **only** valid way to define a new icon.

⚠️
```kotlin
// File: POP Codebase/theme/IconName.kt
// Pattern: object inside Icons { } implementing IconName

object Flag : IconName {
    override val value = "ic_flag"          // String asset key (used for XML / lookup)
    override val outlineRes: Int? = R.drawable.ic_flag   // Outline drawable (null if no outline)
    override val filledRes: Int? = R.drawable.ic_flag    // Filled drawable (null if no filled)
}
```

### 3.2 `PopIconConfig` Data Class

```kotlin
// File: POP Codebase/theme/PopIcons.kt
// Use PopIconConfig to bundle all icon rendering properties together

val iconConfig = PopIconConfig(
    iconName = Icons.Search,
    style = IconStyle.Outline,
    size = IconSize.Medium,           // 24dp
    tint = PopColor.textPrimary,      // always use alias token — never hardcode hex
    contentDescription = "Search"
)
```

### 3.3 Rendering an Icon — Full Pattern

```kotlin
import com.pop.components.theme.*
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Icon

// Step 1: Resolve the resource ID using style
val resourceId = PopIconResources.getIconResourceId(Icons.AlertCircle, IconStyle.Outline)

// Step 2: Render using resolved resource
resourceId?.let {
    Icon(
        painter = painterResource(id = it),
        contentDescription = "Alert",
        tint = PopColor.textPrimary,                  // ✅ alias token — never hex
        modifier = Modifier.size(PopIcons.sizeMedium)  // ✅ token — never raw dp
    )
}
```

### 3.5 Colour Rule

```kotlin
// ✅ Correct — always use alias token
tint = PopColor.iconPrimary

// ❌ Wrong — never hardcode hex
tint = Color(0xFFE6E6E6)
```

### 3.6 Missing Icon Placeholder

If the required icon does not exist in the codebase yet, use the placeholder:

```kotlin
// Design system rule: use Icons/Placeholder until icon is added to library
// In code: use Icons.NoResultsFound as a temporary stand-in
Icon(
    painter = painterResource(id = Icons.NoResultsFound.outlineRes!!),
    contentDescription = "Placeholder"
)
```

---

## 4. Outline Icon Mapping

> **Figma prefix:** `Icons/` · **Format:** SVG · **Export folder:** `icons/outline/`
> **Default wrapper size:** 24px (`PopIcons.sizeMedium`)
> Colour must always be applied via alias token — never hardcoded hex.

### 4.1 Navigation & Structure

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Preview |
|---|---|---|---|---|---|
| Back navigation | `Icons/Type=Back Icon` | `7477:9267` | `Icons.ChevronLeft` | ![back](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Type=BackIcon.png) |
| Close / dismiss | `Icons/Type=Cancel` | `7477:9266` | `Icons.Close`  | ![cancel](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Type=Cancel.png) |
| Cross / remove | `Icons/Cross` | `1893:15048` | `Icons.Cross`| ![cross](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Cross.png) |
| Arrow down | `Icons/Arrow` → `Arrow=Down` | `5:1350` | `Icons.ChevronDown` | ![arrow-down](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Arrow=Down.png) |
| Arrow up | `Icons/Arrow` → `Arrow=Up` | `5:1434` | — *(not yet defined in Icons)* | — | ![arrow-up](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Arrow=Up.png) |
| Arrow left | `Icons/Arrow` → `Arrow=Left` | `5:1447` | — *(not yet defined in Icons)* | — | ![arrow-left](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Arrow=Left.png) |
| Arrow right | `Icons/Arrow` → `Arrow=Right` | `5:1182` | — *(not yet defined in Icons)* | — | ![arrow-right](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Arrow=Right.png) |
| Chevron down | `Icons/Chevron` → `Chevron=Down` | `5:1454` | `Icons.ChevronDown` | ![chevron-down](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Chevron=Down.png) |
| Chevron down (16) | `Icons/ChevronDown16` | — | `Icons.ChevronDown16` |  — |
| Chevron down (12) | `Icons/ChevronDown12` | — | `Icons.ChevronDown12` | — |
| Chevron up | `Icons/Chevron` → `Chevron=Up` | `5:1414` | — *(not yet defined in Icons)* | — | ![chevron-up](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Chevron=Up.png) |
| Chevron left | `Icons/Chevron` → `Chevron=Left` | `5:1435` | `Icons.ChevronLeft` | ![chevron-left](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Chevron=Left.png) |
| Chevron right | `Icons/Chevron` → `Chevron=Right` | `5:1428` | `Icons.ChevronRight` | ![chevron-right](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Chevron=Right.png) |
| Home | `Icons/home-03` | `14166:8733` | `Icons.HomeBottomNav` | ![home](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/home-03.png) |
| More options (horizontal) | `Icons/dots-horizontal` | `5:1562` | — *(not yet defined in Icons)* | — | ![dots-h](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/dots-horizontal.png) |
| More options (vertical) | `Icons/dots-vertical` | `5:1481` | — *(not yet defined in Icons)* | — | ![dots-v](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/dots-vertical.png) |
| Menu / hamburger | `Icons/menu-01` | `5:1212` | — *(not yet defined in Icons)* | — | — |
| Refresh | `Icons/Refresh` | `5:1560` | `Icons.Refresh` | ![refresh](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Refresh.png) |

**Code Pattern — Navigation icons:**
```kotlin
// Back button
Icon(
    painter = painterResource(id = Icons.ChevronLeft.outlineRes!!),
    contentDescription = "Back",
    tint = PopColor.iconPrimary,
    modifier = Modifier.size(PopIcons.sizeMedium)
)

// Dismiss / close bottom sheet
Icon(
    painter = painterResource(id = Icons.Cross.outlineRes!!),
    contentDescription = "Close",
    tint = PopColor.iconPrimary,
    modifier = Modifier.size(PopIcons.sizeMedium)
)
```

---

### 4.2 Search & Input

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Drawable Resource | Preview |
|---|---|---|---|---|---|
| Search | `Icons/search-md` | `78:2309` | `Icons.Search` | `R.drawable.ic_search_outline` | ![search](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/search-md.png) |
| Show password | `Icons/eye` | `5:1519` | — *(not yet defined in Icons)* | — | ![eye](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/eye.png) |
| Hide password | `Icons/eye-off` | `5:1627` | — *(not yet defined in Icons)* | — | ![eye-off](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/eye-off.png) |
| Edit / pencil | `Icons/pencil-line-edit` | `10357:39746` | `Icons.Edit` | `R.drawable.ic_edit_pencil_grey` | ![edit](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/pencil-line-edit.png) |
| Copy | `Icons/copy-06` | `243:11791` | `Icons.Copy06` | `R.drawable.ic_copy_06` | ![copy](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/copy-06.png) |
| Delete / trash | `Icons/trash-04` | `5:1585` | `Icons.Trash` | `R.drawable.ic_trash` | ![trash](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/trash-04.png) |
| Add / plus | `Icons/plus` | `5:1303` | `Icons.AddIcon` | `R.drawable.add_icon` | ![plus](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/plus.png) |
| Minus / subtract | `Icons/minus` | `20285:141644` | — *(not yet defined in Icons)* | — | — |
| Camera | `Icons/camera` | `15242:1507` | — *(not yet defined in Icons)* | — | ![camera](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/camera.png) |

---

### 4.3 Status & Feedback

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Drawable Resource | Preview |
|---|---|---|---|---|---|
| Check / success | `Icons/check` | `5:1555` | `Icons.Check` | `R.drawable.check` | ![check](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/check.png) |
| Check (filled variant) | `Icons/check` | — | `Icons.CheckIcon` | `R.drawable.ic_check_filled` | — |
| Verified badge | `Icons/check-verified-02` | `5:1551` | `Icons.CheckVerified` | `R.drawable.ic_check_verified` | ![check-verified](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/check-verified-02.png) |
| Alert circle | `Icons/alert-circle` | `5:1403` | `Icons.AlertCircle` | `R.drawable.ic_alert_circle` | ![alert-circle](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/alert-circle.png) |
| Alert triangle | `Icons/alert-triangle` | `5:1359` | `Icons.AlertWarning` | `R.drawable.ic_alert_triangle` | ![alert-triangle](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/alert-triangle.png) |
| Alert hexagon | `Icons/alert-hexagon` | `5:1180` | `Icons.AlertHexagon` | `R.drawable.ic_alert_hexagon_outline` | ![alert-hexagon](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/alert-hexagon.png) |
| Info (square) | `Icons/info-square` | `5:1489` | `Icons.InfoSquare` | `R.drawable.ic_info_square` | — |
| Info (circle) | `Icons/info-circle` | `22240:189788` | — *(not yet defined in Icons)* | — | ![info-circle](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/info-circle.png) |
| Pending | `Icons/Pending` | `11480:121407` | `Icons.PendingHourGlass` | `R.drawable.ic_pending_hourglass_grey` | ![pending](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Pending.png) |
| Refresh / retry | `Icons/refresh-ccw-01` | `5:1503` | `Icons.Reset01` | `R.drawable.ic_reset` | ![refresh-ccw](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/refresh-ccw-01.png) |
| Loading | `Icons/loading-02` | `5:1609` | — *(not yet defined in Icons)* | — | ![loading](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/loading-02.png) |
| Star / rating | `Icons/star-01` | `13561:11332` | `Icons.Star` | `R.drawable.ic_rating_star` | ![star](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/star-01.png) |

---

### 4.4 Payments & Finance

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Drawable Resource | Preview |
|---|---|---|---|---|---|
| Send money | `Icons/send-01` | `7116:21940` | — *(use `Icons.SendMoneyArrow`)* | `R.drawable.ic_send_arrow_grey` | ![send](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/send-01.png) |
| Request money | `Icons/Request` | `7183:18170` | — *(not yet defined in Icons)* | — | ![request](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Request.png) |
| Transfer | `Icons/transfer` | `16226:20448` | — *(not yet defined in Icons)* | — | ![transfer](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/transfer.png) |
| Rupee / amount | `Icons/currency-rupee` | `5:1038` | `Icons.RupeeSymbol` / `Icons.RupeeIcon` | `R.drawable.ic_rupee_symbol` | ![rupee](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/currency-rupee.png) |
| Bank | `Icons/bank` | `5:1557` | `Icons.Bank` / `Icons.BankIcon` | `R.drawable.ic_bank` / `R.drawable.ic_bank_grey` | ![bank](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/bank.png) |
| Credit card | `Icons/credit-card-02` | `11480:121636` | `Icons.GreyCard` | `R.drawable.ic_card` | ![credit-card](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/credit-card-02.png) |
| Autopay | `Icons/Autopay` | `11480:121406` | `Icons.ManageAutoPay` | `R.drawable.ic_manage_auto_pay` | ![autopay](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Autopay.png) |
| Scan QR | `Icons/scan QR` | `17403:55799` | `Icons.ScanQR` | `R.drawable.scan_qr_fab` | ![scan-qr](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/scanQR.png) |
| QR code | `Icons/qr-code-01` | `10178:132280` | `Icons.QR` | `R.drawable.ic_qr_grey_curved` | ![qr-code](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/qr-code-01.png) |
| Receipt | `Icons/receipt-check` | `5:1030` | `Icons.ReceiptCheck` | `R.drawable.ic_receipt_check` | ![receipt](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/receipt-check.png) |
| Cashback | `Icons/cashback` | `15607:7034` | — *(not yet defined in Icons)* | — | ![cashback](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/cashback.png) |
| Arrow credited (inbound) | `Icons/arrow-narrow-up-right` | `5:1426` | `Icons.ArrowCredited` | `R.drawable.ic_arrow_credited` | ![arrow-up-right](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/arrow-narrow-up-right.png) |
| Arrow debited (outbound) | `Icons/arrow-narrow-down-right` | `5:1442` | `Icons.ArrowDebited` | `R.drawable.ic_arrow_debited` | ![arrow-down-right](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/arrow-narrow-down-right.png) |
| Check balance | `Icons/check balance` | `15324:10231` | `Icons.CheckBalance` | `R.drawable.ic_check_balance` | ![check-balance](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/checkbalance.png) |
| Manage autopay | `Icons/manage autopay` | `15328:2919` | `Icons.ManageAutoPay` | `R.drawable.ic_manage_auto_pay` | ![manage-autopay](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/manageautopay.png) |
| Manage UPI ID | `Icons/manage upi id` | `15324:10337` | `Icons.MyUpiId` | `R.drawable.ic_my_upi_id` | ![manage-upi](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/manageupiid.png) |
| Blocked VPA | `Icons/blocked VPA` | `15324:10230` | `Icons.BlockedVpa` | `R.drawable.ic_blocked_vpa` | ![blocked-vpa](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/blockedVPA.png) |
| UPI Lite | `Icons/UPI Lite` | `15324:10276` | `Icons.EverythingUpiLite` | `R.drawable.ic_upi_lite_new` | — |

---

### 4.5 Shopping & Commerce

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Drawable Resource | Preview |
|---|---|---|---|---|---|
| Shopping cart | `Icons/shopping-cart-03` | `5:1018` | — *(not yet defined in Icons)* | — | ![cart](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/shopping-cart-03.png) |
| Cart (outline) | `Icons/Cart` | — | `Icons.Cart` (outlineRes) | `R.drawable.cart_outlined` | — |
| Shopping bag | `Icons/shopping-bag-02` | `14166:8734` | — *(not yet defined in Icons)* | — | ![shopping-bag](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/shopping-bag-02.png) |
| Gift / offers | `Icons/gift-01` | `5:1034` | `Icons.GiftWrap` | `R.drawable.ic_gift_wrap` | ![gift](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/gift-01.png) |
| Sale | `Icons/sale-01` | `5:1029` | — *(not yet defined in Icons)* | — | ![sale](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/sale-01.png) |
| Wishlist off | `Icons/Wishlist` → `State=False` | `13557:10935` | — *(not yet defined in Icons)* | — | ![wishlist-off](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/State=False.png) |
| Wishlist on (filled) | `Icons/Wishlist` → `State=True` | `13557:10936` | — *(not yet defined in Icons)* | — | ![wishlist-on](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/State=True.png) |
| Heart | `Icons/Heart` | `14030:244274` | `Icons.Heart` | `R.drawable.icon_heart` | ![heart](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Heart.png) |
| Store | `Icons/store` | `17670:3856` | — *(not yet defined in Icons)* | — | ![store](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/store.png) |
| Delivery | `Icons/delivery` | `19244:150897` | — *(not yet defined in Icons)* | — | ![delivery](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/delivery.png) |
| Shop (bottom nav) | `Icons/shop` | `15607:7033` | `Icons.ShopBottomNav` | `R.drawable.ic_shop_bottom_nav` | — |

---

### 4.6 Account & Profile

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Drawable Resource | Preview |
|---|---|---|---|---|---|
| User / profile | `Icons/user-01` | `5:1261` | `Icons.User01` | `R.drawable.ic_user_01` | ![user](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/user-01.png) |
| User (square) | `Icons/user-square` | `14537:2962` | `Icons.UserSquare` | `R.drawable.ic_user_square` | ![user-square](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/user-square.png) |
| Settings | `Icons/settings-02` | `10826:32946` | `Icons.Settings` | `R.drawable.settings_icon` | ![settings](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/settings-02.png) |
| Help / support | `Icons/help-circle` | `10579:136846` | `Icons.HelpCircle` / `Icons.HELP_CIRCLE` | `R.drawable.ic_help_circle` | ![help](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/help-circle.png) |
| Headphones | `Icons/Headphones` | `10490:121783` | `Icons.Headphone` | `R.drawable.headphone` | ![headphones](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Headphones.png) |
| Notifications | `Icons/bell-ringing-02` | `5:1572` | `Icons.Notification` | `R.drawable.ic_notification` | ![bell](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/bell-ringing-02.png) |
| Log out | `Icons/log-out-01` | `5:1467` | `Icons.Logout` | `R.drawable.ic_logout` | ![logout](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/log-out-01.png) |
| Transaction history | `Icons/transaction history` | `15388:2527` | `Icons.TransactionHistory` | `R.drawable.ic_transaction_history` | ![txn-history](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/transactionhistory.png) |
| Saved | `Icons/saved` | `15373:2122` | `Icons.ProfileWishlist` | `R.drawable.ic_wishlist` | ![saved](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/saved.png) |
| Share (01) | `Icons/share-01` | `5:1473` | `Icons.Share01` | `R.drawable.ic_share_01_outline` | ![share-01](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/share-01.png) |
| Share (06) | `Icons/share-06` | `5:1603` | `Icons.Share06` | `R.drawable.ic_share_06_outline` | ![share-06](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/share-06.png) |
| Download | `Icons/download-01` | `5:1471` | `Icons.Download01` | `R.drawable.ic_download_01` | ![download](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/download-01.png) |
| Rate us | `Icons/rate-us` | `15369:1293` | `Icons.RateUs` | `R.drawable.ic_rate_us` | ![rate-us](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/rate-us.png) |
| Terms & conditions | `Icons/terms&condition` | `15369:1292` | `Icons.TermsAndConditions` | `R.drawable.ic_terms_n_conditions` | — |
| My queries | `Icons/my queries` | `15324:10236` | `Icons.MyQueries` | `R.drawable.ic_my_queries` | ![my-queries](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/myqueries.png) |
| My bills | `Icons/bills` | `15369:1290` | `Icons.MyBills` | `R.drawable.ic_my_bills` | ![bills](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/bills.png) |
| Pay bills | `Icons/pay-bills` | `15369:1291` | `Icons.PayBills` | `R.drawable.ic_pay_bills` | ![pay-bills](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/pay-bills.png) |
| Mobile (phone) | `Icons/mobile-phone-01` | `7183:12735` | `Icons.Mobile` | `R.drawable.ic_mobile_outline` | ![mobile](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/mobile-phone-01.png) |

---

### 4.7 Security & Auth

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Drawable Resource | Preview |
|---|---|---|---|---|---|
| Passcode lock | `Icons/passcode-lock` | `10760:32312` | — *(not yet defined in Icons)* | — | ![passcode](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/passcode-lock.png) |
| Shield / secure | `Icons/shield-tick` | `10760:32310` | — *(not yet defined in Icons)* | — | ![shield](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/shield-tick.png) |
| Face ID | `Icons/face-id` | `21686:1327` | — *(not yet defined in Icons)* | — | ![face-id](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/face-id.png) |
| Fingerprint | `Icons/fingerprint` | `21686:5579` | — *(not yet defined in Icons)* | — | ![fingerprint](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/fingerprint.png) |
| Block | `Icons/block` | `17529:15186` | `Icons.Block` | `R.drawable.block_icon` | ![block](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/block.png) |
| Spam | `Icons/spam` | `17529:15185` | `Icons.Spam` | `R.drawable.spam_icon` | ![spam](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/spam.png) |
| Flag | `Icons/flag-02` | `14140:38431` | `Icons.Flag` | `R.drawable.ic_flag` | — |
| Deactivate | `Icons/Deactivate` | `12260:56586` | — *(not yet defined in Icons)* | — | ![deactivate](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Deactivate.png) |

---

## 5. Filled Icon Mapping

> **Figma prefix:** `Filled Icons/` · **Format:** SVG · **Export folder:** `icons/filled/`
> ⛔ Use filled icons **only** when explicitly specified in the UI design — never default to filled.

Most filled icons are **not yet defined** as separate `Icons.*` objects. In the POP codebase, several outline icons share the same drawable for both `outlineRes` and `filledRes`. Where a dedicated filled object exists, it is noted.

### 5.1 Status & Feedback

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Access Pattern | Preview |
|---|---|---|---|---|---|
| Success / task completed | `Filled Icons/check-circle-Filled` | `24153:21164` | — *(not yet defined)* | — | ![check-circle-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/check-circle-Filled.png) |
| Alert circle active | `Filled Icons/alert-circle-Filled` | `32:2450` | — *(not yet defined)* | — | ![alert-circle-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/alert-circle-Filled.png) |
| Alert triangle active | `Filled Icons/alert-triangle-Filled` | `32:2398` | — *(not yet defined)* | — | ![alert-triangle-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/alert-triangle-Filled.png) |
| Alert hexagon active | `Filled Icons/alert-hexagon-Filled` | `32:2406` | — *(not yet defined)* | — | ![alert-hexagon-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/alert-hexagon-Filled.png) |
| Verified badge active | `Filled Icons/check-verified-02-Filled` | `32:2440` | — *(not yet defined)* | — | ![check-verified-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/check-verified-02-Filled.png) |
| Zap active | `Filled Icons/zap-Filled` | `32:2373` | — *(not yet defined)* | — | ![zap-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/zap-Filled.png) |

### 5.2 Notifications

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Access Pattern | Preview |
|---|---|---|---|---|---|
| Bell active | `Filled Icons/bell-02-Filled` | `32:2400` | — *(not yet defined)* | — | ![bell-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/bell-02-Filled.png) |
| Bell ringing active | `Filled Icons/bell-ringing-02-Filled` | `32:2402` | — *(not yet defined)* | — | ![bell-ringing-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/bell-ringing-02-Filled.png) |
| Bell ringing alt active | `Filled Icons/bell-ringing-04-Filled` | `32:2396` | — *(not yet defined)* | — | ![bell-ringing-04](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/bell-ringing-04-Filled.png) |
| Notifications off active | `Filled Icons/bell-off-02-Filled` | `32:2475` | — *(not yet defined)* | — | ![bell-off](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/bell-off-02-Filled.png) |

### 5.3 Payments & Finance (Filled)

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Access Pattern | Preview |
|---|---|---|---|---|---|
| Bank active | `Filled Icons/bank-Filled` | `32:2381` | — *(not yet defined)* | — | ![bank-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/bank-Filled.png) |
| Credit card active | `Filled Icons/credit-card-02-Filled` | `32:2377` | — *(not yet defined)* | — | ![credit-card-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/credit-card-02-Filled.png) |
| Credit card blocked active | `Filled Icons/credit-card-x-Filled` | `32:2366` | — *(not yet defined)* | — | ![credit-card-x](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/credit-card-x-Filled.png) |
| Receipt active | `Filled Icons/receipt-check-Filled` | `32:2451` | — *(not yet defined)* | — | ![receipt-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/receipt-check-Filled.png) |
| Gift active | `Filled Icons/gift-01-Filled` | `32:2365` | — *(not yet defined)* | — | ![gift-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/gift-01-Filled.png) |

### 5.4 Shopping & Commerce (Filled)

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Access Pattern | Preview |
|---|---|---|---|---|---|
| Shopping cart active | `Filled Icons/shopping-cart-03-Filled` | `32:2401` | — *(not yet defined)* | — | ![shopping-cart-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/shopping-cart-03-Filled.png) |
| Cart active | `Filled Icons/Cart-Filled` | `14108:199255` | `Icons.Cart` (filledRes) | `Icons.Cart.filledRes` → `R.drawable.ic_cart_white` | ![cart-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Cart-Filled.png) |
| Sale active | `Filled Icons/sale-01-Filled` | `32:2469` | — *(not yet defined)* | — | ![sale-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/sale-01-Filled.png) |
| Sale alt active | `Filled Icons/sale-03-Filled` | `32:2416` | — *(not yet defined)* | — | ![sale-03-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/sale-03-Filled.png) |
| Heart (favourited) | `Filled Icons/Heart` | `14030:244273` | `Icons.Heart` (both res = same drawable) | `Icons.Heart.filledRes` → `R.drawable.icon_heart` | ![heart-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Heart.png) |
| Star rating active | `Filled Icons/star-01` | `13561:11332` | `Icons.Star` (both res = same drawable) | `Icons.Star.filledRes` → `R.drawable.ic_rating_star` | — |
| Flag active | `Filled Icons/flag-02` | `14140:38430` | `Icons.Flag` (both res = same drawable) | `Icons.Flag.filledRes` → `R.drawable.ic_flag` | ![flag-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/flag-02.png) |

### 5.5 Account & Profile (Filled)

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Access Pattern | Preview |
|---|---|---|---|---|---|
| User active | `Filled Icons/user-03-Filled` | `32:2411` | — *(not yet defined)* | — | ![user-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/user-03-Filled.png) |
| User circle active | `Filled Icons/user-circle-Filled` | `32:2421` | — *(not yet defined)* | — | ![user-circle-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/user-circle-Filled.png) |
| Settings active | `Filled Icons/settings-02-Filled` | `32:2371` | — *(not yet defined)* | — | ![settings-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/settings-02-Filled.png) |

### 5.6 Actions (Filled)

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Access Pattern | Preview |
|---|---|---|---|---|---|
| Share active | `Filled Icons/share-06-Filled` | `32:2472` | `Icons.Share06` (filledRes) | `Icons.Share06.filledRes` → `R.drawable.ic_share_06_filled` | ![share-06-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/share-06-Filled.png) |
| Delete active | `Filled Icons/trash-04-Filled` | `32:2424` | — *(not yet defined)* | — | ![trash-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/trash-04-Filled.png) |
| Add circle active | `Filled Icons/plus-circle-Filled` | `32:2454` | — *(not yet defined)* | — | ![plus-circle](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/plus-circle-Filled.png) |
| Upload active | `Filled Icons/upload-03-Filled` | `32:2452` | — *(not yet defined)* | — | ![upload-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/upload-03-Filled.png) |
| Show / reveal active | `Filled Icons/eye-Filled` | `32:2462` | — *(not yet defined)* | — | ![eye-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/eye-Filled.png) |
| Hide active | `Filled Icons/eye-off-Filled` | `32:2436` | — *(not yet defined)* | — | ![eye-off-filled](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/eye-off-Filled.png) |

**Code Pattern — switching outline ↔ filled by state:**
```kotlin
// Toggle filled/outline by selection state
val style = if (isSelected) IconStyle.Filled else IconStyle.Outline
val resId = PopIconResources.getIconResourceId(Icons.Cart, style)

resId?.let {
    Icon(
        painter = painterResource(id = it),
        contentDescription = if (isSelected) "Cart selected" else "Cart",
        tint = if (isSelected) PopColor.brandPrimary else PopColor.iconPrimary,
        modifier = Modifier.size(PopIcons.sizeMedium)
    )
}
```

---

## 6. Inline Illustration Icons

> **Figma prefix:** `Illustration/` · **Format:** PNG · **Export folder:** `icons/illustration/`
> ⚠️ Do NOT confuse with Illustration Assets (Section 9). Inline Illustration Icons are small PNG accents used inside components — not full-screen illustrations.
> ⛔ Never re-color — static PNG assets. Do not apply color tokens or CSS filters.
> ⛔ Never use as interactive controls.

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Notes | Preview |
|---|---|---|---|---|
| Generic fallback / default slot | `Illustration/Default` | `6422:3359` | Use when no specific illustration applies | ![default](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Default.png) |
| Success / verified | `Illustration/Check-02` | `13289:2555` | Task completed, verification passed | ![check-02](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Check-02.png) |
| Pending / in-progress | `Illustration/Pending` | `13289:158645` | Awaiting action or processing | ![pending](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Pending.png) |
| Error / failed | `Illustration/Error` | `13318:88592` | Failure states | ![error](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Error.png) |
| Cash / money moment | `Illustration/Cash-Money` | `5469:9981` | Savings, cashback credited, reward screens | ![cash-money](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Cash-Money.png) |
| POPcoin bag | `Illustration/POPcoin bag` | `11805:52140` | POPcoin earned / reward moments | ![popcoin-bag](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/POPcoinbag.png) |
| Coin tilted | `Illustration/coin tilted` | `21152:214252` | Coin-related contextual moments | ![coin-tilted](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/cointilted.png) |
| UPI Lite | `Illustration/UPI Lite` | `10807:106143` | UPI Lite onboarding and feature screens | ![upi-lite-illus](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/UPILite.png) |
| Shield / security | `Illustration/Shield` | `90:7378` | Security, privacy, or protection contexts | ![shield](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Shield.png) |
| Payin3 split | `Illustration/Payin3-split` | `5515:4101` | Buy-now-pay-later split screens | ![payin3](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Payin3-split.png) |
| Decorative star | `Illustration/Decorative star` | `13478:23891` | Celebratory or reward accent | ![decorative-star](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Decorativestar.png) |
| Star shape | `Illustration/Star-Shape` | `10357:101377` | Generic decorative star accent | ![star-shape](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Star-Shape.png) |
| Faves (active) | `Illustration/fave` → `State=True` | `17053:91661` | Favourites / saved active state | ![fave](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/fave.png) |
| Faves (inactive) | `Illustration/fave` → `State=False` | `17053:91663` | Favourites inactive state | ![state-false](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/State=False.png) |
| Blue variant | `Illustration/Blue` | — | Blue variant illustration | ![blue](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Blue.png) |
| Pending (variant 1) | `Illustration/Pending-1` | — | Alternate pending state | ![pending-1](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Pending-1.png) |

**Code Pattern — Inline Illustration Icons:**
```kotlin
// Inline illustration — use as PNG asset in Image composable, NOT Icon
// ⛔ Do NOT apply tint — static PNG
Image(
    painter = painterResource(id = R.drawable.ic_popcoin_bag),  // exact drawable
    contentDescription = "POPcoin earned",
    modifier = Modifier.size(PopIcons.sizeMedium)  // 24dp default, component controls size
)
```

---

## 7. Logos

> **Figma prefix:** `Logos/` · **Format:** SVG · **Export folder:** `icons/logos/`
> ⛔ Never re-color logos — governed brand marks.
> ⛔ Do not substitute a logo with an icon or illustration.

| Use Case | Figma Name (atoms.md) | Figma Node ID (icon.md) | Codebase Object | Notes | Preview |
|---|---|---|---|---|---|
| UPI payment rail | `Logos/upi` | `15367:1207` | — *(not defined in Icons — use dedicated drawable)* | Payment method pickers, transaction confirmation, UPI identity | — |
| UPI Lite mark | `Logos/upi lite` | `15344:3397` | `Icons.EverythingUpiLite` | UPI Lite balance, setup, feature surfaces | ![upi-lite](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/upilite.png) |
| UPI Autopay mark | `Logos/upi autopay` | `15344:3396` | — *(not defined in Icons — use dedicated drawable)* | Autopay setup, mandate management | ![upi-autopay](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/UPIautopay.png) |
| POP brand mark | `Logos/Pop` | `19051:72935` | — *(not defined in Icons — use dedicated drawable)* | POP brand moments, identity surfaces, co-branding | ![pop-logo](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Pop.png) |
| Indian flag | `Logos/Indian-flag1` | `24128:181508` | `Icons.Flag` | Nationality / country indicator | ![indian-flag](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Indian-flag1.png) |

**Code Pattern — Logos:**
```kotlin
// Use Image (not Icon) for logos — preserve exact colors
// ⛔ Do NOT apply tint
Image(
    painter = painterResource(id = R.drawable.ic_upi_logo),
    contentDescription = "UPI",
    modifier = Modifier
        .width(48.dp)   // always use exact size from design spec
        .height(24.dp)
)
```

## 10. Dot Icons (PopDot)

> **Design system (atoms2.md):** Dot atom — 6/8/10px, colour variants: Orange / White / Red / Blue / Green / Yellow.
> **Codebase (PopDotColor.kt, PopDotSize.kt):** `PopDotColor` enum + `PopDotSize` enum.

### 10.1 PopDotColor Mapping

| Design Variant (atoms2.md) | `PopDotColor` Enum Value | Codebase File | Preview |
|---|---|---|---|
| Orange dot | `PopDotColor.Orange` | `PopDotColor.kt` | ![dot-orange](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Dot-Orange.png) |
| White dot | `PopDotColor.White` | `PopDotColor.kt` | ![dot-white](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Dot-White.png) |
| Green dot | `PopDotColor.Green` | `PopDotColor.kt` | ![dot-green](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Dot-Green.png) |
| Blue dot | `PopDotColor.Blue` | `PopDotColor.kt` | — |
| Red dot *(design only)* | — *(not yet in enum)* | `PopDotColor.kt` | ![dot-red](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Dot-Red.png) |
| Yellow dot *(design only)* | — *(not yet in enum)* | `PopDotColor.kt` | ![dot-yellow](https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/Dot-Yellow.png) |

### 10.2 PopDotSize Mapping

| Design Size (atoms2.md) | `PopDotSize` Enum Value | Resolved Dp (via `toDotDp()`) | Codebase File |
|---|---|---|---|
| 6px | `PopDotSize.Med` | `6.dp` | `PopDotSize.kt` |
| 8px | `PopDotSize.Large` | `8.dp` | `PopDotSize.kt` |
| 10px *(design only)* | — *(not yet in enum)* | — | `PopDotSize.kt` |

**Code Pattern — PopDot:**
```kotlin
PopDot(
    color = PopDotColor.Orange,
    size = PopDotSize.Large,     // 8dp
    isActive = true
)
```

---

## 11. Fave Icon

> **Design system (atoms2.md):** Fave icon — sizes 20/24/28/32px, colour variants: Colour (Orange), White, Green, Blue. Has `isActive` state.
> **Codebase (FavIconColor.kt, FavIconSize.kt):** `FavIconColor` enum + `FavIconSize` enum.

### 11.1 FavIconColor Mapping

| Design Variant (atoms2.md) | `FavIconColor` Enum Value | Codebase File |
|---|---|---|
| Colour / Orange | `FavIconColor.Orange` | `FavIconColor.kt` |
| White | `FavIconColor.White` | `FavIconColor.kt` |
| Green | `FavIconColor.Green` | `FavIconColor.kt` |
| Blue | `FavIconColor.Blue` | `FavIconColor.kt` |

### 11.2 FavIconSize Mapping

| Design Size (atoms2.md) | `FavIconSize` Enum Value | Resolved Dp (via `toIconDp()`) | Codebase File |
|---|---|---|---|
| 20px | — *(maps closest to Med)* | — | `FavIconSize.kt` |
| 24px / 28px | `FavIconSize.Large` | `22.dp` | `FavIconSize.kt` |
| 20px / smaller | `FavIconSize.Med` | `18.dp` | `FavIconSize.kt` |
| 32px | — *(no exact match — use Large)* | `22.dp` | `FavIconSize.kt` |

**Code Pattern — FavIcon:**
```kotlin
FavIcon(
    color = FavIconColor.Orange,
    size = FavIconSize.Large,    // 22dp icon
    isActive = true              // shows filled/active state
)
```

**Inline illustration asset (active state):**
```kotlin
// When Fave uses illustration asset (not icon)
Image(
    painter = painterResource(id = R.drawable.illus_fave_active),
    contentDescription = "Favourited",
    modifier = Modifier.size(24.dp)  // always match wrapper size
)
```
