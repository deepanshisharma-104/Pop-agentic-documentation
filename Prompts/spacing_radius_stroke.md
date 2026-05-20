# Master Prompt: Inside "bridge_doc" folder add a file named "spacing_radius_stroke_bridge.md" 

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "spacing-radius-strokes-effects.md"** (design specifications in Figma) with the **POP Codebase Agentic Documentation - POP Codebase /theme/PopSpacing.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

#### 1:1 Font Spacing Mapping Table

- Create a table using below defined columns

##### A. Spacing Mapping Table [this will come from "spacing-radius-strokes-effects.md" (Design system file),  "POP Codebase /theme/PopSpacing.kt", (Codebase file)]
Columns:- 
- Design System File (e.g. ,spacing-radius-strokes-effects.md)
- Spacing Token Name (In spacing-radius-strokes-effects.md)(e.g., | Token: Spacing/0 | Value: 0px |)
- Codebase Variable Name (PopSpacing.kt codebase files )(e.g.,Spacing16)
- Codebase File Name (e.g.,PopSpacing.kt)(kotlin file in codebase)
- Code Pattern

Include mappings for:
- Spacing 

## Detailed Mapping Instructions

### For Each Mapping Table:


4. **Example Template:**
   ```
   Spacing Token Name: | Spacing/0 
   Codebase:  Spacing16
   File: POP Codebase /theme/PopSpacing.kt
   Code Pattern: val Spacing16: Dp = 16.dp

## Example Row from Spacing Tokens Table (use column as above defined names)

| Design System File | DSpacing Token | Codebase File Name | Codebase color variable name | Code Pattern |
|---|---|---|---|---|---|
| spacing-radius-strokes-effects.md | Spacing/0 | POP Codebase /theme/PopSpacing.kt | Spacing16 | val Spacing16: Dp = 16.dp |

#### 1:2 Typography/radius Mapping Table 

- Create a table using below defined columns

##### A. Radius Mapping Table [this will come from "spacing-radius-strokes-effects.md" (Design system file),  "POP Codebase /theme/PopSpacing.kt", (Codebase file)]
Columns:- 
- Design System File (e.g. ,spacing-radius-strokes-effects.md)
- Radius Token Name (In spacing-radius-strokes-effects.md)(e.g., | Token: Radius/0 | Value: 0px | Usage: Sharp / no rounding |)
- Codebase Variable Name (PopSpacing.kt codebase files )(e.g.,0px - Sharp corners, no rounding)
- Codebase File Name (e.g.,PopSpacing.kt)(kotlin file in codebase)
- Code Pattern

Include mappings for:
- Radius 

## Detailed Mapping Instructions

### For Each Mapping Table:


4. **Example Template:**
   ```
   Spacing Token Name: | Radius/0 
   Codebase:  0px
   File: POP Codebase /theme/PopSpacing.kt
   Code Pattern: val None: Dp = 0.dp

## Example Row from Radius Tokens Table (use column as above defined names)

| Design System File | DSpacing Token | Codebase File Name | Codebase color variable name | Code Pattern |
|---|---|---|---|---|---|
| spacing-radius-strokes-effects.md | Radius/0 | POP Codebase /theme/PopSpacing.kt | 0px | val None: Dp = 0.dp |



#### 1:5 Typography/stroks Mapping Table - Create a table using below defined columns

- Create a table using below defined columns

##### A. Stroke Mapping Table [this will come from "spacing-radius-strokes-effects.md" (Design system file),  "POP Codebase /theme/PopSpacing.kt", (Codebase file)]
Columns:- 
- Design System File (e.g. ,spacing-radius-strokes-effects.md)
- Stroke Token Name (In spacing-radius-strokes-effects.md)(e.g., | Token: Strokes/x | Value: 0.5px | Usage: Hairline / subtle dividers |
- Codebase Variable Name (PopSpacing.kt codebase files )(e.g.,0.5px)
- Codebase File Name (e.g.,PopSpacing.kt)(kotlin file in codebase)
- Code Pattern

Include mappings for:
- Stroke 

## Detailed Mapping Instructions

### For Each Mapping Table:


4. **Example Template:**
   ```
   Spacing Token Name: Strokes/x
   Codebase: 0.5px
   File: POP Codebase /theme/PopSpacing.kt
   Code Pattern: val Default: Dp = 0.5.dp

## Example Row from Stroke Tokens Table (use column as above defined names)

| Design System File | DSpacing Token | Codebase File Name | Codebase color variable name | Code Pattern |
|---|---|---|---|---|---|
| spacing-radius-strokes-effects.md | Strokes/x | POP Codebase /theme/PopSpacing.kt | 0.5px | val Default: Dp = 0.5.dp |



1. **Extract from Design System File (spacing-radius-strokes-effects.md , spacing-radius-strokes-effects.md ):**


2. **Match to Codebase (POP Codebase /theme/PopTypography, POP Codebase /theme/PopSpacing.kt).kt:**
   - Find corresponding Kotlin class/object/enum  , also how any tokens is used in codebase as variable
   - Identify file location (PopSpacing.kt)
   - Find the exact variable name

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Include import statements where relevant
   - Show real code examples not pseudocode

### Mapping Priorities:

1. **MUST include 1:1 mappings** showing exact design-to-code translation
2. **MUST show code patterns** 
3. **MUST include all variants** and configuration options
4. **SHOULD include internal building blocks** (prefixed with . in design)

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
| type-spacing-radius-strokes-effects.md | Greys/Grey-800 | PopSpacing.kt | PopColor.grey800 | border(color = PopColor.grey800, width = 1.dp) |


## End of Master Prompt

**Generated bridge file should be named:** typography_bridgefile.md