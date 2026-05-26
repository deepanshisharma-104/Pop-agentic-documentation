# Atoms — Rules Reference

---

## Slot

- A Slot is a blank placeholder component. It has no visual style of its own.
- Slots are used **directly** in layouts — place a Slot, then swap the component inside it.
- Use the component swap property to replace the Slot with the desired component (icon, avatar, badge, image, etc.).

---

## Slot Container

- A Slot Container is **never** placed or used directly in a layout.
- To add content: double-click into the container → navigate inside → find the Slot → swap the component inside the Slot.
- The container handles structural layout. The Slot inside it handles the content.
- Never attempt to swap content directly on a Slot Container — always go one level deeper to the Slot.

---

## Icons

- Always place icons inside an **Icon Wrapper**. Never use raw icon components directly in a layout.
- Use the wrapper size that matches the context — do not freehand resize an icon outside the wrapper system (12, 16, 20, 24, 28px).
- If the icon is **tappable**, use **Icon Button** — not Icon Wrapper. Icon Wrapper is display-only. Icon Button provides the correct 40px touch target.
- Always apply icon colour using a **colour token**. Never hardcode a colour value.
- Outline icons (`Icons/`) are the default style. Use Filled icons (`Filled Icons/`) only for active, selected, or toggled-on states.
- Illustration icons are PNG assets — never use them as functional/interactive icons.
- Logos (`Logos/`) are brand marks — never recolour them.

---

## UPI App Logos

- UPI app logos are **third-party brand assets** — never recolour, tint, or apply any filter to them.
- Always render at a **fixed size of 48×48px**. Do not scale up or down.
- Do not bind UPI app logos to any colour token. They are static assets.
- Use as **PNG only**. Do not convert to SVG or attempt to edit the asset.
- Figma prefix: `Banks/` — export from `icons/upi-logos/`.

---

## Illustration Icons

- Illustration icons are **PNG assets** — always use them as PNG. Do not export or use as SVG.
- Never recolour illustration icons. Do not apply colour tokens or CSS filters to them.
- Do not use illustration icons as functional or interactive icons — they are decorative and contextual only.
- Two sizes are available: **100×100px** (inline/contextual) and **393×209px** (full-width).
- Figma prefix: `Illustration/` — export from `icons/illustration/`.


