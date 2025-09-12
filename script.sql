-- Création des types ENUM
CREATE TYPE conversation_status AS ENUM ('open', 'closed', 'pending');
CREATE TYPE conversation_type AS ENUM ('chat', 'video', 'message');
CREATE TYPE booking_status AS ENUM ('pending', 'confirmed', 'cancelled', 'completed');
CREATE TYPE payment_status AS ENUM ('pending', 'paid', 'failed', 'refunded');
CREATE TYPE payment_type AS ENUM ('credit_card', 'paypal', 'bank_transfer');

-- Table User
CREATE TABLE "User" (
    id SERIAL PRIMARY KEY,
    email VARCHAR(155) NOT NULL UNIQUE,
    password VARCHAR(150) NOT NULL,
    first_name VARCHAR(70) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    birthday DATE
);

-- Table Conversation
CREATE TABLE Conversation (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    sending_date TIMESTAMP NOT NULL DEFAULT NOW(),
    type conversation_type NOT NULL,
    status conversation_status NOT NULL,
    user_id INT NOT NULL REFERENCES "User"(id) ON DELETE CASCADE
);

-- Table Agency
CREATE TABLE Agency (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- Table Vehicle
CREATE TABLE Vehicle (
    id SERIAL PRIMARY KEY,
    acriss_code VARCHAR(50),
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL
);

-- Table Offer
CREATE TABLE Offer (
    id SERIAL PRIMARY KEY,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    vehicle_id INT NOT NULL REFERENCES Vehicle(id) ON DELETE CASCADE,
    agency_id INT NOT NULL REFERENCES Agency(id) ON DELETE CASCADE
);

-- Table Payment
CREATE TABLE Payment (
    id SERIAL PRIMARY KEY,
    amount NUMERIC(10, 2) NOT NULL,
    status payment_status NOT NULL,
    payment_date TIMESTAMP NOT NULL DEFAULT NOW(),
    payment_type payment_type NOT NULL
);

-- Table Booking
CREATE TABLE Booking (
    id SERIAL PRIMARY KEY,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    status booking_status NOT NULL,
    user_id INT NOT NULL REFERENCES "User"(id) ON DELETE CASCADE,
    payment_id INT REFERENCES Payment(id) ON DELETE SET NULL,
    offer_id INT NOT NULL REFERENCES Offer(id) ON DELETE CASCADE
);

-- Ajout relation Payment -> Booking (1-1)
ALTER TABLE Payment
ADD COLUMN booking_id INT UNIQUE REFERENCES Booking(id) ON DELETE CASCADE;
