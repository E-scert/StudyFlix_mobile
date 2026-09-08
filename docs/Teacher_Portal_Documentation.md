# StudyFlix Teacher Portal — System Documentation

**Product:** StudyFlix (Noko Entertainment Studio)
**Module:** Teacher Portal (`/teacher/`)
**Backend:** Firebase (Auth, Firestore, Storage, Cloud Functions)
**Firebase Project:** `studyflix-1c5e5`
**Document version:** 1.0 — generated from static analysis of the uploaded `teacher/` bundle (14 HTML pages, 9 JS modules, 1 shared stylesheet).

---

## 1. System Overview

The Teacher Portal is a client-heavy, server-light web application. There is no custom backend server — every page talks **directly to Firebase** (Firestore for data, Firebase Auth for identity, Firebase Storage for files) using the Firebase JS SDK v9.6.1 in **compat mode** (`firebase-app-compat.js`, `firebase-auth-compat.js`, `firebase-firestore-compat.js`, `firebase-storage-compat.js`, `firebase-functions-compat.js`). One Cloud Function (`generateSchoolCode`) is called from the registration flow; everything else is plain Firestore reads/writes from the browser.

### 1.1 Architecture style

The bundle actually contains **two different, non-interoperating architectures** layered on top of each other:

| Architecture | Pages | Notes |
|---|---|---|
| **A — Multi-Page App (current/live)** | `index.html`, `dashboard.html`, `overview.html`, `my-learners.html`, `write-homework.html`, `my-publications.html`, `marks-results.html`, `submissions-list.html`, `view-submission.html`, `chat.html`, `learners-chat.html`, `settings.html` | Each is a standalone HTML file with its own inline `<script>` block and Firebase config. All of them `fetch('sidebar.html')` at runtime, inject it into a `#main-sidebar` div, then dynamically load `js/sidebar.js` for navigation, badges and logout. This is the **architecture actually wired up and reachable by a user**. |
| **B — Legacy Single-Page App (dead code)** | `teacher-simple.html` (self-contained) + the module set `js/auth.js`, `js/dashboard.js`, `js/assignment.js`, `js/corrections.js`, `js/learners.js`, `js/marks.js`, `js/navigation.js`, `js/pastpapers.js` | This is an older prototype: one HTML page (`teacher-simple.html`) with `<div class="panel">` sections toggled by a `showPanel()` function, backed by a set of `TeacherXxx` classes (`TeacherAuth`, `TeacherDashboard`, `TeacherAssignments`, `TeacherCorrections`, `TeacherLearners`, `TeacherMarks`, `TeacherPastPapers`, `TeacherNavigation`). **None of the 14 shipped HTML pages `<script src="js/...">` reference these 8 files** — they are orphaned. `js/teacher.js` is a 0-byte empty file. |

**Implication for anyone maintaining or migrating this app:** treat folder B (`teacher-simple.html` + the 8 `TeacherXxx` JS classes + `teacher.js`) as legacy/reference material only, not as the source of truth for behavior. All functional descriptions below are based on architecture A unless explicitly marked "(legacy)".

### 1.2 Shared building blocks

- **`sidebar.html`** — HTML+CSS fragment (nav rail, teacher avatar/name/school, subject/grade badge, logout button, mobile hamburger). Injected via `fetch()` into every page's `#main-sidebar`.
- **`js/sidebar.js`** — IIFE that: highlights the active nav link by matching the current filename, wires the hamburger/overlay for mobile, populates teacher name/school/subject from `localStorage.currentTeacher` (or Firestore if absent), and — most importantly — sets up **9 real-time Firestore listeners** that drive the red notification badges next to each nav item (see §17.3).
- **`css/teacher.css`** — global design tokens (`--bg`, `--card`, `--gold`, `--teal`, `--red`, etc.) and shared component styles, used mainly by `dashboard.html`/`index.html`; most of the standalone pages instead re-declare their own `<style>` block with the same CSS variable names (color palette is consistent app-wide even though the CSS is duplicated per page).
- **Firebase config** — identical inline snippet copy-pasted into the `<head>` of every page (see §16).

### 1.3 Core feature set

1. Teacher sign-in and school-based self-registration with admin approval and a per-school access code.
2. Dashboard/overview with class stats, recent assignments, and an "attention" feed of struggling learners / ungraded work.
3. Learner roster per school+grade with live online/average-mark data and a "Chat" shortcut.
4. Assignment authoring: rich-text "written" homework/tests with sub-questions and images, and multiple-choice quizzes with shuffle options.
5. Submission listing, per-question manual grading (tick / half / cross), draft saving, and publishing scores back to learners.
6. AI-flavoured "corrections"/memo tooling (mostly simulated locally in the legacy code; in the live app it is a teacher-authored memo editor, not a live AI call).
7. Marks & Results dashboard with class average, pass rate, at-risk learners.
8. "My Publications" hub: past-paper/question-paper uploads (admin-approved), assignment memo authoring & publishing, quiz management.
9. Two separate chat surfaces: Admin Chat (teacher ↔ platform admin) and Learners Chat (teacher ↔ individual students), both realtime via Firestore.
10. Settings: profile edit, school access code display, subscription status/countdown, password change, subject/grade switch, logout.
11. Subscription/trial gating enforced at login (blocked school, expired subscription, pending/rejected teacher account).

---

## 2. Login & Registration

**File:** `index.html` (also nominally `teacher-simple.html` for the legacy flow).

### 2.1 Sign In tab

Fields: **Email**, **Password**, **School Code** (all three required). Flow (`login()` function):

1. `auth.signInWithEmailAndPassword(email, pwd)`.
2. Fetch `teachers/{uid}`. If the document doesn't exist → sign out, generic "Invalid email or password" error (prevents account enumeration).
3. Check `teacher.status`:
   - `'pending'` → sign out, "Your account is pending admin approval."
   - `'rejected'` → sign out, "Your account was rejected. Contact administrator."
4. Verify the **school code typed by the user matches `teacher.schoolCode`** exactly (case-insensitive — the input is uppercased before comparison). Mismatch → generic invalid-credentials error.
5. Fetch `schools/{teacher.schoolId}`.
   - `school.status === 'blocked'` → sign out, "school-blocked" error.
   - `school.subscriptionEnds` in the past **and** `school.subscription !== 'lifetime'` → sign out, "subscription-expired" error.
6. On success: cache the teacher object (plus embedded `schoolData`) into `localStorage.currentTeacher`, cache `localStorage.teacherSubject` / `teacherGrade`, redirect to `dashboard.html`.

A **"Forgot password?"** link opens a modal that calls `auth.sendPasswordResetEmail(email)`.

There is also a **live "Pay Now" PayPal button** (`https://www.paypal.com/ncp/payment/U5KYM4NPVBQD8`) shown on both the sign-in and sign-up panels — this is the de-facto payment mechanism today; there is no in-app checkout flow wired to Firestore (see §14).

