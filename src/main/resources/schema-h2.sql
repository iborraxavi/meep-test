CREATE TABLE IF NOT EXISTS RESOURCES (
    id INT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id VARCHAR(20)
    name VARCHAR(20),
    x DOUBLE,
    y DOUBLE,
    licence_plate VARCHAR(10),
    range INT,
    battery_level INT,
    helmets INT,
    model VARCHAR(40),
    resource_image_id VARCHAR(50),
    real_time_data BOOLEAN,
    resource_type VARCHAR(20),
    company_zone_id INT,
    leaving_date BIGINT
 );