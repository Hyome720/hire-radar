INSERT INTO recruitment_company (name, career_url, scraping_type, check_interval_minutes, is_active)
SELECT '한화생명', 'https://www.hanwhalife.com', 'STATIC', 60, 1
WHERE NOT EXISTS (SELECT 1 FROM recruitment_company WHERE name = '한화생명');

INSERT INTO recruitment_company (name, career_url, scraping_type, check_interval_minutes, is_active)
SELECT 'KB데이타시스템', 'https://www.kds.co.kr', 'STATIC', 60, 1
WHERE NOT EXISTS (SELECT 1 FROM recruitment_company WHERE name = 'KB데이타시스템');

INSERT INTO recruitment_company (name, career_url, scraping_type, check_interval_minutes, is_active)
SELECT '신한DS', 'https://www.shinhands.co.kr', 'STATIC', 60, 1
WHERE NOT EXISTS (SELECT 1 FROM recruitment_company WHERE name = '신한DS');

INSERT INTO recruitment_company (name, career_url, scraping_type, check_interval_minutes, is_active)
SELECT '메리츠화재', 'https://www.meritzfire.com', 'STATIC', 60, 1
WHERE NOT EXISTS (SELECT 1 FROM recruitment_company WHERE name = '메리츠화재');

INSERT INTO recruitment_company (name, career_url, scraping_type, check_interval_minutes, is_active)
SELECT 'DB손해보험', 'https://www.idbins.com', 'STATIC', 60, 1
WHERE NOT EXISTS (SELECT 1 FROM recruitment_company WHERE name = 'DB손해보험');

INSERT INTO recruitment_company (name, career_url, scraping_type, check_interval_minutes, is_active)
SELECT 'DB Inc.', 'https://www.dbinc.co.kr', 'STATIC', 60, 1
WHERE NOT EXISTS (SELECT 1 FROM recruitment_company WHERE name = 'DB Inc.');

INSERT INTO recruitment_company (name, career_url, scraping_type, check_interval_minutes, is_active)
SELECT '현대해상', 'https://www.hi.co.kr', 'STATIC', 60, 1
WHERE NOT EXISTS (SELECT 1 FROM recruitment_company WHERE name = '현대해상');

INSERT INTO recruitment_company (name, career_url, scraping_type, check_interval_minutes, is_active)
SELECT 'KB손해보험', 'https://www.kbinsure.co.kr', 'STATIC', 60, 1
WHERE NOT EXISTS (SELECT 1 FROM recruitment_company WHERE name = 'KB손해보험');

INSERT INTO recruitment_company (name, career_url, scraping_type, check_interval_minutes, is_active)
SELECT '삼성화재', 'https://www.samsungfire.com', 'STATIC', 60, 1
WHERE NOT EXISTS (SELECT 1 FROM recruitment_company WHERE name = '삼성화재');

INSERT INTO recruitment_company (name, career_url, scraping_type, check_interval_minutes, is_active)
SELECT '삼성생명', 'https://www.samsunglife.com', 'STATIC', 60, 1
WHERE NOT EXISTS (SELECT 1 FROM recruitment_company WHERE name = '삼성생명');
