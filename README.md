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

/chatlogs/<userId> if we pass optional field with Key = limit and Value = 5(suppose) it will return list of 5 chatlogs for particular user
/chatlogs/<userId> if we pass optional field with Key = pageId and Value = initialsofMsggId it will return all the chatlogs having msggId initials same as value.

DELETE METHOD
-------------

/chatlogs/<userId>  if we pass the userId -> it will delete all the chatlogs for the userId
/chatlogs/<userId>/<messageId> if we pass both userId and messageId -> it will delete chatlogs for both conditions
