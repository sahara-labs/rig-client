#!/bin/bash
##
## BatchRunnerTest.sh
##
## Prints out a message to either stdout or stderr every second for 10
## seconds.
##
## Author: Michael Diponio <mdiponio@eng.uts.edu.au>

if [ $# -ne 3 ]; then
	echo "Should be three arguments." >&2
	echo "\t1) Number of sleeps and prints." >&2
	echo "\t2) Redirection to stdout or stderr [stdout|stderr]" >&2
	echo "\t3) Exit code." >&2
fi

case "$2" in
    stdout)
    FD=1;
    ;;
    stderr)
    FD=2;
    ;;
    *)
    echo "Redirection should be stdout or stderr, failing..." >&2
    exit -1;
    ;;
esac

I=0
while [ $I -lt $1 ] ; do
    echo "Sleep loop count is $I" >&$FD
    sleep 1
    let I=$I+1
done

exit $3

