# POP DS — SVG Icon Library Reference
> **For AI/LLM Use** — This file is the complete icon reference for the POP Design System.
> Use it to identify which icon to use, get its SVG tag, understand its semantic meaning,
> and map it to the correct `Icons.*` enum value in Kotlin/Compose code.

---

## How to Use This File

| Goal | What to look for |
|---|---|
| Find icon by name | Search the `## Icon:` heading |
| Find icon by use case | Read the `Use when:` field |
| Get the SVG tag | Copy from the `SVG:` code block |
| Get Kotlin code reference | Use the `Icons Enum:` value |
| Get icon style | Check `Style:` — Outline or Solid |
| Understand visual shape | Read `Visual:` description |

---

## Metadata Schema (per icon)

Every icon entry follows this structure:

```
## Icon: {human-readable-name}
- **Icons Enum:**     Icons.{KotlinEnumValue}
- **Style:**          Outline | Solid
- **Category:**       {Navigation | Action | Status | Communication | Commerce | Media | UI Control | Data | Social}
- **Visual:**         {plain english shape description}
- **Use when:**       {semantic usage guidance — when this icon is appropriate}
- **Do NOT use for:** {anti-patterns — common misuse cases}
- **Size tokens:**    IconSize.Small (18dp) | IconSize.Medium (20dp) | IconSize.Large (24dp)
- **Color tokens:**   IconColor.PrimaryFromTokens | IconColor.BrandFromTokens | etc.
SVG: {raw svg tag}
```

---

## Icon Categories

| Category | Count | Description |
|---|---|---|
| Navigation | 18 | Arrows, chevrons, back/forward, menu |
| Action | 22 | Edit, delete, copy, share, download, upload |
| Status | 14 | Check, cross, alert, info, warning, success |
| Communication | 10 | Message, email, phone, notification, bell |
| Commerce | 12 | Cart, wallet, payment, offer, tag, gift |
| Media | 8 | Image, video, camera, play, pause, mic |
| UI Control | 16 | Search, filter, sort, settings, toggle, grid |
| Data | 10 | Chart, graph, analytics, file, folder |
| Social | 6 | Share, link, user, group, star, heart |

---
---

# NAVIGATION ICONS

---

## Icon: Chevron Right
- **Icons Enum:** `Icons.ChevronRight`
- **Style:** Outline
- **Category:** Navigation
- **Visual:** Two stacked right-pointing arrow chevrons (double chevron / fast-forward style). The left chevron is slightly lighter, right one is brighter — suggesting forward momentum.
- **Use when:** Primary forward navigation. "Next" actions. List item trailing arrow. Drill-down navigation. Carousel "next" button. Pagination forward.
- **Do NOT use for:** Back navigation. Dropdown open/close (use ChevronDown). External links (use ExternalLink).
- **Size tokens:** IconSize.Small (18dp) default for list items, IconSize.Medium (20dp) for standalone CTAs
- **Color tokens:** `IconColor.PrimaryFromTokens` default, `IconColor.BrandFromTokens` for active states

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="19" height="20" viewBox="0 0 19 20" fill="none">
  <path d="M-1.71661e-05 1.91202C0.00024928 0.265137 1.92922 -0.585331 3.1428 0.455933L3.25812 0.563101L11.2404 8.40322C11.9857 9.14854 11.9857 10.3581 11.2404 11.1034L3.25812 19.4066C2.0559 20.6088 0.000257885 19.7578 -1.71661e-05 18.0577V1.91202ZM2.38563 16.9044L9.21468 9.7533L2.38563 3.06407V16.9044Z" fill="#E6E6E6"/>
  <path d="M6.83673 1.93823C6.83699 0.291345 8.76597 -0.559111 9.97954 0.482147L10.0949 0.589315L17.9251 8.4184C18.6545 9.14781 18.6717 10.3255 17.9647 11.0766L10.1345 19.3961C8.94992 20.6539 6.83697 19.8159 6.83673 18.088V15.6917L9.22238 13.1918V16.8835L15.9099 9.77897L9.22238 3.09029V6.41133L6.83673 4.07798V1.93823Z" fill="#E6E6E6"/>
</svg>
```

---

## Icon: Chevron Left
- **Icons Enum:** `Icons.ChevronLeft`
- **Style:** Outline
- **Category:** Navigation
- **Visual:** Mirror of ChevronRight — two left-pointing chevrons. Back/previous direction indicator.
- **Use when:** Back navigation in top bar. "Previous" in pagination. Carousel "prev" button. Breadcrumb back. Collapse sidebar.
- **Do NOT use for:** Forward navigation. Dropdown indicators.
- **Size tokens:** IconSize.Medium (20dp) in top bar, IconSize.Small (18dp) in pagination
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="19" height="20" viewBox="0 0 19 20" fill="none">
  <path d="M19 18.088C18.9997 19.7349 17.0708 20.5853 15.8572 19.5441L15.7419 19.4369L7.75959 11.5968C7.01432 10.8515 7.01432 9.64191 7.75959 8.89664L15.7419 0.593385C16.9441 -0.608835 19 0.242216 19 1.94228V18.088ZM16.6144 3.09557L9.78532 10.2467L16.6144 16.9359V3.09557Z" fill="#E6E6E6"/>
  <path d="M12.1633 18.0618C12.163 19.7087 10.234 20.5591 9.02047 19.5179L8.90515 19.4107L1.07491 11.5816C0.345477 10.8522 0.328304 9.67452 1.03534 8.92342L8.86549 0.603888C10.0501 -0.653912 12.163 0.184112 12.1633 1.91202V4.30826L9.77762 6.80824V3.11649L3.09009 10.221L9.77762 16.9097V13.4887L12.1633 15.822V18.0618Z" fill="#E6E6E6"/>
</svg>
```

---

## Icon: Chevron Down
- **Icons Enum:** `Icons.ChevronDown`
- **Style:** Outline
- **Category:** Navigation
- **Visual:** Downward-pointing chevron/caret. Universal "expand" or "open dropdown" indicator.
- **Use when:** Dropdown menus open state indicator. Accordion expand trigger. Select field. Collapsible panels. "Show more" controls.
- **Do NOT use for:** Navigation back. Scroll down hints (use ArrowDown). Loading states.
- **Size tokens:** IconSize.Small (16dp) inside input fields, IconSize.Medium (20dp) standalone
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.Disabled` when field is disabled

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M4.41107 6.91058C5.07 5.9965 6.39152 5.87904 7.20361 6.63L7.30464 6.73313L10 9.42849L12.6954 6.73313C13.4817 5.94683 14.7653 5.93625 15.5651 6.69877L15.5889 6.72277C16.3752 7.50907 16.3858 8.79268 15.6233 9.59249L11.4128 13.803C10.6303 14.5856 9.36972 14.5856 8.58717 13.803L4.37663 9.59249C3.78581 9.00167 3.69524 8.08122 4.10529 7.39145L4.20069 7.24355L4.41107 6.91058Z" fill="#E6E6E6"/>
</svg>
```

---

## Icon: Chevron Up
- **Icons Enum:** `Icons.ChevronUp`
- **Style:** Outline
- **Category:** Navigation
- **Visual:** Upward-pointing chevron/caret. Universal "collapse" or "close dropdown" indicator.
- **Use when:** Accordion collapse state. Dropdown open state (pointing up = already open). "Show less" controls. Scroll to top hint.
- **Do NOT use for:** Navigation forward. Upvote (use ThumbsUp or ArrowUp).
- **Size tokens:** IconSize.Small (16dp) inside fields, IconSize.Medium (20dp) standalone
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M15.5889 13.0894C14.93 14.0035 13.6085 14.121 12.7964 13.37L12.6954 13.2669L10 10.5715L7.30464 13.2669C6.51834 14.0532 5.23473 14.0638 4.43492 13.3012L4.41107 13.2772C3.62477 12.4909 3.61419 11.2073 4.37671 10.4075L8.58717 6.19699C9.36972 5.41444 10.6303 5.41444 11.4128 6.19699L15.6234 10.4075C16.2142 10.9983 16.3048 11.9188 15.8947 12.6085L15.7993 12.7565L15.5889 13.0894Z" fill="#E6E6E6"/>
</svg>
```

---

## Icon: Arrow Left
- **Icons Enum:** `Icons.ArrowLeft`
- **Style:** Outline
- **Category:** Navigation
- **Visual:** Single left-pointing arrow with a horizontal shaft and arrowhead. More deliberate/strong than a chevron.
- **Use when:** Primary back button in top app bar. Go back action in full-screen modals. Undo direction.
- **Do NOT use for:** List item navigation (use ChevronRight). Dropdown toggles.
- **Size tokens:** IconSize.Medium (20dp) in PopAppBar
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M15.8333 10.0001C15.8333 10.4603 15.4602 10.8334 15 10.8334H6.17258L9.75592 14.4167C10.0814 14.7422 10.0814 15.2696 9.75592 15.595C9.43046 15.9205 8.903 15.9205 8.57754 15.595L3.57754 10.595C3.25208 10.2696 3.25208 9.74214 3.57754 9.41668L8.57754 4.41668C8.903 4.09122 9.43046 4.09122 9.75592 4.41668C10.0814 4.74214 10.0814 5.2696 9.75592 5.59506L6.17258 9.17839H15C15.4602 9.17839 15.8333 9.5515 15.8333 10.0001Z" fill="#E6E6E6"/>
</svg>
```

