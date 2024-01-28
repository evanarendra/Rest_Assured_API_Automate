Feature: Request Stock API Testing

  Scenario: Calling Product Detail API
    Given User login API is provided
    When User call Product get API


  Scenario: Calling Request Stock list API
    Given User login API is provided
    When User call Release Stock
    And User call update Release Stock
    And Login App API is provided
    And User call Request Stock API
    Then Request Stock list will be shown


