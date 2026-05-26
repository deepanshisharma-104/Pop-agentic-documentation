# Type Scale — POP Design System

This document covers all typographic styles across two typefaces used in the POP Design System. Always use only the typescales defined here when building any component, screen, or layout.

---

## Typefaces at a Glance

| Typeface | Role | Italic | Usage |
|---|---|---|---|
| **Figtree** | Functional UI typeface | No | All UI text — headings, labels, paragraphs, links, inputs, error states |
| **Awesome Serif Italic** | Stylised expressive typeface | Always | Expressive headings, section titles, editorial labels only |

---

# Part 1 — Figtree

**Typeface:** Figtree  
**Letter Spacing (default):** 0% · Display styles: -2%  
**Naming convention:** `Category/Size-Weight`

---

## Display

| Style Name | Font Size | Font Weight | Line Height | Letter Spacing |
|---|---|---|---|---|
| Display/X Large-800 | 44px | ExtraBold (800) | 52px | -2% |
| Display/X Large-700 | 44px | Bold (700) | 52px | -2% |
| Display/Large-800 | 36px | ExtraBold (800) | 40px | -2% |
| Display/Large-700 | 36px | Bold (700) | 40px | -2% |
| Display/Medium-800 | 32px | ExtraBold (800) | 38px | -2% |
| Display/Medium-700 | 32px | Bold (700) | 38px | -2% |
| Display/Small-800 | 24px | ExtraBold (800) | 30px | -2% |
| Display/Small-700 | 24px | Bold (700) | 30px | -2% |

---

## Heading

| Style Name | Font Size | Font Weight | Line Height | Letter Spacing |
|---|---|---|---|---|
| Heading/Large-700 | 20px | Bold (700) | 26px | 0% |
| Heading/Large-600 | 20px | SemiBold (600) | 26px | 0% |
| Heading/Medium-600 | 18px | SemiBold (600) | 24px | 0% |
| Heading/Small-500 | 16px | Medium (500) | 24px | 0% |

---

## Paragraph

| Style Name | Font Size | Font Weight | Line Height | Letter Spacing |
|---|---|---|---|---|
| Paragraph/X Large-700 | 24px | Bold (700) | 32px | 0% |
| Paragraph/Large-500 | 24px | Medium (500) | 32px | 0% |
| Paragraph/Medium-500 | 16px | Medium (500) | 24px | 0% |
| Paragraph/Small-500 | 14px | Medium (500) | 22px | 0% |
| Paragraph/X Small-400 | 12px | Regular (400) | 20px | 0% |

---

## Label

| Style Name | Font Size | Font Weight | Line Height | Letter Spacing |
|---|---|---|---|---|
| Label/Large-600 | 18px | SemiBold (600) | 24px | 0% |
| Label/Large-500 | 18px | Medium (500) | 24px | 0% |
| Label/Medium-700 | 16px | Bold (700) | 22px | 0% |
| Label/Medium-600 | 16px | SemiBold (600) | 22px | 0% |
| Label/Medium-500 | 16px | Medium (500) | 24px | 0% |
| Label/Small-700 | 14px | Bold (700) | 20px | 0% |
| Label/Small-600 | 14px | SemiBold (600) | 20px | 0% |
| Label/Small-500 | 14px | Medium (500) | 20px | 0% |
| Label/X Small-700 | 12px | Bold (700) | 16px | 0% |
| Label/X Small-600 | 12px | SemiBold (600) | 16px | 0% |
| Label/X Small-500 | 12px | Medium (500) | 16px | 0% |
| Label/XX Small-500 | 10px | Medium (500) | 12px | 0% |

---

## Link

| Style Name | Font Size | Font Weight | Line Height | Letter Spacing |
|---|---|---|---|---|
| Link/Large-500 | 18px | SemiBold (600) | 24px | 0% |
| Link/Medium-600 | 16px | SemiBold (600) | 22px | 0% |
| Link/Small-500 | 14px | SemiBold (600) | 20px | 0% |
| Link/X Small-500 | 12px | Medium (500) | 16px | 0% |

---

### Figtree Summary

| Property | Value |
|---|---|
| Typeface | Figtree |
| Weight range | Regular (400) → ExtraBold (800) |
| Size range | 10px – 44px |
| Letter spacing | -2% (Display), 0% (all others) |
| Naming convention | `Category/Size-Weight` |

---

---

# Part 2 — Awesome Serif Italic

**Typeface:** Awesome Serif Italic  
**Always italic by nature** — do not apply italic as a separate style; it is baked into every variant of the typeface.

## Usage Rules

| Use | Do Not Use |
|---|---|
| Expressive headings | Body text / paragraphs |
| Section titles | Input labels |
| Editorial labels | Helper text |
| Marketing / promotional copy | Error or success states |
| Hero banners | Any functional UI text |

> Awesome Serif Italic is **never** used for body text, input labels, helper text, error states, or any functional UI text. It is reserved purely for expressive, editorial moments.

---

## Available Font Styles

| Style Name | Character |
|---|---|
| Bold Extra Tall | Primary expressive weight — used across the display scale (44px–20px) |
| Semibold Regular | Lighter expressive weight — used at smaller label sizes (16px–12px) |

---

## Type Scale

> Letter spacing is characteristically tight. Larger sizes (20px+) use 0% tracking; smaller sizes (16px and below) use +2% to maintain legibility.

| Style | Font Size | Font Style | Line Height | Letter Spacing |
|---|---|---|---|---|
| Display/X Large | 44px | Bold Extra Tall | 52px | 0% |
| Display/Large | 36px | Bold Extra Tall | 42px | 0% |
| Display/Medium | 32px | Bold Extra Tall | 38px | 0% |
| Display/Small | 24px | Bold Extra Tall | 32px | 0% |
| Heading/Large | 20px | Bold Extra Tall | 26px | 0% |
| Heading/Small | 16px | Semibold Regular | 24px | 2% |
| Label/Small | 12px | Semibold Regular | 20px | 2% |

---

## Additional Observed Styles

These styles appear in context across the design system and complement the main scale:

| Usage Context | Font Size | Font Style | Line Height | Letter Spacing |
|---|---|---|---|---|
| Section label / intro tag | 16px | Bold Extra Tall | ~18.7px | 2% |
| Compact heading | 24px | Bold Extra Tall | AUTO | -1% |
| Specimen / editorial display | 28px | Bold Extra Tall | 150% | 0% |

---

### Awesome Serif Summary

| Property | Value |
|---|---|
| Typeface | Awesome Serif Italic |
| Always italic | Yes — inherent to all styles |
| Primary weight | Bold Extra Tall |
| Secondary weight | Semibold Regular |
| Size range | 12px – 44px |
| Letter spacing | 0% (20px and above), 2% (16px and below) |
| Used for | Expressive headings, section titles, editorial labels |
| Never used for | Body text, input labels, helper text, error states, functional UI |
