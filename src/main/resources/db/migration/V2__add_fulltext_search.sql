-- Add a tsvector column for full-text search
ALTER TABLE item ADD COLUMN IF NOT EXISTS search_vector tsvector;

-- Populate the column for existing rows
UPDATE item SET search_vector = to_tsvector('english', coalesce(title, '') || ' ' || coalesce(description, ''));

-- Create a GIN index for fast search
CREATE INDEX IF NOT EXISTS idx_item_search ON item USING GIN (search_vector);

-- Trigger to keep the column up-to-date
CREATE OR REPLACE FUNCTION item_tsvector_update() RETURNS trigger AS $$
BEGIN
  NEW.search_vector := to_tsvector('english', coalesce(NEW.title, '') || ' ' || coalesce(NEW.description, ''));
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tsvector_update ON item;
CREATE TRIGGER tsvector_update BEFORE INSERT OR UPDATE ON item
FOR EACH ROW EXECUTE FUNCTION item_tsvector_update();