---

## Icon: Arrow Right
- **Icons Enum:** `Icons.ArrowRight`
- **Style:** Outline
- **Category:** Navigation
- **Visual:** Single right-pointing arrow with a horizontal shaft and arrowhead.
- **Use when:** Proceed/continue actions. External link direction. "See all" row trailing. Step progression.
- **Do NOT use for:** Back navigation. Dropdown indicators (use ChevronDown).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.BrandFromTokens` for CTAs, `IconColor.PrimaryFromTokens` for neutral

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M4.16667 10.0001C4.16667 9.5399 4.53976 9.16681 5 9.16681H13.8274L10.2441 5.5835C9.91864 5.25804 9.91864 4.73058 10.2441 4.40512C10.5696 4.07966 11.097 4.07966 11.4225 4.40512L16.4225 9.40512C16.7479 9.73058 16.7479 10.2581 16.4225 10.5835L11.4225 15.5835C11.097 15.909 10.5696 15.909 10.2441 15.5835C9.91864 15.2581 9.91864 14.7306 10.2441 14.4051L13.8274 10.8218H5C4.53976 10.8218 4.16667 10.4487 4.16667 10.0001Z" fill="#E6E6E6"/>
</svg>
```

---

## Icon: Home
- **Icons Enum:** `Icons.Home`
- **Style:** Outline
- **Category:** Navigation
- **Visual:** Classic house silhouette — peaked roof with a rectangular base and small door at bottom center.
- **Use when:** Bottom navigation "Home" tab. Dashboard link. Return to main screen. Home screen shortcut.
- **Do NOT use for:** Settings. Profile. Anything non-home-related.
- **Size tokens:** IconSize.Medium (20dp) in bottom navigation
- **Color tokens:** `IconColor.PrimaryFromTokens` inactive, `IconColor.BrandFromTokens` active tab

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M10.5893 2.07741C10.2561 1.80753 9.74384 1.80753 9.41068 2.07741L2.5774 7.57741C2.35433 7.7574 2.22217 8.02757 2.22217 8.31329V16.6666C2.22217 17.2189 2.66988 17.6666 3.22217 17.6666H7.22217C7.77445 17.6666 8.22217 17.2189 8.22217 16.6666V13.3333C8.22217 12.7810 8.66988 12.3333 9.22217 12.3333H10.7778C11.3301 12.3333 11.7778 12.7810 11.7778 13.3333V16.6666C11.7778 17.2189 12.2255 17.6666 12.7778 17.6666H16.7778C17.3301 17.6666 17.7778 17.2189 17.7778 16.6666V8.31329C17.7778 8.02757 17.6456 7.7574 17.4226 7.57741L10.5893 2.07741Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Menu (Hamburger)
- **Icons Enum:** `Icons.Menu`
- **Style:** Outline
- **Category:** Navigation
- **Visual:** Three horizontal parallel lines stacked vertically. Universal hamburger menu icon.
- **Use when:** Opening navigation drawer/sidebar. Compact menu in top bar. Options overflow on mobile.
- **Do NOT use for:** Settings (use Settings icon). Filters (use Filter icon). List views.
- **Size tokens:** IconSize.Medium (20dp) in PopAppBar
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M2.5 10H17.5M2.5 5H17.5M2.5 15H17.5" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Close (Cross / X)
- **Icons Enum:** `Icons.Cross`
- **Style:** Outline
- **Category:** Navigation
- **Visual:** Two diagonal lines crossing at center forming an X shape. Standard dismiss/close symbol.
- **Use when:** Closing bottom sheets, modals, dialogs. Dismissing toasts. Clearing input field content. Removing chips (PopChip WithClose variant). Cancel actions.
- **Do NOT use for:** Error status indicators (use AlertCircle). Delete confirmation (use Trash).
- **Size tokens:** IconSize.Small (16dp) in chip close button, IconSize.Medium (20dp) in modal headers
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.Disabled` for inactive

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M15 5L5 15M5 5L15 15" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---
---

# ACTION ICONS

---

## Icon: Search
- **Icons Enum:** `Icons.Search`
- **Style:** Outline
- **Category:** UI Control
- **Visual:** Magnifying glass — a circle with a diagonal handle extending to the bottom right.
- **Use when:** Search input field leading icon (PopInputFieldV2 Search type). Search bar trigger button. "Find" actions. Filter by keyword.
- **Do NOT use for:** Filter (use Filter icon). Zoom in/out.
- **Size tokens:** IconSize.Small (18dp) inside input fields
- **Color tokens:** `IconColor.PrimaryFromTokens` default, `IconColor.BrandFromTokens` when focused

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M17.5 17.5L13.875 13.875M15.8333 9.16667C15.8333 12.8486 12.8486 15.8333 9.16667 15.8333C5.48477 15.8333 2.5 12.8486 2.5 9.16667C2.5 5.48477 5.48477 2.5 9.16667 2.5C12.8486 2.5 15.8333 5.48477 15.8333 9.16667Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Filter
- **Icons Enum:** `Icons.Filter`
- **Style:** Outline
- **Category:** UI Control
- **Visual:** Funnel/triangle shape with horizontal lines narrowing downward — classic filter symbol.
- **Use when:** Filter chips trigger. Sort/filter panel open button. Table column filter. Search results refinement.
- **Do NOT use for:** Search (use Search icon). Settings (use Settings icon).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.BrandFromTokens` when filter is active

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M2.5 5.83333C2.5 5.37309 2.8731 5 3.33333 5H16.6667C17.1269 5 17.5 5.37309 17.5 5.83333C17.5 6.06344 17.4078 6.28441 17.2453 6.44694L12.5 11.1922V16.6667C12.5 17.0382 12.2765 17.3731 11.9354 17.5166C11.5943 17.6601 11.2034 17.5842 10.9393 17.3201L7.60598 13.9867C7.38192 13.7627 7.25 13.4588 7.25 13.1416V11.1922L2.75474 6.44694C2.5922 6.28441 2.5 6.06344 2.5 5.83333Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Edit (Pencil)
- **Icons Enum:** `Icons.Edit`
- **Style:** Outline
- **Category:** Action
- **Visual:** Pencil/pen at a diagonal angle with a writing tip at the bottom-left and eraser at top-right.
- **Use when:** Edit profile. Modify details. Rename item. Inline edit trigger. Update form fields.
- **Do NOT use for:** Create new (use Plus). Write message (use MessageEdit). Draw/annotate.
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.BrandFromTokens` for primary edit CTA

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M14.1667 2.5L17.5 5.83333L6.66667 16.6667H3.33333V13.3333L14.1667 2.5Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M11.6667 4.99992L15.0001 8.33325" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Delete (Trash)
- **Icons Enum:** `Icons.Trash`
- **Style:** Outline
- **Category:** Action
- **Visual:** Waste bin/trash can — rectangular body with a lid on top and vertical lines inside representing items.
- **Use when:** Delete item permanently. Remove from list. Clear all. Destructive action confirmation.
- **Do NOT use for:** Archive (use Archive icon). Hide (use EyeOff). Cancel (use Cross).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.DestructiveFromTokens` — ALWAYS use destructive color for delete actions

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M2.5 5H17.5M15.8333 5L15.1117 15.1213C15.0497 15.9905 14.3272 16.6667 13.4561 16.6667H6.54394C5.67277 16.6667 4.95033 15.9905 4.88826 15.1213L4.16667 5M8.33333 9.16667V13.3333M11.6667 9.16667V13.3333M12.5 5V3.33333C12.5 2.8731 12.1269 2.5 11.6667 2.5H8.33333C7.8731 2.5 7.5 2.8731 7.5 3.33333V5" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Share
- **Icons Enum:** `Icons.Share05`
- **Style:** Outline
- **Category:** Action
- **Visual:** Three dots connected by lines — two dots on the left (top and bottom) connected to one dot on the right. Classic share network graph symbol.
- **Use when:** Share content. Send to another app. Share link. Social sharing. Export to other platforms.
- **Do NOT use for:** Send message (use Send). Upload (use Upload). Export file (use Download).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M15 13.3333C14.337 13.3333 13.7411 13.5937 13.2985 14.0178L7.55833 10.75C7.59833 10.5067 7.625 10.2567 7.625 10C7.625 9.74333 7.59833 9.49333 7.55833 9.25L13.2333 5.98333C13.6911 6.42333 14.3111 6.66667 15 6.66667C16.3808 6.66667 17.5 5.5475 17.5 4.16667C17.5 2.78583 16.3808 1.66667 15 1.66667C13.6192 1.66667 12.5 2.78583 12.5 4.16667C12.5 4.42333 12.5267 4.67333 12.5667 4.91667L6.89167 8.18333C6.43389 7.74333 5.81389 7.5 5.125 7.5C3.74417 7.5 2.625 8.61917 2.625 10C2.625 11.3808 3.74417 12.5 5.125 12.5C5.81389 12.5 6.43389 12.2567 6.89167 11.8167L12.625 15.0917C12.5767 15.3183 12.5 15.5617 12.5 15.8333C12.5 17.1725 13.6192 18.3333 15 18.3333C16.3808 18.3333 17.5 17.1725 17.5 15.8333C17.5 14.4942 16.3808 13.3333 15 13.3333Z" fill="#E6E6E6"/>
</svg>
```

---

## Icon: Copy
- **Icons Enum:** `Icons.Copy`
- **Style:** Outline
- **Category:** Action
- **Visual:** Two overlapping rectangles — one behind and offset to top-right, one in front. Universal copy symbol.
- **Use when:** Copy to clipboard. Duplicate item. Copy code snippet. Copy link/ID.
- **Do NOT use for:** Paste (no paste icon — use Copy). Cut. Share (use Share05).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <rect x="7.5" y="7.5" width="9.16667" height="9.16667" rx="1.5" stroke="#E6E6E6" stroke-width="1.5"/>
  <path d="M13.3333 7.5V5.83333C13.3333 4.91286 12.5871 4.16667 11.6667 4.16667H5.83333C4.91286 4.16667 4.16667 4.91286 4.16667 5.83333V11.6667C4.16667 12.5871 4.91286 13.3333 5.83333 13.3333H7.5" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round"/>
</svg>
```

