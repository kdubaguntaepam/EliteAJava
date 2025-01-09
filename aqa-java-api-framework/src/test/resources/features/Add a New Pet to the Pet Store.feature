Feature: Add a New Pet to the Pet Store

  Scenario Outline: Successful addition of a new pet
    Given I have the details of the pet "<Id>","<Name>","<Status>"
    When I send a POST request to the "pet" endpoint with the pet's details in JSON format
    Then the pet should be added to the store's inventory

    Examples:
      | Id  | Name   | Status    |
      | 100 | Tommy  | available |
      | 200 | Tommy200 | available |

 Scenario Outline: Verify that a pet can be retrieved by its ID
   Given I have the details of the pet "<Id>","<Name>","<Status>" exists
   When I send a POST request to the "pet" endpoint with the pet's details in JSON format
   When User retrieves the pet details with ID "<Id>"
   Then The API should return a 200 OK response with the pet object that matches the "<Id>","<Name>","<Status>" 

     Examples:
      | Id  | Name   | Status    |
      | 300 | Tommy1  | available |
      | 400 | Tommy201 | available |

  Scenario Outline: Verify that a pet's details can be updated by its ID using PUT
  Given I have the details of the pet "<Id>","<Name>","<Status>" exists
   When I send a PUT request to the "pet" endpoint with the pet's updated details in JSON format
   When User retrieves the pet details with ID "<Id>"
   Then The API should return a 200 OK response with the updated pet object that matches the "<Id>","<Name>","<Status>"
 
Examples:
  | Id  | Name            | Status    |
  | 100 | Tommy1Updated   | sold      |
  | 200 | Tommy201Updated | pending   |