Feature: Review API Tests

  Background:
    * def api = 'http://localhost:8080/reviews'
    * def token = karate.read('token.txt')
    * def movieId = 1
    * def userId = 1


  Scenario: Add a review
    * def movieId = 1
    Given url api + '/add'
    And header Authorization = 'Bearer ' + token
    And request {  "movie": {  "movieId": 1  },  "user": {  "userId": 1  },  "rating": 2,  "description": "Great movie!"  }
    When method post
    Then status 201
    And match response.rating == 2
    And match response.description == "Great movie!"
    * def  reviewId = response.reviewId

#Get reviews by movie
    Given url api + '/movie'
    And param movieId = 1
    When method get
    Then status 200
    And match response != null


#   Get average rating by movie
    Given url api + '/movie/' + movieId + '/avg'
    When method get
    Then status 200
    And match response != null

# Get total reviews by movie
    Given url api + '/movie/totalreviews/' + movieId
    When method get
    Then status 200
    And match response != null

#  Delete the review
    Given url api + '/' + reviewId
    And header Authorization = 'Bearer ' + token
    When method delete
    Then status 200
    And match response == 'Review deleted with ID ' + reviewId


