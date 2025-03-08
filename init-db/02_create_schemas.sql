-- Connect to the ride-sharing database and create schemas if they don't exist
\c "ride-sharing";

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = 'drivers') THEN
        CREATE SCHEMA drivers;
    END IF;

    IF NOT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = 'auth') THEN
        CREATE SCHEMA auth;
    END IF;

    IF NOT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = 'ride_management') THEN
        CREATE SCHEMA ride_management;
    END IF;
END $$;

-- Grant full privileges to the postgres user
DO $$
BEGIN
    GRANT ALL PRIVILEGES ON SCHEMA drivers TO postgres;
    GRANT ALL PRIVILEGES ON SCHEMA auth TO postgres;
    GRANT ALL PRIVILEGES ON SCHEMA ride_management TO postgres;
END $$;