---

## Icon: Download
- **Icons Enum:** `Icons.Download`
- **Style:** Outline
- **Category:** Action
- **Visual:** Downward arrow pointing into a horizontal tray/line at the bottom. Download-to-device symbol.
- **Use when:** Download file. Save to device. Export data. Get receipt. Download statement.
- **Do NOT use for:** Upload (use Upload). Share (use Share05). Import.
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.BrandFromTokens` for primary download CTA

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M17.5 12.5V15.8333C17.5 16.7538 16.7538 17.5 15.8333 17.5H4.16667C3.24619 17.5 2.5 16.7538 2.5 15.8333V12.5M10 2.5V13.3333M10 13.3333L6.66667 10M10 13.3333L13.3333 10" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Upload
- **Icons Enum:** `Icons.Upload`
- **Style:** Outline
- **Category:** Action
- **Visual:** Upward arrow pointing out of a horizontal tray/line at the bottom. Upload-from-device symbol.
- **Use when:** Upload file. Attach document. Submit photo. Import data. Send file.
- **Do NOT use for:** Download (use Download). Share (use Share05).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M17.5 12.5V15.8333C17.5 16.7538 16.7538 17.5 15.8333 17.5H4.16667C3.24619 17.5 2.5 16.7538 2.5 15.8333V12.5M10 13.3333V2.5M10 2.5L6.66667 5.83333M10 2.5L13.3333 5.83333" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Plus (Add)
- **Icons Enum:** `Icons.Plus`
- **Style:** Outline
- **Category:** Action
- **Visual:** Simple plus sign — vertical and horizontal lines crossing at center.
- **Use when:** Add new item. Create new entry. Add to cart. Expand/add a field. New beneficiary. Add card.
- **Do NOT use for:** Edit existing (use Edit). Expand accordion (use ChevronDown). Math operations display.
- **Size tokens:** IconSize.Small (16dp) in compact buttons, IconSize.Medium (20dp) standalone
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.BrandFromTokens` in add CTAs

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M10 4.16667V15.8333M4.16667 10H15.8333" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Minus
- **Icons Enum:** `Icons.Minus`
- **Style:** Outline
- **Category:** Action
- **Visual:** Single horizontal line. Subtraction / remove / collapse symbol.
- **Use when:** Remove from cart. Decrement quantity. Collapse/minimize panel. Subtract value.
- **Do NOT use for:** Delete (use Trash). Close (use Cross). Divider.
- **Size tokens:** IconSize.Small (16dp), IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.DestructiveFromTokens` for remove actions

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M4.16667 10H15.8333" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Refresh / Retry
- **Icons Enum:** `Icons.Refresh`
- **Style:** Outline
- **Category:** Action
- **Visual:** Circular arrow — an arc that almost completes a full circle with an arrowhead, suggesting rotation/refresh.
- **Use when:** Retry failed action. Refresh data/feed. Reload page. Resend OTP. Sync status.
- **Do NOT use for:** Undo (use ArrowLeft). Rotate image. Loading (use shimmer).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.BrandFromTokens` for retry CTAs

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M2.5 10C2.5 5.85786 5.85786 2.5 10 2.5C12.4036 2.5 14.5474 3.59226 16.0021 5.31298L17.5 6.66667M17.5 10C17.5 14.1421 14.1421 17.5 10 17.5C7.59639 17.5 5.45259 16.4077 3.99787 14.687L2.5 13.3333M17.5 6.66667V2.5M17.5 6.66667H13.3333M2.5 13.3333L2.5 17.5M2.5 13.3333H6.66667" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---
---

# STATUS ICONS

---

## Icon: Check
- **Icons Enum:** `Icons.Check`
- **Style:** Outline
- **Category:** Status
- **Visual:** A checkmark/tick — a line going from bottom-left up to a midpoint then diagonally up-right. Pure confirmation symbol.
- **Use when:** Success confirmation. Completed step. Selected state. Verified item. Transaction success. OTP verified.
- **Do NOT use for:** Checkbox UI (use PopCheckBoxV2 component). Toggle states (use PopToggle).
- **Size tokens:** IconSize.Small (16dp) in inline status, IconSize.Medium (20dp) standalone
- **Color tokens:** `IconColor.SuccessFromTokens` for success, `IconColor.PrimaryFromTokens` for neutral selection

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M16.6667 5L7.50004 14.1667L3.33337 10" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Check Circle
- **Icons Enum:** `Icons.CheckCircle`
- **Style:** Outline
- **Category:** Status
- **Visual:** A circle with a checkmark inside. Stronger success confirmation than plain checkmark.
- **Use when:** Success state in input fields (PopInputFieldV2 success status). Payment successful. Account verified. Step completed in onboarding. Toast success.
- **Do NOT use for:** Mere selection (use Check). Warning (use AlertCircle).
- **Size tokens:** IconSize.Medium (20dp) in input fields, IconSize.Large (24dp) in success screens
- **Color tokens:** `IconColor.SuccessFromTokens` — ALWAYS use success color

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M18.3334 9.2333V9.99997C18.3323 11.797 17.7504 13.5456 16.6745 14.9849C15.5985 16.4242 14.0861 17.4771 12.3628 17.9866C10.6395 18.4961 8.79774 18.4349 7.11208 17.8122C5.42641 17.1894 3.98718 16.0384 3.00882 14.5309C2.03047 13.0234 1.56588 11.24 1.68454 9.44693C1.80319 7.65387 2.498 5.94691 3.66956 4.58086C4.84112 3.21482 6.42378 2.26279 8.18051 1.86676C9.93724 1.47073 11.7752 1.65192 13.4167 2.3833M18.3334 3.3333L10 11.675L7.50004 9.17497" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Alert Circle (Info / Error)
- **Icons Enum:** `Icons.AlertCircle`
- **Style:** Outline
- **Category:** Status
- **Visual:** Circle with an exclamation mark inside (vertical line + dot). Used for both info and error.
- **Use when:** Error state in input fields. Error toast. Form validation error. Warning message. Info tooltip trigger.
- **Do NOT use for:** Success (use CheckCircle). Generic information display (use Info).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.DestructiveFromTokens` for errors, `IconColor.BrandFromTokens` for info

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M10 6.66663V9.99996M10 13.3333H10.0083M18.3333 9.99996C18.3333 14.6023 14.6023 18.3333 10 18.3333C5.39762 18.3333 1.66666 14.6023 1.66666 9.99996C1.66666 5.39759 5.39762 1.66663 10 1.66663C14.6023 1.66663 18.3333 5.39759 18.3333 9.99996Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Alert Triangle (Warning)
- **Icons Enum:** `Icons.AlertTriangle`
- **Style:** Outline
- **Category:** Status
- **Visual:** Triangle with a point at top and flat base, exclamation mark inside. Universal warning symbol.
- **Use when:** Warning state in input fields (PopInputFieldV2 warning status). Caution messages. Risky action confirmation. Low balance warning. Limit exceeded.
- **Do NOT use for:** Error (use AlertCircle). Info (use Info icon).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.*` with Warning color — TextColor.Warning

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M8.57465 3.21667L1.51632 15C1.37079 15.252 1.29379 15.5377 1.29298 15.8288C1.29216 16.12 1.36757 16.4061 1.51167 16.659C1.65577 16.9119 1.86359 17.1228 2.11441 17.2707C2.36523 17.4186 2.64994 17.498 2.93965 17.5H17.0563C17.3461 17.498 17.6308 17.4186 17.8816 17.2707C18.1324 17.1228 18.3402 16.9119 18.4843 16.659C18.6284 16.4061 18.7038 16.12 18.703 15.8288C18.7022 15.5377 18.6252 15.252 18.4797 15L11.4213 3.21667C11.2728 2.97175 11.0635 2.76928 10.8136 2.62907C10.5637 2.48886 10.2818 2.41553 9.99548 2.41553C9.70917 2.41553 9.42725 2.48886 9.17735 2.62907C8.92745 2.76928 8.71813 2.97175 8.57465 3.21667V3.21667Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M10 7.5V10.8333" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M10 14.1667H10.0083" stroke="#E6E6E6" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Info
- **Icons Enum:** `Icons.Info`
- **Style:** Outline
- **Category:** Status
- **Visual:** Circle with a lowercase letter "i" inside (dot above, vertical bar below). Standard information symbol.
- **Use when:** Info state in input fields (PopInputFieldV2 info status). Tooltip trigger. Help text indicator. "Learn more" hint. Contextual info popup.
- **Do NOT use for:** Error (use AlertCircle). Warning (use AlertTriangle).
- **Size tokens:** IconSize.Small (16dp) in helper text, IconSize.Medium (20dp) standalone
- **Color tokens:** `IconColor.BrandFromTokens` for info, `IconColor.PrimaryFromTokens` neutral

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M10 13.3333V9.99996M10 6.66663H10.0083M18.3333 9.99996C18.3333 14.6023 14.6023 18.3333 10 18.3333C5.39762 18.3333 1.66666 14.6023 1.66666 9.99996C1.66666 5.39759 5.39762 1.66663 10 1.66663C14.6023 1.66663 18.3333 5.39759 18.3333 9.99996Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---
---