### 2.2 Register School tab (school + first admin-teacher signup)

Fields: School Name*, Admin Email*, Admin Full Name*, Phone*, Password* (+confirm), Province (default Gauteng), Grades Taught (multi-select), Subjects Offered (multi-select), School Address.

Client-side password policy (`isPasswordStrong()`): ≥8 chars, upper+lower+digit+special char, enforced via regex before submit; a live strength meter (`checkPasswordStrength()`) colors a progress bar Weak/Medium/Strong as the admin types.

Submit flow (`schoolSignup()`):

1. Generate a **school access code**:
   - Preferred path: call Cloud Function `generateSchoolCode({ schoolName })`.
   - Fallback (if the function call throws): compute a deterministic-looking client-side hash of `schoolName + timestamp + random` → `SF` + 12 base-36 chars, e.g. `SF7XK9M2P4Q1`.
2. `auth.createUserWithEmailAndPassword(email, pwd)`, then `user.updateProfile({ displayName: name })`.
3. Write **three documents in the same transaction-less sequence**:
   - `schools/{uid}` — `{ schoolId, schoolName, schoolEmail, schoolCode, province, address, grades, subjects, createdAt, status: 'pending', subscription: 'trial', subscriptionEnds: now+30 days, learnersCount: 0 }`.
   - `teachers/{uid}` — `{ uid, name, email, phone, schoolId: uid, schoolName, schoolCode, role: 'admin', status: 'pending', grades, subjects, createdAt }`. Note the very first teacher of a school is tagged `role: 'admin'`.
   - `schoolCodes/{schoolCode}` — `{ schoolId: uid, schoolName, createdAt, active: true }` — a reverse-lookup index so a code can be validated/looked-up without scanning `schools`.
4. Show a "Registration successful — pending admin approval" message, then auto-switch back to the Sign-In tab after 5s with the email pre-filled.

### 2.3 Auto-redirect for already-authenticated sessions

`index.html` also attaches an `auth.onAuthStateChanged` listener that runs the **exact same status/subscription checks as login()** whenever a teacher who is already signed in (e.g. reopening the tab) lands back on `index.html`, and if everything checks out, redirects straight to `dashboard.html` without re-entering credentials.

### 2.4 Legacy login variant (`teacher-simple.html` / `js/auth.js`, not live)

Worth flagging because it is materially different and could confuse a future engineer: this legacy flow does **not** ask for a school code at all — it only asks for email/password + a **School dropdown** populated from `schools` where `status in ['active','trial','pending']`. If no `teachers/{uid}` doc exists it silently **auto-creates one** with `subject: 'Mathematics'`, `grade: 'Grade 8'`, `status: 'active'` — i.e. no approval gate. This is materially less secure than the live flow and should not be resurrected.

---

## 3. School Management

There is **no dedicated "manage school" UI inside the teacher portal** — school-level administration (approving teachers, approving question papers, managing subscription/blocking) is implicitly the job of a separate **Admin Portal** (referenced via `/admin/utils/sanitize.js` import in `index.html`, and via the `chat_student_{uid}` chat thread with a hardcoded `'Administrator'` participant) which was **not included in this upload**.

From the teacher side, `schools/{schoolId}` is:

- **Created** once, during Register School (§2.2).
- **Read** at login to check `status` (`pending` / `active` / `trial` / `blocked`) and `subscriptionEnds` (§2.1, §14).
- **Read** in `settings.html` to surface the school's `schoolCode` and name if the teacher document itself doesn't cache them.
- **Never written to** by any teacher-portal page after signup — plan changes, blocking, and code regeneration are admin-only operations that must happen elsewhere (Admin Portal / Firestore console / Cloud Functions).

The **School Access Code** is the mechanism that binds learners and additional teachers to a school: it is displayed (read-only, with a "Copy Code" button) in `settings.html`, and is required as a manual entry field on both teacher sign-in and (presumably) learner sign-up.

---

## 4. Teacher Management

- **Identity:** 1 Firebase Auth user ⇄ 1 `teachers/{uid}` document. The first teacher of a school gets `role: 'admin'`; the intent (not explicitly enforced in the shipped teacher-portal code) is presumably that this role gates access to admin-only tooling elsewhere.
- **Approval state machine:** `status` ∈ `pending → active|rejected`. Set to `pending` at signup; flipped to `active`/`rejected` by an administrator outside this codebase. Both `login()` in `index.html` and the auth guard in `settings.html` re-check this status on every session.
- **Profile fields:** `name`, `email`, `phone`, `school`/`schoolName`, `schoolId`, `schoolCode`, `grades[]`, `subjects[]`, `createdAt`, plus session-scoped `selectedSubject`/`selectedGrade` (or `subject`/`grade` in the legacy shape) which determine which class the teacher is currently "looking at" throughout the dashboard.
- **Editing:** `settings.html` → "Edit Profile" modal lets a teacher change `name`, `phone`, `schoolName`/`school` (writes via `teachers/{uid}.update()` + `auth.currentUser.updateProfile()`). Email cannot be changed from the UI.
- **Password change:** `settings.html` re-authenticates with `EmailAuthProvider.credential(email, currentPassword)` then calls `user.updatePassword(newPwd)` (min 6 chars — looser than the 8-char/complexity rule enforced at signup).
- **Subject/Grade switching:** "Switch Subject/Grade" button clears `localStorage.selectedSubject`/`selectedGrade` and routes back to the dashboard/subject-selector so the teacher can pick a different class context. All subsequent Firestore queries (`assignments`, `students`, `submissions`) are scoped by whatever subject/grade is currently selected.
- **Presence:** `learners-chat.html` calls `updateOnlineStatus()` every 30s, writing `teachers/{uid}.lastActive` — this appears to exist primarily so a *learner-side* app could show "teacher online", though it is not consumed anywhere inside the teacher portal itself.

---

## 5. Learner Management

**File:** `my-learners.html` (roster with realtime marks), plus lighter read-only views in `dashboard.js`'s legacy panel, `marks-results.html`, and the two chat pages.

### 5.1 Data source

Learners are Firebase Auth users mirrored into the top-level `students` collection, keyed by `uid`. The **query filter used to scope "my learners" is inconsistent across pages** — this is a real data-model risk worth flagging:

| Page | Filter used |
|---|---|
| `my-learners.html`, `learners-chat.html`, `overview.html` | `students.where('schoolId','==', teacher.schoolId).where('grade','==', teacherGrade)` |
| `dashboard.js` (legacy), `js/learners.js` (legacy), `js/marks.js` (legacy), `submissions-list.html`'s online-counter | `students.where('school','==', teacher.school).where('grade','==', grade)` (string school **name**, not `schoolId`) |

