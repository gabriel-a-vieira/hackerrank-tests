'use strict';

const fs = require('fs');

process.stdin.resume();
process.stdin.setEncoding('utf-8');

let inputString = '';
let currentLine = 0;

process.stdin.on('data', function(inputStdin) {
    inputString += inputStdin;
});

process.stdin.on('end', function() {
    inputString = inputString.split('\n');

    main();
});

function readLine() {
    return inputString[currentLine++];
}



/*
 * Complete the 'mostBalancedPartition' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts following parameters:
 *  1. INTEGER_ARRAY parent
 *  2. INTEGER_ARRAY files_size
 */

function mostBalancedPartition(parent, files_size) {
    for (let i = parent.length - 1; i > 0; i--) {
        files_size[parent[i]] += files_size[i];
    }

    let mindiff = files_size[0];
    for (let i = 1; i < parent.length; i++) {
        let diff = Math.abs(files_size[0] - 2 * files_size[i]);
        if (diff < mindiff) {
            mindiff = diff;
        }
    }

    return mindiff;

}

function main() {
    const ws = fs.createWriteStream(process.env.OUTPUT_PATH);

    const parentCount = parseInt(readLine().trim(), 10);

    let parent = [];

    for (let i = 0; i < parentCount; i++) {
        const parentItem = parseInt(readLine().trim(), 10);
        parent.push(parentItem);
    }

    const files_sizeCount = parseInt(readLine().trim(), 10);

    let files_size = [];

    for (let i = 0; i < files_sizeCount; i++) {
        const files_sizeItem = parseInt(readLine().trim(), 10);
        files_size.push(files_sizeItem);
    }

    const result = mostBalancedPartition(parent, files_size);

    ws.write(result + '\n');

    ws.end();
}
