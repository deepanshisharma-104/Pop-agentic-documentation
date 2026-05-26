# POP Design System — Icon Library

> Complete icon reference for UI-to-code development. All icons use a **24×24px** default wrapper unless noted.
> SVG fill uses `var(--fill-0, currentColor)` — apply colour via design token, never hardcode hex.
> Status bar icons use system hex values only and are always embedded in the Status Bar component — never used directly.

## How to Use
| Category | Figma Prefix | Export Path | Format |
|---|---|---|---|
| Outline icons | `Icons/` | `icons/outline/` | SVG |
| Filled icons | `Filled Icons/` | `icons/filled/` | SVG |
| Inline Illustration icons | `Illustration/` | `icons/illustration/` | PNG |
| Logos | `Logos/` | `icons/logos/` | SVG |
| UPI App Logos | `Banks/` | `icons/upi-logos/` | PNG |
| Status Bar Icons | `Icons/Status bar icons/` | `icons/status-bar/` | SVG |

- **Default wrapper size**: 24px
- **Tappable icon?** Use `Icon Button` (40px touch target), not Icon Wrapper
- **Missing icon?** Use `Icons/Placeholder` as stand-in until the icon is added to the library
- **Filled icons**: use **only** when explicitly shown in the UI design — never default to filled

---

## 1. Outline Icons

> Default icon style — navigation, actions, UI controls, all functional contexts. Prefix: `Icons/`

