-- Drop the listing_image table if it exists
DROP TABLE IF EXISTS listing_image;

-- Drop the applicant table if it exists
DROP TABLE IF EXISTS applicant;

-- Drop the listing table if it exists
DROP TABLE IF EXISTS listing;

-- Drop the landlord table if it exists
DROP TABLE IF EXISTS landlord;

-- Create the database
CREATE DATABASE IF NOT EXISTS CoastalCasaFinder;

-- Use the database
USE CoastalCasaFinder;

-- Create the landlord table
CREATE TABLE IF NOT EXISTS landlord (
  email VARCHAR(255) PRIMARY KEY,
  password VARCHAR(255) NOT NULL
);

-- Create the listing table
CREATE TABLE IF NOT EXISTS listing (
  id INT PRIMARY KEY AUTO_INCREMENT,
  landlord_email VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  location VARCHAR(255) NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  num_bathrooms INT NOT NULL,
  num_bedrooms INT NOT NULL,
  amenities TEXT NOT NULL,
  FOREIGN KEY (landlord_email) REFERENCES landlord(email)
);

-- Create the applicant table
CREATE TABLE IF NOT EXISTS applicant (
  id INT PRIMARY KEY AUTO_INCREMENT,
  listing_id INT NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  message TEXT NOT NULL,
  move_in_date_range VARCHAR(255) NOT NULL,
  FOREIGN KEY (listing_id) REFERENCES listing(id)
);

-- Create the listing_image table
CREATE TABLE IF NOT EXISTS listing_image (
  id INT PRIMARY KEY AUTO_INCREMENT,
  listing_id INT NOT NULL,
  image_data BLOB NOT NULL,
  FOREIGN KEY (listing_id) REFERENCES listing(id)
);