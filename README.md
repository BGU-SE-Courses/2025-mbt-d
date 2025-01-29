# Software Quality Engineering - System Testing
This is a repository for the system-testing assignment of the Software Quality Engineering course at the [Ben-Gurion University](https://in.bgu.ac.il/), Israel.

## Assignment Description
In this assignment, we tested an open-source software called [PrestaShop](https://demo.prestashop.com/#/en/front).

PrestaShop is an open-source e-commerce platform that enables users to create and manage online stores with ease. It provides an intuitive interface for handling products, customers, orders, and payments, making it accessible to a wide range of users.

## Installation
1. Download PrestaShop (follow instructions in [here](https://docs.prestashop-project.org/v.8-documentation/getting-started)).
2. Make Sure to set up local server and configure local database.
3. Configure maven dependencies for Cucumber and Selenium.
4. Download ChromeDriver according to your Google Chrome version and place it in the Selenium directory.

## What we tested
We tested the checkout process for customers and the ability of an admin to update a product's availability date. We focused on the following user stories:

User story: A customer completes the checkout process for an item.

Preconditions: A product is available for purchase.
Expected outcome: The customer successfully places an order, and the order details are recorded.

User story: An admin updates the "Date Available" field for a product to a future date.

Preconditions: The admin has access to the product catalog and editing permissions.
Expected outcome: The product's availability date is updated, and customers can see the change.

## How we tested
We used two different testing methods:
1. [Cucumber](https://cucumber.io/), a behavior-driven testing framework.
2. [Provengo](https://provengo.tech/), a story-based testing framework.

Each of the testing methods is elaborated in its own directory. 


## Detected Bugs
We detected the following bugs:

1. Bug 1: 
   1. When Admin changes the data availability of a product, customer can still purchase the product.
   2. Steps to reproduce: Customer adds product to the cart, at that time admin changes the product availability to a future date, than customer proceed to check out.
   3. Expected result: Customer gets a decline message and not able to proceed to check out
   4. Actual result: Customer is able to purchase an unavailable product.



