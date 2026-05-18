# Master Prompt: Generate POP Design System Agentic Bridge Documentation

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "type-scale.md"** (design specifications in Figma) with the **POP Codebase Agentic Documentation - POP Codebase /theme/PopTypography.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

#### 1:1 Font weight Mapping Table
Font weight (Design system) | Codebase Font weight variable
Medium (500) | FontWeight.Medium
Bold (700)   | FontWeight.Bold 
SemiBold (600) | FontWeight.SemiBold


#### 1:2 Typography/typscale Mapping Table - Create a table using below defined columns

##### A. Type-scale Mapping Table [this will come from type-scale.md (Design system file),  POP Codebase /theme/PopTypography.kt, (Codebase file)]
Columns:- Design System File (e.g. ,type-scale.md)
- Typescale token Name (In type-scale.md)(e.g., Display.XLarge.700)
- Codebase variable name(PopTypography.kt codebase files )(e.g.,DisplayLarge)
- Codebase File Name (e.g.,PopTypography.kt)(kotlin file in codebase)
- Code Pattern

Include mappings for:
- Display Styles
- Heading Styles
- Label Styles
- Paragraph Styles

## Detailed Mapping Instructions

### For Each Mapping Table:


4. **Example Template:**
   ```
   Color Token Name: | Greys/Grey-1000 | `#0D0D0D` |
   Codebase:  val Grey1000 = Color(0xFF0D0D0D) // XML: @color/grey_1000
   File: POP Codebase /theme/PopTypography.kt
   Code Pattern: PopButtonV2(..., variant = ButtonVariant.Primary)
   ```



#### 1:3 Typography/spacing Mapping Table - Create a table using below defined columns
 
columns
examples




#### 1:4 Typography/radius Mapping Table - Create a table using below defined columns



columns
examples



#### 1:5 Typography/stroks Mapping Table - Create a table using below defined columns



columns
examples



1. **Extract from Design System File (type-scale.md , spacing-radius-strokes-effects.md ):**


2. **Match to Codebase (POP Codebase /theme/PopTypography, POP Codebase /theme/PopSpacing.kt).kt:**
   - Find corresponding Kotlin class/object/enum  , also how any tokens is used in codebase as variable
   - Identify file location (PopTypography.kt)
   - Find the exact variable name

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Include import statements where relevant
   - Show real code examples not pseudocode

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
| type-scale.md | Greys/Grey-800 | PopTypography.kt | `PopColor.grey800` | `border(color = PopColor.grey800, width = 1.dp)` |


## End of Master Prompt

**Generated bridge file should be named:** `typography_bridgefile.md`

