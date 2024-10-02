Feature: Movie API Tests

  Background:
    * def api = 'http://localhost:8080'
    * def movieId = 0
    * def token = karate.read('token.txt')

  Scenario: Get all movies and verify the first movie
    Given url api + '/movies'
    When method get
    Then status 200
    And match response[0].movieId == 1
    And match response[0].title == "Deadpool & Wolverine"

  Scenario: Save a new movie | Fetch it | Delete It
    Given url api + '/admin/movie'
    And header Authorization = 'Bearer ' + token
    And request { "title": "Karate Test", "director": "Test"}
    When method post
    Then status 201
    And match response.title == "Karate Test"
    And match response.director == "Test"
    * def movieId = response.movieId

    Given url api + '/movies/' + movieId
    When method get
    Then status 200
    And match response.title == "Karate Test"
    And match response.director == "Test"

    Given url api + '/admin/movie/' + movieId
    And header Authorization = 'Bearer ' + token
    When method delete
    Then status 200
    And match response == 'Movie Deleted with ID ' + movieId

