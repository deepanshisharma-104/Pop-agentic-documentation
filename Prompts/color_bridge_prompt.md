# Master Prompt: Generate POP Design System Agentic Bridge Documentation

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - colors.md** (design specifications in Figma) with the **POP Codebase Agentic Documentation - POP Codebase /theme/PopColor.kt, POP Codebase /theme/Theme.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.
#### 1:1 Color/ Semantic Mapping TableCreate a table using below defined columns

##### A. COLOR TOKENS Mapping Table [this will come from colors.md (Design system file), POP Codebase /theme/PopColor.kt, (Codebase file)]
Columns:- Design System File (e.g. ,color.md)
- Color Token Name (In color.md DS)
- Codebase color variable name(PopColor.kt codebase files )
- Codebase File Name (e.g.,PopColor.kt)(kotlin file in codebase)
- Code Pattern

Include mappings for:
- Grey palette (Grey-1000 through Grey-100)
- Brand palette (Brand-1000 through Brand-100)
- Success, warning, error, info palettes
- Semantic tokens (Border/Primary, Text/Primary, Surface/Primary, etc.

## Detailed Mapping Instructions

### For Each Mapping Table:

1. **Extract from Design System File (colors.md):**

2. **Match to Codebase:**
   - Find corresponding Kotlin class/object/enum  , also how any tokens is used in codebase as variable
   - Identify file location (PopColor.kt, PopSpacing.kt, etc.)
   - Find the exact variable name

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Include import statements where relevant
   - Show real code examples not pseudocode

4. **Example Template:**
   ```
   Color Token Name: | Greys/Grey-1000 | `#0D0D0D` |
   Codebase:  val Grey1000 = Color(0xFF0D0D0D) // XML: @color/grey_1000
   File: POP Codebase /theme/PopColor.kt
   Code Pattern: PopButtonV2(..., variant = ButtonVariant.Primary)
   ```

### Mapping Priorities:

1. **MUST include 1:1 mappings** showing exact design-to-code translation
2. **MUST show code patterns** 
3. **MUST include all variants** and configuration options
4. **SHOULD include internal building blocks** (prefixed with `.` in design)

## Quality Standards

✓ **Accuracy:** All mappings must be verifiable against actual design and code files
✓ **Completeness:** Include all tokens, components, and patterns from input files
✓ **Clarity:** Use clear language with technical precision
✓ **Usability:** Tables must be easy to search and scan
✓ **Practical:** Include real Kotlin/Compose code patterns
✓ **Consistency:** Use consistent naming and formatting throughout


## Output Format

- **File Type:** Markdown (.md)
- **Structure:** Hierarchical with clear sections and subsections
- **Tables:** Use markdown table format for all mappings
- **Links:** Reference related sections with markdown headers
- **Length:** Comprehensive but organized (avoid single overwhelming wall of text)

## Example Row from Color Tokens Table (use column as above defined names)

| Design System File | Design/color Token | Codebase File Name | Codebase color variable name | Code Pattern |
|---|---|---|---|---|---|
| colors.md | Greys/Grey-800 | PopColor.kt | `PopColor.grey800` | `border(color = PopColor.grey800, width = 1.dp)` |


## End of Master Prompt

**Generated bridge file should be named:** `color_bridgefile.md`

Display StylesHeading StylesLabel StylesParagraph Styles