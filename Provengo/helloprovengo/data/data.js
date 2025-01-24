/*
 *  This is a good place to put common test data, project-wide constants, etc.
 */

const mainPageURL = 'http://localhost:8080';
const adminPageURL = 'http://localhost:8080/admina';

const xpaths = {
  userWindowLogin: {
    desktopUserInfo: '//*[@id="_desktop_user_info"]',
    emailField: '//*[@id="field-email"]',
    passwordField: '//*[@id="field-password"]',
    loginButton: '//*[@id="submit-login"]',
  },
  userAddItem: {
    customizableMugLink: "//a[@href='http://localhost:8080/home-accessories/19-customizable-mug.html']",
    textField: '//*[@id="field-textField1"]',
    submitCustomizedDataButton: '//*[@name="submitCustomizedData"]',
    addToCartButton: "//button[contains(@class, 'add-to-cart')]",
  },
  userProceedCheckOut: {
    cartButton: "//a[contains(@class, 'btn btn-primary') and contains(@href, 'cart?action=show')]",
    checkoutButton: "//a[@href='http://localhost:8080/order' and contains(@class, 'btn btn-primary')]",
  },
  userFillDetails: {
    addressField: '//*[@id="field-address1"]',
    cityField: '//*[@id="field-city"]',
    postCodeField: '//*[@id="field-postcode"]',
    countryDropdown: '//*[@id="field-id_country"]',
    stateDropdown: '//*[@id="field-id_state"]',
    confirmAddressesButton: '//*[@name="confirm-addresses"]',
    confirmDeliveryOptionButton: '//*[@name="confirmDeliveryOption"]',
    termsCheckbox: '//*[@id="conditions_to_approve[terms-and-conditions]"]',
    placeOrderButton: "//button[@type='submit' and @class='btn btn-primary center-block']",
    orderConfirmation: "//*[@id='order-confirmation']",
  },
  adminWindowLogin: {
    emailField: '//*[@id="email"]',
    passwordField: '//*[@id="passwd"]',
    loginButton: '//*[@id="submit_login"]',
  },
  adminSelectItem: {
    catalogMenu: '//*[@id="subtab-AdminCatalog"]/a',
    productsTab: '//*[@id="subtab-AdminProducts"]',
    editProductLink: "//a[@class='text-primary text-nowrap' and contains(@href, 'products-v2/19/edit')]",
  },
  adminChangeAvailability: {
    stockTab: '//*[@id="product_stock-tab-nav"]',
    productStockAvailableDateField: '//*[@id="product_stock_availability_available_date"]',
    saveProductButton: '//*[@id="product_footer_save"]',
    successfulDateUpdate: '//div[contains(@class, \'alert-success\') and contains(., \'Successful update\')]',
  },
};


