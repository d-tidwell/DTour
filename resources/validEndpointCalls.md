'Retrieve profile by ID (GET()/profiles/{id})'
curl -X GET -H 'Authorization: Bearer <TOKEN>' \
https://127.0.0.1:3000/profiles//NoahCainTestEmailAddress@gmail.com

'Update profile by ID (PUT()/profiles/{id})'
curl -v -X PUT http://127.0.0.1:3000/profiles/nataliashnur@gmail.com \            
-H 'Authorization: Bearer <TOKEN>' \
-d '{"id":"natalishnur@gmail.com", "firstName": "Natalia", "lastName": "Shnur", "location": "Nashville", "gender": "female", "dateOfBirth": "2007-12-03T10:15:30+01"}'

'Create profile (POST()/profiles/create)'
curl -v -X POST http://127.0.0.1:3000/profiles/create \
-H 'Authorization: Bearer <TOKEN>' \
-d '{"firstName": "Noah", "lastName": "Cain", "location": "Nashville", "gender": "M", "dateOfBirth": "2007-12-03T10:15:30+01:00"}'

'Update a profile by adding an event to it (PUT()/profiles/addEvent)'
curl -v -X PUT http://127.0.0.1:3000/profiles/addEvent \
-H 'Authorization: Bearer <TOKEN>' \
-d '{"eventId":"B56IM", "profileId":"NoahCainTestEmailAddress@gmail.com"}'

'Update a profile adding 'following' to it (PUT()/profiles/addFollowing)'
curl -v -X PUT http://127.0.0.1:3000/profiles/addFollowing \
-H 'Authorization: Bearer <TOKEN>' \
-d '{"id":"nataliashnur@gmail.com", "idToAdd":"noahzcain@gmail.com"}' 

'Update profile by removing 'following' from it (PUT()/ profiles/removeFollowing)'
curl -v -X PUT http://127.0.0.1:3000/profiles/removeFollowing \
-H 'Authorization: Bearer <TOKEN>' \
-d '{"id":"nataliashnur@gmail.com", "profileIdToRemove":"noahzcain@gmail.com"}'

'Retrieve an event by ID (GET()/ events/{id})'
curl -v -X GET http://127.0.0.1:3000/events/001NASH \
-H 'Authorization: Bearer <TOKEN>'

'Create an event (POST()/events)'
curl -v -X POST http://127.0.0.1:3000/events/create \
-H 'Authorization: Bearer <TOKEN>' \
-d '{"name":"Bubble Day", "eventCreator":"nataliashnur@gmail.com","address":"Tampa, FL", "description":"Enjoy your day among bubbles.", "dateTime":"2023-12-03T10:15:30+01","category":["fun activity"]}'

'Update an event by ID (PUT()/events/{id})'
PUT   /events/{id} + AUTH Required
curl -v -X PUT http://127.0.0.1:3000/events/4QG4B  \
-H 'Authorization: Bearer eyJraWQiOiJLY3NsaE9ZVHRGSGhGTFNFOFNLdzNRbW16aFJPaVZYMEFnMDJQOFZqZGZzPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoieUc0RS1NSl9jWmFtUTRSSURVYkFWZyIsInN1YiI6IjUwMzVmNzg3LWQyYTItNDY0MC04ZDJkLWJiZGEyMGQ3YzE3NSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0yLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMl8zQURLcktwZ0kiLCJjb2duaXRvOnVzZXJuYW1lIjoiNTAzNWY3ODctZDJhMi00NjQwLThkMmQtYmJkYTIwZDdjMTc1Iiwib3JpZ2luX2p0aSI6IjgxMjRiMjI2LTFjZTQtNDM1Ny05NGFmLWU3YWRlMmNkYjc2ZCIsImF1ZCI6IjN1ZG10NGNuZHBoN2szZmRzdDQ5NDg1M2cwIiwiZXZlbnRfaWQiOiIzNGQ3M2FmZS03MDg4LTQ0MWMtYTIxMi00NmE5YjI2M2U0M2MiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTY4MTc5MDU2MywibmFtZSI6Ik5hdHlUZXN0IiwiZXhwIjoxNjgxNzk0MTYzLCJpYXQiOjE2ODE3OTA1NjMsImp0aSI6IjY3ZjE2ZGQzLTgzNzgtNDAyNy1hYjU4LTY3YzlmMDYwNGFkZiIsImVtYWlsIjoibmF0YWxpYXNobnVyQGdtYWlsLmNvbSJ9.SxGf-SK2xAGQuaWqjNlC2PA39vl5-ZpsUYk86C2f-gJyQ0bH9zKk2zYQWbT_nOtJIaykUhfWvcqXrxOn08IKvgFlFoGM-tnmShnIFHVg00Ui-nA8YDjeCcMsft4YkZDDDskaFUZOxQ-v6ibXTDhTnx9TvJRqC-F1hr9eL8sSjq2Llll8ZhLTzHtmvJxapZ6-XZHz9Qu4nAt2x4to97zcfTTDSzKfqsr1jkzQ4ChyMLVdYqjfryGHhdCjTv3s1wt-8701JtO_0NLVJdzqsAIB3dmew2VY6WmgIbiU4Sdt8AfeizeRgdm0jYlc1vFCVqtygqDJsbXbVUmnIVmE7PfvsA' \
-d '{"profileId":"NoahCainTestEmailAddress@gmail.com", "eventId":"4QG4B","name":"Puzzle Day", "eventCreator":"NoahCainTestEmailAddress@gmail.com","address":"Pensacola Beach, FL", "description":"Twist your brain with friends.","dateTime":"2023-07-03T10:15:30+01","category":["social"]}'

