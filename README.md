Generate Token
curl --location --request POST 'http://localhost:8080/api/generateToken' \
--header 'username: user' \
--header 'password: password' \
--header 'Content-Type: application/json' \
--data ''

validate Token
curl --location 'http://localhost:8080/api/validateToken' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzE3ODMzODY4LCJleHAiOjE3MTc4Njk4Njh9.X8K_B821b1WkvIYeuA588E1WunJx4l1FRAMQwnYIra4'
