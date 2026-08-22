# Firebase Chat Implementation with Bug Fix

Implement a Firebase Firestore-backed chat system in the `chatbox` app and fix the `NullPointerException` in the `updateMessageInList` method.

## Proposed Changes

### [Models]

#### [NEW] [Message.java](file:///C:/Users/ELCOT/AndroidStudioProjects/chatbox/app/src/main/java/com/example/chatbox/Message.java)
- Define `Message` class with `id`, `text`, `sender`, and `timestamp`.

### [UI Components]

#### [NEW] [MessageAdapter.java](file:///C:/Users/ELCOT/AndroidStudioProjects/chatbox/app/src/main/java/com/example/chatbox/MessageAdapter.java)
- RecyclerView adapter to display messages.

#### [NEW] [item_message.xml](file:///C:/Users/ELCOT/AndroidStudioProjects/chatbox/app/src/main/res/layout/item_message.xml)
- Layout for individual message items.

#### [MODIFY] [activity_main.xml](file:///C:/Users/ELCOT/AndroidStudioProjects/chatbox/app/src/main/res/layout/activity_main.xml)
- Add `RecyclerView` for messages.
- Add `EditText` and `ImageButton` for message input.

### [Main Logic]

#### [MODIFY] [MainActivity.java](file:///C:/Users/ELCOT/AndroidStudioProjects/chatbox/app/src/main/java/com/example/chatbox/MainActivity.java)
- Initialize Firebase Firestore.
- Implement `listenForMessages()` to fetch updates in real-time.
- Implement `updateMessageInList(Message message)` with a **null-safe check** to fix the reported crash.
- Implement `sendMessage()` to push new messages to Firestore.

## Verification Plan

### Automated Tests
- Build the project using `./gradlew assembleDebug` to ensure no compilation errors.

### Manual Verification
- Deploy to a device/emulator.
- Send messages and verify they appear in the list.
- Verify that updating messages (if applicable) does not trigger the NPE.
