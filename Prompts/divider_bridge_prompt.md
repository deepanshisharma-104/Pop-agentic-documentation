# Master Prompt: Inside "bridge_doc" folder add a file named "divider_bridgefile.md"

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "atoms2.md"** (Horizontal Divider and Vertical Divider sections only) with the **POP Codebase Agentic Documentation - POP Codebase /compose_components/PopDivider.kt , POP Codebase/theme/Color.kt ** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

---

## A: Orientation & Style Enum Mapping Table

Create a table mapping the design system's divider types/orientations to their codebase enum equivalents.

##### A. Divider Orientation Mapping [atoms2.md → PopDivider.kt]

Columns:
- Design System File (e.g., atoms2.md)
- Design Orientation / Type Name (e.g., Horizontal divider, Vertical divider)
- Codebase Enum Value (e.g., `PopDividerOrientation.Horizontal`)
- Codebase File Name (e.g., PopDivider.kt)
- Code Pattern

Include mappings for:
- Horizontal divider
- Vertical divider


---

##### B. Divider Style / Type Mapping [atoms2.md → PopDivider.kt]

Columns:
- Design System File (e.g., atoms2.md)
- Design Type Name (e.g., Continuous, Dashed, Stylised)
- Codebase Enum Value (e.g., `PopDividerStyle.Solid`)
- Codebase File Name (e.g., PopDivider.kt)
- Code Pattern

Include mappings for:
- Continuous → Solid
- Dashed → Dashed
- Stylised → (special handling — see atoms2.md gradient note)
- Padded (vertical) → Solid with padding modifier
- Full height (vertical) → Solid, fillMaxHeight
- color (codebase color variable name and hexcode )

Example Row:
| Design System File | Design Type | Codebase Enum Value | Codebase File Name | Notes | Code Pattern |
|---|---|---|---|---|---|
| atoms2.md | Continuous | `PopDividerStyle.Solid` | PopDivider.kt | Default solid line | `PopDivider(style = PopDividerStyle.Solid)` |

---

## B: Colour Token Mapping Table

##### C. Horizontal Divider Colour Mapping [atoms2.md → PopDivider.kt]

Columns:
- Design System File (e.g., atoms2.md)
- Colour Variant Name (e.g., Primary, Secondary, Tertiary, Blured, Mixed)
- Alias Token (e.g., `Border/Primary`)
- Resolves To (parent token + hex)
- Opacity
- Codebase Color Variable (e.g., `PopColors.Neutral.N5`)
- Codebase File Name (e.g., PopDivider.kt)
- Code Pattern

Include mappings for:
- Primary → `Border/Primary` → `Greys/Grey-800` → `#262626`
- Secondary → `Border/Secondary` → `Greys/Grey-700` → `#333333`
- Tertiary → `Border/Tertiary` → `Greys/Grey-600` → `#4D4D4D`
- Blured → `Border/Primary-30%` → `Greys/Grey-900` → `#1F1F1F` at 30% opacity + background blur
- Mixed → `Surface-Secondary-Gradient/Small` paint style (not a named alias token — use paint style name)

Example Row:
| Design System File | Colour Variant | Alias Token | Resolves To | Opacity | Codebase Color Variable | Codebase File Name | Code Pattern |
|---|---|---|---|---|---|---|---|
| atoms2.md | Primary | `Border/Primary` | `Greys/Grey-800` #262626 | 100% | `PopColors.Neutral.N5` (map to nearest) | PopDivider.kt | `PopDivider(color = PopColors.Neutral.N5)` |

---

##### D. Vertical Divider Colour Mapping [atoms2.md → PopDivider.kt]

Columns:
- Design System File
- Colour Variant Name (e.g., Tertiary, Secondary)
- Alias Token
- Resolves To
- Codebase Color Variable
- Codebase File Name
- Code Pattern

Include mappings for:
- Tertiary → `Border/Tertiary` → `Greys/Grey-600`
- Secondary → `Border/Secondary` → `Greys/Grey-700`

---

## C: Thickness / Stroke Token Mapping Table

##### E. Stroke Weight Mapping [atoms2.md → PopDivider.kt]

Columns:
- Design System File
- Stroke Context (e.g., Default, Blured variant)
- Design Stroke Weight (e.g., 0.5px, 1px)
- Codebase Token (e.g., `PopStroke.Default`)
- Resolved Dp Value (e.g., `0.5.dp`)
- Codebase File Name
- Code Pattern

Include mappings for:
- Default stroke 0.5px → `PopStroke.Default`
- Blured variant stroke 1px → `1.dp` (explicit override)

Example Row:
| Design System File | Stroke Context | Design Weight | Codebase Token | Resolved Dp | Codebase File Name | Code Pattern |
|---|---|---|---|---|---|---|
| atoms2.md | Default (all non-Blured) | 0.5px | `PopStroke.Default` | `0.5.dp` | PopDivider.kt | `PopDivider(thickness = PopStroke.Default)` |