Both a `school` (name string) and a `schoolId` (Auth uid of the school's admin teacher) field are expected to exist on every student document; if a learner-facing signup flow only populates one of the two, some teacher-portal pages will silently show an empty roster. This should be reconciled (pick one canonical field) during any refactor or Android port.

### 5.2 Roster view (`my-learners.html`)

- Realtime via two `onSnapshot` listeners: `subscribeTeacherAssignments()` (all of this teacher's assignments) and `subscribeSubmissions()` (**all** submissions in the whole system, filtered client-side — a scalability concern, see §20) — combined in `refreshLearnersView()` to compute each learner's live average.
- Per learner: avatar (initials, deterministic color from name), online/offline dot (`lastActive` within 5 minutes), computed average %, submission count, and a **Chat** button that deep-links into `learners-chat.html`.
- `showLearnerProfile(learnerId)` (in `learners-chat.html`) opens a modal with full name, email, phone, school, grade, and subscription **plan** (`data.plan`: `'ezame'` → "Ezame Chomi", `'life'` → "Life After Teenage", else "Free") sourced straight from `students/{id}`.

### 5.3 Legacy roster (dead code)

`js/learners.js` / `js/marks.js` render the same kind of table from Firestore but fall back to a **hardcoded demo array of 8 fictitious South African learner names** (Thabo Nkosi, Lerato Sithole, etc.) whenever Firestore returns zero rows or errors — useful only as a UI placeholder in the old panel-based prototype, not present in any live page.

---

## 6. Assignments

**Files:** `write-homework.html` (live authoring UI), `js/assignment.js` (legacy authoring class).

### 6.1 Two assignment types, one collection

Both "written" assignments (homework/tests/exams) and "quizzes" (MCQ) are persisted into the **same top-level `assignments` collection**, disambiguated by a `quizType: true` flag on quiz documents (and by `type: 'Quiz'` in the legacy writer). Quizzes are *additionally* mirrored into a separate `quizzes` collection with a slightly different shape (used by `submissions-list.html`'s selector and `my-publications.html`'s "Quizzes" tab).

### 6.2 Written assignment schema (`write-homework.html` → `publishAssignment()`)

```
assignments/{autoId} = {
  teacherId, teacherName, school, schoolId, grade, subject,
  title, totalMarks, dueDate, duration, examiner, examTime,
  instructions: string[],
  questions: [                     // "parent" questions
    {
      number, text, imageUrl, totalMarks,
      subQuestions: [ { id, text, marks, imageUrl }, ... ]
    }, ...
  ],
  memo: string,                    // teacher-authored plain-text model answer
  status: 'active',
  isGlobal: false,
  createdAt: Date (client-generated, NOT serverTimestamp)
}
```

Questions support an optional **image upload per (sub-)question**, stored in Firebase Storage and referenced by `imageUrl`.

### 6.3 Quiz schema (`write-homework.html` → `publishQuiz()`)

Writes to **both**:

- `assignments/{autoId}` with `quizType: true`, `questions[]` shaped as `{ id, text, type: 'multiple_choice', options: [{id:'A'..'D', text, isCorrect, imageUrl}], correctAnswer, marks, imageUrl }`, plus `shuffleQuestions`/`shuffleAnswers` booleans and a raw `quizData.originalQuestions` backup of the authoring-time question objects.
- `quizzes/{autoId}` with a parallel but not-identical shape (`options` as a plain array of strings + separate `correctOption` index) — kept for pages that specifically query the `quizzes` collection.

### 6.4 Legacy quiz builder (`js/assignment.js`, dead code)

Simpler in-memory model: `{ id, text, options: ['','','',''], correct: indexNumber, marks }`, one flat array, no sub-questions, no images, `shuffleQuestions`/`shuffleAnswers` checkboxes. Publishing writes a single `assignments` document with `type either 'Quiz' or hwType`. Also has a fake **"Generate AI Memo"** button that just `setTimeout`s for 1.8s and inserts a hardcoded 3-question algebra memo — illustrative of intended AI-memo UX, not a real integration.

### 6.5 Lifecycle

`status` starts at `'active'` (visible to learners immediately on publish — there is no "review before publish" gate for the teacher's own regular assignments, only for uploaded question papers, see §10). Sidebar badge logic (§17.3) treats an assignment as "closed" once some other process (not present in this bundle — presumably an Admin/Cloud Function job, or a due-date cron) flips `status: 'closed'` and sets `resultsPublished`/`closedAt`.

---

## 7. Submissions

**Files:** `submissions-list.html` (list/filter), `view-submission.html` (detail + grading), `js/sidebar.js` (unread/ungraded badge).

### 7.1 Collection shape

`submissions/{autoId}`:

```
{
  studentId | userId,          // either field may be present — code checks both
  assignmentId | quizId,
  studentName, studentEmail,
  answers: [ { answers: [{answer: string}, ...] } | {text} | {answer} | string, ... ],
  submittedAt, timeSpent (seconds), autoSubmitted (bool),
  isGraded (bool),
  grading: { ... }              // see §8
}
```

### 7.2 Listing & filtering (`submissions-list.html`)

1. Populate a dropdown with every `assignments` + `quizzes` document owned by `teacherId == currentTeacher.uid` (label suffixed `[Assignment]` / `[Quiz]`).
2. On selection, query `submissions.where(assignmentId|quizId, '==', selectedId)`.
3. Resolve each submission's `studentId`/`userId` to a display name via `students/{id}` (name-cache'd in a `Map` for the session to avoid duplicate reads).
4. Client-side filter chips: **All / Pending Grading / Graded / Published**:
   - Pending = `!isGraded && !grading.published`
   - Graded = `isGraded === true || grading` exists
   - Published = `grading.published === true`
5. Each row shows student name, assignment title, submitted-at, time spent, Manual vs 🤖 Auto-submitted tag, status badge, score preview (if graded), and a **"Grade & Review"** button that opens `view-submission.html?id={submissionId}` in a new tab.

### 7.3 "Online now" counter

Both `submissions-list.html` and several other pages independently recompute an "online learners" count every 30s by re-fetching the full `students` collection for the teacher's school+grade and checking `lastActive` within the last 5 minutes — this is **not** a shared/cached value; each page pays its own Firestore read cost.

---

## 8. Grading

**File:** `view-submission.html` — the single most detailed page in the bundle.

### 8.1 Question flattening

The teacher's original `assignments/{id}.questions[]` (which may have **parent questions with nested `subQuestions`**) is flattened at load time into a single ordered array `answerableItems[]`, each entry carrying a synthetic display number (`"2.3"` for sub-question 3 of parent question 2, or `"Question N"` for a question with no sub-questions). The learner's stored `answers[]` array is matched to `answerableItems` **positionally by index**, tolerating three different possible answer shapes (`{answers:[{answer}]}`, `{text}`/`{answer}`, or a bare string) — defensive coding against an evolving learner-side answer format.

### 8.2 Marking UI

For every answerable item the teacher sees the question text, an optional question image (click-to-enlarge), the learner's answer line(s), and four buttons:

- ✅ **Tick** → full marks (`marksState[i] = 'full'`)
- ➗ **Half** → half marks (`'half'`)
- ❌ **Cross** → zero (`'wrong'`)
- ↺ **Reset** → clears the mark (`null`)

`computeStats()` recomputes tick/half/cross counts and a running `totalPoints` (full = 1 point, half = 0.5, wrong = 0 — **note: this is a normalized 0–1-per-question point system, not the question's actual mark weight**; `totalPossible` is simply `answerableItems.length`, i.e. every question counts as "1" toward the denominator regardless of its authored `marks` value). This is a real inconsistency vs. the assignment's authored per-question `marks` field and should be reconciled before scaling grading to unevenly-weighted question papers.

If the submission already has prior `grading.markLabels` (or a compatible `perQuestionMarks` array), the UI restores the previous marks on load, so grading is idempotent/re-editable.

### 8.3 Save vs. Publish

Two buttons, both calling `persistMarks(publishToStudent)`:

- **Save Draft** (`publishToStudent = false`): writes `submissions/{id}.grading` (merge) + `isGraded: true` + `lastGradedAt`. Not visible to the learner yet (mirrors the "Pending"/"Draft Saved" distinction rendered in `submissions-list.html`).
- **Publish** (`publishToStudent = true`): does the same submission-doc write, **then additionally fans out to two more collections** so the learner-side app (not included in this bundle) can read results without needing teacher-owned security-rule access to `submissions`:
  - `studentsMarks/{studentUid}/myMarks/{submissionId_assignmentId}` — score, percentage, per-question breakdown, `teacherFeedback: 'Graded and published'`, `isPublished: true`.
  - `allStudentMarks/{studentUid}/marks/{submissionId}` — a slightly renamed duplicate (`score`/`outOf` instead of `totalScore`/`totalPossible`) — apparently a second, differently-shaped index used elsewhere in the wider StudyFlix product (student portal / analytics), not read by anything in this teacher-portal bundle.

If the learner's `studentId`/`userId` cannot be resolved at publish time, the flow aborts with a toast rather than silently dropping the publish — a reasonable defensive guard, but it means a malformed submission can be stuck "graded but unpublished" until the missing field is backfilled.

### 8.4 AI-flavoured corrections (legacy, `js/corrections.js`)

Not present in the live grading flow at all. In the legacy panel-based prototype, a teacher picks one of their assignments, clicks "Generate", and after a **hardcoded 2.8s `setTimeout`** a canned 3-question correction set is fabricated client-side (different canned text depending on whether the title contains "algebra"/"expand"/"variable"). Corrections can then be **toggled visible/hidden to the whole class** via a `releasedCorrections` boolean with a confirmation modal. This is UI scaffolding for an eventual real AI-marking integration; no LLM/Cloud Function is actually called anywhere in the bundle.

---

## 9. Results

**File:** `marks-results.html`.

- Loads **every** assignment owned by the teacher, then, per assignment, queries `submissions.where('assignmentId','==', assignmentId)` and keeps only those with `grading.published === true || isGraded === true`.
- Batches learner-name lookups in groups of ≤10 via `students.where('uid','in', batch)` (Firestore's `in` operator cap).
- Computes and renders: total assignments, graded-submission count, per-learner latest %, class average, pass count (≥50%), at-risk count (<50%), top score — plus a filterable-by-assignment table (dropdown built from `assignmentFilterSelect`).
- Score color-coding is consistent app-wide: **≥75% green/high, 50–74% amber/mid, <50% red/low.**

---

## 10. Publications

**File:** `my-publications.html` — three independent sub-sections on one page.

### 10.1 Question Papers (admin-moderated uploads)

`questionPapers/{autoId} = { teacherId, title, subject, year, totalMarks, dueDate, fileUrl (Storage), createdAt, submittedAt, type: 'questionPaper', status: 'pending', reviewedBy: null, reviewedAt: null, rejectionReason: null }`.

- Upload flow: pick a file → Storage upload to `questionPapers/{teacherId}/{timestamp}_{filename}` → get download URL → write the Firestore doc with `status: 'pending'`.
- **This is the one place in the teacher portal with an explicit admin-approval gate on content** (as opposed to regular assignments, which go live immediately): status badges are Pending ⏳ / Approved ✅ / Rejected ❌ (with an optional `rejectionReason` shown to the teacher). Only `approved` papers with a `fileUrl` get a "View Paper" button; the others are disabled placeholders.
- Delete removes both the Firestore doc and the underlying Storage object (`storage.refFromURL(fileUrl).delete()`).

### 10.2 Homework/Assignment memo authoring

Lists the teacher's own `assignments` (excluding quizzes, `quizType !== true`) and lets them open a **memo modal** per assignment:

- Flattens the assignment's `questions[]`/`subQuestions[]` the same way `view-submission.html` does, to present one memo textarea per answerable sub-question.
- **Save Draft** → `assignments/{id}.memoDraft = string[]` (per-question draft text) + `memoDraftUpdatedAt`.
- **Publish to Learners** → `assignments/{id}.memoPerQuestion = string[]` + `memoPublished: true` + `memoPublishedAt` (draft array is preserved too). A toast explicitly tells the teacher students will see it under "My Marks".
- A small on-screen calculator (superscript/√ insertion helpers) is available inside the memo modal for typing math notation into a plain textarea.

### 10.3 Quizzes

Lists `quizzes` owned by the teacher (separate from the assignment memo list above) with delete support; no memo authoring for quizzes (their memo is presumably the `correctAnswer` already baked into each question at authoring time).

---

## 11. Learner Chat

**File:** `learners-chat.html`.

- **Conversation list** = every learner in `students` for `schoolId == teacher.schoolId && grade == currentGrade` (grade sourced from `localStorage.selectedGrade`, falling back to the teacher doc). Each row shows avatar/photo, name, and online-status (`lastActive` < 5 min).
- **Chat/thread id convention:** `chat_{studentId}_{teacherId}` — a deterministic composite key, one thread per (teacher, student) pair, stored as `chats/{chatId}` with a `messages` subcollection.
- Opening a conversation `set()`s (merge) the parent `chats/{chatId}` doc with `participants: [teacherUid, studentId]` + timestamps (idempotent — safe to call every time a thread is opened), then subscribes with `onSnapshot` ordered by `timestamp asc`.
- **Sending** a message writes into `chats/{chatId}/messages` with `{ text, senderId, senderName, recipientId, timestamp: serverTimestamp(), read: false, chatType: 'student' }`. The `chatType` tag exists specifically so the sidebar's unread-badge math can separate "admin" unread counts from "student" unread counts (see §17.3) even though both live under the same top-level `chats` collection.
- **Read receipts:** opening a conversation batch-updates every unread message addressed to the current teacher (`recipientId == teacher.uid && read == false`) to `read: true`.
- **Deletion:** any message can be hard-deleted for everyone (`chats/{chatId}/messages/{id}.delete()`) via a trash icon that appears on hover, after a `confirm()` prompt.
- **Learner profile modal:** clicking a learner's avatar/name fetches `students/{id}` fresh and shows name, email, phone, school, grade, and subscription plan.
- Auto-refreshes the conversation list and the teacher's own presence (`teachers/{uid}.lastActive`) every 30 seconds.

---

## 12. Admin Chat

**File:** `chat.html` — structurally almost identical to Learner Chat but simplified to a **single, fixed conversation** with a hardcoded pseudo-contact `{ id: 'admin', name: 'Administrator' }`.

- **Thread id convention:** `chat_student_{teacherUid}` (fixed prefix `chat_student_`, i.e. every teacher has exactly one admin thread, unlike the per-learner keying in §11).
- Sending sets `chatType: 'admin'` and `recipientId: 'admin'` — this is the counterpart tag consumed by the sidebar badge split logic.
- On open, the page immediately (a) subscribes to an unread-count listener scoped to this one thread, (b) auto-selects the (only) conversation, and (c) marks all unread messages as read — there is no "conversation list" interaction beyond that single admin row.
- No delete-message affordance here (present in Learner Chat, absent in Admin Chat) — likely intentional so a teacher can't erase their side of a moderation conversation with the platform.

---

## 13. Settings

**File:** `settings.html`. Covered functionally in §4 (profile/password) and §3 (school code) and §14 (subscription display); the page also performs the strictest **route guard** in the bundle:

1. `auth.onAuthStateChanged` → if no user, redirect to `index.html`.
2. Attempt a **role check** against a `users/{uid}` collection (`role !== 'teacher'` → deny). This collection/pattern is **not referenced anywhere else in the bundle** — it may be a remnant of, or a bridge to, a unified multi-role `users` collection used elsewhere in the wider StudyFlix product; failure to read it is caught and logged as a warning, not treated as fatal (falls through to the `teachers` check).
3. Re-check `teachers/{uid}.status` (`pending`/`rejected` → sign out).
4. Cache `currentTeacher` + normalized `selectedSubject`/`selectedGrade` into `localStorage` for every other page to read.
5. On any *data-loading* error after auth succeeds, the page deliberately **does not force a redirect** — it just toasts the error, so a transient Firestore hiccup doesn't lock a legitimate teacher out.

---

## 14. Subscription System

There is **no in-app payment/checkout integration** with Firestore or Cloud Functions — subscriptions are managed entirely by:

1. A **default 30-day trial** granted automatically at school registration (`schools/{id}.subscription: 'trial'`, `subscriptionEnds: now + 30d`).
2. A **static PayPal "Pay Now" link** (`https://www.paypal.com/ncp/payment/U5KYM4NPVBQD8`) shown on the login screen — presumably an admin manually upgrades `schools/{id}.subscription`/`subscriptionEnds` in Firestore (or via the separate Admin Portal) after seeing a PayPal payment come in. No webhook or Cloud Function in this bundle reconciles PayPal payments with Firestore automatically.
3. **Enforcement at login** (`index.html`, and mirrored in the legacy `auth.js` redirect check): `school.status === 'blocked'` or (`subscriptionEnds` in the past **and** `subscription !== 'lifetime'`) → the teacher is signed back out immediately with an explanatory toast.
4. **Display in `settings.html`:** plan label mapping — `'ezame'` → *Ezame Chomi (Monthly)*, `'life'` → *Life After Teenage (Yearly)*, anything else → *Free Trial*. Shows registration date, expiry date, a **live "days remaining" countdown** (recomputed once per day via `setInterval(…, 86400000)`), and a status pill (Active/green, Trial/amber, Expired/red). If `subscriptionEnds` is entirely absent, the UI derives an implicit trial-end date as `createdAt + 30 days` purely for display purposes.
5. An **"Upgrade Plan"** button exists (`window.location.href = 'upgrade.html'`) but **`upgrade.html` is not present in this bundle** — this is a dangling link that will 404 as shipped.

The same `subscription`/`plan` vocabulary (`'ezame'`, `'life'`) reappears on the **learner** side (`students/{id}.plan`, surfaced in the learner-profile modal in Learner Chat), suggesting subscriptions may actually be sold per-learner in some flows and per-school in others — this dual model should be clarified with product/business stakeholders before building any real payment automation.

---

## 15. Firestore Database Schema

Consolidated from every query/write found across the bundle (fields marked "†" were only ever observed being **read**, never written, in this codebase — they are presumably populated by the Admin Portal, a Cloud Function, or the learner-side app not included here).

### `schools/{schoolId}`  *(schoolId === the founding admin-teacher's Auth uid)*
```
schoolId, schoolName / name, schoolEmail, schoolCode / code, province, address,
grades: string[], subjects: string[], createdAt,
status: 'pending' | 'active' | 'trial' | 'blocked' †,
subscription: 'trial' | 'ezame' | 'life' | 'lifetime',
subscriptionEnds: Timestamp,
learnersCount: number †
```

### `schoolCodes/{code}`
```
schoolId, schoolName, createdAt, active: boolean
```

### `teachers/{uid}`
```
uid, name, email, phone, schoolId, schoolName, school (name string, legacy alias),
schoolCode, role: 'admin' | 'teacher' †, status: 'pending' | 'active' | 'rejected',
grades: string[], subjects: string[],
selectedSubject / subject, selectedGrade / grade,
createdAt, updatedAt, lastActive,
profileComplete † , emailVerified †
```

### `users/{uid}`  *(read-only from teacher portal; role bridge — see §13)*
```
role: 'teacher' | ... (other roles presumably exist in wider system)
```

### `students/{uid}`
```
uid/id, name / fullName / firstName+surname, email, phone, photoURL,
school (name string), schoolId, grade,
plan: 'ezame' | 'life' | (free/absent),
lastActive, createdAt
```

### `assignments/{autoId}`
```
teacherId, teacherName, school, schoolId, subject, grade, title,
type: 'Homework' | 'Quiz' | 'Classwork' | ... (legacy writer only),
quizType: boolean,           // true ⇒ this doc is a quiz, disambiguates shared collection
totalMarks / marks, dueDate / due, duration, examiner, examTime,
instructions: string[],
questions: [ { number/text/imageUrl/totalMarks, subQuestions: [{id,text,marks,imageUrl}] } ]
          | [ { id,text,type:'multiple_choice', options:[{id,text,isCorrect,imageUrl}], correctAnswer, marks, imageUrl } ],
memo: string,                      // plain-text teacher memo (legacy/simple assignments)
memoDraft: string[],               // per-question memo draft (My Publications)
memoPerQuestion: string[],         // published per-question memo
memoPublished: boolean, memoPublishedAt,
shuffleQuestions, shuffleAnswers, quizData: { originalQuestions, totalMarks },
status: 'active' | 'closed' †, isGlobal: boolean,
createdAt, closedAt †, resultsPublished: boolean †,
submissions: number †, corrections †, releasedCorrections † // legacy panel fields
```

### `quizzes/{autoId}`  *(secondary mirror of quiz-type assignments)*
```
teacherId, teacherName, title, school, schoolId, grade, totalMarks, dueDate,
questions: [ { text, options: string[4], correctOption: index, marks, imageUrl, optionImages: [4] } ],
shuffleQuestions, shuffleAnswers, status: 'active', isGlobal: false, createdAt
```

### `submissions/{autoId}`
```
studentId | userId, studentName, studentEmail,
assignmentId | quizId, assignmentTitle | quizTitle,
answers: [ ... one of three shapes, see §8.1 ... ],
submittedAt, timeSpent (s), autoSubmitted: boolean,
isGraded: boolean, lastGradedAt,
grading: {
  perQuestionMarks: (0|0.5|1|null)[], markLabels: ('full'|'half'|'wrong'|null)[],
  totalScore, totalPossible, stats: { ticks, crosses, halfMarks },
  published: boolean, publishedAt, lastUpdated,
  assignmentTitle, assignmentId, subject
}
```

### `studentsMarks/{studentUid}/myMarks/{submissionId_assignmentId}`
```
submissionId, assignmentId, assignmentTitle, subject,
totalScore, totalPossible, percentage (string, 1dp), marksObtained,
gradedAt, publishedAt, teacherFeedback, perQuestionBreakdown, isPublished: true
```

### `allStudentMarks/{studentUid}/marks/{submissionId}`
```
assignmentName, subject, score, outOf, percentage (string, 1dp),
perQuestionBreakdown, dateGraded, published: true, assignmentId
```

### `questionPapers/{autoId}`
```
teacherId, title, subject, year, totalMarks, dueDate, fileUrl, fileName †, fileSize †,
createdAt, submittedAt, type: 'questionPaper',
status: 'pending' | 'approved' | 'rejected',
reviewedBy †, reviewedAt †, rejectionReason †
```

### `pastPapers/{autoId}`  *(legacy panel only — `js/pastpapers.js`, not on live pages)*
```
teacherId, teacherName, school, subject, title, year, term,
memo: boolean, fileUrl, fileName, fileSize, pages (random 5–19), uploadedAt
```

### `chats/{chatId}` + `chats/{chatId}/messages/{autoId}`
```
chats/{chatId}: { participants: [uid, uid|'admin'], createdAt, updatedAt }
messages/{autoId}: {
  text, senderId, senderName, recipientId, timestamp,
  read: boolean, chatType: 'admin' | 'student'
}
```
Chat-id conventions: **`chat_{studentId}_{teacherId}`** (learner threads) vs. **`chat_student_{teacherId}`** (the one fixed admin thread per teacher) — note both prefixes literally start with `chat_student_...`-looking text but resolve to different ids; do not assume you can regex one format from the other.

### `overviewNotifications/{autoId}`, `dashboardAlerts/{autoId}`, `learners/{autoId}`  *(sidebar badge sources only — see §17.3; no writer found anywhere in this bundle)*
```
teacherId, seen|read: boolean, createdAt
```

---

## 16. Firebase Authentication

- **Provider:** Email/Password only (`firebase.auth().signInWithEmailAndPassword` / `createUserWithEmailAndPassword`). No Google/Apple/phone sign-in wired up.
- **Project config** (identical inline block on every page):
  ```js
  {
    apiKey: "AIzaSyBcoZYDDuQKXYJJGlOfnd7WXmFI8Kqz29E",
    authDomain: "studyflix-1c5e5.firebaseapp.com",
    projectId: "studyflix-1c5e5",
    storageBucket: "studyflix-1c5e5.firebasestorage.app",
    messagingSenderId: "973073144601",
    appId: "1:973073144601:web:02f564a8cca112e062c388"
  }
  ```
  (This API key is a public web key by Firebase design — restricting real access is the job of Firestore/Storage **Security Rules**, not this key. See §18.)
- **Session persistence:** `submissions-list.html` and `my-publications.html` explicitly set `auth.setPersistence(firebase.auth.Auth.Persistence.LOCAL)`; other pages rely on the SDK default (also LOCAL) implicitly.
- **Password reset:** `auth.sendPasswordResetEmail(email)` from the login modal.
- **Re-authentication for sensitive ops:** password change re-authenticates with `EmailAuthProvider.credential` before calling `updatePassword`.
- **Authorization layering on top of Auth:** being a valid Firebase Auth user is *necessary but not sufficient* — every page additionally requires a matching `teachers/{uid}` Firestore document with `status` not in `{pending, rejected}`, and (at login time only) a correct school code and non-blocked/non-expired school. Pages differ slightly in how strictly/consistently they re-verify this after the initial login (see §18 for the security-rules implication).
- **Sign-out:** clears `localStorage` entirely and redirects to `index.html` (done identically from the sidebar logout button, `settings.html`'s logout button, and each page's own auth-guard failure paths).

---

## 17. Cloud Functions

Only **one** Cloud Function is called from the teacher portal:

### `generateSchoolCode({ schoolName })`
- Invoked via `firebase.functions().httpsCallable('generateSchoolCode')` during school registration (§2.2).
- Expected to return `{ data: { code: string } }`.
- **The function's own implementation is not part of this bundle** (Cloud Functions source is deployed separately) — only the client call-site is visible here.
- **Client-side fallback exists** if the function call throws (network issue, function not deployed, quota, etc.): a deterministic-looking hash of `schoolName + Date.now() + random` is computed in the browser to synthesize an `SF…` code. This means **school-code uniqueness is not guaranteed** if the real Cloud Function is ever down for an extended period during high signup volume — worth hardening (e.g., a Firestore transaction with a uniqueness check, or simply blocking signup until the function succeeds) before scaling.

No other Cloud Functions are called anywhere in the teacher portal — grading-publish, memo-publish, chat, and all badge counts are done via **plain, unprotected-by-server-logic Firestore writes from the client** (see §18 for why this matters).

### 17.3 Sidebar real-time badge system (client-side "notification engine")

Although not a Cloud Function, this is the closest thing to cross-cutting backend logic in the bundle, so it's documented here. `js/sidebar.js` maintains a `pageKey → lastVisitTimestamp` map in `localStorage` (`lastVisit_{pageKey}`, updated on nav-link click) and, once a teacher is authenticated, opens **9 simultaneous `onSnapshot` listeners**, each comparing each doc's `createdAt` (or similar) against the stored last-visit time to derive an "unseen count":

1. `assignments` where `teacherId==uid && status=='active'` → **Write Homework** badge.
2. `submissions` where `teacherId==uid && isGraded==false` → **Student Submissions** badge. *(Note: `submissions` docs in this bundle do not actually store a `teacherId` field anywhere else — this listener may silently return zero results unless that field is populated by another part of the system.)*
3. `learners` where `teacherId==uid` → **My Learners** badge. *(Also note: this queries a top-level `learners` collection, distinct from `students` — no writer for `learners` exists anywhere in the bundle; likely another cross-module dependency.)*
4. `assignments` where `teacherId==uid && status=='closed' && resultsPublished==false` → **Marks & Results** badge.
5. `publications` where `teacherId==uid && status=='draft'` → **My Publications** badge (collection name `publications`, distinct from `questionPapers`/`assignments`/`quizzes` actually used by `my-publications.html` — likely stale/aspirational).
6. `overviewNotifications` where `teacherId==uid && seen==false` → **Overview** badge.
7. `dashboardAlerts` where `teacherId==uid && read==false` → **Dashboard** badge.
8. `chats/chat_student_{uid}/messages` where `recipientId==uid && read==false` → **Admin Chat** badge; combined with a `collectionGroup('messages')` query across **all** chats for `recipientId==uid && read==false` to derive **Learners Chat** badge as `total − admin`.
9. `teachers/{uid}` snapshot → flags **Settings** badge if `!profileComplete || !emailVerified`.

**This is a lot of always-on realtime listeners (9 per active tab) and several reference collections/fields that no page in this bundle ever writes to.** Treat items 3, 5, 6, 7, and the `teacherId` field expectation in item 2 as either dead/aspirational code or dependencies on another part of the StudyFlix system not included in this upload — verify against the live Firestore schema before relying on these badges being accurate, and definitely before porting this listener set 1:1 into a mobile app (battery/data cost of 9 concurrent snapshot listeners is non-trivial — see §19).

---

## 18. Security Rules Assumptions

**No `firestore.rules` or `storage.rules` file was included in this upload**, so the following are *inferred requirements* based on what the client code assumes will be allowed/denied — they are not confirmed rules. Anyone deploying this app must write and test actual rules; the notes below are a checklist of what those rules need to support:

1. **Any authenticated user can currently query `schools` unfiltered** to populate the login school dropdown in the legacy flow (`schools.where('status','in',[...]).orderBy('name')`) — if resurrected, this needs a rule allowing unauthenticated or any-authenticated read of a *subset* of school fields (name, province, status) without exposing `subscriptionEnds`/financial data broadly.
2. **A teacher must be able to read only their own `teachers/{uid}` doc**, but the settings/login flows also read `schools/{teacher.schoolId}` (their *own* school) — rules should scope this to "the school referenced by the caller's own teacher doc", not open school reads.
3. **A teacher must be able to read `students` scoped to their own `schoolId`/`school` + `grade`** (multiple pages do exactly this query) but should almost certainly **not** be able to read students belonging to other schools — the client never restricts this beyond the query filter it chooses to write, so a malicious client could simply omit the `where('schoolId', ...)` clause unless rules enforce it server-side.
4. **A teacher must be able to read/write `assignments`/`quizzes` they own** (`teacherId == request.auth.uid` on create; owner-only update/delete). Learner-side apps (not in this bundle) presumably need read access to assignments for their own school+grade — rules must allow that without allowing a learner to *write* to `assignments`.
5. **Submissions:** teachers need to read submissions for assignments they own and write `grading`/`isGraded` fields on them; they should not be able to fabricate a submission's `studentId`/answers. This is one of the more sensitive rule surfaces because §8.3's publish step trusts the client-computed `totalScore`/`percentage` completely — a compromised or buggy client could publish incorrect marks with no server-side recomputation/verification.
6. **`studentsMarks/{studentUid}/...` and `allStudentMarks/{studentUid}/...` are written by the *teacher's* client on the *student's* uid path** — rules must allow a teacher to write into another user's subcollection (scoped to submissions for assignments that teacher owns), which is an unusual but intentional cross-user write pattern that needs very precise rule conditions (e.g., validate against the corresponding `submissions` doc) to avoid a teacher (or a compromised teacher session) writing arbitrary marks for arbitrary students.
7. **Chats:** a teacher should be able to read/write only threads where they are a listed `participant`; the fixed `admin` chat-id convention (`chat_student_{teacherUid}`) needs a rule that recognizes the literal string `'admin'` as a valid "other participant" without that being a spoofable value from the client.
8. **`questionPapers`:** teachers can create (status forced to `'pending'` server-side, not client-trusted) and delete their own; only an admin role should be able to transition `status` to `approved`/`rejected` or set `reviewedBy`.
9. **Storage:** uploads to `pastPapers/{teacherId}/...` and `questionPapers/{teacherId}/...` should be restricted so a teacher can only write under their own uid-prefixed path, with a server-enforced file-type/size check (the client only checks `application/pdf` and 50MB client-side in `js/pastpapers.js`, which is trivially bypassable).
10. **Cloud Function `generateSchoolCode`** should itself enforce global uniqueness of the returned code (via a Firestore transaction) — the client cannot verify this, and its own fallback code-generator (§17) has no uniqueness guarantee at all.

---

## 19. Android App Migration Notes

If/when this teacher portal is rebuilt as a native Android app (Kotlin + Firebase Android SDK, or Flutter), the following carry over directly, and the following need explicit re-design:

### 19.1 Carries over cleanly
- **Data model** (§15) — the Firestore collections and document shapes can be reused as-is; the Android Firestore SDK talks to the same backend.
- **Auth flow** (email/password + Firestore-doc-based status/role gate) maps naturally onto `FirebaseAuth` + a repository layer that checks `teachers/{uid}` after sign-in.
- **Grading UX** (§8) — the tick/half/cross-per-question model with a running score is a good fit for a native list/adapter UI; the flattening logic (`answerableItems`) should become a shared Kotlin data class/use-case rather than being reimplemented per screen.
- **Chat** (§11–12) — Firestore `onSnapshot`-style realtime listeners map directly onto Android's `addSnapshotListener`; the two chat-id conventions should be centralized into a single `ChatIdBuilder` utility to avoid the same fragmentation seen across `chat.html`/`learners-chat.html`.

### 19.2 Needs rework, not a straight port

1. **Consolidate the "school" identity fields.** Pick **either** `schoolId` **or** `school` (name) as the single canonical link between `students`/`teachers`/`assignments` and `schools`, and backfill/migrate the other. Porting both inconsistent query patterns (§5.1) into native code would just duplicate the bug across two codebases.
2. **Do not port the 9-listener sidebar badge system verbatim (§17.3).** On mobile, always-on Firestore snapshot listeners consume battery and mobile data even when the app is backgrounded. Replace with: (a) Firebase Cloud Messaging push notifications driven by Cloud Functions (triggered on write to `assignments`/`submissions`/`chats`) for the "you have something new" signal, and (b) on-demand counts fetched (or a single lightweight aggregated `teacherStats/{uid}` doc updated by a Cloud Function trigger) only when the relevant screen is opened. This also fixes the several badge sources (`learners`, `publications`, `overviewNotifications`, `dashboardAlerts`) that have no writer anywhere in the current system — an Android rewrite is the right moment to either wire them up properly server-side or delete them.
3. **Move score computation server-side.** The grading publish step (§8.3, §18.5) currently trusts the client's arithmetic and per-question point-normalization (full=1/half=0.5/wrong=0 regardless of the question's authored `marks` weight, §8.2). For Android, have a **Cloud Function** (triggered on `submissions/{id}` update, or an explicit callable `publishGrade`) recompute and validate the score server-side using the assignment's authored `marks` per question, and write to `studentsMarks`/`allStudentMarks` from trusted server code — this both fixes the weighting bug and closes the "malicious client can publish any score" security gap.
4. **Replace the fake AI-corrections/memo `setTimeout` (§8.4) with a real Cloud Function** (e.g., calling an LLM API) if that feature is meant to ship. As-is there is nothing to port except the UI shape.
5. **Rebuild subscription/paywall as a real IAP or server-verified flow.** A static PayPal deep-link (§14) doesn't translate to Android (Play Store billing policies generally require Google Play Billing for digital goods/subscriptions inside an Android app, or a compliant external-payment flow); this needs a genuine payment-gateway + Cloud Function reconciliation (updating `schools.subscription`/`subscriptionEnds` on confirmed payment) rather than the current honor-system/manual-Firestore-edit model. Also implement the missing "Upgrade Plan" destination (`upgrade.html` doesn't exist in the web bundle either — §14).
6. **Route all Storage uploads through Cloud Functions or enforce real Storage Security Rules for file-type/size** (§18.9) — a native app should not rely on the client-side `application/pdf` + 50MB checks currently done in JS, which have no server enforcement today.
7. **Drop the legacy panel-based architecture entirely (§1.1, Architecture B).** Do not use `js/auth.js`, `js/dashboard.js`, `js/assignment.js`, `js/corrections.js`, `js/learners.js`, `js/marks.js`, `js/navigation.js`, `js/pastpapers.js`, or `teacher-simple.html` as a spec for Android — they represent an earlier, materially less secure prototype (no school-code check at login, auto-creates teacher records, demo-data fallbacks) superseded by the live multi-page flow.
8. **Single source of truth for Firebase config & shared constants.** The web bundle repeats the same Firebase config object and CSS design tokens on every page; an Android app naturally centralizes this (one `google-services.json` + one theme), but the equivalent discipline (a shared repository/DI layer for Firestore access, rather than ad-hoc queries copy-pasted per screen as seen throughout this HTML bundle) should be enforced in the new codebase from day one.
9. **Offline-first considerations.** South African school connectivity can be patchy; the current web app has no offline handling at all (every action is a live Firestore call with a toast on failure). Android's Firestore SDK supports offline persistence/caching out of the box — enable it and design the grading/publish flow (§8.3) to queue safely if a teacher is grading marks with an intermittent connection, rather than silently failing mid-way through the 4-step publish sequence (submission → studentsMarks → allStudentMarks).
10. **Pagination.** Several screens (`my-learners.html`'s submissions listener, `marks-results.html`'s per-assignment submission fetch loop, the sidebar's collectionGroup query) fetch **entire collections/subcollections with no `limit()`** — fine for a pilot-scale dataset, but will not scale on mobile data/battery budgets as the school/learner count grows. Add pagination (`limit()` + `startAfter()`) from the outset in the Android rewrite.

---

## 20. Future Improvements

Beyond the Android-specific notes above, opportunities identified purely from the current web codebase:

1. **Delete or resurrect the legacy code deliberately.** Right now `js/auth.js`, `js/dashboard.js`, `js/assignment.js`, `js/corrections.js`, `js/learners.js`, `js/marks.js`, `js/navigation.js`, `js/pastpapers.js`, `teacher-simple.html`, and the empty `js/teacher.js` sit in the deployed bundle unreferenced by any live page — this is ~2,300 lines of dead weight shipped to every visitor's network tab and a maintenance/security-audit trap (a future developer could easily assume `teacher-simple.html` is live and "fix" the wrong file).
2. **Reconcile the `school` vs `schoolId` field split** (§5.1, §19.2.1) across `students`, `teachers`, and query call-sites — this is the single highest-risk latent bug in the current system (silently empty rosters/chats for any learner or teacher whose account only has one of the two fields populated).
3. **Fix per-question mark weighting in grading** (§8.2) — ticks/halves/crosses currently ignore the assignment's authored `marks` per question; a 1-mark question and a 10-mark question both currently contribute equally (0/0.5/1) to the published score.
4. **Move score computation and school-code generation server-side** (§8.3, §17) for integrity — see Android migration notes §19.2.3 for the concrete recommendation; this applies equally to the current web app.
5. **Reduce Firestore read amplification.** `my-learners.html` subscribes to *every* `submissions` document in the entire database and filters client-side (§5.2); `marks-results.html` and `overview.html` loop over every owned assignment issuing one `submissions` query per assignment rather than a single batched/`in`-based query; several pages independently re-poll "online now" counts every 30s. These patterns will get materially more expensive (in Firestore billing and client CPU) as the learner base grows and should be replaced with properly scoped queries, `limit()`, and/or Cloud-Function-maintained aggregate documents.
6. **Wire up or remove the sidebar badge collections that nothing writes to** (`learners`, `publications`, `overviewNotifications`, `dashboardAlerts`, and the `teacherId` field on `submissions`) — see §17.3.
7. **Implement the missing `upgrade.html` destination** or repoint the "Upgrade Plan" button, and build a real payment-reconciliation path instead of the manual/PayPal-link honor system (§14).
8. **Standardize security around admin-vs-teacher role.** The `role: 'admin'` flag set on the founding teacher of a school, and the separate `users/{uid}.role` check in `settings.html`, are two different, seemingly-unreconciled notions of "who can do admin things" — this should be unified into one authoritative role/claims source (ideally Firebase custom claims validated server-side) before any admin-capable feature is added to the teacher portal itself.
9. **Real AI corrections/memo generation** (§8.4, §6.4) to replace the `setTimeout`-simulated demo, if that remains a desired product feature — likely a Cloud Function calling an LLM with the assignment/memo/learner-answer context, with human (teacher) review-and-release retained as the final gate (the "release to class" toggle concept already in the legacy UI is worth keeping).
10. **De-duplicate CSS.** Nearly every page re-declares the same `:root` design tokens and component classes (`.card`, `.toast`, `.btn-primary`, footer styles, etc.) in its own `<style>` block instead of consistently using the shared `css/teacher.css` — consolidating this would shrink page weight and reduce the risk of visual drift between pages (a few small palette differences between `dashboard.html`'s inline tokens and `teacher.css`'s tokens were observed, e.g. duplicate but not always identical `--card`/`--red` hex values).
