
'CREATE PROFILE ENDPOINT CURL'
curl -v -X POST http://127.0.0.1:3000/profiles/create \
-H 'Authorization: Bearer <TOKEN HERE> ' \
-d '{"firstName": "Noah", "lastName": "Cain", "location": "Nashville", "gender": "M", "dateOfBirth": "2007-12-03T10:15:30+01:00"}'


'GET PROFILE ENDPOINT'
GetProfile EndPoint

curl -X GET -H 'Authorization: Bearer <TOKEN HERE>' \
https://g3c9cifk5j.execute-api.us-east-2.amazonaws.com/Prod/profiles/NoahCainTestEmailAddress@gmail.com

'CREATE EVENT ENDPOINT'
curl -v -X POST http://127.0.0.1:3000/events/create \            
-H 'Authorization: Bearer <TOKEN HERE>' \
-d '{"name":"Puzzle Day", "eventCreator":"noahzcain@gmail.com","address":"Pensacola", "description":"twist your brain with friends", "dateTime":"2023-12-03T10:15:30+01","category":["educational"]}'

'UPDATE PROFILE ENDPOINT'
curl -X PUT  http://127.0.0.1:3000/profiles/noahzcain@gmail.com \
-H 'Authorization: Bearer <TOKEN HERE>' \
-d '{"firstName": "Noah", "lastName": "Cain", "location": "Nashville", "gender": "M", "dateOfBirth": "1995-06-30T10:15:30+01:00"}'