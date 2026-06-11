CREATE TABLE IF NOT EXISTS recruitment_company (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    career_url TEXT NOT NULL,
    scraping_type TEXT NOT NULL,
    check_interval_minutes INTEGER DEFAULT 60,
    is_active INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS recruitment_posting (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    company_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    status TEXT NOT NULL,
    posted_date TEXT,
    deadline TEXT,
    url TEXT NOT NULL UNIQUE,
    description TEXT,
    created_at TEXT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES recruitment_company(id)
);

CREATE TABLE IF NOT EXISTS notification_history(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    posting_id INTEGER NOT NULL,
    sent_at TEXT NOT NULL,
    FOREIGN KEY (posting_id) REFERENCES recruitment_posting(id)
);