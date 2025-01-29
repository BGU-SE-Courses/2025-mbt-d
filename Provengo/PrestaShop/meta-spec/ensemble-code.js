// @provengo summon ctrl

/**
 * List of events "of interest" that we want test suites to cover.
 */
const GOALS = [
    Event("End(UserFillCheckoutDetails)"),
    Event("End(AdminUpdateProductAvailability)")
];

const makeGoals = function(){
    return [
        [Ctrl.markEvent("End(UserFillCheckoutDetails)")],
        [Ctrl.markEvent("End(AdminUpdateProductAvailability)")]
    ];
}

/**
 * Ranks test suites by how many events from the GOALS array were met.
 * The more goals are met, the higher the score.
 * 
 * It make no difference if a goal was met more then once.
 *
 * @param {Event[][]} ensemble The test suite to be ranked.
 * @returns Number of events from GOALS that have been met.
 */
function rankByMetGoals( ensemble ) {
    const unreachedGoals = [];
    for ( let idx=0; idx<GOALS.length; idx++ ) {
        unreachedGoals.push(GOALS[idx]);
    }

    for (let testIdx = 0; testIdx < ensemble.length; testIdx++) {
        let test = ensemble[testIdx];
        for (let eventIdx = 0; eventIdx < test.length; eventIdx++) {
            let event = test[eventIdx];
            for (let ugIdx=unreachedGoals.length-1; ugIdx >=0; ugIdx--) {
                let unreachedGoal = unreachedGoals[ugIdx];
                if ( unreachedGoal.contains(event) ) {
                    unreachedGoals.splice(ugIdx,1);
                }
            }
        }
    }

    return GOALS.length-unreachedGoals.length;
}

/**
 * List of all the possible events
 * @type {*[]}
 */
const twoWayGoals = [
    Event("Start(UserLogin)"),
    Event("Start(AdminLogin)"),
    Event("Start(UserAddProductToCart)"),
    Event("Start(UserFillCheckoutDetails)"),
    Event("Start(AdminNavigateToEditProduct)"),
    Event("Start(AdminUpdateProductAvailability)")
];

const twoWaysMakeGoals = function () {
    return twoWayGoals.map(goal => [Ctrl.markEvent(goal)]);
};


/**
 * Ranks a given test ensemble based on two-pair goal coverage.
 *
 * This function evaluates how well a test suite (ensemble) covers all possible
 * two-way goal pairs from `twoWayGoals`. The score is calculated by counting
 * how many goal pairs are covered by at least one test in the ensemble.
 *
 * @param {Array<Array<any>>} ensemble - A set of test cases, where each test case
 *        is an array of events.
 * @returns {number} - The number of two-way goal pairs covered by the ensemble.
 *
 * Each test case consists of multiple events. The function checks if any two events
 * within a test case correspond to a pair of goals in `twoWayGoals`. If a goal pair
 * is found in a test case, it is removed from the list of unreached goal pairs.
 *
 * The final score is determined by subtracting the number of uncovered goal pairs
 * from the total possible goal pairs.
 */
function rankByTwoPairCoverage(ensemble) {
    // Initialize an array to store all possible goal pairs
    const unreachedGoalPairs = [];

    // Generate all unique pairs of goals from twoWayGoals
    for (let i = 0; i < twoWayGoals.length; i++) {
        for (let j = i + 1; j < twoWayGoals.length; j++) {
            unreachedGoalPairs.push([twoWayGoals[i], twoWayGoals[j]]);
        }
    }

    // Iterate through each test case in the ensemble
    for (let testIdx = 0; testIdx < ensemble.length; testIdx++) {
        let test = ensemble[testIdx];

        // Check all pairs of events within the test case
        for (let eventIdx1 = 0; eventIdx1 < test.length - 1; eventIdx1++) {
            let event1 = test[eventIdx1];

            for (let eventIdx2 = eventIdx1 + 1; eventIdx2 < test.length; eventIdx2++) {
                let event2 = test[eventIdx2];

                // Iterate through the list of unreached goal pairs in reverse order
                for (let ugPairIdx = unreachedGoalPairs.length - 1; ugPairIdx >= 0; ugPairIdx--) {
                    let [goal1, goal2] = unreachedGoalPairs[ugPairIdx];

                    // Check if the current event pair matches the goal pair
                    if (
                        (goal1.contains(event1) && goal2.contains(event2)) ||
                        (goal1.contains(event2) && goal2.contains(event1))
                    ) {
                        // Remove the covered goal pair from the list
                        unreachedGoalPairs.splice(ugPairIdx, 1);
                    }
                }
            }
        }
    }

    // Compute the total number of goal pairs and subtract the uncovered pairs
    return (twoWayGoals.length * (twoWayGoals.length - 1)) / 2 - unreachedGoalPairs.length;
}

/**
 * Ranks potential test suites based on the percentage of goals they cover.
 * Goal events are defined in the GOALS array above. An ensemble with rank
 * 100 covers all the goal events.
 *
 * Multiple ranking functions are supported - to change ranking function,
 * use the `ensemble.ranking-function` configuration key, or the 
 * --ranking-function <functionName> command-line parameter.
 *
 * @param {Event[][]} ensemble the test suite/ensemble to be ranked
 * @returns the percentage of goals covered by `ensemble`.
 */
 function rankingFunction(ensemble) {
    
    // How many goals did `ensemble` hit?
    const metGoalsCount = rankByTwoPairCoverage(ensemble);
    // What percentage of the goals did `ensemble` cover?
    const metGoalsPercent = metGoalsCount/GOALS.length;

    return metGoalsPercent * 100; // convert to human-readable percentage
}

