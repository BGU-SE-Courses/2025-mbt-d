/* @provengo summon selenium */


/**
 * This Story is user open the shop, login, add a product to the cart
 * and checks out the item
 */
bthread('UserChecksOutNewProduct', function () {
    let s1 = new SeleniumSession('userSession');
    s1.start(mainPageURL);

    sync({request: Event("End(UserLogin)")}, userLogin(s1, {emailField: 'teamd@qa.com', password: '#TeamDTeamD#'}));
    sync({request: Event("End(UserAddProductToCart)")}, addProductToCartAndCheckOut(s1, {text: 'I Love QA'}));
    sync({request: Event("End(UserFillCheckoutDetails)")}, fillCheckoutDetails(s1, {
        address: "Los Angeles 111",
        city: "Los Angeles",
        postcode: "12221",
        country: "United States",
        state: "California",
    }));
});

/**
 * This story opens up the admin's control page and change product
 * availability to future date
 */
bthread('AdminChangeAvailabilityDate', function () {
  let s2 = new SeleniumSession('adminSession')
  s2.start(adminPageURL);
  sync({request:Event("End(AdminLogin)")}, adminLogin(s2,
      {emailField: 'demo@prestashop.com', password: 'prestashop_demo'}));
  sync({request:Event("End(AdminNavigateToEditProduct)")}, navigateToEditProduct(s2, {}));
  sync({request:Event("End(AdminUpdateProductAvailability)")}, updateProductAvailability(s2,
      {newDate: "2026-01-01"}));
});

bthread("FlowControl_AddProductThanAvailability", function () {
    // Ensure user add the product to the cart before admin changes availability data
    sync({ waitFor: Event("End(UserAddProductToCart)"), block: Event("Start(AdminUpdateProductAvailability)") });

});


bthread("FlowControl_AvailabilityUpdateThanUserCheckOut", function () {
    // Ensure admin changes availability date before user proceed with check out
    sync({ waitFor: Event("End(AdminUpdateProductAvailability)"), block: Event("Start(UserFillCheckoutDetails)")});

});
