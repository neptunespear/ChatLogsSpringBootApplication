# ChatLogsSpringBootApplication
GET,POST,DELETE chatlogs based on user id and message id

POST METHOD
------------

YOU CAN USE /chatlogs/<userId>

you can enter any userid along with add the json encoded input with message which cannot be blank and timestamp(i have initialize it with value 0, 
if you input any timestamp that is ok or else it will generate automatic timstamp with epoch seconds)
the response will set the parameter issent to true and add the msgg it 

GET METHOD
-----------

YOU can use /chatlogs    to fetch all the chatlogs

/chatlogs/<userId> will fetch chatlogs for particular userId

/chatlogs/
