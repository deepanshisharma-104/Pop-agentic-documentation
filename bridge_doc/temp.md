# Master Prompt: Inside "bridge_doc" folder add a file named "Atoms.md" 

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "atoms.md"** (design specifications in Figma) with the **POP Codebase Agentic Documentation - POP Codebase /theme/PopIcons.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

## A: Example Row from icon wrapping Tokens Table (use column as above defined names)

Icon Size variable name (codebase) |  Icon size (Codebase) | Codebase file name | Font wrapper size in Design System | Design system file name | Code pattern |
sizeSmall | 16.dp (16x16dp) | PopIcons.kt | 16px | atoms.md | []


## How to extract icon(filled , outline, logo and illustrations)  ?
### Guide : Extract icon from bridge_doc/icon_files.md ( e.g., "https://nrchgsvmkvqjhkqhsdqg.supabase.co/storage/v1/object/public/POPIcons/manageautopay.png") and  set the size using the icon size token from the codebase. (codebase)


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
| type-spacing-radius-strokes-effects.md | Greys/Grey-800 | PopSpacing.kt | `PopColor.grey800` | `border(color = PopColor.grey800, width = 1.dp)` |


## End of Master Prompt

**Generated bridge file should be named:** `icon_bridgefile.md`

