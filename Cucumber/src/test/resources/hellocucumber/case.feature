Feature: Customer Checkout and Admin Updates Product Availability

  @User
  Scenario Outline: Customer purchases an item
    Given a customer navigates and login as "<Username>" and "<Password>"
    When the customer adds a product to their cart
    And proceed to checkout
    Then the order is successfully placed
    Examples:
      | Username   | Password  |
      | teamd@qa.com | #TeamDTeamD# |

  @Admin
  Scenario Outline: Admin changes the availability date
    Given an admin logs into the admin panel as "<Username>" and "<Password>"
    When the admin changes the "Date Available" of the purchased product to a future date
    Then the product availability is updated successfully
    Examples:
      | Username   | Password  |
      | demo@prestashop.com | prestashop_demo |