| Code Name | Figma Name | Property | Figma Node ID | SVG |
|---|---|---|---|---|
| `arrow-right` | `Icons/Arrow` | `Arrow=Right` | `5:1182` | `<svg preserveAspectRatio="none" width="100%" height="100%" overflow="visible" style="display: block;" viewBox="0 0 17.9999 13.9998" fill="none" xmlns="http://www.w3.org/2000/svg"> <path id="Icon (Stroke)" d="M10.293 0.292893C10.6835 -0.0976311 11.3165 -0.0976311 11.707 0.292893L17.707 6.29289C18.0976 6.68342 18.0976 7.31643 17.707 7.70696L11.707 13.707C11.3165 14.0975 10.6835 14.0975 10.293 13.707C9.90244 13.3164 9.90244 12.6834 10.293 12.2929L14.5859 7.99992H1C0.447715 7.99992 0 7.55221 0 6.99992C0 6.44764 0.447715 5.99992 1 5.99992H14.5859L10.293 1.70696C9.90244 1.31643 9.90244 0.683418 10.293 0.292893Z" fill="var(--fill-0, #E6E6E6)"/> </svg>` |
| `arrow-down` | `Icons/Arrow` | `Arrow=Down` | `5:1350` | `<svg preserveAspectRatio="none" width="100%" height="100%" overflow="visible" style="display: block;" viewBox="0 0 13.9998 17.9999" fill="none" xmlns="http://www.w3.org/2000/svg"> <path id="Icon (Stroke)" d="M5.99992 1C5.99992 0.447715 6.44764 0 6.99992 0C7.55221 0 7.99992 0.447715 7.99992 1V14.5859L12.2929 10.293C12.6834 9.90244 13.3164 9.90244 13.707 10.293C14.0975 10.6835 14.0975 11.3165 13.707 11.707L7.70696 17.707C7.31643 18.0976 6.68342 18.0976 6.29289 17.707L0.292893 11.707C-0.0976311 11.3165 -0.0976311 10.6835 0.292893 10.293C0.683418 9.90244 1.31643 9.90244 1.70696 10.293L5.99992 14.5859V1Z" fill="var(--fill-0, #E6E6E6)"/> </svg>` |
| `arrow-left` | `Icons/Arrow` | `Arrow=Left` | `5:1447` | `<svg preserveAspectRatio="none" width="100%" height="100%" overflow="visible" style="display: block;" viewBox="0 0 17.9999 13.9998" fill="none" xmlns="http://www.w3.org/2000/svg"> <path id="Icon (Stroke)" d="M6.29289 0.292893C6.68342 -0.0976311 7.31643 -0.0976311 7.70696 0.292893C8.09748 0.683418 8.09748 1.31643 7.70696 1.70696L3.41399 5.99992H16.9999C17.5522 5.99992 17.9999 6.44764 17.9999 6.99992C17.9999 7.55221 17.5522 7.99992 16.9999 7.99992H3.41399L7.70696 12.2929C8.09748 12.6834 8.09748 13.3164 7.70696 13.707C7.31643 14.0975 6.68342 14.0975 6.29289 13.707L0.292893 7.70696C-0.0976311 7.31643 -0.0976311 6.68342 0.292893 6.29289L6.29289 0.292893Z" fill="var(--fill-0, #E6E6E6)"/> </svg>` |
| `arrow-up` | `Icons/Arrow` | `Arrow=Up` | `5:1434` | `<svg preserveAspectRatio="none" width="100%" height="100%" overflow="visible" style="display: block;" viewBox="0 0 13.9998 17.9998" fill="none" xmlns="http://www.w3.org/2000/svg"> <path id="Icon (Stroke)" d="M5.99992 16.9998V3.41383L1.70696 7.7068C1.31643 8.09732 0.683418 8.09732 0.292893 7.7068C-0.0976311 7.31627 -0.0976311 6.68326 0.292893 6.29273L6.29289 0.292734L6.36907 0.224375C6.76184 -0.0959754 7.34084 -0.0733816 7.70696 0.292734L13.707 6.29273C14.0975 6.68326 14.0975 7.31627 13.707 7.7068C13.3164 8.09732 12.6834 8.09732 12.2929 7.7068L7.99992 3.41383V16.9998C7.99992 17.5521 7.55221 17.9998 6.99992 17.9998C6.44764 17.9998 5.99992 17.5521 5.99992 16.9998Z" fill="var(--fill-0, #E6E6E6)"/> </svg>` |
| `chevron-right` | `Icons/Chevron` | `Chevron=Right` | `5:1428` | *export from node `5:1428`* |
| `chevron-down` | `Icons/Chevron` | `Chevron=Down` | `5:1454` | *export from node `5:1454`* |
| `chevron-up` | `Icons/Chevron` | `Chevron=Up` | `5:1414` | *export from node `5:1414`* |
| `chevron-left` | `Icons/Chevron` | `Chevron=Left` | `5:1435` | *export from node `5:1435`* |
| `chevron-right-double` | `Icons/chevron-right-double` | — | `5:1424` | *export from node `5:1424`* |
| `arrow-narrow-down-left` | `Icons/arrow-narrow-down-left` | — | `5:1477` | *export from node `5:1477`* |
| `arrow-narrow-down-right` | `Icons/arrow-narrow-down-right` | — | `5:1442` | *export from node `5:1442`* |
| `arrow-narrow-up-left` | `Icons/arrow-narrow-up-left` | — | `5:1432` | *export from node `5:1432`* |
| `arrow-narrow-up-right` | `Icons/arrow-narrow-up-right` | — | `5:1426` | *export from node `5:1426`* |
| `back-icon` | `Icons/Type=Back Icon` | — | `7477:9267` | *export from node `7477:9267`* |
| `cancel` | `Icons/Type=Cancel` | — | `7477:9266` | *export from node `7477:9266`* |
| `menu-01` | `Icons/menu-01` | — | `5:1212` | *export from node `5:1212`* |
| `home-03` | `Icons/home-03` | — | `14166:8733` | *export from node `14166:8733`* |
| `dots-horizontal` | `Icons/dots-horizontal` | — | `5:1562` | *export from node `5:1562`* |
| `dots-vertical` | `Icons/dots-vertical` | — | `5:1481` | *export from node `5:1481`* |
| `refresh` | `Icons/Refresh` | — | `5:1560` | *export from node `5:1560`* |
| `refresh-ccw-01` | `Icons/refresh-ccw-01` | — | `5:1503` | *export from node `5:1503`* |
| `search-md` | `Icons/search-md` | — | `78:2309` | *export from node `78:2309`* |
| `eye` | `Icons/eye` | — | `5:1519` | *export from node `5:1519`* |
| `eye-off` | `Icons/eye-off` | — | `5:1627` | *export from node `5:1627`* |
| `pencil-line-edit` | `Icons/pencil-line-edit` | — | `10357:39746` | *export from node `10357:39746`* |
| `copy-06` | `Icons/copy-06` | — | `243:11791` | *export from node `243:11791`* |
| `trash-04` | `Icons/trash-04` | — | `5:1585` | *export from node `5:1585`* |
| `plus` | `Icons/plus` | — | `5:1303` | *export from node `5:1303`* |
| `minus` | `Icons/minus` | — | `20285:141644` | *export from node `20285:141644`* |
| `camera` | `Icons/camera` | — | `15242:1507` | *export from node `15242:1507`* |
| `hash-02` | `Icons/hash-02` | — | `5:1598` | *export from node `5:1598`* |
| `upload-01` | `Icons/upload-01` | — | `5:1528` | *export from node `5:1528`* |
| `delete` | `Icons/delete` | — | `12095:122818` | *export from node `12095:122818`* |
| `check` | `Icons/check` | — | `5:1555` | *export from node `5:1555`* |
| `check-verified-02` | `Icons/check-verified-02` | — | `5:1551` | *export from node `5:1551`* |
| `alert-circle` | `Icons/alert-circle` | — | `5:1403` | *export from node `5:1403`* |
| `alert-hexagon` | `Icons/alert-hexagon` | — | `5:1180` | *export from node `5:1180`* |
| `alert-triangle` | `Icons/alert-triangle` | — | `5:1359` | *export from node `5:1359`* |
| `info-square` | `Icons/info-square` | — | `5:1489` | *export from node `5:1489`* |
| `info-circle` | `Icons/info-circle` | — | `22240:189788` | *export from node `22240:189788`* |
| `pending` | `Icons/Pending` | — | `11480:121407` | *export from node `11480:121407`* |
| `zap` | `Icons/zap` | — | `5:1483` | *export from node `5:1483`* |
| `zap-fast` | `Icons/zap-fast` | — | `10760:32311` | *export from node `10760:32311`* |
| `loading-02` | `Icons/loading-02` | — | `5:1609` | *export from node `5:1609`* |
| `vertical-loader` | `Icons/vertical loader` | — | `22468:7033` | *export from node `22468:7033`* |
| `send-01` | `Icons/send-01` | — | `7116:21940` | *export from node `7116:21940`* |
| `request` | `Icons/Request` | — | `7183:18170` | *export from node `7183:18170`* |
| `transfer` | `Icons/transfer` | — | `16226:20448` | *export from node `16226:20448`* |
| `currency-rupee` | `Icons/currency-rupee` | — | `5:1038` | *export from node `5:1038`* |
| `bank` | `Icons/bank` | — | `5:1557` | *export from node `5:1557`* |
| `credit-card-02` | `Icons/credit-card-02` | — | `11480:121636` | *export from node `11480:121636`* |
| `credit-card-upload` | `Icons/credit-card-upload` | — | `5:1053` | *export from node `5:1053`* |
| `autopay` | `Icons/Autopay` | — | `11480:121406` | *export from node `11480:121406`* |
| `scan-qr` | `Icons/scan QR` | — | `17403:55799` | *export from node `17403:55799`* |
| `qr-code-01` | `Icons/qr-code-01` | — | `10178:132280` | *export from node `10178:132280`* |
| `receipt-check` | `Icons/receipt-check` | — | `5:1030` | *export from node `5:1030`* |
| `gift-01` | `Icons/gift-01` | — | `5:1034` | *export from node `5:1034`* |
| `sale-01` | `Icons/sale-01` | — | `5:1029` | *export from node `5:1029`* |
| `money-4` | `Icons/money-4` | — | `7183:12918` | *export from node `7183:12918`* |
| `cashback` | `Icons/cashback` | — | `15607:7034` | *export from node `15607:7034`* |
| `emi` | `Icons/EMI` | — | `22934:201714` | *export from node `22934:201714`* |
| `upi` | `Icons/upi` | — | `15367:1169` | *export from node `15367:1169`* |
| `shopping-cart-03` | `Icons/shopping-cart-03` | — | `5:1018` | *export from node `5:1018`* |
| `shopping-bag-02` | `Icons/shopping-bag-02` | — | `14166:8734` | *export from node `14166:8734`* |
| `wishlist-off` | `Icons/Wishlist` | `State=False` | `13557:10935` | *export from node `13557:10935`* |
| `wishlist-on` | `Icons/Wishlist` | `State=True` | `13557:10936` | *export from node `13557:10936`* |
| `heart` | `Icons/Heart` | — | `14030:244274` | *export from node `14030:244274`* |
| `store` | `Icons/store` | — | `17670:3856` | *export from node `17670:3856`* |
| `delivery` | `Icons/delivery` | — | `19244:150897` | *export from node `19244:150897`* |
| `offers-percent` | `Icons/Offers-%` | — | `17529:16216` | *export from node `17529:16216`* |
| `user-01` | `Icons/user-01` | — | `5:1261` | *export from node `5:1261`* |
| `settings-02` | `Icons/settings-02` | — | `10826:32946` | *export from node `10826:32946`* |
| `help-circle` | `Icons/help-circle` | — | `10579:136846` | *export from node `10579:136846`* |
| `headphones` | `Icons/Headphones` | — | `10490:121783` | *export from node `10490:121783`* |
| `bell-ringing-02` | `Icons/bell-ringing-02` | — | `5:1572` | *export from node `5:1572`* |
| `log-out-01` | `Icons/log-out-01` | — | `5:1467` | *export from node `5:1467`* |
| `transaction-history` | `Icons/transaction history` | — | `15388:2527` | *export from node `15388:2527`* |
| `saved` | `Icons/saved` | — | `15373:2122` | *export from node `15373:2122`* |
| `share-01` | `Icons/share-01` | — | `5:1473` | *export from node `5:1473`* |
| `share-06` | `Icons/share-06` | — | `5:1603` | *export from node `5:1603`* |
| `download-01` | `Icons/download-01` | — | `5:1471` | *export from node `5:1471`* |
| `user-up-01` | `Icons/user-up-01` | — | `7116:21942` | *export from node `7116:21942`* |
| `user-square` | `Icons/user-square` | — | `14537:2962` | *export from node `14537:2962`* |
| `users-plus` | `Icons/users-plus` | — | `15144:144277` | *export from node `15144:144277`* |
| `passcode-lock` | `Icons/passcode-lock` | — | `10760:32312` | *export from node `10760:32312`* |
| `shield-tick` | `Icons/shield-tick` | — | `10760:32310` | *export from node `10760:32310`* |
| `face-id` | `Icons/face-id` | — | `21686:1327` | *export from node `21686:1327`* |
| `fingerprint` | `Icons/fingerprint` | — | `21686:5579` | *export from node `21686:5579`* |
| `block` | `Icons/block` | — | `17529:15186` | *export from node `17529:15186`* |
| `deactivate` | `Icons/Deactivate` | — | `12260:56586` | *export from node `12260:56586`* |
| `blocked-vpa` | `Icons/blocked VPA` | — | `15324:10230` | *export from node `15324:10230`* |
| `check-balance` | `Icons/check balance` | — | `15324:10231` | *export from node `15324:10231`* |
| `create-upi-no` | `Icons/create upi no.` | — | `15324:10233` | *export from node `15324:10233`* |
| `manage-accounts` | `Icons/manage accounts` | — | `15324:10234` | *export from node `15324:10234`* |
| `manage-upi-id` | `Icons/manage upi id` | — | `15324:10337` | *export from node `15324:10337`* |
| `my-queries` | `Icons/my queries` | — | `15324:10236` | *export from node `15324:10236`* |
| `upi-lite` | `Icons/UPI Lite` | — | `15324:10276` | *export from node `15324:10276`* |
| `manage-autopay` | `Icons/manage autopay` | — | `15328:2919` | *export from node `15328:2919`* |
| `message-alert-circle` | `Icons/message-alert-circle` | — | `11480:121668` | *export from node `11480:121668`* |
| `message-text-circle-01` | `Icons/message-text-circle-01` | — | `11480:121667` | *export from node `11480:121667`* |
| `message-x-circle` | `Icons/message-x-circle` | — | `11480:121666` | *export from node `11480:121666`* |
| `placeholder` | `Icons/Placeholder` | — | `5:1421` | *export from node `5:1421`* |
| `link-01` | `Icons/link-01` | — | `5:1526` | *export from node `5:1526`* |
| `link-04` | `Icons/link-04` | — | `5:1469` | *export from node `5:1469`* |
| `zap` | `Icons/zap` | — | `5:1483` | *export from node `5:1483`* |
| `flag-02` | `Icons/flag-02` | — | `14140:38431` | *export from node `14140:38431`* |
| `sun` | `Icons/sun` | — | `14537:2960` | *export from node `14537:2960`* |
| `marker-pin-04` | `Icons/marker-pin-04` | — | `14537:2959` | *export from node `14537:2959`* |
| `mark` | `Icons/mark` | — | `14537:2958` | *export from node `14537:2958`* |
| `location` | `Icons/location` | — | `15242:1510` | *export from node `15242:1510`* |
| `image` | `Icons/image` | — | `17529:7412` | *export from node `17529:7412`* |
| `torch` | `Icons/torch` | — | `17529:12806` | *export from node `17529:12806`* |
| `spam` | `Icons/spam` | — | `17529:15185` | *export from node `17529:15185`* |
| `more` | `Icons/more` | — | `18011:116151` | *export from node `18011:116151`* |
| `mail` | `Icons/mail` | — | `19058:73542` | *export from node `19058:73542`* |
| `shop` | `Icons/shop` | — | `15607:7033` | *export from node `15607:7033`* |
| `queries` | `Icons/queries` | — | `15369:1289` | *export from node `15369:1289`* |
| `bills` | `Icons/bills` | — | `15369:1290` | *export from node `15369:1290`* |
| `pay-bills` | `Icons/pay-bills` | — | `15369:1291` | *export from node `15369:1291`* |
| `terms-condition` | `Icons/terms&condition` | — | `15369:1292` | *export from node `15369:1292`* |
| `rate-us` | `Icons/rate-us` | — | `15369:1293` | *export from node `15369:1293`* |
| `postpaid` | `Icons/postpaid` | — | `17175:73895` | *export from node `17175:73895`* |
| `prepaid` | `Icons/prepaid` | — | `17175:73896` | *export from node `17175:73896`* |
| `random` | `Icons/random` | — | `23989:106513` | *export from node `23989:106513`* |
| `slider-03` | `Icons/Slider_03` | — | `4824:2986` | *export from node `4824:2986`* |
| `hash-02` | `Icons/hash-02` | — | `5:1598` | *export from node `5:1598`* |
| `mobile-phone-01` | `Icons/mobile-phone-01` | — | `7183:12735` | *export from node `7183:12735`* |
| `history` | `Icons/hisotru 2` | — | `10178:132290` | *export from node `10178:132290`* |
| `cross` | `Icons/Cross` | — | `1893:15048` | *export from node `1893:15048`* |
| `emi` | `Icons/EMI` | — | `22934:201714` | *export from node `22934:201714`* |

