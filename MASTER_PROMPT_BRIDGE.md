# Master Prompt: Generate POP Design System Agentic Bridge Documentation

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation** (design specifications in Figma) with the **POP Codebase Agentic Documentation** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

## Input Files to Reference

### Design System Files (Input)
You will receive or reference these 10 design system documentation files:
1. **colors.md** — Color token system (parent tokens, alias tokens, gradients)
2. **spacing-radius-strokes-effects.md** — Spacing, radius, strokes, and effect tokens
3. **type-scale.md** — Typography system (20+ text styles)
4. **atoms.md** — Foundational atomic components (Slot, Icon system)
5. **atoms2.md** — Additional atomic components (Dividers)
6. **icon.md** — Complete icon library reference
7. **molecules.md** — Composed UI components (12 primary types)
8. **molecules2.md** — Extended molecule library (10 component groups)
9. **organisms.md** — Screen-level components (10 categories)
10. **patterns.md** — Pre-composed product-specific patterns

### Codebase File (Input)
1. **POP_DESIGN_SYSTEM_DOCUMENTATION.md** — Master Kotlin/Jetpack Compose implementation reference

## Mappable Figma Properties

The following property types exist across all components in this design system.
When mapping a component, identify which of these apply and extract their values accordingly.

---

## Output Requirements

### File Name
**bridgefile.md**

### File Structure

#### 1. Header Section
- Title: "POP Agentic Bridge Documentation"
- Purpose statement explaining the bridge's role

