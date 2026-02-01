DROP TABLE IF EXISTS software;
DROP TABLE IF EXISTS accessory;
DROP TABLE IF EXISTS audio_equipment;
DROP TABLE IF EXISTS vinyl;
DROP TABLE IF EXISTS instrument;
DROP TABLE IF EXISTS category;

CREATE TABLE category (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL UNIQUE,
                          description TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT now(),
                          updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE instrument (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(150) NOT NULL,
                            price NUMERIC(10, 2) NOT NULL CHECK (price > 0),
                            stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
                            manufacturer VARCHAR(100) NOT NULL,
                            instrument_type VARCHAR(50) NOT NULL,
                            model VARCHAR(100) NOT NULL,
                            description TEXT,
                            sound_demo TEXT,
                            category_id INT NOT NULL REFERENCES category(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                            created_at TIMESTAMP DEFAULT now(),
                            updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE vinyl (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(150) NOT NULL,
                       price NUMERIC(10, 2) NOT NULL CHECK (price > 0),
                       stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
                       manufacturer VARCHAR(100) NOT NULL,
                       artist VARCHAR(100) NOT NULL,
                       genre VARCHAR(50) NOT NULL,
                       release_year INT NOT NULL CHECK (release_year >= 1900 AND release_year <= 2026),
                       speed_rpm INT NOT NULL CHECK (speed_rpm IN (33, 45, 78)),
                       category_id INT NOT NULL REFERENCES category(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                       created_at TIMESTAMP DEFAULT now(),
                       updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE audio_equipment (
                                 id SERIAL PRIMARY KEY,
                                 name VARCHAR(150) NOT NULL,
                                 price NUMERIC(10, 2) NOT NULL CHECK (price > 0),
                                 stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
                                 manufacturer VARCHAR(100) NOT NULL,
                                 brand VARCHAR(100) NOT NULL,
                                 model VARCHAR(100) NOT NULL,
                                 description TEXT,
                                 category_id INT NOT NULL REFERENCES category(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                                 created_at TIMESTAMP DEFAULT now(),
                                 updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE accessory (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(150) NOT NULL,
                           price NUMERIC(10, 2) NOT NULL CHECK (price > 0),
                           stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
                           manufacturer VARCHAR(100) NOT NULL,
                           accessory_type VARCHAR(100) NOT NULL,
                           description TEXT,
                           category_id INT NOT NULL REFERENCES category(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                           created_at TIMESTAMP DEFAULT now(),
                           updated_at TIMESTAMP DEFAULT now()
);

CREATE TABLE software (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(150) NOT NULL,
                          price NUMERIC(10, 2) NOT NULL CHECK (price > 0),
                          stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
                          manufacturer VARCHAR(100) NOT NULL,
                          developer VARCHAR(100) NOT NULL,
                          version VARCHAR(50) NOT NULL,
                          description TEXT,
                          category_id INT NOT NULL REFERENCES category(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                          created_at TIMESTAMP DEFAULT now(),
                          updated_at TIMESTAMP DEFAULT now()
);

INSERT INTO category (name, description) VALUES
                                             ('String Instruments', 'Guitars, violins, cellos, and other stringed instruments'),
                                             ('Wind Instruments', 'Flutes, saxophones, clarinets, and other wind instruments'),
                                             ('Percussion', 'Drums, cymbals, xylophones, and other percussion instruments'),
                                             ('Keyboard Instruments', 'Pianos, synthesizers, and electronic keyboards'),
                                             ('Rock Vinyls', 'Classic and modern rock music vinyl records'),
                                             ('Jazz Vinyls', 'Jazz music vinyl records from various eras'),
                                             ('Classical Vinyls', 'Classical music vinyl records'),
                                             ('Audio Interfaces', 'Recording interfaces and sound cards'),
                                             ('Speakers & Monitors', 'Studio monitors and speaker systems'),
                                             ('Headphones', 'Professional and consumer headphones'),
                                             ('Cables & Connectors', 'Audio cables, adapters, and connectors'),
                                             ('Guitar Accessories', 'Picks, straps, cases, and other guitar accessories'),
                                             ('Drum Accessories', 'Drumsticks, cases, and drum maintenance items'),
                                             ('DAW Software', 'Digital Audio Workstation software'),
                                             ('Plugins & Effects', 'Audio plugins and effect processors'),
                                             ('Virtual Instruments', 'Software-based instrument libraries');

INSERT INTO instrument (name, price, stock_quantity, manufacturer, instrument_type, model, description, sound_demo, category_id) VALUES
                                                                                                                                     ('Fender Stratocaster', 1299.99, 15, 'Fender', 'Electric Guitar', 'American Professional II', 'Classic electric guitar with versatile tones', 'https://example.com/demos/strat.mp3', 1),
                                                                                                                                     ('Gibson Les Paul', 2499.99, 8, 'Gibson', 'Electric Guitar', 'Standard 60s', 'Iconic rock guitar with powerful humbuckers', 'https://example.com/demos/lespaul.mp3', 1),
                                                                                                                                     ('Yamaha C40', 149.99, 25, 'Yamaha', 'Classical Guitar', 'C40', 'Student-friendly classical guitar with nylon strings', NULL, 1),
                                                                                                                                     ('Yamaha YAS-280', 1899.99, 5, 'Yamaha', 'Alto Saxophone', 'YAS-280', 'Entry-level alto saxophone with excellent tone', 'https://example.com/demos/sax.mp3', 2),
                                                                                                                                     ('Pearl Export Series', 699.99, 10, 'Pearl', 'Drum Kit', 'EXX725S/C', 'Complete 5-piece drum kit for rock and pop', NULL, 3),
                                                                                                                                     ('Zildjian A Custom Crash', 349.99, 12, 'Zildjian', 'Cymbal', 'A Custom 18"', 'Bright and cutting crash cymbal', NULL, 3),
                                                                                                                                     ('Bach Stradivarius', 3299.99, 3, 'Bach', 'Trumpet', '180S37', 'Professional trumpet with exceptional clarity', 'https://example.com/demos/trumpet.mp3', 2),
                                                                                                                                     ('Roland RD-88', 1599.99, 7, 'Roland', 'Stage Piano', 'RD-88', '88-key stage piano with authentic piano sounds', NULL, 4),
                                                                                                                                     ('Korg SV-2', 2199.99, 4, 'Korg', 'Stage Piano', 'SV-2 73', 'Vintage-style stage piano with premium sounds', NULL, 4);

INSERT INTO vinyl (name, price, stock_quantity, manufacturer, artist, genre, release_year, speed_rpm, category_id) VALUES
                                                                                                                       ('Abbey Road', 29.99, 50, 'Apple Records', 'The Beatles', 'Rock', 1969, 33, 5),
                                                                                                                       ('Dark Side of the Moon', 34.99, 45, 'Harvest Records', 'Pink Floyd', 'Progressive Rock', 1973, 33, 5),
                                                                                                                       ('Rumours', 27.99, 38, 'Warner Bros', 'Fleetwood Mac', 'Rock', 1977, 33, 5),
                                                                                                                       ('Led Zeppelin IV', 32.99, 42, 'Atlantic Records', 'Led Zeppelin', 'Hard Rock', 1971, 33, 5),
                                                                                                                       ('Kind of Blue', 31.99, 30, 'Columbia', 'Miles Davis', 'Jazz', 1959, 33, 6),
                                                                                                                       ('A Love Supreme', 29.99, 22, 'Impulse!', 'John Coltrane', 'Jazz', 1965, 33, 6),
                                                                                                                       ('Time Out', 28.99, 18, 'Columbia', 'Dave Brubeck', 'Jazz', 1959, 33, 6),
                                                                                                                       ('The Four Seasons', 24.99, 15, 'Deutsche Grammophon', 'Vivaldi', 'Classical', 1975, 33, 7),
                                                                                                                       ('Symphony No. 9', 32.99, 12, 'Deutsche Grammophon', 'Beethoven', 'Classical', 1978, 33, 7),
                                                                                                                       ('Thriller', 29.99, 60, 'Epic Records', 'Michael Jackson', 'Pop', 1982, 33, 5);

INSERT INTO audio_equipment (name, price, stock_quantity, manufacturer, brand, model, description, category_id) VALUES
                                                                                                                    ('Focusrite Scarlett 2i2', 159.99, 35, 'Focusrite', 'Scarlett', '2i2 3rd Gen', 'Popular 2-in/2-out USB audio interface for home recording', 8),
                                                                                                                    ('Universal Audio Apollo Twin X', 899.99, 12, 'Universal Audio', 'Apollo', 'Twin X Duo', 'Professional audio interface with real-time UAD processing', 8),
                                                                                                                    ('PreSonus AudioBox USB 96', 99.99, 40, 'PreSonus', 'AudioBox', 'USB 96', 'Affordable 2x2 USB audio interface for beginners', 8),
                                                                                                                    ('Yamaha HS5', 199.99, 28, 'Yamaha', 'HS Series', 'HS5', 'Compact studio monitor with accurate sound reproduction', 9),
                                                                                                                    ('KRK Rokit 5 G4', 179.99, 30, 'KRK', 'Rokit', 'RP5 G4', 'Popular studio monitor with powerful bass response', 9),
                                                                                                                    ('JBL 305P MkII', 149.99, 25, 'JBL', 'Professional', '305P MkII', 'Affordable studio monitor with JBL quality', 9),
                                                                                                                    ('Audio-Technica ATH-M50x', 149.99, 50, 'Audio-Technica', 'ATH', 'M50x', 'Industry-standard closed-back studio headphones', 10),
                                                                                                                    ('Beyerdynamic DT 770 PRO', 159.99, 35, 'Beyerdynamic', 'DT Series', '770 PRO 80 Ohm', 'Professional closed-back headphones for mixing', 10),
                                                                                                                    ('Sennheiser HD 650', 499.99, 20, 'Sennheiser', 'HD', '650', 'Premium open-back headphones for critical listening', 10);

INSERT INTO accessory (name, price, stock_quantity, manufacturer, accessory_type, description, category_id) VALUES
                                                                                                                ('Mogami Gold Studio Cable', 29.99, 100, 'Mogami', 'Audio Cable', '10ft XLR microphone cable with gold-plated connectors', 11),
                                                                                                                ('Hosa TRS Patch Cables', 19.99, 80, 'Hosa', 'Audio Cable', '6-pack of 6-inch TRS patch cables', 11),
                                                                                                                ('Monster Cable Instrument Cable', 39.99, 60, 'Monster', 'Instrument Cable', '21ft right-angle instrument cable', 11),
                                                                                                                ('Dunlop Tortex Picks', 4.99, 200, 'Dunlop', 'Guitar Pick', 'Pack of 12 Tortex guitar picks, 0.73mm', 12),
                                                                                                                ('Ernie Ball Guitar Strap', 14.99, 75, 'Ernie Ball', 'Guitar Strap', 'Adjustable polypro guitar strap', 12),
                                                                                                                ('D''Addario String Winder', 9.99, 90, 'D''Addario', 'Maintenance Tool', 'Multi-tool for guitar string changes', 12),
                                                                                                                ('Gator Hard Guitar Case', 149.99, 25, 'Gator', 'Guitar Case', 'Molded hard-shell electric guitar case', 12),
                                                                                                                ('Vic Firth 5A Drumsticks', 12.99, 150, 'Vic Firth', 'Drumsticks', 'Classic 5A hickory drumsticks', 13),
                                                                                                                ('Ahead Armor Stick Bag', 24.99, 40, 'Ahead', 'Stick Bag', 'Durable drumstick carry bag', 13),
                                                                                                                ('Evans Drum Head Cleaning Fluid', 8.99, 65, 'Evans', 'Maintenance', 'Drum head cleaner and conditioner', 13);

INSERT INTO software (name, price, stock_quantity, manufacturer, developer, version, description, category_id) VALUES
                                                                                                                   ('Ableton Live 11 Suite', 749.00, 100, 'Ableton', 'Ableton AG', '11.3.4', 'Professional DAW for music production and live performance', 14),
                                                                                                                   ('FL Studio Producer Edition', 199.00, 150, 'Image-Line', 'Image-Line Software', '21.0.3', 'Popular DAW with pattern-based workflow', 14),
                                                                                                                   ('Logic Pro X', 199.99, 200, 'Apple', 'Apple Inc.', '10.7.5', 'Professional DAW for Mac with extensive sound library', 14),
                                                                                                                   ('Pro Tools Studio', 599.00, 80, 'Avid', 'Avid Technology', '2023.6', 'Industry-standard DAW for recording and mixing', 14),
                                                                                                                   ('Waves Gold Bundle', 299.00, 120, 'Waves', 'Waves Audio Ltd.', '14.0', 'Essential audio plugins for mixing and mastering', 15),
                                                                                                                   ('FabFilter Pro-Q 3', 179.00, 95, 'FabFilter', 'FabFilter', '3.21', 'Professional EQ plugin with spectrum analyzer', 15),
                                                                                                                   ('Valhalla VintageVerb', 50.00, 180, 'Valhalla DSP', 'Valhalla DSP', '3.0.1', 'Lush reverb plugin inspired by classic hardware', 15),
                                                                                                                   ('Native Instruments Komplete 14', 599.00, 70, 'Native Instruments', 'Native Instruments', '14.1', 'Comprehensive virtual instrument and effects bundle', 16),
                                                                                                                   ('Spectrasonics Omnisphere 2', 499.00, 60, 'Spectrasonics', 'Spectrasonics', '2.8', 'Flagship synthesizer with massive sound library', 16),
                                                                                                                   ('Arturia V Collection 9', 599.00, 55, 'Arturia', 'Arturia', '9.2', 'Collection of vintage synthesizer and keyboard emulations', 16);