# COMMUNICATION ICONS

---

## Icon: Bell (Notification)
- **Icons Enum:** `Icons.Bell`
- **Style:** Outline
- **Category:** Communication
- **Visual:** Bell shape — rounded dome with a small clapper dot at bottom and a flat base representing the mounting point.
- **Use when:** Notifications center. Alert preferences. Push notification toggle. Unread alerts badge. Notification settings.
- **Do NOT use for:** Alarm clock. Doorbell. Generic audio.
- **Size tokens:** IconSize.Medium (20dp) in top bar action slots, bottom navigation
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.BrandFromTokens` when notifications are unread

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M15 6.66663C15 5.34054 14.4732 4.06877 13.5355 3.13109C12.5979 2.19341 11.3261 1.66663 10 1.66663C8.67392 1.66663 7.40215 2.19341 6.46447 3.13109C5.52678 4.06877 5 5.34054 5 6.66663C5 12.4999 2.5 14.1666 2.5 14.1666H17.5C17.5 14.1666 15 12.4999 15 6.66663Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M11.4416 17.5C11.2952 17.7526 11.0849 17.9622 10.832 18.1079C10.5791 18.2537 10.2921 18.3304 9.99996 18.3304C9.70782 18.3304 9.42083 18.2537 9.16794 18.1079C8.91505 17.9622 8.70469 17.7526 8.55829 17.5" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Message (Chat Bubble)
- **Icons Enum:** `Icons.MessageCircle`
- **Style:** Outline
- **Category:** Communication
- **Visual:** Rounded speech bubble / chat balloon with a small tail at the bottom-left.
- **Use when:** Chat/messaging feature. Support chat. Comments section. Customer service. In-app messaging.
- **Do NOT use for:** Email (use Mail). Notifications (use Bell). SMS specifically.
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M17.5 9.58329C17.5029 10.6827 17.2459 11.7666 16.75 12.75C16.162 13.9264 15.2581 14.916 14.1395 15.6077C13.021 16.2994 11.7319 16.6662 10.4167 16.6666C9.31714 16.6695 8.23318 16.4125 7.25004 15.9166L2.50004 17.5L4.08337 12.75C3.58749 11.7666 3.33045 10.6827 3.33337 9.58329C3.33384 8.26808 3.70051 6.97897 4.39222 5.86043C5.08394 4.7419 6.07352 3.83801 7.25004 3.24996C8.23318 2.75408 9.31714 2.49704 10.4167 2.49996H10.8334C12.5703 2.59579 14.2109 3.32852 15.4455 4.56313C16.6801 5.79774 17.4128 7.43834 17.5084 9.17496L17.5 9.58329Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Mail (Email)
- **Icons Enum:** `Icons.Mail`
- **Style:** Outline
- **Category:** Communication
- **Visual:** Envelope shape — rectangle with a V-shaped flap at top (like an open envelope seen from front).
- **Use when:** Email address field. Send email. Contact via email. Email verification. Inbox link.
- **Do NOT use for:** Chat (use MessageCircle). Notification (use Bell). SMS.
- **Size tokens:** IconSize.Small (18dp) in input field leading icon, IconSize.Medium (20dp) standalone
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M2.5 6.66663C2.5 5.74615 3.24619 4.99996 4.16667 4.99996H15.8333C16.7538 4.99996 17.5 5.74615 17.5 6.66663V13.3333C17.5 14.2538 16.7538 15 15.8333 15H4.16667C3.24619 15 2.5 14.2538 2.5 13.3333V6.66663Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M2.5 7.08329L10 11.25L17.5 7.08329" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Phone
- **Icons Enum:** `Icons.Phone`
- **Style:** Outline
- **Category:** Communication
- **Visual:** Classic telephone handset silhouette rotated diagonally — receiver and mouthpiece visible.
- **Use when:** Phone number input field. Call action. Contact via phone. Mobile number verification. Click-to-call.
- **Do NOT use for:** Video call (use Video). Mobile device reference (use Smartphone).
- **Size tokens:** IconSize.Small (18dp) in input leading icon, IconSize.Medium (20dp) standalone
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M18.3083 15.275C18.3083 15.575 18.2416 15.8833 18.1 16.1833C17.9583 16.4833 17.775 16.7667 17.5333 17.0333C17.125 17.4833 16.675 17.7 16.2 17.7C15.9 17.7 15.575 17.6333 15.225 17.4917C14.8833 17.35 14.5166 17.15 14.125 16.8833C13.725 16.6083 13.35 16.3083 12.9916 15.9833C12.625 15.65 12.275 15.3 11.9416 14.9333C10.8 13.7583 9.83331 12.5167 9.04165 11.225C8.25831 9.94167 7.86665 8.71667 7.86665 7.55C7.86665 7.26667 7.92498 6.99167 8.04165 6.74167C8.15831 6.48334 8.33331 6.24167 8.57498 6.025C8.86665 5.74167 9.18331 5.60834 9.51665 5.60834C9.64165 5.60834 9.76665 5.63334 9.87498 5.68334C9.99165 5.73334 10.095 5.81667 10.175 5.93334L11.3083 7.55C11.3883 7.65833 11.4416 7.76667 11.4766 7.86667C11.5116 7.95833 11.5316 8.05 11.5316 8.13334C11.5316 8.23334 11.5033 8.33334 11.4466 8.425C11.3983 8.51667 11.325 8.61667 11.2333 8.71667L10.9333 9.02501C10.8916 9.06667 10.8733 9.11667 10.8733 9.18334C10.8733 9.21667 10.8783 9.24167 10.8883 9.27501C10.9066 9.30834 10.925 9.34167 10.9483 9.37501C11.1316 9.71667 11.3833 10.075 11.7 10.4417C12.025 10.8083 12.3666 11.1833 12.7333 11.5417C13.0833 11.8917 13.4333 12.225 13.8 12.5333C14.1583 12.8333 14.5166 13.075 14.8666 13.2583L14.8916 13.2667C14.925 13.275 14.9583 13.2917 14.9916 13.2917C15.0666 13.2917 15.1166 13.2667 15.1583 13.225L15.4833 12.9C15.5916 12.7917 15.7 12.7167 15.8 12.675C15.9 12.625 16 12.6 16.1083 12.6C16.1916 12.6 16.2833 12.6167 16.3833 12.6583C16.4833 12.7 16.5916 12.7583 16.7 12.8417L18.3416 13.9917C18.4583 14.075 18.5416 14.175 18.5833 14.2917C18.6166 14.4083 18.625 14.5333 18.625 14.6667" stroke="#E6E6E6" stroke-width="1.5" stroke-miterlimit="10"/>
</svg>
```

---
---

# COMMERCE ICONS

---

## Icon: Wallet
- **Icons Enum:** `Icons.Wallet`
- **Style:** Outline
- **Category:** Commerce
- **Visual:** A rectangular wallet/purse shape with a coin slot on the right side and a small circular detail.
- **Use when:** Wallet balance display. Payment method. POP Pay / UPI wallet. Add money to wallet. Wallet settings.
- **Do NOT use for:** Bank account (use Bank). Credit card (use CreditCard). Cash.
- **Size tokens:** IconSize.Medium (20dp), IconSize.Large (24dp) in prominent balance displays
- **Color tokens:** `IconColor.BrandFromTokens` for wallet CTA, `IconColor.PrimaryFromTokens` neutral

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M1.66669 5.83329C1.66669 4.91282 2.41288 4.16663 3.33335 4.16663H16.6667C17.5872 4.16663 18.3334 4.91282 18.3334 5.83329V14.1666C18.3334 15.0871 17.5872 15.8333 16.6667 15.8333H3.33335C2.41288 15.8333 1.66669 15.0871 1.66669 14.1666V5.83329Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M13.3334 10C13.3334 9.07952 14.0795 8.33333 15.0001 8.33333H18.3334V11.6667H15.0001C14.0795 11.6667 13.3334 10.9205 13.3334 10Z" stroke="#E6E6E6" stroke-width="1.5"/>
  <path d="M1.66669 7.5H18.3334" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round"/>