---

## 2. Filled Icons

> Use filled icons **only** when explicitly specified in the UI — never default to filled. Prefix: `Filled Icons/`

| Code Name | Figma Name | Figma Node ID | SVG |
|---|---|---|---|
| `share-06-filled` | `Filled Icons/share-06-Filled` | `32:2472` | *export from node `32:2472`* |
| `eye-filled` | `Filled Icons/eye-Filled` | `32:2462` | *export from node `32:2462`* |
| `eye-off-filled` | `Filled Icons/eye-off-Filled` | `32:2436` | *export from node `32:2436`* |
| `check-verified-02-filled` | `Filled Icons/check-verified-02-Filled` | `32:2440` | *export from node `32:2440`* |
| `trash-04-filled` | `Filled Icons/trash-04-Filled` | `32:2424` | *export from node `32:2424`* |
| `plus-circle-filled` | `Filled Icons/plus-circle-Filled` | `32:2454` | *export from node `32:2454`* |
| `upload-03-filled` | `Filled Icons/upload-03-Filled` | `32:2452` | *export from node `32:2452`* |
| `zap-filled` | `Filled Icons/zap-Filled` | `32:2373` | *export from node `32:2373`* |
| `settings-02-filled` | `Filled Icons/settings-02-Filled` | `32:2371` | *export from node `32:2371`* |
| `user-circle-filled` | `Filled Icons/user-circle-Filled` | `32:2421` | *export from node `32:2421`* |
| `user-03-filled` | `Filled Icons/user-03-Filled` | `32:2411` | *export from node `32:2411`* |
| `alert-circle-filled` | `Filled Icons/alert-circle-Filled` | `32:2450` | *export from node `32:2450`* |
| `bell-off-02-filled` | `Filled Icons/bell-off-02-Filled` | `32:2475` | *export from node `32:2475`* |
| `alert-hexagon-filled` | `Filled Icons/alert-hexagon-Filled` | `32:2406` | *export from node `32:2406`* |
| `bell-02-filled` | `Filled Icons/bell-02-Filled` | `32:2400` | *export from node `32:2400`* |
| `alert-triangle-filled` | `Filled Icons/alert-triangle-Filled` | `32:2398` | *export from node `32:2398`* |
| `bell-ringing-02-filled` | `Filled Icons/bell-ringing-02-Filled` | `32:2402` | *export from node `32:2402`* |
| `bell-ringing-04-filled` | `Filled Icons/bell-ringing-04-Filled` | `32:2396` | *export from node `32:2396`* |
| `bank-filled` | `Filled Icons/bank-Filled` | `32:2381` | *export from node `32:2381`* |
| `credit-card-02-filled` | `Filled Icons/credit-card-02-Filled` | `32:2377` | *export from node `32:2377`* |
| `credit-card-x-filled` | `Filled Icons/credit-card-x-Filled` | `32:2366` | *export from node `32:2366`* |
| `gift-01-filled` | `Filled Icons/gift-01-Filled` | `32:2365` | *export from node `32:2365`* |
| `receipt-check-filled` | `Filled Icons/receipt-check-Filled` | `32:2451` | *export from node `32:2451`* |
| `sale-01-filled` | `Filled Icons/sale-01-Filled` | `32:2469` | *export from node `32:2469`* |
| `sale-03-filled` | `Filled Icons/sale-03-Filled` | `32:2416` | *export from node `32:2416`* |
| `shopping-cart-03-filled` | `Filled Icons/shopping-cart-03-Filled` | `32:2401` | *export from node `32:2401`* |
| `check-circle-filled` | `Filled Icons/check-circle-Filled` | `24153:21164` | *export from node `24153:21164`* |
| `star-01-filled` | `Filled Icons/star-01` | `13561:11332` | *export from node `13561:11332`* |
| `heart-filled` | `Filled Icons/Heart` | `14030:244273` | *export from node `14030:244273`* |
| `cart-filled` | `Filled Icons/Cart-Filled` | `14108:199255` | *export from node `14108:199255`* |
| `flag-02-filled` | `Filled Icons/flag-02` | `14140:38430` | *export from node `14140:38430`* |
| `placeholder-filled` | `Filled Icons/Placeholder - filled` | `14702:49423` | *export from node `14702:49423`* |
| `upi-filled` | `Filled Icons/upi` | `15367:1170` | *export from node `15367:1170`* |

