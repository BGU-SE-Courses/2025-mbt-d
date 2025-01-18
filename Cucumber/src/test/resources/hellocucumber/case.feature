Feature: Customer Checkout and Admin Updates Product Availability

  Scenario: Customer purchases an item, and admin changes the availability date
    Given a customer navigates to the shop
    When the customer adds a product to their cart and completes checkout
    Then the order is successfully placed

    Given an admin logs into the admin panel
    When the admin changes the "Date Available" of the purchased product to a future date
    Then the product availability is updated successfully
