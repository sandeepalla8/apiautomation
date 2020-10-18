Feature: As an API user i will get the hotel details based on geo location and checkin dates

  @SmokeTest
  Scenario Outline: Verify the geo based hotel search API response
    When I get a list of hotels and locations using Geo key "<url>","<Method>","<query>","<header>"
    Then I validate the geo based hotel search response "<statusCode>","<query>"

    Examples:
      |Method|statusCode|header|query|url|
      |GET   |200       |Content-Type:application/json|paris|https://www.tajawal.ae/api/hotel/ahs/v2/geo-suggest|


  @SmokeTest
  Scenario Outline: Verify the hotel search API response based on checkin date and place
    When I post a hotel search request with header params and request body using API parameters "<url>","<header>","<Method>","<destination>","<checkin>","<checkout>","<guestCombo>","<placeId>"
    Then I validate the date based hotel search response "<statusCode>","<destination>","<checkin>","<checkout>","<guestCombo>","<placeId>"

    Examples:
    |Method|header|statusCode|destination |checkin        |checkout   |guestCombo       |placeId          |url|
    |POST  |Content-Type:application/json|200       |paris       |11-12-2020     |15-12-2020 |ADT-CHD:3-ADT,ADT-ADT| ChIJD7fiBh9u5kcRYJSMaMOCCwQ|https://www.tajawal.ae/api/hotel/ahs/search/request|
    |POST  |Content-Type:application/json|200       |paris       |01-01-2021     |10-01-2021 |ADT:40-CHD:3-ADT:35,ADT:25-ADT:20| ChIJD7fiBh9u5kcRYJSMaMOCCwQ|https://www.tajawal.ae/api/hotel/ahs/search/request|


  @SmokeTest
  Scenario Outline: Verify the hotel search API error response
    When I post a hotel search request with header params and request body using API parameters "<url>","<header>","<Method>","<destination>","<checkin>","<checkout>","<guestCombo>","<placeId>"
    Then I validate the hotel search error response "<statusCode>","<destination>","<checkin>","<checkout>","<guestCombo>","<placeId>"

    Examples:
      |Method|header|statusCode|destination |checkin        |checkout   |guestCombo       |placeId          |url|
      |POST  |Content-Type:application/json|400|       |11-12-2020 |15-12-2020 |ADT-CHD:3-ADT,ADT-ADT| ChIJD7fiBh9u5kcRYJSMaMOCCwQ|https://www.tajawal.ae/api/hotel/ahs/search/request     |
      |POST  |Content-Type:application/json|400|paris  |           |15-12-2020 |ADT-CHD:3-ADT,ADT-ADT| ChIJD7fiBh9u5kcRYJSMaMOCCwQ|https://www.tajawal.ae/api/hotel/ahs/search/request     |
      |POST  |Content-Type:application/json|400|paris  |11-12-2020 |           |ADT-CHD:3-ADT,ADT-ADT| ChIJD7fiBh9u5kcRYJSMaMOCCwQ|https://www.tajawal.ae/api/hotel/ahs/search/request     |


