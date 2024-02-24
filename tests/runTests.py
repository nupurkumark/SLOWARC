import os
import re

testDir = "tests/"
testDataDir = testDir + "data/"
resultsDir =  testDir + "results/"
testDataFiles = sorted(os.listdir(testDataDir))

for dataFile in testDataFiles:
    filenameNoExtension = dataFile.replace(".csv","")
    fullPath = testDataDir+dataFile
    cmd = "java -jar SlowArc.jar {}".format(fullPath)
    # print(cmd)
    pipe = os.popen(cmd)
    output = pipe.read()
    result = output.split("\n")[-2]
    match = re.match(r"(Ball|Strike)",result)
    
    dataFileName = resultsDir + f"{filenameNoExtension}_result.txt"
    with open(dataFileName,'r') as fd:
        expectedResult = fd.read().strip()
        
    if(match):
        testOutput = match.group(1)
        print("{}: {}".format(
            filenameNoExtension,
            "Pass" if expectedResult == testOutput else f"Fail - Expected:{expectedResult} Output:{testOutput}"
        ))
    else:
        print(f"Error on {fullPath}")

