/* @provengo summon selenium */


function userLogin(session, e) {
  sync({ request: Event("Start(UserLogin)") });

  session.click(xpaths.userWindowLogin.desktopUserInfo);
  session.waitForVisibility(xpaths.userWindowLogin.emailField, 2000);
  session.writeText(xpaths.userWindowLogin.emailField, e.emailField);
  session.writeText(xpaths.userWindowLogin.passwordField, e.password);
  session.click(xpaths.userWindowLogin.loginButton, 2000);
}

function adminLogin(session, e) {
  sync({ request: Event("Start(AdminLogin)") });
  session.waitForVisibility(xpaths.adminWindowLogin.emailField, 2000);
  session.writeText(xpaths.adminWindowLogin.emailField, e.emailField);
  session.writeText(xpaths.adminWindowLogin.passwordField, e.password);
  session.click(xpaths.adminWindowLogin.loginButton, 2000);
}

function addProductToCartAndCheckOut(session, e) {
  sync({ request: Event("Start(UserAddProductToCart)") });

  session.click(xpaths.userAddItem.customizableMugLink);
  session.writeText(xpaths.userAddItem.textField, e.text);
  session.click(xpaths.userAddItem.submitCustomizedDataButton);
  session.waitForVisibility(xpaths.userAddItem.addToCartButton, 2000);
  session.click(xpaths.userAddItem.addToCartButton);
  session.click(xpaths.userProceedCheckOut.cartButton);
  session.click(xpaths.userProceedCheckOut.checkoutButton);
}


function fillCheckoutDetails(session, e) {
  sync({ request: Event("Start(UserFillCheckoutDetails)") });

  session.writeText(xpaths.userFillDetails.addressField, e.address);
  session.writeText(xpaths.userFillDetails.cityField, e.city);
  session.writeText(xpaths.userFillDetails.postCodeField, e.postcode);
  session.click(xpaths.userFillDetails.countryDropdown, 1000);
  session.selectByVisibleText(xpaths.userFillDetails.countryDropdown, e.country);
  session.click(xpaths.userFillDetails.stateDropdown, 1000);
  session.selectByVisibleText(xpaths.userFillDetails.stateDropdown, e.state);

  session.waitForVisibility(xpaths.userFillDetails.confirmAddressesButton,2000);
  session.click(xpaths.userFillDetails.confirmAddressesButton);
  session.click(xpaths.userFillDetails.confirmDeliveryOptionButton);
  session.click(xpaths.userFillDetails.termsCheckbox);

  session.click(xpaths.userFillDetails.placeOrderButton);
  session.waitForVisibility(xpaths.userFillDetails.orderConfirmation,2000)
}



function navigateToEditProduct(session, e) {
  sync({ request: Event("Start(AdminNavigateToEditProduct)") });

  session.click(xpaths.adminSelectItem.catalogMenu);
  session.click(xpaths.adminSelectItem.productsTab);
  session.click(xpaths.adminSelectItem.editProductLink);
}

function updateProductAvailability(session, e) {
  sync({ request: Event("Start(AdminUpdateProductAvailability)") });

  session.click(xpaths.adminChangeAvailability.stockTab);
  session.writeText(
      xpaths.adminChangeAvailability.productStockAvailableDateField,
      e.newDate,
      true
  );
  session.click(xpaths.adminChangeAvailability.saveProductButton);
  session.waitForVisibility(xpaths.adminChangeAvailability.successfulDateUpdate,5000);
}



