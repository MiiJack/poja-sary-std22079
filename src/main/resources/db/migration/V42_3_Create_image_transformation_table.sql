CREATE TABLE if not exists image_transformation (
    id text PRIMARY KEY,
    transformation_status VARCHAR(255) NOT NULL,
    transformation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);