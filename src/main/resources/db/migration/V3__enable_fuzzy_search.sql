-- Enable the pg_trgm extension for fuzzy matching
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- Create trigram indexes for fast fuzzy search on title and description
CREATE INDEX IF NOT EXISTS idx_item_title_trgm ON item USING gin (title gin_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_item_description_trgm ON item USING gin (description gin_trgm_ops);
