Feature: Test Orange HRM

#  Scenario Outline: Verify invalid credentials login
#    Given LoginPage is loaded
#    Then Enter invalid "<username>" and "<password>" and verify message "Invalid credentials"
#
#    Examples:
#    | username    | password  |
#    | test        | test      |


#  Scenario Outline: Admin - Add new locations
#    Given Load TestCase data for "<testCaseID>"
#    Given LoginPage is loaded
#    Then Enter valid credentials and login
#    Then Verify Dashboard page is loaded
#    Then Navigate to Admin Page
#    Then Verify Admin Page is loaded
#    Then Add new Location
#    Then Logout of application
#
#    Examples:
#    | testCaseID |
#    | TC001      |
#    | TC002      |
#    | TC003      |
#    | TC004      |
#    | TC005      |
#    | TC006      |
#    | TC007      |


    Scenario: Admin - Add new locations
      Given Load TestCase data for "TC001"
      Given LoginPage is loaded
      Then Enter valid credentials and login
      Then Verify Dashboard page is loaded
      Then Navigate to Admin Page
      Then Verify Admin Page is loaded
      Then Delete all locations
      Then Logout of application