</svg>
```

---

## Icon: Credit Card
- **Icons Enum:** `Icons.CreditCard`
- **Style:** Outline
- **Category:** Commerce
- **Visual:** Rectangle representing a payment card with a horizontal stripe (chip or magnetic strip) near the top.
- **Use when:** Add payment card. Saved cards list. Card number input. Pay by card. Card management.
- **Do NOT use for:** Wallet (use Wallet). UPI (use specific UPI icon). Bank (use Bank).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <rect x="1.66669" y="4.16663" width="16.6667" height="11.6667" rx="2" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M1.66669 7.5H18.3334" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M5 11.6666H5.00833" stroke="#E6E6E6" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M8.33331 11.6666H10.8333" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Tag (Offer / Discount)
- **Icons Enum:** `Icons.Tag`
- **Style:** Outline
- **Category:** Commerce
- **Visual:** Price tag shape — a tilted rectangle/pentagon with a small circle hole at top-left (the hole for the string).
- **Use when:** Discount/offer badge. Promo code. Coupon. Offer highlight section. Sale tag. Deal label.
- **Do NOT use for:** Label/chip text. Category tag. Settings label.
- **Size tokens:** IconSize.Small (16dp) in badge, IconSize.Medium (20dp) in offer cards
- **Color tokens:** `IconColor.BrandFromTokens` for active offers, `IconColor.PrimaryFromTokens` neutral

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M10.9916 2.6583L17.3333 9.00497C17.6542 9.32632 17.8343 9.76035 17.8343 10.2125C17.8343 10.6646 17.6542 11.0986 17.3333 11.42L11.4166 17.3366C11.0953 17.6576 10.6612 17.8376 10.2091 17.8376C9.75703 17.8376 9.323 17.6576 9.00163 17.3366L2.65997 10.995C2.49977 10.8351 2.37258 10.6445 2.28579 10.4347C2.199 10.2249 2.15437 9.99998 2.1541 9.77288V4.16622C2.1541 3.72419 2.33049 3.30027 2.64305 2.98771C2.95561 2.67515 3.37953 2.49876 3.82156 2.49876H9.42822C9.65592 2.49887 9.88152 2.5438 10.0917 2.63096C10.3019 2.71813 10.4928 2.84574 10.6533 3.00622L10.9916 2.6583Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M5.83331 5.83329H5.84165" stroke="#E6E6E6" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Gift
- **Icons Enum:** `Icons.Gift`
- **Style:** Outline
- **Category:** Commerce
- **Visual:** Gift box — a rectangle with a lid on top, a ribbon going horizontally across the middle and vertically over the top, with a bow at the top center.
- **Use when:** Rewards. Cashback gift. Referral bonus. Special offer. Gift card. Surprise reveal.
- **Do NOT use for:** Package delivery (use Package). Parcel. Shopping.
- **Size tokens:** IconSize.Medium (20dp), IconSize.Large (24dp) in reward screens
- **Color tokens:** `IconColor.BrandFromTokens` for rewards, `IconColor.PrimaryFromTokens` neutral

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M16.6667 10V17.5H3.33337V10" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M18.3334 6.66663H1.66669V9.99996H18.3334V6.66663Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M10 17.5V6.66663" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M10 6.66667H6.25C5.69747 6.66667 5.16756 6.44717 4.77686 6.05647C4.38616 5.66577 4.16667 5.13587 4.16667 4.58333C4.16667 4.0308 4.38616 3.50089 4.77686 3.11019C5.16756 2.71949 5.69747 2.5 6.25 2.5C9.16667 2.5 10 6.66667 10 6.66667Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M10 6.66667H13.75C14.3025 6.66667 14.8325 6.44717 15.2232 6.05647C15.6139 5.66577 15.8334 5.13587 15.8334 4.58333C15.8334 4.0308 15.6139 3.50089 15.2232 3.11019C14.8325 2.71949 14.3025 2.5 13.75 2.5C10.8334 2.5 10 6.66667 10 6.66667Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---
---

# UI CONTROL ICONS

---

## Icon: Settings (Sliders)
- **Icons Enum:** `Icons.Settings`
- **Style:** Outline
- **Category:** UI Control
- **Visual:** Gear/cog shape with teeth around a central circle. Universal settings symbol.
- **Use when:** App settings. Account preferences. Configuration panel. Notification settings. Privacy controls.
- **Do NOT use for:** Filter (use Filter). Sort (use SortAscending). Adjust (use Sliders).
- **Size tokens:** IconSize.Medium (20dp) in top bar action slots, bottom navigation
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M10 12.5C11.3807 12.5 12.5 11.3807 12.5 10C12.5 8.61929 11.3807 7.5 10 7.5C8.61929 7.5 7.5 8.61929 7.5 10C7.5 11.3807 8.61929 12.5 10 12.5Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M16.1666 12.5C16.0558 12.7513 16.0227 13.0302 16.0716 13.3005C16.1204 13.5708 16.2489 13.8203 16.4416 14.0167L16.4916 14.0667C16.6465 14.2214 16.7695 14.4053 16.8532 14.6078C16.937 14.8103 16.9799 15.0274 16.9799 15.2467C16.9799 15.466 16.937 15.6831 16.8532 15.8856C16.7695 16.0881 16.6465 16.272 16.4916 16.4267C16.3369 16.5815 16.153 16.7046 15.9505 16.7883C15.748 16.8721 15.5309 16.9149 15.3116 16.9149C15.0923 16.9149 14.8752 16.8721 14.6727 16.7883C14.4702 16.7046 14.2863 16.5815 14.1316 16.4267L14.0816 16.3767C13.8853 16.1839 13.6357 16.0555 13.3654 16.0066C13.0951 15.9578 12.8163 15.9909 12.565 16.1017C12.3186 16.2071 12.1086 16.3821 11.9607 16.6053C11.8128 16.8286 11.7334 17.0902 11.7333 17.3575V17.5C11.7333 17.9421 11.5577 18.366 11.2451 18.6785C10.9326 18.9911 10.5087 19.1667 10.0666 19.1667C9.62449 19.1667 9.20056 18.9911 8.88801 18.6785C8.57545 18.366 8.39993 17.9421 8.39993 17.5V17.425C8.39361 17.1505 8.30279 16.8849 8.13926 16.6644C7.97572 16.4439 7.74745 16.2789 7.48659 16.1917C7.23528 16.0809 6.9565 16.0478 6.68619 16.0966C6.41588 16.1455 6.16629 16.2739 5.96993 16.4667L5.91993 16.5167C5.76519 16.6715 5.58131 16.7946 5.37882 16.8783C5.17634 16.9621 4.95921 17.0049 4.73993 17.0049C4.52064 17.0049 4.30351 16.9621 4.10103 16.8783C3.89854 16.7946 3.71466 16.6715 3.55993 16.5167C3.40512 16.362 3.28206 16.1781 3.19831 15.9756C3.11456 15.7731 3.07172 15.556 3.07172 15.3367C3.07172 15.1174 3.11456 14.9003 3.19831 14.6978C3.28206 14.4953 3.40512 14.3114 3.55993 14.1567L3.60993 14.1067C3.80274 13.9103 3.93117 13.6607 3.97999 13.3904C4.02881 13.1201 3.99567 12.8413 3.88493 12.59C3.77952 12.3436 3.60452 12.1336 3.38126 11.9857C3.158 11.8378 2.89644 11.7584 2.62826 11.7583H2.49993C2.05782 11.7583 1.63389 11.5828 1.32134 11.2702C1.00878 10.9577 0.833252 10.5337 0.833252 10.0917C0.833252 9.64952 1.00878 9.22559 1.32134 8.91304C1.63389 8.60048 2.05782 8.42496 2.49993 8.42496H2.57493C2.84943 8.41864 3.11506 8.32782 3.33551 8.16428C3.55596 8.00074 3.72101 7.77248 3.80826 7.51162C3.91901 7.2603 3.95215 6.98152 3.90333 6.71121C3.85451 6.4409 3.72608 6.19131 3.53326 5.99496L3.48326 5.94496C3.32846 5.79022 3.20539 5.60634 3.12165 5.40386C3.0379 5.20137 2.99506 4.98424 2.99506 4.76496C2.99506 4.54567 3.0379 4.32854 3.12165 4.12606C3.20539 3.92357 3.32846 3.73969 3.48326 3.58496C3.63799 3.43015 3.82188 3.30708 4.02436 3.22334C4.22685 3.13959 4.44397 3.09675 4.66326 3.09675C4.88255 3.09675 5.09967 3.13959 5.30216 3.22334C5.50464 3.30708 5.68852 3.43015 5.84326 3.58496L5.89326 3.63496C6.08961 3.82777 6.3392 3.9562 6.60951 4.00502C6.87982 4.05384 7.1586 4.02071 7.40993 3.90996H7.49993C7.74634 3.80455 7.9563 3.62955 8.10422 3.40629C8.25213 3.18303 8.33155 2.92147 8.33159 2.65329V2.49996C8.33159 2.05784 8.50712 1.63391 8.81968 1.32136C9.13223 1.0088 9.55616 0.833281 9.99826 0.833281C10.4404 0.833281 10.8643 1.0088 11.1769 1.32136C11.4894 1.63391 11.6649 2.05784 11.6649 2.49996V2.57496C11.665 2.84313 11.7444 3.10469 11.8923 3.32795C12.0402 3.55122 12.2502 3.72622 12.4966 3.83163C12.7479 3.94237 13.0267 3.9755 13.297 3.92668C13.5673 3.87786 13.8169 3.74944 14.0133 3.55663L14.0633 3.50663C14.218 3.35182 14.4019 3.22876 14.6044 3.14501C14.8069 3.06126 15.024 3.01842 15.2433 3.01842C15.4625 3.01842 15.6797 3.06126 15.8821 3.14501C16.0846 3.22876 16.2685 3.35182 16.4233 3.50663C16.5781 3.66136 16.7011 3.84524 16.7849 4.04773C16.8686 4.25021 16.9115 4.46734 16.9115 4.68663C16.9115 4.90591 16.8686 5.12304 16.7849 5.32553C16.7011 5.52801 16.5781 5.71189 16.4233 5.86663L16.3733 5.91663C16.1805 6.11298 16.052 6.36257 16.0032 6.63288C15.9544 6.90319 15.9875 7.18197 16.0983 7.43329V7.52329C16.2037 7.7697 16.3787 7.97966 16.6019 8.12757C16.8252 8.27549 17.0868 8.35491 17.3549 8.35496H17.4999C17.942 8.35496 18.366 8.53048 18.6785 8.84304C18.9911 9.15559 19.1666 9.57952 19.1666 10.0216C19.1666 10.4637 18.9911 10.8877 18.6785 11.2002C18.366 11.5128 17.942 11.6883 17.4999 11.6883H17.4249C17.1568 11.6883 16.8952 11.7677 16.672 11.9156C16.4487 12.0636 16.2737 12.2735 16.1683 12.52L16.1666 12.5Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Eye (Show)
- **Icons Enum:** `Icons.Eye`
- **Style:** Outline
- **Category:** UI Control
- **Visual:** An open eye — almond/lens shape with a circle iris in the center.
- **Use when:** Show password in input field. View details. Preview content. Visibility on. "Watch" action.
- **Do NOT use for:** Hide (use EyeOff). Confirm (use Check). Camera (use Camera).
- **Size tokens:** IconSize.Small (18dp) in input trailing icon
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M1.66669 9.99996C1.66669 9.99996 4.16669 4.16663 10 4.16663C15.8334 4.16663 18.3334 9.99996 18.3334 9.99996C18.3334 9.99996 15.8334 15.8333 10 15.8333C4.16669 15.8333 1.66669 9.99996 1.66669 9.99996Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M10 12.5C11.3807 12.5 12.5 11.3807 12.5 10C12.5 8.61929 11.3807 7.5 10 7.5C8.61929 7.5 7.5 8.61929 7.5 10C7.5 11.3807 8.61929 12.5 10 12.5Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Eye Off (Hide)
- **Icons Enum:** `Icons.EyeOff`
- **Style:** Outline
- **Category:** UI Control
- **Visual:** Eye shape with a diagonal slash/line crossing through it. Hidden/masked state.
- **Use when:** Hide password (toggle from Eye). Mask sensitive data. Hidden content. Privacy mode off.
- **Do NOT use for:** Show (use Eye). Block (use Slash).
- **Size tokens:** IconSize.Small (18dp) in input trailing icon
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M14.95 14.95C13.5255 16.0357 11.7909 16.6394 10 16.6667C4.16667 16.6667 1.66667 10 1.66667 10C2.69439 7.8831 4.23553 6.04265 6.15 4.63334M8.33333 3.46667C8.88119 3.32483 9.4397 3.25307 10 3.25C15.8333 3.25 18.3333 10 18.3333 10C17.8483 11.0548 17.2166 12.0386 16.4583 12.9208M11.7667 11.7667C11.5379 12.0123 11.261 12.2093 10.9531 12.3455C10.6453 12.4817 10.3128 12.5541 9.97595 12.5579C9.63909 12.5618 9.30499 12.497 8.99401 12.3678C8.68303 12.2386 8.40162 12.0477 8.16722 11.8066C7.93282 11.5656 7.75047 11.2793 7.63122 10.9654C7.51196 10.6515 7.45833 10.3163 7.47377 9.97999C7.48921 9.64372 7.5635 9.31275 7.70213 9.00648C7.84076 8.70022 8.04086 8.42486 8.29 8.2M1.66667 1.66667L18.3333 18.3333" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Star (Favorite)
- **Icons Enum:** `Icons.Star`
- **Style:** Outline
- **Category:** Social
- **Visual:** Five-pointed star shape with concave sides between points.
- **Use when:** Favorite/bookmark toggle. Ratings display. Wishlist. Featured item. Top-rated badge.
- **Do NOT use for:** Achievement badge (use Award). Rating number display.
- **Size tokens:** IconSize.Small (16dp) in list item ratings, IconSize.Medium (20dp) in FavIcon avatar overlay
- **Color tokens:** `IconColor.BrandFromTokens` when favorited/filled, `IconColor.PrimaryFromTokens` unfilled outline

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M10 1.66663L12.575 6.88329L18.3333 7.72496L14.1667 11.7833L15.15 17.5166L10 14.8083L4.85 17.5166L5.83333 11.7833L1.66666 7.72496L7.425 6.88329L10 1.66663Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: User (Profile)
- **Icons Enum:** `Icons.User`
- **Style:** Outline
- **Category:** Social
- **Visual:** Person silhouette — circle for head, arc/trapezoid for shoulders/body below.
- **Use when:** Profile section. Account settings. User avatar placeholder. Sign in. Personal details.
- **Do NOT use for:** Group/multiple users (use Users). Business account (use Building).
- **Size tokens:** IconSize.Medium (20dp) in top bar, bottom navigation profile tab
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M16.6667 17.5V15.8333C16.6667 14.9493 16.3155 14.1014 15.6904 13.4763C15.0653 12.8512 14.2174 12.5 13.3334 12.5H6.66671C5.78265 12.5 4.93481 12.8512 4.30968 13.4763C3.68456 14.1014 3.33337 14.9493 3.33337 15.8333V17.5M13.3334 5.83333C13.3334 7.67428 11.8409 9.16667 10 9.16667C8.15909 9.16667 6.66671 7.67428 6.66671 5.83333C6.66671 3.99238 8.15909 2.5 10 2.5C11.8409 2.5 13.3334 3.99238 13.3334 5.83333Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Lock
- **Icons Enum:** `Icons.Lock`
- **Style:** Outline
- **Category:** UI Control
- **Visual:** Padlock — a rectangular body with a U-shaped shackle/arch at the top.
- **Use when:** Secure/locked feature. Password field leading icon. Security settings. Locked content. PIN entry context.
- **Do NOT use for:** Unlocked state (use Unlock/LockOpen). Privacy settings (use Shield).
- **Size tokens:** IconSize.Small (18dp) in input leading icon, IconSize.Medium (20dp) standalone
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <rect x="3.33331" y="9.16663" width="13.3333" height="9.16667" rx="2" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M6.66669 9.16667V6.66667C6.66669 4.82572 8.15907 3.33333 10 3.33333C11.841 3.33333 13.3334 4.82572 13.3334 6.66667V9.16667" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: External Link
- **Icons Enum:** `Icons.ExternalLink`
- **Style:** Outline
- **Category:** Action
- **Visual:** A square with an arrow pointing up-right out of the top-right corner. "Opens outside app" indicator.
- **Use when:** Link that opens browser/external app. "View in browser". T&C links. External documentation. Deeplinks to other apps.
- **Do NOT use for:** Share (use Share05). Forward navigation (use ChevronRight). Download.
- **Size tokens:** IconSize.Small (16dp) inline with text, IconSize.Medium (20dp) standalone
- **Color tokens:** `IconColor.BrandFromTokens` for clickable links, `IconColor.PrimaryFromTokens` neutral

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M15 10.8333V15.8333C15 16.2754 14.8244 16.6993 14.5118 17.0118C14.1993 17.3244 13.7754 17.5 13.3333 17.5H4.16667C3.72464 17.5 3.30072 17.3244 2.98816 17.0118C2.67559 16.6993 2.5 16.2754 2.5 15.8333V6.66667C2.5 6.22464 2.67559 5.80072 2.98816 5.48816C3.30072 5.17559 3.72464 5 4.16667 5H9.16667M12.5 2.5H17.5M17.5 2.5V7.5M17.5 2.5L8.33333 11.6667" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: QR Code
- **Icons Enum:** `Icons.QrCode`
- **Style:** Outline
- **Category:** Commerce
- **Visual:** Square grid pattern with three corner squares and a dot matrix inside — standard QR code representation.
- **Use when:** Scan to pay. QR payment. Show QR code for receiving payment. Scan merchant QR. UPI QR.
- **Do NOT use for:** Barcode (different shape). Camera scan (use Camera). Link (use ExternalLink).
- **Size tokens:** IconSize.Medium (20dp), IconSize.Large (24dp) for prominent pay buttons
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.BrandFromTokens` for pay CTAs

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <rect x="2.5" y="2.5" width="6.66667" height="6.66667" rx="1" stroke="#E6E6E6" stroke-width="1.5"/>
  <rect x="10.8333" y="2.5" width="6.66667" height="6.66667" rx="1" stroke="#E6E6E6" stroke-width="1.5"/>
  <rect x="2.5" y="10.8333" width="6.66667" height="6.66667" rx="1" stroke="#E6E6E6" stroke-width="1.5"/>
  <path d="M4.58331 4.58337H6.24998V6.25004H4.58331V4.58337Z" fill="#E6E6E6"/>
  <path d="M13.3333 4.58337H14.9999V6.25004H13.3333V4.58337Z" fill="#E6E6E6"/>
  <path d="M4.58331 13.3334H6.24998V15.0001H4.58331V13.3334Z" fill="#E6E6E6"/>
  <path d="M10.8333 10.8334H12.4999V12.5H10.8333V10.8334Z" fill="#E6E6E6"/>
  <path d="M12.4999 12.5H14.1666V14.1667H12.4999V12.5Z" fill="#E6E6E6"/>
  <path d="M14.1666 10.8334H15.8332V12.5H14.1666V10.8334Z" fill="#E6E6E6"/>
  <path d="M15.8332 12.5H17.4999V14.1667H15.8332V12.5Z" fill="#E6E6E6"/>
  <path d="M10.8333 14.1667H12.4999V15.8334H10.8333V14.1667Z" fill="#E6E6E6"/>
  <path d="M14.1666 15.8334H17.4999V17.5H14.1666V15.8334Z" fill="#E6E6E6"/>
