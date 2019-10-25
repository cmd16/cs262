-- Retrieve a list of all the games, ordered by date with the most recent game coming first.
-- SELECT *
-- FROM game
-- ORDER BY time DESC;
-- Retrieve all the games that occurred in the past week. Last week began on June 28, 2006.
-- SELECT *
-- FROM game
-- WHERE time >= '2006-06-28';
-- Retrieve a list of players who have (non-NULL) names.
-- SELECT *
-- from player
-- WHERE name is not NULL;
-- Retrieve a list of IDs for players who have some game score larger than 2000.
-- SELECT playerid
-- from PlayerGame
-- WHERE score > 2000;
-- Retrieve a list of players who have GMail accounts.
-- SELECT *
-- FROM player
-- WHERE emailAddress like '%gmail%';

-- Retrieve all “The King”’s game scores in decreasing order.
-- SELECT score
-- FROM playergame, player
-- WHERE playergame.playerid = player.id
-- AND player.name = 'The King'
-- ORDER BY score DESC;
-- Retrieve the name of the winner of the game played on 2006-06-28 13:20:00.
SELECT name
FROM playergame, player, game
WHERE player.id = playergame.playerID
AND playergame.gameid = game.id
AND game.time = '2006-06-28 13:20:00'
ORDER BY score DESC
LIMIT 1;
-- So what does that P1.ID < P2.ID clause do in the last example query?
-- Made sure we didn't duplicate pairings (e.g., p1, p2 is the same as p2, p1)
-- The query that joined the Player table to itself seems rather contrived. Can you think of a realistic situation in which you’d want to join a table to itself?
