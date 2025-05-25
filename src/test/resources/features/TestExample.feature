Feature: Test Orange HRM

  Scenario Outline: Verify invalid credentials login
    Given LoginPage is loaded
    Then Enter invalid "<username>" and "<password>" and verify message "Invalid credentials1"

    Examples:
    | username    | password  |
    | test        | test      |