</svg>
```

---

## Icon: Location Pin
- **Icons Enum:** `Icons.MapPin`
- **Style:** Outline
- **Category:** UI Control
- **Visual:** Teardrop/pin shape — circle on top tapering to a point at the bottom. Classic map location marker.
- **Use when:** Delivery address. Store locator. Current location. Address input leading icon. Map pin on order tracking. Branch finder.
- **Do NOT use for:** Navigation (use Navigation). Direction (use ArrowRight). Compass.
- **Size tokens:** IconSize.Small (18dp) in input leading icon, IconSize.Medium (20dp) in address rows
- **Color tokens:** `IconColor.BrandFromTokens` for active location, `IconColor.PrimaryFromTokens` neutral

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M17.5 8.33337C17.5 14.1667 10 19.1667 10 19.1667C10 19.1667 2.5 14.1667 2.5 8.33337C2.5 6.34424 3.29018 4.43659 4.6967 3.03007C6.10322 1.62354 8.01088 0.833374 10 0.833374C11.9891 0.833374 13.8968 1.62354 15.3033 3.03007C16.7098 4.43659 17.5 6.34424 17.5 8.33337Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M10 10.8334C11.3807 10.8334 12.5 9.71409 12.5 8.33337C12.5 6.95266 11.3807 5.83337 10 5.83337C8.61929 5.83337 7.5 6.95266 7.5 8.33337C7.5 9.71409 8.61929 10.8334 10 10.8334Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Bank / Building
- **Icons Enum:** `Icons.Bank`
- **Style:** Outline
- **Category:** Commerce
- **Visual:** Simplified building facade — triangular roof/pediment at top, columns or vertical lines below, horizontal base.
- **Use when:** Bank account linking. IFSC/account number input. Bank transfer. Financial institution. Add bank account.
- **Do NOT use for:** Wallet (use Wallet). Office building (use Building2). Store (use Store).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M1.66669 17.5H18.3334M1.66669 7.5H18.3334M3.33335 7.5V17.5M16.6667 7.5V17.5M6.66669 7.5V17.5M13.3334 7.5V17.5M10 7.5V17.5M10 2.5L18.3334 7.5H1.66669L10 2.5Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Clock / Time
- **Icons Enum:** `Icons.Clock`
- **Style:** Outline
- **Category:** Data
- **Visual:** Circle representing a clock face with two hands — a short hour hand and longer minute hand pointing to specific positions.
- **Use when:** Transaction time. Processing time estimate. Pending/scheduled status. Time-sensitive offers. Recent activity. History.
- **Do NOT use for:** Calendar/date (use Calendar). Timer countdown (use Timer). Duration display.
- **Size tokens:** IconSize.Small (16dp) inline with timestamp text, IconSize.Medium (20dp) standalone
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.Disabled` for past/expired

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M10 18.3333C14.6024 18.3333 18.3334 14.6023 18.3334 9.99996C18.3334 5.39759 14.6024 1.66663 10 1.66663C5.39765 1.66663 1.66669 5.39759 1.66669 9.99996C1.66669 14.6023 5.39765 18.3333 10 18.3333Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M10 5V10L13.3333 11.6667" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Calendar
- **Icons Enum:** `Icons.Calendar`
- **Style:** Outline
- **Category:** Data
- **Visual:** Rectangle with a row of squares at top (representing calendar grid) and two vertical lines at top edge (the hanging pins/hooks of a wall calendar).
- **Use when:** Date picker. Transaction date. Schedule. Booking date. Statement period. Date range filter.
- **Do NOT use for:** Clock/time (use Clock). Events (use CalendarEvent). Reminder (use Bell).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <rect x="2.5" y="3.33337" width="15" height="15" rx="2" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M13.3333 1.66663V4.99996M6.66669 1.66663V4.99996M2.5 8.33337H17.5" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Help Circle (?)
- **Icons Enum:** `Icons.HelpCircle`
- **Style:** Outline
- **Category:** Communication
- **Visual:** Circle with a question mark "?" inside. Universal help/FAQ indicator.
- **Use when:** Help center. FAQ trigger. Tooltip for unclear fields. "What is this?" explanations. Support link.
- **Do NOT use for:** Info (use Info). Error (use AlertCircle). Settings.
- **Size tokens:** IconSize.Small (16dp) next to labels, IconSize.Medium (20dp) standalone
- **Color tokens:** `IconColor.PrimaryFromTokens`, `IconColor.BrandFromTokens` for prominent help CTAs

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M7.57501 7.49996C7.77093 6.94301 8.15764 6.47338 8.66664 6.17423C9.17564 5.87508 9.77409 5.76543 10.3559 5.86524C10.9378 5.96505 11.4656 6.26798 11.8459 6.71965C12.2261 7.17132 12.4342 7.74298 12.4333 8.33329C12.4333 9.99996 9.93334 10.8333 9.93334 10.8333M10 14.1666H10.0083M18.3333 9.99996C18.3333 14.6023 14.6024 18.3333 10 18.3333C5.39765 18.3333 1.66669 14.6023 1.66669 9.99996C1.66669 5.39759 5.39765 1.66663 10 1.66663C14.6024 1.66663 18.3333 5.39759 18.3333 9.99996Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Scan (Camera with brackets)
- **Icons Enum:** `Icons.Scan`
- **Style:** Outline
- **Category:** Action
- **Visual:** A square with corner brackets at each of the four corners — representing a camera scanning frame/viewfinder.
- **Use when:** QR code scanner. Barcode scan. Face scan. Document scan. AR scan trigger.
- **Do NOT use for:** QR code display (use QrCode). Camera photo (use Camera). Crop/frame.
- **Size tokens:** IconSize.Medium (20dp), IconSize.Large (24dp) for prominent scan CTAs
- **Color tokens:** `IconColor.BrandFromTokens` for active scan, `IconColor.PrimaryFromTokens` neutral

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M6.66669 2.5H4.16669C3.24621 2.5 2.50002 3.24619 2.50002 4.16667V6.66667M13.3334 2.5H15.8334C16.7538 2.5 17.5 3.24619 17.5 4.16667V6.66667M6.66669 17.5H4.16669C3.24621 17.5 2.50002 16.7538 2.50002 15.8333V13.3333M13.3334 17.5H15.8334C16.7538 17.5 17.5 16.7538 17.5 15.8333V13.3333" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Send
- **Icons Enum:** `Icons.Send`
- **Style:** Outline
- **Category:** Action
- **Visual:** Paper plane / arrow-like shape pointing up-right. Represents sending/transmitting.
- **Use when:** Send message. Submit form. Transfer money. Send payment. Send OTP.
- **Do NOT use for:** Share (use Share05). Upload (use Upload). Forward (use ChevronRight).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.BrandFromTokens` for primary send CTA, `IconColor.PrimaryFromTokens` neutral

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M17.5 2.5L9.16667 10.8333M17.5 2.5L12.5 17.5L9.16667 10.8333L2.5 7.5L17.5 2.5Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Percent
- **Icons Enum:** `Icons.Percent`
- **Style:** Outline
- **Category:** Commerce
- **Visual:** The % symbol — diagonal line with two circles at top-left and bottom-right.
- **Use when:** Discount percentage. Cashback percentage. Interest rate. Fee percentage. "X% off" offers.
- **Do NOT use for:** Divide. Tag (use Tag). Math operations display.
- **Size tokens:** IconSize.Small (16dp) in badge, IconSize.Medium (20dp) in offer cards
- **Color tokens:** `IconColor.BrandFromTokens` for active offer percentage

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M15.8333 4.16663L4.16669 15.8333M7.08335 5.83329C7.08335 6.52365 6.52371 7.08329 5.83335 7.08329C5.14299 7.08329 4.58335 6.52365 4.58335 5.83329C4.58335 5.14294 5.14299 4.58329 5.83335 4.58329C6.52371 4.58329 7.08335 5.14294 7.08335 5.83329ZM15.4167 14.1666C15.4167 14.857 14.8571 15.4166 14.1667 15.4166C13.4763 15.4166 12.9167 14.857 12.9167 14.1666C12.9167 13.4763 13.4763 12.9166 14.1667 12.9166C14.8571 12.9166 15.4167 13.4763 15.4167 14.1666Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Lightning / Flash
- **Icons Enum:** `Icons.Zap`
- **Style:** Outline
- **Category:** Status
- **Visual:** Lightning bolt — a zig-zag diagonal shape like a stylized "Z" rotated. Suggests speed, energy, instant action.
- **Use when:** Instant payment. Flash offer. Quick action. Express checkout. Real-time feature. UPI Lite (instant). Highlight fast processing.
- **Do NOT use for:** Power/charging (use Battery). Warning/danger (use AlertTriangle).
- **Size tokens:** IconSize.Small (16dp) in badge, IconSize.Medium (20dp) in feature highlights
- **Color tokens:** `IconColor.BrandFromTokens` for instant/express features

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M10.8333 1.66663L2.5 11.6666H10L9.16667 18.3333L17.5 8.33329H10L10.8333 1.66663Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

## Icon: Document / File
- **Icons Enum:** `Icons.FileText`
- **Style:** Outline
- **Category:** Data
- **Visual:** Rectangle with a folded top-right corner (dog-ear) and three horizontal lines inside representing text content.
- **Use when:** View statement. Download receipt. Terms & conditions. Document upload. Invoice. KYC document.
- **Do NOT use for:** Image file (use Image). Folder (use Folder). Attachment (use Paperclip).
- **Size tokens:** IconSize.Medium (20dp)
- **Color tokens:** `IconColor.PrimaryFromTokens`

**SVG:**
```svg
<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
  <path d="M11.6667 1.66663H5.00002C4.55799 1.66663 4.13407 1.84222 3.82151 2.15478C3.50895 2.46734 3.33335 2.89127 3.33335 3.33329V16.6666C3.33335 17.1087 3.50895 17.5326 3.82151 17.8451C4.13407 18.1577 4.55799 18.3333 5.00002 18.3333H15C15.442 18.3333 15.866 18.1577 16.1785 17.8451C16.4911 17.5326 16.6667 17.1087 16.6667 16.6666V6.66663L11.6667 1.66663Z" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M11.6667 1.66663V6.66663H16.6667M13.3334 10.8333H6.66669M13.3334 14.1666H6.66669M8.33335 7.49996H6.66669" stroke="#E6E6E6" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