---

## D: Full Variant Mapping Table

##### F. Horizontal Divider — All Variants [atoms2.md → PopDivider.kt]

Map every named variant from atoms2.md to its exact `PopDivider()` call.

Columns:
- Design Variant Name (as shown in atoms2.md, e.g., `Type=Continuous, Colour=Primary`)
- `orientation` param
- `style` param
- `color` param
- `thickness` param
- `dashLength` / `gapLength` (if Dashed)
- Special Notes
- Full Code Pattern

Include all 7 variants:
1. Type=Continuous, Colour=Primary
2. Type=Continuous, Colour=Secondary
3. Type=Continuous, Colour=Tertiary
4. Type=Dashed, Colour=Secondary — dash: 6px / gap: 6px
5. Type=Dashed, Colour=Tertiary — dash: 6px / gap: 6px
6. Type=Continuous, Colour=Blured — thickness 1dp, 30% opacity, note blur effect
7. Type=Stylised, Colour=Mixed — gradient, not a simple color token

Example Row:
| Design Variant | orientation | style | color | thickness | dashLength / gapLength | Notes | Code Pattern |
|---|---|---|---|---|---|---|---|
| Type=Continuous, Colour=Primary | `Horizontal` | `Solid` | `PopColors.Neutral.N5` | `PopStroke.Default` | — | — | `PopDivider()` |

---

##### G. Vertical Divider — All Variants [atoms2.md → PopDivider.kt]

Map every named vertical variant. Note: vertical padded type requires a height modifier and the `Padded` variant (16px line / 24px container / 4px top+bottom padding) must use a `height(24.dp)` constraint with internal padding on the composable wrapping the divider.

Columns:
- Design Variant Name (e.g., `Type=Padded, Colour=Tertiary`)
- `orientation` param
- `style` param
- `color` param
- `thickness` param
- full Height / Padding
- Full Code Pattern

Include all 4 variants:
1. Type=Padded, Colour=Tertiary — 16px line, 24px container, 4px padding
2. Type=Padded, Colour=Secondary — 16px line, 24px container, 4px padding
3. Type=Full height, Colour=Tertiary — 24px line, 24px container, no padding
4. Type=Full height, Colour=Secondary — 24px line, 24px container, no padding

---

## Detailed Mapping Instructions

### For Each Mapping Table:

1. **Extract from Design System File (atoms2.md):**
   - Read the Horizontal Divider and Vertical Divider sections
   - Extract all property names, variant names, colour tokens, stroke weights, dash patterns, and sizing rules

2. **Match to Codebase (POP Codebase /compose_components/PopDivider.kt):**
   - Find the corresponding `PopDividerStyle` enum value for each design Type
   - Find the corresponding `PopDividerOrientation` enum value
   - Find the exact color variable from `PopColors` that matches the alias token
   - Find the exact `PopStroke.*` value for the thickness
   - Identify `dashLength` / `gapLength` values that match the 6px/6px design spec

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Every pattern must use `PopDivider()` — never raw `Divider()` from Material
   - Use `PopStroke.Default` for 0.5dp thickness — never raw `0.5.dp`
   - Use `PopColors.*` for color — never hardcode hex values
   - Show real code examples, not pseudocode

### Mapping Priorities:

1. **MUST include 1:1 mappings** showing exact design-to-code translation for every variant
2. **MUST show code patterns** for every variant row
3. **MUST include all colour variants** — Primary, Secondary, Tertiary, Blured, Mixed
4. **MUST document Blured** variant's 30% opacity and background blur note separately
5. **MUST document Stylised/Mixed** gradient limitation — no alias token, use paint style name
6. **MUST document vertical Padded vs Full height** sizing difference (16px line vs 24px line)

---

## Quality Standards

✓ **Accuracy:** All mappings verifiable against atoms2.md and PopDivider.kt
✓ **Completeness:** All 7 horizontal variants + all 4 vertical variants mapped
✓ **Clarity:** Token chain always shown: Colour variant → Alias token → Parent token → Hex
✓ **Usability:** Tables searchable by design variant name and by codebase param
✓ **Practical:** Every row includes a runnable `PopDivider(...)` code pattern
✓ **Consistency:** Same column names and ordering throughout all tables

---

## Output Format

- **File Type:** Markdown (.md)
- **Structure:** Hierarchical with clear sections — A (Orientation/Style), B (Colour), C (Stroke), D (Full Variants)
- **Tables:** Markdown table format for all mappings
- **Code blocks:** Kotlin code blocks (```kotlin) for all multi-line patterns
- **Length:** Comprehensive but organized — one table per mapping concern

---

## End of Master Prompt

**Generated bridge file should be named:** `divider_bridgefile.md`
