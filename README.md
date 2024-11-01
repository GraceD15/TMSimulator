# Turing Machine Simulator

## Description
A simple text-input-output Turing Machine builder and simulator!

Concepts: Regular expressions, input/output, graph representation and traversal

## Features
- Build a Turing Machine (with regex matching)
- Save for later (outputs a custom code that can be pasted in to auto-construct in the future)
- Step through inputs
- Fast forward and breakpoints for inputs
- Batch test inputs

## Usage
You will need to have JDK 17 or up installed.
1. Download `TMSimulator.jar`.
2. Navigate to the download.
3. Run the following command:
```bash
   java -jar TMSimulator.jar
```

## Preview
### Pre-made TM Codes
If you want to try a TM that I've already entered, here are a few custom codes to copy and paste when building with **TM Code** mode:
(They take strings containing a's and b's only)

**Same number of a's and b's?:** 
```
q0|a/#;R -> q1|b/#;R -> q2|~q1|_/a;L -> q3|a;R -> q1|b;R -> q1|~q2|_/b;L -> q3|a;R -> q2|b;R -> q2|~q3|#;R -> q4|a;L -> q3|b;L -> q3|~q4|_;L -> q5|a;R -> q4|b/x;L -> q6|x;R -> q4|~q5|#;R -> qa|a;R -> qr|x;L -> q5|~q6|#;R -> q7|a;L -> q6|b;L -> q6|x;L -> q6|~q7|_;L -> qr|a/x;R -> q8|b;R -> q7|x;R -> q7|~q8|_;L -> qr|a/x;L -> q9|b;R -> q8|x;R -> q8|~q9|#;R -> q4|a;L -> q9|b;L -> q9|x;L -> q9|~qa|~qr|~
```
**Reverse the input:** 
```
q0|_;L -> qa|a/#;R -> q1|b/#;R -> q3|~q1|_;L -> q2|a;R -> q1|b/a;R -> q3|~q2|#/a;R -> q0|a;L -> q2|b;L -> q2|~q3|_;L -> q4|a/b;R -> q1|b;R -> q3|~q4|#/b;R -> q0|a;L -> q4|b;L -> q4|~qa|~qr|~
```
### Build
![Build example](https://github.com/user-attachments/assets/530ff186-cfb2-4c8a-a717-ec7dbb481815)
### Test
![testing demo](https://github.com/user-attachments/assets/1de9231d-8a72-4908-a46c-408acbf5d0c8)
### Batch Test
![batch test ex](https://github.com/user-attachments/assets/224cc4be-afbd-4d1e-81f0-816ab70171ec)
