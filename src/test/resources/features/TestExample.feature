Feature: Test Orange HRM

  Scenario Outline: Verify invalid credentials login
    Given LoginPage is loaded
    Then Enter invalid "<username>" and "<password>" and verify message "Invalid credentials"

    Examples:
    | username    | password  |
    | test        | test      |

#  Scenario: Verify Directory Page fields
#    Given LoginPage is loaded
#    Then Enter valid "Admin" and "admin123" and login
#    Then Verify Dashboard page is loaded
#    Then Navigate to Directory Page
#    Then Verify Directory Page is loaded
#    Then Search and verify Employee "Admin" and "Admin Admin123" with results "(1) Record Found"
#    Then Search and verify Jobtitle "Account Assistant" with results "No Records Found"
#    Then Search and verify Location "Texas R&D" with results "No Records Found"
#    Then Logout of application
#
#  Scenario: Verify Admin Page fields
#    Given LoginPage is loaded
#    Then Enter valid "Admin" and "admin123" and login
#    Then Verify Dashboard page is loaded
#    Then Navigate to Admin Page
#    Then Verify Admin Page is loaded
#    Then Search and verify User Role "Admin" with results "(4) Records Found"
#    Then Logout of application