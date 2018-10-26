-- Exercise 8.1
-- a. All games ordered by date
--SELECT *
--  FROM Game
--  ORDER BY time
--  ;

-- b.
--SELECT *
--  FROM Game
--  WHERE time BETWEEN (DATE(NOW()) - 7) AND DATE(NOW())
--  ;
  
-- c.
--SELECT *
--  FROM Player
--  WHERE name IS NOT NULL
--  ;

-- d.
--SELECT playerID
--  FROM PlayerGame
--  WHERE score > 2000
--  ;

-- e.
--SELECT *
--  FROM Player
--  WHERE emailAddress LIKE '%gmail%'
--  ;

-- Exercise 8.2
-- a.
--SELECT score
--  FROM PlayerGame
--  JOIN Player ON Player.ID = PlayerGame.playerID
--  AND name = 'The King'
--  ;

-- b.
--SELECT name
--  FROM Player
--  JOIN PlayerGame ON Player.ID = PlayerGame.playerID
--  JOIN Game ON PlayerGame.gameID = Game.ID
--  WHERE score = (SELECT MAX(score) FROM PlayerGame
--                 JOIN Game ON PlayerGame.gameID = Game.ID
--		 WHERE time = '2006-06-28 13:20:00')
--  ;

-- c.
-- The P1.ID < P2.ID ensures that the player who's name matches is not itself

-- d.
-- When comparing to row's in a table requires you to do a self join so that you have to entities to make comparisions with. The self join is used when the table references data in itself
