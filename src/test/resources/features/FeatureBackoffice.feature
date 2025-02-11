# language: en
Feature: Tech Challenge App Backoffice

  Scenario: Register a new customer
    When send a request to register a new customer
    Then the customer is registered with success

  Scenario: Find a registered customer
    Given a backoffice user is logged
    When send a request to search a customer
    Then the search return the registered customer

  Scenario: Register a new product
    Given a backoffice user is logged
    When send a request to register a new product
    Then the product is registered with success
