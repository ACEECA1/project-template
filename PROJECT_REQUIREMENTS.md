# Library Application - Project Requirements & Context

## 1. Overview
A secure, offline-first, internal digital library application built with Spring Boot and MySQL. The system allows users to browse, search, and read PDF books directly in their browser. It features a robust dynamic permission system, traceability, and real-time interactive elements like comments and notifications. 

**Crucial Constraint:** The application operates in a strict Intranet (offline) environment with **no internet connection**. We are focusing purely on building a highly optimized, frontend-friendly **Backend REST API**.

---

## 2. Core Entities & Architecture

### 2.1 User & Access Management
*   **Authentication (JWT-Based):** The application will use JWT access tokens and refresh tokens. While immediate invalidation across all clients is harder with stateless JWTs, the application will rely on short-lived access tokens and immediate revocation of refresh tokens in the database when a user is banned or timed out.
*   **Registration:** Users register with First Name, Last Name, Date of Birth, Username, and Password.
*   **Approval Workflow:** Newly registered users cannot log in immediately. They enter a "Pending" state and must be approved by a user holding the `USER_APPROVAL` permission (e.g., Admin or Moderator).
*   **Dynamic Permissions:**
    *   **Permissions:** Granular actions (e.g., `UPLOAD_BOOK`, `APPROVE_BOOK`, `MODERATE_COMMENTS`, `BAN_USER`, `USER_APPROVAL`).
    *   **Roles:** Collections of permissions created dynamically by the Admin.
    *   **Assignment:** Admins assign Roles to Users. 
    *   **Bulk Actions:** Admins can select multiple users at once to assign roles or approve registrations.
*   **Sanctions:** Moderators can Ban or Time-out users, immediately revoking refresh tokens to prevent new sessions.

### 2.2 Book Management & Storage
*   **Storage:** PDFs are stored locally on the server file system in a restricted directory.
*   **Metadata:** Books are associated with **Categories**, **Tags**, **Authors**, and **Series** (e.g., "Volume 1 of 3").
*   **Thumbnail Generation:** The system automatically extracts the first page of the PDF as a thumbnail using Apache PDFBox if no cover is provided.
*   **Upload Workflow:** 
    *   If an Admin uploads a book -> Goes LIVE immediately.
    *   If a user with `UPLOAD_BOOK` permission uploads -> Goes into PENDING status and triggers a notification for Admins to approve it.
*   **Security & Traceability:**
    *   **Virus Scanning:** Local virus scanning validation before saving.
    *   **Streaming:** PDFs are never served as static links. They are streamed byte-by-byte through an authenticated REST endpoint.

### 2.3 Interactive Features
*   **Search Engine:** Uses TF-IDF / inverted index algorithms (via Hibernate Search + Apache Lucene) for fast, full-text and metadata searching.
*   **Progress Tracking:** The system remembers the last page a user read.
*   **Commenting System:** Threaded comments (replies) on books. 
    *   **Upvotes/Downvotes:** Users can rate comments to bubble up the best reviews.
    *   **Draft Mode:** Users can auto-save comment drafts to the backend to prevent data loss.
*   **Moderation:** Users can report toxic comments or corrupted books. Moderators/Admins can delete comments.
*   **Real-Time Notifications:** Delivered via WebSockets / SSE (e.g., "Registration pending", "Someone replied").
*   **Related Books:** Algorithm to suggest books based on overlapping categories/tags/authors.

### 2.4 Analytics, Traceability & Initialization
*   **Data Seeder:** The database will be automatically seeded on startup using Spring Boot Java Configuration (`CommandLineRunner`) to generate the root Admin account and default permissions.
*   **View Statistics:** Tracks views per book and aggregates data for Admins.
*   **Comprehensive Audit Log:** A system-wide ledger that tracks all sensitive actions with timestamps.

---

## 3. Developer Experience (Frontend Support)
To ensure the frontend developer can easily integrate with this backend, we will implement:
*   **Standardized Responses:** Every API endpoint will return a consistent `ApiResponse<T>` wrapper (e.g., `{ success: true, data: {...}, message: "" }`).
*   **Global Exception Handling:** Clean, user-friendly error messages with proper HTTP status codes.
*   **Pagination & Sorting:** All list endpoints (searching books, fetching comments, user lists) will be paginated by default.

---

## 4. Technology Stack
*   **Backend:** Java 17/21 + Spring Boot 3.x
*   **Database:** MySQL (`library_db`)
*   **Search Engine:** Hibernate Search (Lucene)
*   **Security:** Spring Security (JWT-based)
*   **File Processing:** Apache PDFBox (Thumbnail extraction)
*   **Real-time:** Spring WebSockets (STOMP)
