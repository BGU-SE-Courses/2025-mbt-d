/* @provengo summon selenium */


/**
 * This Story is user open the shop, login, add a product to the cart
 * and checks out the item
 */
bthread('User checks out new product', function () {
    let s1 = new SeleniumSession('userSession')
    s1.start(mainPageURL);

    sync({request: Event("End(UserLogin)")}, userLogin(s1, {emailField: 'teamd@qa.com', password: '#TeamDTeamD#'}));
    sync({request: Event("End(UserAddProductToCart)")}, addProductToCartAndCheckOut(s1, {text: 'I Love QA'}));
    sync({request: Event("End(FillCheckoutDetails)")}, fillCheckoutDetails(s1, {
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
bthread('Admin Change Availability Date', function () {
  let s2 = new SeleniumSession('adminSession')
  s2.start(adminPageURL);
  sync({request:Event("End(AdminLogin)")}, adminLogin(s2,
      {emailField: 'demo@prestashop.com', password: 'prestashop_demo'}));
  sync({request:Event("End(NavigateToEditProduct)")}, navigateToEditProduct(s2, {}));
  sync({request:Event("End(UpdateProductAvailability)")}, updateProductAvailability(s2,
      {numOfDays: 3}));
});

bthread("FlowControl", function () {
    // Ensure End(UserAddProductToCart) occurs before Start(UpdateProductAvailability)
    sync({ waitFor: Event("End(UserAddProductToCart)") });
    sync({ waitFor: Event("Start(UpdateProductAvailability)"), block: Event("Start(UpdateProductAvailability)") });

    // Ensure End(UpdateProductAvailability) occurs before Start(FillCheckoutDetails)
    sync({ waitFor: Event("End(UpdateProductAvailability)") });
    sync({ waitFor: Event("Start(FillCheckoutDetails)"), block: Event("Start(FillCheckoutDetails)") });
});