---

## 3. Inline Illustration Icons

> ⚠️ **Do not confuse with Illustration Assets** (full-page PNGs). Inline Illustration Icons are small PNG accents used inside components.
> Prefix: `Illustration/` · Format: **PNG** · Export folder: `icons/illustration/`
> ⛔ Never re-color — they are static PNG assets. Do not apply color tokens or CSS filters.

| Figma Name | Figma Node ID | Use Case / Notes | Format |
|---|---|---|---|
| `Illustration/Default` | `6422:3359` | Default fallback / generic slot | PNG |
| `Illustration/Check-02` | `13289:2555` | Success / verified / task completed | PNG |
| `Illustration/Pending` | `13289:158645` | Pending / in-progress state | PNG |
| `Illustration/Pending` | `13426:11746` | Pending (alternate) | PNG |
| `Illustration/Error` | `13318:88592` | Error / failure state | PNG |
| `Illustration/Offers-%` | `5469:10023` | Discount / cashback offer context | PNG |
| `Illustration/Cash-Money` | `5469:9981` | Savings / cashback credited / reward | PNG |
| `Illustration/POPcoin bag` | `11805:52140` | POPcoin earned / reward moment | PNG |
| `Illustration/coin tilted` | `21152:214252` | Coin-related contextual moment | PNG |
| `Illustration/UPI Lite` | `10807:106143` | UPI Lite onboarding / feature screens | PNG |
| `Illustration/Shield` | `90:7378` | Security / privacy / protection context | PNG |
| `Illustration/Payin3-split` | `5515:4101` | Buy-now-pay-later / Pay in 3 context | PNG |
| `Illustration/Star-Shape` | `10357:101377` | Generic decorative star accent | PNG |
| `Illustration/Decorative star` | `13478:23891` | Celebratory / reward accent | PNG |
| `Illustration/fave` | `17053:91661` | Favourites (State=True / active) | PNG |
| `Illustration/fave` | `17053:91663` | Favourites (State=False / inactive) | PNG |
| `Property 1=1x` | `17529:14947` | Scale property: 1x | PNG |
| `Property 1=2x` | `17529:14946` | Scale property: 2x | PNG |
| `Property 1=3x` | `17529:14945` | Scale property: 3x | PNG |

