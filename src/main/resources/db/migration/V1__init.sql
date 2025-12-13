CREATE TABLE IF NOT EXISTS item (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    category VARCHAR(255),
    status VARCHAR(255),
    type VARCHAR(255),
    location VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    date DATE,
    user_id VARCHAR(255),
    claimed_by VARCHAR(255),
    claimed_at TIMESTAMP WITHOUT TIME ZONE,
    contact_info VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS item_image_urls (
    item_id BIGINT NOT NULL,
    image_urls VARCHAR(255)
);
