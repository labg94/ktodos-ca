Feature: create a Todo

  Scenario Outline: when the user add a new task should create a todo
    Given a new task "<task>"
    When I added it
    Then I should receive a new Todo with "CREATED" status

    Examples:
      | task
      | my new task
      | other example


