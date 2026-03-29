# Event Listener Refactoring Plan

## Objective
Separate the monolithic `AdministrationEventsListener` into specialized, module-specific event listeners for better organization and maintainability.

---

## Current State

### File Structure
```
notification/api/events/listeners/
└── AdministrationEventsListener.java  (36 lines, handles both contact & newsletter)
```

### Current Implementation
- Single class handles events from multiple domains
- Mixed responsibilities (contact requests + newsletter subscriptions)
- Harder to maintain and extend

---

## Target State

### New File Structure
```
notification/api/events/listeners/
└── administration/
    ├── ContactEventListener.java      (handles ContactRequestCreatedEvent)
    └── NewsletterEventListener.java   (handles NewsletterSubscriptionCreatedEvent)
```

### Benefits
1. **Single Responsibility** - Each listener handles one domain
2. **Easier Maintenance** - Changes to contact logic don't affect newsletter logic
3. **Better Organization** - Clear separation by module
4. **Scalability** - Easy to add new listeners for other modules (account, order, etc.)

---

## Implementation Steps

### Step 1: Create Directory Structure
**Action:** Create new directory
```bash
mkdir -p notification/api/events/listeners/administration/
```

**Status:** ✅ Already created

---

### Step 2: Create ContactEventListener.java

**Location:** `notification/api/events/listeners/administration/ContactEventListener.java`

**Package:** `fr.gopartner.tregusto.notification.api.events.listeners.administration`

**Content:**
```java
package fr.gopartner.tregusto.notification.api.events.listeners.administration;

import fr.gopartner.tregusto.administration.api.events.ContactRequestCreatedEvent;
import fr.gopartner.tregusto.notification.internal.EmailNotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContactEventListener {

    private final EmailNotificationService emailNotificationService;

    @ApplicationModuleListener
    void handleContactFormSubmissionEvent(ContactRequestCreatedEvent event) throws MessagingException {
        log.info("Processing contact form submission for request ID: {}", event.requestId());
        emailNotificationService.sendContactConfirmation(
                event.requestId(),
                event.recipientName(),
                event.recipientEmail(),
                event.message()
        );
    }
}
```

**Changes from original:**
- Updated log level from `ERROR` to `INFO` (more appropriate for successful processing)
- Clean separation of contact logic

---

### Step 3: Create NewsletterEventListener.java

**Location:** `notification/api/events/listeners/administration/NewsletterEventListener.java`

**Package:** `fr.gopartner.tregusto.notification.api.events.listeners.administration`

**Content:**
```java
package fr.gopartner.tregusto.notification.api.events.listeners.administration;

import fr.gopartner.tregusto.administration.api.events.NewsletterSubscriptionCreatedEvent;
import fr.gopartner.tregusto.notification.internal.EmailNotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsletterEventListener {

    private final EmailNotificationService emailNotificationService;

    @ApplicationModuleListener
    void handleNewsletterSubscriptionCreated(NewsletterSubscriptionCreatedEvent event) {
        log.info("Sending newsletter confirmation email for: {}", event.email());
        try {
            emailNotificationService.sendNewsletterConfirmation(event.email(), event.token());
        } catch (MessagingException e) {
            log.error("Failed to send newsletter confirmation email to {}", event.email(), e);
        }
    }
}
```

**Key points:**
- Handles only newsletter events
- Includes error handling for email failures
- Proper logging at info and error levels

---

### Step 4: Delete AdministrationEventsListener.java

**Action:** Remove the old monolithic listener file

**Reason:** All functionality has been migrated to specialized listeners

---

### Step 5: Verify Compilation

**Action:** Run Maven compile to ensure all imports and dependencies are correct

```bash
mvn clean compile
```

**Expected:** No compilation errors

---

### Step 6: Test Event Handling

**Test Scenarios:**
1. Submit contact form → Verify `ContactEventListener` processes event
2. Subscribe to newsletter → Verify `NewsletterEventListener` processes event
3. Check logs → Verify appropriate log messages appear

---

## Future Considerations

### Potential New Listeners
As the application grows, we may need:
- `account/` package for user-related events
- `order/` package for order events
- `reservation/` package for reservation events

### Shared/Common Listeners
If we have events that need to be handled across multiple modules, we could create:
- `listeners/common/` for shared event handling
- `listeners/global/` for application-wide events

---

## Risk Assessment

### Low Risk
- ✅ No business logic changes
- ✅ Same functionality, better organization
- ✅ Spring's component scanning will automatically detect new listeners
- ✅ `@ApplicationModuleListener` ensures proper event handling

### Mitigation
- Compile after changes to verify no import errors
- Test both contact form and newsletter subscription flows
- Monitor logs to ensure events are being processed correctly

---

## Success Criteria

1. ✅ Directory structure created
2. ✅ ContactEventListener.java created and working
3. ✅ NewsletterEventListener.java created and working
4. ✅ AdministrationEventsListener.java removed
5. ✅ Project compiles successfully
6. ✅ Both event types are handled correctly
7. ✅ Logs show appropriate messages

---

## Estimated Effort
- **Time:** ~15 minutes
- **Complexity:** Low
- **Risk:** Low

---

## Notes
- Package naming follows the pattern: `listeners.{module}.{event-type}`
- This structure aligns with Spring Modulith best practices
- Each listener is a Spring `@Component` for automatic discovery
- Dependency injection via constructor (`@RequiredArgsConstructor`)
