Feature: User API Tests

  Background:
    * def api = 'http://localhost:8080'
    * def token = karate.read('token.txt')
    * def userId = 0

  Scenario: Register a new user
    Given url api + '/register'
    And request {"username": "testUser", "email": "testuser@example.com","password": "Password@123","role": "USER"  }
    When method post
    Then status 201
    And match response.username == "testUser"
    And match response.email == "testuser@example.com"
    * def userId = response.userId

    Given url api + '/login'
    And request {"email": "testuser@example.com","password": "Password@123" }
    When method post
    Then status 200
    And match response.token != null
    And match response.email == "testuser@example.com"
    And match response.role == "USER"


    Given url api + '/count'
    And header Authorization = 'Bearer ' + token
    When method get
    Then status 200
    And match response !=null


    Given url api + '/admin/user/' + userId
    And header Authorization = 'Bearer ' + token
    When method delete
    Then status 200
    And match response == 'User deleted with id ' + userId


