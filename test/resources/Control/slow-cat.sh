#!/bin/bash
##
## BatchRunnerTest.sh
##
## SLOOOOW 'cat' that prints the lines of a text file in the following format:
##
## <Percentage complete>[space]<Text file line>
## 
## Author: Michael Diponio <mdiponio@eng.uts.edu.au>
##

if [ $# -lt 3 ]; then
    echo "Incorrect number of arguments, should be <file reference> <sleep time> [stdout|stderr]"
    exit 1
fi

FILE=$1
if [ ! -e $FILE ]; then
    echo "Requested file does not exist"
    exit 2
fi

SLEEP=$2
if [ $SLEEP -lt 1 ]; then
    echo "Sleep time must be greater then 1 second."
    exit 3
fi

FD=0;
case "$3" in
    stdout)
	FD=1
	;;
    stderr)
	FD=2
	;;
    *)
	echo "Redirection should be to either 'stdout' or 'stderr'"
	exit 4
	;;
esac

RUNTIME=`wc -l $FILE | cut -d ' ' -f1`
I=1
let TOTAL=$SLEEP*$RUNTIME

while [ $I -lt $RUNTIME ] ; do
    let PERCENTAGE=$I*$SLEEP*100/$TOTAL

    echo "$PERCENTAGE `head -n $I $FILE | tail -n 1`" >&$FD
    if [ $FD -eq 2 ]; then
	echo "$PERCENTAGE <Random Junk>"
    fi

    sleep $SLEEP
    let I=$I+1
done

exit 0