```

---

# ICON QUICK-REFERENCE TABLE

| Icon Name | Icons Enum | Category | Primary Use Case | Color Token |
|---|---|---|---|---|
| Chevron Right | `Icons.ChevronRight` | Navigation | List item forward, next | PrimaryFromTokens |
| Chevron Left | `Icons.ChevronLeft` | Navigation | Back, previous | PrimaryFromTokens |
| Chevron Down | `Icons.ChevronDown` | Navigation | Dropdown open, accordion expand | PrimaryFromTokens |
| Chevron Up | `Icons.ChevronUp` | Navigation | Dropdown close, collapse | PrimaryFromTokens |
| Arrow Left | `Icons.ArrowLeft` | Navigation | Top bar back button | PrimaryFromTokens |
| Arrow Right | `Icons.ArrowRight` | Navigation | Proceed, see all | BrandFromTokens |
| Home | `Icons.Home` | Navigation | Bottom nav home tab | Brand when active |
| Menu | `Icons.Menu` | Navigation | Hamburger / drawer | PrimaryFromTokens |
| Close/Cross | `Icons.Cross` | Navigation | Dismiss, clear, remove chip | PrimaryFromTokens |
| Search | `Icons.Search` | UI Control | Search input, find | Brand when focused |
| Filter | `Icons.Filter` | UI Control | Filter panel, refine | Brand when active |
| Edit | `Icons.Edit` | Action | Edit profile, modify | PrimaryFromTokens |
| Delete | `Icons.Trash` | Action | Delete item | DestructiveFromTokens |
| Share | `Icons.Share05` | Action | Share content | PrimaryFromTokens |
| Copy | `Icons.Copy` | Action | Copy to clipboard | PrimaryFromTokens |
| Download | `Icons.Download` | Action | Save, export | PrimaryFromTokens |
| Upload | `Icons.Upload` | Action | Attach, submit file | PrimaryFromTokens |
| Plus | `Icons.Plus` | Action | Add new, create | BrandFromTokens |
| Minus | `Icons.Minus` | Action | Remove, decrement | PrimaryFromTokens |
| Refresh | `Icons.Refresh` | Action | Retry, reload, resend OTP | BrandFromTokens |
| Check | `Icons.Check` | Status | Success, selected, verified | SuccessFromTokens |
| Check Circle | `Icons.CheckCircle` | Status | Payment success, verified | SuccessFromTokens |
| Alert Circle | `Icons.AlertCircle` | Status | Error, validation fail | DestructiveFromTokens |
| Alert Triangle | `Icons.AlertTriangle` | Status | Warning, caution | Warning color |
| Info | `Icons.Info` | Status | Info helper text, tooltip | BrandFromTokens |
| Bell | `Icons.Bell` | Communication | Notifications | Brand when unread |
| Message | `Icons.MessageCircle` | Communication | Chat, support | PrimaryFromTokens |
| Mail | `Icons.Mail` | Communication | Email field, inbox | PrimaryFromTokens |
| Phone | `Icons.Phone` | Communication | Phone field, call | PrimaryFromTokens |
| Wallet | `Icons.Wallet` | Commerce | Balance, payment method | BrandFromTokens |
| Credit Card | `Icons.CreditCard` | Commerce | Card payment, saved cards | PrimaryFromTokens |
| Tag | `Icons.Tag` | Commerce | Offer, discount, promo | BrandFromTokens |
| Gift | `Icons.Gift` | Commerce | Rewards, cashback, bonus | BrandFromTokens |
| Settings | `Icons.Settings` | UI Control | App settings, config | PrimaryFromTokens |
| Eye | `Icons.Eye` | UI Control | Show password | PrimaryFromTokens |
| Eye Off | `Icons.EyeOff` | UI Control | Hide password | PrimaryFromTokens |
| Star | `Icons.Star` | Social | Favorite, rating | Brand when active |
| User | `Icons.User` | Social | Profile, account | PrimaryFromTokens |
| Lock | `Icons.Lock` | UI Control | Secure, password field | PrimaryFromTokens |
| External Link | `Icons.ExternalLink` | Action | Open in browser | BrandFromTokens |
| QR Code | `Icons.QrCode` | Commerce | Scan/show QR, UPI | BrandFromTokens |
| Map Pin | `Icons.MapPin` | UI Control | Address, location | Brand when active |
| Bank | `Icons.Bank` | Commerce | Bank account, transfer | PrimaryFromTokens |
| Clock | `Icons.Clock` | Data | Time, pending, history | PrimaryFromTokens |
| Calendar | `Icons.Calendar` | Data | Date, schedule | PrimaryFromTokens |
| Help Circle | `Icons.HelpCircle` | Communication | Help, FAQ | PrimaryFromTokens |
| Scan | `Icons.Scan` | Action | QR scan, document scan | BrandFromTokens |
| Send | `Icons.Send` | Action | Send payment, message | BrandFromTokens |
| Percent | `Icons.Percent` | Commerce | Discount %, cashback | BrandFromTokens |
| Lightning/Zap | `Icons.Zap` | Status | Instant, express, flash | BrandFromTokens |
| File/Document | `Icons.FileText` | Data | Statement, receipt, T&C | PrimaryFromTokens |

---

## Notes for LLM

1. **Icon Style Rule:** Always default to `IconStyle.Outline`. Use `IconStyle.Solid` only for filled/active states like selected bottom nav tabs or toggled favorites.

2. **Color Rule:** Never hardcode icon colors. Always use `IconColor.*` tokens. The most common are:
   - `IconColor.PrimaryFromTokens` → neutral/default icons
   - `IconColor.BrandFromTokens` → active, primary action, brand-colored icons
   - `IconColor.DestructiveFromTokens` → delete, error, danger icons
   - `IconColor.SuccessFromTokens` → success, verified, complete icons
   - `IconColor.Disabled` → disabled/inactive state icons

3. **Size Rule:** Use `ButtonSpecs.IconSize` for icons inside buttons. Use `IconSize.*` tokens for standalone icons in lists, app bars, and other contexts.

4. **Resolution Pattern:**
```kotlin
val iconResId = PopIconResources.getIconResourceId(Icons.Check, IconStyle.Outline)
// OR for VisualElement contexts:
VisualElement.buildFrom(Icons.Check, IconStyle.Outline, fallbackResId = R.drawable.ic_check)
```

5. **SVG fill color `#E6E6E6`** shown in this file is the reference/documentation color. In actual app code, the SVG fill is driven by the `IconColor.*` token — never hardcoded.

6. **When to fetch the actual SVG:** Use these SVG tags for:
   - Generating Android `res/drawable/*.xml` vector drawables
   - Embedding directly in documentation
   - Visual reference in AI-assisted design-to-code conversion

---

*POP DS Icon Library Reference v1.0 — Generated April 2026*
*For AI-Powered Figma-to-Code Automation*