---

## 4. Logos

> Brand mark SVGs for UPI payment rails and POP brand. Prefix: `Logos/` · Format: SVG
> ⛔ Never re-color logos — they are governed brand marks.
> ⛔ Always use at the exact size specified in the design.

| Code Name | Figma Name | Figma Node ID | SVG | Use Case |
|---|---|---|---|---|
| `upi` | `Logos/upi` | `15367:1207` | *export from node `15367:1207`* | UPI payment rail mark — pickers, transaction confirmation, UPI identity surfaces |
| `upi-lite` | `Logos&Others/upi lite` | `15344:3397` | *export from node `15344:3397`* | UPI Lite balance, setup, and feature surfaces |
| `upi-autopay` | `Logos&Others/UPI autopay` | `15344:3396` | *export from node `15344:3396`* | Autopay setup, mandate management, recurring payment surfaces |
| `pop` | `Logos&Others/Pop` | `19051:72935` | *export from node `19051:72935`* | POP brand moments, identity surfaces, co-branding |
| `indian-flag` | `Logos&Others/Indian-flag1` | `24128:181508` | *export from node `24128:181508`* | Indian flag — nationality / country indicator |

---

## 5. Status Bar Icons

> ⚠️ Rendered by the native OS. Use system hex values **only** — never apply design tokens.
> **Never** use these directly in screens — they are always embedded in the Status Bar organism component.
> Size: iOS battery = 25×12px · iOS wifi = 16×11px · iOS network = 17×11px · Android = 17×17px · Android battery = 8×15px

