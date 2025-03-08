-- Check if the database exists, and create it if it doesn't
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'ride-sharing') THEN
        CREATE DATABASE "ride-sharing";
    END IF;
END $$;