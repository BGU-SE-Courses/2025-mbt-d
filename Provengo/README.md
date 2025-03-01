# Testing PrestaShop using Provengo
This directory contains the Provengo project for testing PrestaShop.


## Running the tests
To run a single random test, run:
```shell 
provengo run PrestaShop
```

## Tool Documentation
See [Provengo README](PrestaShop/README.md) for a short description of the tool and how to use it.

For a full documentation go to [https://docs.provengo.tech](https://docs.provengo.tech)

## How we created the test model:
1. We started by creating the following files: [behavior.js](PrestaShop/spec/js/behavior.js),[actions.js](PrestaShop/spec/js/actions.js), and [data.js](PrestaShop/data/data.js).
2. We then ran the following command to generate the test model:
```shell
provengo analyze -f PDF PrestaShop   
```
3. We repeated steps 1-2 until we were satisfied with the generated model.
4. We used the following command to run the tests:
```shell
provengo run --show-sessions PrestaShop
```
5. We repeated steps 1-4 until we were satisfied with the result.
6. We recorded a video of the running tests and added it to the report. Since more than one browser session was opened, we recorded the entire screen. The link for the video is [[here](https://www.loom.com/share/f9917f6fcc5b4a5a894c0770aee18f84?sid=d8baac0d-17c2-4780-aad5-6b8138a11e6a)].

7. We copied the generated graph of the model to a file named [model.pdf](submission-files/model.pdf) inside the submission-files directory.

### Test files
The test data is in [data.js](PrestaShop/data/data.js), the set of possible actions is in [actions.js](PrestaShop/spec/js/actions.js), and the behavior of the system is in [behavior.js](PrestaShop/spec/js/behavior.js).
See the files for a detailed description.



## How we tested the system
See the last two lessons of the [Provengo Course](https://provengo.github.io/Course/Online%20Course/0.9.5/index.html) for a detailed explanation of the following steps.

1. We implemented a domain-specific ranking function at the beginning of the [ensemble-code.js](PrestaShop/meta-spec/ensemble-code.js) file and updated the `rankingFunction` to use our function. We added a documentation comment that explains our function.
2. We sampled the state space of the system using the following command that created a [samples.json](PrestaShop/products/run-source/samples.json) file:
```shell
provengo sample --overwrite --size 10 PrestaShop
```
3. Given this sample, we created an ensemble (test suite) using the following commands that created an [ensemble.json](PrestaShop/products/run-source/ensemble.json) file:
```shell
provengo ensemble --size 5 PrestaShop
```
4. We repeated the last two steps, changing the two size parameters, until we were satisfied with the grade of the generated test suites.
5. We copied the [ensemble.json](PrestaShop/products/run-source/ensemble.json) file to [domain-specific.json](submission-files/domain-specific.json).
6. We visualized the specification, and highlighted the traces in the optimized test suite create by the previous command and copied the output to [domain-specific.pdf](submission-files/domain-specific.pdf).
```shell
provengo analyze -f pdf --highlight products/run-source/ensemble.json PrestaShop
```
7. We ran the generated test suites using the following command:
```shell
provengo run -s products/run-source/ensemble.json PrestaShop 
```
8. We generated a report of the test-suite run and looked for errors. If errors were found and the reason was a bug in the model, we fixed the model and repeated the steps 2--7. If the reason was a bug in the system, we reported the bug by filling a bug report in the [README.md](../README.md) file of the root directory of the project. The report can be generated by running the following command:
```shell
provengo report PrestaShop
```
9. We repeated steps 1--8 for the two-way coverage criterion, where the json name of step 5 is [two-way.json](submission-files/two-way.json) and the pdf name of step 6 is [two-way.pdf](submission-files/two-way.pdf).