| Icon Name | Platform | Theme | Figma File | SVG |
|---|---|---|---|---|
| Battery | Android | onwhite | `Battery - Andriod onwhite.svg` | `<svg width="8" height="15" viewBox="0 0 8 15" fill="none" xmlns="http://www.w3.org/2000/svg"> <path opacity="0.3" d="M5.5 0H2.5V1.5H1C0.447715 1.5 0 2.00368 0 2.625V13.875C0 14.4963 0.447715 15 1 15H7C7.55228 15 8 14.4963 8 13.875V2.625C8 2.00368 7.55228 1.5 7 1.5H5.5V0Z" fill="#1D1B20"/> <path d="M6.17902e-06 8C-1.48833e-06 8.58333 1.46004e-07 13.3667 1.46004e-07 13.95C1.46004e-07 14.5299 0.447715 15 1 15H7C7.55228 15 8 14.5299 8 13.95C8 13.3667 8 8.58333 8 8H6.17902e-06Z" fill="#1D1B20"/> </svg>` |
| Battery | Android | onblack | `Battery- Android onblack.svg` | `<svg width="8" height="15" viewBox="0 0 8 15" fill="none" xmlns="http://www.w3.org/2000/svg"> <path opacity="0.3" d="M5.5 0H2.5V1.5H1C0.447715 1.5 0 2.00368 0 2.625V13.875C0 14.4963 0.447715 15 1 15H7C7.55228 15 8 14.4963 8 13.875V2.625C8 2.00368 7.55228 1.5 7 1.5H5.5V0Z" fill="white"/> <path d="M6.17902e-06 8C-1.48833e-06 8.58333 1.46004e-07 13.3667 1.46004e-07 13.95C1.46004e-07 14.5299 0.447715 15 1 15H7C7.55228 15 8 14.5299 8 13.95C8 13.3667 8 8.58333 8 8H6.17902e-06Z" fill="white"/> </svg>` |
| Battery | iOS | onblack | `battery-IOS onblack.svg` | `<svg width="25" height="12" viewBox="0 0 25 12" fill="none" xmlns="http://www.w3.org/2000/svg"> <path opacity="0.35" d="M16.333 0C18.8387 0 20.0923 0.000281334 20.9287 0.702148C21.0622 0.814235 21.1858 0.93775 21.2979 1.07129C21.9997 1.90774 22 3.16127 22 5.66699C22 8.17249 21.9997 9.4253 21.2979 10.2617C21.1856 10.3954 21.0624 10.5196 20.9287 10.6318C20.0923 11.3337 18.8387 11.333 16.333 11.333H5.66699C3.16127 11.333 1.90774 11.3337 1.07129 10.6318C0.937563 10.5196 0.814367 10.3954 0.702148 10.2617C0.000311732 9.4253 1.31744e-09 8.17249 0 5.66699C0 3.16127 0.000281334 1.90774 0.702148 1.07129C0.814235 0.93775 0.93775 0.814235 1.07129 0.702148C1.90774 0.000281334 3.16127 0 5.66699 0H16.333Z" fill="#595959" fill-opacity="0.7"/> <path d="M5.66797 11.333H5.66699C3.16127 11.333 1.90774 11.3337 1.07129 10.6318C0.937563 10.5196 0.814367 10.3954 0.702148 10.2617C0.000311732 9.4253 1.31744e-09 8.17249 0 5.66699C0 3.16127 0.000281334 1.90774 0.702148 1.07129C0.814235 0.93775 0.93775 0.814235 1.07129 0.702148C1.90774 0.000281334 3.16127 0 5.66699 0H5.66797V11.333Z" fill="#FF3B30"/> <path opacity="0.4" d="M23.0002 3.66797V7.66797C23.805 7.32919 24.3283 6.5411 24.3283 5.66797C24.3283 4.79484 23.805 4.00675 23.0002 3.66797" fill="#595959" fill-opacity="0.7"/> </svg>` |
| Battery | iOS | onwhite | `battery-IOS onwhite.svg` | `<svg width="25" height="12" viewBox="0 0 25 12" fill="none" xmlns="http://www.w3.org/2000/svg"> <path opacity="0.35" d="M16.333 0C18.8387 0 20.0923 0.000281334 20.9287 0.702148C21.0622 0.814235 21.1858 0.93775 21.2979 1.07129C21.9997 1.90774 22 3.16127 22 5.66699C22 8.17249 21.9997 9.4253 21.2979 10.2617C21.1856 10.3954 21.0624 10.5196 20.9287 10.6318C20.0923 11.3337 18.8387 11.333 16.333 11.333H5.66699C3.16127 11.333 1.90774 11.3337 1.07129 10.6318C0.937563 10.5196 0.814367 10.3954 0.702148 10.2617C0.000311732 9.4253 1.31744e-09 8.17249 0 5.66699C0 3.16127 0.000281334 1.90774 0.702148 1.07129C0.814235 0.93775 0.93775 0.814235 1.07129 0.702148C1.90774 0.000281334 3.16127 0 5.66699 0H16.333Z" fill="#595959" fill-opacity="0.7"/> <path d="M5.66797 11.333H5.66699C3.16127 11.333 1.90774 11.3337 1.07129 10.6318C0.937563 10.5196 0.814367 10.3954 0.702148 10.2617C0.000311732 9.4253 1.31744e-09 8.17249 0 5.66699C0 3.16127 0.000281334 1.90774 0.702148 1.07129C0.814235 0.93775 0.93775 0.814235 1.07129 0.702148C1.90774 0.000281334 3.16127 0 5.66699 0H5.66797V11.333Z" fill="#FF3B30"/> <path opacity="0.4" d="M23.0002 3.66797V7.66797C23.805 7.32919 24.3283 6.5411 24.3283 5.66797C24.3283 4.79484 23.805 4.00675 23.0002 3.66797" fill="#595959" fill-opacity="0.7"/> </svg>` |
| Network/Signal | Android | onblack | `network-Android onblack.svg` | `<svg width="17" height="17" viewBox="0 0 17 17" fill="none" xmlns="http://www.w3.org/2000/svg"> <path d="M15.5834 1.41663L1.41675 15.5833H15.5834V1.41663V1.41663Z" fill="white"/> </svg>` |
| Network/Signal | Android | onwhite | `network-Android onwhite.svg` | `<svg width="17" height="17" viewBox="0 0 17 17" fill="none" xmlns="http://www.w3.org/2000/svg"> <path d="M15.5834 1.41663L1.41675 15.5833H15.5834V1.41663V1.41663Z" fill="#1D1B20"/> </svg>` |
| Network/Signal | iOS | onblack | `network-IOS onblack.svg` | `<svg width="17" height="11" viewBox="0 0 17 11" fill="none" xmlns="http://www.w3.org/2000/svg"> <path fill-rule="evenodd" clip-rule="evenodd" d="M16 0H15C14.4477 0 14 0.447715 14 1V9.66667C14 10.219 14.4477 10.6667 15 10.6667H16C16.5523 10.6667 17 10.219 17 9.66667V1C17 0.447715 16.5523 0 16 0ZM10.3333 2.33333H11.3333C11.8856 2.33333 12.3333 2.78105 12.3333 3.33333V9.66667C12.3333 10.219 11.8856 10.6667 11.3333 10.6667H10.3333C9.78105 10.6667 9.33333 10.219 9.33333 9.66667V3.33333C9.33333 2.78105 9.78105 2.33333 10.3333 2.33333ZM6.66667 4.66667H5.66667C5.11438 4.66667 4.66667 5.11438 4.66667 5.66667V9.66667C4.66667 10.219 5.11438 10.6667 5.66667 10.6667H6.66667C7.21895 10.6667 7.66667 10.219 7.66667 9.66667V5.66667C7.66667 5.11438 7.21895 4.66667 6.66667 4.66667ZM2 6.66667H1C0.447715 6.66667 0 7.11438 0 7.66667V9.66667C0 10.219 0.447715 10.6667 1 10.6667H2C2.55228 10.6667 3 10.219 3 9.66667V7.66667C3 7.11438 2.55228 6.66667 2 6.66667Z" fill="#0F0F0F"/> </svg>` |
| Network/Signal | iOS | onwhite | `network-IOS onwhite.svg` | `<svg width="17" height="11" viewBox="0 0 17 11" fill="none" xmlns="http://www.w3.org/2000/svg"> <path fill-rule="evenodd" clip-rule="evenodd" d="M16 0H15C14.4477 0 14 0.447715 14 1V9.66667C14 10.219 14.4477 10.6667 15 10.6667H16C16.5523 10.6667 17 10.219 17 9.66667V1C17 0.447715 16.5523 0 16 0ZM10.3333 2.33333H11.3333C11.8856 2.33333 12.3333 2.78105 12.3333 3.33333V9.66667C12.3333 10.219 11.8856 10.6667 11.3333 10.6667H10.3333C9.78105 10.6667 9.33333 10.219 9.33333 9.66667V3.33333C9.33333 2.78105 9.78105 2.33333 10.3333 2.33333ZM6.66667 4.66667H5.66667C5.11438 4.66667 4.66667 5.11438 4.66667 5.66667V9.66667C4.66667 10.219 5.11438 10.6667 5.66667 10.6667H6.66667C7.21895 10.6667 7.66667 10.219 7.66667 9.66667V5.66667C7.66667 5.11438 7.21895 4.66667 6.66667 4.66667ZM2 6.66667H1C0.447715 6.66667 0 7.11438 0 7.66667V9.66667C0 10.219 0.447715 10.6667 1 10.6667H2C2.55228 10.6667 3 10.219 3 9.66667V7.66667C3 7.11438 2.55228 6.66667 2 6.66667Z" fill="#E6E6E6"/> </svg>` |
| WiFi | Android | onblack | `wifi-Android onblack.svg` | `<svg width="17" height="17" viewBox="0 0 17 17" fill="none" xmlns="http://www.w3.org/2000/svg"> <path opacity="0.1" d="M8.5 1.41663C5.1 1.41663 2.125 2.90413 0 5.24163L8.5 15.5833L17 5.24163C14.875 2.90413 11.9 1.41663 8.5 1.41663Z" fill="white"/> </svg>` |
| WiFi | Android | onwhite | `wifi-Android onwhite.svg` | `<svg width="17" height="17" viewBox="0 0 17 17" fill="none" xmlns="http://www.w3.org/2000/svg"> <path opacity="0.1" d="M8.5 1.41663C5.1 1.41663 2.125 2.90413 0 5.24163L8.5 15.5833L17 5.24163C14.875 2.90413 11.9 1.41663 8.5 1.41663Z" fill="#1D1B20"/> </svg>` |
| WiFi | iOS | onblack | `wifi-IOS onblack.svg` | `<svg width="16" height="11" viewBox="0 0 16 11" fill="none" xmlns="http://www.w3.org/2000/svg"> <path fill-rule="evenodd" clip-rule="evenodd" d="M7.63661 2.27733C9.8525 2.27742 11.9837 3.12886 13.5896 4.65566C13.7105 4.77354 13.9038 4.77205 14.0229 4.65233L15.1789 3.48566C15.2392 3.42494 15.2729 3.34269 15.2724 3.25711C15.2719 3.17153 15.2373 3.08967 15.1763 3.02966C10.9612 -1.00989 4.31137 -1.00989 0.0962725 3.02966C0.0352139 3.08963 0.00057 3.17146 6.97078e-06 3.25704C-0.000556058 3.34262 0.0330082 3.42489 0.0932725 3.48566L1.24961 4.65233C1.36863 4.77223 1.56208 4.77372 1.68294 4.65566C3.28909 3.12876 5.4205 2.27732 7.63661 2.27733ZM7.63661 6.07299C8.8541 6.07292 10.0281 6.52545 10.9306 7.34266C11.0527 7.45864 11.245 7.45613 11.3639 7.33699L12.5186 6.17033C12.5794 6.10913 12.6132 6.02612 12.6123 5.93985C12.6114 5.85359 12.576 5.77127 12.5139 5.71133C9.76574 3.15494 5.5098 3.15494 2.76161 5.71133C2.69953 5.77127 2.66411 5.85363 2.6633 5.93992C2.66248 6.02621 2.69634 6.10922 2.75727 6.17033L3.91161 7.33699C4.03059 7.45613 4.22288 7.45864 4.34494 7.34266C5.24681 6.52599 6.41992 6.0735 7.63661 6.07299ZM9.9496 8.62681C9.95137 8.71332 9.91736 8.79672 9.85561 8.85733L7.85827 10.873C7.79972 10.9322 7.7199 10.9656 7.63661 10.9656C7.55332 10.9656 7.47349 10.9322 7.41494 10.873L5.41727 8.85733C5.35556 8.79668 5.32161 8.71325 5.32344 8.62674C5.32527 8.54023 5.36272 8.45831 5.42694 8.40033C6.70251 7.32144 8.5707 7.32144 9.84627 8.40033C9.91045 8.45836 9.94783 8.54031 9.9496 8.62681Z" fill="#0F0F0F"/> </svg>` |
| WiFi | iOS | onwhite | `wifi-IOS onwhite.svg` | `<svg width="16" height="11" viewBox="0 0 16 11" fill="none" xmlns="http://www.w3.org/2000/svg"> <path fill-rule="evenodd" clip-rule="evenodd" d="M7.63661 2.27733C9.8525 2.27742 11.9837 3.12886 13.5896 4.65566C13.7105 4.77354 13.9038 4.77205 14.0229 4.65233L15.1789 3.48566C15.2392 3.42494 15.2729 3.34269 15.2724 3.25711C15.2719 3.17153 15.2373 3.08967 15.1763 3.02966C10.9612 -1.00989 4.31137 -1.00989 0.0962725 3.02966C0.0352139 3.08963 0.00057 3.17146 6.97078e-06 3.25704C-0.000556058 3.34262 0.0330082 3.42489 0.0932725 3.48566L1.24961 4.65233C1.36863 4.77223 1.56208 4.77372 1.68294 4.65566C3.28909 3.12876 5.4205 2.27732 7.63661 2.27733ZM7.63661 6.07299C8.8541 6.07292 10.0281 6.52545 10.9306 7.34266C11.0527 7.45864 11.245 7.45613 11.3639 7.33699L12.5186 6.17033C12.5794 6.10913 12.6132 6.02612 12.6123 5.93985C12.6114 5.85359 12.576 5.77127 12.5139 5.71133C9.76574 3.15494 5.5098 3.15494 2.76161 5.71133C2.69953 5.77127 2.66411 5.85363 2.6633 5.93992C2.66248 6.02621 2.69634 6.10922 2.75727 6.17033L3.91161 7.33699C4.03059 7.45613 4.22288 7.45864 4.34494 7.34266C5.24681 6.52599 6.41992 6.0735 7.63661 6.07299ZM9.9496 8.62681C9.95137 8.71332 9.91736 8.79672 9.85561 8.85733L7.85827 10.873C7.79972 10.9322 7.7199 10.9656 7.63661 10.9656C7.55332 10.9656 7.47349 10.9322 7.41494 10.873L5.41727 8.85733C5.35556 8.79668 5.32161 8.71325 5.32344 8.62674C5.32527 8.54023 5.36272 8.45831 5.42694 8.40033C6.70251 7.32144 8.5707 7.32144 9.84627 8.40033C9.91045 8.45836 9.94783 8.54031 9.9496 8.62681Z" fill="#E6E6E6"/> </svg>` |

---

## 6. Exporting SVGs from Figma

Icons marked *export from node `X:Y`* have not yet been exported. To export:

**Using Figma REST API:**
```
GET https://api.figma.com/v1/images/yDf8UqgkvEJofpxyCYAmIC
  ?ids=5:1182,5:1350,5:1447,5:1434,...
  &format=svg
  &svg_include_id=false
  &svg_simplify_stroke=true
```
Pass your Figma personal access token in the `X-FIGMA-TOKEN` header.

**Using Figma Desktop:**
1. Open the file `yDf8UqgkvEJofpxyCYAmIC`
2. Navigate to the **Icon ✅** page
3. Select the icon component
4. Export as SVG at 1× in the right panel
