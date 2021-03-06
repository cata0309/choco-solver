#!/bin/sh

NB_NODES=1
TIME_LIMIT=500
MEM_LIMIT=500
DIR=././../../../target/
TDIR=./
SEED=0
JAR_NAME=choco-parsers-4.0.5-SNAPSHOT-with-dependencies.jar
CHOCO_JAR=${DIR}/${JAR_NAME}
usage="\

Usage: parse.sh [<options>] <file>

    Parse and solve <file> using Choco.

OPTIONS:

    -h, --help
        Display this message.

    -dir <s>
        Stands for the directory where the uploaded files reside.
        The default is ${DIR}.

    -tl <n>
        Stands for the maximum CPU time given to solver (in seconds).
        The default is ${TIME_LIMIT}.

    -p  <n>
        Stands for the number of processing units allocated to the solver.
        The default is ${NB_NODES}.

    -ml <n>
        Stands for the maximum memory that the solver may use (in MiB).
        The default is ${MEM_LIMIT}.

    -tdir <s>
        Ignored for now.
        The directory where the solver is allowed to create temporary files.
        The default is ${TDIR}.

    -seed <n>
        Ignored for now.
        Stands for a random seed to be used by the solver.
        The default is ${SEED}.

    -jar <j>
        Override the jar file.  (The default is $CHOCO_JAR.)

    --jargs <args>
		Override default java argument (\"-Xss64m -Xms64m -Xmx4096m -server\")
		
EXAMPLES:
	
	Basic command to solve a fzn model with choco:
	$> ./parse.sh alpha.xml


	Additionnal arguments:
	$> ./parse.sh --jargs \"-Xmx128m\" -tl 100 ./choco.jar ./alpha.xml

"

if test $# -eq 0
then
    echo "%% No file found"
    exit 1
fi

while test $# -gt 0
do
    case "$1" in

        -h|--help)
            echo "$usage"
            exit 0
        ;;

        -dir)
            DIR="$2"
            shift
        ;;

        -tl)
            TIME_LIMIT="$2"
            shift
        ;;

        -p)
            NB_NODES="$2"
            shift
        ;;

        -ml)
            MEM_LIMIT="$2"
            shift
        ;;

        -tdir)
            TDIR="$2"
            shift
        ;;

        -seed)
            SEED="$2"
            shift
        ;;

        -jar)
            CHOCO_JAR="$2"
            shift
        ;;

    	--jargs)
            JAVA_ARGS="$2"
            shift
        ;;

        -*)
            echo "$0: unknown option \`$1'" 1>&2
            echo "$usage" 1>&2
            exit 2
        ;;

        *)
           break
        ;;

    esac
    shift
done

FILE="$1"
ARGS=" -limit(${TIME_LIMIT}s) -p ${NB_NODES} -x 0"

CMD="java -server -Xmx${MEM_LIMIT}m -jar ${CHOCO_JAR} \"${FILE}\" ${ARGS}"

echo "c $CMD"
eval ${CMD}

