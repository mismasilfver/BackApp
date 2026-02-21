# Development Agent Behavior Control

## Project Context
- **Project**: Back Pain Exercise Android App
- **Architecture**: Clean Architecture (Domain, Data, Presentation layers)
- **Methodology**: TDD (Test-Driven Development)
- **Technology Stack**: Kotlin, Jetpack Compose, Room, Hilt, Material Design 3

## Core Development Principles

### 1. TDD Approach (Strict)
- **ALWAYS** write failing tests before implementation
- **NEVER** write production code without corresponding test
- **RED-GREEN-REFACTOR** cycle must be followed
- Test failures must be verified before implementation

### 2. SOLID Principles (Non-negotiable)
- **Single Responsibility**: Each class has one reason to change
- **Open/Closed**: Open for extension, closed for modification
- **Liskov Substitution**: Subtypes must be substitutable for base types
- **Interface Segregation**: Clients shouldn't depend on unused interfaces
- **Dependency Inversion**: Depend on abstractions, not concretions

### 3. Clean Architecture Compliance
- **Domain Layer**: Pure business logic, no framework dependencies
- **Data Layer**: Database, network, external integrations
- **Presentation Layer**: UI, ViewModels, Composables
- **Dependency Direction**: Always points inward (Presentation → Domain ← Data)

## Coding Standards

### 1. Kotlin Conventions
- Use immutable data classes where possible
- Prefer `val` over `var`
- Use expression functions for simple logic
- Apply proper visibility modifiers
- Use `@Inject constructor` for dependency injection

### 2. Room Database Rules
- Entities must have primary keys
- Use proper Room annotations (@Entity, @Dao, @Query)
- Implement type converters for complex types
- Use Flow for reactive data streams
- Handle nullability properly

### 3. Jetpack Compose Guidelines
- Follow Material Design 3 principles
- Create reusable composables
- Use proper state management (remember, mutableStateOf)
- Implement proper navigation patterns
- Handle configuration changes

### 4. Testing Requirements
- **Unit Tests**: 70% coverage minimum
- **Integration Tests**: 20% coverage minimum
- **UI Tests**: 10% coverage minimum
- Use descriptive test names with backticks
- Test both happy path and edge cases
- Mock external dependencies

## Project-Specific Rules

### 1. Exercise Management
- All 11 exercises must be supported
- Timer functionality for each exercise
- Completion tracking without analytics
- Offline-first design
- Optional YouTube video integration

### 2. Data Flow
```
UI Event → ViewModel → Use Case → Repository → Room Database
```

### 3. Error Handling
- Use sealed classes for result types
- Handle network vs local errors differently
- Provide user-friendly error messages
- Log technical errors for debugging

### 4. Performance Requirements
- Lazy loading for large datasets
- Proper coroutine usage
- Memory leak prevention
- Efficient image loading

## Development Workflow

### 1. Feature Development
1. Write failing test for new feature
2. Implement minimal code to pass test
3. Refactor while keeping tests green
4. Add integration tests if needed
5. Update documentation

### 2. Code Review Checklist
- [ ] Tests written before implementation
- [ ] SOLID principles followed
- [ ] Clean architecture maintained
- [ ] No code duplication
- [ ] Proper error handling
- [ ] Performance considerations
- [ ] Documentation updated

### 3. Git Commit Standards
- Use descriptive commit messages
- Commit atomic changes
- Include issue references when applicable
- Follow conventional commit format

### 4. Commit Workflow
- **Always prompt for creating a commit** after completing each development phase or significant feature
- Ensure all tests pass before committing
- Review changes for completeness and accuracy
- Include relevant context in commit messages
- Maintain clean git history with meaningful commits

## Quality Gates

### Before Moving to Next Phase:
1. All tests must pass
2. Code coverage requirements met
3. Architecture principles validated
4. Performance benchmarks met
5. Documentation complete

### Code Smells to Avoid:
- God classes
- Long parameter lists
- Magic numbers/strings
- Deep nesting
- Inconsistent naming
- Missing error handling

## Tool Configuration

### 1. Gradle Configuration
- Use Kotlin DSL
- Proper dependency management
- Configured for offline development
- Enabled build caching

### 2. IDE Settings
- Kotlin coding standards
- Proper import organization
- Code formatting rules
- Inspection profiles

### 3. Testing Framework
- JUnit 5 for unit tests
- MockK for mocking
- Room testing for database
- Compose testing for UI

## Decision Making Framework

### When Adding New Features:
1. Does it follow TDD? → If no, write tests first
2. Does it violate SOLID? → If yes, refactor
3. Does it fit clean architecture? → If no, redesign
4. Are tests comprehensive? → If no, add more tests
5. Is documentation updated? → If no, update docs

### When Refactoring:
1. Ensure all tests pass before refactoring
2. Make small, incremental changes
3. Run tests after each change
4. Update tests if interface changes
5. Verify no functionality is lost

## Emergency Protocols

### If Tests Fail:
1. Stop all new development
2. Identify root cause
3. Fix failing tests
4. Verify no regressions
5. Resume development

### If Architecture Violated:
1. Identify violation type
2. Plan refactoring approach
3. Implement changes incrementally
4. Update tests accordingly
5. Validate architecture compliance

## Success Metrics

### Code Quality:
- Test coverage > 90%
- Zero critical code smells
- All SOLID principles followed
- Clean architecture maintained

### Project Health:
- All tests passing
- Build time under 2 minutes
- App startup time under 2 seconds
- Memory usage within limits

---

**This document controls all development behavior. Deviations require explicit approval and documentation of reasons.**
