# SLOWARC
Version: 0.1

Authors:
- Alec Lorimer
- Nupur Kumar
- Mouhamad Ali Elamine
- Natalie Chung

## SlowArc Program

### SlowArc Demonstration Video

[Link to Video](SlowArcDemonstration.mp4)

### How to build
1. Verify `make` and a JDK are installed
2. Run `make`, and a `SlowArc.jar` file will be generated


### How to run
```bash
$ java -jar SlowArc.jar <datafile.csv>
```

### Tests
Within the directory `tests/` there are two more directories, `data/` and `results/`. Files within the data folder will have a `<testname>.csv` name, and a paired result file in the results directory with the name of `<testname>_result.txt` which contains wether or not `<testname>.csv` results in a "Ball" or "Strike".

The tests folder contains a script `runTests.py` which goes feeds each data file as an input into the SlowArc.jar program and pipes its output into the test script. From here, the script reads the corresponding result text file, and compares the piped output from the SlowArc program to the expected result in the text file. Below is an example of how to run the test script.

```bash
$ python3 tests/runTests.py
```

The output from the test script is as follows. If the test passes, the program will print out the name of the test and "Pass" like "pitch4: Pass". If the test fails, the script prints out the name of the test with a fail message, then saying what the expected and calculated results were. Below is an **example** that contains both a pass and fail.
```bash
$ python3 tests/runTests.py
pitch1: Pass
pitch10: Pass
pitch2: Pass
pitch3: Fail: - Expected:Ball Output:Strike
pitch4: Pass
pitch5: Pass
pitch6: Fail: - Expected:Ball Output:Strike
pitch7: Pass
pitch8: Pass
pitch9: Fail: - Expected:Strike Output:Ball
```
_Note: Reminder to the grader, this is not the true output of our program._

### Development Documents
For a list of development documents involving the sprint, check out the links to the PDFs below or look in the `docs/` directory.
- [Product and Sprint Backlog](docs/Product_and_Sprint_Backlog.pdf)
- [Scrum meetings](docs/Scrum_Meetings.pdf)
- [Sprint Planning Meeting](docs/Sprint_Planning_Meeting.pdf)
- [Sprint Review](docs/Sprint_Review.pdf)
- [Sprint Retrospective](docs/Sprint_Retrospective.pdf)
