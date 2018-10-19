--
-- This SQL script builds a monopoly database, deleting any pre-existing version.
--
-- @author kvlinden
-- @version Summer, 2015
--

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys.
DROP TABLE IF EXISTS PlayerGame;
DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS Player;
DROP TABLE IF EXISTS Property;
DROP TABLE IF EXISTS PlayerState;

-- Create the schema.
CREATE TABLE Game (
	ID integer PRIMARY KEY, 
	time timestamp
	);

CREATE TABLE Player (
	ID integer PRIMARY KEY, 
	emailAddress varchar(50) NOT NULL,
	name varchar(50)
	);

CREATE TABLE PlayerGame (
	gameID integer REFERENCES Game(ID), 
	playerID integer REFERENCES Player(ID),
	score integer
	);

-- boardPosition: number between 1 and 40 - 40 spaces in monopoly
CREATE TABLE PlayerState (
        gameID integer REFERENCES Game(ID),
	playerID integer REFERENCES Player(ID),
	cash integer DEFAULT 0,
	boardPosition integer DEFAULT 1 CHECK (boardPosition BETWEEN 1 AND 40)
        );

-- property: number between 1 and 28 - 28 properties in monopoly
-- housing: number between 0 and 5
--		 1 = a house
--		 ...
--		 5 = hotel
CREATE TABLE PropertyState (
        gameID integer REFERENCES Game(ID),
	playerID integer REFERENCES Player(ID),
	property integer NOT NULL CHECK (property BETWEEN 1 AND 28),
	housing integer DEFAULT 0 CHECK (housing BETWEEN 0 AND 5)
        );

-- Allow users to select data from the tables.
GRANT SELECT ON Game TO PUBLIC;
GRANT SELECT ON Player TO PUBLIC;
GRANT SELECT ON PlayerGame TO PUBLIC;
GRANT SELECT ON PlayerState TO PUBLIC;
GRANT SELECT ON PropertyState TO PUBLIC;

-- Add sample records.
INSERT INTO Game VALUES (1, '2006-06-27 08:00:00');
INSERT INTO Game VALUES (2, '2006-06-28 13:20:00');
INSERT INTO Game VALUES (3, '2006-06-29 18:41:00');
INSERT INTO Game VALUES (4, '2006-07-29 08:00:00');
INSERT INTO Game VALUES (5, '2006-08-01 07:00:00');

INSERT INTO Player(ID, emailAddress) VALUES (1, 'me@calvin.edu');
INSERT INTO Player VALUES (2, 'king@gmail.edu', 'The King');
INSERT INTO Player VALUES (3, 'dog@gmail.edu', 'Dogbreath');

INSERT INTO PlayerGame VALUES (1, 1, 0.00);
INSERT INTO PlayerGame VALUES (1, 2, 0.00);
INSERT INTO PlayerGame VALUES (1, 3, 2350.00);
INSERT INTO PlayerGame VALUES (2, 1, 1000.00);
INSERT INTO PlayerGame VALUES (2, 2, 0.00);
INSERT INTO PlayerGame VALUES (2, 3, 500.00);
INSERT INTO PlayerGame VALUES (3, 2, 0.00);
INSERT INTO PlayerGame VALUES (3, 3, 5500.00);

-- Add sample active game records
INSERT INTO PlayerState VALUES (4, 1, 100, 30);
INSERT INTO PlayerState VALUES (4, 2, 30, 2);

-- Add properties for active games
INSERT INTO PropertyState VALUES (4, 1, 21, 3);
INSERT INTO PropertyState VALUES (4, 1, 18, 2);
INSERT INTO PropertyState VALUES (4, 2, 4, 5);