#### 2. Design System File Descriptions
For each of the 10 design system files, provide:
- **File Name**
- **Description** (2-3 sentences covering scope and organization)
- **Scope** (what it covers)
- **Organization** (how it's structured)
- **Key Content** (main components/tokens)

#### 3. Codebase File Descriptions
For POP_DESIGN_SYSTEM_DOCUMENTATION.md:
- **File Name**
- **Description** (overview of the codebase structure)
- **Scope** (what it implements)
- **Organization** (folder/file structure)

#### 4. 1:1 Mapping Tables

Create separate detailed mapping tables for each category:

##### A. COLOR TOKENS Mapping Table
Columns:
- Design System File
- Design Token Name
- Codebase variable name
- Codebase File (kotlin file in codebase)
- Kotlin Implementation (class/object name)
- Code Usage Pattern
- Description (usecase)

Include mappings for:
- Grey palette (Grey-1000 through Grey-100)
- Brand palette (Brand-1000 through Brand-100)
- Success, warning, error, info palettes
- Semantic tokens (Border/Primary, Text/Primary, Surface/Primary, etc.)

##### B. SPACING TOKENS Mapping Table
Columns:
- Design System Token (Spacing/0 through Spacing/120)
- Value(Design system token value) (px)
- Codebase variable name
- Codebase variable value
- Description (usecase)
- Codebase Implementation (PopSpacing.kt)
- Code Pattern


Include all spacing values

##### C. RADIUS TOKENS Mapping Table
Columns:
- Design System Token (Radius/0 through Radius/16)
- Value(Design system token value) (px)
- Codebase variable name
- Codebase variable value
- Description (usecase)
- Codebase Implementation (PopSpacing.kt)
- Code Pattern

##### D. STROKE/BORDER TOKENS Mapping Table
Columns:
- Design System Token (Stroke/Hairline, Thin, Medium)
- Value(Design system token value) (px)
- Codebase variable name
- Codebase variable value
- Description (usecase)
- Codebase Implementation (PopSpacing.kt)
- Code Pattern

##### E. TYPOGRAPHY/TYPESCALE Mapping Table
Columns:
- Design System File
- Style Category (Display, Heading, Paragraph, Label, Link)
- Style Name ( in both DS and codebase )
- Codebase File (kotlin file name in codebase)
- Description (usecase)
- Kotlin Implementation
- Code Pattern

Include all text styles from type-scale.md

##### F. ICON SYSTEM Mapping Table
Columns:
- Icon Category (Outline, Filled, Illustration, Logos, UPI App Logos, Status Bar)
- Export Format (SVG/PNG)
- Export Path (figma)
- Codebase File (kotlin file name in codebase)
- Description (usecase)
- Implementation (IconName variants, IconStyle enum)(in coadebase)
- Code Pattern

Include mapping for Icon Wrapper sizing system (Small, Medium, Large, XLarge)

##### G. EFFECTS TOKENS Mapping Table
Columns:
- Effect Token
- Type (Shadow, Glow, Blur)
- Description
- Usage Context
- Codebase File Name (kotlin file name in codebase)
- Implementation
- Code Pattern

##### H. ATOMS COMPONENTS Mapping Table
Columns:
- Component name (from atoms.md)
- Key Properties/Variants [Type, Fill, Count, Size, Color, State]
- Description (usecase)
- Figma Page
- Codebase File (kotlin file name in codebase)
- Implementation
- Code Pattern

Components to map:
- Slot
- Slot Container
- Icons (with wrapper)
- Icon Wrapper System
- Icon Button
- UPI App logo

##### I. ATOMS 2 COMPONENTS Mapping Table
Columns:
- Component name (from atoms2.md)
- Key Properties/Variants [Type, Fill, Count, Size, Color, State, Dot, Fave, Falg, is disabled, indeterminant, promoted, is active -  this includes all variables and boolean properties]
- Variants
- Description (usecase)
- Figma Page
- Codebase File Name (kotlin file name in codebase)
- Code Implementation (of each atoms)
- Key Properties

Components to map:
- Horizontal Divider
- Vertical Divider
- Base radio
- Base checkbox
- Radio with text 
- Checkbox with text
- Radio with text (stack)
- Checkbox with text (stack)
- Toggle
- Rating star
- Rating star group
- Rating badge 
- Scrim
- Avatar
- Dot 
- Flag
- Fave icon
- Avatar stack


##### J. MOLECULES COMPONENTS Mapping Table
Columns:
- Component name (from molecules.md)
- Key Properties/Variants
- Description (usecase)
- Figma Page
- Codebase File Name (kotlin file name in codebase)
- Code Implementation (of each molecule)
- Code Pattern

Components to map (12 total):
- Status Bar
- App Bar
- Title Bar
- Section Row Item
- Nudge
- Inline Action Row
- Section Header
- Chip & Chip Stack
- Tab
- Input Field
- Card & Card Stack
- ODP Card

##### K. MOLECULES 2 COMPONENTS Mapping Table
Columns:
- Component Group (from molecules2.md)
- Key Properties/Variants
- Description
- Figma Page
- Codebase File name
- code Implementation (of each molecule)
- Internal Building Blocks

Component groups to map (10 total):
- PL Card
- Accordion
- List
- Table
- Pagination
- Badge & Content Card
- Vertical Button & Benefit Callout
- Text Divider
- Button
- Keypad
- Timeline
- Quantity Stepper

##### L. ORGANISMS COMPONENTS Mapping Table
Columns:
- Component Group (from organisms.md)
- Description (usecase)
- Key Properties/Variants
- Figma Page
- Codebase File Name
- Code Implementation (of each component)
- Internal Building Blocks

Component groups to map (10 total):
- Top Bar
- Toast & Toast Bar
- Offers (multiple sub-types)
- Bottom Sheet
- Popup
- Carousel
- Stacks
- Empty State
- Bento Grid
- RCBP Card

##### M. PATTERNS Mapping Table
Columns:
- Product Context (Payments, Shop, Recharges, Account Management, Profile QR)
- Pattern Type
- Description(usecase)
- Key Composed Components
- Figma Page
- Codebase Implementation Pattern
- Codebase File Name
- Usage Scenario

Include mapping for:
- Payment patterns (Payment list - Chevron - Pattern, Payment list - Label - Pattern, Balance list - Pattern, Payment list - Button - Pattern, Payment list - Radio - Pattern, Transaction list - Pattern, Tranaction result - Pattern, Autopay card - Pattern, UPI Lite Balance Pattern, Collect request - Popup - Pattern, Input field - Note - Pattern, Payee - Pattern, Payee - Pattern, Payee details - Pattern - Unit, RCBP - Pattern - Unit, RCBP - Pattern - Unit, Payment list - Left - Small - Pattern,Payment list - Right - Small - Pattern, Button + Payment - Pattern, Input field - Naked Large - Pattern)
- Shop patterns (P3 offer application, OLP card- Pattern, Order history carousel)
- Recharge patterns (Recharge card-unit, Mini - Card, Title bar -S2S, Mini recharge card - carousel, RCBP - Payment list, List + body text and Redirection loader )
- Account Management patterns (Account management pattern)
- Profile QR patterns (QR display, Profile Card pattern)

#### 5. Mapping Summary Table
Create a comprehensive summary table showing:
- Category
- Design Files
- Token/Component Count
- Properties/Props
- Codebase Files
- Implementation Count
- Mapping Type (1:1, 1:Many)

Include totals for: Colors, Spacing, Radius, Strokes, Typography, Icons, Effects, Atoms, Molecules, Molecules2, Organisms, Patterns

#### 6. Usage Guidelines
Provide practical guidance for design-to-code development:

For each category, include:
- **Color Implementation** - When to use semantic vs parent tokens
- **Spacing Implementation** - Never hardcode values, always use tokens
- **Typography Implementation** - How to apply text styles in code
- **Icon Implementation** - Wrapper usage, style/size specification
- **Component Implementation** - Hierarchy and composition rules
- **State & Variant Management** - How to apply variants from design spec
- **Responsive Design** - Using ScaledDimensions for adaptive sizing

#### 7. File Organization Reference
Create a visual tree showing:
- Each design file on left
- Arrow (:left_right_arrow:) in middle indicating mapping relationship
- Corresponding codebase file(s) on right

Example format:
```
Design System (Figma)
├── colors.md                         :left_right_arrow: PopColor.kt, Color.kt, PopGradient.kt :left_right_arrow: Usecase
├── spacing-radius-strokes-effects.md :left_right_arrow: PopSpacing.kt :left_right_arrow: Usecase
...
```

#### 8. Next Steps
Include section with:
- Design-to-Code Sync workflow
- Token Verification process
- Component Composition rules
- Variant Consistency checks
- Documentation Update process


## Detailed Mapping Instructions

### For Each Mapping Table:

1. **Extract from Design System File:**
   - Token/component name from design
   - Description of what it does
   - All variants or configurations

2. **Match to Codebase:**
   - Find corresponding Kotlin class/object/enum  , also how any tokens is used in codebase as variable
   - Identify file location (PopColor.kt, PopSpacing.kt, etc.)
   - Find the exact property/function name
   - Determine which composable uses it

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Include import statements where relevant
   - Show real code examples not pseudocode

4. **Example Template:**
   ```
   Design Token: Button -> Type -> primary
   Codebase: ButtonVariant.Primary
   File: ButtonVariant.kt, PopButtonV2.kt
   Code Pattern: PopButtonV2(..., variant = ButtonVariant.Primary)
   ```

### Mapping Priorities:

1. **MUST include 1:1 mappings** showing exact design-to-code translation
2. **MUST show code patterns** - not just names, but actual usage
3. **MUST include all variants** and configuration options
4. **SHOULD include internal building blocks** (prefixed with `.` in design)
5. **SHOULD note 1:Many relationships** where one design token maps to multiple implementations


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
- **Code Blocks:** Use triple-backtick code fences with language identifier (kotlin, yaml)
- **Links:** Reference related sections with markdown headers
- **Length:** Comprehensive but organized (avoid single overwhelming wall of text)

## Example Row from Color Tokens Table (use column as above defined names)

| Design System File | Design Token | Description | Codebase File | Implementation | Code Pattern |
|---|---|---|---|---|---|
| colors.md | Greys/Grey-800 | Primary border color for high-contrast separators on dark surfaces | PopColor.kt | `PopColor.grey800` | `border(color = PopColor.grey800, width = 1.dp)` |


## Example Row from Button Molecules Table

| Component | Count | Description | Key Variants | Figma Page | Codebase File | Implementation | Code Pattern |
|---|---|---|---|---|---|---|---|
| Button | 1 | Primary interactive element with multiple variants and states | Variant (primary, secondary, tertiary, outline, ghost, danger), State (default, loading, disabled), Size (large, medium, small) | :arrow_right_hook: ❖ Button :white_check_mark: | PopButtonV2.kt | `PopButtonV2` composable | `PopButtonV2(text = "Click Me", variant = ButtonVariant.Primary, onClick = {...})` |


## End of Master Prompt

**Generated bridge file should be named:** `bridgefile.md`
**Primary use:** Reference document for translating design system specifications to Kotlin/Jetpack Compose code